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

public class ExcavatorItem extends ToolItem implements EffectiveToolItem, SizedToolItem {
   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);

  // Taken from MIT licensed Practical Tools
  // https://github.com/astradamus/PracticalTools/blob/master/src/main/java/com/alexanderstrada/practicaltools/items/ExcavatorItem.java
  private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.CLAY, Material.ORGANIC, Material.EARTH, Material.SAND, Material.SNOW, Material.SNOW_BLOCK);

  private final int width;

  public ExcavatorItem(ToolItemProperties builder) {
    super((float) builder.getAttackDamage(), builder.getAttackSpeed(), builder.getTier(), EFFECTIVE_ON, builder.addToolType(ToolType.get("excavator"), builder.getTier().getHarvestLevel()));
    this.width = builder.getWidth();
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public boolean canHarvestBlock(BlockState blockIn) {
    if (blockIn.getHarvestTool() == ToolType.SHOVEL || blockIn.getHarvestTool() == ToolType.get("excavator")) {
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
