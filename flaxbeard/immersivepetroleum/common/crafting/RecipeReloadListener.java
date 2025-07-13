/*    */ package flaxbeard.immersivepetroleum.common.crafting;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*    */ import flaxbeard.immersivepetroleum.client.render.dyn.DynamicTextureWrapper;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.ReloadableServerResources;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.RecipeManager;
/*    */ import net.minecraftforge.client.event.RecipesUpdatedEvent;
/*    */ import net.minecraftforge.eventbus.api.EventPriority;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ 
/*    */ public class RecipeReloadListener implements ResourceManagerReloadListener {
/*    */   private final ReloadableServerResources dataPackRegistries;
/*    */   
/*    */   public RecipeReloadListener(ReloadableServerResources dataPackRegistries) {
/* 31 */     this.dataPackRegistries = dataPackRegistries;
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_6213_(@Nonnull ResourceManager resourceManager) {
/* 36 */     if (this.dataPackRegistries != null) {
/* 37 */       lists(this.dataPackRegistries.m_206887_());
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.HIGH)
/*    */   public void recipesUpdated(RecipesUpdatedEvent event) {
/* 43 */     if (!Minecraft.m_91087_().m_91091_()) {
/* 44 */       lists(event.getRecipeManager());
/*    */     }
/*    */     
/* 47 */     DynamicTextureWrapper.clearCache();
/*    */   }
/*    */   
/*    */   static void lists(RecipeManager recipeManager) {
/* 51 */     Collection<Recipe<?>> recipes = recipeManager.m_44051_();
/* 52 */     if (recipes.size() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 56 */     ImmersivePetroleum.log.info("Loading Distillation Recipes.");
/* 57 */     DistillationTowerRecipe.recipes = filterRecipes(recipes, DistillationTowerRecipe.class, IPRecipeTypes.DISTILLATION);
/*    */     
/* 59 */     ImmersivePetroleum.log.info("Loading Reservoirs.");
/* 60 */     ReservoirType.map = filterRecipes(recipes, ReservoirType.class, IPRecipeTypes.RESERVOIR);
/*    */     
/* 62 */     ImmersivePetroleum.log.info("Loading Coker-Unit Recipes.");
/* 63 */     CokerUnitRecipe.recipes = filterRecipes(recipes, CokerUnitRecipe.class, IPRecipeTypes.COKER);
/*    */     
/* 65 */     ImmersivePetroleum.log.info("Loading High-Pressure Refinery Recipes.");
/* 66 */     HighPressureRefineryRecipe.recipes = filterRecipes(recipes, HighPressureRefineryRecipe.class, IPRecipeTypes.HYDROTREATER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static <R extends Recipe<?>> Map<ResourceLocation, R> filterRecipes(Collection<Recipe<?>> recipes, Class<R> recipeClass, IERecipeTypes.TypeWithClass<R> recipeType) {
/* 72 */     Objects.requireNonNull(recipeClass); return (Map<ResourceLocation, R>)recipes.stream().filter(iRecipe -> (iRecipe.m_6671_() == recipeType.get())).map(recipeClass::cast)
/* 73 */       .collect(Collectors.toMap(recipe -> recipe.m_6423_(), recipe -> recipe));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\RecipeReloadListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */