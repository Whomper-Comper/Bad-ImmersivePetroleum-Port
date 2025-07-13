/*     */ package flaxbeard.immersivepetroleum.common.entity;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.util.IESounds;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.items.DebugItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.GasolineBottleItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.MotorboatItem;
/*     */ import flaxbeard.immersivepetroleum.common.network.IPPacketHandler;
/*     */ import flaxbeard.immersivepetroleum.common.network.MessageConsumeBoatFuel;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.entity.vehicle.DismountHelper;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CollisionGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.entity.IEntityAdditionalSpawnData;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.network.NetworkHooks;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ public class MotorboatEntity
/*     */   extends Boat
/*     */   implements IEntityAdditionalSpawnData
/*     */ {
/*     */   public static EntityDataAccessor<Byte> getFlags() {
/*  80 */     return f_19805_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   static final EntityDataAccessor<String> TANK_FLUID = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135030_);
/*  87 */   static final EntityDataAccessor<Integer> TANK_AMOUNT = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135028_);
/*     */   
/*  89 */   static final EntityDataAccessor<ItemStack> UPGRADE_0 = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135033_);
/*  90 */   static final EntityDataAccessor<ItemStack> UPGRADE_1 = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135033_);
/*  91 */   static final EntityDataAccessor<ItemStack> UPGRADE_2 = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135033_);
/*  92 */   static final EntityDataAccessor<ItemStack> UPGRADE_3 = SynchedEntityData.m_135353_(MotorboatEntity.class, EntityDataSerializers.f_135033_);
/*     */   
/*     */   public boolean isFireproof = false;
/*     */   
/*     */   public boolean hasIcebreaker = false;
/*     */   public boolean hasTank = false;
/*     */   public boolean hasRudders = false;
/*     */   public boolean hasPaddles = false;
/*     */   public boolean isBoosting = false;
/*     */   public float lastMoving;
/* 102 */   public float propellerYRotation = 0.0F;
/* 103 */   public float propellerXRot = 0.0F;
/* 104 */   public float propellerXRotSpeed = 0.0F; protected float oYRot;
/*     */   
/*     */   public MotorboatEntity(Level world) {
/* 107 */     this((EntityType<MotorboatEntity>)IPEntityTypes.MOTORBOAT.get(), world);
/*     */   }
/*     */   protected boolean fastEnough; protected int oFuelAmount;
/*     */   public MotorboatEntity(Level world, double x, double y, double z) {
/* 111 */     this((EntityType<MotorboatEntity>)IPEntityTypes.MOTORBOAT.get(), world);
/* 112 */     m_6034_(x, y, z);
/* 113 */     this.f_19854_ = x;
/* 114 */     this.f_19855_ = y;
/* 115 */     this.f_19856_ = z;
/*     */   }
/*     */   
/*     */   public MotorboatEntity(EntityType<MotorboatEntity> type, Level world) {
/* 119 */     super(type, world);
/* 120 */     this.f_19850_ = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_8097_() {
/* 125 */     super.m_8097_();
/* 126 */     this.f_19804_.m_135372_(TANK_FLUID, "");
/* 127 */     this.f_19804_.m_135372_(TANK_AMOUNT, Integer.valueOf(0));
/* 128 */     this.f_19804_.m_135372_(UPGRADE_0, ItemStack.f_41583_);
/* 129 */     this.f_19804_.m_135372_(UPGRADE_1, ItemStack.f_41583_);
/* 130 */     this.f_19804_.m_135372_(UPGRADE_2, ItemStack.f_41583_);
/* 131 */     this.f_19804_.m_135372_(UPGRADE_3, ItemStack.f_41583_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7378_(@Nonnull CompoundTag compound) {
/* 136 */     super.m_7378_(compound);
/*     */     
/* 138 */     String fluid = "";
/* 139 */     int amount = 0;
/* 140 */     ItemStack stack0 = ItemStack.f_41583_;
/* 141 */     ItemStack stack1 = ItemStack.f_41583_;
/* 142 */     ItemStack stack2 = ItemStack.f_41583_;
/* 143 */     ItemStack stack3 = ItemStack.f_41583_;
/*     */     
/* 145 */     if (compound.m_128441_("tank")) {
/* 146 */       CompoundTag tank = compound.m_128469_("tank");
/* 147 */       fluid = tank.m_128461_("fluid");
/* 148 */       amount = tank.m_128451_("amount");
/*     */     } 
/*     */     
/* 151 */     if (compound.m_128441_("upgrades")) {
/* 152 */       CompoundTag upgrades = compound.m_128469_("upgrades");
/* 153 */       stack0 = ItemStack.m_41712_(upgrades.m_128469_("0"));
/* 154 */       stack1 = ItemStack.m_41712_(upgrades.m_128469_("1"));
/* 155 */       stack2 = ItemStack.m_41712_(upgrades.m_128469_("2"));
/* 156 */       stack3 = ItemStack.m_41712_(upgrades.m_128469_("3"));
/*     */     } 
/*     */     
/* 159 */     this.f_19804_.m_135381_(TANK_FLUID, fluid);
/* 160 */     this.f_19804_.m_135381_(TANK_AMOUNT, Integer.valueOf(amount));
/* 161 */     this.f_19804_.m_135381_(UPGRADE_0, stack0);
/* 162 */     this.f_19804_.m_135381_(UPGRADE_1, stack1);
/* 163 */     this.f_19804_.m_135381_(UPGRADE_2, stack2);
/* 164 */     this.f_19804_.m_135381_(UPGRADE_3, stack3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7380_(@Nonnull CompoundTag compound) {
/* 169 */     super.m_7380_(compound);
/*     */     
/* 171 */     String fluid = (String)this.f_19804_.m_135370_(TANK_FLUID);
/* 172 */     int amount = ((Integer)this.f_19804_.m_135370_(TANK_AMOUNT)).intValue();
/* 173 */     ItemStack stack0 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_0);
/* 174 */     ItemStack stack1 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_1);
/* 175 */     ItemStack stack2 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_2);
/* 176 */     ItemStack stack3 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_3);
/*     */     
/* 178 */     CompoundTag tank = new CompoundTag();
/* 179 */     tank.m_128359_("fluid", fluid);
/* 180 */     tank.m_128405_("amount", amount);
/* 181 */     compound.m_128365_("tank", (Tag)tank);
/*     */     
/* 183 */     CompoundTag upgrades = new CompoundTag();
/* 184 */     upgrades.m_128365_("0", (Tag)stack0.serializeNBT());
/* 185 */     upgrades.m_128365_("1", (Tag)stack1.serializeNBT());
/* 186 */     upgrades.m_128365_("2", (Tag)stack2.serializeNBT());
/* 187 */     upgrades.m_128365_("3", (Tag)stack3.serializeNBT());
/* 188 */     compound.m_128365_("upgrades", (Tag)upgrades);
/*     */   }
/*     */   
/*     */   public void setUpgrades(NonNullList<ItemStack> stacks) {
/* 192 */     if (stacks != null && stacks.size() > 0) {
/* 193 */       ItemStack o0 = (ItemStack)stacks.get(0);
/* 194 */       ItemStack o1 = (ItemStack)stacks.get(1);
/* 195 */       ItemStack o2 = (ItemStack)stacks.get(2);
/* 196 */       ItemStack o3 = (ItemStack)stacks.get(3);
/* 197 */       this.f_19804_.m_135381_(UPGRADE_0, o0);
/* 198 */       this.f_19804_.m_135381_(UPGRADE_1, o1);
/* 199 */       this.f_19804_.m_135381_(UPGRADE_2, o2);
/* 200 */       this.f_19804_.m_135381_(UPGRADE_3, o3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isLeftDown() {
/* 205 */     return this.f_38273_;
/*     */   }
/*     */   
/*     */   public boolean isRightDown() {
/* 209 */     return this.f_38274_;
/*     */   }
/*     */   
/*     */   public boolean isForwardDown() {
/* 213 */     return this.f_38275_;
/*     */   }
/*     */   
/*     */   public boolean isBackDown() {
/* 217 */     return this.f_38276_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7350_(@Nonnull EntityDataAccessor<?> key) {
/* 222 */     super.m_7350_(key);
/* 223 */     if (key == UPGRADE_0 || key == UPGRADE_1 || key == UPGRADE_2 || key == UPGRADE_3) {
/* 224 */       NonNullList<ItemStack> upgrades = getUpgrades();
/* 225 */       this.isFireproof = false;
/* 226 */       this.hasIcebreaker = false;
/* 227 */       for (ItemStack upgrade : upgrades) {
/* 228 */         if (upgrade != null && upgrade != ItemStack.f_41583_) {
/* 229 */           Item item = upgrade.m_41720_();
/* 230 */           if (item == IPContent.BoatUpgrades.REINFORCED_HULL.get()) {
/* 231 */             this.isFireproof = true; continue;
/* 232 */           }  if (item == IPContent.BoatUpgrades.ICE_BREAKER.get()) {
/* 233 */             this.hasIcebreaker = true; continue;
/* 234 */           }  if (item == IPContent.BoatUpgrades.TANK.get()) {
/* 235 */             this.hasTank = true; continue;
/* 236 */           }  if (item == IPContent.BoatUpgrades.RUDDERS.get()) {
/* 237 */             this.hasRudders = true; continue;
/* 238 */           }  if (item == IPContent.BoatUpgrades.PADDLES.get()) {
/* 239 */             this.hasPaddles = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setContainedFluid(FluidStack stack) {
/* 247 */     if (stack == null) {
/* 248 */       this.f_19804_.m_135381_(TANK_FLUID, "");
/* 249 */       this.f_19804_.m_135381_(TANK_AMOUNT, Integer.valueOf(0));
/*     */     } else {
/* 251 */       this.f_19804_.m_135381_(TANK_FLUID, (stack.getFluid() == null) ? "" : RegistryUtils.getRegistryNameOf(stack.getFluid()).toString());
/* 252 */       this.f_19804_.m_135381_(TANK_AMOUNT, Integer.valueOf(stack.getAmount()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public FluidStack getContainedFluid() {
/* 257 */     String fluidName = (String)this.f_19804_.m_135370_(TANK_FLUID);
/* 258 */     int amount = ((Integer)this.f_19804_.m_135370_(TANK_AMOUNT)).intValue();
/*     */     
/* 260 */     if (fluidName == null || fluidName.isEmpty() || amount == 0) {
/* 261 */       return FluidStack.EMPTY;
/*     */     }
/* 263 */     Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
/* 264 */     if (fluid == null) {
/* 265 */       return FluidStack.EMPTY;
/*     */     }
/* 267 */     return new FluidStack(fluid, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public double m_6048_() {
/* 272 */     return 0.05D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vec3 m_7688_(LivingEntity pLivingEntity) {
/* 278 */     Vec3 vec3 = m_19903_((m_20205_() * Mth.f_13994_), pLivingEntity.m_20205_(), pLivingEntity.m_146908_());
/* 279 */     double d0 = m_20185_() + vec3.f_82479_;
/* 280 */     double d1 = m_20189_() + vec3.f_82481_;
/* 281 */     BlockPos blockpos = new BlockPos(d0, (m_20191_()).f_82292_, d1);
/* 282 */     BlockPos blockpos1 = blockpos.m_7495_();
/* 283 */     if (!this.f_19853_.m_46801_(blockpos1) && !this.f_19853_.m_6425_(blockpos1).m_205070_(FluidTags.f_13132_)) {
/* 284 */       List<Vec3> list = Lists.newArrayList();
/* 285 */       double d2 = this.f_19853_.m_45573_(blockpos);
/* 286 */       if (DismountHelper.m_38439_(d2)) {
/* 287 */         list.add(new Vec3(d0, blockpos.m_123342_() + d2, d1));
/*     */       }
/*     */       
/* 290 */       double d3 = this.f_19853_.m_45573_(blockpos1);
/* 291 */       if (DismountHelper.m_38439_(d3)) {
/* 292 */         list.add(new Vec3(d0, blockpos1.m_123342_() + d3, d1));
/*     */       }
/*     */       
/* 295 */       for (UnmodifiableIterator<Pose> unmodifiableIterator = pLivingEntity.m_7431_().iterator(); unmodifiableIterator.hasNext(); ) { Pose pose = unmodifiableIterator.next();
/* 296 */         for (Vec3 vec31 : list) {
/* 297 */           if (DismountHelper.m_150279_((CollisionGetter)this.f_19853_, vec31, pLivingEntity, pose)) {
/* 298 */             pLivingEntity.m_20124_(pose);
/* 299 */             return vec31;
/*     */           } 
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 305 */     return new Vec3(m_20185_(), (m_20191_()).f_82292_, m_20189_());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_6469_(@Nonnull DamageSource source, float amount) {
/* 310 */     if (m_6673_(source) || (this.isFireproof && source.m_19384_()))
/* 311 */       return false; 
/* 312 */     if (!this.f_19853_.f_46443_ && m_6084_()) {
/* 313 */       if (source instanceof net.minecraft.world.damagesource.IndirectEntityDamageSource && source.m_7640_() != null && m_20363_(source.m_7640_())) {
/* 314 */         return false;
/*     */       }
/* 316 */       m_38362_(-m_38386_());
/* 317 */       m_38354_(10);
/* 318 */       m_38311_(m_38384_() + amount * 10.0F);
/* 319 */       m_5834_();
/* 320 */       boolean isPlayer = source.m_7640_() instanceof Player;
/* 321 */       boolean isCreativePlayer = (isPlayer && (((Player)source.m_7640_()).m_150110_()).f_35937_);
/* 322 */       if (((isCreativePlayer || m_38384_() > 40.0F) && (!this.isFireproof || isPlayer)) || m_38384_() > 240.0F) {
/* 323 */         if (!isCreativePlayer && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
/* 324 */           MotorboatItem item = (MotorboatItem)m_38369_();
/* 325 */           ItemStack stack = new ItemStack((ItemLike)item, 1);
/*     */           
/* 327 */           IItemHandler handler = (IItemHandler)stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).orElse(null);
/* 328 */           if (handler != null && handler instanceof flaxbeard.immersivepetroleum.common.util.IPItemStackHandler) {
/* 329 */             NonNullList<ItemStack> upgrades = getUpgrades();
/* 330 */             for (int i = 0; i < handler.getSlots(); i++) {
/* 331 */               handler.insertItem(i, (ItemStack)upgrades.get(i), false);
/*     */             }
/*     */           } 
/*     */           
/* 335 */           writeTank(stack.m_41784_(), true);
/*     */           
/* 337 */           if (isPlayer) {
/* 338 */             Player player = (Player)source.m_7640_();
/* 339 */             if (!player.m_36356_(stack)) {
/* 340 */               ItemEntity itemEntity = new ItemEntity(this.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), stack);
/* 341 */               itemEntity.m_32061_();
/* 342 */               this.f_19853_.m_7967_((Entity)itemEntity);
/*     */             } 
/*     */           } else {
/* 345 */             m_5552_(stack, 0.0F);
/*     */           } 
/*     */         } 
/*     */         
/* 349 */         m_142687_(Entity.RemovalReason.DISCARDED);
/*     */       } 
/*     */       
/* 352 */       return true;
/*     */     } 
/*     */     
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readTank(CompoundTag nbt) {
/* 360 */     FluidTank tank = new FluidTank(getMaxFuel());
/* 361 */     if (nbt != null) {
/* 362 */       tank.readFromNBT(nbt.m_128469_("tank"));
/*     */     }
/* 364 */     setContainedFluid(tank.getFluid());
/*     */   }
/*     */   
/*     */   public void writeTank(CompoundTag nbt, boolean toItem) {
/* 368 */     FluidTank tank = new FluidTank(getMaxFuel());
/* 369 */     tank.setFluid(getContainedFluid());
/*     */     
/* 371 */     boolean write = (tank.getFluidAmount() > 0);
/* 372 */     if (!toItem || write) {
/* 373 */       nbt.m_128365_("tank", (Tag)tank.writeToNBT(new CompoundTag()));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6096_(Player player, @Nonnull InteractionHand hand) {
/* 379 */     ItemStack stack = player.m_21120_(hand);
/*     */     
/* 381 */     if (stack != ItemStack.f_41583_) { Item item1 = stack.m_41720_(); if (item1 instanceof DebugItem) { DebugItem debugItem = (DebugItem)item1;
/* 382 */         debugItem.onSpeedboatClick(this, player, stack);
/* 383 */         return InteractionResult.SUCCESS; }
/*     */        }
/*     */     
/* 386 */     if (Utils.isFluidRelatedItemStack(stack)) {
/* 387 */       FluidStack fstack = FluidUtil.getFluidContained(stack).orElse(null);
/* 388 */       if (fstack != null) {
/* 389 */         FluidTank tank = getInternalTank();
/*     */         
/* 391 */         if (FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)tank)) {
/* 392 */           setContainedFluid(tank.getFluid());
/* 393 */           advancement(tank, fstack, player);
/*     */         } 
/*     */       } 
/* 396 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 399 */     Item item = stack.m_41720_(); if (item instanceof GasolineBottleItem) { GasolineBottleItem gasbottle = (GasolineBottleItem)item;
/* 400 */       FluidTank tank = getInternalTank();
/* 401 */       FluidStack fstack = new FluidStack(IPContent.Fluids.GASOLINE.get(), 250);
/*     */       
/* 403 */       if (tank.fill(fstack, IFluidHandler.FluidAction.SIMULATE) >= 250) {
/* 404 */         tank.fill(fstack, IFluidHandler.FluidAction.EXECUTE);
/*     */         
/* 406 */         gasbottle.toEmptyBottle(player, stack);
/*     */         
/* 408 */         setContainedFluid(tank.getFluid());
/* 409 */         advancement(tank, fstack, player);
/*     */       } 
/*     */       
/* 412 */       return InteractionResult.SUCCESS; }
/*     */ 
/*     */     
/* 415 */     if (!this.f_19853_.f_46443_ && !player.m_6144_() && this.f_38265_ < 60.0F && !player.m_20365_((Entity)this)) {
/* 416 */       player.m_20329_((Entity)this);
/* 417 */       if (this.f_19853_.m_46472_().equals(Level.f_46429_) && this.isFireproof) {
/* 418 */         Utils.unlockIPAdvancement(player, "main/reinforced_hull");
/*     */       }
/* 420 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 423 */     return InteractionResult.FAIL;
/*     */   }
/*     */   
/*     */   private FluidTank getInternalTank() {
/* 427 */     FluidTank tank = new FluidTank(getMaxFuel(), e -> FuelHandler.isValidBoatFuel(e.getFluid()));
/* 428 */     FluidStack fs = getContainedFluid();
/* 429 */     tank.setFluid(fs);
/* 430 */     return tank;
/*     */   }
/*     */   
/*     */   private void advancement(FluidTank tank, FluidStack fstack, Player player) {
/* 434 */     if (tank.isFluidValid(fstack)) {
/* 435 */       Utils.unlockIPAdvancement(player, "main/motorboat");
/* 436 */       if (this.hasTank && tank.getFluidAmount() == tank.getCapacity()) {
/* 437 */         Utils.unlockIPAdvancement(player, "main/tank");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_38342_(boolean pLeftInputDown, boolean pRightInputDown, boolean pForwardInputDown, boolean pBackInputDown) {
/* 444 */     super.m_38342_(pLeftInputDown, pRightInputDown, pForwardInputDown, pBackInputDown);
/* 445 */     this.isBoosting = (!isEmergency() && pForwardInputDown && (Minecraft.m_91087_()).f_91066_.f_92089_.m_90857_());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpinningFastEnough() {
/* 455 */     return this.fastEnough;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_8119_() {
/* 463 */     if (!this.f_19853_.f_46443_) {
/*     */ 
/*     */       
/* 466 */       float diff = m_146908_() - this.oYRot;
/* 467 */       this.fastEnough = (diff <= -5.0F || diff >= 5.0F);
/* 468 */       this.oYRot = m_146908_();
/*     */ 
/*     */ 
/*     */       
/* 472 */       int current = ((Integer)this.f_19804_.m_135370_(TANK_AMOUNT)).intValue();
/* 473 */       int i = current - this.oFuelAmount;
/* 474 */       if (i != 0 && current == 0) {
/* 475 */         Entity entity = m_146895_(); if (entity instanceof Player) { Player player = (Player)entity; if (this.hasPaddles)
/* 476 */             Utils.unlockIPAdvancement(player, "main/paddles");  }
/*     */       
/*     */       } 
/* 479 */       this.oFuelAmount = current;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     this.f_38280_ = this.f_38279_;
/* 486 */     this.f_38279_ = m_38392_();
/* 487 */     if (this.f_38279_ != Boat.Status.UNDER_WATER && this.f_38279_ != Boat.Status.UNDER_FLOWING_WATER) {
/* 488 */       this.f_38265_ = 0.0F;
/*     */     } else {
/* 490 */       this.f_38265_++;
/*     */     } 
/*     */     
/* 493 */     if (!this.f_19853_.f_46443_ && this.f_38265_ >= 60.0F) {
/* 494 */       m_20153_();
/*     */     }
/*     */     
/* 497 */     if (m_38385_() > 0) {
/* 498 */       m_38354_(m_38385_() - 1);
/*     */     }
/*     */     
/* 501 */     if (m_38384_() > 0.0F) {
/* 502 */       m_38311_(m_38384_() - 1.0F);
/*     */     }
/*     */     
/* 505 */     this.f_19854_ = m_20185_();
/* 506 */     this.f_19855_ = m_20186_();
/* 507 */     this.f_19856_ = m_20189_();
/*     */ 
/*     */     
/* 510 */     m_6075_();
/*     */ 
/*     */     
/* 513 */     m_38391_();
/*     */     
/* 515 */     if (m_6109_()) {
/* 516 */       if (!(m_146895_() instanceof Player)) {
/* 517 */         m_38339_(false, false);
/*     */       }
/*     */       
/* 520 */       m_38395_();
/* 521 */       if (this.f_19853_.f_46443_) {
/* 522 */         m_38396_();
/* 523 */         this.f_19853_.m_5503_((Packet)new ServerboundPaddleBoatPacket(m_38313_(0), m_38313_(1)));
/*     */       } 
/*     */       
/* 526 */       m_6478_(MoverType.SELF, m_20184_());
/*     */     } else {
/* 528 */       m_20256_(Vec3.f_82478_);
/*     */     } 
/*     */     
/* 531 */     m_38388_();
/*     */     
/* 533 */     if (this.f_19853_.f_46443_ && 
/* 534 */       !isEmergency()) {
/* 535 */       float moving = (this.f_38275_ || this.f_38276_) ? (this.isBoosting ? 0.9F : 0.7F) : 0.5F;
/* 536 */       if (this.lastMoving != moving) {
/* 537 */         this.lastMoving = moving;
/* 538 */         ImmersivePetroleum.proxy.handleEntitySound((SoundEvent)IESounds.dieselGenerator.get(), (Entity)this, false, 0.5F, 0.5F);
/*     */       } 
/* 540 */       ImmersivePetroleum.proxy.handleEntitySound((SoundEvent)IESounds.dieselGenerator.get(), (Entity)this, (m_20160_() && getContainedFluid() != FluidStack.EMPTY && getContainedFluid().getAmount() > 0), (this.f_38275_ || this.f_38276_) ? 0.5F : 0.3F, moving);
/*     */       
/* 542 */       if (this.f_38275_ && this.f_19853_.f_46441_.m_188503_(2) == 0) {
/* 543 */         if (m_20077_()) {
/* 544 */           if (this.f_19853_.f_46441_.m_188503_(3) == 0) {
/* 545 */             float f1 = Mth.m_14031_(-m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 546 */             float f2 = Mth.m_14089_(m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 547 */             float yO = 0.4F + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 548 */             Vec3 motion = m_20184_();
/* 549 */             this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123756_, m_20185_() - (f1 * 1.5F), m_20186_() + yO, m_20189_() - (f2 * 1.5F), -2.0D * motion.m_7096_(), 0.0D, -2.0D * motion.m_7094_());
/*     */           } 
/*     */         } else {
/* 552 */           float f1 = Mth.m_14031_(-m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 553 */           float f2 = Mth.m_14089_(m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 554 */           float yO = 0.1F + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 555 */           this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123795_, m_20185_() - (f1 * 1.5F), m_20186_() + yO, m_20189_() - (f2 * 1.5F), 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/* 558 */       if (this.isBoosting && this.f_19853_.f_46441_.m_188503_(2) == 0) {
/* 559 */         float f1 = Mth.m_14031_(-m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 560 */         float f2 = Mth.m_14089_(m_146908_() * 0.017453292F) + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 561 */         float yO = 0.8F + (this.f_19853_.f_46441_.m_188501_() - 0.5F) * 0.3F;
/* 562 */         this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, m_20185_() - (f1 * 1.3F), m_20186_() + yO, m_20189_() - (f2 * 1.3F), 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 567 */     if (isEmergency()) {
/* 568 */       for (int i = 0; i <= 1; i++) {
/* 569 */         if (m_38313_(i)) {
/* 570 */           if (!m_20067_() && (this.f_38263_[i] % 6.2831855F) <= 0.7853981852531433D && ((this.f_38263_[i] + 0.3926991F) % 6.2831855F) >= 0.7853981852531433D) {
/* 571 */             SoundEvent soundevent = m_38370_();
/* 572 */             if (soundevent != null) {
/* 573 */               Vec3 vec3 = m_20252_(1.0F);
/* 574 */               double d0 = (i == 1) ? -vec3.f_82481_ : vec3.f_82481_;
/* 575 */               double d1 = (i == 1) ? vec3.f_82479_ : -vec3.f_82479_;
/* 576 */               this.f_19853_.m_6263_((Player)null, m_20185_() + d0, m_20186_(), m_20189_() + d1, soundevent, m_5720_(), 1.0F, 0.8F + 0.4F * this.f_19796_.m_188501_());
/* 577 */               this.f_19853_.m_142346_(m_6688_(), GameEvent.f_157784_, new BlockPos(m_20185_() + d0, m_20186_(), m_20189_() + d1));
/*     */             } 
/*     */           } 
/*     */           
/* 581 */           this.f_38263_[i] = this.f_38263_[i] + 0.3926991F;
/*     */         } else {
/* 583 */           this.f_38263_[i] = 0.0F;
/*     */         }
/*     */       
/*     */       } 
/* 587 */     } else if (m_38313_(0)) {
/* 588 */       this.f_38263_[0] = (float)(this.f_38263_[0] + (this.isBoosting ? 0.02D : 0.01D));
/* 589 */     } else if (m_38313_(1)) {
/* 590 */       this.f_38263_[0] = (float)(this.f_38263_[0] - 0.01D);
/*     */     } 
/*     */ 
/*     */     
/* 594 */     float xO = Mth.m_14031_(-m_146908_() * 0.017453292F);
/* 595 */     float zO = Mth.m_14089_(m_146908_() * 0.017453292F);
/* 596 */     Vector3f vec = new Vector3f(xO, zO, 0.0F);
/* 597 */     vec.m_122278_();
/*     */     
/* 599 */     if (!this.f_19853_.f_46443_ && this.hasIcebreaker && !isEmergency()) {
/* 600 */       AABB bb = m_20191_().m_82400_(0.1D);
/* 601 */       BlockPos.MutableBlockPos mutableBlockPos0 = new BlockPos.MutableBlockPos(bb.f_82288_ + 0.001D, bb.f_82289_ + 0.001D, bb.f_82290_ + 0.001D);
/* 602 */       BlockPos.MutableBlockPos mutableBlockPos1 = new BlockPos.MutableBlockPos(bb.f_82291_ - 0.001D, bb.f_82292_ - 0.001D, bb.f_82293_ - 0.001D);
/* 603 */       BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
/*     */       
/* 605 */       if (this.f_19853_.m_46832_((BlockPos)mutableBlockPos0, (BlockPos)mutableBlockPos1)) {
/* 606 */         boolean brokeIce = false;
/* 607 */         for (int i = mutableBlockPos0.m_123341_(); i <= mutableBlockPos1.m_123341_(); i++) {
/* 608 */           for (int j = mutableBlockPos0.m_123342_(); j <= mutableBlockPos1.m_123342_(); j++) {
/* 609 */             for (int k = mutableBlockPos0.m_123343_(); k <= mutableBlockPos1.m_123343_(); k++) {
/* 610 */               mutableBlockPos2.m_122178_(i, j, k);
/* 611 */               BlockState BlockState = this.f_19853_.m_8055_((BlockPos)mutableBlockPos2);
/*     */               
/* 613 */               Vector3f vec2 = new Vector3f((float)((i + 0.5F) - m_20185_()), (float)((k + 0.5F) - m_20189_()), 0.0F);
/* 614 */               vec2.m_122278_();
/*     */               
/* 616 */               float sim = vec2.m_122276_(vec);
/* 617 */               if (BlockState.m_60734_() == Blocks.f_50126_ && sim > 0.3F) {
/* 618 */                 this.f_19853_.m_46961_((BlockPos)mutableBlockPos2, false);
/* 619 */                 this.f_19853_.m_46597_((BlockPos)mutableBlockPos2, Blocks.f_49990_.m_49966_());
/* 620 */                 brokeIce = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 626 */         if (brokeIce) { Entity entity = m_146895_(); if (entity instanceof Player) { Player player = (Player)entity;
/* 627 */             Utils.unlockIPAdvancement(player, "main/ice_breaker"); }
/*     */            }
/*     */       
/*     */       } 
/*     */     } 
/* 632 */     m_20101_();
/*     */     
/* 634 */     if (!this.f_19853_.f_46443_) {
/* 635 */       List<Entity> list = this.f_19853_.m_6249_((Entity)this, m_20191_().m_82377_(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelector.m_20421_((Entity)this));
/* 636 */       if (!list.isEmpty()) {
/* 637 */         boolean flag = !(m_6688_() instanceof Player);
/*     */         
/* 639 */         for (Entity entity : list) {
/* 640 */           if (!entity.m_20363_((Entity)this)) {
/* 641 */             if (flag && m_20197_().size() < 2 && !entity.m_20159_() && entity.m_20205_() < m_20205_() && entity instanceof LivingEntity && !(entity instanceof net.minecraft.world.entity.animal.WaterAnimal) && !(entity instanceof Player)) {
/* 642 */               entity.m_20329_((Entity)this); continue;
/*     */             } 
/* 644 */             m_7334_(entity);
/*     */             
/* 646 */             if (this.hasIcebreaker && 
/* 647 */               entity instanceof LivingEntity && !(entity instanceof Player)) { Entity entity1 = m_6688_(); if (entity1 instanceof Player) { Player player = (Player)entity1;
/* 648 */                 Vector3f vec2 = new Vector3f((float)(entity.m_20185_() - m_20185_()), (float)(entity.m_20189_() - m_20189_()), 0.0F);
/* 649 */                 vec2.m_122278_();
/*     */                 
/* 651 */                 float sim = vec2.m_122276_(vec);
/* 652 */                 if (sim > 0.5F) {
/* 653 */                   Vec3 motion = entity.m_20184_();
/* 654 */                   entity.m_6469_(DamageSource.m_19344_(player), 4.0F);
/* 655 */                   entity.m_20256_(new Vec3(motion.f_82479_ + (vec2.m_122239_() * 0.75F), motion.f_82480_, motion.f_82481_ + (vec2.m_122260_() * 0.75F)));
/*     */                 }  }
/*     */                }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void m_38396_() {
/* 668 */     if (m_20160_()) {
/* 669 */       float f = 0.0F;
/*     */       
/* 671 */       if (isEmergency()) {
/* 672 */         if (this.f_38273_) {
/* 673 */           this.f_38266_--;
/*     */         }
/*     */         
/* 676 */         if (this.f_38274_) {
/* 677 */           this.f_38266_++;
/*     */         }
/*     */         
/* 680 */         if (this.f_38274_ != this.f_38273_ && !this.f_38275_ && !this.f_38276_) {
/* 681 */           f += 0.005F;
/*     */         }
/*     */         
/* 684 */         m_146922_(m_146908_() + this.f_38266_);
/* 685 */         if (this.f_38275_) {
/* 686 */           f += 0.04F;
/*     */         }
/*     */         
/* 689 */         if (this.f_38276_) {
/* 690 */           f -= 0.005F;
/*     */         }
/*     */         
/* 693 */         double xa = (Mth.m_14031_(-m_146908_() * 0.017453292F) * f);
/* 694 */         double za = (Mth.m_14089_(m_146908_() * 0.017453292F) * f);
/* 695 */         Vec3 motion = m_20184_().m_82520_(xa, 0.0D, za);
/* 696 */         m_20256_(motion);
/* 697 */         m_38339_(((this.f_38274_ && !this.f_38273_) || this.f_38275_), ((this.f_38273_ && !this.f_38274_) || this.f_38275_));
/*     */       } else {
/* 699 */         FluidStack fluid = getContainedFluid();
/* 700 */         int consumeAmount = 0;
/* 701 */         if (fluid != FluidStack.EMPTY) {
/* 702 */           consumeAmount = FuelHandler.getBoatFuelUse(fluid.getFluid());
/*     */         }
/*     */         
/* 705 */         if (fluid != FluidStack.EMPTY && fluid.getAmount() >= consumeAmount && (this.f_38275_ || this.f_38276_)) {
/* 706 */           int toConsume = consumeAmount;
/* 707 */           if (this.f_38275_) {
/* 708 */             f += 0.05F;
/* 709 */             if (this.isBoosting && fluid.getAmount() >= 3 * consumeAmount) {
/* 710 */               f = (float)(f * 1.6D);
/* 711 */               toConsume *= 3;
/*     */             } 
/*     */           } 
/*     */           
/* 715 */           if (this.f_38276_) {
/* 716 */             f -= 0.01F;
/*     */           }
/*     */           
/* 719 */           fluid.setAmount(Math.max(0, fluid.getAmount() - toConsume));
/* 720 */           setContainedFluid(fluid);
/*     */           
/* 722 */           IPPacketHandler.sendToServer(new MessageConsumeBoatFuel(toConsume));
/*     */           
/* 724 */           m_38339_(this.f_38275_, this.f_38276_);
/*     */         } else {
/* 726 */           m_38339_(false, false);
/*     */         } 
/*     */         
/* 729 */         double xa = (Mth.m_14031_(-m_146908_() * 0.017453292F) * f);
/* 730 */         double za = (Mth.m_14089_(m_146908_() * 0.017453292F) * f);
/* 731 */         Vec3 motion = m_20184_().m_82520_(xa, 0.0D, za);
/* 732 */         m_20256_(motion);
/*     */         
/* 734 */         if (this.f_38273_ || this.f_38274_) {
/* 735 */           float speed = Mth.m_14116_((float)(motion.f_82479_ * motion.f_82479_ + motion.f_82481_ * motion.f_82481_));
/*     */           
/* 737 */           if (this.f_38274_) {
/* 738 */             this.f_38266_ += 1.1F * speed * (this.hasRudders ? 1.5F : 1.0F) * (this.isBoosting ? 0.5F : 1.0F) * ((this.f_38276_ && !this.f_38275_) ? 2.0F : 1.0F);
/*     */             
/* 740 */             this.propellerYRotation = Mth.m_14036_(this.propellerYRotation - 0.2F, -1.0F, 1.0F);
/*     */           } 
/*     */           
/* 743 */           if (this.f_38273_) {
/* 744 */             this.f_38266_ -= 1.1F * speed * (this.hasRudders ? 1.5F : 1.0F) * (this.isBoosting ? 0.5F : 1.0F) * ((this.f_38276_ && !this.f_38275_) ? 2.0F : 1.0F);
/*     */             
/* 746 */             this.propellerYRotation = Mth.m_14036_(this.propellerYRotation + 0.2F, -1.0F, 1.0F);
/*     */           } 
/*     */         } 
/*     */         
/* 750 */         if (!this.f_38273_ && !this.f_38274_ && this.propellerYRotation != 0.0F) {
/* 751 */           this.propellerYRotation *= 0.7F;
/* 752 */           if (this.propellerYRotation > -0.01F && this.propellerYRotation < 0.01F) {
/* 753 */             this.propellerYRotation = 0.0F;
/*     */           }
/*     */         } 
/*     */         
/* 757 */         m_146922_(m_146908_() + this.f_38266_);
/* 758 */         m_38339_(((this.f_38274_ && !this.f_38273_) || this.f_38275_), ((this.f_38273_ && !this.f_38274_) || this.f_38275_));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMaxFuel() {
/* 764 */     return this.hasTank ? 16000 : 8000;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Item m_38369_() {
/* 770 */     return (Item)IPContent.Items.SPEEDBOAT.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_6060_() {
/* 775 */     if (this.isFireproof) {
/* 776 */       return false;
/*     */     }
/* 778 */     return super.m_6060_();
/*     */   }
/*     */   
/*     */   public boolean isEmergency() {
/* 782 */     FluidStack fluid = getContainedFluid();
/* 783 */     if (fluid != FluidStack.EMPTY) {
/* 784 */       int consumeAmount = FuelHandler.getBoatFuelUse(fluid.getFluid());
/* 785 */       return (fluid.getAmount() < consumeAmount && this.hasPaddles);
/*     */     } 
/*     */     
/* 788 */     return this.hasPaddles;
/*     */   }
/*     */   
/*     */   public NonNullList<ItemStack> getUpgrades() {
/* 792 */     NonNullList<ItemStack> stackList = NonNullList.m_122780_(4, ItemStack.f_41583_);
/* 793 */     stackList.set(0, this.f_19804_.m_135370_(UPGRADE_0));
/* 794 */     stackList.set(1, this.f_19804_.m_135370_(UPGRADE_1));
/* 795 */     stackList.set(2, this.f_19804_.m_135370_(UPGRADE_2));
/* 796 */     stackList.set(3, this.f_19804_.m_135370_(UPGRADE_3));
/* 797 */     return stackList;
/*     */   }
/*     */   
/*     */   public String[] getOverlayText(Player player, HitResult mop) {
/* 801 */     if (Utils.isFluidRelatedItemStack(player.m_21120_(InteractionHand.MAIN_HAND))) {
/* 802 */       String s = null;
/* 803 */       FluidStack stack = getContainedFluid();
/* 804 */       if (stack != FluidStack.EMPTY) {
/* 805 */         s = stack.getDisplayName().getString() + ": " + stack.getDisplayName().getString() + "mB";
/*     */       } else {
/* 807 */         s = I18n.m_118938_("gui.immersiveengineering.empty", new Object[0]);
/*     */       } 
/* 809 */       return new String[] { s };
/*     */     } 
/*     */     
/* 812 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public float m_38371_() {
/* 817 */     AABB aabb = m_20191_();
/* 818 */     int i = Mth.m_14107_(aabb.f_82288_);
/* 819 */     int j = Mth.m_14165_(aabb.f_82291_);
/* 820 */     int k = Mth.m_14107_(aabb.f_82292_);
/* 821 */     int l = Mth.m_14165_(aabb.f_82292_ - this.f_38281_);
/* 822 */     int i1 = Mth.m_14107_(aabb.f_82290_);
/* 823 */     int j1 = Mth.m_14165_(aabb.f_82293_);
/* 824 */     BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
/*     */     int k1;
/* 826 */     label26: for (k1 = k; k1 < l; k1++) {
/* 827 */       float f = 0.0F;
/*     */       
/* 829 */       for (int l1 = i; l1 < j; l1++) {
/* 830 */         for (int i2 = i1; i2 < j1; i2++) {
/* 831 */           blockpos.m_122178_(l1, k1, i2);
/* 832 */           FluidState fluidstate = this.f_19853_.m_6425_((BlockPos)blockpos);
/* 833 */           if (fluidstate.m_205070_(FluidTags.f_13131_) || (this.isFireproof && fluidstate.m_205070_(FluidTags.f_13132_))) {
/* 834 */             f = Math.max(f, fluidstate.m_76155_((BlockGetter)this.f_19853_, (BlockPos)blockpos));
/*     */           }
/*     */           
/* 837 */           if (f >= 1.0F) {
/*     */             continue label26;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 843 */       if (f < 1.0F) {
/* 844 */         return blockpos.m_123342_() + f;
/*     */       }
/*     */     } 
/*     */     
/* 848 */     return (l + 1);
/*     */   }
/*     */   
/*     */   protected boolean m_38393_() {
/*     */     int m;
/* 853 */     AABB aabb = m_20191_();
/* 854 */     int i = Mth.m_14107_(aabb.f_82288_);
/* 855 */     int j = Mth.m_14165_(aabb.f_82291_);
/* 856 */     int k = Mth.m_14107_(aabb.f_82289_);
/* 857 */     int l = Mth.m_14165_(aabb.f_82289_ + 0.001D);
/* 858 */     int i1 = Mth.m_14107_(aabb.f_82290_);
/* 859 */     int j1 = Mth.m_14165_(aabb.f_82293_);
/* 860 */     boolean flag = false;
/* 861 */     this.f_38277_ = -1.7976931348623157E308D;
/* 862 */     BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 864 */     for (int k1 = i; k1 < j; k1++) {
/* 865 */       for (int l1 = k; l1 < l; l1++) {
/* 866 */         for (int i2 = i1; i2 < j1; i2++) {
/* 867 */           blockpos.m_122178_(k1, l1, i2);
/* 868 */           FluidState fluidstate = this.f_19853_.m_6425_((BlockPos)blockpos);
/* 869 */           if (fluidstate.m_205070_(FluidTags.f_13131_) || (this.isFireproof && fluidstate.m_205070_(FluidTags.f_13132_))) {
/* 870 */             float f = l1 + fluidstate.m_76155_((BlockGetter)this.f_19853_, (BlockPos)blockpos);
/* 871 */             this.f_38277_ = Math.max(f, this.f_38277_);
/* 872 */             m = flag | ((aabb.f_82289_ < f) ? 1 : 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 878 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Boat.Status m_38394_() {
/* 883 */     AABB aabb = m_20191_();
/* 884 */     double d0 = aabb.f_82292_ + 0.001D;
/* 885 */     int i = Mth.m_14107_(aabb.f_82288_);
/* 886 */     int j = Mth.m_14165_(aabb.f_82291_);
/* 887 */     int k = Mth.m_14107_(aabb.f_82292_);
/* 888 */     int l = Mth.m_14165_(d0);
/* 889 */     int i1 = Mth.m_14107_(aabb.f_82290_);
/* 890 */     int j1 = Mth.m_14165_(aabb.f_82293_);
/* 891 */     boolean flag = false;
/* 892 */     BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 894 */     for (int k1 = i; k1 < j; k1++) {
/* 895 */       for (int l1 = k; l1 < l; l1++) {
/* 896 */         for (int i2 = i1; i2 < j1; i2++) {
/* 897 */           blockpos.m_122178_(k1, l1, i2);
/* 898 */           FluidState fluidstate = this.f_19853_.m_6425_((BlockPos)blockpos);
/* 899 */           if ((fluidstate.m_205070_(FluidTags.f_13131_) || (this.isFireproof && fluidstate.m_205070_(FluidTags.f_13132_))) && d0 < (blockpos.m_123342_() + fluidstate.m_76155_((BlockGetter)this.f_19853_, (BlockPos)blockpos))) {
/* 900 */             if (!fluidstate.m_76170_()) {
/* 901 */               return Boat.Status.UNDER_FLOWING_WATER;
/*     */             }
/*     */             
/* 904 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 910 */     return flag ? Boat.Status.UNDER_WATER : null;
/*     */   }
/*     */   
/*     */   public boolean isLeftInDown() {
/* 914 */     return this.f_38273_;
/*     */   }
/*     */   
/*     */   public boolean isRightInDown() {
/* 918 */     return this.f_38274_;
/*     */   }
/*     */   
/*     */   public boolean isForwardInDown() {
/* 922 */     return this.f_38275_;
/*     */   }
/*     */   
/*     */   public boolean isBackInDown() {
/* 926 */     return this.f_38276_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_20291_(int flag) {
/* 931 */     return super.m_20291_(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_20115_(int flag, boolean set) {
/* 936 */     super.m_20115_(flag, set);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Packet<?> m_5654_() {
/* 942 */     return NetworkHooks.getEntitySpawningPacket((Entity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSpawnData(FriendlyByteBuf buffer) {
/* 947 */     String fluid = buffer.m_130277_();
/* 948 */     int amount = buffer.readInt();
/* 949 */     ItemStack stack0 = buffer.m_130267_();
/* 950 */     ItemStack stack1 = buffer.m_130267_();
/* 951 */     ItemStack stack2 = buffer.m_130267_();
/* 952 */     ItemStack stack3 = buffer.m_130267_();
/*     */     
/* 954 */     this.f_19804_.m_135381_(TANK_FLUID, fluid);
/* 955 */     this.f_19804_.m_135381_(TANK_AMOUNT, Integer.valueOf(amount));
/* 956 */     this.f_19804_.m_135381_(UPGRADE_0, stack0);
/* 957 */     this.f_19804_.m_135381_(UPGRADE_1, stack1);
/* 958 */     this.f_19804_.m_135381_(UPGRADE_2, stack2);
/* 959 */     this.f_19804_.m_135381_(UPGRADE_3, stack3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSpawnData(FriendlyByteBuf buffer) {
/* 964 */     String fluid = (String)this.f_19804_.m_135370_(TANK_FLUID);
/* 965 */     int amount = ((Integer)this.f_19804_.m_135370_(TANK_AMOUNT)).intValue();
/* 966 */     ItemStack stack0 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_0);
/* 967 */     ItemStack stack1 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_1);
/* 968 */     ItemStack stack2 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_2);
/* 969 */     ItemStack stack3 = (ItemStack)this.f_19804_.m_135370_(UPGRADE_3);
/*     */     
/* 971 */     buffer.m_130070_(fluid);
/* 972 */     buffer.writeInt(amount);
/* 973 */     buffer.m_130055_(stack0);
/* 974 */     buffer.m_130055_(stack1);
/* 975 */     buffer.m_130055_(stack2);
/* 976 */     buffer.m_130055_(stack3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\entity\MotorboatEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */