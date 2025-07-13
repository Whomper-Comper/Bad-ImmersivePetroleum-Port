/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
/*     */ import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
/*     */ import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcessInMachine;
/*     */ import blusunrize.immersiveengineering.common.util.MultiblockCapability;
/*     */ import blusunrize.immersiveengineering.common.util.orientation.RelativeBlockFace;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*     */ import flaxbeard.immersivepetroleum.common.IPMenuTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.ICanSkipGUI;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.HydroTreaterMultiblock;
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
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
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
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.ItemHandlerHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HydrotreaterTileEntity
/*     */   extends PoweredMultiblockBlockEntity<HydrotreaterTileEntity, HighPressureRefineryRecipe>
/*     */   implements IPCommonTickableTile, ICanSkipGUI, IPMenuProvider<HydrotreaterTileEntity>, IEBlockInterfaces.IBlockBounds
/*     */ {
/*     */   public static final int TANK_INPUT_A = 0;
/*     */   public static final int TANK_INPUT_B = 1;
/*     */   public static final int TANK_OUTPUT = 2;
/*  66 */   public static final BlockPos Fluid_IN_A = new BlockPos(1, 0, 3);
/*     */ 
/*     */   
/*  69 */   public static final BlockPos Fluid_IN_B = new BlockPos(2, 2, 1);
/*     */ 
/*     */   
/*  72 */   public static final BlockPos Fluid_OUT = new BlockPos(0, 1, 2);
/*     */ 
/*     */   
/*  75 */   public static final BlockPos Item_OUT = new BlockPos(0, 0, 2);
/*     */ 
/*     */   
/*  78 */   public static final Set<PoweredMultiblockBlockEntity.MultiblockFace> Energy_IN = (Set<PoweredMultiblockBlockEntity.MultiblockFace>)ImmutableSet.of(new PoweredMultiblockBlockEntity.MultiblockFace(2, 2, 3, RelativeBlockFace.UP));
/*     */ 
/*     */   
/*  81 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(0, 1, 3));
/*     */   
/*  83 */   public final FluidTank[] tanks = new FluidTank[] { new FluidTank(12000), new FluidTank(12000), new FluidTank(12000) };
/*     */   private final MultiblockCapability<IFluidHandler> inputAHandler;
/*  85 */   private final MultiblockCapability<IFluidHandler> inputBHandler; private final MultiblockCapability<IFluidHandler> outputHandler; public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); this.tanks[0].readFromNBT(nbt.m_128469_("tank0")); this.tanks[1].readFromNBT(nbt.m_128469_("tank1")); this.tanks[2].readFromNBT(nbt.m_128469_("tank2")); } public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128365_("tank0", (Tag)this.tanks[0].writeToNBT(new CompoundTag())); nbt.m_128365_("tank1", (Tag)this.tanks[1].writeToNBT(new CompoundTag())); nbt.m_128365_("tank2", (Tag)this.tanks[2].writeToNBT(new CompoundTag())); } protected HighPressureRefineryRecipe getRecipeForId(Level level, ResourceLocation id) { return (HighPressureRefineryRecipe)HighPressureRefineryRecipe.recipes.get(id); } public NonNullList<ItemStack> getInventory() { return null; } public boolean isStackValid(int slot, ItemStack stack) { return false; } public int getSlotLimit(int slot) { return 0; } public HydrotreaterTileEntity(BlockEntityType<HydrotreaterTileEntity> type, BlockPos pWorldPosition, BlockState pBlockState) { super((IETemplateMultiblock)HydroTreaterMultiblock.INSTANCE, 8000, true, type, pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     this.inputAHandler = MultiblockCapability.make(this, be -> be.inputAHandler, MultiblockPartBlockEntity::master, 
/* 306 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.tanks[0] }));
/*     */     
/* 308 */     this.inputBHandler = MultiblockCapability.make(this, be -> be.inputBHandler, MultiblockPartBlockEntity::master, 
/* 309 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.tanks[1] }));
/*     */     
/* 311 */     this.outputHandler = MultiblockCapability.make(this, be -> be.outputHandler, MultiblockPartBlockEntity::master, 
/* 312 */         registerFluidOutput(new IFluidTank[] { (IFluidTank)this.tanks[2] })); this.tanks[0].setValidator(fs -> HighPressureRefineryRecipe.hasRecipeWithInput(fs, true)); this.tanks[1].setValidator(fs -> HighPressureRefineryRecipe.hasRecipeWithSecondaryInput(fs, true)); }
/*     */   public void doGraphicalUpdates() { m_6596_();
/*     */     markContainingBlockForUpdate(null); }
/*     */   public Set<PoweredMultiblockBlockEntity.MultiblockFace> getEnergyPos() { return Energy_IN; }
/*     */   public Set<BlockPos> getRedstonePos() { return Redstone_IN; }
/*     */   public IFluidTank[] getInternalTanks() { return (IFluidTank[])this.tanks; }
/* 318 */   public HighPressureRefineryRecipe findRecipeForInsertion(ItemStack inserting) { return null; } @Nonnull public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> capability, @Nullable Direction side) { if (capability == ForgeCapabilities.FLUID_HANDLER) {
/* 319 */       if (this.posInMultiblock.equals(Fluid_IN_A) && (side == null || side == getFacing().m_122424_()))
/* 320 */         return this.inputAHandler.getAndCast(); 
/* 321 */       if (this.posInMultiblock.equals(Fluid_IN_B) && (side == null || side == Direction.UP))
/* 322 */         return this.inputBHandler.getAndCast(); 
/* 323 */       if (this.posInMultiblock.equals(Fluid_OUT) && (side == null || side == Direction.UP)) {
/* 324 */         return this.outputHandler.getAndCast();
/*     */       }
/*     */     } 
/* 327 */     return super.getCapability(capability, side); } public int[] getOutputSlots() { return null; } public int[] getOutputTanks() { return new int[] { 2 }; } public boolean additionalCanProcessCheck(MultiblockProcess<HighPressureRefineryRecipe> process) { int outputAmount = 0; for (FluidStack outputFluid : ((HighPressureRefineryRecipe)process.getRecipe(this.f_58857_)).getFluidOutputs()) outputAmount += outputFluid.getAmount();  return (this.tanks[2].getCapacity() >= this.tanks[2].getFluidAmount() + outputAmount); }
/*     */   public void doProcessOutput(ItemStack output) { if (output == null || output.m_41619_()) return;  Direction outputdir = getIsMirrored() ? getFacing().m_122427_() : getFacing().m_122428_(); BlockPos outputpos = getBlockPosForPos(Item_OUT).m_121945_(outputdir); BlockEntity te = this.f_58857_.m_7702_(outputpos); if (te != null) { IItemHandler handler = (IItemHandler)te.getCapability(ForgeCapabilities.ITEM_HANDLER, outputdir.m_122424_()).orElse(null); if (handler != null) output = ItemHandlerHelper.insertItem(handler, output, false);  }  if (!output.m_41619_()) { double x = outputpos.m_123341_() + 0.5D; double y = outputpos.m_123342_() + 0.25D; double z = outputpos.m_123343_() + 0.5D; Direction facing = getIsMirrored() ? getFacing().m_122424_() : getFacing(); if (facing != Direction.EAST && facing != Direction.WEST) x = outputpos.m_123341_() + ((facing == Direction.SOUTH) ? 0.15D : 0.85D);  if (facing != Direction.NORTH && facing != Direction.SOUTH)
/*     */         z = outputpos.m_123343_() + ((facing == Direction.WEST) ? 0.15D : 0.85D);  ItemEntity ei = new ItemEntity(this.f_58857_, x, y, z, output.m_41777_()); ei.m_20334_(0.075D * outputdir.m_122429_(), 0.025D, 0.075D * outputdir.m_122431_()); this.f_58857_.m_7967_((Entity)ei); }  }
/*     */   public void doProcessFluidOutput(FluidStack output) {}
/*     */   public void onProcessFinish(MultiblockProcess<HighPressureRefineryRecipe> process) {}
/* 332 */   public HydrotreaterTileEntity getGuiMaster() { return (HydrotreaterTileEntity)master(); } public void tickClient() {} public void tickServer() { boolean update = false; if (!isRSDisabled() && this.energyStorage.getEnergyStored() > 0 && this.processQueue.size() < getProcessQueueMaxLength() && (this.tanks[0].getFluidAmount() > 0 || this.tanks[1].getFluidAmount() > 0)) { HighPressureRefineryRecipe recipe = HighPressureRefineryRecipe.findRecipe(this.tanks[0].getFluid(), this.tanks[1].getFluid()); if (recipe != null && this.energyStorage.getEnergyStored() >= recipe.getTotalProcessEnergy() / recipe.getTotalProcessTime() && this.tanks[0].getFluidAmount() >= recipe.getInputFluid().getAmount() && (recipe.getSecondaryInputFluid() == null || this.tanks[1].getFluidAmount() >= recipe.getSecondaryInputFluid().getAmount())) { int[] inputs, inputAmounts; if (recipe.getSecondaryInputFluid() != null) { inputs = new int[] { 0, 1 }; inputAmounts = new int[] { recipe.getInputFluid().getAmount(), recipe.getSecondaryInputFluid().getAmount() }; } else { inputs = new int[] { 0 }; inputAmounts = new int[] { recipe.getInputFluid().getAmount() }; }  MultiblockProcessInMachine<HighPressureRefineryRecipe> process = (new MultiblockProcessInMachine((MultiblockRecipe)recipe, this::getRecipeForId, new int[0])).setInputTanks(inputs).setInputAmounts(inputAmounts); if (addProcessToQueue((MultiblockProcess)process, true)) { addProcessToQueue((MultiblockProcess)process, false); update = true; }  }  }  if (!this.processQueue.isEmpty())
/*     */       update = true;  super.tickServer(); if (this.tanks[2].getFluidAmount() > 0) { BlockPos outPos = getBlockPosForPos(Fluid_OUT).m_7494_(); update |= ((Boolean)FluidUtil.getFluidHandler(this.f_58857_, outPos, Direction.DOWN).map(output -> { boolean ret = false; FluidStack target = this.tanks[2].getFluid(); target = FluidHelper.copyFluid(target, Math.min(target.getAmount(), 1000)); int accepted = output.fill(target, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { int drained = output.fill(FluidHelper.copyFluid(target, Math.min(target.getAmount(), accepted)), IFluidHandler.FluidAction.EXECUTE); this.tanks[2].drain(new FluidStack(target.getFluid(), drained), IFluidHandler.FluidAction.EXECUTE); ret = true; }  return Boolean.valueOf(ret);
/*     */           }).orElse(Boolean.valueOf(false))).booleanValue(); }
/*     */      if (update)
/*     */       updateMasterBlock(null, true);  }
/*     */   public int getMaxProcessPerTick() { return 1; }
/* 338 */   @Nonnull public IPMenuProvider.BEContainerIP<? super HydrotreaterTileEntity, ?> getContainerType() { return IPMenuTypes.HYDROTREATER; } public int getProcessQueueMaxLength() { return 1; } public float getMinProcessDistance(MultiblockProcess<HighPressureRefineryRecipe> process) { return 1.0F; }
/*     */   public boolean isInWorldProcessingMachine() {
/*     */     return false;
/*     */   }
/*     */   public boolean canUseGui(@Nonnull Player player) {
/* 343 */     return this.formed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipGui(Direction hitFace) {
/* 348 */     Direction facing = getFacing();
/*     */ 
/*     */     
/* 351 */     if (getEnergyPos().stream().anyMatch(t -> t.posInMultiblock().equals(this.posInMultiblock)) && hitFace == Direction.UP) {
/* 352 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 356 */     if (getRedstonePos().contains(this.posInMultiblock) && hitFace == facing.m_122424_()) {
/* 357 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 361 */     if (this.posInMultiblock.equals(Fluid_IN_A) && hitFace == facing.m_122424_()) {
/* 362 */       return true;
/*     */     }
/* 364 */     if (this.posInMultiblock.equals(Fluid_IN_B) && hitFace == Direction.UP) {
/* 365 */       return true;
/*     */     }
/* 367 */     if (this.posInMultiblock.equals(Fluid_OUT) && hitFace == Direction.UP) {
/* 368 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 372 */     if (this.posInMultiblock.equals(Item_OUT) && hitFace == (getIsMirrored() ? facing.m_122427_() : facing.m_122428_())) {
/* 373 */       return true;
/*     */     }
/*     */     
/* 376 */     return false;
/*     */   }
/*     */   
/* 379 */   private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(HydrotreaterTileEntity::getShape);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/* 384 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*     */   }
/*     */   
/*     */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/* 388 */     int x = posInMultiblock.m_123341_();
/* 389 */     int y = posInMultiblock.m_123342_();
/* 390 */     int z = posInMultiblock.m_123343_();
/*     */     
/* 392 */     List<AABB> main = new ArrayList<>();
/*     */ 
/*     */     
/* 395 */     if (y == 0 && (x != 0 || z != 2) && (z != 3 || (x != 1 && x != 2))) {
/* 396 */       AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */     }
/*     */ 
/*     */     
/* 400 */     if (y == 0 && x == 0 && z == 3) {
/* 401 */       AABBUtils.box16(main, 12.0D, 8.0D, 10.0D, 14.0D, 16.0D, 14.0D);
/* 402 */       AABBUtils.box16(main, 2.0D, 8.0D, 10.0D, 4.0D, 16.0D, 14.0D);
/* 403 */     } else if (y == 1 && x == 0 && z == 3) {
/* 404 */       AABBUtils.box16(main, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/*     */     } 
/*     */ 
/*     */     
/* 408 */     if (x == 0) {
/*     */       
/* 410 */       if (y == 0) {
/* 411 */         if (z == 0) {
/* 412 */           AABBUtils.box16(main, 2.0D, 12.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/* 413 */           AABBUtils.box16(main, 4.0D, 8.0D, 12.0D, 14.0D, 12.0D, 16.0D);
/*     */         } 
/* 415 */         if (z == 1) {
/* 416 */           AABBUtils.box16(main, 2.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */         }
/* 418 */         if (z == 3) {
/* 419 */           AABBUtils.box16(main, 2.0D, 12.0D, 0.0D, 16.0D, 16.0D, 4.0D);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 425 */       if (y == 1) {
/* 426 */         if (z == 0) {
/* 427 */           AABBUtils.box16(main, 2.0D, 0.0D, 8.0D, 16.0D, 12.0D, 16.0D);
/*     */         }
/* 429 */         if (z == 1) {
/* 430 */           AABBUtils.box16(main, 2.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
/*     */         }
/* 432 */         if (z == 3) {
/* 433 */           AABBUtils.box16(main, 2.0D, 0.0D, 0.0D, 16.0D, 12.0D, 4.0D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     if (y == 0) {
/* 442 */       if (z == 0) {
/* 443 */         if (x == 1) {
/* 444 */           AABBUtils.box16(main, 2.0D, 5.0D, 1.0D, 6.0D, 16.0D, 5.0D);
/*     */         }
/* 446 */         if (x == 2) {
/* 447 */           AABBUtils.box16(main, 10.0D, 5.0D, 1.0D, 14.0D, 16.0D, 5.0D);
/*     */         }
/*     */       } 
/* 450 */       if (z == 1) {
/* 451 */         if (x == 1) {
/* 452 */           AABBUtils.box16(main, 2.0D, 5.0D, 16.0D, 6.0D, 16.0D, 16.0D);
/*     */         }
/* 454 */         if (x == 2) {
/* 455 */           AABBUtils.box16(main, 10.0D, 5.0D, 16.0D, 16.0D, 16.0D, 16.0D);
/*     */         }
/*     */       } 
/* 458 */       if (x == 2) {
/* 459 */         if (z == 1) AABBUtils.box16(main, 10.0D, 5.0D, 14.0D, 14.0D, 16.0D, 16.0D); 
/* 460 */         if (z == 2) AABBUtils.box16(main, 10.0D, 5.0D, 0.0D, 14.0D, 16.0D, 2.0D);
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 466 */     if (main.isEmpty()) {
/* 467 */       main.add(AABBUtils.FULL);
/*     */     }
/* 469 */     return main;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\HydrotreaterTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */