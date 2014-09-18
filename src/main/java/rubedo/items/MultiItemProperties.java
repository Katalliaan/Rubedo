package rubedo.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class MultiItemProperties<I extends MultiItem> {
	protected NBTTagCompound tag;
	protected NBTTagCompound baseTags;
	protected ItemStack stack;
	protected I item;

	public MultiItemProperties(ItemStack stack, I item) {
		if (stack.getItem() instanceof MultiItem) {
			this.baseTags = stack.getTagCompound();
			this.stack = stack;
			this.item = item;
		} else
			throw new RuntimeException(
					"Passed the wrong stack type to properties");
	}

	public boolean isValid() {
		return tag != null;
	}

	public NBTTagCompound getTag() {
		return tag;
	}

	public ItemStack getStack() {
		return stack;
	}

	public I getItem() {
		return item;
	}
}
