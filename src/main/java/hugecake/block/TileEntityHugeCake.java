package hugecake.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHugeCake extends TileEntity
{
	private HugeCakePartBB partBB;

	public TileEntityHugeCake ()
	{
		super();

		this.partBB = new HugeCakePartBB();
	}

	private static final String TAG_PARTBB = "bb";

	@Override
	public void readFromNBT (NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.partBB.readFromNBT(nbt, TAG_PARTBB);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		this.partBB.writeToNBT(nbt, TAG_PARTBB);
	}

	@Override
	public Packet getDescriptionPacket ()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);

		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}

	public HugeCakePartBB getPartBB ()
	{
		return this.partBB;
	}
}