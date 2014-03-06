package rubedo.items.spells;

import rubedo.common.ContentSpells;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SpellProperties {
	protected String baseMaterial;
	protected String focusMaterial;
	protected String effectMaterial;

	public SpellProperties(ItemStack stack) {
		//TODO better error checking?
		NBTTagCompound tags = stack.getTagCompound();
		
		if (tags != null)
		{
			tags = tags.getCompoundTag("RubedoSpell");
			this.baseMaterial = tags.getString("base");
			this.focusMaterial = tags.getString("focus");
			this.effectMaterial = tags.getString("effect");
		}
	}

	public String getBaseMaterial() {
		return baseMaterial;
	}

	public String getFocusMaterial() {
		return focusMaterial;
	}

	public String getEffectMaterial() {
		return effectMaterial;
	}
}
