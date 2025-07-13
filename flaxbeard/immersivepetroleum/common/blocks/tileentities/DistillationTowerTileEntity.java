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
/*     */ import blusunrize.immersiveengineering.common.util.Utils;
/*     */ import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
/*     */ import blusunrize.immersiveengineering.common.util.orientation.RelativeBlockFace;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*     */ import flaxbeard.immersivepetroleum.common.IPMenuTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.ICanSkipGUI;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.DistillationTowerMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.util.AABBUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.FluidHelper;
/*     */ import flaxbeard.immersivepetroleum.common.util.inventory.MultiFluidTankFiltered;
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
/*     */ import net.minecraft.world.ContainerHelper;
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
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.ItemHandlerHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistillationTowerTileEntity
/*     */   extends PoweredMultiblockBlockEntity<DistillationTowerTileEntity, DistillationTowerRecipe>
/*     */   implements IPCommonTickableTile, ICanSkipGUI, IPMenuProvider<DistillationTowerTileEntity>, IEBlockInterfaces.IBlockBounds
/*     */ {
/*     */   public static final int TANK_INPUT = 0;
/*     */   public static final int TANK_OUTPUT = 1;
/*     */   public static final int INV_0 = 0;
/*     */   public static final int INV_1 = 1;
/*     */   public static final int INV_2 = 2;
/*     */   public static final int INV_3 = 3;
/*  79 */   public static final BlockPos Fluid_IN = new BlockPos(3, 0, 3);
/*     */ 
/*     */   
/*  82 */   public static final BlockPos Fluid_OUT = new BlockPos(1, 0, 3);
/*     */ 
/*     */   
/*  85 */   public static final BlockPos Item_OUT = new BlockPos(0, 0, 1);
/*     */ 
/*     */   
/*  88 */   public static final Set<PoweredMultiblockBlockEntity.MultiblockFace> Energy_IN = (Set<PoweredMultiblockBlockEntity.MultiblockFace>)ImmutableSet.of(new PoweredMultiblockBlockEntity.MultiblockFace(3, 1, 3, RelativeBlockFace.UP));
/*     */ 
/*     */   
/*  91 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(0, 1, 3));
/*     */   
/*  93 */   public NonNullList<ItemStack> inventory = NonNullList.m_122780_(4, ItemStack.f_41583_);
/*     */   public final MultiFluidTank[] tanks;
/*     */   private int cooldownTicks;
/*     */   private boolean wasActive;
/*     */   private final MultiblockCapability<IFluidHandler> outputHandler;
/*     */   private final MultiblockCapability<IFluidHandler> inputHandler;
/*     */   
/*     */   public DistillationTowerTileEntity(BlockEntityType<DistillationTowerTileEntity> type, BlockPos pWorldPosition, BlockState pBlockState)
/*     */   {
/* 102 */     super((IETemplateMultiblock)DistillationTowerMultiblock.INSTANCE, 16000, true, type, pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.tanks = new MultiFluidTank[] { (MultiFluidTank)new MultiFluidTankFiltered(24000, fs -> Boolean.valueOf((DistillationTowerRecipe.findRecipe(fs) != null))), (MultiFluidTank)new MultiFluidTankFiltered(24000) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.cooldownTicks = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.wasActive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 450 */     this.outputHandler = MultiblockCapability.make(this, be -> be.outputHandler, MultiblockPartBlockEntity::master, 
/*     */         
/* 452 */         registerFluidOutput(new IFluidTank[] { (IFluidTank)this.tanks[1] }));
/*     */     
/* 454 */     this.inputHandler = MultiblockCapability.make(this, be -> be.inputHandler, MultiblockPartBlockEntity::master, 
/*     */         
/* 456 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.tanks[0] })); }
/*     */   public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); this.tanks[0].readFromNBT(nbt.m_128469_("tank0")); this.tanks[1].readFromNBT(nbt.m_128469_("tank1")); this.cooldownTicks = nbt.m_128451_("cooldownTicks"); if (!descPacket) this.inventory = readInventory(nbt.m_128469_("inventory"));  }
/*     */   public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128365_("tank0", (Tag)this.tanks[0].writeToNBT(new CompoundTag())); nbt.m_128365_("tank1", (Tag)this.tanks[1].writeToNBT(new CompoundTag())); nbt.m_128405_("cooldownTicks", this.cooldownTicks); if (!descPacket) nbt.m_128365_("inventory", (Tag)writeInventory(this.inventory));  }
/*     */   protected NonNullList<ItemStack> readInventory(CompoundTag nbt) { NonNullList<ItemStack> list = NonNullList.m_122779_(); ContainerHelper.m_18980_(nbt, list); if (list.size() == 0) { list = (this.inventory.size() == 4) ? this.inventory : NonNullList.m_122780_(4, ItemStack.f_41583_); } else if (list.size() < 4) { while (list.size() < 4) list.add(ItemStack.f_41583_);  }  return list; }
/*     */   protected CompoundTag writeInventory(NonNullList<ItemStack> list) { return ContainerHelper.m_18973_(new CompoundTag(), list); }
/*     */   public void tickClient() {}
/* 462 */   public void tickServer() { if (isDummy()) return;  if (this.cooldownTicks > 0) this.cooldownTicks--;  super.tickServer(); boolean update = false; if (!isRSDisabled()) { if (this.energyStorage.getEnergyStored() > 0 && this.processQueue.size() < getProcessQueueMaxLength() && this.tanks[0].getFluidAmount() > 0) { DistillationTowerRecipe recipe = DistillationTowerRecipe.findRecipe(this.tanks[0].getFluid()); if (recipe != null && this.tanks[0].getFluidAmount() >= recipe.getInputFluid().getAmount() && this.energyStorage.getEnergyStored() >= recipe.getTotalProcessEnergy() / recipe.getTotalProcessTime()) { MultiblockProcessInMachine<DistillationTowerRecipe> process = (new MultiblockProcessInMachine((MultiblockRecipe)recipe, this::getRecipeForId, new int[0])).setInputTanks(new int[] { 0 }); if (addProcessToQueue((MultiblockProcess)process, true)) { addProcessToQueue((MultiblockProcess)process, false); update = true; }  }  }  if (!this.processQueue.isEmpty()) { this.wasActive = true; this.cooldownTicks = 10; update = true; } else if (this.wasActive) { this.wasActive = false; update = true; }  super.tickServer(); }  if (this.inventory.get(0) != ItemStack.f_41583_ && this.tanks[0].getFluidAmount() < this.tanks[0].getCapacity()) { ItemStack emptyContainer = Utils.drainFluidContainer((IFluidHandler)this.tanks[0], (ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1)); if (!emptyContainer.m_41619_()) { if (!((ItemStack)this.inventory.get(1)).m_41619_() && ItemHandlerHelper.canItemStacksStack((ItemStack)this.inventory.get(1), emptyContainer)) { ((ItemStack)this.inventory.get(1)).m_41769_(emptyContainer.m_41613_()); } else if (((ItemStack)this.inventory.get(1)).m_41619_()) { this.inventory.set(1, emptyContainer.m_41777_()); }  ((ItemStack)this.inventory.get(0)).m_41774_(1); if (((ItemStack)this.inventory.get(0)).m_41613_() <= 0) this.inventory.set(0, ItemStack.f_41583_);  update = true; }  }  if (this.tanks[1].getFluidAmount() > 0) { if (this.inventory.get(2) != ItemStack.f_41583_) if (this.tanks[1].getFluidTypes() > 0) { MultiFluidTank outTank = this.tanks[1]; for (int i = outTank.getFluidTypes() - 1; i >= 0; i--) { FluidStack fs = outTank.getFluidInTank(i); if (fs.getAmount() > 0) { ItemStack filledContainer = FluidHelper.fillFluidContainer((IFluidTank)outTank, fs, (ItemStack)this.inventory.get(2), (ItemStack)this.inventory.get(3)); if (!filledContainer.m_41619_()) { if (((ItemStack)this.inventory.get(3)).m_41613_() == 1 && !FluidHelper.isFluidContainerFull(filledContainer)) { this.inventory.set(3, filledContainer.m_41777_()); } else { if (!((ItemStack)this.inventory.get(3)).m_41619_() && ItemHandlerHelper.canItemStacksStack((ItemStack)this.inventory.get(3), filledContainer)) { ((ItemStack)this.inventory.get(3)).m_41769_(filledContainer.m_41613_()); } else if (((ItemStack)this.inventory.get(3)).m_41619_()) { this.inventory.set(3, filledContainer.m_41777_()); }  ((ItemStack)this.inventory.get(2)).m_41774_(1); if (((ItemStack)this.inventory.get(2)).m_41613_() <= 0) this.inventory.set(2, ItemStack.f_41583_);  }  update = true; break; }  }  }  }   BlockPos outPos = getBlockPosForPos(Fluid_OUT).m_121945_(getFacing().m_122424_()); update |= ((Boolean)FluidUtil.getFluidHandler(this.f_58857_, outPos, getFacing()).map(output -> { boolean ret = false; if ((this.tanks[1]).fluids.size() > 0) { List<FluidStack> toDrain = new ArrayList<>(); boolean iePipe = this.f_58857_.m_7702_(outPos) instanceof blusunrize.immersiveengineering.api.fluid.IFluidPipe; for (FluidStack target : (this.tanks[1]).fluids) { FluidStack outStack = FluidHelper.copyFluid(target, Math.min(target.getAmount(), 100), iePipe); int accepted = output.fill(outStack, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { int drained = output.fill(FluidHelper.copyFluid(outStack, Math.min(outStack.getAmount(), accepted), iePipe), IFluidHandler.FluidAction.EXECUTE); toDrain.add(new FluidStack(target.getFluid(), drained)); ret = true; }  }  toDrain.forEach(()); }  return Boolean.valueOf(ret); }).orElse(Boolean.valueOf(false))).booleanValue(); }  if (update) updateMasterBlock(null, true);  } public NonNullList<ItemStack> getInventory() { return this.inventory; } public boolean isStackValid(int slot, ItemStack stack) { return true; } public int getSlotLimit(int slot) { return 64; } public void doGraphicalUpdates() { updateMasterBlock(null, true); } public DistillationTowerTileEntity getGuiMaster() { return (DistillationTowerTileEntity)master(); } @Nonnull public IPMenuProvider.BEContainerIP<? super DistillationTowerTileEntity, ?> getContainerType() { return IPMenuTypes.DISTILLATION_TOWER; } public boolean canUseGui(@Nonnull Player player) { return this.formed; } public boolean skipGui(Direction hitFace) { Direction facing = getFacing(); if (getEnergyPos().stream().anyMatch(t -> t.posInMultiblock().equals(this.posInMultiblock)) && hitFace == Direction.UP) return true;  if (getRedstonePos().contains(this.posInMultiblock) && hitFace == (getIsMirrored() ? facing.m_122427_() : facing.m_122428_())) return true;  if (this.posInMultiblock.equals(Fluid_IN) && hitFace == (getIsMirrored() ? facing.m_122428_() : facing.m_122427_())) return true;  if (this.posInMultiblock.equals(Fluid_OUT) && hitFace == facing.m_122424_()) return true;  if (this.posInMultiblock.equals(Item_OUT) && hitFace == (getIsMirrored() ? facing.m_122427_() : facing.m_122428_())) return true;  return false; } protected DistillationTowerRecipe getRecipeForId(Level level, ResourceLocation id) { return (DistillationTowerRecipe)DistillationTowerRecipe.recipes.get(id); } @Nonnull public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> capability, @Nullable Direction side) { if (capability == ForgeCapabilities.FLUID_HANDLER) {
/* 463 */       if (this.posInMultiblock.equals(Fluid_IN) && (
/* 464 */         side == null || (getIsMirrored() ? (side == getFacing().m_122428_()) : (side == getFacing().m_122427_())))) {
/* 465 */         return this.inputHandler.getAndCast();
/*     */       }
/*     */       
/* 468 */       if (this.posInMultiblock.equals(Fluid_OUT) && (side == null || side == getFacing().m_122424_())) {
/* 469 */         return this.outputHandler.getAndCast();
/*     */       }
/*     */     } 
/* 472 */     return super.getCapability(capability, side); }
/*     */   public Set<PoweredMultiblockBlockEntity.MultiblockFace> getEnergyPos() { return Energy_IN; }
/*     */   public Set<BlockPos> getRedstonePos() { return Redstone_IN; }
/*     */   public IFluidTank[] getInternalTanks() { return (IFluidTank[])this.tanks; }
/* 476 */   public DistillationTowerRecipe findRecipeForInsertion(ItemStack inserting) { return null; } public int[] getOutputSlots() { return null; } public int[] getOutputTanks() { return new int[] { 1 }; } public boolean additionalCanProcessCheck(MultiblockProcess<DistillationTowerRecipe> process) { int outputAmount = 0; for (FluidStack outputFluid : ((DistillationTowerRecipe)process.getRecipe(this.f_58857_)).getFluidOutputs()) outputAmount += outputFluid.getAmount();  return (this.tanks[1].getCapacity() >= this.tanks[1].getFluidAmount() + outputAmount); } public void doProcessOutput(ItemStack output) { Direction outputdir = getIsMirrored() ? getFacing().m_122427_() : getFacing().m_122428_(); BlockPos outputpos = getBlockPosForPos(Item_OUT).m_121945_(outputdir); BlockEntity te = this.f_58857_.m_7702_(outputpos); if (te != null) { IItemHandler handler = (IItemHandler)te.getCapability(ForgeCapabilities.ITEM_HANDLER, outputdir.m_122424_()).orElse(null); if (handler != null) output = ItemHandlerHelper.insertItem(handler, output, false);  }  if (!output.m_41619_()) { double x = outputpos.m_123341_() + 0.5D; double y = outputpos.m_123342_() + 0.25D; double z = outputpos.m_123343_() + 0.5D; Direction facing = getIsMirrored() ? getFacing().m_122424_() : getFacing(); if (facing != Direction.EAST && facing != Direction.WEST) x = outputpos.m_123341_() + ((facing == Direction.SOUTH) ? 0.15D : 0.85D);  if (facing != Direction.NORTH && facing != Direction.SOUTH) z = outputpos.m_123343_() + ((facing == Direction.WEST) ? 0.15D : 0.85D);  ItemEntity ei = new ItemEntity(this.f_58857_, x, y, z, output.m_41777_()); ei.m_20334_(0.075D * outputdir.m_122429_(), 0.025D, 0.075D * outputdir.m_122431_()); this.f_58857_.m_7967_((Entity)ei); }  } public void doProcessFluidOutput(FluidStack output) {} public void onProcessFinish(MultiblockProcess<DistillationTowerRecipe> process) {} public float getMinProcessDistance(MultiblockProcess<DistillationTowerRecipe> process) { return 1.0F; } public int getMaxProcessPerTick() { return 1; } public int getProcessQueueMaxLength() { return 1; } public boolean isInWorldProcessingMachine() { return false; } public boolean shouldRenderAsActiveImpl() { return (this.cooldownTicks > 0 || super.shouldRenderAsActiveImpl()); } public boolean isLadder() { return (this.posInMultiblock.m_123342_() > 0 && this.posInMultiblock.m_123341_() == 2 && this.posInMultiblock.m_123343_() == 0); }
/*     */ 
/*     */   
/* 479 */   private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(DistillationTowerTileEntity::getShape);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/* 484 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*     */   }
/*     */   
/*     */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/* 488 */     int x = posInMultiblock.m_123341_();
/* 489 */     int y = posInMultiblock.m_123342_();
/* 490 */     int z = posInMultiblock.m_123343_();
/*     */     
/* 492 */     List<AABB> main = new ArrayList<>();
/*     */ 
/*     */     
/* 495 */     if (y < 2 && 
/* 496 */       x == 0 && z == 3) {
/* 497 */       if (y == 1) {
/* 498 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
/*     */       } else {
/* 500 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/* 501 */         AABBUtils.box16(main, 2.0D, 0.0D, 12.0D, 6.0D, 16.0D, 14.0D);
/* 502 */         AABBUtils.box16(main, 2.0D, 0.0D, 2.0D, 6.0D, 16.0D, 4.0D);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 508 */     if (y == 2 && x == 3 && z == 2) {
/* 509 */       AABBUtils.box16(main, -1.0D, 6.0D, 2.0D, 1.0D, 18.0D, 14.0D);
/* 510 */       AABBUtils.box16(main, 2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
/* 511 */       AABBUtils.box16(main, 4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/* 512 */       AABBUtils.box16(main, 0.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*     */     } 
/*     */ 
/*     */     
/* 516 */     if (y > 0 && x == 1 && z == 3) {
/* 517 */       if (y != 15) {
/* 518 */         AABBUtils.box16(main, 3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/* 519 */         if (y > 0 && y % 4 == 0) {
/* 520 */           AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */         }
/*     */       } else {
/* 523 */         AABBUtils.box16(main, 3.0D, 0.0D, -1.0D, 13.0D, 10.0D, 13.0D);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 528 */     if (y > 0 && x == 2 && z == 0) {
/* 529 */       AABBUtils.box16(main, 1.0D, (y == 1) ? 2.0D : 0.0D, 14.0D, 15.0D, 16.0D, 17.0D);
/* 530 */       if (y > 0 && y % 4 == 0) {
/* 531 */         AABBUtils.box16(main, 0.0D, 8.0D, 14.0D, 16.0D, 16.0D, 17.0D);
/* 532 */         AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 537 */     if (x > 0 && x < 3 && z > 0 && z < 3) {
/* 538 */       if (y > 0) {
/*     */         
/* 540 */         AABB bb = AABBUtils.box16(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/* 541 */         if (z == 1) {
/* 542 */           if (x == 1) bb = AABBUtils.box16(1.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D); 
/* 543 */           if (x == 2) bb = AABBUtils.box16(0.0D, 0.0D, 1.0D, 15.0D, 16.0D, 16.0D); 
/* 544 */         } else if (z == 2) {
/* 545 */           if (x == 1) bb = AABBUtils.box16(1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D); 
/* 546 */           if (x == 2) bb = AABBUtils.box16(0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 15.0D); 
/*     */         } 
/* 548 */         main.add(bb);
/*     */       } else {
/*     */         
/* 551 */         AABBUtils.box16(main, -2.0D, 8.0D, -2.0D, 18.0D, 18.0D, 18.0D);
/* 552 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 557 */     if (y > 0 && y % 4 == 0 && (x != 2 || z != 0) && (x < 1 || x > 2 || z < 1 || z > 2)) {
/* 558 */       AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */     }
/*     */ 
/*     */     
/* 562 */     if (y == 0 && (
/* 563 */       x != 0 || z != 1) && (x != 1 || z != 3) && (x != 3 || z != 2) && (x != 3 || z != 3)) {
/* 564 */       AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 569 */     if (main.isEmpty()) {
/* 570 */       main.add(AABBUtils.FULL);
/*     */     }
/* 572 */     return main;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\DistillationTowerTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */