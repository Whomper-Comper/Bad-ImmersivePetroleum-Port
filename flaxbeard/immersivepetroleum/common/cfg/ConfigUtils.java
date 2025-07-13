/*    */ package flaxbeard.immersivepetroleum.common.cfg;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
/*    */ import java.util.List;
/*    */ import net.minecraft.ResourceLocationException;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ 
/*    */ 
/*    */ public class ConfigUtils
/*    */ {
/*    */   public static void addGeneratorFuel(List<? extends String> list) {
/* 13 */     for (int i = 0; i < list.size(); i++) {
/* 14 */       String str = list.get(i);
/*    */       
/* 16 */       if (!str.isEmpty()) {
/*    */         
/* 18 */         String[] split = str.split(", {0,}");
/*    */         
/* 20 */         if (split.length < 3) {
/* 21 */           throw new IllegalArgumentException("Missing values in \"" + str + "\".");
/*    */         }
/*    */         
/* 24 */         ResourceLocation fluidRL = null;
/* 25 */         int mbPerTick = 0;
/* 26 */         int fluxPerTick = 0;
/*    */         
/*    */         try {
/* 29 */           fluidRL = new ResourceLocation(split[0].trim());
/* 30 */         } catch (ResourceLocationException e) {
/* 31 */           throw new IllegalArgumentException(e);
/*    */         } 
/*    */         
/*    */         try {
/* 35 */           mbPerTick = Integer.valueOf(split[1].trim()).intValue();
/* 36 */           if (mbPerTick < 0) {
/* 37 */             throw new IllegalArgumentException("Negative value for fuel mB/tick for generator fuel " + i + 1);
/*    */           }
/* 39 */         } catch (NumberFormatException e) {
/* 40 */           throw new IllegalArgumentException("Invalid value for fuel mB/tick for generator fuel " + i + 1, e);
/*    */         } 
/*    */         
/*    */         try {
/* 44 */           fluxPerTick = Integer.valueOf(split[2].trim()).intValue();
/* 45 */           if (fluxPerTick < 0) {
/* 46 */             throw new IllegalArgumentException("Negative value for fuel RF/tick for generator fuel " + i + 1);
/*    */           }
/* 48 */         } catch (NumberFormatException e) {
/* 49 */           throw new IllegalArgumentException("Invalid value for fuel RF/tick for generator fuel " + i + 1, e);
/*    */         } 
/*    */         
/* 52 */         if (!ForgeRegistries.FLUIDS.containsKey(fluidRL)) {
/* 53 */           throw new RuntimeException("\"" + fluidRL + "\" did not resolve into a valid fluid. (" + fluidRL + ")");
/*    */         }
/*    */         
/* 56 */         FuelHandler.registerPortableGeneratorFuel(fluidRL, fluxPerTick, mbPerTick);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void addBoatFuel(List<? extends String> list) {
/* 62 */     for (int i = 0; i < list.size(); i++) {
/* 63 */       String str = list.get(i);
/*    */       
/* 65 */       if (!str.isEmpty()) {
/*    */         
/* 67 */         String[] split = str.split(", {0,}");
/*    */         
/* 69 */         if (split.length < 2) {
/* 70 */           throw new IllegalArgumentException("Missing values in \"" + str + "\".");
/*    */         }
/*    */         
/* 73 */         ResourceLocation fluidRL = null;
/* 74 */         int mbPerTick = 0;
/*    */         
/*    */         try {
/* 77 */           fluidRL = new ResourceLocation(split[0].trim());
/* 78 */         } catch (ResourceLocationException e) {
/* 79 */           throw new IllegalArgumentException(e);
/*    */         } 
/*    */         
/*    */         try {
/* 83 */           mbPerTick = Integer.valueOf(split[1].trim()).intValue();
/* 84 */           if (mbPerTick < 0) {
/* 85 */             throw new IllegalArgumentException("Negative value for fuel mB/tick for boat fuel " + i + 1);
/*    */           }
/* 87 */         } catch (NumberFormatException e) {
/* 88 */           throw new IllegalArgumentException("Invalid value for fuel mB/tick for boat fuel " + i + 1, e);
/*    */         } 
/*    */         
/* 91 */         if (!ForgeRegistries.FLUIDS.containsKey(fluidRL)) {
/* 92 */           throw new RuntimeException("\"" + fluidRL + "\" did not resolve into a valid fluid. (" + fluidRL + ")");
/*    */         }
/*    */         
/* 95 */         FuelHandler.registerMotorboatFuel(fluidRL, mbPerTick);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\cfg\ConfigUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */