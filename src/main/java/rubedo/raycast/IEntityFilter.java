package rubedo.raycast;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public abstract class IEntityFilter implements IFilter<Entity> {
	protected IEntitySelector selector = new IEntitySelector() {
		@Override
		public boolean isEntityApplicable(Entity entity) {
			return IEntityFilter.this.matches(entity);
		}
	};
	
	public IEntitySelector getIEntitySelector() {
		return selector;
	}
}
