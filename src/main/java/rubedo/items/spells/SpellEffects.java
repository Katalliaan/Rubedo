package rubedo.items.spells;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Helper class for spells
 */
public class SpellEffects {

	public static boolean hitsBlocks(String effectType) {
		ArrayList<String> effects = new ArrayList<String>();

		effects.add("fire");
		effects.add("water");
		effects.add("break");
		effects.add("life");

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
		} else if (effectType == "life" && entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).heal(power);
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
		} // break spells have to test against air blocks in the event of one
			// block relying on another (signs, vines, torches, etc)
		else if (effectType == "break"
				&& world.getBlockId(blockX, blockY, blockZ) != 0) {
			Block block = Block.blocksList[world.getBlockId(blockX, blockY,
					blockZ)];

			// TODO: add tests to determine if a block should be broken
			block.dropBlockAsItemWithChance(world, blockX, blockY, blockZ,
					world.getBlockMetadata(blockX, blockY, blockZ), 1.0F, 0);
			world.setBlock(blockX, blockY, blockZ, 0);
		} else if (effectType == "life") {
			int l = world.getBlockId(blockX, blockY, blockZ); 
			if (Block.blocksList[l] instanceof BlockCrops)
			{
				world.scheduleBlockUpdate(blockX, blockY, blockZ, l, 0);
			}
		}
	}
}