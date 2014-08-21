package rubedo.ai;

import rubedo.common.Config;
import rubedo.common.ContentWorld;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class LivingDropsEventHandler {
	@ForgeSubscribe
	public void onEntityDrop(LivingDropsEvent event) {
		if (event.entity instanceof EntityPigZombie) {
			for (EntityItem drop : event.drops) {
				if (drop.getEntityItem().itemID == Item.ingotGold.itemID)
					drop.setEntityItemStack(new ItemStack(ContentWorld.metalItems, drop.getEntityItem().stackSize, ContentWorld.metalItems.getTextureIndex("silver_ingot")));
				if (drop.getEntityItem().itemID == Item.goldNugget.itemID)
					drop.setEntityItemStack(new ItemStack(ContentWorld.metalItems, drop.getEntityItem().stackSize, ContentWorld.metalItems.getTextureIndex("silver_nugget")));
				if (drop.getEntityItem().itemID == Config.getId("ToolSword"))
				{
					drop.getEntityItem().setItemDamage(0);
				}
			}
		}
	}
}
