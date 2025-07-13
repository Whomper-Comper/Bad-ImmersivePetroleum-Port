/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.api.event.ProjectorEvent;
/*     */ import flaxbeard.immersivepetroleum.client.IPShaders;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import flaxbeard.immersivepetroleum.common.util.projector.MultiblockProjection;
/*     */ import flaxbeard.immersivepetroleum.common.util.projector.Settings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiPredicate;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.block.ModelBlockRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.client.event.RenderLevelStageEvent;
/*     */ import net.minecraftforge.client.model.data.ModelData;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.eventbus.api.Event;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*     */ public class ClientRenderHandler
/*     */ {
/*     */   @SubscribeEvent
/*     */   public static void renderLevelStage(RenderLevelStageEvent event) {
/* 383 */     if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
/* 384 */       renderProjection(event);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void renderProjection(RenderLevelStageEvent event) {
/* 389 */     Minecraft mc = Minecraft.m_91087_();
/*     */     
/* 391 */     if (mc.f_91074_ != null) {
/* 392 */       PoseStack matrix = event.getPoseStack();
/* 393 */       matrix.m_85836_();
/*     */ 
/*     */       
/* 396 */       Vec3 renderView = MCUtil.getGameRenderer().m_109153_().m_90583_();
/* 397 */       matrix.m_85837_(-renderView.f_82479_, -renderView.f_82480_, -renderView.f_82481_);
/*     */       
/* 399 */       ItemStack secondItem = mc.f_91074_.m_21206_();
/* 400 */       boolean off = (secondItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(secondItem, "settings", 10));
/*     */       
/* 402 */       for (int i = 0; i <= 10; i++) {
/* 403 */         ItemStack stack = (i == 10) ? secondItem : mc.f_91074_.m_150109_().m_8020_(i);
/* 404 */         if (stack.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(stack, "settings", 10)) {
/* 405 */           Settings settings = ProjectorItem.getSettings(stack);
/* 406 */           matrix.m_85836_();
/*     */           
/* 408 */           boolean renderMoving = (i == (mc.f_91074_.m_150109_()).f_35977_ || (i == 10 && off));
/* 409 */           renderSchematic(matrix, settings, (Player)mc.f_91074_, mc.f_91074_.f_19853_, event.getPartialTick(), renderMoving);
/*     */           
/* 411 */           matrix.m_85849_();
/*     */         } 
/*     */       } 
/*     */       
/* 415 */       matrix.m_85849_();
/*     */     } 
/*     */   }
/*     */   
/* 419 */   static final BlockPos.MutableBlockPos FULL_MAX = new BlockPos.MutableBlockPos(2147483647, 2147483647, 2147483647);
/*     */   public static void renderSchematic(PoseStack matrix, Settings settings, Player player, Level world, float partialTicks, boolean renderMoving) {
/* 421 */     if (settings.getMultiblock() == null) {
/*     */       return;
/*     */     }
/* 424 */     Vec3i size = settings.getMultiblock().getSize(world);
/* 425 */     BlockPos.MutableBlockPos hit = new BlockPos.MutableBlockPos(FULL_MAX.m_123341_(), FULL_MAX.m_123342_(), FULL_MAX.m_123343_());
/* 426 */     MutableBoolean isPlaced = new MutableBoolean(false);
/* 427 */     if (settings.getPos() != null) {
/* 428 */       hit.m_122190_((Vec3i)settings.getPos());
/* 429 */       isPlaced.setTrue();
/*     */     }
/* 431 */     else if (renderMoving && MCUtil.getHitResult() != null && MCUtil.getHitResult().m_6662_() == HitResult.Type.BLOCK) {
/* 432 */       BlockHitResult blockRTResult = (BlockHitResult)MCUtil.getHitResult();
/*     */       
/* 434 */       BlockPos pos = blockRTResult.m_82425_();
/*     */       
/* 436 */       BlockState state = world.m_8055_(pos);
/* 437 */       if (state.m_60767_().m_76336_() || blockRTResult.m_82434_() != Direction.UP) {
/* 438 */         hit.m_122190_((Vec3i)pos);
/*     */       } else {
/* 440 */         hit.m_122154_((Vec3i)pos, 0, 1, 0);
/*     */       } 
/*     */       
/* 443 */       ProjectorItem.alignHit(hit, player, size, settings.getRotation(), settings.isMirrored());
/*     */     } 
/*     */     
/* 446 */     if (!hit.equals(FULL_MAX)) {
/* 447 */       ResourceLocation name = settings.getMultiblock().getUniqueName();
/* 448 */       if (name.m_135815_().contains("excavator_demo") || name.m_135815_().contains("bucket_wheel")) {
/* 449 */         hit.m_122154_((Vec3i)hit, 0, -2, 0);
/*     */       }
/*     */       
/* 452 */       MultiblockProjection projection = new MultiblockProjection(world, settings.getMultiblock());
/* 453 */       projection.setRotation(settings.getRotation());
/* 454 */       projection.setFlip(settings.isMirrored());
/*     */       
/* 456 */       List<Pair<ProjectorItem.RenderLayer, MultiblockProjection.Info>> toRender = new ArrayList<>();
/* 457 */       MutableInt currentLayer = new MutableInt();
/* 458 */       MutableInt badBlocks = new MutableInt();
/* 459 */       MutableInt goodBlocks = new MutableInt();
/* 460 */       BiPredicate<Integer, MultiblockProjection.Info> bipred = (layer, info) -> {
/*     */           if (badBlocks.getValue().intValue() == 0 && layer.intValue() > currentLayer.getValue().intValue()) {
/*     */             currentLayer.setValue(layer);
/*     */           } else if (!Objects.equals(layer, currentLayer.getValue())) {
/*     */             return true;
/*     */           } 
/*     */           
/*     */           if (isPlaced.booleanValue() && Objects.equals(layer, currentLayer.getValue())) {
/*     */             BlockPos realPos = info.tPos.m_121955_((Vec3i)hit);
/*     */             
/*     */             BlockState toCompare = world.m_8055_(realPos);
/*     */             
/*     */             BlockState tState = info.getModifiedState(world, realPos);
/*     */             
/*     */             boolean skip = false;
/*     */             
/*     */             if (tState == toCompare) {
/*     */               toRender.add(Pair.of(ProjectorItem.RenderLayer.PERFECT, info));
/*     */               
/*     */               goodBlocks.increment();
/*     */               
/*     */               skip = true;
/*     */             } else if (!toCompare.m_60795_()) {
/*     */               toRender.add(Pair.of(ProjectorItem.RenderLayer.BAD, info));
/*     */               
/*     */               skip = true;
/*     */             } else {
/*     */               badBlocks.increment();
/*     */             } 
/*     */             
/*     */             if (skip) {
/*     */               return false;
/*     */             }
/*     */           } 
/*     */           
/*     */           toRender.add(Pair.of(ProjectorItem.RenderLayer.ALL, info));
/*     */           
/*     */           return false;
/*     */         };
/* 499 */       projection.processAll(bipred);
/*     */       
/* 501 */       boolean perfect = (goodBlocks.getValue().intValue() == projection.getBlockCount());
/*     */       
/* 503 */       BlockPos.MutableBlockPos min = new BlockPos.MutableBlockPos(2147483647, 2147483647, 2147483647);
/* 504 */       BlockPos.MutableBlockPos max = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/* 505 */       float flicker = world.f_46441_.m_188501_() / 2.0F + 0.25F;
/*     */       
/* 507 */       matrix.m_85837_(hit.m_123341_(), hit.m_123342_(), hit.m_123343_());
/*     */       
/* 509 */       toRender.sort((a, b) -> {
/*     */             int ao = ((ProjectorItem.RenderLayer)a.getLeft()).ordinal();
/*     */ 
/*     */             
/*     */             int bo = ((ProjectorItem.RenderLayer)b.getLeft()).ordinal();
/*     */ 
/*     */             
/*     */             return (ao > bo) ? 1 : ((ao < bo) ? -1 : 0);
/*     */           });
/*     */ 
/*     */       
/* 520 */       MultiBufferSource.BufferSource mainBuffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */       
/* 522 */       ItemStack heldStack = player.m_21205_();
/* 523 */       for (Pair<ProjectorItem.RenderLayer, MultiblockProjection.Info> pair : toRender) {
/* 524 */         boolean held; int x; float alpha; int y, z; MultiblockProjection.Info rInfo = (MultiblockProjection.Info)pair.getRight();
/*     */         
/* 526 */         switch (ProjectorItem.null.$SwitchMap$flaxbeard$immersivepetroleum$common$items$ProjectorItem$RenderLayer[((ProjectorItem.RenderLayer)pair.getLeft()).ordinal()]) {
/*     */           case 1:
/* 528 */             held = (heldStack.m_41720_() == rInfo.getRawState().m_60734_().m_5456_());
/* 529 */             alpha = held ? 1.0F : 0.5F;
/*     */             
/* 531 */             matrix.m_85836_();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 536 */             renderPhantom(matrix, world, rInfo, settings.isMirrored(), flicker, alpha, partialTicks);
/*     */             
/* 538 */             if (held) {
/* 539 */               renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 11513775, flicker);
/*     */             }
/*     */             
/* 542 */             matrix.m_85849_();
/*     */           
/*     */           case 2:
/* 545 */             matrix.m_85836_();
/*     */             
/* 547 */             matrix.m_85837_(rInfo.tPos.m_123341_(), rInfo.tPos.m_123342_(), rInfo.tPos.m_123343_());
/*     */             
/* 549 */             renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 16711680, flicker);
/*     */             
/* 551 */             matrix.m_85849_();
/*     */           
/*     */           case 3:
/* 554 */             x = rInfo.tPos.m_123341_();
/* 555 */             y = rInfo.tPos.m_123342_();
/* 556 */             z = rInfo.tPos.m_123343_();
/*     */             
/* 558 */             min.m_122178_(Math.min(x, min.m_123341_()), Math.min(y, min.m_123342_()), Math.min(z, min.m_123343_()));
/* 559 */             max.m_122178_(Math.max(x, max.m_123341_()), Math.max(y, max.m_123342_()), Math.max(z, max.m_123343_()));
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 564 */       if (perfect) {
/*     */         
/* 566 */         matrix.m_85836_();
/*     */         
/* 568 */         renderOutlineBox((MultiBufferSource)mainBuffer, matrix, (Vec3i)min, (Vec3i)max, 48896, flicker);
/*     */         
/* 570 */         matrix.m_85849_();
/*     */ 
/*     */         
/* 573 */         if (!player.m_21206_().m_41619_() && player.m_21206_().m_41720_() == IPContent.DEBUGITEM.get()) {
/* 574 */           matrix.m_85836_();
/*     */ 
/*     */           
/* 577 */           matrix.m_85837_(min.m_123341_(), min.m_123342_(), min.m_123343_());
/* 578 */           renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 16711680, flicker);
/*     */           
/* 580 */           matrix.m_85849_();
/*     */           
/* 582 */           matrix.m_85836_();
/*     */ 
/*     */           
/* 585 */           matrix.m_85837_(max.m_123341_(), max.m_123342_(), max.m_123343_());
/* 586 */           renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 65280, flicker);
/*     */           
/* 588 */           matrix.m_85849_();
/*     */           
/* 590 */           matrix.m_85836_();
/*     */ 
/*     */           
/* 593 */           BlockPos center = min.m_7949_().m_121955_((Vec3i)max);
/* 594 */           matrix.m_85837_((center.m_123341_() / 2), (center.m_123342_() / 2), (center.m_123343_() / 2));
/* 595 */           renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 255, flicker);
/*     */           
/* 597 */           matrix.m_85849_();
/*     */         } 
/*     */       } 
/*     */       
/* 601 */       mainBuffer.m_109911_();
/*     */     } 
/*     */   }
/*     */   
/* 605 */   private static final Tesselator PHANTOM_TESSELATOR = new Tesselator();
/*     */   private static void renderPhantom(PoseStack matrix, Level realWorld, MultiblockProjection.Info rInfo, boolean mirror, float flicker, float alpha, float partialTicks) {
/* 607 */     BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
/* 608 */     ModelBlockRenderer blockRenderer = dispatcher.m_110937_();
/* 609 */     BlockColors blockColors = Minecraft.m_91087_().m_91298_();
/*     */ 
/*     */     
/* 612 */     matrix.m_85837_(rInfo.tPos.m_123341_(), rInfo.tPos.m_123342_(), rInfo.tPos.m_123343_());
/*     */     
/* 614 */     MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(PHANTOM_TESSELATOR.m_85915_());
/*     */     
/* 616 */     BlockState state = rInfo.getModifiedState(realWorld, rInfo.tPos);
/*     */     
/* 618 */     ProjectorEvent.RenderBlock renderEvent = new ProjectorEvent.RenderBlock(rInfo.multiblock, rInfo.templateWorld, rInfo.tBlockInfo.f_74675_, realWorld, rInfo.tPos, state, rInfo.settings.m_74404_());
/* 619 */     if (!MinecraftForge.EVENT_BUS.post((Event)renderEvent)) {
/* 620 */       BakedModel ibakedmodel; ItemStack stack; int i; float red, green, blue; VertexConsumer vc; state = renderEvent.getState();
/* 621 */       state.m_60701_((LevelAccessor)realWorld, rInfo.tPos, 3);
/*     */       
/* 623 */       ModelData modelData = ModelData.EMPTY;
/* 624 */       BlockEntity te = rInfo.templateWorld.m_7702_(rInfo.tBlockInfo.f_74675_);
/* 625 */       if (te != null) {
/* 626 */         te.f_58856_ = state;
/* 627 */         modelData = te.getModelData();
/*     */       } 
/*     */       
/* 630 */       RenderShape blockrendertype = state.m_60799_();
/* 631 */       switch (ProjectorItem.null.$SwitchMap$net$minecraft$world$level$block$RenderShape[blockrendertype.ordinal()]) {
/*     */         case 1:
/* 633 */           ibakedmodel = dispatcher.m_110910_(state);
/* 634 */           i = blockColors.m_92577_(state, null, null, 0);
/* 635 */           red = (i >> 16 & 0xFF) / 255.0F;
/* 636 */           green = (i >> 8 & 0xFF) / 255.0F;
/* 637 */           blue = (i & 0xFF) / 255.0F;
/*     */           
/* 639 */           modelData = ibakedmodel.getModelData((BlockAndTintGetter)rInfo.templateWorld, rInfo.tBlockInfo.f_74675_, state, modelData);
/*     */           
/* 641 */           IPShaders.projNoise(flicker * alpha, (MCUtil.getPlayer()).f_19797_ + partialTicks);
/*     */           
/* 643 */           vc = buffer.m_6299_(IPRenderTypes.PROJECTION);
/*     */           
/* 645 */           blockRenderer.renderModel(matrix.m_85850_(), vc, state, ibakedmodel, red, green, blue, 15728880, OverlayTexture.f_118083_, modelData, null);
/*     */           break;
/*     */         case 2:
/* 648 */           stack = new ItemStack((ItemLike)state.m_60734_());
/*     */           
/* 650 */           MCUtil.getItemRenderer().m_174269_(stack, ItemTransforms.TransformType.NONE, 15728880, OverlayTexture.f_118083_, matrix, (MultiBufferSource)buffer, 0);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 656 */     buffer.m_109911_();
/*     */   }
/*     */   
/*     */   private static void renderOutlineBox(MultiBufferSource buffer, PoseStack matrix, Vec3i min, Vec3i max, int rgb, float flicker) {
/* 660 */     renderBox(buffer, matrix, Vec3.m_82528_(min), Vec3.m_82528_(max).m_82520_(1.0D, 1.0D, 1.0D), rgb, flicker);
/*     */   }
/*     */   
/*     */   private static void renderBox(MultiBufferSource buffer, PoseStack matrix, Vec3 min, Vec3 max, int rgb, float flicker) {
/* 664 */     VertexConsumer builder = buffer.m_6299_(IPRenderTypes.TRANSLUCENT_LINE);
/*     */     
/* 666 */     float alpha = 0.25F + 0.5F * flicker;
/*     */     
/* 668 */     int rgba = rgb | (int)(alpha * 255.0F) << 24;
/*     */     
/* 670 */     line(builder, matrix, min, max, 2, 6, rgba);
/* 671 */     line(builder, matrix, min, max, 6, 7, rgba);
/* 672 */     line(builder, matrix, min, max, 7, 3, rgba);
/* 673 */     line(builder, matrix, min, max, 3, 2, rgba);
/*     */     
/* 675 */     line(builder, matrix, min, max, 2, 0, rgba);
/* 676 */     line(builder, matrix, min, max, 6, 4, rgba);
/* 677 */     line(builder, matrix, min, max, 3, 1, rgba);
/* 678 */     line(builder, matrix, min, max, 7, 5, rgba);
/*     */     
/* 680 */     line(builder, matrix, min, max, 0, 4, rgba);
/* 681 */     line(builder, matrix, min, max, 4, 5, rgba);
/* 682 */     line(builder, matrix, min, max, 5, 1, rgba);
/* 683 */     line(builder, matrix, min, max, 1, 0, rgba);
/*     */   }
/*     */   
/*     */   private static void renderCenteredOutlineBox(MultiBufferSource buffer, PoseStack matrix, int rgb, float flicker) {
/* 687 */     renderBox(buffer, matrix, Vec3.f_82478_, new Vec3(1.0D, 1.0D, 1.0D), rgb, flicker);
/*     */   }
/*     */   
/*     */   private static Vector3f combine(Vec3 start, Vec3 end, int mixBits) {
/* 691 */     float eps = 0.01F;
/* 692 */     return new Vector3f(
/* 693 */         (float)(((mixBits & 0x4) != 0) ? (end.f_82479_ + 0.009999999776482582D) : (start.f_82479_ - 0.009999999776482582D)), 
/* 694 */         (float)(((mixBits & 0x2) != 0) ? (end.f_82480_ + 0.009999999776482582D) : (start.f_82480_ - 0.009999999776482582D)), 
/* 695 */         (float)(((mixBits & 0x1) != 0) ? (end.f_82481_ + 0.009999999776482582D) : (start.f_82481_ - 0.009999999776482582D)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void line(VertexConsumer out, PoseStack mat, Vec3 min, Vec3 max, int startBits, int endBits, int rgba) {
/* 700 */     Vector3f start = combine(min, max, startBits);
/* 701 */     Vector3f end = combine(min, max, endBits);
/* 702 */     Vector3f delta = end.m_122281_();
/* 703 */     delta.m_122267_(start);
/* 704 */     out.m_85982_(mat.m_85850_().m_85861_(), start.m_122239_(), start.m_122260_(), start.m_122269_())
/* 705 */       .m_193479_(rgba)
/* 706 */       .m_85977_(mat.m_85850_().m_85864_(), delta.m_122239_(), delta.m_122260_(), delta.m_122269_())
/* 707 */       .m_5752_();
/* 708 */     out.m_85982_(mat.m_85850_().m_85861_(), end.m_122239_(), end.m_122260_(), end.m_122269_())
/* 709 */       .m_193479_(rgba)
/* 710 */       .m_85977_(mat.m_85850_().m_85864_(), delta.m_122239_(), delta.m_122260_(), delta.m_122269_())
/* 711 */       .m_5752_();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\ProjectorItem$ClientRenderHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */