/*    */ package flaxbeard.immersivepetroleum.client.gui;
/*    */ 
/*    */ import blusunrize.immersiveengineering.client.gui.IEContainerScreen;
/*    */ import blusunrize.immersiveengineering.client.gui.info.FluidInfoArea;
/*    */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import flaxbeard.immersivepetroleum.client.gui.elements.CokerChamberInfoArea;
/*    */ import flaxbeard.immersivepetroleum.client.gui.elements.EnergyDisplay;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.gui.CokerUnitContainer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CokerUnitScreen
/*    */   extends IEContainerScreen<CokerUnitContainer>
/*    */ {
/* 28 */   public static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/coker.png");
/*    */   CokerUnitTileEntity tile;
/*    */   
/*    */   public CokerUnitScreen(CokerUnitContainer inventorySlotsIn, Inventory inv, Component title) {
/* 32 */     super((AbstractContainerMenu)inventorySlotsIn, inv, title, GUI_TEXTURE);
/* 33 */     this.tile = (CokerUnitTileEntity)((CokerUnitContainer)this.f_97732_).getTile();
/*    */     
/* 35 */     this.f_97726_ = 200;
/* 36 */     this.f_97727_ = 187;
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
/* 47 */     return (List)List.of(new FluidInfoArea((IFluidTank)this.tile.bufferTanks[0], new Rect2i(this.f_97735_ + 32, this.f_97736_ + 14, 16, 47), 202, 2, 16, 47, GUI_TEXTURE), new FluidInfoArea((IFluidTank)this.tile.bufferTanks[1], new Rect2i(this.f_97735_ + 152, this.f_97736_ + 14, 16, 47), 202, 2, 16, 47, GUI_TEXTURE), new EnergyDisplay(this.f_97735_ + 168, this.f_97736_ + 67, 7, 21, (IEnergyStorage)this.tile.energyStorage), new CokerChamberInfoArea(this.tile.chambers[0], new Rect2i(this.f_97735_ + 74, this.f_97736_ + 24, 6, 38)), new CokerChamberInfoArea(this.tile.chambers[1], new Rect2i(this.f_97735_ + 120, this.f_97736_ + 24, 6, 38)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\CokerUnitScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */