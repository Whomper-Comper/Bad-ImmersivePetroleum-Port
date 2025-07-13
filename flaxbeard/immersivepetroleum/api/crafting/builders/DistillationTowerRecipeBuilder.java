/*     */ package flaxbeard.immersivepetroleum.api.crafting.builders;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.ApiUtils;
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import blusunrize.immersiveengineering.api.crafting.builders.IEFinishedRecipe;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.ShapedRecipe;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistillationTowerRecipeBuilder
/*     */   extends IEFinishedRecipe<DistillationTowerRecipeBuilder>
/*     */ {
/*     */   public static DistillationTowerRecipeBuilder builder(FluidStack... fluidOutput) {
/*  30 */     if (fluidOutput == null || fluidOutput.length == 0) {
/*  31 */       throw new IllegalArgumentException("Fluid output missing. It's required.");
/*     */     }
/*  33 */     DistillationTowerRecipeBuilder b = new DistillationTowerRecipeBuilder();
/*  34 */     b.addFluids("results", fluidOutput);
/*  35 */     return b;
/*     */   }
/*     */ 
/*     */   
/*  39 */   private final List<Tuple<ItemStack, Double>> byproducts = new ArrayList<>();
/*     */   
/*     */   private DistillationTowerRecipeBuilder() {
/*  42 */     super((IERecipeSerializer)Serializers.DISTILLATION_SERIALIZER.get());
/*  43 */     addWriter(jsonObject -> {
/*     */           if (this.byproducts.size() > 0) {
/*     */             JsonArray main = new JsonArray();
/*     */             this.byproducts.forEach(());
/*     */             jsonObject.add("byproducts", (JsonElement)main);
/*     */             this.byproducts.clear();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DistillationTowerRecipeBuilder addByproduct(ItemStack byproduct, int chance) {
/*  61 */     return addByproduct(byproduct, chance / 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DistillationTowerRecipeBuilder addByproduct(ItemStack byproduct, double chance) {
/*  72 */     this.byproducts.add(new Tuple(byproduct, Double.valueOf(Mth.m_14008_(chance, 0.0D, 1.0D))));
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder setTimeAndEnergy(int time, int energy) {
/*  77 */     return (DistillationTowerRecipeBuilder)((DistillationTowerRecipeBuilder)setTime(time)).setEnergy(energy);
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder addInput(TagKey<Fluid> fluidTag, int amount) {
/*  81 */     return (DistillationTowerRecipeBuilder)addFluidTag("input", fluidTag, amount);
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder addInput(Fluid fluid, int amount) {
/*  85 */     return addInput(new FluidStack(fluid, amount));
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder addInput(FluidStack fluidStack) {
/*  89 */     return (DistillationTowerRecipeBuilder)addFluid("input", fluidStack);
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder addFluids(String key, FluidStack... fluidStacks) {
/*  93 */     return (DistillationTowerRecipeBuilder)addWriter(jsonObject -> {
/*     */           JsonArray array = new JsonArray();
/*     */           for (FluidStack stack : fluidStacks)
/*     */             array.add(ApiUtils.jsonSerializeFluidStack(stack)); 
/*     */           jsonObject.add(key, (JsonElement)array);
/*     */         });
/*     */   }
/*     */   
/*     */   public DistillationTowerRecipeBuilder addItems(String key, ItemStack... itemStacks) {
/* 102 */     return (DistillationTowerRecipeBuilder)addWriter(jsonObject -> {
/*     */           JsonArray array = new JsonArray();
/*     */           for (ItemStack stack : itemStacks) {
/*     */             array.add((JsonElement)serializeItemStack(stack));
/*     */           }
/*     */           jsonObject.add(key, (JsonElement)array);
/*     */         });
/*     */   }
/*     */   
/*     */   public static Tuple<ItemStack, Double> deserializeItemStackWithChance(JsonObject jsonObject) {
/* 112 */     if (jsonObject.has("item")) {
/* 113 */       double chance = 1.0D;
/* 114 */       if (jsonObject.has("chance")) {
/* 115 */         chance = jsonObject.get("chance").getAsDouble();
/*     */       }
/* 117 */       ItemStack stack = ShapedRecipe.m_151274_(jsonObject);
/* 118 */       return new Tuple(stack, Double.valueOf(chance));
/*     */     } 
/*     */     
/* 121 */     throw new IllegalArgumentException("Unexpected json object.");
/*     */   }
/*     */   
/* 124 */   private static final DistillationTowerRecipeBuilder dummy = new DistillationTowerRecipeBuilder();
/*     */   public static JsonObject serializerItemStackWithChance(@Nonnull Tuple<ItemStack, Double> tuple) {
/* 126 */     JsonObject itemJson = dummy.serializeItemStack((ItemStack)tuple.m_14418_());
/* 127 */     itemJson.addProperty("chance", ((Double)tuple.m_14419_()).toString());
/* 128 */     return itemJson;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\builders\DistillationTowerRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */