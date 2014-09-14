package rubedo.common;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import rubedo.RubedoCore;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class Config {
	
	private static File directory;
	private static Configuration config;
	
	public static void load(FMLPreInitializationEvent event) {
		directory = event.getModConfigurationDirectory();
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
				
		for (IContent content : RubedoCore.contentUnits.values())
			content.config(config);
		
		config.save();
	}
	
	public static File getDirectory() {
		return directory;
	}

	public static Configuration getConfiguration() {
		return config;
	}
}
