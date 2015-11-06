package rubedo.integration.thaumcraft;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.RubedoCore;
import rubedo.common.ContentBlackSmith;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.util.Singleton;
import scala.actors.threadpool.Arrays;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
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

	static ContentTools contentTools = Singleton
			.getInstance(ContentTools.class);

	static ItemStack thaumiumTools[] = {
			new ItemStack(ConfigItems.itemAxeThaumium),
			new ItemStack(ConfigItems.itemShovelThaumium),
			new ItemStack(ConfigItems.itemPickThaumium),
			new ItemStack(ConfigItems.itemHoeThaumium),
			new ItemStack(ConfigItems.itemSwordThaumium) };

	public static void preInit() {
		registerMaterial(Thaumium.class);
	}

	public static void postInit() {
		addRecipes();

		registerAspects();
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

			}
		}
	}

	public static void addRecipes() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		MaterialMultiItem material = contentTools.getMaterial(Thaumium.class);

		ItemStack thaumiumTools[] = {
				new ItemStack(ConfigItems.itemAxeThaumium),
				new ItemStack(ConfigItems.itemShovelThaumium),
				new ItemStack(ConfigItems.itemPickThaumium),
				new ItemStack(ConfigItems.itemHoeThaumium),
				new ItemStack(ConfigItems.itemSwordThaumium) };

		ArrayList craftingRecipes = new ArrayList<Object>(
				ThaumcraftApi.getCraftingRecipes());

		for (Object recipe : craftingRecipes) {
			if (recipe instanceof InfusionRecipe
					&& ((InfusionRecipe) recipe).getRecipeOutput() instanceof ItemStack) {
				InfusionRecipe infusion = (InfusionRecipe) recipe;

				ItemStack input = infusion.getRecipeInput();
				ItemStack components[] = infusion.getComponents();
				boolean changed = false;

				for (int i = 0; i < thaumiumTools.length; i++) {
					if (matchesThaumiumTool(infusion.getRecipeInput(), i)) {
						input = getThaumiumHead(i);
						changed = true;
					}

					for (ItemStack component : components) {
						if (matchesThaumiumTool(component, i)) {
							component = getThaumiumHead(i);
							changed = true;
						}
					}
				}

				if (changed) {
					ThaumcraftApi.addInfusionCraftingRecipe(
							infusion.getResearch(), infusion.getRecipeOutput(),
							infusion.getInstability(), infusion.getAspects(),
							input, components);
				}
			} else if (recipe instanceof ShapedArcaneRecipe) {
				ShapedArcaneRecipe shaped = (ShapedArcaneRecipe) recipe;

				Object[] input = shaped.getInput();
				boolean changed = false;
				
				for (int i = 0; i < input.length; i++) {
					if (input[i] instanceof ItemStack) {
						for (int j = 0; j < thaumiumTools.length; j++) {
							if (matchesThaumiumTool((ItemStack)input[i], j)) {
								input[i] = getThaumiumHead(j);
								changed = true;
							}
						}
					}
				}

				if (changed) {
					ArrayList<Object> uniqueItems = new ArrayList<Object>();
					String shape[] = new String[shaped.height];
					Character symbol = new Character('A');

					for (int y = 0; y < shaped.height; y++) {
						shape[y] = "";
						
						for (int x = 0; x < shaped.width; x++) {
							if (input[y * shaped.height + x] == null) {
								shape[y] = shape[y] + " ";
							} else {
								if (uniqueItems.contains(input[y
										* shaped.height + x])) {
									shape[y] = shape[y]
											+ uniqueItems
													.get(uniqueItems
															.indexOf(input[y
																	* shaped.height
																	+ x]) - 1);
								} else {
									uniqueItems.add(symbol);
									shape[y] = shape[y] + symbol;
									symbol++;
									uniqueItems
											.add(input[y * shaped.height + x]);
								}
							}
						}
					}
					Object[] recipeItems = new Object[shape.length + uniqueItems.size()];
					
					for (int i = 0; i < shape.length; i++) {
						recipeItems[i] = shape[i];
					}
					
					for (int i = shape.length; i < shape.length + uniqueItems.size(); i++) {
						recipeItems[i] = uniqueItems.get(i - shape.length);
					}

					ThaumcraftApi.addArcaneCraftingRecipe(shaped.getResearch(),
							shaped.getRecipeOutput(), shaped.getAspects(), recipeItems);
				}
			}
		}
	}

	private static boolean matchesThaumiumTool(ItemStack input, int i) {
		return InfusionRecipe
				.areItemStacksEqual(thaumiumTools[i], input, false);
	}

	private static ItemStack getThaumiumHead(int i) {
		MaterialMultiItem material = contentTools.getMaterial(Thaumium.class);
		ItemStack thaumiumHeads[] = { material.getToolHead("axe"),
				material.getToolHead("shovel"),
				material.getToolHead("pickaxe"),
				material.getToolHead("scythe"), material.getToolHead("sword") };

		return thaumiumHeads[i];
	}

	public static void registerAspects() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ContentWorld contentWorld = Singleton.getInstance(ContentWorld.class);
		ContentBlackSmith contentBS = Singleton
				.getInstance(ContentBlackSmith.class);

		for (MaterialMultiItem material : contentTools.getMaterials()) {
			ThaumcraftApi.registerObjectTag(material.getToolHead("sword"),
					new AspectList().add(Aspect.WEAPON, material.damage + 1));
			ThaumcraftApi
					.registerObjectTag(material.getToolHead("pickaxe"),
							new AspectList().add(Aspect.MINE,
									material.miningLevel + 1));
			ThaumcraftApi.registerObjectTag(material.getToolHead("scythe"),
					new AspectList().add(Aspect.HARVEST,
							material.miningLevel + 1));

			ThaumcraftApi
					.registerObjectTag(material.getToolHead("shovel"),
							new AspectList().add(Aspect.TOOL,
									material.miningLevel + 1));
			ThaumcraftApi
					.registerObjectTag(material.getToolHead("axe"),
							new AspectList().add(Aspect.TOOL,
									material.miningLevel + 1));
		}

		ThaumcraftApi.registerObjectTag(new ItemStack(ContentWorld.metalItems,
				1, ContentWorld.metalItems.getTextureIndex("copper_gem")),
				new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 5));
		ThaumcraftApi.registerObjectTag(
				contentBS.magma_furnace.getDefaultBlock(),
				new AspectList().add(Aspect.FIRE, 10).add(Aspect.EARTH, 10)
						.add(Aspect.METAL, 8).add(Aspect.ENERGY, 5));
	}
}
