package rubedo.integration.fsp;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rubedo.common.ContentTools;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.util.RemapHelper;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.steamcraft.SteamcraftItems;

public class MaterialIntegration {
	public static class Gilded extends MaterialMultiItem {
		public Gilded() {
			this.name = "gilded";
			this.type = MaterialType.METAL_ARCANE;
			this.isColdWorkable = false;
			this.durability = 250;
			this.mundaneLevel = 3;
			this.modDamage = 0.7f;
			this.arcaneLevel = 3;
			this.damage = 2;
			this.speed = 6.0f;
			this.miningLevel = 2;
			this.headMaterial = new ItemStack(Items.iron_ingot, 1, 0);
		}
	}

	public static class Brass extends MaterialMultiItem {
		public Brass() {
			this.name = "brass";
			this.type = MaterialType.METAL_BRONZE;
			this.isColdWorkable = false;
			this.durability = 200;
			this.mundaneLevel = 3;
			this.modDamage = 0.7f;
			this.arcaneLevel = 3;
			this.damage = 3;
			this.speed = 6.0f;
			this.miningLevel = 1;
			this.headMaterial = new ItemStack(Items.iron_ingot, 1, 0);
		}
	}

	public static void preInit() {
		registerMaterial(Gilded.class);
		registerMaterial(Brass.class);
	}

	public static void postInit() {
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.axe("GildedGold")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.pick("GildedGold")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.shovel("GildedGold")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.hoe("GildedGold")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.sword("GildedGold")));

		RemapHelper
				.removeAnyRecipe(new ItemStack(SteamcraftItems.axe("Brass")));
		RemapHelper
				.removeAnyRecipe(new ItemStack(SteamcraftItems.pick("Brass")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.shovel("Brass")));
		RemapHelper
				.removeAnyRecipe(new ItemStack(SteamcraftItems.hoe("Brass")));
		RemapHelper.removeAnyRecipe(new ItemStack(SteamcraftItems
				.sword("Brass")));
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
}
