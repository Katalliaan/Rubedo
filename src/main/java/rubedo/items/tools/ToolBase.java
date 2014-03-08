package rubedo.items.tools;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rubedo.common.Content;
import rubedo.common.ContentTools;
import rubedo.common.ContentTools.Material;
import rubedo.items.MultiItem;

//TODO: add getStrVsBlock
public abstract class ToolBase extends MultiItem {
	public ToolBase(int id) {
		super(id);
        this.setUnlocalizedName("ToolBase");
        this.setCreativeTab(Content.creativeTab);
        
        this.maxStackSize = 1;
        setMaxDamage(100);
        
        setNoRepair();
        canRepair = false;
	}
	
	public abstract String getName();
	public abstract int getItemDamageOnHit();
	public abstract int getItemDamageOnBreak();
	
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
			if (!stack.getTagCompound().getCompoundTag("RubedoTool").getBoolean("broken"))
				name = getName() + "_head_" + properties.getHeadMaterial();
			else
				name = getName() + "_head_" + properties.getHeadMaterial() + "_broken";
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
		
		Icon icon = getRenderList().get(name);
		if (icon == null)
			icon = getRenderList().get("blank");
		
		return icon;
    }

	@Override
    public void registerIcons (IconRegister iconRegister)
    {	
		super.registerIcons(iconRegister);
		
		for (Entry<String, Material> headEntry : ContentTools.toolHeadMaterials.entrySet()) {
			String name = getName() + "_head_" + headEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon("rubedo:tools/" + name));
			getRenderList().put(name + "_broken", iconRegister.registerIcon("rubedo:tools/" + name + "_broken"));
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
    
    // Tool interactions
    
    @Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        return ToolUtil.hitEntity(this, stack, par3EntityLivingBase);
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
        return ToolUtil.onBlockDestroyed(this, stack, par2World, par3, par4, par5, par6, par7EntityLivingBase);
    }
	
	@Override
    public float getStrVsBlock (ItemStack stack, Block block, int meta)
    {
        return ToolUtil.getStrVsBlock(this, stack, block, meta);
    }
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		return ToolUtil.getDisplayDamage(this, stack);
	}
	
	@Override
    public boolean onLeftClickEntity (ItemStack stack, EntityPlayer player, Entity entity)
    {
        ToolUtil.onLeftClickEntity(this, stack, player, entity);
        return false;
    }
	
	public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
		ToolUtil.onItemRightClick(this, stack, world, player);
		return stack;
    }
	
	// Misc
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list) {
    	for (Entry<String, Material> headEntry : ContentTools.toolHeadMaterials.entrySet())
    	for (Entry<String, Material> rodEntry : ContentTools.toolRodMaterials.entrySet())
    	for (Entry<String, Material> capEntry : ContentTools.toolCapMaterials.entrySet()) {
    		list.add(this.buildTool(headEntry.getKey(), rodEntry.getKey(), capEntry.getKey()));
    	}
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
