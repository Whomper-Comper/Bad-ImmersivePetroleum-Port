/*    */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.EntityBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ 
/*    */ public class WellBlock extends IPBlockBase implements EntityBlock {
/*    */   public WellBlock() {
/* 23 */     super(BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60913_(-1.0F, 3600000.0F).m_222994_().m_60922_((s, r, p, e) -> false).m_60999_());
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<BlockItem> blockItemSupplier() {
/* 28 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
/* 33 */     return ((BlockEntityType)IPTileTypes.WELL.get()).m_155264_(pPos, pState);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
/* 39 */     return createCommonTicker(level.f_46443_, type, IPTileTypes.WELL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\WellBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */