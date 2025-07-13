/*    */ package flaxbeard.immersivepetroleum.common.util.loot;
/*    */ 
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.world.level.storage.loot.Serializer;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
/*    */ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*    */ import net.minecraftforge.registries.DeferredRegister;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ public class IPLootFunctions {
/* 11 */   private static final DeferredRegister<LootPoolEntryType> REGISTER = DeferredRegister.create(Registry.f_122815_, "immersivepetroleum");
/*    */ 
/*    */   
/* 14 */   public static final RegistryObject<LootPoolEntryType> TILE_DROP = REGISTER.register(IPTileDropLootEntry.ID
/* 15 */       .m_135815_(), () -> new LootPoolEntryType((Serializer)new IPTileDropLootEntry.Serializer()));
/*    */ 
/*    */   
/*    */   public static void modConstruction() {
/* 19 */     REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\loot\IPLootFunctions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */