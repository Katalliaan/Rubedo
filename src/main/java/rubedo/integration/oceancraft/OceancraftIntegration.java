package rubedo.integration.oceancraft;

import net.minecraft.item.ItemStack;
import rubedo.common.ContentTools;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;

import com.Oceancraft.common.Oceancraft;

import cpw.mods.fml.common.registry.GameRegistry;

public class OceancraftIntegration {
	public static class Coral extends MaterialMultiItem {
		public Coral() {
			ItemStack coral = GameRegistry.findItemStack("Oceancraft", "Coral",
					1);

			this.name = "coral";
			this.durability = 75;
			this.damage = 0;
			this.speed = 4.0f;
			this.miningLevel = 0;
			this.headMaterial = coral;
		}
	}

	public static void preInit() {
		registerMaterial(Coral.class);
	}

	public static void postInit() {
		RemapHelper.removeAnyRecipe(new ItemStack(Oceancraft.CoralAxe));
		RemapHelper.removeAnyRecipe(new ItemStack(Oceancraft.CoralHoe));
		RemapHelper.removeAnyRecipe(new ItemStack(Oceancraft.CoralPickaxe));
		RemapHelper.removeAnyRecipe(new ItemStack(Oceancraft.CoralShovel));
		RemapHelper.removeAnyRecipe(new ItemStack(Oceancraft.CoralSword));
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
		}
	}
}
