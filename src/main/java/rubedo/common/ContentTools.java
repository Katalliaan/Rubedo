package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import cpw.mods.fml.common.registry.GameRegistry;

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
			flint.damage = 1;
			flint.speed = 2.0f;
			flint.headMaterial = new ItemStack(Item.flint);
		}
		Material wood = new Material();
		{
			wood.name = "wood";
			wood.modifier = 1.0f;
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
			leather.modifier = 1.2f;
			leather.rodMaterial = new ItemStack(Item.leather);
		}
		Material bone = new Material();
		{
			bone.name = "bone";
			bone.modifier = 1.4f;
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
			copper.damage = 2;
			copper.speed = 4.0f;
			//TODO: replace by copper head
			copper.headMaterial = new ItemStack(Item.ingotGold);
			//TODO: replace by copper ingot
			copper.capMaterial = new ItemStack(Item.ingotGold);
		}
		Material iron = new Material();
		{
			iron.name = "iron";
			iron.durability = 250;
			iron.special = 3;
			iron.damage = 3;
			iron.speed = 6.0f;
			//TODO: replace by iron head
			iron.headMaterial = new ItemStack(Item.ingotIron);
			iron.capMaterial = new ItemStack(Item.ingotIron);
		}
		
		toolHeadMaterials = new HashMap<String, Material>();
		{
			toolHeadMaterials.put(flint.name, flint);
			toolHeadMaterials.put(copper.name, copper);
			toolHeadMaterials.put(iron.name, iron);
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
		}
	}
	
	private void registerToolRecipes() {
		for (Entry<String, Material> headEntry : toolHeadMaterials.entrySet())
    	for (Entry<String, Material> rodEntry : toolRodMaterials.entrySet())
    	for (Entry<String, Material> capEntry : toolCapMaterials.entrySet()) {
    		//Sword Recipes
    		GameRegistry.addRecipe(new ShapedRecipes(3, 3, 
    				new ItemStack[] {
    					null, headEntry.getValue().headMaterial, null,
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
		public ItemStack headMaterial;
		public ItemStack rodMaterial;
		public ItemStack capMaterial;
	}
}
