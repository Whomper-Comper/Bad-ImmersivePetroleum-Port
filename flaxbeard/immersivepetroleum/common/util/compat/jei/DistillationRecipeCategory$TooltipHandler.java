/*     */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotView;
/*     */ import mezz.jei.api.ingredients.ITypedIngredient;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TooltipHandler
/*     */   implements IRecipeSlotTooltipCallback
/*     */ {
/*  98 */   private final Map<ResourceLocation, Double> map = new HashMap<>();
/*     */   
/*     */   public TooltipHandler(DistillationTowerRecipe recipe) {
/* 101 */     NonNullList<ItemStack> list = recipe.getItemOutputs();
/* 102 */     for (int i = 0; i < list.size(); i++) {
/* 103 */       ItemStack stack = (ItemStack)list.get(i);
/*     */       
/* 105 */       this.map.put(RegistryUtils.getRegistryNameOf(stack.m_41720_()), Double.valueOf(recipe.chances()[i]));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
/* 111 */     ITypedIngredient<?> type = recipeSlotView.getDisplayedIngredient().orElse(null);
/* 112 */     if (type != null) { Object object = type.getIngredient(); ItemStack stack = (ItemStack)object; Double t; if (object instanceof ItemStack && (
/*     */         
/* 114 */         t = this.map.get(RegistryUtils.getRegistryNameOf(stack.m_41720_()))) != null) {
/* 115 */         double chance = t.doubleValue();
/*     */ 
/*     */         
/* 118 */         MutableComponent mutableComponent = Component.m_237115_("desc.immersivepetroleum.compat.jei.distillation.byproduct").m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.UNDERLINE });
/*     */         
/* 120 */         tooltip.add(0, mutableComponent);
/* 121 */         tooltip.add(2, toTextComponent(chance));
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private Component toTextComponent(double chance) {
/* 127 */     return (Component)Component.m_237113_(String.format(Locale.ENGLISH, "%.2f%%", new Object[] { Double.valueOf(100.0D * chance) })).m_130940_(ChatFormatting.GRAY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\DistillationRecipeCategory$TooltipHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */