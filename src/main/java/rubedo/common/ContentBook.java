package rubedo.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.BookBuilder;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageUnlocItemStack;
import amerifrance.guideapi.pages.PageUnlocText;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolProperties;
import rubedo.util.Singleton;

public class ContentBook extends Singleton<ContentBook> implements IContent {

	protected ContentBook() {
		super(ContentBook.class);
	}

	public static class Config {
		public static boolean giveBookOnCraft = true;
	}

	public static Book rubedoGuide;
	public static List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

	@Override
	public void config(Configuration config) {
		Config.giveBookOnCraft = config.get("Books", "GiveBook",
				Config.giveBookOnCraft,
				"Give Rubedo guide on first tool crafted?").getBoolean();
	}

	@Override
	public void registerBase() {

	}

	@Override
	public void registerDerivatives() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tweak() {
		createToolEntries();
		createBlacksmithingEntries();

		BookBuilder builder = new BookBuilder();
		builder.setCategories(categories);
		builder.setUnlocBookTitle("rubedo.guide.title");
		builder.setUnlocWelcomeMessage("rubedo.guide.description");
		builder.setUnlocDisplayName("rubedo.guide.title");
		builder.setBookColor(new Color(255, 46, 61));
		rubedoGuide = builder.build();

		GuideRegistry.registerBook(rubedoGuide);
		GameRegistry.addShapelessRecipe(GuideRegistry
				.getItemStackForBook(rubedoGuide), new ItemStack(Items.flint),
				new ItemStack(Items.book));

		if (Config.giveBookOnCraft) {
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.BookEventsHandler());
			FMLCommonHandler.instance().bus()
					.register(new rubedo.ai.BookEventsHandler());
		}
	}

	private void createToolEntries() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		MaterialMultiItem flint = contentTools.getMaterial("flint");
		MaterialMultiItem wood = contentTools.getMaterial("wood");
		MaterialMultiItem copper = contentTools.getMaterial("copper");
		MaterialMultiItem leather = contentTools.getMaterial("leather");

		List<EntryAbstract> earlyToolsEntries = new ArrayList<EntryAbstract>();

		// Entry: Tool Heads
		ArrayList<IPage> toolHeads = new ArrayList<IPage>();
		toolHeads.add(new PageUnlocText(
				"rubedo.guide.earlyTools.toolHeads.explanation"));
		toolHeads.add(new PageIRecipe(new ShapedOreRecipe(flint
				.getToolHead("sword"), "X", "X", 'X', flint.headMaterial)));
		toolHeads.add(new PageIRecipe(new ShapedOreRecipe(flint
				.getToolHead("shovel"), "XX", "XX", 'X', flint.headMaterial)));
		toolHeads
				.add(new PageIRecipe(new ShapedOreRecipe(flint
						.getToolHead("axe"), true, "XX", " X", 'X',
						flint.headMaterial)));
		toolHeads.add(new PageIRecipe(new ShapedOreRecipe(flint
				.getToolHead("scythe"), true, "XXX", "X  ", 'X',
				flint.headMaterial)));
		toolHeads.add(new PageIRecipe(new ShapedOreRecipe(flint
				.getToolHead("pickaxe"), "XXX", 'X', flint.headMaterial)));
		earlyToolsEntries.add(new EntryUniText(toolHeads,
				"rubedo.guide.earlyTools.toolHeads"));

		// Entry: Tool Crafting
		ArrayList<IPage> earlyTools = new ArrayList<IPage>();
		earlyTools.add(new PageUnlocText(
				"rubedo.guide.earlyTools.toolCrafting.explanation"));
		earlyTools.add(new PageIRecipe(new ShapelessOreRecipe(contentTools
				.getItem(ToolPickaxe.class).buildTool(flint, wood, wood), flint
				.getToolHead("pickaxe"), wood.rodMaterial, wood.capMaterial)));
		earlyToolsEntries.add(new EntryUniText(earlyTools,
				"rubedo.guide.earlyTools.toolCrafting"));

		// Entry: Replacing Tool Parts
		ArrayList<IPage> replaceParts = new ArrayList<IPage>();
		ItemStack flintPick = contentTools.getItem(ToolPickaxe.class)
				.buildTool(flint, wood, wood);
		ToolProperties flintPickProperties = new ToolProperties(flintPick,
				contentTools.getItem(ToolPickaxe.class));
		flintPickProperties.setBroken(true);
		replaceParts.add(new PageUnlocItemStack(
				"rubedo.guide.earlyTools.replaceParts.explanation",
				contentTools.getItem(ToolPickaxe.class).buildTool(copper,
						leather, copper)));
		replaceParts.add(new PageIRecipe(new ShapelessOreRecipe(contentTools
				.getItem(ToolPickaxe.class).buildTool(copper, wood, wood),
				flintPick, copper.getToolHead("pickaxe"))));
		replaceParts.add(new PageIRecipe(new ShapelessOreRecipe(contentTools
				.getItem(ToolPickaxe.class).buildTool(copper, leather, wood),
				contentTools.getItem(ToolPickaxe.class).buildTool(copper, wood,
						wood), leather.rodMaterial)));
		replaceParts.add(new PageIRecipe(new ShapelessOreRecipe(contentTools
				.getItem(ToolPickaxe.class).buildTool(copper, leather, copper),
				contentTools.getItem(ToolPickaxe.class).buildTool(copper,
						leather, wood), copper.capMaterial)));
		earlyToolsEntries.add(new EntryUniText(replaceParts,
				"rubedo.guide.earlyTools.replaceParts"));

		// Category: First Tools
		categories.add(new CategoryItemStack(earlyToolsEntries,
				"rubedo.guide.earlyTools", contentTools.getItem(
						ToolPickaxe.class).buildTool(
						contentTools.getMaterial("flint"), wood, wood)));
	}
	
	private void createBlacksmithingEntries() {
		ContentBlackSmith contentBS = Singleton
				.getInstance(ContentBlackSmith.class);
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		MaterialMultiItem iron = contentTools.getMaterial("iron");
		
		List<EntryAbstract> blacksmithEntries = new ArrayList<EntryAbstract>();
		
		// Entry: Advanced Tool Heads
				ArrayList<IPage> advancedHeads = new ArrayList<IPage>();
				advancedHeads.add(new PageUnlocItemStack(
						"rubedo.guide.blacksmith.advancedHeads.explanation", iron
								.getToolHead("unrefined")));
				if (Loader.isModLoaded("Steamcraft")
						&& Loader.isModLoaded("rubedoIntegration")) {
					advancedHeads
							.add(new PageUnlocItemStack(
									"rubedo.guide.blacksmith.advancedHeads.steamcraft.explanation",
									GameRegistry.findItemStack("rubedoIntegration", "moldToolHead", 1)));
					advancedHeads.add(new PageIRecipe(new ShapelessOreRecipe(
							GameRegistry.findItemStack("Steamcraft", "book", 1),
							Items.book, "oreCopper", "oreZinc")));
				}
				advancedHeads.add(new PageIRecipe(new ShapedOreRecipe(iron
						.getToolHead("sword"), "X", "X", "Y", 'X', new ItemStack(
						Items.clay_ball), 'Y', iron.getToolHead("hot"))));
				advancedHeads.add(new PageIRecipe(new ShapedOreRecipe(iron
						.getToolHead("shovel"), "XXY", "XX ", 'X', new ItemStack(
						Items.clay_ball), 'Y', iron.getToolHead("hot"))));
				advancedHeads.add(new PageIRecipe(new ShapedOreRecipe(iron
						.getToolHead("axe"), true, "XX", " X", " Y", 'X',
						new ItemStack(Items.clay_ball), 'Y', iron.getToolHead("hot"))));
				advancedHeads.add(new PageIRecipe(new ShapedOreRecipe(iron
						.getToolHead("scythe"), true, "XXX", "XY ", 'X', new ItemStack(
						Items.clay_ball), 'Y', iron.getToolHead("hot"))));
				advancedHeads.add(new PageIRecipe(new ShapedOreRecipe(iron
						.getToolHead("pickaxe"), "XXX", " Y ", 'X', new ItemStack(
						Items.clay_ball), 'Y', iron.getToolHead("hot"))));
				blacksmithEntries.add(new EntryUniText(advancedHeads,
						"rubedo.guide.blacksmith.advancedHeads"));

				// Entry: Alloys
				ArrayList<IPage> alloys = new ArrayList<IPage>();
				alloys.add(new PageUnlocText(
						"rubedo.guide.blacksmith.alloys.explanation"));
				if (ContentWorld.Config.addAlloyRecipes) {
					alloys.add(new PageIRecipe(new ShapelessOreRecipe(OreDictionary
							.getOres("ingotSteel").get(0), "ingotIron", new ItemStack(
							Items.blaze_rod))));

					ItemStack orichalcum = OreDictionary.getOres("ingotOrichalcum")
							.get(0).copy();
					orichalcum.stackSize = 2;
					alloys.add(new PageIRecipe(new ShapelessOreRecipe(orichalcum,
							"ingotCopper", "ingotGold")));

					ItemStack mythril = OreDictionary.getOres("ingotMythril").get(0)
							.copy();
					mythril.stackSize = 2;
					alloys.add(new PageIRecipe(new ShapelessOreRecipe(mythril,
							"ingotCopper", "ingotSilver")));

					ItemStack hepatizon = OreDictionary.getOres("ingotHepatizon")
							.get(0);
					alloys.add(new PageIRecipe(new ShapelessOreRecipe(hepatizon,
							"ingotOrichalcum", "ingotMythril", new ItemStack(
									Blocks.end_stone))));
				}
				blacksmithEntries.add(new EntryUniText(alloys,
						"rubedo.guide.blacksmith.alloys"));

				// Entry: Magma Furnace
				ArrayList<IPage> magmaFurnace = new ArrayList<IPage>();
				magmaFurnace.add(new PageUnlocItemStack(
						"rubedo.guide.blacksmith.magmaFurnace.explanation",
						contentBS.magma_furnace.getDefaultBlock()));
				magmaFurnace.add(new PageIRecipe(new ShapedOreRecipe(
						contentBS.magma_furnace.getDefaultBlock(), "CCC", "CBC", "bbb",
						'C', "ingotCopper", 'B', new ItemStack(Blocks.coal_block), 'b',
						new ItemStack(Items.brick))));
				blacksmithEntries.add(new EntryUniText(magmaFurnace,
						"rubedo.guide.blacksmith.magmaFurnace"));

				// Entry: Enchanting
				ArrayList<IPage> enchanting = new ArrayList<IPage>();
				enchanting.add(new PageUnlocItemStack(
						"rubedo.guide.blacksmith.enchanting.explanation.1",
						Blocks.enchanting_table));
				enchanting.add(new PageUnlocText(
						"rubedo.guide.blacksmith.enchanting.explanation.2"));
				enchanting.add(new PageIRecipe(new ShapedOreRecipe(new ItemStack(
						Blocks.enchanting_table), " b ", "dWd", "WcW", 'b',
						new ItemStack(Items.book), 'd', new ItemStack(Items.diamond),
						'W', "plankWood", 'c', "gemCopper")));
				blacksmithEntries.add(new EntryUniText(enchanting,
						"rubedo.guide.blacksmith.enchanting"));

				// Entry: Anvils
				ArrayList<IPage> anvils = new ArrayList<IPage>();
				anvils.add(new PageUnlocItemStack(
						"rubedo.guide.blacksmith.anvils.explanation", Blocks.anvil));
				blacksmithEntries.add(new EntryUniText(anvils,
						"rubedo.guide.blacksmith.anvils"));

				// Category: Blacksmithing
				categories.add(new CategoryItemStack(blacksmithEntries,
						"rubedo.guide.blacksmith", new ItemStack(Blocks.anvil)));
	}
}
