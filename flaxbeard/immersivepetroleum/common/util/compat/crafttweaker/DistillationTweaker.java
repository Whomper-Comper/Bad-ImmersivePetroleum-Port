/*    */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*    */ import com.blamejared.crafttweaker.api.fluid.IFluidStack;
/*    */ import com.blamejared.crafttweaker.api.item.IItemStack;
/*    */ import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
/*    */ import com.blamejared.crafttweaker.api.tag.type.KnownTag;
/*    */ import com.blamejared.crafttweaker.api.util.Many;
/*    */ import com.blamejared.crafttweaker_annotations.annotations.Document;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import org.openzen.zencode.java.ZenCodeType.Method;
/*    */ import org.openzen.zencode.java.ZenCodeType.Name;
/*    */ 
/*    */ 
/*    */ @ZenRegister
/*    */ @Document("mods/immersivepetroleum/DistillationTowerAlt")
/*    */ @Name("mods.immersivepetroleum.DistillationTowerAlt")
/*    */ public class DistillationTweaker
/*    */   implements IRecipeManager<DistillationTowerRecipe>
/*    */ {
/*    */   public RecipeType<DistillationTowerRecipe> getRecipeType() {
/* 30 */     return IPRecipeTypes.DISTILLATION.get();
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void addRecipe(String name, Many<KnownTag<Fluid>> inputFluidTag, IFluidStack fluidsOutput, IItemStack byproduct, double byproductChance, int energy, int time) {
/* 35 */     addRecipe(name, inputFluidTag, new IFluidStack[] { fluidsOutput }, new IItemStack[] { byproduct }, new double[] { byproductChance }, energy, time);
/*    */   }
/*    */   
/*    */   @Method
/*    */   public void addRecipe(String name, Many<KnownTag<Fluid>> inputFluidTag, IFluidStack[] fluidsOutput, IItemStack[] byproducts, double[] byproductChances, int energy, int time) {
/* 40 */     name = fixRecipeName(name);
/*    */     
/* 42 */     ResourceLocation id = ResourceUtils.ct("distillation/" + name);
/*    */     
/* 44 */     FluidTagInput inputFluid = new FluidTagInput(((KnownTag)inputFluidTag.getData()).getTagKey(), inputFluidTag.getAmount());
/*    */     
/* 46 */     if (byproductChances.length != byproducts.length) {
/* 47 */       throw new IllegalArgumentException("Byproducts and ByproductChances arrays must be equal in size.");
/*    */     }
/*    */     
/* 50 */     FluidStack[] outputFluids = new FluidStack[fluidsOutput.length];
/* 51 */     for (int i = 0; i < fluidsOutput.length; i++) {
/* 52 */       outputFluids[i] = fluidsOutput[i].getInternal();
/*    */     }
/*    */     
/* 55 */     ItemStack[] outputItems = new ItemStack[byproducts.length];
/* 56 */     for (int j = 0; j < byproducts.length; j++) {
/* 57 */       outputItems[j] = byproducts[j].getInternal();
/*    */     }
/*    */     
/* 60 */     DistillationTowerRecipe recipe = new DistillationTowerRecipe(id, outputFluids, outputItems, inputFluid, energy, time, byproductChances);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     DistillationTowerRecipe.recipes.put(id, recipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\DistillationTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */