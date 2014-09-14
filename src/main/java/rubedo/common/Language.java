package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.resources.I18n;
import rubedo.RubedoCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Language {
	public static enum Formatting {
		UPPERCASE, LOWERCASE, CAPITALIZED
	}
	
	private static HashMap<String, Formatter> formatterCache = new HashMap<String, Formatter>();
	
	@SideOnly(Side.CLIENT)
	public static Formatter getFormattedLocalization(String key) {
		return getFormattedLocalization(key, false);
	}
	
	@SideOnly(Side.CLIENT)
	public static Formatter getFormattedLocalization(String key, boolean cached) {
		String formatted = I18n.format(RubedoCore.modid + "." + key);
				
		if (cached) {
			if (!formatterCache.containsKey(formatted))
				formatterCache.put(formatted, new Formatter(formatted));
			
			return formatterCache.get(formatted);
		}
			
		
		return new Formatter(formatted);
	}
	
	@SideOnly(Side.CLIENT)
	public static String getLocalization(String key) {
		return I18n.format(RubedoCore.modid + "." + key);
	}
	
	/**
	 * For convenience sake
	 */
	@SideOnly(Side.CLIENT)
	public static class Formatter {
		private String formatted;
		private Map<String, String> parameters;
		private Map<String, Formatting> formatting;

		private Formatter(String formatted) {
			this.formatted = formatted;
			this.parameters = new HashMap<String, String>();
			this.formatting = new HashMap<String, Formatting>();
		}
		
		/**
		 * Add a parameter to the Formatter
		 */
		public Formatter put(String key, String value) {
			parameters.put(key, value);
			return this;
		}
		
		/**
		 * Add a parameter to the Formatter
		 */
		public Formatter put(String key, String value, Formatting fmt) {
			parameters.put(key, value);
			formatting.put(key, fmt);
			return this;
		}
		
		/**
		 * Get the formatted result
		 */
		public String getResult() {
			String result = formatted;
			for (Entry<String, String> parameter : parameters.entrySet()) {
				String value = getLocalization(parameter.getValue());
				
				if (formatting.containsKey(parameter.getKey())) {
					switch (formatting.get(parameter.getKey())) {
					case CAPITALIZED:
						if (FMLCommonHandler.instance().getCurrentLanguage() == "en_US"
						 || FMLCommonHandler.instance().getCurrentLanguage() == "en_UK")
							value = value.substring(0, 1).toUpperCase() + value.substring(1);
						break;
					case LOWERCASE:
						value = value.toLowerCase();
						break;
					case UPPERCASE:
						value = value.toUpperCase();
						break;
					}
				}
				result = result.replace(
						parameter.getKey(), 
						value);
			}
			return result;
		}
	}
}
