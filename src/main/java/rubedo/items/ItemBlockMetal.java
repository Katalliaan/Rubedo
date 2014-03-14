package rubedo.items;

import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.Language;
import net.minecraft.item.ItemStack;

public class ItemBlockMetal extends ItemBlockBase {
	
	public ItemBlockMetal(int itemID) {
		super(itemID);
		this.setCreativeTab(RubedoCore.creativeTab);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return Language.getFormattedLocalization("materials.blockName", true)
				.put("$material", "materials." + ContentWorld.metals.get(stack.getItemDamage()).name)
				.getResult();
	}
}
