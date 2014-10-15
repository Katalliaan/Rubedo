package rubedo.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import rubedo.blocks.behavior.BlockBehaviorCreator;
import rubedo.blocks.behavior.BlockBehaviorEntity;
import rubedo.tileentity.TileEntityMagmaFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagmaFurnace extends
		BlockBase<BlockBehaviorEntity<TileEntityMagmaFurnace>> {

	public BlockMagmaFurnace() {
		super(Material.rock, BlockBehaviorCreator //
				.fromTextures("magma_furnace") //
				.toFacing(BlockBehaviorCreator //
						.fromTextures("magma_furnace_front_off") //
						.toActivatable(BlockBehaviorCreator //
								.fromTextures("magma_furnace_front_on") //
								.select()) //
						.select()) //
				.toSided(
						new ForgeDirection[] { ForgeDirection.DOWN,
								ForgeDirection.UP }) //
				.addEntity(TileEntityMagmaFurnace.class) //
				.select());
		this.setBlockName("magma_furnace");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase placer, ItemStack stack) {
		int facing = MathHelper
				.floor_double(placer.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		facing = facing == 0 ? 3 : facing == 1 ? 4 : facing == 2 ? 2 : 5;
		world.setBlockMetadataWithNotify(x, y, z, facing * 2, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z,
			Random random) {
		if (true) {
			int meta = world.getBlockMetadata(x, y, z);
			float pX = x + 0.5F;
			float pY = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float pZ = z + 0.5F;
			float offset1 = 0.52F;
			float offset2 = random.nextFloat() * 0.6F - 0.3F;

			if (meta == 4) {
				world.spawnParticle("smoke", pX - offset1, pY, pZ + offset2, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("lava", pX - offset1, pY, pZ + offset2, 0.0D, 0.0D,
						0.0D);
			} else if (meta == 5) {
				world.spawnParticle("smoke", pX + offset1, pY, pZ + offset2, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("lava", pX + offset1, pY, pZ + offset2, 0.0D, 0.0D,
						0.0D);
			} else if (meta == 2) {
				world.spawnParticle("smoke", pX + offset2, pY, pZ - offset1, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("lava", pX + offset2, pY, pZ - offset1, 0.0D, 0.0D,
						0.0D);
			} else if (meta == 3) {
				world.spawnParticle("smoke", pX + offset2, pY, pZ + offset1, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("lava", pX + offset2, pY, pZ + offset1, 0.0D, 0.0D,
						0.0D);
			}
		}
	}
}
