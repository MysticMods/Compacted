package noobanidus.mods.compacted;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class CompactedTags {
  public static class Items {
    public static Tag<Item> COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/single");
    public static Tag<Item> DOUBLE_COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/double");

    public static Tag<Item> STONE_ROD = itemTag("forge", "rods/stone");
    public static Tag<Item> COMPACTED_STONE_ROD = itemTag("forge", "rods/stone/compacted/single");
    public static Tag<Item> DOUBLE_COMPACTED_STONE_ROD = itemTag("forge", "rods/stone/compacted/double");
  }

  public static class Blocks {
    public static Tag<Block> COMPACTED_COBBLESTONE = blockTag("forge", "compacted/cobblestone/single");
    public static Tag<Block> DOUBLE_COMPACTED_COBBLESTONE = blockTag("forge", "compacted/cobblestone/double");
  }

  static <T extends Tag<?>> T tag(Function<ResourceLocation, T> creator, String modid, String name) {
    return creator.apply(new ResourceLocation(modid, name));
  }

  static Tag<Item> itemTag(String modid, String name) {
    return tag(ItemTags.Wrapper::new, modid, name);
  }

  static Tag<Block> blockTag(String modid, String name) {
    return tag(BlockTags.Wrapper::new, modid, name);
  }

}
