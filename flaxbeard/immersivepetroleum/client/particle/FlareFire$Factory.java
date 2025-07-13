/*    */ package flaxbeard.immersivepetroleum.client.particle;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.particle.ParticleProvider;
/*    */ import net.minecraft.client.particle.SpriteSet;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class Factory
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/*    */   private final SpriteSet spriteSet;
/*    */   
/*    */   public Factory(SpriteSet spriteSet) {
/* 80 */     this.spriteSet = spriteSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(@Nonnull SimpleParticleType typeIn, @Nonnull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
/* 85 */     return (Particle)new FlareFire(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FlareFire$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */