package rubedo.common;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

public class ContentAI implements IContent {

	@Override
	public void config(Configuration config) {}

	@Override
	public void register() {
		// Adding AI EventHandlers
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityAnimalEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.EntityLivingEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.LivingDropsEventHandler());
		MinecraftForge.EVENT_BUS.register(new rubedo.ai.LivingSpawnEventHandler());
	}
}
