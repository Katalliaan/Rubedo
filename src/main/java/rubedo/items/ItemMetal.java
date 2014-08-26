package rubedo.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import rubedo.common.Language;

public class ItemMetal extends Item {
		
	private String[] textures;
	private Icon[] icons;
	private HashMap<String, String> textureNames;
	
	public ItemMetal(int id) {
		super(id);
		this.setCreativeTab(RubedoCore.creativeTab);
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
        
        textureNames = new HashMap<String, String>();
        
        textureNames.put("iron_nugget", "iron materials.nuggetName");
        
        textureNames.put("tools/sword_head_flint", "flint tools.type.sword");
        textureNames.put("tools/pickaxe_head_flint", "flint tools.type.pickaxe");
        textureNames.put("tools/axe_head_flint", "flint tools.type.axe");
        textureNames.put("tools/shovel_head_flint", "flint tools.type.shovel");
        textureNames.put("tools/scythe_head_flint", "flint tools.type.scythe");
        
        textureNames.put("tools/sword_head_iron", "iron tools.type.sword");
        textureNames.put("tools/pickaxe_head_iron", "iron tools.type.pickaxe");
        textureNames.put("tools/axe_head_iron", "iron tools.type.axe");
        textureNames.put("tools/shovel_head_iron", "iron tools.type.shovel");
        textureNames.put("tools/scythe_head_iron", "iron tools.type.scythe");
        
        textureNames.put("tools/sword_head_gold", "gold tools.type.sword");
        textureNames.put("tools/pickaxe_head_gold", "gold tools.type.pickaxe");
        textureNames.put("tools/axe_head_gold", "gold tools.type.axe");
        textureNames.put("tools/shovel_head_gold", "gold tools.type.shovel");
        textureNames.put("tools/scythe_head_gold", "gold tools.type.scythe");
        
        for (Metal metal : ContentWorld.metals) {
        	textureNames.put(metal.name + "_nugget", metal.name +" materials.nuggetName");
        	textureNames.put(metal.name + "_ingot", metal.name +" materials.ingotName");
        	textureNames.put("tools/sword_head_" + metal.name, metal.name +" tools.type.sword");
        	textureNames.put("tools/pickaxe_head_" + metal.name, metal.name +" tools.type.pickaxe");
        	textureNames.put("tools/axe_head_" + metal.name, metal.name +" tools.type.axe");
        	textureNames.put("tools/shovel_head_" + metal.name, metal.name +" tools.type.shovel");
        	textureNames.put("tools/scythe_head_" + metal.name, metal.name +" tools.type.scythe");
        }
        
        this.textures = textureNames.keySet().toArray(new String[textureNames.size()]);
	}
	
	public int getTextureIndex(String name) {
		return Arrays.asList(this.textures).indexOf(name);
	}
	
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textures.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            if (!(textures[i].equals(""))) {
                this.icons[i] = iconRegister.registerIcon(RubedoCore.getId() + ":" + textures[i]);
            }
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        return RubedoCore.getId() + ".items." + textures[stack.getItemDamage()].replace('/', '.');
    }
    
    @Override
	public String getItemDisplayName(ItemStack stack) {
    	String[] split = textureNames.get(textures[stack.getItemDamage()]).split(" ");
    	
    	if (split[1].contains("tools.type."))
    	{
    		return Language.getFormattedLocalization("tools.toolHead", true)
    				.put("$material", "materials." + split[0])
    				.put("$tool.type", split[1])
    				.getResult();
    	}
    	
		return Language.getFormattedLocalization(split[1], true)
				.put("$material", "materials." + split[0])
				.getResult();
	}

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems (int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textures.length; i++)
            if (!(textures[i].equals("")))
                list.add(new ItemStack(id, 1, i));
    }
}
