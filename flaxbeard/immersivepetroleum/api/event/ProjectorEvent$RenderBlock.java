/*    */ package flaxbeard.immersivepetroleum.api.event;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.eventbus.api.Cancelable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RenderBlock
/*    */   extends ProjectorEvent
/*    */ {
/*    */   public RenderBlock(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/* 45 */     super(multiblock, templateWorld, templatePos, world, worldPos, state, rotation);
/*    */   }
/*    */   
/*    */   public void setState(BlockState state) {
/* 49 */     this.state = state;
/*    */   }
/*    */   
/*    */   public void setState(Block block) {
/* 53 */     this.state = block.m_49966_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\event\ProjectorEvent$RenderBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */