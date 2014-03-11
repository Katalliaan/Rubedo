package rubedo.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import rubedo.common.Content;

public class ItemBlockBase extends ItemBlock {
	
	public static String[] names;

	public ItemBlockBase(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setCreativeTab(Content.creativeTab);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return "rubedo.blocks." + names[itemstack.getItemDamage()];
    }
}
