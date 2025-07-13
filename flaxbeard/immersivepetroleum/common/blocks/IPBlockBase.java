/*    */ package flaxbeard.immersivepetroleum.common.blocks;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlacementReader;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlayerInteraction;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPClientTickableTile;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPServerTickableTile;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ public class IPBlockBase
/*    */   extends Block {
/*    */   private final boolean isEntityBlock;
/*    */   
/*    */   public IPBlockBase(BlockBehaviour.Properties props) {
/* 34 */     super(props);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     this.isEntityBlock = this instanceof net.minecraft.world.level.block.EntityBlock;
/*    */   }
/*    */   
/*    */   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
/* 45 */     if (this.isEntityBlock) {
/* 46 */       BlockEntity blockEntity = pLevel.m_7702_(pPos); if (blockEntity instanceof IPlayerInteraction) { IPlayerInteraction inst = (IPlayerInteraction)blockEntity;
/* 47 */         Vec3 loc = pHit.m_82450_();
/* 48 */         return inst.interact(pHit.m_82434_(), pPlayer, pHand, pPlayer.m_21120_(pHand), (float)loc.f_82479_, (float)loc.f_82480_, (float)loc.f_82481_); }
/*    */     
/*    */     } 
/* 51 */     return InteractionResult.FAIL;
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_6402_(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
/* 56 */     if (!pLevel.f_46443_ && this.isEntityBlock) {
/* 57 */       BlockEntity blockEntity = pLevel.m_7702_(pPos); if (blockEntity instanceof IPlacementReader) { IPlacementReader read = (IPlacementReader)blockEntity;
/* 58 */         read.readOnPlacement(pPlacer, pStack); }
/*    */     
/*    */     } 
/*    */   } public Supplier<BlockItem> blockItemSupplier() {
/*    */     return () -> new IPBlockItemBase(this, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab));
/*    */   } @Nullable
/*    */   public static <E extends BlockEntity & flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile, A extends BlockEntity> BlockEntityTicker<A> createCommonTicker(boolean isClient, BlockEntityType<A> actual, RegistryObject<BlockEntityType<E>> expected) {
/* 65 */     return createCommonTicker(isClient, actual, (BlockEntityType<BlockEntity>)expected.get());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static <E extends BlockEntity & flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile, A extends BlockEntity> BlockEntityTicker<A> createCommonTicker(boolean isClient, BlockEntityType<A> actual, BlockEntityType<E> expected) {
/* 70 */     if (isClient) {
/* 71 */       return createClientTicker(actual, expected);
/*    */     }
/* 73 */     return createServerTicker(actual, expected);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static <E extends BlockEntity & IPClientTickableTile, A extends BlockEntity> BlockEntityTicker<A> createClientTicker(BlockEntityType<A> actual, BlockEntityType<E> expected) {
/* 79 */     return createTickerHelper(actual, expected, IPClientTickableTile::makeTicker);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static <E extends BlockEntity & IPServerTickableTile, A extends BlockEntity> BlockEntityTicker<A> createServerTicker(BlockEntityType<A> actual, BlockEntityType<E> expected) {
/* 84 */     return createTickerHelper(actual, expected, IPServerTickableTile::makeTicker);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> actual, BlockEntityType<E> expected, Supplier<BlockEntityTicker<? super E>> ticker) {
/* 90 */     return (expected == actual) ? (BlockEntityTicker<A>)ticker.get() : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\IPBlockBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */