package rubedo.items.spells;

import net.minecraft.item.ItemStack;
import rubedo.common.ContentSpells;
import rubedo.items.MultiItemProperties;
import rubedo.util.Singleton;

public class SpellProperties extends MultiItemProperties<SpellBase> {
	private static ContentSpells content = Singleton
			.getInstance(ContentSpells.class);
	
	public SpellProperties(ItemStack stack, SpellBase spell) {
		super(stack, spell);

		if (this.baseTags != null) {
			this.tag = this.baseTags.getCompoundTag("RubedoSpell");
		}
	}

	public String getBaseMaterial() {
		return tag.getString("base");
	}

	public void setBaseMaterial(String base) {
		tag.setString("base", base);
	}

	public String getFocusMaterial() {
		return tag.getString("focus");
	}

	public void setFocusMaterial(String focus) {
		tag.setString("focus", focus);
	}

	public String getEffectMaterial() {
		return tag.getString("effect");
	}

	public void setEffectMaterial(String effect) {
		tag.setString("effect", effect);
	}

	public int getPower() {
		
		return content.getMaterial(getBaseMaterial()).arcaneLevel;
	}

	public float getFocusModifier() {
		return content.getMaterial(getBaseMaterial()).speed;
	}

	public String getEffectType() {
		return ContentSpells.spellEffectMaterials.get(getEffectMaterial()).effectType;
	}

	public float getCastTime() {
		return ContentSpells.spellFocusMaterials.get(getFocusMaterial()).castTime;
	}

	public int getMiningLevel() {
		return content.getMaterial(getBaseMaterial()).miningLevel;
	}
}
