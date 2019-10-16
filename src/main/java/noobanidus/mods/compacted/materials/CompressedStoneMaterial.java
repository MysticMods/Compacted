package noobanidus.mods.compacted.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import noobanidus.mods.compacted.init.Registry;

public class CompressedStoneMaterial implements IItemTier {
  @Override
  public int getMaxUses() {
    return 490;
  }

  @Override
  public float getEfficiency() {
    return 4f;
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
    return 15;
  }

  @Override
  public Ingredient getRepairMaterial() {
    return Ingredient.fromItems((IItemProvider) () -> Registry.COMPACTED_COBBLESTONE_ITEM.orElse(null));
  }
}
