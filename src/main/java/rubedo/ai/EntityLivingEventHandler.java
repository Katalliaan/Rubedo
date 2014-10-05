package rubedo.ai;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * Allows mobs to use EntityAITweakedWandering
 */
public class EntityLivingEventHandler {
	@SubscribeEvent
	public void entitySpawning(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityLiving)
			updateLivingAI((EntityLiving) event.entity);
	}

	private void updateLivingAI(EntityLiving entity) {
		EntityAITaskEntry wanderTask = null;
		EntityAITaskEntry rangedAttackTask = null;

		for (Object task : entity.tasks.taskEntries) {
			EntityAITaskEntry taskEntry = (EntityAITaskEntry) task;

			if (taskEntry.action instanceof EntityAIWander)
				wanderTask = taskEntry;
			if (taskEntry.action instanceof EntityAIArrowAttack)
				rangedAttackTask = taskEntry;
		}

		if (entity instanceof EntitySkeleton && rangedAttackTask != null) {
			entity.tasks.addTask(3, new EntityAIFallBack(
					(EntitySkeleton) entity, EntityPlayer.class, 7.0F, 1.8D));
		}

		if (wanderTask != null) {
			entity.tasks.taskEntries.remove(wanderTask);
			float moveSpeed = 1.0F;
			if (entity instanceof EntityChicken)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityCow)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityCreeper)
				moveSpeed = 0.8F;
			else if (entity instanceof EntityHorse)
				moveSpeed = 0.7F;
			else if (entity instanceof EntityIronGolem)
				moveSpeed = 0.6F;
			else if (entity instanceof EntityOcelot)
				moveSpeed = 0.8F;
			else if (entity instanceof EntityPig)
				moveSpeed = 1.0F;
			else if (entity instanceof EntitySheep)
				moveSpeed = 1.0F;
			else if (entity instanceof EntitySkeleton)
				moveSpeed = 1.0F;
			else if (entity instanceof EntitySnowman)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityVillager)
				moveSpeed = 0.6F;
			else if (entity instanceof EntityWitch)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityWither)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityWolf)
				moveSpeed = 1.0F;
			else if (entity instanceof EntityZombie)
				moveSpeed = 1.0F;
			else
				moveSpeed = 0.85F;

			entity.tasks.addTask(((EntityAITaskEntry) wanderTask).priority,
					new EntityAITweakedWandering((EntityCreature) entity,
							moveSpeed));
		}
	}
}
