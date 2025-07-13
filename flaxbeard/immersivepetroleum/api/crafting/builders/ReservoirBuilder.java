/*     */ package flaxbeard.immersivepetroleum.api.crafting.builders;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import blusunrize.immersiveengineering.api.crafting.builders.IEFinishedRecipe;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ 
/*     */ public class ReservoirBuilder
/*     */   extends IEFinishedRecipe<ReservoirBuilder>
/*     */ {
/*     */   private String fluid;
/*     */   private int fluidMinimum;
/*     */   private int fluidMaximum;
/*     */   private int fluidTrace;
/*     */   private int equilibrium;
/*     */   private int weight;
/*     */   private boolean isDimBlacklist = false;
/*  26 */   private final JsonArray dimensions = new JsonArray();
/*     */   
/*     */   private boolean isBioBlacklist = false;
/*  29 */   private final JsonArray biomes = new JsonArray();
/*     */   
/*     */   private ReservoirBuilder() {
/*  32 */     super((IERecipeSerializer)Serializers.RESERVOIR_SERIALIZER.get());
/*  33 */     addWriter(writer -> {
/*     */           writer.addProperty("fluid", this.fluid);
/*     */           
/*     */           writer.addProperty("fluidminimum", Integer.valueOf(this.fluidMinimum));
/*     */           writer.addProperty("fluidcapacity", Integer.valueOf(this.fluidMaximum));
/*     */           writer.addProperty("fluidtrace", Integer.valueOf(this.fluidTrace));
/*     */           writer.addProperty("weight", Integer.valueOf(this.weight));
/*     */           writer.addProperty("equilibrium", Integer.valueOf(this.equilibrium));
/*     */         });
/*  42 */     addWriter(writer -> {
/*     */           JsonObject dimensions = new JsonObject();
/*     */           
/*     */           dimensions.add("isBlacklist", (JsonElement)new JsonPrimitive(Boolean.valueOf(this.isDimBlacklist)));
/*     */           dimensions.add("list", (JsonElement)this.dimensions);
/*     */           writer.add("dimensions", (JsonElement)dimensions);
/*     */         });
/*  49 */     addWriter(writer -> {
/*     */           JsonObject biomes = new JsonObject();
/*     */           biomes.add("isBlacklist", (JsonElement)new JsonPrimitive(Boolean.valueOf(this.isBioBlacklist)));
/*     */           biomes.add("list", (JsonElement)this.biomes);
/*     */           writer.add("biomes", (JsonElement)biomes);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReservoirBuilder builder(String name) {
/*  64 */     return (ReservoirBuilder)(new ReservoirBuilder()).addWriter(writer -> writer.addProperty("name", name));
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
/*     */   public static ReservoirBuilder builder(String name, Fluid fluid, double min, double max, double trace, int weight) {
/*  79 */     return builder(name).setFluid(fluid).min(min).max(max).trace(trace).weight(weight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirBuilder setFluid(Fluid fluid) {
/*  89 */     this.fluid = RegistryUtils.getRegistryNameOf(fluid).toString();
/*  90 */     return this;
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
/*     */   public ReservoirBuilder min(double amount) {
/* 103 */     this.fluidMinimum = (int)Math.floor(amount * 1000.0D);
/* 104 */     return this;
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
/*     */   public ReservoirBuilder max(double amount) {
/* 117 */     this.fluidMaximum = (int)Math.floor(amount * 1000.0D);
/* 118 */     return this;
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
/*     */   public ReservoirBuilder trace(double amount) {
/* 131 */     this.fluidTrace = (int)Math.floor(amount * 1000.0D);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirBuilder weight(int weight) {
/* 142 */     this.weight = weight;
/* 143 */     return this;
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
/*     */   public ReservoirBuilder equilibrium(double amount) {
/* 156 */     this.equilibrium = (int)Math.floor(amount * 1000.0D);
/* 157 */     return this;
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
/*     */   public ReservoirBuilder setDimensions(boolean isBlacklist, @Nonnull ResourceLocation[] dimensions) {
/* 171 */     if (this.dimensions.size() > 0) {
/* 172 */       throw new IllegalArgumentException("Dimensions list already set.");
/*     */     }
/* 174 */     Objects.requireNonNull(dimensions);
/*     */     
/* 176 */     this.isDimBlacklist = isBlacklist;
/* 177 */     for (ResourceLocation rl : dimensions) {
/* 178 */       if (rl != null && !this.dimensions.contains((JsonElement)new JsonPrimitive(rl.toString()))) {
/* 179 */         this.dimensions.add(rl.toString());
/*     */       }
/*     */     } 
/*     */     
/* 183 */     return this;
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
/*     */   public ReservoirBuilder setBiomes(boolean isBlacklist, @Nonnull ResourceLocation[] biomes) {
/* 197 */     if (this.biomes.size() > 0) {
/* 198 */       throw new IllegalArgumentException("Biomes list already set.");
/*     */     }
/* 200 */     Objects.requireNonNull(biomes);
/*     */     
/* 202 */     this.isBioBlacklist = isBlacklist;
/* 203 */     for (ResourceLocation rl : biomes) {
/* 204 */       if (rl != null && !this.biomes.contains((JsonElement)new JsonPrimitive(rl.toString()))) {
/* 205 */         this.biomes.add(rl.toString());
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\builders\ReservoirBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */