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
		if (event.block == Blocks.stone && !event.getPlayer().capabilities.isCreativeMode && event.getPlayer().getHeldItem() == null)
		{
			EntityItem flint = new EntityItem(event.world, event.x, event.y, event.z, new ItemStack(Items.flint));
			event.world.spawnEntityInWorld(flint);
		}
	}
}
