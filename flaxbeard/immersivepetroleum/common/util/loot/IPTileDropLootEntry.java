/*    */ package flaxbeard.immersivepetroleum.common.util.loot;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IBlockEntityDrop;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ 
/*    */ public class IPTileDropLootEntry
/*    */   extends LootPoolSingletonContainer
/*    */ {
/* 23 */   public static final ResourceLocation ID = ResourceUtils.ip("tile_drop");
/*    */   
/*    */   protected IPTileDropLootEntry(int weightIn, int qualityIn, LootItemCondition[] conditionsIn, LootItemFunction[] functionsIn) {
/* 26 */     super(weightIn, qualityIn, conditionsIn, functionsIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void m_6948_(@Nonnull Consumer<ItemStack> stackConsumer, LootContext context) {
/* 31 */     if (context.m_78936_(LootContextParams.f_81462_)) {
/* 32 */       BlockEntity te = (BlockEntity)context.m_78953_(LootContextParams.f_81462_);
/* 33 */       if (te instanceof IBlockEntityDrop) { IBlockEntityDrop dropTe = (IBlockEntityDrop)te;
/* 34 */         dropTe.getBlockEntityDrop(context).forEach(stackConsumer); }
/*    */     
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public LootPoolEntryType m_6751_() {
/* 42 */     return (LootPoolEntryType)IPLootFunctions.TILE_DROP.get();
/*    */   }
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> builder() {
/* 46 */     return m_79687_(IPTileDropLootEntry::new);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootPoolSingletonContainer.Serializer<IPTileDropLootEntry> {
/*    */     @Nonnull
/*    */     protected IPTileDropLootEntry deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context, int weight, int quality, @Nonnull LootItemCondition[] conditions, @Nonnull LootItemFunction[] functions) {
/* 53 */       return new IPTileDropLootEntry(weight, quality, conditions, functions);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\loot\IPTileDropLootEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */