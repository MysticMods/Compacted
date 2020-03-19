package noobanidus.mods.compacted.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import noobanidus.mods.compacted.init.ModBlocks;

public class DoubleCompactedStoneMaterial implements IItemTier {
  public static DoubleCompactedStoneMaterial MATERIAL = new DoubleCompactedStoneMaterial();

  @Override
  public int getMaxUses() {
    return (int) (CompactedStoneMaterial.MATERIAL.getMaxUses() * 2.5); // 1637
  }

  @Override
  public float getEfficiency() {
    return 4.15f;
  }

  @Override
  public float getAttackDamage() {
    return 2f;
  }

  @Override
  public int getHarvestLevel() {
    return 1;
  }

  @Override
  public int getEnchantability() {
    return 13;
  }

  @Override
  public Ingredient getRepairMaterial() {
    return Ingredient.fromItems(ModBlocks.DOUBLE_COMPACTED_COBBLESTONE.get());
  }
}
