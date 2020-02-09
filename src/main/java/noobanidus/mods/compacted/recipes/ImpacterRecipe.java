package noobanidus.mods.compacted.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.init.Registry;

public class ImpacterRecipe extends SpecialRecipe {
  public static ResourceLocation UID = new ResourceLocation(Compacted.MODID, "impacter");

  public ImpacterRecipe() {
    super(UID);
  }

  public ImpacterRecipe(ResourceLocation idIn) {
    super(idIn);
  }

  @Override
  public boolean matches(CraftingInventory inv, World worldIn) {
    boolean found = false;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack inSlot = inv.getStackInSlot(i);
      if (inSlot.isEmpty()) {
        continue;
      }

      if (inSlot.getItem() == Registry.POCKET_IMPACTER.get()) {
        if (found) {
          return false;
        }

        found = true;
      }
    }
    return found;
  }

  @Override
  public ItemStack getCraftingResult(CraftingInventory inv) {
    ItemStack impacter = ItemStack.EMPTY;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (inv.getStackInSlot(i).getItem() == Registry.POCKET_IMPACTER.get()) {
        impacter = inv.getStackInSlot(i);
        break;
      }
    }
    if (impacter.isEmpty()) {
      return impacter;
    }

    CompoundNBT tag = impacter.getOrCreateTag();
    if (tag.contains("consumed", Constants.NBT.TAG_LONG)) {
      long consumed = tag.getLong("consumed");
      if (consumed == 0) {
        return ItemStack.EMPTY;
      }
      ItemStack cobblestone = new ItemStack(Items.COBBLESTONE);
      int count = (int) Math.min(cobblestone.getMaxStackSize(), consumed);
      cobblestone.setCount(count);
      tag.putLong("consumed", consumed - count);
      //impacter.setTag(tag);
      return cobblestone;
    } else {
      return ItemStack.EMPTY;
    }
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
    NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (inv.getStackInSlot(i).getItem() == Registry.POCKET_IMPACTER.get()) {
        result.set(i, inv.getStackInSlot(i));
        inv.setInventorySlotContents(i, ItemStack.EMPTY);
      }
    }
    return result;
  }

  @Override
  public boolean canFit(int width, int height) {
    return true;
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return Registry.IMPACTER_RECIPE.get();
  }
}
