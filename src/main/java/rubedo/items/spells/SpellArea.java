package rubedo.items.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import rubedo.common.ContentSpells;
import rubedo.raycast.IBlockRayFilter;
import rubedo.raycast.IShapedRayCast;
import rubedo.raycast.ShapedRayCast;
import rubedo.raycast.SphericalRayCast;

public class SpellArea extends SpellBase {

	public SpellArea(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "area";
	}
	
	@Override
	public void castSpell(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
		SpellProperties properties = getSpellProperties(itemStack);
		float focusModifier = properties.getFocusModifier();
		int power = properties.getPower();
		String effectType = properties.getEffectType();
		
		// get the camera position and direction
		Vec3 direction = ShapedRayCast.eulerToVec(world,
				entityPlayer.rotationPitch, entityPlayer.rotationYaw);
		Vec3 camera = ShapedRayCast.getCameraPosition(world, entityPlayer);

		// create a new raycaster
		IShapedRayCast rayCaster = new SphericalRayCast(world, camera.xCoord,
				camera.yCoord, camera.zCoord, direction.xCoord,
				direction.yCoord, direction.zCoord, focusModifier);

		ChunkPosition cameraCP = new ChunkPosition(camera);

		for (Entity entity : rayCaster.getEntitiesExcludingEntity(entityPlayer)) {
			SpellEffects.hitEntity(world, entityPlayer, properties);
		}

		if (SpellEffects.hitsBlocks(effectType)) {
			IBlockRayFilter filter = new IBlockRayFilter() {
				@Override
				public boolean matches(WorldPosition position) {
					if (position.getBlock() == null)
						return false;
					else if (position.getBlock().isAirBlock(position.world,
							position.position.x, position.position.y,
							position.position.z))
						return false;
					else
						return true;

				}

				@Override
				public float getBlockResistance(WorldPosition position) {
					return 0;
				}

			};

			for (ChunkPosition pos : rayCaster.getBlocks(filter)) {
				SpellEffects
						.hitBlock(world, properties, pos.x, pos.y, pos.z, 1);
			}
		}
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(ContentSpells.spellArea);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}

}
