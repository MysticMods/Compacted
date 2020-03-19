package noobanidus.mods.compacted.init;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.Compacted;
import noobanidus.mods.compacted.CompactedTags;
import noobanidus.mods.compacted.items.*;
import noobanidus.mods.compacted.materials.CompactedStoneMaterial;
import noobanidus.mods.compacted.materials.DoubleCompactedStoneMaterial;

import java.util.function.Supplier;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModItems {
  private static Supplier<ToolItemProperties> STONE = () -> new ToolItemProperties().attackDamage(6).attackSpeed(-3.5f).tier(ItemTier.STONE).width(1).setGroup(Compacted.GROUP);
  private static Supplier<ToolItemProperties> COMPACTED = () -> new ToolItemProperties().attackDamage(8).attackSpeed(-3.5f).tier(CompactedStoneMaterial.MATERIAL).width(1).setGroup(Compacted.GROUP);
  private static Supplier<ToolItemProperties> DOUBLE = () -> new ToolItemProperties().attackDamage(10).attackSpeed(-3.5f).tier(DoubleCompactedStoneMaterial.MATERIAL).width(1).setGroup(Compacted.GROUP);
  public static final RegistryEntry<HammerItem> STONE_HAMMER = REGISTRATE.item("stone_hammer", (b) -> new HammerItem(STONE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("###")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_hammer_from_cobblestone");
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("###")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.STONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.STONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_hammer_from_stone");
      })
      .register();

  public static final RegistryEntry<HammerItem> COMPACTED_HAMMER = REGISTRATE.item("compacted_hammer", (b) -> new HammerItem(COMPACTED.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("###")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addIngredient(ModItems.STONE_HAMMER.get())
            .addCriterion("has_hammer", p.hasItem(ModItems.STONE_HAMMER.get()))
            .build(p, "compacted_hammer_from_stone_hammers");
      })
      .register();

  public static final RegistryEntry<HammerItem> DOUBLE_COMPACTED_HAMMER = REGISTRATE.item("double_compacted_hammer", (b) -> new HammerItem(DOUBLE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("###")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addIngredient(ModItems.COMPACTED_HAMMER.get())
            .addCriterion("has_compacted_hammer", p.hasItem(ModItems.COMPACTED_HAMMER.get()))
            .build(p, "double_compacted_hammer_from_compacted_hammers");
      })
      .register();

  public static final RegistryEntry<ExcavatorItem> STONE_EXCAVATOR = REGISTRATE.item("stone_excavator", (b) -> new ExcavatorItem(STONE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine(" # ")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_excavator_from_cobblestone");
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine(" # ")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.STONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.STONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_excavator_from_stone");
      })
      .register();

  public static final RegistryEntry<ExcavatorItem> COMPACTED_EXCAVATOR = REGISTRATE.item("compacted_excavator", (b) -> new ExcavatorItem(COMPACTED.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine(" # ")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addIngredient(ModItems.STONE_EXCAVATOR.get())
            .addCriterion("has_excavator", p.hasItem(ModItems.STONE_EXCAVATOR.get()))
            .build(p, "compacted_excavator_from_stone_excavators");
      })
      .register();

  public static final RegistryEntry<ExcavatorItem> DOUBLE_COMPACTED_EXCAVATOR = REGISTRATE.item("double_compacted_excavator", (b) -> new ExcavatorItem(DOUBLE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine(" # ")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addIngredient(ModItems.COMPACTED_EXCAVATOR.get())
            .addCriterion("has_compacted_excavator", p.hasItem(ModItems.COMPACTED_EXCAVATOR.get()))
            .build(p, "double_compacted_excavator_from_compacted_excavators");
      })
      .register();

  public static final RegistryEntry<HeavyAxeItem> STONE_HEAVY_AXE = REGISTRATE.item("stone_heavy_axe", (b) -> new HeavyAxeItem(STONE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("##")
            .patternLine("#X")
            .patternLine(" X")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_heavy_axe_from_cobblestone");
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine(" # ")
            .patternLine("#X#")
            .patternLine(" X ")
            .key('X', CompactedTags.Items.STONE_ROD)
            .key('#', Tags.Items.STONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.STONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "stone_heavy_axe_from_stone");
      })
      .register();

  public static final RegistryEntry<HeavyAxeItem> COMPACTED_HEAVY_AXE = REGISTRATE.item("compacted_heavy_axe", (b) -> new HeavyAxeItem(COMPACTED.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("##")
            .patternLine("#X")
            .patternLine(" X")
            .key('X', CompactedTags.Items.COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addIngredient(ModItems.STONE_HEAVY_AXE.get())
            .addCriterion("has_heavy_axe", p.hasItem(ModItems.STONE_HEAVY_AXE.get()))
            .build(p, "compacted_heavy_axe_from_stone_heavy_axes");
      })
      .register();

  public static final RegistryEntry<HeavyAxeItem> DOUBLE_COMPACTED_HEAVY_AXE = REGISTRATE.item("double_compacted_heavy_axe", (b) -> new HeavyAxeItem(DOUBLE.get()))
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("##")
            .patternLine("#X")
            .patternLine(" X")
            .key('X', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
            .key('#', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
            .addCriterion("has_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
            .build(p);
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addIngredient(ModItems.COMPACTED_HEAVY_AXE.get())
            .addCriterion("has_compacted_heavy_axe", p.hasItem(ModItems.COMPACTED_HEAVY_AXE.get()))
            .build(p, "double_compacted_heavy_axe_from_compacted_heavy_axes");
      })
      .register();

  public static final RegistryEntry<Item> STONE_ROD = REGISTRATE.item("stone_rod", Item::new)
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("X")
            .patternLine("X")
            .key('X', Tags.Items.COBBLESTONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.COBBLESTONE))
            .build(p, "stone_rod_from_cobblestone");
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("X")
            .patternLine("X")
            .key('X', Tags.Items.STONE)
            .addCriterion("has_cobblestone", p.hasItem(Tags.Items.STONE))
            .build(p, "stone_rod_from_stone");
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 9)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addCriterion("has_compacted_rod", p.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .build(p, "stone_rods_from_compacted_rod");
      })
      .tag(CompactedTags.Items.STONE_ROD)
      .register();

  public static final RegistryEntry<Item> COMPACTED_STONE_ROD = REGISTRATE.item("compacted_rod", Item::new)
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("X")
            .patternLine("X")
            .key('X', CompactedTags.Items.COMPACTED_COBBLESTONE)
            .addCriterion("has_compcated_cobblestone", p.hasItem(CompactedTags.Items.COMPACTED_COBBLESTONE))
            .build(p, "compacted_stone_rod_from_compacted_cobblestone");
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addIngredient(CompactedTags.Items.STONE_ROD)
            .addCriterion("has_stone_rod", p.hasItem(CompactedTags.Items.STONE_ROD))
            .build(p, "compacted_stone_rod_from_stone_rods");
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 9)
            .addIngredient(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
            .addCriterion("has_double_compacted_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
            .build(p, "compacted_stone_rods_from_double_compacted_rod");
      })
      .tag(CompactedTags.Items.COMPACTED_STONE_ROD)
      .register();

  public static final RegistryEntry<Item> DOUBLE_COMPACTED_STONE_ROD = REGISTRATE.item("double_compacted_rod", Item::new)
      .recipe((ctx, p) -> {
        ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
            .patternLine("X")
            .patternLine("X")
            .key('X', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
            .addCriterion("has_double_compcated_cobblestone", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
            .build(p, "double_compacted_stone_rod_from_double_compacted_cobblestone");
        ShapelessRecipeBuilder.shapelessRecipe(ctx.getEntry(), 1)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addIngredient(CompactedTags.Items.COMPACTED_STONE_ROD)
            .addCriterion("has_compacted_stone_rod", p.hasItem(CompactedTags.Items.COMPACTED_STONE_ROD))
            .build(p, "double_compacted_stone_rod_from_compacted_stone_rods");
      })
      .tag(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
      .register();

  public static final RegistryEntry<PocketCompacter> POCKET_COMPACTER = REGISTRATE.item("pocket_compacter", PocketCompacter::new)
      .properties(o -> o.rarity(Rarity.UNCOMMON).maxDamage(620))
      .model(NonNullBiConsumer.noop())
      .recipe((ctx, p) ->
          ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
              .patternLine("RPR")
              .patternLine("PXP")
              .patternLine("RPR")
              .key('R', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
              .key('X', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
              .key('P', Items.PISTON)
              .addCriterion("has_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
              .addCriterion("has_cobble", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
              .addCriterion("has_piston", p.hasItem(Items.PISTON))
              .build(p))
      .register();

  public static final RegistryEntry<PocketImpacter> POCKET_IMPACTER = REGISTRATE.item("pocket_impacter", PocketImpacter::new)
      .properties(o -> o.rarity(Rarity.EPIC))
      .model(NonNullBiConsumer.noop())
      .recipe((ctx, p) ->
          ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
              .patternLine("RPR")
              .patternLine("PDP")
              .patternLine("XPX")
              .key('R', CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD)
              .key('X', CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE)
              .key('P', Items.PISTON)
              .key('D', Tags.Items.GEMS_DIAMOND)
              .addCriterion("has_diamond", p.hasItem(Tags.Items.GEMS_DIAMOND))
              .addCriterion("has_rod", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_STONE_ROD))
              .addCriterion("has_cobble", p.hasItem(CompactedTags.Items.DOUBLE_COMPACTED_COBBLESTONE))
              .addCriterion("has_piston", p.hasItem(Items.PISTON))
              .build(p))
      .register();

  public static void load () {

  }
}
