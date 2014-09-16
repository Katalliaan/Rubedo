package rubedo.common;

import rubedo.util.Singleton;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class ContentAI extends Singleton<ContentAI> implements IContent {

	protected ContentAI() {
		super(ContentAI.class);
	}

	@Override
	public void config(Configuration config) {}

	@Override
	public void register() {
		// Adding AI EventHandlers
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityAnimalEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityLivingEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.LivingDropsEventHandler());
		// MinecraftForge.EVENT_BUS.register(new rubedo.ai.LivingSpawnEventHandler());
	}
}
