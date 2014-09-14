package rubedo.ai;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import rubedo.common.ContentWorld;

// TODO: find a way to fire this after mobs get their equipment
public class LivingSpawnEventHandler {
	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent event) {
		if (event.entityLiving instanceof EntityPigZombie) {
						// if (event.entityLiving.getCurrentItemOrArmor(0).itemID ==
			// Item.swordGold.itemID) {
			//ItemStack itemStack = ContentTools.toolSword.buildTool("gold", "bone", "gold");
			//event.entityLiving.setCurrentItemOrArmor(0, itemStack);
			// }

			event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(ContentWorld.metalItems, 1, ContentWorld.metalItems.getTextureIndex("copper_ingot")));
		}
	}
}
