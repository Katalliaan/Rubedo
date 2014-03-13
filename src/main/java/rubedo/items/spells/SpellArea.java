package rubedo.items.spells;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import rubedo.common.Content;
import rubedo.raycast.IShapedRayCast;
import rubedo.raycast.LinearRayCast;
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

	
	// TODO: figure this out
	@Override
	public void castSpell(World world, EntityPlayer entityPlayer, int power,
			String effectType, float focusModifier) {		
		// get the camera position and direction
		Vec3 direction = ShapedRayCast.eulerToVec(world, entityPlayer.rotationPitch, entityPlayer.rotationYaw);
		Vec3 camera = ShapedRayCast.getCameraPosition(world, entityPlayer);
		
		// create a new raycaster
		IShapedRayCast rayCaster = new SphericalRayCast(
				world, 
				camera.xCoord, camera.yCoord, camera.zCoord, 
				direction.xCoord, direction.yCoord, direction.zCoord, 
				16);
		
		ChunkPosition cameraCP = new ChunkPosition(camera);
		
		for (ChunkPosition pos : rayCaster.getBlocks()) {
			//this is how you'd drop the correct items
			/*int blockId = world.getBlockId(pos.x, pos.y, pos.z);
			
			if (blockId > 0)
            {
                Block block = Block.blocksList[blockId];

                block.dropBlockAsItemWithChance(
                		world, pos.x, pos.y, pos.z, 
                		world.getBlockMetadata(pos.x, pos.y, pos.z), 
                		1.0F, 0);
            }*/
			
			// isRemote is needed to only run this server-side
			if (!world.isRemote && !pos.equals(cameraCP))
            	world.setBlock(pos.x, pos.y, pos.z, Block.glass.blockID);
		}
		
		for (Entity entity : rayCaster.getEntities())
		{
			entity.setFire(100);
		}
	}

	@Override
	public ItemStack buildSpell(String base, String focus, String effect) {
		ItemStack spell = new ItemStack(Content.spellArea);

		super.buildSpell(spell, base, focus, effect);

		return spell;
	}

}
