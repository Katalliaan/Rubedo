package rubedo.items;

import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(int par1) {
		super(par1);
		this.setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
