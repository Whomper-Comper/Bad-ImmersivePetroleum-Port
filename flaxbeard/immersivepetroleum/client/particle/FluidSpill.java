/*    */ package flaxbeard.immersivepetroleum.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.particle.ParticleProvider;
/*    */ import net.minecraft.client.particle.ParticleRenderType;
/*    */ import net.minecraft.client.particle.TextureSheetParticle;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.inventory.InventoryMenu;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class FluidSpill
/*    */   extends TextureSheetParticle {
/*    */   final double ogMotionX;
/*    */   
/*    */   protected FluidSpill(Fluid fluid, ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
/* 28 */     super(world, x, y, z, motionX, motionY, motionZ);
/* 29 */     m_107250_(0.5F, 0.5F);
/* 30 */     m_107257_(50);
/* 31 */     this.f_107663_ = 0.25F;
/*    */     
/* 33 */     this.f_107219_ = false;
/*    */     
/* 35 */     this.f_107216_ = motionY;
/*    */     
/* 37 */     this.ogMotionX = motionX;
/* 38 */     this.ogMotionY = motionY;
/* 39 */     this.ogMotionZ = motionZ;
/*    */     
/* 41 */     FluidStack fs = new FluidStack(fluid, 1000);
/*    */     
/* 43 */     IClientFluidTypeExtensions fluidProperties = IClientFluidTypeExtensions.of(fluid);
/*    */     
/* 45 */     ResourceLocation location = fluidProperties.getStillTexture(fs);
/* 46 */     TextureAtlasSprite sprite = Minecraft.m_91087_().m_91304_().m_119428_(InventoryMenu.f_39692_).m_118316_(location);
/* 47 */     m_108337_(sprite);
/*    */     
/* 49 */     int argb = fluidProperties.getTintColor(fs);
/* 50 */     this.f_107230_ = (argb >> 24 & 0xFF) / 255.0F;
/* 51 */     this.f_107227_ = (argb >> 16 & 0xFF) / 255.0F;
/* 52 */     this.f_107228_ = (argb >> 8 & 0xFF) / 255.0F;
/* 53 */     this.f_107229_ = (argb & 0xFF) / 255.0F;
/*    */   }
/*    */   final double ogMotionY; final double ogMotionZ;
/*    */   
/*    */   public void m_5989_() {
/* 58 */     this.f_107209_ = this.f_107212_;
/* 59 */     this.f_107210_ = this.f_107213_;
/* 60 */     this.f_107211_ = this.f_107214_;
/* 61 */     if (this.f_107224_++ >= this.f_107225_) {
/* 62 */       m_107274_();
/*    */     } else {
/* 64 */       this.f_107216_ -= 0.04D;
/* 65 */       m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
/* 66 */       this.f_107215_ *= 0.98D;
/* 67 */       this.f_107216_ *= 0.98D;
/* 68 */       this.f_107217_ *= 0.98D;
/* 69 */       if (this.f_107218_) {
/* 70 */         this.f_107215_ *= 0.7D;
/* 71 */         this.f_107217_ *= 0.7D;
/*    */       } 
/*    */       
/* 74 */       this.f_107219_ = (this.f_107216_ <= 0.0D);
/*    */       
/* 76 */       this.f_107663_ = (float)(this.f_107663_ * 0.97D);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_5744_(@Nonnull VertexConsumer buffer, @Nonnull Camera renderInfo, float partialTicks) {
/* 82 */     super.m_5744_(buffer, renderInfo, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ParticleRenderType m_7556_() {
/* 88 */     return ParticleRenderType.f_107429_;
/*    */   }
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public static class Factory
/*    */     implements ParticleProvider<FluidParticleData> {
/*    */     public Particle createParticle(FluidParticleData type, @Nonnull ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
/* 95 */       return (Particle)new FluidSpill(type.getFluid(), world, x, y, z, xSpeed, ySpeed, zSpeed);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FluidSpill.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */