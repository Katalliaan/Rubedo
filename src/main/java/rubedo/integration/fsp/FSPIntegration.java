package rubedo.integration.fsp;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import rubedo.common.ContentWorld;
import rubedo.common.ContentWorld.Metal;
import rubedo.integration.fsp.item.ItemRubedoPlate;
import rubedo.integration.fsp.item.ItemToolHeadMold;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.steamcraft.api.CrucibleFormula;
import flaxbeard.steamcraft.api.CrucibleLiquid;
import flaxbeard.steamcraft.api.ICrucibleMold;
import flaxbeard.steamcraft.api.SteamcraftRegistry;

public class FSPIntegration {
	public static Map<String, int[]> metalColors = new HashMap<String, int[]>();
	public static Map<String, CrucibleLiquid> liquids = new HashMap<String, CrucibleLiquid>();

	static {
		metalColors.put("silver", new int[] { 190, 210, 249 });
		metalColors.put("steel", new int[] { 50, 50, 50 });
		metalColors.put("orichalcum", new int[] { 241, 146, 109 });
		metalColors.put("mythril", new int[] { 147, 207, 203 });
		metalColors.put("hepatizon", new int[] { 68, 47, 105 });
	}

	public static void preInit() {
		if (Loader.isModLoaded("rubedo")) {
			MaterialIntegration.preInit();

			if (flaxbeard.steamcraft.Config.enableMold) {
				Item toolHeadMold = new ItemToolHeadMold();
				GameRegistry.registerItem(toolHeadMold, "moldToolHead");
				SteamcraftRegistry
						.addCarvableMold((ICrucibleMold) toolHeadMold);
			}

			if (flaxbeard.steamcraft.Config.enableCopperPlate) {
				Item plates = new ItemRubedoPlate();
				GameRegistry.registerItem(plates, "rubedoPlate");
				for (int i = 0; i < ContentWorld.metals.size(); i++) {
					Metal metal = ContentWorld.metals.get(i);
					if (metal.name != "copper") {
						OreDictionary.registerOre("plateSteamcraft" + metal,
								new ItemStack(plates, 1, i));
					}
				}
			}
		}
	}

	public static void init() {
		if (Loader.isModLoaded("rubedo")) {
			{
				CrucibleLiquid liquidBlaze = new CrucibleLiquid("blaze", null,
						null, null, null, 255, 200, 0);

				liquids.put("blaze", liquidBlaze);
				SteamcraftRegistry.registerLiquid(liquidBlaze);

				SteamcraftRegistry.registerSmeltThing(Items.blaze_rod,
						liquidBlaze, 9);
			}

			{
				CrucibleLiquid liquidEnd = new CrucibleLiquid("end", null,
						null, null, null, 255, 255, 190);

				liquids.put("end", liquidEnd);
				SteamcraftRegistry.registerLiquid(liquidEnd);

				SteamcraftRegistry.registerSmeltThing(
						Item.getItemFromBlock(Blocks.end_stone), liquidEnd, 9);
			}

			for (Metal metal : ContentWorld.metals) {
				if (metal.name != "copper"
						&& SteamcraftRegistry.getLiquidFromName(metal.name) == null) {
					int[] color = metalColors.get(metal.name);
					if (color == null) {
						color = new int[] { 255, 0, 255 };
					}

					CrucibleLiquid liquid = new CrucibleLiquid(metal.name, //
							OreDictionary.getOres("ingot" + metal).get(0), //
							OreDictionary.getOres("plateSteamcraft" + metal)
									.get(0), //
							OreDictionary.getOres("nugget" + metal).get(0), //
							getFormula(metal.name), color[0], color[1],
							color[2]);

					liquids.put(metal.name, liquid);
					SteamcraftRegistry.registerLiquid(liquid);

					SteamcraftRegistry.registerSmeltThingOredict("ingot"
							+ metal, liquid, 9);
					SteamcraftRegistry.registerSmeltThingOredict("dustTiny"
							+ metal, liquid, 1);
					SteamcraftRegistry.registerSmeltThingOredict("nugget"
							+ metal, liquid, 1);
					SteamcraftRegistry.registerSmeltThingOredict(
							"dust" + metal, liquid, 9);
					SteamcraftRegistry.registerSmeltThingOredict(
							"plateSteamcraft" + metal, liquid, 6);
				}
			}
		}
	}

	public static void postInit() {
		MaterialIntegration.postInit();
	}

	public static CrucibleFormula getFormula(String liquid) {
		CrucibleFormula formula = null;

		if (liquid == "steel")
			formula = new CrucibleFormula(
					SteamcraftRegistry.getLiquidFromName("iron"), 1,
					SteamcraftRegistry.getLiquidFromName("blaze"), 1, 1);

		if (liquid == "orichalcum")
			formula = new CrucibleFormula(
					SteamcraftRegistry.getLiquidFromName("copper"), 1,
					SteamcraftRegistry.getLiquidFromName("gold"), 1, 2);

		if (liquid == "mythril")
			formula = new CrucibleFormula(
					SteamcraftRegistry.getLiquidFromName("copper"), 1,
					SteamcraftRegistry.getLiquidFromName("silver"), 1, 2);

		if (liquid == "hepatizon") {
			{
				CrucibleLiquid liquidTemp = new CrucibleLiquid(
						"tempatizon",
						null,
						null,
						null,
						new CrucibleFormula(
								SteamcraftRegistry
										.getLiquidFromName("orichalcum"),
								1,
								SteamcraftRegistry.getLiquidFromName("mythril"),
								1, 2), //
						52, 3, 21);

				liquids.put("tempatizon", liquidTemp);
				SteamcraftRegistry.registerLiquid(liquidTemp);
			}

			formula = new CrucibleFormula(
					SteamcraftRegistry.getLiquidFromName("tempatizon"), 1,
					SteamcraftRegistry.getLiquidFromName("end"), 1, 1);
		}

		return formula;
	}
}
