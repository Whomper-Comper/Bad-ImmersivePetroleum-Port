/*    */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ public class WellPipeTileEntity extends IPTileEntityBase {
/*    */   public WellPipeTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
/* 13 */     super((BlockEntityType)IPTileTypes.WELL_PIPE.get(), pWorldPosition, pBlockState);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeCustom(CompoundTag nbt) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readCustom(CompoundTag nbt) {}
/*    */ 
/*    */   
/*    */   public WellTileEntity getWell() {
/* 26 */     for (int y = this.f_58858_.m_123342_() - 1; y >= this.f_58857_.m_141937_(); y--) {
/* 27 */       BlockEntity teLow = this.f_58857_.m_7702_(new BlockPos(this.f_58858_.m_123341_(), y, this.f_58858_.m_123343_()));
/*    */       
/* 29 */       if (teLow instanceof WellTileEntity) { WellTileEntity well = (WellTileEntity)teLow;
/* 30 */         return well; }
/*    */ 
/*    */       
/* 33 */       if (!(teLow instanceof WellPipeTileEntity)) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos checkForMissingPipe() {
/* 43 */     for (int y = this.f_58858_.m_123342_() + 1; y < this.f_58857_.m_151558_(); y++) {
/* 44 */       BlockPos pos = new BlockPos(this.f_58858_.m_123341_(), y, this.f_58858_.m_123343_());
/* 45 */       BlockEntity teHigh = this.f_58857_.m_7702_(pos);
/*    */       
/* 47 */       if (!(teHigh instanceof WellPipeTileEntity)) {
/* 48 */         return pos;
/*    */       }
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Pair<Boolean, BlockPos> hasValidConnection() {
/* 58 */     BlockPos pos = null;
/* 59 */     for (int y = this.f_58858_.m_123342_() + 1; y < this.f_58857_.m_151558_(); y++) {
/* 60 */       pos = new BlockPos(this.f_58858_.m_123341_(), y, this.f_58858_.m_123343_());
/* 61 */       BlockEntity teHigh = this.f_58857_.m_7702_(pos);
/*    */       
/* 63 */       if (teHigh instanceof PumpjackTileEntity) { PumpjackTileEntity pumpjack = (PumpjackTileEntity)teHigh; if (pumpjack.offsetToMaster.equals(BlockPos.f_121853_))
/* 64 */           return Pair.of(Boolean.valueOf(true), pos);  }  if (teHigh instanceof DerrickTileEntity) { DerrickTileEntity derrick = (DerrickTileEntity)teHigh; if (derrick.offsetToMaster.equals(BlockPos.f_121853_)) return Pair.of(Boolean.valueOf(true), pos);
/*    */          }
/*    */       
/* 67 */       if (!(teHigh instanceof WellPipeTileEntity)) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 72 */     return Pair.of(Boolean.valueOf(false), pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\WellPipeTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */