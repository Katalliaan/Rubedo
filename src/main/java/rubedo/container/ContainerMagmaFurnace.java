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
		this.addSlotToContainer(new Slot((IInventory) entity, 2, 133, 35));

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
			ItemStack stackSlot = slot.getStack();
			itemstack = stackSlot.copy();

			if (slotID >= 36 && slotID <= 38) {
				if (!this.mergeItemStack(stackSlot, 27, 36, false)) {
					if (!this.mergeItemStack(stackSlot, 0, 27, false))
						return null;
				}
				slot.onSlotChange(stackSlot, itemstack);
			}
			if (slotID < 36) {
				if (!this.mergeItemStack(stackSlot, 36, 37, false))
					return null;

				slot.onSlotChange(stackSlot, itemstack);
			}

			if (stackSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (stackSlot.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackSlot);
			if (stackSlot.stackSize == 0) {
				slot.putStack(null);
				return null;
			}
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
