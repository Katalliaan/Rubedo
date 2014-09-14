package rubedo.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.Language;

public class ItemBlockMetal extends ItemBlockBase {
	
	public ItemBlockMetal(Block metal) {
		super(metal);
		this.setCreativeTab(RubedoCore.creativeTab);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Language.getFormattedLocalization("materials.blockName", true)
				.put("$material", "materials." + ContentWorld.metals.get(stack.getItemDamage()).name)
				.getResult();
	}
}
