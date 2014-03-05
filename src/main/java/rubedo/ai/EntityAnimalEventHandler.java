package rubedo.ai;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

// TODO: Add this as an AI, instead of an event handler
/**
 * Allows animals to eat off the ground
 */
public class EntityAnimalEventHandler 
{

	@ForgeSubscribe
	public void entitySpawning(EntityJoinWorldEvent event)
	{
		if (!(event.entity instanceof EntityAnimal)) 
			return;

		EntityAnimal animal = (EntityAnimal)event.entity;

		// add
		animal.tasks.addTask(0, new EntityAIGroundEater(animal));			
	}
}