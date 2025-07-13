/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.common.util.LazyOptional;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*    */ 
/*    */ public class IPSlot
/*    */   extends Slot
/*    */ {
/*    */   private final Predicate<ItemStack> consumer;
/*    */   
/*    */   public IPSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
/* 20 */     super(inventoryIn, index, xPosition, yPosition);
/* 21 */     this.consumer = null;
/*    */   }
/*    */   
/*    */   public IPSlot(Container inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> placeCheck) {
/* 25 */     super(inventoryIn, index, xPosition, yPosition);
/* 26 */     this.consumer = placeCheck;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_5857_(ItemStack pStack) {
/* 31 */     if (this.consumer != null) {
/* 32 */       return this.consumer.test(pStack);
/*    */     }
/* 34 */     return super.m_5857_(pStack);
/*    */   }
/*    */   
/*    */   public static class ItemOutput extends IPSlot {
/*    */     public ItemOutput(Container inventoryIn, int index, int xPosition, int yPosition) {
/* 39 */       super(inventoryIn, index, xPosition, yPosition);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean m_5857_(@Nonnull ItemStack stack) {
/* 44 */       return false;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class CokerInput extends IPSlot {
/*    */     public CokerInput(AbstractContainerMenu container, Container inv, int id, int x, int y) {
/* 50 */       super(inv, id, x, y);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean m_5857_(ItemStack stack) {
/* 55 */       return (!stack.m_41619_() && CokerUnitRecipe.hasRecipeWithInput(stack, true));
/*    */     }
/*    */   }
/*    */   
/*    */   public static class FluidContainer extends IPSlot { FluidFilter filter;
/*    */     
/*    */     public FluidContainer(Container inv, int id, int x, int y, FluidFilter filter) {
/* 62 */       super(inv, id, x, y);
/* 63 */       this.filter = filter;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean m_5857_(@Nonnull ItemStack itemStack) {
/* 68 */       LazyOptional<IFluidHandlerItem> handlerCap = FluidUtil.getFluidHandler(itemStack);
/* 69 */       return ((Boolean)handlerCap.map(handler -> { if (handler.getTanks() <= 0)
/*    */               return Boolean.valueOf(false);  switch (this.filter) { default:
/*    */                 throw new IncompatibleClassChangeError();
/*    */               case FULL:
/*    */               
/*    */               case EMPTY:
/*    */               
/*    */               case ANY:
/*    */                 break; }  return Boolean.valueOf(true);
/* 78 */           }).orElse(Boolean.valueOf(false))).booleanValue();
/*    */     }
/*    */     
/*    */     public enum FluidFilter {
/* 82 */       ANY, EMPTY, FULL; } } public enum FluidFilter { ANY, EMPTY, FULL; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\IPSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */