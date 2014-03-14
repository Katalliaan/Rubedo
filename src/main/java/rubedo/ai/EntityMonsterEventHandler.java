package rubedo.ai;

import java.util.logging.Level;

import rubedo.RubedoCore;
import rubedo.common.Config;
import rubedo.common.ContentTools;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolProperties;
import rubedo.items.tools.ToolSword;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityMonsterEventHandler {
	@ForgeSubscribe
	public void entitySpawning(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityZombie) {
			if (event.entity instanceof EntityPigZombie) {

				//ItemStack stack = ContentTools.toolSword.buildTool(new ItemStack(ContentTools.toolSword, 1, 0), "gold","bone", "gold");
				
				ItemStack stack = ContentTools.toolSword.buildTool("gold", "bone", "gold");

				event.entity.setCurrentItemOrArmor(0, stack);
			}
		}
	}
}
