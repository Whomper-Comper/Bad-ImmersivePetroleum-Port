/*     */ package flaxbeard.immersivepetroleum.api.reservoir;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*     */ import blusunrize.immersiveengineering.api.crafting.IESerializableRecipe;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.common.util.Lazy;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ public class ReservoirType
/*     */   extends IESerializableRecipe
/*     */ {
/*  31 */   static final Lazy<ItemStack> EMPTY_LAZY = Lazy.of(() -> ItemStack.f_41583_);
/*     */   
/*  33 */   public static Map<ResourceLocation, ReservoirType> map = new HashMap<>();
/*     */   
/*     */   public final String name;
/*     */   
/*     */   public final ResourceLocation fluidLocation;
/*     */   
/*     */   public final int weight;
/*     */   
/*     */   public final int minSize;
/*     */   public final int maxSize;
/*     */   public final int residual;
/*     */   public final int equilibrium;
/*     */   private final Fluid fluid;
/*  46 */   private BWList biomes = new BWList(false);
/*  47 */   private BWList dimensions = new BWList(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirType(String name, ResourceLocation id, ResourceLocation fluidLocation, int minSize, int maxSize, int residual, int equilibrium, int weight) {
/*  62 */     this(name, id, (Fluid)ForgeRegistries.FLUIDS.getValue(fluidLocation), minSize, maxSize, residual, equilibrium, weight);
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
/*     */   public ReservoirType(String name, ResourceLocation id, Fluid fluid, int minSize, int maxSize, int residual, int equilibrium, int weight) {
/*  78 */     super(EMPTY_LAZY, IPRecipeTypes.RESERVOIR, id);
/*  79 */     this.name = name;
/*  80 */     this.fluidLocation = RegistryUtils.getRegistryNameOf(fluid);
/*  81 */     this.fluid = fluid;
/*  82 */     this.residual = residual;
/*  83 */     this.equilibrium = equilibrium;
/*  84 */     this.minSize = minSize;
/*  85 */     this.maxSize = maxSize;
/*  86 */     this.weight = weight;
/*     */   }
/*     */   
/*     */   public ReservoirType(CompoundTag nbt) {
/*  90 */     super(EMPTY_LAZY, IPRecipeTypes.RESERVOIR, new ResourceLocation(nbt.m_128461_("id")));
/*     */     
/*  92 */     this.name = nbt.m_128461_("name");
/*     */     
/*  94 */     this.fluidLocation = new ResourceLocation(nbt.m_128461_("fluid"));
/*  95 */     this.fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(this.fluidLocation);
/*     */     
/*  97 */     this.minSize = nbt.m_128451_("minSize");
/*  98 */     this.maxSize = nbt.m_128451_("maxSize");
/*  99 */     this.residual = nbt.m_128451_("residual");
/* 100 */     this.equilibrium = nbt.m_128451_("equilibrium");
/*     */     
/* 102 */     this.biomes = new BWList(nbt.m_128469_("biomes"));
/* 103 */     this.dimensions = new BWList(nbt.m_128469_("dimensions"));
/*     */     
/* 105 */     this.weight = nbt.m_128451_("weight");
/*     */   }
/*     */ 
/*     */   
/*     */   protected IERecipeSerializer<ReservoirType> getIESerializer() {
/* 110 */     return (IERecipeSerializer<ReservoirType>)Serializers.RESERVOIR_SERIALIZER.get();
/*     */   }
/*     */   
/*     */   public CompoundTag writeToNBT() {
/* 114 */     return writeToNBT(new CompoundTag());
/*     */   }
/*     */   
/*     */   public CompoundTag writeToNBT(CompoundTag nbt) {
/* 118 */     nbt.m_128359_("name", this.name);
/* 119 */     nbt.m_128359_("id", this.id.toString());
/* 120 */     nbt.m_128359_("fluid", this.fluidLocation.toString());
/*     */     
/* 122 */     nbt.m_128405_("minSize", this.minSize);
/* 123 */     nbt.m_128405_("maxSize", this.maxSize);
/* 124 */     nbt.m_128405_("residual", this.residual);
/* 125 */     nbt.m_128405_("equilibrium", this.equilibrium);
/*     */     
/* 127 */     nbt.m_128365_("biomes", (Tag)this.biomes.toNbt());
/* 128 */     nbt.m_128365_("dimensions", (Tag)this.dimensions.toNbt());
/*     */     
/* 130 */     nbt.m_128405_("weight", this.weight);
/*     */     
/* 132 */     return nbt;
/*     */   }
/*     */   
/*     */   public void setBiomes(boolean blacklist, ResourceLocation... names) {
/* 136 */     setBiomes(blacklist, Arrays.asList(names));
/*     */   }
/*     */   
/*     */   public void setBiomes(boolean blacklist, List<ResourceLocation> names) {
/* 140 */     this.biomes = new BWList(new HashSet<>(names), blacklist);
/*     */   }
/*     */   
/*     */   public void setDimensions(boolean blacklist, ResourceLocation... names) {
/* 144 */     setDimensions(blacklist, Arrays.asList(names));
/*     */   }
/*     */   
/*     */   public void setDimensions(boolean blacklist, List<ResourceLocation> names) {
/* 148 */     this.dimensions = new BWList(new HashSet<>(names), blacklist);
/*     */   }
/*     */   
/*     */   public Set<ResourceLocation> getBiomeList() {
/* 152 */     return this.biomes.getSet();
/*     */   }
/*     */   
/*     */   public Set<ResourceLocation> getDimensionList() {
/* 156 */     return this.dimensions.getSet();
/*     */   }
/*     */   
/*     */   public BWList getDimensions() {
/* 160 */     return this.dimensions;
/*     */   }
/*     */   
/*     */   public BWList getBiomes() {
/* 164 */     return this.biomes;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack m_8043_() {
/* 170 */     return ItemStack.f_41583_;
/*     */   }
/*     */   
/*     */   public Fluid getFluid() {
/* 174 */     return this.fluid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 179 */     return writeToNBT().toString();
/*     */   }
/*     */   
/*     */   static Set<ResourceLocation> toSet(ListTag nbtList) {
/* 183 */     Set<ResourceLocation> set = new HashSet<>();
/* 184 */     if (nbtList.size() > 0) {
/* 185 */       nbtList.forEach(tag -> {
/*     */             if (tag instanceof StringTag) {
/*     */               set.add(new ResourceLocation(tag.m_7916_()));
/*     */             }
/*     */           });
/*     */     }
/* 191 */     return set;
/*     */   }
/*     */   
/*     */   static ListTag toNbt(Set<ResourceLocation> set) {
/* 195 */     ListTag nbtList = new ListTag();
/* 196 */     if (set.size() > 0) {
/* 197 */       set.forEach(rl -> nbtList.add(StringTag.m_129297_(rl.toString())));
/*     */     }
/* 199 */     return nbtList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BWList
/*     */   {
/*     */     private Set<ResourceLocation> set;
/*     */     
/*     */     private boolean isBlacklist;
/*     */ 
/*     */     
/*     */     public BWList(boolean isBlacklist) {
/* 211 */       this(new HashSet<>(), isBlacklist);
/*     */     }
/*     */     
/*     */     public BWList(Set<ResourceLocation> set, boolean isBlacklist) {
/* 215 */       this.set = set;
/* 216 */       this.isBlacklist = isBlacklist;
/*     */     }
/*     */     
/*     */     public BWList(CompoundTag tag) {
/* 220 */       this.isBlacklist = tag.m_128471_("isBlacklist");
/*     */       
/* 222 */       if (tag.m_128425_("list", 9)) {
/* 223 */         ListTag list = tag.m_128437_("list", 8);
/*     */         
/* 225 */         Set<ResourceLocation> set = new HashSet<>();
/* 226 */         if (list.size() > 0) {
/* 227 */           list.forEach(t -> {
/*     */                 if (t instanceof StringTag) {
/*     */                   set.add(new ResourceLocation(t.m_7916_()));
/*     */                 }
/*     */               });
/*     */         }
/* 233 */         this.set = set;
/*     */       } else {
/* 235 */         this.set = new HashSet<>();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isBlacklist() {
/* 240 */       return this.isBlacklist;
/*     */     }
/*     */     
/*     */     public boolean add(ResourceLocation rl) {
/* 244 */       return this.set.add(rl);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends ResourceLocation> c) {
/* 248 */       return this.set.addAll(c);
/*     */     }
/*     */     
/*     */     public boolean hasEntries() {
/* 252 */       return (this.set.size() > 0);
/*     */     }
/*     */     
/*     */     public boolean valid(ResourceLocation rl) {
/* 256 */       if (this.set.isEmpty())
/*     */       {
/* 258 */         return true;
/*     */       }
/*     */       
/* 261 */       boolean contains = this.set.contains(rl);
/* 262 */       return this.isBlacklist ? (!contains) : contains;
/*     */     }
/*     */     
/*     */     public Set<ResourceLocation> getSet() {
/* 266 */       return Collections.unmodifiableSet(this.set);
/*     */     }
/*     */     
/*     */     public void forEach(Consumer<ResourceLocation> action) {
/* 270 */       this.set.forEach(action);
/*     */     }
/*     */     
/*     */     public CompoundTag toNbt() {
/* 274 */       CompoundTag tag = new CompoundTag();
/* 275 */       tag.m_128379_("isBlacklist", this.isBlacklist);
/* 276 */       tag.m_128365_("list", (Tag)toNbtList());
/* 277 */       return tag;
/*     */     }
/*     */     
/*     */     private ListTag toNbtList() {
/* 281 */       ListTag nbtList = new ListTag();
/* 282 */       if (this.set.size() > 0) {
/* 283 */         this.set.forEach(rl -> nbtList.add(StringTag.m_129297_(rl.toString())));
/*     */       }
/* 285 */       return nbtList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\reservoir\ReservoirType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */