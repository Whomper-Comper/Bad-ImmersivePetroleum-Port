/*    */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.client.gui.CokerUnitScreen;
/*    */ import flaxbeard.immersivepetroleum.client.gui.DistillationTowerScreen;
/*    */ import flaxbeard.immersivepetroleum.client.gui.HydrotreaterScreen;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.ArrayList;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.IModPlugin;
/*    */ import mezz.jei.api.JeiPlugin;
/*    */ import mezz.jei.api.helpers.IGuiHelper;
/*    */ import mezz.jei.api.recipe.RecipeType;
/*    */ import mezz.jei.api.recipe.category.IRecipeCategory;
/*    */ import mezz.jei.api.registration.IGuiHandlerRegistration;
/*    */ import mezz.jei.api.registration.IRecipeCatalystRegistration;
/*    */ import mezz.jei.api.registration.IRecipeCategoryRegistration;
/*    */ import mezz.jei.api.registration.IRecipeRegistration;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ @JeiPlugin
/*    */ public class JEIStuff implements IModPlugin {
/* 28 */   private static final ResourceLocation ID = ResourceUtils.ip("main");
/*    */   
/*    */   private RecipeType<DistillationTowerRecipe> distillation_type;
/*    */   
/*    */   private RecipeType<CokerUnitRecipe> coker_type;
/*    */   private RecipeType<HighPressureRefineryRecipe> recovery_type;
/*    */   
/*    */   @Nonnull
/*    */   public ResourceLocation getPluginUid() {
/* 37 */     return ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerCategories(IRecipeCategoryRegistration registration) {
/* 42 */     IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
/*    */     
/* 44 */     DistillationRecipeCategory distillation = new DistillationRecipeCategory(guiHelper);
/* 45 */     CokerUnitRecipeCategory coker = new CokerUnitRecipeCategory(guiHelper);
/* 46 */     HighPressureRefineryRecipeCategory recovery = new HighPressureRefineryRecipeCategory(guiHelper);
/*    */     
/* 48 */     this.distillation_type = distillation.getRecipeType();
/* 49 */     this.coker_type = coker.getRecipeType();
/* 50 */     this.recovery_type = recovery.getRecipeType();
/*    */     
/* 52 */     registration.addRecipeCategories(new IRecipeCategory[] { distillation, coker, recovery });
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerRecipes(IRecipeRegistration registration) {
/* 57 */     registration.addRecipes(this.distillation_type, new ArrayList(DistillationTowerRecipe.recipes.values()));
/* 58 */     registration.addRecipes(this.coker_type, new ArrayList(CokerUnitRecipe.recipes.values()));
/* 59 */     registration.addRecipes(this.recovery_type, new ArrayList(HighPressureRefineryRecipe.recipes.values()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
/* 64 */     registration.addRecipeCatalyst(new ItemStack((ItemLike)IPContent.Multiblock.DISTILLATIONTOWER.get()), new RecipeType[] { this.distillation_type });
/* 65 */     registration.addRecipeCatalyst(new ItemStack((ItemLike)IPContent.Multiblock.COKERUNIT.get()), new RecipeType[] { this.coker_type });
/* 66 */     registration.addRecipeCatalyst(new ItemStack((ItemLike)IPContent.Multiblock.HYDROTREATER.get()), new RecipeType[] { this.recovery_type });
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
/* 71 */     registration.addRecipeClickArea(DistillationTowerScreen.class, 85, 19, 18, 51, new RecipeType[] { this.distillation_type });
/*    */     
/* 73 */     registration.addRecipeClickArea(CokerUnitScreen.class, 59, 21, 15, 67, new RecipeType[] { this.coker_type });
/* 74 */     registration.addRecipeClickArea(CokerUnitScreen.class, 64, 63, 73, 25, new RecipeType[] { this.coker_type });
/* 75 */     registration.addRecipeClickArea(CokerUnitScreen.class, 127, 21, 15, 67, new RecipeType[] { this.coker_type });
/* 76 */     registration.addRecipeClickArea(CokerUnitScreen.class, 81, 21, 39, 42, new RecipeType[] { this.coker_type });
/* 77 */     registration.addRecipeClickArea(HydrotreaterScreen.class, 55, 9, 32, 51, new RecipeType[] { this.recovery_type });
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\JEIStuff.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */