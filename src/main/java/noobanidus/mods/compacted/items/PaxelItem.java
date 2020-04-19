package noobanidus.mods.compacted.items;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaxelItem extends ToolItem {
  private static Set<Block> EFFECTIVE_ON = new HashSet<>();
  private static Set<Material> EFFECTIVE_MATERIALS = new HashSet<>();

  static {
    EFFECTIVE_ON.addAll(ExcavatorItem.EFFECTIVE_ON);
    EFFECTIVE_ON.addAll(HammerItem.EFFECTIVE_ON);
    EFFECTIVE_ON.addAll(HeavyAxeItem.EFFECTIVE_ON);
    EFFECTIVE_MATERIALS.addAll(ExcavatorItem.EFFECTIVE_MATERIALS);
    EFFECTIVE_MATERIALS.addAll(HammerItem.EFFECTIVE_MATERIALS);
    EFFECTIVE_MATERIALS.addAll(HeavyAxeItem.EFFECTIVE_MATERIALS);
  }

  public PaxelItem(ToolItemProperties builder) {
    super((float) builder.getAttackDamage(), builder.getAttackSpeed(), builder.getTier(), EFFECTIVE_ON,
        builder.addToolType(ToolType.PICKAXE, builder.getTier().getHarvestLevel())
            .addToolType(ToolType.SHOVEL, builder.getTier().getHarvestLevel())
            .addToolType(ToolType.AXE, builder.getTier().getHarvestLevel())
            .maxDamage(builder.getTier().getMaxUses() * 3)
    );
  }

  @Override
  public boolean canHarvestBlock(BlockState state) {
    Block block = state.getBlock();
    int tier = this.getTier().getHarvestLevel();
    ToolType harvestTool = state.getHarvestTool();
    if (harvestTool == ToolType.PICKAXE || harvestTool == ToolType.SHOVEL) {
      return tier >= state.getHarvestLevel();
    }

    if (EFFECTIVE_ON.contains(block)) {
      return true;
    }

    return EFFECTIVE_MATERIALS.contains(state.getMaterial());
  }

  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state) {
    Material material = state.getMaterial();
    return (EFFECTIVE_MATERIALS.contains(material)) ? this.efficiency : super.getDestroySpeed(stack, state);
  }

  private static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState(), Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState(), Blocks.COARSE_DIRT, Blocks.GRASS_PATH.getDefaultState()));

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    World world = context.getWorld();
    BlockPos blockpos = context.getPos();
    BlockState blockstate = world.getBlockState(blockpos);
    if (context.getFace() == Direction.DOWN) {
      return ActionResultType.PASS;
    } else {
      PlayerEntity playerentity = context.getPlayer();
      BlockState blockstate1 = SHOVEL_LOOKUP.get(blockstate.getBlock());
      BlockState blockstate2 = null;
      if (blockstate1 != null && world.isAirBlock(blockpos.up())) {
        world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
        blockstate2 = blockstate1;
      } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
        world.playEvent(null, 1009, blockpos, 0);
        blockstate2 = blockstate.with(CampfireBlock.LIT, Boolean.FALSE);
      }

      if (blockstate2 != null) {
        if (!world.isRemote) {
          world.setBlockState(blockpos, blockstate2, 11);
          if (playerentity != null) {
            context.getItem().damageItem(1, playerentity, (entity) -> entity.sendBreakAnimation(context.getHand()));
          }
        }

        return ActionResultType.SUCCESS;
      } else {
        return ActionResultType.PASS;
      }
    }
  }
}
