package rubedo.raycast;

import net.minecraft.block.Block;

public interface IBlockRayFilter extends IFilter<Block> {
	/**
	 * Check if the block ray is allowed to return this block.
	 * Note: disallowing a block will not stop the ray, a very high resistance will.
	 */
	boolean matches(Block arg);
	
	float getBlockResistance(Block block);
}
