package rubedo.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import rubedo.RubedoCore;
import rubedo.blocks.behavior.IBlockBehavior;
import rubedo.blocks.behavior.IBlockBehaviorEntity;
import rubedo.tileentity.TileEntityInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase<TBehavior extends IBlockBehavior> extends Block
		implements ITileEntityProvider {
	protected TBehavior behavior;

	public BlockBase(Material material, TBehavior behavior) {
		super(material);
		this.behavior = behavior;
		this.setCreativeTab(RubedoCore.creativeTab);
	}

	public TBehavior getBehavior() {
		return this.behavior;
	}

	public ForgeDirection getFacing(int meta) {
		return this.behavior.getFacing(meta);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for (String texture : this.behavior.getTextures()) {
			this.behavior
					.setIcon(
							texture,
							iconRegister.registerIcon(RubedoCore.modid + ":"
									+ texture));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.behavior.getIcon(side, meta);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.addAll(this.behavior.getSubBlocks(item));
	}

	// Tile Entity stuff

	@Override
	public boolean hasTileEntity(int metadata) {
		return this.behavior instanceof IBlockBehaviorEntity;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (this.behavior instanceof IBlockBehaviorEntity)
			return this.createNewTileEntity(world, metadata);
		else
			return super.createTileEntity(world, metadata);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		if (this.behavior instanceof IBlockBehaviorEntity)
			return ((IBlockBehaviorEntity<? extends TileEntity>) this.behavior)
					.createTileEntity(world, metadata);
		else
			return super.createTileEntity(world, metadata);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity != null && tileEntity instanceof TileEntityInventory
				&& !world.isRemote) {
			TileEntityInventory inventory = (TileEntityInventory) tileEntity;
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlotOnClosing(i);

				if (stack != null) {
					float spawnX = x + world.rand.nextFloat() - 0.5f;
					float spawnY = y + world.rand.nextFloat() - 0.5f;
					float spawnZ = z + world.rand.nextFloat() - 0.5f;

					EntityItem entity = new EntityItem(world, spawnX, spawnY,
							spawnZ, stack);

					float mult = .05F;

					entity.motionX = (-.5F + world.rand.nextFloat()) * mult;
					entity.motionY = (4F + world.rand.nextFloat()) * mult;
					entity.motionZ = (-.5F + world.rand.nextFloat()) * mult;

					world.spawnEntityInWorld(entity);
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
