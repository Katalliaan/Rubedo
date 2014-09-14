package rubedo.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.RubedoCore;
import rubedo.blocks.BlockMetal;
import rubedo.blocks.BlockMetalOre;
import rubedo.items.ItemBlockMetal;
import rubedo.items.ItemBlockMetalOre;
import rubedo.items.ItemMetal;
import rubedo.world.WorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentWorld implements IContent {
	// Config
	public static Configuration metalsConfig;
	
	// Blocks
	public static BlockMetalOre oreBlocks;
	public static BlockMetal metalBlocks;
	
	// Items
	public static ItemMetal  metalItems;
	
	// I think you can add like 16 of these
	public static final List<Metal> metals = Arrays.asList(new Metal[] {
			new Metal("copper", 0, true, 8, 8, 20, 64, true, new int[] { -1, 1 }),
			new Metal("orichalcum", 2, false, 0, 0, 0, 0, false, new int[]{}),
			new Metal("steel", 2, false, 0, 0, 0, 0, false, new int[]{}),
			new Metal("silver", 3, true, 64, 0.5, 0, 128, false, new int[] { -1 }),
			new Metal("mythril", 3, false, 0, 0, 0, 0, false, new int[]{}),
			new Metal("hepatizon", 3, false, 0, 0, 0, 0, false, new int[]{})
	});

	@Override
	public void config(Configuration config) {		
		// Generation
		configMetals();
	}
	
	private void configMetals() {
        File newFile = new File(Config.getDirectory() + "/rubedo_metals.cfg");

        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            RubedoCore.logger.severe("Could not create RubedoMetals.cfg. Reason:");
            RubedoCore.logger.severe(e.getLocalizedMessage());
        }

        metalsConfig = new Configuration(newFile);
        metalsConfig.load();
        
        for (Metal metal : metals) {
        	metal.isGenerated = metalsConfig.get(metal.toString(), "Generate", String.valueOf(metal.isGenerated), "Generate this ore?", Property.Type.BOOLEAN).getBoolean(metal.isGenerated);
        	metal.harvestLevel = metalsConfig.get(metal.toString(), "Harvest Level", metal.harvestLevel).getInt(metal.harvestLevel);
        	metal.oreDensity = metalsConfig.get(metal.toString(), "Ore Density", metal.oreDensity).getDouble(metal.oreDensity);
    		metal.oreMinY = metalsConfig.get(metal.toString(), "Ore MinY", metal.oreMinY).getInt(metal.oreMinY);
    		metal.oreMaxY = metalsConfig.get(metal.toString(), "Ore MaxY", metal.oreMaxY).getInt(metal.oreMaxY);
    		metal.dimensionExclusive = metalsConfig.get(metal.toString(), "Dimensions Exclusive", String.valueOf(metal.dimensionExclusive), "Is the list of dimensions the exclude list? (set to false to make it an inclusive list)", Property.Type.BOOLEAN).getBoolean(metal.dimensionExclusive);
    		metal.dimensions = metalsConfig.get(metal.toString(), "Dimensions", metal.dimensions).getIntList();
        }
        
        metalsConfig.save();
	}

	@Override
	public void register() {
		registerMetals();
				
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
	}
	
	private void registerMetals() {
		oreBlocks = new BlockMetalOre();
		metalBlocks = new BlockMetal();
		metalItems = new ItemMetal();
		
		// Register blocks
		GameRegistry.registerBlock(oreBlocks, ItemBlockMetalOre.class, "MetalOre");
		GameRegistry.registerBlock(metalBlocks, ItemBlockMetal.class, "MetalBlock");
		
		// Register metals
		for (Metal metal : metals)
			registerMetal(metal);
		
		// Iron nugget recipes
		GameRegistry.addRecipe(new ItemStack(Items.iron_ingot), "###", "###", "###", '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex("iron_nugget")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex("iron_nugget")), "m", 'm', new ItemStack(Items.iron_ingot));
		OreDictionary.registerOre("nuggetIron", new ItemStack(metalItems, 9, metalItems.getTextureIndex("iron_nugget")));
		
		// Bucket change
		RecipeRemover.removeAnyRecipe(new ItemStack(Items.bucket));
		GameRegistry.addRecipe(new ShapedRecipes(3, 2, new ItemStack[]{new ItemStack(metalItems, 2, metalItems.getTextureIndex("steel_ingot")), null, new ItemStack(metalItems, 2, metalItems.getTextureIndex("steel_ingot")), null, new ItemStack(metalItems, 2, metalItems.getTextureIndex("steel_ingot")), null}, new ItemStack(Items.bucket)));
		
		// Temporary alloy recipes
		GameRegistry.addShapelessRecipe(new ItemStack(metalItems, 2, metalItems.getTextureIndex("orichalcum_ingot")), new ItemStack(metalItems, 1, metalItems.getTextureIndex("copper_ingot")), new ItemStack(Items.gold_ingot));
		GameRegistry.addShapelessRecipe(new ItemStack(metalItems, 2, metalItems.getTextureIndex("steel_ingot")), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.soul_sand));
		GameRegistry.addShapelessRecipe(new ItemStack(metalItems, 2, metalItems.getTextureIndex("mythril_ingot")), new ItemStack(metalItems, 1, metalItems.getTextureIndex("copper_ingot")), new ItemStack(metalItems, 1, metalItems.getTextureIndex("silver_ingot")));
		GameRegistry.addShapelessRecipe(new ItemStack(metalItems, 2, metalItems.getTextureIndex("hepatizon_ingot")), new ItemStack(metalItems, 1, metalItems.getTextureIndex("orichalcum_ingot")), new ItemStack(metalItems, 1, metalItems.getTextureIndex("mythril_ingot")), new ItemStack(Blocks.end_stone));
	}
	
	private void registerMetal(Metal metal) {
		String[] patBlock = { "###", "###", "###" };
		
		// Harvest levels
		if (metal.isGenerated == true)
			oreBlocks.setHarvestLevel("pickaxe", metal.harvestLevel, oreBlocks.getTextureIndex(metal.name+"_ore"));
		metalBlocks.setHarvestLevel("pickaxe", metal.harvestLevel, metalBlocks.getTextureIndex(metal.name+"_block"));
		
		// Recipes: nugget <-> ingot <-> block
		GameRegistry.addRecipe(new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")), patBlock, '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_nugget")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_nugget")), "m", 'm', new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")));
		GameRegistry.addRecipe(new ItemStack(metalBlocks, 1, metalBlocks.getTextureIndex(metal.name+"_block")), patBlock, '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_ingot")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_ingot")), "m", 'm', new ItemStack(metalBlocks, 1, metalBlocks.getTextureIndex(metal.name+"_block")));
	
		if (metal.isGenerated == true)
			GameRegistry.addSmelting(
					new ItemStack(oreBlocks, 1,
					oreBlocks.getTextureIndex(metal.name+"_ore")), 
					new ItemStack(
							metalItems, 
							1, 
							metalItems.getTextureIndex(metal.name+"_ingot")
					), 
					0.5F);
		
		if (metal.isGenerated == true)
			OreDictionary.registerOre("ore"+metal, new ItemStack(oreBlocks, 1, oreBlocks.getTextureIndex(metal.name+"_ore")));
		OreDictionary.registerOre("ingot"+metal, new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")));
		OreDictionary.registerOre("nugget"+metal, new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_nugget")));
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
		public Metal(
				String name, int harvestLevel, boolean isGenerated,
				int oreSize, double oreDensity, int oreMinY, int oreMaxY,
				boolean dimensionExclude, int[] dimensions) 
		{
			this.name = name;
			
			this.isGenerated = true;
			this.harvestLevel = harvestLevel;
			
			this.oreSize = oreSize;
			this.oreDensity = oreDensity;
			this.oreMinY = oreMinY;
			this.oreMaxY = oreMaxY;
			
			this.dimensionExclusive = dimensionExclude;
			this.dimensions = dimensions;
		}
		
		public String toString() {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		
		/**
		 * cached for performance!
		 */
		public List<Integer> getDimensionList() {
			if (dimensionList == null || dimensionList.size() != dimensions.length) {
				dimensionList = new ArrayList<Integer>();
				
				for (int d = 0; d < dimensions.length; d++) {
	        		dimensionList.add(dimensions[d]);
				}
			}
			
			return dimensionList;
		}
	}
}
