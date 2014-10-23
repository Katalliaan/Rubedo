package rubedo.common;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import rubedo.common.materials.MaterialMultiItem;
import rubedo.items.MultiItem;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ContentMultiItem<T extends MultiItem> extends
		Singleton<T> implements IContent {
	protected ContentMultiItem(Class<?> type) {
		super(type);
	}

	private Map<Class<? extends T>, T> multiItems = new LinkedHashMap<Class<? extends T>, T>();
	private Map<Class<? extends MaterialMultiItem>, MaterialMultiItem> materials = new LinkedHashMap<Class<? extends MaterialMultiItem>, MaterialMultiItem>();
	private Map<String, Class<? extends MaterialMultiItem>> materialNames = new LinkedHashMap<String, Class<? extends MaterialMultiItem>>();

	public void setKinds(Set<Class<? extends T>> classes) {
		this.multiItems = new LinkedHashMap<Class<? extends T>, T>();
		for (Class<? extends T> clazz : classes)
			this.multiItems.put(clazz, null);
	}

	public Iterable<Class<? extends T>> getKinds() {
		return this.multiItems.keySet();
	}

	public Iterable<T> getItems() {
		return this.multiItems.values();
	}

	@SuppressWarnings("unchecked")
	public <U extends T> U getItem(Class<U> kind) {
		return (U) this.multiItems.get(kind);
	}

	public void setMaterials(Set<Class<? extends MaterialMultiItem>> materials) {
		this.materials = new LinkedHashMap<Class<? extends MaterialMultiItem>, MaterialMultiItem>();
		this.materialNames = new LinkedHashMap<String, Class<? extends MaterialMultiItem>>();

		for (Class<? extends MaterialMultiItem> material : materials) {
			this.addMaterial(material);
		}
	}

	public Collection<MaterialMultiItem> getMaterials() {
		return this.materials.values();
	}

	public void addMaterial(Class<? extends MaterialMultiItem> material) {
		MaterialMultiItem instance = null;
		try {
			instance = material.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (instance != null) {
			this.materials.put(material, instance);
			this.materialNames.put(instance.name, material);
		}
	}

	public MaterialMultiItem getMaterial(
			Class<? extends MaterialMultiItem> material) {
		return this.materials.get(material);
	}

	public MaterialMultiItem getMaterial(String name) {
		return this.materials.get(this.materialNames.get(name));
	}

	@Override
	public void registerBase() {
		try {
			for (Class<? extends T> clazz : this.multiItems.keySet()) {
				Constructor<? extends T> constructor = clazz.getConstructor();
				T instance = constructor.newInstance();

				this.multiItems.put(clazz, instance);
				GameRegistry.registerItem(instance, clazz.getSimpleName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
