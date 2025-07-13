/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.multiblocks.HydroTreaterMultiblock;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ 
/*    */ public class HydrotreaterContainer extends MultiblockAwareGuiContainer<HydrotreaterTileEntity> {
/*    */   public HydrotreaterContainer(MenuType<?> type, int id, Inventory playerInventory, HydrotreaterTileEntity tile) {
/* 10 */     super(type, tile, id, (IETemplateMultiblock)HydroTreaterMultiblock.INSTANCE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\HydrotreaterContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */