package noobanidus.mods.compacted.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CompacterRecipe extends SpecialRecipe {
  public CompacterRecipe(ResourceLocation idIn) {
    super(idIn);
  }

  @Override
  public boolean matches(CraftingInventory inv, World worldIn) {
    return false;
  }

  @Override
  public ItemStack getCraftingResult(CraftingInventory inv) {
    return null;
  }

  @Override
  public boolean canFit(int width, int height) {
    return false;
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return null;
  }
}
