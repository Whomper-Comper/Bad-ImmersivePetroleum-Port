/*     */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*     */ import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
/*     */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*     */ import com.blamejared.crafttweaker.api.fluid.IFluidStack;
/*     */ import com.blamejared.crafttweaker.api.ingredient.IIngredient;
/*     */ import com.blamejared.crafttweaker.api.item.IItemStack;
/*     */ import com.blamejared.crafttweaker.api.item.MCItemStack;
/*     */ import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
/*     */ import com.blamejared.crafttweaker.api.tag.type.KnownTag;
/*     */ import com.blamejared.crafttweaker.api.util.Many;
/*     */ import com.blamejared.crafttweaker_annotations.annotations.Document;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.common.util.Lazy;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import org.openzen.zencode.java.ZenCodeType.Method;
/*     */ import org.openzen.zencode.java.ZenCodeType.Name;
/*     */ 
/*     */ @ZenRegister
/*     */ @Document("mods/immersivepetroleum/Coker")
/*     */ @Name("mods.immersivepetroleum.CokerUnit")
/*     */ public class CokerUnitRecipeTweaker
/*     */   implements IRecipeManager<CokerUnitRecipe>
/*     */ {
/*     */   public RecipeType<CokerUnitRecipe> getRecipeType() {
/*  35 */     return IPRecipeTypes.COKER.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Method
/*     */   public void removeAll() {
/*  43 */     CokerUnitRecipe.recipes.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Method
/*     */   public void remove(IIngredient output) {
/*  55 */     CokerUnitRecipe.recipes.values().removeIf(recipe -> output.matches((IItemStack)new MCItemStack((ItemStack)recipe.outputItem.get())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Method
/*     */   public void remove(IFluidStack output) {
/*  65 */     CokerUnitRecipe.recipes.values().removeIf(recipe -> recipe.outputFluid.isFluidEqual(output.getInternal()));
/*     */   }
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
/*     */   @Method
/*     */   public void addRecipe(String name, IItemStack inputItem, IItemStack outputItem, Many<KnownTag<Fluid>> inputFluid, IFluidStack outputFluid, int energy) {
/*  86 */     name = fixRecipeName(name);
/*     */     
/*  88 */     ResourceLocation id = ResourceUtils.ct("cokerunit/" + name);
/*  89 */     FluidStack outFluid = outputFluid.getInternal();
/*  90 */     FluidTagInput inFluid = new FluidTagInput(((KnownTag)inputFluid.getData()).getTagKey(), inputFluid.getAmount());
/*     */     
/*  92 */     IngredientWithSize inStack = new IngredientWithSize(Ingredient.m_43927_(new ItemStack[] { inputItem.getInternal() }, ), inputItem.getAmount());
/*  93 */     Objects.requireNonNull(outputItem); Lazy<ItemStack> outStack = Lazy.of(outputItem::getInternal);
/*     */     
/*  95 */     CokerUnitRecipe recipe = new CokerUnitRecipe(id, outStack, outFluid, inStack, inFluid, energy, 30);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     CokerUnitRecipe.recipes.put(id, recipe);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\CokerUnitRecipeTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */