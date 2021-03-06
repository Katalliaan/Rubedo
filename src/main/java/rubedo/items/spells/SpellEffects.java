package rubedo.items.spells;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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
	public static void hitEntity(World world, Entity entity,
			SpellProperties properties) {
		String effectType = properties.getEffectType();
		int power = properties.getPower();

		if (effectType == "fire") {
			if (!entity.isImmuneToFire())
				entity.setFire(power);
		} else if (effectType == "water" && entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(
					Potion.moveSlowdown.getId(), 100, power, false));
			entity.extinguish();
		} else if (effectType == "life" && entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).heal(power);
		}
	}

	public static void hitBlock(World world, SpellProperties properties,
			int blockX, int blockY, int blockZ, int sideHit) {
		String effectType = properties.getEffectType();
		Block block = world.getBlock(blockX, blockY, blockZ);

		if (effectType == "fire") {
			switch (sideHit) {
			case 0:
				--blockY;
				break;
			case 1:
				++blockY;
				break;
			case 2:
				--blockZ;
				break;
			case 3:
				++blockZ;
				break;
			case 4:
				--blockX;
				break;
			case 5:
				++blockX;
			}

			if (world.isAirBlock(blockX, blockY, blockZ)) {
				world.setBlock(blockX, blockY, blockZ, Blocks.fire);
			}
		} else if (effectType == "water" && !world.provider.isHellWorld) {
			switch (sideHit) {
			case 0:
				--blockY;
				break;
			case 1:
				++blockY;
				break;
			case 2:
				--blockZ;
				break;
			case 3:
				++blockZ;
				break;
			case 4:
				--blockX;
				break;
			case 5:
				++blockX;
			}

			if (world.isAirBlock(blockX, blockY, blockZ)) {
				world.setBlock(blockX, blockY, blockZ, Blocks.flowing_water);
				world.setBlockMetadataWithNotify(blockX, blockY, blockZ, 1, 1);

				for (int i = 0; i < 4; i++) {
					int secondaryX = blockX;
					int secondaryZ = blockZ;

					switch (i) {
					case 0:
						--secondaryX;
						break;
					case 1:
						++secondaryX;
						break;
					case 2:
						--secondaryZ;
						break;
					case 3:
						++secondaryZ;
						break;
					}

					if (world.isAirBlock(secondaryX, blockY, secondaryZ)) {
						world.setBlock(secondaryX, blockY, secondaryZ,
								Blocks.flowing_water);
						world.setBlockMetadataWithNotify(secondaryX, blockY,
								secondaryZ, 2, 1);
					}
				}
			}
		} // break spells have to test against air blocks in the event of one
			// block relying on another (signs, vines, torches, etc)
		else if (effectType == "break"
				&& world.getBlock(blockX, blockY, blockZ) != Blocks.air) {
			int metadata = world.getBlockMetadata(blockX, blockY, blockZ);

			// TODO: see if this can be cleaned up a bit
			boolean canHarvest = false;
			int miningLevel = properties.getMiningLevel();

			if (block.getHarvestLevel(metadata) <= miningLevel
					&& block.getHarvestLevel(metadata) != -1)
				canHarvest = true;
			else if (block.getMaterial().isToolNotRequired())
				canHarvest = true;

			if (canHarvest) {
				block.dropBlockAsItemWithChance(world, blockX, blockY, blockZ,
						world.getBlockMetadata(blockX, blockY, blockZ), 1.0F, 0);
				world.setBlock(blockX, blockY, blockZ, Blocks.air);
			}
		} else if (effectType == "life") {
			if (block instanceof BlockCrops) {
				world.scheduleBlockUpdate(blockX, blockY, blockZ, block, 0);
			}
		}
	}
}