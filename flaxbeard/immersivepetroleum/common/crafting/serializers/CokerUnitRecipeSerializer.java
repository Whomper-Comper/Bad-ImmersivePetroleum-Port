/*    */ package flaxbeard.immersivepetroleum.common.crafting.serializers;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraftforge.common.crafting.conditions.ICondition;
/*    */ import net.minecraftforge.common.util.Lazy;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class CokerUnitRecipeSerializer
/*    */   extends IERecipeSerializer<CokerUnitRecipe> {
/*    */   public CokerUnitRecipe readFromJson(ResourceLocation recipeId, JsonObject json, ICondition.IContext context) {
/* 25 */     FluidStack outputFluid = ApiUtils.jsonDeserializeFluidStack(GsonHelper.m_13930_(json, "resultfluid"));
/* 26 */     FluidTagInput inputFluid = FluidTagInput.deserialize((JsonElement)GsonHelper.m_13930_(json, "inputfluid"));
/*    */     
/* 28 */     Lazy<ItemStack> outputItem = readOutput(json.get("result"));
/* 29 */     IngredientWithSize inputItem = IngredientWithSize.deserialize((JsonElement)GsonHelper.m_13930_(json, "input"));
/*    */     
/* 31 */     int energy = GsonHelper.m_13927_(json, "energy");
/* 32 */     int time = GsonHelper.m_13927_(json, "time");
/*    */     
/* 34 */     return new CokerUnitRecipe(recipeId, outputItem, outputFluid, inputItem, inputFluid, energy, time);
/*    */   }
/*    */ 
/*    */   
/*    */   public CokerUnitRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
/* 39 */     IngredientWithSize inputItem = IngredientWithSize.read(buffer);
/* 40 */     ItemStack outputItem = buffer.m_130267_();
/*    */     
/* 42 */     FluidTagInput inputFluid = FluidTagInput.read(buffer);
/* 43 */     FluidStack outputFluid = FluidStack.readFromPacket(buffer);
/*    */     
/* 45 */     int energy = buffer.readInt();
/* 46 */     int time = buffer.readInt();
/*    */     
/* 48 */     return new CokerUnitRecipe(recipeId, Lazy.of(() -> outputItem), outputFluid, inputItem, inputFluid, energy, time);
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(@Nonnull FriendlyByteBuf buffer, CokerUnitRecipe recipe) {
/* 53 */     recipe.inputItem.write(buffer);
/* 54 */     buffer.m_130055_((ItemStack)recipe.outputItem.get());
/*    */     
/* 56 */     recipe.inputFluid.write(buffer);
/* 57 */     recipe.outputFluid.writeToPacket(buffer);
/*    */     
/* 59 */     buffer.writeInt(recipe.getTotalProcessEnergy());
/* 60 */     buffer.writeInt(recipe.getTotalProcessTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getIcon() {
/* 65 */     return new ItemStack((ItemLike)IPContent.Multiblock.COKERUNIT.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\serializers\CokerUnitRecipeSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */