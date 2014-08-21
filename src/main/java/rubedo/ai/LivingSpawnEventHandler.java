package rubedo.ai;

import rubedo.RubedoCore;
import rubedo.common.Config;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.items.tools.ToolSword;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class LivingSpawnEventHandler {
	@ForgeSubscribe
	public void onSpawn(LivingSpawnEvent event) {
		if (event.entityLiving instanceof EntityPigZombie) {
						// if (event.entityLiving.getCurrentItemOrArmor(0).itemID ==
			// Item.swordGold.itemID) {
			//ItemStack itemStack = ContentTools.toolSword.buildTool("gold", "bone", "gold");
			//event.entityLiving.setCurrentItemOrArmor(0, itemStack);
			// }

			event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(ContentWorld.metalItems, 1, ContentWorld.metalItems.getTextureIndex("copper_ingot")));
			
			RubedoCore.logger.info(Integer.toString(event.entityLiving
					.getCurrentItemOrArmor(0).itemID));
		}
	}
}
