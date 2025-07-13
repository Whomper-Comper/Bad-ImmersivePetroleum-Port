/*     */ package flaxbeard.immersivepetroleum.client.gui;
/*     */ import blusunrize.immersiveengineering.client.gui.info.EnergyInfoArea;
/*     */ import blusunrize.immersiveengineering.client.gui.info.FluidInfoArea;
/*     */ import blusunrize.immersiveengineering.client.gui.info.InfoArea;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.gui.DerrickContainer;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
/*     */ import net.minecraft.client.renderer.Rect2i;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.energy.IEnergyStorage;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ 
/*     */ public class DerrickScreen extends AbstractContainerScreen<DerrickContainer> {
/*  34 */   static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/derrick.png");
/*     */   
/*     */   Button cfgButton;
/*     */   
/*     */   final DerrickTileEntity tile;
/*     */   private List<InfoArea> areas;
/*     */   
/*     */   public DerrickScreen(DerrickContainer inventorySlotsIn, Inventory inv, Component title) {
/*  42 */     super((AbstractContainerMenu)inventorySlotsIn, inv, title);
/*  43 */     this.tile = (DerrickTileEntity)((DerrickContainer)this.f_97732_).getTile();
/*     */ 
/*     */     
/*  46 */     this.f_97726_ = 200;
/*  47 */     this.f_97727_ = 164;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7856_() {
/*  52 */     this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
/*  53 */     this.f_97736_ = (this.f_96544_ - this.f_97727_) / 2;
/*     */     
/*  55 */     this.cfgButton = new Button(this.f_97735_ + 125, this.f_97736_ + 52, 50, 20, (Component)Component.m_237113_(I18n.m_118938_("gui.immersivepetroleum.derrick.msg.config", new Object[0])), button -> this.f_96541_.m_91152_(new DerrickSettingsScreen(this)), (button, matrix, mx, my) -> {
/*     */           if (!button.f_93623_) {
/*     */             m_169388_(matrix, List.of(Component.m_237113_(I18n.m_118938_("gui.immersivepetroleum.derrick.msg.set_in_stone", new Object[0]))), Optional.empty(), mx, my);
/*     */           }
/*     */         });
/*  60 */     m_142416_((GuiEventListener)this.cfgButton);
/*  61 */     this.areas = List.of(new FluidInfoArea((IFluidTank)this.tile.tank, new Rect2i(this.f_97735_ + 11, this.f_97736_ + 16, 16, 47), 200, 0, 20, 51, GUI_TEXTURE), new EnergyInfoArea(this.f_97735_ + 185, this.f_97736_ + 19, (IEnergyStorage)this.tile.energyStorage));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6305_(@Nonnull PoseStack matrix, int mx, int my, float partialTicks) {
/*  69 */     this.f_97731_ = this.f_97727_ - 40;
/*  70 */     m_7333_(matrix);
/*  71 */     super.m_6305_(matrix, mx, my, partialTicks);
/*  72 */     m_7025_(matrix, mx, my);
/*     */     
/*  74 */     List<Component> tooltip = new ArrayList<>();
/*     */     
/*  76 */     for (InfoArea area : this.areas) {
/*  77 */       area.fillTooltip(mx, my, tooltip);
/*     */     }
/*     */     
/*  80 */     if (!tooltip.isEmpty()) {
/*  81 */       m_169388_(matrix, tooltip, Optional.empty(), mx, my);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7027_(@Nonnull PoseStack matrix, int x, int y) {
/*  87 */     if (this.tile.m_58899_().m_123342_() <= 62) {
/*  88 */       drawInfoTextCenteredMultiLine(matrix, I18n.m_118938_("gui.immersivepetroleum.derrick.msg.water_table", new Object[0]), 15663104);
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     WellTileEntity well = this.tile.getWell();
/*  93 */     if (well != null) {
/*  94 */       if (this.cfgButton.f_93623_ && well.wellPipeLength > 0) {
/*  95 */         this.cfgButton.f_93623_ = false;
/*     */       }
/*     */ 
/*     */       
/*  99 */       if (well.wellPipeLength < well.getMaxPipeLength()) {
/* 100 */         if (this.tile.isRSDisabled()) {
/* 101 */           drawInfoTextCentered(matrix, (Component)Component.m_237115_("gui.immersivepetroleum.derrick.msg.disabled"), 0, 15663104);
/*     */           
/*     */           return;
/*     */         } 
/* 105 */         if (this.tile.drilling) {
/* 106 */           String str = String.format(Locale.ROOT, "(%d%%)", new Object[] { Integer.valueOf((int)((100 * well.wellPipeLength) / well.getMaxPipeLength())) });
/* 107 */           drawInfoText(matrix, (Component)Component.m_237110_("gui.immersivepetroleum.derrick.msg.drilling", new Object[] { str }), 0); return;
/*     */         } 
/* 109 */         if (well.pipes <= 0 && !((DerrickContainer)this.f_97732_).m_38853_(0).m_6657_()) {
/* 110 */           drawInfoTextCentered(matrix, (Component)Component.m_237115_("gui.immersivepetroleum.derrick.msg.out_of_pipes"), 3, 15663104);
/*     */           
/*     */           return;
/*     */         } 
/* 114 */         if (this.tile.tank.isEmpty()) {
/* 115 */           int realPipeLength = this.tile.m_58899_().m_123342_() - 1 - well.m_58899_().m_123342_();
/* 116 */           int concreteNeeded = 125 * (realPipeLength - well.wellPipeLength);
/* 117 */           if (concreteNeeded > 0) {
/* 118 */             drawInfoText(matrix, (Component)Component.m_237110_("gui.immersivepetroleum.derrick.msg.missing", new Object[] { Utils.fDecimal(concreteNeeded) + "mB" }), 0, 15663104);
/* 119 */             drawInfoText(matrix, ExternalModContent.getIEFluid_Concrete(1).getDisplayName(), 1, 15663104);
/*     */             
/*     */             return;
/*     */           } 
/* 123 */           int waterNeeded = 125 * (well.getMaxPipeLength() - well.wellPipeLength);
/* 124 */           if (waterNeeded > 0) {
/* 125 */             drawInfoText(matrix, (Component)Component.m_237110_("gui.immersivepetroleum.derrick.msg.missing", new Object[] { Utils.fDecimal(waterNeeded) + "mB" }), 0, 15663104);
/* 126 */             drawInfoText(matrix, (new FluidStack((Fluid)Fluids.f_76193_, 1)).getDisplayName(), 1, 15663104);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 131 */       } else if (this.tile.spilling) {
/* 132 */         drawInfoTextCenteredMultiLine(matrix, I18n.m_118938_("gui.immersivepetroleum.derrick.msg.safety_valve", new Object[0]), 15663104);
/*     */       } else {
/* 134 */         drawInfoTextCenteredMultiLine(matrix, I18n.m_118938_("gui.immersivepetroleum.derrick.msg.completed", new Object[0]), 16750848);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawInfoText(PoseStack matrix, Component text, int line) {
/* 141 */     drawInfoText(matrix, text, line, 16750848);
/*     */   }
/*     */   
/*     */   private void drawInfoText(PoseStack matrix, Component text, int line, int color) {
/* 145 */     this.f_96547_.m_92889_(matrix, text, 60.0F, (10 + 9 * line), color);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawInfoTextCentered(PoseStack matrix, Component text, int line) {
/* 150 */     drawInfoTextCentered(matrix, text, line, 16750848);
/*     */   }
/*     */   
/*     */   private void drawInfoTextCentered(PoseStack matrix, Component text, int line, int color) {
/* 154 */     int strWidth = this.f_96547_.m_92895_(text.getString());
/* 155 */     this.f_96547_.m_92889_(matrix, text, 118.5F - strWidth / 2.0F, (10 + 9 * line), color);
/*     */   }
/*     */   
/*     */   private void drawInfoTextCenteredMultiLine(PoseStack matrix, String text, int color) {
/* 159 */     String[] lines = text.split("<br>");
/* 160 */     for (int i = 0; i < Math.min(lines.length, 4); i++) {
/* 161 */       drawInfoTextCentered(matrix, (Component)Component.m_237113_((lines[i].length() > 25) ? lines[i].substring(0, 25) : lines[i]), i, color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7286_(@Nonnull PoseStack matrix, float partialTicks, int mx, int my) {
/* 167 */     MCUtil.bindTexture(GUI_TEXTURE);
/* 168 */     m_93228_(matrix, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
/* 169 */     for (InfoArea area : this.areas)
/* 170 */       area.draw(matrix); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\DerrickScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */