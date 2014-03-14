package rubedo.items;

import net.minecraft.item.ItemStack;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.Language;

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
