package rubedo.common;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import rubedo.common.materials.MaterialTool;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentTools extends ContentMultiItem<ToolBase, MaterialTool>
implements IContent {

	public Map<MaterialTool, String> VanillaToolMaterials;

	protected ContentTools() {
		super(ContentTools.class);

		// Tool kinds
		Set<Class<? extends ToolBase>> toolKinds = new LinkedHashSet<Class<? extends ToolBase>>();

		toolKinds.add(ToolSword.class);
		toolKinds.add(ToolPickaxe.class);
		toolKinds.add(ToolShovel.class);
		toolKinds.add(ToolAxe.class);
		toolKinds.add(ToolScythe.class);

		this.setKinds(toolKinds);
	}

	private void initializeToolMaterials() {
		// Tool materials
		Set<Class<? extends MaterialTool>> toolMaterials = new LinkedHashSet<Class<? extends MaterialTool>>();

		toolMaterials.add(MaterialTool.Wood.class);
		toolMaterials.add(MaterialTool.Flint.class);
		toolMaterials.add(MaterialTool.Copper.class);
		toolMaterials.add(MaterialTool.Iron.class);
		toolMaterials.add(MaterialTool.Gold.class);
		toolMaterials.add(MaterialTool.Orichalcum.class);
		toolMaterials.add(MaterialTool.Silver.class);
		toolMaterials.add(MaterialTool.Steel.class);
		toolMaterials.add(MaterialTool.Mythril.class);
		toolMaterials.add(MaterialTool.Hepatizon.class);

		this.setMaterials(toolMaterials);

		// Vanilla tool materials
		this.VanillaToolMaterials = new LinkedHashMap<MaterialTool, String>();

		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialTool.Wood.class), "wooden");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialTool.Flint.class), "stone");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialTool.Iron.class), "iron");
		this.VanillaToolMaterials.put(
				this.getMaterial(MaterialTool.Gold.class), "golden");
	}

	@Override
	public void config(Configuration config) {
	}

	@Override
	public void registerBase() {
		super.registerBase();

		this.initializeToolMaterials();

		boolean registerVanillaTools = !ContentVanilla.Config.replaceVanillaTools;

		// Get all materials
		for (MaterialTool material : this.getMaterials()) {
			// For all tool heads
			if (material.headMaterial != null) {
				// Get all tool kinds
				for (ToolBase kind : this.getItems()) {
					// Check if we need to exclude vanilla materials
					if (registerVanillaTools
							|| !this.VanillaToolMaterials.containsKey(material
									.getClass())) {
						String name = kind.getName() + "_head_" + material.name;
						Item item = new ItemToolHead(name);
						GameRegistry.registerItem(item, name);
					}
				}
			}
		}
	}

	@Override
	public void registerDerivatives() {
		this.registerToolRecipes();
	}

	@Override
	public void tweak() {

	}

	private void registerToolRecipes() {
		// GameRegistry.addRecipe(new ToolEnchantmentRecipes());
		// GameRegistry.addRecipe(new ToolRepairRecipes());

		// Tool head recipes
		for (MaterialTool material : this.getMaterials()) {
			if (material.headMaterial != null) {
				// Sword heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("sword"), "X", "X", 'X',
						material.headMaterial));
				// Shovel heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("shovel"), "XX", "XX", 'X',
						material.headMaterial));
				// Axe heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("axe"), true, "XX", " X", 'X',
						material.headMaterial));
				// Scythe heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("scythe"), true, "XXX", "X  ", 'X',
						material.headMaterial));
				// Pick heads
				GameRegistry.addRecipe(new ShapedOreRecipe(material
						.getToolHead("pickaxe"), "XXX", 'X',
						material.headMaterial));
			}
		}
	}
}
