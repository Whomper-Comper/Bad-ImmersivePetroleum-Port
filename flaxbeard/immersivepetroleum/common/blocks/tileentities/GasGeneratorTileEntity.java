/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ import blusunrize.immersiveengineering.ImmersiveEngineering;
/*     */ import blusunrize.immersiveengineering.api.TargetingInfo;
/*     */ import blusunrize.immersiveengineering.api.energy.MutableEnergyStorage;
/*     */ import blusunrize.immersiveengineering.api.utils.CapabilityUtils;
/*     */ import blusunrize.immersiveengineering.api.wires.Connection;
/*     */ import blusunrize.immersiveengineering.api.wires.ConnectionPoint;
/*     */ import blusunrize.immersiveengineering.api.wires.IImmersiveConnectable;
/*     */ import blusunrize.immersiveengineering.api.wires.WireType;
/*     */ import blusunrize.immersiveengineering.api.wires.impl.ImmersiveConnectableBlockEntity;
/*     */ import blusunrize.immersiveengineering.api.wires.localhandlers.EnergyTransferHandler;
/*     */ import blusunrize.immersiveengineering.api.wires.utils.WireUtils;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import blusunrize.immersiveengineering.common.blocks.PlacementLimitation;
/*     */ import blusunrize.immersiveengineering.common.config.IEServerConfig;
/*     */ import blusunrize.immersiveengineering.common.util.IESounds;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IBlockEntityDrop;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlacementReader;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlayerInteraction;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ 
/*     */ public class GasGeneratorTileEntity extends ImmersiveConnectableBlockEntity implements IPCommonTickableTile, IPlacementReader, IPlayerInteraction, IBlockEntityDrop, IEBlockInterfaces.IDirectionalBE, IEBlockInterfaces.IBlockOverlayText, IEBlockInterfaces.ISoundBE, EnergyTransferHandler.EnergyConnector {
/*     */   public static final int FUEL_CAPACITY = 8000;
/*     */   protected WireType wireType;
/*     */   protected boolean isActive = false;
/*  70 */   protected int fluidTick = 0;
/*  71 */   protected int currentFlux = 0;
/*  72 */   protected Direction facing = Direction.NORTH;
/*  73 */   protected final MutableEnergyStorage energyStorage = new MutableEnergyStorage(getMaxStorage(), 2147483647, getMaxOutput());
/*     */   protected final FluidTank tank;
/*     */   private final LazyOptional<IFluidHandler> fluidHandler;
/*     */   private final LazyOptional<IEnergyStorage> energyHandler;
/*  77 */   public int getMaxOutput() { return IEServerConfig.MACHINES.lvCapConfig.output.getAsInt(); } private int getMaxStorage() { return IEServerConfig.MACHINES.lvCapConfig.storage.getAsInt(); } public void m_142466_(@Nonnull CompoundTag nbt) { super.m_142466_(nbt); this.isActive = nbt.m_128471_("isActive"); this.fluidTick = nbt.m_128451_("fluidTick"); this.currentFlux = nbt.m_128451_("currentFlux"); this.tank.readFromNBT(nbt.m_128469_("tank")); this.wireType = nbt.m_128441_("wiretype") ? WireUtils.getWireTypeFromNBT(nbt, "wiretype") : null; if (nbt.m_128441_("buffer")) this.energyStorage.deserializeNBT(nbt.m_128423_("buffer"));  } public void m_183515_(CompoundTag nbt) { nbt.m_128405_("fluidTick", this.fluidTick); nbt.m_128405_("currentFlux", this.currentFlux); nbt.m_128379_("isActive", this.isActive); nbt.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag())); nbt.m_128365_("buffer", this.energyStorage.serializeNBT()); if (this.wireType != null) nbt.m_128359_("wiretype", this.wireType.getUniqueName());  } public GasGeneratorTileEntity(BlockPos pWorldPosition, BlockState pBlockState) { super((BlockEntityType)IPTileTypes.GENERATOR.get(), pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.tank = new FluidTank(8000, fluid -> (fluid != FluidStack.EMPTY && FuelHandler.isValidFuel(fluid.getFluid())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     this.fluidHandler = CapabilityUtils.constantOptional(this.tank);
/* 185 */     this.energyHandler = CapabilityUtils.constantOptional(this.energyStorage); }
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() { return ClientboundBlockEntityDataPacket.m_195642_((BlockEntity)this, b -> m_5995_()); }
/*     */   public void handleUpdateTag(CompoundTag tag) { m_142466_(tag); }
/* 188 */   @Nonnull public CompoundTag m_5995_() { CompoundTag nbt = new CompoundTag(); m_183515_(nbt); return nbt; } @Nonnull public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) { if (cap == ForgeCapabilities.FLUID_HANDLER && (side == null || side == Direction.UP))
/* 189 */       return this.fluidHandler.cast(); 
/* 190 */     if (cap == ForgeCapabilities.ENERGY && (side == null || side == this.facing)) {
/* 191 */       return this.energyHandler.cast();
/*     */     }
/* 193 */     return super.getCapability(cap, side); } public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) { if (pkt.m_131708_() != null) m_142466_(pkt.m_131708_());  } public void readOnPlacement(LivingEntity placer, ItemStack stack) { if (stack.m_41782_()) { CompoundTag nbt = stack.m_41784_(); this.tank.readFromNBT(nbt.m_128469_("tank")); this.energyStorage.deserializeNBT(nbt.m_128423_("energy")); }  } public void m_6596_() { super.m_6596_(); BlockState state = this.f_58857_.m_8055_(this.f_58858_); this.f_58857_.m_7260_(this.f_58858_, state, state, 3); this.f_58857_.m_46672_(this.f_58858_, state.m_60734_()); } public int getAvailableEnergy() { return Math.min(getMaxOutput(), this.energyStorage.getEnergyStored()); }
/*     */   public void extractEnergy(int amount) { this.energyStorage.extractEnergy(amount, false); }
/*     */   public boolean isSource(ConnectionPoint cp) { return true; }
/*     */   public boolean isSink(ConnectionPoint cp) { return false; }
/*     */   public boolean shouldPlaySound(@Nonnull String sound) { return this.isActive; }
/* 198 */   public void invalidateCaps() { super.invalidateCaps();
/* 199 */     this.fluidHandler.invalidate();
/* 200 */     this.energyHandler.invalidate(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component[] getOverlayText(Player player, @Nonnull HitResult mop, boolean hammer) {
/* 205 */     if (Utils.isFluidRelatedItemStack(player.m_21120_(InteractionHand.MAIN_HAND))) {
/* 206 */       MutableComponent mutableComponent; Component s = null;
/* 207 */       if (this.tank.getFluid().getAmount() > 0) {
/* 208 */         mutableComponent = ((MutableComponent)this.tank.getFluid().getDisplayName()).m_130946_(": " + this.tank.getFluidAmount() + "mB");
/*     */       } else {
/* 210 */         mutableComponent = Component.m_237115_("gui.immersiveengineering.empty");
/* 211 */       }  return new Component[] { (Component)mutableComponent };
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useNixieFont(@Nonnull Player player, @Nonnull HitResult mop) {
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Direction side, Player player, InteractionHand hand, ItemStack heldItem, float hitX, float hitY, float hitZ) {
/* 223 */     if (FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)this.tank)) {
/* 224 */       m_6596_();
/* 225 */       Utils.unlockIPAdvancement(player, "main/gas_generator");
/* 226 */       return InteractionResult.SUCCESS;
/* 227 */     }  if (player.m_6144_()) {
/* 228 */       boolean added = false;
/* 229 */       if (player.m_150109_().m_36056_().m_41619_()) {
/* 230 */         added = true;
/* 231 */         player.m_150109_().m_6836_((player.m_150109_()).f_35977_, getFirstBlockEntityDrop());
/*     */       } else {
/* 233 */         added = player.m_150109_().m_36054_(getFirstBlockEntityDrop());
/*     */       } 
/*     */       
/* 236 */       if (added) {
/* 237 */         this.f_58857_.m_46597_(this.f_58858_, Blocks.f_50016_.m_49966_());
/*     */       }
/* 239 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 242 */     return InteractionResult.FAIL;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<ItemStack> getBlockEntityDrop(LootContext context) {
/* 247 */     ItemStack stack = new ItemStack((ItemLike)m_58900_().m_60734_());
/*     */     
/* 249 */     CompoundTag nbt = new CompoundTag();
/*     */     
/* 251 */     if (this.tank.getFluidAmount() > 0) {
/* 252 */       CompoundTag tankNbt = this.tank.writeToNBT(new CompoundTag());
/* 253 */       nbt.m_128365_("tank", (Tag)tankNbt);
/*     */     } 
/*     */     
/* 256 */     if (this.energyStorage.getEnergyStored() > 0) {
/* 257 */       Tag energyNbt = this.energyStorage.serializeNBT();
/* 258 */       nbt.m_128365_("energy", energyNbt);
/*     */     } 
/*     */     
/* 261 */     if (!nbt.m_128456_())
/* 262 */       stack.m_41751_(nbt); 
/* 263 */     return (List<ItemStack>)ImmutableList.of(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Direction getFacing() {
/* 269 */     return this.facing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacing(@Nonnull Direction facing) {
/* 274 */     this.facing = facing;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PlacementLimitation getFacingLimitation() {
/* 280 */     return PlacementLimitation.HORIZONTAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mirrorFacingOnPlacement(@Nonnull LivingEntity placer) {
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHammerRotate(@Nonnull Direction side, @Nonnull Vec3 hit, @Nonnull LivingEntity entity) {
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickClient() {
/* 295 */     ImmersiveEngineering.proxy.handleTileSound((Supplier)IESounds.dieselGenerator, (BlockEntity)this, this.isActive, 0.3F, 0.75F);
/* 296 */     if (this.isActive && this.f_58857_.m_46467_() % 4L == 0L) {
/* 297 */       Direction fl = this.facing;
/* 298 */       Direction fw = this.facing.m_122427_();
/*     */       
/* 300 */       Vec3i vec = fw.m_122424_().m_122436_();
/*     */       
/* 302 */       double x = this.f_58858_.m_123341_() + 0.5D + ((fl.m_122429_() * 2) / 16.0F) + (-fw.m_122429_() * 0.6125F);
/* 303 */       double y = this.f_58858_.m_123342_() + 0.4D;
/* 304 */       double z = this.f_58858_.m_123343_() + 0.5D + ((fl.m_122431_() * 2) / 16.0F) + (-fw.m_122431_() * 0.6125F);
/*     */       
/* 306 */       this.f_58857_.m_7106_((this.f_58857_.f_46441_.m_188503_(10) == 0) ? (ParticleOptions)ParticleTypes.f_123755_ : (ParticleOptions)ParticleTypes.f_123762_, x, y, z, vec.m_123341_() * 0.025D, 0.0D, vec.m_123343_() * 0.025D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickServer() {
/* 312 */     boolean lastActive = this.isActive;
/* 313 */     this.isActive = false;
/* 314 */     if (!this.f_58857_.m_46753_(this.f_58858_)) {
/* 315 */       if (this.fluidTick == 0) {
/* 316 */         Fluid fluid = this.tank.getFluid().getFluid();
/* 317 */         int amount = FuelHandler.getGeneratorFuelUse(fluid);
/* 318 */         if (amount > 0 && this.tank.getFluidAmount() >= amount) {
/* 319 */           this.tank.drain(new FluidStack(fluid, amount), IFluidHandler.FluidAction.EXECUTE);
/* 320 */           this.currentFlux = FuelHandler.getFluxGeneratedPerTick(fluid);
/* 321 */           this.fluidTick = 20;
/*     */         } 
/*     */       } 
/*     */       
/* 325 */       if (this.fluidTick > 0 && 
/* 326 */         this.energyStorage.receiveEnergy(this.currentFlux, true) >= this.currentFlux) {
/* 327 */         this.energyStorage.receiveEnergy(this.currentFlux, false);
/* 328 */         this.isActive = true;
/* 329 */         this.fluidTick--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 334 */     if (lastActive != this.isActive || (!this.f_58857_.f_46443_ && this.isActive)) {
/* 335 */       m_6596_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void connectCable(WireType cableType, ConnectionPoint target, IImmersiveConnectable other, ConnectionPoint otherTarget) {
/* 341 */     this.wireType = cableType;
/* 342 */     m_6596_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeCable(@Nullable Connection connection, ConnectionPoint attachedPoint) {
/* 347 */     this.wireType = null;
/* 348 */     m_6596_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnect() {
/* 353 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectCable(WireType cableType, ConnectionPoint target, Vec3i offset) {
/* 358 */     if (this.f_58857_.m_8055_(target.position()).m_60734_() != this.f_58857_.m_8055_(m_58899_()).m_60734_()) {
/* 359 */       return false;
/*     */     }
/*     */     
/* 362 */     return (this.wireType == null && (cableType.getCategory().equals("LV") || cableType.getCategory().equals("MV")));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getConnectionMaster(@Nullable WireType cableType, TargetingInfo target) {
/* 367 */     return this.f_58858_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConnectionPoint getTargetedPoint(TargetingInfo info, Vec3i offset) {
/* 372 */     return new ConnectionPoint(this.f_58858_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ConnectionPoint> getConnectionPoints() {
/* 377 */     return List.of(new ConnectionPoint(this.f_58858_, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 382 */     return this.f_58858_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getConnectionOffset(ConnectionPoint here, ConnectionPoint other, WireType type) {
/* 387 */     float xo = this.facing.m_122436_().m_123341_() * 0.5F + 0.5F;
/* 388 */     float zo = this.facing.m_122436_().m_123343_() * 0.5F + 0.5F;
/* 389 */     return new Vec3(xo, 0.5D, zo);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getRequestedHandlers() {
/* 394 */     return (Collection<ResourceLocation>)ImmutableList.of(EnergyTransferHandler.ID);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\GasGeneratorTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */