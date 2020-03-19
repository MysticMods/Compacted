package noobanidus.mods.compacted.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import noobanidus.mods.compacted.init.ModBlocks;

public class CompactedStoneMaterial implements IItemTier {
  public static CompactedStoneMaterial MATERIAL = new CompactedStoneMaterial();

  @Override
  public int getMaxUses() {
    return ItemTier.STONE.getMaxUses() * 5; // 655
  }

  @Override
  public float getEfficiency() {
    return 4.1f;
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
    return 9;
  }

  @Override
  public Ingredient getRepairMaterial() {
    return Ingredient.fromItems(ModBlocks.COMPACTED_COBBLESTONE.get());
  }
}
