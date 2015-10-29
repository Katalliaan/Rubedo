package rubedo.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class makeGravelEvent {

	@SubscribeEvent
	public void returnGravelBowl(ItemCraftedEvent event)
	{
		if(event.crafting.getItem().equals(Items.flint))
		{
			final int sizeInventory = event.craftMatrix.getSizeInventory();
			for (int i=0; i<sizeInventory;i++) {
				ItemStack thisSlotStack = event.craftMatrix.getStackInSlot(i);
				if(thisSlotStack != null) {
					if(thisSlotStack.getItem().equals(Items.bowl)) {
						event.craftMatrix.setInventorySlotContents(i, new ItemStack(Items.bowl,event.craftMatrix.getStackInSlot(i).stackSize+1));
						break;
					}
				}
			}
		}
	}

}
