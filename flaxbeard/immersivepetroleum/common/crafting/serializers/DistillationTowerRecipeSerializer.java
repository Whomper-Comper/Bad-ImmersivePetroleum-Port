/*     */ package flaxbeard.immersivepetroleum.common.crafting.serializers;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.ApiUtils;
/*     */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.builders.DistillationTowerRecipeBuilder;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraftforge.common.crafting.conditions.ICondition;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class DistillationTowerRecipeSerializer
/*     */   extends IERecipeSerializer<DistillationTowerRecipe> {
/*     */   public DistillationTowerRecipe readFromJson(ResourceLocation recipeId, JsonObject json, ICondition.IContext context) {
/*  29 */     FluidTagInput input = FluidTagInput.deserialize((JsonElement)GsonHelper.m_13930_(json, "input"));
/*  30 */     JsonArray fluidResults = GsonHelper.m_13933_(json, "results");
/*     */     
/*  32 */     FluidStack[] fluidOutput = new FluidStack[fluidResults.size()];
/*  33 */     for (int i = 0; i < fluidOutput.length; i++) {
/*  34 */       fluidOutput[i] = ApiUtils.jsonDeserializeFluidStack(fluidResults.get(i).getAsJsonObject());
/*     */     }
/*  36 */     ItemStack[] array0 = new ItemStack[0];
/*  37 */     double[] array1 = new double[0];
/*  38 */     if (json.has("byproducts")) {
/*  39 */       JsonArray itemResults = GsonHelper.m_13933_(json, "byproducts");
/*     */       
/*  41 */       List<ItemStack> byproducts = new ArrayList<>(0);
/*  42 */       List<Double> chances = new ArrayList<>(0); int j;
/*  43 */       for (j = 0; j < itemResults.size(); j++) {
/*  44 */         Tuple<ItemStack, Double> chancedStack = DistillationTowerRecipeBuilder.deserializeItemStackWithChance(itemResults.get(j).getAsJsonObject());
/*     */         
/*  46 */         byproducts.add((ItemStack)chancedStack.m_14418_());
/*  47 */         chances.add((Double)chancedStack.m_14419_());
/*     */       } 
/*     */       
/*  50 */       if (byproducts.size() != chances.size()) {
/*  51 */         int d = Math.abs(chances.size() - byproducts.size());
/*  52 */         throw new JsonSyntaxException("" + d + " byproduct" + d + " a missing value or too many.");
/*     */       } 
/*     */       
/*  55 */       array0 = byproducts.<ItemStack>toArray(new ItemStack[0]);
/*  56 */       array1 = new double[chances.size()];
/*  57 */       for (j = 0; j < chances.size(); j++) {
/*  58 */         array1[j] = ((Double)chances.get(j)).doubleValue();
/*     */       }
/*     */     } 
/*  61 */     int energy = GsonHelper.m_13927_(json, "energy");
/*  62 */     int time = GsonHelper.m_13927_(json, "time");
/*     */     
/*  64 */     return new DistillationTowerRecipe(recipeId, fluidOutput, array0, input, energy, time, array1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DistillationTowerRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
/*  69 */     FluidStack[] fluidOutput = new FluidStack[buffer.readInt()];
/*  70 */     for (int i = 0; i < fluidOutput.length; i++) {
/*  71 */       fluidOutput[i] = buffer.readFluidStack();
/*     */     }
/*  73 */     ItemStack[] byproducts = new ItemStack[buffer.readInt()];
/*  74 */     for (int j = 0; j < byproducts.length; j++) {
/*  75 */       byproducts[j] = buffer.m_130267_();
/*     */     }
/*  77 */     double[] chances = new double[buffer.readInt()];
/*  78 */     for (int k = 0; k < chances.length; k++) {
/*  79 */       chances[k] = buffer.readDouble();
/*     */     }
/*  81 */     FluidTagInput input = FluidTagInput.read(buffer);
/*  82 */     int energy = buffer.readInt();
/*  83 */     int time = buffer.readInt();
/*     */     
/*  85 */     return new DistillationTowerRecipe(recipeId, fluidOutput, byproducts, input, energy, time, chances);
/*     */   }
/*     */ 
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf buffer, DistillationTowerRecipe recipe) {
/*  90 */     buffer.writeInt(recipe.getFluidOutputs().size());
/*  91 */     for (FluidStack stack : recipe.getFluidOutputs()) {
/*  92 */       buffer.writeFluidStack(stack);
/*     */     }
/*  94 */     buffer.writeInt(recipe.getItemOutputs().size());
/*  95 */     for (ItemStack stack : recipe.getItemOutputs()) {
/*  96 */       buffer.m_130055_(stack);
/*     */     }
/*  98 */     buffer.writeInt((recipe.chances()).length);
/*  99 */     for (double d : recipe.chances()) {
/* 100 */       buffer.writeDouble(d);
/*     */     }
/* 102 */     recipe.getInputFluid().write(buffer);
/* 103 */     buffer.writeInt(recipe.getTotalProcessEnergy());
/* 104 */     buffer.writeInt(recipe.getTotalProcessTime());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getIcon() {
/* 109 */     return new ItemStack((ItemLike)IPContent.Multiblock.DISTILLATIONTOWER.get());
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\serializers\DistillationTowerRecipeSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */