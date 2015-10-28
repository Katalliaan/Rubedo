package rubedo.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import rubedo.common.ContentAI;
import rubedo.common.ContentTools;
import rubedo.common.ContentWorld;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.common.materials.MaterialMultiItem.Blazerod;
import rubedo.common.materials.MaterialMultiItem.Bone;
import rubedo.common.materials.MaterialMultiItem.Gold;
import rubedo.common.materials.MaterialMultiItem.Iron;
import rubedo.common.materials.MaterialMultiItem.Silver;
import rubedo.common.materials.MaterialMultiItem.Wood;
import rubedo.items.ItemToolHead;
import rubedo.items.tools.ToolBase;
import rubedo.items.tools.ToolSword;
import rubedo.util.ReflectionHelper;
import rubedo.util.Singleton;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobEquipmentHandler {
	@SubscribeEvent
	public void onSpawn(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityMob
				&& event.entityLiving.getHeldItem() != null
				&& event.entityLiving.getHeldItem().getItem() instanceof ItemToolHead) {

			EntityMob entity = (EntityMob) event.entityLiving;

			float[] equipmentDropChances = (float[]) ReflectionHelper.getField(
					entity, "equipmentDropChances");

			// This is so you can't throw a tool head at a mob and get a tool
			// out of it
			if (equipmentDropChances[0] > 1.9F && entity.isNoDespawnRequired())
				return;

			ItemToolHead heldItem = (ItemToolHead) entity.getHeldItem()
					.getItem();

			ContentTools content = Singleton.getInstance(ContentTools.class);
			ContentAI contentAI = Singleton.getInstance(ContentAI.class);

			ToolBase kind = content.getItem(heldItem.getKind());

			MaterialMultiItem headMaterial = heldItem.getMaterial();
			MaterialMultiItem rodMaterial;
			MaterialMultiItem capMaterial;

			switch (headMaterial.type) {
			case METAL_ARCANE:
			case METAL_BRONZE:
				rodMaterial = content.getMaterial(Blazerod.class);
				capMaterial = content.getMaterial(Gold.class);
				break;
			case METAL_MUNDANE:
				rodMaterial = content.getMaterial(Bone.class);
				capMaterial = content.getMaterial(Iron.class);
				break;
			default:
				rodMaterial = content.getMaterial(Wood.class);
				capMaterial = content.getMaterial(Wood.class);
				break;
			}

			if (entity instanceof EntityPigZombie && contentAI.pigmenDropSilver
					&& heldItem == Items.golden_sword) {
				headMaterial = content.getMaterial(Silver.class);
			}

			ItemStack itemStack = kind.buildTool(headMaterial, rodMaterial,
					capMaterial);
			itemStack.stackSize = 1;

			entity.setCurrentItemOrArmor(0, itemStack);
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
