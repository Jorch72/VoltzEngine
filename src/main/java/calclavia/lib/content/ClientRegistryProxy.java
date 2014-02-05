package calclavia.lib.content;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import calclavia.lib.Calclavia;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientRegistryProxy extends CommonRegistryProxy
{
	@Override
	public void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name, String modID)
	{
		super.registerBlock(block, itemClass, name, modID);

		BlockInfo blockInfo = block.getClass().getAnnotation(BlockInfo.class);

		if (blockInfo != null)
		{
			for (int i = 0; i < blockInfo.renderer().length; i++)
			{
				try
				{
					ClientRegistry.bindTileEntitySpecialRenderer(blockInfo.tileEntity()[i], blockInfo.renderer()[i].newInstance());
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new RuntimeException("Failed to register block for: " + name);
				}
			}
		}
	}

	@Override
	public void registerTileEntity(String name, Class<? extends TileEntity> tileClass)
	{
		super.registerTileEntity(name, tileClass);

		TileEntitySpecialRenderer tileRenderer = null;

		try
		{

			String rendererName = tileClass.getName().replaceFirst("Tile", "Render");
			Class renderClass = Class.forName(rendererName);
			tileRenderer = (TileEntitySpecialRenderer) renderClass.newInstance();
		}
		catch (Exception e)
		{
			Calclavia.LOGGER.severe("Failed to register TileEntity renderer for " + name);
		}

		if (tileRenderer != null)
		{
			ClientRegistry.bindTileEntitySpecialRenderer(tileClass, tileRenderer);
		}
	}
}
