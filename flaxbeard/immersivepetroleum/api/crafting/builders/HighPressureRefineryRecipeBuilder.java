/*    */ package flaxbeard.immersivepetroleum.api.crafting.builders;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import blusunrize.immersiveengineering.api.crafting.builders.IEFinishedRecipe;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class HighPressureRefineryRecipeBuilder extends IEFinishedRecipe<HighPressureRefineryRecipeBuilder> {
/*    */   public static HighPressureRefineryRecipeBuilder builder(FluidStack fluidOutput, int energy, int time) {
/* 16 */     return (new HighPressureRefineryRecipeBuilder())
/* 17 */       .setTimeAndEnergy(time, energy)
/* 18 */       .addResultFluid(fluidOutput);
/*    */   }
/*    */   
/*    */   protected HighPressureRefineryRecipeBuilder() {
/* 22 */     super((IERecipeSerializer)Serializers.HYDROTREATER_SERIALIZER.get());
/*    */   }
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addResultFluid(FluidStack fluid) {
/* 26 */     return (HighPressureRefineryRecipeBuilder)addFluid("result", fluid);
/*    */   }
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addInputFluid(FluidStack fluid) {
/* 30 */     return (HighPressureRefineryRecipeBuilder)addFluid("input", fluid);
/*    */   }
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addInputFluid(FluidTagInput fluid) {
/* 34 */     return (HighPressureRefineryRecipeBuilder)addFluidTag("input", fluid);
/*    */   }
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addInputFluid(TagKey<Fluid> fluid, int amount) {
/* 38 */     return (HighPressureRefineryRecipeBuilder)addFluidTag("input", fluid, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addSecondaryInputFluid(FluidStack fluid) {
/* 43 */     return (HighPressureRefineryRecipeBuilder)addFluid("secondary_input", fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addSecondaryInputFluid(FluidTagInput fluid) {
/* 48 */     return (HighPressureRefineryRecipeBuilder)addFluidTag("secondary_input", fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addSecondaryInputFluid(TagKey<Fluid> fluid, int amount) {
/* 53 */     return (HighPressureRefineryRecipeBuilder)addFluidTag("secondary_input", fluid, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public HighPressureRefineryRecipeBuilder addItemWithChance(ItemStack item, double chance) {
/* 58 */     return (HighPressureRefineryRecipeBuilder)addWriter(jsonObject -> jsonObject.add("secondary_result", (JsonElement)serializerItemStackWithChance(item, chance)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected HighPressureRefineryRecipeBuilder setTimeAndEnergy(int time, int energy) {
/* 64 */     return (HighPressureRefineryRecipeBuilder)((HighPressureRefineryRecipeBuilder)setTime(time)).setEnergy(energy);
/*    */   }
/*    */   
/*    */   protected JsonObject serializerItemStackWithChance(ItemStack stack, double chance) {
/* 68 */     JsonObject itemJson = serializeItemStack(stack);
/* 69 */     itemJson.addProperty("chance", Double.toString(chance));
/* 70 */     return itemJson;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\builders\HighPressureRefineryRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */