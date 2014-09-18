package rubedo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.resources.I18n;
import rubedo.RubedoCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Language {
	public static enum Formatting {
		UPPERCASE, LOWERCASE, CAPITALIZED
	}

	public static enum FormatterCodes {
		BLACK("\u00a70"), DARK_BLUE("\u00a71"), DARK_GREEN("\u00a72"),
		DARK_AQUA("\u00a73"), DARK_RED("\u00a74"), DARK_PURPLE("\u00a75"),
		GOLD("\u00a76"), GRAY("\u00a77"), DARK_GRAY("\u00a78"), BLUE("\u00a79"),
		GREEN("\u00a7a"), AQUA("\u00a7b"), RED("\u00a7c"), LIGHT_PURPLE("\u00a7d"),
		YELLOW("\u00a7e"), WHITE("\u00a7f"), OBFUSCATED("\u00a7k"), BOLD("\u00a7l"),
		STRIKETHROUGH("\u00a7m"), UNDERLINE("\u00a7"), ITALIC("\u00a7o"), RESET("\u00a7r");

		private final String text;

		private FormatterCodes(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
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
						case CAPITALIZED :
							value = value.substring(0, 1).toUpperCase()
									+ value.substring(1);
							break;
						case LOWERCASE :
							value = value.toLowerCase();
							break;
						case UPPERCASE :
							value = value.toUpperCase();
							break;
					}
				}
				result = result.replace(parameter.getKey(), value);
			}
			return result;
		}
	}
}
