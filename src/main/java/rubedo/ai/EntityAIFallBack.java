package rubedo.ai;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityAIFallBack extends EntityAIBase {
	public final IEntitySelector selector = new EntityAIFallBackSelector(this);

	/** The entity we are attached to */
	private EntitySkeleton theEntity;
	private double speed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;

	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;

	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;

	/** The class of the entity we should avoid */
	private Class targetEntityClass;

	public EntityAIFallBack(EntitySkeleton theEntity, Class targetEntityClass,
			float distanceFromEntity, double speed) {
		this.theEntity = theEntity;
		this.targetEntityClass = targetEntityClass;
		this.distanceFromEntity = distanceFromEntity;
		this.speed = speed;
		this.entityPathNavigate = theEntity.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.targetEntityClass == EntityPlayer.class) {
			this.closestLivingEntity = this.theEntity.worldObj
					.getClosestPlayerToEntity(this.theEntity,
							(double) this.distanceFromEntity);

			if (this.closestLivingEntity == null
					|| this.closestLivingEntity
							.getDistanceSqToEntity(theEntity) > 49) {
				return false;
			}
		} else {
			List<Entity> list = this.theEntity.worldObj
					.selectEntitiesWithinAABB(this.targetEntityClass,
							this.theEntity.boundingBox.expand(
									(double) this.distanceFromEntity, 3.0D,
									(double) this.distanceFromEntity),
							this.selector);

			if (list.isEmpty()) {
				return false;
			}

			this.closestLivingEntity = (Entity) list.get(0);
		}

		Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(
				this.theEntity, 6, 4, Vec3.createVectorHelper(
						this.closestLivingEntity.posX,
						this.closestLivingEntity.posY,
						this.closestLivingEntity.posZ));

		if (vec3 == null) {
			return false;
		} else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord,
				vec3.yCoord, vec3.zCoord) < this.closestLivingEntity
				.getDistanceSqToEntity(this.theEntity)) {
			return false;
		} else {
			this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(
					vec3.xCoord, vec3.yCoord, vec3.zCoord);
			return this.entityPathEntity == null ? false
					: this.entityPathEntity.isDestinationSame(vec3);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		if (closestLivingEntity.getDistanceToEntity(theEntity) > 49)
			return false;
		
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.entityPathNavigate.setPath(this.entityPathEntity, this.speed);
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		this.theEntity.getNavigator().setSpeed(this.speed);
	}

	static EntityCreature getCreature(EntityAIFallBack entityAvoiderAI) {
		return entityAvoiderAI.theEntity;
	}
}
