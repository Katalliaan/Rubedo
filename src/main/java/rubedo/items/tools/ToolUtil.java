package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ToolUtil {
	public static boolean hitEntity(
			ToolBase toolBase, ItemStack stack, 
			EntityLivingBase entity) {
		
		stack.damageItem(toolBase.getItemDamageOnHit(), entity);
        
        if (getDamagePercentage(toolBase.getToolProperties(stack), stack.getItemDamage()) >= 1) {
        	NBTTagCompound tags = stack.getTagCompound();
        	tags.getCompoundTag("RubedoTool").setBoolean("broken", true);
        }
        
        return true;
	}

	public static boolean onBlockDestroyed(
			ToolBase toolBase, ItemStack stack, 
			World world, int blockID, 
			int blockX, int blockY, int blockZ, 
			EntityLivingBase entity) {
		
		if ((double)Block.blocksList[blockID].getBlockHardness(world, blockX, blockY, blockZ) != 0.0D)
        {
        	stack.damageItem(toolBase.getItemDamageOnBreak(), entity);
            
            if (getDamagePercentage(toolBase.getToolProperties(stack), stack.getItemDamage()) >= 1) {
            	stack.getTagCompound().getCompoundTag("RubedoTool").setBoolean("broken", true);
            }
        }

        return true;
	}
	
	public static float getStrVsBlock(ToolBase toolBase, ItemStack stack, Block block, int meta) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void onLeftClickEntity(ToolBase toolBase, ItemStack stack,
			EntityPlayer player, Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public static void onItemRightClick(ToolBase toolBase, ItemStack stack,
			World world, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
	
	public static int getDisplayDamage(ToolBase toolBase, ItemStack stack) {
		ToolProperties properties = toolBase.getToolProperties(stack);
		
		if (!properties.isBroken())
			return (int) (getDamagePercentage(properties, stack.getItemDamage()) * 100);
		else
			return -1;
	}
	
	protected static float getDamagePercentage(ToolProperties properties, int itemDamage) {		
		float baseDur = properties.getDurability();
		float percentage = itemDamage / baseDur;
		
		return percentage;
	}
}
