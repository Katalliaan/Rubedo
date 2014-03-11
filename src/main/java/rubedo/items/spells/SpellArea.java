package rubedo.items.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rubedo.common.Content;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class SpellArea extends SpellBase {

	public List affectedBlockPositions = new ArrayList();
	private Map field_77288_k = new HashMap();
	private int maxRange = 16;

	public SpellArea(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "area";
	}

	
	// TODO: figure this out
	@Override
	public void castSpell(World world, EntityPlayer entityPlayer, int power,
			String effectType, float focusModifier) {
		/*HashSet hashset = new HashSet();
		int testX;
		int testY;
		int testZ;
		double playerPosX;
		double playerPosY;
		double playerPosZ;

		for (testX = 0; testX < maxRange; ++testX) {
			for (testY = 0; testY < maxRange; ++testY) {
				for (testZ = 0; testZ < maxRange; ++testZ) {
					if (testX == 0 || testX == maxRange - 1 || testY == 0
							|| testY == maxRange - 1 || testZ == 0
							|| testZ == maxRange - 1) {
						double d3 = (double) ((float) testX
								/ ((float) maxRange - 1.0F) * 2.0F - 1.0F);
						double d4 = (double) ((float) testY
								/ ((float) maxRange - 1.0F) * 2.0F - 1.0F);
						double d5 = (double) ((float) testZ
								/ ((float) maxRange - 1.0F) * 2.0F - 1.0F);
						double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
						d3 /= d6;
						d4 /= d6;
						d5 /= d6;
						float f1 = power
								* (0.7F + entityPlayer.worldObj.rand
										.nextFloat() * 0.6F);
						playerPosX = entityPlayer.posX;
						playerPosY = entityPlayer.posY;
						playerPosZ = entityPlayer.posZ;

						for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
							int l = MathHelper.floor_double(playerPosX);
							int i1 = MathHelper.floor_double(playerPosY);
							int j1 = MathHelper.floor_double(playerPosZ);
							int blockId = entityPlayer.worldObj.getBlockId(l,
									i1, j1);

							if (blockId > 0) {
								Block block = Block.blocksList[blockId];
								float blockExplosionResistance = entityPlayer != null
										? entityPlayer
												.getBlockExplosionResistance(
														this,
														entityPlayer.worldObj,
														l, i1, j1, block)
										: block.getExplosionResistance(
												entityPlayer,
												entityPlayer.worldObj, l, i1,
												j1, entityPlayer.posX,
												entityPlayer.posY,
												entityPlayer.posZ);
								f1 -= (blockExplosionResistance + 0.3F) * f2;
							}

							if (f1 > 0.0F
									&& (entityPlayer == null || entityPlayer
											.shouldExplodeBlock(this,
													entityPlayer.worldObj, l,
													i1, j1, blockId, f1))) {
								hashset.add(new ChunkPosition(l, i1, j1));
							}

							playerPosX += d3 * (double) f2;
							playerPosY += d4 * (double) f2;
							playerPosZ += d5 * (double) f2;
						}
					}
				}
			}
		}
		
		power *= 2.0F;
		testX = MathHelper.floor_double(entityPlayer.posX - (double) power
				- 1.0D);
		testY = MathHelper.floor_double(entityPlayer.posX + (double) power
				+ 1.0D);
		testZ = MathHelper.floor_double(entityPlayer.posY - (double) power
				- 1.0D);
		int l1 = MathHelper.floor_double(entityPlayer.posY + (double) power
				+ 1.0D);
		int i2 = MathHelper.floor_double(entityPlayer.posZ - (double) power
				- 1.0D);
		int j2 = MathHelper.floor_double(entityPlayer.posZ + (double) power
				+ 1.0D);

		// Iterate through entities
		List list = entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(
				entityPlayer,
				AxisAlignedBB.getAABBPool().getAABB((double) testX,
						(double) testZ, (double) i2, (double) testY,
						(double) l1, (double) j2));
		Vec3 vec3 = entityPlayer.worldObj.getWorldVec3Pool().getVecFromPool(
				entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entityHit = (Entity) list.get(k2);
			double d7 = entityHit.getDistance(entityPlayer.posX,
					entityPlayer.posY, entityPlayer.posZ) / (double) power;

			if (d7 <= 1.0D) {
				playerPosX = entityHit.posX - entityPlayer.posX;
				playerPosY = entityHit.posY + (double) entityHit.getEyeHeight()
						- entityPlayer.posY;
				playerPosZ = entityHit.posZ - entityPlayer.posZ;
				double d8 = (double) MathHelper.sqrt_double(playerPosX
						* playerPosX + playerPosY * playerPosY + playerPosZ
						* playerPosZ);

				if (d8 != 0.0D) {
					playerPosX /= d8;
					playerPosY /= d8;
					playerPosZ /= d8;
					double d9 = (double) entityPlayer.worldObj.getBlockDensity(
							vec3, entityHit.boundingBox);
					double d10 = (1.0D - d7) * d9;
					SpellEffects.hitEntity(entityPlayer.worldObj, entityHit,
							power, effectType);

					if (entityHit instanceof EntityPlayer) {
						this.field_77288_k.put(
								(EntityPlayer) entityHit,
								entityPlayer.worldObj.getWorldVec3Pool()
										.getVecFromPool(playerPosX * d10,
												playerPosY * d10,
												playerPosZ * d10));
					}
				}
			}
		}*/
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(Content.spellArea);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}

}
