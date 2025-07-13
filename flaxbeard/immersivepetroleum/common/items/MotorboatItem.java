/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.tool.IUpgrade;
/*     */ import blusunrize.immersiveengineering.api.tool.IUpgradeableTool;
/*     */ import blusunrize.immersiveengineering.api.utils.ItemUtils;
/*     */ import blusunrize.immersiveengineering.common.gui.IESlot;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.util.IPItemStackHandler;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ 
/*     */ public class MotorboatItem extends IPItemBase implements IUpgradeableTool {
/*     */   public static final String UPGRADE_TYPE = "MOTORBOAT";
/*     */   
/*     */   public MotorboatItem() {
/*  51 */     super((new Item.Properties()).m_41487_(1).m_41491_(ImmersivePetroleum.creativeTab));
/*     */   }
/*     */ 
/*     */   
/*     */   public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
/*  56 */     return (ICapabilityProvider)new IPItemStackHandler(4);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpgrades(ItemStack stack) {
/*  61 */     return stack.m_41782_() ? stack.m_41784_().m_128469_("upgrades") : new CompoundTag();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearUpgrades(ItemStack stack) {
/*  66 */     ItemUtils.removeTag(stack, "upgrades");
/*     */   }
/*     */   
/*     */   protected NonNullList<ItemStack> getContainedItems(ItemStack stack) {
/*  70 */     IItemHandler handler = (IItemHandler)stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
/*     */     
/*  72 */     if (handler == null) {
/*  73 */       ImmersivePetroleum.log.debug("No valid inventory handler found for " + stack);
/*  74 */       return NonNullList.m_122779_();
/*     */     } 
/*     */     
/*  77 */     if (handler instanceof IPItemStackHandler) { IPItemStackHandler ipStackHandler = (IPItemStackHandler)handler;
/*  78 */       return ipStackHandler.getContainedItems(); }
/*     */ 
/*     */     
/*  81 */     ImmersivePetroleum.log.warn("Inefficiently getting contained items. Why does " + stack + " have a non-IP IItemHandler?");
/*  82 */     NonNullList<ItemStack> inv = NonNullList.m_122780_(handler.getSlots(), ItemStack.f_41583_);
/*  83 */     for (int i = 0; i < handler.getSlots(); i++) {
/*  84 */       inv.set(i, handler.getStackInSlot(i));
/*     */     }
/*     */     
/*  87 */     return inv;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeFromWorkbench(ItemStack stack) {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canModify(ItemStack stack) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recalculateUpgrades(ItemStack stack, Level w, Player player) {
/* 102 */     if (w.f_46443_) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     clearUpgrades(stack);
/*     */     
/* 108 */     LazyOptional<IItemHandler> lazy = stack.getCapability(ForgeCapabilities.ITEM_HANDLER);
/* 109 */     lazy.ifPresent(handler -> {
/*     */           CompoundTag nbt = new CompoundTag();
/*     */           for (int i = 0; i < handler.getSlots(); i++) {
/*     */             ItemStack u = handler.getStackInSlot(i);
/*     */             Item patt4075$temp = u.m_41720_();
/*     */             if (patt4075$temp instanceof IUpgrade) {
/*     */               IUpgrade upg = (IUpgrade)patt4075$temp;
/*     */               if (upg.getUpgradeTypes(u).contains("MOTORBOAT") && upg.canApplyUpgrades(stack, u)) {
/*     */                 upg.applyUpgrades(stack, u, nbt);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           stack.m_41784_().m_128365_("upgrades", (Tag)nbt);
/*     */           finishUpgradeRecalculation(stack);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromWorkbench(Player player, ItemStack stack) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishUpgradeRecalculation(ItemStack stack) {}
/*     */ 
/*     */   
/*     */   public Slot[] getWorkbenchSlots(AbstractContainerMenu container, ItemStack stack, Level world, Supplier<Player> getPlayer, IItemHandler inv) {
/* 136 */     if (inv != null) {
/* 137 */       return new Slot[] { (Slot)new IESlot.Upgrades(container, inv, 0, 78, 30, "MOTORBOAT", stack, true, world, getPlayer), (Slot)new IESlot.Upgrades(container, inv, 1, 98, 40, "MOTORBOAT", stack, true, world, getPlayer), (Slot)new IESlot.Upgrades(container, inv, 2, 118, 30, "MOTORBOAT", stack, true, world, getPlayer) };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return new Slot[0];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component m_7626_(@Nonnull ItemStack stack) {
/*     */     MutableComponent mutableComponent;
/* 150 */     boolean hasUpgrades = getContainedItems(stack).stream().anyMatch(s -> (s != ItemStack.f_41583_));
/*     */     
/* 152 */     Component c = super.m_7626_(stack);
/* 153 */     if (hasUpgrades) {
/* 154 */       mutableComponent = Component.m_237115_("desc.immersivepetroleum.flavour.speedboat.prefix").m_7220_(c).m_130940_(ChatFormatting.GOLD);
/*     */     }
/* 156 */     return (Component)mutableComponent;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void m_7373_(ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 162 */     if (stack.m_41782_()) {
/* 163 */       CompoundTag tag = stack.m_41783_();
/*     */       
/* 165 */       if (tag.m_128441_("tank")) {
/* 166 */         FluidStack fs = FluidStack.loadFluidStackFromNBT(tag.m_128469_("tank"));
/* 167 */         if (fs != null) {
/* 168 */           tooltip.add(((MutableComponent)fs.getDisplayName()).m_130946_(": " + fs.getAmount() + "mB").m_130940_(ChatFormatting.GRAY));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
/*     */           for (int i = 0; i < handler.getSlots(); i++) {
/*     */             if (!handler.getStackInSlot(i).m_41619_()) {
/*     */               tooltip.add(Component.m_237110_("desc.immersivepetroleum.flavour.speedboat.upgrade", new Object[] { Integer.valueOf(i + 1) }).m_7220_(handler.getStackInSlot(i).m_41786_()));
/*     */             }
/*     */           } 
/*     */         });
/*     */ 
/*     */     
/* 182 */     super.m_7373_(stack, worldIn, tooltip, flagIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
/* 188 */     ItemStack itemstack = playerIn.m_21120_(handIn);
/* 189 */     float f1 = playerIn.f_19860_ + playerIn.m_146909_() - playerIn.f_19860_;
/* 190 */     float f2 = playerIn.f_19859_ + playerIn.m_146908_() - playerIn.f_19859_;
/* 191 */     double d0 = playerIn.f_19854_ + playerIn.m_20185_() - playerIn.f_19854_;
/* 192 */     double d1 = playerIn.f_19855_ + playerIn.m_20186_() - playerIn.f_19855_ + playerIn.m_20192_();
/* 193 */     double d2 = playerIn.f_19856_ + playerIn.m_20189_() - playerIn.f_19856_;
/* 194 */     Vec3 vec3d = new Vec3(d0, d1, d2);
/* 195 */     float f3 = Mth.m_14089_(-f2 * 0.017453292F - 3.1415927F);
/* 196 */     float f4 = Mth.m_14031_(-f2 * 0.017453292F - 3.1415927F);
/* 197 */     float f5 = -Mth.m_14089_(-f1 * 0.017453292F);
/* 198 */     float f6 = Mth.m_14031_(-f1 * 0.017453292F);
/* 199 */     float f7 = f4 * f5;
/* 200 */     float f8 = f3 * f5;
/*     */     
/* 202 */     Vec3 vec3d1 = vec3d.m_82520_(f7 * 5.0D, f6 * 5.0D, f8 * 5.0D);
/* 203 */     BlockHitResult blockHitResult = worldIn.m_45547_(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, (Entity)playerIn));
/*     */     
/* 205 */     Vec3 vec3d2 = playerIn.m_20252_(1.0F);
/* 206 */     boolean flag = false;
/* 207 */     AABB bb = playerIn.m_20191_();
/*     */     
/* 209 */     List<Entity> list = worldIn.m_45933_((Entity)playerIn, bb.m_82363_(vec3d2.f_82479_ * 5.0D, vec3d2.f_82480_ * 5.0D, vec3d2.f_82481_ * 5.0D).m_82400_(1.0D));
/* 210 */     for (Entity entity : list) {
/* 211 */       if (entity.m_6087_()) {
/* 212 */         AABB axisalignedbb = entity.m_20191_();
/* 213 */         if (axisalignedbb.m_82400_(entity.m_6143_()).m_82390_(vec3d)) {
/* 214 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     if (flag)
/* 220 */       return new InteractionResultHolder(InteractionResult.PASS, itemstack); 
/* 221 */     if (blockHitResult.m_6662_() != HitResult.Type.BLOCK) {
/* 222 */       return new InteractionResultHolder(InteractionResult.PASS, itemstack);
/*     */     }
/* 224 */     Vec3 hit = blockHitResult.m_82450_();
/* 225 */     Block block = worldIn.m_8055_(new BlockPos(hit.m_82520_(0.0D, 0.5D, 0.0D))).m_60734_();
/* 226 */     boolean flag1 = (block == Blocks.f_49990_);
/* 227 */     MotorboatEntity entityboat = new MotorboatEntity(worldIn, hit.f_82479_, flag1 ? (hit.f_82480_ - 0.12D) : hit.f_82480_, hit.f_82481_);
/*     */     
/* 229 */     entityboat.m_146922_(playerIn.f_19859_);
/* 230 */     entityboat.setUpgrades(getContainedItems(itemstack));
/* 231 */     entityboat.readTank(itemstack.m_41783_());
/*     */ 
/*     */     
/* 234 */     if (worldIn.m_186434_((Entity)entityboat, entityboat.m_20191_().m_82400_(-0.1D)).iterator().hasNext()) {
/* 235 */       return new InteractionResultHolder(InteractionResult.FAIL, itemstack);
/*     */     }
/* 237 */     if (!worldIn.f_46443_) {
/* 238 */       worldIn.m_7967_((Entity)entityboat);
/*     */     }
/*     */     
/* 241 */     if (!playerIn.m_7500_()) {
/* 242 */       itemstack.m_41774_(1);
/*     */     }
/*     */ 
/*     */     
/* 246 */     return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\MotorboatItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */