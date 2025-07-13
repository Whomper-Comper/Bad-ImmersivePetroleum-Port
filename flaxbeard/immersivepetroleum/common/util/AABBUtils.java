/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ public class AABBUtils
/*    */ {
/*  9 */   public static final AABB FULL = new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*    */ 
/*    */   
/*    */   public static void box16(List<AABB> list, double x0, double y0, double z0, double x1, double y1, double z1) {
/* 13 */     list.add(box16(x0, y0, z0, x1, y1, z1));
/*    */   }
/*    */ 
/*    */   
/*    */   public static AABB box16(double x0, double y0, double z0, double x1, double y1, double z1) {
/* 18 */     return new AABB(x0 / 16.0D, y0 / 16.0D, z0 / 16.0D, x1 / 16.0D, y1 / 16.0D, z1 / 16.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void box(List<AABB> list, double x0, double y0, double z0, double x1, double y1, double z1) {
/* 23 */     list.add(box(x0, y0, z0, x1, y1, z1));
/*    */   }
/*    */ 
/*    */   
/*    */   public static AABB box(double x0, double y0, double z0, double x1, double y1, double z1) {
/* 28 */     return new AABB(x0, y0, z0, x1, y1, z1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\AABBUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */