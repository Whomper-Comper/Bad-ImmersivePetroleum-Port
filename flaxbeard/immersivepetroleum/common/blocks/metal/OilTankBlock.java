/*    */ package flaxbeard.immersivepetroleum.common.blocks.metal;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPMetalMultiblock;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class OilTankBlock extends IPMetalMultiblock<OilTankTileEntity> {
/*    */   public OilTankBlock() {
/* 14 */     super(IPTileTypes.OILTANK);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
/* 19 */     BlockEntity te = world.m_7702_(pos);
/* 20 */     if (te instanceof OilTankTileEntity) { OilTankTileEntity oiltank = (OilTankTileEntity)te; if (oiltank.isLadder())
/* 21 */         return true;  }
/*    */     
/* 23 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\metal\OilTankBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */