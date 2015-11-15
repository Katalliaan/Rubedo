package rubedo.playerstats;

import java.lang.ref.WeakReference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class RubedoStats implements IExtendedEntityProperties {
	
	public static final String PROP_NAME = "RubedoCore";
	
	public boolean rubedoGuide;
	
	public RubedoStats() {
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound rubedoTag = new NBTTagCompound();
		rubedoTag.setBoolean("rubedoGuide", this.rubedoGuide);
		compound.setTag(PROP_NAME, rubedoTag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(PROP_NAME);
		
		this.rubedoGuide = properties.getBoolean("rubedoGuide");
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
	public static final RubedoStats get(EntityPlayer player) {
		return (RubedoStats) player.getExtendedProperties(PROP_NAME);
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(PROP_NAME, new RubedoStats());
	}
}
