package hugecake;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import hugecake.proxy.CommonProxy;

@Mod(modid = "hugecake", name = "HugeCake")
public class HugeCake
{
	@SidedProxy(clientSide = "hugecake.proxy.ClientProxy", serverSide = "hugecake.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent event)
	{
		HugeCakeContentRegister.registerBlocks();
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent event)
	{
		proxy.registerTileEntity();

		HugeCakeContentRegister.registerRecipes();
	}
}