/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.CCTUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.MultiblockPartPeripheral;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OilTankPeripheral
/*    */   extends MultiblockPartPeripheral {
/*    */   public OilTankPeripheral(OilTankTileEntity oiltank) {
/* 13 */     super((MultiblockPartBlockEntity)oiltank);
/* 14 */     this.master = (OilTankTileEntity)oiltank.master();
/*    */   }
/*    */   OilTankTileEntity master;
/*    */   
/*    */   public String getType() {
/* 19 */     return "ip_oiltank";
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getFluid() {
/* 24 */     return CCTUtils.fluidToMap(this.master.tank.getFluid());
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final int getTankSize() {
/* 29 */     return this.master.tank.getCapacity();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\OilTankPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */