/*    */ package flaxbeard.immersivepetroleum.client.particle;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ 
/*    */ public class FluidParticleData implements ParticleOptions {
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.STRING.fieldOf("fluid").forGetter(())).apply((Applicative)instance, FluidParticleData::new));
/*    */   }
/*    */   public static final Codec<FluidParticleData> CODEC;
/* 23 */   public static final ParticleOptions.Deserializer<FluidParticleData> DESERIALIZER = new ParticleOptions.Deserializer<FluidParticleData>()
/*    */     {
/*    */       @Nonnull
/*    */       public FluidParticleData fromCommand(@Nonnull ParticleType<FluidParticleData> particleTypeIn, StringReader reader) {
/* 27 */         String name = reader.getString();
/* 28 */         return new FluidParticleData(name);
/*    */       }
/*    */ 
/*    */       
/*    */       @Nonnull
/*    */       public FluidParticleData fromNetwork(@Nonnull ParticleType<FluidParticleData> particleTypeIn, FriendlyByteBuf buffer) {
/* 34 */         String name = buffer.m_130277_();
/* 35 */         return new FluidParticleData(name);
/*    */       }
/*    */     };
/*    */   private final Fluid fluid;
/*    */   
/*    */   public FluidParticleData(String name) {
/* 41 */     this((Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(name)));
/*    */   }
/*    */   
/*    */   public FluidParticleData(Fluid fluid) {
/* 45 */     this.fluid = fluid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ParticleType<FluidParticleData> m_6012_() {
/* 51 */     return (ParticleType<FluidParticleData>)IPParticleTypes.FLUID_SPILL.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_7711_(FriendlyByteBuf buffer) {
/* 56 */     buffer.m_130070_(RegistryUtils.getRegistryNameOf(this.fluid).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String m_5942_() {
/* 62 */     return RegistryUtils.getRegistryNameOf(this.fluid).toString();
/*    */   }
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public Fluid getFluid() {
/* 67 */     return this.fluid;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FluidParticleData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */