package rubedo.common;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.blocks.BlockMetal;
import rubedo.blocks.BlockMetalOre;
import rubedo.items.ItemBlockMetal;
import rubedo.items.ItemBlockMetalOre;
import rubedo.items.ItemMetal;
import rubedo.items.tools.ToolEnchantmentRecipes;
import rubedo.items.tools.ToolSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class Content {
	public static CreativeTabs creativeTab;
	public static ContentTools tools;
	public static ContentWorld world;
	
	public Content() {
		Content.creativeTab = new CreativeTabs("Rubedo");
		
		registerItems();
		registerBlocks();
		registerRecipes();
		registerSmeltingRecipes();
		registerOreDictionary();
		
		world = new ContentWorld();
		tools = new ContentTools();
	}

	private void registerItems() {	
		ItemMetal.meta = new HashMap<String, Integer>();
		for (int i = 0; i < metalItemsList.length; i++)
			ItemMetal.meta.put(metalItemsList[i], i);
		
		metalItems = new ItemMetal(3302, metalItemsList);		
				
		toolSword = new ToolSword(3301);
	}
	
	private void registerBlocks() {
		// Metal blocks
		{
			BlockMetal.meta = new HashMap<String, Integer>();
			for (int i = 0; i < metalBlocksList.length; i++)
				BlockMetal.meta.put(metalBlocksList[i], i);
			
			ItemBlockMetal.names = metalBlocksList;
			
			metalBlocks = (new BlockMetal(3303, metalBlocksList))
					.setHardness(5.0F)
					.setResistance(10.0F)
					.setStepSound(Block.soundMetalFootstep);
		
			GameRegistry.registerBlock(metalBlocks, ItemBlockMetal.class, "MetalBlock");
			MinecraftForge.setBlockHarvestLevel(metalBlocks, BlockMetal.meta.get("copper_block"), "pickaxe", 1);
		}
		
		// Ores
		{
			BlockMetalOre.meta = new HashMap<String, Integer>();
			for (int i = 0; i < oreList.length; i++)
				BlockMetalOre.meta.put(oreList[i], i);
			
			ItemBlockMetalOre.names = oreList;
			
			oreBlocks = (new BlockMetalOre(3300, oreList))
					.setHardness(3.0F)
					.setResistance(5.0F)
					.setStepSound(Block.soundStoneFootstep);
			
			GameRegistry.registerBlock(oreBlocks, ItemBlockMetalOre.class, "MetalOre");
			MinecraftForge.setBlockHarvestLevel(oreBlocks, BlockMetalOre.meta.get("ore_copper"), "pickaxe", 1);
		}
	}
	
	private void registerRecipes() {
		String[] patBlock = { "###", "###", "###" };
		
		GameRegistry.addRecipe(new ToolEnchantmentRecipes());
		
		// ingot -> nuggets
		GameRegistry.addRecipe(new ItemStack(metalItems, 1, ItemMetal.meta.get("copper_ingot")), patBlock, '#', new ItemStack(metalItems, 9, ItemMetal.meta.get("copper_nugget")));
		GameRegistry.addRecipe(new ItemStack(Item.ingotIron), patBlock, '#', new ItemStack(metalItems, 9, ItemMetal.meta.get("iron_nugget")));
		
		// nuggets -> ingot
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, ItemMetal.meta.get("copper_nugget")), "m", 'm', new ItemStack(metalItems, 1, ItemMetal.meta.get("copper_ingot")));
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, ItemMetal.meta.get("iron_nugget")), "m", 'm', new ItemStack(Item.ingotIron));
		
		// block -> ingots
		GameRegistry.addRecipe(new ItemStack(metalBlocks, 1, BlockMetal.meta.get("copper_block")), patBlock, '#', new ItemStack(metalItems, 9, ItemMetal.meta.get("copper_ingot")));
		
		// ingots -> block
		GameRegistry.addRecipe(new ItemStack(metalItems, 9, ItemMetal.meta.get("copper_ingot")), "m", 'm', new ItemStack(metalBlocks, 1, BlockMetal.meta.get("copper_block")));
	}
	
	private void registerSmeltingRecipes() {
		FurnaceRecipes.smelting().addSmelting(oreBlocks.blockID, BlockMetalOre.meta.get("ore_copper"), new ItemStack(metalItems, 1, ItemMetal.meta.get("copper_ingot")), 0.5F);
	}
	
	private void registerOreDictionary() {
		OreDictionary.registerOre("oreCopper", new ItemStack(oreBlocks, 1, BlockMetalOre.meta.get("ore_copper")));
		
		OreDictionary.registerOre("ingotCopper", new ItemStack(metalItems, 1, ItemMetal.meta.get("copper_ingot")));
		
		OreDictionary.registerOre("nuggetCopper", new ItemStack(metalItems, 1, ItemMetal.meta.get("copper_nugget")));
	}
	
	public static String[] oreList = new String[] { "ore_copper" };
	public static String[] metalBlocksList = new String[] { "copper_block" };
	public static String[] metalItemsList = new String[] { 
		"tools/sword_head_flint", "tools/sword_head_copper", "tools/sword_head_iron", "tools/sword_head_gold",
		"copper_ingot", 
		"copper_nugget", "iron_nugget" };
	
	public static Block oreBlocks;
	public static Block metalBlocks;
	public static Item metalItems;
	
	public static ToolSword toolSword;
}
