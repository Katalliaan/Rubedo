package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import rubedo.RubedoCore;
import rubedo.blocks.BlockMagmaFurnace;
import rubedo.client.gui.GuiHandler;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.tileentity.TileEntityMagmaFurnace;
import rubedo.util.Singleton;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentBlackSmith extends Singleton<ContentBlackSmith> implements
		IContent {
	public static final BlockMagmaFurnace magma_furnace = new BlockMagmaFurnace();

	protected MagmaSmelting magmaSmelting;

	protected ContentBlackSmith() {
		super(ContentBlackSmith.class);
	}

	public MagmaSmelting getMagmaSmelting() {
		return this.magmaSmelting;
	}

	@Override
	public void config(Configuration config) {
	}

	@Override
	public void registerBase() {
		GameRegistry.registerBlock(magma_furnace, "magma_furnace");
		GameRegistry.registerTileEntity(TileEntityMagmaFurnace.class,
				"magma_furnace");

		NetworkRegistry.INSTANCE.registerGuiHandler(RubedoCore.instance,
				new GuiHandler());
	}

	@Override
	public void registerDerivatives() {
		this.magmaSmelting = new MagmaSmelting();

		this.magmaSmelting.addSmeltingRecipe(new ItemStack(
				ContentWorld.oreBlocks, 1, ContentWorld.oreBlocks.getBehavior()
						.getTextureMeta("copper_ore")), //
				OreDictionary.getOres("ingotCopper").get(0), //
				new ItemStack(ContentWorld.metalItems, 1,
						ContentWorld.metalItems.getTextureIndex("copper_gem")), //
				(float) ContentWorld.Config.dropCupriteChance);

		this.magmaSmelting.addOreRecipe("oreIron",
				OreDictionary.getOres("ingotIron").get(0), OreDictionary
						.getOres("nuggetIron").get(0), 0.3F);
		this.magmaSmelting.addOreRecipe("oreGold",
				OreDictionary.getOres("ingotGold").get(0), OreDictionary
						.getOres("nuggetGold").get(0), 0.3F);
		this.magmaSmelting.addOreRecipe("oreSilver",
				OreDictionary.getOres("ingotSilver").get(0), OreDictionary
						.getOres("nuggetSilver").get(0), 0.3F);

		for (MaterialMultiItem material : Singleton.getInstance(
				ContentTools.class).getMaterials()) {
			if (!material.isColdWorkable) {
				this.magmaSmelting.addSmeltingRecipe(
						material.getToolHead("unrefined"),
						material.getToolHead("hot"), null, 0.0F);

				// Sword heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("sword"), "X", "X", "Y", //
						'X', new ItemStack(Items.clay_ball), //
						'Y', material.getToolHead("hot")));
				// Shovel heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("shovel"), "XXY", "XX ", //
						'X', new ItemStack(Items.clay_ball), //
						'Y', material.getToolHead("hot")));
				// Axe heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("axe"), true, "XX", " X", " Y", //
						'X', new ItemStack(Items.clay_ball), //
						'Y', material.getToolHead("hot")));
				// Scythe heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("scythe"), true, "XXX", "XY ", //
						'X', new ItemStack(Items.clay_ball), //
						'Y', material.getToolHead("hot")));
				// Pick heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("pickaxe"), "XXX", " Y ", //
						'X', new ItemStack(Items.clay_ball), //
						'Y', material.getToolHead("hot")));
			}
		}
	}

	@Override
	public void tweak() {
	}

	public static class MagmaSmelting {
		private Map<Item, ItemStack> keyMap;
		private Map<String, ItemStack> oreMap;
		private Map<ItemStack, SmeltResult> smelting;

		private Random random;

		public MagmaSmelting() {
			this.random = new Random();

			this.keyMap = new HashMap<Item, ItemStack>();
			this.oreMap = new HashMap<String, ItemStack>();
			this.smelting = new HashMap<ItemStack, SmeltResult>();
		}

		public void addSmeltingRecipe(ItemStack input, ItemStack output,
				ItemStack extra, float chance) {
			if (!this.keyMap.containsKey(input.getItem())) {
				this.keyMap.put(input.getItem(), input);
				this.smelting
						.put(input, new SmeltResult(output, extra, chance));
			}
		}

		public void addOreRecipe(String oredict, ItemStack output,
				ItemStack extra, float chance) {
			if (!this.oreMap.containsKey(oredict)) {
				ItemStack ore = OreDictionary.getOres(oredict).get(0);
				this.oreMap.put(oredict, ore);
				this.smelting.put(ore, new SmeltResult(output, extra, chance));
			}
		}

		public ItemStack getSmeltingResult(ItemStack input) {
			SmeltResult result = this.getResult(input);
			if (result != null)
				return result.smelted;
			else
				return null;
		}

		public ItemStack getSmeltingExtra(ItemStack input) {
			SmeltResult result = this.getResult(input);
			if (result != null && this.random.nextFloat() < result.extraChance)
				return result.extra;
			else
				return null;
		}

		private SmeltResult getResult(ItemStack input) {
			if (this.keyMap.containsKey(input.getItem())
					&& this.keyMap.get(input.getItem()).isItemEqual(input)) {
				return this.smelting.get(this.keyMap.get(input.getItem()));
			} else if (OreDictionary.getOreIDs(input).length > 0) {
				int[] ids = OreDictionary.getOreIDs(input);

				for (int id : ids) {
					String oredict = OreDictionary.getOreName(id);

					if (this.oreMap.containsKey(oredict))
						return this.smelting.get(this.oreMap.get(oredict));
				}

			}
			return null;
		}

		private static class SmeltResult {
			public SmeltResult(ItemStack smelted, ItemStack extra, float chance) {
				this.smelted = smelted;
				this.extra = extra;
				this.extraChance = chance;
			}

			public ItemStack smelted;
			public ItemStack extra;
			public float extraChance;
		}
	}
}
