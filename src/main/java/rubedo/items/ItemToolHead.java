package rubedo.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rubedo.RubedoCore;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import rubedo.common.Language;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolHead extends Item {
	
	public static Map<String, ItemToolHead> headMap = new LinkedHashMap<String, ItemToolHead>();
	
	protected String name;
	protected String[] textures;
	protected IIcon[] icons;
	protected HashMap<String, String> textureNames;
	
	public ItemToolHead(String name) {
		this.name = name;
		this.setCreativeTab(RubedoCore.creativeTab);
		this.setMaxDamage(0);
		this.setHasSubtypes(false);
		
		textureNames = new LinkedHashMap<String, String>();
		
		textureNames.put("sword_head_flint", "flint tools.type.sword");
        textureNames.put("pickaxe_head_flint", "flint tools.type.pickaxe");
        textureNames.put("axe_head_flint", "flint tools.type.axe");
        textureNames.put("shovel_head_flint", "flint tools.type.shovel");
        textureNames.put("scythe_head_flint", "flint tools.type.scythe");
        
        textureNames.put("sword_head_iron", "iron tools.type.sword");
        textureNames.put("pickaxe_head_iron", "iron tools.type.pickaxe");
        textureNames.put("axe_head_iron", "iron tools.type.axe");
        textureNames.put("shovel_head_iron", "iron tools.type.shovel");
        textureNames.put("scythe_head_iron", "iron tools.type.scythe");
        
        textureNames.put("sword_head_gold", "gold tools.type.sword");
        textureNames.put("pickaxe_head_gold", "gold tools.type.pickaxe");
        textureNames.put("axe_head_gold", "gold tools.type.axe");
        textureNames.put("shovel_head_gold", "gold tools.type.shovel");
        textureNames.put("scythe_head_gold", "gold tools.type.scythe");
        
        for (Metal metal : ContentWorld.metals) {
        	textureNames.put("sword_head_" + metal.name, metal.name +" tools.type.sword");
        	textureNames.put("pickaxe_head_" + metal.name, metal.name +" tools.type.pickaxe");
        	textureNames.put("axe_head_" + metal.name, metal.name +" tools.type.axe");
        	textureNames.put("shovel_head_" + metal.name, metal.name +" tools.type.shovel");
        	textureNames.put("scythe_head_" + metal.name, metal.name +" tools.type.scythe");
        }
        
        this.textures = textureNames.keySet().toArray(new String[textureNames.size()]);
        ItemToolHead.headMap.put(name, this);
	}
	
	public int getTextureIndex(String name) {
		return Arrays.asList(this.textures).indexOf(name);
	}
	
    public IIcon getIconFromDamage (int meta)
    {
        return icons[getTextureIndex(name)];
    }

    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textures.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            if (!(textures[i].equals(""))) {
                this.icons[i] = iconRegister.registerIcon(RubedoCore.modid + ":tools/" + textures[i]);
            }
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        return RubedoCore.modid + ".items.tools." + textures[getTextureIndex(name)];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
    	String[] split = textureNames.get(textures[getTextureIndex(name)]).split(" ");
    	
		return Language.getFormattedLocalization("tools.toolHead", true)
				.put("$material", "materials." + split[0])
				.put("$tool.type", split[1])
				.getResult();
	}
}
