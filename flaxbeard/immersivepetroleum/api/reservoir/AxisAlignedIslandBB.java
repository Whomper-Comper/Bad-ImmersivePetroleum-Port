/*    */ package flaxbeard.immersivepetroleum.api.reservoir;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AxisAlignedIslandBB
/*    */ {
/*    */   final int minX;
/*    */   final int minZ;
/*    */   final int maxX;
/*    */   final int maxZ;
/*    */   final BlockPos center;
/*    */   
/*    */   public AxisAlignedIslandBB(int minX, int minZ, int maxX, int maxZ) {
/* 19 */     this.minX = minX;
/* 20 */     this.minZ = minZ;
/* 21 */     this.maxX = maxX;
/* 22 */     this.maxZ = maxZ;
/*    */     
/* 24 */     this.center = new BlockPos((this.minX + this.maxX) / 2, 0, (this.minZ + this.maxZ) / 2);
/*    */   }
/*    */   
/*    */   public AxisAlignedIslandBB(CompoundTag nbt) {
/* 28 */     this(nbt
/* 29 */         .m_128451_("minX"), nbt
/* 30 */         .m_128451_("minZ"), nbt
/* 31 */         .m_128451_("maxX"), nbt
/* 32 */         .m_128451_("maxZ"));
/*    */   }
/*    */ 
/*    */   
/*    */   public int minX() {
/* 37 */     return this.minX;
/*    */   }
/*    */   
/*    */   public int maxX() {
/* 41 */     return this.maxX;
/*    */   }
/*    */   
/*    */   public int minZ() {
/* 45 */     return this.minZ;
/*    */   }
/*    */   
/*    */   public int maxZ() {
/* 49 */     return this.maxZ;
/*    */   }
/*    */   
/*    */   public BlockPos getCenter() {
/* 53 */     return this.center;
/*    */   }
/*    */   
/*    */   public boolean contains(BlockPos pos) {
/* 57 */     return contains(pos.m_123341_(), pos.m_123343_());
/*    */   }
/*    */   
/*    */   public boolean contains(int x, int z) {
/* 61 */     return (x >= this.minX && x <= this.maxX && z >= this.minZ && z <= this.maxZ);
/*    */   }
/*    */   
/*    */   public CompoundTag writeToNBT() {
/* 65 */     CompoundTag bounds = new CompoundTag();
/* 66 */     bounds.m_128405_("minX", this.minX);
/* 67 */     bounds.m_128405_("minZ", this.minZ);
/* 68 */     bounds.m_128405_("maxX", this.maxX);
/* 69 */     bounds.m_128405_("maxZ", this.maxZ);
/* 70 */     return bounds;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 75 */     return Objects.hash(new Object[] { Integer.valueOf(this.maxX), Integer.valueOf(this.maxZ), Integer.valueOf(this.minX), Integer.valueOf(this.minZ) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 80 */     if (this == obj) {
/* 81 */       return true;
/*    */     }
/* 83 */     if (!(obj instanceof AxisAlignedIslandBB)) {
/* 84 */       return false;
/*    */     }
/* 86 */     AxisAlignedIslandBB other = (AxisAlignedIslandBB)obj;
/* 87 */     return (this.maxX == other.maxX && this.maxZ == other.maxZ && this.minX == other.minX && this.minZ == other.minZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return String.format("IslandAxisAlignedBB [minX = %d, minZ = %d, maxX = %d, maxZ = %d]", new Object[] { Integer.valueOf(this.minX), Integer.valueOf(this.minZ), Integer.valueOf(this.maxX), Integer.valueOf(this.maxZ) });
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated(forRemoval = true)
/*    */   public static AxisAlignedIslandBB readFromNBT(CompoundTag nbt) {
/* 98 */     return new AxisAlignedIslandBB(nbt);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\reservoir\AxisAlignedIslandBB.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */