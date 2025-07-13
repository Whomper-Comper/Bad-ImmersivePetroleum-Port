/*    */ package flaxbeard.immersivepetroleum.common.fluids;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ import net.minecraft.world.level.material.MaterialColor;
/*    */ 
/*    */ public class CrudeOilFluid
/*    */   extends IPFluid {
/* 11 */   public static final Material MATERIAL = createMaterial(MaterialColor.f_76365_);
/*    */   
/*    */   public CrudeOilFluid(IPFluid.IPFluidEntry entry) {
/* 14 */     super(entry);
/*    */   }
/*    */ 
/*    */   
/*    */   public int m_6718_(@Nonnull LevelReader p_205569_1_) {
/* 19 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCustomSlowdown() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getEntitySlowdown() {
/* 29 */     return 0.4D;
/*    */   }
/*    */   
/*    */   public static class CrudeOilBlock extends IPFluid.IPFluidBlock {
/*    */     public CrudeOilBlock(IPFluid.IPFluidEntry entry, BlockBehaviour.Properties props) {
/* 34 */       super(entry, BlockBehaviour.Properties.m_60939_(CrudeOilFluid.MATERIAL).m_60910_().m_60978_(100.0F));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\CrudeOilFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */