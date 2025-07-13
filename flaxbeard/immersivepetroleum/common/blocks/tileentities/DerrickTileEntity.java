/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
/*     */ import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
/*     */ import blusunrize.immersiveengineering.common.util.MultiblockCapability;
/*     */ import blusunrize.immersiveengineering.common.util.ResettableCapability;
/*     */ import blusunrize.immersiveengineering.common.util.orientation.RelativeBlockFace;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.client.ClientProxy;
/*     */ import flaxbeard.immersivepetroleum.client.gui.elements.PipeConfig;
/*     */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPMenuTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.ICanSkipGUI;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellPipeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.DerrickMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.util.AABBUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.FluidHelper;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ public class DerrickTileEntity extends PoweredMultiblockBlockEntity<DerrickTileEntity, MultiblockRecipe> implements IPCommonTickableTile, ICanSkipGUI, IPMenuProvider<DerrickTileEntity>, IEBlockInterfaces.IBlockBounds {
/*     */   public static final int REQUIRED_WATER_AMOUNT = 125;
/*     */   public static final int REQUIRED_CONCRETE_AMOUNT = 125;
/*     */   
/*     */   public enum Inventory {
/*  79 */     INPUT;
/*     */     
/*     */     public int id() {
/*  82 */       return ordinal();
/*     */     }
/*     */   }
/*     */   
/*  86 */   public static final FluidTank DUMMY_TANK = new FluidTank(0);
/*     */ 
/*     */   
/*  89 */   public static final BlockPos Fluid_IN = new BlockPos(2, 0, 4);
/*     */ 
/*     */   
/*  92 */   public static final BlockPos Fluid_OUT = new BlockPos(4, 0, 2);
/*     */ 
/*     */   
/*  95 */   public static final Set<PoweredMultiblockBlockEntity.MultiblockFace> Energy_IN = (Set<PoweredMultiblockBlockEntity.MultiblockFace>)ImmutableSet.of(new PoweredMultiblockBlockEntity.MultiblockFace(2, 1, 0, RelativeBlockFace.UP));
/*     */ 
/*     */   
/*  98 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(0, 1, 1));
/*     */   
/* 100 */   public int timer = 0;
/* 101 */   public int rotation = 0;
/*     */   public boolean drilling;
/*     */   public boolean spilling;
/* 104 */   public final FluidTank tank = new FluidTank(8000, this::acceptsFluid);
/* 105 */   public final NonNullList<ItemStack> inventory = NonNullList.m_122780_(1, ItemStack.f_41583_);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PipeConfig.Grid gridStorage;
/*     */   
/* 111 */   private Fluid fluidSpilled = Fluids.f_76191_;
/*     */   private int clientFlow;
/*     */   
/*     */   public DerrickTileEntity(BlockEntityType<? extends DerrickTileEntity> type, BlockPos pos, BlockState state) {
/* 115 */     super((IETemplateMultiblock)DerrickMultiblock.INSTANCE, 16000, true, type, pos, state);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     this.wellCache = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 580 */     this.fluidInputHandler = MultiblockCapability.make(this, be -> be.fluidInputHandler, MultiblockPartBlockEntity::master, 
/* 581 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.tank }));
/*     */     
/* 583 */     this.dummyTank = registerFluidOutput(new IFluidTank[] { (IFluidTank)DUMMY_TANK });
/*     */   } public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); this.drilling = nbt.m_128471_("drilling"); this.spilling = nbt.m_128471_("spilling"); this.clientFlow = nbt.m_128451_("spillflow"); try { this.fluidSpilled = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.m_128461_("spillingfluid"))); } catch (ResourceLocationException rle) { this.fluidSpilled = Fluids.f_76191_; }  this.tank.readFromNBT(nbt.m_128469_("tank")); if (nbt.m_128425_("grid", 10)) this.gridStorage = PipeConfig.Grid.fromCompound(nbt.m_128469_("grid"));  if (!descPacket && !isDummy()) { ContainerHelper.m_18980_(nbt, this.inventory); m_6596_(); }  } public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128379_("drilling", this.drilling); nbt.m_128379_("spilling", this.spilling); nbt.m_128405_("spillflow", getReservoirFlow()); nbt.m_128359_("spillingfluid", RegistryUtils.getRegistryNameOf(this.fluidSpilled).toString()); nbt.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag())); if (this.gridStorage != null) nbt.m_128365_("grid", (Tag)this.gridStorage.toCompound());  if (!descPacket && !isDummy()) ContainerHelper.m_18973_(nbt, this.inventory);  } private int getReservoirFlow() { ReservoirIsland island = ReservoirHandler.getIsland(getLevelNonnull(), m_58899_()); if (island == null || this.f_58858_.m_123342_() < getLevelNonnull().m_5736_()) return 10;  return island.getFlowFromPressure(getLevelNonnull(), m_58899_()); } private boolean acceptsFluid(FluidStack fs) { if (fs.isEmpty()) return false;  WellTileEntity well = createAndGetWell(false); if (well == null) return false;  Fluid inFluid = fs.getFluid(); boolean isConcrete = (inFluid == ExternalModContent.getIEFluid_Concrete()); boolean isWater = (inFluid == Fluids.f_76193_); if (!isConcrete && !isWater) return false;  int realPipeLength = m_58899_().m_123342_() - 1 - well.m_58899_().m_123342_(); int concreteNeeded = 125 * (realPipeLength - well.wellPipeLength); if (concreteNeeded > 0 && isConcrete) { FluidStack tankFluidStack = this.tank.getFluid(); if ((!tankFluidStack.isEmpty() && inFluid != tankFluidStack.getFluid()) || tankFluidStack.getAmount() >= concreteNeeded) return false;  return (concreteNeeded >= fs.getAmount()); }  if (concreteNeeded <= 0) { int waterNeeded = 125 * (well.getMaxPipeLength() - well.wellPipeLength); if (waterNeeded > 0 && isWater) { FluidStack tankFluidStack = this.tank.getFluid(); if ((!tankFluidStack.isEmpty() && inFluid != tankFluidStack.getFluid()) || tankFluidStack.getAmount() >= waterNeeded) return false;  return (waterNeeded >= fs.getAmount()); }  }  return false; }
/*     */   private static final BlockState[] PARTICLESTATES = new BlockState[] { Blocks.f_50069_.m_49966_(), Blocks.f_50122_.m_49966_(), Blocks.f_49994_.m_49966_(), Blocks.f_152550_.m_49966_(), Blocks.f_50228_.m_49966_(), Blocks.f_49992_.m_49966_(), Blocks.f_50334_.m_49966_() }; private WellTileEntity wellCache; private final MultiblockCapability<IFluidHandler> fluidInputHandler; private final ResettableCapability<IFluidHandler> dummyTank;
/*     */   public void tickClient() { if (isDummy()) return;  if (this.drilling) { this.rotation += 10; this.rotation %= 2160; double x = this.f_58858_.m_123341_() + 0.5D; double y = this.f_58858_.m_123342_() + 1.0D; double z = this.f_58858_.m_123343_() + 0.5D; int r = this.f_58857_.f_46441_.m_188503_(PARTICLESTATES.length); for (int i = 0; i < 5; i++) { float xa = (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 10.0F; float ya = 5.0F; float za = (this.f_58857_.f_46441_.m_188501_() - 0.5F) * 10.0F; this.f_58857_.m_7106_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, PARTICLESTATES[r]), x, y, z, xa, ya, za); }  }  if (this.spilling) ClientProxy.spawnSpillParticles(this.f_58857_, this.f_58858_, this.fluidSpilled, 5, 1.25F, this.clientFlow);  }
/* 587 */   public <C> LazyOptional<C> getCapability(Capability<C> capability, Direction side) { if (capability == ForgeCapabilities.FLUID_HANDLER) {
/* 588 */       if (this.posInMultiblock.equals(Fluid_IN) && (side == null || side == getFacing().m_122424_())) {
/* 589 */         return this.fluidInputHandler.getAndCast();
/*     */       }
/* 591 */       if (this.posInMultiblock.equals(Fluid_OUT) && (
/* 592 */         side == null || (getIsMirrored() ? (side == getFacing().m_122428_()) : (side == getFacing().m_122427_())))) {
/* 593 */         return this.dummyTank.cast();
/*     */       }
/*     */     } 
/*     */     
/* 597 */     return super.getCapability(capability, side); }
/*     */   public void tickServer() { if (isDummy()) return;  if (!this.f_58857_.isAreaLoaded(m_58899_(), 2)) return;  boolean forceUpdate = false; boolean lastDrilling = this.drilling; boolean lastSpilling = this.spilling; this.drilling = this.spilling = false; if (this.f_58858_.m_123342_() < this.f_58857_.m_5736_()) { if (this.fluidSpilled == Fluids.f_76191_) this.fluidSpilled = (Fluid)Fluids.f_76193_;  this.spilling = true; } else { WellTileEntity well = createAndGetWell((getInventory(Inventory.INPUT) != ItemStack.f_41583_)); if (!isRSDisabled() && this.energyStorage.extractEnergy(((Integer)IPServerConfig.EXTRACTION.derrick_consumption.get()).intValue(), true) >= ((Integer)IPServerConfig.EXTRACTION.derrick_consumption.get()).intValue()) if (well != null && well.wellPipeLength < well.getMaxPipeLength()) { if (well.pipes <= 0 && getInventory(Inventory.INPUT) != ItemStack.f_41583_) { ItemStack stack = getInventory(Inventory.INPUT); if (stack.m_41613_() > 0) { stack.m_41774_(1); well.pipes = 6; if (stack.m_41613_() <= 0) setInventory(Inventory.INPUT, ItemStack.f_41583_);  well.m_6596_(); forceUpdate = true; }  }  if (well.pipes > 0) { BlockPos dPos = m_58899_(); BlockPos wPos = well.m_58899_(); int realPipeLength = dPos.m_123342_() - 1 - wPos.m_123342_(); if (well.phyiscalPipesList.size() < realPipeLength && well.wellPipeLength < realPipeLength) { if (this.tank.drain(125, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 125) { this.energyStorage.extractEnergy(((Integer)IPServerConfig.EXTRACTION.derrick_consumption.get()).intValue(), false); if (advanceTimer()) { Level world = getLevelNonnull(); int y = dPos.m_123342_() - 1; for (; y > wPos.m_123342_(); y--) { BlockPos current = new BlockPos(dPos.m_123341_(), y, dPos.m_123343_()); BlockState state = world.m_8055_(current); if (state.m_60734_() == Blocks.f_50752_ || state.m_60734_() == IPContent.Blocks.WELL.get()) break;  if (state.m_60734_() != IPContent.Blocks.WELL_PIPE.get() || ((Boolean)state.m_61143_((Property)WellPipeBlock.BROKEN)).booleanValue()) { world.m_46961_(current, false); world.m_46597_(current, ((WellPipeBlock)IPContent.Blocks.WELL_PIPE.get()).m_49966_()); well.phyiscalPipesList.add(Integer.valueOf(y)); this.tank.drain(125, IFluidHandler.FluidAction.EXECUTE); well.usePipe(); break; }  }  if (well.phyiscalPipesList.size() >= realPipeLength && well.wellPipeLength >= realPipeLength) { well.pastPhysicalPart = true; well.m_6596_(); }  }  forceUpdate = true; this.drilling = true; }  } else { if (!this.tank.getFluid().isEmpty() && this.tank.getFluid().getFluid() == ExternalModContent.getIEFluid_Concrete()) { this.tank.drain(this.tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE); forceUpdate = true; }  if (this.tank.drain(125, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 125) { this.energyStorage.extractEnergy(((Integer)IPServerConfig.EXTRACTION.derrick_consumption.get()).intValue(), false); if (advanceTimer()) { restorePhysicalPipeProgress(well, dPos, realPipeLength); this.tank.drain(125, IFluidHandler.FluidAction.EXECUTE); well.usePipe(); }  forceUpdate = true; this.drilling = true; }  }  }  }   if (well != null && well.wellPipeLength == well.getMaxPipeLength()) outputReservoirFluid();  }  if (this.spilling && this.fluidSpilled == Fluids.f_76191_) this.fluidSpilled = IPContent.Fluids.CRUDEOIL.get();  if (!this.spilling && this.fluidSpilled != Fluids.f_76191_) this.fluidSpilled = Fluids.f_76191_;  if (forceUpdate || lastDrilling != this.drilling || lastSpilling != this.spilling) { updateMasterBlock(null, true); m_6596_(); }  }
/*     */   public WellTileEntity createAndGetWell(boolean popList) { if (this.wellCache != null && this.wellCache.m_58901_()) this.wellCache = null;  if (this.wellCache == null) { Level world = getLevelNonnull(); WellTileEntity well = null; for (int y = m_58899_().m_123342_() - 1; y >= world.m_141937_(); y--) { BlockPos current = new BlockPos(m_58899_().m_123341_(), y, m_58899_().m_123343_()); BlockState state = world.m_8055_(current); if (state.m_60734_() == IPContent.Blocks.WELL.get()) { well = (WellTileEntity)world.m_7702_(current); break; }  if (state.m_60734_() == Blocks.f_50752_) { world.m_46597_(current, ((WellBlock)IPContent.Blocks.WELL.get()).m_49966_()); well = (WellTileEntity)world.m_7702_(current); break; }  }  this.wellCache = well; }  if (popList && this.wellCache != null && this.wellCache.tappedIslands.isEmpty()) if (this.gridStorage != null) { transferGridDataToWell(this.wellCache); } else { this.wellCache.tappedIslands.add(Utils.toColumnPos(this.f_58858_)); this.wellCache.m_6596_(); }   if (this.wellCache != null) this.wellCache.abortSelfDestructSequence();  return this.wellCache; }
/*     */   @Nullable public WellTileEntity getWell() { if (this.wellCache != null && this.wellCache.m_58901_())
/*     */       this.wellCache = null;  if (this.wellCache == null) { Level world = getLevelNonnull(); WellTileEntity well = null; for (int y = m_58899_().m_123342_() - 1; y >= world.m_141937_(); y--) { BlockPos current = new BlockPos(m_58899_().m_123341_(), y, m_58899_().m_123343_()); BlockState state = world.m_8055_(current); if (state.m_60734_() == IPContent.Blocks.WELL.get()) { well = (WellTileEntity)world.m_7702_(current); break; }  }  this.wellCache = well; }  return this.wellCache; }
/* 602 */   public void transferGridDataToWell(@Nullable WellTileEntity well) { if (well != null) { int additionalPipes = 0; List<ColumnPos> list = new ArrayList<>(); PipeConfig.Grid grid = this.gridStorage; for (int j = 0; j < grid.getHeight(); j++) { for (int i = 0; i < grid.getWidth(); i++) { int type = grid.get(i, j); if (type > 0) { int x; int z; ColumnPos pos; switch (type) { case 2: case 3: x = i - grid.getWidth() / 2; z = j - grid.getHeight() / 2; pos = new ColumnPos(this.f_58858_.m_123341_() + x, this.f_58858_.m_123343_() + z); list.add(pos);case 1: additionalPipes++; break; }  }  }  }  well.tappedIslands = list; well.additionalPipes = additionalPipes; well.m_6596_(); }  } public boolean skipGui(Direction hitFace) { Direction facing = getFacing();
/*     */ 
/*     */     
/* 605 */     if (getEnergyPos().stream().anyMatch(t -> t.posInMultiblock().equals(this.posInMultiblock)) && hitFace == Direction.UP) {
/* 606 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 610 */     if (getRedstonePos().contains(this.posInMultiblock) && hitFace == (getIsMirrored() ? facing.m_122427_() : facing.m_122428_())) {
/* 611 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 615 */     if (this.posInMultiblock.equals(Fluid_IN) && hitFace == getFacing().m_122424_()) {
/* 616 */       return true;
/*     */     }
/* 618 */     if (this.posInMultiblock.equals(Fluid_OUT) && hitFace == (getIsMirrored() ? getFacing().m_122428_() : getFacing().m_122427_())) {
/* 619 */       return true;
/*     */     }
/*     */     
/* 622 */     return false; }
/*     */   private boolean advanceTimer() { if (this.timer-- <= 0) { this.timer = 10; return true; }  return false; }
/*     */   public void restorePhysicalPipeProgress(@Nonnull WellTileEntity well, BlockPos dPos, int realPipeLength) { int min = Math.min(well.wellPipeLength, realPipeLength); for (int i = 1; i < min; i++) { BlockPos current = new BlockPos(dPos.m_123341_(), dPos.m_123342_() - i, dPos.m_123343_()); BlockState state = getLevelNonnull().m_8055_(current); if (!(state.m_60734_() instanceof WellPipeBlock)) { getLevelNonnull().m_46961_(current, false); getLevelNonnull().m_46597_(current, ((WellPipeBlock)IPContent.Blocks.WELL_PIPE.get()).m_49966_()); }  }  }
/*     */   private void outputReservoirFluid() { WellTileEntity well = createAndGetWell(true); if (well == null) return;  FluidStack extracted = getExtractedFluidStack(well); if (!extracted.isEmpty()) { Direction facing = getIsMirrored() ? getFacing().m_122428_() : getFacing().m_122427_(); BlockPos outPos = getBlockPosForPos(Fluid_OUT).m_5484_(facing, 1); IFluidHandler output = (IFluidHandler)FluidUtil.getFluidHandler(this.f_58857_, outPos, facing.m_122424_()).orElse(null); if (output != null) { FluidStack fluid = FluidHelper.copyFluid(extracted, extracted.getAmount()); int accepted = output.fill(fluid, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { boolean iePipe = this.f_58857_.m_7702_(outPos) instanceof blusunrize.immersiveengineering.api.fluid.IFluidPipe; int drained = output.fill(FluidHelper.copyFluid(fluid, Math.min(fluid.getAmount(), accepted), iePipe), IFluidHandler.FluidAction.EXECUTE); if (fluid.getAmount() - drained > 0) this.spilling = true;  } else { this.spilling = true; }  } else { this.spilling = true; }  }  if (this.spilling && !extracted.isEmpty() && this.fluidSpilled != extracted.getFluid()) this.fluidSpilled = extracted.getFluid();  if (!this.spilling && this.fluidSpilled != Fluids.f_76191_) this.fluidSpilled = Fluids.f_76191_;  }
/* 626 */   private FluidStack getExtractedFluidStack(@Nonnull WellTileEntity well) { Fluid extractedFluid = Fluids.f_76191_; int extractedAmount = 0; for (ColumnPos cPos : well.tappedIslands) { ReservoirIsland island = ReservoirHandler.getIsland(this.f_58857_, cPos); if (island != null) { if (extractedFluid == Fluids.f_76191_) { extractedFluid = island.getFluid(); } else if (island.getFluid() != extractedFluid) { continue; }  extractedAmount += island.extractWithPressure(getLevelNonnull(), cPos.f_140723_(), cPos.f_140724_()); }  }  return new FluidStack(extractedFluid, extractedAmount); } public void disassemble() { if (this.formed && !this.f_58857_.f_46443_ && this.offsetToMaster.equals(Vec3i.f_123288_)) { WellTileEntity well = getWell(); if (well != null && !well.drillingCompleted) if (well.wellPipeLength > 0) { well.startSelfDestructSequence(); } else { this.f_58857_.m_46597_(well.m_58899_(), Blocks.f_50752_.m_49966_()); }   }  super.disassemble(); } public ItemStack getInventory(Inventory inv) { return (ItemStack)this.inventory.get(inv.id()); }
/*     */ 
/*     */   
/*     */   public ItemStack setInventory(Inventory inv, ItemStack stack) {
/* 630 */     return (ItemStack)this.inventory.set(inv.id(), stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public DerrickTileEntity getGuiMaster() {
/* 635 */     return (DerrickTileEntity)master();
/*     */   }
/*     */ 
/*     */   
/*     */   public IPMenuProvider.BEContainerIP<? super DerrickTileEntity, ?> getContainerType() {
/* 640 */     return IPMenuTypes.DERRICK;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseGui(Player player) {
/* 645 */     return this.formed;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getInventory() {
/* 650 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStackValid(int slot, ItemStack stack) {
/* 655 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotLimit(int slot) {
/* 660 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doGraphicalUpdates() {
/* 665 */     updateMasterBlock(null, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MultiblockRecipe getRecipeForId(Level level, ResourceLocation id) {
/* 670 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<BlockPos> getRedstonePos() {
/* 675 */     return Redstone_IN;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PoweredMultiblockBlockEntity.MultiblockFace> getEnergyPos() {
/* 680 */     return Energy_IN;
/*     */   }
/*     */ 
/*     */   
/*     */   public IFluidTank[] getInternalTanks() {
/* 685 */     return new IFluidTank[] { (IFluidTank)this.tank };
/*     */   }
/*     */ 
/*     */   
/*     */   public MultiblockRecipe findRecipeForInsertion(ItemStack inserting) {
/* 690 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/* 695 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getOutputTanks() {
/* 700 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean additionalCanProcessCheck(MultiblockProcess<MultiblockRecipe> process) {
/* 705 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doProcessOutput(ItemStack output) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void doProcessFluidOutput(FluidStack output) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessFinish(MultiblockProcess<MultiblockRecipe> process) {}
/*     */ 
/*     */   
/*     */   public int getMaxProcessPerTick() {
/* 722 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProcessQueueMaxLength() {
/* 727 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinProcessDistance(MultiblockProcess<MultiblockRecipe> process) {
/* 732 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInWorldProcessingMachine() {
/* 737 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isLadder() {
/* 741 */     int x = this.posInMultiblock.m_123341_();
/* 742 */     int y = this.posInMultiblock.m_123342_();
/* 743 */     int z = this.posInMultiblock.m_123343_();
/*     */     
/* 745 */     return (y >= 0 && y <= 2 && x == 0 && z == 2);
/*     */   }
/*     */   
/* 748 */   private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(DerrickTileEntity::getShape);
/*     */   
/*     */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/* 751 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*     */   }
/*     */   
/*     */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/* 755 */     int x = posInMultiblock.m_123341_();
/* 756 */     int y = posInMultiblock.m_123342_();
/* 757 */     int z = posInMultiblock.m_123343_();
/*     */     
/* 759 */     List<AABB> main = new ArrayList<>();
/*     */     
/* 761 */     if (y == 0) {
/* 762 */       if ((x != 2 || z != 4) && (x != 4 || z != 2) && (x != 2 || z != 0) && (x != 2 || z != 2)) {
/* 763 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */       }
/*     */       
/* 766 */       if (z == 4) {
/*     */         
/* 768 */         if (x == 1) AABBUtils.box16(main, 8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D); 
/* 769 */         if (x == 3) AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D);
/*     */       
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 775 */       if (x == 1 && z == 1) AABBUtils.box16(main, 12.0D, 8.0D, 12.0D, 16.0D, 16.0D, 16.0D); 
/* 776 */       if (x == 3 && z == 1) AABBUtils.box16(main, 0.0D, 8.0D, 12.0D, 4.0D, 16.0D, 16.0D); 
/* 777 */       if (x == 1 && z == 3) AABBUtils.box16(main, 12.0D, 8.0D, 0.0D, 16.0D, 16.0D, 4.0D); 
/* 778 */       if (x == 3 && z == 3) AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 4.0D, 16.0D, 4.0D);
/*     */ 
/*     */       
/* 781 */       if (x == 2 && z == 1) AABBUtils.box16(main, 0.0D, 8.0D, 12.0D, 16.0D, 16.0D, 16.0D); 
/* 782 */       if (x == 2 && z == 3) AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 4.0D); 
/* 783 */       if (x == 1 && z == 2) AABBUtils.box16(main, 12.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D); 
/* 784 */       if (x == 3 && z == 2) AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 4.0D, 16.0D, 16.0D);
/*     */     
/*     */     } 
/*     */     
/* 788 */     if (y == 0 || y == 1)
/*     */     {
/* 790 */       if (z == 0) {
/* 791 */         if (x == 1) {
/* 792 */           AABBUtils.box16(main, 4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/* 793 */         } else if (x == 3) {
/* 794 */           AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 800 */     if (x == 1 && z == 2) {
/* 801 */       if (y == 0) AABBUtils.box16(main, 0.0D, 8.0D, 3.0D, 12.0D, 16.0D, 13.0D); 
/* 802 */       if (y == 1) AABBUtils.box16(main, 0.0D, 0.0D, 3.0D, 12.0D, 2.0D, 13.0D);
/*     */     
/*     */     } 
/*     */     
/* 806 */     if (x == 1 && z == 1) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D); 
/* 807 */     if (x == 3 && z == 1) AABBUtils.box16(main, 12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D); 
/* 808 */     if (x == 1 && z == 3) AABBUtils.box16(main, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D); 
/* 809 */     if (x == 3 && z == 3) AABBUtils.box16(main, 12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
/*     */ 
/*     */     
/* 812 */     if (y >= 0 && y <= 2 && x == 0 && z == 2) {
/* 813 */       AABBUtils.box16(main, 15.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
/*     */     }
/*     */ 
/*     */     
/* 817 */     if (y == 2 && x >= 1 && x <= 3 && z >= 1 && z <= 3) {
/* 818 */       AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */     }
/*     */ 
/*     */     
/* 822 */     if (y == 3) {
/*     */       
/* 824 */       if (x == 1 && z == 2) {
/* 825 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 1.0D);
/* 826 */         AABBUtils.box16(main, 0.0D, 0.0D, 15.0D, 1.0D, 16.0D, 16.0D);
/*     */       } 
/* 828 */       if (x >= 1 && x <= 3) {
/* 829 */         if (z == 1) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D); 
/* 830 */         if (z == 3) AABBUtils.box16(main, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D); 
/*     */       } 
/* 832 */       if (x == 1 && (z == 1 || z == 3)) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D); 
/* 833 */       if (z >= 1 && z <= 3 && 
/* 834 */         x == 3) AABBUtils.box16(main, 15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 839 */     if (y >= 1 && 
/* 840 */       x == 2 && z == 2) {
/* 841 */       if (y == 7) {
/* 842 */         AABBUtils.box16(main, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D);
/*     */       } else {
/* 844 */         AABBUtils.box16(main, 4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 850 */     if (x == 0 && z == 1) {
/* 851 */       if (y == 0) {
/* 852 */         AABBUtils.box16(main, 2.0D, 0.0D, 2.0D, 6.0D, 16.0D, 4.0D);
/* 853 */         AABBUtils.box16(main, 2.0D, 0.0D, 12.0D, 6.0D, 16.0D, 14.0D);
/*     */       } 
/* 855 */       if (y == 1) AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
/*     */     
/*     */     } 
/*     */     
/* 859 */     if (main.isEmpty()) {
/* 860 */       main.add(AABBUtils.FULL);
/*     */     }
/* 862 */     return main;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\DerrickTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */