/*    */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*    */ import com.blamejared.crafttweaker.api.fluid.IFluidStack;
/*    */ import com.blamejared.crafttweaker.api.ingredient.IIngredient;
/*    */ import com.blamejared.crafttweaker.api.item.IItemStack;
/*    */ import com.blamejared.crafttweaker.api.item.MCItemStack;
/*    */ import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
/*    */ import com.blamejared.crafttweaker.api.tag.type.KnownTag;
/*    */ import com.blamejared.crafttweaker.api.util.Many;
/*    */ import com.blamejared.crafttweaker_annotations.annotations.Document;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import org.openzen.zencode.java.ZenCodeType.Method;
/*    */ import org.openzen.zencode.java.ZenCodeType.Name;
/*    */ 
/*    */ 
/*    */ @ZenRegister
/*    */ @Document("mods/immersivepetroleum/SRU")
/*    */ @Name("mods.immersivepetroleum.Hydrotreater")
/*    */ public class SulfurRecoveryRecipeTweaker
/*    */   implements IRecipeManager<HighPressureRefineryRecipe>
/*    */ {
/*    */   public RecipeType<HighPressureRefineryRecipe> getRecipeType() {
/* 30 */     return IPRecipeTypes.HYDROTREATER.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Method
/*    */   public void removeAll() {
/* 38 */     HighPressureRefineryRecipe.recipes.clear();
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void removeByOutputItem(IIngredient output) {
/* 43 */     HighPressureRefineryRecipe.recipes.values().removeIf(recipe -> output.matches((IItemStack)new MCItemStack(recipe.outputItem)));
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void removeByOutputFluid(IFluidStack output) {
/* 48 */     HighPressureRefineryRecipe.recipes.values().removeIf(recipe -> recipe.output.isFluidEqual(output.getInternal()));
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void addRecipe(String name, IFluidStack output, IItemStack outputItem, double chance, Many<KnownTag<Fluid>> inputFluid, int energy) {
/* 53 */     name = fixRecipeName(name);
/*    */     
/* 55 */     ResourceLocation id = ResourceUtils.ct("hydrotreater/" + name);
/*    */     
/* 57 */     FluidTagInput primary = new FluidTagInput(((KnownTag)inputFluid.getData()).getTagKey(), inputFluid.getAmount());
/*    */     
/* 59 */     newRecipe(id, output, outputItem, chance, primary, (FluidTagInput)null, energy);
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void addRecipeWithSecondary(String name, IFluidStack output, IItemStack outputItem, double chance, Many<KnownTag<Fluid>> inputFluid, Many<KnownTag<Fluid>> inputFluidSecondary, int energy) {
/* 64 */     name = fixRecipeName(name);
/*    */     
/* 66 */     ResourceLocation id = ResourceUtils.ct("hydrotreater/" + name);
/*    */     
/* 68 */     FluidTagInput primary = new FluidTagInput(((KnownTag)inputFluid.getData()).getTagKey(), inputFluid.getAmount());
/* 69 */     FluidTagInput secondary = new FluidTagInput(((KnownTag)inputFluidSecondary.getData()).getTagKey(), inputFluidSecondary.getAmount());
/*    */     
/* 71 */     newRecipe(id, output, outputItem, chance, primary, secondary, energy);
/*    */   }
/*    */   
/*    */   private void newRecipe(ResourceLocation id, IFluidStack output, IItemStack outputItem, double chance, FluidTagInput primary, FluidTagInput secondary, int energy) {
/* 75 */     HighPressureRefineryRecipe recipe = new HighPressureRefineryRecipe(id, output.getInternal(), outputItem.getInternal(), primary, secondary, chance, energy, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     HighPressureRefineryRecipe.recipes.put(id, recipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\SulfurRecoveryRecipeTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */