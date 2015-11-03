package rubedo.items.tools;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import rubedo.common.ContentTools;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.common.materials.MaterialMultiItem.MaterialType;
import rubedo.items.MultiItemProperties;
import rubedo.util.Singleton;

public class ToolProperties extends MultiItemProperties<ToolBase> {
	private static ContentTools content = Singleton
			.getInstance(ContentTools.class);

	public ToolProperties(ItemStack stack, ToolBase tool) {
		super(stack, tool);

		if (stack.getItem() instanceof ToolBase && this.baseTags != null
				&& this.baseTags.hasKey("RubedoTool")) {
			this.tag = this.baseTags.getCompoundTag("RubedoTool");
		}
	}

	public boolean isBroken() {
		return !this.isValid() || this.tag.getBoolean("broken");
	}

	public void setBroken(boolean isBroken) {
		if (this.isValid()) {
			this.tag.setBoolean("broken", isBroken);
			this.generateAttackDamageNBT();
		}
	}

	public MaterialMultiItem getHeadMaterial() {
		MaterialMultiItem head = content
				.getMaterial(this.tag.getString("head"));
		if (this.isValid() && head.headMaterial != null)
			return head;
		else
			return content.getMaterial("invalid");
	}

	public void setHeadMaterial(MaterialMultiItem head) {
		if (this.isValid())
			this.tag.setString("head", head.name);
	}

	public MaterialMultiItem getRodMaterial() {
		MaterialMultiItem rod = content.getMaterial(this.tag.getString("rod"));
		if (this.isValid() && rod.rodMaterial != null)
			return rod;
		else
			return content.getMaterial("invalid");
	}

	public void setRodMaterial(MaterialMultiItem rod) {
		if (this.isValid())
			this.tag.setString("rod", rod.name);
	}

	public MaterialMultiItem getCapMaterial() {
		MaterialMultiItem cap = content.getMaterial(this.tag.getString("cap"));
		if (this.isValid() && cap.capMaterial != null)
			return cap;
		else
			return content.getMaterial("invalid");
	}

	public void setCapMaterial(MaterialMultiItem cap) {
		if (this.isValid())
			this.tag.setString("cap", cap.name);
	}

	public void generateAttackDamageNBT() {
		if (this.isValid()) {
			NBTTagCompound nnbt = new NBTTagCompound();
			NBTTagList nnbtl = new NBTTagList();
			AttributeModifier att = new AttributeModifier(
					"generic.attackDamage", this.getAttackDamage(), 0);
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
		if (!this.isBroken())
			return (this.item.getWeaponDamage() + this.getHeadMaterial().damage)
					* this.getCapMaterial().modDamage;
		else
			return 0;
	}

	public void updateAttackDamage() {
		if (this.getAttackDamage() != this.baseTags
				.getTagList("AttributeModifiers", 10).getCompoundTagAt(0)
				.getDouble("Amount")) {
			this.generateAttackDamageNBT();
		}
	}

	public int getDurability() {
		if (this.isValid()) {
			int baseDur = this.getHeadMaterial().durability;
			float modifier = this.getRodMaterial().modDurability;
			return (int) (baseDur * modifier);
		}
		return 1;
	}

	public int getMiningLevel() {
		if (this.isValid() && !this.isBroken()) {
			int boosted = (this.getStack().isItemEnchanted() && this
					.getHeadMaterial().type == MaterialType.METAL_BRONZE) ? 1
					: 0;
			return this.getHeadMaterial().miningLevel + boosted;
		} else
			return -1;
	}

	public int getSpecial() {
		if (this.isValid())
			return this.getCapMaterial().mundaneLevel;
		else
			return 0;
	}

	public MaterialType getMaterialType() {
		return this.getHeadMaterial().type;
	}
}
