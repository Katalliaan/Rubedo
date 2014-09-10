package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import rubedo.common.ContentTools;

public class ToolUtil {
	public static boolean hitEntity(ToolProperties properties,
			EntityLivingBase attackedEntity, EntityLivingBase attackingEntity) {

		damageTool(properties, attackingEntity, properties.getItem()
				.getItemDamageOnHit());

		return true;
	}

	public static boolean onBlockDestroyed(ToolProperties properties,
			World world, int blockID, int blockX, int blockY, int blockZ,
			EntityLivingBase entity) {

		damageTool(properties, entity, properties.getItem()
				.getItemDamageOnBreak());

		return true;
	}

	public static float getStrVsBlock(ToolProperties properties, Block block,
			int meta) {

		if (properties.isValid()) {
			if (properties.isBroken())
				return 0.1f;

			for (int i = 0; i < properties.getItem().getEffectiveBlocks().length; i++) {
				if (properties.getItem().getEffectiveBlocks()[i] == block) {
					return properties.getItem().getEffectiveBlockSpeed()
							* ContentTools.toolHeads.get(properties
									.getHeadMaterial()).speed;
				}
			}

			for (int i = 0; i < properties.getItem().getEffectiveMaterials().length; i++) {
				if (properties.getItem().getEffectiveMaterials()[i] == block.blockMaterial) {
					return properties.getItem().getEffectiveMaterialSpeed()
							* ContentTools.toolHeads.get(properties
									.getHeadMaterial()).speed;
				}
			}
		}

		return properties.getItem().getBaseSpeed();
	}

	public static void damageTool(ToolProperties properties,
			EntityLivingBase entity, int damage) {		
		if (properties.isValid() && !properties.isBroken()) {
			// fixes bug that leads to disappearing tools
			if (properties.getDurability() < properties.getStack().getItemDamage() + damage)
				damage = properties.getDurability() - properties.getStack().getItemDamage();
			
			properties.getStack().damageItem(damage, entity);

			if (properties.getStack().getItemDamage() >= properties
					.getDurability()) {
				properties.setBroken(true);
				properties.generateAttackDamageNBT();
				entity.renderBrokenItemStack(properties.getStack());
			}
		}
	}

	public static boolean isDamaged(ToolProperties properties) {
		if (properties.isValid())
			return properties.getStack().getItemDamage() > 0
					&& !properties.isBroken();
		else
			return false;
	}
}
