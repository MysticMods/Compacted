package noobanidus.mods.compacted.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.CompactedTags;
import noobanidus.mods.compacted.recipes.ImpacterRecipe;
import noobanidus.mods.compacted.recipes.RepairRecipe;

import javax.annotation.Nullable;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModRecipes {
  public static final RegistryEntry<ImpacterRecipe.ImpacterSerializer> IMAPCTER_SERIALIZER = REGISTRATE.recipeSerializer("impacter_recipe", ImpacterRecipe.ImpacterSerializer::new).register();
  public static final RegistryEntry<RepairRecipe.RepairSerializer> REPAIR_SERIALIZER = REGISTRATE.recipeSerializer("repair_recipe", RepairRecipe.RepairSerializer::new).register();

  public static void compacter(Block start, RegistryEntry<? extends Block> compressed, RegistryEntry<? extends Block> doubleCompressed, Tag<Item> tag, RegistrateRecipeProvider p) {
    String startName = start.getRegistryName().getPath();
    String compressedName = compressed.getId().getPath();
    String doubleName = doubleCompressed.getId().getPath();
    ShapelessRecipeBuilder.shapelessRecipe(compressed.get(), 1)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addIngredient(tag)
        .addCriterion("has_" + startName, p.hasItem(tag))
        .build(p, new ResourceLocation(Compacted.MODID, compressedName + "_from_" + startName));
    ShapelessRecipeBuilder.shapelessRecipe(doubleCompressed.get(), 1)
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addIngredient(compressed.get())
        .addCriterion("has_" + compressedName, p.hasItem(compressed.get()))
        .build(p, new ResourceLocation(Compacted.MODID, doubleName + "_from_" + compressedName));
    // Uncompression
    ShapelessRecipeBuilder.shapelessRecipe(start, 9)
        .addIngredient(compressed.get())
        .addCriterion("has_" + compressedName, p.hasItem(compressed.get()))
        .build(p, new ResourceLocation(Compacted.MODID, startName + "_from_" + compressedName));
    ShapelessRecipeBuilder.shapelessRecipe(compressed.get(), 9)
        .addIngredient(doubleCompressed.get())
        .addCriterion("has_" + doubleName, p.hasItem(doubleCompressed.get()))
        .build(p, new ResourceLocation(Compacted.MODID, compressedName + "_from_" + doubleName));
  }

  static {
    REGISTRATE.addDataGenerator(ProviderType.RECIPE, (ctx) -> {
      compacter(Blocks.ANDESITE, ModAdditionalBlocks.COMPACTED_ANDESITE, ModAdditionalBlocks.DOUBLE_COMPACTED_ANDESITE, CompactedTags.Items.ANDESITE, ctx);
      compacter(Blocks.DIORITE, ModAdditionalBlocks.COMPACTED_DIORITE, ModAdditionalBlocks.DOUBLE_COMPACTED_DIORITE, CompactedTags.Items.DIORITE, ctx);
      compacter(Blocks.DIRT, ModAdditionalBlocks.COMPACTED_DIRT, ModAdditionalBlocks.DOUBLE_COMPACTED_DIRT, CompactedTags.Items.DIRT
          , ctx);
      compacter(Blocks.END_STONE, ModAdditionalBlocks.COMPACTED_END_STONE, ModAdditionalBlocks.DOUBLE_COMPACTED_END_STONE, Tags.Items.END_STONES, ctx);
      compacter(Blocks.GRANITE, ModAdditionalBlocks.COMPACTED_GRANITE, ModAdditionalBlocks.DOUBLE_COMPACTED_GRANITE, CompactedTags.Items.GRANITE, ctx);
      compacter(Blocks.GRAVEL, ModAdditionalBlocks.COMPACTED_GRAVEL, ModAdditionalBlocks.DOUBLE_COMPACTED_GRAVEL, Tags.Items.GRAVEL, ctx);
      compacter(Blocks.NETHERRACK, ModAdditionalBlocks.COMPACTED_NETHERRACK, ModAdditionalBlocks.DOUBLE_COMPACTED_NETHERRACK, Tags.Items.NETHERRACK, ctx);
      compacter(Blocks.PRISMARINE, ModAdditionalBlocks.COMPACTED_PRISMARINE, ModAdditionalBlocks.DOUBLE_COMPACTED_PRISMARINE, CompactedTags.Items.PRISMARINE, ctx);
      compacter(Blocks.RED_SAND, ModAdditionalBlocks.COMPACTED_RED_SAND, ModAdditionalBlocks.DOUBLE_COMPACTED_RED_SAND, Tags.Items.SAND_RED, ctx);
      compacter(Blocks.REDSTONE_BLOCK, ModAdditionalBlocks.COMPACTED_REDSTONE_BLOCK, ModAdditionalBlocks.DOUBLE_COMPACTED_REDSTONE_BLOCK, Tags.Items.STORAGE_BLOCKS_REDSTONE, ctx);
      compacter(Blocks.SAND, ModAdditionalBlocks.COMPACTED_SAND, ModAdditionalBlocks.DOUBLE_COMPACTED_SAND, Tags.Items.SAND_COLORLESS, ctx);
      compacter(Blocks.SMOOTH_STONE, ModAdditionalBlocks.COMPACTED_SMOOTH_STONE, ModAdditionalBlocks.DOUBLE_COMPACTED_SMOOTH_STONE, CompactedTags.Items.SMOOTH_STONE, ctx);
      compacter(Blocks.TERRACOTTA, ModAdditionalBlocks.COMPACTED_TERRACOTTA, ModAdditionalBlocks.DOUBLE_COMPACTED_TERRACOTTA, CompactedTags.Items.TERRACOTTA, ctx);
      compacter(Blocks.STONE, ModAdditionalBlocks.COMPACTED_STONE, ModAdditionalBlocks.DOUBLE_COMPACTED_STONE, CompactedTags.Items.STONE_PLAIN, ctx);
      compacter(Blocks.SOUL_SAND, ModAdditionalBlocks.COMPACTED_SOUL_SAND, ModAdditionalBlocks.DOUBLE_COMPACTED_SOUL_SAND, CompactedTags.Items.SOUL_SAND, ctx);
    });
  }

  public static void load() {

  }
}
