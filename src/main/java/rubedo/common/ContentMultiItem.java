package rubedo.common;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import rubedo.common.materials.Material;
import rubedo.items.MultiItem;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ContentMultiItem<T extends MultiItem, TMaterial extends Material> extends Singleton<T> implements IContent {
	protected ContentMultiItem(Class<?> type) {
		super(type);
	}

	private Map<Class<? extends T>, T> multiItems = new LinkedHashMap<Class<? extends T>, T>();
	private Map<Class<? extends TMaterial>, TMaterial> materials = new LinkedHashMap<Class<? extends TMaterial>, TMaterial>();
	private Map<String, Class<? extends TMaterial>> materialNames = new LinkedHashMap<String, Class<? extends TMaterial>>();

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

	public T getItem(Class<? extends T> kind) {
		return this.multiItems.get(kind);
	}

	public void setMaterials(Set<Class<? extends TMaterial>> materials) {
		this.materials = new LinkedHashMap<Class<? extends TMaterial>, TMaterial>();
		this.materialNames = new LinkedHashMap<String, Class<? extends TMaterial>>();

		for (Class<? extends TMaterial> material : materials) {
			TMaterial instance = null;
			try {
				instance = material.getDeclaredConstructor().newInstance();
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			if (instance != null) {
				this.materials.put(material, instance);
				this.materialNames.put(instance.name, material);
			}
		}
	}

	public Iterable<TMaterial> getMaterials() {
		return this.materials.values();
	}

	public TMaterial getMaterial(Class<? extends TMaterial> material) {
		return this.materials.get(material);
	}

	public TMaterial getMaterial(String name) {
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
