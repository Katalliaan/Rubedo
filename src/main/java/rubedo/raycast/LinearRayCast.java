package rubedo.raycast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class LinearRayCast extends ShapedRayCast {

	/**
	 * Casts a single directional ray
	 */
	public LinearRayCast(World world, double originX, double originY,
			double originZ, double directionX, double directionY,
			double directionZ, float range) {
		super(world, originX, originY, originZ, directionX, directionY,
				directionZ, range);
	}

	@Override
	public Set<ChunkPosition> getBlocks() {
		return getBlocks(null);
	}

	@Override
	public Set<ChunkPosition> getBlocks(IBlockRayFilter filter) {
		Set<ChunkPosition> output = new HashSet<ChunkPosition>();

		Vec3 direction = ShapedRayCast.normalizeVector(this.world, directionX,
				directionY, directionZ);

		castBlockRay(direction.xCoord, direction.yCoord, direction.zCoord,
				filter, output);

		return output;
	}

	@Override
	public Set<Entity> getEntities() {
		return getEntities(null);
	}

	@Override
	public Set<Entity> getEntities(IEntityFilter filter) {
		return getEntitiesExcludingEntity(null, null);
	}

	@Override
	public Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity) {
		return getEntitiesExcludingEntity(excludedEntity, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity,
			IEntityFilter filter) {
		// TODO: fix me!
		Set<Entity> output = new HashSet<Entity>();

		Vec3 direction = ShapedRayCast.normalizeVector(this.world, directionX,
				directionY, directionZ);
		Vec3 origin = Vec3.createVectorHelper(this.originX, this.originY,
				this.originZ);
		Vec3 target = Vec3.createVectorHelper(this.originX
				+ (direction.xCoord * this.range), this.originY
				+ (direction.yCoord * this.range), this.originZ
				+ (direction.zCoord * this.range));

		IEntitySelector selector = filter == null ? new IEntitySelector() {
			@Override
			public boolean isEntityApplicable(Entity entity) {
				return true;
			}
		} : filter.getIEntitySelector();

		List<Entity> entities = this.world
				.getEntitiesWithinAABBExcludingEntity(excludedEntity,
						AxisAlignedBB.getBoundingBox(origin.xCoord,
								origin.yCoord, origin.zCoord, target.xCoord,
								target.yCoord, target.zCoord), selector);

		for (Entity entity : entities) {
			double distance = entity.getDistance(this.originX, this.originY,
					this.originZ);

			if (distance <= this.range && entity.canBeCollidedWith()) {
				float entBorder = entity.getCollisionBorderSize();
				AxisAlignedBB entityBb = entity.boundingBox;
				if (entityBb != null) {
					entityBb = entityBb.expand(entBorder, entBorder, entBorder);
					MovingObjectPosition intercept = entityBb
							.calculateIntercept(origin, target);
					if (intercept != null) {
						output.add(entity);
					}
				}
			}
		}

		return output;
	}
}
