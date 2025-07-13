/*    */ package flaxbeard.immersivepetroleum.client.render;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Quaternion;
/*    */ import flaxbeard.immersivepetroleum.client.model.IPModel;
/*    */ import flaxbeard.immersivepetroleum.client.model.IPModels;
/*    */ import flaxbeard.immersivepetroleum.client.model.ModelPumpjack;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ public class MultiblockPumpjackRenderer
/*    */   implements BlockEntityRenderer<PumpjackTileEntity>
/*    */ {
/* 22 */   private static final Supplier<IPModel> pumpjackarm = IPModels.getSupplier("pumpjackarm");
/*    */ 
/*    */   
/*    */   public int m_142163_() {
/* 26 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(@Nonnull PumpjackTileEntity te, float partialTicks, @Nonnull PoseStack transform, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
/* 31 */     if (!te.isDummy()) {
/* 32 */       transform.m_85836_();
/* 33 */       Direction rotation = te.getFacing();
/* 34 */       switch (rotation) {
/*    */         case NORTH:
/* 36 */           transform.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 37 */           transform.m_85837_(-6.0D, 0.0D, -1.0D); break;
/*    */         case EAST:
/* 39 */           transform.m_85837_(-5.0D, 0.0D, -1.0D); break;
/*    */         case SOUTH:
/* 41 */           transform.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/* 42 */           transform.m_85837_(-5.0D, 0.0D, -2.0D);
/*    */           break;
/*    */         case WEST:
/* 45 */           transform.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/* 46 */           transform.m_85837_(-6.0D, 0.0D, -2.0D);
/*    */           break;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/*    */       ModelPumpjack model;
/* 53 */       if ((model = (ModelPumpjack)pumpjackarm.get()) != null) {
/* 54 */         float ticks = te.activeTicks + (te.wasActive ? partialTicks : 0.0F);
/* 55 */         model.ticks = 1.5F * ticks;
/*    */         
/* 57 */         model.m_7695_(transform, buffer.m_6299_(model.m_103119_(ModelPumpjack.TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */       } 
/* 59 */       transform.m_85849_();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\MultiblockPumpjackRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */