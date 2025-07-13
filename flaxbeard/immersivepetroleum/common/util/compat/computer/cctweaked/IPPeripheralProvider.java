/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import dan200.computercraft.api.ComputerCraftAPI;
/*    */ import dan200.computercraft.api.peripheral.IPeripheral;
/*    */ import dan200.computercraft.api.peripheral.IPeripheralProvider;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.CokerUnitPeripheral;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.DerrickPeripheral;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.DistillationTowerPeripheral;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.HydrotreaterPeripheral;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.OilTankPeripheral;
/*    */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.multiblocks.PumpjackPeripheral;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.common.util.LazyOptional;
/*    */ 
/*    */ public class IPPeripheralProvider implements IPeripheralProvider {
/* 26 */   public static final IPPeripheralProvider INSTANCE = new IPPeripheralProvider();
/*    */ 
/*    */   
/*    */   public LazyOptional<IPeripheral> getPeripheral(Level world, BlockPos pos, Direction side) {
/* 30 */     BlockEntity be = world.m_7702_(pos);
/* 31 */     if (be instanceof MultiblockPartBlockEntity) { MultiblockPartBlockEntity<?> mbpbe = (MultiblockPartBlockEntity)be; if (mbpbe.isRedstonePos()) {
/*    */         
/* 33 */         if (be instanceof HydrotreaterTileEntity) { HydrotreaterTileEntity hydrotreater = (HydrotreaterTileEntity)be;
/* 34 */           return LazyOptional.of(() -> new HydrotreaterPeripheral(hydrotreater)); }
/*    */         
/* 36 */         if (be instanceof DistillationTowerTileEntity) { DistillationTowerTileEntity tower = (DistillationTowerTileEntity)be;
/* 37 */           return LazyOptional.of(() -> new DistillationTowerPeripheral(tower)); }
/*    */         
/* 39 */         if (be instanceof CokerUnitTileEntity) { CokerUnitTileEntity coker = (CokerUnitTileEntity)be;
/* 40 */           return LazyOptional.of(() -> new CokerUnitPeripheral(coker)); }
/*    */         
/* 42 */         if (be instanceof PumpjackTileEntity) { PumpjackTileEntity pumpjack = (PumpjackTileEntity)be;
/* 43 */           return LazyOptional.of(() -> new PumpjackPeripheral(pumpjack)); }
/*    */         
/* 45 */         if (be instanceof OilTankTileEntity) { OilTankTileEntity oiltank = (OilTankTileEntity)be;
/* 46 */           return LazyOptional.of(() -> new OilTankPeripheral(oiltank)); }
/*    */         
/* 48 */         if (be instanceof DerrickTileEntity) { DerrickTileEntity derrick = (DerrickTileEntity)be;
/* 49 */           return LazyOptional.of(() -> new DerrickPeripheral(derrick)); }
/*    */       
/*    */       }  }
/*    */     
/* 53 */     return LazyOptional.empty();
/*    */   }
/*    */   
/*    */   public static void init() {
/* 57 */     ComputerCraftAPI.registerPeripheralProvider(INSTANCE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\IPPeripheralProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */