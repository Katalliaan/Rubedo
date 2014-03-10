package rubedo.items.tools;

import rubedo.common.ContentTools;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ToolProperties {	
	protected NBTTagCompound tag;
	protected NBTTagCompound baseTags;

	public ToolProperties(ItemStack stack) {
		this.baseTags = stack.getTagCompound();
		
		if (this.baseTags != null)
		{
			this.tag = this.baseTags.getCompoundTag("RubedoTool");
		}
	}
	
	public boolean isValid() { return tag != null; }
	
	public NBTTagCompound getTag() { return tag; }
	
	public boolean isBroken() { return tag.getBoolean("broken"); }
	public void setBroken(boolean isBroken) { tag.setBoolean("broken", isBroken); }

	public String getHeadMaterial() { return tag.getString("head");	}
	public void setHeadMaterial(String head) { tag.setString("head", head); }
	public String getRodMaterial() { return tag.getString("rod"); }
	public void setRodMaterial(String rod) { tag.setString("rod", rod); }
	public String getCapMaterial() { return tag.getString("cap"); }
	public void setCapMaterial(String cap) { tag.setString("cap", cap); }
	
	public int getDurability() {
		int baseDur = ContentTools.toolHeadMaterials.get(getHeadMaterial()).durability;
		float modifier = ContentTools.toolRodMaterials.get(getRodMaterial()).modifier;
		return  (int) (baseDur * modifier);
	}
	
	public String getName() { return baseTags.getCompoundTag("display").getString("Name"); }
	public void setName(String name) { baseTags.getCompoundTag("display").setString("Name", name); }
	
	public void resetName(String name) {
		String broken = "";
		if (isBroken()) {
			broken = "§4Broken ";
		}
		
		setName("§r" + broken +
				getHeadMaterial().substring(0, 1).toUpperCase() + getHeadMaterial().substring(1) + " " +
				name.substring(0, 1).toUpperCase() + name.substring(1));
	}
}
