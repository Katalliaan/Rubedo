package rubedo.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rubedo.RubedoCore;
import rubedo.common.materials.MaterialMultiItem.MaterialType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemAutoRepair extends Item {
	protected IIcon icon;

	public ItemAutoRepair() {
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(RubedoCore.creativeTabTools);
		this.setTextureName(RubedoCore.modid + ":autorepair");
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
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

			if (stack != null && stack.getItem() instanceof ToolBase && ((EntityPlayer) holdingEntity).experienceLevel > 0) {
				ToolProperties properties = ((ToolBase) stack.getItem())
						.getToolProperties(stack);

				if (!properties.isBroken() && properties.getMaterialType() == MaterialType.METAL_ARCANE && stack.getItemDamage() > 0) {
					((EntityPlayer) holdingEntity).addExperienceLevel(-1);
					stack.setItemDamage(stack.getItemDamage() - Math.min(stack.getItemDamage(), 5));
				}
			}
		}
	}
}
