package noobanidus.mods.compacted.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import noobanidus.mods.compacted.items.SizedToolItem;
import noobanidus.mods.compacted.util.BreakUtil;

import java.util.Objects;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientRenderEvents {
  @SubscribeEvent(priority = EventPriority.LOW)
  public static void renderExtraBlockStuff(RenderWorldLastEvent event) {
    Minecraft mc = Minecraft.getInstance();
    PlayerController controller = mc.playerController;

    if (controller == null || mc.world == null) {
      return;
    }

    PlayerEntity player = Minecraft.getInstance().player;
    if (player == null || player.isSneaking()) {
      return;
    }
    ItemStack tool = player.getHeldItemMainhand();
    if (tool.getItem() instanceof SizedToolItem) {
      Entity renderEntity = Minecraft.getInstance().getRenderViewEntity();
      if (renderEntity == null) {
        return;
      }

      RayTraceResult ray = BreakUtil.rayTrace(player.world, player);
      if (ray.getType() != RayTraceResult.Type.BLOCK) {
        return;
      }

      IRenderTypeBuffer.Impl buffers = Minecraft.getInstance().getBufferBuilders().getEntityVertexConsumers();
      MatrixStack stack = event.getMatrixStack();
      stack.push();

      BlockRayTraceResult trace = (BlockRayTraceResult) ray;
      Set<BlockPos> positions = BreakUtil.nearbyBlocks(tool, trace.getPos(), trace.getFace(), player.world, player);
      ActiveRenderInfo info = mc.gameRenderer.getActiveRenderInfo();
      Vec3d vec3d = info.getProjectedView();
      double d0 = vec3d.getX();
      double d1 = vec3d.getY();
      double d2 = vec3d.getZ();

      for (BlockPos position : positions) {
        IVertexBuilder ivertexBuilder = mc.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderType.getLines());
        mc.worldRenderer.drawBlockOutline(stack, ivertexBuilder, info.getRenderViewEntity(), d0, d1, d2, position, mc.world.getBlockState(position));
      }

      stack.pop();
      buffers.draw();

      if (controller.getIsHittingBlock()) {
        drawBlockDamage(stack, player.world, info, positions, trace.getPos());
      }
    }
  }

  @SuppressWarnings("deprecation")
  private static boolean drawBlockDamage(MatrixStack stack, World world, ActiveRenderInfo activeRenderInfo, Set<BlockPos> positions, BlockPos origin) {
    DestroyBlockProgress progress = null;

    for (Int2ObjectMap.Entry<DestroyBlockProgress> entry : Minecraft.getInstance().worldRenderer.damagedBlocks.int2ObjectEntrySet()) {
      if (entry.getValue().getPosition().equals(origin)) {
        progress = entry.getValue();
        break;
      }
    }

    if (progress == null) {
      return false;
    }

    Minecraft mc = Minecraft.getInstance();
    RenderTypeBuffers buffers = mc.getBufferBuilders();

    double d0 = activeRenderInfo.getProjectedView().x;
    double d1 = activeRenderInfo.getProjectedView().y;
    double d2 = activeRenderInfo.getProjectedView().z;

    boolean drew = false;
    stack.push();

    IRenderTypeBuffer.Impl vertices = buffers.getEffectVertexConsumers();
    RenderType type = ModelBakery.BLOCK_DESTRUCTION_RENDER_LAYERS.get(progress.getPartialBlockDamage());
    IVertexBuilder builder = vertices.getBuffer(type);
    IVertexBuilder ivertexbuilder1 = new MatrixApplyingVertexBuilder(builder, stack.peek());

    for (BlockPos blockpos : positions) {
      TileEntity te = world.getTileEntity(blockpos);
      boolean hasBreak = te != null && te.canRenderBreaking();

      if (!hasBreak) {
        BlockState state = world.getBlockState(blockpos);
        if (state.getMaterial() != Material.AIR) {
          stack.push();
          stack.translate((double) blockpos.getX() - d0, (double) blockpos.getY() - d1, (double) blockpos.getZ() - d2);
          mc.getBlockRendererDispatcher().renderDamage(Objects.requireNonNull(mc.world).getBlockState(blockpos), blockpos, mc.world, stack, ivertexbuilder1);
          stack.pop();
          drew = true;
        }
      }
    }
    stack.pop();
    vertices.draw();

    return drew;
  }
}
