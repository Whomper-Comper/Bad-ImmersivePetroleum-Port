/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
/*     */ 
/*     */ 
/*     */ public class OilCanItem
/*     */   extends IPItemBase
/*     */ {
/*     */   public OilCanItem() {
/*  45 */     super((new Item.Properties()).m_41487_(1).m_41491_(ImmersivePetroleum.creativeTab));
/*     */   }
/*     */ 
/*     */   
/*     */   public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
/*  50 */     if (!stack.m_41619_()) {
/*  51 */       return (ICapabilityProvider)new FluidHandlerItemStack(stack, 8000);
/*     */     }
/*     */     
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void m_7373_(@Nonnull ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/*  60 */     if (ForgeCapabilities.FLUID_HANDLER_ITEM == null) {
/*     */       return;
/*     */     }
/*  63 */     FluidUtil.getFluidContained(stack).ifPresent(fluid -> {
/*     */           if (!fluid.isEmpty() && fluid.getAmount() > 0) {
/*     */             MutableComponent mutableComponent = ((MutableComponent)fluid.getDisplayName()).m_7220_((Component)Component.m_237113_(": " + fluid.getAmount() + "/8000mB")).m_130940_(ChatFormatting.GRAY);
/*     */             tooltip.add(mutableComponent);
/*     */           } else {
/*     */             tooltip.add(Component.m_237113_(I18n.m_118938_("desc.immersiveengineering.flavour.drill.empty", new Object[0])));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6225_(UseOnContext context) {
/*  77 */     ItemStack stack = context.m_43722_();
/*  78 */     Player player = context.m_43723_();
/*  79 */     InteractionHand hand = context.m_43724_();
/*  80 */     Level world = context.m_43725_();
/*  81 */     BlockPos pos = context.m_8083_();
/*     */     
/*  83 */     if (!world.f_46443_) {
/*  84 */       BlockEntity tileEntity = world.m_7702_(pos);
/*  85 */       if (tileEntity != null) {
/*  86 */         IFluidHandler cap = (IFluidHandler)tileEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
/*     */         
/*  88 */         if (cap != null && FluidUtil.interactWithFluidHandler(player, hand, cap)) {
/*  89 */           return InteractionResult.SUCCESS;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         InteractionResult ret = FluidUtil.getFluidHandler(stack).map(handler -> { if (handler instanceof FluidHandlerItemStack) { FluidHandlerItemStack can = (FluidHandlerItemStack)handler; FluidStack fs = can.getFluid(); if (!fs.isEmpty() && LubricantHandler.isValidLube(fs.getFluid())) { int amountNeeded = LubricantHandler.getLubeAmount(fs.getFluid()) * 5 * 20; if (fs.getAmount() >= amountNeeded && LubricatedHandler.lubricateTile(world.m_7702_(pos), fs.getFluid(), 600)) { player.m_5496_(SoundEvents.f_11778_, 1.0F, 1.0F); if (!player.m_7500_()) can.drain(amountNeeded, IFluidHandler.FluidAction.EXECUTE);  Utils.unlockIPAdvancement(player, "main/oil_can"); return InteractionResult.SUCCESS; }  }  }  return InteractionResult.PASS; }).orElse(InteractionResult.PASS);
/*     */         
/* 111 */         return ret;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7579_(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
/* 121 */     m_6880_(stack, null, target, InteractionHand.MAIN_HAND);
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6880_(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
/* 128 */     if (target instanceof IronGolem) { IronGolem golem = (IronGolem)target;
/*     */       
/* 130 */       FluidUtil.getFluidHandler(stack).ifPresent(con -> {
/*     */             if (con instanceof FluidHandlerItemStack) {
/*     */               FluidHandlerItemStack handler = (FluidHandlerItemStack)con;
/*     */               
/*     */               if (!handler.getFluid().isEmpty() && LubricantHandler.isValidLube(handler.getFluid().getFluid())) {
/*     */                 int amountNeeded = LubricantHandler.getLubeAmount(handler.getFluid().getFluid()) * 5 * 20;
/*     */                 if (handler.getFluid().getAmount() >= amountNeeded) {
/*     */                   player.m_5496_(SoundEvents.f_11778_, 1.0F, 1.0F);
/*     */                   golem.m_21153_(Math.max(golem.m_21223_() + 2.0F, golem.m_21233_()));
/*     */                   golem.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 1200, 1));
/*     */                   golem.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 1200, 1));
/*     */                   if (!player.m_7500_()) {
/*     */                     handler.drain(amountNeeded, IFluidHandler.FluidAction.EXECUTE);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           });
/* 148 */       return InteractionResult.SUCCESS; }
/*     */     
/* 150 */     return InteractionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasContainerItem(ItemStack stack) {
/* 156 */     return (ItemNBTHelper.hasKey(stack, "jerrycanDrain") || FluidUtil.getFluidContained(stack).isPresent());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getContainerItem(ItemStack stack) {
/* 161 */     if (ItemNBTHelper.hasKey(stack, "jerrycanDrain")) {
/* 162 */       ItemStack ret = stack.m_41777_();
/* 163 */       FluidUtil.getFluidHandler(ret).ifPresent(handler -> {
/*     */             handler.drain(ItemNBTHelper.getInt(ret, "jerrycanDrain"), IFluidHandler.FluidAction.EXECUTE);
/*     */             ItemNBTHelper.remove(ret, "jerrycanDrain");
/*     */           });
/* 167 */       return ret;
/* 168 */     }  if (FluidUtil.getFluidContained(stack).isPresent()) {
/* 169 */       ItemStack ret = stack.m_41777_();
/* 170 */       FluidUtil.getFluidHandler(ret).ifPresent(handler -> handler.drain(1000, IFluidHandler.FluidAction.EXECUTE));
/* 171 */       return ret;
/*     */     } 
/* 173 */     return stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\OilCanItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */