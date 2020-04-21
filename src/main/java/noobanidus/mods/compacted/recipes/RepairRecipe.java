package noobanidus.mods.compacted.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noobanidus.mods.compacted.init.ModRecipes;
import noobanidus.mods.compacted.items.EffectiveToolItem;
import noobanidus.mods.compacted.items.PaxelItem;

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

      if (inSlot.getItem() instanceof EffectiveToolItem || inSlot.getItem() instanceof PaxelItem) {
        if (found) {
          return false;
        }

        found = true;
        tier = ((ToolItem) inSlot.getItem()).getTier();
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

      if (inSlot.getItem() instanceof EffectiveToolItem || inSlot.getItem() instanceof PaxelItem) {
        tier = ((ToolItem) inSlot.getItem()).getTier();
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
    return ModRecipes.REPAIR_SERIALIZER.get();
  }

  public static class RepairSerializer extends SpecialRecipeSerializer<RepairRecipe> {
    public RepairSerializer() {
      super(RepairRecipe::new);
    }
  }
}
