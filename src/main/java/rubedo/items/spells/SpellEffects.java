package rubedo.items.spells;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Helper class for spells
 */
public class SpellEffects {

	/**
	 * Use when targeting entities
	 * 
	 * @param world
	 * @param entity
	 *            entity hit
	 * @param power
	 *            power of spell
	 * @param effectType
	 *            type of spell
	 */
	public static void hitEntity(World world, Entity entity, int power,
			String effectType) {
		if (effectType == "fire") {
			if (!entity.isImmuneToFire())
				entity.setFire(power);
		} else if (effectType == "water" && entity instanceof EntityLiving) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(
					Potion.moveSlowdown.getId(), 100, power, false));
		}
	}

	public static void hitBlock(World world, String effectType, int blockX,
			int blockY, int blockZ, int sideHit) {
		if (effectType == "fire") {
			switch (sideHit) {
				case 0 :
					--blockY;
					break;
				case 1 :
					++blockY;
					break;
				case 2 :
					--blockZ;
					break;
				case 3 :
					++blockZ;
					break;
				case 4 :
					--blockX;
					break;
				case 5 :
					++blockX;
			}

			if (world.isAirBlock(blockX, blockY, blockZ)) {
				world.setBlock(blockX, blockY, blockZ, Block.fire.blockID);
			}
		} else if (effectType == "water") {
			switch (sideHit) {
				case 0 :
					--blockY;
					break;
				case 1 :
					++blockY;
					break;
				case 2 :
					--blockZ;
					break;
				case 3 :
					++blockZ;
					break;
				case 4 :
					--blockX;
					break;
				case 5 :
					++blockX;
			}

			// TODO: figure out why this doesn't behave as expected -
			// disappears instantly instead of flowing for a bit
			if (world.isAirBlock(blockX, blockY, blockZ)) {
				world.setBlock(blockX, blockY, blockZ,
						Block.waterMoving.blockID);
				world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 8, 1);
			}
		}
	}
}