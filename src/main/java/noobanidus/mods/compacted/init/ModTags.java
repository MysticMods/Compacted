package noobanidus.mods.compacted.init;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.CompactedTags;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

@SuppressWarnings("unchecked")
public class ModTags {
  static {
    REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, (p) -> {
      p.getOrCreateBuilder(CompactedTags.Items.ANDESITE).add(Items.ANDESITE, Items.POLISHED_ANDESITE);
      p.getOrCreateBuilder(CompactedTags.Items.DIORITE).add(Items.DIORITE, Items.POLISHED_DIORITE);
      p.getOrCreateBuilder(CompactedTags.Items.GRANITE).add(Items.GRANITE, Items.POLISHED_GRANITE);
      p.getOrCreateBuilder(CompactedTags.Items.DIRT).add(Items.DIRT, Items.COARSE_DIRT);
      p.getOrCreateBuilder(CompactedTags.Items.PRISMARINE).add(Items.PRISMARINE);
      p.getOrCreateBuilder(CompactedTags.Items.SMOOTH_STONE).add(Items.SMOOTH_STONE);
      p.getOrCreateBuilder(CompactedTags.Items.STONE_PLAIN).add(Items.STONE);
      p.getOrCreateBuilder(CompactedTags.Items.SOUL_SAND).add(Items.SOUL_SAND);
      p.getOrCreateBuilder(CompactedTags.Items.TERRACOTTA).add(Items.TERRACOTTA, Items.BLACK_TERRACOTTA, Items.BLUE_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.GRAY_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.LIME_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.PINK_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.RED_TERRACOTTA, Items.WHITE_TERRACOTTA, Items.YELLOW_TERRACOTTA);
      p.getOrCreateBuilder(CompactedTags.Items.TIER1).addTags(Tags.Items.COBBLESTONE, Tags.Items.STONE);
      p.getOrCreateBuilder(CompactedTags.Items.TIER2).addTags(CompactedTags.Items.COMPACTED_STONE, CompactedTags.Items.COMPACTED_COBBLESTONE);
      p.getOrCreateBuilder(CompactedTags.Items.TIER3).addTags(CompactedTags.Items.DOUBLE_COMPACTED_STONE, CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE);

      p.getOrCreateBuilder(CompactedTags.Items.COMPRESSED_DIRT).add(ModAdditionalBlocks.COMPACTED_DIRT.get().asItem());
      p.getOrCreateBuilder(CompactedTags.Items.COMPRESSED_STONE).add(ModAdditionalBlocks.COMPACTED_STONE.get().asItem(), ModAdditionalBlocks.COMPACTED_ANDESITE.get().asItem(), ModAdditionalBlocks.COMPACTED_DIORITE.get().asItem(), ModAdditionalBlocks.COMPACTED_GRANITE.get().asItem(), ModAdditionalBlocks.COMPACTED_SMOOTH_STONE.get().asItem());

      p.getOrCreateBuilder(CompactedTags.Items.DOUBLE_COMPRESSED_DIRT).add(ModAdditionalBlocks.DOUBLE_COMPACTED_DIRT.get().asItem());
      p.getOrCreateBuilder(CompactedTags.Items.DOUBLE_COMPRESSED_STONE).add(ModAdditionalBlocks.DOUBLE_COMPACTED_STONE.get().asItem(), ModAdditionalBlocks.DOUBLE_COMPACTED_ANDESITE.get().asItem(), ModAdditionalBlocks.DOUBLE_COMPACTED_DIORITE.get().asItem(), ModAdditionalBlocks.DOUBLE_COMPACTED_GRANITE.get().asItem(), ModAdditionalBlocks.DOUBLE_COMPACTED_SMOOTH_STONE.get().asItem());
    });
  }

  public static void load() {

  }
}
