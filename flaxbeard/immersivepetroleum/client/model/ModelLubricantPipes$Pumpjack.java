/*     */ package flaxbeard.immersivepetroleum.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pumpjack
/*     */   extends IPModel
/*     */ {
/*     */   public static final String ID_NORMAL = "pumpjack_lubepipes_normal";
/*     */   public static final String ID_MIRRORED = "pumpjack_lubepipes_mirrored";
/*     */   private boolean mirrored = false;
/*     */   private ModelPart origin;
/*     */   
/*     */   public Pumpjack(boolean mirror) {
/* 113 */     super(RenderType::m_110446_);
/* 114 */     this.mirrored = mirror;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 119 */     MeshDefinition meshDefinition = new MeshDefinition();
/*     */     
/* 121 */     if (this.mirrored) {
/* 122 */       PartDefinition origin_Definition = meshDefinition.m_171576_().m_171599_("origin", singleCube(21.0F, 8.0F, 12.0F, 15.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/* 124 */       origin_Definition.m_171599_("p1", singleCube(-1.0F, -1.0F, 0.0F, 12.0F, 2.0F, 2.0F), PartPose.m_171423_(23.0F, 9.0F, 1.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 125 */       origin_Definition.m_171599_("p2", singleCube(-1.0F, -1.0F, 0.0F, 13.0F, 2.0F, 2.0F), PartPose.m_171423_(38.0F, 9.0F, 13.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 126 */       origin_Definition.m_171599_("p3", singleCube(34.0F, 8.0F, 23.0F, 2.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 127 */       origin_Definition.m_171599_("p4", singleCube(0.0F, -1.0F, -1.0F, 30.0F, 2.0F, 2.0F), PartPose.m_171423_(33.0F, 8.0F, 24.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/* 128 */       origin_Definition.m_171599_("p5", singleCube(24.0F, 36.0F, 23.0F, 8.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 129 */       origin_Definition.m_171599_("p6", singleCube(0.0F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F), PartPose.m_171423_(26.0F, 9.01F, 0.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 130 */       origin_Definition.m_171599_("p7", singleCube(25.0F, 8.0F, 8.5F, 18.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/* 132 */       PartDefinition leg1_Definition = origin_Definition.m_171599_("leg1", empty(), PartPose.m_171423_(42.4F, 8.0F, 12.0F, (float)Math.toRadians(9.0D), (float)Math.toRadians(20.0D), (float)Math.toRadians(-14.0D)));
/* 133 */       leg1_Definition.m_171599_("leg2", singleCube(1.0F, -1.0F, -4.0F, 38.0F, 2.0F, 2.0F), PartPose.m_171430_(0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*     */       
/* 135 */       origin_Definition.m_171599_("p8", singleCube(0.0F, 0.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(52.5F, 43.3F, 14.7F, 0.0F, (float)Math.toRadians(30.0D), 0.0F));
/* 136 */       origin_Definition.m_171599_("p9", singleCube(0.0F, -2.0F, 0.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(55.0F, 43.3F, 13.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*     */     } else {
/*     */       
/* 139 */       PartDefinition origin_Definition = meshDefinition.m_171576_().m_171599_("origin", singleCube(21.0F, 8.0F, 34.0F, 15.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/* 141 */       origin_Definition.m_171599_("p1", singleCube(-1.0F, -1.0F, 0.0F, 12.0F, 2.0F, 2.0F), PartPose.m_171423_(23.0F, 9.0F, 37.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 142 */       origin_Definition.m_171599_("p2", singleCube(-1.0F, -1.0F, 0.0F, 13.0F, 2.0F, 2.0F), PartPose.m_171423_(38.0F, 9.0F, 24.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 143 */       origin_Definition.m_171599_("p3", singleCube(34.0F, 8.0F, 23.0F, 2.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 144 */       origin_Definition.m_171599_("p4", singleCube(0.0F, -1.0F, -1.0F, 30.0F, 2.0F, 2.0F), PartPose.m_171423_(33.0F, 8.0F, 24.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/* 145 */       origin_Definition.m_171599_("p5", singleCube(24.0F, 36.0F, 23.0F, 8.0F, 2.0F, 2.0F), PartPose.f_171404_);
/* 146 */       origin_Definition.m_171599_("p6", singleCube(39.0F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F), PartPose.m_171423_(26.0F, 9.01F, 0.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/* 147 */       origin_Definition.m_171599_("p7", singleCube(25.0F, 8.0F, 38.5F, 18.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/* 149 */       PartDefinition leg1_Definition = origin_Definition.m_171599_("leg1", empty(), PartPose.m_171423_(42.4F, 8.0F, 36.0F, (float)Math.toRadians(-10.0D), (float)Math.toRadians(-20.0D), (float)Math.toRadians(-15.0D)));
/* 150 */       leg1_Definition.m_171599_("leg2", singleCube(1.0F, -1.0F, 3.0F, 38.0F, 2.0F, 2.0F), PartPose.m_171430_(0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*     */       
/* 152 */       origin_Definition.m_171599_("p8", singleCube(0.0F, 0.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 43.3F, 31.7F, 0.0F, (float)Math.toRadians(-30.0D), 0.0F));
/* 153 */       origin_Definition.m_171599_("p9", singleCube(0.0F, -2.0F, 0.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(55.0F, 43.3F, 33.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*     */     } 
/*     */     
/* 156 */     this.origin = LayerDefinition.m_171565_(meshDefinition, 16, 16).m_171564_().m_171324_("origin");
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7695_(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
/* 161 */     this.origin.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\ModelLubricantPipes$Pumpjack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */