package rubedo.items.spells;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rubedo.items.MultiItem;

public abstract class SpellBase extends MultiItem {

	public SpellBase(int id) {
		super(id);
		this.setUnlocalizedName("SpellBase");
	}
	
	//TODO: replace by proper tooltip system for spells
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
    	list.add("DEBUG: This is a Spell");
    }

}
