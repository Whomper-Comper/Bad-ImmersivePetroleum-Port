/*    */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.constants.VanillaTypes;
/*    */ import mezz.jei.api.forge.ForgeTypes;
/*    */ import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
/*    */ import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
/*    */ import mezz.jei.api.gui.drawable.IDrawable;
/*    */ import mezz.jei.api.gui.drawable.IDrawableStatic;
/*    */ import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
/*    */ import mezz.jei.api.helpers.IGuiHelper;
/*    */ import mezz.jei.api.ingredients.IIngredientType;
/*    */ import mezz.jei.api.recipe.IFocusGroup;
/*    */ import mezz.jei.api.recipe.RecipeIngredientRole;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class HighPressureRefineryRecipeCategory extends IPRecipeCategory<HighPressureRefineryRecipe> {
/* 30 */   public static final ResourceLocation ID = ResourceUtils.ip("hydrotreater");
/*    */   private final IDrawableStatic tankOverlay;
/*    */   
/*    */   public HighPressureRefineryRecipeCategory(IGuiHelper guiHelper) {
/* 34 */     super(HighPressureRefineryRecipe.class, guiHelper, ID, "block.immersivepetroleum.hydrotreater");
/* 35 */     ResourceLocation background = ResourceUtils.ip("textures/gui/jei/hydrotreater.png");
/* 36 */     setBackground(guiHelper.createDrawable(background, 0, 0, 113, 75));
/* 37 */     setIcon(new ItemStack((ItemLike)IPContent.Multiblock.HYDROTREATER.get()));
/*    */     
/* 39 */     this.tankOverlay = guiHelper.createDrawable(background, 113, 0, 20, 51);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipe(IRecipeLayoutBuilder builder, HighPressureRefineryRecipe recipe, @Nonnull IFocusGroup focuses) {
/* 44 */     int primaryInputAmount = recipe.inputFluid.getAmount();
/* 45 */     int secondaryInputAmount = (recipe.inputFluidSecondary != null) ? recipe.inputFluidSecondary.getAmount() : 0;
/* 46 */     int outputAmount = recipe.output.getAmount();
/* 47 */     int guiTankSize = Math.min(Math.max(Math.max(primaryInputAmount, secondaryInputAmount), outputAmount), 1000);
/*    */     
/* 49 */     builder.addSlot(RecipeIngredientRole.INPUT, 25, 3)
/* 50 */       .setFluidRenderer(guiTankSize, false, 20, 51)
/* 51 */       .setOverlay((IDrawable)this.tankOverlay, 0, 0)
/* 52 */       .addIngredients((IIngredientType)ForgeTypes.FLUID_STACK, recipe.inputFluid.getMatchingFluidStacks());
/*    */ 
/*    */ 
/*    */     
/* 56 */     IRecipeSlotBuilder secondary = builder.addSlot(RecipeIngredientRole.INPUT, 3, 3).setFluidRenderer(guiTankSize, false, 20, 51).setOverlay((IDrawable)this.tankOverlay, 0, 0);
/* 57 */     if (recipe.inputFluidSecondary != null) {
/* 58 */       secondary.addIngredients((IIngredientType)ForgeTypes.FLUID_STACK, recipe.inputFluidSecondary.getMatchingFluidStacks());
/*    */     }
/* 60 */     builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 3)
/* 61 */       .setFluidRenderer(guiTankSize, false, 20, 51)
/* 62 */       .setOverlay((IDrawable)this.tankOverlay, 0, 0)
/* 63 */       .addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, recipe.output);
/*    */     
/* 65 */     builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 21)
/* 66 */       .addIngredient((IIngredientType)VanillaTypes.ITEM_STACK, recipe.outputItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(HighPressureRefineryRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, PoseStack matrix, double mouseX, double mouseY) {
/* 71 */     IDrawable background = getBackground();
/* 72 */     int bWidth = background.getWidth();
/* 73 */     int bHeight = background.getHeight();
/* 74 */     Font font = MCUtil.getFont();
/*    */     
/* 76 */     int time = recipe.getTotalProcessTime();
/* 77 */     int energy = recipe.getTotalProcessEnergy() / recipe.getTotalProcessTime();
/* 78 */     int chance = (int)(100.0D * recipe.chance);
/*    */     
/* 80 */     matrix.m_85836_();
/* 81 */     String text0 = I18n.m_118938_("desc.immersiveengineering.info.ift", new Object[] { Utils.fDecimal(energy) });
/* 82 */     Objects.requireNonNull(font); font.m_92883_(matrix, text0, (bWidth / 2 - font.m_92895_(text0) / 2), (bHeight - 9 * 2), 0);
/*    */     
/* 84 */     String text1 = I18n.m_118938_("desc.immersiveengineering.info.seconds", new Object[] { Utils.fDecimal(time / 20.0D) });
/* 85 */     Objects.requireNonNull(font); font.m_92883_(matrix, text1, (bWidth / 2 - font.m_92895_(text1) / 2), (bHeight - 9), 0);
/*    */     
/* 87 */     if (recipe.hasSecondaryItem()) {
/* 88 */       String text2 = String.format(Locale.US, "%d%%", new Object[] { Integer.valueOf(chance) });
/* 89 */       font.m_92883_(matrix, text2, (bWidth + 3 - font.m_92895_(text2)), (bHeight / 2 + 4), 0);
/*    */     } 
/* 91 */     matrix.m_85849_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\HighPressureRefineryRecipeCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */