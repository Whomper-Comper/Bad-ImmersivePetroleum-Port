/*     */ package flaxbeard.immersivepetroleum.common.blocks.wooden;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraft.world.level.material.MaterialColor;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ public class AutoLubricatorBlock extends IPBlockBase implements EntityBlock {
/*  43 */   private static final Material material = new Material(MaterialColor.f_76411_, false, false, true, true, false, false, PushReaction.BLOCK);
/*     */   
/*  45 */   public static final DirectionProperty FACING = DirectionProperty.m_61546_("facing", (Predicate)Direction.Plane.HORIZONTAL);
/*  46 */   public static final BooleanProperty SLAVE = BooleanProperty.m_61465_("slave");
/*     */   
/*     */   public AutoLubricatorBlock() {
/*  49 */     super(BlockBehaviour.Properties.m_60939_(material).m_60913_(5.0F, 6.0F).m_60918_(SoundType.f_56736_).m_60999_().m_60955_());
/*     */     
/*  51 */     m_49959_((BlockState)((BlockState)((BlockState)m_49965_().m_61090_())
/*  52 */         .m_61124_((Property)FACING, (Comparable)Direction.NORTH))
/*  53 */         .m_61124_((Property)SLAVE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Supplier<BlockItem> blockItemSupplier() {
/*  58 */     return () -> new AutoLubricatorBlockItem((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/*  63 */     builder.m_61104_(new Property[] { (Property)FACING, (Property)SLAVE });
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7753_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7420_(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public float m_7749_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  79 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState m_5573_(BlockPlaceContext context) {
/*  84 */     return (BlockState)m_49966_().m_61124_((Property)FACING, (Comparable)context.m_8125_());
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6402_(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
/*  89 */     if (!pLevel.f_46443_) {
/*  90 */       pLevel.m_46597_(pPos.m_7918_(0, 1, 0), (BlockState)pState.m_61124_((Property)SLAVE, Boolean.valueOf(true)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
/*  96 */     AutoLubricatorTileEntity te = (AutoLubricatorTileEntity)((BlockEntityType)IPTileTypes.AUTOLUBE.get()).m_155264_(pPos, pState);
/*  97 */     te.isSlave = ((Boolean)pState.m_61143_((Property)SLAVE)).booleanValue();
/*  98 */     te.facing = (Direction)pState.m_61143_((Property)FACING);
/*  99 */     return (BlockEntity)te;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
/* 105 */     return createCommonTicker(level.f_46443_, type, IPTileTypes.AUTOLUBE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_5707_(@Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull Player player) {
/* 110 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/* 111 */       worldIn.m_46961_(pos.m_7918_(0, -1, 0), !player.m_7500_());
/*     */     } else {
/* 113 */       worldIn.m_46961_(pos.m_7918_(0, 1, 0), false);
/*     */     } 
/*     */     
/* 116 */     super.m_5707_(worldIn, pos, state, player);
/*     */   }
/*     */   
/* 119 */   static final VoxelShape SHAPE_SLAVE = Shapes.m_83048_(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
/* 120 */   static final VoxelShape SHAPE_MASTER = Shapes.m_83048_(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape m_5940_(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
/* 125 */     return ((Boolean)state.m_61143_((Property)SLAVE)).booleanValue() ? SHAPE_SLAVE : SHAPE_MASTER;
/*     */   }
/*     */   
/*     */   public static class AutoLubricatorBlockItem extends IPBlockItemBase {
/*     */     public AutoLubricatorBlockItem(Block blockIn) {
/* 130 */       super(blockIn, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab));
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean m_40610_(@Nonnull BlockPlaceContext con, @Nonnull BlockState state) {
/* 135 */       if (super.m_40610_(con, state)) {
/* 136 */         BlockPos otherPos = con.m_8083_().m_121945_(Direction.UP);
/* 137 */         BlockState otherState = con.m_43725_().m_8055_(otherPos);
/*     */         
/* 139 */         return otherState.m_60795_();
/*     */       } 
/* 141 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\wooden\AutoLubricatorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */