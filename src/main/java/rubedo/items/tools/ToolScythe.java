package rubedo.items.tools;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.raycast.IShapedRayCast;
import rubedo.raycast.ShapedRayCast;
import rubedo.raycast.SphericalRayCast;

public class ToolScythe extends ToolBase {

	public ToolScythe(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "scythe";
	}

	@Override
	public float getWeaponDamage() {
		return 2.0F;
	}

	@Override
	public int getItemDamageOnHit() {
		return 1;
	}

	@Override
	public int getItemDamageOnBreak() {
		return 1;
	}

	@Override
	public float getEffectiveBlockSpeed() {
		return 2.0F;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase hitEntity,
			EntityLivingBase attackingEntity) {
		ToolProperties properties = this.getToolProperties(stack);

		if (attackingEntity instanceof EntityPlayer && !properties.isBroken()) {
			// get the camera direction
			Vec3 direction = ShapedRayCast.eulerToVec(attackingEntity.worldObj,
					attackingEntity.rotationPitch, attackingEntity.rotationYaw);

			// create a new raycaster
			IShapedRayCast rayCaster = new SphericalRayCast(
					attackingEntity.worldObj, hitEntity.posX, hitEntity.posY,
					hitEntity.posZ, direction.xCoord, direction.yCoord,
					direction.zCoord, 4);

			int mobsHit = 0;

			Map<Integer, Integer> enchants = EnchantmentHelper
					.getEnchantments(stack);
			int smiteLevel = 0;
			int sharpnessLevel = 0;
			int baneLevel = 0;
			int fireAspectLevel = 0;
			int knockbackLevel = 0;

			for (Entry<Integer, Integer> entry : enchants.entrySet()) {
				if (entry.getKey() == Enchantment.smite.effectId)
					smiteLevel = entry.getValue() / 2;
				if (entry.getKey() == Enchantment.sharpness.effectId)
					sharpnessLevel = entry.getValue() / 2;
				if (entry.getKey() == Enchantment.baneOfArthropods.effectId)
					baneLevel = entry.getValue() / 2;
				if (entry.getKey() == Enchantment.fireAspect.effectId)
					fireAspectLevel = entry.getValue() / 2;
				if (entry.getKey() == Enchantment.knockback.effectId)
					knockbackLevel = entry.getValue() / 2;
			}

			for (Entity entity : rayCaster
					.getEntitiesExcludingEntity(attackingEntity)) {
				if (entity instanceof EntityLivingBase
						&& mobsHit < properties.getSpecial()) {
					if (!entity.equals(hitEntity)) {
						float attackDamage = this.getToolProperties(stack)
								.getAttackDamage();

						attackDamage += Enchantment.smite.func_152376_a(
								smiteLevel, hitEntity.getCreatureAttribute());
						attackDamage += Enchantment.sharpness.func_152376_a(
								sharpnessLevel,
								hitEntity.getCreatureAttribute());
						attackDamage += Enchantment.baneOfArthropods
								.func_152376_a(baneLevel,
										hitEntity.getCreatureAttribute());
						entity.setFire(fireAspectLevel * 4);
						if (knockbackLevel > 0) {
							entity.addVelocity(
									(double) (-MathHelper
											.sin(attackingEntity.rotationYaw
													* (float) Math.PI / 180.0F)
											* (float) knockbackLevel * 0.5F),
									0.1D,
									(double) (MathHelper
											.cos(attackingEntity.rotationYaw
													* (float) Math.PI / 180.0F)
											* (float) knockbackLevel * 0.5F));
							attackingEntity.motionX *= 0.6D;
							attackingEntity.motionZ *= 0.6D;
						}

						entity.attackEntityFrom(
								DamageSource
										.causePlayerDamage((EntityPlayer) attackingEntity),
								attackDamage);

						mobsHit++;
					}
					super.hitEntity(stack, (EntityLivingBase) entity,
							attackingEntity);
				}
			}
		} else
			super.hitEntity(stack, hitEntity, attackingEntity);

		return false;
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[]{Material.vine, Material.leaves};
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		Integer[] allowedEnchants = new Integer[]{
				Enchantment.efficiency.effectId, Enchantment.fortune.effectId,
				Enchantment.unbreaking.effectId,

				Enchantment.sharpness.effectId, Enchantment.smite.effectId,
				Enchantment.baneOfArthropods.effectId,
				Enchantment.knockback.effectId,
				Enchantment.fireAspect.effectId, Enchantment.looting.effectId};
		return Arrays.asList(allowedEnchants);
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int xCoord, int yCoord, int zCoord, int par7,
			float par8, float par9, float par10) {
		if (!entityPlayer
				.canPlayerEdit(xCoord, yCoord, zCoord, par7, itemStack)) {
			return false;
		} else {
			UseHoeEvent event = new UseHoeEvent(entityPlayer, itemStack, world,
					xCoord, yCoord, zCoord);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return false;
			}

			if (event.getResult() == Event.Result.ALLOW) {
				ToolProperties properties = this.getToolProperties(itemStack);
				ToolUtil.damageTool(properties, entityPlayer, properties
						.getItem().getItemDamageOnBreak());
				return true;
			}

			Block block = world.getBlock(xCoord, yCoord, zCoord);
			boolean airAbove = world.isAirBlock(xCoord, yCoord + 1, zCoord);

			if (par7 != 0
					&& airAbove
					&& (block == Blocks.grass || block == Blocks.dirt)) {
				world.playSoundEffect((double) ((float) xCoord + 0.5F),
						(double) ((float) yCoord + 0.5F),
						(double) ((float) zCoord + 0.5F),
						Blocks.farmland.stepSound.soundName,
						(Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F,
						Blocks.farmland.stepSound.getPitch() * 0.8F);

				if (world.isRemote) {
					return true;
				} else {
					world.setBlock(xCoord, yCoord, zCoord, Blocks.farmland);
					ToolProperties properties = this
							.getToolProperties(itemStack);
					ToolUtil.damageTool(properties, entityPlayer, properties
							.getItem().getItemDamageOnBreak());
					return true;
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ContentTools contentTools = (ContentTools) RubedoCore.contentUnits
				.get(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolScythe.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}
}
