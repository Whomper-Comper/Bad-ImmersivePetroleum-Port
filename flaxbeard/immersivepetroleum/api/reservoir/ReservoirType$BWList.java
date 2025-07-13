/*     */ package flaxbeard.immersivepetroleum.api.reservoir;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ public class BWList
/*     */ {
/*     */   private Set<ResourceLocation> set;
/*     */   private boolean isBlacklist;
/*     */   
/*     */   public BWList(boolean isBlacklist) {
/* 211 */     this(new HashSet<>(), isBlacklist);
/*     */   }
/*     */   
/*     */   public BWList(Set<ResourceLocation> set, boolean isBlacklist) {
/* 215 */     this.set = set;
/* 216 */     this.isBlacklist = isBlacklist;
/*     */   }
/*     */   
/*     */   public BWList(CompoundTag tag) {
/* 220 */     this.isBlacklist = tag.m_128471_("isBlacklist");
/*     */     
/* 222 */     if (tag.m_128425_("list", 9)) {
/* 223 */       ListTag list = tag.m_128437_("list", 8);
/*     */       
/* 225 */       Set<ResourceLocation> set = new HashSet<>();
/* 226 */       if (list.size() > 0) {
/* 227 */         list.forEach(t -> {
/*     */               if (t instanceof StringTag) {
/*     */                 set.add(new ResourceLocation(t.m_7916_()));
/*     */               }
/*     */             });
/*     */       }
/* 233 */       this.set = set;
/*     */     } else {
/* 235 */       this.set = new HashSet<>();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBlacklist() {
/* 240 */     return this.isBlacklist;
/*     */   }
/*     */   
/*     */   public boolean add(ResourceLocation rl) {
/* 244 */     return this.set.add(rl);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends ResourceLocation> c) {
/* 248 */     return this.set.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean hasEntries() {
/* 252 */     return (this.set.size() > 0);
/*     */   }
/*     */   
/*     */   public boolean valid(ResourceLocation rl) {
/* 256 */     if (this.set.isEmpty())
/*     */     {
/* 258 */       return true;
/*     */     }
/*     */     
/* 261 */     boolean contains = this.set.contains(rl);
/* 262 */     return this.isBlacklist ? (!contains) : contains;
/*     */   }
/*     */   
/*     */   public Set<ResourceLocation> getSet() {
/* 266 */     return Collections.unmodifiableSet(this.set);
/*     */   }
/*     */   
/*     */   public void forEach(Consumer<ResourceLocation> action) {
/* 270 */     this.set.forEach(action);
/*     */   }
/*     */   
/*     */   public CompoundTag toNbt() {
/* 274 */     CompoundTag tag = new CompoundTag();
/* 275 */     tag.m_128379_("isBlacklist", this.isBlacklist);
/* 276 */     tag.m_128365_("list", (Tag)toNbtList());
/* 277 */     return tag;
/*     */   }
/*     */   
/*     */   private ListTag toNbtList() {
/* 281 */     ListTag nbtList = new ListTag();
/* 282 */     if (this.set.size() > 0) {
/* 283 */       this.set.forEach(rl -> nbtList.add(StringTag.m_129297_(rl.toString())));
/*     */     }
/* 285 */     return nbtList;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\reservoir\ReservoirType$BWList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */