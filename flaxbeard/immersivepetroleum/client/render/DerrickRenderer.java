/*    */ package flaxbeard.immersivepetroleum.client.render;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Quaternion;
/*    */ import com.mojang.math.Vector3f;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.client.model.data.ModelData;
/*    */ 
/*    */ 
/*    */ public class DerrickRenderer
/*    */   implements BlockEntityRenderer<DerrickTileEntity>
/*    */ {
/* 25 */   static final Vector3f Y_AXIS = new Vector3f(0.0F, 1.0F, 0.0F);
/*    */   
/* 27 */   public static final ResourceLocation DRILL = ResourceUtils.ip("multiblock/dyn/derrick_drill");
/* 28 */   public static final ResourceLocation PIPE_SEGMENT = ResourceUtils.ip("multiblock/dyn/derrick_pipe_segment");
/* 29 */   public static final ResourceLocation PIPE_TOP = ResourceUtils.ip("multiblock/dyn/derrick_pipe_top");
/*    */ 
/*    */   
/*    */   public boolean shouldRenderOffScreen(@Nonnull DerrickTileEntity te) {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(DerrickTileEntity te, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
/* 39 */     if (!te.formed || te.isDummy() || !te.getLevelNonnull().m_46805_(te.m_58899_())) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     matrix.m_85836_();
/*    */     
/* 45 */     float rot = te.rotation + (te.drilling ? (10.0F * partialTicks) : 0.0F);
/*    */     
/* 47 */     matrix.m_85837_(0.5D, 1.0D, 0.5D);
/* 48 */     matrix.m_85845_(new Quaternion(Y_AXIS, rot, true));
/* 49 */     renderObj(DRILL, bufferIn, matrix, combinedLightIn, combinedOverlayIn);
/*    */     
/* 51 */     float pipeHeight = -(rot / 360.0F);
/*    */     
/* 53 */     for (int i = 0; i < 6; i++) {
/* 54 */       float y = pipeHeight + i;
/* 55 */       if (y > -1.0D) {
/* 56 */         matrix.m_85836_();
/*    */         
/* 58 */         matrix.m_85837_(0.0D, y + 0.75D, 0.0D);
/* 59 */         renderObj((i < 5) ? PIPE_SEGMENT : PIPE_TOP, bufferIn, matrix, combinedLightIn, combinedOverlayIn);
/*    */         
/* 61 */         matrix.m_85849_();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 66 */     matrix.m_85849_();
/*    */   }
/*    */   
/*    */   private void renderObj(ResourceLocation modelRL, @Nonnull MultiBufferSource bufferIn, @Nonnull PoseStack matrix, int light, int overlay) {
/* 70 */     List<BakedQuad> quads = MCUtil.getModel(modelRL).getQuads(null, null, ApiUtils.RANDOM_SOURCE, ModelData.EMPTY, null);
/* 71 */     PoseStack.Pose last = matrix.m_85850_();
/* 72 */     VertexConsumer solid = bufferIn.m_6299_(RenderType.m_110451_());
/* 73 */     for (BakedQuad quad : quads)
/* 74 */       solid.m_85987_(last, quad, 1.0F, 1.0F, 1.0F, light, overlay); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\DerrickRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */