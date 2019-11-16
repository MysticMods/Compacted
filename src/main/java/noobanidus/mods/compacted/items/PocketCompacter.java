package noobanidus.mods.compacted.items;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.PistonBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import noobanidus.mods.compacted.init.Registry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PocketCompacter extends Item {
  private static Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.EFFICIENCY, Enchantments.MENDING, Enchantments.UNBREAKING);

  public PocketCompacter(Properties properties) {
    super(properties);
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (worldIn.isRemote()) {
      return;
    }

    if (!(entityIn instanceof PlayerEntity)) {
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
        } else if (inSlot.getItem() == Registry.COMPACTED_COBBLESTONE_ITEM.get()) {
          compacted.put(i, inSlot);
        }
      }

      if (!cobblestone.isEmpty()) {
        int needed = 9;
        int count = cobblestone.values().stream().mapToInt(ItemStack::getCount).sum();
        if (count >= 9) {
          ItemStack compactedStack = new ItemStack(Registry.COMPACTED_COBBLESTONE_ITEM.get(), 1);
          handleCompaction(player, inventory, wrapped, cobblestone, needed, compactedStack, stack, isSelected);
        }
      }

      if (!compacted.isEmpty()) {
        int needed = 9;
        int count = compacted.values().stream().mapToInt(ItemStack::getCount).sum();
        if (count >= 9) {
          ItemStack compactedStack = new ItemStack(Registry.DOUBLE_COMPACTED_COBBLESTONE_ITEM.get(), 1);
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
  public boolean isEnchantable(ItemStack stack) {
    return true;
  }

  @Override
  public int getItemEnchantability() {
    return 10;
  }

  @Override
  public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
    if (stack.getItem() != this) return false;

    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(book);
    for (Enchantment ench : map.keySet()) {
      if (!ALLOWED_ENCHANTMENTS.contains(ench)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int getItemEnchantability(ItemStack stack) {
    return 10;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    return stack.getItem() == this && ALLOWED_ENCHANTMENTS.contains(enchantment);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.desc1").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.desc2").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
  }
}
