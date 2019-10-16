package noobanidus.mods.compacted.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.compacted.Compacted;

import java.util.function.Function;
import java.util.function.Supplier;

public class Registry {
  public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Compacted.MODID);
  public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Compacted.MODID);

  public static final RegistryObject<Block> COMPACTED_COBBLESTONE = registerBlock("compacted_cobblestone", block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 8.0f)));
  public static final RegistryObject<Block> DOUBLE_COMPACTED_COBBLESTONE = registerBlock("double_compacted_cobblestone", block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f, 10.0f)));

  public static final RegistryObject<BlockItem> COMPACTED_COBBLESTONE_ITEM = registerItem("compacted_cobblestone", blockItem(COMPACTED_COBBLESTONE));
  public static final RegistryObject<BlockItem> DOUBLE_COMPACTED_COBBLESTONE_ITEM = registerItem("double_compacted_cobblestone", blockItem(DOUBLE_COMPACTED_COBBLESTONE));

  public static final RegistryObject<Item> STONE_ROD = registerItem("stone_rod", item(Item::new, Item.Properties::new));
  public static final RegistryObject<Item> COMPACTED_ROD = registerItem("compacted_rod", item(Item::new, Item.Properties::new));
  public static final RegistryObject<Item> DOUBLE_COMPACTED_ROD = registerItem("double_compacted_rod", item(Item::new, Item.Properties::new));

  public static final RegistryObject<Item> STONE_HAMMER = registerItem("stone_hammer", item(Item::new, () -> new Item.Properties().addToolType(ToolType.get("hammer"), 1).defaultMaxDamage(99)));
  public static final RegistryObject<Item> COMPACTED_HAMMER = registerItem("compacted_hammer", item(Item::new, () -> new Item.Properties().addToolType(ToolType.get("hammer"), 1).defaultMaxDamage(99)));
  public static final RegistryObject<Item> DOUBLE_COMPACTED_HAMMER = registerItem("double_compacted_hammer", item(Item::new, () -> new Item.Properties().addToolType(ToolType.get("hammer"), 1).defaultMaxDamage(99)));

  private static <T extends Item> RegistryObject<T> registerItem(final String name, final Supplier<T> supplier) {
    return ITEMS.register(name, supplier);
  }

  private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<T> supplier) {
    RegistryObject<T> result = BLOCKS.register(name, supplier);
    //ITEMS.register(name, blockItem(result));
    return result;
  }

  private static <T extends Block> Supplier<T> block (Function<Block.Properties, T> creator, Supplier<Block.Properties> properties) {
    return () -> creator.apply(properties.get());
  }

  private static <T extends Block> Supplier<BlockItem> blockItem (RegistryObject<T> block) {
    return () -> new BlockItem(block.get(), new Item.Properties().group(Compacted.GROUP));
  }

  private static <T extends Item> Supplier<T> item (Function<Item.Properties, T> creator, Supplier<Item.Properties> properties) {
    return () -> creator.apply(properties.get().group(Compacted.GROUP));
  }
}
