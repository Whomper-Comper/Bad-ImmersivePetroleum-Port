/*    */ package flaxbeard.immersivepetroleum.common.util.compat.jei;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.constants.VanillaTypes;
/*    */ import mezz.jei.api.gui.drawable.IDrawable;
/*    */ import mezz.jei.api.gui.drawable.IDrawableStatic;
/*    */ import mezz.jei.api.helpers.IGuiHelper;
/*    */ import mezz.jei.api.ingredients.IIngredientType;
/*    */ import mezz.jei.api.recipe.RecipeType;
/*    */ import mezz.jei.api.recipe.category.IRecipeCategory;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public abstract class IPRecipeCategory<T>
/*    */   implements IRecipeCategory<T> {
/*    */   public String localizedName;
/*    */   protected final IGuiHelper guiHelper;
/*    */   private IDrawableStatic background;
/*    */   private IDrawable icon;
/*    */   private RecipeType<T> type;
/*    */   
/*    */   public IPRecipeCategory(Class<? extends T> recipeClass, IGuiHelper guiHelper, ResourceLocation id, String localKey) {
/* 25 */     this.guiHelper = guiHelper;
/* 26 */     this.localizedName = I18n.m_118938_(localKey, new Object[0]);
/*    */     
/* 28 */     this.type = new RecipeType(id, recipeClass);
/*    */   }
/*    */   
/*    */   protected void setBackground(IDrawableStatic background) {
/* 32 */     this.background = background;
/*    */   }
/*    */   
/*    */   protected void setIcon(ItemStack stack) {
/* 36 */     setIcon(this.guiHelper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, stack));
/*    */   }
/*    */   
/*    */   protected void setIcon(IDrawable icon) {
/* 40 */     this.icon = icon;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component getTitle() {
/* 46 */     return (Component)Component.m_237115_(this.localizedName);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IDrawable getBackground() {
/* 52 */     return (IDrawable)this.background;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IDrawable getIcon() {
/* 58 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public RecipeType<T> getRecipeType() {
/* 64 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\jei\IPRecipeCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */