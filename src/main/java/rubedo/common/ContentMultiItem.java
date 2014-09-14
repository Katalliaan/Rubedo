package rubedo.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import rubedo.items.MultiItem;

public class ContentMultiItem<T extends MultiItem> implements IContent {

	private Map<Class<? extends T>, T> multiItems = new LinkedHashMap<Class<? extends T>, T>();

	public void setItems(List<Class<? extends T>> classes) {
		for (Class<? extends T> clazz : classes)
			multiItems.put(clazz, null);
	}

	public T getItem(Class<? extends T> item) {
		return multiItems.get(item);
	}

	@Override
	public void config(Configuration config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}
}
