/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.ImmersiveEngineering;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.FlarestackHandler;
/*     */ import flaxbeard.immersivepetroleum.client.particle.IPParticleTypes;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import flaxbeard.immersivepetroleum.common.util.sounds.IPSounds;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ 
/*     */ public class FlarestackTileEntity extends IPTileEntityBase implements IPCommonTickableTile, IEBlockInterfaces.ISoundBE {
/*  37 */   static final DamageSource FLARESTACK = (new DamageSource("ipFlarestack")).m_19380_().m_19383_();
/*     */   protected boolean isRedstoneInverted;
/*     */   protected boolean isActive;
/*     */   protected short drained;
/*     */   protected final FluidTank tank;
/*     */   private LazyOptional<IFluidHandler> inputHandler;
/*     */   
/*     */   public FlarestackTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
/*  45 */     super((BlockEntityType)IPTileTypes.FLARE.get(), pWorldPosition, pBlockState);
/*     */     this.tank = new FluidTank(250, fstack -> (fstack != FluidStack.EMPTY && FlarestackHandler.isBurnable(fstack)));
/*     */   }
/*     */   public void invertRedstone() {
/*  49 */     this.isRedstoneInverted = !this.isRedstoneInverted;
/*  50 */     m_6596_();
/*     */   }
/*     */   
/*     */   public boolean isRedstoneInverted() {
/*  54 */     return this.isRedstoneInverted;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  58 */     return this.isActive;
/*     */   }
/*     */   
/*     */   public short getFlow() {
/*  62 */     return this.drained;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readCustom(CompoundTag nbt) {
/*  67 */     this.isRedstoneInverted = nbt.m_128471_("inverted");
/*  68 */     this.isActive = nbt.m_128471_("active");
/*  69 */     this.drained = nbt.m_128448_("drained");
/*  70 */     this.tank.readFromNBT(nbt.m_128469_("tank"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCustom(CompoundTag nbt) {
/*  75 */     nbt.m_128379_("inverted", this.isRedstoneInverted);
/*  76 */     nbt.m_128379_("active", this.isActive);
/*  77 */     nbt.m_128376_("drained", this.drained);
/*     */     
/*  79 */     CompoundTag tank = this.tank.writeToNBT(new CompoundTag());
/*  80 */     nbt.m_128365_("tank", (Tag)tank);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
/*  88 */     if (cap == ForgeCapabilities.FLUID_HANDLER && (
/*  89 */       side == null || side == Direction.DOWN)) {
/*  90 */       BlockEntity te = this.f_58857_.m_7702_(m_58899_());
/*  91 */       if (te instanceof FlarestackTileEntity) { FlarestackTileEntity flare = (FlarestackTileEntity)te;
/*  92 */         if (this.inputHandler == null) {
/*  93 */           this.inputHandler = LazyOptional.of(() -> flare.tank);
/*     */         } }
/*     */       else
/*  96 */       { return LazyOptional.empty(); }
/*     */ 
/*     */       
/*  99 */       return this.inputHandler.cast();
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return super.getCapability(cap, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7651_() {
/* 108 */     super.m_7651_();
/* 109 */     if (this.inputHandler != null) {
/* 110 */       this.inputHandler.invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateCaps() {
/* 116 */     super.invalidateCaps();
/* 117 */     if (this.inputHandler != null) {
/* 118 */       this.inputHandler.invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6596_() {
/* 124 */     super.m_6596_();
/*     */     
/* 126 */     BlockState state = this.f_58857_.m_8055_(this.f_58858_);
/* 127 */     this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
/* 128 */     this.f_58857_.m_46672_(this.f_58858_, state.m_60734_());
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public AABB getRenderBoundingBox() {
/* 134 */     BlockPos pos = m_58899_();
/* 135 */     return new AABB(pos.m_7918_(-1, -1, -1), pos.m_7918_(1, 2, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickClient() {
/* 140 */     ImmersiveEngineering.proxy.handleTileSound((Supplier)IPSounds.FLARESTACK, this, this.isActive, 1.0F, 0.75F);
/* 141 */     if (this.isActive) {
/* 142 */       if (this.f_58857_.m_46467_() % 2L == 0L) {
/* 143 */         float xPos = this.f_58858_.m_123341_() + 0.5F + (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.4375F;
/* 144 */         float zPos = this.f_58858_.m_123343_() + 0.5F + (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.4375F;
/* 145 */         float yPos = this.f_58858_.m_123342_() + 1.875F + 0.2F * this.f_58857_.f_46441_.m_188501_();
/*     */         
/* 147 */         this.f_58857_.m_7106_((ParticleOptions)IPParticleTypes.FLARE_FIRE.get(), xPos, yPos, zPos, 0.0D, (0.0625F + this.drained / this.tank.getCapacity() * 0.125F), 0.0D);
/*     */       }
/*     */     
/* 150 */     } else if (this.f_58857_.m_46467_() % 5L == 0L) {
/* 151 */       float xPos = this.f_58858_.m_123341_() + 0.5F + (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.4375F;
/* 152 */       float zPos = this.f_58858_.m_123343_() + 0.5F + (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.4375F;
/* 153 */       float yPos = this.f_58858_.m_123342_() + 1.6F;
/* 154 */       float xa = (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.00625F;
/* 155 */       float ya = (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 0.00625F;
/*     */       
/* 157 */       this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, xPos, yPos, zPos, xa, 0.02500000037252903D, ya);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickServer() {
/* 163 */     boolean lastActive = this.isActive;
/* 164 */     this.isActive = false;
/*     */     
/* 166 */     int redstone = this.f_58857_.m_46755_(this.f_58858_);
/* 167 */     if (isRedstoneInverted()) {
/* 168 */       redstone = 15 - redstone;
/*     */     }
/*     */     
/* 171 */     if (redstone > 0 && this.tank.getFluidAmount() > 0) {
/* 172 */       float signal = redstone / 15.0F;
/* 173 */       FluidStack fs = this.tank.drain((int)(this.tank.getCapacity() * signal), IFluidHandler.FluidAction.SIMULATE);
/* 174 */       if (fs.getAmount() > 0) {
/* 175 */         this.tank.drain(fs.getAmount(), IFluidHandler.FluidAction.EXECUTE);
/* 176 */         this.drained = (short)fs.getAmount();
/* 177 */         this.isActive = true;
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     if (this.isActive && this.f_58857_.m_46467_() % 10L == 0L) {
/*     */       
/* 183 */       List<Entity> list = m_58904_().m_45976_(Entity.class, (new AABB(this.f_58858_)).m_82400_(1.0D));
/* 184 */       if (!list.isEmpty()) {
/* 185 */         list.forEach(e -> {
/*     */               if (!e.m_5825_()) {
/*     */                 e.m_20254_(15);
/*     */                 
/*     */                 e.m_6469_(FLARESTACK, 6.0F * this.drained / this.tank.getCapacity());
/*     */               } 
/*     */             });
/* 192 */         List<Entity> goats = list.stream().filter(e -> e instanceof net.minecraft.world.entity.animal.goat.Goat).toList();
/* 193 */         if (!goats.isEmpty()) {
/* 194 */           List<Player> players = m_58904_().m_45976_(Player.class, (new AABB(this.f_58858_)).m_82400_(8.0D));
/* 195 */           for (Entity g : goats) {
/* 196 */             if (!g.m_6084_()) {
/* 197 */               players.forEach(p -> Utils.unlockIPAdvancement(p, "main/flarestack"));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 205 */     if (lastActive != this.isActive || (!this.f_58857_.f_46443_ && this.isActive)) {
/* 206 */       m_6596_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPlaySound(@Nonnull String sound) {
/* 212 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\FlarestackTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */