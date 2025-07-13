/*    */ package flaxbeard.immersivepetroleum.common.fluids;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.CommonEventHandler;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Material;
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
/*    */ public class NapalmFluidBlock
/*    */   extends IPFluid.IPFluidBlock
/*    */ {
/*    */   public NapalmFluidBlock(IPFluid.IPFluidEntry entry, BlockBehaviour.Properties props) {
/* 59 */     super(entry, props);
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_6807_(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
/* 64 */     for (Direction facing : Direction.values()) {
/* 65 */       BlockPos notifyPos = pos.m_121945_(facing);
/* 66 */       if (world.m_8055_(notifyPos).m_60734_() instanceof net.minecraft.world.level.block.FireBlock || world.m_8055_(notifyPos).m_60767_() == Material.f_76309_) {
/* 67 */         world.m_46597_(pos, Blocks.f_50083_.m_49966_());
/*    */         break;
/*    */       } 
/*    */     } 
/* 71 */     super.m_6807_(state, world, pos, oldState, isMoving);
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_6861_(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
/* 76 */     if (world.m_8055_(fromPos).m_60734_() instanceof net.minecraft.world.level.block.FireBlock || world.m_8055_(fromPos).m_60767_() == Material.f_76309_) {
/* 77 */       ResourceLocation d = world.m_46472_().m_135782_();
/* 78 */       if (!CommonEventHandler.napalmPositions.containsKey(d) || !((List)CommonEventHandler.napalmPositions.get(d)).contains(fromPos)) {
/* 79 */         NapalmFluid.processFire(this.entry, world, pos);
/*    */       }
/*    */     } 
/*    */     
/* 83 */     super.m_6861_(state, world, pos, blockIn, fromPos, isMoving);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\NapalmFluid$NapalmFluidBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */