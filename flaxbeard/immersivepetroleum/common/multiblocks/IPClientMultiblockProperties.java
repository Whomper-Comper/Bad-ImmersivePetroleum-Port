/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
/*    */ import blusunrize.immersiveengineering.common.util.Utils;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ public class IPClientMultiblockProperties
/*    */   implements ClientMultiblocks.MultiblockManualData
/*    */ {
/*    */   private final IPTemplateMultiblock multiblock;
/*    */   @Nullable
/*    */   private NonNullList<ItemStack> materials;
/*    */   private final ItemStack renderStack;
/*    */   @Nullable
/*    */   private final Vec3 renderOffset;
/*    */   
/*    */   private IPClientMultiblockProperties(IPTemplateMultiblock multiblock, @Nullable Vec3 renderOffset) {
/* 31 */     this.multiblock = multiblock;
/* 32 */     this.renderStack = new ItemStack((ItemLike)multiblock.getBaseBlock());
/* 33 */     this.renderOffset = renderOffset;
/*    */   }
/*    */   
/*    */   public IPClientMultiblockProperties(IPTemplateMultiblock multiblock, double offX, double offY, double offZ) {
/* 37 */     this(multiblock, new Vec3(offX, offY, offZ));
/*    */   }
/*    */   
/*    */   public IPClientMultiblockProperties(IPTemplateMultiblock multiblock) {
/* 41 */     this(multiblock, null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean usingCustomRendering() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NonNullList<ItemStack> getTotalMaterials() {
/* 52 */     if (this.materials == null) {
/* 53 */       List<StructureTemplate.StructureBlockInfo> structure = this.multiblock.getStructure(null);
/* 54 */       this.materials = NonNullList.m_122779_();
/* 55 */       for (StructureTemplate.StructureBlockInfo info : structure) {
/* 56 */         ItemStack picked = Utils.getPickBlock(info.f_74676_);
/* 57 */         boolean added = false;
/* 58 */         for (ItemStack existing : this.materials) {
/* 59 */           if (ItemStack.m_41746_(existing, picked)) {
/* 60 */             existing.m_41769_(1);
/* 61 */             added = true; break;
/*    */           } 
/*    */         } 
/* 64 */         if (!added)
/* 65 */           this.materials.add(picked.m_41777_()); 
/*    */       } 
/*    */     } 
/* 68 */     return this.materials;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canRenderFormedStructure() {
/* 73 */     return (this.renderOffset != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderExtras(PoseStack matrix, MultiBufferSource buffer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderCustomFormedStructure(PoseStack matrix, MultiBufferSource buffer) {}
/*    */ 
/*    */   
/*    */   public final void renderFormedStructure(PoseStack matrix, MultiBufferSource buffer) {
/* 86 */     Objects.requireNonNull(this.renderOffset);
/*    */     
/* 88 */     if (usingCustomRendering()) {
/* 89 */       renderCustomFormedStructure(matrix, buffer);
/*    */       
/*    */       return;
/*    */     } 
/* 93 */     matrix.m_85837_(this.renderOffset.f_82479_, this.renderOffset.f_82480_, this.renderOffset.f_82481_);
/* 94 */     MCUtil.getItemRenderer().m_174269_(this.renderStack, ItemTransforms.TransformType.NONE, 15728880, OverlayTexture.f_118083_, matrix, buffer, 0);
/* 95 */     matrix.m_85836_();
/*    */     
/* 97 */     renderExtras(matrix, buffer);
/*    */     
/* 99 */     matrix.m_85849_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\IPClientMultiblockProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */