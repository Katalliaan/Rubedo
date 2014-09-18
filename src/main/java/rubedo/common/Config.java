package rubedo.common;

import java.io.File;
import java.util.Collection;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class Config {

	private static File directory;
	private static Configuration config;

	public static void load(FMLPreInitializationEvent event,
			Collection<IContent> content) {
		directory = event.getModConfigurationDirectory();
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		for (IContent unit : content)
			unit.config(config);

		config.save();
	}

	public static File getDirectory() {
		return directory;
	}

	public static Configuration getConfiguration() {
		return config;
	}
}
