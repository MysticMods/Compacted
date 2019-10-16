package noobanidus.mods.compacted.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Set;

public interface EffectiveToolItem {
  Set<Block> getEffectiveBlocks ();
  Set<Material> getEffectiveMaterials ();
}
