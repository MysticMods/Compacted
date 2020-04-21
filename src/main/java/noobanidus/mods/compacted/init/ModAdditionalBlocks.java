package noobanidus.mods.compacted.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import noobanidus.mods.compacted.blocks.VariableRedstoneBlock;

import static noobanidus.mods.compacted.Compacted.REGISTRATE;

public class ModAdditionalBlocks {

/*  static {
    compacter(Blocks.ANDESITE, null
    compacter(Blocks.DIORITE, null
    compacter(Blocks.DIRT, null
    compacter(Blocks.END_STONE, Tags.Items.END_STONES
    compacter(Blocks.GRANITE, null
    compacter(Blocks.GRAVEL, Tags.Items.GRAVEL
    compacter(Blocks.NETHERRACK, Tags.Items.NETHERRACK
    compacter(Blocks.PRISMARINE, null
    compacter(Blocks.RED_SAND, Tags.Items.SAND_RED
    compacter(Blocks.REDSTONE_BLOCK, Tags.Items.STORAGE_BLOCKS_REDSTONE
    compacter(Blocks.SAND, Tags.Items.SAND_COLORLESS
    compacter(Blocks.SMOOTH_STONE, null
    compacter(Blocks.TERRACOTTA, null
    compacted(Blocks.STONE, Tags.Items.STONE
    compacted(Blocks.SOUL_SAND, null
  }*/


  public static final RegistryEntry<Block> COMPACTED_ANDESITE = REGISTRATE.block("compacted_andesite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_ANDESITE = REGISTRATE.block("double_compacted_andesite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_DIORITE = REGISTRATE.block("compacted_diorite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_DIORITE = REGISTRATE.block("double_compacted_diorite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_DIRT = REGISTRATE.block("compacted_dirt", Material.EARTH, Block::new)
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.GROUND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_DIRT = REGISTRATE.block("double_compacted_dirt", Material.EARTH, Block::new)
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.GROUND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_END_STONE = REGISTRATE.block("compacted_end_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(3.0f, 9.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_END_STONE = REGISTRATE.block("double_compacted_end_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(3.0f, 9.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_GRANITE = REGISTRATE.block("compacted_granite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_GRANITE = REGISTRATE.block("double_compacted_granite", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<GravelBlock> COMPACTED_GRAVEL = REGISTRATE.block("compacted_gravel", Material.SAND, GravelBlock::new)
      .properties(o -> o.hardnessAndResistance(0.6f, 0.6f).sound(SoundType.GROUND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<GravelBlock> DOUBLE_COMPACTED_GRAVEL = REGISTRATE.block("double_compacted_gravel", Material.SAND, GravelBlock::new)
      .properties(o -> o.hardnessAndResistance(0.6f, 0.6f).sound(SoundType.GROUND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_NETHERRACK = REGISTRATE.block("compacted_netherrack", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(0.4f, 0.4f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_NETHERRACK = REGISTRATE.block("double_compacted_netherrack", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(0.4f, 0.4f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_PRISMARINE = REGISTRATE.block("compacted_prismarine", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_PRISMARINE = REGISTRATE.block("double_compacted_prismarine", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<VariableRedstoneBlock> COMPACTED_REDSTONE_BLOCK = REGISTRATE.block("compacted_redstone_block", Material.ROCK, (p) -> new VariableRedstoneBlock(8, p))
      .properties(o -> o.hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL))
      .item()
      .build()
      .register();

  public static final RegistryEntry<VariableRedstoneBlock> DOUBLE_COMPACTED_REDSTONE_BLOCK = REGISTRATE.block("double_compacted_redstone_block", Material.ROCK, (p) -> new VariableRedstoneBlock(4, p))
      .properties(o -> o.hardnessAndResistance(5f, 6f).sound(SoundType.METAL))
      .item()
      .build()
      .register();

  public static final RegistryEntry<SandBlock> COMPACTED_SAND = REGISTRATE.block("compacted_sand", Material.SAND, (p) -> new SandBlock(14406560, p))
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<SandBlock> DOUBLE_COMPACTED_SAND = REGISTRATE.block("double_compacted_sand", Material.SAND, (p) -> new SandBlock(14406560, p))
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<SandBlock> COMPACTED_RED_SAND = REGISTRATE.block("compacted_red_sand", Material.SAND, (p) -> new SandBlock(11098415, p))
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<SandBlock> DOUBLE_COMPACTED_RED_SAND = REGISTRATE.block("double_compacted_red_sand", Material.SAND, (p) -> new SandBlock(11098415, p))
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_SMOOTH_STONE = REGISTRATE.block("compacted_smooth_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_SMOOTH_STONE = REGISTRATE.block("double_compacted_smooth_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_TERRACOTTA = REGISTRATE.block("compacted_terracotta", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.25f, 4.2f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_TERRACOTTA = REGISTRATE.block("double_compacted_terracotta", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.25f, 4.2f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> COMPACTED_STONE = REGISTRATE.block("compacted_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<Block> DOUBLE_COMPACTED_STONE = REGISTRATE.block("double_compacted_stone", Material.ROCK, Block::new)
      .properties(o -> o.hardnessAndResistance(1.6f, 6.0f))
      .item()
      .build()
      .register();


  public static final RegistryEntry<SoulSandBlock> COMPACTED_SOUL_SAND = REGISTRATE.block("compacted_soul_sand", Material.SAND, SoulSandBlock::new)
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND).velocityMultiplier(0.2f))
      .item()
      .build()
      .register();

  public static final RegistryEntry<SoulSandBlock> DOUBLE_COMPACTED_SOUL_SAND = REGISTRATE.block("double_compacted_soul_sand", Material.SAND, SoulSandBlock::new)
      .properties(o -> o.hardnessAndResistance(0.5f, 0.5f).sound(SoundType.SAND).velocityMultiplier(0.1f))
      .item()
      .build()
      .register();

  public static void load() {

  }
}
