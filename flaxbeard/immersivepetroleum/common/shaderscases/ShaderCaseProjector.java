/*    */ package flaxbeard.immersivepetroleum.common.shaderscases;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.shader.ShaderCase;
/*    */ import blusunrize.immersiveengineering.api.shader.ShaderLayer;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ShaderCaseProjector
/*    */   extends ShaderCase {
/* 11 */   public static final ResourceLocation TYPE = ResourceUtils.ip("projector");
/*    */   
/*    */   public ShaderCaseProjector(ShaderLayer... layers) {
/* 14 */     super(layers);
/*    */   }
/*    */   
/*    */   public ShaderCaseProjector(Collection<ShaderLayer> layers) {
/* 18 */     super(layers);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLayerInsertionIndex() {
/* 23 */     return this.layers.length - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRenderGroupForPass(String modelPart, int pass) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getShaderType() {
/* 33 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\shaderscases\ShaderCaseProjector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */