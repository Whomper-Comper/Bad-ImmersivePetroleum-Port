/*     */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import mezz.jei.api.forge.ForgeTypes;
/*     */ import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
/*     */ import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
/*     */ import mezz.jei.api.gui.drawable.IDrawable;
/*     */ import mezz.jei.api.gui.drawable.IDrawableStatic;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotView;
/*     */ import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
/*     */ import mezz.jei.api.helpers.IGuiHelper;
/*     */ import mezz.jei.api.ingredients.IIngredientType;
/*     */ import mezz.jei.api.ingredients.ITypedIngredient;
/*     */ import mezz.jei.api.recipe.IFocusGroup;
/*     */ import mezz.jei.api.recipe.RecipeIngredientRole;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class DistillationRecipeCategory extends IPRecipeCategory<DistillationTowerRecipe> {
/*  40 */   public static final ResourceLocation ID = ResourceUtils.ip("distillation");
/*     */   private final IDrawableStatic tankOverlay;
/*     */   
/*     */   public DistillationRecipeCategory(IGuiHelper guiHelper) {
/*  44 */     super(DistillationTowerRecipe.class, guiHelper, ID, "block.immersivepetroleum.distillation_tower");
/*  45 */     ResourceLocation background = ResourceUtils.ip("textures/gui/jei/distillationtower.png");
/*  46 */     setBackground(guiHelper.createDrawable(background, 0, 0, 120, 77));
/*  47 */     setIcon(new ItemStack((ItemLike)IPContent.Multiblock.DISTILLATIONTOWER.get()));
/*  48 */     this.tankOverlay = guiHelper.createDrawable(background, 120, 0, 20, 51);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, DistillationTowerRecipe recipe, @Nonnull IFocusGroup focuses) {
/*  53 */     int outputTotal = 0;
/*  54 */     List<FluidStack> list = recipe.getFluidOutputs();
/*  55 */     if (!list.isEmpty()) {
/*  56 */       for (FluidStack f : list) {
/*  57 */         outputTotal += f.getAmount();
/*     */       }
/*     */ 
/*     */       
/*  61 */       int tW = 16, tH = 47;
/*  62 */       int x0 = 47;
/*     */       
/*  64 */       int lastHeight = 52;
/*  65 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  66 */         FluidStack f = list.get(i);
/*  67 */         int height = (int)(tH * f.getAmount() / outputTotal);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  72 */         IRecipeSlotBuilder slot = (IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.OUTPUT, x0, lastHeight - height).setFluidRenderer(f.getAmount(), false, tW, height).addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, f);
/*     */         
/*  74 */         lastHeight -= height;
/*     */         
/*  76 */         if (i == 0)
/*     */         {
/*  78 */           slot.setOverlay((IDrawable)this.tankOverlay, -2, -lastHeight + 3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (recipe.getInputFluid() != null) {
/*  84 */       builder.addSlot(RecipeIngredientRole.INPUT, 11, 21)
/*  85 */         .setFluidRenderer(outputTotal, false, 16, 47)
/*  86 */         .setOverlay((IDrawable)this.tankOverlay, -2, -2)
/*  87 */         .addIngredients((IIngredientType)ForgeTypes.FLUID_STACK, recipe.getInputFluid().getMatchingFluidStacks());
/*     */     }
/*     */ 
/*     */     
/*  91 */     IRecipeSlotBuilder itemOutput = builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 37).addTooltipCallback(new TooltipHandler(recipe));
/*  92 */     for (ItemStack s : recipe.getItemOutputs())
/*  93 */       itemOutput.addItemStack(s); 
/*     */   }
/*     */   
/*     */   private static class TooltipHandler
/*     */     implements IRecipeSlotTooltipCallback {
/*  98 */     private final Map<ResourceLocation, Double> map = new HashMap<>();
/*     */     
/*     */     public TooltipHandler(DistillationTowerRecipe recipe) {
/* 101 */       NonNullList<ItemStack> list = recipe.getItemOutputs();
/* 102 */       for (int i = 0; i < list.size(); i++) {
/* 103 */         ItemStack stack = (ItemStack)list.get(i);
/*     */         
/* 105 */         this.map.put(RegistryUtils.getRegistryNameOf(stack.m_41720_()), Double.valueOf(recipe.chances()[i]));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onTooltip(IRecipeSlotView recipeSlotView, List<Component> tooltip) {
/* 111 */       ITypedIngredient<?> type = recipeSlotView.getDisplayedIngredient().orElse(null);
/* 112 */       if (type != null) { Object object = type.getIngredient(); ItemStack stack = (ItemStack)object; Double t; if (object instanceof ItemStack && (
/*     */           
/* 114 */           t = this.map.get(RegistryUtils.getRegistryNameOf(stack.m_41720_()))) != null) {
/* 115 */           double chance = t.doubleValue();
/*     */ 
/*     */           
/* 118 */           MutableComponent mutableComponent = Component.m_237115_("desc.immersivepetroleum.compat.jei.distillation.byproduct").m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.UNDERLINE });
/*     */           
/* 120 */           tooltip.add(0, mutableComponent);
/* 121 */           tooltip.add(2, toTextComponent(chance));
/*     */         }  }
/*     */     
/*     */     }
/*     */     
/*     */     private Component toTextComponent(double chance) {
/* 127 */       return (Component)Component.m_237113_(String.format(Locale.ENGLISH, "%.2f%%", new Object[] { Double.valueOf(100.0D * chance) })).m_130940_(ChatFormatting.GRAY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(@Nonnull DistillationTowerRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull PoseStack matrix, double mouseX, double mouseY) {
/* 133 */     IDrawable background = getBackground();
/* 134 */     int bWidth = background.getWidth();
/* 135 */     int bHeight = background.getHeight();
/* 136 */     Font font = MCUtil.getFont();
/*     */     
/* 138 */     int time = recipe.getTotalProcessTime();
/* 139 */     int energy = recipe.getTotalProcessEnergy() / time;
/*     */     
/* 141 */     matrix.m_85836_();
/*     */     
/* 143 */     matrix.m_85837_(23.0D, 0.0D, 0.0D);
/*     */     
/* 145 */     String text0 = I18n.m_118938_("desc.immersiveengineering.info.ift", new Object[] { Utils.fDecimal(energy) });
/* 146 */     Objects.requireNonNull(font); font.m_92883_(matrix, text0, (bWidth / 2 - font.m_92895_(text0) / 2), (bHeight - 9 * 2), 0);
/*     */     
/* 148 */     String text1 = I18n.m_118938_("desc.immersiveengineering.info.seconds", new Object[] { Utils.fDecimal(time / 20.0D) });
/* 149 */     Objects.requireNonNull(font); font.m_92883_(matrix, text1, (bWidth / 2 - font.m_92895_(text1) / 2), (bHeight - 9), 0);
/*     */     
/* 151 */     matrix.m_85849_();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\DistillationRecipeCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */