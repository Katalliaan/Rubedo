package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

public class ContentSpells {
	public ContentSpells() {
		registerSpellMaterials();
		registerSpellRecipes();
	}
	
	public void registerSpellMaterials() {
		// Bases		
		Material copper = new Material();
		{
			copper.name = "copper";
			copper.cost = 130;
			copper.damage = 3;
			copper.special = 4.0f;
			//TODO: replace by copper base
			copper.baseMaterial = new ItemStack(Item.ingotGold);
		}
		
		Material iron = new Material();
		{
			iron.name = "iron";
			iron.cost = 250;
			iron.damage = 2;
			iron.special = 6.0f;
			//TODO: replace by iron base
			iron.baseMaterial = new ItemStack(Item.ingotIron);
		}
		
		// Foci		
		Material arrow = new Material();
		{
			arrow.name = "arrow";
			arrow.focusMaterial = new ItemStack(Item.arrow);
			arrow.castTime = 1.0f;
		}
		
		Material gunpowder = new Material();
		{
			gunpowder.name = "gunpowder";
			gunpowder.focusMaterial = new ItemStack(Item.gunpowder);
			gunpowder.castTime = 2.0f;
		}
		
		// Effects
		Material blazerod = new Material();
		{
			blazerod.name = "blazerod";
			blazerod.effectMaterial = new ItemStack(Item.blazeRod);
		}
		
		spellBaseMaterials = new HashMap<String, Material>();
		{
			spellBaseMaterials.put(copper.name, copper);
			spellBaseMaterials.put(iron.name, iron);
		}
		
		spellFocusMaterials = new HashMap<String, Material>();
		{
			spellFocusMaterials.put(arrow.name, arrow);
			spellFocusMaterials.put(gunpowder.name, gunpowder);
		}
		
		spellEffectMaterials = new HashMap<String, Material>();
		{
			spellEffectMaterials.put(blazerod.name, blazerod);
		}
	}
	
	private void registerSpellRecipes() {
		for (Entry<String, Material> baseEntry : spellBaseMaterials.entrySet())
    	for (Entry<String, Material> focusEntry : spellFocusMaterials.entrySet())
    	for (Entry<String, Material> effectEntry : spellEffectMaterials.entrySet()) {
    		GameRegistry.addRecipe(new ShapedRecipes(3, 3, 
    				new ItemStack[] {
    					null, baseEntry.getValue().baseMaterial, null,
    					null, focusEntry.getValue().focusMaterial,  null,
    					null, effectEntry.getValue().effectMaterial,  null
    				},
    				Content.spellProjectile.buildSpell(
    						baseEntry.getKey(),
    						focusEntry.getKey(),
    						effectEntry.getKey())));
    	}
	}
	
	public static Map<String, Material> spellBaseMaterials;
	public static Map<String, Material> spellFocusMaterials;
	public static Map<String, Material> spellEffectMaterials;
	
	public class Material {
		public String name;
		public String focusType;
		public String effectType;
		public int cost;
		public float modifier;
		public int damage;
		public float special;
		public float castTime;
		public ItemStack baseMaterial;
		public ItemStack focusMaterial;
		public ItemStack effectMaterial;
	}
}
