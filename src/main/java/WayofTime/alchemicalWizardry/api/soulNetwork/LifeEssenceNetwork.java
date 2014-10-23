package WayofTime.alchemicalWizardry.api.soulNetwork;

import net.minecraft.nbt.NBTTagCompound;

public class LifeEssenceNetwork extends net.minecraft.world.WorldSavedData {
	public int currentEssence;

	public LifeEssenceNetwork(String par1Str) {
		super(par1Str);
		this.currentEssence = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.currentEssence = nbttagcompound.getInteger("currentEssence");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("currentEssence", this.currentEssence);
	}
}
