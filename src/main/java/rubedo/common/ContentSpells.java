package rubedo.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.config.Configuration;
import rubedo.RubedoCore;
import rubedo.items.spells.EntitySpellProjectile;
import rubedo.items.spells.SpellArea;
import rubedo.items.spells.SpellProjectile;
import rubedo.items.spells.SpellSelf;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentSpells implements IContent {
	public static SpellProjectile spellProjectile;
	public static SpellSelf spellSelf;
	public static SpellArea spellArea;

	@Override
	public void config(Configuration config) {
		// Tools
		Config.initId("SpellProjectile");
		Config.initId("SpellSelf");
		Config.initId("SpellArea");
	}

	@Override
	public void register() {
		spellProjectile = new SpellProjectile(Config.getId("SpellProjectile"));
		spellSelf = new SpellSelf(Config.getId("SpellSelf"));
		spellArea = new SpellArea(Config.getId("SpellArea"));

		registerSpellMaterials();
		// registerSpellRecipes();

		EntityRegistry.registerModEntity(EntitySpellProjectile.class,
				"SpellProjectile", cpw.mods.fml.common.registry.EntityRegistry
						.findGlobalUniqueEntityId(), RubedoCore.instance, 64,
				1, true);
	}

	public void registerSpellMaterials() {
		// Bases
		Material copper = new Material();
		{
			copper.name = "copper";
			copper.cost = 130;
			copper.power = 3;
			copper.miningLevel = 1;
			copper.focusModifier = 2.0f;
			copper.baseMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
		}

		Material iron = new Material();
		{
			iron.name = "iron";
			iron.cost = 250;
			iron.power = 2;
			iron.miningLevel = 2;
			iron.focusModifier = 0.6f;
			iron.baseMaterial = new ItemStack(Item.ingotIron);
		}

		Material gold = new Material();
		{
			gold.name = "gold";
			gold.cost = 250;
			gold.power = 5;
			gold.miningLevel = 0;
			gold.focusModifier = 4.0f;
			gold.baseMaterial = new ItemStack(Item.ingotGold);
		}

		Material silver = new Material();
		{
			silver.name = "silver";
			silver.cost = 250;
			silver.power = 10;
			silver.miningLevel = 2;
			silver.focusModifier = 1.5f;
			silver.baseMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
		}

		// Foci
		Material arrow = new Material();
		{
			arrow.name = "arrow";
			arrow.focusMaterial = new ItemStack(Item.arrow);
			arrow.castTime = 1.0f;
			arrow.focusType = "projectile";
		}

		Material bottle = new Material();
		{
			bottle.name = "bottle";
			bottle.focusMaterial = new ItemStack(Item.glassBottle);
			bottle.castTime = 1.0f;
			bottle.focusType = "self";
		}

		Material gunpowder = new Material();
		{
			gunpowder.name = "gunpowder";
			gunpowder.focusMaterial = new ItemStack(Item.gunpowder);
			gunpowder.castTime = 1.0f;
			gunpowder.focusType = "area";
		}

		// Effects
		Material blazerod = new Material();
		{
			blazerod.name = "blazerod";
			blazerod.effectMaterial = new ItemStack(Item.blazeRod);
			blazerod.effectType = "fire";
		}

		Material snowball = new Material();
		{
			snowball.name = "snowball";
			snowball.effectMaterial = new ItemStack(Item.snowball);
			snowball.effectType = "water";
		}

		Material flint = new Material();
		{
			flint.name = "flint";
			flint.effectMaterial = new ItemStack(Item.flint);
			flint.effectType = "break";
		}
		
		Material bone = new Material();
		{
			bone.name = "bone";
			bone.effectMaterial = new ItemStack(Item.bone);
			bone.effectType = "life";
		}

		spellBaseMaterials = new LinkedHashMap<String, Material>();
		{
			spellBaseMaterials.put(copper.name, copper);
			spellBaseMaterials.put(iron.name, iron);
			spellBaseMaterials.put(gold.name, gold);
			spellBaseMaterials.put(silver.name, silver);
		}

		spellFocusMaterials = new LinkedHashMap<String, Material>();
		{
			spellFocusMaterials.put(arrow.name, arrow);
			spellFocusMaterials.put(bottle.name, bottle);
			spellFocusMaterials.put(gunpowder.name, gunpowder);
		}

		spellEffectMaterials = new LinkedHashMap<String, Material>();
		{
			spellEffectMaterials.put(blazerod.name, blazerod);
			spellEffectMaterials.put(snowball.name, snowball);
			spellEffectMaterials.put(flint.name, flint);
			spellEffectMaterials.put(bone.name, bone);
		}
	}

	private void registerSpellRecipes() {
		for (Entry<String, Material> baseEntry : spellBaseMaterials.entrySet())
			for (Entry<String, Material> focusEntry : spellFocusMaterials
					.entrySet())
				for (Entry<String, Material> effectEntry : spellEffectMaterials
						.entrySet()) {
					if (focusEntry.getValue().focusType == "projectile") {
						GameRegistry.addRecipe(new ShapedRecipes(3, 3,
								new ItemStack[]{null,
										baseEntry.getValue().baseMaterial,
										null, null,
										focusEntry.getValue().focusMaterial,
										null, null,
										effectEntry.getValue().effectMaterial,
										null},

								spellProjectile.buildSpell(baseEntry.getKey(),
										focusEntry.getKey(),
										effectEntry.getKey())));
					} else if (focusEntry.getValue().focusType == "self") {
						GameRegistry.addRecipe(new ShapedRecipes(3, 3,
								new ItemStack[]{null,
										baseEntry.getValue().baseMaterial,
										null, null,
										focusEntry.getValue().focusMaterial,
										null, null,
										effectEntry.getValue().effectMaterial,
										null},

								spellSelf.buildSpell(baseEntry.getKey(),
										focusEntry.getKey(),
										effectEntry.getKey())));
					}
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
		public int power;
		public int miningLevel; // needed for "break" spells
		public float focusModifier;
		public float castTime;
		public ItemStack baseMaterial;
		public ItemStack focusMaterial;
		public ItemStack effectMaterial;
	}
}
