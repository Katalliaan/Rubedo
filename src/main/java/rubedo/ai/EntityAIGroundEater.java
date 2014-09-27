package rubedo.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import rubedo.util.ReflectionHelper;

/**
 * Enables mobs to eat breeding food from the ground
 */
public class EntityAIGroundEater extends EntityAIBase {
	private EntityAnimal entity;

	public EntityAIGroundEater(EntityAnimal entity) {
		this.entity = entity;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return this.entity.isEntityAlive() && !this.entity.isInLove()
				&& this.entity.getGrowingAge() == 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		if (this.entity.isEntityAlive() && !this.entity.isInLove()
				&& this.entity.getGrowingAge() == 0) {
			@SuppressWarnings("rawtypes")
			List nearbyItems = this.entity.worldObj
					.getEntitiesWithinAABBExcludingEntity(this.entity,
							this.entity.boundingBox.expand(1.0D, 0.0D, 1.0D));

			if (nearbyItems != null && nearbyItems.size() > 0) {
				for (int itemId = 0; itemId < nearbyItems.size(); itemId++) {
					Entity e = (Entity) nearbyItems.get(itemId);
					if ((e instanceof EntityItem)
							&& this.entity.isBreedingItem(((EntityItem) e)
									.getEntityItem())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		if (this.entity.isEntityAlive() && !this.entity.isInLove()
				&& this.entity.getGrowingAge() == 0) {
			@SuppressWarnings("rawtypes")
			List nearbyItems = this.entity.worldObj
					.getEntitiesWithinAABBExcludingEntity(this.entity,
							this.entity.boundingBox.expand(2.0D, 0.0D, 2.0D));
			if (nearbyItems != null && nearbyItems.size() > 0) {
				for (int itemId = 0; itemId < nearbyItems.size(); itemId++) {
					Entity e = (Entity) nearbyItems.get(itemId);
					if ((e instanceof EntityItem)
							&& this.entity.isBreedingItem(((EntityItem) e)
									.getEntityItem())) {
						if (((EntityItem) e).getEntityItem().stackSize >= 1) {
							// making the love time higher, due to the fact that
							// other AI might need to catch up
							ReflectionHelper.setField(this.entity, "inlove",
									2400);

							((EntityItem) e).getEntityItem().stackSize--;

							if (((EntityItem) e).getEntityItem().stackSize <= 0) {
								e.setDead();
							}

							// sound/particle not working all the time for some
							// reason, needs research....
							this.entity.worldObj
									.playSoundAtEntity(
											this.entity,
											"random.pop",
											0.2F,
											((this.entity.getRNG().nextFloat() - this.entity
													.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);

							this.entity.worldObj
									.playSoundEffect(this.entity.posX,
											this.entity.posY, this.entity.posZ,
											"random.explosion", 1.0F,
											this.entity.worldObj.rand
													.nextFloat() * 0.1F + 0.9F);

							double d0 = this.entity.getRNG().nextGaussian() * 0.02D;
							double d1 = this.entity.getRNG().nextGaussian() * 0.02D;
							double d2 = this.entity.getRNG().nextGaussian() * 0.02D;

							this.entity.worldObj.spawnParticle("heart",
									this.entity.posX
											+ this.entity.getRNG().nextFloat()
											* this.entity.width * 2.0F
											- this.entity.width,
									this.entity.posY + 0.5D
											+ this.entity.getRNG().nextFloat()
											* this.entity.height,
									this.entity.posZ
											+ this.entity.getRNG().nextFloat()
											* this.entity.width * 2.0F
											- this.entity.width, d0, d1, d2);
							break;
						}
					}
				}
			}

		}
	}
}