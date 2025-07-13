/*    */ package flaxbeard.immersivepetroleum.client.gui;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.gui.IEContainerScreen;
/*    */ import blusunrize.immersiveengineering.client.gui.info.EnergyInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.FluidInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.MultitankArea;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.gui.DistillationTowerContainer;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.renderer.Rect2i;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraftforge.energy.IEnergyStorage;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class DistillationTowerScreen extends IEContainerScreen<DistillationTowerContainer> {
/* 23 */   static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/distillation.png");
/*    */   
/*    */   DistillationTowerTileEntity tile;
/*    */   
/*    */   public DistillationTowerScreen(DistillationTowerContainer container, Inventory playerInventory, Component title) {
/* 28 */     super((AbstractContainerMenu)container, playerInventory, title, GUI_TEXTURE);
/* 29 */     this.tile = (DistillationTowerTileEntity)container.getTile();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void m_7027_(PoseStack transform, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected List<InfoArea> makeInfoAreas() {
/* 40 */     return (List)List.of(new FluidInfoArea((IFluidTank)this.tile.tanks[0], new Rect2i(this.f_97735_ + 62, this.f_97736_ + 21, 16, 47), 177, 31, 20, 51, GUI_TEXTURE), new EnergyInfoArea(this.f_97735_ + 158, this.f_97736_ + 22, (IEnergyStorage)this.tile.energyStorage), new MultitankArea(new Rect2i(this.f_97735_ + 112, this.f_97736_ + 21, 16, 47), this.tile.tanks[1]
/*    */ 
/*    */           
/* 43 */           .getCapacity(), () -> (this.tile.tanks[1]).fluids));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\DistillationTowerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */