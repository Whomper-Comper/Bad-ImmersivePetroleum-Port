/*     */ package flaxbeard.immersivepetroleum.common.util;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.fluids.FluidActionResult;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.items.ItemHandlerHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FluidHelper
/*     */ {
/*     */   public static FluidStack copyFluid(FluidStack fluid, int amount) {
/*  22 */     return copyFluid(fluid, amount, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluidStack copyFluid(FluidStack fluid, int amount, boolean pressurize) {
/*  34 */     FluidStack fs = new FluidStack(fluid.getFluid(), amount);
/*  35 */     if (pressurize && amount > 50) {
/*  36 */       fs.getOrCreateTag().m_128379_("pressurized", true);
/*     */     }
/*  38 */     return fs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluidStack makePressurizedFluid(Fluid fluid, int amount) {
/*  50 */     FluidStack fs = new FluidStack(fluid, amount);
/*  51 */     if (amount > 50) {
/*  52 */       fs.getOrCreateTag().m_128379_("pressurized", true);
/*     */     }
/*  54 */     return fs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFluidContainerFull(ItemStack stack) {
/*  61 */     return ((Boolean)FluidUtil.getFluidHandler(stack).map(handler -> {
/*     */           for (int t = 0; t < handler.getTanks(); t++) {
/*     */             if (handler.getFluidInTank(t).getAmount() < handler.getTankCapacity(t))
/*     */               return Boolean.valueOf(false); 
/*     */           }  return Boolean.valueOf(true);
/*  66 */         }).orElse(Boolean.valueOf(true))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack fillFluidContainer(IFluidHandler handler, ItemStack containerIn, ItemStack containerOut, @Nullable Player player) {
/*  74 */     if (containerIn == null || containerIn.m_41619_()) {
/*  75 */       return ItemStack.f_41583_;
/*     */     }
/*  77 */     FluidActionResult result = FluidUtil.tryFillContainer(containerIn, handler, 2147483647, player, false);
/*  78 */     if (result.isSuccess()) {
/*  79 */       ItemStack full = result.getResult();
/*  80 */       if (containerOut.m_41619_() || ItemHandlerHelper.canItemStacksStack(containerOut, full)) {
/*  81 */         if (!containerOut.m_41619_() && containerOut.m_41613_() + full.m_41613_() > containerOut.m_41741_())
/*  82 */           return ItemStack.f_41583_; 
/*  83 */         result = FluidUtil.tryFillContainer(containerIn, handler, 2147483647, player, true);
/*  84 */         if (result.isSuccess()) {
/*  85 */           return result.getResult();
/*     */         }
/*     */       } 
/*     */     } 
/*  89 */     return ItemStack.f_41583_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack fillFluidContainer(IFluidTank tank, FluidStack fluid, ItemStack containerIn, ItemStack containerOut) {
/*  98 */     if (containerIn == null || containerIn.m_41619_()) {
/*  99 */       return ItemStack.f_41583_;
/*     */     }
/* 101 */     FluidActionResult result = tryFillContainer(tank, fluid, containerIn, false);
/* 102 */     if (result.isSuccess()) {
/* 103 */       ItemStack full = result.getResult();
/* 104 */       if (containerOut.m_41619_() || ItemHandlerHelper.canItemStacksStack(containerOut, full)) {
/* 105 */         if (!containerOut.m_41619_() && containerOut.m_41613_() + full.m_41613_() > containerOut.m_41741_()) {
/* 106 */           return ItemStack.f_41583_;
/*     */         }
/*     */         
/* 109 */         result = tryFillContainer(tank, fluid, containerIn, true);
/* 110 */         if (result.isSuccess()) {
/* 111 */           return result.getResult();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return ItemStack.f_41583_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FluidActionResult tryFillContainer(IFluidTank tank, FluidStack fluidSource, @Nonnull ItemStack container, boolean doFill) {
/* 124 */     ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(container, 1);
/* 125 */     return FluidUtil.getFluidHandler(containerCopy).map(containerFluidHandler -> {
/*     */           int fillableAmount = containerFluidHandler.fill(fluidSource, IFluidHandler.FluidAction.SIMULATE);
/*     */           
/*     */           if (fillableAmount > 0) {
/*     */             if (doFill) {
/*     */               FluidStack fs = new FluidStack(fluidSource, Math.min(fluidSource.getAmount(), fillableAmount));
/*     */               
/*     */               containerFluidHandler.fill(fs, IFluidHandler.FluidAction.EXECUTE);
/*     */               
/*     */               tank.drain(fs, IFluidHandler.FluidAction.EXECUTE);
/*     */             } 
/*     */             ItemStack resultContainer = containerFluidHandler.getContainer();
/*     */             return new FluidActionResult(resultContainer);
/*     */           } 
/*     */           return FluidActionResult.FAILURE;
/* 140 */         }).orElse(FluidActionResult.FAILURE);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\FluidHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */