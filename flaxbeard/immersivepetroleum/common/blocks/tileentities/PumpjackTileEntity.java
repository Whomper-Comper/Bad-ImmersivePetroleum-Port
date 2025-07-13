/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
/*     */ import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
/*     */ import blusunrize.immersiveengineering.common.util.ResettableCapability;
/*     */ import blusunrize.immersiveengineering.common.util.orientation.RelativeBlockFace;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.PumpjackMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.util.AABBUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.FluidHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
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
/*     */ 
/*     */ 
/*     */ public class PumpjackTileEntity
/*     */   extends PoweredMultiblockBlockEntity<PumpjackTileEntity, MultiblockRecipe>
/*     */   implements IPCommonTickableTile, IEBlockInterfaces.IBlockBounds
/*     */ {
/*  53 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(0, 1, 5));
/*     */ 
/*     */   
/*  56 */   public static final Set<PoweredMultiblockBlockEntity.MultiblockFace> Energy_IN = (Set<PoweredMultiblockBlockEntity.MultiblockFace>)ImmutableSet.of(new PoweredMultiblockBlockEntity.MultiblockFace(2, 1, 5, RelativeBlockFace.UP));
/*     */ 
/*     */   
/*  59 */   public static final BlockPos East_Port = new BlockPos(2, 0, 2);
/*     */ 
/*     */   
/*  62 */   public static final BlockPos West_Port = new BlockPos(0, 0, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final BlockPos Down_Port = new BlockPos(1, 0, 0);
/*     */   
/*  69 */   public static final FluidTank FAKE_TANK = new FluidTank(0);
/*     */   public boolean wasActive = false;
/*  71 */   public float activeTicks = 0.0F; private final ResettableCapability<IFluidHandler> fakeFluidHandler;
/*     */   
/*  73 */   public PumpjackTileEntity(BlockEntityType<PumpjackTileEntity> type, BlockPos pWorldPosition, BlockState pBlockState) { super((IETemplateMultiblock)PumpjackMultiblock.INSTANCE, 16000, true, type, pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     this.fakeFluidHandler = registerFluidHandler(new IFluidTank[] { (IFluidTank)FAKE_TANK }); }
/*     */   public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); boolean lastActive = this.wasActive; this.wasActive = nbt.m_128471_("wasActive"); if (!this.wasActive && lastActive) this.activeTicks++;  }
/*     */   public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128379_("wasActive", this.wasActive); }
/*     */   public void tickClient() { if (!isDummy() && this.wasActive) this.activeTicks++;  }
/*     */   public void tickServer() { if (isDummy()) return;  super.tickServer(); boolean active = false; if (!isRSDisabled()) { BlockEntity teLow = getLevelNonnull().m_7702_(this.f_58858_.m_7495_()); if (teLow instanceof WellPipeTileEntity) { WellPipeTileEntity pipe = (WellPipeTileEntity)teLow; WellTileEntity well = pipe.getWell(); if (well != null) { int consumption = ((Integer)IPServerConfig.EXTRACTION.pumpjack_consumption.get()).intValue(); int extracted = this.energyStorage.extractEnergy(consumption, true); if (extracted >= consumption) { boolean foundPressurizedIsland = false; for (ColumnPos cPos : well.tappedIslands) { ReservoirIsland island = ReservoirHandler.getIsland(this.f_58857_, cPos); if (island != null && island.getPressure(getLevelNonnull(), cPos.f_140723_(), cPos.f_140724_()) > 0.0F) { foundPressurizedIsland = true; break; }  }  if (!foundPressurizedIsland) { int extractSpeed = ((Integer)IPServerConfig.EXTRACTION.pumpjack_speed.get()).intValue(); Direction portEast_facing = getIsMirrored() ? getFacing().m_122428_() : getFacing().m_122427_(); Direction portWest_facing = getIsMirrored() ? getFacing().m_122427_() : getFacing().m_122428_(); BlockPos portEast_pos = getBlockPosForPos(East_Port).m_121945_(portEast_facing); BlockPos portWest_pos = getBlockPosForPos(West_Port).m_121945_(portWest_facing); IFluidHandler portEast_output = (IFluidHandler)FluidUtil.getFluidHandler(this.f_58857_, portEast_pos, portEast_facing.m_122424_()).orElse(null); IFluidHandler portWest_output = (IFluidHandler)FluidUtil.getFluidHandler(this.f_58857_, portWest_pos, portWest_facing.m_122424_()).orElse(null); for (ColumnPos cPos : well.tappedIslands) { ReservoirIsland island = ReservoirHandler.getIsland(this.f_58857_, cPos); if (island != null) { FluidStack fluid = new FluidStack(island.getFluid(), island.extract(extractSpeed, IFluidHandler.FluidAction.SIMULATE)); if (portEast_output != null) { int accepted = portEast_output.fill(fluid, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { int drained = portEast_output.fill(FluidHelper.copyFluid(fluid, Math.min(fluid.getAmount(), accepted)), IFluidHandler.FluidAction.EXECUTE); island.extract(drained, IFluidHandler.FluidAction.EXECUTE); fluid = FluidHelper.copyFluid(fluid, fluid.getAmount() - drained); active = true; }  }  if (portWest_output != null && fluid.getAmount() > 0) { int accepted = portWest_output.fill(fluid, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { int drained = portWest_output.fill(FluidHelper.copyFluid(fluid, Math.min(fluid.getAmount(), accepted)), IFluidHandler.FluidAction.EXECUTE); island.extract(drained, IFluidHandler.FluidAction.EXECUTE); active = true; }  }  }  }  if (active) { this.energyStorage.extractEnergy(consumption, false); this.activeTicks++; }  }  }  }  }  }  if (active != this.wasActive) { m_6596_(); markContainingBlockForUpdate(null); }  this.wasActive = active; }
/* 285 */   public Set<PoweredMultiblockBlockEntity.MultiblockFace> getEnergyPos() { return Energy_IN; } public Set<BlockPos> getRedstonePos() { return Redstone_IN; } public boolean isInWorldProcessingMachine() { return false; } public void doProcessOutput(ItemStack output) {} public void doProcessFluidOutput(FluidStack output) {} public void onProcessFinish(MultiblockProcess<MultiblockRecipe> process) {} public boolean additionalCanProcessCheck(MultiblockProcess<MultiblockRecipe> process) { return false; } @Nonnull public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> capability, @Nullable Direction side) { if (capability == ForgeCapabilities.FLUID_HANDLER) {
/*     */       
/* 287 */       if (this.posInMultiblock.equals(East_Port) && (
/* 288 */         side == null || (getIsMirrored() ? (side == getFacing().m_122428_()) : (side == getFacing().m_122427_())))) {
/* 289 */         return this.fakeFluidHandler.cast();
/*     */       }
/*     */ 
/*     */       
/* 293 */       if (this.posInMultiblock.equals(West_Port) && (
/* 294 */         side == null || (getIsMirrored() ? (side == getFacing().m_122427_()) : (side == getFacing().m_122428_())))) {
/* 295 */         return this.fakeFluidHandler.cast();
/*     */       }
/*     */     } 
/*     */     
/* 299 */     return super.getCapability(capability, side); }
/*     */   public float getMinProcessDistance(MultiblockProcess<MultiblockRecipe> process) { return 0.0F; }
/*     */   public int getMaxProcessPerTick() { return 1; }
/* 302 */   public int getProcessQueueMaxLength() { return 1; } public boolean isStackValid(int slot, ItemStack stack) { return true; } public int getSlotLimit(int slot) { return 64; } public int[] getOutputSlots() { return null; } public int[] getOutputTanks() { return new int[] { 1 }; } public void doGraphicalUpdates() { m_6596_(); markContainingBlockForUpdate(null); } public MultiblockRecipe findRecipeForInsertion(ItemStack inserting) { return null; } protected MultiblockRecipe getRecipeForId(Level level, ResourceLocation id) { return null; } public NonNullList<ItemStack> getInventory() { return null; } public IFluidTank[] getInternalTanks() { return null; } private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(PumpjackTileEntity::getShape);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/* 307 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*     */   }
/*     */   
/*     */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/* 311 */     int x = posInMultiblock.m_123341_();
/* 312 */     int y = posInMultiblock.m_123342_();
/* 313 */     int z = posInMultiblock.m_123343_();
/*     */     
/* 315 */     List<AABB> main = new ArrayList<>();
/*     */ 
/*     */     
/* 318 */     if ((y == 3 && x == 1 && z != 2) || (x == 1 && y == 2 && z == 0)) {
/* 319 */       return main;
/*     */     }
/*     */ 
/*     */     
/* 323 */     if (y < 3 && x == 1 && z == 4) {
/* 324 */       if (y == 2) {
/* 325 */         AABBUtils.box16(main, 4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D);
/*     */       } else {
/* 327 */         AABBUtils.box16(main, 4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
/*     */       } 
/* 329 */       if (y == 0) {
/* 330 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 335 */     if (z == 2 && y > 0) {
/* 336 */       if (x == 0) {
/* 337 */         if (y == 1) {
/* 338 */           AABBUtils.box16(main, 11.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
/* 339 */           AABBUtils.box16(main, 11.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
/*     */         } 
/* 341 */         if (y == 2) {
/* 342 */           AABBUtils.box16(main, 13.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/* 343 */           AABBUtils.box16(main, 13.0D, 8.0D, 4.0D, 16.0D, 16.0D, 12.0D);
/*     */         } 
/* 345 */         if (y == 3) {
/* 346 */           AABBUtils.box16(main, 15.0D, 0.0D, 6.0D, 16.0D, 2.0D, 10.0D);
/*     */         }
/*     */       } 
/* 349 */       if (x == 1 && y == 3) {
/* 350 */         AABBUtils.box16(main, 0.0D, -2.0D, 6.0D, 16.0D, 2.0D, 10.0D);
/*     */       }
/* 352 */       if (x == 2) {
/* 353 */         if (y == 1) {
/* 354 */           AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 5.0D, 16.0D, 4.0D);
/* 355 */           AABBUtils.box16(main, 0.0D, 0.0D, 12.0D, 5.0D, 16.0D, 16.0D);
/*     */         } 
/* 357 */         if (y == 2) {
/* 358 */           AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 3.0D, 8.0D, 16.0D);
/* 359 */           AABBUtils.box16(main, 0.0D, 8.0D, 4.0D, 3.0D, 16.0D, 12.0D);
/*     */         } 
/* 361 */         if (y == 3) {
/* 362 */           AABBUtils.box16(main, 0.0D, 0.0D, 6.0D, 1.0D, 2.0D, 10.0D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 368 */     if (x == 0 && z == 5) {
/* 369 */       if (y == 0) {
/* 370 */         AABBUtils.box16(main, 12.0D, 0.0D, 10.0D, 14.0D, 16.0D, 14.0D);
/* 371 */         AABBUtils.box16(main, 2.0D, 0.0D, 10.0D, 4.0D, 16.0D, 14.0D);
/*     */       } 
/* 373 */       if (y == 1) {
/* 374 */         AABBUtils.box16(main, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 379 */     if (x == 2 && y == 0 && z == 5) {
/* 380 */       AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */     }
/*     */ 
/*     */     
/* 384 */     if (y == 0) {
/*     */ 
/*     */       
/* 387 */       if ((x != 2 || z != 5) && (z != 2 || (x != 0 && x != 2)) && x >= 0 && x <= 2 && z >= 1 && z <= 5) {
/* 388 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */       }
/*     */       
/* 391 */       if (z == 1 && (x == 0 || x == 2)) {
/* 392 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */         
/* 394 */         if (x == 0) {
/* 395 */           AABBUtils.box16(main, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/*     */         }
/* 397 */         if (x == 2) {
/* 398 */           AABBUtils.box16(main, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 403 */       if (z == 3 && (x == 0 || x == 2)) {
/* 404 */         if (x == 0) {
/* 405 */           AABBUtils.box16(main, 8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
/*     */         }
/* 407 */         if (x == 2) {
/* 408 */           AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
/*     */         }
/*     */       } 
/*     */       
/* 412 */       if (x == 1) {
/*     */         
/* 414 */         if (z == 0) {
/* 415 */           AABBUtils.box16(main, 5.0D, 8.0D, 13.0D, 11.0D, 14.0D, 16.0D);
/* 416 */           AABBUtils.box16(main, 3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/*     */         } 
/*     */ 
/*     */         
/* 420 */         if (z == 1) {
/* 421 */           AABBUtils.box16(main, 5.0D, 8.0D, 0.0D, 11.0D, 14.0D, 16.0D);
/*     */         }
/* 423 */         if (z == 2) {
/* 424 */           AABBUtils.box16(main, 5.0D, 8.0D, 0.0D, 11.0D, 14.0D, 11.0D);
/* 425 */           AABBUtils.box16(main, 0.0D, 8.0D, 5.0D, 16.0D, 14.0D, 11.0D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 431 */     if (main.isEmpty()) {
/* 432 */       main.add(AABBUtils.FULL);
/*     */     }
/* 434 */     return main;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\PumpjackTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */