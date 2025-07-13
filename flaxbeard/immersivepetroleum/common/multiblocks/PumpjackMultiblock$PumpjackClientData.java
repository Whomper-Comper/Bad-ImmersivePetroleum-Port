/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.ApiUtils;
/*    */ import blusunrize.immersiveengineering.client.utils.RenderUtils;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Quaternion;
/*    */ import com.mojang.math.Vector3f;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.metal.PumpjackBlock;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.client.model.data.ModelData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PumpjackClientData
/*    */   extends IPClientMultiblockProperties
/*    */ {
/*    */   private PumpjackTileEntity te;
/*    */   private List<BakedQuad> list;
/*    */   final Quaternion rot;
/*    */   
/*    */   public PumpjackClientData() {
/* 50 */     super(PumpjackMultiblock.INSTANCE, 0.0D, 0.0D, 0.0D);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 97 */     this.rot = new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true);
/*    */   }
/*    */   
/*    */   protected boolean usingCustomRendering() {
/*    */     return true;
/*    */   }
/*    */   
/*    */   public boolean canRenderFormedStructure() {
/*    */     return true;
/*    */   }
/*    */   
/*    */   public void renderCustomFormedStructure(PoseStack matrix, MultiBufferSource buffer) {
/*    */     if (this.te == null)
/*    */       this.te = new PumpjackTileEntity(IPTileTypes.PUMP.master(), BlockPos.f_121853_, ((PumpjackBlock)IPContent.Multiblock.PUMPJACK.get()).m_49966_()); 
/*    */     if (this.list == null) {
/*    */       BlockState state = this.te.m_58900_();
/*    */       BakedModel model = MCUtil.getBlockRenderer().m_110910_(state);
/*    */       this.list = model.getQuads(state, null, ApiUtils.RANDOM_SOURCE, ModelData.EMPTY, null);
/*    */     } 
/*    */     if (this.list != null && this.list.size() > 0) {
/*    */       ClientLevel clientLevel = MCUtil.getLevel();
/*    */       if (clientLevel != null) {
/*    */         matrix.m_85836_();
/*    */         matrix.m_85837_(1.0D, 0.0D, 0.0D);
/*    */         RenderUtils.renderModelTESRFast(this.list, buffer.m_6299_(RenderType.m_110451_()), matrix, 15728880, OverlayTexture.f_118083_);
/*    */         matrix.m_85836_();
/*    */         matrix.m_85845_(this.rot);
/*    */         matrix.m_85837_(-2.0D, -1.0D, -1.0D);
/*    */         ImmersivePetroleum.proxy.renderTile((BlockEntity)this.te, buffer.m_6299_(RenderType.m_110451_()), matrix, buffer);
/*    */         matrix.m_85849_();
/*    */         matrix.m_85849_();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\PumpjackMultiblock$PumpjackClientData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */