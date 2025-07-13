/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
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
/*    */ class null
/*    */   extends IPSlot
/*    */ {
/*    */   null(Container inventoryIn, int index, int xPosition, int yPosition) {
/* 25 */     super(inventoryIn, index, xPosition, yPosition);
/*    */   }
/*    */   public boolean m_5857_(@Nonnull ItemStack stack) {
/* 28 */     return ((Boolean)FluidUtil.getFluidHandler(stack).map(h -> {
/*    */           if (h.getTanks() <= 0) {
/*    */             return Boolean.valueOf(false);
/*    */           }
/*    */           
/*    */           FluidStack fs = h.getFluidInTank(0);
/*    */           
/*    */           if (fs.isEmpty() || (tile.tanks[0].getFluidAmount() > 0 && !fs.isFluidEqual(tile.tanks[0].getFluid()))) {
/*    */             return Boolean.valueOf(false);
/*    */           }
/*    */           DistillationTowerRecipe recipe = DistillationTowerRecipe.findRecipe(fs);
/*    */           return Boolean.valueOf((recipe != null));
/* 40 */         }).orElse(Boolean.valueOf(false))).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\DistillationTowerContainer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */