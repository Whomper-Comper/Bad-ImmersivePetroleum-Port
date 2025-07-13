/*     */ package flaxbeard.immersivepetroleum.common.blocks.metal;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlacementReader;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlayerInteraction;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.GasGeneratorTileEntity;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraft.world.level.material.MaterialColor;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class GasGeneratorBlock extends IPBlockBase implements EntityBlock {
/*  39 */   private static final Material material = new Material(MaterialColor.f_76404_, false, false, true, true, false, false, PushReaction.BLOCK);
/*     */   
/*  41 */   public static final DirectionProperty FACING = DirectionProperty.m_61546_("facing", (Predicate)Direction.Plane.HORIZONTAL);
/*     */   
/*     */   public GasGeneratorBlock() {
/*  44 */     super(BlockBehaviour.Properties.m_60939_(material).m_60913_(5.0F, 6.0F).m_60918_(SoundType.f_56743_).m_60999_().m_60955_());
/*     */     
/*  46 */     m_49959_((BlockState)((BlockState)m_49965_().m_61090_()).m_61124_((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/*  51 */     builder.m_61104_(new Property[] { (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7753_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  56 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float m_7749_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  61 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7420_(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  71 */   static final VoxelShape SHAPE = Shapes.m_83048_(1.0E-4D, 1.0E-4D, 1.0E-4D, 0.9999D, 0.9999D, 0.9999D);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape m_5940_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
/*  76 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape m_5939_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
/*  82 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6227_(@Nonnull BlockState state, Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
/*  88 */     BlockEntity te = worldIn.m_7702_(pos);
/*  89 */     if (te instanceof IPlayerInteraction) { IPlayerInteraction inst = (IPlayerInteraction)te;
/*  90 */       return inst.interact(hit.m_82434_(), player, handIn, player.m_21120_(handIn), (float)(hit.m_82450_()).f_82479_, (float)(hit.m_82450_()).f_82480_, (float)(hit.m_82450_()).f_82481_); }
/*     */     
/*  92 */     return InteractionResult.FAIL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6402_(Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
/*  97 */     if (!worldIn.f_46443_) {
/*  98 */       BlockEntity te = worldIn.m_7702_(pos);
/*  99 */       if (te instanceof IPlacementReader) { IPlacementReader read = (IPlacementReader)te;
/* 100 */         read.readOnPlacement(placer, stack); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState m_5573_(BlockPlaceContext context) {
/* 107 */     return (BlockState)m_49966_().m_61124_((Property)FACING, (Comparable)context.m_8125_().m_122424_());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
/* 112 */     GasGeneratorTileEntity te = (GasGeneratorTileEntity)((BlockEntityType)IPTileTypes.GENERATOR.get()).m_155264_(pPos, pState);
/* 113 */     te.setFacing((Direction)pState.m_61143_((Property)FACING));
/* 114 */     return (BlockEntity)te;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
/* 120 */     return createCommonTicker(level.f_46443_, type, IPTileTypes.GENERATOR);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\metal\GasGeneratorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */