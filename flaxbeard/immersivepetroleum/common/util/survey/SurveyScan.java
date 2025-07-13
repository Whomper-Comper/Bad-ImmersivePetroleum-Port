/*     */ package flaxbeard.immersivepetroleum.common.util.survey;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ public class SurveyScan
/*     */   implements ISurveyInfo
/*     */ {
/*     */   public static final String TAG_KEY = "surveyscan";
/*     */   public static final int SCAN_RADIUS = 32;
/*     */   public static final int SCAN_SIZE = 65;
/*  24 */   private static final double sqrt2048 = Math.sqrt(2048.0D);
/*     */   @Nullable
/*     */   private UUID uuid;
/*     */   private int x;
/*     */   private int z;
/*     */   private byte[] data;
/*     */   
/*     */   public SurveyScan(CompoundTag tag) {
/*  32 */     this.uuid = tag.m_128403_("uuid") ? tag.m_128342_("uuid") : null;
/*  33 */     this.x = tag.m_128451_("x");
/*  34 */     this.z = tag.m_128451_("z");
/*  35 */     this.data = tag.m_128463_("map");
/*     */   }
/*     */   
/*     */   public SurveyScan(Level world, BlockPos pos) {
/*  39 */     this.uuid = UUID.randomUUID();
/*  40 */     this.x = pos.m_123341_();
/*  41 */     this.z = pos.m_123343_();
/*     */     
/*  43 */     this.data = scanArea(world, pos);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getUuid() {
/*  48 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  52 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  56 */     return this.z;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/*  60 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag writeToStack(ItemStack stack) {
/*  65 */     return writeToTag(stack.m_41698_("surveyscan"));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag writeToTag(CompoundTag tag) {
/*  70 */     tag.m_128362_("uuid", UUID.randomUUID());
/*  71 */     tag.m_128405_("x", this.x);
/*  72 */     tag.m_128405_("z", this.z);
/*  73 */     tag.m_128382_("map", this.data);
/*     */     
/*  75 */     return tag;
/*     */   }
/*     */   
/*     */   private byte[] scanArea(Level world, BlockPos pos) {
/*  79 */     List<ReservoirIsland> islandCache = new ArrayList<>();
/*  80 */     byte[] scanData = new byte[4225];
/*     */     
/*  82 */     for (int j = -32, a = 0; j <= 32; j++, a++) {
/*  83 */       for (int i = -32, b = 0; i <= 32; i++, b++) {
/*  84 */         int x = pos.m_123341_() - i;
/*  85 */         int z = pos.m_123343_() - j;
/*     */         
/*  87 */         int data = 0;
/*  88 */         double current = ReservoirHandler.getValueOf(world, x, z);
/*  89 */         if (current != -1.0D) {
/*     */ 
/*     */           
/*  92 */           Optional<ReservoirIsland> optional = islandCache.stream().filter(res -> res.contains(x, z)).findFirst();
/*     */           
/*  94 */           ReservoirIsland nearbyIsland = optional.isPresent() ? optional.get() : null;
/*  95 */           if (nearbyIsland == null) {
/*  96 */             nearbyIsland = ReservoirHandler.getIslandNoCache(world, new ColumnPos(x, z));
/*     */             
/*  98 */             if (nearbyIsland != null) {
/*  99 */               islandCache.add(nearbyIsland);
/*     */             }
/*     */           } 
/*     */           
/* 103 */           if (nearbyIsland != null) {
/* 104 */             data = (int)Mth.m_14008_(255.0D * current, 0.0D, 255.0D);
/*     */           }
/*     */         } 
/*     */         
/* 108 */         int noise = 31 + (int)(127.0D * Math.random());
/*     */         
/* 110 */         double blend = Math.sqrt((i * i + j * j)) / sqrt2048;
/* 111 */         int lerped = (int)Mth.m_14085_(data, noise, blend);
/* 112 */         scanData[a * 65 + b] = (byte)(lerped & 0xFF);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return normalizeScanData(scanData);
/*     */   }
/*     */   
/*     */   private byte[] normalizeScanData(byte[] scanData) {
/* 120 */     int max = Integer.MIN_VALUE; int i;
/* 121 */     for (i = 0; i < scanData.length; i++) {
/* 122 */       int data = scanData[i] & 0xFF;
/* 123 */       if (data > max) max = data; 
/*     */     } 
/* 125 */     for (i = 0; i < scanData.length; i++) {
/* 126 */       int data = scanData[i] & 0xFF;
/* 127 */       scanData[i] = (byte)(int)(255.0F * data / max);
/*     */     } 
/* 129 */     return scanData;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\survey\SurveyScan.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */