package rubedo.items.tools;

import rubedo.common.Content;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ToolSword extends ToolBase {

	public ToolSword(int id) {
		super(id);
	}

	@Override
	public String getPrefix() {
		return "sword";
	}
	
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(Content.toolSword);
		
		NBTTagCompound compound = new NBTTagCompound();
		compound.setCompoundTag("RubedoTool", new NBTTagCompound());
		compound.getCompoundTag("RubedoTool").setString("head", head);
		compound.getCompoundTag("RubedoTool").setString("rod", rod);
		compound.getCompoundTag("RubedoTool").setString("cap", cap);
		
		tool.setTagCompound(compound);
		
		return tool;
	}
}
