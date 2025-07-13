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
/*     */ public class Excavator
/*     */   extends IPModel
/*     */ {
/*     */   public static final String ID_NORMAL = "excavator_lubepipes_normal";
/*     */   public static final String ID_MIRRORED = "excavator_lubepipes_mirrored";
/*     */   private ModelPart origin;
/*     */   private boolean mirrored;
/*     */   
/*     */   public Excavator(boolean mirror) {
/*  56 */     super(RenderType::m_110446_);
/*  57 */     this.mirrored = mirror;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  62 */     MeshDefinition meshDefinition = new MeshDefinition();
/*     */     
/*  64 */     if (this.mirrored) {
/*  65 */       PartDefinition origin_Definition = meshDefinition.m_171576_().m_171599_("origin", singleCube(51.0F, 8.0F, 6.0F, 20.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/*  67 */       origin_Definition.m_171599_("p1", singleCube(-1.0F, -1.0F, 0.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(71.0F, 9.0F, 1.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  68 */       origin_Definition.m_171599_("p2", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 9.0F, 3.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  69 */       origin_Definition.m_171599_("p3", singleCube(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 10.0F, 3.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  70 */       origin_Definition.m_171599_("p4", singleCube(0.0F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 32.0F, 8.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  71 */       origin_Definition.m_171599_("p5", singleCube(48.0F, 39.0F, 7.0F, 3.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  72 */       origin_Definition.m_171599_("p6", singleCube(0.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 16.0F, -1.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  73 */       origin_Definition.m_171599_("p7", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 15.0F, -1.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  74 */       origin_Definition.m_171599_("p8", singleCube(-1.0F, -1.0F, 0.0F, 7.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 33.0F, 1.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  75 */       origin_Definition.m_171599_("p9", singleCube(48.0F, 39.0F, 39.0F, 3.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  76 */       origin_Definition.m_171599_("p10", singleCube(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.m_171423_(75.0F, 9.0F, 1.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  77 */       origin_Definition.m_171599_("p11", singleCube(73.0F, 8.0F, 2.0F, 16.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  78 */       origin_Definition.m_171599_("p12", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(89.0F, 9.0F, 5.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*     */     } else {
/*     */       
/*  81 */       PartDefinition origin_Definition = meshDefinition.m_171576_().m_171599_("origin", singleCube(51.0F, 8.0F, 40.0F, 20.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*     */       
/*  83 */       origin_Definition.m_171599_("p1", singleCube(-1.0F, -1.0F, 0.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(71.0F, 9.0F, 43.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  84 */       origin_Definition.m_171599_("p2", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 9.0F, 43.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  85 */       origin_Definition.m_171599_("p3", singleCube(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 10.0F, 45.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  86 */       origin_Definition.m_171599_("p4", singleCube(0.0F, -1.0F, -1.0F, 9.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 32.0F, 40.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  87 */       origin_Definition.m_171599_("p5", singleCube(48.0F, 39.0F, 39.0F, 3.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  88 */       origin_Definition.m_171599_("p6", singleCube(0.0F, -1.0F, -1.0F, 18.0F, 2.0F, 2.0F), PartPose.m_171423_(52.0F, 16.0F, 49.0F, 0.0F, 0.0F, (float)Math.toRadians(90.0D)));
/*  89 */       origin_Definition.m_171599_("p7", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 15.0F, 47.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  90 */       origin_Definition.m_171599_("p8", singleCube(-1.0F, -1.0F, 0.0F, 7.0F, 2.0F, 2.0F), PartPose.m_171423_(53.0F, 33.0F, 42.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  91 */       origin_Definition.m_171599_("p9", singleCube(48.0F, 39.0F, 39.0F, 3.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  92 */       origin_Definition.m_171599_("p10", singleCube(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F), PartPose.m_171423_(75.0F, 9.0F, 47.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*  93 */       origin_Definition.m_171599_("p11", singleCube(73.0F, 8.0F, 44.0F, 16.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  94 */       origin_Definition.m_171599_("p12", singleCube(-1.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171423_(89.0F, 9.0F, 41.0F, 0.0F, (float)Math.toRadians(270.0D), 0.0F));
/*     */     } 
/*     */     
/*  97 */     this.origin = LayerDefinition.m_171565_(meshDefinition, 16, 16).m_171564_().m_171324_("origin");
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7695_(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
/* 102 */     this.origin.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\ModelLubricantPipes$Excavator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */