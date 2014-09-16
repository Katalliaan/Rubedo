package rubedo.common;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rubedo.items.MultiItem;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ContentMultiItem<T extends MultiItem> extends Singleton<T> implements IContent {
	protected ContentMultiItem(Class<?> type) {
		super(type);
	}

	private Map<Class<? extends T>, T> multiItems = new LinkedHashMap<Class<? extends T>, T>();

	public void setItems(List<Class<? extends T>> classes) {
		for (Class<? extends T> clazz : classes)
			multiItems.put(clazz, null);
	}

	public T getItem(Class<? extends T> item) {
		return multiItems.get(item);
	}

	@Override
	public void register() {
		try {
			for (Class<? extends T> clazz : multiItems.keySet()) {
				Constructor<? extends T> constructor = clazz.getConstructor();
				T instance = constructor.newInstance();

				multiItems.put(clazz, instance);
				GameRegistry.registerItem(instance, clazz.getSimpleName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
