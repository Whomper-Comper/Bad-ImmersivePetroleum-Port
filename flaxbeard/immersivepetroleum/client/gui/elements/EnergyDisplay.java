/*    */ package flaxbeard.immersivepetroleum.client.gui.elements;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.Rect2i;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraftforge.energy.IEnergyStorage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyDisplay
/*    */   extends InfoArea
/*    */ {
/*    */   private final IEnergyStorage storage;
/*    */   
/*    */   public EnergyDisplay(int x, int y, int width, int height, IEnergyStorage storage) {
/* 21 */     super(new Rect2i(x, y, width, height));
/* 22 */     this.storage = storage;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fillTooltipOverArea(int mouseX, int mouseY, List<Component> tooltip) {
/* 27 */     tooltip.add(Component.m_237113_("" + this.storage.getEnergyStored() + "/" + this.storage.getEnergyStored() + " IF"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(PoseStack transform) {
/* 32 */     int height = this.area.m_110091_();
/* 33 */     int stored = (int)(height * this.storage.getEnergyStored() / this.storage.getMaxEnergyStored());
/* 34 */     m_93179_(transform, this.area
/* 35 */         .m_110085_(), this.area.m_110086_() + height - stored, this.area
/* 36 */         .m_110085_() + this.area.m_110090_(), this.area.m_110086_() + this.area.m_110091_(), -4909824, -10482944);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\elements\EnergyDisplay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */