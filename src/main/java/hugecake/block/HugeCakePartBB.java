package hugecake.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class HugeCakePartBB
{
	public boolean isTopPart;
	public boolean isBottomPart;
	public int chippedNorth;
	public int chippedEast;
	public int chippedSouth;
	public int chippedWest;

	public boolean connectNorth;
	public boolean connectEast;
	public boolean connectSouth;
	public boolean connectWest;

	public HugeCakePartBB ()
	{
		this.isTopPart = false;
		this.isBottomPart = false;
		this.chippedNorth = 0;
		this.chippedEast = 0;
		this.chippedSouth = 0;
		this.chippedWest = 0;

		this.connectNorth = false;
		this.connectEast = false;
		this.connectSouth = false;
		this.connectWest = false;
	}

	public void readFromNBT (NBTTagCompound nbt, String baseTagName)
	{
		this.isTopPart = nbt.getBoolean(baseTagName + "_isTop");
		this.isBottomPart = nbt.getBoolean(baseTagName + "_isBot");
		this.chippedNorth = nbt.getInteger(baseTagName + "_n");
		this.chippedEast = nbt.getInteger(baseTagName + "_e");
		this.chippedSouth = nbt.getInteger(baseTagName + "_s");
		this.chippedWest = nbt.getInteger(baseTagName + "_w");

		this.connectNorth = nbt.getBoolean(baseTagName + "_c_n");
		this.connectEast = nbt.getBoolean(baseTagName + "_c_e");
		this.connectSouth = nbt.getBoolean(baseTagName + "_c_s");
		this.connectWest = nbt.getBoolean(baseTagName + "_c_w");
	}

	public void writeToNBT (NBTTagCompound nbt, String baseTagName)
	{
		nbt.setBoolean(baseTagName + "_isTop", this.isTopPart);
		nbt.setBoolean(baseTagName + "_isBot", this.isBottomPart);
		nbt.setInteger(baseTagName + "_n", this.chippedNorth);
		nbt.setInteger(baseTagName + "_e", this.chippedEast);
		nbt.setInteger(baseTagName + "_s", this.chippedSouth);
		nbt.setInteger(baseTagName + "_w", this.chippedWest);

		nbt.setBoolean(baseTagName + "_c_n", this.connectNorth);
		nbt.setBoolean(baseTagName + "_c_e", this.connectEast);
		nbt.setBoolean(baseTagName + "_c_s", this.connectSouth);
		nbt.setBoolean(baseTagName + "_c_w", this.connectWest);
	}

	public AxisAlignedBB convertAABB ()
	{
		return AxisAlignedBB.getBoundingBox(
			toDoubleChip(this.chippedWest), 0D, toDoubleChip(this.chippedNorth),
			1 - toDoubleChip(this.chippedEast), 1 - (this.isTopPart ? toDoubleChip(2) : 0D), 1 - toDoubleChip(this.chippedSouth));
	}

	public AxisAlignedBB convertAABB (int baseX, int baseY, int baseZ)
	{
		return AxisAlignedBB.getBoundingBox(
			baseX + toDoubleChip(this.chippedWest), (double)baseY, baseZ + toDoubleChip(this.chippedNorth),
			baseX + 1 - toDoubleChip(this.chippedEast), baseY + 1 - (this.isTopPart ? toDoubleChip(2) : 0D), baseZ + 1 - toDoubleChip(this.chippedSouth));
	}

	private static double toDoubleChip (int integerChip)
	{
		return (double)integerChip / 16D;
	}

	@Override
	public String toString ()
	{
		return "{ TOP:" + this.isTopPart + ", BOTTOM:" + this.isBottomPart + " [" +
				this.chippedNorth + ", " + this.chippedEast + ", " +
				this.chippedSouth + ", " + this.chippedWest + "]}";
	}
}