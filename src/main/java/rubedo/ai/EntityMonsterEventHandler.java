package rubedo.ai;

import java.util.logging.Level;

import rubedo.RubedoCore;
import rubedo.common.Config;
import rubedo.common.ContentTools;
import rubedo.items.tools.ToolSword;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityMonsterEventHandler {
	@ForgeSubscribe
	public void entitySpawning(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityZombie)
		{
			if (event.entity instanceof EntityPigZombie)
			{
				ToolSword toolSword = new ToolSword(Config.getId("ToolSword"));
				
				ItemStack itemStack = toolSword.buildTool("gold", "blazerod", "gold");
				
				event.entity.setCurrentItemOrArmor(0, itemStack);
			}
		}
	}
}
