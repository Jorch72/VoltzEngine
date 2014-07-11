package resonant.lib.render.model;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

public class FixedTechneModelLoader implements IModelCustomLoader
{

	private static final String[] types = { "tcn" };

	@Override
	public String getType()
	{
		return "Techne model";
	}

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	@Override
	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		return new FixedTechneModel(resource);
	}

}