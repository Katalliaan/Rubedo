package rubedo.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rubedo.common.Content;
import rubedo.items.MultiItem;

public abstract class ToolBase extends MultiItem {
	public ToolBase(int id) {
		super(id);
        this.setUnlocalizedName("ToolBase");
        
        setNoRepair();
        canRepair = false;
	}
	
	public abstract String getPrefix();
	
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
			name = getPrefix() + "_head_" + properties.getHeadMaterial();
			break;
		case 1:
			//Rod
			name = getPrefix() + "_rod_" + properties.getRodMaterial();
			break;
		case 2:
			//Cap
			name = getPrefix() + "_cap_" + properties.getCapMaterial();
			break;
		}
		
    	return getRenderList().get(name);
    }

	@Override
    public void registerIcons (IconRegister iconRegister)
    {	
		super.registerIcons(iconRegister);
		
		for (String head : Content.toolHeadMaterials) {
			String name = getPrefix() + "_head_" + head;
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
		}
		
		for (String rod : Content.toolRodMaterials) {
			String name = getPrefix() + "_rod_" + rod;
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
		}
		
		for (String cap : Content.toolCapMaterials) {
			String name = getPrefix() + "_cap_" + cap;
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
    	
    	list.add("Head:" + properties.getHeadMaterial());
    	list.add("Rod:" + properties.getRodMaterial());
    	list.add("Cap:" + properties.getCapMaterial());
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
    	for (String head : Content.toolHeadMaterials)
    	for (String rod : Content.toolRodMaterials)
    	for (String cap : Content.toolCapMaterials) {
    		list.add(this.buildTool(head, rod, cap));
    	}
    }
    
    public abstract ItemStack buildTool(String head, String rod, String cap);
}
