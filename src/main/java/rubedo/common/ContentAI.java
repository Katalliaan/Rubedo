package rubedo.common;

import rubedo.util.Singleton;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class ContentAI extends Singleton<ContentAI> implements IContent {

	private boolean animalsEatOffTheGround = true;
	private boolean tweakedMobWandering = true;
	private boolean pigmenDropSilver = true;

	protected ContentAI() {
		super(ContentAI.class);
	}

	@Override
	public void config(Configuration config) {
		animalsEatOffTheGround = config.get("Vanilla Changes",
				"animalsEatOffTheGround", true).getBoolean();
		tweakedMobWandering = config.get("Vanilla Changes",
				"tweakedMobWandering", true).getBoolean();
		pigmenDropSilver = config.get("Vanilla Changes", "pigmenDropSilver",
				true).getBoolean();
	}

	@Override
	public void registerBase() {
	}

	@Override
	public void registerDerivatives() {
	}

	@Override
	public void tweak() {
		// Adding AI EventHandlers
		if (animalsEatOffTheGround)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.EntityAnimalEventHandler());
		if (tweakedMobWandering)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.EntityLivingEventHandler());
		if (pigmenDropSilver)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.LivingDropsEventHandler());
		// MinecraftForge.EVENT_BUS.register(new
		// rubedo.ai.LivingSpawnEventHandler());
	}
}
