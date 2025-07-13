/*    */ package flaxbeard.immersivepetroleum.common.crafting.serializers;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraftforge.common.crafting.conditions.ICondition;
/*    */ 
/*    */ public class ReservoirSerializer
/*    */   extends IERecipeSerializer<ReservoirType>
/*    */ {
/*    */   public ReservoirType readFromJson(ResourceLocation recipeId, JsonObject json, ICondition.IContext context) {
/* 23 */     String name = GsonHelper.m_13906_(json, "name");
/* 24 */     ResourceLocation fluid = new ResourceLocation(GsonHelper.m_13906_(json, "fluid"));
/* 25 */     int min = GsonHelper.m_13927_(json, "fluidminimum");
/* 26 */     int max = GsonHelper.m_13927_(json, "fluidcapacity");
/* 27 */     int trace = GsonHelper.m_13927_(json, "fluidtrace");
/* 28 */     int equilibrium = GsonHelper.m_13824_(json, "equilibrium", 0);
/* 29 */     int weight = GsonHelper.m_13927_(json, "weight");
/*    */     
/* 31 */     ReservoirType reservoir = new ReservoirType(name, recipeId, fluid, min, max, trace, equilibrium, weight);
/*    */     
/* 33 */     ImmersivePetroleum.log.debug("Loaded reservoir {} as {}, with {}mB to {}mB of {} and {}mB trace at {}mB equilibrium, with {} of weight.", recipeId, name, 
/* 34 */         Integer.valueOf(min), Integer.valueOf(max), fluid, Integer.valueOf(trace), Integer.valueOf(equilibrium), Integer.valueOf(weight));
/*    */     
/* 36 */     if (GsonHelper.m_13900_(json, "dimensions")) {
/* 37 */       JsonObject dimensions = GsonHelper.m_13930_(json, "dimensions");
/*    */       
/* 39 */       boolean isBlacklist = GsonHelper.m_13912_(dimensions, "isBlacklist");
/*    */       
/* 41 */       if (GsonHelper.m_13900_(dimensions, "list")) {
/* 42 */         JsonArray array = GsonHelper.m_13933_(dimensions, "list");
/*    */         
/* 44 */         List<ResourceLocation> list = new ArrayList<>();
/* 45 */         array.forEach(rl -> list.add(new ResourceLocation(rl.getAsString())));
/* 46 */         reservoir.setDimensions(isBlacklist, list);
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     if (GsonHelper.m_13900_(json, "biomes")) {
/* 51 */       JsonObject biomes = GsonHelper.m_13930_(json, "biomes");
/*    */       
/* 53 */       boolean isBlacklist = GsonHelper.m_13912_(biomes, "isBlacklist");
/*    */       
/* 55 */       if (GsonHelper.m_13900_(biomes, "list")) {
/* 56 */         JsonArray array = GsonHelper.m_13933_(biomes, "list");
/*    */         
/* 58 */         List<ResourceLocation> list = new ArrayList<>();
/* 59 */         array.forEach(rl -> list.add(new ResourceLocation(rl.getAsString())));
/* 60 */         reservoir.setBiomes(isBlacklist, list);
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return reservoir;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReservoirType fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
/* 69 */     return new ReservoirType(buffer.m_130260_());
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(FriendlyByteBuf buffer, ReservoirType recipe) {
/* 74 */     buffer.m_130079_(recipe.writeToNBT());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getIcon() {
/* 79 */     return ItemStack.f_41583_;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\serializers\ReservoirSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */