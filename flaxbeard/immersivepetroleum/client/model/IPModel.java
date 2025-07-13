/*    */ package flaxbeard.immersivepetroleum.client.model;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.model.Model;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public abstract class IPModel
/*    */   extends Model {
/*    */   public IPModel(Function<ResourceLocation, RenderType> renderTypeIn) {
/* 12 */     super(renderTypeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void init();
/*    */ 
/*    */ 
/*    */   
/*    */   protected final CubeListBuilder singleCube(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
/* 22 */     return singleCube(0, 0, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final CubeListBuilder singleCube(int pXTexOffs, int pYTexOffs, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
/* 27 */     return CubeListBuilder.m_171558_().m_171514_(pXTexOffs, pYTexOffs).m_171481_(pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final CubeListBuilder empty() {
/* 32 */     return CubeListBuilder.m_171558_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\IPModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */