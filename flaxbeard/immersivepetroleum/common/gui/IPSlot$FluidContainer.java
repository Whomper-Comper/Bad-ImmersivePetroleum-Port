/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.common.util.LazyOptional;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
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
/*    */ public class FluidContainer
/*    */   extends IPSlot
/*    */ {
/*    */   FluidFilter filter;
/*    */   
/*    */   public FluidContainer(Container inv, int id, int x, int y, FluidFilter filter) {
/* 62 */     super(inv, id, x, y);
/* 63 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_5857_(@Nonnull ItemStack itemStack) {
/* 68 */     LazyOptional<IFluidHandlerItem> handlerCap = FluidUtil.getFluidHandler(itemStack);
/* 69 */     return ((Boolean)handlerCap.map(handler -> { if (handler.getTanks() <= 0)
/*    */             return Boolean.valueOf(false);  switch (IPSlot.null.$SwitchMap$flaxbeard$immersivepetroleum$common$gui$IPSlot$FluidContainer$FluidFilter[this.filter.ordinal()]) { default:
/*    */               throw new IncompatibleClassChangeError();
/*    */             case 1:
/*    */             
/*    */             case 2:
/*    */             
/*    */             case 3:
/*    */               break; }  return Boolean.valueOf(true);
/* 78 */         }).orElse(Boolean.valueOf(false))).booleanValue();
/*    */   }
/*    */   
/*    */   public enum FluidFilter {
/* 82 */     ANY, EMPTY, FULL;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\IPSlot$FluidContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */