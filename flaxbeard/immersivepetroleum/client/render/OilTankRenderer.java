/*     */ package flaxbeard.immersivepetroleum.client.render;
/*     */ 
/*     */ import blusunrize.immersiveengineering.client.utils.GuiHelper;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Matrix4f;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ @EventBusSubscriber(value = {Dist.CLIENT}, modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*     */ public class OilTankRenderer
/*     */   implements BlockEntityRenderer<OilTankTileEntity>
/*     */ {
/*     */   public boolean shouldRenderOffScreen(@Nonnull OilTankTileEntity te) {
/*  31 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(OilTankTileEntity te, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
/*  37 */     if (!te.formed || te.isDummy() || !te.getLevelNonnull().m_46805_(te.m_58899_())) {
/*     */       return;
/*     */     }
/*  40 */     combinedOverlay = OverlayTexture.f_118083_;
/*     */     
/*  42 */     matrix.m_85836_();
/*     */     
/*  44 */     switch (te.getFacing()) {
/*     */       case EAST:
/*  46 */         matrix.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/*  47 */         matrix.m_85837_(0.0D, 0.0D, -1.0D);
/*     */         break;
/*     */       case SOUTH:
/*  50 */         matrix.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/*  51 */         matrix.m_85837_(-1.0D, 0.0D, -1.0D);
/*     */         break;
/*     */       case WEST:
/*  54 */         matrix.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/*  55 */         matrix.m_85837_(-1.0D, 0.0D, 0.0D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     matrix.m_85836_();
/*     */     
/*  64 */     matrix.m_85837_(1.0D, 2.0D, 2.994999885559082D);
/*     */ 
/*     */     
/*  67 */     Matrix4f mat = matrix.m_85850_().m_85861_();
/*  68 */     VertexConsumer builder = buffer.m_6299_(IPRenderTypes.TRANSLUCENT_POSITION_COLOR);
/*  69 */     builder.m_85982_(mat, 1.5F, -0.5F, 0.0F).m_6122_(34, 34, 34, 255).m_5752_();
/*  70 */     builder.m_85982_(mat, 1.5F, 1.0F, 0.0F).m_6122_(34, 34, 34, 255).m_5752_();
/*  71 */     builder.m_85982_(mat, 0.0F, 1.0F, 0.0F).m_6122_(34, 34, 34, 255).m_5752_();
/*  72 */     builder.m_85982_(mat, 0.0F, -0.5F, 0.0F).m_6122_(34, 34, 34, 255).m_5752_();
/*     */     
/*  74 */     OilTankTileEntity master = (OilTankTileEntity)te.master();
/*  75 */     if (master != null) {
/*  76 */       FluidStack fs = master.tank.getFluid();
/*  77 */       if (!fs.isEmpty()) {
/*  78 */         matrix.m_85836_();
/*     */         
/*  80 */         matrix.m_85837_(0.25D, 0.875D, 0.0024999999441206455D);
/*  81 */         matrix.m_85841_(0.0625F, -0.0625F, 0.0625F);
/*     */         
/*  83 */         float h = fs.getAmount() / master.tank.getCapacity();
/*  84 */         GuiHelper.drawRepeatedFluidSprite(buffer.m_6299_(RenderType.m_110451_()), matrix, fs, 0.0F, 0.0F + (1.0F - h) * 16.0F, 16.0F, h * 16.0F);
/*     */         
/*  86 */         matrix.m_85849_();
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     matrix.m_85849_();
/*     */     
/*  92 */     matrix.m_85836_();
/*     */ 
/*     */     
/*  95 */     if (te.getIsMirrored()) {
/*  96 */       OilTankTileEntity oilTankTileEntity = (OilTankTileEntity)te.master();
/*  97 */       if (oilTankTileEntity != null) {
/*  98 */         for (OilTankTileEntity.Port port : OilTankTileEntity.Port.DYNAMIC_PORTS) {
/*  99 */           matrix.m_85836_();
/*     */           
/* 101 */           BlockPos p = port.posInMultiblock.m_121996_((Vec3i)te.posInMultiblock);
/* 102 */           matrix.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/* 103 */           matrix.m_85837_((p.m_123341_() - 1), p.m_123342_(), (-p.m_123343_() - 1));
/* 104 */           quad(matrix, buffer, oilTankTileEntity.getPortStateFor(port), (port.posInMultiblock.m_123341_() == 4), combinedLight, combinedOverlay);
/*     */           
/* 106 */           matrix.m_85849_();
/*     */         } 
/*     */       }
/*     */     } else {
/* 110 */       OilTankTileEntity oilTankTileEntity = (OilTankTileEntity)te.master();
/* 111 */       if (oilTankTileEntity != null) {
/* 112 */         for (OilTankTileEntity.Port port : OilTankTileEntity.Port.DYNAMIC_PORTS) {
/* 113 */           matrix.m_85836_();
/*     */           
/* 115 */           BlockPos p = port.posInMultiblock.m_121996_((Vec3i)te.posInMultiblock);
/* 116 */           matrix.m_85837_(p.m_123341_(), p.m_123342_(), p.m_123343_());
/* 117 */           quad(matrix, buffer, oilTankTileEntity.getPortStateFor(port), (port.posInMultiblock.m_123341_() == 4), combinedLight, combinedOverlay);
/*     */           
/* 119 */           matrix.m_85849_();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 124 */     matrix.m_85849_();
/*     */     
/* 126 */     matrix.m_85849_();
/*     */   }
/*     */   
/*     */   public void quad(PoseStack matrix, MultiBufferSource buffer, OilTankTileEntity.PortState portState, boolean flip, int combinedLight, int combinedOverlay) {
/* 130 */     Matrix4f mat = matrix.m_85850_().m_85861_();
/* 131 */     VertexConsumer builder = buffer.m_6299_(IPRenderTypes.OIL_TANK);
/*     */     
/* 133 */     boolean input = (portState == OilTankTileEntity.PortState.INPUT);
/* 134 */     float u0 = input ? 0.0F : 0.1F, v0 = 0.5F;
/* 135 */     float u1 = u0 + 0.1F, v1 = v0 + 0.1F;
/* 136 */     if (flip) {
/* 137 */       builder.m_85982_(mat, 1.001F, 0.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 138 */       builder.m_85982_(mat, 1.001F, 1.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 139 */       builder.m_85982_(mat, 1.001F, 1.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 140 */       builder.m_85982_(mat, 1.001F, 0.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */     } else {
/* 142 */       builder.m_85982_(mat, -0.001F, 0.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u0, v1).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 143 */       builder.m_85982_(mat, -0.001F, 0.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u1, v1).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 144 */       builder.m_85982_(mat, -0.001F, 1.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u1, v0).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/* 145 */       builder.m_85982_(mat, -0.001F, 1.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(u0, v0).m_86008_(combinedOverlay).m_85969_(combinedLight).m_5601_(1.0F, 1.0F, 1.0F).m_5752_();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\OilTankRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */