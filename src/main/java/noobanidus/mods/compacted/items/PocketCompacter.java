package noobanidus.mods.compacted.items;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.PistonBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import noobanidus.mods.compacted.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class PocketCompacter extends PocketItem {

  public PocketCompacter(Properties properties) {
    super(properties);
    ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.EFFICIENCY, Enchantments.MENDING, Enchantments.UNBREAKING);
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (worldIn.isRemote()) {
      return;
    }

    if (!isActive(stack)) {
      return;
    }

    if (!(entityIn instanceof PlayerEntity)) {
      return;
    }

    if (stack.getDamage() == stack.getMaxDamage() - 1) {
      return;
    }

    ServerPlayerEntity player = (ServerPlayerEntity) entityIn;
    PlayerInventory inventory = player.inventory;
    InvWrapper wrapped = new InvWrapper(inventory);

    Int2ObjectOpenHashMap<ItemStack> cobblestone = new Int2ObjectOpenHashMap<>();
    Int2ObjectOpenHashMap<ItemStack> compacted = new Int2ObjectOpenHashMap<>();

    int iterations = (EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) * 2) + 1;
    for (int q = 0; q < iterations; q++) {
      cobblestone.clear();
      compacted.clear();

      for (int i = 0; i < wrapped.getSlots(); i++) {
        ItemStack inSlot = wrapped.getStackInSlot(i);
        if (inSlot.getItem() == Items.COBBLESTONE) {
          cobblestone.put(i, inSlot);
        } else if (inSlot.getItem() == ModBlocks.COMPACTED_COBBLESTONE.get().asItem()) {
          compacted.put(i, inSlot);
        }
      }

      if (!cobblestone.isEmpty()) {
        int needed = 9;
        int count = cobblestone.values().stream().mapToInt(ItemStack::getCount).sum();
        if (count >= 9) {
          ItemStack compactedStack = new ItemStack(ModBlocks.COMPACTED_COBBLESTONE.get(), 1);
          handleCompaction(player, inventory, wrapped, cobblestone, needed, compactedStack, stack, isSelected);
        }
      }

      if (!compacted.isEmpty()) {
        int needed = 9;
        int count = compacted.values().stream().mapToInt(ItemStack::getCount).sum();
        if (count >= 9) {
          ItemStack compactedStack = new ItemStack(ModBlocks.DOUBLE_COMPACTED_COBBLESTONE.get(), 1);
          handleCompaction(player, inventory, wrapped, compacted, needed, compactedStack, stack, isSelected);
        }
      }
    }
  }

  private void handleCompaction(PlayerEntity player, PlayerInventory inventory, InvWrapper wrapped, Int2ObjectOpenHashMap<ItemStack> cobblestone, int needed, ItemStack compactedStack, ItemStack compacter, boolean isSelected) {
    ItemStack test = ItemHandlerHelper.insertItemStacked(wrapped, compactedStack, true);
    if (!test.isEmpty()) {
      return;
    }

    for (Int2ObjectOpenHashMap.Entry<ItemStack> entry : cobblestone.int2ObjectEntrySet()) {
      ItemStack inSlot = entry.getValue();
      int slot = entry.getIntKey();
      if (inSlot.getCount() < needed) {
        needed -= inSlot.getCount();
        inventory.decrStackSize(slot, inSlot.getCount());
      } else {
        inventory.decrStackSize(slot, needed);
        break;
      }
    }
    ItemHandlerHelper.insertItemStacked(wrapped, compactedStack, false);
    compacter.damageItem(1, player, (playerIn) -> {
      if (isSelected) {
        playerIn.sendBreakAnimation(Hand.MAIN_HAND);
      }
    });
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    if (toRepair.getItem() == this) {
      if (repair.getItem() instanceof BlockItem) {
        BlockItem item = (BlockItem) repair.getItem();
        return item.getBlock() instanceof PistonBlock;
      }
    }
    return false;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new StringTextComponent(""));
    if (stack.getDamage() == stack.getMaxDamage() - 1) {
      tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.broken").setStyle(new Style().setColor(TextFormatting.DARK_RED).setBold(true)));
    }
    tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.desc1", isActive(stack) ? new TranslationTextComponent("tooltip.compacted.impacter_active").setStyle(new Style().setColor(TextFormatting.AQUA)) : new TranslationTextComponent("tooltip.compacted.impacter_inactive").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE))).setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.desc2").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
  }
}
