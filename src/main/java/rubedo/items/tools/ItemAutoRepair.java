package rubedo.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAutoRepair extends Item {

	public ItemAutoRepair() {
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
		this.setMaxStackSize(0);
		// this.setCreativeTab(RubedoCore.creativeTabTools);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "rubedo.tools.autorepair";
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world,
			Entity holdingEntity, int p_77663_4_, boolean inHand) {
		if (!world.isRemote && holdingEntity instanceof EntityPlayer) {
			ItemStack stack = ((EntityPlayer) holdingEntity).getHeldItem();

			if (stack != null && stack.getItem() instanceof ToolBase) {
				ToolProperties properties = ((ToolBase) stack.getItem())
						.getToolProperties(stack);

				if (!properties.isBroken() && stack.getItemDamage() > 0) {
					stack.setItemDamage(stack.getItemDamage() - 1);
				}
			}
		}
	}
}
