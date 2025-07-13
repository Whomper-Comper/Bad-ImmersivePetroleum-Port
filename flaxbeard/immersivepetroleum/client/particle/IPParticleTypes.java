/*    */ package flaxbeard.immersivepetroleum.client.particle;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ 
/*    */ public class IPParticleTypes
/*    */ {
/* 14 */   public static final RegistryObject<SimpleParticleType> FLARE_FIRE = createBasicParticle("flare_fire", false);
/* 15 */   public static final RegistryObject<ParticleType<FluidParticleData>> FLUID_SPILL = createParticleWithData("fluid_spill", FluidParticleData.DESERIALIZER, FluidParticleData.CODEC);
/*    */ 
/*    */   
/*    */   public static void forceClassLoad() {}
/*    */   
/*    */   private static RegistryObject<SimpleParticleType> createBasicParticle(String name, boolean alwaysShow) {
/* 21 */     return IPRegisters.registerParticleType(name, () -> new SimpleParticleType(alwaysShow));
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> createParticleWithData(String name, ParticleOptions.Deserializer<T> deserializer, final Codec<T> codec) {
/* 26 */     ParticleType<T> type = new ParticleType<T>(false, deserializer)
/*    */       {
/*    */         @Nonnull
/*    */         public Codec<T> m_7652_() {
/* 30 */           return codec;
/*    */         }
/*    */       };
/*    */     
/* 34 */     return IPRegisters.registerParticleType(name, () -> type);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\IPParticleTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */