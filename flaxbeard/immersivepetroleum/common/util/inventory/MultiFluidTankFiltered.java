/*    */ package flaxbeard.immersivepetroleum.common.util.inventory;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*    */ 
/*    */ public class MultiFluidTankFiltered
/*    */   extends MultiFluidTank {
/*    */   protected Function<FluidStack, Boolean> validator;
/*    */   
/*    */   public MultiFluidTankFiltered(int capacity) {
/* 14 */     this(capacity, fs -> Boolean.valueOf(true));
/*    */   }
/*    */   
/*    */   public MultiFluidTankFiltered(int capacity, @Nonnull Function<FluidStack, Boolean> validator) {
/* 18 */     super(capacity);
/* 19 */     this.validator = validator;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFluidValid(FluidStack stack) {
/* 24 */     return ((Boolean)this.validator.apply(stack)).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
/* 29 */     return isFluidValid(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
/* 34 */     if (resource.isEmpty() || !isFluidValid(resource)) {
/* 35 */       return 0;
/*    */     }
/*    */     
/* 38 */     return super.fill(resource, action);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\inventory\MultiFluidTankFiltered.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */