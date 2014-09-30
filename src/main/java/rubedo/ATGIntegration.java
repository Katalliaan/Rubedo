package rubedo;


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
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.biomeSandStoneRangesM;
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

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import ttftcuts.atg.api.ATGBiomes;
import ttftcuts.atg.api.ATGBiomes.BiomeType;
import ttftcuts.atg.api.IGenMod;

public class ATGIntegration {

	/*
	 * To add: Genmod: all Archipelagos Genmod: biomeBasin
	 * 
	 * Replace ATG volcano with: biomeVolcano and biomeVolcanoM
	 */

	static double nb = 0.1; // Modifier used for biomes that have their own
							// group
	static double[] tiers = { 1.0, 0.5, 0.3, 0.2, 0.1, 0.04 };

	static private BiomeType land = BiomeType.LAND;
	static private BiomeType coast = BiomeType.COAST;
	static private BiomeType sea = BiomeType.SEA;

	protected static void init() {
		addSubBiomes();
		addLandBiomesGroup();
		addCoastBiomesGroup();
		addSeaBiomesGroup();
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

		ATGBiomes.addSubBiome(biomeSandStoneRanges, biomeSandStoneRangesM, 1.0);
		ATGBiomes.addSubBiome(biomeGrasslands, biomeGrasslandsRoofed, 1.0);
		ATGBiomes.addSubBiome(biomeMeadow, biomeMeadowM, 1.0);
		ATGBiomes.addSubBiome(biomeAlpine, biomeAlpineM, 1.0);
		ATGBiomes.addSubBiome(biomeTundra, biomeSnowDesert, 1.0);
		ATGBiomes.addSubBiome(biomeWasteLands, biomeWasteLandsSnowy, 1.0);
		ATGBiomes.addSubBiome(biomeSandStoneCanyon, biomeSandStoneGorge, 1.0);
		ATGBiomes.addSubBiome(biomeStoneCanyon, biomeStoneGorge, 1.0);
		ATGBiomes.addSubBiome(biomeEphemeralLake, biomeWoodlandLake, 1.0);
	}

	private static void addLandBiomesGroup() {
		addForestBiomesGroup();
		addJungleBiomesGroup();
		addPlainsBiomesGroup();
		addIcePlainsBiomesGroup();
		addTaigaBiomesGroup();
		addDesertBiomesGroup();
		addShrublandBiomesGroup();
		addBorealForestBiomesGroup();
		addTundraBiomesGroup();
		addSteppeBiomesGroup();
		addSavannahBiomesGroup();
		addTropicalShrublandBiomesGroup();
		addWoodlandBiomesGroup();
		addMesaBiomesGroup();
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
		ATGBiomes.addBiome(land, "Ice Plains", biomeAlpine, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Ice Plains", biomeGlacier, tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Ice Plains", biomeScree, tiers[2]);
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

	private static void addDesertBiomesGroup() {
		// ########################
		// Desert
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Desert", biomeSahara, tiers[0]);
		ATGBiomes.addBiome(land, "Desert", biomeRedDesert, tiers[0]);

		// Tier 3
		// ATGBiomes.addBiome(land, "Desert", biomeXericShrubland, tiers[2]);

		// Tier 4
		ATGBiomes.addBiome(land, "Desert", biomeCreekBed, tiers[3]);

		// Tier 5
		ATGBiomes.addBiome(land, "Desert", biomeOasis, tiers[4]);
	}

	private static void addShrublandBiomesGroup() {
		// ########################
		// Shrubland
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Shrubland", biomeWoodlandField, tiers[0]);
		ATGBiomes.addBiome(land, "Shrubland", biomeSandStoneRanges, tiers[0]);

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

		ATGBiomes.addBiome(land, "Boreal Forest", biomeSilverPineForest,
				tiers[1]);
		ATGBiomes.addBiome(land, "Boreal Forest", biomeSilverPineHills,
				tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Boreal Forest", biomeShield, tiers[2]);
	}

	private static void addTundraBiomesGroup() {
		// ########################
		// Tundra
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Tundra", biomeTundra, tiers[0]);
		ATGBiomes.addBiome(land, "Tundra", biomeMountainTundra, tiers[0]);

		// Tier 2
		ATGBiomes.addBiome(land, "Tundra", biomePolarDesert, tiers[1]);
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

	private static void addSavannahBiomesGroup() {
		// ########################
		// Savannah
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Savannah", biomeSavannah, tiers[0]);
		ATGBiomes.addBiome(land, "Savannah", biomeScrub, tiers[0]);

		// Tier 2

		ATGBiomes.addBiome(land, "Savannah", biomePrairie, tiers[1]);

		// Tier 3
		ATGBiomes.addBiome(land, "Savannah", biomeKakadu, tiers[2]);
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

	private static void addMesaBiomesGroup() {
		// ########################
		// Mesa
		// ########################

		// Tier 1
		ATGBiomes.addBiome(land, "Mesa", biomeBadlands, tiers[0]);
		ATGBiomes.addBiome(land, "Mesa", biomeRockyDesert, tiers[0]);
		ATGBiomes.addBiome(land, "Mesa", biomeRockHills, tiers[0]);
		ATGBiomes.addBiome(land, "Mesa", biomeSandStoneCanyon, tiers[0]);
		ATGBiomes.addBiome(land, "Mesa", biomeClayHills, tiers[0]);

		ATGBiomes.addGenMod(biomeStoneGorge, new GenModGorge());
		ATGBiomes.addGenMod(biomeSandStoneGorge, new GenModGorge());
	}

	private static void addCoastBiomesGroup() {
		// ########################
		// Swamp
		// ########################

		// Tier 1
		ATGBiomes.addBiome(coast, "Swamp", biomeMangrove, tiers[0]);
		ATGBiomes.addBiome(coast, "Swamp", biomeFen, tiers[0]);
		ATGBiomes.addBiome(coast, "Swamp", biomeCarr, tiers[0]);
		ATGBiomes.addBiome(coast, "Swamp", biomeMarsh, tiers[0]);

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
}
