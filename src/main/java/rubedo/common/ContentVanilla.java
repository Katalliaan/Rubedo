package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import rubedo.items.ItemToolHead;
import rubedo.util.ReflectionHelper;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ContentVanilla extends Singleton<ContentVanilla> implements
		IContent {
	protected ContentVanilla() {
		super(ContentVanilla.class);
	}

	private boolean replaceTools = true;
	private boolean removeRecipes = true;
	private boolean changeMiningProgression = true;
	private boolean addFlintRecipe = true;

	@Override
	public void config(Configuration config) {
		replaceTools = config.get("Vanilla Changes", "replaceTools", true)
				.getBoolean();
		removeRecipes = config
				.get("Vanilla Changes", "removeToolRecipes", true).getBoolean();
		changeMiningProgression = config.get("Vanilla Changes",
				"changeMiningProgression", true).getBoolean();
		addFlintRecipe = config.get("Vanilla Changes", "addFlintRecipe", true)
				.getBoolean();
	}

	@Override
	public void registerBase() {
		// Remap vanilla tools
		remapToolHeads();

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
		if (addFlintRecipe)
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(
					Items.flint), new ItemStack(Items.bowl
					.setContainerItem(Items.bowl)),
					new ItemStack(Blocks.gravel)));
	}

	@Override
	public void tweak() {
		// Mining balance changes
		if (changeMiningProgression) {
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
		Map<String, String> remaps = new HashMap<String, String>();

		remaps.put("sword_head_wood", "wooden_sword");
		remaps.put("axe_head_wood", "wooden_axe");
		remaps.put("shovel_head_wood", "wooden_shovel");
		remaps.put("pickaxe_head_wood", "wooden_pickaxe");
		remaps.put("scythe_head_wood", "wooden_hoe");

		remaps.put("sword_head_flint", "stone_sword");
		remaps.put("axe_head_flint", "stone_axe");
		remaps.put("shovel_head_flint", "stone_shovel");
		remaps.put("pickaxe_head_flint", "stone_pickaxe");
		remaps.put("scythe_head_flint", "stone_hoe");

		remaps.put("sword_head_iron", "iron_sword");
		remaps.put("axe_head_iron", "iron_axe");
		remaps.put("shovel_head_iron", "iron_shovel");
		remaps.put("pickaxe_head_iron", "iron_pickaxe");
		remaps.put("scythe_head_iron", "iron_hoe");

		remaps.put("sword_head_gold", "golden_sword");
		remaps.put("axe_head_gold", "golden_axe");
		remaps.put("shovel_head_gold", "golden_shovel");
		remaps.put("pickaxe_head_gold", "golden_pickaxe");
		remaps.put("scythe_head_gold", "golden_hoe");

		if (replaceTools || removeRecipes) {
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

		if (replaceTools) {
			for (Entry<String, String> entry : remaps.entrySet()) {
				Item item = new ItemToolHead(entry.getKey());

				ReflectionHelper.setStatic(Items.class, entry.getValue(), item);
				RemapHelper.overwriteEntry(Item.itemRegistry, "minecraft:"
						+ entry.getValue(), item);
			}
		}
	}
}
