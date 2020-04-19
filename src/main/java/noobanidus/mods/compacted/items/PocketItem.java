package noobanidus.mods.compacted.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noobanidus.mods.compacted.network.Networking;
import noobanidus.mods.compacted.network.RightClickedEmpty;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public abstract class PocketItem extends Item {
  protected Set<Enchantment> ALLOWED_ENCHANTMENTS;

  protected PocketItem(Properties properties) {
    super(properties);
    MinecraftForge.EVENT_BUS.addListener(this::onRightClickEmpty);
  }

  protected boolean isActive(ItemStack stack) {
    if (stack.getItem() != this) {
      return false;
    }

    CompoundNBT nbt = stack.getOrCreateTag();
    if (nbt.contains("active", Constants.NBT.TAG_BYTE)) {
      return nbt.getBoolean("active");
    }

    return true;
  }

  public static void toggleItem(ItemStack stack) {
    CompoundNBT nbt = stack.getOrCreateTag();
    boolean cur = true;
    if (nbt.contains("active", Constants.NBT.TAG_BYTE)) {
      cur = nbt.getBoolean("active");
    }
    nbt.putBoolean("active", !cur);
  }

  public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
    final PlayerEntity player = event.getPlayer();
    final ItemStack heldItem = player.getHeldItemMainhand();
    if (heldItem.getItem() != this || !player.isSneaking()) {
      return;
    }
    RightClickedEmpty packet = new RightClickedEmpty();
    Networking.sendToServer(packet);
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
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    return stack.getItem() == this && ALLOWED_ENCHANTMENTS.contains(enchantment);
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
  public int getItemEnchantability(ItemStack stack) {
    return 10;
  }
}
