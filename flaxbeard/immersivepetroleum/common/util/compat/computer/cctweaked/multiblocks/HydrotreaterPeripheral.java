/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ import dan200.computercraft.api.lua.MethodResult;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.CCTUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.PoweredMultiblockPeripheral;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HydrotreaterPeripheral
/*    */   extends PoweredMultiblockPeripheral {
/*    */   public HydrotreaterPeripheral(HydrotreaterTileEntity treater) {
/* 14 */     super((PoweredMultiblockBlockEntity)treater);
/* 15 */     this.master = (HydrotreaterTileEntity)treater.master();
/*    */   }
/*    */   HydrotreaterTileEntity master;
/*    */   
/*    */   public String getType() {
/* 20 */     return "ip_hydrotreater";
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final MethodResult getTankSize(int tank) {
/* 25 */     switch (tank) {
/*    */       case 1:
/*    */       case 2:
/*    */       case 3:
/* 29 */         return MethodResult.of(Integer.valueOf(this.master.tanks[tank - 1].getCapacity()));
/*    */     } 
/* 31 */     return MethodResult.of(new Object[] { null, "Index " + tank + " out of Bounds." });
/*    */   }
/*    */ 
/*    */   
/*    */   @LuaFunction
/*    */   public final MethodResult getInputTank(int tank) {
/* 37 */     switch (tank) {
/*    */       case 1:
/*    */       case 2:
/* 40 */         return MethodResult.of(CCTUtils.fluidToMap(this.master.tanks[tank - 1].getFluid()));
/*    */     } 
/* 42 */     return MethodResult.of(new Object[] { null, "Index " + tank + " out of Bounds." });
/*    */   }
/*    */ 
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getOutputTank() {
/* 48 */     return CCTUtils.fluidToMap(this.master.tanks[2].getFluid());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\HydrotreaterPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */