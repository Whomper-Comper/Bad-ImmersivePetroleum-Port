/*     */ package flaxbeard.immersivepetroleum.common.cfg;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraftforge.common.ForgeConfigSpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Generation
/*     */ {
/*     */   public final ForgeConfigSpec.ConfigValue<List<? extends String>> fuels;
/*     */   
/*     */   Generation(ForgeConfigSpec.Builder builder) {
/* 118 */     builder.push("Generation");
/*     */     
/* 120 */     this
/*     */       
/* 122 */       .fuels = builder.comment("List of Portable Generator fuels. Format: fluid_name, mb_used_per_second, flux_produced_per_tick").defineList("generator_fuels", 
/* 123 */         List.of("immersivepetroleum:naphtha, 9, 256", "immersivepetroleum:gasoline, 6, 256", "immersivepetroleum:benzene, 6, 256"), o -> true);
/*     */ 
/*     */ 
/*     */     
/* 127 */     builder.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPServerConfig$Generation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */