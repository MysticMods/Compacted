package noobanidus.mods.compacted;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import noobanidus.mods.compacted.events.ClientRenderEvents;
import noobanidus.mods.compacted.init.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

@Mod(Compacted.MODID)
public class Compacted {
  public static Compacted INSTANCE;
  public static final String MODID = "compacted";
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  public static ItemGroup GROUP;

  public Compacted() {
    //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);
    INSTANCE = this;
    GROUP = new CompactedItemGroup();

    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    modBus.addListener(this::modSetup);

    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      modBus.addListener(this::clientSetup);
    });

    Registry.BLOCKS.register(modBus);
    Registry.ITEMS.register(modBus);
    Registry.SERIALIZERS.register(modBus);
    Registry.SOUND.register(modBus);

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

  public static class Tags {
    public static Tag<Item> COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/single");
    public static Tag<Item> DOUBLE_COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/double");

    static <T extends Tag<?>> T tag(Function<ResourceLocation, T> creator, String modid, String name) {
      return creator.apply(new ResourceLocation(modid, name));
    }

    static Tag<Item> itemTag(String modid, String name) {
      return tag(ItemTags.Wrapper::new, modid, name);
    }
  }
}
