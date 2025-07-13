/*    */ package flaxbeard.immersivepetroleum.client.render;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.utils.GuiHelper;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Quaternion;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoLubricatorRenderer
/*    */   implements BlockEntityRenderer<AutoLubricatorTileEntity>
/*    */ {
/*    */   public boolean shouldRenderOffScreen(@Nonnull AutoLubricatorTileEntity te) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void render(@Nonnull AutoLubricatorTileEntity te, float partialTicks, @Nonnull PoseStack transform, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
/* 32 */     if (te.isSlave) {
/*    */       return;
/*    */     }
/* 35 */     FluidStack fs = te.tank.getFluid();
/* 36 */     float level = 0.0F;
/* 37 */     if (!fs.isEmpty()) {
/* 38 */       level = fs.getAmount() / te.tank.getCapacity();
/*    */     }
/*    */     
/* 41 */     if (level > 0.0F) {
/* 42 */       float height = 16.0F;
/*    */       
/* 44 */       transform.m_85836_();
/*    */       
/* 46 */       float scale = 0.0625F;
/* 47 */       transform.m_85837_(0.25D, 0.875D, 0.25D);
/* 48 */       transform.m_85841_(scale, scale, scale);
/*    */       
/* 50 */       VertexConsumer builder = bufferIn.m_6299_(RenderType.m_110451_());
/*    */       
/* 52 */       float h = height * level;
/* 53 */       GuiHelper.drawRepeatedFluidSprite(builder, transform, fs, 0.0F, 0.0F, 8.0F, h);
/* 54 */       transform.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 55 */       transform.m_85837_(-7.98D, 0.0D, 0.0D);
/* 56 */       GuiHelper.drawRepeatedFluidSprite(builder, transform, fs, 0.0F, 0.0F, 8.0F, h);
/* 57 */       transform.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 58 */       transform.m_85837_(-7.98D, 0.0D, 0.0D);
/* 59 */       GuiHelper.drawRepeatedFluidSprite(builder, transform, fs, 0.0F, 0.0F, 8.0F, h);
/* 60 */       transform.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 61 */       transform.m_85837_(-7.98D, 0.0D, 0.0D);
/* 62 */       GuiHelper.drawRepeatedFluidSprite(builder, transform, fs, 0.0F, 0.0F, 8.0F, h);
/* 63 */       if (h < height) {
/* 64 */         transform.m_85845_(new Quaternion(90.0F, 0.0F, 0.0F, true));
/* 65 */         transform.m_85837_(0.0D, 0.0D, -h);
/* 66 */         GuiHelper.drawRepeatedFluidSprite(builder, transform, fs, 0.0F, 0.0F, 8.0F, 8.0F);
/*    */       } 
/*    */       
/* 69 */       transform.m_85849_();
/*    */     } 
/*    */     
/* 72 */     transform.m_85836_();
/*    */     
/* 74 */     BlockPos target = te.m_58899_().m_121945_(te.getFacing());
/* 75 */     BlockEntity test = te.m_58904_().m_7702_(target);
/*    */     
/* 77 */     LubricatedHandler.ILubricationHandler<BlockEntity> handler = LubricatedHandler.getHandlerForTile(test);
/* 78 */     if (handler != null) {
/* 79 */       BlockEntity master = handler.isPlacedCorrectly(te.m_58904_(), te, te.getFacing());
/* 80 */       if (master != null) {
/* 81 */         handler.renderPipes(te, master, transform, bufferIn, combinedLightIn, combinedOverlayIn);
/*    */       }
/*    */     } 
/*    */     
/* 85 */     transform.m_85849_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\AutoLubricatorRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */