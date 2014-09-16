package rubedo.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.items.tools.ToolSword;
import rubedo.util.Singleton;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LivingDropsEventHandler {
	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {
		if (event.entity instanceof EntityPigZombie) {
			for (EntityItem drop : event.drops) {
				if (drop.getEntityItem().getItem() == Items.gold_ingot)
					drop.setEntityItemStack(new ItemStack(ContentWorld.metalItems, drop.getEntityItem().stackSize, ContentWorld.metalItems.getTextureIndex("silver_ingot")));
				if (drop.getEntityItem().getItem() == Items.gold_nugget)
					drop.setEntityItemStack(new ItemStack(ContentWorld.metalItems, drop.getEntityItem().stackSize, ContentWorld.metalItems.getTextureIndex("silver_nugget")));
				
				if (drop.getEntityItem().getItem() == Singleton.getInstance(ContentTools.class).getItem(ToolSword.class))
				{
					drop.getEntityItem().setItemDamage(0);
				}
			}
		}
	}
}
