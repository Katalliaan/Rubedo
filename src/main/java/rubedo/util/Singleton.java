package rubedo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Singleton <T> {
	private static Map<Class<?>, Object> instances = new HashMap<Class<?>, Object>();
	
	@SuppressWarnings("unchecked")
	public static <U> U getInstance(Class<U> type) {
		if (!instances.containsKey(type)) {
			Constructor<?> constructor = null;
			try {
				constructor = type.getDeclaredConstructor();
				
				constructor.setAccessible(true);
				Object instance = constructor.newInstance();
				constructor.setAccessible(false);
				instances.put(type, instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (type.getDeclaredConstructors().length != 1 
					|| constructor == null
					|| (constructor != null
							&& Modifier.isPublic(constructor.getModifiers())))
				throw new RuntimeException("Class "+type.getCanonicalName()+" is not a Singleton!");
		}
		return (U) instances.get(type);
	}
	
	protected Singleton(Class<?> type) {}
}
