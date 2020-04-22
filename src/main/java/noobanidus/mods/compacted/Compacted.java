package noobanidus.mods.compacted;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import noobanidus.mods.compacted.events.ClientRenderEvents;
import noobanidus.mods.compacted.events.Mappings;
import noobanidus.mods.compacted.init.*;
import noobanidus.mods.compacted.network.Networking;
import noobanidus.mods.compacted.registrate.CustomRegistrate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(Compacted.MODID)
public class Compacted {
  public static final String MODID = "compacted";
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  public static final ItemGroup GROUP = new ItemGroup(MODID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModItems.COMPACTED_HAMMER.get());
    }
  };

  public static CustomRegistrate REGISTRATE;

  public Compacted() {
    //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);

    REGISTRATE = CustomRegistrate.create(MODID);

    REGISTRATE.itemGroup(() -> GROUP);
    ModBlocks.load();
    ModAdditionalBlocks.load();
    ModItems.load();
    ModRecipes.load();
    ModSounds.load();
    ModLang.load();
    ModTags.load();

    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    modBus.addListener(this::modSetup);

    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      modBus.addListener(this::clientSetup);
    });

    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.addListener(Mappings::onItemMappings);
  }

  private void modSetup(FMLCommonSetupEvent event) {
    Networking.INSTANCE.registerMessages();
  }

  @OnlyIn(Dist.CLIENT)
  private void clientSetup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(ClientRenderEvents.class);
  }
}
