package rubedo.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.Language;

public class ItemBlockMetalOre extends ItemBlockBase {

	public ItemBlockMetalOre(Block block) {
		super(block);
		this.setCreativeTab(RubedoCore.creativeTab);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Language
				.getFormattedLocalization("materials.oreName", true)
				.put("$material",
						"materials."
								+ ContentWorld.metals
										.get(ContentWorld.oreBlocks
												.getBehavior().getId(
														stack.getItemDamage())).name)
				.getResult();
	}
}
