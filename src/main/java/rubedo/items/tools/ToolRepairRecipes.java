package rubedo.items.tools;

import java.util.Map;
import java.util.Map.Entry;

import rubedo.common.ContentTools;
import rubedo.common.ContentTools.Material;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ToolRepairRecipes implements IRecipe {
	private ToolProperties tool;
	private ItemStack modifier;

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		this.tool = null;
		this.modifier = null;

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
					}
				}
			}

		if (this.tool == null)
			return false;

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(
						j, i);

				if (itemstack != null
						&& !(itemstack.getItem() instanceof ToolBase)) {
					if (this.modifier != null)
						return false;

					// TODO: optimize this, it's slow, though the two step
					// process already
					// ensures this doesn't run until you put a ToolBase in the
					// crafting grid
					for (ItemStack material : ContentTools.materialStacks
							.keySet()) {
						// TODO: This is just so WTF I don't even words, but
						// apparently there's a null key
						if (material == null)
							continue;

						Material toolMaterial = ContentTools.materialStacks
								.get(this.modifier);

						if (material.getItem().equals(itemstack.getItem())
								&& material.getItemDamage() == itemstack
										.getItemDamage()) {
							if ((this.tool.getItem() instanceof ToolAxe && toolMaterial.axeHead == material)
									|| (this.tool.getItem() instanceof ToolPickaxe && toolMaterial.pickaxeHead == material)
									|| (this.tool.getItem() instanceof ToolScythe && toolMaterial.scytheHead == material)
									|| (this.tool.getItem() instanceof ToolShovel && toolMaterial.shovelHead == material)
									|| (this.tool.getItem() instanceof ToolSword && toolMaterial.swordHead == material)) {
								this.modifier = material;
								return true;
							} else {
								return false;
							}
						}
					}
				}
			}

		return this.modifier != null && this.tool != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		Material material = ContentTools.materialStacks.get(this.modifier);

		if (ContentTools.toolCaps.containsKey(material.name)
				&& ContentTools.toolCaps.get(material.name).capMaterial == this.modifier) {
			this.tool.setCapMaterial(material.name);
			return this.tool.getStack();
		}

		if (ContentTools.toolRods.containsKey(material.name)
				&& ContentTools.toolRods.get(material.name).rodMaterial == this.modifier) {
			this.tool.setRodMaterial(material.name);
			return this.tool.getStack();
		}

		// Bit weird, but at this point we're sure it's a known tool head anyway
		this.tool.setBroken(false);
		this.tool.setHeadMaterial(material.name);
		this.tool.getStack().setItemDamage(0);
		return this.tool.getStack().copy();
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ContentTools.toolSword);
	}
}
