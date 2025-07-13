/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MolotovItemEntity;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.UseAnim;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ public class MolotovItem
/*     */   extends IPItemBase
/*     */ {
/*     */   private static final int SECONDS = 15;
/*     */   private final boolean isLit;
/*     */   
/*     */   private static Item.Properties makeProperty(boolean isLit) {
/*  28 */     Item.Properties prop = (new Item.Properties()).m_41487_(isLit ? 1 : 64).m_41503_(15).setNoRepair();
/*  29 */     if (!isLit) {
/*  30 */       prop.m_41491_(ImmersivePetroleum.creativeTab);
/*     */     }
/*  32 */     return prop;
/*     */   }
/*     */ 
/*     */   
/*     */   public MolotovItem(boolean isLit) {
/*  37 */     super(makeProperty(isLit));
/*  38 */     this.isLit = isLit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6883_(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
/*  43 */     super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
/*     */     
/*  45 */     if (this.isLit && pEntity instanceof Player) { Player player = (Player)pEntity;
/*  46 */       if (pStack.m_41782_() && pStack.m_41783_().m_128425_("lit_time", 4)) {
/*  47 */         int duration = (int)(pLevel.m_46467_() - pStack.m_41783_().m_128454_("lit_time")) / 20;
/*     */         
/*  49 */         if ((player.m_150110_()).f_35937_) {
/*  50 */           if (duration > 0 && pStack.m_41773_() == 0) {
/*  51 */             pStack.m_41721_(1);
/*     */           }
/*     */         } else {
/*  54 */           if (duration > 15) {
/*  55 */             player.m_141942_(pSlotId).m_142104_(new ItemStack((ItemLike)Items.f_42590_, 1));
/*     */             
/*     */             return;
/*     */           } 
/*  59 */           if (pStack.m_41773_() != duration) {
/*  60 */             pStack.m_41721_(duration);
/*     */           }
/*     */         } 
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
/*  69 */     if (this.isLit) {
/*  70 */       ItemStack stack = pPlayer.m_21120_(pUsedHand);
/*  71 */       if (stack.m_41768_() && !pLevel.f_46443_) {
/*  72 */         pLevel.m_6263_(null, pPlayer.m_20185_(), pPlayer.m_20186_(), pPlayer.m_20189_(), SoundEvents.f_11870_, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.m_213780_().m_188501_() * 0.4F + 0.8F));
/*     */         
/*  74 */         MolotovItemEntity entity = new MolotovItemEntity(pLevel, (LivingEntity)pPlayer);
/*  75 */         entity.m_37446_(stack);
/*  76 */         entity.m_37251_((Entity)pPlayer, pPlayer.m_146909_(), pPlayer.m_146908_(), 0.0F, 0.75F, 1.0F);
/*  77 */         pLevel.m_7967_((Entity)entity);
/*     */         
/*  79 */         if (!(pPlayer.m_150110_()).f_35937_) {
/*  80 */           stack.m_41774_(1);
/*     */         }
/*     */       } 
/*     */       
/*  84 */       return InteractionResultHolder.m_19096_(pPlayer.m_21120_(pUsedHand));
/*     */     } 
/*  86 */     ItemStack mainStack = pPlayer.m_21120_(pUsedHand);
/*  87 */     ItemStack offStack = pPlayer.m_21120_(InteractionHand.OFF_HAND);
/*     */     
/*  89 */     if (mainStack.m_41720_() == this && offStack.m_41720_() == Items.f_42409_) {
/*  90 */       pPlayer.m_6672_(pUsedHand);
/*     */     }
/*     */     
/*  93 */     return InteractionResultHolder.m_19096_(pPlayer.m_21120_(pUsedHand));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack m_5922_(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
/*  99 */     if (!this.isLit && pLivingEntity instanceof Player) { Player player = (Player)pLivingEntity;
/* 100 */       ItemStack mainStack = player.m_21120_(InteractionHand.MAIN_HAND);
/* 101 */       ItemStack offStack = player.m_21120_(InteractionHand.OFF_HAND);
/*     */       
/* 103 */       if (mainStack.m_41720_() == this && offStack.m_41720_() == Items.f_42409_) {
/* 104 */         pStack.m_41774_(1);
/* 105 */         if (player instanceof net.minecraft.server.level.ServerPlayer && !(player.m_150110_()).f_35937_) {
/* 106 */           offStack.m_41622_(1, (LivingEntity)player, p -> p.m_21190_(InteractionHand.OFF_HAND));
/*     */         }
/*     */         
/* 109 */         ItemStack lit = new ItemStack((ItemLike)IPContent.Items.MOLOTOV_LIT.get(), 1);
/* 110 */         lit.m_41784_().m_128356_("lit_time", pLevel.m_46467_() - 1L);
/* 111 */         return lit;
/*     */       }  }
/*     */ 
/*     */     
/* 115 */     return pStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim m_6164_(ItemStack pStack) {
/* 120 */     return UseAnim.BOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_8105_(ItemStack pStack) {
/* 125 */     return 20;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\MolotovItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */