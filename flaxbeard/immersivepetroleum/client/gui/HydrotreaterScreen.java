/*    */ package flaxbeard.immersivepetroleum.client.gui;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.gui.IEContainerScreen;
/*    */ import blusunrize.immersiveengineering.client.gui.info.EnergyInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.FluidInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.gui.HydrotreaterContainer;
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
/*    */ public class HydrotreaterScreen extends IEContainerScreen<HydrotreaterContainer> {
/* 22 */   static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/hydrotreater.png");
/*    */   HydrotreaterTileEntity tile;
/*    */   
/*    */   public HydrotreaterScreen(HydrotreaterContainer inventorySlotsIn, Inventory inv, Component title) {
/* 26 */     super((AbstractContainerMenu)inventorySlotsIn, inv, title, GUI_TEXTURE);
/* 27 */     this.tile = (HydrotreaterTileEntity)((HydrotreaterContainer)this.f_97732_).getTile();
/*    */     
/* 29 */     this.f_97726_ = 140;
/* 30 */     this.f_97727_ = 69;
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
/* 41 */     return (List)List.of(new FluidInfoArea((IFluidTank)this.tile.tanks[0], new Rect2i(this.f_97735_ + 34, this.f_97736_ + 11, 16, 47), 140, 0, 20, 51, GUI_TEXTURE), new FluidInfoArea((IFluidTank)this.tile.tanks[1], new Rect2i(this.f_97735_ + 11, this.f_97736_ + 11, 16, 47), 140, 0, 20, 51, GUI_TEXTURE), new FluidInfoArea((IFluidTank)this.tile.tanks[2], new Rect2i(this.f_97735_ + 92, this.f_97736_ + 11, 16, 47), 140, 0, 20, 51, GUI_TEXTURE), new EnergyInfoArea(this.f_97735_ + 122, this.f_97736_ + 12, (IEnergyStorage)this.tile.energyStorage));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\HydrotreaterScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */