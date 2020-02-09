package noobanidus.mods.compacted.items;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import noobanidus.mods.compacted.util.BreakUtil;

import java.util.Set;


public class HeavyAxeItem extends ToolItem implements EffectiveToolItem, SizedToolItem {
   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);

  // Taken from MIT licensed Practical Tools
  // https://github.com/astradamus/PracticalTools/blob/master/src/main/java/com/alexanderstrada/practicaltools/items/GreataxeItem.java
  private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.WOOD, Material.GOURD, Material.CACTUS, Material.PLANTS, Material.BAMBOO, Material.TALL_PLANTS);

  private final int width;

  public HeavyAxeItem(ToolItemProperties builder) {
    super((float) builder.getAttackDamage(), builder.getAttackSpeed(), builder.getTier(), EFFECTIVE_ON, builder.addToolType(ToolType.get("heavyaxe"), builder.getTier().getHarvestLevel()));
    this.width = builder.getWidth();
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public boolean canHarvestBlock(BlockState blockIn) {
    if (blockIn.getHarvestTool() == ToolType.AXE || blockIn.getHarvestTool() == ToolType.get("heavyaxe")) {
      return this.getTier().getHarvestLevel() >= blockIn.getHarvestLevel();
    }
    Material material = blockIn.getMaterial();
    return EFFECTIVE_MATERIALS.contains(material);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state) {
    Material material = state.getMaterial();
    if (EFFECTIVE_MATERIALS.contains(material)) {
      return this.efficiency;
    }
    return super.getDestroySpeed(stack, state);
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
    if (entity instanceof PlayerEntity && !entity.isSneaking()) {
      BreakUtil.breakNeighbours(stack, world, pos, (PlayerEntity) entity);
    }

    return super.onBlockDestroyed(stack, world, state, pos, entity);
  }

  @Override
  public Set<Block> getEffectiveBlocks() {
    return EFFECTIVE_ON;
  }

  @Override
  public Set<Material> getEffectiveMaterials() {
    return EFFECTIVE_MATERIALS;
  }
}
