/*     */ package flaxbeard.immersivepetroleum.api.crafting;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LubricatedTileInfo
/*     */ {
/*     */   public BlockPos pos;
/*     */   public ResourceKey<Level> world;
/*  83 */   public Fluid lubricant = Fluids.f_76191_;
/*     */   public int ticks;
/*     */   
/*     */   public LubricatedTileInfo(ResourceKey<Level> registryKey, BlockPos pos, Fluid lubricant, int ticks) {
/*  87 */     this.world = registryKey;
/*  88 */     this.pos = pos;
/*  89 */     this.ticks = ticks;
/*     */     
/*  91 */     if (lubricant != null && lubricant != Fluids.f_76191_) {
/*  92 */       this.lubricant = lubricant;
/*     */     }
/*     */   }
/*     */   
/*     */   public LubricatedTileInfo(CompoundTag tag) {
/*  97 */     int ticks = tag.m_128451_("ticks");
/*  98 */     int x = tag.m_128451_("x");
/*  99 */     int y = tag.m_128451_("y");
/* 100 */     int z = tag.m_128451_("z");
/* 101 */     String name = tag.m_128461_("world");
/* 102 */     String lubricantName = tag.m_128461_("lubricant");
/*     */     
/* 104 */     this.world = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(name));
/* 105 */     this.pos = new BlockPos(x, y, z);
/* 106 */     this.ticks = ticks;
/*     */     
/* 108 */     this.lubricant = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(lubricantName));
/* 109 */     if (this.lubricant == null) {
/* 110 */       this.lubricant = Fluids.f_76191_;
/*     */     }
/*     */   }
/*     */   
/*     */   public CompoundTag writeToNBT() {
/* 115 */     CompoundTag tag = new CompoundTag();
/*     */     
/* 117 */     tag.m_128405_("ticks", this.ticks);
/* 118 */     tag.m_128405_("x", this.pos.m_123341_());
/* 119 */     tag.m_128405_("y", this.pos.m_123342_());
/* 120 */     tag.m_128405_("z", this.pos.m_123343_());
/* 121 */     tag.m_128359_("world", this.world.m_135782_().toString());
/* 122 */     tag.m_128359_("lubricant", ForgeRegistries.FLUIDS.getKey(this.lubricant).toString());
/*     */     
/* 124 */     return tag;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\LubricatedHandler$LubricatedTileInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */