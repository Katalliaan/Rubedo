package rubedo.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.launchwrapper.Launch;

public class MCPHelper {
	private static final Map<String, String> fieldMap = new HashMap<String, String>();

	public static String getField(String name) {
		if ((!(Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
				&& fieldMap.containsKey(name)) {
			return fieldMap.get(name);
		}
		return name;
	}

	static {
		// Items.class
		fieldMap.put("wooden_axe", "field_151053_p");
		fieldMap.put("wooden_hoe", "field_151017_I");
		fieldMap.put("wooden_pickaxe", "field_151039_o");
		fieldMap.put("wooden_shovel", "field_151038_n");
		fieldMap.put("wooden_sword", "field_151041_m");

		fieldMap.put("stone_axe", "field_151049_t");
		fieldMap.put("stone_hoe", "field_151018_J");
		fieldMap.put("stone_pickaxe", "field_151050_s");
		fieldMap.put("stone_shovel", "field_151051_r");
		fieldMap.put("stone_sword", "field_151052_q");

		fieldMap.put("iron_axe", "field_151036_c");
		fieldMap.put("iron_hoe", "field_151019_K");
		fieldMap.put("iron_pickaxe", "field_151035_b");
		fieldMap.put("iron_shovel", "field_151037_a");
		fieldMap.put("iron_sword", "field_151040_l");

		fieldMap.put("golden_axe", "field_151006_E");
		fieldMap.put("golden_hoe", "field_151013_M");
		fieldMap.put("golden_pickaxe", "field_151005_D");
		fieldMap.put("golden_shovel", "field_151011_C");
		fieldMap.put("golden_sword", "field_151010_B");

		fieldMap.put("diamond_axe", "field_151056_x");
		fieldMap.put("diamond_hoe", "field_151012_L");
		fieldMap.put("diamond_pickaxe", "field_151046_w");
		fieldMap.put("diamond_shovel", "field_151047_v");
		fieldMap.put("diamond_sword", "field_151048_u");

		// RegistryNamespaced.class
		fieldMap.put("registryObjects", "field_82596_a");
		fieldMap.put("underlyingIntegerMap", "field_148759_a");

		// EntityAnimal.class
		fieldMap.put("inLove", "field_70881_d");

		// EntityLiving.class
		fieldMap.put("equipmentDropChances", "field_82174_bp");
	}
}
