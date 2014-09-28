package rubedo.items.tools.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import rubedo.common.ContentTools;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolProperties;
import rubedo.util.Singleton;

public class ToolRepairRecipes implements IRecipe {

	private static ContentTools contentTools = Singleton
			.getInstance(ContentTools.class);

	private ToolProperties tool;
	private ItemStack modifier;
	private MaterialMultiItem material;

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		this.tool = null;
		this.modifier = null;
		this.material = null;
		ItemStack modifierCandidate = null;

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(
						j, i);

				if (itemstack != null) {
					if (itemstack.getItem() instanceof ToolBase) {
						if (this.tool != null)
							return false;

						this.tool = ((ToolBase) itemstack.getItem())
								.getToolProperties(itemstack);
					} else {
						if (modifierCandidate != null)
							return false;

						modifierCandidate = itemstack;
					}
				}
			}

		if (this.tool == null || modifierCandidate == null)
			return false;

		for (MaterialMultiItem material : contentTools.getMaterials()) {
			if (material.headMaterial.isItemEqual(modifierCandidate)
					|| material.rodMaterial.isItemEqual(modifierCandidate)
					|| material.capMaterial.isItemEqual(modifierCandidate)) {
				this.modifier = material.headMaterial;
				this.material = material;
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ToolProperties copy = this.tool.getItem().getToolProperties(
				this.tool.getStack().copy());

		if (this.material.capMaterial == this.modifier) {
			copy.setCapMaterial(this.material);
			return copy.getStack();
		} else if (this.material.rodMaterial == this.modifier) {
			copy.setRodMaterial(this.material);
			return copy.getStack();
		} else if (this.material.headMaterial == this.modifier) {
			copy.setBroken(false);
			copy.setHeadMaterial(this.material);
			copy.getStack().setItemDamage(0);
			return copy.getStack();
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}
}
