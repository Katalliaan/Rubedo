package rubedo.integration.fsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import rubedo.integration.fsp.item.ItemRubedoPlate;
import rubedo.integration.fsp.item.ItemToolHeadMold;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.steamcraft.api.CrucibleLiquid;
import flaxbeard.steamcraft.api.ICrucibleMold;
import flaxbeard.steamcraft.api.SteamcraftRegistry;

public class FSPIntegration {
	public static Map<String, int[]> metalColors = new HashMap<String, int[]>();
	public static List<CrucibleLiquid> liquids = new ArrayList<CrucibleLiquid>();

	static {
		metalColors.put("silver", new int[] { 190, 210, 249 });
		metalColors.put("steel", new int[] { 50, 50, 50 });
		metalColors.put("orichalcum", new int[] { 241, 146, 109 });
		metalColors.put("mythril", new int[] { 147, 207, 203 });
		metalColors.put("hepatizon", new int[] { 68, 47, 105 });
	}

	public static void preInit() {
		if (Loader.isModLoaded("rubedo")) {
			if (flaxbeard.steamcraft.Config.enableMold) {
				Item toolHeadMold = new ItemToolHeadMold();
				GameRegistry.registerItem(toolHeadMold, "moldToolHead");
				SteamcraftRegistry
						.addCarvableMold((ICrucibleMold) toolHeadMold);
			}

			if (flaxbeard.steamcraft.Config.enableCopperPlate) {
				Item plates = new ItemRubedoPlate();
				GameRegistry.registerItem(plates, "rubedoPlate");
				for (int i = 0; i < ContentWorld.metals.size(); i++) {
					Metal metal = ContentWorld.metals.get(i);
					if (metal.name != "copper") {
						OreDictionary.registerOre("plateSteamcraft" + metal,
								new ItemStack(plates, 1, i));
					}
				}
			}
		}
	}

	public static void init() {
		if (Loader.isModLoaded("rubedo")) {
			for (Metal metal : ContentWorld.metals) {
				if (metal.name != "copper"
						&& SteamcraftRegistry.getLiquidFromName(metal.name) == null) {
					int[] color = metalColors.get(metal.name);
					if (color == null) {
						color = new int[] { 255, 0, 255 };
					}

					CrucibleLiquid liquid = new CrucibleLiquid(metal.name, //
							OreDictionary.getOres("ingot" + metal).get(0), //
							OreDictionary.getOres("plateSteamcraft" + metal)
									.get(0), //
							OreDictionary.getOres("nugget" + metal).get(0), //
							null, color[0], color[1], color[2]);

					liquids.add(liquid);
					SteamcraftRegistry.registerLiquid(liquid);

					SteamcraftRegistry.registerSmeltThingOredict("ingot"
							+ metal, liquid, 9);
					SteamcraftRegistry.registerSmeltThingOredict("dustTiny"
							+ metal, liquid, 1);
					SteamcraftRegistry.registerSmeltThingOredict("nugget"
							+ metal, liquid, 1);
					SteamcraftRegistry.registerSmeltThingOredict(
							"dust" + metal, liquid, 9);
					SteamcraftRegistry.registerSmeltThingOredict(
							"plateSteamcraft" + metal, liquid, 6);
				}
			}
		}
	}
}
