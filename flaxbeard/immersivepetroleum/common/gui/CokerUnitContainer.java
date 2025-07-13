/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.multiblocks.CokerUnitMultiblock;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*    */ 
/*    */ public class CokerUnitContainer extends MultiblockAwareGuiContainer<CokerUnitTileEntity> {
/*    */   public CokerUnitContainer(MenuType<?> type, int id, Inventory playerInventory, CokerUnitTileEntity tile) {
/* 16 */     super(type, tile, id, (IETemplateMultiblock)CokerUnitMultiblock.INSTANCE);
/*    */     
/* 18 */     m_38897_(new IPSlot.CokerInput((AbstractContainerMenu)this, getInv(), CokerUnitTileEntity.Inventory.INPUT.id(), 20, 71));
/* 19 */     m_38897_(new IPSlot(getInv(), CokerUnitTileEntity.Inventory.INPUT_FILLED.id(), 9, 14, stack -> ((Boolean)FluidUtil.getFluidHandler(stack).map(()).orElse(Boolean.valueOf(false))).booleanValue()));
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
/* 33 */     m_38897_(new IPSlot.ItemOutput(getInv(), CokerUnitTileEntity.Inventory.INPUT_EMPTY.id(), 9, 45));
/*    */     
/* 35 */     m_38897_(new IPSlot.FluidContainer(getInv(), CokerUnitTileEntity.Inventory.OUTPUT_EMPTY.id(), 175, 14, IPSlot.FluidContainer.FluidFilter.EMPTY));
/* 36 */     m_38897_(new IPSlot.ItemOutput(getInv(), CokerUnitTileEntity.Inventory.OUTPUT_FILLED.id(), 175, 45));
/*    */     
/* 38 */     this.ownSlotCount = (CokerUnitTileEntity.Inventory.values()).length;
/*    */     
/* 40 */     addPlayerInventorySlots(playerInventory, 20, 105);
/* 41 */     addPlayerHotbarSlots(playerInventory, 20, 163);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\CokerUnitContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */