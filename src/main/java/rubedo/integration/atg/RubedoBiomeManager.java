package rubedo.integration.atg;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraft.world.biome.BiomeGenBase;
import ttftcuts.atg.biome.ATGBiomeGroup;
import ttftcuts.atg.gen.ATGBiomeManager;
import ttftcuts.atg.gen.ATGBiomeMod;

public class RubedoBiomeManager extends ATGBiomeManager {
	private static ATGBiomeMod[] genMods = new ATGBiomeMod[256];

	public RubedoBiomeManager() {
		try {
			Field field = ATGBiomeManager.class.getDeclaredField("genMods");
			field.setAccessible(true);

			genMods = (ATGBiomeMod[]) field.get(this);

		} catch (Exception e) {
			throw new ReportedException(new CrashReport(
					e.getLocalizedMessage(), e));
		}
	}

	public static BiomeGenBase mutationOf(BiomeGenBase biome) {
		return BiomeGenBase.getBiome(biome.biomeID + 128);
	}

	public static void clear() {
		landGroups = new HashMap<String, ATGBiomeGroup>();
		seaGroups = new HashMap<String, ATGBiomeGroup>();
		coastGroups = new HashMap<String, ATGBiomeGroup>();

		genMods = new ATGBiomeMod[256];
	}
}
