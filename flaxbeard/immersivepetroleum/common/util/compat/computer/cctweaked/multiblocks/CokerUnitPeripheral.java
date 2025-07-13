/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ import dan200.computercraft.api.lua.MethodResult;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.CCTUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.PoweredMultiblockPeripheral;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class CokerUnitPeripheral extends PoweredMultiblockPeripheral {
/*    */   CokerUnitTileEntity master;
/*    */   
/*    */   public CokerUnitPeripheral(CokerUnitTileEntity coker) {
/* 18 */     super((PoweredMultiblockBlockEntity)coker);
/* 19 */     this.master = (CokerUnitTileEntity)coker.master();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 24 */     return "ip_cokerunit";
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getChamberA() {
/* 29 */     return getChamber(0);
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getChamberB() {
/* 34 */     return getChamber(1);
/*    */   }
/*    */   
/*    */   private Map<String, Object> getChamber(int id) {
/* 38 */     CokerUnitTileEntity.CokingChamber chamber = this.master.chambers[id];
/*    */     
/* 40 */     Map<String, Object> map = new HashMap<>();
/*    */ 
/*    */     
/* 43 */     ResourceLocation rl = RegistryUtils.getRegistryNameOf(chamber.getInputItem().m_41720_());
/* 44 */     String regName = (rl == null) ? null : rl.toString();
/*    */     
/* 46 */     Map<String, Object> inputMap = new HashMap<>();
/* 47 */     inputMap.put("name", regName);
/* 48 */     inputMap.put("count", Integer.valueOf(chamber.getInputAmount()));
/* 49 */     map.put("input", inputMap);
/*    */ 
/*    */ 
/*    */     
/* 53 */     rl = RegistryUtils.getRegistryNameOf(chamber.getOutputItem().m_41720_());
/* 54 */     regName = (rl == null) ? null : rl.toString();
/*    */     
/* 56 */     Map<String, Object> outputMap = new HashMap<>();
/* 57 */     outputMap.put("name", regName);
/* 58 */     outputMap.put("count", Integer.valueOf(chamber.getOutputAmount()));
/* 59 */     map.put("output", outputMap);
/*    */ 
/*    */     
/* 62 */     map.put("state", chamber.getState().toString());
/* 63 */     map.put("tank", CCTUtils.fluidToMap(chamber.getTank().getFluid()));
/* 64 */     map.put("tankCapacity", Integer.valueOf(chamber.getTank().getCapacity()));
/* 65 */     map.put("itemCapacity", Integer.valueOf(chamber.getCapacity()));
/* 66 */     return map;
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final MethodResult getTankSize(int tank) {
/* 71 */     switch (tank) {
/*    */       case 1:
/*    */       case 2:
/* 74 */         return MethodResult.of(Integer.valueOf(this.master.bufferTanks[tank - 1].getCapacity()));
/*    */     } 
/* 76 */     return MethodResult.of(new Object[] { null, "Index " + tank + " out of Bounds." });
/*    */   }
/*    */ 
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getInputTank() {
/* 82 */     return CCTUtils.fluidToMap(this.master.bufferTanks[0].getFluid());
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getOutputTank() {
/* 87 */     return CCTUtils.fluidToMap(this.master.bufferTanks[1].getFluid());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\CokerUnitPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */