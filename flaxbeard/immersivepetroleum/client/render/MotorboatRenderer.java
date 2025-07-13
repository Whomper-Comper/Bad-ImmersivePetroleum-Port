/*     */ package flaxbeard.immersivepetroleum.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Quaternion;
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.client.model.ModelMotorboat;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.entity.EntityRenderer;
/*     */ import net.minecraft.client.renderer.entity.EntityRendererProvider;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class MotorboatRenderer
/*     */   extends EntityRenderer<MotorboatEntity> {
/*  25 */   private static final ResourceLocation texture = ResourceUtils.ip("textures/models/boat_motor.png");
/*  26 */   private static final ResourceLocation textureArmor = ResourceUtils.ip("textures/models/boat_motor_armor.png");
/*     */ 
/*     */   
/*  29 */   protected final ModelMotorboat modelBoat = new ModelMotorboat();
/*     */   
/*     */   public MotorboatRenderer(EntityRendererProvider.Context renderManagerIn) {
/*  32 */     super(renderManagerIn);
/*  33 */     this.f_114477_ = 0.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(@Nonnull MotorboatEntity entity, float entityYaw, float partialTicks, PoseStack matrix, @Nonnull MultiBufferSource bufferIn, int packedLight) {
/*  38 */     matrix.m_85836_();
/*     */     
/*  40 */     matrix.m_85837_(0.0D, 0.375D, 0.0D);
/*  41 */     setupRotation(entity, entityYaw, partialTicks, matrix);
/*  42 */     this.modelBoat.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
/*     */     
/*  44 */     if (entity.m_20077_()) {
/*  45 */       matrix.m_85837_(0.0D, -0.24375000596046448D, 0.0D);
/*     */     }
/*     */ 
/*     */     
/*  49 */     if (!entity.isEmergency()) {
/*  50 */       if (entity.isForwardDown()) {
/*  51 */         entity.propellerXRotSpeed += entity.isBoosting ? 0.2F : 0.1F;
/*     */       }
/*  53 */       if (entity.isBackDown()) {
/*  54 */         entity.propellerXRotSpeed -= 0.2F;
/*     */       }
/*     */       
/*  57 */       entity.propellerXRot += entity.propellerXRotSpeed;
/*  58 */       entity.propellerXRot %= 360.0F;
/*     */     } 
/*     */     
/*  61 */     entity.propellerXRotSpeed *= 0.985F;
/*  62 */     if (entity.propellerXRotSpeed != 0.0F && entity.propellerXRotSpeed >= -0.001F && entity.propellerXRotSpeed <= 0.001F) {
/*  63 */       entity.propellerXRotSpeed = 0.0F;
/*     */     }
/*     */     
/*  66 */     this.modelBoat.propeller.f_104203_ = entity.propellerXRot * 0.017453292F;
/*     */     
/*  68 */     float pr = entity.isEmergency() ? 0.0F : entity.propellerYRotation;
/*  69 */     if (entity.isLeftInDown() && !entity.isRightInDown() && pr > -1.0F) {
/*  70 */       pr -= 0.1F * partialTicks;
/*     */     }
/*  72 */     if (entity.isRightInDown() && !entity.isLeftInDown() && pr < 1.0F) {
/*  73 */       pr += 0.1F * partialTicks;
/*     */     }
/*  75 */     if (!entity.isLeftInDown() && !entity.isRightInDown()) {
/*  76 */       pr = (float)(pr * Math.pow(0.7D, partialTicks));
/*     */     }
/*  78 */     this.modelBoat.propellerAssembly.f_104204_ = (float)Math.toRadians((pr * 15.0F));
/*     */ 
/*     */     
/*  81 */     this.modelBoat.m_7695_(matrix, bufferIn.m_6299_(this.modelBoat.m_103119_(getEntityTexture(entity.isFireproof))), packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  83 */     if (entity.hasPaddles) {
/*  84 */       VertexConsumer vbuilder_normal = bufferIn.m_6299_(this.modelBoat.m_103119_(texture));
/*     */       
/*  86 */       this.modelBoat.paddles[0].m_104301_(matrix, vbuilder_normal, packedLight, OverlayTexture.f_118083_);
/*  87 */       this.modelBoat.paddles[1].m_104301_(matrix, vbuilder_normal, packedLight, OverlayTexture.f_118083_);
/*     */     } 
/*     */     
/*  90 */     VertexConsumer vbuilder_armored = bufferIn.m_6299_(this.modelBoat.m_103119_(textureArmor));
/*     */     
/*  92 */     if (entity.hasIcebreaker) {
/*  93 */       this.modelBoat.icebreak.m_104301_(matrix, vbuilder_armored, packedLight, OverlayTexture.f_118083_);
/*     */     }
/*     */     
/*  96 */     if (entity.hasRudders) {
/*  97 */       this.modelBoat.ruddersBase.m_104301_(matrix, vbuilder_armored, packedLight, OverlayTexture.f_118083_);
/*     */       
/*  99 */       float f = entity.propellerYRotation;
/* 100 */       if (entity.isLeftInDown() && !entity.isRightInDown() && f > -1.0F) {
/* 101 */         f -= 0.1F * partialTicks;
/*     */       }
/*     */       
/* 104 */       if (entity.isRightInDown() && !entity.isLeftInDown() && f < 1.0F) {
/* 105 */         f += 0.1F * partialTicks;
/*     */       }
/*     */       
/* 108 */       if (!entity.isLeftInDown() && !entity.isRightInDown()) {
/* 109 */         f = (float)(f * Math.pow(0.699999988079071D, partialTicks));
/*     */       }
/*     */       
/* 112 */       this.modelBoat.rudder1.f_104204_ = (float)Math.toRadians((f * 20.0F));
/* 113 */       this.modelBoat.rudder2.f_104204_ = (float)Math.toRadians((f * 20.0F));
/*     */       
/* 115 */       this.modelBoat.rudder1.m_104301_(matrix, vbuilder_armored, packedLight, OverlayTexture.f_118083_);
/* 116 */       this.modelBoat.rudder2.m_104301_(matrix, vbuilder_armored, packedLight, OverlayTexture.f_118083_);
/*     */     } 
/*     */     
/* 119 */     if (entity.hasTank) {
/* 120 */       this.modelBoat.tank.m_104301_(matrix, vbuilder_armored, packedLight, OverlayTexture.f_118083_);
/*     */     }
/*     */     
/* 123 */     if (!entity.m_5842_()) {
/* 124 */       VertexConsumer vbuilder_mask = bufferIn.m_6299_(RenderType.m_110478_());
/* 125 */       this.modelBoat.noWaterRenderer().m_104301_(matrix, vbuilder_mask, packedLight, OverlayTexture.f_118083_);
/*     */     } 
/*     */     
/* 128 */     matrix.m_85849_();
/*     */     
/* 130 */     super.m_7392_((Entity)entity, entityYaw, partialTicks, matrix, bufferIn, packedLight);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResourceLocation getTextureLocation(@Nonnull MotorboatEntity entity) {
/* 136 */     return texture;
/*     */   }
/*     */   
/*     */   public ResourceLocation getEntityTexture(boolean armored) {
/* 140 */     return armored ? textureArmor : texture;
/*     */   }
/*     */   
/*     */   public void setupRotation(MotorboatEntity boat, float entityYaw, float partialTicks, PoseStack matrix) {
/* 144 */     matrix.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - entityYaw));
/* 145 */     float f = boat.m_38385_() - partialTicks;
/* 146 */     float f1 = boat.m_38384_() - partialTicks;
/*     */     
/* 148 */     if (f1 < 0.0F) {
/* 149 */       f1 = 0.0F;
/*     */     }
/*     */     
/* 152 */     if (f > 0.0F) {
/* 153 */       matrix.m_85845_(new Quaternion(Mth.m_14031_(f) * f * f1 / 10.0F * boat.m_38386_(), 0.0F, 0.0F, true));
/*     */     }
/*     */     
/* 156 */     if (boat.isBoosting) {
/* 157 */       matrix.m_85845_(new Quaternion(3.0F, 0.0F, 0.0F, true));
/*     */     }
/*     */     
/* 160 */     matrix.m_85841_(-1.0F, -1.0F, 1.0F);
/* 161 */     matrix.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\MotorboatRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */