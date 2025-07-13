/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*    */ import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
/*    */ import dan200.computercraft.api.lua.LuaFunction;
/*    */ import dan200.computercraft.api.lua.MethodResult;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.CCTUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.PoweredMultiblockPeripheral;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class DistillationTowerPeripheral extends PoweredMultiblockPeripheral {
/*    */   public DistillationTowerPeripheral(DistillationTowerTileEntity tower) {
/* 17 */     super((PoweredMultiblockBlockEntity)tower);
/* 18 */     this.master = (DistillationTowerTileEntity)tower.master();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 23 */     return "ip_distillationtower";
/*    */   }
/*    */   DistillationTowerTileEntity master;
/*    */   @LuaFunction
/*    */   public final MethodResult getTankSize(int tank) {
/* 28 */     switch (tank) {
/*    */       case 1:
/*    */       case 2:
/* 31 */         return MethodResult.of(Integer.valueOf(this.master.tanks[tank - 1].getCapacity()));
/*    */     } 
/* 33 */     return MethodResult.of(new Object[] { null, "Index " + tank + " out of Bounds." });
/*    */   }
/*    */ 
/*    */   
/*    */   @LuaFunction
/*    */   public final Map<String, Object> getInputTank() {
/* 39 */     return CCTUtils.fluidToMap(this.master.tanks[0].getFluid());
/*    */   }
/*    */   
/*    */   @LuaFunction
/*    */   public final List<Map<String, Object>> getOutputTank() {
/* 44 */     MultiFluidTank tank = this.master.tanks[1];
/*    */     
/* 46 */     List<Map<String, Object>> list = new ArrayList<>();
/* 47 */     if (!tank.fluids.isEmpty()) {
/* 48 */       tank.fluids.forEach(f -> list.add(CCTUtils.fluidToMap(f)));
/*    */     }
/*    */     
/* 51 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\DistillationTowerPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */