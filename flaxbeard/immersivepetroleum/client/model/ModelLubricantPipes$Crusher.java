/*    */ package flaxbeard.immersivepetroleum.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Crusher
/*    */   extends IPModel
/*    */ {
/*    */   public static final String ID = "crusher_lubepipes";
/*    */   private ModelPart origin;
/*    */   
/*    */   public Crusher() {
/* 22 */     super(RenderType::m_110446_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 27 */     MeshDefinition meshDefinition = new MeshDefinition();
/*    */     
/* 29 */     PartDefinition origin_Definition = meshDefinition.m_171576_().m_171599_("origin", singleCube(20.0F, 8.0F, 9.0F, 12.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*    */     
/* 31 */     origin_Definition.m_171599_("p1", singleCube(-1.0F, -1.0F, 0.0F, 12.0F, 2.0F, 2.0F), PartPose.m_171423_(20.0F, 9.0F, 10.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 32 */     origin_Definition.m_171599_("p2", singleCube(-1.0F, -1.0F, 0.0F, 18.0F, 2.0F, 2.0F), PartPose.m_171423_(31.0F, 9.0F, -10.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 33 */     origin_Definition.m_171599_("p3", singleCube(0.0F, -1.0F, -1.0F, 40.0F, 2.0F, 2.0F), PartPose.m_171423_(30.0F, 10.0F, -10.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/* 34 */     origin_Definition.m_171599_("p5", singleCube(31.0F, 8.0F, 5.0F, 1.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 35 */     origin_Definition.m_171599_("p6", singleCube(23.0F, 48.0F, -11.0F, 6.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 36 */     origin_Definition.m_171599_("p7", singleCube(8.0F, 8.0F, 19.0F, 10.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 37 */     origin_Definition.m_171599_("p8", singleCube(-1.0F, -1.0F, 0.0F, 5.0F, 2.0F, 2.0F), PartPose.m_171423_(8.0F, 9.0F, 17.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 38 */     origin_Definition.m_171599_("p9", singleCube(0.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F), PartPose.m_171423_(7.0F, 10.0F, 17.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*    */     
/* 40 */     this.origin = LayerDefinition.m_171565_(meshDefinition, 16, 16).m_171564_().m_171324_("origin");
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_7695_(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
/* 45 */     this.origin.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\ModelLubricantPipes$Crusher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */