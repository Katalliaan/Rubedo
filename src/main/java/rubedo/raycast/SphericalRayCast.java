package rubedo.raycast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class SphericalRayCast extends ShapedRayCast {
	public SphericalRayCast(World world, double originX, double originY,
			double originZ, double directionX, double directionY,
			double directionZ, float range) {
		super(world, originX, originY, originZ, directionX, directionY, directionZ,	range);
	}

	@Override
	public Set<ChunkPosition> getBlocks() { return getBlocks(null); }
	@Override
	public Set<ChunkPosition> getBlocks(IBlockRayFilter filter) {
		Set<ChunkPosition> output = new HashSet<ChunkPosition>();
			
		// Loop over a 16x16x16 cube
		for (int x = 0; x < this.boundSize; ++x)
        for (int y = 0; y < this.boundSize; ++y)
        for (int z = 0; z < this.boundSize; ++z)
        {
        	// Only consider the edges (hollow cube)
            if (x == 0 || x == this.boundSize - 1 || y == 0 || y == this.boundSize - 1 || z == 0 || z == this.boundSize - 1)
            {
            	// Clamp to [-1,1]
            	double xCoord = ((double) x * 2.0D / this.boundSize) - 1.0D;
            	double yCoord = ((double) y * 2.0D / this.boundSize) - 1.0D;
            	double zCoord = ((double) z * 2.0D / this.boundSize) - 1.0D;
            	
            	Vec3 direction = ShapedRayCast.normalizeVector(this.world, xCoord, yCoord, zCoord);
            	
            	castBlockRay(
        				direction.xCoord, direction.yCoord, direction.zCoord,
        				filter, output);
            }
        }
		
		return output;
	}

	@Override
	public Set<Entity> getEntities() { return getEntities(null); }
	@Override
	public Set<Entity> getEntities(IEntityFilter filter) { return getEntitiesExcludingEntity(null, null); }
	@Override
	public Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity) { return getEntitiesExcludingEntity(excludedEntity, null); }
	@SuppressWarnings("unchecked")
	@Override
	public Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity, IEntityFilter filter) {
		Set<Entity> output = new HashSet<Entity>();
		
		IEntitySelector selector = filter == null ? 
				new IEntitySelector() {
					@Override
					public boolean isEntityApplicable(Entity entity) {
						return true;
					}
				} : filter.getIEntitySelector();
		
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(excludedEntity, 
				AxisAlignedBB.getBoundingBox(
						this.originX - this.range,
						this.originY - this.range,
						this.originZ - this.range,
						this.originX + this.range,
						this.originY + this.range,
						this.originZ + this.range), 
						selector);
		
		for (Entity entity : entities) {
			double distance = entity.getDistance(this.originX, this.originY, this.originZ);
			
			if (distance <= this.range)
				output.add(entity);
		}
		
		return output;
	}
}
