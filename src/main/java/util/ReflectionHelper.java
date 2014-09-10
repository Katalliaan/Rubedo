package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
	public static <T> void setStatic(Class<T> clazz, String name, Object value) {
		try {
			Field field = clazz.getField(name);
			
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
			Field field = obj.getClass().getField(name);
			
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
}
