package rubedo.items.tools;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import rubedo.RubedoCore;
import rubedo.common.ContentTools;
import rubedo.common.Language;
import rubedo.common.Language.Formatting;
import rubedo.items.MultiItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//TODO: add getStrVsBlock
public abstract class ToolBase extends MultiItem {
	public ToolBase(int id) {
		super(id);
        this.setUnlocalizedName("ToolBase");
        this.setCreativeTab(RubedoCore.creativeTab);
        
        this.maxStackSize = 1;
        setMaxDamage(Integer.MAX_VALUE);
        
        setNoRepair();
        canRepair = false;
	}
	
	public abstract String getName();
	public float getWeaponDamage() { return 0.0F; }
	public abstract int getItemDamageOnHit();
	public abstract int getItemDamageOnBreak();
	public abstract float getEffectiveBlockSpeed();
	public float getEffectiveMaterialSpeed() { return 1.5F; }
	public float getBaseSpeed() { return 1.0f; }
	public abstract Material[] getEffectiveMaterials();
	public abstract Block[] getEffectiveBlocks();
	
	public abstract List<Integer> getAllowedEnchantments();
	
	protected ToolProperties getToolProperties(ItemStack stack) {
		return new ToolProperties(stack, this);
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
		
		for (Entry<String, ContentTools.Material> headEntry : ContentTools.toolHeads.entrySet()) {
			String name = getName() + "_head_" + headEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon(RubedoCore.getId() + ":tools/" + name));
			getRenderList().put(name + "_broken", iconRegister.registerIcon(RubedoCore.getId() + ":tools/" + name + "_broken"));
		}
		
		for (Entry<String, ContentTools.Material> rodEntry : ContentTools.toolRods.entrySet()) {
			String name = getName() + "_rod_" + rodEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon(RubedoCore.getId() + ":tools/" + name));
		}
		
		for (Entry<String, ContentTools.Material> capEntry : ContentTools.toolCaps.entrySet()) {
			String name = getName() + "_cap_" + capEntry.getKey();
			getRenderList().put(name, iconRegister.registerIcon(RubedoCore.getId() + ":tools/" + name));
		}
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect (ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }
    
	@Override
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
    	ToolProperties properties = this.getToolProperties(stack);
        return ToolUtil.hitEntity(properties, par3EntityLivingBase);
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLivingBase player)
    {
		if ((double)Block.blocksList[blockID].getBlockHardness(world, x, y, z) != 0.0D)
        {
			ToolProperties properties = this.getToolProperties(stack);
			return ToolUtil.onBlockDestroyed(properties, world, blockID, x, y, z, player);
        }
		return false;
    }
	
	@Override
    public float getStrVsBlock (ItemStack stack, Block block, int meta)
    {
		ToolProperties properties = this.getToolProperties(stack);
        return ToolUtil.getStrVsBlock(properties, block, meta);
    }
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		ToolProperties properties = this.getToolProperties(stack);
		return ToolUtil.isDamaged(properties);
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		ToolProperties properties = this.getToolProperties(stack);
		return ToolUtil.getDisplayDamage(properties);
	}
	
	// Misc
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void getSubItems(int id, CreativeTabs tabs, List list) {
    	for (Entry<String, ContentTools.Material> headEntry : ContentTools.toolHeads.entrySet())
    	for (Entry<String, ContentTools.Material> rodEntry : ContentTools.toolRods.entrySet())
    	for (Entry<String, ContentTools.Material> capEntry : ContentTools.toolCaps.entrySet()) {
    		list.add(this.buildTool(headEntry.getKey(), rodEntry.getKey(), capEntry.getKey()));
    	}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
    	ToolProperties properties = getToolProperties(stack);
    	
    	list.add("§2§o" + Language.getFormattedLocalization("tools.toolRod", true)
    				.put("$material1", "materials." + properties.getCapMaterial(), Formatting.CAPITALIZED)
    				.put("$material2", "materials." + properties.getRodMaterial(), Formatting.LOWERCASE)
    				.getResult());
    	list.add("");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack stack) {
    	ToolProperties properties = getToolProperties(stack);
    	
    	String key;
		String modifier;
		if (properties.isBroken()) {
			modifier = "§4";
			key = "tools.toolNameBroken";
		}
		else {
			modifier = "";
			key = "tools.toolName";
		}
		
		return modifier + Language.getFormattedLocalization(key, true)
				.put("$material", "materials." + properties.getHeadMaterial(), Formatting.CAPITALIZED)
				.put("$tool.type", "tools.type." + getName(), Formatting.CAPITALIZED)
				.getResult();
    }
    
    public abstract ItemStack buildTool(String head, String rod, String cap);
    
    public ItemStack buildTool(ItemStack tool, String head, String rod, String cap) {
    	NBTTagCompound compound = new NBTTagCompound();
    	if (!compound.hasKey("RubedoTool")) {
			compound.setCompoundTag("RubedoTool", new NBTTagCompound());
			tool.setTagCompound(compound);
    	}
    	
    	// Set the correct tool properties
    	ToolProperties properties = this.getToolProperties(tool);
    	properties.setHeadMaterial(head);
    	properties.setRodMaterial(rod);
    	properties.setCapMaterial(cap);
    	
    	if (getWeaponDamage() > 0)
    		properties.generateAttackDamageNBT();
		
    	return tool;
    }
}
