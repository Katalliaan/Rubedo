package rubedo.items.tools;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import rubedo.common.Content;

public class ToolEnchantmentRecipes implements IRecipe {
	private ToolProperties tool;
	private ItemStack enchantedBook;
	
    public ItemStack getRecipeOutput()
    {
        return new ItemStack(Content.toolSword);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {   	
    	this.enchantedBook = null;
    	this.tool = null;
    	
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = par1InventoryCrafting.getStackInRowAndColumn(j, i);

                if (itemstack != null)
                {
                    if (itemstack.getItem().itemID == Item.enchantedBook.itemID && itemstack.getEnchantmentTagList() != null) {
                    	if (this.enchantedBook != null)
                    		return false;
                    	
                    	this.enchantedBook = itemstack;
                    }
                    
                    if (itemstack.getItem() instanceof ToolBase) {
                    	if (this.tool != null)
                    		return false;
                    	
                    	this.tool = ((ToolBase)itemstack.getItem()).getToolProperties(itemstack);
                    }
                }
            }
        }

        return this.enchantedBook != null && this.tool != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
    	ItemStack output = this.tool.getStack().copy();
    	NBTTagList toolList = output.getEnchantmentTagList();
    	NBTTagList bookList = this.enchantedBook.copy().getEnchantmentTagList();
    	
    	boolean changed = false;
    	
    	//TODO: check to make sure tool can accept the enchant
    	if (toolList == null) {
    		this.tool.getStack().getTagCompound().setTag("ench", bookList);
    	} 
    	else {
    		for (int iBook = 0; iBook < bookList.tagCount(); iBook++) {
    			boolean found = false;
    			NBTTagCompound bookEnchant = (NBTTagCompound) bookList.tagAt(iBook);
	    		for (int iTool = 0; iTool < toolList.tagCount(); iTool++) {
	    			NBTTagCompound toolEnchant = (NBTTagCompound) toolList.tagAt(iTool);
	    			if (toolEnchant.getShort("id") == bookEnchant.getShort("id")) {
	    				found = true;
	    				if (toolEnchant.getShort("lvl") < bookEnchant.getShort("lvl")) {
		    				changed = true;
		    				toolEnchant.setShort("lvl", bookEnchant.getShort("lvl"));
	    				}
	    				break;
	    			}
	    		}
	    		if (!found) {
	    			changed = true;
	    			toolList.appendTag(bookEnchant);
	    		}
    		}
    	}
    	
    	return changed ? output : null;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 2;
    }
}
