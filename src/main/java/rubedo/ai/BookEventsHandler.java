package rubedo.ai;

import amerifrance.guideapi.api.GuideRegistry;
import rubedo.CommonProxy;
import rubedo.RubedoCore;
import rubedo.common.ContentBook;
import rubedo.common.ContentTools;
import rubedo.items.tools.ToolBase;
import rubedo.playerstats.RubedoStats;
import rubedo.util.Singleton;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class BookEventsHandler {
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		if (event.crafting.getItem() instanceof ToolBase) {
			ToolBase tool = (ToolBase) event.crafting.getItem();
			if (!event.player.worldObj.isRemote) {
				RubedoStats stats = RubedoStats.get(event.player);

				if (!stats.rubedoGuide) {
					stats.rubedoGuide = true;

					ContentBook contentBook = Singleton
							.getInstance(ContentBook.class);

					EntityItem guide = new EntityItem(
							event.player.worldObj,
							event.player.posX,
							event.player.posY,
							event.player.posZ,
							GuideRegistry
									.getItemStackForBook(contentBook.rubedoGuide));
					event.player.worldObj.spawnEntityInWorld(guide);
				}
			}
		}
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			NBTTagCompound playerData = CommonProxy
					.getEntityData(((EntityPlayer) event.entity)
							.getDisplayName());
			if (playerData != null) {
				event.entity.getExtendedProperties(RubedoStats.PROP_NAME)
						.loadNBTData(playerData);
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer
				&& RubedoStats.get((EntityPlayer) event.entity) == null) {
			RubedoStats.register((EntityPlayer) event.entity);
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (!event.entity.worldObj.isRemote
				&& event.entity instanceof EntityPlayer) {
			RubedoStats stats = RubedoStats.get((EntityPlayer) event.entity);

			NBTTagCompound playerData = new NBTTagCompound();

			event.entity.getExtendedProperties(RubedoStats.PROP_NAME)
					.saveNBTData(playerData);

			CommonProxy.storeEntityData(
					((EntityPlayer) event.entity).getDisplayName(), playerData);
		}
	}
}
