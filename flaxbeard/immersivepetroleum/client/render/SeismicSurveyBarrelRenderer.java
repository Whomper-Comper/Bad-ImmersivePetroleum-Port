/*    */ package flaxbeard.immersivepetroleum.client.render;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.SeismicSurveyTileEntity;
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
/*    */ 
/*    */ public class SeismicSurveyBarrelRenderer
/*    */   implements BlockEntityRenderer<SeismicSurveyTileEntity>
/*    */ {
/* 24 */   public static final ResourceLocation BARREL = ResourceUtils.ip("block/dyn/seismic_survey_tool_barrel");
/*    */ 
/*    */   
/*    */   public boolean shouldRenderOffScreen(@Nonnull SeismicSurveyTileEntity pBlockEntity) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(SeismicSurveyTileEntity te, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int light, int overlay) {
/* 34 */     if (te.isSlave || !te.m_58904_().m_46805_(te.m_58899_())) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     matrix.m_85836_();
/*    */     
/* 40 */     double d = te.timer / 10.0D;
/*    */     
/* 42 */     matrix.m_85837_(0.0D, -0.125D * (1.0D - d), 0.0D);
/*    */     
/* 44 */     List<BakedQuad> quads = MCUtil.getModel(BARREL).getQuads(null, null, ApiUtils.RANDOM_SOURCE, ModelData.EMPTY, null);
/* 45 */     PoseStack.Pose last = matrix.m_85850_();
/* 46 */     VertexConsumer solid = buffer.m_6299_(RenderType.m_110451_());
/* 47 */     for (BakedQuad quad : quads) {
/* 48 */       solid.m_85987_(last, quad, 1.0F, 1.0F, 1.0F, light, overlay);
/*    */     }
/*    */     
/* 51 */     matrix.m_85849_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\SeismicSurveyBarrelRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */