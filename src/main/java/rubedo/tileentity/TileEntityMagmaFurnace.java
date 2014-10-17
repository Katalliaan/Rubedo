package rubedo.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import rubedo.blocks.BlockMagmaFurnace;
import rubedo.common.ContentBlackSmith;
import rubedo.container.ContainerMagmaFurnace;
import rubedo.util.Singleton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMagmaFurnace extends TileEntityInventory implements
		ISidedInventory {

	private static final ContentBlackSmith content = Singleton
			.getInstance(ContentBlackSmith.class);

	protected static final int maxBurnTime = TileEntityFurnace
			.getItemBurnTime(new ItemStack(Items.lava_bucket));
	protected static final int maxSmeltTime = 100; // Furnace = 200

	public int burnTime;
	public int smeltTime;
	private boolean isBurning;

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int scale) {
		return this.smeltTime * scale / maxSmeltTime;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnProgressScaled(int scale) {
		return this.burnTime * scale / maxBurnTime;
	}

	public TileEntityMagmaFurnace() {
		super(3);
	}

	@Override
	public String getInventoryName() {
		return "rubedo.blocks.magma_furnace";
	}

	private void updateMeta(Block block, int meta) {
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
				this.zCoord, meta, 3);
		this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord,
				this.yCoord, this.zCoord);
		this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord,
				block);
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			boolean dirty = false;

			BlockMagmaFurnace block = (BlockMagmaFurnace) this.worldObj
					.getBlock(this.xCoord, this.yCoord, this.zCoord);
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord,
					this.zCoord);

			if (this.isBurning) {
				if (this.canSmelt()) {
					this.smeltTime++;
					this.burnTime--;

					if (this.smeltTime >= maxSmeltTime) {
						this.smeltTime = 0;
						this.smelt();
					}
					dirty = true;
				} else {
					this.smeltTime = 0;
				}

				if (this.burnTime <= 0) {
					this.isBurning = false;
					dirty = true;
				}
			} else if (this.eatLava(this.xCoord, this.yCoord - 1, this.zCoord)) {
				this.burnTime = maxBurnTime;
				this.isBurning = true;
			} else {
				this.smeltTime = 0;
			}

			if (this.isBurning && !block.isActive(meta)) {
				this.updateMeta(block, meta | 1);
				dirty = true;
			}

			if (!this.isBurning && block.isActive(meta)) {
				this.updateMeta(block, meta & 14);
				dirty = true;
			}

			if (dirty) {
				this.markDirty();
			}
		}
	}

	private boolean canSmelt() {
		if (this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED] == null) {
			return false;
		} else {
			ItemStack itemstack = this
					.smeltResult(this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED]);
			if (itemstack == null)
				return false;
			if (this.inventory[ContainerMagmaFurnace.SLOT_COOKED] == null)
				return true;
			if (!this.inventory[ContainerMagmaFurnace.SLOT_COOKED]
					.isItemEqual(itemstack))
				return false;
			int result = this.inventory[ContainerMagmaFurnace.SLOT_COOKED].stackSize
					+ itemstack.stackSize;
			return result <= this.getInventoryStackLimit()
					&& result <= this.inventory[ContainerMagmaFurnace.SLOT_COOKED]
							.getMaxStackSize();
		}
	}

	public ItemStack smeltResult(ItemStack stack) {
		ItemStack result = content.getMagmaSmelting().getSmeltingResult(stack);
		if (result == null) {
			result = FurnaceRecipes.smelting().getSmeltingResult(stack);
		}
		return result;
	}

	private ItemStack extraResult(ItemStack stack) {
		ItemStack result = content.getMagmaSmelting().getSmeltingExtra(stack);
		return result;
	}

	private void smelt() {
		ItemStack smelted = this
				.smeltResult(this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED]);
		ItemStack extra = this
				.extraResult(this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED]);

		if (this.inventory[ContainerMagmaFurnace.SLOT_COOKED] == null) {
			this.inventory[ContainerMagmaFurnace.SLOT_COOKED] = smelted.copy();
		} else if (this.inventory[ContainerMagmaFurnace.SLOT_COOKED].getItem() == smelted
				.getItem()) {
			this.inventory[ContainerMagmaFurnace.SLOT_COOKED].stackSize += smelted.stackSize;
		}

		if (extra != null
				&& this.inventory[ContainerMagmaFurnace.SLOT_EXTRA] == null) {
			this.inventory[ContainerMagmaFurnace.SLOT_EXTRA] = extra.copy();
		} else if (extra != null
				&& this.inventory[ContainerMagmaFurnace.SLOT_EXTRA].getItem() == extra
						.getItem()) {
			this.inventory[ContainerMagmaFurnace.SLOT_EXTRA].stackSize += smelted.stackSize;
		}

		--this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED].stackSize;

		if (this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED].stackSize <= 0) {
			this.inventory[ContainerMagmaFurnace.SLOT_UNCOOKED] = null;
		}
	}

	public boolean isLava(int x, int y, int z) {
		Block block = this.worldObj.getBlock(x, y, z);
		return block == Blocks.lava || block == Blocks.flowing_lava;
	}

	public boolean eatLava(int x, int y, int z) {
		int meta = this.worldObj.getBlockMetadata(x, y, z);
		if (this.isLava(x, y, z)) {
			if (meta == 0) {
				// source block
				this.worldObj.setBlock(x, y, z, Blocks.flowing_lava, 1, 3);
				return true;

			} else if ((meta & 8) > 0) {
				// vertical flow
				return this.eatLava(x, y + 1, z);
			} else {
				// check surrounding blocks
				for (int xOffset = -1; xOffset <= 1; xOffset++) {
					for (int zOffset = -1; zOffset <= 1; zOffset++) {

						if (xOffset == 0 && zOffset == 0)
							continue;

						if (this.isLava(x + xOffset, y, z + zOffset)) {
							int offsetMeta = this.worldObj.getBlockMetadata(x
									+ xOffset, y, z + zOffset);

							if ((offsetMeta & 8) > 0)
								offsetMeta = 0;

							// if lava level is higher than before
							if (offsetMeta < meta
									&& this.eatLava(x + xOffset, y, z + zOffset)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch (ForgeDirection.getOrientation(side)) {
		case UP:
			return new int[] { ContainerMagmaFurnace.SLOT_UNCOOKED };
		case DOWN:
			return new int[] {};
		default:
			return new int[] { ContainerMagmaFurnace.SLOT_COOKED };
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == ContainerMagmaFurnace.SLOT_COOKED ? false
				: slot == ContainerMagmaFurnace.SLOT_EXTRA ? false : true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		switch (ForgeDirection.getOrientation(side)) {
		case UP:
			return slot == ContainerMagmaFurnace.SLOT_UNCOOKED;
		case DOWN:
			return false;
		default:
			return slot == ContainerMagmaFurnace.SLOT_COOKED;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("smeltTime", this.smeltTime);

		nbt.setBoolean("isBurning", this.isBurning);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.burnTime = nbt.getInteger("burnTime");
		this.smeltTime = nbt.getInteger("smeltTime");

		this.isBurning = nbt.getBoolean("isBurning");
	}
}
