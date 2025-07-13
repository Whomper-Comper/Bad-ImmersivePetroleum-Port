/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ import dan200.computercraft.api.peripheral.IComputerAccess;
/*    */ import dan200.computercraft.api.peripheral.IPeripheral;
/*    */ 
/*    */ public abstract class MultiblockPartPeripheral
/*    */   implements IPeripheral {
/*    */   public MultiblockPartPeripheral(MultiblockPartBlockEntity<?> mbPart) {
/* 11 */     this.mbPart = mbPart;
/*    */   }
/*    */   MultiblockPartBlockEntity<?> mbPart;
/*    */   @LuaFunction
/*    */   public final void setEnabled(boolean bool) {
/* 16 */     this.mbPart.computerControl.setEnabled(bool);
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final boolean isEnabled() {
/* 21 */     return this.mbPart.computerControl.isEnabled();
/*    */   }
/*    */ 
/*    */   
/*    */   public void attach(IComputerAccess computer) {
/* 26 */     this.mbPart.computerControl.addReference();
/*    */   }
/*    */ 
/*    */   
/*    */   public void detach(IComputerAccess computer) {
/* 31 */     this.mbPart.computerControl.removeReference();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(IPeripheral other) {
/* 36 */     if (other == this)
/* 37 */       return true; 
/* 38 */     if (other instanceof MultiblockPartPeripheral) { MultiblockPartPeripheral part = (MultiblockPartPeripheral)other; if (part.mbPart.m_58899_().equals(this.mbPart.m_58899_()))
/* 39 */         return true;  }
/* 40 */      return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\generic\MultiblockPartPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */