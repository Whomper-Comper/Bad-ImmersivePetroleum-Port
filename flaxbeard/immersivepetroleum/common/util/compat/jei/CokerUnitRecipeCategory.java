/*    */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.constants.VanillaTypes;
/*    */ import mezz.jei.api.forge.ForgeTypes;
/*    */ import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
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
/*    */ public class CokerUnitRecipeCategory extends IPRecipeCategory<CokerUnitRecipe> {
/* 30 */   public static final ResourceLocation ID = ResourceUtils.ip("cokerunit");
/*    */   private final IDrawableStatic tankOverlay;
/*    */   
/*    */   public CokerUnitRecipeCategory(IGuiHelper guiHelper) {
/* 34 */     super(CokerUnitRecipe.class, guiHelper, ID, "block.immersivepetroleum.coker_unit");
/* 35 */     ResourceLocation background = ResourceUtils.ip("textures/gui/jei/coker.png");
/* 36 */     ResourceLocation coker = ResourceUtils.ip("textures/gui/coker.png");
/*    */     
/* 38 */     setBackground(guiHelper.createDrawable(background, 0, 0, 150, 77));
/* 39 */     setIcon(new ItemStack((ItemLike)IPContent.Multiblock.COKERUNIT.get()));
/*    */     
/* 41 */     this.tankOverlay = guiHelper.createDrawable(coker, 200, 0, 20, 51);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, CokerUnitRecipe recipe, @Nonnull IFocusGroup focuses) {
/* 46 */     int inputAmount = recipe.inputFluid.getAmount();
/* 47 */     int outputAmount = recipe.outputFluid.getAmount();
/* 48 */     int guiTankSize = Math.max(inputAmount, outputAmount);
/*    */     
/* 50 */     builder.addSlot(RecipeIngredientRole.INPUT, 2, 2)
/* 51 */       .setFluidRenderer(guiTankSize, false, 20, 51)
/* 52 */       .setOverlay((IDrawable)this.tankOverlay, 0, 0)
/* 53 */       .addIngredients((IIngredientType)ForgeTypes.FLUID_STACK, recipe.inputFluid.getMatchingFluidStacks());
/*    */     
/* 55 */     builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 2)
/* 56 */       .setFluidRenderer(guiTankSize, false, 20, 51)
/* 57 */       .setOverlay((IDrawable)this.tankOverlay, 0, 0)
/* 58 */       .addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, recipe.outputFluid);
/*    */     
/* 60 */     builder.addSlot(RecipeIngredientRole.INPUT, 4, 58)
/* 61 */       .addIngredients((IIngredientType)VanillaTypes.ITEM_STACK, Arrays.asList(recipe.inputItem.getMatchingStacks()));
/*    */     
/* 63 */     builder.addSlot(RecipeIngredientRole.OUTPUT, 52, 58)
/* 64 */       .addIngredients((IIngredientType)VanillaTypes.ITEM_STACK, Collections.singletonList((ItemStack)recipe.outputItem.get()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(CokerUnitRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, PoseStack matrix, double mouseX, double mouseY) {
/* 69 */     IDrawable background = getBackground();
/* 70 */     int bWidth = background.getWidth();
/* 71 */     int bHeight = background.getHeight();
/* 72 */     Font font = MCUtil.getFont();
/*    */     
/* 74 */     int time = (recipe.getTotalProcessTime() + 2 + 5) * recipe.inputItem.getCount();
/* 75 */     int energy = recipe.getTotalProcessEnergy() / recipe.getTotalProcessTime();
/*    */     
/* 77 */     matrix.m_85836_();
/* 78 */     String text0 = I18n.m_118938_("desc.immersiveengineering.info.ift", new Object[] { Utils.fDecimal(energy) });
/* 79 */     Objects.requireNonNull(font); font.m_92883_(matrix, text0, (bWidth - 5 - font.m_92895_(text0)), (bHeight / 3 + 9), -1);
/*    */     
/* 81 */     String text1 = I18n.m_118938_("desc.immersiveengineering.info.seconds", new Object[] { Utils.fDecimal(time / 20.0D) });
/* 82 */     Objects.requireNonNull(font); font.m_92883_(matrix, text1, (bWidth - 10 - font.m_92895_(text1)), (bHeight / 3 + 9 * 2), -1);
/* 83 */     matrix.m_85849_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\CokerUnitRecipeCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */