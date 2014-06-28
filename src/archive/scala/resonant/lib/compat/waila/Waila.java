package resonant.lib.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import resonant.lib.compat.Mods;
import resonant.lib.modproxy.ICompatProxy;

/**
 * @author tgame14
 * @since 21/03/14
 */
public class Waila implements ICompatProxy
{
	@Override
	public void preInit()
	{
		// nothing
	}

	@Override
	public void init()
	{
		FMLInterModComms.sendMessage(Mods.WAILA(), "register", "resonant.lib.compat.waila.WailaRegistrar.wailaCallBack");
	}

	@Override
	public void postInit()
	{
		// nothing
	}

	@Override
	public String modId()
	{
		return Mods.WAILA();
	}
}