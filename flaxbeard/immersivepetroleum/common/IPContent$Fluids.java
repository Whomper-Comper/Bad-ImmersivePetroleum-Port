/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.fluids.IPFluid;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fluids
/*     */ {
/* 113 */   public static final IPFluid.IPFluidEntry CRUDEOIL = IPFluid.makeFluid("crudeoil", 1000, 2250, 0.007D, flaxbeard.immersivepetroleum.common.fluids.CrudeOilFluid::new, flaxbeard.immersivepetroleum.common.fluids.CrudeOilFluid.CrudeOilBlock::new);
/* 114 */   public static final IPFluid.IPFluidEntry DIESEL_SULFUR = IPFluid.makeFluid("diesel_sulfur", 789, 1750, flaxbeard.immersivepetroleum.common.fluids.DieselFluid::new);
/* 115 */   public static final IPFluid.IPFluidEntry DIESEL = IPFluid.makeFluid("diesel", 789, 1750, flaxbeard.immersivepetroleum.common.fluids.DieselFluid::new);
/* 116 */   public static final IPFluid.IPFluidEntry LUBRICANT = IPFluid.makeFluid("lubricant", 925, 1000);
/* 117 */   public static final IPFluid.IPFluidEntry GASOLINE = IPFluid.makeFluid("gasoline", 789, 1200);
/*     */   
/* 119 */   public static final IPFluid.IPFluidEntry NAPHTHA = IPFluid.makeFluid("naphtha", 750, 750);
/* 120 */   public static final IPFluid.IPFluidEntry NAPHTHA_CRACKED = IPFluid.makeFluid("naphtha_cracked", 750, 750);
/* 121 */   public static final IPFluid.IPFluidEntry BENZENE = IPFluid.makeFluid("benzene", 876, 700);
/* 122 */   public static final IPFluid.IPFluidEntry PROPYLENE = IPFluid.makeFluid("propylene", 2, 1);
/* 123 */   public static final IPFluid.IPFluidEntry ETHYLENE = IPFluid.makeFluid("ethylene", 1, 1);
/* 124 */   public static final IPFluid.IPFluidEntry LUBRICANT_CRACKED = IPFluid.makeFluid("lubricant_cracked", 925, 1000);
/* 125 */   public static final IPFluid.IPFluidEntry KEROSENE = IPFluid.makeFluid("kerosene", 810, 900);
/* 126 */   public static final IPFluid.IPFluidEntry GASOLINE_ADDITIVES = IPFluid.makeFluid("gasoline_additives", 800, 900);
/*     */   
/* 128 */   public static final IPFluid.IPFluidEntry NAPALM = IPFluid.makeFluid("napalm", 1000, 4000, 0.0105D, flaxbeard.immersivepetroleum.common.fluids.NapalmFluid.NapalmFluidBlock::new);
/*     */   
/*     */   private static void forceClassLoad() {}
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPContent$Fluids.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */