package rubedo.raycast;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class LinearRayCast extends ShapedRayCast {
	
	/**
	 * Casts a single directional ray
	 */
	public LinearRayCast(World world, 
			double originX, double originY, double originZ, 
			double directionX, double directionY, double directionZ, 
			float range) {
		super(world, originX, originY, originZ, directionX, directionY, directionZ, range);
	}
	
	@Override
	public Set<ChunkPosition> getBlocks() { return getBlocks(null); }
	@Override
	public Set<ChunkPosition> getBlocks(IBlockRayFilter filter) {
		Set<ChunkPosition> output = new HashSet<ChunkPosition>();
		
		Vec3 direction = ShapedRayCast.normalizeVector(directionX, directionY, directionZ);
		
		castBlockRay(
				direction.xCoord, direction.yCoord, direction.zCoord,
				filter, output);
		
		return output;
	}

	@Override
	public Set<Entity> getEntities() {	return getEntities(null); }
	@Override
	public Set<Entity> getEntities(IFilter<Entity> filter) {
		Set<Entity> output = new HashSet<Entity>();
		//TODO: cast entity ray: http://www.minecraftforge.net/forum/index.php?topic=8565.0
		return output;
	}
}
