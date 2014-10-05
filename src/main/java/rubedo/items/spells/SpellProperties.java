package rubedo.items.spells;

import net.minecraft.item.ItemStack;
import rubedo.common.ContentSpells;
import rubedo.common.materials.MaterialMultiItem;
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

	public void setBaseMaterial(MaterialMultiItem base) {
		tag.setString("base", base.name);
	}

	public String getFocusMaterial() {
		return tag.getString("focus");
	}

	public void setFocusMaterial(MaterialMultiItem focus) {
		tag.setString("focus", focus.name);
	}

	public String getEffectMaterial() {
		return tag.getString("effect");
	}

	public void setEffectMaterial(MaterialMultiItem effect) {
		tag.setString("effect", effect.name);
	}

	public int getPower() {
		return content.getMaterial(getBaseMaterial()).arcaneLevel;
	}
	
	public int getMundane() {
		return content.getMaterial(getBaseMaterial()).mundaneLevel;
	}

	public float getFocusModifier() {
		return content.getMaterial(getBaseMaterial()).speed / 2;
	}

	public String getEffectType() {
		return content.getMaterial(getEffectMaterial()).spellEffectType;
	}

	public int getMiningLevel() {
		return content.getMaterial(getBaseMaterial()).miningLevel;
	}
	
	public int getCost() {
		float power = getPower();
		float mundane = getMundane();
		
		// Avoiding div0 errors and free spells
		if (power == 0)
			power = 0.5f;
		if (mundane == 0)
			mundane = 0.5f;
		
		int cost = (int) (1000 / power * mundane);
		
		return cost;
	}
}
