package rubedo.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolEnchantmentRecipes;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolRepairRecipes;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentTools extends ContentMultiItem<ToolBase>
		implements
			IContent {

	public ContentTools() {
		List<Class<? extends ToolBase>> toolKinds = new LinkedList<Class<? extends ToolBase>>();

		toolKinds.add(ToolSword.class);
		toolKinds.add(ToolPickaxe.class);
		toolKinds.add(ToolShovel.class);
		toolKinds.add(ToolAxe.class);
		toolKinds.add(ToolScythe.class);

		setItems(toolKinds);
	}

	@Override
	public void register() {
		super.register();

		registerToolMaterials();
		registerToolRecipes();
	}

	public void registerMaterial(Material material) {
		// Ensure a certain item only appears in these lists once, to make
		// crafting
		// easier to figure out. Players will get confused if a certain item can
		// be
		// used to craft a rod and a cap

		if (material.rodMaterial != null) {
			toolRods.put(material.name, material);

			if (materialStacks.containsKey(material.rodMaterial))
				throw new RuntimeException("Duplicate tool material resource");

			materialStacks.put(material.rodMaterial, material);
		}

		if (material.capMaterial != null) {
			toolCaps.put(material.name, material);

			if (materialStacks.containsKey(material.capMaterial))
				throw new RuntimeException("Duplicate tool material resource");

			materialStacks.put(material.rodMaterial, material);
		}

		if (material.headMaterial != null) {
			toolHeads.put(material.name, material);

			if (materialStacks.containsKey(material.headMaterial))
				throw new RuntimeException("Duplicate tool material resource");

			materialStacks.put(material.headMaterial, material);
		}
	}

	public void registerToolMaterials() {
		toolHeads = new LinkedHashMap<String, Material>();
		toolRods = new LinkedHashMap<String, Material>();
		toolCaps = new LinkedHashMap<String, Material>();
		materialStacks = new HashMap<ItemStack, Material>();

		Material flint = new Material();
		{
			flint.name = "flint";
			flint.durability = 60;
			flint.damage = 0;
			flint.speed = 4.0f;
			flint.miningLevel = 0;
			flint.headMaterial = new ItemStack(Item.flint, 1, 0);
		}
		Material wood = new Material();
		{
			wood.name = "wood";
			wood.modifier = 0.5f;
			wood.special = 0;
			wood.rodMaterial = new ItemStack(Item.stick);
			wood.capMaterial = new ItemStack(Block.planks, 1,
					OreDictionary.WILDCARD_VALUE);
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
			copper.speed = 2.0f;
			copper.miningLevel = 1;
			copper.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
			copper.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("copper_ingot"));
		}
		Material iron = new Material();
		{
			iron.name = "iron";
			iron.durability = 250;
			iron.special = 1;
			iron.damage = 2;
			iron.speed = 6.0f;
			iron.miningLevel = 2;
			iron.headMaterial = new ItemStack(Item.ingotIron, 1, 0);
			iron.capMaterial = new ItemStack(Item.ingotIron);
		}
		Material gold = new Material();
		{
			gold.name = "gold";
			gold.durability = 35;
			gold.special = 3;
			gold.damage = 0;
			gold.speed = 6.0f;
			gold.miningLevel = 0;
			gold.headMaterial = new ItemStack(Item.ingotGold, 1, 0);
			gold.capMaterial = new ItemStack(Item.ingotGold);
		}
		Material orichalcum = new Material();
		{
			orichalcum.name = "orichalcum";
			orichalcum.durability = 200;
			orichalcum.special = 3;
			orichalcum.damage = 2;
			orichalcum.speed = 4.0f;
			orichalcum.miningLevel = 1;
			orichalcum.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("orichalcum_ingot"));
			orichalcum.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("orichalcum_ingot"));
		}
		Material silver = new Material();
		{
			silver.name = "silver";
			silver.durability = 75;
			silver.special = 2;
			silver.damage = 1;
			silver.speed = 8.0f;
			silver.miningLevel = 2;
			silver.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
			silver.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("silver_ingot"));
		}
		Material steel = new Material();
		{
			steel.name = "steel";
			steel.durability = 1500;
			steel.special = 2;
			steel.damage = 3;
			steel.speed = 9.0f;
			steel.miningLevel = 3;
			steel.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("steel_ingot"));
			steel.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("steel_ingot"));
		}
		Material mythril = new Material();
		{
			mythril.name = "mythril";
			mythril.durability = 500;
			mythril.special = 4;
			mythril.damage = 1;
			mythril.speed = 12.0f;
			mythril.miningLevel = 4;
			mythril.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("mythril_ingot"));
			mythril.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("mythril_ingot"));
		}
		Material hepatizon = new Material();
		{
			hepatizon.name = "hepatizon";
			hepatizon.durability = 750;
			hepatizon.special = 5;
			hepatizon.damage = 3;
			hepatizon.speed = 10.0f;
			hepatizon.miningLevel = 4;
			hepatizon.headMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("hepatizon_ingot"));
			hepatizon.capMaterial = new ItemStack(ContentWorld.metalItems, 1,
					ContentWorld.metalItems.getTextureIndex("hepatizon_ingot"));
		}

		// TODO: this is butt ugly, this entire method is, ah well
		registerMaterial(flint);
		registerMaterial(wood);
		registerMaterial(leather);
		registerMaterial(bone);
		registerMaterial(blazerod);
		registerMaterial(copper);
		registerMaterial(iron);
		registerMaterial(gold);
		registerMaterial(orichalcum);
		registerMaterial(silver);
		registerMaterial(steel);
		registerMaterial(mythril);
		registerMaterial(hepatizon);
	}

	private void registerToolRecipes() {
		GameRegistry.addRecipe(new ToolEnchantmentRecipes());
		GameRegistry.addRecipe(new ToolRepairRecipes());

		Item[] toBeRemoved = {Item.swordDiamond, Item.swordGold,
				Item.swordIron, Item.swordStone, Item.swordWood,
				Item.shovelDiamond, Item.shovelGold, Item.shovelIron,
				Item.shovelStone, Item.shovelWood, Item.axeDiamond,
				Item.axeGold, Item.axeIron, Item.axeStone, Item.axeWood,
				Item.hoeDiamond, Item.hoeGold, Item.hoeIron, Item.hoeStone,
				Item.hoeWood, Item.pickaxeDiamond, Item.pickaxeGold,
				Item.pickaxeIron, Item.pickaxeStone, Item.pickaxeWood};

		for (int i = 0; i < toBeRemoved.length; i++) {
			toBeRemoved[i].setMaxDamage(1);
		}

		for (Entry<String, Material> headEntry : toolHeads.entrySet())
			for (Entry<String, Material> rodEntry : toolRods.entrySet())
				for (Entry<String, Material> capEntry : toolCaps.entrySet()) {
					// Sword Recipes
					GameRegistry
							.addRecipe(new ShapedRecipes(
									1,
									3,
									new ItemStack[]{
											capEntry.getValue().capMaterial,
											new ItemStack(
													ContentWorld.metalItems,
													1,
													ContentWorld.metalItems
															.getTextureIndex("tools/sword_head_"
																	+ headEntry)),
											rodEntry.getValue().rodMaterial},
									getItem(ToolSword.class).buildTool(
											headEntry.getKey(),
											rodEntry.getKey(),
											capEntry.getKey())));

					// Shovel Recipes
					GameRegistry
							.addRecipe(new ShapedRecipes(
									1,
									3,
									new ItemStack[]{
											capEntry.getValue().capMaterial,
											new ItemStack(
													ContentWorld.metalItems,
													1,
													ContentWorld.metalItems
															.getTextureIndex("tools/shovel_head_"
																	+ headEntry)),
											rodEntry.getValue().rodMaterial},
									getItem(ToolShovel.class).buildTool(
											headEntry.getKey(),
											rodEntry.getKey(),
											capEntry.getKey())));

					// Axe Recipes
					GameRegistry
							.addRecipe(new ShapedRecipes(
									1,
									3,
									new ItemStack[]{
											capEntry.getValue().capMaterial,
											new ItemStack(
													ContentWorld.metalItems,
													1,
													ContentWorld.metalItems
															.getTextureIndex("tools/axe_head_"
																	+ headEntry)),
											rodEntry.getValue().rodMaterial},
									getItem(ToolAxe.class).buildTool(
											headEntry.getKey(),
											rodEntry.getKey(),
											capEntry.getKey())));

					// Scythe Recipes
					GameRegistry
							.addRecipe(new ShapedRecipes(
									1,
									3,
									new ItemStack[]{
											capEntry.getValue().capMaterial,
											new ItemStack(
													ContentWorld.metalItems,
													1,
													ContentWorld.metalItems
															.getTextureIndex("tools/scythe_head_"
																	+ headEntry)),
											rodEntry.getValue().rodMaterial},
									getItem(ToolScythe.class).buildTool(
											headEntry.getKey(),
											rodEntry.getKey(),
											capEntry.getKey())));

					// Pickaxe Recipes
					GameRegistry
							.addRecipe(new ShapedRecipes(
									1,
									3,
									new ItemStack[]{
											capEntry.getValue().capMaterial,
											new ItemStack(
													ContentWorld.metalItems,
													1,
													ContentWorld.metalItems
															.getTextureIndex("tools/pickaxe_head_"
																	+ headEntry)),
											rodEntry.getValue().rodMaterial},
									getItem(ToolPickaxe.class).buildTool(
											headEntry.getKey(),
											rodEntry.getKey(),
											capEntry.getKey())));
				}

		// Tool head recipes
		for (Entry<String, Material> headEntry : toolHeads.entrySet()) {
			// Sword heads
			GameRegistry.addRecipe(new ShapedRecipes(1, 2, new ItemStack[]{
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial}, new ItemStack(
					ContentWorld.metalItems, 1, ContentWorld.metalItems
							.getTextureIndex("tools/sword_head_"
									+ headEntry.getValue().name))));
			// Shovel heads
			GameRegistry.addRecipe(new ShapedRecipes(2, 2, new ItemStack[]{
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial}, new ItemStack(
					ContentWorld.metalItems, 1, ContentWorld.metalItems
							.getTextureIndex("tools/shovel_head_"
									+ headEntry.getValue().name))));
			// Axe heads
			GameRegistry.addRecipe(new ShapedRecipes(2, 2, new ItemStack[]{
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial, null,
					headEntry.getValue().headMaterial}, new ItemStack(
					ContentWorld.metalItems, 1, ContentWorld.metalItems
							.getTextureIndex("tools/axe_head_"
									+ headEntry.getValue().name))));
			// Scythe heads
			GameRegistry.addRecipe(new ShapedRecipes(3, 2, new ItemStack[]{
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial, null, null,
					headEntry.getValue().headMaterial}, new ItemStack(
					ContentWorld.metalItems, 1, ContentWorld.metalItems
							.getTextureIndex("tools/scythe_head_"
									+ headEntry.getValue().name))));
			// Pick heads
			GameRegistry.addRecipe(new ShapedRecipes(3, 1, new ItemStack[]{
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial,
					headEntry.getValue().headMaterial}, new ItemStack(
					ContentWorld.metalItems, 1, ContentWorld.metalItems
							.getTextureIndex("tools/pickaxe_head_"
									+ headEntry.getValue().name))));
		}
	}

	public static Map<String, Material> toolHeads;
	public static Map<String, Material> toolRods;
	public static Map<String, Material> toolCaps;

	public static Map<ItemStack, Material> materialStacks;

	public class Material {
		public String name;
		public int durability;
		public float modifier;
		public int damage;
		public float speed;
		public int special;
		public int miningLevel;
		public ItemStack headMaterial;
		public ItemStack rodMaterial;
		public ItemStack capMaterial;
	}
}
