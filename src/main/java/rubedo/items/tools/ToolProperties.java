package rubedo.items.tools;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import rubedo.common.ContentTools;

public class ToolProperties {	
	protected NBTTagCompound tag;
	protected NBTTagCompound baseTags;
	protected ItemStack stack;
	protected ToolBase tool;

	public ToolProperties(ItemStack stack, ToolBase tool) {
		this.baseTags = stack.getTagCompound();
		this.stack = stack;
		this.tool = tool;
		
		if (this.baseTags != null)
		{
			this.tag = this.baseTags.getCompoundTag("RubedoTool");
		}
	}
	
	public boolean isValid() { return tag != null; }
	
	public NBTTagCompound getTag() { return tag; }
	public ItemStack getStack() { return stack; }
	public ToolBase getTool() { return tool; }
	
	public boolean isBroken() { return tag.getBoolean("broken"); }
	public void setBroken(boolean isBroken) { tag.setBoolean("broken", isBroken); }

	public String getHeadMaterial() { return tag.getString("head");	}
	public void setHeadMaterial(String head) { tag.setString("head", head); }
	public String getRodMaterial() { return tag.getString("rod"); }
	public void setRodMaterial(String rod) { tag.setString("rod", rod); }
	public String getCapMaterial() { return tag.getString("cap"); }
	public void setCapMaterial(String cap) { tag.setString("cap", cap); }
	
	public void generateAttackDamageNBT() {
		NBTTagCompound nnbt = new NBTTagCompound();
		NBTTagList nnbtl = new NBTTagList();
		AttributeModifier att = new AttributeModifier("generic.attackDamage", getAttackDamage(), 0);
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
		return tool.getWeaponDamage() + ContentTools.toolHeadMaterials.get(getHeadMaterial()).damage;
	}
	
	public int getDurability() {
		int baseDur = ContentTools.toolHeadMaterials.get(getHeadMaterial()).durability;
		float modifier = ContentTools.toolRodMaterials.get(getRodMaterial()).modifier;
		return  (int) (baseDur * modifier);
	}
}
