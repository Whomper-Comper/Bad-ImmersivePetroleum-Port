/*     */ package flaxbeard.immersivepetroleum.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Matrix4f;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ @EventBusSubscriber(value = {Dist.CLIENT}, modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*     */ public class MultiblockDistillationTowerRenderer
/*     */   implements BlockEntityRenderer<DistillationTowerTileEntity>
/*     */ {
/*     */   public boolean shouldRenderOffScreen(@Nonnull DistillationTowerTileEntity te) {
/*  26 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(@Nonnull DistillationTowerTileEntity te, float partialTicks, @Nonnull PoseStack transform, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
/*  31 */     if (te.formed && !te.isDummy() && 
/*  32 */       te.shouldRenderAsActive()) {
/*  33 */       combinedOverlayIn = OverlayTexture.f_118083_;
/*     */       
/*  35 */       transform.m_85836_();
/*     */       
/*  37 */       Direction rotation = te.getFacing();
/*  38 */       switch (rotation) {
/*     */         
/*     */         case NORTH:
/*  41 */           transform.m_85837_(3.0D, 0.0D, 4.0D);
/*     */           break;
/*     */         case SOUTH:
/*  44 */           transform.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/*  45 */           transform.m_85837_(2.0D, 0.0D, 3.0D);
/*     */           break;
/*     */         case EAST:
/*  48 */           transform.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/*  49 */           transform.m_85837_(3.0D, 0.0D, 3.0D);
/*     */           break;
/*     */         case WEST:
/*  52 */           transform.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/*  53 */           transform.m_85837_(2.0D, 0.0D, 4.0D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  59 */       float br = 0.75F;
/*     */ 
/*     */ 
/*     */       
/*  63 */       VertexConsumer buf = bufferIn.m_6299_(IPRenderTypes.DISTILLATION_TOWER_ACTIVE);
/*  64 */       if (te.getIsMirrored()) {
/*  65 */         transform.m_85836_();
/*     */         
/*  67 */         transform.m_85837_(-4.0D, 0.0D, -4.0D);
/*  68 */         Matrix4f mat = transform.m_85850_().m_85861_();
/*     */ 
/*     */         
/*  71 */         int ux = 96, vy = 134;
/*  72 */         int w = 32, h = 24;
/*  73 */         float uw = w / 256.0F, vh = h / 256.0F, u0 = ux / 256.0F, v0 = vy / 256.0F, u1 = u0 + uw, v1 = v0 + vh;
/*     */         
/*  75 */         buf.m_85982_(mat, -0.0015F, 0.5F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  76 */         buf.m_85982_(mat, -0.0015F, 0.5F + h / 16.0F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  77 */         buf.m_85982_(mat, -0.0015F, 0.5F + h / 16.0F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  78 */         buf.m_85982_(mat, -0.0015F, 0.5F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */ 
/*     */         
/*  81 */         ux = 96; vy = 158;
/*  82 */         w = 32; h = 24;
/*  83 */         uw = w / 256.0F; vh = h / 256.0F; u0 = ux / 256.0F; v0 = vy / 256.0F; u1 = u0 + uw; v1 = v0 + vh;
/*     */         
/*  85 */         buf.m_85982_(mat, 1.0015F, 0.5F + h / 16.0F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  86 */         buf.m_85982_(mat, 1.0015F, 0.5F + h / 16.0F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  87 */         buf.m_85982_(mat, 1.0015F, 0.5F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  88 */         buf.m_85982_(mat, 1.0015F, 0.5F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */ 
/*     */         
/*  91 */         ux = 80; vy = 134;
/*  92 */         w = 16; h = 24;
/*  93 */         uw = w / 256.0F; vh = h / 256.0F; u0 = ux / 256.0F; v0 = vy / 256.0F; u1 = u0 + uw; v1 = v0 + vh;
/*     */         
/*  95 */         buf.m_85982_(mat, w / 16.0F, 0.5F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  96 */         buf.m_85982_(mat, w / 16.0F, 0.5F + h / 16.0F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  97 */         buf.m_85982_(mat, 0.0F, 0.5F + h / 16.0F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*  98 */         buf.m_85982_(mat, 0.0F, 0.5F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */         
/* 100 */         transform.m_85849_();
/*     */       } else {
/*     */         
/* 103 */         transform.m_85836_();
/*     */         
/* 105 */         transform.m_85837_(-2.0D, 0.0D, -4.0D);
/* 106 */         Matrix4f mat = transform.m_85850_().m_85861_();
/*     */ 
/*     */         
/* 109 */         int ux = 96, vy = 158;
/* 110 */         int w = 32, h = 24;
/* 111 */         float uw = w / 256.0F, vh = h / 256.0F, u0 = ux / 256.0F, v0 = vy / 256.0F, u1 = u0 + uw, v1 = v0 + vh;
/*     */         
/* 113 */         buf.m_85982_(mat, -0.0015F, 0.5F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 114 */         buf.m_85982_(mat, -0.0015F, 0.5F + h / 16.0F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 115 */         buf.m_85982_(mat, -0.0015F, 0.5F + h / 16.0F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 116 */         buf.m_85982_(mat, -0.0015F, 0.5F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */ 
/*     */         
/* 119 */         ux = 96; vy = 134;
/* 120 */         w = 32; h = 24;
/* 121 */         uw = w / 256.0F; vh = h / 256.0F; u0 = ux / 256.0F; v0 = vy / 256.0F; u1 = u0 + uw; v1 = v0 + vh;
/*     */         
/* 123 */         buf.m_85982_(mat, 1.0015F, 0.5F + h / 16.0F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 124 */         buf.m_85982_(mat, 1.0015F, 0.5F + h / 16.0F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 125 */         buf.m_85982_(mat, 1.0015F, 0.5F, w / 16.0F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 126 */         buf.m_85982_(mat, 1.0015F, 0.5F, 0.0F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */ 
/*     */         
/* 129 */         ux = 80; vy = 134;
/* 130 */         w = 16; h = 24;
/* 131 */         uw = w / 256.0F; vh = h / 256.0F; u0 = ux / 256.0F; v0 = vy / 256.0F; u1 = u0 + uw; v1 = v0 + vh;
/*     */         
/* 133 */         buf.m_85982_(mat, w / 16.0F, 0.5F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 134 */         buf.m_85982_(mat, w / 16.0F, 0.5F + h / 16.0F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 135 */         buf.m_85982_(mat, 0.0F, 0.5F + h / 16.0F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 136 */         buf.m_85982_(mat, 0.0F, 0.5F, 2.0015F).m_85950_(br, br, br, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlayIn).m_85969_(combinedLightIn).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */         
/* 138 */         transform.m_85849_();
/*     */       } 
/*     */       
/* 141 */       transform.m_85849_();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\MultiblockDistillationTowerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */