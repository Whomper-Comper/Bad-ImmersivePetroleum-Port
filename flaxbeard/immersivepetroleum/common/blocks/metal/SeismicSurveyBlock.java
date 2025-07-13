/*     */ package flaxbeard.immersivepetroleum.common.blocks.metal;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.SeismicSurveyTileEntity;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
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
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraft.world.level.material.MaterialColor;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ public class SeismicSurveyBlock
/*     */   extends IPBlockBase
/*     */   implements EntityBlock
/*     */ {
/*  51 */   private static final Material material = new Material(MaterialColor.f_76404_, false, false, true, true, false, false, PushReaction.BLOCK);
/*     */   
/*  53 */   public static final BooleanProperty SLAVE = BooleanProperty.m_61465_("slave");
/*     */   
/*     */   public SeismicSurveyBlock() {
/*  56 */     super(BlockBehaviour.Properties.m_60939_(material).m_60913_(5.0F, 6.0F).m_60918_(SoundType.f_56743_).m_60999_().m_60955_());
/*     */     
/*  58 */     m_49959_((BlockState)((BlockState)m_49965_().m_61090_())
/*  59 */         .m_61124_((Property)SLAVE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Supplier<BlockItem> blockItemSupplier() {
/*  64 */     return () -> new SeismicSurveyBlockItem((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/*  69 */     builder.m_61104_(new Property[] { (Property)SLAVE });
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7753_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  74 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7420_(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public float m_7749_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  85 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
/*  90 */     SeismicSurveyTileEntity te = (SeismicSurveyTileEntity)((BlockEntityType)IPTileTypes.SEISMIC_SURVEY.get()).m_155264_(pPos, pState);
/*  91 */     te.isSlave = ((Boolean)pState.m_61143_((Property)SLAVE)).booleanValue();
/*  92 */     return (BlockEntity)te;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
/*  98 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/*  99 */       return null;
/*     */     }
/* 101 */     return createCommonTicker(level.f_46443_, type, IPTileTypes.SEISMIC_SURVEY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_5707_(@Nonnull Level world, @Nonnull BlockPos pos, BlockState state, @Nonnull Player player) {
/* 106 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/*     */       
/* 108 */       for (int i = 1; i < 3; i++) {
/* 109 */         BlockPos p = pos.m_7918_(0, -i, 0);
/* 110 */         BlockState stateDown = world.m_8055_(p);
/*     */         
/* 112 */         if (!stateDown.m_60795_() && stateDown.m_60734_().equals(this) && !((Boolean)stateDown.m_61143_((Property)SLAVE)).booleanValue()) {
/* 113 */           world.m_46961_(p, !player.m_7500_());
/* 114 */           world.m_46961_(p.m_7918_(0, 1, 0), false);
/* 115 */           world.m_46961_(p.m_7918_(0, 2, 0), false);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 120 */       world.m_46961_(pos.m_7918_(0, 1, 0), false);
/* 121 */       world.m_46961_(pos.m_7918_(0, 2, 0), false);
/*     */     } 
/*     */     
/* 124 */     super.m_5707_(world, pos, state, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<ItemStack> m_7381_(BlockState state, @Nonnull LootContext.Builder builder) {
/* 131 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue())
/*     */     {
/* 133 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 136 */     return super.m_7381_(state, builder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6810_(BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pNewState, boolean pIsMoving) {
/* 141 */     if (pState.m_155947_() && (!pState.m_60713_(pNewState.m_60734_()) || !pNewState.m_155947_())) {
/* 142 */       if (!pLevel.f_46443_) { BlockEntity blockEntity = pLevel.m_7702_(pPos); if (blockEntity instanceof SeismicSurveyTileEntity) { SeismicSurveyTileEntity survey = (SeismicSurveyTileEntity)blockEntity; if (!survey.isSlave && 
/* 143 */             !survey.stack.m_41619_())
/* 144 */             Block.m_49840_(pLevel, pPos, survey.stack);  }
/*     */          }
/*     */       
/* 147 */       pLevel.m_46747_(pPos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6227_(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
/* 154 */     BlockEntity te = world.m_7702_(pos);
/* 155 */     if (te instanceof SeismicSurveyTileEntity) { SeismicSurveyTileEntity survey = (SeismicSurveyTileEntity)te;
/* 156 */       survey = survey.master();
/*     */       
/* 158 */       if (survey != null && survey.interact(state, world, survey.m_58899_(), player, hand)) {
/* 159 */         return InteractionResult.SUCCESS;
/*     */       } }
/*     */     
/* 162 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6402_(Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
/* 167 */     if (!worldIn.f_46443_) {
/* 168 */       worldIn.m_46597_(pos.m_7918_(0, 1, 0), (BlockState)state.m_61124_((Property)SLAVE, Boolean.valueOf(true)));
/* 169 */       worldIn.m_46597_(pos.m_7918_(0, 2, 0), (BlockState)state.m_61124_((Property)SLAVE, Boolean.valueOf(true)));
/*     */     } 
/*     */   }
/*     */   
/* 173 */   static final VoxelShape SHAPE_MASTER = Shapes.m_83048_(0.001D, 0.001D, 0.001D, 0.999D, 0.999D, 0.999D);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape m_5940_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
/* 178 */     return SHAPE_MASTER;
/*     */   }
/*     */   
/*     */   public static class SeismicSurveyBlockItem extends IPBlockItemBase {
/*     */     public SeismicSurveyBlockItem(Block blockIn) {
/* 183 */       super(blockIn, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab));
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean m_40610_(@Nonnull BlockPlaceContext con, @Nonnull BlockState state) {
/* 188 */       if (super.m_40610_(con, state)) {
/* 189 */         BlockPos posA = con.m_8083_().m_5484_(Direction.UP, 1);
/* 190 */         BlockState stateA = con.m_43725_().m_8055_(posA);
/* 191 */         if (stateA.m_60795_()) {
/* 192 */           BlockPos posB = con.m_8083_().m_5484_(Direction.UP, 2);
/* 193 */           BlockState stateB = con.m_43725_().m_8055_(posB);
/*     */           
/* 195 */           return stateB.m_60795_();
/*     */         } 
/*     */       } 
/* 198 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\metal\SeismicSurveyBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */