/*    */ package flaxbeard.immersivepetroleum.api.energy;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.cfg.ConfigUtils;
/*    */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*    */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fml.event.config.ModConfigEvent;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class FuelHandler
/*    */ {
/* 18 */   protected static final Logger log = LogManager.getLogger("immersivepetroleum/FuelHandler");
/*    */   
/* 20 */   static final Map<ResourceLocation, Values> portablegen = new HashMap<>();
/*    */   
/* 22 */   static final Map<ResourceLocation, Integer> motorboatAmountTick = new HashMap<>();
/*    */   
/*    */   public static void registerPortableGeneratorFuel(Fluid fuel, int fluxPerTick, int mbPerTick) {
/* 25 */     if (fuel != null) {
/* 26 */       registerPortableGeneratorFuel(RegistryUtils.getRegistryNameOf(fuel), mbPerTick, fluxPerTick);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void registerMotorboatFuel(Fluid fuel, int mbPerTick) {
/* 31 */     if (fuel != null) {
/* 32 */       registerMotorboatFuel(RegistryUtils.getRegistryNameOf(fuel), mbPerTick);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void registerPortableGeneratorFuel(ResourceLocation fuelRL, int fluxPerTick, int mbPerTick) {
/* 37 */     if (fuelRL != null && !fuelRL.toString().isEmpty()) {
/* 38 */       portablegen.put(fuelRL, new Values(fluxPerTick, mbPerTick));
/*    */       
/* 40 */       log.info("Added {} as Portable Generator Fuel. ({}RF/t {}mB/t)", fuelRL, Integer.valueOf(fluxPerTick), Integer.valueOf(mbPerTick));
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void registerMotorboatFuel(ResourceLocation fuelRL, int mbPerTick) {
/* 45 */     if (fuelRL != null && !fuelRL.toString().isEmpty()) {
/* 46 */       motorboatAmountTick.put(fuelRL, Integer.valueOf(mbPerTick));
/*    */       
/* 48 */       log.info("Added {} as Motorboat Fuel. ({} mB/t)", fuelRL, Integer.valueOf(mbPerTick));
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isValidBoatFuel(Fluid fuel) {
/* 53 */     return (fuel != null && motorboatAmountTick.containsKey(RegistryUtils.getRegistryNameOf(fuel)));
/*    */   }
/*    */   
/*    */   public static boolean isValidFuel(Fluid fuel) {
/* 57 */     return (fuel != null && portablegen.containsKey(RegistryUtils.getRegistryNameOf(fuel)));
/*    */   }
/*    */   
/*    */   public static int getBoatFuelUse(Fluid fuel) {
/* 61 */     if (!isValidBoatFuel(fuel)) {
/* 62 */       return 0;
/*    */     }
/* 64 */     return ((Integer)motorboatAmountTick.get(RegistryUtils.getRegistryNameOf(fuel))).intValue();
/*    */   }
/*    */   
/*    */   public static int getGeneratorFuelUse(Fluid fuel) {
/* 68 */     if (!isValidFuel(fuel)) {
/* 69 */       return 0;
/*    */     }
/* 71 */     return ((Values)portablegen.get(RegistryUtils.getRegistryNameOf(fuel))).mBPerConsume;
/*    */   }
/*    */   
/*    */   public static int getFluxGeneratedPerTick(Fluid fuel) {
/* 75 */     if (!isValidFuel(fuel)) {
/* 76 */       return 0;
/*    */     }
/* 78 */     return ((Values)portablegen.get(RegistryUtils.getRegistryNameOf(fuel))).fluxPerTick;
/*    */   }
/*    */   
/*    */   public static void onConfigReload(ModConfigEvent ev) {
/* 82 */     if (ev.getConfig().getSpec() != IPServerConfig.ALL) {
/*    */       return;
/*    */     }
/*    */     
/* 86 */     portablegen.clear();
/* 87 */     motorboatAmountTick.clear();
/*    */     
/* 89 */     ConfigUtils.addGeneratorFuel((List)IPServerConfig.GENERATION.fuels.get());
/* 90 */     ConfigUtils.addBoatFuel((List)IPServerConfig.MISCELLANEOUS.boat_fuels.get());
/*    */   }
/*    */   private static final class Values extends Record { private final int fluxPerTick; private final int mBPerConsume;
/* 93 */     private Values(int fluxPerTick, int mBPerConsume) { this.fluxPerTick = fluxPerTick; this.mBPerConsume = mBPerConsume; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #93	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 93 */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values; } public int fluxPerTick() { return this.fluxPerTick; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #93	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #93	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lflaxbeard/immersivepetroleum/api/energy/FuelHandler$Values;
/* 93 */       //   0	8	1	o	Ljava/lang/Object; } public int mBPerConsume() { return this.mBPerConsume; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\energy\FuelHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */