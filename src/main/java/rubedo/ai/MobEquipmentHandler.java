package rubedo.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import rubedo.common.ContentAI;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.common.materials.MaterialMultiItem.Bone;
import rubedo.common.materials.MaterialMultiItem.Flint;
import rubedo.common.materials.MaterialMultiItem.Gold;
import rubedo.common.materials.MaterialMultiItem.Iron;
import rubedo.common.materials.MaterialMultiItem.Silver;
import rubedo.common.materials.MaterialMultiItem.Wood;
import rubedo.items.tools.ToolAxe;
import rubedo.items.tools.ToolPickaxe;
import rubedo.items.tools.ToolScythe;
import rubedo.items.tools.ToolShovel;
import rubedo.items.tools.ToolSword;
import rubedo.util.Singleton;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobEquipmentHandler {
	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent event) {
		ContentTools content = Singleton.getInstance(ContentTools.class);
		ContentAI contentAI = Singleton.getInstance(ContentAI.class);
		ToolSword sword = content.getItem(ToolSword.class);
		ToolAxe axe = content.getItem(ToolAxe.class);
		ToolShovel shovel = content.getItem(ToolShovel.class);
		ToolPickaxe pick = content.getItem(ToolPickaxe.class);
		ToolScythe scythe = content.getItem(ToolScythe.class);

		Item heldItem = event.entityLiving.getHeldItem() != null ? event.entityLiving
				.getHeldItem().getItem() : null;

		if (heldItem == Items.golden_sword) {
			MaterialMultiItem headMaterial;
			if (contentAI.pigmenDropSilver
					&& event.entity instanceof EntityPigZombie) {
				headMaterial = content.getMaterial(Silver.class);
			} else {
				headMaterial = content.getMaterial(Gold.class);
			}
			ItemStack itemStack = sword.buildTool(headMaterial,
					content.getMaterial(Bone.class),
					content.getMaterial(Gold.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.iron_sword) {
			ItemStack itemStack = sword.buildTool(
					content.getMaterial(Iron.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Iron.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.iron_axe) {
			ItemStack itemStack = axe.buildTool(
					content.getMaterial(Iron.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Iron.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.iron_shovel) {
			ItemStack itemStack = shovel.buildTool(
					content.getMaterial(Iron.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Iron.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.iron_pickaxe) {
			ItemStack itemStack = pick.buildTool(
					content.getMaterial(Iron.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Iron.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.iron_hoe) {
			ItemStack itemStack = scythe.buildTool(
					content.getMaterial(Iron.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Iron.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}

		if (heldItem == Items.stone_sword) {
			ItemStack itemStack = sword.buildTool(
					content.getMaterial(Flint.class),
					content.getMaterial(Bone.class),
					content.getMaterial(Wood.class));
			itemStack.stackSize = 1;

			event.entity.setCurrentItemOrArmor(0, itemStack);
		}
	}

	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {
		ContentAI contentAI = Singleton.getInstance(ContentAI.class);
		if (contentAI.pigmenDropSilver
				&& event.entity instanceof EntityPigZombie) {
			for (EntityItem drop : event.drops) {
				if (drop.getEntityItem().getItem() == Items.gold_ingot)
					drop.setEntityItemStack(new ItemStack(
							ContentWorld.metalItems,
							drop.getEntityItem().stackSize,
							ContentWorld.metalItems
									.getTextureIndex("silver_ingot")));
				if (drop.getEntityItem().getItem() == Items.gold_nugget)
					drop.setEntityItemStack(new ItemStack(
							ContentWorld.metalItems,
							drop.getEntityItem().stackSize,
							ContentWorld.metalItems
									.getTextureIndex("silver_nugget")));

				if (drop.getEntityItem().getItem() == Singleton.getInstance(
						ContentTools.class).getItem(ToolSword.class)) {
					drop.getEntityItem().setItemDamage(0);
				}
			}
		}
	}
}
