package rubedo.ai;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.oredict.OreDictionary;

public class BreakEventHandler {
	@SubscribeEvent
	public void blockBroken(BreakEvent event) {	
		// TODO: replace stone check with check against OreDictionary
		ItemStack blockStack = new ItemStack(event.world.getBlock(event.x, event.y, event.z), 1, event.blockMetadata);
		 
		if (isStone(blockStack) && !event.getPlayer().capabilities.isCreativeMode && event.getPlayer().getHeldItem() == null)
		{
			EntityItem flint = new EntityItem(event.world, event.x, event.y, event.z, new ItemStack(Items.flint));
			event.world.spawnEntityInWorld(flint);
		}
	}
	
	public boolean isStone(ItemStack stack)
	{
		if (stack == null || stack.getItem() == null)
			return false;
		
		for (ItemStack oreStack : OreDictionary.getOres("stone"))
		{
			ItemStack copyStack = oreStack.copy();
			if (copyStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				copyStack.setItemDamage(stack.getItemDamage());
			
			if (stack.isItemEqual(copyStack))
				return true;
		}
		
		return false;
	}
}
