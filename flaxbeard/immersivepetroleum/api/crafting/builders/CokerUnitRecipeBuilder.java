/*    */ package flaxbeard.immersivepetroleum.api.crafting.builders;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
/*    */ import blusunrize.immersiveengineering.api.crafting.builders.IEFinishedRecipe;
/*    */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class CokerUnitRecipeBuilder extends IEFinishedRecipe<CokerUnitRecipeBuilder> {
/*    */   public static CokerUnitRecipeBuilder builder(ItemStack output, TagKey<Fluid> outputFluid, int fluidOutAmount) {
/* 17 */     Objects.requireNonNull(output);
/* 18 */     if (output.m_41619_()) {
/* 19 */       throw new IllegalArgumentException("Input stack cannot be empty.");
/*    */     }
/* 21 */     return ((CokerUnitRecipeBuilder)(new CokerUnitRecipeBuilder())
/* 22 */       .addResult(output))
/* 23 */       .addOutputFluid(outputFluid, fluidOutAmount);
/*    */   }
/*    */   
/*    */   public static CokerUnitRecipeBuilder builder(ItemStack output, Fluid fluid, int amount) {
/* 27 */     Objects.requireNonNull(output);
/* 28 */     if (output.m_41619_()) {
/* 29 */       throw new IllegalArgumentException("Input stack cannot be empty.");
/*    */     }
/* 31 */     return ((CokerUnitRecipeBuilder)(new CokerUnitRecipeBuilder())
/* 32 */       .addResult(output))
/* 33 */       .addOutputFluid(fluid, amount);
/*    */   }
/*    */   
/*    */   private CokerUnitRecipeBuilder() {
/* 37 */     super((IERecipeSerializer)Serializers.COKER_SERIALIZER.get());
/*    */   }
/*    */   
/*    */   public CokerUnitRecipeBuilder addInputItem(TagKey<Item> item, int amount) {
/* 41 */     return (CokerUnitRecipeBuilder)addInput(new IngredientWithSize(item, amount));
/*    */   }
/*    */   
/*    */   public CokerUnitRecipeBuilder addInputFluid(TagKey<Fluid> fluidTag, int amount) {
/* 45 */     return (CokerUnitRecipeBuilder)addFluidTag("inputfluid", new FluidTagInput(fluidTag.f_203868_(), amount));
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public CokerUnitRecipeBuilder addOutputFluid(TagKey<Fluid> fluidTag, int amount) {
/* 50 */     return (CokerUnitRecipeBuilder)addFluidTag("resultfluid", new FluidTagInput(fluidTag.f_203868_(), amount));
/*    */   }
/*    */   
/*    */   public CokerUnitRecipeBuilder addOutputFluid(Fluid fluid, int amount) {
/* 54 */     return (CokerUnitRecipeBuilder)addFluid("resultfluid", new FluidStack(fluid, amount));
/*    */   }
/*    */   
/*    */   public CokerUnitRecipeBuilder setTimeAndEnergy(int time, int energy) {
/* 58 */     return (CokerUnitRecipeBuilder)((CokerUnitRecipeBuilder)setTime(time)).setEnergy(energy);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\builders\CokerUnitRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */