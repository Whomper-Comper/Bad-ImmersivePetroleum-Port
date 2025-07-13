/*    */ package flaxbeard.immersivepetroleum.common.cfg;
/*    */ 
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
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
/*    */ public class Extraction
/*    */ {
/*    */   public final ForgeConfigSpec.ConfigValue<Integer> pumpjack_consumption;
/*    */   public final ForgeConfigSpec.ConfigValue<Integer> pumpjack_speed;
/*    */   public final ForgeConfigSpec.ConfigValue<Integer> derrick_consumption;
/*    */   
/*    */   Extraction(ForgeConfigSpec.Builder builder) {
/* 59 */     builder.push("Extraction");
/*    */     
/* 61 */     this
/*    */       
/* 63 */       .pumpjack_consumption = builder.comment(new String[] { "The Flux the Pumpjack requires each tick to pump", "Default: 1024" }).define("pumpjack_consumption", Integer.valueOf(1024));
/*    */     
/* 65 */     this
/*    */       
/* 67 */       .pumpjack_speed = builder.comment(new String[] { "The amount of mB of oil a Pumpjack extracts per tick", "Default: 15" }).define("pumpjack_speed", Integer.valueOf(15));
/*    */     
/* 69 */     this
/*    */       
/* 71 */       .derrick_consumption = builder.comment(new String[] { "The Flux the Derrick requires each tick to operate", "Default: 512" }).define("derrick_consumption", Integer.valueOf(512));
/*    */     
/* 73 */     builder.pop();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPServerConfig$Extraction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */