/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionData
/*     */   extends SavedData
/*     */ {
/*     */   final ReservoirRegionDataStorage.RegionPos regionPos;
/* 227 */   final Multimap<ResourceKey<Level>, ReservoirIsland> reservoirlist = (Multimap<ResourceKey<Level>, ReservoirIsland>)ArrayListMultimap.create();
/*     */   RegionData(ReservoirRegionDataStorage.RegionPos regionPos) {
/* 229 */     this.regionPos = regionPos;
/*     */   }
/*     */   RegionData(ReservoirRegionDataStorage.RegionPos regionPos, CompoundTag nbt) {
/* 232 */     this.regionPos = regionPos;
/* 233 */     load(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_77757_(File pFile) {
/* 238 */     if (!pFile.getParentFile().exists()) {
/* 239 */       pFile.getParentFile().mkdirs();
/*     */     }
/* 241 */     super.m_77757_(pFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag m_7176_(CompoundTag nbt) {
/* 246 */     ListTag reservoirs = new ListTag();
/* 247 */     synchronized (this.reservoirlist) {
/* 248 */       for (ResourceKey<Level> dimension : (Iterable<ResourceKey<Level>>)this.reservoirlist.keySet()) {
/* 249 */         CompoundTag dim = new CompoundTag();
/* 250 */         dim.m_128359_("dimension", dimension.m_135782_().toString());
/*     */         
/* 252 */         ListTag islands = new ListTag();
/* 253 */         for (ReservoirIsland island : this.reservoirlist.get(dimension)) {
/* 254 */           islands.add(island.writeToNBT());
/*     */         }
/* 256 */         dim.m_128365_("islands", (Tag)islands);
/*     */         
/* 258 */         reservoirs.add(dim);
/*     */       } 
/*     */     } 
/* 261 */     nbt.m_128365_("reservoirs", (Tag)reservoirs);
/*     */     
/* 263 */     ReservoirRegionDataStorage.log.debug("{} Saved.", this);
/* 264 */     return nbt;
/*     */   }
/*     */   
/*     */   private void load(CompoundTag nbt) {
/* 268 */     ListTag reservoirs = nbt.m_128437_("reservoirs", 10);
/* 269 */     if (!reservoirs.isEmpty()) {
/* 270 */       synchronized (this.reservoirlist) {
/* 271 */         for (int i = 0; i < reservoirs.size(); i++) {
/* 272 */           CompoundTag dim = reservoirs.m_128728_(i);
/* 273 */           ResourceLocation rl = new ResourceLocation(dim.m_128461_("dimension"));
/* 274 */           ResourceKey<Level> dimType = ResourceKey.m_135785_(Registry.f_122819_, rl);
/* 275 */           ListTag islands = dim.m_128437_("islands", 10);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 280 */           List<ReservoirIsland> list = (List<ReservoirIsland>)islands.stream().map(inbt -> ReservoirIsland.readFromNBT((CompoundTag)inbt)).filter(o -> (o != null)).collect(Collectors.toList());
/* 281 */           list.forEach(island -> island.setRegion(this));
/* 282 */           this.reservoirlist.putAll(dimType, list);
/*     */         } 
/*     */       } 
/* 285 */       ReservoirRegionDataStorage.log.debug("{} Loaded.", this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReservoirRegionDataStorage.RegionPos position() {
/* 291 */     return this.regionPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ReservoirIsland get(ResourceKey<Level> dimension, ColumnPos pos) {
/* 303 */     synchronized (this.reservoirlist) {
/* 304 */       for (ReservoirIsland island : this.reservoirlist.get(dimension)) {
/* 305 */         if (island.contains(pos))
/*     */         {
/* 307 */           return island;
/*     */         }
/*     */       } 
/* 310 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Multimap<ResourceKey<Level>, ReservoirIsland> getReservoirIslandList() {
/* 320 */     synchronized (this.reservoirlist) {
/* 321 */       return (Multimap<ResourceKey<Level>, ReservoirIsland>)ImmutableMultimap.copyOf(this.reservoirlist);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 327 */     return Objects.hash(new Object[] { this.regionPos });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 332 */     if (this == obj) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (!(obj instanceof RegionData)) {
/* 336 */       return false;
/*     */     }
/* 338 */     RegionData other = (RegionData)obj;
/* 339 */     return Objects.equals(this.regionPos, other.regionPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 344 */     return String.format("RegionData[%d, %d]", new Object[] { Integer.valueOf(this.regionPos.x()), Integer.valueOf(this.regionPos.z()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\ReservoirRegionDataStorage$RegionData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */