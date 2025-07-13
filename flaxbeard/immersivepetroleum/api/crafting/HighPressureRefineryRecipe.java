/*     */ package flaxbeard.immersivepetroleum.api.crafting;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class HighPressureRefineryRecipe
/*     */   extends IPMultiblockRecipe
/*     */ {
/*  25 */   public static Map<ResourceLocation, HighPressureRefineryRecipe> recipes = new HashMap<>(); public final ItemStack outputItem; public final double chance;
/*     */   
/*     */   public static HighPressureRefineryRecipe findRecipe(@Nonnull FluidStack input, @Nonnull FluidStack secondary) {
/*  28 */     Objects.requireNonNull(input);
/*  29 */     Objects.requireNonNull(secondary);
/*     */     
/*  31 */     for (HighPressureRefineryRecipe recipe : recipes.values()) {
/*  32 */       if (secondary.isEmpty()) {
/*  33 */         if (recipe.inputFluidSecondary == null && recipe.inputFluid != null && recipe.inputFluid.test(input))
/*  34 */           return recipe; 
/*     */         continue;
/*     */       } 
/*  37 */       if (recipe.inputFluid != null && recipe.inputFluid.test(input) && recipe.inputFluidSecondary != null && recipe.inputFluidSecondary.test(secondary)) {
/*  38 */         return recipe;
/*     */       }
/*     */     } 
/*     */     
/*  42 */     return null;
/*     */   } public final FluidStack output; public final FluidTagInput inputFluid; @Nullable
/*     */   public final FluidTagInput inputFluidSecondary;
/*     */   public static boolean hasRecipeWithInput(@Nonnull FluidStack fluid, boolean ignoreAmount) {
/*  46 */     Objects.requireNonNull(fluid);
/*     */     
/*  48 */     if (!fluid.isEmpty()) {
/*  49 */       for (HighPressureRefineryRecipe recipe : recipes.values()) {
/*  50 */         if (recipe.inputFluid != null && ((
/*  51 */           !ignoreAmount && recipe.inputFluid.test(fluid)) || (ignoreAmount && recipe.inputFluid.testIgnoringAmount(fluid)))) {
/*  52 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasRecipeWithSecondaryInput(@Nonnull FluidStack fluid, boolean ignoreAmount) {
/*  61 */     Objects.requireNonNull(fluid);
/*     */     
/*  63 */     if (!fluid.isEmpty()) {
/*  64 */       for (HighPressureRefineryRecipe recipe : recipes.values()) {
/*  65 */         if (recipe.inputFluidSecondary != null && ((
/*  66 */           !ignoreAmount && recipe.inputFluidSecondary.test(fluid)) || (ignoreAmount && recipe.inputFluidSecondary.testIgnoringAmount(fluid)))) {
/*  67 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  72 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public HighPressureRefineryRecipe(ResourceLocation id, FluidStack output, ItemStack outputItem, FluidTagInput inputFluid, @Nullable FluidTagInput inputFluidSecondary, double chance, int energy, int time) {
/*  95 */     super(ItemStack.f_41583_, (IERecipeTypes.TypeWithClass)IPRecipeTypes.HYDROTREATER, id);
/*  96 */     this.output = output;
/*  97 */     this.outputItem = outputItem;
/*  98 */     this.inputFluid = inputFluid;
/*  99 */     this.inputFluidSecondary = inputFluidSecondary;
/* 100 */     this.chance = chance;
/*     */     
/* 102 */     this.fluidOutputList = Collections.singletonList(output);
/* 103 */     (new FluidTagInput[2])[0] = inputFluid; (new FluidTagInput[2])[1] = inputFluidSecondary; (new FluidTagInput[1])[0] = inputFluid; this.fluidInputList = Arrays.asList((inputFluidSecondary != null) ? new FluidTagInput[2] : new FluidTagInput[1]);
/*     */     
/* 105 */     timeAndEnergy(time, energy);
/* 106 */     Objects.requireNonNull(IPServerConfig.REFINING.hydrotreater_timeModifier); Objects.requireNonNull(IPServerConfig.REFINING.hydrotreater_energyModifier); modifyTimeAndEnergy(IPServerConfig.REFINING.hydrotreater_timeModifier::get, IPServerConfig.REFINING.hydrotreater_energyModifier::get);
/*     */   }
/*     */   
/*     */   public boolean hasSecondaryItem() {
/* 110 */     return (this.outputItem != null && !this.outputItem.m_41619_());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMultipleProcessTicks() {
/* 115 */     return 0;
/*     */   }
/*     */   
/*     */   public FluidTagInput getInputFluid() {
/* 119 */     return this.inputFluid;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FluidTagInput getSecondaryInputFluid() {
/* 124 */     return this.inputFluidSecondary;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getActualItemOutputs(BlockEntity tile) {
/* 129 */     NonNullList<ItemStack> list = NonNullList.m_122779_();
/* 130 */     Level level = tile.m_58904_();
/* 131 */     if (level.f_46441_.m_188501_() <= this.chance) {
/* 132 */       list.add(this.outputItem);
/*     */     }
/* 134 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IERecipeSerializer<HighPressureRefineryRecipe> getIESerializer() {
/* 139 */     return (IERecipeSerializer<HighPressureRefineryRecipe>)Serializers.HYDROTREATER_SERIALIZER.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\HighPressureRefineryRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */