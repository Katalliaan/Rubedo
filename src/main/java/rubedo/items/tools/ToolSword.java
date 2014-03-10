package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rubedo.common.Content;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ToolSword extends ToolBase {

	public ToolSword(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "sword";
	}
	
	@Override
	public int getItemDamageOnHit() { return 1;	}
	@Override
	public int getItemDamageOnBreak() {	return 2; }
	@Override
	public float getEffectiveSpeed () { return 1.5f; }
	@Override
	public Material[] getEffectiveMaterials() {	return new Material[] { Material.plants, Material.vine, Material.coral, Material.leaves, Material.pumpkin }; }
	
	@Override
    public float getStrVsBlock (ItemStack stack, Block block, int meta)
    {
		if (block.blockMaterial == Material.web)
			return 15.0f;
		
        return super.getStrVsBlock(stack, block, meta);
    }
	
	@Override
	public EnumAction getItemUseAction (ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
	
	@Override
	public int getMaxItemUseDuration (ItemStack par1ItemStack)
    {
        return 72000;
    }
	
	@Override
	public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return super.onItemRightClick(stack, world, player);
    }
	
	@Override
	public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        return false;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void onUpdate (ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        super.onUpdate(stack, world, entity, par4, par5);
        if (entity instanceof EntityPlayerSP)
        {
            EntityPlayerSP player = (EntityPlayerSP) entity;
            ItemStack usingItem = player.getItemInUse();
            if (usingItem != null && usingItem.getItem() == this)
            {
                player.movementInput.moveForward *= 2.5F;
                player.movementInput.moveStrafe *= 2.5F;
            }
        }
    }
	
	/**
	 * Build the tool from its parts
	 */
	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(Content.toolSword);
		
		super.buildTool(tool, head, rod, cap);
		
		return tool;
	}
}
