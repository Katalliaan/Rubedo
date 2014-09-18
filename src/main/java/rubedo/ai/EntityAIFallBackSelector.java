package rubedo.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

class EntityAIFallBackSelector implements IEntitySelector {
	final EntityAIFallBack entityAvoiderAI;

	EntityAIFallBackSelector(EntityAIFallBack par1EntityAIAvoidEntity) {
		this.entityAvoiderAI = par1EntityAIAvoidEntity;
	}

	/**
	 * Return whether the specified entity is applicable to this filter.
	 */
	public boolean isEntityApplicable(Entity par1Entity) {
		return par1Entity.isEntityAlive()
				&& EntityAIFallBack.getCreature(this.entityAvoiderAI)
						.getEntitySenses().canSee(par1Entity);
	}
}
