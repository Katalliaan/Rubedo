package rubedo.items.tools;

import java.util.List;
import java.util.Set;

import rubedo.common.ContentTools;
import rubedo.raycast.IShapedRayCast;
import rubedo.raycast.ShapedRayCast;
import rubedo.raycast.SphericalRayCast;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

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

			for (Entity entity : rayCaster
					.getEntitiesExcludingEntity(attackingEntity)) {
				if (entity instanceof EntityLivingBase) {
					if (!entity.equals(hitEntity)) {
						entity.attackEntityFrom(
								DamageSource
										.causePlayerDamage((EntityPlayer) attackingEntity),
								this.getToolProperties(stack).getAttackDamage());
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
		// TODO Auto-generated method stub
		return null;
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

			if (event.getResult() == Result.ALLOW) {
				ToolProperties properties = this.getToolProperties(itemStack);
				ToolUtil.damageTool(properties, entityPlayer, properties
						.getItem().getItemDamageOnBreak());
				return true;
			}

			int blockId = world.getBlockId(xCoord, yCoord, zCoord);
			boolean airAbove = world.isAirBlock(xCoord, yCoord + 1, zCoord);

			if (par7 != 0
					&& airAbove
					&& (blockId == Block.grass.blockID || blockId == Block.dirt.blockID)) {
				world.playSoundEffect(
						(double) ((float) xCoord + 0.5F),
						(double) ((float) yCoord + 0.5F),
						(double) ((float) zCoord + 0.5F),
						Block.tilledField.stepSound.getStepSound(),
						(Block.tilledField.stepSound.getVolume() + 1.0F) / 2.0F,
						Block.tilledField.stepSound.getPitch() * 0.8F);

				if (world.isRemote) {
					return true;
				} else {
					world.setBlock(xCoord, yCoord, zCoord,
							Block.tilledField.blockID);
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
		ItemStack tool = new ItemStack(ContentTools.toolScythe);

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

}
