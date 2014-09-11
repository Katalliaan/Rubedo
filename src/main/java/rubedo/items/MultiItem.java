package rubedo.items;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import rubedo.RubedoCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MultiItem extends Item {
	protected Icon iconBlank;
	
	private HashMap<String, Icon> registeredIcons;
	
	public MultiItem(int id) {
		super(id);
		this.maxStackSize = 1;
        this.setUnlocalizedName("MultiItem");
        
        registeredIcons = new LinkedHashMap<String, Icon>();
	}
	
	public Map<String, Icon> getRenderList() {
		return registeredIcons;
	}

	public abstract int getIconCount();
	
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
	
	@Override
    public void registerIcons (IconRegister iconRegister)
    {		
		getRenderList().put("blank", iconRegister.registerIcon(RubedoCore.getId() + ":blank"));
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage (int meta)
    {
        return iconBlank;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public abstract Icon getIcon (ItemStack stack, int renderPass);

    @Override
    public boolean isFull3D ()
    {
        return true;
    }
}

