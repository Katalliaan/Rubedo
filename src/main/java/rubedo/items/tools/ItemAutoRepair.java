package rubedo.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.materials.MaterialMultiItem.MaterialType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAutoRepair extends Item {
	protected IIcon icon;

	private static final int DUR_PER_LEVEL = 5;

	public ItemAutoRepair() {
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(RubedoCore.creativeTabTools);
		this.setTextureName(RubedoCore.modid + ":autorepair");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
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

			if (stack != null && stack.getItem() instanceof ToolBase
					&& ((EntityPlayer) holdingEntity).experienceLevel > 0) {
				ToolProperties properties = ((ToolBase) stack.getItem())
						.getToolProperties(stack);

				if (!properties.isBroken()
						&& properties.getMaterialType() == MaterialType.METAL_ARCANE
						&& stack.getItemDamage() > DUR_PER_LEVEL) {
					((EntityPlayer) holdingEntity).addExperienceLevel(-1);
					stack.setItemDamage(stack.getItemDamage() - DUR_PER_LEVEL);
				}
			}
		}
	}
}
