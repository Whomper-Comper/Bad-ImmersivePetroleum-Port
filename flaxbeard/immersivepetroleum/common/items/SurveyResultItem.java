/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.client.gui.SeismicSurveyScreen;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.ISurveyInfo;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.IslandInfo;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SurveyResultItem
/*     */   extends IPItemBase
/*     */ {
/*     */   public SurveyResultItem() {
/*  31 */     super((new Item.Properties()).m_41487_(1));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component m_7626_(@Nonnull ItemStack stack) {
/*  37 */     String selfKey = m_5671_(stack);
/*  38 */     return (Component)Component.m_237115_(selfKey).m_130940_(ChatFormatting.GOLD);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
/*  43 */     ItemStack held = pPlayer.m_21120_(pUsedHand);
/*     */     
/*  45 */     if (pLevel.f_46443_) { ISurveyInfo iSurveyInfo = ISurveyInfo.from(held); if (iSurveyInfo instanceof SurveyScan) { SurveyScan scan = (SurveyScan)iSurveyInfo;
/*  46 */         if (scan.getUuid() == null) {
/*  47 */           pPlayer.m_5661_((Component)Component.m_237113_("This survey is faulty. (Destroy me!)").m_130940_(ChatFormatting.RED), true);
/*  48 */           return InteractionResultHolder.m_19100_(held);
/*     */         } 
/*     */         
/*  51 */         openGUI(pLevel, scan);
/*  52 */         return InteractionResultHolder.m_19090_(held); }
/*     */        }
/*     */     
/*  55 */     return InteractionResultHolder.m_19098_(held);
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   private static void openGUI(Level level, @Nonnull SurveyScan scan) {
/*  60 */     MCUtil.setScreen((Screen)new SeismicSurveyScreen(level, scan));
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7373_(ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/*  65 */     if (stack.m_41782_() && stack.m_41783_() != null) {
/*  66 */       ISurveyInfo info = ISurveyInfo.from(stack);
/*     */       
/*  68 */       if (info instanceof SurveyScan) { SurveyScan scan = (SurveyScan)info;
/*  69 */         if (scan.getUuid() == null) {
/*  70 */           tooltip.add(Component.m_237113_("SORRY, IM FAULTY!").m_130940_(ChatFormatting.RED));
/*  71 */           tooltip.add(Component.m_237113_("YOU'LL HAVE TO TOSS ME!").m_130940_(ChatFormatting.RED));
/*     */           
/*     */           return;
/*     */         } 
/*  75 */         tooltip.add(Component.m_237115_("desc.immersivepetroleum.flavour.surveytool.rightclickme"));
/*     */         
/*  77 */         if (flagIn == TooltipFlag.Default.ADVANCED) {
/*  78 */           tooltip.add(Component.m_237113_("ID: " + ((scan.getUuid() != null) ? scan.getUuid().toString() : "Null")));
/*  79 */           tooltip.add(Component.m_237113_("dSize: " + ((scan.getData() != null) ? (String)Integer.valueOf((scan.getData()).length) : "Null")));
/*     */         }  }
/*     */ 
/*     */       
/*  83 */       if (info instanceof IslandInfo) { IslandInfo islandInfo = (IslandInfo)info;
/*  84 */         int expected = islandInfo.getExpected();
/*  85 */         long amount = islandInfo.getAmount();
/*  86 */         byte percentage = islandInfo.getStatus();
/*  87 */         FluidStack fs = islandInfo.getFluidStack();
/*     */         
/*  89 */         if (islandInfo.getFluidStack() == FluidStack.EMPTY) {
/*  90 */           tooltip.add(Component.m_237113_("SORRY, IM FAULTY!").m_130940_(ChatFormatting.RED));
/*  91 */           tooltip.add(Component.m_237113_("YOU'LL HAVE TO TOSS ME!").m_130940_(ChatFormatting.RED));
/*     */           
/*     */           return;
/*     */         } 
/*  95 */         tooltip.add(Component.m_237115_(fs.getTranslationKey()).m_130940_(ChatFormatting.DARK_GRAY));
/*  96 */         tooltip.add(Component.m_237110_("desc.immersivepetroleum.info.survey_result.amount", new Object[] { String.format(Locale.ENGLISH, "%,.3f", new Object[] { Double.valueOf(amount / 1000.0D) }), Byte.valueOf(percentage) }).m_130940_(ChatFormatting.DARK_GRAY));
/*  97 */         tooltip.add(Component.m_237110_("desc.immersivepetroleum.info.survey_result.expected", new Object[] { Integer.valueOf(expected) }).m_130940_(ChatFormatting.DARK_GRAY)); }
/*     */ 
/*     */ 
/*     */       
/* 101 */       if (info != null) {
/* 102 */         int x = info.getX();
/* 103 */         int z = info.getZ();
/*     */         
/* 105 */         tooltip.add(Component.m_237110_("desc.immersivepetroleum.flavour.surveytool.location", new Object[] { Integer.valueOf(x), Integer.valueOf(z) }).m_130940_(ChatFormatting.DARK_GRAY));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\SurveyResultItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */