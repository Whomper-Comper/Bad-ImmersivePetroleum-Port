/*     */ package flaxbeard.immersivepetroleum.common.fluids;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.StateHolder;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPFluidBlock
/*     */   extends LiquidBlock
/*     */ {
/*     */   private static IPFluid.IPFluidEntry staticEntry;
/*     */   protected final IPFluid.IPFluidEntry entry;
/*     */   @Nullable
/*     */   private MobEffect effect;
/*     */   private int duration;
/*     */   private int level;
/*     */   
/*     */   public IPFluidBlock(IPFluid.IPFluidEntry entry, BlockBehaviour.Properties props) {
/* 340 */     super((Supplier)entry.source(), (BlockBehaviour.Properties)Util.m_137469_(props, p -> staticEntry = entry));
/* 341 */     this.entry = entry;
/* 342 */     staticEntry = null;
/*     */   }
/*     */   
/*     */   public void setEffect(@Nullable MobEffect effect, int duration, int level) {
/* 346 */     this.effect = effect;
/* 347 */     this.duration = duration;
/* 348 */     this.level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/* 353 */     super.m_7926_(builder);
/* 354 */     for (Property<?> p : ((this.entry == null) ? staticEntry : this.entry).properties()) {
/* 355 */       builder.m_61104_(new Property[] { p });
/*     */     } 
/*     */   }
/*     */   
/*     */   public FluidState m_5888_(BlockState pState) {
/* 360 */     FluidState state = super.m_5888_(pState);
/* 361 */     for (Property<?> prop : (Iterable<Property<?>>)state.m_61147_()) {
/* 362 */       if (pState.m_61138_(prop))
/* 363 */         state = copyValue(prop, state, (StateHolder<?, ?>)pState); 
/*     */     } 
/* 365 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_7892_(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
/* 371 */     super.m_7892_(pState, pLevel, pPos, pEntity);
/* 372 */     if (this.effect != null && pEntity instanceof LivingEntity) { LivingEntity living = (LivingEntity)pEntity;
/* 373 */       living.m_7292_(new MobEffectInstance(this.effect, this.duration, this.level)); }
/*     */   
/*     */   }
/*     */   public static <S extends StateHolder<?, S>, P extends Comparable<P>> S copyValue(Property<P> prop, S oldState, StateHolder<?, ?> from) {
/* 377 */     return (S)oldState.m_61124_(prop, from.m_61143_(prop));
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\IPFluid$IPFluidBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */