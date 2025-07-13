/*    */ package flaxbeard.immersivepetroleum.common.blocks.interfaces;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ 
/*    */ public interface IBlockEntityDrop
/*    */ {
/*    */   @Nonnull
/*    */   List<ItemStack> getBlockEntityDrop(LootContext paramLootContext);
/*    */   
/*    */   default ItemStack getFirstBlockEntityDrop() {
/* 15 */     return getBlockEntityDrop(null).get(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\interfaces\IBlockEntityDrop.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */