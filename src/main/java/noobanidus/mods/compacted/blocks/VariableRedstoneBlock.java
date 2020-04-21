package noobanidus.mods.compacted.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("deprecation")
public class VariableRedstoneBlock extends Block {
   private int value;

   public VariableRedstoneBlock(int value, Block.Properties props) {
      super(props);
      this.value = value;
   }

   public boolean canProvidePower(BlockState state) {
      return true;
   }

   public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction facing) {
      return value;
   }
}
