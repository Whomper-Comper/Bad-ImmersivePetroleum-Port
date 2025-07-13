/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.fluid.FluidUtils;
/*     */ import blusunrize.immersiveengineering.api.fluid.IPressurizedFluidOutput;
/*     */ import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
/*     */ import blusunrize.immersiveengineering.client.utils.TextUtils;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*     */ import blusunrize.immersiveengineering.common.util.MultiblockCapability;
/*     */ import blusunrize.immersiveengineering.common.util.Utils;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.OilTankMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.util.AABBUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.FluidHelper;
/*     */ import flaxbeard.immersivepetroleum.common.util.LayeredComparatorOutput;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ 
/*     */ public class OilTankTileEntity
/*     */   extends MultiblockPartBlockEntity<OilTankTileEntity>
/*     */   implements IPCommonTickableTile, IEBlockInterfaces.IPlayerInteraction, IEBlockInterfaces.IBlockOverlayText, IEBlockInterfaces.IBlockBounds, IEBlockInterfaces.IHammerInteraction, IEBlockInterfaces.IComparatorOverride, IPressurizedFluidOutput {
/*     */   public enum PortState implements StringRepresentable {
/*  60 */     INPUT, OUTPUT;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String m_7912_() {
/*  65 */       return toString().toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     public Component getText() {
/*  69 */       return (Component)Component.m_237115_("desc.immersivepetroleum.info.oiltank." + m_7912_());
/*     */     }
/*     */     
/*     */     public PortState next() {
/*  73 */       return (this == INPUT) ? OUTPUT : INPUT;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Port implements StringRepresentable {
/*  78 */     TOP((String)new BlockPos(2, 2, 3)),
/*  79 */     BOTTOM((String)new BlockPos(2, 0, 3)),
/*  80 */     DYNAMIC_A((String)new BlockPos(0, 1, 2)),
/*  81 */     DYNAMIC_B((String)new BlockPos(4, 1, 2)),
/*  82 */     DYNAMIC_C((String)new BlockPos(0, 1, 4)),
/*  83 */     DYNAMIC_D((String)new BlockPos(4, 1, 4));
/*     */     
/*  85 */     public static final Port[] DYNAMIC_PORTS = new Port[] { DYNAMIC_A, DYNAMIC_B, DYNAMIC_C, DYNAMIC_D }; public final BlockPos posInMultiblock; static {
/*     */     
/*     */     }
/*     */     Port(BlockPos posInMultiblock) {
/*  89 */       this.posInMultiblock = posInMultiblock;
/*     */     }
/*     */     
/*     */     public boolean matches(BlockPos posInMultiblock) {
/*  93 */       return posInMultiblock.equals(this.posInMultiblock);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String m_7912_() {
/*  99 */       return toString().toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     static Set<BlockPos> toSet(Port... ports) {
/* 103 */       ImmutableSet.Builder<BlockPos> builder = ImmutableSet.builder();
/* 104 */       for (Port port : ports) {
/* 105 */         builder.add(port.posInMultiblock);
/*     */       }
/* 107 */       return (Set<BlockPos>)builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(2, 2, 5), new BlockPos(2, 2, 2));
/*     */   
/* 116 */   public final FluidTank tank = new FluidTank(1024000);
/* 117 */   public final EnumMap<Port, PortState> portConfig = new EnumMap<>(Port.class); static final int MAX_FLUID_IO = 10000; private final MultiblockCapability<IFluidHandler> inputHandler; private final MultiblockCapability<IFluidHandler> outputHandler; private final LayeredComparatorOutput comparatorHelper;
/*     */   
/* 119 */   public OilTankTileEntity(BlockEntityType<OilTankTileEntity> type, BlockPos pWorldPosition, BlockState pBlockState) { super((IETemplateMultiblock)OilTankMultiblock.INSTANCE, type, true, pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     this.inputHandler = MultiblockCapability.make(this, be -> be.inputHandler, MultiblockPartBlockEntity::master, 
/* 324 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.tank }));
/*     */     
/* 326 */     this.outputHandler = MultiblockCapability.make(this, be -> be.outputHandler, MultiblockPartBlockEntity::master, 
/* 327 */         registerFluidOutput(new IFluidTank[] { (IFluidTank)this.tank }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     this
/* 380 */       .comparatorHelper = new LayeredComparatorOutput(this.tank.getCapacity(), 3, () -> this.f_58857_.m_46672_(m_58899_(), m_58900_().m_60734_()), layer -> { BlockPos masterPos = this.f_58858_.m_121996_((Vec3i)this.offsetToMaster); for (int z = -2; z <= 2; z++) { for (int x = -2; x <= 2; x++) { BlockPos pos = masterPos.m_7918_(x, layer, z); this.f_58857_.m_46672_(pos, this.f_58857_.m_8055_(pos).m_60734_()); }  }  }); this.redstoneControlInverted = false; for (Port port : Port.values()) { if (port == Port.DYNAMIC_B || port == Port.DYNAMIC_C || port == Port.BOTTOM) { this.portConfig.put(port, PortState.OUTPUT); } else { this.portConfig.put(port, PortState.INPUT); }  }  }
/*     */   public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); this.tank.readFromNBT(nbt.m_128469_("tank")); for (Port port : Port.DYNAMIC_PORTS) this.portConfig.put(port, PortState.values()[nbt.m_128451_(port.m_7912_())]);  }
/*     */   public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag())); for (Port port : Port.DYNAMIC_PORTS) nbt.m_128405_(port.m_7912_(), getPortStateFor(port).ordinal());  }
/*     */   public void tickClient() {}
/*     */   public void tickServer() { if (isDummy()) return;  int threshold = 1; PortState portStateA = getPortStateFor(Port.DYNAMIC_A); PortState portStateB = getPortStateFor(Port.DYNAMIC_B); PortState portStateC = getPortStateFor(Port.DYNAMIC_C); PortState portStateD = getPortStateFor(Port.DYNAMIC_D); boolean wasBalancing = false; if ((portStateA == PortState.OUTPUT && portStateC == PortState.INPUT) || (portStateA == PortState.INPUT && portStateC == PortState.OUTPUT)) wasBalancing |= equalize(Port.DYNAMIC_A, threshold, 1000);  if ((portStateB == PortState.OUTPUT && portStateD == PortState.INPUT) || (portStateB == PortState.INPUT && portStateD == PortState.OUTPUT))
/*     */       wasBalancing |= equalize(Port.DYNAMIC_B, threshold, 1000);  if (isRSDisabled())
/*     */       for (Port port : Port.values()) { if ((!wasBalancing && getPortStateFor(port) == PortState.OUTPUT) || (wasBalancing && port == Port.BOTTOM)) { Direction facing = getPortDirection(port); BlockPos pos = getBlockPosForPos(port.posInMultiblock).m_121945_(facing); FluidUtil.getFluidHandler(this.f_58857_, pos, facing.m_122424_()).map(out -> { if (this.tank.getFluidAmount() > 0) { FluidStack fs = FluidHelper.copyFluid(this.tank.getFluid(), Math.min(this.tank.getFluidAmount(), 432), false); int accepted = out.fill(fs, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { int drained = out.fill(FluidHelper.copyFluid(fs, Math.min(fs.getAmount(), accepted), false), IFluidHandler.FluidAction.EXECUTE); this.tank.drain(Utils.copyFluidStackWithAmount(this.tank.getFluid(), drained, false), IFluidHandler.FluidAction.EXECUTE); m_6596_(); markContainingBlockForUpdate(null); return Boolean.valueOf(true); }  }  return Boolean.valueOf(false); }).orElse(Boolean.valueOf(false)); }  }   this.comparatorHelper.update(this.tank.getFluidAmount()); }
/*     */   private boolean equalize(Port port, int threshold, int maxTransfer) { Direction facing = getPortDirection(port); BlockPos pos = getBlockPosForPos(port.posInMultiblock).m_121945_(facing); BlockEntity te = m_58904_().m_7702_(pos); OilTankTileEntity otherMaster = (OilTankTileEntity)te; if (te instanceof OilTankTileEntity && (otherMaster = (OilTankTileEntity)otherMaster.master()) != null) { int diff = otherMaster.tank.getFluidAmount() - this.tank.getFluidAmount(); int amount = Math.min(Math.abs(diff) / 2, maxTransfer); return ((diff <= -threshold && transfer(this, otherMaster, amount)) || (diff >= threshold && transfer(otherMaster, this, amount))); }  return false; }
/*     */   private boolean transfer(OilTankTileEntity src, OilTankTileEntity dst, int amount) { FluidStack fs = new FluidStack(src.tank.getFluid(), amount); int accepted = dst.tank.fill(fs, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { fs = new FluidStack(src.tank.getFluid(), accepted); dst.tank.fill(fs, IFluidHandler.FluidAction.EXECUTE); src.tank.drain(fs, IFluidHandler.FluidAction.EXECUTE); src.m_6596_(); dst.m_6596_(); src.markContainingBlockForUpdate(null); dst.markContainingBlockForUpdate(null); return true; }  return false; }
/*     */   private Direction getPortDirection(Port port) { switch (port) { case INPUT: case OUTPUT: return getIsMirrored() ? getFacing().m_122428_() : getFacing().m_122427_();case null: case null: return getIsMirrored() ? getFacing().m_122427_() : getFacing().m_122428_();case null: return Direction.UP; }  return Direction.DOWN; }
/*     */   public boolean isRSDisabled() { Set<BlockPos> rsPositions = getRedstonePos(); if (rsPositions == null || rsPositions.isEmpty())
/*     */       return false;  MultiblockPartBlockEntity<?> master = master(); if (master == null)
/*     */       master = this;  if (master.computerControl.isAttached())
/*     */       return !master.computerControl.isEnabled();  boolean ret = false; for (BlockPos rsPos : rsPositions) { OilTankTileEntity tile = (OilTankTileEntity)getEntityForPos(rsPos); if (tile != null)
/* 394 */         ret |= tile.isRSPowered();  }  return (this.redstoneControlInverted != ret); } public boolean interact(@Nonnull Direction side, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull ItemStack heldItem, float hitX, float hitY, float hitZ) { OilTankTileEntity master = (OilTankTileEntity)master(); if (master != null && FluidUtils.interactWithFluidHandler(player, hand, (IFluidHandler)master.tank)) { updateMasterBlock(null, true); return true; }  return false; } public int getComparatorInputOverride() { OilTankTileEntity master = (OilTankTileEntity)master();
/* 395 */     if (master != null && this.offsetToMaster.m_123342_() >= 0 && this.offsetToMaster.m_123342_() < this.comparatorHelper.getLayers()) {
/* 396 */       return master.comparatorHelper.getLayerOutput(this.offsetToMaster.m_123342_());
/*     */     }
/* 398 */     return 0; }
/*     */   public boolean hammerUseSide(@Nonnull Direction side, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull Vec3 hitVec) { Level level = getLevelNonnull(); if (!level.f_46443_) for (Port port : Port.DYNAMIC_PORTS) { if (port.posInMultiblock.equals(this.posInMultiblock)) { OilTankTileEntity master = (OilTankTileEntity)master(); if (master != null) { PortState portState = master.getPortStateFor(port); master.portConfig.put(port, portState.next()); updateMasterBlock(null, true); return true; }  break; }  }   return false; }
/*     */   public PortState getPortStateFor(Port port) { return this.portConfig.get(port); }
/* 401 */   public int getMaxAcceptedFluidAmount(FluidStack resource) { return 10000; } public Set<BlockPos> getRedstonePos() { return Redstone_IN; } @Nonnull public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) { if (cap == ForgeCapabilities.FLUID_HANDLER) for (Port port : Port.values()) { if (port.matches(this.posInMultiblock)) { OilTankTileEntity master = isDummy() ? (OilTankTileEntity)master() : this; if (master == null) return LazyOptional.empty();  switch ((PortState)master.portConfig.get(port)) { default: throw new IncompatibleClassChangeError();case INPUT: case OUTPUT: break; }  return this.outputHandler.getAndCast(); }  }   return super.getCapability(cap, side); } public boolean isLadder() { int x = this.posInMultiblock.m_123341_(); int z = this.posInMultiblock.m_123343_(); return (x == 3 && z == 0); } public Component[] getOverlayText(Player player, @Nonnull HitResult mop, boolean hammer) { if (Utils.isFluidRelatedItemStack(player.m_21120_(InteractionHand.MAIN_HAND))) { OilTankTileEntity master = (OilTankTileEntity)master(); FluidStack fs = (master != null) ? master.tank.getFluid() : this.tank.getFluid(); return new Component[] { TextUtils.formatFluidStack(fs) }; }  return null; } public boolean useNixieFont(@Nonnull Player player, @Nonnull HitResult mop) { return false; } @OnlyIn(Dist.CLIENT) public AABB getRenderBoundingBox() { BlockPos pos = m_58899_(); return new AABB(pos.m_7918_(-3, -1, -3), pos.m_7918_(3, 4, 3)); } private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(OilTankTileEntity::getShape);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/* 406 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*     */   }
/*     */   
/*     */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/* 410 */     int x = posInMultiblock.m_123341_();
/* 411 */     int y = posInMultiblock.m_123342_();
/* 412 */     int z = posInMultiblock.m_123343_();
/*     */     
/* 414 */     List<AABB> main = new ArrayList<>();
/*     */ 
/*     */     
/* 417 */     if (y == 0) {
/*     */       
/* 419 */       if (x == 0 && z == 1) {
/* 420 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
/* 421 */         AABBUtils.box16(main, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
/*     */       } 
/* 423 */       if (x == 4 && z == 1) {
/* 424 */         AABBUtils.box16(main, 12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
/* 425 */         AABBUtils.box16(main, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
/*     */       } 
/* 427 */       if (x == 0 && z == 5) {
/* 428 */         AABBUtils.box16(main, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
/* 429 */         AABBUtils.box16(main, 8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
/*     */       } 
/* 431 */       if (x == 4 && z == 5) {
/* 432 */         AABBUtils.box16(main, 12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
/* 433 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 438 */       if (x < 1 || z < 2 || x > 3 || z > 4) {
/* 439 */         AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */       }
/* 441 */       if (z >= 2 && z <= 4) {
/* 442 */         if (x == 0) AABBUtils.box16(main, 8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D); 
/* 443 */         if (x == 4) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D); 
/*     */       } 
/* 445 */       if (x >= 1 && x <= 3) {
/* 446 */         if (z == 1) AABBUtils.box16(main, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D); 
/* 447 */         if (z == 5) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 452 */     if (x == 3 && z == 0 && (
/* 453 */       y == 1 || y == 2)) {
/* 454 */       AABBUtils.box16(main, 2.0D, 0.0D, 15.0D, 14.0D, 16.0D, 16.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 459 */     if (y == 2 && 
/* 460 */       z == 0 && (x == 2 || x == 4)) {
/* 461 */       AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 466 */     if (y == 3) {
/* 467 */       if (z >= 1 && z <= 5) {
/* 468 */         if (x == 0) {
/* 469 */           AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/* 470 */         } else if (x == 4) {
/* 471 */           AABBUtils.box16(main, 15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */         } 
/*     */       }
/* 474 */       if (x >= 0 && x <= 4) {
/* 475 */         if (z == 5) {
/* 476 */           AABBUtils.box16(main, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/* 477 */         } else if (z == 1 && x != 4) {
/* 478 */           AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 484 */     if (main.isEmpty()) {
/* 485 */       main.add(AABBUtils.FULL);
/*     */     }
/* 487 */     return main;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\OilTankTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */