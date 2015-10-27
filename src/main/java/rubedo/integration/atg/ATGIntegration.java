package rubedo.integration.atg;

import static enhancedbiomes.world.biome.EnhancedBiomesGrass.biomeBadlands;
import static enhancedbiomes.world.biome.EnhancedBiomesGrass.biomeClearing;
import static enhancedbiomes.world.biome.EnhancedBiomesGrass.biomeMountainTundra;
import static enhancedbiomes.world.biome.EnhancedBiomesGrass.biomeMountains;
import static enhancedbiomes.world.biome.EnhancedBiomesGrass.biomePlateau;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeGrasslands;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeGrasslandsRoofed;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeLowHills;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeMeadow;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeMeadowM;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomePrairie;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeSavannah;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.biomeSteppe;
import static enhancedbiomes.world.biome.EnhancedBiomesRock.biomeRockHills;
import static enhancedbiomes.world.biome.EnhancedBiomesRock.biomeStoneCanyon;
import static enhancedbiomes.world.biome.EnhancedBiomesRock.biomeStoneGorge;
import static enhancedbiomes.world.biome.EnhancedBiomesRock.biomeWasteLands;
import static enhancedbiomes.world.biome.EnhancedBiomesSand.biomeRedDesert;
import static enhancedbiomes.world.biome.EnhancedBiomesSand.biomeRockyDesert;
import static enhancedbiomes.world.biome.EnhancedBiomesSand.biomeSahara;
import static enhancedbiomes.world.biome.EnhancedBiomesSand.biomeScrub;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeClayHills;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeCreekBed;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeSandStoneCanyon;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeSandStoneGorge;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeSandStoneRanges;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeScree;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeAlpine;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeAlpineM;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeGlacier;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeIceSheet;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomePlateauSnow;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomePolarDesert;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeSnowDesert;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeTundra;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.biomeWasteLandsSnowy;
import static enhancedbiomes.world.biome.EnhancedBiomesSnowForest.biomeColdBorealForest;
import static enhancedbiomes.world.biome.EnhancedBiomesSnowForest.biomeColdCypressForest;
import static enhancedbiomes.world.biome.EnhancedBiomesSnowForest.biomeColdFirForest;
import static enhancedbiomes.world.biome.EnhancedBiomesSnowForest.biomeColdPineForest;
import static enhancedbiomes.world.biome.EnhancedBiomesTropical.biomeOasis;
import static enhancedbiomes.world.biome.EnhancedBiomesTropical.biomeRainforest;
import static enhancedbiomes.world.biome.EnhancedBiomesTropical.biomeRainforestValley;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeCarr;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeEphemeralLake;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeFen;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeLake;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeMangrove;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeMarsh;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.biomeWoodlandLake;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeAspenForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeAspenHills;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeBlossomHills;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeBlossomWoods;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeBorealForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeBorealPlateau;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeBorealPlateauM;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeCypressForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeFirForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeForestMountains;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeForestValley;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeKakadu;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeOakForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomePineForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeShield;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeSilverPineForest;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeSilverPineHills;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeWoodLandHills;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeWoodLands;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.biomeWoodlandField;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import rubedo.integration.enhancedbiomes.ReplaceBiomeBlocksHandler;
import ttftcuts.atg.api.ATGBiomes;
import ttftcuts.atg.api.ATGBiomes.BiomeType;
import ttftcuts.atg.api.IGenMod;
import ttftcuts.atg.config.ATGBiomeList;
import ttftcuts.atg.gen.ATGBiomeManager;
import cpw.mods.fml.common.Loader;

public class ATGIntegration {

	public static RubedoWorldType rubedoWorldType;
	public static ATGBiomeManager biomeManager;

	/*
	 * To add:
	 * 
	 * Genmod: all Archipelagos
	 * 
	 * Genmod: biomeBasin
	 * 
	 * Replace ATG volcano with: biomeVolcano and biomeVolcanoM
	 * 
	 * Correction: add biomes to volcano group it seems
	 */

	static double nb = 0.1; // Modifier used for biomes that have their own
							// group
	static double[] tiers = { 1.0, 0.5, 0.3, 0.2, 0.1, 0.04 };

	static private BiomeType land = BiomeType.LAND;
	static private BiomeType coast = BiomeType.COAST;
	static private BiomeType sea = BiomeType.SEA;

	public static void preInit() {
		if (Loader.isModLoaded("enhancedbiomes")) {
			rubedoWorldType = new RubedoWorldType();
			biomeManager = new RubedoBiomeManager();
		}
	}

	public static void init() {
		if (Loader.isModLoaded("enhancedbiomes")) {
			MinecraftForge.EVENT_BUS.register(new ReplaceBiomeBlocksHandler());
			MinecraftForge.TERRAIN_GEN_BUS
					.register(new ReplaceBiomeBlocksHandler());
		}
	}

	public static void postInit() {
		if (Loader.isModLoaded("enhancedbiomes")) {
			addSubBiomes();
			addLandBiomesGroup();
			addCoastBiomesGroup();
			addSeaBiomesGroup();
		}
	}

	private static void addSubBiomes() {
		// ########################
		// sub-biomes
		// ########################

		ATGBiomes.addSubBiome(biomePlateau, biomePlateauSnow, 1.0);
		ATGBiomes.addSubBiome(biomeWoodLands, biomeForestMountains, 1.0);
		ATGBiomes.addSubBiome(biomeWoodLandHills, biomeForestValley, 1.0);
		ATGBiomes.addSubBiome(biomeBorealPlateau, biomeBorealPlateauM, 1.0);
		ATGBiomes.addSubBiome(biomePolarDesert, biomeIceSheet, 1.0);
		ATGBiomes.addSubBiome(biomeGrasslands, biomeGrasslandsRoofed, 1.0);
		ATGBiomes.addSubBiome(biomeMeadow, biomeMeadowM, 1.0);
		ATGBiomes.addSubBiome(biomeAlpine, biomeAlpineM, 1.0);
		ATGBiomes.addSubBiome(biomeTundra, biomeSnowDesert, 1.0);
		ATGBiomes.addSubBiome(biomeWasteLands, biomeWasteLandsSnowy, 1.0);
		ATGBiomes.addSubBiome(biomeStoneCanyon, biomeStoneGorge, 1.0);
		ATGBiomes.addSubBiome(biomeEphemeralLake, biomeWoodlandLake, 1.0);
	}

	private static void addLandBiomesGroup() {
		addForestBiomesGroup();
		addJungleBiomesGroup();
		addPlainsBiomesGroup();
		addIcePlainsBiomesGroup();
		addTaigaBiomesGroup();
		addShrublandBiomesGroup();
		addBorealForestBiomesGroup();
		addTundraBiomesGroup();
		addSteppeBiomesGroup();
		addTropicalShrublandBiomesGroup();
		addWoodlandBiomesGroup();
		addSavannaBiomes();
		addDesertBiomes();
		addMesaBiomes();

		addAtlasBiomes();
		addAlpineBiomes();
	}

	private static void addForestBiomesGroup() {
		// ########################
		// Forest
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Forest", biomeBlossomWoods, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Forest", biomeAspenForest, tiers[1]);
		ATGBiomes.addBiome(land, "Forest", biomeCypressForest, tiers[1]);
		ATGBiomes.addBiome(land, "Forest", biomeOakForest, tiers[1]);

		ATGBiomes.addBiome(land, "Forest", biomeBlossomHills, tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Forest", biomeAspenHills, tiers[2]);
		ATGBiomes.addBiome(land, "Plains", biomeClearing, tiers[2]);
	}

	private static void addJungleBiomesGroup() {
		// ########################
		// Jungle
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Jungle", biomeRainforest, tiers[0]);
	}

	private static void addPlainsBiomesGroup() {
		// ########################
		// Plains
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Plains", biomeGrasslands, tiers[0]);
		ATGBiomes.addBiome(land, "Plains", biomeMountains, tiers[0]);
		ATGBiomes.addBiome(land, "Plains", biomeLowHills, tiers[0]);
		ATGBiomes.addBiome(land, "Plains", biomeMeadow, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Plains", biomePlateau, tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Plains", biomeLake, tiers[2]);
	}

	private static void addIcePlainsBiomesGroup() {
		// ########################
		// Ice Plains
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Ice Plains", biomePolarDesert, tiers[0]);
	}

	private static void addTaigaBiomesGroup() {
		// ########################
		// Taiga
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Taiga", biomeColdPineForest, tiers[0]);
		ATGBiomes.addBiome(land, "Taiga", biomeColdFirForest, tiers[0]);
		ATGBiomes.addBiome(land, "Taiga", biomeColdBorealForest, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Taiga", biomeColdCypressForest, tiers[1]);
	}

	private static void addShrublandBiomesGroup() {
		// ########################
		// Shrubland
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Shrubland", biomeWoodlandField, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Shrubland", biomeKakadu, tiers[1] * nb);
	}

	private static void addBorealForestBiomesGroup() {
		// ########################
		// Boreal Forest
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Boreal Forest", biomeBorealForest, tiers[0]);
		ATGBiomes.addBiome(land, "Boreal Forest", biomePineForest, tiers[0]);
		ATGBiomes.addBiome(land, "Boreal Forest", biomeFirForest, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Boreal Forest", biomeBorealPlateau, tiers[1]);
		ATGBiomes.addBiome(land, "Boreal Forest", biomeStoneCanyon, tiers[1]);

		ATGBiomes.addBiome(land, "Boreal Forest", biomeSilverPineForest,
				tiers[1]);
		ATGBiomes.addBiome(land, "Boreal Forest", biomeSilverPineHills,
				tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Boreal Forest", biomeShield, tiers[2]);

		ATGBiomes.addGenMod(biomeStoneGorge, new GenModGorge());
	}

	private static void addTundraBiomesGroup() {
		// ########################
		// Tundra
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Tundra", biomeTundra, tiers[0]);
		ATGBiomes.addBiome(land, "Tundra", biomeMountainTundra, tiers[0]);
	}

	private static void addSteppeBiomesGroup() {
		// ########################
		// Steppe
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Steppe", biomeSteppe, tiers[0]);

		// Tier 3
		ATGBiomes.addBiome(land, "Steppe", biomeWasteLands, tiers[2]);
	}

	private static void addTropicalShrublandBiomesGroup() {
		// ########################
		// Tropical Shrubland
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Tropical Shrubland", biomeRainforestValley,
				tiers[0]);

		// Tier 3
		ATGBiomes.addBiome(land, "Tropical Shrubland", biomeEphemeralLake,
				tiers[2]);
	}

	private static void addWoodlandBiomesGroup() {
		// ########################
		// Woodland
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Woodland", biomeWoodLands, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Woodland", biomeWoodLandHills, tiers[1]);
	}

	private static void addSavannaBiomes() {
		// ########################
		// Savanna
		// ########################

		// Tier 1
		int tier = 0;
		ATGBiomes.addBiome(land, "Savanna", biomeSavannah, tiers[tier]);
		ATGBiomes.addBiome(land, "Savanna", biomeScrub, tiers[tier]);

		// Tier 2

		ATGBiomes.addBiome(land, "Savanna", biomePrairie, tiers[tier]);

		// Tier 3
		ATGBiomes.addBiome(land, "Savanna", biomeKakadu, tiers[tier]);
	}

	private static void addDesertBiomes() {
		// ########################
		// Desert
		// ########################

		ATGBiomeList.groupDesert.height = 0.22;

		// Tier 1
		int tier = 0;
		ATGBiomes.addBiome(land, "Desert", biomeSahara, tiers[tier]);

		// Tier 2
		tier++;
		ATGBiomes.addBiome(land, "Desert", biomeRedDesert, tiers[tier]);

		// Tier 3
		tier++;
		ATGBiomes.addBiome(land, "Desert",
				RubedoBiomeManager.mutationOf(BiomeGenBase.mesa), tiers[tier]);

		// Sub biomes
		ATGBiomes.addSubBiome(BiomeGenBase.desert,
				RubedoBiomeManager.mutationOf(BiomeGenBase.desert), tiers[3]);
		ATGBiomes.addSubBiome(biomeSahara, biomeOasis, tiers[5]);
		ATGBiomes.addSubBiome(biomeRedDesert, biomeCreekBed, tiers[4]);
	}

	private static void addMesaBiomes() {
		// ########################
		// Mesa
		// ########################
		ATGBiomeList.groupMesa.height = 0.275;
		ATGBiomeList.groupMesa.minHeight = 0.3;
		ATGBiomeList.groupMesa.maxHeight = 0.6;

		// Tier 1
		int tier = 0;
		ATGBiomes.addBiome(land, "Mesa", BiomeGenBase.desertHills, tiers[tier]);
		ATGBiomes.addBiome(land, "Mesa", biomeSandStoneCanyon, tiers[tier]);

		// Tier 2
		tier++;
		ATGBiomes.addBiome(land, "Mesa", biomeScree, tiers[tier]);
		ATGBiomes.addBiome(land, "Mesa", biomeSandStoneRanges, tiers[tier]);

		// Tier 3
		tier++;
		ATGBiomes.addBiome(land, "Mesa", biomeBadlands, tiers[tier]);

		// Sub biomes
		ATGBiomes.addSubBiome(biomeSandStoneCanyon, biomeSandStoneGorge,
				tiers[3]);

		ATGBiomes.addGenMod(biomeSandStoneGorge, new GenModGorge());
	}

	private static void addAtlasBiomes() {
		// ########################
		// Atlas
		// ########################
		ATGBiomes.addBiomeGroup(land, "Atlas", 1.6, 0.4, 0.3);
		RubedoBiomeManager.getGroupFromName("Atlas").minHeight = 0.5;
		RubedoBiomeManager.getGroupFromName("Atlas").maxHeight = 1.0;
		RubedoBiomeManager.getGroupFromName("Atlas").suitability = 0.2;

		// Tier 1
		int tier = 0;
		ATGBiomes.addBiome(land, "Atlas", biomeClayHills, tiers[tier]);
		ATGBiomes.addBiome(land, "Atlas", biomeRockyDesert, tiers[tier]);

		// Tier 2
		tier++;
		ATGBiomes
				.addBiome(land, "Atlas", BiomeGenBase.mesaPlateau, tiers[tier]);

		// Sub biomes
		ATGBiomes.addSubBiome(BiomeGenBase.mesaPlateau,
				BiomeGenBase.mesaPlateau_F, tiers[3]);
	}

	private static void addAlpineBiomes() {
		// ########################
		// Atlas
		// ########################
		ATGBiomes.addBiomeGroup(land, "Alpine", 0.00, 0.6, 0.51);
		RubedoBiomeManager.getGroupFromName("Alpine").minHeight = 0.51;
		RubedoBiomeManager.getGroupFromName("Alpine").maxHeight = 1.0;
		RubedoBiomeManager.getGroupFromName("Alpine").suitability = 0.3;

		// Tier 1
		int tier = 0;
		ATGBiomes.addBiome(land, "Alpine", biomeAlpine, tiers[tier]);
		ATGBiomes.addBiome(land, "Alpine", biomeRockHills, tiers[tier]);

		// Tier 2
		tier++;
		ATGBiomes.addBiome(land, "Alpine", biomeGlacier, tiers[tier]);
	}

	private static void addCoastBiomesGroup() {
		// ########################
		// Swamp
		// ########################

		// Tier 1
		ATGBiomes.addBiome(coast, "Swampland", biomeMangrove, tiers[0]);
		ATGBiomes.addBiome(coast, "Swampland", biomeFen, tiers[0]);
		ATGBiomes.addBiome(coast, "Swampland", biomeCarr, tiers[0]);
		ATGBiomes.addBiome(coast, "Swampland", biomeMarsh, tiers[0]);

		IGenMod swampmod = ATGBiomes.getGenMod(BiomeGenBase.swampland).get();
		ATGBiomes.addGenMod(biomeMangrove, swampmod);
		ATGBiomes.addGenMod(biomeFen, swampmod);
		ATGBiomes.addGenMod(biomeCarr, swampmod);
		ATGBiomes.addGenMod(biomeMarsh, swampmod);
	}

	private static void addSeaBiomesGroup() {
		// Add Archipelagos here with proper gen mods
	}

	private static class GenModGorge implements IGenMod {
		private final static double cutoff = 0.45;

		@Override
		public int modify(World world, int height, Random random,
				double rawHeight, int x, int z) {
			return (int) Math.round(height * 0.6
					+ ((rawHeight - cutoff) * 0.7 + cutoff) * 256 * 0.4);
		}

		@Override
		public double noiseFactor() {
			return 20.0;
		}
	}

	public static void onGuiInit(InitGuiEvent event) {
		try {
			Field f = event.gui.getClass().getDeclaredField("field_146331_K");
			f.setAccessible(true);
			f.set(event.gui, rubedoWorldType.getWorldTypeID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
