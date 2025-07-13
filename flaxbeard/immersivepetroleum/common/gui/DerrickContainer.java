/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.multiblocks.DerrickMultiblock;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class DerrickContainer extends MultiblockAwareGuiContainer<DerrickTileEntity> {
/*    */   public DerrickContainer(MenuType<?> type, int id, Inventory playerInventory, DerrickTileEntity tile) {
/* 15 */     super(type, tile, id, (IETemplateMultiblock)DerrickMultiblock.INSTANCE);
/*    */     
/* 17 */     m_38897_(new Slot(getInv(), 0, 92, 55)
/*    */         {
/*    */           public boolean m_5857_(@Nonnull ItemStack stack) {
/* 20 */             return ExternalModContent.isIEItem_Pipe(stack);
/*    */           }
/*    */         });
/*    */     
/* 24 */     this.ownSlotCount = 1;
/*    */     
/* 26 */     addPlayerInventorySlots(playerInventory, 20, 82);
/* 27 */     addPlayerHotbarSlots(playerInventory, 20, 140);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\DerrickContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */