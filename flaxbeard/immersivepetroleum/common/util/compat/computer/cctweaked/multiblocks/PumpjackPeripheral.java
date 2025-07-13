/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.PoweredMultiblockPeripheral;
/*    */ 
/*    */ public class PumpjackPeripheral extends PoweredMultiblockPeripheral {
/*    */   public PumpjackPeripheral(PumpjackTileEntity pumpjack) {
/*  8 */     super((PoweredMultiblockBlockEntity)pumpjack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 13 */     return "ip_pumpjack";
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\PumpjackPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */