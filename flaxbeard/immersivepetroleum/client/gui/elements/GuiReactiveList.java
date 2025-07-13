/*     */ package flaxbeard.immersivepetroleum.client.gui.elements;
/*     */ 
/*     */ import blusunrize.immersiveengineering.client.ClientUtils;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiReactiveList
/*     */   extends Button
/*     */ {
/*     */   private final Screen gui;
/*     */   private String[] entries;
/*  31 */   private int[] padding = new int[] { 0, 0, 0, 0 };
/*     */   private boolean needsSlider = false;
/*     */   private int perPage;
/*     */   private Function<String, String> translationFunction;
/*  35 */   private int scrollMode = 0;
/*  36 */   private float textScale = 1.0F;
/*  37 */   private int textColor = 14737632;
/*  38 */   private int textHoverColor = -557004;
/*     */   
/*     */   private int offset;
/*     */   
/*     */   private int maxOffset;
/*  43 */   private long prevWheelNano = 0L;
/*  44 */   private int targetEntry = -1;
/*  45 */   private float hoverTimer = 0.0F;
/*     */   
/*     */   public GuiReactiveList(Screen gui, int x, int y, int w, int h, Button.OnPress handler, String... entries) {
/*  48 */     super(x, y, w, h, (Component)Component.m_237119_(), handler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     this.selectedOption = -1;
/*     */     this.gui = gui;
/*     */     this.entries = entries;
/*     */     recalculateEntries(); } public boolean m_6375_(double mx, double my, int key) {
/* 207 */     this.selectedOption = -1;
/* 208 */     if (this.f_93623_ && this.f_93624_ && 
/* 209 */       m_7972_(key) && m_93680_(mx, my)) {
/*     */       
/* 211 */       Font fr = MCUtil.getFont();
/* 212 */       double mmY = my - this.f_93621_;
/* 213 */       for (int i = 0; i < Math.min(this.perPage, this.entries.length); i++) {
/* 214 */         Objects.requireNonNull(fr); Objects.requireNonNull(fr); if (mmY >= (i * 9) && mmY < ((i + 1) * 9))
/* 215 */           this.selectedOption = this.offset + i; 
/*     */       } 
/* 217 */     }  super.m_6375_(mx, my, key);
/* 218 */     return (this.selectedOption != -1);
/*     */   }
/*     */   
/*     */   private void recalculateEntries() {
/*     */     Objects.requireNonNull(MCUtil.getFont());
/*     */     this.perPage = (int)((this.f_93619_ - this.padding[0] - this.padding[1]) / 9.0F * this.textScale);
/*     */     if (this.perPage < this.entries.length) {
/*     */       this.needsSlider = true;
/*     */       this.maxOffset = this.entries.length - this.perPage;
/*     */     } else {
/*     */       this.needsSlider = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GuiReactiveList setTextColor(int color) {
/*     */     this.textColor = color;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public GuiReactiveList setTextHoverColor(int color) {
/*     */     this.textHoverColor = color;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public GuiReactiveList setPadding(int up, int down, int left, int right) {
/*     */     this.padding[0] = up;
/*     */     this.padding[1] = down;
/*     */     this.padding[2] = left;
/*     */     this.padding[3] = right;
/*     */     recalculateEntries();
/*     */     return this;
/*     */   }
/*     */   
/*     */   public GuiReactiveList setTranslationFunc(Function<String, String> func) {
/*     */     this.translationFunction = func;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public GuiReactiveList setScrollMode(int mode) {
/*     */     this.scrollMode = mode;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public GuiReactiveList setFormatting(float textScale) {
/*     */     this.textScale = textScale;
/*     */     recalculateEntries();
/*     */     return this;
/*     */   }
/*     */   
/*     */   public int getOffset() {
/*     */     return this.offset;
/*     */   }
/*     */   
/*     */   public void setOffset(int offset) {
/*     */     this.offset = offset;
/*     */   }
/*     */   
/*     */   public int getMaxOffset() {
/*     */     return this.maxOffset;
/*     */   }
/*     */   
/*     */   static final ResourceLocation TEXTURE = ResourceUtils.ie("textures/gui/hud_elements.png");
/*     */   public int selectedOption;
/*     */   
/*     */   public void m_6305_(@Nonnull PoseStack transform, int mx, int my, float partialTicks) {
/*     */     Font fr = MCUtil.getFont();
/*     */     int mmY = my - this.f_93621_;
/*     */     int strWidth = this.f_93618_ - this.padding[2] - this.padding[3] - (this.needsSlider ? 6 : 0);
/*     */     if (this.needsSlider) {
/*     */       ClientUtils.bindTexture(TEXTURE);
/*     */       m_93228_(transform, this.f_93620_ + this.f_93618_ - 6, this.f_93621_, 16, 136, 6, 4);
/*     */       m_93228_(transform, this.f_93620_ + this.f_93618_ - 6, this.f_93621_ + this.f_93619_ - 4, 16, 144, 6, 4);
/*     */       for (int j = 0; j < this.f_93619_ - 8; j += 2)
/*     */         m_93228_(transform, this.f_93620_ + this.f_93618_ - 6, this.f_93621_ + 4 + j, 16, 141, 6, 2); 
/*     */       Objects.requireNonNull(fr);
/*     */       int sliderSize = Math.max(6, this.f_93619_ - this.maxOffset * 9);
/*     */       float silderShift = (this.f_93619_ - sliderSize) / this.maxOffset * this.offset;
/*     */       m_93228_(transform, this.f_93620_ + this.f_93618_ - 5, (int)(this.f_93621_ + silderShift + 1.0F), 20, 129, 4, 2);
/*     */       m_93228_(transform, this.f_93620_ + this.f_93618_ - 5, (int)(this.f_93621_ + silderShift + sliderSize - 4.0F), 20, 132, 4, 3);
/*     */       for (int k = 0; k < sliderSize - 7; k++)
/*     */         m_93228_(transform, this.f_93620_ + this.f_93618_ - 5, (int)(this.f_93621_ + silderShift + 3.0F + k), 20, 131, 4, 1); 
/*     */     } 
/*     */     transform.m_85841_(this.textScale, this.textScale, 1.0F);
/*     */     this.f_93622_ = (mx >= this.f_93620_ && mx < this.f_93620_ + this.f_93618_ && my >= this.f_93621_ && my < this.f_93621_ + this.f_93619_);
/*     */     boolean hasTarget = false;
/*     */     for (int i = 0; i < Math.min(this.perPage, this.entries.length); i++) {
/*     */       int j = this.offset + i;
/*     */       int col = this.textColor;
/*     */       Objects.requireNonNull(fr);
/*     */       Objects.requireNonNull(fr);
/*     */       boolean selectionHover = (this.f_93622_ && mmY >= i * 9 && mmY < (i + 1) * 9);
/*     */       if (selectionHover) {
/*     */         hasTarget = true;
/*     */         if (this.targetEntry != j) {
/*     */           this.targetEntry = j;
/*     */           this.hoverTimer = 0.0F;
/*     */         } else {
/*     */           this.hoverTimer = (float)(this.hoverTimer + 2.5D * partialTicks);
/*     */         } 
/*     */         col = this.textHoverColor;
/*     */       } 
/*     */       if (j > this.entries.length - 1)
/*     */         j = this.entries.length - 1; 
/*     */       String s = (this.translationFunction != null) ? this.translationFunction.apply(this.entries[j]) : this.entries[j];
/*     */       int overLength = s.length() - fr.m_92834_(s, strWidth).length();
/*     */       if (overLength > 0) {
/*     */         if (selectionHover && this.hoverTimer > 20.0F) {
/*     */           int textOffset = (int)this.hoverTimer / 10 % s.length();
/*     */           s = s.substring(textOffset) + " " + s.substring(textOffset);
/*     */         } 
/*     */         s = fr.m_92834_(s, strWidth);
/*     */       } 
/*     */       float tx = (this.f_93620_ + this.padding[2]) / this.textScale;
/*     */       Objects.requireNonNull(fr);
/*     */       float ty = (this.f_93621_ + this.padding[0] + 9 * i) / this.textScale;
/*     */       transform.m_85837_(tx, ty, 0.0D);
/*     */       fr.m_92883_(transform, s, 0.0F, 0.0F, col);
/*     */       transform.m_85837_(-tx, -ty, 0.0D);
/*     */     } 
/*     */     transform.m_85841_(1.0F / this.textScale, 1.0F / this.textScale, 1.0F);
/*     */     if (!hasTarget) {
/*     */       this.targetEntry = -1;
/*     */       this.hoverTimer = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean m_6050_(double mouseX, double mouseY, double delta) {
/*     */     if (delta != 0.0D && this.maxOffset > 0) {
/*     */       if (delta < 0.0D && this.offset < this.maxOffset)
/*     */         this.offset++; 
/*     */       if (delta > 0.0D && this.offset > 0)
/*     */         this.offset--; 
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\elements\GuiReactiveList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */