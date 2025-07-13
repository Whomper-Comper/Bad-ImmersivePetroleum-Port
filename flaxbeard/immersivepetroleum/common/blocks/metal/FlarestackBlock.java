/*     */ package flaxbeard.immersivepetroleum.common.blocks.metal;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.util.ChatUtils;
/*     */ import blusunrize.immersiveengineering.common.util.Utils;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.FlarestackTileEntity;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
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
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ public class FlarestackBlock extends IPBlockBase implements EntityBlock {
/*  54 */   private static final Material material = new Material(MaterialColor.f_76404_, false, false, true, true, false, false, PushReaction.BLOCK);
/*     */   
/*  56 */   public static final BooleanProperty SLAVE = BooleanProperty.m_61465_("slave"); static VoxelShape SHAPE_SLAVE;
/*     */   
/*     */   public FlarestackBlock() {
/*  59 */     super(BlockBehaviour.Properties.m_60939_(material).m_60913_(3.0F, 15.0F).m_60918_(SoundType.f_56743_).m_60999_().m_60955_());
/*     */     
/*  61 */     m_49959_((BlockState)((BlockState)m_49965_().m_61090_()).m_61124_((Property)SLAVE, Boolean.valueOf(false)));
/*     */   }
/*     */   static VoxelShape SHAPE_MASTER;
/*     */   
/*     */   public Supplier<BlockItem> blockItemSupplier() {
/*  66 */     return () -> new FlarestackBlockItem((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/*  71 */     builder.m_61104_(new Property[] { (Property)SLAVE });
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7753_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  76 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7420_(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public float m_7749_(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos) {
/*  87 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6227_(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
/*  93 */     if (Utils.isScrewdriver(player.m_21120_(handIn))) {
/*  94 */       if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/*  95 */         pos = pos.m_121945_(Direction.DOWN);
/*     */       }
/*     */       
/*  98 */       if (!worldIn.f_46443_) {
/*  99 */         BlockEntity te = worldIn.m_7702_(pos);
/* 100 */         if (te instanceof FlarestackTileEntity) { FlarestackTileEntity flare = (FlarestackTileEntity)te;
/* 101 */           flare.invertRedstone();
/*     */           
/* 103 */           ChatUtils.sendServerNoSpamMessages(player, (Component)Component.m_237115_("chat.immersiveengineering.info.rsControl." + (flare.isRedstoneInverted() ? "invertedOn" : "invertedOff"))); }
/*     */       
/*     */       } 
/*     */       
/* 107 */       return InteractionResult.SUCCESS;
/*     */     } 
/* 109 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_5707_(@Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull Player player) {
/* 114 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/* 115 */       worldIn.m_46961_(pos.m_7918_(0, -1, 0), !player.m_7500_());
/*     */     } else {
/* 117 */       worldIn.m_46961_(pos.m_7918_(0, 1, 0), false);
/*     */     } 
/* 119 */     super.m_5707_(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6402_(Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
/* 124 */     if (!worldIn.f_46443_) {
/* 125 */       worldIn.m_46597_(pos.m_121945_(Direction.UP), (BlockState)state.m_61124_((Property)SLAVE, Boolean.valueOf(true)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7892_(BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
/* 131 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue() && !entityIn.m_5825_()) {
/* 132 */       entityIn.m_6469_(DamageSource.f_19309_, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<ItemStack> m_7381_(BlockState state, @Nonnull LootContext.Builder builder) {
/* 140 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue())
/*     */     {
/* 142 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 145 */     return super.m_7381_(state, builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape m_5940_(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
/* 154 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/* 155 */       if (SHAPE_SLAVE == null) {
/* 156 */         VoxelShape s0 = Shapes.m_83048_(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
/* 157 */         VoxelShape s1 = Shapes.m_83048_(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.375D, 0.9375D);
/* 158 */         SHAPE_SLAVE = Shapes.m_83113_(s0, s1, BooleanOp.f_82695_);
/*     */       } 
/*     */       
/* 161 */       return SHAPE_SLAVE;
/*     */     } 
/* 163 */     if (SHAPE_MASTER == null) {
/* 164 */       VoxelShape s0 = Shapes.m_83048_(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
/* 165 */       VoxelShape s1 = Shapes.m_83048_(0.0625D, 0.5D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
/* 166 */       SHAPE_MASTER = Shapes.m_83113_(s0, s1, BooleanOp.f_82695_);
/*     */     } 
/*     */     
/* 169 */     return SHAPE_MASTER;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockEntity m_142194_(@Nonnull BlockPos pPos, BlockState pState) {
/* 175 */     if (((Boolean)pState.m_61143_((Property)SLAVE)).booleanValue()) {
/* 176 */       return null;
/*     */     }
/* 178 */     return ((BlockEntityType)IPTileTypes.FLARE.get()).m_155264_(pPos, pState);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
/* 184 */     if (((Boolean)state.m_61143_((Property)SLAVE)).booleanValue()) {
/* 185 */       return null;
/*     */     }
/* 187 */     return createCommonTicker(level.f_46443_, type, IPTileTypes.FLARE);
/*     */   }
/*     */   
/*     */   public static class FlarestackBlockItem extends IPBlockItemBase {
/*     */     public FlarestackBlockItem(Block blockIn) {
/* 192 */       super(blockIn, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab));
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean m_40610_(@Nonnull BlockPlaceContext con, @Nonnull BlockState state) {
/* 197 */       if (super.m_40610_(con, state)) {
/* 198 */         BlockPos otherPos = con.m_8083_().m_121945_(Direction.UP);
/* 199 */         BlockState otherState = con.m_43725_().m_8055_(otherPos);
/*     */         
/* 201 */         return otherState.m_60795_();
/*     */       } 
/* 203 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\metal\FlarestackBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */