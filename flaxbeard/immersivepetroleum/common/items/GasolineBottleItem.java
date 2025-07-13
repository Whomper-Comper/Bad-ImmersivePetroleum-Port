/*    */ package flaxbeard.immersivepetroleum.common.items;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ClipContext;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*    */ 
/*    */ public class GasolineBottleItem extends IPItemBase {
/*    */   public static final int FILLED_AMOUNT = 250;
/*    */   
/*    */   public GasolineBottleItem() {
/* 29 */     super((new Item.Properties()).m_41487_(16).m_41491_(ImmersivePetroleum.creativeTab));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize(ItemStack stack) {
/* 34 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand usedHand) {
/* 39 */     ItemStack stack = player.m_21120_(usedHand);
/*    */     
/* 41 */     BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Block.COLLIDER);
/* 42 */     if (blockHitResult.m_6662_() == HitResult.Type.BLOCK && blockHitResult instanceof BlockHitResult) { BlockHitResult bHit = blockHitResult;
/* 43 */       BlockPos pos = bHit.m_82425_();
/* 44 */       if (!level.m_7966_(player, pos)) {
/* 45 */         return InteractionResultHolder.m_19098_(stack);
/*    */       }
/*    */       
/* 48 */       BlockEntity be = level.m_7702_(pos);
/* 49 */       if (be != null) {
/* 50 */         IFluidHandler fh = (IFluidHandler)be.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
/* 51 */         if (fh != null) {
/* 52 */           FluidStack fs = new FluidStack(IPContent.Fluids.GASOLINE.get(), 250);
/* 53 */           if (fh.fill(fs, IFluidHandler.FluidAction.SIMULATE) >= 250) {
/* 54 */             fh.fill(fs, IFluidHandler.FluidAction.EXECUTE);
/*    */             
/* 56 */             toEmptyBottle(player, stack);
/*    */             
/* 58 */             return InteractionResultHolder.m_19092_(stack, level.f_46443_);
/*    */           } 
/*    */         } 
/*    */         
/* 62 */         return InteractionResultHolder.m_19098_(stack);
/*    */       }  }
/*    */ 
/*    */     
/* 66 */     return InteractionResultHolder.m_19098_(stack);
/*    */   }
/*    */   
/*    */   public void toEmptyBottle(Player player, ItemStack stack) {
/* 70 */     if ((player.m_150110_()).f_35937_) {
/*    */       return;
/*    */     }
/* 73 */     stack.m_41774_(1);
/* 74 */     ItemStack bottle = new ItemStack((ItemLike)Items.f_42590_);
/* 75 */     if (!player.m_36356_(bottle)) {
/* 76 */       player.m_36176_(bottle, false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected static BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Block blockMode) {
/* 82 */     float f = player.m_146909_();
/* 83 */     float f1 = player.m_146908_();
/* 84 */     Vec3 vec3 = player.m_146892_();
/* 85 */     float f2 = Mth.m_14089_(-f1 * 0.017453292F - 3.1415927F);
/* 86 */     float f3 = Mth.m_14031_(-f1 * 0.017453292F - 3.1415927F);
/* 87 */     float f4 = -Mth.m_14089_(-f * 0.017453292F);
/* 88 */     float f5 = Mth.m_14031_(-f * 0.017453292F);
/* 89 */     float f6 = f3 * f4;
/* 90 */     float f7 = f2 * f4;
/* 91 */     double d0 = player.getReachDistance();
/* 92 */     Vec3 vec31 = vec3.m_82520_(f6 * d0, f5 * d0, f7 * d0);
/* 93 */     return level.m_45547_(new ClipContext(vec3, vec31, blockMode, ClipContext.Fluid.NONE, (Entity)player));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\GasolineBottleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */