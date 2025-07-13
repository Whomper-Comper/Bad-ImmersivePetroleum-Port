/*     */ package flaxbeard.immersivepetroleum.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.model.ListModel;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class ModelMotorboat
/*     */   extends ListModel<MotorboatEntity>
/*     */ {
/*     */   private final List<ModelPart> list;
/*     */   public ModelPart noWater;
/*  32 */   public ModelPart[] boatSides = new ModelPart[5];
/*     */   
/*     */   public ModelPart motor;
/*     */   public ModelPart propeller;
/*     */   public ModelPart propellerAssembly;
/*     */   public ModelPart icebreak;
/*     */   public ModelPart coreSampleBoat;
/*     */   public ModelPart coreSampleBoatDrill;
/*     */   public ModelPart tank;
/*     */   public ModelPart rudder1;
/*     */   public ModelPart rudder2;
/*     */   public ModelPart ruddersBase;
/*  44 */   public ModelPart[] paddles = new ModelPart[2];
/*     */ 
/*     */   
/*     */   public ModelMotorboat() {
/*  48 */     MeshDefinition meshDefinition = new MeshDefinition();
/*  49 */     PartDefinition rootDefinition = meshDefinition.m_171576_();
/*     */     
/*  51 */     rootDefinition.m_171599_("boat_side0", singleCube(0, 0, -14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), PartPose.m_171423_(0.0F, 3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
/*  52 */     rootDefinition.m_171599_("boat_side1", singleCube(0, 19, -13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), PartPose.m_171423_(-15.0F, 4.0F, 4.0F, 0.0F, 4.712389F, 0.0F));
/*  53 */     rootDefinition.m_171599_("boat_side2", singleCube(0, 27, -8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F), PartPose.m_171423_(15.0F, 4.0F, 0.0F, 0.0F, 1.5707964F, 0.0F));
/*  54 */     rootDefinition.m_171599_("boat_side3", singleCube(0, 35, -14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), PartPose.m_171423_(0.0F, 4.0F, -9.0F, 0.0F, 3.1415927F, 0.0F));
/*  55 */     rootDefinition.m_171599_("boat_side4", singleCube(0, 43, -14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), PartPose.m_171419_(0.0F, 4.0F, 9.0F));
/*     */     
/*  57 */     rootDefinition.m_171599_("no_water", singleCube(0, 0, -14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), PartPose.m_171423_(0.0F, -3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  59 */     rootDefinition.m_171599_("motor", singleCube(104, 0, -19.0F, -8.0F, -3.0F, 6.0F, 5.0F, 6.0F), PartPose.f_171404_);
/*     */     
/*  61 */     PartDefinition propAssembly_Definition = rootDefinition.m_171599_("propeller_assembly", singleCube(96, 0, -1.0F, -8.1F, -1.0F, 2.0F, 10.0F, 2.0F), PartPose.m_171419_(-17.0F, 5.0F, 0.0F));
/*     */     
/*  63 */     propAssembly_Definition.m_171599_("handle", singleCube(72, 0, 4.0F, -9.7F, -0.5F, 6.0F, 1.0F, 1.0F), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, toRadians(-5.0F)));
/*  64 */     PartDefinition propeller_Definition = propAssembly_Definition.m_171599_("propeller", singleCube(86, 0, -1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F), PartPose.m_171419_(-3.0F, 0.0F, 0.0F));
/*  65 */     propeller_Definition.m_171599_("propeller1", singleCube(90, 4, 0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, toRadians(15.0F), 0.0F));
/*     */     
/*  67 */     PartDefinition propeller2B_Definition = propeller_Definition.m_171599_("propeller2B", empty(90, 4), PartPose.m_171423_(0.0F, 0.0F, 0.0F, toRadians(120.0F), 0.0F, 0.0F)).m_171599_("propeller2", singleCube(90, 4, 0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, toRadians(15.0F), 0.0F));
/*     */     
/*  69 */     PartDefinition propeller3B_Definition = propeller_Definition.m_171599_("propeller3B", empty(90, 4), PartPose.m_171423_(0.0F, 0.0F, 0.0F, toRadians(240.0F), 0.0F, 0.0F)).m_171599_("propeller3", singleCube(90, 4, 0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, toRadians(15.0F), 0.0F));
/*     */     
/*  71 */     PartDefinition icebreak_Definition = rootDefinition.m_171599_("icebreak", singleCube(34, 56, 16.0F, -2.0F, -2.0F, 7.0F, 4.0F, 4.0F), PartPose.f_171404_);
/*  72 */     PartDefinition icebreak_iS1_Definition = icebreak_Definition.m_171599_("icebreak_iS1", singleCube(56, 52, 0.01F, -7.01F, -0.01F, 16.0F, 10.0F, 2.0F), PartPose.m_171423_(26.0F, 3.0F, 0.0F, 0.0F, toRadians(225.0F), 0.0F));
/*  73 */     PartDefinition icebreak_iS1T_Definition = icebreak_iS1_Definition.m_171599_("icebreak_iS1T", singleCube(100, 45, 4.0F, 0.0F, -2.0F, 12.0F, 5.0F, 2.0F), PartPose.m_171423_(0.0F, -7.0F, 0.0F, toRadians(157.0F), 0.0F, 0.0F));
/*  74 */     PartDefinition icebreak_iS2_Definition = icebreak_Definition.m_171599_("icebreak_iS2", singleCube(56, 52, 0.0F, -7.0F, -2.0F, 16.0F, 10.0F, 2.0F), PartPose.m_171423_(26.0F, 3.0F, 0.0F, 0.0F, toRadians(135.0F), 0.0F));
/*  75 */     PartDefinition icebreak_iS2T_Definition = icebreak_iS2_Definition.m_171599_("icebreak_iS2T", singleCube(100, 45, 4.0F, 0.0F, 0.0F, 12.0F, 5.0F, 2.0F), PartPose.m_171423_(0.0F, -7.0F, 0.0F, toRadians(203.0F), 0.0F, 0.0F));
/*     */     
/*  77 */     PartDefinition tank_Definition = rootDefinition.m_171599_("tank", singleCube(86, 24, -14.0F, -2.0F, -8.0F, 5.0F, 5.0F, 16.0F), PartPose.f_171404_);
/*  78 */     PartDefinition pipe1_Definition = tank_Definition.m_171599_("pipe1", singleCube(112, 38, -13.0F, -3.0F, 4.0F, 1.0F, 1.0F, 1.0F), PartPose.f_171404_);
/*  79 */     PartDefinition pipe2_Definition = tank_Definition.m_171599_("pipe2", singleCube(116, 38, -15.0F, -4.0F, 4.0F, 3.0F, 1.0F, 1.0F), PartPose.f_171404_);
/*  80 */     PartDefinition pipe3_Definition = tank_Definition.m_171599_("pipe3", singleCube(112, 38, -15.0F, -4.0F, 3.0F, 1.0F, 1.0F, 1.0F), PartPose.f_171404_);
/*     */     
/*  82 */     PartDefinition ruddersBase_Definition = rootDefinition.m_171599_("ruddersBase", singleCube(92, 29, -18.0F, -3.0F, -8.0F, 2.0F, 6.0F, 3.0F), PartPose.f_171404_);
/*  83 */     PartDefinition ruddersBase2_Definition = ruddersBase_Definition.m_171599_("ruddersBase2", singleCube(92, 29, -18.0F, -3.0F, 6.0F, 2.0F, 6.0F, 3.0F), PartPose.f_171404_);
/*     */     
/*  85 */     PartDefinition rudder1_Definition = rootDefinition.m_171599_("rudder1", singleCube(112, 23, -4.0F, 0.0F, -0.5F, 4.0F, 6.0F, 1.0F), PartPose.m_171419_(-15.0F, 3.0F, -6.5F));
/*  86 */     PartDefinition rudder2_Definition = rootDefinition.m_171599_("rudder2", singleCube(112, 23, -4.0F, 0.0F, -0.5F, 4.0F, 6.0F, 1.0F), PartPose.m_171419_(-15.0F, 3.0F, 7.5F));
/*     */     
/*  88 */     PartDefinition coreSampleBoat_Definition = rootDefinition.m_171599_("coreSampleBoat", singleCube(10, 0, -10.0F, -1.0F, -13.0F, 4.0F, 2.0F, 2.0F), PartPose.f_171404_);
/*  89 */     PartDefinition core2_Definition = coreSampleBoat_Definition.m_171599_("core2", singleCube(10, 0, -11.0F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.f_171404_);
/*  90 */     PartDefinition core3_Definition = coreSampleBoat_Definition.m_171599_("core3", singleCube(10, 0, -6.0F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.f_171404_);
/*     */     
/*  92 */     PartDefinition coreSampleBoatDrill_Definition = rootDefinition.m_171599_("coreSampleBoatDrill", singleCube(10, 0, -3.0F, -8.0F, -16.0F, 6.0F, 18.0F, 6.0F), PartPose.f_171404_);
/*     */     
/*  94 */     PartDefinition paddle0_Definition = rootDefinition.m_171599_("paddle_left", makePaddle(rootDefinition, true), PartPose.m_171423_(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
/*  95 */     PartDefinition paddle1_Definition = rootDefinition.m_171599_("paddle_right", makePaddle(rootDefinition, false), PartPose.m_171423_(3.0F, -5.0F, -9.0F, 0.0F, 3.1415927F, 0.19634955F));
/*     */     
/*  97 */     LayerDefinition layerDefinition = LayerDefinition.m_171565_(meshDefinition, 128, 64);
/*  98 */     ModelPart root = layerDefinition.m_171564_();
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.boatSides[0] = root.m_171324_("boat_side0");
/* 103 */     this.boatSides[1] = root.m_171324_("boat_side1");
/* 104 */     this.boatSides[2] = root.m_171324_("boat_side2");
/* 105 */     this.boatSides[3] = root.m_171324_("boat_side3");
/* 106 */     this.boatSides[4] = root.m_171324_("boat_side4");
/*     */     
/* 108 */     this.noWater = root.m_171324_("no_water");
/*     */     
/* 110 */     this.motor = root.m_171324_("motor");
/* 111 */     this.propellerAssembly = root.m_171324_("propeller_assembly");
/* 112 */     this.propeller = this.propellerAssembly.m_171324_("propeller");
/*     */     
/* 114 */     this.icebreak = root.m_171324_("icebreak");
/* 115 */     this.tank = root.m_171324_("tank");
/*     */     
/* 117 */     this.ruddersBase = root.m_171324_("ruddersBase");
/* 118 */     this.paddles[0] = root.m_171324_("paddle_left");
/* 119 */     this.paddles[1] = root.m_171324_("paddle_right");
/*     */     
/* 121 */     this.rudder1 = root.m_171324_("rudder1");
/* 122 */     this.rudder2 = root.m_171324_("rudder2");
/*     */     
/* 124 */     this.coreSampleBoat = root.m_171324_("coreSampleBoat");
/* 125 */     this.coreSampleBoatDrill = root.m_171324_("coreSampleBoatDrill");
/*     */ 
/*     */ 
/*     */     
/* 129 */     ImmutableList.Builder<ModelPart> builder = ImmutableList.builder();
/* 130 */     builder.addAll(Arrays.asList(this.boatSides));
/* 131 */     builder.addAll(Arrays.asList(new ModelPart[] { this.motor, this.propellerAssembly }));
/* 132 */     this.list = (List<ModelPart>)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CubeListBuilder makePaddle(PartDefinition rootDefinition, boolean left) {
/* 139 */     CubeListBuilder builder = CubeListBuilder.m_171558_().m_171514_(62, left ? 2 : 22).m_171481_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).m_171481_(left ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
/* 140 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(@Nonnull MotorboatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
/* 145 */     setPaddleRotationAngles((Boat)entityIn, 0, limbSwing, entityIn.isEmergency());
/* 146 */     setPaddleRotationAngles((Boat)entityIn, 1, limbSwing, entityIn.isEmergency());
/*     */   }
/*     */   
/*     */   public void setPaddleRotationAngles(Boat boat, int paddle, float limbSwing, boolean isEmergency) {
/* 150 */     if (isEmergency) {
/* 151 */       float f = boat.m_38315_(paddle, limbSwing);
/* 152 */       ModelPart model = this.paddles[paddle];
/* 153 */       model.f_104203_ = (float)Mth.m_14085_(-1.0471975803375244D, -0.2617993950843811D, ((Mth.m_14031_(-f) + 1.0F) / 2.0F));
/* 154 */       model.f_104204_ = (float)Mth.m_14085_(-0.7853981852531433D, 0.7853981852531433D, ((Mth.m_14031_(-f + 1.0F) + 1.0F) / 2.0F));
/*     */       
/* 156 */       model.m_104227_(3.0F, -5.0F, 9.0F);
/*     */       
/* 158 */       if (paddle == 1) {
/* 159 */         model.m_104227_(3.0F, -5.0F, -9.0F);
/* 160 */         model.f_104204_ = 3.1415927F - model.f_104204_;
/*     */       } 
/*     */     } else {
/* 163 */       ModelPart model = this.paddles[paddle];
/* 164 */       model.f_104203_ = (float)Math.toRadians(-25.0D);
/* 165 */       model.f_104204_ = (float)Math.toRadians(-90.0D);
/*     */       
/* 167 */       model.m_104227_(3.0F, -2.0F, 11.0F);
/*     */       
/* 169 */       if (paddle == 1) {
/* 170 */         model.m_104227_(3.0F, -2.0F, -11.0F);
/* 171 */         model.f_104204_ = 3.1415927F - model.f_104204_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Iterable<ModelPart> m_6195_() {
/* 182 */     return this.list;
/*     */   }
/*     */   
/*     */   public ModelPart noWaterRenderer() {
/* 186 */     return this.noWater;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final CubeListBuilder singleCube(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
/* 191 */     return singleCube(0, 0, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final CubeListBuilder singleCube(int pXTexOffs, int pYTexOffs, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
/* 196 */     return CubeListBuilder.m_171558_().m_171514_(pXTexOffs, pYTexOffs).m_171481_(pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final CubeListBuilder empty(int pXTexOffs, int pYTexOffs) {
/* 204 */     return empty().m_171514_(pXTexOffs, pYTexOffs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final CubeListBuilder empty() {
/* 212 */     return CubeListBuilder.m_171558_();
/*     */   }
/*     */   
/*     */   private float toRadians(float degrees) {
/* 216 */     return (float)Math.toRadians(degrees);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\ModelMotorboat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */