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
	
	protected float baseSpeed ()
    {
        return 1.5f;
    }

    protected float effectiveSpeed ()
    {
        return 15f;
    }
	
	@Override
    public float getStrVsBlock (ItemStack stack, Block block, int meta)
    {
        if (stack.getTagCompound().getCompoundTag("RubedoTool").getBoolean("broken"))
            return 0.1f;

        //TODO: set up a better list of things swords are effective against?
    	if (block.blockID == Block.web.blockID)
    	{
            return 15f;
        }
    	else
    	{
    		Material material = block.blockMaterial;
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.pumpkin ? 1.0F : 1.5F;
    	}
    }
	
	@Override
	public int getItemDamageOnHit() {
		return 1;
	}

	@Override
	public int getItemDamageOnBreak() {
		return 2;
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
        return stack;
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
