package rubedo.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import rubedo.util.Singleton;

public class ContentAI extends Singleton<ContentAI> implements IContent {

	private boolean animalsEatOffTheGround = true;
	private boolean tweakedMobWandering = true;
	public boolean pigmenDropSilver = true;

	protected ContentAI() {
		super(ContentAI.class);
	}

	@Override
	public void config(Configuration config) {
		this.animalsEatOffTheGround = config.get("Vanilla Changes",
				"animalsEatOffTheGround", true).getBoolean();
		this.tweakedMobWandering = config.get("Vanilla Changes",
				"tweakedMobWandering", true).getBoolean();
		this.pigmenDropSilver = config.get("Vanilla Changes",
				"pigmenDropSilver", true).getBoolean();
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
		if (this.animalsEatOffTheGround)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.EntityAnimalEventHandler());
		if (this.tweakedMobWandering)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.EntityLivingEventHandler());
		if (this.pigmenDropSilver)
			MinecraftForge.EVENT_BUS
					.register(new rubedo.ai.MobEquipmentHandler());
	}
}
