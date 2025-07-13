/*     */ package flaxbeard.immersivepetroleum.api.crafting;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*     */ import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.common.util.Lazy;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class CokerUnitRecipe
/*     */   extends IPMultiblockRecipe {
/*  22 */   public static Map<ResourceLocation, CokerUnitRecipe> recipes = new HashMap<>(); public final Lazy<ItemStack> outputItem; public final FluidStack outputFluid;
/*     */   
/*     */   public static CokerUnitRecipe findRecipe(ItemStack stack, FluidStack fluid) {
/*  25 */     for (CokerUnitRecipe recipe : recipes.values()) {
/*  26 */       if (recipe.inputItem != null && recipe.inputItem.test(stack) && recipe.inputFluid != null && recipe.inputFluid.test(fluid)) {
/*  27 */         return recipe;
/*     */       }
/*     */     } 
/*     */     
/*  31 */     return null;
/*     */   }
/*     */   public final IngredientWithSize inputItem; public final FluidTagInput inputFluid;
/*     */   public static boolean hasRecipeWithInput(@Nonnull ItemStack stack, @Nonnull FluidStack fluid) {
/*  35 */     Objects.requireNonNull(stack);
/*  36 */     Objects.requireNonNull(fluid);
/*     */     
/*  38 */     if (!stack.m_41619_() && !fluid.isEmpty()) {
/*  39 */       for (CokerUnitRecipe recipe : recipes.values()) {
/*  40 */         if (recipe.inputItem != null && recipe.inputFluid != null && recipe.inputItem.test(stack) && recipe.inputFluid.test(fluid)) {
/*  41 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasRecipeWithInput(@Nonnull ItemStack stack, boolean ignoreSize) {
/*  49 */     Objects.requireNonNull(stack);
/*     */     
/*  51 */     if (!stack.m_41619_()) {
/*  52 */       for (CokerUnitRecipe recipe : recipes.values()) {
/*  53 */         if (recipe.inputItem != null && ((
/*  54 */           !ignoreSize && recipe.inputItem.test(stack)) || (ignoreSize && recipe.inputItem.testIgnoringSize(stack)))) {
/*  55 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasRecipeWithInput(@Nonnull FluidStack fluid, boolean ignoreAmount) {
/*  64 */     Objects.requireNonNull(fluid);
/*     */     
/*  66 */     if (!fluid.isEmpty()) {
/*  67 */       for (CokerUnitRecipe recipe : recipes.values()) {
/*  68 */         if (recipe.inputFluid != null && ((
/*  69 */           !ignoreAmount && recipe.inputFluid.test(fluid)) || (ignoreAmount && recipe.inputFluid.testIgnoringAmount(fluid)))) {
/*  70 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return false;
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
/*     */   public CokerUnitRecipe(ResourceLocation id, Lazy<ItemStack> outputItem2, FluidStack outputFluid, IngredientWithSize inputItem, FluidTagInput inputFluid, int energy, int time) {
/*  91 */     super(ItemStack.f_41583_, (IERecipeTypes.TypeWithClass)IPRecipeTypes.COKER, id);
/*  92 */     this.inputFluid = inputFluid;
/*  93 */     this.inputItem = inputItem;
/*  94 */     this.outputFluid = outputFluid;
/*  95 */     this.outputItem = outputItem2;
/*     */     
/*  97 */     timeAndEnergy(time, energy);
/*  98 */     Objects.requireNonNull(IPServerConfig.REFINING.cokerUnit_timeModifier); Objects.requireNonNull(IPServerConfig.REFINING.cokerUnit_energyModifier); modifyTimeAndEnergy(IPServerConfig.REFINING.cokerUnit_timeModifier::get, IPServerConfig.REFINING.cokerUnit_energyModifier::get);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMultipleProcessTicks() {
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getActualItemOutputs(BlockEntity tile) {
/* 108 */     NonNullList<ItemStack> list = NonNullList.m_122779_();
/* 109 */     list.add(this.outputItem.get());
/* 110 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IERecipeSerializer<CokerUnitRecipe> getIESerializer() {
/* 115 */     return (IERecipeSerializer<CokerUnitRecipe>)Serializers.COKER_SERIALIZER.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\CokerUnitRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */