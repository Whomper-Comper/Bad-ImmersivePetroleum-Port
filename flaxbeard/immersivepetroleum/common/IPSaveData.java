/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ 
/*    */ public class IPSaveData
/*    */   extends SavedData
/*    */ {
/*    */   public static final String dataName = "ImmersivePetroleum-SaveData";
/*    */   private static IPSaveData INSTANCE;
/*    */   
/*    */   public IPSaveData() {
/* 18 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   public IPSaveData(CompoundTag nbt) {
/* 22 */     INSTANCE = this;
/*    */     
/* 24 */     ListTag lubricatedList = nbt.m_128437_("lubricated", 10);
/* 25 */     LubricatedHandler.lubricatedTiles.clear();
/* 26 */     for (int i = 0; i < lubricatedList.size(); i++) {
/* 27 */       CompoundTag tag = lubricatedList.m_128728_(i);
/* 28 */       LubricatedHandler.LubricatedTileInfo info = new LubricatedHandler.LubricatedTileInfo(tag);
/* 29 */       LubricatedHandler.lubricatedTiles.add(info);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompoundTag m_7176_(@Nonnull CompoundTag nbt) {
/* 36 */     ListTag lubricatedList = new ListTag();
/* 37 */     for (LubricatedHandler.LubricatedTileInfo info : LubricatedHandler.lubricatedTiles) {
/* 38 */       if (info != null) {
/* 39 */         CompoundTag tag = info.writeToNBT();
/* 40 */         lubricatedList.add(tag);
/*    */       } 
/*    */     } 
/* 43 */     nbt.m_128365_("lubricated", (Tag)lubricatedList);
/*    */     
/* 45 */     return nbt;
/*    */   }
/*    */   
/*    */   public static void markDirty() {
/* 49 */     if (INSTANCE != null)
/* 50 */       INSTANCE.m_77762_(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPSaveData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */