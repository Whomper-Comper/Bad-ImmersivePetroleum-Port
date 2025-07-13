/*     */ package flaxbeard.immersivepetroleum.common.entity;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.FireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MolotovItemEntity extends ThrowableItemProjectile {
/*     */   public MolotovItemEntity(Level world, LivingEntity living) {
/*  32 */     this((EntityType<MolotovItemEntity>)IPEntityTypes.MOLOTOV.get(), world, living);
/*     */   }
/*     */   
/*     */   public MolotovItemEntity(Level world, LivingEntity living, double x, double y, double z) {
/*  36 */     this((EntityType<MolotovItemEntity>)IPEntityTypes.MOLOTOV.get(), world, living);
/*  37 */     m_6034_(x, y, z);
/*  38 */     this.f_19854_ = x;
/*  39 */     this.f_19855_ = y;
/*  40 */     this.f_19856_ = z;
/*     */   }
/*     */   
/*     */   public MolotovItemEntity(EntityType<MolotovItemEntity> type, Level world) {
/*  44 */     super(type, world);
/*  45 */     this.f_19850_ = true;
/*     */   }
/*     */   
/*     */   public MolotovItemEntity(EntityType<MolotovItemEntity> type, Level world, LivingEntity living) {
/*  49 */     super(type, living, world);
/*  50 */     this.f_19850_ = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item m_7881_() {
/*  55 */     return (Item)IPContent.Items.MOLOTOV_LIT.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_8119_() {
/*  60 */     super.m_8119_();
/*     */     
/*  62 */     if (this.f_19853_.f_46443_) {
/*  63 */       double m = 0.125D;
/*  64 */       double x = m_20185_() + m - m * 2.0D * this.f_19796_.m_188500_();
/*  65 */       double y = m_20186_() + m - m * 2.0D * this.f_19796_.m_188500_();
/*  66 */       double z = m_20189_() + m - m * 2.0D * this.f_19796_.m_188500_();
/*  67 */       this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, x, y, z, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_6532_(HitResult pResult) {
/*  73 */     super.m_6532_(pResult);
/*  74 */     if (!this.f_19853_.f_46443_) {
/*  75 */       this.f_19853_.m_7605_((Entity)this, (byte)3);
/*  76 */       m_146870_();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_5790_(EntityHitResult pResult) {
/*  82 */     super.m_5790_(pResult);
/*     */     
/*  84 */     if (!this.f_19853_.f_46443_) {
/*  85 */       fire(new BlockPos(pResult.m_82450_()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_8060_(BlockHitResult hitResult) {
/*  91 */     super.m_8060_(hitResult);
/*     */     
/*  93 */     if (!this.f_19853_.f_46443_) {
/*  94 */       fire(hitResult.m_82425_().m_121945_(hitResult.m_82434_()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void fire(BlockPos pos) {
/*  99 */     if (this.f_19853_.m_8055_(pos).m_60767_().m_76332_()) {
/* 100 */       this.f_19853_.m_5594_(null, pos, SoundEvents.f_11937_, SoundSource.BLOCKS, 1.0F, this.f_19853_.m_213780_().m_188501_() * 0.4F + 0.8F);
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     this.f_19853_.m_5594_(null, pos, SoundEvents.f_11983_, SoundSource.BLOCKS, 0.3F, 0.7F);
/*     */     
/* 106 */     Set<BlockPos> hits = new HashSet<>();
/* 107 */     scanArea(pos, pos, hits, 9);
/* 108 */     if (hits.isEmpty())
/*     */       return; 
/* 110 */     hits.forEach(this::placeFire);
/*     */     
/* 112 */     this.f_19853_.m_5594_(null, pos, SoundEvents.f_11769_, SoundSource.NEUTRAL, 1.0F, 1.0F);
/*     */     
/* 114 */     AABB bounds = AABB.m_165882_(new Vec3(pos.m_123341_(), pos.m_123342_(), pos.m_123343_()), 6.5D, 6.5D, 6.5D);
/* 115 */     List<LivingEntity> list = this.f_19853_.m_45976_(LivingEntity.class, bounds);
/* 116 */     if (list.isEmpty()) {
/*     */       return;
/*     */     }
/* 119 */     Entity entity = m_37282_(); if (entity instanceof Player) { Player player = (Player)entity;
/* 120 */       DamageSource src = DamageSource.m_19344_(player);
/* 121 */       list.forEach(e -> e.m_6469_(src, 1.0F)); }
/*     */ 
/*     */     
/* 124 */     entity = m_37282_(); if (entity instanceof LivingEntity) { LivingEntity living = (LivingEntity)entity;
/* 125 */       living.m_21335_((Entity)list.get(list.size() - 1)); }
/*     */   
/*     */   }
/*     */   
/*     */   private void scanArea(BlockPos start, BlockPos pos, Set<BlockPos> visited, int radiusSqr) {
/* 130 */     BlockPos dif = pos.m_121996_((Vec3i)start);
/* 131 */     int sqr = dif.m_123341_() * dif.m_123341_() + dif.m_123342_() * dif.m_123342_() + dif.m_123343_() * dif.m_123343_();
/* 132 */     if (sqr > radiusSqr)
/*     */       return; 
/* 134 */     if (pos != start && !this.f_19853_.m_8055_(pos).m_60795_())
/*     */       return; 
/* 136 */     if (visited.contains(pos)) {
/*     */       return;
/*     */     }
/* 139 */     visited.add(pos);
/* 140 */     scanArea(start, pos.m_7494_(), visited, radiusSqr);
/* 141 */     scanArea(start, pos.m_7495_(), visited, radiusSqr);
/* 142 */     scanArea(start, pos.m_122012_(), visited, radiusSqr);
/* 143 */     scanArea(start, pos.m_122029_(), visited, radiusSqr);
/* 144 */     scanArea(start, pos.m_122019_(), visited, radiusSqr);
/* 145 */     scanArea(start, pos.m_122024_(), visited, radiusSqr);
/*     */   }
/*     */   
/*     */   private void placeFire(BlockPos pos) {
/* 149 */     if (!this.f_19853_.m_8055_(pos).m_60795_()) {
/*     */       return;
/*     */     }
/* 152 */     BlockState fire = Blocks.f_50083_.m_49966_();
/*     */     
/* 154 */     boolean up = false;
/* 155 */     boolean north = false;
/* 156 */     boolean east = false;
/* 157 */     boolean south = false;
/* 158 */     boolean west = false;
/* 159 */     if (this.f_19853_.m_8055_(pos.m_7495_()).m_60795_()) {
/* 160 */       BlockPos abovePos = pos.m_7494_();
/* 161 */       BlockPos northPos = pos.m_122012_();
/* 162 */       BlockPos eastPos = pos.m_122029_();
/* 163 */       BlockPos southPos = pos.m_122019_();
/* 164 */       BlockPos westPos = pos.m_122024_();
/*     */       
/* 166 */       up = this.f_19853_.m_8055_(abovePos).isFlammable((BlockGetter)this.f_19853_, abovePos, Direction.DOWN);
/* 167 */       north = this.f_19853_.m_8055_(northPos).isFlammable((BlockGetter)this.f_19853_, northPos, Direction.SOUTH);
/* 168 */       east = this.f_19853_.m_8055_(eastPos).isFlammable((BlockGetter)this.f_19853_, eastPos, Direction.WEST);
/* 169 */       south = this.f_19853_.m_8055_(southPos).isFlammable((BlockGetter)this.f_19853_, southPos, Direction.NORTH);
/* 170 */       west = this.f_19853_.m_8055_(westPos).isFlammable((BlockGetter)this.f_19853_, westPos, Direction.EAST);
/*     */       
/* 172 */       fire = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)fire.m_61124_((Property)FireBlock.f_53413_, Boolean.valueOf(up))).m_61124_((Property)FireBlock.f_53409_, Boolean.valueOf(north))).m_61124_((Property)FireBlock.f_53410_, Boolean.valueOf(east))).m_61124_((Property)FireBlock.f_53411_, Boolean.valueOf(south))).m_61124_((Property)FireBlock.f_53412_, Boolean.valueOf(west));
/*     */     } 
/*     */     
/* 175 */     this.f_19853_.m_7731_(pos, fire, 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\entity\MolotovItemEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */