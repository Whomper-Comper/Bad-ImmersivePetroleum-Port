/*    */ package flaxbeard.immersivepetroleum.common.util.loot;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Serializer
/*    */   extends LootPoolSingletonContainer.Serializer<IPTileDropLootEntry>
/*    */ {
/*    */   @Nonnull
/*    */   protected IPTileDropLootEntry deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context, int weight, int quality, @Nonnull LootItemCondition[] conditions, @Nonnull LootItemFunction[] functions) {
/* 53 */     return new IPTileDropLootEntry(weight, quality, conditions, functions);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\loot\IPTileDropLootEntry$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */