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

		if (stack.getItem() instanceof ToolBase 
				&& this.baseTags != null && this.baseTags.hasKey("RubedoTool")) {
			this.tag = this.baseTags.getCompoundTag("RubedoTool");
		}
	}

	public boolean isBroken() {
		return !isValid() || tag.getBoolean("broken");
	}
	public void setBroken(boolean isBroken) {
		if (isValid()) {
			tag.setBoolean("broken", isBroken);
			generateAttackDamageNBT();
		}
	}

	public String getHeadMaterial() {
		if (isValid())
			return tag.getString("head");
		else
			return "";
	}
	public void setHeadMaterial(String head) {
		if (isValid())
			tag.setString("head", head);
	}
	public String getRodMaterial() {
		if (isValid())
			return tag.getString("rod");
		else
			return "";
	}
	public void setRodMaterial(String rod) {
		if (isValid())
			tag.setString("rod", rod);
	}
	public String getCapMaterial() {
		if (isValid())
			return tag.getString("cap");
		else
			return "";
	}
	public void setCapMaterial(String cap) {
		if (isValid())
			tag.setString("cap", cap);
	}

	public void generateAttackDamageNBT() {
		if (isValid()) {
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
	}

	public float getAttackDamage() {
		if (!isBroken())
			return item.getWeaponDamage()
					+ ContentTools.toolHeads.get(getHeadMaterial()).damage;
		else
			return 0;

	}

	public int getDurability() {
		if (isValid()) {
			int baseDur = ContentTools.toolHeads.get(getHeadMaterial()).durability;
			float modifier = ContentTools.toolRods.get(getRodMaterial()).modifier;
			return (int) (baseDur * modifier);
		}
		return 1;
	}

	public int getMiningLevel() {
		if (isValid() && !isBroken())
			return ContentTools.toolHeads.get(getHeadMaterial()).miningLevel;
		else
			return -1;
	}

	public int getSpecial() {
		if (isValid())
			return ContentTools.toolCaps.get(getCapMaterial()).special;
		else
			return 0;
	}
}
