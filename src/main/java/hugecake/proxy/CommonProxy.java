package hugecake.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import hugecake.block.TileEntityHugeCake;

public class CommonProxy
{
	public final void registerTileEntity ()
	{
		GameRegistry.registerTileEntity(TileEntityHugeCake.class, "hugecake:TileHugeCake");
	}
}