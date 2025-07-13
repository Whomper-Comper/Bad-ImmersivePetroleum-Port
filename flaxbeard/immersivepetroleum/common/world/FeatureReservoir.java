/*    */ package flaxbeard.immersivepetroleum.common.world;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkAccess;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class FeatureReservoir
/*    */   extends Feature<NoneFeatureConfiguration> {
/* 17 */   public static HashMultimap<ResourceKey<Level>, ChunkPos> generatedReservoirChunks = HashMultimap.create();
/*    */   
/*    */   public FeatureReservoir() {
/* 20 */     super(NoneFeatureConfiguration.f_67815_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
/* 25 */     WorldGenLevel reader = pContext.m_159774_();
/* 26 */     BlockPos pos = pContext.m_159777_();
/* 27 */     ReservoirHandler.initGenerator(reader);
/*    */     
/* 29 */     ResourceKey<Level> dimension = reader.m_6018_().m_46472_();
/* 30 */     ChunkAccess chunk = reader.m_46865_(pos);
/* 31 */     if (!generatedReservoirChunks.containsEntry(dimension, chunk.m_7697_())) {
/* 32 */       generatedReservoirChunks.put(dimension, chunk.m_7697_());
/*    */       
/* 34 */       ReservoirHandler.scanChunkForNewReservoirs(reader.m_6018_(), chunk.m_7697_(), pContext.m_225041_());
/* 35 */       return true;
/*    */     } 
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\world\FeatureReservoir.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */