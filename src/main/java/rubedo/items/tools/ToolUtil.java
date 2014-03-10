package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ToolUtil {
	public static boolean hitEntity(
			ToolBase toolBase, ItemStack stack, 
			EntityLivingBase entity) {
		
		damageTool(stack, toolBase, entity, toolBase.getItemDamageOnHit());
		
		//TODO: damage entity?
                
        return true;
	}

	public static boolean onBlockDestroyed(
			ToolBase toolBase, ItemStack stack, 
			World world, int blockID, 
			int blockX, int blockY, int blockZ, 
			EntityLivingBase entity) {

		damageTool(stack, toolBase, entity, toolBase.getItemDamageOnBreak());

        return true;
	}
	
	public static float getStrVsBlock(ToolBase toolBase, ItemStack stack, Block block, int meta) {
		
		ToolProperties properties = toolBase.getToolProperties(stack);
		
		if (properties.isValid()) {
			if (properties.isBroken())
	            return 0.1f;
	
	    	for (int i = 0; i < toolBase.getEffectiveMaterials().length; i++)
	        {
	            if (toolBase.getEffectiveMaterials()[i] == block.blockMaterial)
	            {
	                return toolBase.getEffectiveSpeed();
	            }
	        }
		}
		
    	return toolBase.getBaseSpeed();
	}
	
	public static void damageTool(ItemStack stack, ToolBase toolBase, EntityLivingBase entity, int damage) {
		ToolProperties properties = toolBase.getToolProperties(stack);
		
		if (properties.isValid() && !properties.isBroken()) {
			stack.damageItem(damage, entity);
			
			if (stack.getItemDamage() >= properties.getDurability()) {
				properties.setBroken(true);
				properties.resetName(toolBase.getName());
			}
		}
	}
	
	public static boolean isDamaged(ToolBase toolBase, ItemStack stack) {
		ToolProperties properties = toolBase.getToolProperties(stack);
		
		if (properties.isValid())
			return stack.getItemDamage() > 0 && !properties.isBroken();
		else
			return false;
	}
	
	public static int getDisplayDamage(ToolBase toolBase, ItemStack stack) {
		ToolProperties properties = toolBase.getToolProperties(stack);
		
		if (properties.isValid())
			return (int) (((float) stack.getItemDamage()) / properties.getDurability() * Integer.MAX_VALUE);
		else
			return 0;
	}
}
