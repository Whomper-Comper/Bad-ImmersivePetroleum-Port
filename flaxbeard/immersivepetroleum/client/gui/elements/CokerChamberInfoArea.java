/*    */ package flaxbeard.immersivepetroleum.client.gui.elements;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.ClientUtils;
/*    */ import blusunrize.immersiveengineering.client.gui.info.FluidInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.client.gui.CokerUnitScreen;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.Rect2i;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class CokerChamberInfoArea
/*    */   extends InfoArea {
/*    */   private final CokerUnitTileEntity.CokingChamber chamber;
/*    */   private final FluidInfoArea fluidDisplay;
/*    */   
/*    */   public CokerChamberInfoArea(CokerUnitTileEntity.CokingChamber chamber, Rect2i area) {
/* 20 */     super(area);
/* 21 */     this.chamber = chamber;
/* 22 */     this
/*    */       
/* 24 */       .fluidDisplay = new FluidInfoArea((IFluidTank)chamber.getTank(), new Rect2i(area.m_110085_(), area.m_110086_(), 6, 38), 0, 0, 0, 0, CokerUnitScreen.GUI_TEXTURE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void fillTooltipOverArea(int mouseX, int mouseY, List<Component> tooltip) {
/* 32 */     this.fluidDisplay.fillTooltipOverArea(mouseX, mouseY, tooltip);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(PoseStack transform) {
/* 37 */     ClientUtils.bindTexture(CokerUnitScreen.GUI_TEXTURE);
/* 38 */     int scale = 38;
/* 39 */     int off = (int)(this.chamber.getTotalAmount() / this.chamber.getCapacity() * scale);
/* 40 */     m_93228_(transform, this.area.m_110085_(), this.area.m_110086_() + scale - off, 200, 51, 6, off);
/*    */ 
/*    */     
/* 43 */     off = (int)((this.chamber.getTotalAmount() > 0) ? (scale * this.chamber.getOutputAmount() / this.chamber.getCapacity()) : 0.0F);
/* 44 */     m_93228_(transform, this.area.m_110085_(), this.area.m_110086_() + scale - off, 206, 51 + scale - off, 6, off);
/* 45 */     this.fluidDisplay.draw(transform);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\elements\CokerChamberInfoArea.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */