package rubedo.common;

import java.util.Map.Entry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.common.materials.Material;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.util.ReflectionHelper;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentVanilla extends Singleton<ContentVanilla> implements
IContent {
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
				"replaceTools", Config.replaceVanillaTools).getBoolean();
		Config.removeRecipes = config.get("Vanilla Changes",
				"removeToolRecipes", Config.removeRecipes).getBoolean();
		Config.changeMiningProgression = config.get("Vanilla Changes",
				"changeMiningProgression", Config.changeMiningProgression)
				.getBoolean();
		Config.addFlintRecipe = config.get("Vanilla Changes", "addFlintRecipe",
				Config.addFlintRecipe).getBoolean();
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
			Blocks.obsidian.setHarvestLevel("pickaxe", 2);
			Blocks.netherrack.setHarvestLevel("pickaxe", 3);
			Blocks.netherrack.setHardness(1.5F);
			Blocks.quartz_ore.setHarvestLevel("pickaxe", 3);
			Blocks.quartz_ore.setHardness(3.0F);
			Blocks.nether_brick.setHarvestLevel("pickaxe", 3);
			Blocks.end_stone.setHarvestLevel("pickaxe", 4);
		}
	}

	private void remapToolHeads() {
		// TODO: make it so replaceTools doesn't override removeRecipes
		if (Config.replaceVanillaTools || Config.removeRecipes) {
			Item[] toBeRemoved = { Items.golden_sword, Items.iron_sword,
					Items.stone_sword, Items.wooden_sword, Items.golden_sword,
					Items.iron_shovel, Items.stone_shovel, Items.wooden_shovel,
					Items.golden_axe, Items.iron_axe, Items.stone_axe,
					Items.wooden_axe, Items.golden_hoe, Items.iron_hoe,
					Items.stone_hoe, Items.wooden_hoe, Items.golden_pickaxe,
					Items.iron_pickaxe, Items.stone_pickaxe,
					Items.wooden_pickaxe, };
			Item[] toBeNerfed = {
					// Leave these in for now
					Items.diamond_sword, Items.diamond_hoe, Items.diamond_axe,
					Items.diamond_pickaxe, Items.diamond_shovel };

			for (int i = 0; i < toBeRemoved.length; i++) {
				RemapHelper.removeAnyRecipe(new ItemStack(toBeRemoved[i]));
			}

			for (int i = 0; i < toBeNerfed.length; i++) {
				toBeNerfed[i].setMaxDamage(1);
			}
		}

		if (Config.replaceVanillaTools) {
			ContentTools contentTools = Singleton
					.getInstance(ContentTools.class);
			for (Entry<Material, String> material : contentTools.VanillaToolMaterials
					.entrySet()) {
				for (ToolBase kind : contentTools.getItems()) {
					String name = kind.getName() + "_head_" + material.getKey().name;

					if (material.getKey().name == "wood")
						ItemToolHead.getHeadMap().put(name, new ItemToolHead(name));

					Item item = ItemToolHead.getHeadMap().get(name);

					ReflectionHelper.setStatic(Items.class, material.getValue()
							+ "_" + this.toVanillaKind(kind.getName()), item);
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
