/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RegionPos
/*     */   extends Record
/*     */ {
/*     */   private final int x;
/*     */   private final int z;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #202	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #202	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #202	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/ReservoirRegionDataStorage$RegionPos;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public RegionPos(int x, int z) {
/* 202 */     this.x = x; this.z = z; } public int x() { return this.x; } public int z() { return this.z; }
/*     */    public RegionPos(BlockPos pos, int xOff, int zOff) {
/* 204 */     this(pos.m_123341_() + 256 * xOff >> 9, pos.m_123343_() + 256 * zOff >> 9);
/*     */   }
/*     */   
/*     */   public RegionPos(ColumnPos pos, int xOff, int zOff) {
/* 208 */     this(pos.f_140723_() + 256 * xOff >> 9, pos.f_140724_() + 256 * zOff >> 9);
/*     */   }
/*     */   
/*     */   public RegionPos(BlockPos pos) {
/* 212 */     this(pos.m_123341_() >> 9, pos.m_123343_() >> 9);
/*     */   }
/*     */   
/*     */   public RegionPos(ColumnPos pos) {
/* 216 */     this(pos.f_140723_() >> 9, pos.f_140724_() >> 9);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\ReservoirRegionDataStorage$RegionPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */