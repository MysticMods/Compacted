package noobanidus.mods.compacted.items;

import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"NullableProblems", "Duplicates"})
public class PocketImpacter extends PocketItem {

  public PocketImpacter(Properties properties) {
    super(properties);
    ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.EFFICIENCY);
  }

  @Override
  public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (worldIn.isRemote() || !(entityIn instanceof PlayerEntity)) {
      return;
    }

    if (!isActive(stack)) {
      return;
    }

    CompoundNBT nbt = stack.getOrCreateTag();

    PlayerEntity player = (PlayerEntity) entityIn;

    InvWrapper wrapped = new InvWrapper(player.inventory);

    int iterations = (EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) * 2) + 1;
    int consumed = 0;
    for (int q = 0; q < iterations; q++) {
      for (int i = 0; i < wrapped.getSlots(); i++) {
        ItemStack inSlot = wrapped.getStackInSlot(i);
        if (inSlot.getItem() == Items.COBBLESTONE) {
          ItemStack extracted = wrapped.extractItem(i, 1, false);
          if (!extracted.isEmpty()) {
            consumed++;
            break;
          }
        }
      }
    }

    if (consumed > 0) {
      if (nbt.contains("consumed", Constants.NBT.TAG_LONG)) {
        nbt.putLong("consumed", nbt.getLong("consumed") + consumed);
      } else {
        nbt.putLong("consumed", consumed);
      }
    }
  }

  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state) {
    if (stack.getItem() == this && state.getBlock().isIn(Tags.Blocks.COBBLESTONE)) {
      return 1000f;
    }
    return super.getDestroySpeed(stack, state);
  }

  @Override
  public boolean canHarvestBlock(BlockState blockIn) {
    return blockIn.getBlock().isIn(Tags.Blocks.COBBLESTONE);
  }

  public long countContained(ItemStack stack) {
    if (stack.getItem() != this) {
      return 0;
    }

    CompoundNBT nbt = stack.getOrCreateTag();
    if (nbt.contains("consumed", Constants.NBT.TAG_LONG)) {
      return nbt.getLong("consumed");
    }

    return 0;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_impacter.desc1", countContained(stack), isActive(stack) ? new TranslationTextComponent("tooltip.compacted.impacter_active").setStyle(new Style().setColor(TextFormatting.AQUA)) : new TranslationTextComponent("tooltip.compacted.impacter_inactive").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE))).setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
    tooltip.add(new StringTextComponent(""));
    tooltip.add(new TranslationTextComponent("tooltip.compacted.sneak_right_click").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
    if (Screen.hasShiftDown()) {
      tooltip.add(new StringTextComponent(""));
      tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_impacter.desc2").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
      tooltip.add(new StringTextComponent(""));
      tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_impacter.desc3").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
      tooltip.add(new StringTextComponent(""));
      tooltip.add(new TranslationTextComponent("tooltip.compacted.pocket_compacter.desc2").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
    } else {
      tooltip.add(new StringTextComponent(""));
      tooltip.add(new TranslationTextComponent("tooltip.compacted.hold_shift").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
    }
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
    return actionresulttype != ActionResultType.SUCCESS && this.isFood() ? this.onItemRightClick(context.getWorld(), Objects.requireNonNull(context.getPlayer()), context.getHand()).getType() : actionresulttype;
  }

  private ActionResultType tryPlace(@Nullable BlockItemUseContext context) {
    if (context == null || !context.canPlace()) {
      return ActionResultType.FAIL;
    } else {
      ItemStack itemstack = context.getItem();
      CompoundNBT tag = itemstack.getOrCreateTag();
      long consumed;
      if (!tag.contains("consumed", Constants.NBT.TAG_LONG)) {
        return ActionResultType.FAIL;
      } else {
        consumed = tag.getLong("consumed");
      }
      if (consumed <= 0) {
        return ActionResultType.FAIL;
      }
      PlayerEntity playerentity = context.getPlayer();
      BlockState blockstate = Blocks.COBBLESTONE.getStateForPlacement(context);
      World world = context.getWorld();
      BlockPos blockpos = context.getPos();
      if (blockstate == null || !canPlace(context, blockstate, playerentity)) {
        return ActionResultType.FAIL;
      } else if (!world.setBlockState(blockpos, blockstate, 11)) {
        return ActionResultType.FAIL;
      } else {
        BlockState blockstate1 = world.getBlockState(blockpos);
        Block block = blockstate1.getBlock();
        if (block == blockstate.getBlock()) {
          blockstate1 = this.setBlockStateFromCompound(blockpos, world, itemstack, blockstate1);
          BlockItem.setTileEntityNBT(world, playerentity, blockpos, itemstack);
          block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
          if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
          }
        }

        SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
        world.playSound(playerentity, blockpos, blockstate1.getSoundType(world, blockpos, playerentity).getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        tag.putLong("consumed", consumed - 1);
        return ActionResultType.SUCCESS;
      }
    }
  }

  private BlockState setBlockStateFromCompound(BlockPos pos, World world, ItemStack stack, BlockState state) {
    BlockState blockstate = state;
    CompoundNBT compoundnbt = stack.getTag();
    if (compoundnbt != null) {
      CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
      StateContainer<Block, BlockState> statecontainer = state.getBlock().getStateContainer();

      for (String s : compoundnbt1.keySet()) {
        IProperty<?> iproperty = statecontainer.getProperty(s);
        if (iproperty != null) {
          String s1 = Objects.requireNonNull(compoundnbt1.get(s)).getString();
          blockstate = setStatePropertyFromString(blockstate, iproperty, s1);
        }
      }
    }

    if (blockstate != state) {
      world.setBlockState(pos, blockstate, 2);
    }

    return blockstate;
  }

  private static <T extends Comparable<T>> BlockState setStatePropertyFromString(BlockState state, IProperty<T> property, String value) {
    return property.parseValue(value).map((p) -> state.with(property, p)).orElse(state);
  }

  private boolean canPlace(BlockItemUseContext context, BlockState state, PlayerEntity playerentity) {
    ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(playerentity);
    return (state.isValidPosition(context.getWorld(), context.getPos())) && context.getWorld().func_217350_a(state, context.getPos(), iselectioncontext);
  }
}
