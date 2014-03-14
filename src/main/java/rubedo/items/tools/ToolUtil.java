package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import rubedo.common.ContentTools;

public class ToolUtil {
	public static boolean hitEntity(
			ToolProperties properties, 
			EntityLivingBase entity) {
		
		damageTool(properties, entity, properties.getTool().getItemDamageOnHit());
		
		//TODO: damage entity?
                
        return true;
	}

	public static boolean onBlockDestroyed(
			ToolProperties properties, 
			World world, int blockID, 
			int blockX, int blockY, int blockZ, 
			EntityLivingBase entity) {

		damageTool(properties, entity, properties.getTool().getItemDamageOnBreak());

        return true;
	}
	
	public static float getStrVsBlock(ToolProperties properties, Block block, int meta) {
				
		if (properties.isValid()) {
			if (properties.isBroken())
	            return 0.1f;
			
			for (int i = 0; i < properties.getTool().getEffectiveBlocks().length; i++)
            {
				if (properties.getTool().getEffectiveBlocks()[i] == block) {
					return properties.getTool().getEffectiveBlockSpeed()
							* ContentTools.toolHeadMaterials.get(properties.getHeadMaterial()).speed;
				}
            }
	
    		for (int i = 0; i < properties.getTool().getEffectiveMaterials().length; i++)
            {
    			if (properties.getTool().getEffectiveMaterials()[i] == block.blockMaterial) {
	                return properties.getTool().getEffectiveMaterialSpeed();
    			}
            }
		}
		
    	return properties.getTool().getBaseSpeed();
	}
	
	public static void damageTool(ToolProperties properties, EntityLivingBase entity, int damage) {	
		if (properties.isValid() && !properties.isBroken()) {
			properties.getStack().damageItem(damage, entity);
			
			if (properties.getStack().getItemDamage() >= properties.getDurability()) {
				properties.setBroken(true);
			}
		}
	}
	
	public static boolean isDamaged(ToolProperties properties) {
		if (properties.isValid())
			return properties.getStack().getItemDamage() > 0 && !properties.isBroken();
		else
			return false;
	}
	
	public static int getDisplayDamage(ToolProperties properties) {
		if (properties.isValid())
			return (int) (((float) properties.getStack().getItemDamage()) / properties.getDurability() * Integer.MAX_VALUE);
		else
			return 0;
	}
}
