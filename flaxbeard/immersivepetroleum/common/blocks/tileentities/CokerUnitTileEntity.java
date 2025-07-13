/*      */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*      */ import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
/*      */ import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
/*      */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*      */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*      */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*      */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*      */ import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
/*      */ import blusunrize.immersiveengineering.common.util.MultiblockCapability;
/*      */ import blusunrize.immersiveengineering.common.util.ResettableCapability;
/*      */ import blusunrize.immersiveengineering.common.util.inventory.IEInventoryHandler;
/*      */ import blusunrize.immersiveengineering.common.util.inventory.IIEInventory;
/*      */ import blusunrize.immersiveengineering.common.util.orientation.RelativeBlockFace;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*      */ import flaxbeard.immersivepetroleum.common.IPMenuTypes;
/*      */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.ICanSkipGUI;
/*      */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*      */ import flaxbeard.immersivepetroleum.common.gui.CokerUnitContainer;
/*      */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*      */ import flaxbeard.immersivepetroleum.common.multiblocks.CokerUnitMultiblock;
/*      */ import flaxbeard.immersivepetroleum.common.util.AABBUtils;
/*      */ import flaxbeard.immersivepetroleum.common.util.FluidHelper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ResourceLocationException;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.NonNullList;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.ContainerHelper;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import net.minecraftforge.common.capabilities.Capability;
/*      */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*      */ import net.minecraftforge.common.util.LazyOptional;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.FluidUtil;
/*      */ import net.minecraftforge.fluids.IFluidTank;
/*      */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*      */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*      */ import net.minecraftforge.items.IItemHandler;
/*      */ import net.minecraftforge.items.ItemHandlerHelper;
/*      */ 
/*      */ public class CokerUnitTileEntity extends PoweredMultiblockBlockEntity<CokerUnitTileEntity, CokerUnitRecipe> implements IPCommonTickableTile, ICanSkipGUI, IPMenuProvider<CokerUnitTileEntity>, IEBlockInterfaces.IBlockBounds {
/*      */   public static final int TANK_INPUT = 0;
/*      */   public static final int TANK_OUTPUT = 1;
/*      */   public static final int CHAMBER_A = 0;
/*      */   public static final int CHAMBER_B = 1;
/*      */   
/*      */   public enum Inventory {
/*   69 */     INPUT,
/*      */     
/*   71 */     INPUT_FILLED,
/*      */     
/*   73 */     INPUT_EMPTY,
/*      */     
/*   75 */     OUTPUT_EMPTY,
/*      */     
/*   77 */     OUTPUT_FILLED;
/*      */     
/*      */     public int id() {
/*   80 */       return ordinal();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   public static final BlockPos Chamber_A_OUT = new BlockPos(2, 0, 2);
/*      */ 
/*      */   
/*  100 */   public static final BlockPos Chamber_B_OUT = new BlockPos(6, 0, 2);
/*      */ 
/*      */   
/*  103 */   public static final BlockPos Fluid_IN = new BlockPos(2, 0, 4);
/*      */ 
/*      */   
/*  106 */   public static final BlockPos Fluid_OUT = new BlockPos(5, 0, 4);
/*      */ 
/*      */   
/*  109 */   public static final BlockPos Item_IN = new BlockPos(3, 0, 4);
/*      */ 
/*      */   
/*  112 */   public static final Set<PoweredMultiblockBlockEntity.MultiblockFace> Energy_IN = (Set<PoweredMultiblockBlockEntity.MultiblockFace>)ImmutableSet.of(new PoweredMultiblockBlockEntity.MultiblockFace(6, 1, 4, RelativeBlockFace.FRONT), new PoweredMultiblockBlockEntity.MultiblockFace(7, 1, 4, RelativeBlockFace.FRONT));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   public static final Set<BlockPos> Redstone_IN = (Set<BlockPos>)ImmutableSet.of(new BlockPos(1, 1, 4));
/*      */   
/*  120 */   public final NonNullList<ItemStack> inventory = NonNullList.m_122780_((Inventory.values()).length, ItemStack.f_41583_);
/*  121 */   public final FluidTank[] bufferTanks = new FluidTank[] { new FluidTank(16000), new FluidTank(16000) };
/*  122 */   public final CokingChamber[] chambers = new CokingChamber[] { new CokingChamber(64, 8000), new CokingChamber(64, 8000) }; private final MultiblockCapability<IItemHandler> insertionHandler; private final MultiblockCapability<IFluidHandler> fluidOutHandler; private final MultiblockCapability<IFluidHandler> fluidInHandler; int updateDelay; int lastCompared;
/*      */   
/*  124 */   public CokerUnitTileEntity(BlockEntityType<CokerUnitTileEntity> type, BlockPos pWorldPosition, BlockState pBlockState) { super((IETemplateMultiblock)CokerUnitMultiblock.INSTANCE, 24000, true, type, pWorldPosition, pBlockState);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  195 */     this.insertionHandler = MultiblockCapability.make(this, be -> be.insertionHandler, MultiblockPartBlockEntity::master, new ResettableCapability(new IEInventoryHandler(1, (IIEInventory)this, 0, new boolean[] { true }, new boolean[8])));
/*      */ 
/*      */ 
/*      */     
/*  199 */     this.fluidOutHandler = MultiblockCapability.make(this, be -> be.fluidOutHandler, MultiblockPartBlockEntity::master, 
/*      */         
/*  201 */         registerFluidOutput(new IFluidTank[] { (IFluidTank)this.bufferTanks[1] }));
/*      */     
/*  203 */     this.fluidInHandler = MultiblockCapability.make(this, be -> be.fluidInHandler, MultiblockPartBlockEntity::master, 
/*      */         
/*  205 */         registerFluidInput(new IFluidTank[] { (IFluidTank)this.bufferTanks[0] }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  378 */     this.updateDelay = 0;
/*  379 */     this.lastCompared = 0; this.bufferTanks[0].setValidator(fs -> CokerUnitRecipe.hasRecipeWithInput(fs, true)); } public void readCustomNBT(CompoundTag nbt, boolean descPacket) { super.readCustomNBT(nbt, descPacket); this.bufferTanks[0].readFromNBT(nbt.m_128469_("tank0")); this.bufferTanks[1].readFromNBT(nbt.m_128469_("tank1")); this.chambers[0].readFromNBT(nbt.m_128469_("chamber0")); this.chambers[1].readFromNBT(nbt.m_128469_("chamber1")); if (!descPacket) readInventory(nbt.m_128469_("inventory"));  } public void writeCustomNBT(CompoundTag nbt, boolean descPacket) { super.writeCustomNBT(nbt, descPacket); nbt.m_128365_("tank0", (Tag)this.bufferTanks[0].writeToNBT(new CompoundTag())); nbt.m_128365_("tank1", (Tag)this.bufferTanks[1].writeToNBT(new CompoundTag())); nbt.m_128365_("chamber0", (Tag)this.chambers[0].writeToNBT(new CompoundTag())); nbt.m_128365_("chamber1", (Tag)this.chambers[1].writeToNBT(new CompoundTag())); if (!descPacket) nbt.m_128365_("inventory", (Tag)writeInventory(this.inventory));  } protected void readInventory(CompoundTag nbt) { NonNullList<ItemStack> list = NonNullList.m_122779_(); ContainerHelper.m_18980_(nbt, list); for (int i = 0; i < this.inventory.size(); i++) { ItemStack stack = ItemStack.f_41583_; if (i < list.size()) stack = (ItemStack)list.get(i);  this.inventory.set(i, stack); }  } protected CompoundTag writeInventory(NonNullList<ItemStack> list) { return ContainerHelper.m_18973_(new CompoundTag(), list); } public void doGraphicalUpdates() { updateMasterBlock(null, true); }
/*      */   public CokerUnitRecipe findRecipeForInsertion(ItemStack inserting) { return null; }
/*  381 */   private void updateComparatorOutput() { boolean update = false;
/*      */     
/*  383 */     ItemStack stack = getInventory(Inventory.INPUT);
/*  384 */     if (!stack.m_41619_()) {
/*  385 */       int compared = Mth.m_14045_(Mth.m_14143_(stack.m_41613_() / Math.min(getSlotLimit(Inventory.INPUT.id()), stack.m_41741_()) * 15.0F), 0, 15);
/*  386 */       if (compared != this.lastCompared) {
/*  387 */         this.lastCompared = compared;
/*  388 */         update = true;
/*      */       } 
/*  390 */     } else if (this.lastCompared != 0) {
/*  391 */       this.lastCompared = 0;
/*  392 */       update = true;
/*      */     } 
/*      */     
/*  395 */     if (update)
/*  396 */       getRedstonePos().forEach(pos -> {
/*      */             BlockPos p = getBlockPosForPos(pos); this.f_58857_.m_46672_(p, this.f_58857_.m_8055_(p).m_60734_());
/*      */           });  }
/*      */   public boolean additionalCanProcessCheck(MultiblockProcess<CokerUnitRecipe> process) { return false; }
/*      */   public void doProcessOutput(ItemStack output) {} @Nonnull public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> capability, @Nullable Direction facing) { if ((facing == null || this.posInMultiblock.equals(Item_IN)) && capability == ForgeCapabilities.ITEM_HANDLER)
/*      */       return this.insertionHandler.getAndCast();  if (capability == ForgeCapabilities.FLUID_HANDLER) {
/*      */       if (this.posInMultiblock.equals(Fluid_OUT) && (facing == null || facing == getFacing().m_122424_()))
/*      */         return this.fluidOutHandler.getAndCast();  if (this.posInMultiblock.equals(Fluid_IN) && (facing == null || facing == getFacing().m_122424_()))
/*      */         return this.fluidInHandler.getAndCast(); 
/*  405 */     }  return super.getCapability(capability, facing); } public int getComparatorInputOverride() { if (isRedstonePos()) {
/*  406 */       CokerUnitTileEntity master = (CokerUnitTileEntity)master();
/*  407 */       if (master != null) {
/*  408 */         return master.lastCompared;
/*      */       }
/*      */     } 
/*  411 */     return 0; }
/*      */   public void doProcessFluidOutput(FluidStack output) {}
/*      */   public void onProcessFinish(MultiblockProcess<CokerUnitRecipe> process) {} public void tickClient() { if (isDummy() || isRSDisabled()) return;  boolean debug = false; for (int i = 0; i < this.chambers.length; i++) { if (debug || this.chambers[i].getState() == CokingState.DUMPING) { BlockPos cOutPos = getBlockPosForPos((i == 0) ? Chamber_A_OUT : Chamber_B_OUT); Vec3 origin = new Vec3(cOutPos.m_123341_() + 0.5D, cOutPos.m_123342_() + 2.125D, cOutPos.m_123343_() + 0.5D); for (int j = 0; j < 10; j++) { double rX = (Math.random() - 0.5D) * 0.4D; double rY = (Math.random() - 0.5D) * 0.5D; double rdx = (Math.random() - 0.5D) * 0.1D; double rdy = (Math.random() - 0.5D) * 0.1D; this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, origin.f_82479_ + rX, origin.f_82480_, origin.f_82481_ + rY, rdx, -(Math.random() * 0.06D + 0.11D), rdy); }  }  }  } public void tickServer() { if (isDummy()) return;  super.tickServer(); boolean update = false; if (!isRSDisabled()) { ItemStack inputStack = getInventory(Inventory.INPUT); FluidStack inputFluid = this.bufferTanks[0].getFluid(); if (!inputStack.m_41619_() && inputFluid.getAmount() > 0 && CokerUnitRecipe.hasRecipeWithInput(inputStack, inputFluid)) { CokerUnitRecipe recipe = CokerUnitRecipe.findRecipe(inputStack, inputFluid); if (recipe != null && inputStack.m_41613_() >= recipe.inputItem.getCount() && inputFluid.getAmount() >= recipe.inputFluid.getAmount()) for (CokingChamber chamber : this.chambers) { int acceptedStack; boolean skipNext = false; switch (chamber.getState()) { case STANDBY: if (chamber.setRecipe(recipe)) { update = true; skipNext = true; }  break;case PROCESSING: acceptedStack = chamber.addStack(copyStack(inputStack, recipe.inputItem.getCount()), true); if (acceptedStack >= recipe.inputItem.getCount()) { acceptedStack = Math.min(acceptedStack, inputStack.m_41613_()); chamber.addStack(copyStack(inputStack, acceptedStack), false); inputStack.m_41774_(acceptedStack); skipNext = true; update = true; }  break; }  if (skipNext) break;  }   }  for (int i = 0; i < this.chambers.length; i++) update |= this.chambers[i].tick(this, i);  }  if (!getInventory(Inventory.INPUT_FILLED).m_41619_() && this.bufferTanks[0].getFluidAmount() < this.bufferTanks[0].getCapacity()) { ItemStack container = Utils.drainFluidContainer((IFluidHandler)this.bufferTanks[0], getInventory(Inventory.INPUT_FILLED), getInventory(Inventory.INPUT_EMPTY)); if (!container.m_41619_()) { if (!getInventory(Inventory.INPUT_EMPTY).m_41619_() && ItemHandlerHelper.canItemStacksStack(getInventory(Inventory.INPUT_EMPTY), container)) { getInventory(Inventory.INPUT_EMPTY).m_41769_(container.m_41613_()); } else if (getInventory(Inventory.INPUT_EMPTY).m_41619_()) { setInventory(Inventory.INPUT_EMPTY, container.m_41777_()); }  getInventory(Inventory.INPUT_FILLED).m_41774_(1); if (getInventory(Inventory.INPUT_FILLED).m_41613_() <= 0) setInventory(Inventory.INPUT_FILLED, ItemStack.f_41583_);  update = true; }  }  if (this.bufferTanks[1].getFluidAmount() > 0) { if (!getInventory(Inventory.OUTPUT_EMPTY).m_41619_()) { ItemStack filledContainer = FluidHelper.fillFluidContainer((IFluidHandler)this.bufferTanks[1], getInventory(Inventory.OUTPUT_EMPTY), getInventory(Inventory.OUTPUT_FILLED), null); if (!filledContainer.m_41619_()) { if (getInventory(Inventory.OUTPUT_FILLED).m_41613_() == 1 && !FluidHelper.isFluidContainerFull(filledContainer)) { setInventory(Inventory.OUTPUT_FILLED, filledContainer.m_41777_()); } else { if (!getInventory(Inventory.OUTPUT_FILLED).m_41619_() && ItemHandlerHelper.canItemStacksStack(getInventory(Inventory.OUTPUT_FILLED), filledContainer)) { getInventory(Inventory.OUTPUT_FILLED).m_41769_(filledContainer.m_41613_()); } else if (getInventory(Inventory.OUTPUT_FILLED).m_41619_()) { setInventory(Inventory.OUTPUT_FILLED, filledContainer.m_41777_()); }  getInventory(Inventory.OUTPUT_EMPTY).m_41774_(1); if (getInventory(Inventory.OUTPUT_EMPTY).m_41613_() <= 0)
/*      */               setInventory(Inventory.OUTPUT_EMPTY, ItemStack.f_41583_);  }  update = true; }  }  BlockPos outPos = getBlockPosForPos(Fluid_OUT).m_121945_(getFacing().m_122424_()); update |= ((Boolean)FluidUtil.getFluidHandler(this.f_58857_, outPos, getFacing()).map(out -> { if (this.bufferTanks[1].getFluidAmount() > 0) { FluidStack fs = this.bufferTanks[1].getFluid(); fs = FluidHelper.copyFluid(fs, Math.min(fs.getAmount(), 250)); int accepted = out.fill(fs, IFluidHandler.FluidAction.SIMULATE); if (accepted > 0) { boolean iePipe = this.f_58857_.m_7702_(outPos) instanceof blusunrize.immersiveengineering.api.fluid.IFluidPipe; int drained = out.fill(FluidHelper.copyFluid(fs, Math.min(fs.getAmount(), accepted), iePipe), IFluidHandler.FluidAction.EXECUTE); this.bufferTanks[1].drain(FluidHelper.copyFluid(fs, drained), IFluidHandler.FluidAction.EXECUTE); return Boolean.valueOf(true); }  }  return Boolean.valueOf(false); }).orElse(Boolean.valueOf(false))).booleanValue(); }  if (update)
/*  415 */       updateMasterBlock(null, true);  updateComparatorOutput(); } private ItemStack copyStack(ItemStack stack, int amount) { ItemStack copy = stack.m_41777_();
/*  416 */     copy.m_41764_(amount);
/*  417 */     return copy; }
/*      */ 
/*      */   
/*      */   public ItemStack getInventory(Inventory inv) {
/*  421 */     return (ItemStack)this.inventory.get(inv.id());
/*      */   }
/*      */   
/*      */   public ItemStack setInventory(Inventory inv, ItemStack stack) {
/*  425 */     return (ItemStack)this.inventory.set(inv.id(), stack);
/*      */   }
/*      */ 
/*      */   
/*      */   public NonNullList<ItemStack> getInventory() {
/*  430 */     return this.inventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSlotLimit(int slot) {
/*  435 */     return 64;
/*      */   }
/*      */ 
/*      */   
/*      */   protected CokerUnitRecipe getRecipeForId(Level level, ResourceLocation id) {
/*  440 */     return (CokerUnitRecipe)CokerUnitRecipe.recipes.get(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<PoweredMultiblockBlockEntity.MultiblockFace> getEnergyPos() {
/*  445 */     return Energy_IN;
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<BlockPos> getRedstonePos() {
/*  450 */     return Redstone_IN;
/*      */   }
/*      */ 
/*      */   
/*      */   public IFluidTank[] getInternalTanks() {
/*  455 */     return (IFluidTank[])this.bufferTanks;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] getOutputSlots() {
/*  460 */     return new int[0];
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] getOutputTanks() {
/*  465 */     return new int[] { 1 };
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxProcessPerTick() {
/*  470 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getProcessQueueMaxLength() {
/*  475 */     return 2;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMinProcessDistance(MultiblockProcess<CokerUnitRecipe> process) {
/*  480 */     return 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getComparatedSize() {
/*  485 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public CokerUnitTileEntity getGuiMaster() {
/*  490 */     return (CokerUnitTileEntity)master();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUseGui(@Nonnull Player player) {
/*  495 */     return this.formed;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean skipGui(Direction hitFace) {
/*  500 */     Direction facing = getFacing();
/*      */ 
/*      */     
/*  503 */     if (getEnergyPos().stream().anyMatch(t -> t.posInMultiblock().equals(this.posInMultiblock)) && hitFace == facing) {
/*  504 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  508 */     if (getRedstonePos().contains(this.posInMultiblock) && hitFace == facing.m_122424_()) {
/*  509 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  513 */     if (this.posInMultiblock.equals(Fluid_IN) || this.posInMultiblock.equals(Fluid_OUT)) {
/*  514 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  518 */     if (this.posInMultiblock.equals(Item_IN)) {
/*  519 */       return true;
/*      */     }
/*      */     
/*  522 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public IPMenuProvider.BEContainerIP<CokerUnitTileEntity, CokerUnitContainer> getContainerType() {
/*  528 */     return IPMenuTypes.COKER;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInWorldProcessingMachine() {
/*  533 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isStackValid(int slot, ItemStack stack) {
/*  538 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isLadder() {
/*  542 */     int bX = this.posInMultiblock.m_123341_();
/*  543 */     int bY = this.posInMultiblock.m_123342_();
/*  544 */     int bZ = this.posInMultiblock.m_123343_();
/*      */ 
/*      */     
/*  547 */     if (bX == 8 && bZ == 2 && bY >= 5 && bY <= 13) {
/*  548 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  552 */     if (bX == 7 && bZ == 2 && bY >= 15 && bY <= 21) {
/*  553 */       return true;
/*      */     }
/*      */     
/*  556 */     return false;
/*      */   }
/*      */   
/*  559 */   private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(CokerUnitTileEntity::getShape);
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public VoxelShape getBlockBounds(CollisionContext ctx) {
/*  564 */     return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), Boolean.valueOf(getIsMirrored())));
/*      */   }
/*      */   
/*      */   private static List<AABB> getShape(BlockPos posInMultiblock) {
/*  568 */     int x = posInMultiblock.m_123341_();
/*  569 */     int y = posInMultiblock.m_123342_();
/*  570 */     int z = posInMultiblock.m_123343_();
/*      */     
/*  572 */     List<AABB> main = new ArrayList<>();
/*      */     
/*  574 */     if (y >= 15 && y <= 22 && z == 2 && (x == 2 || x == 6))
/*      */     {
/*  576 */       AABBUtils.box16(main, 4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*      */     }
/*      */ 
/*      */     
/*  580 */     if (y == 22 || y == 18) {
/*  581 */       if (x == 1) {
/*  582 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*      */       }
/*  584 */       if (x == 7) {
/*  585 */         AABBUtils.box16(main, 15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*      */       }
/*  587 */       if (z == 1) {
/*  588 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*      */       }
/*  590 */       if (z == 3) {
/*  591 */         AABBUtils.box16(main, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/*      */       }
/*      */     } 
/*  594 */     if ((y == 14 || y == 9) && 
/*  595 */       x >= 0 && x <= 8) {
/*  596 */       if (z == 0) {
/*  597 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*      */       }
/*  599 */       if (z == 4) {
/*  600 */         AABBUtils.box16(main, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/*      */       }
/*      */       
/*  603 */       if (x == 8 && (z == 0 || z == 4)) {
/*  604 */         AABBUtils.box16(main, 15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*      */       }
/*      */       
/*  607 */       if (x == 0 && (z == 0 || z == 4)) {
/*  608 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  618 */     if ((y == 8 || y == 13) && (
/*  619 */       x < 1 || x > 3 || z < 1 || z > 3) && (x < 5 || x > 7 || z < 1 || z > 3) && (
/*  620 */       x != 8 || z != 2)) {
/*  621 */       AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  629 */     if ((y == 17 || y == 21) && 
/*  630 */       x >= 1 && x <= 7 && z >= 1 && z <= 3 && ((
/*  631 */       x != 2 && x != 6 && x != 7) || z != 2)) {
/*  632 */       AABBUtils.box16(main, 0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  642 */     boolean lower = (y >= 4 && y <= 13);
/*  643 */     boolean upper = (y >= 14 && y <= 21);
/*  644 */     if (lower || upper) {
/*      */       
/*  646 */       if ((lower && x == 0 && z == 0) || (upper && ((x == 1 && z == 1) || (x == 5 && z == 1)))) {
/*  647 */         AABBUtils.box16(main, 2.0D, 0.0D, 2.0D, 6.0D, 16.0D, 6.0D);
/*      */       }
/*  649 */       if ((lower && x == 0 && z == 4) || (upper && ((x == 1 && z == 3) || (x == 5 && z == 3)))) {
/*  650 */         AABBUtils.box16(main, 2.0D, 0.0D, 10.0D, 6.0D, 16.0D, 14.0D);
/*      */       }
/*  652 */       if ((lower && x == 8 && z == 0) || (upper && ((x == 3 && z == 1) || (x == 7 && z == 1)))) {
/*  653 */         AABBUtils.box16(main, 10.0D, 0.0D, 2.0D, 14.0D, 16.0D, 6.0D);
/*      */       }
/*  655 */       if ((lower && x == 8 && z == 4) || (upper && ((x == 3 && z == 3) || (x == 7 && z == 3)))) {
/*  656 */         AABBUtils.box16(main, 10.0D, 0.0D, 10.0D, 14.0D, 16.0D, 14.0D);
/*      */       }
/*      */ 
/*      */       
/*  660 */       if (lower && x == 4 && z == 0) {
/*  661 */         AABBUtils.box16(main, 6.0D, 0.0D, 2.0D, 10.0D, 16.0D, 6.0D);
/*      */       }
/*  663 */       if (lower && x == 4 && z == 4) {
/*  664 */         AABBUtils.box16(main, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D, 14.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  669 */     if (y == 6 || y == 11) {
/*      */       
/*  671 */       if (x >= 0 && x <= 8) {
/*  672 */         if (z == 0) {
/*  673 */           double xa = (x == 0) ? 2.0D : 0.0D;
/*  674 */           double xb = (x == 8) ? 14.0D : 16.0D;
/*  675 */           AABBUtils.box16(main, xa, 2.0D, 2.0D, xb, 6.0D, 6.0D);
/*      */         } 
/*  677 */         if (z == 4) {
/*  678 */           double xa = (x == 0) ? 2.0D : 0.0D;
/*  679 */           double xb = (x == 8) ? 14.0D : 16.0D;
/*  680 */           AABBUtils.box16(main, xa, 2.0D, 10.0D, xb, 6.0D, 14.0D);
/*      */         } 
/*      */       } 
/*  683 */       if (z >= 0 && z <= 4) {
/*  684 */         if (x == 0) {
/*  685 */           double za = (z == 0) ? 2.0D : 0.0D;
/*  686 */           double zb = (z == 4) ? 14.0D : 16.0D;
/*  687 */           AABBUtils.box16(main, 2.0D, 2.0D, za, 6.0D, 6.0D, zb);
/*      */         } 
/*  689 */         if (x == 8 && z != 2) {
/*  690 */           double za = (z == 0) ? 2.0D : 0.0D;
/*  691 */           double zb = (z == 4) ? 14.0D : 16.0D;
/*  692 */           AABBUtils.box16(main, 10.0D, 2.0D, za, 14.0D, 6.0D, zb);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  701 */     boolean u = false, d = false, n = false, s = false, e = false, w = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     boolean v = false, hX = false, hZ = false;
/*      */     
/*  708 */     if ((y >= 1 && y <= 7 && y != 3 && x == 2 && z == 4) || (y >= 9 && y <= 13 && x == 0 && z == 2) || (y == 1 && x == 5 && z == 4)) {
/*  709 */       v = true;
/*      */       
/*  711 */       if (y == 2 || y == 7 || (x == 5 && z == 4)) {
/*  712 */         u = true;
/*      */       }
/*  714 */       if (y == 1 || y == 4 || y == 9) {
/*  715 */         d = true;
/*      */       }
/*      */     } 
/*      */     
/*  719 */     if (y == 0 && 
/*  720 */       x == 3 && z == 3) {
/*  721 */       hZ = n = s = true;
/*      */     }
/*      */ 
/*      */     
/*  725 */     if (y == 1 && 
/*  726 */       x == 4 && z == 2) {
/*  727 */       v = u = d = true;
/*      */     }
/*      */     
/*  730 */     if (y == 2) {
/*  731 */       if (x == 3 && z == 3) {
/*  732 */         hX = w = true;
/*      */       }
/*  734 */       if (x == 4 && z == 3) {
/*  735 */         hX = e = true;
/*      */       }
/*  737 */       if ((x == 3 || x == 5) && z == 2) {
/*  738 */         hX = e = w = true;
/*      */       }
/*  740 */       if (x == 4 && z == 2) {
/*  741 */         hX = e = w = d = true;
/*  742 */         AABBUtils.box16(main, 4.0D, 2.0D, 4.0D, 12.0D, 4.0D, 12.0D);
/*      */       } 
/*  744 */       if (x == 5 && z == 3) {
/*  745 */         hX = e = w = s = true;
/*  746 */         AABBUtils.box16(main, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D, 14.0D);
/*      */       } 
/*      */     } 
/*  749 */     if (y == 8) {
/*  750 */       if (x == 1 && z == 4) {
/*  751 */         hX = e = w = true;
/*      */       }
/*  753 */       if (x == 0 && z == 3) {
/*  754 */         hZ = n = s = true;
/*      */       }
/*      */     } 
/*      */     
/*  758 */     if (v) AABBUtils.box16(main, 4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D); 
/*  759 */     if (hX) AABBUtils.box16(main, 0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D); 
/*  760 */     if (hZ) AABBUtils.box16(main, 4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     if (y == 0) {
/*  766 */       if (x == 4 && z == 2) {
/*  767 */         u = w = true;
/*  768 */         AABBUtils.box16(main, 2.0D, 4.0D, 4.0D, 12.0D, 14.0D, 12.0D);
/*      */       } 
/*  770 */       if (x == 3 && z == 2) {
/*  771 */         e = s = true;
/*  772 */         AABBUtils.box16(main, 4.0D, 4.0D, 4.0D, 14.0D, 12.0D, 14.0D);
/*      */       } 
/*      */     } 
/*  775 */     if (y == 2) {
/*  776 */       if (x == 2 && z == 3) {
/*  777 */         n = e = true;
/*  778 */         AABBUtils.box16(main, 4.0D, 4.0D, 2.0D, 14.0D, 12.0D, 12.0D);
/*      */       } 
/*  780 */       if (x == 5 && z == 4) {
/*  781 */         d = n = true;
/*  782 */         AABBUtils.box16(main, 4.0D, 2.0D, 2.0D, 12.0D, 12.0D, 12.0D);
/*      */       } 
/*  784 */       if (x == 6 && z == 3) {
/*  785 */         w = n = true;
/*  786 */         AABBUtils.box16(main, 2.0D, 4.0D, 2.0D, 12.0D, 12.0D, 12.0D);
/*      */       } 
/*      */     } 
/*  789 */     if (y == 8) {
/*  790 */       if (x == 0 && z == 2) {
/*  791 */         u = s = true;
/*  792 */         AABBUtils.box16(main, 4.0D, 4.0D, 4.0D, 12.0D, 14.0D, 14.0D);
/*      */       } 
/*  794 */       if (x == 0 && z == 4) {
/*  795 */         n = e = true;
/*  796 */         AABBUtils.box16(main, 4.0D, 4.0D, 2.0D, 14.0D, 12.0D, 12.0D);
/*      */       } 
/*  798 */       if (x == 2 && z == 4) {
/*  799 */         d = w = true;
/*  800 */         AABBUtils.box16(main, 2.0D, 2.0D, 4.0D, 12.0D, 12.0D, 12.0D);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  805 */     if (u) AABBUtils.box16(main, 2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D); 
/*  806 */     if (d) AABBUtils.box16(main, 2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D); 
/*  807 */     if (n) AABBUtils.box16(main, 2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 2.0D); 
/*  808 */     if (s) AABBUtils.box16(main, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D, 16.0D); 
/*  809 */     if (e) AABBUtils.box16(main, 14.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D); 
/*  810 */     if (w) AABBUtils.box16(main, 0.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D);
/*      */ 
/*      */ 
/*      */     
/*  814 */     if (x == 1 && z == 4) {
/*  815 */       if (y == 0) {
/*  816 */         AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*  817 */         AABBUtils.box16(main, 12.0D, 0.0D, 10.0D, 14.0D, 16.0D, 14.0D);
/*  818 */         AABBUtils.box16(main, 2.0D, 0.0D, 10.0D, 4.0D, 16.0D, 14.0D);
/*      */       } 
/*  820 */       if (y == 1) {
/*  821 */         AABBUtils.box16(main, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  826 */     if (y == 1 && z == 4 && (x == 6 || x == 7)) {
/*  827 */       AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 14.0D);
/*  828 */       AABBUtils.box16(main, 4.0D, 4.0D, 14.0D, 12.0D, 12.0D, 16.0D);
/*      */     } 
/*      */ 
/*      */     
/*  832 */     if ((x == 6 && y == 2 && z == 4) || (y >= 4 && y <= 13 && x == 4 && z == 2)) {
/*  833 */       AABBUtils.box16(main, 5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
/*      */     }
/*      */ 
/*      */     
/*  837 */     if (y == 1 && x == 3 && z == 4) {
/*  838 */       AABBUtils.box16(main, 0.0D, 15.0D, 14.0D, 16.0D, 17.0D, 16.0D);
/*  839 */       AABBUtils.box16(main, 1.0D, 1.0D, 14.0D, 15.0D, 15.0D, 15.0D);
/*      */     } 
/*      */ 
/*      */     
/*  843 */     if (x == 8 && z == 2 && y >= 5 && y <= 13) {
/*  844 */       AABBUtils.box16(main, 0.0D, 0.0D, 2.0D, 1.0D, 16.0D, 14.0D);
/*      */     }
/*      */ 
/*      */     
/*  848 */     if (x == 7 && z == 2 && y >= 15 && y <= 21) {
/*  849 */       AABBUtils.box16(main, 0.0D, 0.0D, 2.0D, 1.0D, 16.0D, 14.0D);
/*      */     }
/*      */ 
/*      */     
/*  853 */     if (y == 0 && ((
/*  854 */       z != 0 && z != 4) || (x != 0 && x != 4 && x != 8)) && (z != 4 || (x != 2 && x != 3 && (x < 5 || x > 7)))) {
/*  855 */       AABBUtils.box16(main, 0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  861 */     if (y >= 4 && y <= 13) {
/*  862 */       if (x == 1 || x == 5) {
/*  863 */         if (z == 1) List.of(AABBUtils.box16(1.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D)); 
/*  864 */         if (z == 2) List.of(AABBUtils.box16(1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)); 
/*  865 */         if (z == 3) List.of(AABBUtils.box16(1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D)); 
/*      */       } 
/*  867 */       if (x == 2 || x == 6) {
/*  868 */         if (z == 1) List.of(AABBUtils.box16(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D)); 
/*  869 */         if (z == 3) List.of(AABBUtils.box16(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D)); 
/*      */       } 
/*  871 */       if (x == 3 || x == 7) {
/*  872 */         if (z == 1) List.of(AABBUtils.box16(0.0D, 0.0D, 1.0D, 15.0D, 16.0D, 16.0D)); 
/*  873 */         if (z == 2) List.of(AABBUtils.box16(0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D)); 
/*  874 */         if (z == 3) List.of(AABBUtils.box16(0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 15.0D));
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  881 */     if (y == 0 || y == 1) {
/*  882 */       if (z >= 1 && z <= 3) {
/*  883 */         if (x == 0) {
/*  884 */           AABBUtils.box16(main, 6.0D, 0.0D, 0.0D, 7.0D, 16.0D, 16.0D);
/*      */         }
/*  886 */         if (x == 8) {
/*  887 */           AABBUtils.box16(main, 9.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
/*      */         }
/*      */       } 
/*  890 */       if (((x >= 1 && x <= 3) || (x >= 5 && x <= 7)) && 
/*  891 */         z == 0) {
/*  892 */         AABBUtils.box16(main, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 7.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     if (main.isEmpty()) {
/*  900 */       main.add(AABBUtils.FULL);
/*      */     }
/*  902 */     return main;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public enum CokingState
/*      */   {
/*  909 */     STANDBY,
/*      */ 
/*      */     
/*  912 */     PROCESSING,
/*      */ 
/*      */     
/*  915 */     DRAIN_RESIDUE,
/*      */ 
/*      */     
/*  918 */     FLOODING,
/*      */ 
/*      */     
/*  921 */     DUMPING;
/*      */     
/*      */     public int id() {
/*  924 */       return ordinal();
/*      */     } }
/*      */   
/*      */   public static class CokingChamber {
/*      */     @Nullable
/*  929 */     CokerUnitRecipe recipe = null;
/*      */     
/*  931 */     CokerUnitTileEntity.CokingState state = CokerUnitTileEntity.CokingState.STANDBY;
/*      */     
/*      */     FluidTank tank;
/*      */     
/*      */     int capacity;
/*      */     
/*  937 */     int inputAmount = 0;
/*      */     
/*  939 */     int outputAmount = 0;
/*      */     
/*  941 */     int timer = 0;
/*      */     
/*      */     public CokingChamber(int itemCapacity, int fluidCapacity) {
/*  944 */       this.capacity = itemCapacity;
/*  945 */       this.tank = new FluidTank(fluidCapacity);
/*      */     }
/*      */     
/*      */     public CokingChamber readFromNBT(CompoundTag nbt) {
/*  949 */       this.tank.readFromNBT(nbt.m_128469_("tank"));
/*  950 */       this.timer = nbt.m_128451_("timer");
/*  951 */       this.inputAmount = nbt.m_128451_("input");
/*  952 */       this.outputAmount = nbt.m_128451_("output");
/*  953 */       this.state = CokerUnitTileEntity.CokingState.values()[nbt.m_128451_("state")];
/*      */       
/*  955 */       if (nbt.m_128425_("recipe", 8)) {
/*      */         try {
/*  957 */           this.recipe = (CokerUnitRecipe)CokerUnitRecipe.recipes.get(new ResourceLocation(nbt.m_128461_("recipe")));
/*  958 */         } catch (ResourceLocationException e) {
/*  959 */           ImmersivePetroleum.log.error("Tried to load a coking recipe with an invalid name", (Throwable)e);
/*      */         } 
/*      */       } else {
/*  962 */         this.recipe = null;
/*      */       } 
/*      */       
/*  965 */       return this;
/*      */     }
/*      */     
/*      */     public CompoundTag writeToNBT(CompoundTag nbt) {
/*  969 */       nbt.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag()));
/*  970 */       nbt.m_128405_("timer", this.timer);
/*  971 */       nbt.m_128405_("input", this.inputAmount);
/*  972 */       nbt.m_128405_("output", this.outputAmount);
/*  973 */       nbt.m_128405_("state", this.state.id());
/*      */       
/*  975 */       if (this.recipe != null) {
/*  976 */         nbt.m_128359_("recipe", this.recipe.m_6423_().toString());
/*      */       }
/*      */       
/*  979 */       return nbt;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setRecipe(@Nullable CokerUnitRecipe recipe) {
/*  984 */       if (this.state == CokerUnitTileEntity.CokingState.STANDBY) {
/*  985 */         this.recipe = recipe;
/*  986 */         return true;
/*      */       } 
/*      */       
/*  989 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public int addStack(@Nonnull ItemStack stack, boolean simulate) {
/*  994 */       if (this.recipe != null && !stack.m_41619_() && this.recipe.inputItem.test(stack)) {
/*  995 */         int capacity = getCapacity() * this.recipe.inputItem.getCount();
/*  996 */         int current = getTotalAmount() * this.recipe.inputItem.getCount();
/*      */         
/*  998 */         if (simulate) {
/*  999 */           return Math.min(capacity - current, stack.m_41613_());
/*      */         }
/*      */         
/* 1002 */         int filled = capacity - current;
/* 1003 */         if (stack.m_41613_() < filled) {
/* 1004 */           filled = stack.m_41613_();
/*      */         }
/* 1006 */         this.inputAmount++;
/*      */         
/* 1008 */         return filled;
/*      */       } 
/*      */       
/* 1011 */       return 0;
/*      */     }
/*      */     
/*      */     public CokerUnitTileEntity.CokingState getState() {
/* 1015 */       return this.state;
/*      */     }
/*      */     
/*      */     public int getCapacity() {
/* 1019 */       return this.capacity;
/*      */     }
/*      */     
/*      */     public int getInputAmount() {
/* 1023 */       return this.inputAmount;
/*      */     }
/*      */     
/*      */     public int getOutputAmount() {
/* 1027 */       return this.outputAmount;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalAmount() {
/* 1032 */       return this.inputAmount + this.outputAmount;
/*      */     }
/*      */     
/*      */     public int getTimer() {
/* 1036 */       return this.timer;
/*      */     }
/*      */     
/*      */     private boolean setStage(CokerUnitTileEntity.CokingState state) {
/* 1040 */       if (this.state != state) {
/* 1041 */         this.state = state;
/* 1042 */         return true;
/*      */       } 
/* 1044 */       return false;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public CokerUnitRecipe getRecipe() {
/* 1049 */       return this.recipe;
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getInputItem() {
/* 1054 */       if (this.recipe == null) {
/* 1055 */         return ItemStack.f_41583_;
/*      */       }
/* 1057 */       return this.recipe.inputItem.getMatchingStacks()[0];
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getOutputItem() {
/* 1062 */       if (this.recipe == null) {
/* 1063 */         return ItemStack.f_41583_;
/*      */       }
/*      */       
/* 1066 */       return ((ItemStack)this.recipe.outputItem.get()).m_41777_();
/*      */     }
/*      */     
/*      */     public FluidTank getTank() {
/* 1070 */       return this.tank;
/*      */     }
/*      */     
/*      */     public boolean tick(CokerUnitTileEntity cokerunit, int chamberId) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   4: ifnonnull -> 15
/*      */       //   7: aload_0
/*      */       //   8: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.STANDBY : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   11: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   14: ireturn
/*      */       //   15: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$1.$SwitchMap$flaxbeard$immersivepetroleum$common$blocks$tileentities$CokerUnitTileEntity$CokingState : [I
/*      */       //   18: aload_0
/*      */       //   19: getfield state : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   22: invokevirtual ordinal : ()I
/*      */       //   25: iaload
/*      */       //   26: tableswitch default -> 787, 1 -> 60, 2 -> 75, 3 -> 278, 4 -> 379, 5 -> 532
/*      */       //   60: aload_0
/*      */       //   61: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   64: ifnull -> 787
/*      */       //   67: aload_0
/*      */       //   68: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.PROCESSING : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   71: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   74: ireturn
/*      */       //   75: aload_0
/*      */       //   76: getfield inputAmount : I
/*      */       //   79: ifle -> 787
/*      */       //   82: aload_0
/*      */       //   83: invokevirtual getInputItem : ()Lnet/minecraft/world/item/ItemStack;
/*      */       //   86: invokevirtual m_41619_ : ()Z
/*      */       //   89: ifne -> 787
/*      */       //   92: aload_0
/*      */       //   93: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   96: invokevirtual getCapacity : ()I
/*      */       //   99: aload_0
/*      */       //   100: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   103: invokevirtual getFluidAmount : ()I
/*      */       //   106: isub
/*      */       //   107: aload_0
/*      */       //   108: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   111: getfield outputFluid : Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   114: invokevirtual getAmount : ()I
/*      */       //   117: if_icmplt -> 787
/*      */       //   120: aload_1
/*      */       //   121: getfield energyStorage : Lblusunrize/immersiveengineering/api/energy/AveragingEnergyStorage;
/*      */       //   124: invokevirtual getEnergyStored : ()I
/*      */       //   127: aload_0
/*      */       //   128: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   131: invokevirtual getTotalProcessEnergy : ()I
/*      */       //   134: aload_0
/*      */       //   135: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   138: invokevirtual getTotalProcessTime : ()I
/*      */       //   141: idiv
/*      */       //   142: if_icmplt -> 787
/*      */       //   145: aload_1
/*      */       //   146: getfield energyStorage : Lblusunrize/immersiveengineering/api/energy/AveragingEnergyStorage;
/*      */       //   149: aload_0
/*      */       //   150: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   153: invokevirtual getTotalProcessEnergy : ()I
/*      */       //   156: aload_0
/*      */       //   157: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   160: invokevirtual getTotalProcessTime : ()I
/*      */       //   163: idiv
/*      */       //   164: iconst_0
/*      */       //   165: invokevirtual extractEnergy : (IZ)I
/*      */       //   168: pop
/*      */       //   169: aload_0
/*      */       //   170: dup
/*      */       //   171: getfield timer : I
/*      */       //   174: iconst_1
/*      */       //   175: iadd
/*      */       //   176: putfield timer : I
/*      */       //   179: aload_0
/*      */       //   180: getfield timer : I
/*      */       //   183: aload_0
/*      */       //   184: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   187: invokevirtual getTotalProcessTime : ()I
/*      */       //   190: aload_0
/*      */       //   191: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   194: getfield inputItem : Lblusunrize/immersiveengineering/api/crafting/IngredientWithSize;
/*      */       //   197: invokevirtual getCount : ()I
/*      */       //   200: imul
/*      */       //   201: if_icmplt -> 276
/*      */       //   204: aload_0
/*      */       //   205: iconst_0
/*      */       //   206: putfield timer : I
/*      */       //   209: aload_0
/*      */       //   210: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   213: aload_0
/*      */       //   214: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   217: getfield outputFluid : Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   220: aload_0
/*      */       //   221: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   224: getfield outputFluid : Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   227: invokevirtual getAmount : ()I
/*      */       //   230: iconst_0
/*      */       //   231: invokestatic copyFluidStackWithAmount : (Lnet/minecraftforge/fluids/FluidStack;IZ)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   234: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   237: invokevirtual fill : (Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I
/*      */       //   240: pop
/*      */       //   241: aload_0
/*      */       //   242: dup
/*      */       //   243: getfield inputAmount : I
/*      */       //   246: iconst_1
/*      */       //   247: isub
/*      */       //   248: putfield inputAmount : I
/*      */       //   251: aload_0
/*      */       //   252: dup
/*      */       //   253: getfield outputAmount : I
/*      */       //   256: iconst_1
/*      */       //   257: iadd
/*      */       //   258: putfield outputAmount : I
/*      */       //   261: aload_0
/*      */       //   262: getfield inputAmount : I
/*      */       //   265: ifgt -> 276
/*      */       //   268: aload_0
/*      */       //   269: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.DRAIN_RESIDUE : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   272: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   275: pop
/*      */       //   276: iconst_1
/*      */       //   277: ireturn
/*      */       //   278: aload_0
/*      */       //   279: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   282: invokevirtual getFluidAmount : ()I
/*      */       //   285: ifle -> 371
/*      */       //   288: aload_1
/*      */       //   289: getfield bufferTanks : [Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   292: iconst_1
/*      */       //   293: aaload
/*      */       //   294: astore_3
/*      */       //   295: aload_0
/*      */       //   296: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   299: bipush #25
/*      */       //   301: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.SIMULATE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   304: invokevirtual drain : (ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   307: astore #4
/*      */       //   309: aload_3
/*      */       //   310: aload #4
/*      */       //   312: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.SIMULATE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   315: invokevirtual fill : (Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I
/*      */       //   318: istore #5
/*      */       //   320: iload #5
/*      */       //   322: ifle -> 368
/*      */       //   325: aload #4
/*      */       //   327: invokevirtual getAmount : ()I
/*      */       //   330: iload #5
/*      */       //   332: invokestatic min : (II)I
/*      */       //   335: istore #6
/*      */       //   337: aload_0
/*      */       //   338: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   341: iload #6
/*      */       //   343: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   346: invokevirtual drain : (ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   349: pop
/*      */       //   350: aload_3
/*      */       //   351: aload #4
/*      */       //   353: iload #6
/*      */       //   355: iconst_0
/*      */       //   356: invokestatic copyFluidStackWithAmount : (Lnet/minecraftforge/fluids/FluidStack;IZ)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   359: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   362: invokevirtual fill : (Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I
/*      */       //   365: pop
/*      */       //   366: iconst_1
/*      */       //   367: ireturn
/*      */       //   368: goto -> 787
/*      */       //   371: aload_0
/*      */       //   372: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.FLOODING : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   375: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   378: ireturn
/*      */       //   379: aload_0
/*      */       //   380: dup
/*      */       //   381: getfield timer : I
/*      */       //   384: iconst_1
/*      */       //   385: iadd
/*      */       //   386: putfield timer : I
/*      */       //   389: aload_0
/*      */       //   390: getfield timer : I
/*      */       //   393: iconst_2
/*      */       //   394: if_icmplt -> 787
/*      */       //   397: aload_0
/*      */       //   398: iconst_0
/*      */       //   399: putfield timer : I
/*      */       //   402: aload_0
/*      */       //   403: invokevirtual getTotalAmount : ()I
/*      */       //   406: aload_0
/*      */       //   407: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   410: getfield inputFluid : Lblusunrize/immersiveengineering/api/crafting/FluidTagInput;
/*      */       //   413: invokevirtual getAmount : ()I
/*      */       //   416: imul
/*      */       //   417: istore_3
/*      */       //   418: aload_0
/*      */       //   419: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   422: invokevirtual getFluidAmount : ()I
/*      */       //   425: iload_3
/*      */       //   426: if_icmpge -> 510
/*      */       //   429: aload_1
/*      */       //   430: getfield bufferTanks : [Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   433: iconst_0
/*      */       //   434: aaload
/*      */       //   435: aload_0
/*      */       //   436: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   439: getfield inputFluid : Lblusunrize/immersiveengineering/api/crafting/FluidTagInput;
/*      */       //   442: invokevirtual getAmount : ()I
/*      */       //   445: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.SIMULATE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   448: invokevirtual drain : (ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   451: astore #4
/*      */       //   453: aload #4
/*      */       //   455: invokevirtual getAmount : ()I
/*      */       //   458: aload_0
/*      */       //   459: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   462: getfield inputFluid : Lblusunrize/immersiveengineering/api/crafting/FluidTagInput;
/*      */       //   465: invokevirtual getAmount : ()I
/*      */       //   468: if_icmplt -> 507
/*      */       //   471: aload_1
/*      */       //   472: getfield bufferTanks : [Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   475: iconst_0
/*      */       //   476: aaload
/*      */       //   477: aload_0
/*      */       //   478: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   481: getfield inputFluid : Lblusunrize/immersiveengineering/api/crafting/FluidTagInput;
/*      */       //   484: invokevirtual getAmount : ()I
/*      */       //   487: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   490: invokevirtual drain : (ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   493: pop
/*      */       //   494: aload_0
/*      */       //   495: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   498: aload #4
/*      */       //   500: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   503: invokevirtual fill : (Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I
/*      */       //   506: pop
/*      */       //   507: goto -> 529
/*      */       //   510: aload_0
/*      */       //   511: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   514: invokevirtual getFluidAmount : ()I
/*      */       //   517: iload_3
/*      */       //   518: if_icmplt -> 529
/*      */       //   521: aload_0
/*      */       //   522: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.DUMPING : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   525: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   528: ireturn
/*      */       //   529: goto -> 787
/*      */       //   532: iconst_0
/*      */       //   533: istore_3
/*      */       //   534: aload_0
/*      */       //   535: dup
/*      */       //   536: getfield timer : I
/*      */       //   539: iconst_1
/*      */       //   540: iadd
/*      */       //   541: putfield timer : I
/*      */       //   544: aload_0
/*      */       //   545: getfield timer : I
/*      */       //   548: iconst_5
/*      */       //   549: if_icmplt -> 724
/*      */       //   552: aload_0
/*      */       //   553: iconst_0
/*      */       //   554: putfield timer : I
/*      */       //   557: aload_0
/*      */       //   558: getfield outputAmount : I
/*      */       //   561: ifle -> 724
/*      */       //   564: aload_1
/*      */       //   565: invokevirtual getLevelNonnull : ()Lnet/minecraft/world/level/Level;
/*      */       //   568: astore #4
/*      */       //   570: aload_0
/*      */       //   571: getfield outputAmount : I
/*      */       //   574: iconst_1
/*      */       //   575: invokestatic min : (II)I
/*      */       //   578: istore #5
/*      */       //   580: aload_0
/*      */       //   581: getfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   584: getfield outputItem : Lnet/minecraftforge/common/util/Lazy;
/*      */       //   587: invokeinterface get : ()Ljava/lang/Object;
/*      */       //   592: checkcast net/minecraft/world/item/ItemStack
/*      */       //   595: invokevirtual m_41777_ : ()Lnet/minecraft/world/item/ItemStack;
/*      */       //   598: astore #6
/*      */       //   600: aload #6
/*      */       //   602: iload #5
/*      */       //   604: invokevirtual m_41764_ : (I)V
/*      */       //   607: aload_1
/*      */       //   608: iload_2
/*      */       //   609: ifne -> 618
/*      */       //   612: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity.Chamber_A_OUT : Lnet/minecraft/core/BlockPos;
/*      */       //   615: goto -> 621
/*      */       //   618: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity.Chamber_B_OUT : Lnet/minecraft/core/BlockPos;
/*      */       //   621: invokevirtual getBlockPosForPos : (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;
/*      */       //   624: astore #7
/*      */       //   626: new net/minecraft/world/phys/Vec3
/*      */       //   629: dup
/*      */       //   630: aload #7
/*      */       //   632: invokevirtual m_123341_ : ()I
/*      */       //   635: i2d
/*      */       //   636: ldc2_w 0.5
/*      */       //   639: dadd
/*      */       //   640: aload #7
/*      */       //   642: invokevirtual m_123342_ : ()I
/*      */       //   645: i2d
/*      */       //   646: ldc2_w 0.5
/*      */       //   649: dsub
/*      */       //   650: aload #7
/*      */       //   652: invokevirtual m_123343_ : ()I
/*      */       //   655: i2d
/*      */       //   656: ldc2_w 0.5
/*      */       //   659: dadd
/*      */       //   660: invokespecial <init> : (DDD)V
/*      */       //   663: astore #8
/*      */       //   665: new net/minecraft/world/entity/item/ItemEntity
/*      */       //   668: dup
/*      */       //   669: aload_1
/*      */       //   670: invokevirtual getLevelNonnull : ()Lnet/minecraft/world/level/Level;
/*      */       //   673: aload #8
/*      */       //   675: getfield f_82479_ : D
/*      */       //   678: aload #8
/*      */       //   680: getfield f_82480_ : D
/*      */       //   683: aload #8
/*      */       //   685: getfield f_82481_ : D
/*      */       //   688: aload #6
/*      */       //   690: invokespecial <init> : (Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V
/*      */       //   693: astore #9
/*      */       //   695: aload #9
/*      */       //   697: dconst_0
/*      */       //   698: dconst_0
/*      */       //   699: dconst_0
/*      */       //   700: invokevirtual m_20334_ : (DDD)V
/*      */       //   703: aload #4
/*      */       //   705: aload #9
/*      */       //   707: invokevirtual m_7967_ : (Lnet/minecraft/world/entity/Entity;)Z
/*      */       //   710: pop
/*      */       //   711: aload_0
/*      */       //   712: dup
/*      */       //   713: getfield outputAmount : I
/*      */       //   716: iload #5
/*      */       //   718: isub
/*      */       //   719: putfield outputAmount : I
/*      */       //   722: iconst_1
/*      */       //   723: istore_3
/*      */       //   724: aload_0
/*      */       //   725: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   728: invokevirtual getFluidAmount : ()I
/*      */       //   731: ifle -> 749
/*      */       //   734: aload_0
/*      */       //   735: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   738: bipush #25
/*      */       //   740: getstatic net/minecraftforge/fluids/capability/IFluidHandler$FluidAction.EXECUTE : Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;
/*      */       //   743: invokevirtual drain : (ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   746: pop
/*      */       //   747: iconst_1
/*      */       //   748: istore_3
/*      */       //   749: aload_0
/*      */       //   750: getfield outputAmount : I
/*      */       //   753: ifgt -> 781
/*      */       //   756: aload_0
/*      */       //   757: getfield tank : Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   760: invokevirtual isEmpty : ()Z
/*      */       //   763: ifeq -> 781
/*      */       //   766: aload_0
/*      */       //   767: aconst_null
/*      */       //   768: putfield recipe : Lflaxbeard/immersivepetroleum/api/crafting/CokerUnitRecipe;
/*      */       //   771: aload_0
/*      */       //   772: getstatic flaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState.STANDBY : Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;
/*      */       //   775: invokevirtual setStage : (Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingState;)Z
/*      */       //   778: pop
/*      */       //   779: iconst_1
/*      */       //   780: istore_3
/*      */       //   781: iload_3
/*      */       //   782: ifeq -> 787
/*      */       //   785: iconst_1
/*      */       //   786: ireturn
/*      */       //   787: iconst_0
/*      */       //   788: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1075	-> 0
/*      */       //   #1076	-> 7
/*      */       //   #1079	-> 15
/*      */       //   #1081	-> 60
/*      */       //   #1082	-> 67
/*      */       //   #1086	-> 75
/*      */       //   #1087	-> 120
/*      */       //   #1088	-> 145
/*      */       //   #1090	-> 169
/*      */       //   #1091	-> 179
/*      */       //   #1092	-> 204
/*      */       //   #1094	-> 209
/*      */       //   #1095	-> 241
/*      */       //   #1096	-> 251
/*      */       //   #1098	-> 261
/*      */       //   #1099	-> 268
/*      */       //   #1103	-> 276
/*      */       //   #1108	-> 278
/*      */       //   #1109	-> 288
/*      */       //   #1110	-> 295
/*      */       //   #1112	-> 309
/*      */       //   #1113	-> 320
/*      */       //   #1114	-> 325
/*      */       //   #1116	-> 337
/*      */       //   #1117	-> 350
/*      */       //   #1119	-> 366
/*      */       //   #1121	-> 368
/*      */       //   #1122	-> 371
/*      */       //   #1126	-> 379
/*      */       //   #1127	-> 389
/*      */       //   #1128	-> 397
/*      */       //   #1130	-> 402
/*      */       //   #1131	-> 418
/*      */       //   #1132	-> 429
/*      */       //   #1133	-> 453
/*      */       //   #1134	-> 471
/*      */       //   #1135	-> 494
/*      */       //   #1137	-> 507
/*      */       //   #1138	-> 521
/*      */       //   #1140	-> 529
/*      */       //   #1143	-> 532
/*      */       //   #1145	-> 534
/*      */       //   #1146	-> 544
/*      */       //   #1147	-> 552
/*      */       //   #1149	-> 557
/*      */       //   #1150	-> 564
/*      */       //   #1151	-> 570
/*      */       //   #1152	-> 580
/*      */       //   #1153	-> 600
/*      */       //   #1156	-> 607
/*      */       //   #1157	-> 626
/*      */       //   #1158	-> 665
/*      */       //   #1159	-> 695
/*      */       //   #1160	-> 703
/*      */       //   #1161	-> 711
/*      */       //   #1163	-> 722
/*      */       //   #1168	-> 724
/*      */       //   #1169	-> 734
/*      */       //   #1171	-> 747
/*      */       //   #1174	-> 749
/*      */       //   #1175	-> 766
/*      */       //   #1176	-> 771
/*      */       //   #1178	-> 779
/*      */       //   #1181	-> 781
/*      */       //   #1182	-> 785
/*      */       //   #1187	-> 787
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   337	31	6	amount	I
/*      */       //   295	73	3	buffer	Lnet/minecraftforge/fluids/capability/templates/FluidTank;
/*      */       //   309	59	4	drained	Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   320	48	5	accepted	I
/*      */       //   453	54	4	accepted	Lnet/minecraftforge/fluids/FluidStack;
/*      */       //   418	111	3	max	I
/*      */       //   570	154	4	world	Lnet/minecraft/world/level/Level;
/*      */       //   580	144	5	amount	I
/*      */       //   600	124	6	copy	Lnet/minecraft/world/item/ItemStack;
/*      */       //   626	98	7	itemOutPos	Lnet/minecraft/core/BlockPos;
/*      */       //   665	59	8	center	Lnet/minecraft/world/phys/Vec3;
/*      */       //   695	29	9	ent	Lnet/minecraft/world/entity/item/ItemEntity;
/*      */       //   534	253	3	update	Z
/*      */       //   0	789	0	this	Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity$CokingChamber;
/*      */       //   0	789	1	cokerunit	Lflaxbeard/immersivepetroleum/common/blocks/tileentities/CokerUnitTileEntity;
/*      */       //   0	789	2	chamberId	I
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\CokerUnitTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */