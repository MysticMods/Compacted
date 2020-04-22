package noobanidus.mods.compacted.init;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.CompactedTags;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModTags {
  static {
    REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, (p) -> {
      p.getBuilder(CompactedTags.Items.ANDESITE).add(Items.ANDESITE, Items.POLISHED_ANDESITE).build(CompactedTags.Items.ANDESITE.getId());
      p.getBuilder(CompactedTags.Items.DIORITE).add(Items.DIORITE, Items.POLISHED_DIORITE).build(CompactedTags.Items.DIORITE.getId());
      p.getBuilder(CompactedTags.Items.GRANITE).add(Items.GRANITE, Items.POLISHED_GRANITE).build(CompactedTags.Items.GRANITE.getId());
      p.getBuilder(CompactedTags.Items.DIRT).add(Items.DIRT, Items.COARSE_DIRT).build(CompactedTags.Items.DIRT.getId());
      p.getBuilder(CompactedTags.Items.PRISMARINE).add(Items.PRISMARINE).build(CompactedTags.Items.PRISMARINE.getId());
      p.getBuilder(CompactedTags.Items.SMOOTH_STONE).add(Items.SMOOTH_STONE).build(CompactedTags.Items.SMOOTH_STONE.getId());
      p.getBuilder(CompactedTags.Items.STONE_PLAIN).add(Items.STONE).build(CompactedTags.Items.STONE_PLAIN.getId());
      p.getBuilder(CompactedTags.Items.SOUL_SAND).add(Items.SOUL_SAND).build(CompactedTags.Items.SOUL_SAND.getId());
      p.getBuilder(CompactedTags.Items.TERRACOTTA).add(Items.TERRACOTTA, Items.BLACK_TERRACOTTA, Items.BLUE_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.GRAY_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.LIME_TERRACOTTA, Items.MAGENTA_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.PINK_TERRACOTTA, Items.PURPLE_TERRACOTTA, Items.RED_TERRACOTTA, Items.WHITE_TERRACOTTA, Items.YELLOW_TERRACOTTA).build(CompactedTags.Items.TERRACOTTA.getId());
      p.getBuilder(CompactedTags.Items.TIER1).add(Tags.Items.COBBLESTONE, Tags.Items.STONE).build(CompactedTags.Items.TIER1.getId());
      p.getBuilder(CompactedTags.Items.TIER2).add(CompactedTags.Items.COMPACTED_STONE, CompactedTags.Items.COMPACTED_COBBLESTONE).build(CompactedTags.Items.TIER2.getId());
      p.getBuilder(CompactedTags.Items.TIER3).add(CompactedTags.Items.DOUBLE_COMPACTED_STONE, CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE).build(CompactedTags.Items.TIER3.getId());
    });
  }

  public static void load() {

  }
}
