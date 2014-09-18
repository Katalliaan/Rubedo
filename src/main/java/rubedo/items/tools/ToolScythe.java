package rubedo.items.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import rubedo.common.ContentTools;
import rubedo.raycast.IShapedRayCast;
import rubedo.raycast.ShapedRayCast;
import rubedo.raycast.SphericalRayCast;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ToolScythe extends ToolBase {

	private Item vanillaEquivalent;

	public ToolScythe() {
		super();

		this.vanillaEquivalent = new ItemHoe(ToolMaterial.EMERALD) {
		};
		this.vanillaEquivalent.setUnlocalizedName("hoeDiamond").setTextureName("diamond_hoe");
		GameRegistry.registerItem(this.vanillaEquivalent, "dummy_hoe");
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

			@SuppressWarnings("unchecked")
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
									-MathHelper.sin(attackingEntity.rotationYaw
											* (float) Math.PI / 180.0F)
											* knockbackLevel * 0.5F,
											0.1D,
											MathHelper.cos(attackingEntity.rotationYaw
													* (float) Math.PI / 180.0F)
													* knockbackLevel * 0.5F);
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
		return new Material[] { Material.vine, Material.leaves };
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		Integer[] allowedEnchants = new Integer[] {
				Enchantment.efficiency.effectId, Enchantment.fortune.effectId,
				Enchantment.unbreaking.effectId,

				Enchantment.sharpness.effectId, Enchantment.smite.effectId,
				Enchantment.baneOfArthropods.effectId,
				Enchantment.knockback.effectId,
				Enchantment.fireAspect.effectId, Enchantment.looting.effectId };
		return Arrays.asList(allowedEnchants);
	}

	@Override
	public ItemStack buildTool(rubedo.common.materials.MaterialMultiItem head,
			rubedo.common.materials.MaterialMultiItem rod,
			rubedo.common.materials.MaterialMultiItem cap) {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolScythe.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	@Override
	protected Item getEquivalentTool() {
		return this.vanillaEquivalent;
	}
}
