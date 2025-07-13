/*     */ package flaxbeard.immersivepetroleum.api.reservoir;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.ReservoirRegionDataStorage;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReservoirHandler
/*     */ {
/*  40 */   private static final Map<Pair<ResourceKey<Level>, ColumnPos>, ReservoirIsland> CACHE = new HashMap<>();
/*     */   
/*  42 */   private static Map<ResourceLocation, Map<ResourceLocation, Integer>> totalWeightMap = new HashMap<>();
/*     */   
/*     */   private static long lastSeed;
/*     */   private static PerlinSimplexNoise generator;
/*     */   
/*     */   public static void scanChunkForNewReservoirs(ServerLevel world, ChunkPos chunkPos, RandomSource randomSource) {
/*  48 */     int chunkX = chunkPos.m_45604_();
/*  49 */     int chunkZ = chunkPos.m_45605_();
/*     */     
/*  51 */     ResourceKey<Level> dimensionKey = world.m_46472_();
/*  52 */     ResourceLocation dimensionRL = dimensionKey.m_135782_();
/*     */     
/*  54 */     ReservoirRegionDataStorage storage = ReservoirRegionDataStorage.get();
/*     */     
/*  56 */     for (int j = 0; j < 16; j++) {
/*  57 */       for (int i = 0; i < 16; i++) {
/*  58 */         int x = chunkX + i;
/*  59 */         int z = chunkZ + j;
/*     */ 
/*     */         
/*  62 */         if (getValueOf((Level)world, x, z) > -1.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  67 */           ResourceLocation biomeRL = RegistryUtils.getRegistryNameOf((Biome)world.m_204166_(new BlockPos(x, 64, z)).m_203334_());
/*     */           
/*  69 */           ColumnPos current = new ColumnPos(x, z);
/*  70 */           if (storage.existsAt(current)) {
/*     */             return;
/*     */           }
/*     */           
/*  74 */           ReservoirType reservoir = null;
/*  75 */           int totalWeight = getTotalWeight(dimensionRL, biomeRL);
/*  76 */           if (totalWeight > 0) {
/*  77 */             int weight = Math.abs(randomSource.m_188502_() % totalWeight);
/*  78 */             for (ReservoirType res : ReservoirType.map.values()) {
/*  79 */               if (res.getDimensions().valid(dimensionRL) && res.getBiomes().valid(biomeRL)) {
/*  80 */                 weight -= res.weight;
/*  81 */                 if (weight < 0) {
/*  82 */                   reservoir = res;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*  88 */             if (reservoir != null) {
/*  89 */               Set<ColumnPos> pol = new HashSet<>();
/*  90 */               next((Level)world, pol, x, z);
/*  91 */               List<ColumnPos> poly = optimizeIsland((Level)world, new ArrayList<>(pol));
/*     */               
/*  93 */               if (!poly.isEmpty()) {
/*  94 */                 int amount = (int)Mth.m_14179_(randomSource.m_188501_(), reservoir.minSize, reservoir.maxSize);
/*     */                 
/*  96 */                 ReservoirIsland island = new ReservoirIsland(poly, reservoir, amount);
/*  97 */                 storage.addIsland(dimensionKey, island);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static final double scale = 0.015625D;
/*     */   
/*     */   static final double d0 = 0.6666666666666666D;
/*     */   static final double d1 = 0.3333333333333333D;
/*     */   
/*     */   public static int getTotalWeight(ResourceLocation dimension, ResourceLocation biome) {
/* 113 */     Map<ResourceLocation, Integer> map = totalWeightMap.computeIfAbsent(dimension, k -> new HashMap<>());
/*     */     
/* 115 */     Integer totalWeight = map.get(biome);
/* 116 */     if (totalWeight == null) {
/* 117 */       totalWeight = Integer.valueOf(0);
/*     */       
/* 119 */       for (ReservoirType reservoir : ReservoirType.map.values()) {
/* 120 */         if (reservoir.getDimensions().valid(dimension) && reservoir.getBiomes().valid(biome)) {
/* 121 */           totalWeight = Integer.valueOf(totalWeight.intValue() + reservoir.weight);
/*     */         }
/*     */       } 
/*     */       
/* 125 */       map.put(biome, totalWeight);
/*     */     } 
/*     */     
/* 128 */     return totalWeight.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReservoirIsland getIsland(Level world, BlockPos pos) {
/* 133 */     return getIsland(world, Utils.toColumnPos(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReservoirIsland getIsland(Level world, ColumnPos pos) {
/* 138 */     if (world.f_46443_) {
/* 139 */       return null;
/*     */     }
/*     */     
/* 142 */     ResourceKey<Level> dimension = world.m_46472_();
/* 143 */     Pair<ResourceKey<Level>, ColumnPos> cacheKey = Pair.of(dimension, pos);
/* 144 */     synchronized (CACHE) {
/* 145 */       ReservoirIsland ret = CACHE.get(cacheKey);
/*     */       
/* 147 */       if (ret == null) {
/* 148 */         ReservoirIsland island = ReservoirRegionDataStorage.get().getIsland(world, pos);
/* 149 */         CACHE.put(cacheKey, island);
/* 150 */         return island;
/*     */       } 
/*     */       
/* 153 */       return ret;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReservoirIsland getIslandNoCache(Level world, BlockPos pos) {
/* 159 */     return getIslandNoCache(world, Utils.toColumnPos(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReservoirIsland getIslandNoCache(Level world, ColumnPos pos) {
/* 164 */     if (world.f_46443_) {
/* 165 */       return null;
/*     */     }
/*     */     
/* 168 */     ReservoirIsland island = ReservoirRegionDataStorage.get().getIsland(world, pos);
/* 169 */     return island;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReservoirType addReservoir(ResourceLocation id, ReservoirType reservoir) {
/* 180 */     ReservoirType.map.put(id, reservoir);
/* 181 */     return reservoir;
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
/*     */   
/*     */   public static double getValueOf(@Nonnull Level level, int x, int z) {
/* 197 */     if (!level.f_46443_ && level instanceof WorldGenLevel) { WorldGenLevel worldGen = (WorldGenLevel)level;
/* 198 */       initGenerator(worldGen); }
/*     */ 
/*     */     
/* 201 */     double noise = Math.abs(generator.m_75449_(x * 0.015625D, z * 0.015625D, false));
/* 202 */     if (noise > 0.6666666666666666D) {
/* 203 */       return (noise - 0.6666666666666666D) / 0.3333333333333333D;
/*     */     }
/*     */     
/* 206 */     return -1.0D;
/*     */   }
/*     */   
/*     */   public static void initGenerator(WorldGenLevel world) {
/* 210 */     if (generator == null || world.m_7328_() != lastSeed) {
/* 211 */       lastSeed = world.m_7328_();
/* 212 */       generator = new PerlinSimplexNoise((RandomSource)new WorldgenRandom((RandomSource)new SingleThreadedRandomSource(lastSeed)), (List)ImmutableList.of(Integer.valueOf(0)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static PerlinSimplexNoise getGenerator() {
/* 218 */     return generator;
/*     */   }
/*     */ 
/*     */   
/*     */   static void next(Level world, Set<ColumnPos> list, int x, int z) {
/* 223 */     if (getValueOf(world, x, z) > -1.0D && !list.contains(new ColumnPos(x, z))) {
/* 224 */       list.add(new ColumnPos(x, z));
/*     */       
/* 226 */       next(world, list, x + 1, z);
/* 227 */       next(world, list, x - 1, z);
/* 228 */       next(world, list, x, z + 1);
/* 229 */       next(world, list, x, z - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clearCache() {
/* 234 */     synchronized (CACHE) {
/* 235 */       CACHE.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void recalculateChances() {
/* 240 */     totalWeightMap.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<ColumnPos> optimizeIsland(Level world, List<ColumnPos> poly) {
/* 248 */     poly = keepOutline(world, poly);
/* 249 */     poly = makeDirectional(poly);
/* 250 */     poly = cullLines(poly);
/*     */     
/* 252 */     return poly;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<ColumnPos> keepOutline(Level world, List<ColumnPos> poly) {
/* 257 */     Set<ColumnPos> set = new HashSet<>();
/*     */     
/* 259 */     poly.forEach(pos -> {
/*     */           for (int z = -1; z <= 1; z++) {
/*     */             for (int x = -1; x <= 1; x++) {
/*     */               if (getValueOf(world, pos.f_140723_() + 1, pos.f_140724_()) == -1.0D) {
/*     */                 ColumnPos p = new ColumnPos(pos.f_140723_() + 1, pos.f_140724_());
/*     */                 
/*     */                 set.add(p);
/*     */               } 
/*     */               if (getValueOf(world, pos.f_140723_() - 1, pos.f_140724_()) == -1.0D) {
/*     */                 ColumnPos p = new ColumnPos(pos.f_140723_() - 1, pos.f_140724_());
/*     */                 set.add(p);
/*     */               } 
/*     */               if (getValueOf(world, pos.f_140723_(), pos.f_140724_() + 1) == -1.0D) {
/*     */                 ColumnPos p = new ColumnPos(pos.f_140723_(), pos.f_140724_() + 1);
/*     */                 set.add(p);
/*     */               } 
/*     */               if (getValueOf(world, pos.f_140723_(), pos.f_140724_() - 1) == -1.0D) {
/*     */                 ColumnPos p = new ColumnPos(pos.f_140723_(), pos.f_140724_() - 1);
/*     */                 set.add(p);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 282 */     return new ArrayList<>(set);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<ColumnPos> makeDirectional(List<ColumnPos> poly) {
/* 289 */     List<ColumnPos> list = new ArrayList<>();
/* 290 */     list.add(poly.remove(0));
/* 291 */     int a = 0;
/* 292 */     while (poly.size() > 0) {
/* 293 */       ColumnPos col = list.get(a);
/*     */       
/* 295 */       if (moveNext(col, poly, list)) {
/* 296 */         a++; continue;
/*     */       } 
/* 298 */       ImmersivePetroleum.log.warn("This should not happen, but it did..");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 303 */     return list;
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
/*     */   private static ArrayList<ColumnPos> cullLines(List<ColumnPos> poly) {
/* 316 */     ArrayList<ColumnPos> list = new ArrayList<>(poly);
/*     */     
/* 318 */     int endIndex = 0;
/* 319 */     ColumnPos startPos = null, endPos = null;
/* 320 */     for (int startIndex = 0; startIndex < list.size(); startIndex++) {
/* 321 */       startPos = list.get(startIndex);
/*     */       
/*     */       int j;
/*     */       
/* 325 */       for (j = 1; j < 64; j++) {
/* 326 */         int index = (startIndex + j) % list.size();
/* 327 */         ColumnPos pos = list.get(index);
/*     */         
/* 329 */         if (startPos.f_140724_() != pos.f_140724_()) {
/*     */           break;
/*     */         }
/*     */         
/* 333 */         endIndex = index;
/* 334 */         endPos = pos;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       for (j = 1; j < 64; j++) {
/* 341 */         int index = (startIndex + j) % list.size();
/* 342 */         ColumnPos pos = list.get(index);
/*     */         
/* 344 */         if (startPos.f_140723_() != pos.f_140723_()) {
/*     */           break;
/*     */         }
/*     */         
/* 348 */         endIndex = index;
/* 349 */         endPos = pos;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 355 */       boolean debug = false;
/* 356 */       if (debug) {
/* 357 */         for (int i = 1; i < 64; i++) {
/* 358 */           int index = (startIndex + i) % list.size();
/* 359 */           ColumnPos pos = list.get(index);
/*     */           
/* 361 */           int dx = Math.abs(pos.f_140723_() - startPos.f_140723_());
/* 362 */           int dz = Math.abs(pos.f_140724_() - startPos.f_140724_());
/*     */           
/* 364 */           if (dx != dz) {
/*     */             break;
/*     */           }
/*     */           
/* 368 */           endIndex = index;
/* 369 */           endPos = pos;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 374 */       if (startPos != null && endPos != null) {
/* 375 */         int len = endIndex - startIndex;
/* 376 */         if (len > 1) {
/* 377 */           int index = startIndex + 1;
/* 378 */           for (int i = index; i < endIndex; i++) {
/* 379 */             list.remove(index % list.size());
/*     */           }
/* 381 */         } else if (len < 0) {
/*     */           
/* 383 */           len = len + list.size() - 1;
/*     */           
/* 385 */           if (len > 1) {
/* 386 */             int index = startIndex + 1;
/* 387 */             for (int i = 0; i < len; i++) {
/* 388 */               list.remove(index % list.size());
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 393 */         startPos = endPos = null;
/*     */       } 
/*     */     } 
/*     */     
/* 397 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean moveNext(ColumnPos pos, List<ColumnPos> src, List<ColumnPos> dst) {
/* 402 */     ColumnPos p0 = new ColumnPos(pos.f_140723_() + 1, pos.f_140724_());
/* 403 */     ColumnPos p1 = new ColumnPos(pos.f_140723_() - 1, pos.f_140724_());
/* 404 */     ColumnPos p2 = new ColumnPos(pos.f_140723_(), pos.f_140724_() + 1);
/* 405 */     ColumnPos p3 = new ColumnPos(pos.f_140723_(), pos.f_140724_() - 1);
/*     */     
/* 407 */     if ((src.remove(p0) && dst.add(p0)) || (src.remove(p1) && dst.add(p1)) || (src.remove(p2) && dst.add(p2)) || (src.remove(p3) && dst.add(p3))) {
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 412 */     ColumnPos p4 = new ColumnPos(pos.f_140723_() - 1, pos.f_140724_() - 1);
/* 413 */     ColumnPos p5 = new ColumnPos(pos.f_140723_() - 1, pos.f_140724_() + 1);
/* 414 */     ColumnPos p6 = new ColumnPos(pos.f_140723_() + 1, pos.f_140724_() - 1);
/* 415 */     ColumnPos p7 = new ColumnPos(pos.f_140723_() + 1, pos.f_140724_() + 1);
/*     */     
/* 417 */     if ((src.remove(p4) && dst.add(p4)) || (src.remove(p5) && dst.add(p5)) || (src.remove(p6) && dst.add(p6)) || (src.remove(p7) && dst.add(p7))) {
/* 418 */       return true;
/*     */     }
/*     */     
/* 421 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\reservoir\ReservoirHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */