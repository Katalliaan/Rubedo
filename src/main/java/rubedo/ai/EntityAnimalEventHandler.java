package rubedo.ai;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * Allows animals to eat off the ground
 */
public class EntityAnimalEventHandler 
{

	@SubscribeEvent
	public void entitySpawning(EntityJoinWorldEvent event)
	{
		if (!(event.entity instanceof EntityAnimal)) 
			return;

		EntityAnimal animal = (EntityAnimal)event.entity;

		// add
		animal.tasks.addTask(0, new EntityAIGroundEater(animal));			
	}
}