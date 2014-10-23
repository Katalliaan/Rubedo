package rubedo.common;

import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rubedo.RubedoCore;
import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.ItemSpellBase;
import rubedo.items.spells.EntitySpellProjectile;
import rubedo.items.spells.SpellArea;
import rubedo.items.spells.SpellBase;
import rubedo.items.spells.SpellProjectile;
import rubedo.items.spells.SpellSelf;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentSpells extends ContentMultiItem<SpellBase> implements
		IContent {
	public static SpellProjectile spellProjectile;
	public static SpellSelf spellSelf;
	public static SpellArea spellArea;

	protected ContentSpells() {
		super(ContentSpells.class);
		Set<Class<? extends SpellBase>> spellKinds = new LinkedHashSet<Class<? extends SpellBase>>();
		spellKinds.add(SpellProjectile.class);
		spellKinds.add(SpellArea.class);
		spellKinds.add(SpellSelf.class);

		this.setKinds(spellKinds);
	}

	private void initializeSpellMaterials() {
		Set<Class<? extends MaterialMultiItem>> spellMaterials = new LinkedHashSet<Class<? extends MaterialMultiItem>>();

		spellMaterials.add(MaterialMultiItem.Copper.class);
		spellMaterials.add(MaterialMultiItem.Iron.class);
		spellMaterials.add(MaterialMultiItem.Gold.class);
		spellMaterials.add(MaterialMultiItem.Silver.class);

		spellMaterials.add(MaterialMultiItem.Arrow.class);
		spellMaterials.add(MaterialMultiItem.Bottle.class);
		spellMaterials.add(MaterialMultiItem.Gunpowder.class);

		spellMaterials.add(MaterialMultiItem.Blazerod.class);
		spellMaterials.add(MaterialMultiItem.Snow.class);
		spellMaterials.add(MaterialMultiItem.Flint.class);
		spellMaterials.add(MaterialMultiItem.Bone.class);

		this.setMaterials(spellMaterials);
	}

	@Override
	public void config(Configuration config) {

	}

	@Override
	public void registerBase() {
		super.registerBase();

		this.initializeSpellMaterials();

		EntityRegistry.registerModEntity(EntitySpellProjectile.class,
				"SpellProjectile", cpw.mods.fml.common.registry.EntityRegistry
						.findGlobalUniqueEntityId(), RubedoCore.instance, 64,
				1, true);

		for (MaterialMultiItem material : this.getMaterials()) {
			if (material.baseMaterial != null) {
				String name = "base_" + material.name;
				Item item = ItemSpellBase.getBaseMap().get(name);
				GameRegistry.registerItem(item, name);
			}
		}
	}

	@Override
	public void registerDerivatives() {
		this.registerSpellRecipes();
	}

	@Override
	public void tweak() {

	}

	private void registerSpellRecipes() {
		for (MaterialMultiItem materialBase : this.getMaterials()) {
			if (materialBase.baseMaterial != null) {
				Object baseMaterial = materialBase.baseMaterial;
				if (OreDictionary.getOreIDs(materialBase.baseMaterial).length > 0)
					baseMaterial = OreDictionary.getOreName(OreDictionary
							.getOreIDs(materialBase.baseMaterial)[0]);

				// Spell base recipe
				GameRegistry.addRecipe(new ShapedOreRecipe(materialBase
						.getSpellPart("base"), "XXX", "XOX", "XXX", 'X',
						baseMaterial, 'O', new ItemStack(
								ContentWorld.metalItems, 1,
								ContentWorld.metalItems
										.getTextureIndex("copper_gem"))));

				// Spell recipes
				// TODO: get this to work
				for (MaterialMultiItem materialFocus : this.getMaterials()) {
					if (materialFocus.spellFocusMaterial != null) {

						Object spellFocusMaterial = materialFocus.spellFocusMaterial;
						if (OreDictionary
								.getOreIDs(materialFocus.spellFocusMaterial).length > 0)
							spellFocusMaterial = OreDictionary
									.getOreName(OreDictionary
											.getOreIDs(materialFocus.spellFocusMaterial)[0]);

						for (MaterialMultiItem materialEffect : this
								.getMaterials()) {
							if (materialEffect.spellEffectMaterial != null) {

								Object spellEffectMaterial = materialEffect.spellEffectMaterial;
								if (OreDictionary
										.getOreIDs(materialEffect.spellEffectMaterial).length > 0)
									spellEffectMaterial = OreDictionary
											.getOreName(OreDictionary
													.getOreIDs(materialEffect.spellEffectMaterial)[0]);

								for (Class<? extends SpellBase> kind : this
										.getKinds()) {
									ItemStack spell = this.getItem(kind)
											.buildSpell(materialBase,
													materialFocus,
													materialEffect);

									String name = ((SpellBase) spell.getItem())
											.getName();

									if (materialFocus.spellFocusType
											.equals(name)) {
										GameRegistry
												.addRecipe(new ShapelessOreRecipe(
														spell,
														materialFocus.spellFocusMaterial,
														materialBase
																.getSpellPart("base"),
														spellEffectMaterial));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
