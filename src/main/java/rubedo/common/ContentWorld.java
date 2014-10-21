package rubedo.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.RubedoCore;
import rubedo.blocks.BlockMetal;
import rubedo.blocks.BlockMetalOre;
import rubedo.items.ItemBlockMetal;
import rubedo.items.ItemBlockMetalOre;
import rubedo.items.ItemMetal;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import rubedo.world.WorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentWorld extends Singleton<ContentWorld> implements IContent {
	protected ContentWorld() {
		super(ContentWorld.class);
	}

	// Config
	public static Configuration metalsConfig;

	public static class Config {
		// Default Values
		public static boolean dropCuprite = true;
		public static double dropCupriteChance = 0.1;
		public static String oreCuprite = "oreCopper";

		public static boolean addAlloyRecipes = true;
	}

	// Blocks
	public static BlockMetalOre oreBlocks;
	public static BlockMetal metalBlocks;

	// Items
	public static ItemMetal metalItems;

	// I think you can add like 16 of these
	public static final List<Metal> metals = Arrays
			.asList(new Metal[] {
					new Metal("copper", 0, true, 8, 30, 20, 64, true,
							new int[] { -1, 1 }),
					new Metal("orichalcum", 2, false, 0, 0, 0, 0, false,
							new int[] {}),
					new Metal("steel", 2, false, 0, 0, 0, 0, false,
							new int[] {}),
					new Metal("silver", 3, true, 64, 0.5, 0, 128, false,
							new int[] { -1 }),
					new Metal("mythril", 3, false, 0, 0, 0, 0, false,
							new int[] {}),
					new Metal("hepatizon", 3, false, 0, 0, 0, 0, false,
							new int[] {}) });

	@Override
	public void config(Configuration config) {
		// Generation
		Config.dropCuprite = config.get("cuprite", "Drop", Config.dropCuprite,
				"Drop cuprite on block harvest?")
				.getBoolean(Config.dropCuprite);
		Config.dropCupriteChance = config.get("cuprite", "DropChance",
				Config.dropCupriteChance, "Drop chance for cuprite").getDouble(
				Config.dropCupriteChance);
		Config.oreCuprite = config.get("cuprite", "Ore", Config.oreCuprite,
				"Which ore yields cuprite?").getString();

		Config.addAlloyRecipes = config.get("tools", "AddAlloyRecipes",
				Config.addAlloyRecipes, "Add recipes for alloys?").getBoolean(
				Config.addAlloyRecipes);

		this.configMetals();
	}

	private void configMetals() {
		File newFile = new File(ConfigFile.getDirectory()
				+ "/rubedo_metals.cfg");

		try {
			newFile.createNewFile();
		} catch (IOException e) {
			RubedoCore.logger
					.severe("Could not create RubedoMetals.cfg. Reason:");
			RubedoCore.logger.severe(e.getLocalizedMessage());
		}

		metalsConfig = new Configuration(newFile);
		metalsConfig.load();

		for (Metal metal : metals) {
			metal.isGenerated = metalsConfig.get(metal.toString(), "Generate",
					String.valueOf(metal.isGenerated), "Generate this ore?",
					Property.Type.BOOLEAN).getBoolean(metal.isGenerated);
			metal.harvestLevel = metalsConfig.get(metal.toString(),
					"HarvestLevel", metal.harvestLevel).getInt(
					metal.harvestLevel);
			metal.oreDensity = metalsConfig.get(metal.toString(), "OreDensity",
					metal.oreDensity).getDouble(metal.oreDensity);
			metal.oreMinY = metalsConfig.get(metal.toString(), "OreMinY",
					metal.oreMinY).getInt(metal.oreMinY);
			metal.oreMaxY = metalsConfig.get(metal.toString(), "OreMaxY",
					metal.oreMaxY).getInt(metal.oreMaxY);
			metal.dimensionExclusive = metalsConfig
					.get(metal.toString(),
							"DimensionsExclusive",
							String.valueOf(metal.dimensionExclusive),
							"Is the list of dimensions the exclude list? (set to false to make it an inclusive list)",
							Property.Type.BOOLEAN).getBoolean(
							metal.dimensionExclusive);
			metal.dimensions = metalsConfig.get(metal.toString(), "Dimensions",
					metal.dimensions).getIntList();
		}

		metalsConfig.save();
	}

	@Override
	public void registerBase() {
		this.registerMetals();

		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
	}

	private void registerMetals() {
		oreBlocks = new BlockMetalOre();
		metalBlocks = new BlockMetal();
		metalItems = new ItemMetal();

		// Register blocks
		GameRegistry.registerBlock(oreBlocks, ItemBlockMetalOre.class,
				"MetalOre");
		GameRegistry.registerBlock(metalBlocks, ItemBlockMetal.class,
				"MetalBlock");
		GameRegistry.registerItem(metalItems, "MetalItems");

		// Register metals
		for (Metal metal : metals)
			this.registerMetal(metal);

		// Iron nugget recipes
		OreDictionary.registerOre("nuggetIron", new ItemStack(metalItems, 1,
				metalItems.getTextureIndex("iron_nugget")));

		ItemStack nuggets = OreDictionary.getOres("nuggetIron").get(0).copy();
		nuggets.stackSize = 9;

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				Items.iron_ingot), "###", "###", "###", '#', "nuggetIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(nuggets, "ingotIron"));

		// Bucket change
		RemapHelper.removeAnyRecipe(new ItemStack(Items.bucket));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.bucket),
				"X X", " X ", 'X', "ingotSteel"));

		// Alloy recipes
		if (Config.addAlloyRecipes) {
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(OreDictionary
						.getOres("ingotSteel").get(0), "ingotIron",
						new ItemStack(Items.blaze_rod)));
			}

			{
				ItemStack orichalcum = OreDictionary.getOres("ingotOrichalcum")
						.get(0).copy();
				orichalcum.stackSize = 2;

				GameRegistry.addRecipe(new ShapelessOreRecipe(orichalcum,
						"ingotCopper", "ingotGold"));
			}

			{
				ItemStack mythril = OreDictionary.getOres("ingotMythril")
						.get(0).copy();
				mythril.stackSize = 2;

				GameRegistry.addRecipe(new ShapelessOreRecipe(mythril,
						"ingotCopper", "ingotSilver"));
			}

			{
				ItemStack hepatizon = OreDictionary.getOres("ingotHepatizon")
						.get(0);

				GameRegistry.addRecipe(new ShapelessOreRecipe(hepatizon,
						"ingotOrichalcum", "ingotMythril", new ItemStack(
								Blocks.end_stone)));
			}
		}
	}

	private void registerMetal(Metal metal) {
		// Harvest levels
		if (metal.isGenerated == true)
			oreBlocks.setHarvestLevel("pickaxe", metal.harvestLevel, oreBlocks
					.getBehavior().getTextureMeta(metal.name + "_ore"));
		metalBlocks.setHarvestLevel("pickaxe", metal.harvestLevel, metalBlocks
				.getBehavior().getTextureMeta(metal.name + "_block"));

		if (metal.isGenerated == true)
			OreDictionary.registerOre("ore" + metal,
					new ItemStack(oreBlocks, 1, oreBlocks.getBehavior()
							.getTextureMeta(metal.name + "_ore")));
		OreDictionary.registerOre("ingot" + metal, new ItemStack(metalItems, 1,
				metalItems.getTextureIndex(metal.name + "_ingot")));
		OreDictionary.registerOre("nugget" + metal, new ItemStack(metalItems,
				1, metalItems.getTextureIndex(metal.name + "_nugget")));
		OreDictionary.registerOre("block" + metal,
				new ItemStack(metalBlocks, 1, metalBlocks.getBehavior()
						.getTextureMeta(metal.name + "_block")));

		// Recipes: nugget <-> ingot <-> block
		ItemStack nuggets = OreDictionary.getOres("nugget" + metal).get(0)
				.copy();
		nuggets.stackSize = 9;
		ItemStack ingots = OreDictionary.getOres("ingot" + metal).get(0).copy();
		ingots.stackSize = 9;

		GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres(
				"ingot" + metal).get(0), "###", "###", "###", '#', "nugget"
				+ metal));
		GameRegistry
				.addRecipe(new ShapelessOreRecipe(nuggets, "ingot" + metal));
		GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres(
				"block" + metal).get(0), "###", "###", "###", '#', "ingot"
				+ metal));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ingots, "block" + metal));

		if (metal.isGenerated == true)
			GameRegistry.addSmelting(
					new ItemStack(oreBlocks, 1, oreBlocks.getBehavior()
							.getTextureMeta(metal.name + "_ore")),
					new ItemStack(metalItems, 1, metalItems
							.getTextureIndex(metal.name + "_ingot")), 0.5F);
	}

	public static class Metal {
		public String name;

		public boolean isGenerated;
		public int harvestLevel;

		public int oreSize;
		public double oreDensity;
		public int oreMinY;
		public int oreMaxY;

		public boolean dimensionExclusive;
		public int[] dimensions;
		private List<Integer> dimensionList;

		/**
		 * 
		 * @param name
		 * @param harvestLevel
		 * @param isGenerated
		 * @param oreSize
		 * @param oreDensity
		 * @param oreMinY
		 * @param oreMaxY
		 * @param dimensionExclude
		 * @param dimensions
		 */
		public Metal(String name, int harvestLevel, boolean isGenerated,
				int oreSize, double oreDensity, int oreMinY, int oreMaxY,
				boolean dimensionExclude, int[] dimensions) {
			this.name = name;

			this.isGenerated = isGenerated;
			this.harvestLevel = harvestLevel;

			this.oreSize = oreSize;
			this.oreDensity = oreDensity;
			this.oreMinY = oreMinY;
			this.oreMaxY = oreMaxY;

			this.dimensionExclusive = dimensionExclude;
			this.dimensions = dimensions;
		}

		@Override
		public String toString() {
			return this.name.substring(0, 1).toUpperCase()
					+ this.name.substring(1);
		}

		/**
		 * cached for performance!
		 */
		public List<Integer> getDimensionList() {
			if (this.dimensionList == null
					|| this.dimensionList.size() != this.dimensions.length) {
				this.dimensionList = new ArrayList<Integer>();

				for (int d = 0; d < this.dimensions.length; d++) {
					this.dimensionList.add(this.dimensions[d]);
				}
			}

			return this.dimensionList;
		}
	}

	@Override
	public void registerDerivatives() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tweak() {
		// TODO Auto-generated method stub

	}
}
