/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IBlockEntityDrop;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlacementReader;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IPlayerInteraction;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.wooden.AutoLubricatorBlock;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ 
/*     */ public class AutoLubricatorTileEntity extends IPTileEntityBase implements IPCommonTickableTile, IPlacementReader, IPlayerInteraction, IBlockEntityDrop, IEBlockInterfaces.IBlockOverlayText {
/*     */   public boolean isSlave;
/*  49 */   public Direction facing = Direction.NORTH;
/*     */   public FluidTank tank;
/*     */   private LazyOptional<IFluidHandler> outputHandler;
/*     */   int count;
/*  53 */   int countClient; int lastTank; public AutoLubricatorTileEntity master() { if (this.isSlave) { BlockEntity te = m_58904_().m_7702_(m_58899_().m_7495_()); if (te instanceof AutoLubricatorTileEntity) { AutoLubricatorTileEntity autolube = (AutoLubricatorTileEntity)te; return autolube; }  return null; }  return this; } protected void readCustom(CompoundTag compound) { this.isSlave = compound.m_128471_("slave"); Direction facing = Direction.m_122402_(compound.m_128461_("facing")); if (facing.m_122416_() == -1) this.facing = Direction.NORTH;  this.facing = facing; this.tank.readFromNBT(compound.m_128469_("tank")); } protected void writeCustom(CompoundTag compound) { compound.m_128379_("slave", this.isSlave); compound.m_128359_("facing", this.facing.m_122433_()); compound.m_128405_("count", this.count); CompoundTag tank = this.tank.writeToNBT(new CompoundTag()); compound.m_128365_("tank", (Tag)tank); } public void readTank(CompoundTag nbt) { this.tank.readFromNBT(nbt.m_128469_("tank")); } public AutoLubricatorTileEntity(BlockPos pWorldPosition, BlockState pBlockState) { super((BlockEntityType)IPTileTypes.AUTOLUBE.get(), pWorldPosition, pBlockState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.tank = new FluidTank(8000, fluid -> (fluid != null && LubricantHandler.isValidLube(fluid.getFluid())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.count = 0;
/* 236 */     this.countClient = 0;
/* 237 */     this.lastTank = 0; }
/*     */   public void writeTank(CompoundTag nbt, boolean toItem) { boolean write = (this.tank.getFluidAmount() > 0); CompoundTag tankTag = this.tank.writeToNBT(new CompoundTag()); if (!toItem || write) nbt.m_128365_("tank", (Tag)tankTag);  }
/*     */   public void readOnPlacement(LivingEntity placer, ItemStack stack) { if (stack.m_41782_()) readTank(stack.m_41783_());  if (placer instanceof Player) { Player player = (Player)placer; BlockPos target = this.f_58858_.m_121945_(this.facing); BlockEntity te = this.f_58857_.m_7702_(target); LubricatedHandler.ILubricationHandler<BlockEntity> handler = LubricatedHandler.getHandlerForTile(te); if (handler != null && handler.isPlacedCorrectly(this.f_58857_, this, this.facing) != null) Utils.unlockIPAdvancement(player, "main/auto_lubricator");  }  }
/*     */   @Nonnull public List<ItemStack> getBlockEntityDrop(LootContext context) { BlockState state = (BlockState)context.m_78953_(LootContextParams.f_81461_); if (((Boolean)state.m_61143_((Property)AutoLubricatorBlock.SLAVE)).booleanValue()) return List.of(ItemStack.f_41583_);  ItemStack stack = new ItemStack((ItemLike)state.m_60734_()); BlockEntity te = (BlockEntity)context.m_78953_(LootContextParams.f_81462_); if (te instanceof AutoLubricatorTileEntity) { AutoLubricatorTileEntity autolube = (AutoLubricatorTileEntity)te; CompoundTag tag = new CompoundTag(); autolube.writeTank(tag, true); if (!tag.m_128456_()) stack.m_41751_(tag);  }  return List.of(stack); }
/* 241 */   @Nonnull public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) { if (cap == ForgeCapabilities.FLUID_HANDLER && this.isSlave && (side == null || side == Direction.UP)) { AutoLubricatorTileEntity master = master(); if (master == null) return LazyOptional.empty();  if (this.outputHandler == null) this.outputHandler = LazyOptional.of(() -> master.tank);  return this.outputHandler.cast(); }  return super.getCapability(cap, side); } public void tickClient() { if (this.isSlave) {
/*     */       return;
/*     */     }
/*     */     
/* 245 */     if (!this.tank.isEmpty() && LubricantHandler.isValidLube(this.tank.getFluid()) && this.tank.getFluidAmount() >= LubricantHandler.getLubeAmount(this.tank.getFluid()))
/* 246 */     { BlockPos target = this.f_58858_.m_121945_(this.facing);
/* 247 */       BlockEntity te = this.f_58857_.m_7702_(target);
/*     */       
/* 249 */       LubricatedHandler.ILubricationHandler<BlockEntity> handler = LubricatedHandler.getHandlerForTile(te);
/* 250 */       if (handler != null)
/* 251 */       { BlockEntity master = handler.isPlacedCorrectly(this.f_58857_, this, this.facing);
/*     */         
/* 253 */         handler.lubricateClient((ClientLevel)this.f_58857_, this.tank.getFluid().getFluid(), this.count, master);
/*     */         
/* 255 */         if (master != null && handler.isMachineEnabled(this.f_58857_, master) && this.countClient++ % 50 == 0)
/* 256 */         { this.countClient = this.f_58857_.f_46441_.m_188503_(40);
/* 257 */           handler.spawnLubricantParticles((ClientLevel)this.f_58857_, this, this.facing, master); }  }  }  } public void m_7651_() { super.m_7651_(); if (this.outputHandler != null) this.outputHandler.invalidate();  }
/*     */   public void m_6596_() { super.m_6596_(); BlockState state = this.f_58857_.m_8055_(this.f_58858_); this.f_58857_.m_7260_(this.f_58858_, state, state, 3); this.f_58857_.m_46672_(this.f_58858_, state.m_60734_()); }
/*     */   public void invalidateCaps() { super.invalidateCaps(); if (this.outputHandler != null) this.outputHandler.invalidate();  }
/*     */   public Direction getFacing() { return this.facing; }
/*     */   public boolean isMaster() { return !this.isSlave; }
/*     */   @OnlyIn(Dist.CLIENT) public AABB getRenderBoundingBox() { BlockPos pos = m_58899_(); return new AABB(pos.m_7918_(-3, -3, -3), pos.m_7918_(3, 3, 3)); }
/*     */   public Component[] getOverlayText(Player player, @Nonnull HitResult mop, boolean hammer) { if (Utils.isFluidRelatedItemStack(player.m_21120_(InteractionHand.MAIN_HAND))) { AutoLubricatorTileEntity master = master(); if (master != null) { MutableComponent mutableComponent; Component s = null; if (!master.tank.isEmpty()) { mutableComponent = ((MutableComponent)master.tank.getFluid().getDisplayName()).m_130946_(": " + master.tank.getFluidAmount() + "mB"); } else { mutableComponent = Component.m_237115_("gui.immersiveengineering.empty"); }  return new Component[] { (Component)mutableComponent }; }  }  return null; }
/*     */   public boolean useNixieFont(@Nonnull Player player, @Nonnull HitResult mop) { return false; }
/*     */   public InteractionResult interact(@Nonnull Direction side, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull ItemStack heldItem, float hitX, float hitY, float hitZ) { AutoLubricatorTileEntity master = master(); if (master != null) { if (!this.f_58857_.f_46443_ && FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)master.tank)) m_6596_();  return InteractionResult.SUCCESS; }  return InteractionResult.FAIL; }
/* 266 */   public void tickServer() { if (this.isSlave) {
/*     */       return;
/*     */     }
/*     */     
/* 270 */     if (!this.tank.isEmpty() && LubricantHandler.isValidLube(this.tank.getFluid()) && this.tank.getFluidAmount() >= LubricantHandler.getLubeAmount(this.tank.getFluid())) {
/* 271 */       BlockPos target = this.f_58858_.m_121945_(this.facing);
/* 272 */       BlockEntity te = this.f_58857_.m_7702_(target);
/*     */       
/* 274 */       LubricatedHandler.ILubricationHandler<BlockEntity> handler = LubricatedHandler.getHandlerForTile(te);
/* 275 */       if (handler != null) {
/* 276 */         BlockEntity master = handler.isPlacedCorrectly(this.f_58857_, this, this.facing);
/* 277 */         if (master != null && handler.isMachineEnabled(this.f_58857_, master)) {
/* 278 */           handler.lubricateServer((ServerLevel)this.f_58857_, this.tank.getFluid().getFluid(), this.count, master);
/*     */           
/* 280 */           if (this.count++ % 4 == 0) {
/* 281 */             this.tank.drain(LubricantHandler.getLubeAmount(this.tank.getFluid()), IFluidHandler.FluidAction.EXECUTE);
/*     */           }
/*     */           
/* 284 */           m_6596_();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     if (!this.f_58857_.f_46443_ && this.lastTank != this.tank.getFluidAmount()) {
/* 290 */       this.lastTank = this.tank.getFluidAmount();
/* 291 */       m_6596_();
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\AutoLubricatorTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */