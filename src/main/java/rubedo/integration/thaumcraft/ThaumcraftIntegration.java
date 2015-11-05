package rubedo.integration.thaumcraft;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThaumcraftIntegration {
	public static class Thaumium extends MaterialMultiItem {
		public Thaumium() {
			ItemStack ingotThaumium = OreDictionary.getOres("ingotThaumium")
					.get(0);

			this.name = "thaumium";
			this.type = MaterialType.METAL_BRONZE;
			this.isColdWorkable = false;
			this.durability = 250;
			this.mundaneLevel = 3;
			this.modDamage = 0.7f;
			this.arcaneLevel = 4;
			this.damage = 2;
			this.speed = 6.0f;
			this.miningLevel = 2;
			this.headMaterial = ingotThaumium;
		}
	}

	public static void preInit() {
		registerMaterial(Thaumium.class);
	}

	public static void postInit() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		MaterialMultiItem material = contentTools.getMaterial(Thaumium.class);

		RemapHelper.removeAnyRecipe(new ItemStack(ConfigItems.itemAxeThaumium));
		RemapHelper
				.removeAnyRecipe(new ItemStack(ConfigItems.itemPickThaumium));
		RemapHelper.removeAnyRecipe(new ItemStack(
				ConfigItems.itemShovelThaumium));
		RemapHelper.removeAnyRecipe(new ItemStack(ConfigItems.itemHoeThaumium));
		RemapHelper
				.removeAnyRecipe(new ItemStack(ConfigItems.itemSwordThaumium));

		ItemStack axe = material.getToolHead("axe");

		ThaumcraftApi.addInfusionCraftingRecipe("ELEMENTALAXE", new ItemStack(
				ConfigItems.itemAxeElemental), 1,
				new AspectList().add(Aspect.WATER, 16).add(Aspect.TREE, 8),
				material.getToolHead("axe"), new ItemStack[] {
						new ItemStack(ConfigItems.itemShard, 1, 2),
						new ItemStack(ConfigItems.itemShard, 1, 2),
						new ItemStack(Items.diamond),
						new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0) });

		ThaumcraftApi.addInfusionCraftingRecipe(
				"ELEMENTALPICK",
				new ItemStack(ConfigItems.itemPickElemental),
				1,
				new AspectList().add(Aspect.FIRE, 8).add(Aspect.MINE, 8)
						.add(Aspect.SENSES, 8),
				material.getToolHead("pickaxe"), new ItemStack[] {
						new ItemStack(ConfigItems.itemShard, 1, 1),
						new ItemStack(ConfigItems.itemShard, 1, 1),
						new ItemStack(Items.diamond),
						new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0) });

		ThaumcraftApi.addInfusionCraftingRecipe("ELEMENTALSHOVEL",
				new ItemStack(ConfigItems.itemShovelElemental), 1,
				new AspectList().add(Aspect.EARTH, 16).add(Aspect.CRAFT, 8),
				material.getToolHead("shovel"), new ItemStack[] {
						new ItemStack(ConfigItems.itemShard, 1, 3),
						new ItemStack(ConfigItems.itemShard, 1, 3),
						new ItemStack(Items.diamond),
						new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0) });

		ThaumcraftApi.addInfusionCraftingRecipe("ELEMENTALHOE", new ItemStack(
				ConfigItems.itemHoeElemental), 1,
				new AspectList().add(Aspect.HARVEST, 8).add(Aspect.PLANT, 8)
						.add(Aspect.EARTH, 8), material.getToolHead("scythe"),
				new ItemStack[] { new ItemStack(ConfigItems.itemShard, 1, 4),
						new ItemStack(ConfigItems.itemShard, 1, 5),
						new ItemStack(Items.diamond),
						new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0) });

		ThaumcraftApi.addInfusionCraftingRecipe(
				"ELEMENTALSWORD",
				new ItemStack(ConfigItems.itemSwordElemental),
				1,
				new AspectList().add(Aspect.AIR, 8).add(Aspect.MOTION, 8)
						.add(Aspect.ENERGY, 8), material.getToolHead("sword"),
				new ItemStack[] { new ItemStack(ConfigItems.itemShard, 1, 0),
						new ItemStack(ConfigItems.itemShard, 1, 0),
						new ItemStack(Items.diamond),
						new ItemStack(ConfigBlocks.blockMagicalLog, 1, 0) });
	}

	public static void registerMaterial(
			Class<? extends MaterialMultiItem> materialClass) {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);

		if (contentTools.getMaterial(materialClass) == null)
			contentTools.addMaterial(materialClass);

		MaterialMultiItem material = contentTools.getMaterial(materialClass);

		if (material.headMaterial != null) {
			// Get all tool kinds
			for (ToolBase kind : contentTools.getItems()) {
				String name = kind.getName() + "_head_" + material.name;
				ItemToolHead toolHead = new ItemToolHead(name, kind.getClass(),
						material);
				GameRegistry.registerItem(toolHead, name);
				material.setToolHead(kind.getName(), new ItemStack(toolHead, 1));
			}
			if (!material.isColdWorkable) {
				String name = "_head_" + material.name;

				ItemToolHead unrefined = new ItemToolHead("unrefined" + name,
						ToolBase.class, material);
				ItemToolHead hot = new ItemToolHead("hot" + name,
						ToolBase.class, material);

				GameRegistry.registerItem(unrefined, "unrefined" + name);
				GameRegistry.registerItem(hot, "hot" + name);

				material.setToolHead("unrefined", new ItemStack(unrefined, 1));
				material.setToolHead("hot", new ItemStack(hot, 1));
				
				registerAspects();
			}
		}
	}
	
	public static void registerAspects() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ContentWorld contentWorld = Singleton.getInstance(ContentWorld.class);
		
		for ( MaterialMultiItem material : contentTools.getMaterials())
		{
			ThaumcraftApi.registerObjectTag(material.getToolHead("sword"), new AspectList().add(Aspect.WEAPON, material.damage + 1));
			ThaumcraftApi.registerObjectTag(material.getToolHead("pickaxe"), new AspectList().add(Aspect.MINE, material.miningLevel + 1));
			ThaumcraftApi.registerObjectTag(material.getToolHead("scythe"), new AspectList().add(Aspect.HARVEST, material.miningLevel + 1));
			
			ThaumcraftApi.registerObjectTag(material.getToolHead("shovel"), new AspectList().add(Aspect.TOOL, material.miningLevel + 1));
			ThaumcraftApi.registerObjectTag(material.getToolHead("axe"), new AspectList().add(Aspect.TOOL, material.miningLevel + 1));
		}
		
		ThaumcraftApi.registerObjectTag(new ItemStack(ContentWorld.metalItems, 1, ContentWorld.metalItems.getTextureIndex("copper_gem")), new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 5));
	}
}
