/*     */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*     */ import dan200.computercraft.api.lua.LuaFunction;
/*     */ import dan200.computercraft.api.lua.MethodResult;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.CCTUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.generic.PoweredMultiblockPeripheral;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class DerrickPeripheral extends PoweredMultiblockPeripheral {
/*     */   public DerrickPeripheral(DerrickTileEntity tower) {
/*  21 */     super((PoweredMultiblockBlockEntity)tower);
/*  22 */     this.master = (DerrickTileEntity)tower.master();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  27 */     return "ip_derrick";
/*     */   }
/*     */   DerrickTileEntity master;
/*     */   @LuaFunction
/*     */   public final boolean isDrilling() {
/*  32 */     return this.master.drilling;
/*     */   }
/*     */   
/*     */   @LuaFunction
/*     */   public final boolean isSpilling() {
/*  37 */     return this.master.spilling;
/*     */   }
/*     */   
/*     */   @LuaFunction
/*     */   public final boolean completed() {
/*     */     WellTileEntity well;
/*  43 */     if ((well = this.master.getWell()) == null) {
/*  44 */       return false;
/*     */     }
/*     */     
/*  47 */     return (well.wellPipeLength >= well.getMaxPipeLength() && !this.master.spilling);
/*     */   }
/*     */ 
/*     */   
/*     */   @LuaFunction
/*     */   public final Map<String, Object> getInputTank() {
/*  53 */     return CCTUtils.fluidToMap(this.master.tank.getFluid());
/*     */   }
/*     */ 
/*     */   
/*     */   @LuaFunction
/*     */   public final MethodResult getExpectedFluid() {
/*     */     WellTileEntity well;
/*  60 */     if ((well = this.master.getWell()) == null) {
/*  61 */       return MethodResult.of(new Object[] { null, "Well not found!" });
/*     */     }
/*     */     
/*  64 */     int realPipeLength = this.master.m_58899_().m_123342_() - 1 - well.m_58899_().m_123342_();
/*  65 */     int concreteNeeded = 125 * (realPipeLength - well.wellPipeLength);
/*  66 */     if (concreteNeeded > 0) {
/*  67 */       return MethodResult.of(CCTUtils.fluidToMap(new FluidStack(ExternalModContent.getIEFluid_Concrete(), concreteNeeded)));
/*     */     }
/*  69 */     int waterNeeded = 125 * (well.getMaxPipeLength() - well.wellPipeLength);
/*  70 */     if (waterNeeded > 0) {
/*  71 */       return MethodResult.of(CCTUtils.fluidToMap(new FluidStack((Fluid)Fluids.f_76193_, waterNeeded)));
/*     */     }
/*     */ 
/*     */     
/*  75 */     return MethodResult.of();
/*     */   }
/*     */ 
/*     */   
/*     */   @LuaFunction
/*     */   public final float getPressure() {
/*     */     WellTileEntity well;
/*  82 */     if ((well = this.master.getWell()) == null) {
/*  83 */       return 0.0F;
/*     */     }
/*     */     
/*  86 */     float highest = 0.0F;
/*  87 */     for (ColumnPos cPos : well.tappedIslands) {
/*  88 */       ReservoirIsland island = ReservoirHandler.getIsland(this.master.getLevelNonnull(), cPos);
/*  89 */       if (island != null) {
/*  90 */         float pressure = island.getPressure(this.master.getLevelNonnull(), cPos.f_140723_(), cPos.f_140724_());
/*  91 */         if (highest < pressure) {
/*  92 */           highest = pressure;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return highest;
/*     */   }
/*     */ 
/*     */   
/*     */   @LuaFunction
/*     */   public final int getFlowrate() {
/*     */     WellTileEntity well;
/* 104 */     if ((well = this.master.getWell()) == null) {
/* 105 */       return 0;
/*     */     }
/*     */     
/* 108 */     int totalFlowrate = 0;
/* 109 */     for (ColumnPos cPos : well.tappedIslands) {
/* 110 */       ReservoirIsland island = ReservoirHandler.getIsland(this.master.getLevelNonnull(), cPos);
/* 111 */       if (island != null) {
/* 112 */         float pressure = island.getPressure(this.master.getLevelNonnull(), cPos.f_140723_(), cPos.f_140724_());
/* 113 */         totalFlowrate += ReservoirIsland.getFlow(pressure);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return totalFlowrate;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\multiblocks\DerrickPeripheral.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */