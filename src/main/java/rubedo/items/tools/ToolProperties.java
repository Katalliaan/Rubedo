package rubedo.items.tools;

import rubedo.common.ContentTools;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ToolProperties {
	protected String headMaterial;
	protected String rodMaterial;
	protected String capMaterial;

	public ToolProperties(ItemStack stack) {
		//TODO better error checking?
		NBTTagCompound tags = stack.getTagCompound();
		
		if (tags != null)
		{
			tags = tags.getCompoundTag("RubedoTool");
			this.headMaterial = tags.getString("head");
			this.rodMaterial = tags.getString("rod");
			this.capMaterial = tags.getString("cap");
		}
	}

	public String getHeadMaterial() {
		return headMaterial;
	}

	public String getRodMaterial() {
		return rodMaterial;
	}

	public String getCapMaterial() {
		return capMaterial;
	}
	
	public int getDurability() {
		int baseDur = ContentTools.toolHeadMaterials.get(headMaterial).durability;
		float modifier = ContentTools.toolRodMaterials.get(rodMaterial).modifier;
		return  (int) (baseDur * modifier);
	}
}
