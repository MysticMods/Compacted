package noobanidus.mods.compacted.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.init.Registry;
import noobanidus.mods.compacted.items.EffectiveToolItem;
import noobanidus.mods.compacted.materials.CompactedStoneMaterial;
import noobanidus.mods.compacted.materials.DoubleCompactedStoneMaterial;

public class RepairRecipe extends SpecialRecipe {
  public RepairRecipe(ResourceLocation idIn) {
    super(idIn);
  }

  @Override
  public boolean matches(CraftingInventory inv, World worldIn) {
    IItemTier tier = null;
    boolean found = false;
    int slot = -1;
    int count = 0;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack inSlot = inv.getStackInSlot(i);
      if (inSlot.isEmpty()) {
        continue;
      }

      if (inSlot.getItem() instanceof EffectiveToolItem) {
        if (found) {
          return false;
        }

        found = true;
        tier = ((ToolItem)inSlot.getItem()).getTier();
        slot = i;
      }
    }
    if (!found) {
      return false;
    }
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (i == slot) {
        continue;
      }

      ItemStack inSlot = inv.getStackInSlot(i);

      if (tier.getRepairMaterial().test(inSlot)) {
        count++;
      } else if (!inSlot.isEmpty()) {
        return false;
      }
    }

    return count > 0;
  }

  @Override
  public ItemStack getCraftingResult(CraftingInventory inv) {
    IItemTier tier = null;
    ItemStack tool = ItemStack.EMPTY;
    int slot = -1;
    int count = 0;

    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack inSlot = inv.getStackInSlot(i);
      if (inSlot.isEmpty()) {
        continue;
      }

      if (inSlot.getItem() instanceof EffectiveToolItem) {
        tier = ((ToolItem)inSlot.getItem()).getTier();
        tool = inSlot.copy();
        slot = i;
        break;
      }
    }
    if (tier == null) {
      return ItemStack.EMPTY;
    }
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      if (i == slot) {
        continue;
      }

      ItemStack inSlot = inv.getStackInSlot(i);

      if (tier.getRepairMaterial().test(inSlot)) {
        count++;
      }
    }
    if (count == 0) {
      return ItemStack.EMPTY;
    }

    int toRepair = (int) Math.floor((((float) tier.getMaxUses()) * 0.1) * count);
    tool.setDamage(tool.getDamage() - toRepair);
    return tool;
  }

  @Override
  public boolean canFit(int width, int height) {
    return true;
  }

  @Override
  public IRecipeSerializer<?> getSerializer() {
    return Registry.REPAIR_RECIPE.get();
  }
}
