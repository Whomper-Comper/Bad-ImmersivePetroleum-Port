/*    */ package flaxbeard.immersivepetroleum.api.event;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
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
/*    */ @Cancelable
/*    */ public class PlaceBlockPost
/*    */   extends ProjectorEvent
/*    */ {
/*    */   public PlaceBlockPost(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/* 38 */     super(multiblock, templateWorld, templatePos, world, worldPos, state, rotation);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\event\ProjectorEvent$PlaceBlockPost.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */