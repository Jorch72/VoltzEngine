package resonant.content.factory.resources;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import resonant.lib.ore.OreGenReplaceStone;
import resonant.lib.ore.OreGenerator;

/**
 * Generic set of ore types that are commonly found in most mods, and MC itself
 *
 * @author Darkguardsman
 */
public enum DefinedResources
{
	COPPER(15, 60, 5, 20),
	TIN(15, 60, 5, 15),
    BRONZE(),
	IRON(),
    STEEL(),
	SILVER(15, 60, 5, 4),
	GOLD(),
    LEAD(15, 30, 5, 4),
    ZINC(20, 40, 5, 20),
    NICKEL(15, 60, 5, 8),
    ALUMINIUM(15, 60, 5, 10),
    MAGNESIUM(5, 10, 3, 6);

    final boolean generateOres;
    boolean genItems;
    //TODO add config for the following to test alternate settings
    //TODO add world creation option for each
    //TODO add easy world creation config to generation amount(NONE, REDUCED, NORMAL, INCREASED, ABUNDANT)
    int minY = 1;
    int maxY = 100;
    int amountPerChunk = 16;
    int amountPerBranch = 5;

	private DefinedResources()
	{
        this.generateOres = false;
	}

    private DefinedResources(int min, int max, int amountPerBranch, int amountPerChunk)
    {
        this(min, max, amountPerBranch, amountPerChunk, true);
    }
    private DefinedResources(int min, int max, int amountPerBranch, int amountPerChunk, boolean genItems)
    {
        this.generateOres = true;
        this.minY = min;
        this.maxY = max;
        this.amountPerBranch = amountPerBranch;
        this.amountPerChunk = amountPerChunk;
        this.genItems = genItems;
    }

    /** Registers first 16 ores
     *
     * @param block - block to use for the generate, uses metadata
     * @param config - config to check if the generate is allowed to generate
     */
    public static boolean registerSet(int set, Block block, Configuration config)
    {
        int meta = 0;
        for(int i = set * 16; i < (set * 16) + 16 && i < values().length; i++)
        {
            DefinedResources resource = values()[i];

            if (resource.generateOres && config.getBoolean("ore_" + resource.name().toLowerCase(), "ORE_GENERATOR", true, ""))
            {
                OreGenerator generator = new OreGenReplaceStone(resource.name().toLowerCase() + "Ore", new ItemStack(block, 1, meta), resource.minY, resource.maxY, resource.amountPerChunk, resource.amountPerBranch, "pickaxe", 1);
                GameRegistry.registerWorldGenerator(generator, meta);
            }
            meta++;
        }
        return meta >= 15;
    }
}
