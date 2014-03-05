package rubedo.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MultiItem extends Item {
	protected Icon iconBlank;
	
	private HashMap<Integer, String> renderList;
	private HashMap<Integer, Icon> renderListIcons;
	
	public MultiItem(int id) {
		super(id);
		this.maxStackSize = 1;
        this.setUnlocalizedName("MultiItem");
        //TODO: add our own creative tab
        //this.setCreativeTab(TabTools);
        
        renderList = new HashMap<Integer, String>();
        renderListIcons = new HashMap<Integer, Icon>();
	}
	
	public Map<Integer, String> getRenderList() {
		return renderList;
	}

	public int getIconCount() {
		return renderList.size();
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
        return renderList.size();
    }
	
	@Override
    public void registerIcons (IconRegister iconRegister)
    {
		renderListIcons.clear();
		
		for (Entry<Integer, String> entry : renderList.entrySet()) {
			renderListIcons.put(
					entry.getKey(), 
					iconRegister.registerIcon(entry.getValue()));
		}
		
		iconBlank = iconRegister.registerIcon("rubedo:blank");
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
    	return renderListIcons.get(renderPass);
    }

    @Override
    public boolean isFull3D ()
    {
        return true;
    }
}

