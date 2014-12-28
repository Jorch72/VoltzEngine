package resonant.lib.prefab.tile

import net.minecraft.block.material.Material
import resonant.lib.prefab.tile.traits.TThermal

/**
 * @since 27/05/14
 * @author tgame14
 */
abstract class TileThermal(material: Material) extends TileAdvanced(material: Material) with TThermal
{
}