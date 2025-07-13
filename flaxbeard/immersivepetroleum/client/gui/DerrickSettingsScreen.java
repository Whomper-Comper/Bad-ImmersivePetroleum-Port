/*     */ package flaxbeard.immersivepetroleum.client.gui;
/*     */ 
/*     */ import blusunrize.immersiveengineering.client.ClientUtils;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import flaxbeard.immersivepetroleum.client.gui.elements.PipeConfig;
/*     */ import flaxbeard.immersivepetroleum.common.network.MessageDerrick;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiComponent;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ 
/*     */ public class DerrickSettingsScreen
/*     */   extends Screen {
/*  28 */   static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/derrick_settings.png");
/*     */   
/*  30 */   private final int xSize = 158;
/*  31 */   private final int ySize = 176;
/*     */   private int guiLeft;
/*     */   private int guiTop;
/*     */   private PipeConfig pipeConfig;
/*     */   final DerrickScreen derrickScreen;
/*     */   
/*     */   public DerrickSettingsScreen(DerrickScreen derrickScreen) {
/*  38 */     super((Component)Component.m_237113_("DerrickSettings"));
/*  39 */     this.derrickScreen = derrickScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7856_() {
/*  44 */     this.f_96543_ = this.f_96541_.m_91268_().m_85445_();
/*  45 */     this.f_96544_ = this.f_96541_.m_91268_().m_85446_();
/*     */     
/*  47 */     Objects.requireNonNull(this); this.guiLeft = (this.f_96543_ - 158) / 2;
/*  48 */     Objects.requireNonNull(this); this.guiTop = (this.f_96544_ - 176) / 2;
/*     */     
/*  50 */     this.pipeConfig = new PipeConfig(this.derrickScreen.tile, this.guiLeft + 10, this.guiTop + 10, 138, 138, 69, 69, 2);
/*  51 */     m_142416_((GuiEventListener)this.pipeConfig);
/*     */ 
/*     */     
/*  54 */     MutableComponent mutableComponent1 = Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.set");
/*  55 */     Objects.requireNonNull(this); Objects.requireNonNull(this); m_142416_((GuiEventListener)new Button(this.guiLeft + 158 / 2 - 65, this.guiTop + 176 - 25, 40, 20, (Component)mutableComponent1, b -> MessageDerrick.sendToServer(this.derrickScreen.tile.m_58899_(), this.pipeConfig.getGrid()), (button, matrix, mx, my) -> {
/*     */             List<Component> list = new ArrayList<>();
/*     */             
/*     */             list.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.set.desc"));
/*     */             
/*     */             m_169388_(matrix, list, Optional.empty(), mx, my);
/*     */           }));
/*     */     
/*  63 */     MutableComponent mutableComponent2 = Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.reset");
/*  64 */     Objects.requireNonNull(this); Objects.requireNonNull(this); m_142416_((GuiEventListener)new Button(this.guiLeft + 158 / 2 - 20, this.guiTop + 176 - 25, 40, 20, (Component)mutableComponent2, b -> this.pipeConfig.reset(this.derrickScreen.tile), (button, matrix, mx, my) -> {
/*     */             List<Component> list = new ArrayList<>();
/*     */             
/*     */             list.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.reset.desc"));
/*     */             
/*     */             m_169388_(matrix, list, Optional.empty(), mx, my);
/*     */           }));
/*     */     
/*  72 */     MutableComponent mutableComponent3 = Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.close");
/*  73 */     Objects.requireNonNull(this); Objects.requireNonNull(this); m_142416_((GuiEventListener)new Button(this.guiLeft + 158 / 2 + 25, this.guiTop + 176 - 25, 40, 20, (Component)mutableComponent3, b -> m_7379_(), (button, matrix, mx, my) -> {
/*     */             List<Component> list = new ArrayList<>();
/*     */             list.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.button.close.desc"));
/*     */             m_169388_(matrix, list, Optional.empty(), mx, my);
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6305_(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTick) {
/*  84 */     background(matrix, mouseX, mouseY, partialTick);
/*  85 */     super.m_6305_(matrix, mouseX, mouseY, partialTick);
/*     */     
/*  87 */     List<Component> tooltip = new ArrayList<>();
/*     */     
/*  89 */     if (mouseX >= this.pipeConfig.f_93620_ && mouseX < this.pipeConfig.f_93620_ + this.pipeConfig.m_5711_() && mouseY >= this.pipeConfig.f_93621_ && mouseY < this.pipeConfig.f_93621_ + this.pipeConfig.m_93694_()) {
/*  90 */       int x = (mouseX - this.pipeConfig.f_93620_) / this.pipeConfig.getGridScale();
/*  91 */       int y = (mouseY - this.pipeConfig.f_93621_) / this.pipeConfig.getGridScale();
/*     */       
/*  93 */       int px = x - this.pipeConfig.getGrid().getWidth() / 2;
/*  94 */       int py = y - this.pipeConfig.getGrid().getHeight() / 2;
/*     */       
/*  96 */       if (px >= -2 && px <= 2 && py >= -2 && py <= 2) {
/*  97 */         tooltip.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.derrickishere"));
/*     */       } else {
/*  99 */         MutableComponent d = Component.m_237119_();
/* 100 */         if (py < 0) {
/* 101 */           d.m_7220_((Component)Component.m_237115_("gui.immersivepetroleum.dirs.north"));
/* 102 */         } else if (py > 0) {
/* 103 */           d.m_7220_((Component)Component.m_237115_("gui.immersivepetroleum.dirs.south"));
/*     */         } 
/* 105 */         if (px != 0) {
/* 106 */           if (py != 0) {
/* 107 */             d.m_7220_((Component)Component.m_237113_("-"));
/*     */           }
/*     */           
/* 110 */           if (px < 0) {
/* 111 */             d.m_7220_((Component)Component.m_237115_("gui.immersivepetroleum.dirs.west"));
/* 112 */           } else if (px > 0) {
/* 113 */             d.m_7220_((Component)Component.m_237115_("gui.immersivepetroleum.dirs.east"));
/*     */           } 
/*     */         } 
/*     */         
/* 117 */         tooltip.add(d.m_130940_(ChatFormatting.UNDERLINE));
/*     */       } 
/*     */       
/* 120 */       ColumnPos tilePos = Utils.toColumnPos(this.derrickScreen.tile.m_58899_());
/* 121 */       tooltip.add(Component.m_237113_(String.format(Locale.ENGLISH, "X: %d Â§7(%d)", new Object[] { Integer.valueOf(tilePos.f_140723_() + px), Integer.valueOf(px) })));
/* 122 */       tooltip.add(Component.m_237113_(String.format(Locale.ENGLISH, "Z: %d Â§7(%d)", new Object[] { Integer.valueOf(tilePos.f_140724_() + py), Integer.valueOf(py) })));
/*     */       
/* 124 */       int i = this.pipeConfig.getGrid().get(x, y);
/* 125 */       if (i > 0) {
/* 126 */         switch (i) { case 1:
/* 127 */             tooltip.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.pipe.normal")); break;
/* 128 */           case 2: tooltip.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.pipe.perforated")); break;
/* 129 */           case 3: tooltip.add(Component.m_237115_("gui.immersivepetroleum.derrick.settings.pipe.perforated_fixed"));
/*     */             break; }
/*     */       
/*     */       }
/* 133 */       int xa = this.pipeConfig.f_93620_ + x * this.pipeConfig.getGridScale();
/* 134 */       int ya = this.pipeConfig.f_93621_ + y * this.pipeConfig.getGridScale();
/* 135 */       GuiComponent.m_93172_(matrix, xa, ya, xa + this.pipeConfig.getGridScale(), ya + this.pipeConfig.getGridScale(), 2147483647);
/*     */     } 
/*     */     
/* 138 */     if (!tooltip.isEmpty()) {
/* 139 */       m_96597_(matrix, tooltip, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean m_7043_() {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_7379_() {
/* 149 */     this.f_96541_.m_91152_((Screen)this.derrickScreen);
/* 150 */     this.pipeConfig.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6574_(@Nonnull Minecraft minecraft, int width, int height) {
/* 155 */     PipeConfig oldGrid = this.pipeConfig;
/* 156 */     super.m_6574_(minecraft, width, height);
/* 157 */     this.pipeConfig.copyDataFrom(oldGrid);
/* 158 */     oldGrid.dispose();
/*     */   }
/*     */   
/*     */   private void background(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 162 */     ClientUtils.bindTexture(GUI_TEXTURE);
/* 163 */     Objects.requireNonNull(this); Objects.requireNonNull(this); m_93228_(matrix, this.guiLeft, this.guiTop, 0, 0, 158, 176);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\DerrickSettingsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */