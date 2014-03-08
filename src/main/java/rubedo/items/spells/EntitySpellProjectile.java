package rubedo.items.spells;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySpellProjectile extends Entity implements IProjectile {

	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private int inData;
	private boolean inGround;
	public EntityLivingBase shootingEntity;
	private int ticksAlive;
	private int ticksInAir;

	// Related to spell
	private int power;
	private String type;

	public EntitySpellProjectile(World par1World) {
		super(par1World);
		this.setSize(1.0F, 1.0F);
	}

	/**
	 * Used in casting SpellProjectile
	 * 
	 * @param par1World
	 * @param par2EntityLivingBase
	 *            Casting Player
	 * @param speed
	 *            Initial push imparted by cast
	 * @param type
	 *            Type of effect
	 * @param power
	 *            Power of effect
	 */
	public EntitySpellProjectile(World par1World,
			EntityLivingBase par2EntityLivingBase, float speed, String type,
			int power) {
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLivingBase;
		this.type = type;
		this.power = power;

		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(
				par2EntityLivingBase.posX,
				par2EntityLivingBase.posY
						+ (double) par2EntityLivingBase.getEyeHeight(),
				par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw,
				par2EntityLivingBase.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ,
				speed * 1.5F, 1.0F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));

	}

	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (type == "fire")
			impactFire(par1MovingObjectPosition);
		else if (type == "water")
			impactWater(par1MovingObjectPosition);
	}

	protected void impactWater(MovingObjectPosition par1MovingObjectPosition) {
		if (!this.worldObj.isRemote) {
			if (par1MovingObjectPosition.entityHit != null
					&& par1MovingObjectPosition.entityHit instanceof EntityLiving) {
				EntityLiving entityHit = (EntityLiving) par1MovingObjectPosition.entityHit;

				entityHit.addPotionEffect(new PotionEffect(Potion.moveSlowdown
						.getId(), 100, power, false));
			} else {
				int blockX = par1MovingObjectPosition.blockX;
				int blockY = par1MovingObjectPosition.blockY;
				int blockZ = par1MovingObjectPosition.blockZ;

				switch (par1MovingObjectPosition.sideHit) {
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
				if (this.worldObj.isAirBlock(blockX, blockY, blockZ)) {
					this.worldObj.setBlock(blockX, blockY, blockZ,
							Block.waterMoving.blockID);
					this.worldObj.setBlockMetadataWithNotify(blockX, blockY,
							blockZ, 8, 1);
				}
			}

			this.setDead();
		}
	}

	protected void impactFire(MovingObjectPosition par1MovingObjectPosition) {
		if (!this.worldObj.isRemote) {
			if (par1MovingObjectPosition.entityHit != null) {
				if (!par1MovingObjectPosition.entityHit.isImmuneToFire()) {
					par1MovingObjectPosition.entityHit.setFire(power);
				}
			} else {
				int blockX = par1MovingObjectPosition.blockX;
				int blockY = par1MovingObjectPosition.blockY;
				int blockZ = par1MovingObjectPosition.blockZ;

				switch (par1MovingObjectPosition.sideHit) {
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

				if (this.worldObj.isAirBlock(blockX, blockY, blockZ)) {
					this.worldObj.setBlock(blockX, blockY, blockZ,
							Block.fire.blockID);
				}
			}

			this.setDead();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float motion = MathHelper.sqrt_double(this.motionX * this.motionX
					+ this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(
					this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(
					this.motionY, (double) motion) * 180.0D / Math.PI);
		}

		int currentTile = this.worldObj.getBlockId(this.xTile, this.yTile,
				this.zTile);

		if (currentTile > 0) {
			Block.blocksList[currentTile].setBlockBoundsBasedOnState(
					this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB axisalignedbb = Block.blocksList[currentTile]
					.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile,
							this.yTile, this.zTile);

			if (axisalignedbb != null
					&& axisalignedbb.isVecInside(this.worldObj
							.getWorldVec3Pool().getVecFromPool(this.posX,
									this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.inGround) {
			int inBlockId = this.worldObj.getBlockId(this.xTile, this.yTile,
					this.zTile);

			if (inBlockId == this.inTile) {
				++this.ticksAlive;

				if (this.ticksAlive == 600) {
					this.setDead();
				}

				return;
			}

			this.inGround = false;
			this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
			this.ticksAlive = 0;
			this.ticksInAir = 0;
		} else {
			++this.ticksInAir;
			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(
					this.posX, this.posY, this.posZ);
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(
					this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj
					.rayTraceBlocks_do_do(vec3, vec31, false, true);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX,
					this.posY, this.posZ);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(
					this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(
						movingobjectposition.hitVec.xCoord,
						movingobjectposition.hitVec.yCoord,
						movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
					this,
					this.boundingBox.addCoord(this.motionX, this.motionY,
							this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int l;
			float f1;

			for (l = 0; l < list.size(); ++l) {
				Entity entity1 = (Entity) list.get(l);

				if (entity1.canBeCollidedWith()
						&& (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(
							(double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1
							.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null) {
						double d1 = vec3
								.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null
					&& movingobjectposition.entityHit != null
					&& movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage
						|| this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity)
								.canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}

			float f2;
			float f3;

			if (movingobjectposition != null) {
				onImpact(movingobjectposition);
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX
					+ this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float) (Math.atan2(this.motionY,
					(double) f2) * 180.0D / Math.PI); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch
					+ (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw
					+ (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f4 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int j1 = 0; j1 < 4; ++j1) {
					f3 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX
							- this.motionX * (double) f3, this.posY
							- this.motionY * (double) f3, this.posZ
							- this.motionZ * (double) f3, this.motionX,
							this.motionY, this.motionZ);
				}

				f4 = 0.8F;
			}

			this.motionX *= (double) f4;
			this.motionY *= (double) f4;
			this.motionZ *= (double) f4;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.xTile = nbttagcompound.getShort("xTile");
		this.yTile = nbttagcompound.getShort("yTile");
		this.zTile = nbttagcompound.getShort("zTile");
		this.inTile = nbttagcompound.getByte("inTile") & 255;
		this.inGround = nbttagcompound.getByte("inGround") == 1;

		if (nbttagcompound.hasKey("direction")) {
			NBTTagList nbttaglist = nbttagcompound.getTagList("direction");
			this.motionX = ((NBTTagDouble) nbttaglist.tagAt(0)).data;
			this.motionY = ((NBTTagDouble) nbttaglist.tagAt(1)).data;
			this.motionZ = ((NBTTagDouble) nbttaglist.tagAt(2)).data;
		} else {
			this.setDead();
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("xTile", (short) this.xTile);
		nbttagcompound.setShort("yTile", (short) this.yTile);
		nbttagcompound.setShort("zTile", (short) this.zTile);
		nbttagcompound.setByte("inTile", (byte) this.inTile);
		nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		nbttagcompound.setTag(
				"direction",
				this.newDoubleNBTList(new double[]{this.motionX, this.motionY,
						this.motionZ}));

	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float force,
			float coneOfFire) {
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) f2;
		y /= (double) f2;
		z /= (double) f2;
		x += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		y += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		z += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		x *= (double) force;
		y *= (double) force;
		z *= (double) force;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y,
				(double) f3) * 180.0D / Math.PI);

	}

}
