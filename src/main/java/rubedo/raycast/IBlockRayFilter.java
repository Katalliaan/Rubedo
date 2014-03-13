package rubedo.raycast;

import net.minecraft.block.Block;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public interface IBlockRayFilter extends IFilter<IBlockRayFilter.WorldPosition> {
	/**
	 * Check if the block ray is allowed to return this block.
	 * Note: disallowing a block will not stop the ray, a very high resistance will.
	 */
	boolean matches(IBlockRayFilter.WorldPosition position);
	
	float getBlockResistance(IBlockRayFilter.WorldPosition position);
	
	public static class WorldPosition {
		public WorldPosition(World world, ChunkPosition position) {
			this.world = world;
			this.position = position;
		}
		
		public World world;
		public ChunkPosition position;
		
		public Block getBlock() {
			return Block.blocksList[this.world.getBlockId(this.position.x, this.position.y, this.position.z)];
		}
	}
}
