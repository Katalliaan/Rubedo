package rubedo.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import rubedo.RubedoCore;
import rubedo.blocks.behavior.BlockBehaviorCreator;
import rubedo.blocks.behavior.BlockBehaviorEntity;
import rubedo.client.gui.GuiHandler;
import rubedo.tileentity.TileEntityMagmaFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagmaFurnace extends
		BlockBase<BlockBehaviorEntity<TileEntityMagmaFurnace>> {

	public BlockMagmaFurnace() {
		super(Material.rock, BlockBehaviorCreator //
				.fromTextures("magma_furnace") //
				.toSided(
						new ForgeDirection[] { ForgeDirection.DOWN,
								ForgeDirection.UP }) //
				.toFacing(BlockBehaviorCreator //
						.fromTextures("magma_furnace_front_off") //
						.toActivatable(BlockBehaviorCreator //
								.fromTextures("magma_furnace_front_on") //
								.select()) //
						.select()) //
				.addEntity(TileEntityMagmaFurnace.class) //
				.select());
		this.setBlockName("magma_furnace");
	}

	public boolean isActive(int meta) {
		return (meta % 2) > 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof TileEntityMagmaFurnace) {
			player.openGui(RubedoCore.instance,
					GuiHandler.Types.MAGMA_FURNACE.ordinal(), world, x, y, z);
			return true;
		}

		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY,
				hitZ);
	}

	@Override
	public int damageDropped(int meta) {
		return ForgeDirection.SOUTH.ordinal() * 2;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase placer, ItemStack stack) {
		int facing = MathHelper
				.floor_double(placer.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		facing = facing == 0 ? ForgeDirection.SOUTH.ordinal() //
				: facing == 1 ? ForgeDirection.WEST.ordinal() //
						: facing == 2 ? ForgeDirection.SOUTH.ordinal() //
								: ForgeDirection.EAST.ordinal();
		world.setBlockMetadataWithNotify(x, y, z, facing * 2, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z,
			Random random) {

		int meta = world.getBlockMetadata(x, y, z);

		if (this.isActive(meta)) {

			float pX = x + 0.5F;
			float pY = y + 0.1F + random.nextFloat() * 6.0F / 16.0F;
			float pZ = z + 0.5F;
			float offset1 = 0.52F;
			float offset2 = random.nextFloat() * 0.6F - 0.3F;

			ForgeDirection facing = this.getFacing(meta);

			world.spawnParticle("lava", pX, pY, pZ, 0.0D, 0.0D, 0.0D);

			pX += (offset1 * facing.offsetX)
					+ (offset2 * Math.abs(facing.offsetZ));
			pZ += (offset1 * facing.offsetZ)
					+ (offset2 * Math.abs(facing.offsetX));

			world.spawnParticle("smoke", pX, pY, pZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", pX, pY, pZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return this.isActive(meta) ? 15 : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, ForgeDirection.SOUTH.ordinal() * 2));
	}
}
