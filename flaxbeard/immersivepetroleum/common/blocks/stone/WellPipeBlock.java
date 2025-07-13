/*     */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraft.world.level.material.MaterialColor;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class WellPipeBlock extends IPBlockBase implements EntityBlock {
/*  29 */   public static final BooleanProperty BROKEN = BooleanProperty.m_61465_("broken");
/*     */   
/*     */   public WellPipeBlock() {
/*  32 */     super(BlockBehaviour.Properties.m_60944_(Material.f_76278_, MaterialColor.f_76370_).m_60913_(75.0F, 10.0F).m_60918_(SoundType.f_56742_).m_60999_());
/*     */     
/*  34 */     m_49959_((BlockState)((BlockState)m_49965_().m_61090_())
/*  35 */         .m_61124_((Property)BROKEN, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/*  40 */     builder.m_61104_(new Property[] { (Property)BROKEN });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Supplier<BlockItem> blockItemSupplier() {
/*  46 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6807_(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public float m_5880_(BlockState state, @Nonnull Player player, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  62 */     float f = state.m_60800_(worldIn, pos);
/*     */     
/*  64 */     if (((Boolean)state.m_61143_((Property)BROKEN)).booleanValue()) {
/*  65 */       f /= 5.0F;
/*     */     }
/*     */     
/*  68 */     if (f == -1.0F) {
/*  69 */       return 0.0F;
/*     */     }
/*  71 */     int i = ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
/*  72 */     return player.getDigSpeed(state, pos) / f / i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6810_(BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
/*  78 */     if (state.m_155947_() && (!state.m_60713_(newState.m_60734_()) || !newState.m_155947_())) {
/*  79 */       removed(state, world, pos);
/*  80 */       world.m_46747_(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removed(BlockState state, Level world, BlockPos pos) {
/*  85 */     if (world.f_46443_ || ((Boolean)state.m_61143_((Property)BROKEN)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     BlockEntity te = world.m_7702_(pos);
/*  90 */     if (te instanceof flaxbeard.immersivepetroleum.common.blocks.tileentities.WellPipeTileEntity) {
/*  91 */       WellTileEntity well = null;
/*     */       
/*  93 */       for (int y = pos.m_123342_() - 1; y >= world.m_141937_(); y--) {
/*  94 */         BlockEntity teLow = world.m_7702_(new BlockPos(pos.m_123341_(), y, pos.m_123343_()));
/*     */         
/*  96 */         if (teLow instanceof WellTileEntity) { WellTileEntity w = (WellTileEntity)teLow;
/*  97 */           well = w;
/*     */           
/*     */           break; }
/*     */       
/*     */       } 
/* 102 */       if (well != null && !well.pastPhysicalPart) {
/* 103 */         well.phyiscalPipesList.remove(Integer.valueOf(pos.m_123342_()));
/* 104 */         if (well.wellPipeLength > 0) {
/* 105 */           well.wellPipeLength--;
/*     */         }
/* 107 */         well.m_6596_();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
/* 114 */     return ((BlockEntityType)IPTileTypes.WELL_PIPE.get()).m_155264_(pPos, pState);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\WellPipeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */