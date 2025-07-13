/*    */ package flaxbeard.immersivepetroleum.common.cfg;
/*    */ 
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.event.config.ModConfigEvent;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class IPClientConfig
/*    */ {
/*    */   public static final Miscellaneous MISCELLANEOUS;
/*    */   public static final GridColors GRID_COLORS;
/*    */   public static final ForgeConfigSpec ALL;
/*    */   
/*    */   static {
/* 22 */     ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
/* 23 */     GRID_COLORS = new GridColors(builder);
/* 24 */     MISCELLANEOUS = new Miscellaneous(builder);
/* 25 */     ALL = builder.build();
/*    */   }
/*    */   
/*    */   public static class GridColors {
/* 29 */     private static final Logger log = LogManager.getLogger("immersivepetroleum/ClientConfig/GridColors");
/*    */     public final ForgeConfigSpec.ConfigValue<String> pipe_normal_color;
/*    */     public final ForgeConfigSpec.ConfigValue<String> pipe_perforated_color;
/*    */     public final ForgeConfigSpec.ConfigValue<String> pipe_perforated_fixed_color;
/*    */     
/*    */     GridColors(ForgeConfigSpec.Builder builder) {
/* 35 */       builder.push("GridColors");
/*    */       
/* 37 */       this
/*    */         
/* 39 */         .pipe_normal_color = builder.comment("Normal pipe color. (Hex RGB)").define("normal_pipe_color", "A5A5A5", o -> hexValidator(o, "normal_pipe_color"));
/*    */       
/* 41 */       this
/*    */         
/* 43 */         .pipe_perforated_color = builder.comment("Perforated pipe color. (Hex RGB)").define("perforated_pipe_color", "54FF54", o -> hexValidator(o, "perforated_pipe_color"));
/*    */       
/* 45 */       this
/*    */         
/* 47 */         .pipe_perforated_fixed_color = builder.comment("Perforated pipe color. (Hex RGB)").define("fixed_perforated_pipe_color", "FF515A", o -> hexValidator(o, "fixed_perforated_pipe_color"));
/*    */       
/* 49 */       builder.pop();
/*    */     }
/*    */     
/*    */     private boolean hexValidator(Object obj, String cfgPath) {
/* 53 */       if (obj instanceof String) { String str = (String)obj;
/* 54 */         if (str.length() > 6) {
/* 55 */           String strNew = str.substring(str.length() - 6);
/* 56 */           log.warn("{}: \"{}\" was cut down to \"{}\".", cfgPath, str, strNew);
/* 57 */           str = strNew;
/*    */         } 
/* 59 */         if (str.length() == 6) {
/*    */           try {
/* 61 */             Integer.valueOf(str, 16);
/* 62 */             return true;
/* 63 */           } catch (NumberFormatException numberFormatException) {}
/*    */         }
/*    */         
/* 66 */         log.error("{}: \"{}\" is not a valid RGB Hex color.", cfgPath, str); }
/*    */ 
/*    */       
/* 69 */       return false;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Miscellaneous {
/*    */     Miscellaneous(ForgeConfigSpec.Builder builder) {
/* 75 */       builder.push("Miscellaneous");
/*    */       
/* 77 */       builder.pop();
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void onConfigChange(ModConfigEvent ev) {}
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPClientConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */