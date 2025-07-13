/*     */ package flaxbeard.immersivepetroleum.common.cfg;
/*     */ 
/*     */ import com.electronwill.nightconfig.core.Config;
/*     */ import com.google.common.base.Preconditions;
/*     */ import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import net.minecraftforge.common.ForgeConfigSpec;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.event.config.ModConfigEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*     */ public class IPServerConfig
/*     */ {
/*     */   public static final Extraction EXTRACTION;
/*     */   public static final Refining REFINING;
/*     */   public static final Generation GENERATION;
/*     */   public static final Miscellaneous MISCELLANEOUS;
/*     */   public static final ForgeConfigSpec ALL;
/*     */   private static Config rawConfig;
/*     */   
/*     */   static {
/*  29 */     ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
/*     */     
/*  31 */     EXTRACTION = new Extraction(builder);
/*  32 */     REFINING = new Refining(builder);
/*  33 */     GENERATION = new Generation(builder);
/*  34 */     MISCELLANEOUS = new Miscellaneous(builder);
/*     */     
/*  36 */     ALL = builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Config getRawConfig() {
/*  41 */     if (rawConfig == null) {
/*     */       try {
/*  43 */         Field childConfig = ForgeConfigSpec.class.getDeclaredField("childConfig");
/*  44 */         childConfig.setAccessible(true);
/*  45 */         rawConfig = (Config)childConfig.get(ALL);
/*  46 */         Preconditions.checkNotNull(rawConfig);
/*  47 */       } catch (Exception x) {
/*  48 */         throw new RuntimeException(x);
/*     */       } 
/*     */     }
/*  51 */     return rawConfig;
/*     */   }
/*     */   
/*     */   public static class Extraction
/*     */   {
/*     */     public final ForgeConfigSpec.ConfigValue<Integer> pumpjack_consumption;
/*     */     
/*     */     Extraction(ForgeConfigSpec.Builder builder) {
/*  59 */       builder.push("Extraction");
/*     */       
/*  61 */       this
/*     */         
/*  63 */         .pumpjack_consumption = builder.comment(new String[] { "The Flux the Pumpjack requires each tick to pump", "Default: 1024" }).define("pumpjack_consumption", Integer.valueOf(1024));
/*     */       
/*  65 */       this
/*     */         
/*  67 */         .pumpjack_speed = builder.comment(new String[] { "The amount of mB of oil a Pumpjack extracts per tick", "Default: 15" }).define("pumpjack_speed", Integer.valueOf(15));
/*     */       
/*  69 */       this
/*     */         
/*  71 */         .derrick_consumption = builder.comment(new String[] { "The Flux the Derrick requires each tick to operate", "Default: 512" }).define("derrick_consumption", Integer.valueOf(512));
/*     */       
/*  73 */       builder.pop();
/*     */     }
/*     */     public final ForgeConfigSpec.ConfigValue<Integer> pumpjack_speed; public final ForgeConfigSpec.ConfigValue<Integer> derrick_consumption; }
/*     */   
/*     */   public static class Refining { public final ForgeConfigSpec.ConfigValue<Double> distillationTower_energyModifier;
/*     */     public final ForgeConfigSpec.ConfigValue<Double> distillationTower_timeModifier;
/*     */     public final ForgeConfigSpec.ConfigValue<Double> cokerUnit_energyModifier;
/*     */     public final ForgeConfigSpec.ConfigValue<Double> cokerUnit_timeModifier;
/*     */     public final ForgeConfigSpec.ConfigValue<Double> hydrotreater_energyModifier;
/*     */     public final ForgeConfigSpec.ConfigValue<Double> hydrotreater_timeModifier;
/*     */     
/*     */     Refining(ForgeConfigSpec.Builder builder) {
/*  85 */       builder.push("Refining");
/*     */       
/*  87 */       this
/*     */         
/*  89 */         .distillationTower_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every Distillation Tower recipe", "Default: 1.0" }).define("distillationTower_energyModifier", Double.valueOf(1.0D));
/*     */       
/*  91 */       this
/*     */         
/*  93 */         .distillationTower_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every Distillation recipe. Can't be lower than 1", "Default: 1.0" }).define("distillationTower_timeModifier", Double.valueOf(1.0D));
/*     */       
/*  95 */       this
/*     */         
/*  97 */         .cokerUnit_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every Coker Tower recipe", "Default: 1.0" }).define("cokerUnit_energyModifier", Double.valueOf(1.0D));
/*     */       
/*  99 */       this
/*     */         
/* 101 */         .cokerUnit_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every Coker recipe. Can't be lower than 1", "Default: 1.0" }).define("cokerUnit_timeModifier", Double.valueOf(1.0D));
/*     */       
/* 103 */       this
/*     */         
/* 105 */         .hydrotreater_energyModifier = builder.comment(new String[] { "A modifier to apply to the energy costs of every High-Pressure Refinery Unit recipe", "Default: 1.0" }).define("hydrotreater_energyModifier", Double.valueOf(1.0D));
/*     */       
/* 107 */       this
/*     */         
/* 109 */         .hydrotreater_timeModifier = builder.comment(new String[] { "A modifier to apply to the time of every High-Pressure Refinery Unit recipe. Can't be lower than 1", "Default: 1.0" }).define("hydrotreater_timeModifier", Double.valueOf(1.0D));
/*     */       
/* 111 */       builder.pop();
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Generation { public final ForgeConfigSpec.ConfigValue<List<? extends String>> fuels;
/*     */     
/*     */     Generation(ForgeConfigSpec.Builder builder) {
/* 118 */       builder.push("Generation");
/*     */       
/* 120 */       this
/*     */         
/* 122 */         .fuels = builder.comment("List of Portable Generator fuels. Format: fluid_name, mb_used_per_second, flux_produced_per_tick").defineList("generator_fuels", 
/* 123 */           List.of("immersivepetroleum:naphtha, 9, 256", "immersivepetroleum:gasoline, 6, 256", "immersivepetroleum:benzene, 6, 256"), o -> true);
/*     */ 
/*     */ 
/*     */       
/* 127 */       builder.pop();
/*     */     } }
/*     */   
/*     */   public static class Miscellaneous {
/*     */     public final ForgeConfigSpec.ConfigValue<List<? extends String>> boat_fuels;
/*     */     public final ForgeConfigSpec.BooleanValue autounlock_recipes;
/*     */     public final ForgeConfigSpec.BooleanValue asphalt_speed;
/*     */     
/*     */     Miscellaneous(ForgeConfigSpec.Builder builder) {
/* 136 */       builder.push("Miscellaneous");
/*     */       
/* 138 */       this
/*     */         
/* 140 */         .boat_fuels = builder.comment("List of Motorboat fuels. Format: fluid_name, mb_used_per_tick").defineList("boat_fuels", 
/* 141 */           List.of("immersivepetroleum:gasoline, 1", "immersivepetroleum:naphtha, 2", "immersivepetroleum:benzene, 2"), o -> true);
/*     */ 
/*     */ 
/*     */       
/* 145 */       this
/*     */         
/* 147 */         .autounlock_recipes = builder.comment(new String[] { "Automatically unlock IP recipes for new players", "Default: true" }).define("autounlock_recipes", true);
/*     */       
/* 149 */       this
/*     */         
/* 151 */         .asphalt_speed = builder.comment(new String[] { "Set to false to disable the asphalt block boosting player speed", "Default: true" }).define("asphalt_speed", true);
/*     */       
/* 153 */       builder.pop();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onConfigReload(ModConfigEvent ev) {
/* 159 */     FuelHandler.onConfigReload(ev);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\IPServerConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */