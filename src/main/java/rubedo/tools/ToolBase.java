package rubedo.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

//TODO: make abstract
public class ToolBase extends Item {
	private Icon iconBlank;
	
	//TODO: DEBUG: remove these
	private Icon iconTest1;
	private Icon iconBox;
	
	
	
	public ToolBase(int id, int basedamage) {
		super(id);
		this.maxStackSize = 1;
        this.setMaxDamage(100);
        this.setUnlocalizedName("ToolBase");
        //TODO: add our own creative tab
        //this.setCreativeTab(TabTools);
        setNoRepair();
        canRepair = false;
	}
	
	//TODO: make abstract
	public int getIconCount() {
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses ()
    {
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public int getRenderPasses (int metadata)
    {
        return getIconCount();
    }
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect (ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return hasEffect(par1ItemStack) && (pass == getIconCount() - 1);
    }
	
	@Override
    public void registerIcons (IconRegister iconRegister)
    {
		iconBlank = iconRegister.registerIcon("rubedo:blank");
		iconTest1 = iconRegister.registerIcon("rubedo:test1");
		iconBox = iconRegister.registerIcon("rubedo:box");
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage (int meta)
    {
        return iconBlank;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (ItemStack stack, int renderPass)
    {
    	if (renderPass == 0)
    		return iconTest1;
    	else
    		return iconBox;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
    	list.add("testing tags");
    }
    
    // Vanilla overrides
    @Override
    public boolean isItemTool (ItemStack par1ItemStack)
    {
        return false;
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

    @Override
    public int getItemEnchantability ()
    {
        return 0;
    }

    @Override
    public boolean isFull3D ()
    {
        return true;
    }
}
