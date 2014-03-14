package rubedo.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
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
			// name, harvestLevel, isGenerated, oreDensity, oreMinY, oreMaxY, dimensionExclude, dimensions
			new Metal("copper", 1, true, 8, 20, 64, true, new int[] { -1, 1 })
	});

	@Override
	public void config(Configuration config) {
		// Blocks
		Config.initId("BlockOres");
		Config.initId("BlockMetals");
		
		// Items
		Config.initId("ItemMetals");
		
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
        	metal.isGenerated = metalsConfig.get(metal.toString(), "Generate", metal.isGenerated).getBoolean(metal.isGenerated);
        	metal.harvestLevel = metalsConfig.get(metal.toString(), "Harvest Level", metal.harvestLevel).getInt(metal.harvestLevel);
        	metal.oreDensity = metalsConfig.get(metal.toString(), "Ore Density", metal.oreDensity).getInt(metal.oreDensity);
    		metal.oreMinY = metalsConfig.get(metal.toString(), "Ore MinY", metal.oreMinY).getInt(metal.oreMinY);
    		metal.oreMaxY = metalsConfig.get(metal.toString(), "Ore MaxY", metal.oreMaxY).getInt(metal.oreMaxY);
    		metal.dimensionExclusive = metalsConfig.get(metal.toString(), "Dimensions Exclusive", metal.dimensionExclusive).getBoolean(metal.dimensionExclusive);
    		metal.dimensions = metalsConfig.get(metal.toString(), "Dimensions", metal.dimensions).getIntList();
        }
        
        metalsConfig.save();
	}

	@Override
	public void register() {
		registerMetals();
				
		GameRegistry.registerWorldGenerator(new WorldGenerator());
	}
	
	private void registerMetals() {
		oreBlocks = new BlockMetalOre(Config.getId("BlockOres"));
		metalBlocks = new BlockMetal(Config.getId("BlockMetals"));
		metalItems = new ItemMetal(Config.getId("ItemMetals"));
		
		// Register blocks
		GameRegistry.registerBlock(oreBlocks, ItemBlockMetalOre.class, "MetalOre");
		GameRegistry.registerBlock(metalBlocks, ItemBlockMetal.class, "MetalBlock");
		
		// Register metals
		for (Metal metal : metals)
			registerMetal(metal);
		
		// Iron nugget recipes
		GameRegistry.addRecipe(new ItemStack(Item.ingotIron), "###", "###", "###", '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex("iron_nugget")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex("iron_nugget")), "m", 'm', new ItemStack(Item.ingotIron));
	}
	
	private void registerMetal(Metal metal) {
		String[] patBlock = { "###", "###", "###" };
		
		// Harvest levels
		MinecraftForge.setBlockHarvestLevel(oreBlocks, oreBlocks.getTextureIndex(metal.name+"_ore"), "pickaxe", metal.harvestLevel);
		MinecraftForge.setBlockHarvestLevel(metalBlocks, metalBlocks.getTextureIndex(metal.name+"_block"), "pickaxe", metal.harvestLevel);
		
		// Recipes: nugget <-> ingot <-> block
		GameRegistry.addRecipe(new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")), patBlock, '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_nugget")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_nugget")), "m", 'm', new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")));
		GameRegistry.addRecipe(new ItemStack(metalBlocks, 1, metalBlocks.getTextureIndex(metal.name+"_block")), patBlock, '#', new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_ingot")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, metalItems.getTextureIndex(metal.name+"_ingot")), "m", 'm', new ItemStack(metalBlocks, 1, metalBlocks.getTextureIndex(metal.name+"_block")));
	
		FurnaceRecipes.smelting().addSmelting(oreBlocks.blockID, metalBlocks.getTextureIndex(metal.name+"_ore"), new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")), 0.5F);
	
		OreDictionary.registerOre("ore"+metal, new ItemStack(oreBlocks, 1, metalBlocks.getTextureIndex(metal.name+"_ore")));
		OreDictionary.registerOre("ingot"+metal, new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_ingot")));
		OreDictionary.registerOre("nugget"+metal, new ItemStack(metalItems, 1, metalItems.getTextureIndex(metal.name+"_nugget")));
	}
	
	public static class Metal {
		public String name;
		
		public boolean isGenerated;
		public int harvestLevel;
		
		public int oreDensity;
		public int oreMinY;
		public int oreMaxY;
		
		public boolean dimensionExclusive;
		public int[] dimensions;
		private List<Integer> dimensionList;
		
		public Metal(
				String name, int harvestLevel, boolean isGenerated,
				int oreDensity, int oreMinY, int oreMaxY,
				boolean dimensionExclude, int[] dimensions) 
		{
			this.name = name;
			
			this.isGenerated = true;
			this.harvestLevel = harvestLevel;
			
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
