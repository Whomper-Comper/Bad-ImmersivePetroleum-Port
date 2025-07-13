/*     */ package flaxbeard.immersivepetroleum.common.cfg;
/*     */ 
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
/*     */ public class Refining
/*     */ {
/*     */   public final ForgeConfigSpec.ConfigValue<Double> distillationTower_energyModifier;
/*     */   public final ForgeConfigSpec.ConfigValue<Double> distillationTower_timeModifier;
/*     */   public final ForgeConfigSpec.ConfigValue<Double> cokerUnit_energyModifier;
/*     */   public final ForgeConfigSpec.ConfigValue<Double> cokerUnit_timeModifier;
/*     */   public final ForgeConfigSpec.ConfigValue<Double> hydrotreater_energyModifier;
/*     */   public final ForgeConfigSpec.ConfigValue<Double> hydrotreater_timeModifier;
/*     */   
/*     */   Refining(ForgeConfigSpec.Builder builder) {
/*  85 */     builder.push("Refining");
/*     */     
/*  87 */     this
/*     */       
/*  89 */       .distillationTower_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every Distillation Tower recipe", "Default: 1.0" }).define("distillationTower_energyModifier", Double.valueOf(1.0D));
/*     */     
/*  91 */     this
/*     */       
/*  93 */       .distillationTower_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every Distillation recipe. Can't be lower than 1", "Default: 1.0" }).define("distillationTower_timeModifier", Double.valueOf(1.0D));
/*     */     
/*  95 */     this
/*     */       
/*  97 */       .cokerUnit_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every Coker Tower recipe", "Default: 1.0" }).define("cokerUnit_energyModifier", Double.valueOf(1.0D));
/*     */     
/*  99 */     this
/*     */       
/* 101 */       .cokerUnit_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every Coker recipe. Can't be lower than 1", "Default: 1.0" }).define("cokerUnit_timeModifier", Double.valueOf(1.0D));
/*     */     
/* 103 */     this
/*     */       
/* 105 */       .hydrotreater_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every High-Pressure Refinery Unit recipe", "Default: 1.0" }).define("hydrotreater_energyModifier", Double.valueOf(1.0D));
/*     */     
/* 107 */     this
/*     */       
/* 109 */       .hydrotreater_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every High-Pressure Refinery Unit recipe. Can't be lower than 1", "Default: 1.0" }).define("hydrotreater_timeModifier", Double.valueOf(1.0D));
/*     */     
/* 111 */     builder.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPServerConfig$Refining.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */