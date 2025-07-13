/*    */ package flaxbeard.immersivepetroleum.client.particle;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.network.FriendlyByteBuf;
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
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<FluidParticleData>
/*    */ {
/*    */   @Nonnull
/*    */   public FluidParticleData fromCommand(@Nonnull ParticleType<FluidParticleData> particleTypeIn, StringReader reader) {
/* 27 */     String name = reader.getString();
/* 28 */     return new FluidParticleData(name);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FluidParticleData fromNetwork(@Nonnull ParticleType<FluidParticleData> particleTypeIn, FriendlyByteBuf buffer) {
/* 34 */     String name = buffer.m_130277_();
/* 35 */     return new FluidParticleData(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FluidParticleData$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */