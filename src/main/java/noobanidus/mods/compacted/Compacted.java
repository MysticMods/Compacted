package noobanidus.mods.compacted;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import noobanidus.libs.noobutil.registrate.CustomRegistrate;
import noobanidus.mods.compacted.events.ClientRenderEvents;
import noobanidus.mods.compacted.events.Mappings;
import noobanidus.mods.compacted.init.*;
import noobanidus.mods.compacted.network.Networking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    MinecraftForge.EVENT_BUS.addGenericListener(Item.class, Mappings::onItemMappings);
  }

  private void modSetup(FMLCommonSetupEvent event) {
    Networking.INSTANCE.registerMessages();
  }

  @OnlyIn(Dist.CLIENT)
  private void clientSetup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(ClientRenderEvents.class);

    event.enqueueWork(() -> {
      RenderTypeLookup.setRenderLayer(ModBlocks.COMPACTED_STONE_TORCH.get(), RenderType.getCutout());
      RenderTypeLookup.setRenderLayer(ModBlocks.STONE_TORCH.get(), RenderType.getCutout());
      RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_COMPACTED_STONE_TORCH.get(), RenderType.getCutout());
      RenderTypeLookup.setRenderLayer(ModBlocks.COMPACTED_STONE_WALL_TORCH.get(), RenderType.getCutout());
      RenderTypeLookup.setRenderLayer(ModBlocks.STONE_WALL_TORCH.get(), RenderType.getCutout());
      RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_COMPACTED_STONE_WALL_TORCH.get(), RenderType.getCutout());
    });
  }
}
