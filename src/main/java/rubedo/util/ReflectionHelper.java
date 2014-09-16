package rubedo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

public class ReflectionHelper {
	public static <T> void setStatic(Class<T> clazz, String name, Object value) {
		try {
			Field field = clazz.getDeclaredField(name);
			
			field.setAccessible(true);

		    // remove final modifier from field
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    field.set(null, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void setField(T obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			
			field.setAccessible(true);

		    // remove final modifier from field
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> Object getField(T obj, String name) {
		try {
			List<Field> fields = getInheritedPrivateFields(obj.getClass());
			Field field = null;
			
			for (Field f : fields)
				if (f.getName() == name)
					field = f;
			
			field.setAccessible(true);

		    // remove final modifier from field
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Field> getInheritedPrivateFields(Class<?> type) {
	    List<Field> result = new ArrayList<Field>();

	    Class<?> i = type;
	    while (i != null && i != Object.class) {
	        result.addAll(Arrays.asList(i.getDeclaredFields()));
	        i = i.getSuperclass();
	    }

	    return result;
	}
	
	public static <T> T getInstance(Class<T> clazz, Object... params) {
		try {
	        Constructor<T> constructor;
	        Class<?>[] paramTypes = new Class<?>[params.length];
	        for (int i = 0; i < params.length; i++) {
	        	paramTypes[i] = params[i].getClass();
	        }
	        	
	        constructor = clazz.getDeclaredConstructor(paramTypes);
	        constructor.setAccessible(true);
        
			return constructor.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }  
}
