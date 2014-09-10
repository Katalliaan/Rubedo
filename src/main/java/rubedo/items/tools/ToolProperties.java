package rubedo.items.tools;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import rubedo.common.ContentTools;
import rubedo.items.MultiItemProperties;

public class ToolProperties extends MultiItemProperties<ToolBase> {
	public ToolProperties(ItemStack stack, ToolBase tool) {
		super(stack, tool);

		if (this.baseTags != null) {
			this.tag = this.baseTags.getCompoundTag("RubedoTool");
			this.baseTags.setCompoundTag("RubedoTool", this.tag);
		}
	}

	public boolean isBroken() {
		if (tag.hasKey("broken"))
			return tag.getBoolean("broken");
		else
			return false;
	}
	public void setBroken(boolean isBroken) {
		tag.setBoolean("broken", isBroken);
	}

	public String getHeadMaterial() {
		return tag.getString("head");
	}
	public void setHeadMaterial(String head) {
		tag.setString("head", head);
	}
	public String getRodMaterial() {
		return tag.getString("rod");
	}
	public void setRodMaterial(String rod) {
		tag.setString("rod", rod);
	}
	public String getCapMaterial() {
		return tag.getString("cap");
	}
	public void setCapMaterial(String cap) {
		tag.setString("cap", cap);
	}

	public void generateAttackDamageNBT() {
		NBTTagCompound nnbt = new NBTTagCompound();
		NBTTagList nnbtl = new NBTTagList();
		AttributeModifier att = new AttributeModifier("generic.attackDamage",
				getAttackDamage(), 0);
		nnbt.setLong("UUIDMost", att.getID().getMostSignificantBits());
		nnbt.setLong("UUIDLeast", att.getID().getLeastSignificantBits());
		nnbt.setString("Name", att.getName());
		nnbt.setDouble("Amount", att.getAmount());
		nnbt.setInteger("Operation", att.getOperation());
		nnbt.setString("AttributeName", att.getName());
		nnbtl.appendTag(nnbt);
		this.baseTags.setTag("AttributeModifiers", nnbtl);
	}

	public float getAttackDamage() {
		if (!isBroken())
			return item.getWeaponDamage()
					+ ContentTools.toolHeads.get(getHeadMaterial()).damage;
		else
			return 0;

	}

	public int getDurability() {
		int baseDur = ContentTools.toolHeads.get(getHeadMaterial()).durability;
		float modifier = ContentTools.toolRods.get(getRodMaterial()).modifier;
		return (int) (baseDur * modifier);
	}

	public int getMiningLevel() {
		if (!isBroken())
			return ContentTools.toolHeads.get(getHeadMaterial()).miningLevel;
		else
			return -1;
	}

	public int getSpecial() {
		return ContentTools.toolCaps.get(getCapMaterial()).special;
	}
}
