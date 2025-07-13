/*     */ package flaxbeard.immersivepetroleum.client.particle;
/*     */ 
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleProvider;
/*     */ import net.minecraft.client.particle.SimpleAnimatedParticle;
/*     */ import net.minecraft.client.particle.SpriteSet;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.LogicalSide;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class FlareFire extends SimpleAnimatedParticle {
/*     */   final double ogMotionY;
/*     */   final float red;
/*     */   final float green;
/*     */   final float blue;
/*     */   final float rotation;
/*     */   
/*     */   protected FlareFire(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet spriteWithAge) {
/*  31 */     super(world, x, y, z, spriteWithAge, 0.0F);
/*  32 */     m_107250_(0.5F, 0.5F);
/*  33 */     m_108339_(spriteWithAge);
/*  34 */     this.f_172258_ = 1.0F;
/*  35 */     m_107253_(1.0F, 1.0F, 1.0F);
/*  36 */     m_107257_(60);
/*     */     
/*  38 */     this.f_107663_ = 0.5F;
/*     */     
/*  40 */     this.red = this.green = this.blue = 1.0F;
/*     */     
/*  42 */     this.ogMotionY = motionY;
/*     */     
/*  44 */     this.rotation = 0.25F * (world.f_46441_.m_188501_() - 0.5F);
/*     */     
/*  46 */     this.f_107204_ = 360.0F * world.f_46441_.m_188501_();
/*  47 */     this.f_107231_ = this.f_107204_ + this.rotation * world.f_46441_.m_188501_();
/*     */ 
/*     */     
/*  50 */     this.f_107215_ = this.f_107216_ = this.f_107217_ = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_5989_() {
/*  55 */     float f = this.f_107224_ / this.f_107225_;
/*  56 */     Vector3f vec = Wind.getDirection();
/*     */     
/*  58 */     if (this.f_107224_++ >= this.f_107225_) {
/*  59 */       m_107274_();
/*     */     }
/*  61 */     if (this.f_107224_ == this.f_107225_ - 36) {
/*  62 */       this.f_107227_ = this.f_107228_ = this.f_107229_ = (float)(0.4000000059604645D * Math.random());
/*     */     }
/*  64 */     m_108339_(this.f_107644_);
/*     */     
/*  66 */     this.f_107209_ = this.f_107212_;
/*  67 */     this.f_107210_ = this.f_107213_;
/*  68 */     this.f_107211_ = this.f_107214_;
/*  69 */     this.f_107204_ = this.f_107231_;
/*     */     
/*  71 */     m_6257_((vec.m_122239_() * f), this.ogMotionY * (1.0F - f), (vec.m_122269_() * f));
/*  72 */     this.f_107231_ += this.rotation;
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public static class Factory implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet spriteSet;
/*     */     
/*     */     public Factory(SpriteSet spriteSet) {
/*  80 */       this.spriteSet = spriteSet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(@Nonnull SimpleParticleType typeIn, @Nonnull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
/*  85 */       return (Particle)new FlareFire(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*     */   public static class Wind
/*     */   {
/*  96 */     private static Vector3f vec = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */     private static long lastGT;
/*     */     private static float lastDirection;
/*     */     private static float thisDirection;
/*     */     
/*     */     public static Vector3f getDirection() {
/* 102 */       return vec;
/*     */     }
/*     */     
/*     */     @SubscribeEvent
/*     */     public static void clientTick(TickEvent.ClientTickEvent event) {
/* 107 */       if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.START) {
/* 108 */         ClientLevel world = MCUtil.getLevel();
/* 109 */         if (world == null) {
/*     */           return;
/*     */         }
/* 112 */         long gameTime = world.m_46467_();
/* 113 */         if (gameTime / 20L != lastGT) {
/* 114 */           lastGT = gameTime / 20L;
/*     */           
/* 116 */           double fGameTime = gameTime / 20.0D;
/* 117 */           Random lastRand = new Random(Mth.m_14107_(fGameTime));
/* 118 */           Random thisRand = new Random(Mth.m_14165_(fGameTime));
/*     */           
/* 120 */           lastDirection = lastRand.nextFloat() * 360.0F;
/* 121 */           thisDirection = thisRand.nextFloat() * 360.0F;
/*     */         } 
/*     */         
/* 124 */         double interpDirection = Mth.m_14179_((float)(gameTime % 20L) / 20.0F, lastDirection, thisDirection);
/*     */         
/* 126 */         float xSpeed = (float)Math.sin(interpDirection) * 0.1F;
/* 127 */         float zSpeed = (float)Math.cos(interpDirection) * 0.1F;
/*     */         
/* 129 */         vec = new Vector3f(xSpeed, 0.0F, zSpeed);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FlareFire.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */