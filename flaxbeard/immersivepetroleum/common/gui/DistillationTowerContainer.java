/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.multiblocks.DistillationTowerMultiblock;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistillationTowerContainer
/*    */   extends MultiblockAwareGuiContainer<DistillationTowerTileEntity>
/*    */ {
/*    */   public DistillationTowerContainer(MenuType<?> type, int id, Inventory playerInventory, final DistillationTowerTileEntity tile) {
/* 23 */     super(type, tile, id, (IETemplateMultiblock)DistillationTowerMultiblock.INSTANCE);
/*    */     
/* 25 */     m_38897_(new IPSlot(getInv(), 0, 12, 17)
/*    */         {
/*    */           public boolean m_5857_(@Nonnull ItemStack stack) {
/* 28 */             return ((Boolean)FluidUtil.getFluidHandler(stack).map(h -> {
/*    */                   if (h.getTanks() <= 0) {
/*    */                     return Boolean.valueOf(false);
/*    */                   }
/*    */                   
/*    */                   FluidStack fs = h.getFluidInTank(0);
/*    */                   
/*    */                   if (fs.isEmpty() || (tile.tanks[0].getFluidAmount() > 0 && !fs.isFluidEqual(tile.tanks[0].getFluid()))) {
/*    */                     return Boolean.valueOf(false);
/*    */                   }
/*    */                   DistillationTowerRecipe recipe = DistillationTowerRecipe.findRecipe(fs);
/*    */                   return Boolean.valueOf((recipe != null));
/* 40 */                 }).orElse(Boolean.valueOf(false))).booleanValue();
/*    */           }
/*    */         });
/* 43 */     m_38897_(new IPSlot.ItemOutput(getInv(), 1, 12, 53));
/*    */     
/* 45 */     m_38897_(new IPSlot.FluidContainer(getInv(), 2, 134, 17, IPSlot.FluidContainer.FluidFilter.EMPTY));
/* 46 */     m_38897_(new IPSlot.ItemOutput(getInv(), 3, 134, 53));
/*    */     
/* 48 */     this.ownSlotCount = 4;
/*    */     
/* 50 */     addPlayerInventorySlots(playerInventory, 8, 85);
/* 51 */     addPlayerHotbarSlots(playerInventory, 8, 143);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\DistillationTowerContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */