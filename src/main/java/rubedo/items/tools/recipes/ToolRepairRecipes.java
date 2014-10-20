package rubedo.items.tools.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
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

		return this.matches(this.tool.getStack(), modifierCandidate);
	}

	public boolean matches(ItemStack toolStack, ItemStack modifierCandidate) {
		if (toolStack == null || modifierCandidate == null)
			return false;

		this.tool = ((ToolBase) toolStack.getItem())
				.getToolProperties(toolStack);

		for (MaterialMultiItem material : contentTools.getMaterials()) {

			ItemStack toolhead = material.getToolHead(this.tool.getItem()
					.getName());

			if ((toolhead != null && modifierCandidate.isItemEqual(toolhead))
					|| (material.rodMaterial != null && itemOreMatches(
							modifierCandidate, material.rodMaterial))
					|| (material.capMaterial != null && itemOreMatches(
							modifierCandidate, material.capMaterial))) {
				this.modifier = modifierCandidate;
				this.material = material;
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		return this.getCraftingResult();
	}

	public ItemStack getCraftingResult() {
		ToolProperties copy = this.tool.getItem().getToolProperties(
				this.tool.getStack().copy());

		ItemStack toolhead = this.material.getToolHead(this.tool.getItem()
				.getName());

		if (this.material.capMaterial != null
				&& itemOreMatches(this.material.capMaterial, this.modifier)) {
			copy.setCapMaterial(this.material);
			return copy.getStack();
		} else if (this.material.rodMaterial != null
				&& itemOreMatches(this.material.rodMaterial, this.modifier)) {
			copy.setRodMaterial(this.material);
			return copy.getStack();
		} else if (toolhead != null && toolhead.isItemEqual(this.modifier)) {
			copy.setBroken(false);
			if (copy.getStack().getTagCompound() != null) {
				copy.getStack().getTagCompound().removeTag("ench");
			}
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

	private static boolean itemOreMatches(ItemStack input, ItemStack target) {
		if (OreDictionary.itemMatches(input, target, false))
			return true;

		String ore1 = null;
		String ore2 = null;

		if (OreDictionary.getOreIDs(input).length > 0)
			ore1 = OreDictionary.getOreName(OreDictionary.getOreIDs(input)[0]);

		if (OreDictionary.getOreIDs(target).length > 0)
			ore2 = OreDictionary.getOreName(OreDictionary.getOreIDs(target)[0]);

		if (ore1 == null || ore2 == null)
			return false;

		return ore1 == ore2;
	}
}
