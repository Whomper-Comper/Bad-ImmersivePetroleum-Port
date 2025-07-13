/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Port
/*     */   implements StringRepresentable
/*     */ {
/*  78 */   TOP(new BlockPos(2, 2, 3)),
/*  79 */   BOTTOM(new BlockPos(2, 0, 3)),
/*  80 */   DYNAMIC_A(new BlockPos(0, 1, 2)),
/*  81 */   DYNAMIC_B(new BlockPos(4, 1, 2)),
/*  82 */   DYNAMIC_C(new BlockPos(0, 1, 4)),
/*  83 */   DYNAMIC_D(new BlockPos(4, 1, 4)); public static final Port[] DYNAMIC_PORTS;
/*     */   static {
/*  85 */     DYNAMIC_PORTS = new Port[] { DYNAMIC_A, DYNAMIC_B, DYNAMIC_C, DYNAMIC_D };
/*     */   }
/*     */   public final BlockPos posInMultiblock;
/*     */   Port(BlockPos posInMultiblock) {
/*  89 */     this.posInMultiblock = posInMultiblock;
/*     */   }
/*     */   
/*     */   public boolean matches(BlockPos posInMultiblock) {
/*  93 */     return posInMultiblock.equals(this.posInMultiblock);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String m_7912_() {
/*  99 */     return toString().toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   static Set<BlockPos> toSet(Port... ports) {
/* 103 */     ImmutableSet.Builder<BlockPos> builder = ImmutableSet.builder();
/* 104 */     for (Port port : ports) {
/* 105 */       builder.add(port.posInMultiblock);
/*     */     }
/* 107 */     return (Set<BlockPos>)builder.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\OilTankTileEntity$Port.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */