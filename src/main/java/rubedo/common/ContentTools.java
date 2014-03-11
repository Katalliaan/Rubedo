package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import rubedo.items.ItemMetal;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

public class ContentTools {
	public ContentTools() {
		registerToolMaterials();
		registerToolRecipes();
	}
	
	public void registerToolMaterials() {
		Material flint = new Material();
		{
			flint.name = "flint";
			flint.durability = 60;
			flint.damage = 0;
			flint.speed = 2.0f;
			flint.swordHeadMaterial = new ItemStack(Content.metalItems, 1, ItemMetal.meta.get("tools/sword_head_flint"));
		}
		Material wood = new Material();
		{
			wood.name = "wood";
			wood.modifier = 0.5f;
			wood.special = 0;
			wood.rodMaterial = new ItemStack(Item.stick);
			wood.capMaterial = new ItemStack(Block.planks);
		}
		Material stone = new Material();
		{
			stone.name = "stone";
			stone.special = 1;
			stone.capMaterial = new ItemStack(Block.stone);
		}
		Material leather = new Material();
		{
			leather.name = "leather";
			leather.modifier = 1.0f;
			leather.rodMaterial = new ItemStack(Item.leather);
		}
		Material bone = new Material();
		{
			bone.name = "bone";
			bone.modifier = 1.2f;
			bone.rodMaterial = new ItemStack(Item.bone);
		}
		Material blazerod = new Material();
		{
			blazerod.name = "blazerod";
			blazerod.modifier = 2.0f;
			blazerod.rodMaterial = new ItemStack(Item.blazeRod);
		}
		Material copper = new Material();
		{
			copper.name = "copper";
			copper.durability = 130;
			copper.special = 2;
			copper.damage = 1;
			copper.speed = 4.0f;
			copper.swordHeadMaterial = new ItemStack(Content.metalItems, 1, ItemMetal.meta.get("tools/sword_head_copper"));
			copper.capMaterial = new ItemStack(Content.metalItems, 1, ItemMetal.meta.get("copper_ingot"));
		}
		Material iron = new Material();
		{
			iron.name = "iron";
			iron.durability = 250;
			iron.special = 3;
			iron.damage = 2;
			iron.speed = 6.0f;
			iron.swordHeadMaterial = new ItemStack(Content.metalItems, 1, ItemMetal.meta.get("tools/sword_head_iron"));
			iron.capMaterial = new ItemStack(Item.ingotIron);
		}
		Material gold = new Material();
		{
			gold.name = "gold";
			gold.durability = 130;
			gold.special = 3;
			gold.damage = 2;
			gold.speed = 6.0f;
			gold.swordHeadMaterial = new ItemStack(Content.metalItems, 1, ItemMetal.meta.get("tools/sword_head_gold"));
			gold.capMaterial = new ItemStack(Item.ingotGold);
		}
		
		toolHeadMaterials = new HashMap<String, Material>();
		{
			toolHeadMaterials.put(flint.name, flint);
			toolHeadMaterials.put(copper.name, copper);
			toolHeadMaterials.put(iron.name, iron);
			toolHeadMaterials.put(gold.name, gold);
		}
		
		toolRodMaterials = new HashMap<String, Material>();
		{
			toolRodMaterials.put(wood.name, wood);
			toolRodMaterials.put(leather.name, leather);
			toolRodMaterials.put(bone.name, bone);
			toolRodMaterials.put(blazerod.name, blazerod);
		}
		
		toolCapMaterials = new HashMap<String, Material>();
		{
			toolCapMaterials.put(wood.name, wood);
			toolCapMaterials.put(stone.name, stone);
			toolCapMaterials.put(copper.name, copper);
			toolCapMaterials.put(iron.name, iron);
			toolCapMaterials.put(gold.name, gold);
		}
	}
	
	private void registerToolRecipes() {
		for (Entry<String, Material> headEntry : toolHeadMaterials.entrySet())
    	for (Entry<String, Material> rodEntry : toolRodMaterials.entrySet())
    	for (Entry<String, Material> capEntry : toolCapMaterials.entrySet()) {
    		//Sword Recipes
    		GameRegistry.addRecipe(new ShapedRecipes(3, 3, 
    				new ItemStack[] {
    					null, headEntry.getValue().swordHeadMaterial, null,
    					null, rodEntry.getValue().rodMaterial,  null,
    					null, capEntry.getValue().capMaterial,  null
    				},
    				Content.toolSword.buildTool(
    						headEntry.getKey(), 
    						rodEntry.getKey(), 
    						capEntry.getKey())));
    		//TODO: add all other recipes
    	}
	}
	
	public static Map<String, Material> toolHeadMaterials;
	public static Map<String, Material> toolRodMaterials;
	public static Map<String, Material> toolCapMaterials;
	
	public class Material {
		public String name;
		public int durability;
		public float modifier;
		public int damage;
		public float speed;
		public int special;
		public int miningLevel;
		public ItemStack swordHeadMaterial;
		public ItemStack rodMaterial;
		public ItemStack capMaterial;
	}
}
