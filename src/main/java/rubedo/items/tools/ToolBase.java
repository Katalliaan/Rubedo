package rubedo.items.tools;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import rubedo.common.ContentTools;
import rubedo.common.ContentTools.Material;
import rubedo.items.MultiItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//TODO: add getStrVsBlock
public abstract class ToolBase extends MultiItem {
	public ToolBase(int id) {
		super(id);
        this.setUnlocalizedName("ToolBase");
        
        setNoRepair();
        canRepair = false;
	}
	
	public abstract String getName();
	
	protected ToolProperties getToolProperties(ItemStack stack) {
		return new ToolProperties(stack);
	}
	
	@Override
	public int getIconCount() {
		return 3;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (ItemStack stack, int renderPass)
    {
		ToolProperties properties = getToolProperties(stack);
		
		String name = "blank";
		
		switch(renderPass) {
		case 0:
			//Head
			name = getName() + "_head_" + properties.getHeadMaterial();
			break;
		case 1:
			//Rod
			name = getName() + "_rod_" + properties.getRodMaterial();
			break;
		case 2:
			//Cap
			name = getName() + "_cap_" + properties.getCapMaterial();
			break;
		}
		
    	return getRenderList().get(name);
    }

	@Override
    public void registerIcons (IconRegister iconRegister)
    {	
		super.registerIcons(iconRegister);
		
		for (Entry<String, Material> headEntry : ContentTools.toolHeadMaterials.entrySet()) {
			String name = getName() + "_head_" + headEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
		}
		
		for (Entry<String, Material> rodEntry : ContentTools.toolRodMaterials.entrySet()) {
			String name = getName() + "_rod_" + rodEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
		}
		
		for (Entry<String, Material> capEntry : ContentTools.toolCapMaterials.entrySet()) {
			String name = getName() + "_cap_" + capEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
		}
    }
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect (ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return hasEffect(par1ItemStack) && (pass == 0);
    }
    
    //TODO: replace by proper tooltip system for tools
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
    	ToolProperties properties = getToolProperties(stack);
    	
    	list.add("Head: " + properties.getHeadMaterial());
    	list.add("Rod: " + properties.getRodMaterial());
    	list.add("Cap: " + properties.getCapMaterial());
    }
    
    @Override
    public boolean isItemTool (ItemStack par1ItemStack)
    {
        return true;
    }
    
    @Override
    public boolean getIsRepairable (ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

    @Override
    public boolean isRepairable ()
    {
        return false;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list) {
    	for (Entry<String, Material> headEntry : ContentTools.toolHeadMaterials.entrySet())
    	for (Entry<String, Material> rodEntry : ContentTools.toolRodMaterials.entrySet())
    	for (Entry<String, Material> capEntry : ContentTools.toolCapMaterials.entrySet()) {
    		list.add(this.buildTool(headEntry.getKey(), rodEntry.getKey(), capEntry.getKey()));
    	}
    }
    
    public abstract ItemStack buildTool(String head, String rod, String cap);
    
    public ItemStack buildTool(ItemStack tool, String head, String rod, String cap) {
    	// Set the correct tool properties
		NBTTagCompound compound = new NBTTagCompound();
		compound.setCompoundTag("RubedoTool", new NBTTagCompound());
		compound.getCompoundTag("RubedoTool").setString("head", head);
		compound.getCompoundTag("RubedoTool").setString("rod", rod);
		compound.getCompoundTag("RubedoTool").setString("cap", cap);
		
		// Set the name, capitalized
		compound.setCompoundTag("display", new NBTTagCompound());
		compound.getCompoundTag("display")
			.setString("Name", 
					head.substring(0, 1).toUpperCase() + head.substring(1) + " " +
					getName().substring(0, 1).toUpperCase() + getName().substring(1));
		
		tool.setTagCompound(compound);
    	
    	return tool;
    }
}
