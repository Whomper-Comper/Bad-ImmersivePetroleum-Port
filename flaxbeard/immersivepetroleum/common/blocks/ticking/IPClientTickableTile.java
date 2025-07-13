/*    */ package flaxbeard.immersivepetroleum.common.blocks.ticking;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public interface IPClientTickableTile {
/*    */   void tickClient();
/*    */   
/*    */   static <T extends BlockEntity & IPClientTickableTile> BlockEntityTicker<T> makeTicker() {
/* 13 */     return (level, pos, state, te) -> ((IPClientTickableTile)te).tickClient();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\ticking\IPClientTickableTile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */