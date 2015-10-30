package rubedo.items.tools.recipes;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolProperties;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolEnchantmentRecipes implements IRecipe {
	private ToolProperties tool;
	private ItemStack enchantedBook;
	private int cost;

	@SubscribeEvent
	public void anvilUpdateEvent(AnvilUpdateEvent event) {
		if (event.left.getItem() instanceof ToolBase) {
			this.tool = null;
			this.enchantedBook = null;
			this.cost = 0;

			ToolBase tool = (ToolBase) event.left.getItem();

			if (!tool.getToolProperties(event.left).isBroken()
					&& tool.getToolProperties(event.left).getHeadMaterial().headMaterial
							.getItem() == event.right.getItem()) {
				event.output = event.left.copy();

				int repairStep = event.left.getMaxDamage() / 4;
				for (int i = 0; i < event.right.stackSize; i++) {
					int repairedDamage = Math.max(event.output.getItem()
							.getMaxDamage(), event.output.getItemDamage()
							- repairStep);

					if (repairedDamage < event.output.getItemDamage()) {
						event.output.setItemDamage(repairedDamage);
						this.cost++;
					} else {
						break;
					}
				}

				event.cost = this.cost;
				event.materialCost = this.cost;
				return;

			} else if (event.right.getItem() instanceof ItemToolHead) {
				ToolRepairRecipes recipe = new ToolRepairRecipes();

				if (recipe.matches(event.left, event.right)) {
					event.output = recipe.getCraftingResult();

					NBTTagList toolList = getEnchantmentTagList(event.left);
					if (toolList.tagCount() > 0) {
						this.enchantedBook = new ItemStack(Items.enchanted_book);
						this.enchantedBook.getTagCompound().setTag("ench",
								toolList);
						if (this.matches(event.left, this.enchantedBook)) {
							event.output = this.getCraftingResult();
						}
					}
				}

				event.materialCost = 1;
				event.cost = 5;
				return;

			} else if (this.matches(event.left, event.right)) {
				event.output = this.getCraftingResult();
				event.cost = this.cost;
				return;
			}
		}
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	public boolean matches(ItemStack left, ItemStack right) {
		this.enchantedBook = null;
		this.tool = null;

		if (Item.getIdFromItem(right.getItem()) == Item
				.getIdFromItem(Items.enchanted_book)) {
			this.enchantedBook = right;
		}

		if (left.getItem() instanceof ToolBase) {
			this.tool = ((ToolBase) left.getItem()).getToolProperties(left);
		}

		return this.enchantedBook != null && this.tool != null;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting,
			World par2World) {
		this.enchantedBook = null;
		this.tool = null;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				ItemStack itemstack = par1InventoryCrafting
						.getStackInRowAndColumn(j, i);

				if (itemstack != null) {
					if (Item.getIdFromItem(itemstack.getItem()) == Item
							.getIdFromItem(Items.enchanted_book)) {
						if (this.enchantedBook != null)
							return false;

						this.enchantedBook = itemstack;
					}

					if (itemstack.getItem() instanceof ToolBase) {
						if (this.tool != null)
							return false;

						this.tool = ((ToolBase) itemstack.getItem())
								.getToolProperties(itemstack);
					}
				}
			}
		}

		return this.enchantedBook != null && this.tool != null;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
		return this.getCraftingResult();
	}

	public ItemStack getCraftingResult() {
		this.cost = 0;
		ItemStack output = this.tool.getStack().copy();

		NBTTagList toolList = getEnchantmentTagList(output);
		NBTTagList bookList = getEnchantmentTagList(this.enchantedBook.copy());

		boolean changed = false;

		for (int iBook = 0; iBook < bookList.tagCount(); iBook++) {
			boolean found = false;
			NBTTagCompound bookEnchant = bookList.getCompoundTagAt(iBook);

			// Check the tool for allowed enchants
			if (!this.tool
					.getItem()
					.getAllowedEnchantments()
					.contains(
							Enchantment.enchantmentsList[bookEnchant
									.getShort("id")].type))
				continue;

			// Check if the enchant already exists
			for (int iTool = 0; iTool < toolList.tagCount(); iTool++) {
				NBTTagCompound toolEnchant = toolList.getCompoundTagAt(iTool);
				if (toolEnchant.getShort("id") == bookEnchant.getShort("id")) {
					found = true;
					if (toolEnchant.getShort("lvl") < bookEnchant
							.getShort("lvl")) {
						changed = true;
						toolEnchant
								.setShort("lvl", bookEnchant.getShort("lvl"));
					}
					continue;
				}
			}

			// It doesn't exist yet, just add it
			if (!found) {
				boolean allowed = true;
				for (int iTool = 0; iTool < toolList.tagCount(); iTool++) {
					int toolEnchant = toolList.getCompoundTagAt(iTool)
							.getShort("id");
					if (!Enchantment.enchantmentsList[toolEnchant]
							.canApplyTogether(Enchantment.enchantmentsList[bookEnchant
									.getShort("id")])) {
						allowed = false;
					}
				}

				if (allowed) {
					changed = true;
					toolList.appendTag(bookEnchant);
					int lvl = bookEnchant.getShort("lvl") + 1;
					int cost = lvl * lvl * 2
							/ (this.tool.getHeadMaterial().arcaneLevel + 1);
					this.cost += cost;
				}
			}
		}

		if ((output.getEnchantmentTagList() == null || output
				.getEnchantmentTagList().tagCount() == 0)
				&& toolList.tagCount() > 0) {
			output.getTagCompound().setTag("ench", toolList);
		}

		return changed ? output : null;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return 2;
	}

	public static NBTTagList getEnchantmentTagList(ItemStack itemStack) {
		NBTTagList nbttaglist = itemStack.getEnchantmentTagList();

		if (nbttaglist == null
				|| (nbttaglist.tagCount() == 0 && Item.getIdFromItem(itemStack
						.getItem()) == Item.getIdFromItem(Items.enchanted_book)))
			nbttaglist = Items.enchanted_book.func_92110_g(itemStack);

		return nbttaglist != null ? nbttaglist : new NBTTagList();
	}
}
