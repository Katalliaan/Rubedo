package rubedo.raycast;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkPosition;

public interface IShapedRayCast {
	/**
	 * Cast all rays and return all matched ChunkPositions
	 */
	Set<ChunkPosition> getBlocks();
	/**
	 * Cast all rays and return all matched ChunkPositions, filtered
	 */
	Set<ChunkPosition> getBlocks(IBlockRayFilter filter);
	
	/**
	 * Cast all rays and return all matched Entities
	 */
	Set<Entity> getEntities();
	/**
	 * Cast all rays and return all matched Entities, filtered
	 */
	Set<Entity> getEntities(IEntityFilter filter);
	
	/**
	 * Cast all rays and return all matched Entities, excluding entity
	 */
	Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity);
	/**
	 * Cast all rays and return all matched Entities, filtered, excluding entity
	 */
	Set<Entity> getEntitiesExcludingEntity(Entity excludedEntity, IEntityFilter filter);
}
