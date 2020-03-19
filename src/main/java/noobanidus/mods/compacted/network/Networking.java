package noobanidus.mods.compacted.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import noobanidus.mods.compacted.Compacted;

public class Networking extends PacketHandler {
  public static Networking INSTANCE = new Networking();

  public Networking() {
    super(Compacted.MODID);
  }

  @Override
  public void registerMessages() {
    // Client -> Server
    registerMessage(RightClickedEmpty.class, RightClickedEmpty::encode, RightClickedEmpty::new, RightClickedEmpty::handle);
  }

  public static void sendTo(Object msg, ServerPlayerEntity player) {
    INSTANCE.sendToInternal(msg, player);
  }

  public static void sendToServer(Object msg) {
    INSTANCE.sendToServerInternal(msg);
  }

  public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
    INSTANCE.sendInternal(target, message);
  }
}
