package rubedo.items.spells;

import net.minecraft.item.ItemStack;
import rubedo.items.MultiItemProperties;

public class SpellProperties extends MultiItemProperties<SpellBase> {
	public SpellProperties(ItemStack stack, SpellBase spell) {
		super(stack, spell);
		
		if (this.baseTags != null)
		{
			this.tag = this.baseTags.getCompoundTag("RubedoSpell");
		}
	}
	
	public String getBaseMaterial() { return tag.getString("base"); }
	public void setBaseMaterial(String base) { tag.setString("base", base); }
	public String getFocusMaterial() { return tag.getString("focus"); }
	public void setFocusMaterial(String focus) { tag.setString("focus", focus); }
	public String getEffectMaterial() { return tag.getString("effect");	}
	public void setEffectMaterial(String effect) { tag.setString("effect", effect); }
}
