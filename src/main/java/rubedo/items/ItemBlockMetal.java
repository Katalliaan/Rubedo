package rubedo.items;

import net.minecraft.item.ItemStack;

public class ItemBlockMetal extends ItemBlockBase {
	
	public static String[] names;

	public ItemBlockMetal(int itemID) {
		super(itemID);
	}

	@Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return "rubedo.blocks." + names[itemstack.getItemDamage()];
    }
}
