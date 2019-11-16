package noobanidus.mods.compacted.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.blocks.StoneTorchBlock;
import noobanidus.mods.compacted.blocks.WallStoneTorchBlock;
import noobanidus.mods.compacted.items.HammerItem;
import noobanidus.mods.compacted.items.HammerItemProperties;
import noobanidus.mods.compacted.items.PocketCompacter;
import noobanidus.mods.compacted.materials.CompactedStoneMaterial;
import noobanidus.mods.compacted.materials.DoubleCompactedStoneMaterial;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Registry {
  public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Compacted.MODID);
  public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Compacted.MODID);

  public static final RegistryObject<Block> COMPACTED_COBBLESTONE = registerBlock("compacted_cobblestone", block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 8.0f)));
  public static final RegistryObject<Block> DOUBLE_COMPACTED_COBBLESTONE = registerBlock("double_compacted_cobblestone", block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f, 10.0f)));

  public static final RegistryObject<StoneTorchBlock> STONE_TORCH = registerBlockNoItem("stone_torch", block(StoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE)));
  public static final RegistryObject<WallStoneTorchBlock> STONE_WALL_TORCH = registerBlockNoItem("wall_stone_torch", block(WallStoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE).lootFrom(STONE_TORCH.get())));

  public static final RegistryObject<StoneTorchBlock> COMPACTED_STONE_TORCH = registerBlockNoItem("compacted_stone_torch", block(StoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE)));
  public static final RegistryObject<WallStoneTorchBlock> COMPACTED_STONE_WALL_TORCH = registerBlockNoItem("compacted_wall_stone_torch", block(WallStoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE).lootFrom(STONE_TORCH.get())));

  public static final RegistryObject<StoneTorchBlock> DOUBLE_COMPACTED_STONE_TORCH = registerBlockNoItem("double_compacted_stone_torch", block(StoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE)));
  public static final RegistryObject<WallStoneTorchBlock> DOUBLE_COMPACTED_STONE_WALL_TORCH = registerBlockNoItem("double_compacted_wall_stone_torch", block(WallStoneTorchBlock::new, () -> Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f, 0f).lightValue(14).sound(SoundType.STONE).lootFrom(STONE_TORCH.get())));

  public static final RegistryObject<BlockItem> COMPACTED_COBBLESTONE_ITEM = registerItem("compacted_cobblestone", blockItem(COMPACTED_COBBLESTONE));
  public static final RegistryObject<BlockItem> DOUBLE_COMPACTED_COBBLESTONE_ITEM = registerItem("double_compacted_cobblestone", blockItem(DOUBLE_COMPACTED_COBBLESTONE));

  public static final RegistryObject<Item> STONE_ROD = registerItem("stone_rod", item(Item::new, Item.Properties::new));
  public static final RegistryObject<Item> COMPACTED_ROD = registerItem("compacted_rod", item(Item::new, Item.Properties::new));
  public static final RegistryObject<Item> DOUBLE_COMPACTED_ROD = registerItem("double_compacted_rod", item(Item::new, Item.Properties::new));

  public static final RegistryObject<Item> STONE_HAMMER = registerItem("stone_hammer", hammer(HammerItem::new, () -> new HammerItemProperties().attackDamage(6).attackSpeed(-3.5f).tier(ItemTier.STONE).width(1)));
  public static final RegistryObject<Item> COMPACTED_HAMMER = registerItem("compacted_hammer", hammer(HammerItem::new, () -> new HammerItemProperties().attackDamage(8).attackSpeed(-3.5f).tier(CompactedStoneMaterial.MATERIAL).width(1)));
  public static final RegistryObject<Item> DOUBLE_COMPACTED_HAMMER = registerItem("double_compacted_hammer", hammer(HammerItem::new, () -> new HammerItemProperties().attackDamage(10).attackSpeed(-3.5f).tier(DoubleCompactedStoneMaterial.MATERIAL).width(1)));

  public static final RegistryObject<Item> POCKET_COMPACTER = registerItem("pocket_compacter", item(PocketCompacter::new, () -> new Item.Properties().rarity(Rarity.UNCOMMON).maxDamage(620)));

  public static final RegistryObject<Item> STONE_TORCH_ITEM = registerItem("stone_torch", item(properties -> new WallOrFloorItem(STONE_TORCH.get(), STONE_WALL_TORCH.get(), properties), Item.Properties::new));
  public static final RegistryObject<Item> COMPACTED_STONE_TORCH_ITEM = registerItem("compacted_stone_torch", item(properties -> new WallOrFloorItem(COMPACTED_STONE_TORCH.get(), COMPACTED_STONE_WALL_TORCH.get(), properties), Item.Properties::new));
  public static final RegistryObject<Item> DOUBLE_STONE_TORCH_ITEM = registerItem("double_compacted_stone_torch", item(properties -> new WallOrFloorItem(DOUBLE_COMPACTED_STONE_TORCH.get(), DOUBLE_COMPACTED_STONE_WALL_TORCH.get(), properties), Item.Properties::new));

  private static <T extends Item> RegistryObject<T> registerItem(final String name, final Supplier<T> supplier) {
    return ITEMS.register(name, supplier);
  }

  private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<T> supplier) {
    RegistryObject<T> result = BLOCKS.register(name, supplier);
    //ITEMS.register(name, blockItem(result));
    return result;
  }

  private static <T extends Block> RegistryObject<T> registerBlockNoItem(final String name, final Supplier<T> supplier) {
    RegistryObject<T> result = BLOCKS.register(name, supplier);
    return result;
  }

  private static <T extends Block> Supplier<T> block(Function<Block.Properties, T> creator, Supplier<Block.Properties> properties) {
    return () -> creator.apply(properties.get());
  }

  private static <T extends Block> Supplier<BlockItem> blockItem(RegistryObject<T> block) {
    return () -> new BlockItem(block.get(), new Item.Properties().group(Compacted.GROUP));
  }

  private static <T extends Item> Supplier<T> item(Function<Item.Properties, T> creator, Supplier<Item.Properties> properties) {
    return () -> creator.apply(properties.get().group(Compacted.GROUP));
  }

  private static Supplier<Item> hammer(Function<HammerItemProperties, HammerItem> creator, Supplier<HammerItemProperties> properties) {
    return () -> creator.apply(properties.get().setGroup(Compacted.GROUP));
  }
}
