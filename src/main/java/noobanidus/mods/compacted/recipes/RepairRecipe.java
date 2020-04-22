package noobanidus.mods.compacted.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.CompactedTags;
import noobanidus.mods.compacted.init.ModRecipes;
import noobanidus.mods.compacted.items.EffectiveToolItem;
import noobanidus.mods.compacted.items.PaxelItem;
import noobanidus.mods.compacted.materials.CompactedStoneMaterial;
import noobanidus.mods.compacted.materials.DoubleCompactedStoneMaterial;

@SuppressWarnings("Duplicates")
public class RepairRecipe extends SpecialRecipe {
  public RepairRecipe(ResourceLocation idIn) {
    super(idIn);
  }

  @Override
  public boolean matches(CraftingInventory inv, World worldIn) {
    boolean found = false;
    int slot = -1;
    int count = 0;
    Ingredient tier11 = Ingredient.fromTag(Tags.Items.STONE);
    Ingredient tier12 = Ingredient.fromTag(Tags.Items.COBBLESTONE);
    Ingredient tier2 = Ingredient.fromTag(CompactedTags.Items.COMPACTED_COBBLESTONE);
    Ingredient tier3 = Ingredient.fromTag(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE);
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

      int thisTier = 0;
      if (tier11.test(inSlot) || tier12.test(inSlot)) {
        thisTier = 1;
      } else if (tier2.test(inSlot)) {
        thisTier = 2;
      } else if (tier3.test(inSlot)) {
        thisTier = 3;
      } else if (!inSlot.isEmpty()) {
        return false;
      }

      if (thisTier > 0) {
        count++;
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

    Ingredient tier11 = Ingredient.fromTag(Tags.Items.STONE);
    Ingredient tier12 = Ingredient.fromTag(Tags.Items.COBBLESTONE);
    Ingredient tier2 = Ingredient.fromTag(CompactedTags.Items.COMPACTED_COBBLESTONE);
    Ingredient tier3 = Ingredient.fromTag(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE);

    int tierNum = 0;

    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack inSlot = inv.getStackInSlot(i);
      if (inSlot.isEmpty()) {
        continue;
      }


      if (inSlot.getItem() instanceof EffectiveToolItem || inSlot.getItem() instanceof PaxelItem) {
        tier = ((ToolItem) inSlot.getItem()).getTier();
        tool = inSlot.copy();
        slot = i;
        if (tier == ItemTier.STONE) {
          tierNum = 1;
        } else if (tier == CompactedStoneMaterial.MATERIAL) {
          tierNum = 2;
        } else if (tier == DoubleCompactedStoneMaterial.MATERIAL) {
          tierNum = 3;
        }
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

      int thisTier = 0;
      if (tier11.test(inSlot) || tier12.test(inSlot)) {
        thisTier = 1;
      } else if (tier2.test(inSlot)) {
        thisTier = 2;
      } else if (tier3.test(inSlot)) {
        thisTier = 3;
      } else if (!inSlot.isEmpty()) {
        return ItemStack.EMPTY;
      }

      if (thisTier == tierNum) {
        count += 2;
      } else if (thisTier < tierNum) {
        count += Math.min(1, 2 - (tierNum - thisTier));
      } else {
        count += 2 + 2 * (thisTier - tierNum);
      }
    }
    if (count == 0) {
      return ItemStack.EMPTY;
    }

    int toRepair = (int) Math.floor((((float) tier.getMaxUses()) * 0.05) * count);
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
