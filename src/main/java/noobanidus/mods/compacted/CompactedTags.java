package noobanidus.mods.compacted;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.tags.ITag.INamedTag;

public class CompactedTags {
  public static class Items {
    public static INamedTag<Item> TIER1 = itemTag("compacted", "tier_1_repair");
    public static INamedTag<Item> TIER2 = itemTag("compacted", "tier_2_repair");
    public static INamedTag<Item> TIER3 = itemTag("compacted", "tier_3_repair");
    public static INamedTag<Item> ANDESITE = itemTag("forge", "andesite");
    public static INamedTag<Item> DIORITE = itemTag("forge", "diorite");
    public static INamedTag<Item> DIRT = itemTag("forge", "dirt");
    public static INamedTag<Item> GRANITE = itemTag("forge", "granite");
    public static INamedTag<Item> PRISMARINE = itemTag("forge", "prismarine");
    public static INamedTag<Item> SMOOTH_STONE = itemTag("forge", "smooth_stone");
    public static INamedTag<Item> TERRACOTTA = itemTag("forge", "terracotta");
    public static INamedTag<Item> STONE_PLAIN = itemTag("forge", "stone_plain");
    public static INamedTag<Item> SOUL_SAND = itemTag("forge", "soul_sand");

    public static INamedTag<Item> COMPACTED_STONE = itemTag("forge", "compacted/stone/single");
    public static INamedTag<Item> DOUBLE_COMPACTED_STONE = itemTag("forge", "compacted/stone/double");

    public static INamedTag<Item> COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/single");
    public static INamedTag<Item> DOUBLE_COMPACTED_COBBLESTONE = itemTag("forge", "compacted/cobblestone/double");

    public static INamedTag<Item> STONE_ROD = itemTag("forge", "rods/stone");
    public static INamedTag<Item> COMPACTED_STONE_ROD = itemTag("forge", "rods/stone/compacted/single");
    public static INamedTag<Item> DOUBLE_COMPACTED_STONE_ROD = itemTag("forge", "rods/stone/compacted/double");

    public static INamedTag<Item> COMPRESSED_DIRT = itemTag("forge", "storage_blocks/compressed_dirt");
    public static INamedTag<Item> DOUBLE_COMPRESSED_DIRT = itemTag("forge", "storage_blocks/double_compressed_dirt");

    public static INamedTag<Item> COMPRESSED_STONE = itemTag("forge", "storage_blocks/compressed_stone");
    public static INamedTag<Item> DOUBLE_COMPRESSED_STONE = itemTag("forge", "storage_blocks/double_compressed_stone");
  }

  public static class Blocks {
    public static INamedTag<Block> COMPACTED_COBBLESTONE = blockTag("forge", "compacted/cobblestone/single");
    public static INamedTag<Block> DOUBLE_COMPACTED_COBBLESTONE = blockTag("forge", "compacted/cobblestone/double");
  }

  static ITag.INamedTag<Item> itemTag(String modid, String name) {
    return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, new ResourceLocation(Compacted.MODID, name));
  }

  static INamedTag<Block> blockTag(String modid, String name) {
    return ForgeTagHandler.makeWrapperTag(ForgeRegistries.BLOCKS, new ResourceLocation(Compacted.MODID, name));
  }
}
