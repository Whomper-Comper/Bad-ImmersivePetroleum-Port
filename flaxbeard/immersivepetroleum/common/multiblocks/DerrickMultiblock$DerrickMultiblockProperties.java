/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import flaxbeard.immersivepetroleum.client.render.DerrickRenderer;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraftforge.client.model.data.ModelData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DerrickMultiblockProperties
/*    */   extends IPClientMultiblockProperties
/*    */ {
/*    */   public DerrickMultiblockProperties() {
/* 50 */     super(DerrickMultiblock.INSTANCE, 2.5D, 0.5D, 2.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderExtras(PoseStack matrix, MultiBufferSource buffer) {
/* 55 */     matrix.m_85837_(0.0D, 0.5D, 0.0D);
/* 56 */     renderObj(DerrickRenderer.DRILL, buffer, matrix);
/* 57 */     for (int i = 0; i < 6; i++) {
/* 58 */       matrix.m_85836_();
/*    */       
/* 60 */       matrix.m_85837_(0.0D, i + 0.75D, 0.0D);
/* 61 */       renderObj((i < 5) ? DerrickRenderer.PIPE_SEGMENT : DerrickRenderer.PIPE_TOP, buffer, matrix);
/*    */       
/* 63 */       matrix.m_85849_();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void renderObj(ResourceLocation modelRL, @Nonnull MultiBufferSource bufferIn, @Nonnull PoseStack matrix) {
/* 68 */     List<BakedQuad> quads = MCUtil.getModel(modelRL).getQuads(null, null, ApiUtils.RANDOM_SOURCE, ModelData.EMPTY, null);
/* 69 */     PoseStack.Pose last = matrix.m_85850_();
/* 70 */     VertexConsumer solid = bufferIn.m_6299_(RenderType.m_110451_());
/* 71 */     for (BakedQuad quad : quads)
/* 72 */       solid.m_85987_(last, quad, 1.0F, 1.0F, 1.0F, 15728880, OverlayTexture.f_118083_); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\DerrickMultiblock$DerrickMultiblockProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */