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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
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

      IRenderTypeBuffer.Impl buffers = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
      MatrixStack stack = event.getMatrixStack();
      stack.push();

      BlockRayTraceResult trace = (BlockRayTraceResult) ray;
      Set<BlockPos> positions = BreakUtil.nearbyBlocks(tool, trace.getPos(), trace.getFace(), player.world, player);
      ActiveRenderInfo info = mc.gameRenderer.getActiveRenderInfo();
      Vector3d Vector3d = info.getProjectedView();
      double d0 = Vector3d.getX();
      double d1 = Vector3d.getY();
      double d2 = Vector3d.getZ();

      for (BlockPos position : positions) {
        IVertexBuilder ivertexBuilder = buffers.getBuffer(RenderType.getLines());
        mc.worldRenderer.drawSelectionBox(stack, ivertexBuilder, info.getRenderViewEntity(), d0, d1, d2, position, mc.world.getBlockState(position));
      }

      stack.pop();
      buffers.finish();

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
    RenderTypeBuffers buffers = mc.getRenderTypeBuffers();

    double d0 = activeRenderInfo.getProjectedView().x;
    double d1 = activeRenderInfo.getProjectedView().y;
    double d2 = activeRenderInfo.getProjectedView().z;

    boolean drew = false;
    stack.push();

    IRenderTypeBuffer.Impl vertices = buffers.getBufferSource();
    RenderType type = ModelBakery.DESTROY_RENDER_TYPES.get(progress.getPartialBlockDamage());
    MatrixStack.Entry entry = stack.getLast();
    IVertexBuilder ivertexbuilder1 = new MatrixApplyingVertexBuilder(mc.worldRenderer.renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.DESTROY_RENDER_TYPES.get(progress.getPartialBlockDamage())), entry.getMatrix(), entry.getNormal());

    for (BlockPos blockpos : positions) {
      BlockState state = world.getBlockState(blockpos);
      if (state.getMaterial() != Material.AIR) {
        stack.push();
        stack.translate((double) blockpos.getX() - d0, (double) blockpos.getY() - d1, (double) blockpos.getZ() - d2);
        mc.getBlockRendererDispatcher().renderBlockDamage(Objects.requireNonNull(mc.world).getBlockState(blockpos), blockpos, mc.world, stack, ivertexbuilder1);
        stack.pop();
        drew = true;
      }
    }
    stack.pop();
    vertices.finish();

    return drew;
  }
}
