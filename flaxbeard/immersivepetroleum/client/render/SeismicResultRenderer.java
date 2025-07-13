/*    */ package flaxbeard.immersivepetroleum.client.render;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.Tesselator;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Matrix4f;
/*    */ import com.mojang.math.Quaternion;
/*    */ import flaxbeard.immersivepetroleum.client.render.dyn.DynamicTextureWrapper;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.survey.ISurveyInfo;
/*    */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.client.event.RenderHandEvent;
/*    */ import net.minecraftforge.client.event.RenderItemInFrameEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SeismicResultRenderer
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void renderHandEvent(RenderHandEvent event) {
/* 31 */     ItemStack stack = event.getItemStack();
/* 32 */     if (stack.m_41720_().equals(IPContent.Items.SURVEYRESULT.get()) && stack.m_41782_() && stack.m_41737_("surveyscan") != null) {
/* 33 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */   
/* 37 */   static final Tesselator TESSELATOR = new Tesselator();
/*    */   
/* 39 */   private static final ResourceLocation OVERLAY = ResourceUtils.ip("textures/gui/seismicsurvey_overlay.png");
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderItemFrame(RenderItemInFrameEvent event) {
/* 43 */     if (event.getItemStack().m_41720_() instanceof flaxbeard.immersivepetroleum.common.items.SurveyResultItem && MCUtil.getPlayer().m_20270_((Entity)event.getItemFrameEntity()) < 1000.0F) {
/* 44 */       ISurveyInfo iSurveyInfo = ISurveyInfo.from(event.getItemStack()); if (iSurveyInfo instanceof SurveyScan) { SurveyScan scan = (SurveyScan)iSurveyInfo;
/* 45 */         DynamicTextureWrapper wrapper = DynamicTextureWrapper.getOrCreate(65, 65, scan);
/*    */         
/* 47 */         if (wrapper != null) {
/* 48 */           PoseStack matrix = event.getPoseStack();
/*    */           
/* 50 */           MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(TESSELATOR.m_85915_());
/*    */           
/* 52 */           matrix.m_85836_();
/*    */           
/* 54 */           int light = event.getPackedLight();
/* 55 */           int rot = event.getItemFrameEntity().m_31823_();
/* 56 */           float scale = 0.015384615F;
/* 57 */           matrix.m_85845_(new Quaternion(0.0F, 0.0F, 180.0F - -rot * 45.0F, true));
/* 58 */           matrix.m_85837_(-0.5D, -0.5D, -0.0062500000931322575D);
/* 59 */           matrix.m_85841_(scale, scale, 1.0F);
/*    */           
/* 61 */           matrix.m_85836_();
/*    */           
/* 63 */           int a = wrapper.width;
/* 64 */           int b = wrapper.height;
/* 65 */           VertexConsumer builder = buffer.m_6299_(wrapper.renderType);
/* 66 */           Matrix4f mat = matrix.m_85850_().m_85861_();
/*    */           
/* 68 */           builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(-1).m_7421_(1.0F, 1.0F).m_85969_(light).m_5752_();
/* 69 */           builder.m_85982_(mat, 0.0F, b, 0.0F).m_193479_(-1).m_7421_(1.0F, 0.0F).m_85969_(light).m_5752_();
/* 70 */           builder.m_85982_(mat, a, b, 0.0F).m_193479_(-1).m_7421_(0.0F, 0.0F).m_85969_(light).m_5752_();
/* 71 */           builder.m_85982_(mat, a, 0.0F, 0.0F).m_193479_(-1).m_7421_(0.0F, 1.0F).m_85969_(light).m_5752_();
/*    */           
/* 73 */           matrix.m_85849_();
/*    */ 
/*    */           
/* 76 */           matrix.m_85836_();
/*    */           
/* 78 */           int w = 65;
/* 79 */           int h = 65;
/*    */           
/* 81 */           matrix.m_85837_(0.0D, 0.0D, -0.0020000000949949026D);
/*    */           
/* 83 */           builder = buffer.m_6299_(RenderType.m_110497_(OVERLAY));
/* 84 */           mat = matrix.m_85850_().m_85861_();
/*    */           
/* 86 */           builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(-1).m_7421_(0.0F, 0.0F).m_85969_(light).m_5752_();
/* 87 */           builder.m_85982_(mat, 0.0F, 65.0F, 0.0F).m_193479_(-1).m_7421_(0.0F, 1.0F).m_85969_(light).m_5752_();
/* 88 */           builder.m_85982_(mat, 65.0F, 65.0F, 0.0F).m_193479_(-1).m_7421_(1.0F, 1.0F).m_85969_(light).m_5752_();
/* 89 */           builder.m_85982_(mat, 65.0F, 0.0F, 0.0F).m_193479_(-1).m_7421_(1.0F, 0.0F).m_85969_(light).m_5752_();
/*    */           
/* 91 */           matrix.m_85849_();
/*    */           
/* 93 */           matrix.m_85849_();
/*    */           
/* 95 */           buffer.m_109911_();
/* 96 */           event.setCanceled(true);
/*    */         }  }
/*    */     
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\SeismicResultRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */