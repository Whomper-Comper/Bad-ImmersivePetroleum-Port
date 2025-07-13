/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReservoirRegionDataStorage
/*     */   extends SavedData
/*     */ {
/*  40 */   private static final Logger log = LogManager.getLogger("immersivepetroleum/RegionDataStorage");
/*     */   
/*     */   private static final String DATA_NAME = "ImmersivePetroleum-ReservoirRegions";
/*     */   private static ReservoirRegionDataStorage active_instance;
/*     */   
/*     */   public static ReservoirRegionDataStorage get() {
/*  46 */     return active_instance;
/*     */   }
/*     */   
/*     */   public static final void init(DimensionDataStorage dimData) {
/*  50 */     active_instance = (ReservoirRegionDataStorage)dimData.m_164861_(t -> new ReservoirRegionDataStorage(dimData, t), () -> { log.debug("Creating new ReservoirRegionDataStorage instance."); return new ReservoirRegionDataStorage(dimData); }"ImmersivePetroleum-ReservoirRegions");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   final Map<RegionPos, RegionData> regions = new HashMap<>(); final DimensionDataStorage dimData;
/*     */   
/*     */   public ReservoirRegionDataStorage(DimensionDataStorage dimData) {
/*  62 */     this.dimData = dimData;
/*     */   }
/*     */   public ReservoirRegionDataStorage(DimensionDataStorage dimData, CompoundTag nbt) {
/*  65 */     this.dimData = dimData;
/*  66 */     load(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag m_7176_(CompoundTag nbt) {
/*  71 */     ListTag list = new ListTag();
/*  72 */     this.regions.forEach((key, entry) -> {
/*     */           CompoundTag tag = new CompoundTag();
/*     */           tag.m_128405_("x", key.x());
/*     */           tag.m_128405_("z", key.z());
/*     */           list.add(tag);
/*     */         });
/*  78 */     nbt.m_128365_("regions", (Tag)list);
/*     */     
/*  80 */     log.debug("Saved regions file.");
/*  81 */     return nbt;
/*     */   }
/*     */   
/*     */   private void load(CompoundTag nbt) {
/*  85 */     ListTag regions = nbt.m_128437_("regions", 10);
/*  86 */     for (int i = 0; i < regions.size(); i++) {
/*  87 */       CompoundTag tag = regions.m_128728_(i);
/*  88 */       int x = tag.m_128451_("x");
/*  89 */       int z = tag.m_128451_("z");
/*     */       
/*  91 */       RegionPos rPos = new RegionPos(x, z);
/*  92 */       RegionData rData = getOrCreateRegionData(rPos);
/*  93 */       this.regions.put(rPos, rData);
/*     */     } 
/*     */     
/*  96 */     log.debug("Loaded regions file.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void markAllDirty() {
/* 101 */     m_77762_();
/* 102 */     this.regions.values().forEach(SavedData::m_77762_);
/*     */   }
/*     */   
/*     */   public void addIsland(ResourceKey<Level> dimensionKey, ReservoirIsland island) {
/* 106 */     RegionPos regionPos = new RegionPos(island.getBoundingBox().getCenter());
/*     */     
/* 108 */     RegionData regionData = getOrCreateRegionData(regionPos);
/* 109 */     synchronized (regionData.reservoirlist) {
/* 110 */       if (!regionData.reservoirlist.containsEntry(dimensionKey, island)) {
/* 111 */         regionData.reservoirlist.put(dimensionKey, island);
/* 112 */         island.setRegion(regionData);
/* 113 */         regionData.m_77762_();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ReservoirIsland getIsland(Level world, BlockPos pos) {
/* 121 */     return getIsland(world, Utils.toColumnPos(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ReservoirIsland getIsland(Level world, ColumnPos pos) {
/* 127 */     if (world.f_46443_) {
/* 128 */       return null;
/*     */     }
/*     */     
/* 131 */     ResourceKey<Level> dimKey = world.m_46472_();
/*     */     
/* 133 */     ReservoirIsland ret = getIsland(dimKey, pos, 0, 0);
/* 134 */     if ((ret = getIsland(dimKey, pos, 1, -1)) == null && (
/* 135 */       ret = getIsland(dimKey, pos, 1, 1)) == null && (
/* 136 */       ret = getIsland(dimKey, pos, -1, -1)) == null) {
/* 137 */       ret = getIsland(dimKey, pos, -1, 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     return ret;
/*     */   }
/*     */   
/*     */   private ReservoirIsland getIsland(ResourceKey<Level> dimKey, ColumnPos pos, int regionXOff, int regionZOff) {
/* 146 */     RegionData regionData = getRegionData(new RegionPos(pos, regionXOff, regionZOff));
/* 147 */     return (regionData != null) ? regionData.get(dimKey, pos) : null;
/*     */   }
/*     */   
/*     */   public boolean existsAt(ColumnPos pos) {
/* 151 */     boolean ret = false;
/* 152 */     if (!(ret = existsAt(pos, 1, -1)) && 
/* 153 */       !(ret = existsAt(pos, 1, 1)) && 
/* 154 */       !(ret = existsAt(pos, -1, -1))) {
/* 155 */       ret = existsAt(pos, -1, 1);
/*     */     }
/*     */ 
/*     */     
/* 159 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean existsAt(ColumnPos pos, int regionXOff, int regionZOff) {
/* 163 */     RegionData regionData = getRegionData(new RegionPos(pos, regionXOff, regionZOff));
/* 164 */     if (regionData == null) {
/* 165 */       return false;
/*     */     }
/* 167 */     boolean ret = false;
/* 168 */     synchronized (regionData.reservoirlist) {
/* 169 */       ret = regionData.reservoirlist.values().stream().anyMatch(island -> island.contains(pos));
/*     */     } 
/* 171 */     return ret;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RegionData getRegionData(BlockPos pos) {
/* 176 */     return getRegionData(new RegionPos(pos));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RegionData getRegionData(RegionPos regionPos) {
/* 181 */     RegionData ret = this.regions.getOrDefault(regionPos, null);
/* 182 */     return ret;
/*     */   }
/*     */   
/*     */   private RegionData getOrCreateRegionData(RegionPos regionPos) {
/* 186 */     RegionData ret = this.regions.computeIfAbsent(regionPos, p -> {
/*     */           String fn = getRegionFileName(p);
/*     */           RegionData data = (RegionData)this.dimData.m_164861_((), (), fn);
/*     */           m_77762_();
/*     */           log.debug("Created RegionData[{}, {}]", Integer.valueOf(regionPos.x()), Integer.valueOf(regionPos.z()));
/*     */           return data;
/*     */         });
/* 193 */     return ret;
/*     */   }
/*     */   
/*     */   private String getRegionFileName(RegionPos regionPos) {
/* 197 */     return "ImmersivePetroleum-ReservoirRegions" + File.separatorChar + regionPos.x() + "_" + regionPos.z();
/*     */   } public static final class RegionPos extends Record { private final int x; private final int z; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #202	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #202	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/* 202 */     } public RegionPos(int x, int z) { this.x = x; this.z = z; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #202	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/* 202 */       //   0	8	1	o	Ljava/lang/Object; } public int x() { return this.x; } public int z() { return this.z; }
/*     */      public RegionPos(BlockPos pos, int xOff, int zOff) {
/* 204 */       this(pos.m_123341_() + 256 * xOff >> 9, pos.m_123343_() + 256 * zOff >> 9);
/*     */     }
/*     */     
/*     */     public RegionPos(ColumnPos pos, int xOff, int zOff) {
/* 208 */       this(pos.f_140723_() + 256 * xOff >> 9, pos.f_140724_() + 256 * zOff >> 9);
/*     */     }
/*     */     
/*     */     public RegionPos(BlockPos pos) {
/* 212 */       this(pos.m_123341_() >> 9, pos.m_123343_() >> 9);
/*     */     }
/*     */     
/*     */     public RegionPos(ColumnPos pos) {
/* 216 */       this(pos.f_140723_() >> 9, pos.f_140724_() >> 9);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RegionData
/*     */     extends SavedData
/*     */   {
/*     */     final ReservoirRegionDataStorage.RegionPos regionPos;
/*     */     
/* 227 */     final Multimap<ResourceKey<Level>, ReservoirIsland> reservoirlist = (Multimap<ResourceKey<Level>, ReservoirIsland>)ArrayListMultimap.create();
/*     */     RegionData(ReservoirRegionDataStorage.RegionPos regionPos) {
/* 229 */       this.regionPos = regionPos;
/*     */     }
/*     */     RegionData(ReservoirRegionDataStorage.RegionPos regionPos, CompoundTag nbt) {
/* 232 */       this.regionPos = regionPos;
/* 233 */       load(nbt);
/*     */     }
/*     */ 
/*     */     
/*     */     public void m_77757_(File pFile) {
/* 238 */       if (!pFile.getParentFile().exists()) {
/* 239 */         pFile.getParentFile().mkdirs();
/*     */       }
/* 241 */       super.m_77757_(pFile);
/*     */     }
/*     */ 
/*     */     
/*     */     public CompoundTag m_7176_(CompoundTag nbt) {
/* 246 */       ListTag reservoirs = new ListTag();
/* 247 */       synchronized (this.reservoirlist) {
/* 248 */         for (ResourceKey<Level> dimension : (Iterable<ResourceKey<Level>>)this.reservoirlist.keySet()) {
/* 249 */           CompoundTag dim = new CompoundTag();
/* 250 */           dim.m_128359_("dimension", dimension.m_135782_().toString());
/*     */           
/* 252 */           ListTag islands = new ListTag();
/* 253 */           for (ReservoirIsland island : this.reservoirlist.get(dimension)) {
/* 254 */             islands.add(island.writeToNBT());
/*     */           }
/* 256 */           dim.m_128365_("islands", (Tag)islands);
/*     */           
/* 258 */           reservoirs.add(dim);
/*     */         } 
/*     */       } 
/* 261 */       nbt.m_128365_("reservoirs", (Tag)reservoirs);
/*     */       
/* 263 */       ReservoirRegionDataStorage.log.debug("{} Saved.", this);
/* 264 */       return nbt;
/*     */     }
/*     */     
/*     */     private void load(CompoundTag nbt) {
/* 268 */       ListTag reservoirs = nbt.m_128437_("reservoirs", 10);
/* 269 */       if (!reservoirs.isEmpty()) {
/* 270 */         synchronized (this.reservoirlist) {
/* 271 */           for (int i = 0; i < reservoirs.size(); i++) {
/* 272 */             CompoundTag dim = reservoirs.m_128728_(i);
/* 273 */             ResourceLocation rl = new ResourceLocation(dim.m_128461_("dimension"));
/* 274 */             ResourceKey<Level> dimType = ResourceKey.m_135785_(Registry.f_122819_, rl);
/* 275 */             ListTag islands = dim.m_128437_("islands", 10);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 280 */             List<ReservoirIsland> list = (List<ReservoirIsland>)islands.stream().map(inbt -> ReservoirIsland.readFromNBT((CompoundTag)inbt)).filter(o -> (o != null)).collect(Collectors.toList());
/* 281 */             list.forEach(island -> island.setRegion(this));
/* 282 */             this.reservoirlist.putAll(dimType, list);
/*     */           } 
/*     */         } 
/* 285 */         ReservoirRegionDataStorage.log.debug("{} Loaded.", this);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ReservoirRegionDataStorage.RegionPos position() {
/* 291 */       return this.regionPos;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public ReservoirIsland get(ResourceKey<Level> dimension, ColumnPos pos) {
/* 303 */       synchronized (this.reservoirlist) {
/* 304 */         for (ReservoirIsland island : this.reservoirlist.get(dimension)) {
/* 305 */           if (island.contains(pos))
/*     */           {
/* 307 */             return island;
/*     */           }
/*     */         } 
/* 310 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Multimap<ResourceKey<Level>, ReservoirIsland> getReservoirIslandList() {
/* 320 */       synchronized (this.reservoirlist) {
/* 321 */         return (Multimap<ResourceKey<Level>, ReservoirIsland>)ImmutableMultimap.copyOf(this.reservoirlist);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 327 */       return Objects.hash(new Object[] { this.regionPos });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 332 */       if (this == obj) {
/* 333 */         return true;
/*     */       }
/* 335 */       if (!(obj instanceof RegionData)) {
/* 336 */         return false;
/*     */       }
/* 338 */       RegionData other = (RegionData)obj;
/* 339 */       return Objects.equals(this.regionPos, other.regionPos);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 344 */       return String.format("RegionData[%d, %d]", new Object[] { Integer.valueOf(this.regionPos.x()), Integer.valueOf(this.regionPos.z()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\ReservoirRegionDataStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */