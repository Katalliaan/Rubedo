package rubedo.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import rubedo.tileentity.TileEntityMagmaFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMagmaFurnace extends Container {

	public static final int SLOT_UNCOOKED = 0;
	public static final int SLOT_COOKED = 1;
	public static final int SLOT_EXTRA = 2;

	private TileEntityMagmaFurnace entity;
	private int lastSmeltTime;
	private int lastBurnTime;

	public ContainerMagmaFurnace(InventoryPlayer inventory, TileEntity entity) {
		if (entity instanceof TileEntityMagmaFurnace)
			this.entity = (TileEntityMagmaFurnace) entity;

		this.addSlotToContainer(new Slot((IInventory) entity, 0, 46, 35));
		this.addSlotToContainer(new SlotFurnace(inventory.player,
				(IInventory) entity, 1, 106, 35));
		this.addSlotToContainer(new SlotFurnace(inventory.player,
				(IInventory) entity, 2, 133, 35));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9,
						j * 18 + 8, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, i * 18 + 8, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity != null;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotID != 1 && slotID != 0) {
				if (this.entity.smeltResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (slotID >= 3 && slotID < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (slotID >= 30 && slotID < 39
						&& !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.entity.smeltTime = par2;
		}

		if (par1 == 1) {
			this.entity.burnTime = par2;
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastSmeltTime != this.entity.smeltTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.entity.smeltTime);
			}

			if (this.lastBurnTime != this.entity.burnTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.entity.burnTime);
			}
		}

		this.lastSmeltTime = this.entity.smeltTime;
		this.lastBurnTime = this.entity.burnTime;
	}
}
