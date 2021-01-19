package noobanidus.mods.compacted.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.CompactedTags;
import noobanidus.mods.compacted.blocks.StoneTorchBlock;
import noobanidus.mods.compacted.blocks.WallStoneTorchBlock;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModBlocks {

  public static final BlockEntry<Block> COMPACTED_COBBLESTONE = REGISTRATE.block("compacted_cobblestone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(3.0f, 8.0f))
      .recipe((ctx, p) -> {
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addIngredient(Tags.Items.COBBLESTONE)
            .addCriterion("has_cobblestone", RegistrateRecipeProvider.hasItem(Tags.Items.COBBLESTONE))
            .build(p, new ResourceLocation(Compacted.MODID, "compacted_cobblestone_from_cobblestone"));
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 9)
            .addIngredient(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
            .addCriterion("has_double_compacted", RegistrateRecipeProvider.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
            .build(p, new ResourceLocation(Compacted.MODID, "compacted_cobblestone_from_double_compacted_cobblestone"));
        ShapelessRecipeBuilder.shapelessRecipe(Items.COBBLESTONE, 9)
            .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
            .addCriterion("has_double_compacted", RegistrateRecipeProvider.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
            .build(p, new ResourceLocation(Compacted.MODID, "cobblestone_from_compacted_cobblestone"));
      })
      .item()
      .tag(CompactedTags.Items.COMPACTED_COBBLESTONE)
      .build()
      .tag(CompactedTags.Blocks.COMPACTED_COBBLESTONE)
      .register();

  public static final BlockEntry<Block> DOUBLE_COMPACTED_COBBLESTONE = REGISTRATE.block("double_compacted_cobblestone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(4.0f, 10.0f))
      .recipe((ctx, p) ->
          ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addIngredient(CompactedTags.Items.COMPACTED_COBBLESTONE)
              .addCriterion("has_compacted_cobblestone", RegistrateRecipeProvider.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
              .build(p, new ResourceLocation(Compacted.MODID, "double_compacted_cobblestone_from_compacted_cobblestone"))
      )
      .item()
      .tag(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
      .build()
      .tag(CompactedTags.Blocks.DOUBLE_COMPACTED_COBBLESTONE)
      .register();

  public static final BlockEntry<StoneTorchBlock> STONE_TORCH = REGISTRATE.block("stone_torch", Material.MISCELLANEOUS, StoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .item((block, props) -> new WallOrFloorItem(block, ModBlocks.STONE_WALL_TORCH.get(), props))
      .model((ctx, p) -> p.generated(ctx::getEntry, new ResourceLocation(Compacted.MODID, "block/stone_torch")))
      .build()
      .blockstate(NonNullBiConsumer.noop())
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("C")
            .patternLine("R")
            .key('R', CompactedTags.Items.STONE_ROD)
            .key('C', Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
            .addCriterion("has_rod", RegistrateRecipeProvider.hasItem(CompactedTags.Items.STONE_ROD))
            .addCriterion("has_coal", RegistrateRecipeProvider.hasItem(Items.COAL))
            .build(p, new ResourceLocation(Compacted.MODID, "stone_torch"));
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 9)
            .addIngredient(ModBlocks.COMPACTED_STONE_TORCH.get())
            .addCriterion("has_compacted_torch", RegistrateRecipeProvider.hasItem(ModBlocks.COMPACTED_STONE_TORCH.get()))
            .build(p, new ResourceLocation(Compacted.MODID, "stone_torches_from_compacted_torch"));
      })
      // TODO: RECIPE
      .register();

  public static final BlockEntry<WallStoneTorchBlock> STONE_WALL_TORCH = REGISTRATE.block("wall_stone_torch", Material.MISCELLANEOUS, WallStoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .blockstate(NonNullBiConsumer.noop())
      .setData(ProviderType.LANG, NonNullBiConsumer.noop())
      .register();

  public static final BlockEntry<StoneTorchBlock> COMPACTED_STONE_TORCH = REGISTRATE.block("compacted_stone_torch", Material.MISCELLANEOUS, StoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .item((block, props) -> new WallOrFloorItem(block, ModBlocks.COMPACTED_STONE_WALL_TORCH.get(), props))
      .model((ctx, p) -> p.generated(ctx::getEntry, new ResourceLocation(Compacted.MODID, "block/compacted_stone_torch")))
      .build()
      .blockstate(NonNullBiConsumer.noop())
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("C")
            .patternLine("R")
            .key('R', CompactedTags.Items.COMPACTED_STONE_ROD)
            .key('C', Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
            .addCriterion("has_rod", RegistrateRecipeProvider.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .addCriterion("has_coal", RegistrateRecipeProvider.hasItem(Items.COAL))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 9)
            .addIngredient(ModBlocks.DOUBLE_COMPACTED_STONE_TORCH.get())
            .addCriterion("has_double_compacted_torch", RegistrateRecipeProvider.hasItem(ModBlocks.DOUBLE_COMPACTED_STONE_TORCH.get()))
            .build(p, new ResourceLocation(Compacted.MODID, "compacted_stone_torches_from_double_compacted_torch"));
      })
      .register();

  public static final BlockEntry<WallStoneTorchBlock> COMPACTED_STONE_WALL_TORCH = REGISTRATE.block("compacted_wall_stone_torch", Material.MISCELLANEOUS, WallStoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .blockstate(NonNullBiConsumer.noop())
      .setData(ProviderType.LANG, NonNullBiConsumer.noop())
      .register();

  public static final BlockEntry<StoneTorchBlock> DOUBLE_COMPACTED_STONE_TORCH = REGISTRATE.block("double_compacted_stone_torch", Material.MISCELLANEOUS, StoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .item((block, props) -> new WallOrFloorItem(block, ModBlocks.DOUBLE_COMPACTED_STONE_WALL_TORCH.get(), props))
      .model((ctx, p) -> p.generated(ctx::getEntry, new ResourceLocation(Compacted.MODID, "block/double_compacted_stone_torch")))
      .build()
      .blockstate(NonNullBiConsumer.noop())
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("C")
            .patternLine("R")
            .key('R', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
            .key('C', Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
            .addCriterion("has_rod", RegistrateRecipeProvider.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
            .addCriterion("has_coal", RegistrateRecipeProvider.hasItem(Items.COAL))
            .build(p);
      })
      // TODO: RECIPE
      .register();

  public static final BlockEntry<WallStoneTorchBlock> DOUBLE_COMPACTED_STONE_WALL_TORCH = REGISTRATE.block("double_compacted_wall_stone_torch", Material.MISCELLANEOUS, WallStoneTorchBlock::new)
      .properties(o -> o.hardnessAndResistance(0f, 0f).setLightLevel((a) -> 14).sound(SoundType.STONE).doesNotBlockMovement())
      .blockstate(NonNullBiConsumer.noop())
      .setData(ProviderType.LANG, NonNullBiConsumer.noop())
      .register();

  public static void load() {

  }
}
