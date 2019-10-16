package noobanidus.mods.compacted;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import noobanidus.mods.compacted.config.Configuration;
import noobanidus.mods.compacted.events.ClientRenderEvents;
import noobanidus.mods.compacted.init.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Compacted.MODID)
public class Compacted {
  public static Compacted INSTANCE;
  public static final String MODID = "compacted";
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  public static ItemGroup GROUP;

  public Compacted() {
    INSTANCE = this;
    GROUP = new CompactedItemGroup();

    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    modBus.addListener(this::modSetup);

    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      modBus.addListener(this::clientSetup);
    });

    Registry.BLOCKS.register(modBus);
    Registry.ITEMS.register(modBus);

    MinecraftForge.EVENT_BUS.register(this);
  }

  private void modSetup(FMLCommonSetupEvent event) {
  }

  @OnlyIn(Dist.CLIENT)
  private void clientSetup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(ClientRenderEvents.class);
  }

  public void serverStarting(FMLServerStartingEvent event) {
  }

  public static class CompactedItemGroup extends ItemGroup {
    public CompactedItemGroup() {
      super("compacted");
    }

    @Override
    public ItemStack createIcon() {
      return new ItemStack(Registry.COMPACTED_HAMMER.get());
    }
  }
}
