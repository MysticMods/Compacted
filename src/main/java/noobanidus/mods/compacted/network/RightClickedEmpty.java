package noobanidus.mods.compacted.network;


import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;
import noobanidus.mods.compacted.init.ModItems;
import noobanidus.mods.compacted.init.ModSounds;

import java.util.function.Supplier;

public class RightClickedEmpty {
  public RightClickedEmpty(PacketBuffer buffer) {
  }

  public RightClickedEmpty() {
  }

  public void encode(PacketBuffer buf) {
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  private static void handle(RightClickedEmpty message, Supplier<NetworkEvent.Context> context) {
    final ServerPlayerEntity player = context.get().getSender();
    if (player != null) {
      final ItemStack stack = player.getHeldItemMainhand();
      if (stack.getItem() == ModItems.POCKET_IMPACTER.get()) {
        CompoundNBT nbt = stack.getOrCreateTag();
        boolean cur = true;
        if (nbt.contains("active", Constants.NBT.TAG_BYTE)) {
          cur = nbt.getBoolean("active");
        }
        nbt.putBoolean("active", !cur);
        player.world.playSound(null, player.getPosition(), ModSounds.DING.get(), SoundCategory.PLAYERS, 0.2f, 1);
      }
    }

    context.get().setPacketHandled(true);
  }
}

