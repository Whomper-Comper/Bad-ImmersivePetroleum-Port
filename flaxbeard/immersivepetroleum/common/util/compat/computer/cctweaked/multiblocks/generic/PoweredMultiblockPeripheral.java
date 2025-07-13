/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ 
/*    */ public abstract class PoweredMultiblockPeripheral extends MultiblockPartPeripheral {
/*    */   public PoweredMultiblockPeripheral(PoweredMultiblockBlockEntity<?, ?> mbPowered) {
/*  9 */     super((MultiblockPartBlockEntity<?>)mbPowered);
/* 10 */     this.mbPowered = (PoweredMultiblockBlockEntity<?, ?>)mbPowered.master();
/*    */   }
/*    */   PoweredMultiblockBlockEntity<?, ?> mbPowered;
/*    */   @LuaFunction
/*    */   public final boolean isRunning() {
/* 15 */     return this.mbPowered.shouldRenderAsActive();
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final int getEnergyStored() {
/* 20 */     return this.mbPowered.energyStorage.getEnergyStored();
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final int getMaxEnergyStored() {
/* 25 */     return this.mbPowered.energyStorage.getMaxEnergyStored();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\generic\PoweredMultiblockPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */