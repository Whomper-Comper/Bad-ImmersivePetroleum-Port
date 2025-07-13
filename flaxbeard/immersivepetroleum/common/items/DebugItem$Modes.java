/*    */ package flaxbeard.immersivepetroleum.common.items;
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
/*    */ public enum Modes
/*    */ {
/* 44 */   DISABLED("Disabled"),
/* 45 */   INFO_SPEEDBOAT("Info: Speedboat"),
/*    */   
/* 47 */   SEEDBASED_RESERVOIR("Seed-Based Reservoir: Heatmap"),
/* 48 */   SEEDBASED_RESERVOIR_AREA_TEST("Seed-Based Reservoir: Island Testing"),
/*    */   
/* 50 */   REFRESH_ALL_IPMODELS("Refresh all IPModels"),
/* 51 */   UPDATE_SHAPES("Does nothing without Debugging Enviroment"),
/* 52 */   GENERAL_TEST("This one could be dangerous to trigger!");
/*    */   
/*    */   public final String display;
/*    */   
/*    */   Modes(String display) {
/* 57 */     this.display = display;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\DebugItem$Modes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */