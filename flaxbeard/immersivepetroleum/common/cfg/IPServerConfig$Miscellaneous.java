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
/*     */ public class Miscellaneous
/*     */ {
/*     */   public final ForgeConfigSpec.ConfigValue<List<? extends String>> boat_fuels;
/*     */   public final ForgeConfigSpec.BooleanValue autounlock_recipes;
/*     */   public final ForgeConfigSpec.BooleanValue asphalt_speed;
/*     */   
/*     */   Miscellaneous(ForgeConfigSpec.Builder builder) {
/* 136 */     builder.push("Miscellaneous");
/*     */     
/* 138 */     this
/*     */       
/* 140 */       .boat_fuels = builder.comment("List of Motorboat fuels. Format: fluid_name, mb_used_per_tick").defineList("boat_fuels", 
/* 141 */         List.of("immersivepetroleum:gasoline, 1", "immersivepetroleum:naphtha, 2", "immersivepetroleum:benzene, 2"), o -> true);
/*     */ 
/*     */ 
/*     */     
/* 145 */     this
/*     */       
/* 147 */       .autounlock_recipes = builder.comment(new String[] { "Automatically unlock IP recipes for new players", "Default: true" }).define("autounlock_recipes", true);
/*     */     
/* 149 */     this
/*     */       
/* 151 */       .asphalt_speed = builder.comment(new String[] { "Set to false to disable the asphalt block boosting player speed", "Default: true" }).define("asphalt_speed", true);
/*     */     
/* 153 */     builder.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPServerConfig$Miscellaneous.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */