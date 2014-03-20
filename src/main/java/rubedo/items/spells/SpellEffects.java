package rubedo.items.spells;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Helper class for spells
 */
public class SpellEffects {
	
	public static boolean hitsBlocks(String effectType)
	{
		ArrayList effects = new ArrayList();
		
		effects.add("fire");
		effects.add("water");
		
		return effects.contains(effectType);
	}

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
		} else if (effectType == "water" && entity instanceof EntityLivingBase) {
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
		} else if (effectType == "water" && !world.provider.isHellWorld) {
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
				world.setBlock(blockX, blockY, blockZ,
						Block.waterMoving.blockID);
				world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 1, 1);

				for (int i = 0; i < 4; i++) {
					int secondaryX = blockX;
					int secondaryZ = blockZ;

					switch (i) {
						case 0 :
							--secondaryX;
							break;
						case 1 :
							++secondaryX;
							break;
						case 2 :
							--secondaryZ;
							break;
						case 3 :
							++secondaryZ;
							break;
					}

					if (world.isAirBlock(secondaryX, blockY, secondaryZ)) {
						world.setBlock(secondaryX, blockY, secondaryZ,
								Block.waterMoving.blockID);
						world.setBlockMetadataWithNotify(secondaryX, blockY,
								secondaryZ, 2, 1);
					}
				}
			}
		}
	}
}