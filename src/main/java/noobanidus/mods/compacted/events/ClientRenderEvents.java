package noobanidus.mods.compacted.events;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientRenderEvents {
  @SubscribeEvent(priority = EventPriority.LOW)
  public static void renderExtraBlockStuff(RenderWorldLastEvent event) {
    PlayerController controller = Minecraft.getInstance().playerController;

    if (controller == null) {
      return;
    }

    PlayerEntity player = Minecraft.getInstance().player;
    if (player.isSneaking()) {
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

      BlockRayTraceResult trace = (BlockRayTraceResult) ray;
      Set<BlockPos> positions = BreakUtil.nearbyBlocks(tool, trace.getPos(), trace.getFace(), player.world, player);
      for (BlockPos position : positions) {
        event.getContext().drawSelectionBox(Minecraft.getInstance().gameRenderer.getActiveRenderInfo(), new BlockRayTraceResult(new Vec3d(0, 0, 0), trace.getFace(), position, false), 0);
      }

      if (controller.getIsHittingBlock()) {
        drawBlockDamage(player.world, Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), Minecraft.getInstance().gameRenderer.getActiveRenderInfo(), positions, trace.getPos());
      }
    }
  }

  private static void preRenderDamagedBlocks() {
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.enableBlend();
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.5F);
    GlStateManager.polygonOffset(-1.0F, -10.0F);
    GlStateManager.enablePolygonOffset();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.enableAlphaTest();
    GlStateManager.pushMatrix();
  }

  private static void postRenderDamagedBlocks() {
    GlStateManager.disableAlphaTest();
    GlStateManager.polygonOffset(0.0F, 0.0F);
    GlStateManager.disablePolygonOffset();
    GlStateManager.enableAlphaTest();
    GlStateManager.depthMask(true);
    GlStateManager.popMatrix();
  }

  public static void drawBlockDamage(World world, Tessellator tessellator, BufferBuilder bufferBuilder, ActiveRenderInfo activeRenderInfo, Set<BlockPos> positions, BlockPos origin) {
    DestroyBlockProgress progress = null;

    for (Map.Entry<Integer, DestroyBlockProgress> entry : Minecraft.getInstance().worldRenderer.damagedBlocks.entrySet()) {
      if (entry.getValue().getPosition().equals(origin)) {
        progress = entry.getValue();
        break;
      }
    }

    if (progress == null) {
      return;
    }

    double d0 = activeRenderInfo.getProjectedView().x;
    double d1 = activeRenderInfo.getProjectedView().y;
    double d2 = activeRenderInfo.getProjectedView().z;

    Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    preRenderDamagedBlocks();
    bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    bufferBuilder.setTranslation(-d0, -d1, -d2);
    bufferBuilder.noColor();

    for (BlockPos blockpos : positions) {
      TileEntity te = world.getTileEntity(blockpos);
      boolean hasBreak = te != null && te.canRenderBreaking();

      if (!hasBreak) {
        BlockState state = world.getBlockState(blockpos);
        if (state.getMaterial() != Material.AIR) {
          Minecraft.getInstance().getBlockRendererDispatcher().renderBlockDamage(state, blockpos, Minecraft.getInstance().worldRenderer.destroyBlockIcons[progress.getPartialBlockDamage()], world);
        }
      }
    }

    tessellator.draw();
    bufferBuilder.setTranslation(0.0D, 0.0D, 0.0D);
    postRenderDamagedBlocks();
  }
}
