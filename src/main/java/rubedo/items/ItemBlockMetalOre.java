package rubedo.items;

import java.util.ArrayList;
import java.util.List;

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
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < ContentWorld.metals.size(); i++) {
			if (ContentWorld.metals.get(i).isGenerated)
				names.add(ContentWorld.metals.get(i).name);
		}

		return Language
				.getFormattedLocalization("materials.oreName", true)
				.put("$material",
						"materials."
								+ names.get(ContentWorld.oreBlocks
										.getBehavior().getId(
												stack.getItemDamage())))
				.getResult();
	}
}
