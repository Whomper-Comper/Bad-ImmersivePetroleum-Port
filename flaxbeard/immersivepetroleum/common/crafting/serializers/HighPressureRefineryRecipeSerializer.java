/*    */ package flaxbeard.immersivepetroleum.common.crafting.serializers;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.builders.DistillationTowerRecipeBuilder;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import net.minecraft.util.Tuple;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraftforge.common.crafting.conditions.ICondition;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class HighPressureRefineryRecipeSerializer
/*    */   extends IERecipeSerializer<HighPressureRefineryRecipe> {
/*    */   public HighPressureRefineryRecipe readFromJson(ResourceLocation recipeId, JsonObject json, ICondition.IContext context) {
/* 25 */     FluidStack output = ApiUtils.jsonDeserializeFluidStack(GsonHelper.m_13930_(json, "result"));
/* 26 */     FluidTagInput inputFluid0 = FluidTagInput.deserialize((JsonElement)GsonHelper.m_13930_(json, "input"));
/* 27 */     FluidTagInput inputFluid1 = null;
/* 28 */     Tuple<ItemStack, Double> itemWithChance = new Tuple(ItemStack.f_41583_, Double.valueOf(0.0D));
/*    */     
/* 30 */     if (json.has("secondary_input")) {
/* 31 */       inputFluid1 = FluidTagInput.deserialize((JsonElement)GsonHelper.m_13930_(json, "secondary_input"));
/*    */     }
/*    */     
/* 34 */     if (json.has("secondary_result")) {
/* 35 */       itemWithChance = DistillationTowerRecipeBuilder.deserializeItemStackWithChance(json.get("secondary_result").getAsJsonObject());
/*    */     }
/*    */     
/* 38 */     int energy = GsonHelper.m_13927_(json, "energy");
/* 39 */     int time = GsonHelper.m_13927_(json, "time");
/*    */     
/* 41 */     return new HighPressureRefineryRecipe(recipeId, output, (ItemStack)itemWithChance.m_14418_(), inputFluid0, inputFluid1, ((Double)itemWithChance.m_14419_()).doubleValue(), energy, time);
/*    */   }
/*    */ 
/*    */   
/*    */   public HighPressureRefineryRecipe fromNetwork(@Nonnull ResourceLocation id, FriendlyByteBuf buffer) {
/* 46 */     ItemStack outputItem = buffer.m_130267_();
/* 47 */     double chance = buffer.readDouble();
/*    */     
/* 49 */     FluidStack output = buffer.readFluidStack();
/* 50 */     FluidTagInput inputFluid0 = FluidTagInput.read(buffer);
/* 51 */     FluidTagInput inputFluid1 = null;
/*    */     
/* 53 */     boolean hasSecondary = buffer.readBoolean();
/* 54 */     if (hasSecondary) {
/* 55 */       inputFluid1 = FluidTagInput.read(buffer);
/*    */     }
/*    */     
/* 58 */     int energy = buffer.readInt();
/* 59 */     int time = buffer.readInt();
/*    */     
/* 61 */     return new HighPressureRefineryRecipe(id, output, outputItem, inputFluid0, inputFluid1, chance, energy, time);
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(FriendlyByteBuf buffer, HighPressureRefineryRecipe recipe) {
/* 66 */     buffer.m_130055_(recipe.outputItem);
/* 67 */     buffer.writeDouble(recipe.chance);
/*    */     
/* 69 */     buffer.writeFluidStack(recipe.output);
/* 70 */     recipe.inputFluid.write(buffer);
/*    */     
/* 72 */     boolean hasSecondary = (recipe.getSecondaryInputFluid() != null);
/* 73 */     buffer.writeBoolean(hasSecondary);
/* 74 */     if (hasSecondary) {
/* 75 */       recipe.inputFluidSecondary.write(buffer);
/*    */     }
/*    */     
/* 78 */     buffer.writeInt(recipe.getTotalProcessEnergy());
/* 79 */     buffer.writeInt(recipe.getTotalProcessTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getIcon() {
/* 84 */     return new ItemStack((ItemLike)IPContent.Multiblock.HYDROTREATER.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\serializers\HighPressureRefineryRecipeSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */