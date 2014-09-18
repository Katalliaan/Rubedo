package rubedo.raycast;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public abstract class ShapedRayCast implements IShapedRayCast {
	/**
	 * When creating the ray targets, this decides the size of the shape
	 * structure. The larger this is, the more rays cast
	 */
	protected int boundSize = 16;

	protected World world;
	protected double originX, originY, originZ;
	protected double directionX, directionY, directionZ;
	protected float range;

	public ShapedRayCast(World world, double originX, double originY,
			double originZ, double directionX, double directionY,
			double directionZ, float range) {

		this.world = world;

		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;

		this.directionX = directionX;
		this.directionY = directionY;
		this.directionZ = directionZ;

		this.range = range;
	}

	public int getBoundSize() {
		return boundSize;
	}

	public void setBoundSize(int boundSize) {
		this.boundSize = boundSize;
	}

	/**
	 * Casts a ray in the direction and adds all blocks hit to the collection
	 */
	protected void castBlockRay(double directionRayX, double directionRayY,
			double directionRayZ, IBlockRayFilter filter,
			Collection<ChunkPosition> collection) {

		float stepSize = 0.3F;
		// Changes as resistance is met
		float actualRange = this.range;

		for (float length = 0.0F; length <= actualRange; length += stepSize) {
			int blockX = MathHelper.floor_double(this.originX
					+ (directionRayX * length));
			int blockY = MathHelper.floor_double(this.originY
					+ (directionRayY * length));
			int blockZ = MathHelper.floor_double(this.originZ
					+ (directionRayZ * length));

			ChunkPosition blockPos = new ChunkPosition(blockX, blockY, blockZ);

			boolean allowed = true;
			if (filter != null) {

				allowed = filter.matches(new IBlockRayFilter.WorldPosition(
						this.world, blockPos));

				if (allowed) {
					actualRange -= filter
							.getBlockResistance(new IBlockRayFilter.WorldPosition(
									this.world, blockPos));
				}
			}

			if (allowed)
				collection.add(blockPos);
		}
	}

	/**
	 * convert euler angles in degrees to a directional vector with length 1
	 */
	public static Vec3 eulerToVec(World world, float pitch, float yaw) {
		// store in valuables to cache calculations
		float pitchRadians = pitch / 180.0F * (float) Math.PI;
		float yawRadians = yaw / 180.0F * (float) Math.PI;

		double sinPitch = MathHelper.sin(pitchRadians);
		double cosPitch = MathHelper.cos(pitchRadians);
		double sinYaw = MathHelper.sin(yawRadians);
		double cosYaw = MathHelper.cos(yawRadians);

		double calculatedX = (double) (cosPitch * -sinYaw);
		double calculatedZ = (double) (cosPitch * cosYaw);
		double calculatedY = (double) (-sinPitch);

		return Vec3.createVectorHelper(calculatedX, calculatedY, calculatedZ);
	}

	/**
	 * Get the camera position of a player
	 */
	public static Vec3 getCameraPosition(World world, EntityPlayer entity) {
		return Vec3.createVectorHelper(entity.posX, entity.posY
				+ (double) entity.getEyeHeight() - 0.10000000149011612D,
				entity.posZ);
	}

	/**
	 * Normalize the vector
	 */
	public static Vec3 normalizeVector(World world, double x, double y, double z) {
		double length = MathHelper.sqrt_double(x * x + y * y + z * z);

		return Vec3.createVectorHelper(x / length, y / length, z / length);
	}
}
