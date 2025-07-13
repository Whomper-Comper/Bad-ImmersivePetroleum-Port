/*    */ package flaxbeard.immersivepetroleum.common.blocks;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.SlabBlock;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.block.state.properties.SlabType;
/*    */ 
/*    */ public class IPBlockSlab<B extends IPBlockBase> extends SlabBlock {
/*    */   private final B base;
/*    */   
/*    */   public IPBlockSlab(B base) {
/* 17 */     super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)base).m_60960_(causesSuffocation((Block)base)).m_60924_(isNormalCube((Block)base)));
/* 18 */     this.base = base;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int m_7753_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/* 24 */     return Math.min(this.base.m_7753_(state, worldIn, pos), super.m_7753_(state, worldIn, pos));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_7420_(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
/* 29 */     return (super.m_7420_(state, reader, pos) || this.base.m_7420_(state, reader, pos));
/*    */   }
/*    */   
/*    */   public static BlockBehaviour.StatePredicate causesSuffocation(Block base) {
/* 33 */     return (state, world, pos) -> (base.m_49966_().m_60828_(world, pos) && state.m_61143_((Property)f_56353_) == SlabType.DOUBLE);
/*    */   }
/*    */   
/*    */   public static BlockBehaviour.StatePredicate isNormalCube(Block base) {
/* 37 */     return (state, world, pos) -> (base.m_49966_().m_60796_(world, pos) && state.m_61143_((Property)f_56353_) == SlabType.DOUBLE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\IPBlockSlab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */