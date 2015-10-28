package rubedo.common;

import java.util.Map.Entry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.common.materials.MaterialMultiItem.Wood;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import rubedo.util.ReflectionHelper;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentVanilla extends Singleton<ContentVanilla> implements
		IContent {
	private static ContentTools contentTools = Singleton
			.getInstance(ContentTools.class);

	protected ContentVanilla() {
		super(ContentVanilla.class);
	}

	public static class Config {
		// Default values
		public static boolean replaceVanillaTools = true;
		public static boolean removeRecipes = true;
		public static boolean changeMiningProgression = true;
		public static boolean addFlintRecipe = true;
	}

	@Override
	public void config(Configuration config) {
		Config.replaceVanillaTools = config.get("Vanilla Changes",
				"ReplaceTools", Config.replaceVanillaTools,
				"Replace vanilla tools with Rubedo tool heads?").getBoolean();
		Config.removeRecipes = config.get("Vanilla Changes",
				"RemoveToolRecipes", Config.removeRecipes,
				"Remove the vanilla tool recipes?").getBoolean();
		Config.changeMiningProgression = config.get("Vanilla Changes",
				"ChangeMiningProgression", Config.changeMiningProgression,
				"Change the harvest level progression?").getBoolean();
		Config.addFlintRecipe = config.get("Vanilla Changes", "AddFlintRecipe",
				Config.addFlintRecipe, "Add alternate flint recipe?")
				.getBoolean();
	}

	@Override
	public void registerBase() {
		// Remap vanilla tools
		this.remapToolHeads();

		// TODO: figure out how Nether Portals are made
		/*
		 * BlockPortal portal = new BlockPortal() {
		 * 
		 * @Override public boolean tryToCreatePortal(World world, int x, int y,
		 * int z) { //return super.tryToCreatePortal(world, x, y, z); return
		 * false; } };
		 * portal.setHardness(-1.0F).setStepSound(Block.soundTypeGlass
		 * ).setLightLevel
		 * (0.75F).setBlockName("portal").setBlockTextureName("portal");
		 * 
		 * ReflectionHelper.setStatic(Block.class, "portal", portal);
		 */
	}

	@Override
	public void registerDerivatives() {
		// Backup flint recipe
		if (Config.addFlintRecipe)
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(
					Items.flint), new ItemStack(Items.bowl
					.setContainerItem(Items.bowl)),
					new ItemStack(Blocks.gravel)));
	}

	@Override
	public void tweak() {
		// Mining balance changes
		if (Config.changeMiningProgression) {
			// Make sure ForgeHooks has finished initializing
			ReflectionHelper.initialize(ForgeHooks.class);

			Blocks.netherrack.setHarvestLevel("pickaxe", 4);
			Blocks.netherrack.setHardness(1.5F);
			Blocks.quartz_ore.setHarvestLevel("pickaxe", 4);
			Blocks.quartz_ore.setHardness(3.0F);
			Blocks.nether_brick.setHarvestLevel("pickaxe", 4);
			Blocks.end_stone.setHarvestLevel("pickaxe", 5);
		}
	}

	private void remapToolHeads() {
		// TODO: make it so replaceTools doesn't override removeRecipes
		if (Config.replaceVanillaTools || Config.removeRecipes) {
			Item[] toBeRemoved = { Items.golden_sword, Items.iron_sword,
					Items.stone_sword, Items.golden_sword, Items.iron_shovel,
					Items.stone_shovel, Items.golden_axe, Items.iron_axe,
					Items.stone_axe, Items.golden_hoe, Items.iron_hoe,
					Items.stone_hoe, Items.golden_pickaxe, Items.iron_pickaxe,
					Items.stone_pickaxe, Items.diamond_sword,
					Items.diamond_hoe, Items.diamond_axe,
					Items.diamond_pickaxe, Items.diamond_shovel };

			for (int i = 0; i < toBeRemoved.length; i++) {
				RemapHelper.removeAnyRecipe(new ItemStack(toBeRemoved[i]));
			}

			MaterialMultiItem wood = contentTools.getMaterial(Wood.class);

			RemapHelper.tryReplaceRecipeOutput(
					new ItemStack(Items.wooden_axe),
					contentTools.getItem(ToolAxe.class).buildTool(wood, wood,
							wood));
			RemapHelper.tryReplaceRecipeOutput(
					new ItemStack(Items.wooden_hoe),
					contentTools.getItem(ToolScythe.class).buildTool(wood,
							wood, wood));
			RemapHelper.tryReplaceRecipeOutput(
					new ItemStack(Items.wooden_pickaxe),
					contentTools.getItem(ToolPickaxe.class).buildTool(wood,
							wood, wood));
			RemapHelper.tryReplaceRecipeOutput(new ItemStack(
					Items.wooden_shovel), contentTools
					.getItem(ToolShovel.class).buildTool(wood, wood, wood));
			RemapHelper.tryReplaceRecipeOutput(
					new ItemStack(Items.wooden_sword),
					contentTools.getItem(ToolSword.class).buildTool(wood, wood,
							wood));
		}

		if (Config.replaceVanillaTools) {
			ContentTools contentTools = Singleton
					.getInstance(ContentTools.class);
			for (Entry<MaterialMultiItem, String> material : contentTools.VanillaToolMaterials
					.entrySet()) {
				for (ToolBase kind : contentTools.getItems()) {
					String name = kind.getName() + "_head_"
							+ material.getKey().name;

					if (material.getKey().name == "wood")
						ItemToolHead.getHeadMap().put(
								name,
								new ItemToolHead(name, kind.getClass(),
										material.getKey()));

					Item item = ItemToolHead.getHeadMap().get(name);

					String refName = material.getValue() + "_"
							+ this.toVanillaKind(kind.getName());

					ReflectionHelper.setStatic(Items.class, refName, item);
					RemapHelper.overwriteEntry(
							Item.itemRegistry,
							"minecraft:" + material.getValue() + "_"
									+ this.toVanillaKind(kind.getName()), item);
				}
			}
		}
	}

	// TODO: change all string references of "scythe" to "hoe" instead of this
	// hack
	private String toVanillaKind(String kind) {
		return kind == "scythe" ? "hoe" : kind;
	}
}
