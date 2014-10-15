package rubedo.tileentity;

public class TileEntityMagmaFurnace extends TileEntityInventory {

	public TileEntityMagmaFurnace() {
		super(3);
	}

	@Override
	public String getInventoryName() {
		return "rubedo.magmafurnace";
	}

}
