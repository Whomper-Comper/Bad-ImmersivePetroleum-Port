/*     */ package flaxbeard.immersivepetroleum.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class ModelPumpjack
/*     */   extends IPModel
/*     */ {
/*     */   public static final String ID = "pumpjackarm";
/*  20 */   public static final ResourceLocation TEXTURE = ResourceUtils.ip("textures/models/pumpjack_armature.png");
/*     */   
/*     */   public ModelPart origin;
/*     */   
/*     */   public ModelPart swingy;
/*     */   public ModelPart connector;
/*     */   public ModelPart arm;
/*     */   public ModelPart wellConnector;
/*     */   public ModelPart wellConnector2;
/*  29 */   public float ticks = 0.0F;
/*     */   
/*     */   public ModelPumpjack() {
/*  32 */     super(IPRenderTypes::getEntitySolid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  40 */     MeshDefinition meshDefinition = new MeshDefinition();
/*  41 */     PartDefinition rootDefinition = meshDefinition.m_171576_();
/*     */     
/*  43 */     PartDefinition origin_Definition = rootDefinition.m_171599_("origin", singleCube(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.f_171404_);
/*  44 */     PartDefinition arm_Definition = origin_Definition.m_171599_("arm", singleCube(0, 40, -40.0F, 0.0F, -4.0F, 70.0F, 10.0F, 8.0F), PartPose.m_171419_(56.0F, 48.0F, 24.0F));
/*  45 */     arm_Definition.m_171599_("head", singleCube(30.0F, -15.0F, -5.0F, 12.0F, 30.0F, 10.0F), PartPose.f_171404_);
/*  46 */     arm_Definition.m_171599_("barBack", singleCube(138, 0, -35.0F, 3.0F, -11.0F, 4.0F, 4.0F, 22.0F), PartPose.f_171404_);
/*  47 */     PartDefinition swingy_Definition = origin_Definition.m_171599_("swingy", singleCube(44, 14, -4.0F, -2.0F, -14.0F, 8.0F, 10.0F, 4.0F), PartPose.m_171419_(24.0F, 30.0F, 30.0F));
/*  48 */     swingy_Definition.m_171599_("swingy2", singleCube(44, 14, -4.0F, -2.0F, -2.0F, 8.0F, 10.0F, 4.0F), PartPose.f_171404_);
/*  49 */     swingy_Definition.m_171599_("counter", singleCube(44, 0, -12.0F, 8.0F, -14.0F, 24.0F, 10.0F, 4.0F), PartPose.f_171404_);
/*  50 */     swingy_Definition.m_171599_("counter2", singleCube(44, 0, -12.0F, 8.0F, -2.0F, 24.0F, 10.0F, 4.0F), PartPose.f_171404_);
/*  51 */     PartDefinition connector_Definition = origin_Definition.m_171599_("connector", singleCube(108, 0, -1.0F, -1.0F, -12.0F, 2.0F, 24.0F, 2.0F), PartPose.f_171404_);
/*  52 */     connector_Definition.m_171599_("connector2", singleCube(100, 0, -1.0F, -1.0F, 6.0F, 2.0F, 24.0F, 2.0F), PartPose.f_171404_);
/*  53 */     origin_Definition.m_171599_("wellConnector", singleCube(108, 0, -1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F), PartPose.f_171404_);
/*  54 */     origin_Definition.m_171599_("wellConnector2", singleCube(108, 0, -1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F), PartPose.f_171404_);
/*     */     
/*  56 */     LayerDefinition layerDefinition = LayerDefinition.m_171565_(meshDefinition, 190, 58);
/*  57 */     ModelPart root = layerDefinition.m_171564_();
/*     */     
/*  59 */     this.origin = root.m_171324_("origin");
/*  60 */     this.arm = this.origin.m_171324_("arm");
/*  61 */     this.swingy = this.origin.m_171324_("swingy");
/*  62 */     this.connector = this.origin.m_171324_("connector");
/*  63 */     this.wellConnector = this.origin.m_171324_("wellConnector");
/*  64 */     this.wellConnector2 = this.origin.m_171324_("wellConnector2");
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7695_(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
/*  69 */     this.arm.f_104205_ = (float)Math.toRadians(15.0D * Math.sin((this.ticks / 25.0F)));
/*  70 */     this.swingy.f_104205_ = (float)(1.5707963267948966D + (this.ticks / 25.0F));
/*     */     
/*  72 */     float dist = 8.5F;
/*     */     
/*  74 */     float sin = Mth.m_14031_(this.swingy.f_104205_);
/*  75 */     float cos = Mth.m_14089_(this.swingy.f_104205_);
/*  76 */     this.connector.m_104227_(24.0F - dist * sin, 30.0F + dist * cos, 26.0F);
/*  77 */     if (sin < 0.0F) {
/*  78 */       this.connector.f_104205_ = (float)(1.5707963705062866D + Math.atan((25.0F / dist * sin)));
/*  79 */     } else if (sin > 0.0F) {
/*  80 */       this.connector.f_104205_ = (float)(4.71238899230957D + Math.atan((25.0F / dist * sin)));
/*     */     } 
/*     */     
/*  83 */     float sin2 = Mth.m_14031_(this.arm.f_104205_);
/*  84 */     float cos2 = Mth.m_14089_(this.arm.f_104205_);
/*     */     
/*  86 */     float x = 24.0F - dist * sin;
/*  87 */     float y = 30.0F + dist * cos;
/*     */     
/*  89 */     float w = 33.0F;
/*  90 */     float h = 4.0F;
/*     */     
/*  92 */     float tx = 56.0F + w * -cos2 - h * sin2;
/*  93 */     float ty = 48.0F + w * -sin2 + h * cos2;
/*     */     
/*  95 */     this.connector.m_104227_(x, y, 26.0F);
/*  96 */     this.connector.f_104205_ = (float)(4.71238899230957D + Math.atan2((ty - y), (tx - x)));
/*     */     
/*  98 */     this.wellConnector.m_104227_(88.0F, 16.0F, 24.0F);
/*  99 */     this.wellConnector2.m_104227_(88.0F, 16.0F, 24.0F);
/*     */     
/* 101 */     float w2 = -34.0F;
/* 102 */     float h2 = -13.0F;
/*     */     
/* 104 */     float x2 = w2 * -cos2 - h2 * sin2;
/* 105 */     float y2 = w2 * -sin2 + h2 * cos2;
/*     */     
/* 107 */     float tx2 = 32.0F;
/* 108 */     float ty2 = -32.0F;
/*     */     
/* 110 */     this.wellConnector.m_104227_(56.0F + x2, 48.0F + y2, 24.0F);
/* 111 */     this.wellConnector2.m_104227_(56.0F + x2, 48.0F + y2, 24.0F);
/*     */     
/* 113 */     float zRot = (float)(4.71238899230957D + Math.atan2((ty2 - y2), (tx2 - x2)));
/* 114 */     this.wellConnector.f_104205_ = zRot;
/* 115 */     this.wellConnector2.f_104205_ = zRot;
/*     */     
/* 117 */     double sqrt = Math.sqrt(((tx2 - x2) * (tx2 - x2) + (ty2 - y2) * (ty2 - y2)));
/* 118 */     if (sqrt <= 16.0D) {
/* 119 */       this.wellConnector.f_104207_ = true;
/* 120 */       this.wellConnector2.f_104207_ = false;
/*     */     } else {
/* 122 */       this.wellConnector.f_104207_ = true;
/* 123 */       this.wellConnector2.f_104207_ = true;
/*     */     } 
/*     */     
/* 126 */     this.origin.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\ModelPumpjack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */