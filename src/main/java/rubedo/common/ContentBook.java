package rubedo.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.BookBuilder;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageUnlocText;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.tools.ToolPickaxe;
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

		BookBuilder builder = new BookBuilder();
		builder.setCategories(categories);
		builder.setUnlocBookTitle("rubedo.guide.title");
		builder.setUnlocWelcomeMessage("rubedo.guide.description");
		builder.setUnlocDisplayName("rubedo.guide.title");
		builder.setBookColor(new Color(255, 46, 61));
		rubedoGuide = builder.build();

		GuideRegistry.registerBook(rubedoGuide);
	}

	private void createToolEntries() {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		MaterialMultiItem flint = contentTools.getMaterial("flint");

		List<EntryAbstract> earlyToolsEntries = new ArrayList<EntryAbstract>();

		// Entry: Tool Heads
		ArrayList<IPage> toolHeads = new ArrayList<IPage>();
		toolHeads.add(new PageUnlocText("rubedo.guide.earlyTools.toolHeads.explanation"));
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
		earlyTools.add(new PageUnlocText("rubedo.guide.earlyTools.toolCrafting.explanation"));
		earlyTools.add(new PageIRecipe(new ShapelessOreRecipe(contentTools
				.getItem(ToolPickaxe.class).buildTool(flint,
						contentTools.getMaterial("wood"),
						contentTools.getMaterial("wood")), flint
				.getToolHead("pickaxe"), "stickWood", "plankWood")));
		earlyToolsEntries.add(new EntryUniText(earlyTools, "rubedo.guide.earlyTools.toolCrafting"));

		// Category: First Tools
		categories.add(new CategoryItemStack(earlyToolsEntries,
				"rubedo.guide.earlyTools", contentTools.getItem(
						ToolPickaxe.class).buildTool(
						contentTools.getMaterial("flint"),
						contentTools.getMaterial("wood"),
						contentTools.getMaterial("wood"))));
	}
}
