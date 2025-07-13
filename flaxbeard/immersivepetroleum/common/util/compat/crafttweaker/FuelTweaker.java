/*    */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*    */ 
/*    */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*    */ import com.blamejared.crafttweaker.api.fluid.IFluidStack;
/*    */ import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import org.openzen.zencode.java.ZenCodeType.Method;
/*    */ import org.openzen.zencode.java.ZenCodeType.Name;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ZenRegister
/*    */ @Name("mods.immersivepetroleum.FuelRegistry")
/*    */ public class FuelTweaker
/*    */ {
/*    */   @Method
/*    */   public static void registerGeneratorFuel(IFluidStack fluid, int fluxPerTick) {
/* 18 */     if (fluid == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 23 */     if (fluxPerTick < 1) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 28 */     FluidStack fstack = fluid.getInternal();
/* 29 */     FuelHandler.registerPortableGeneratorFuel(fstack.getFluid(), fluxPerTick, fstack.getAmount());
/*    */   }
/*    */   
/*    */   @Method
/*    */   public static void registerMotorboatFuel(IFluidStack fluid) {
/* 34 */     if (fluid == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 39 */     FluidStack fstack = fluid.getInternal();
/* 40 */     FuelHandler.registerMotorboatFuel(fstack.getFluid(), fstack.getAmount());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\FuelTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */