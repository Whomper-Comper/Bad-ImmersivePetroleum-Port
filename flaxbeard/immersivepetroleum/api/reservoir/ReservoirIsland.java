/*     */ package flaxbeard.immersivepetroleum.api.reservoir;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.ReservoirRegionDataStorage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReservoirIsland
/*     */ {
/*     */   public static final int MIN_MBPT = 15;
/*     */   public static final int MAX_MBPT = 2500;
/*     */   public static final long MAX_AMOUNT = 4294967295L;
/*     */   private ReservoirRegionDataStorage.RegionData regionData;
/*     */   @Nonnull
/*     */   private ReservoirType reservoir;
/*     */   @Nonnull
/*     */   private List<ColumnPos> poly;
/*     */   private AxisAlignedIslandBB islandAABB;
/*     */   private long amount;
/*     */   private long capacity;
/*     */   
/*     */   public ReservoirIsland(@Nonnull List<ColumnPos> poly, @Nonnull ReservoirType reservoir, long amount) {
/*  53 */     Objects.requireNonNull(poly);
/*  54 */     Objects.requireNonNull(reservoir);
/*     */     
/*  56 */     this.poly = poly;
/*  57 */     this.reservoir = reservoir;
/*  58 */     setAmountAndCapacity(amount, amount);
/*     */     
/*  60 */     createBoundingBox();
/*     */   }
/*     */   
/*     */   void createBoundingBox() {
/*  64 */     int minX = Integer.MAX_VALUE;
/*  65 */     int minZ = Integer.MAX_VALUE;
/*     */     
/*  67 */     int maxX = Integer.MIN_VALUE;
/*  68 */     int maxZ = Integer.MIN_VALUE;
/*     */     
/*  70 */     for (ColumnPos p : this.poly) {
/*  71 */       if (p.f_140723_() < minX) minX = p.f_140723_(); 
/*  72 */       if (p.f_140724_() < minZ) minZ = p.f_140724_();
/*     */       
/*  74 */       if (p.f_140723_() > maxX) maxX = p.f_140723_(); 
/*  75 */       if (p.f_140724_() > maxZ) maxZ = p.f_140724_();
/*     */     
/*     */     } 
/*  78 */     this.islandAABB = new AxisAlignedIslandBB(minX, minZ, maxX, maxZ);
/*     */   }
/*     */   
/*     */   public void setRegion(ReservoirRegionDataStorage.RegionData data) {
/*  82 */     if (this.regionData == null) {
/*  83 */       this.regionData = data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirIsland setAmountAndCapacity(long amount, long capacity) {
/*  92 */     setCapacity(capacity);
/*  93 */     setAmount(amount);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirIsland setAmount(long amount) {
/* 103 */     this.amount = clamp(amount, 0L, this.capacity);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirIsland setCapacity(long capacity) {
/* 113 */     this.capacity = clamp(capacity, 0L, 4294967295L);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public static long clamp(long num, long min, long max) {
/* 118 */     return Math.max(min, Math.min(max, num));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReservoirIsland setReservoirType(@Nonnull ReservoirType reservoir) {
/* 125 */     this.reservoir = Objects.<ReservoirType>requireNonNull(reservoir);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAmount() {
/* 133 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCapacity() {
/* 140 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 144 */     return (this.amount <= 0L);
/*     */   }
/*     */   
/*     */   public void setDirty() {
/* 148 */     if (this.regionData != null) {
/* 149 */       this.regionData.m_77762_();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ReservoirType getType() {
/* 155 */     return this.reservoir;
/*     */   }
/*     */   
/*     */   public Fluid getFluid() {
/* 159 */     return this.reservoir.getFluid();
/*     */   }
/*     */   
/*     */   public AxisAlignedIslandBB getBoundingBox() {
/* 163 */     return this.islandAABB;
/*     */   }
/*     */   
/*     */   public List<ColumnPos> getPolygon() {
/* 167 */     return Collections.unmodifiableList(this.poly);
/*     */   }
/*     */   
/* 170 */   private long lastEquilibriumTick = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean belowHydrostaticEquilibrium(@Nonnull Level level) {
/* 179 */     return (this.reservoir.residual > 0 && this.amount <= this.reservoir.equilibrium && this.lastEquilibriumTick != level.m_46467_());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void equalizeHydrostaticPressure(@Nonnull Level level) {
/* 188 */     if (this.amount <= this.reservoir.equilibrium && this.lastEquilibriumTick != level.m_46467_()) {
/* 189 */       this.lastEquilibriumTick = level.m_46467_();
/* 190 */       this.amount += this.reservoir.residual;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extract(int amount, IFluidHandler.FluidAction fluidAction) {
/* 202 */     if (isEmpty()) {
/* 203 */       return 0;
/*     */     }
/*     */     
/* 206 */     int extracted = (int)Math.min(amount, this.amount);
/*     */     
/* 208 */     if (fluidAction == IFluidHandler.FluidAction.EXECUTE) {
/* 209 */       this.amount -= extracted;
/* 210 */       setDirty();
/*     */     } 
/*     */     
/* 213 */     return extracted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractWithPressure(@Nonnull Level world, int x, int z) {
/* 222 */     float pressure = getPressure(world, x, z);
/*     */     
/* 224 */     if (pressure > 0.0D && this.amount > 0L) {
/* 225 */       int flow = (int)Math.min(getFlow(pressure), this.amount);
/*     */       
/* 227 */       this.amount -= flow;
/* 228 */       setDirty();
/* 229 */       return flow;
/*     */     } 
/*     */     
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlowFromPressure(@Nonnull Level level, BlockPos pos) {
/* 243 */     float pressure = getPressure(level, pos.m_123341_(), pos.m_123343_());
/* 244 */     return getFlow(pressure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFlow(float pressure) {
/* 252 */     return 15 + (int)Math.floor((2485.0F * Mth.m_14036_(pressure, 0.0F, 1.0F)));
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
/*     */   public float getPressure(@Nonnull Level level, int x, int z) {
/* 265 */     double noise = ReservoirHandler.getValueOf(level, x, z);
/*     */     
/* 267 */     if (noise > 0.0D) {
/*     */ 
/*     */ 
/*     */       
/* 271 */       double half = this.capacity * 0.5D;
/* 272 */       double alt = this.amount - half;
/* 273 */       if (alt > 0.0D) {
/* 274 */         double pre = alt / half;
/* 275 */         return (float)(pre * noise);
/*     */       } 
/*     */     } 
/*     */     
/* 279 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public CompoundTag writeToNBT() {
/* 283 */     CompoundTag nbt = new CompoundTag();
/* 284 */     nbt.m_128359_("reservoir", this.reservoir.m_6423_().toString());
/* 285 */     nbt.m_128405_("amount", (int)(getAmount() & 0xFFFFFFFFL));
/* 286 */     nbt.m_128405_("capacity", (int)(getCapacity() & 0xFFFFFFFFL));
/* 287 */     nbt.m_128365_("bounds", (Tag)getBoundingBox().writeToNBT());
/*     */     
/* 289 */     AxisAlignedIslandBB bounds = getBoundingBox();
/* 290 */     ListTag points = new ListTag();
/* 291 */     this.poly.forEach(pos -> {
/*     */           byte x = (byte)(pos.f_140723_() - bounds.minX & 0xFF);
/*     */           
/*     */           byte z = (byte)(pos.f_140724_() - bounds.minZ & 0xFF);
/*     */           
/*     */           CompoundTag point = new CompoundTag();
/*     */           point.m_128344_("x", x);
/*     */           point.m_128344_("z", z);
/*     */           points.add(point);
/*     */         });
/* 301 */     nbt.m_128365_("points", (Tag)points);
/*     */     
/* 303 */     return nbt;
/*     */   }
/*     */   
/*     */   public static ReservoirIsland readFromNBT(CompoundTag nbt) {
/*     */     try {
/* 308 */       ReservoirType reservoir = ReservoirType.map.get(new ResourceLocation(nbt.m_128461_("reservoir")));
/* 309 */       if (reservoir != null) {
/* 310 */         long amount = nbt.m_128451_("amount") & 0xFFFFFFFFL;
/* 311 */         long capacity = nbt.m_128451_("capacity") & 0xFFFFFFFFL;
/* 312 */         AxisAlignedIslandBB bounds = new AxisAlignedIslandBB(nbt.m_128469_("bounds"));
/*     */         
/* 314 */         List<ColumnPos> points = new ArrayList<>();
/* 315 */         ListTag list = nbt.m_128437_("points", 10);
/* 316 */         list.forEach(tag -> {
/*     */               CompoundTag point = (CompoundTag)tag;
/*     */               
/*     */               int x = bounds.minX + (point.m_128445_("x") & 0xFF);
/*     */               int z = bounds.minZ + (point.m_128445_("z") & 0xFF);
/*     */               points.add(new ColumnPos(x, z));
/*     */             });
/* 323 */         ReservoirIsland island = new ReservoirIsland();
/* 324 */         island.reservoir = reservoir;
/* 325 */         island.amount = amount;
/* 326 */         island.capacity = capacity;
/* 327 */         island.poly = points;
/* 328 */         island.islandAABB = bounds;
/* 329 */         return island;
/*     */       } 
/* 331 */     } catch (ResourceLocationException resourceLocationException) {}
/*     */ 
/*     */ 
/*     */     
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(ColumnPos pos) {
/* 344 */     return contains(pos.f_140723_(), pos.f_140724_());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int x, int z) {
/* 355 */     if (!this.islandAABB.contains(x, z)) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     return polygonContains(x, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean polygonContains(ColumnPos pos) {
/* 368 */     return polygonContains(pos.f_140723_(), pos.f_140724_());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean polygonContains(int x, int z) {
/*     */     int k;
/* 379 */     boolean ret = false;
/* 380 */     int j = this.poly.size() - 1;
/* 381 */     for (int i = 0; i < this.poly.size(); i++) {
/* 382 */       ColumnPos a = this.poly.get(i);
/* 383 */       ColumnPos b = this.poly.get(j);
/*     */ 
/*     */       
/* 386 */       float ax = a.f_140723_(), az = a.f_140724_();
/* 387 */       float bx = b.f_140723_(), bz = b.f_140724_();
/*     */ 
/*     */       
/* 390 */       if (ax == x && az == z)
/* 391 */         return false; 
/* 392 */       if (ax == x && bx == x && ((z > bz && z < az) || (z > az && z < bz)))
/* 393 */         return false; 
/* 394 */       if (az == z && bz == z && ((x > ax && x < bx) || (x > bx && x < ax))) {
/* 395 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 399 */       if (((az < z && bz >= z) || (bz < z && az >= z)) && (ax <= x || bx <= x)) {
/* 400 */         float f0 = ax + (z - az) / (bz - az) * (bx - ax);
/* 401 */         k = ret ^ ((f0 < x) ? 1 : 0);
/*     */       } 
/*     */       
/* 404 */       j = i;
/*     */     } 
/*     */     
/* 407 */     return k;
/*     */   }
/*     */   
/*     */   private ReservoirIsland() {}
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\reservoir\ReservoirIsland.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */