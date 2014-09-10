package rubedo.common;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraftforge.common.Configuration;
import rubedo.RubedoCore;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class Config {
	
	private static File directory;
	private static Configuration config;
	private static HashMap<String, Integer> ids;
	private static int baseId = 3300;
	private static int nextId = 0;
	
	public static void load(FMLPreInitializationEvent event) {
		directory = event.getModConfigurationDirectory();
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		ids = new LinkedHashMap<String, Integer>();
		
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

	public static int getId(String name) {
		if (!ids.containsKey(name)) {
			ids.put(name, baseId + nextId);
			nextId++;
		}
		
		return ids.get(name);
	}
	
	public static void setId(String name, int id) {
		ids.put(name, id);
	}

	public static int initId(String name) {
		int id = config.getBlock(name, Config.getId(name)).getInt(Config.getId(name));
		setId(name, id);
		return id;
	}
}
