/*     */ package flaxbeard.immersivepetroleum.client.gui;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Matrix4f;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.client.render.dyn.DynamicTextureWrapper;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.network.MessageSurveyResultDetails;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ public class SeismicSurveyScreen
/*     */   extends Screen
/*     */ {
/*  33 */   private static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/seismicsurvey_gui.png");
/*  34 */   private static final ResourceLocation OVERLAY_TEXTURE = ResourceUtils.ip("textures/gui/seismicsurvey_overlay.png");
/*     */   
/*     */   private static final int X_SIZE = 154;
/*     */   
/*     */   private static final int Y_SIZE = 154;
/*     */   private int guiLeft;
/*     */   private int guiTop;
/*     */   private int surveyLeft;
/*     */   private int surveyTop;
/*     */   private int surveyRight;
/*     */   private int surveyBottom;
/*  45 */   private int gridScale = 2;
/*     */   
/*     */   private float hoverSquareScale;
/*     */   boolean requestSent = false;
/*     */   private BitSet bitSet;
/*     */   @Nonnull
/*     */   public final SurveyScan scan;
/*     */   
/*     */   public SeismicSurveyScreen(Level level, @Nonnull SurveyScan scan) {
/*  54 */     super((Component)Component.m_237113_("seismicsurveyscreen"));
/*  55 */     this.scan = Objects.<SurveyScan>requireNonNull(scan);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7856_() {
/*  60 */     this.f_96543_ = Minecraft.m_91087_().m_91268_().m_85445_();
/*  61 */     this.f_96544_ = Minecraft.m_91087_().m_91268_().m_85446_();
/*     */     
/*  63 */     this.hoverSquareScale = 0.5F - (float)(this.f_96541_.m_91268_().m_85449_() / 10.0D);
/*     */     
/*  65 */     this.guiLeft = (this.f_96543_ - 154) / this.gridScale;
/*  66 */     this.guiTop = (this.f_96544_ - 154) / this.gridScale;
/*     */     
/*  68 */     this.surveyLeft = this.guiLeft + 12;
/*  69 */     this.surveyTop = this.guiTop + 12;
/*  70 */     this.surveyRight = this.surveyLeft + 65 * this.gridScale;
/*  71 */     this.surveyBottom = this.surveyTop + 65 * this.gridScale;
/*     */     
/*  73 */     this.requestSent = true;
/*  74 */     MessageSurveyResultDetails.sendRequestToServer(this.scan);
/*     */   }
/*     */   
/*     */   public void setBitSet(BitSet bitSet) {
/*  78 */     this.bitSet = bitSet;
/*     */   }
/*     */   
/*     */   private int getScanData(int x, int y) {
/*  82 */     if (x < 0 || x >= 65 || y < 0 || y >= 65) {
/*  83 */       return -1;
/*     */     }
/*  85 */     int index = y * 65 + x;
/*  86 */     return this.scan.getData()[index] & 0xFF;
/*     */   }
/*     */   
/*     */   private boolean hasReservoirAt(int x, int y) {
/*  90 */     if (this.bitSet == null) {
/*  91 */       return false;
/*     */     }
/*  93 */     if (x < 0 || x >= 65 || y < 0 || y >= 65) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     int index = Mth.m_14045_(y * 65 + x, 0, 4225);
/*  98 */     return this.bitSet.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6305_(PoseStack matrix, int mouseX, int mouseY, float partialTick) {
/* 103 */     background(matrix, mouseX, mouseY, partialTick);
/* 104 */     super.m_6305_(matrix, mouseX, mouseY, partialTick);
/*     */     
/* 106 */     DynamicTextureWrapper wrapper = DynamicTextureWrapper.getOrCreate(65, 65, this.scan);
/* 107 */     if (wrapper == null) {
/*     */       return;
/*     */     }
/* 110 */     List<Component> tooltip = new ArrayList<>();
/*     */     
/* 112 */     int scanX = 64 - (mouseX - this.surveyLeft) / this.gridScale;
/* 113 */     int scanY = 64 - (mouseY - this.surveyTop) / this.gridScale;
/*     */     
/* 115 */     int scanXCentered = scanX - 32;
/* 116 */     int scanYCentered = scanY - 32;
/*     */     
/* 118 */     if (mouseX >= this.guiLeft + 70 && mouseX <= this.guiLeft + 83 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 10) {
/* 119 */       tooltip.add(Component.m_237115_("gui.immersivepetroleum.dirs.north").m_130940_(ChatFormatting.AQUA));
/*     */     }
/*     */     
/* 122 */     matrix.m_85836_();
/*     */     
/* 124 */     matrix.m_85837_(this.surveyLeft, this.surveyTop, 0.0D);
/*     */     
/* 126 */     renderScanTexture(matrix, wrapper);
/*     */     
/* 128 */     if (mouseX >= this.surveyLeft && mouseX < this.surveyRight && mouseY >= this.surveyTop && mouseY < this.surveyBottom) {
/*     */       int data;
/* 130 */       if ((data = getScanData(scanX, scanY)) != -1) {
/* 131 */         renderCursorBox(matrix, mouseX, mouseY, 0xFF000000 | ((data < 127) ? 16777215 : 0));
/*     */       }
/*     */       
/* 134 */       int worldX = this.scan.getX() - scanXCentered;
/* 135 */       int worldZ = this.scan.getZ() - scanYCentered;
/* 136 */       tooltip.add(Component.m_237110_("gui.immersivepetroleum.seismicsurvey.worldcoords", new Object[] { Integer.valueOf(worldX), Integer.valueOf(worldZ) }));
/*     */       
/* 138 */       if (scanXCentered == 0 && scanYCentered == 0) {
/* 139 */         tooltip.add(Component.m_237115_("gui.immersivepetroleum.seismicsurvey.takenhere").m_130940_(ChatFormatting.GRAY));
/*     */       }
/*     */       
/* 142 */       if (this.bitSet != null) {
/* 143 */         if (hasReservoirAt(scanX, scanY)) {
/* 144 */           tooltip.add(Component.m_237115_("gui.immersivepetroleum.seismicsurvey.possibility").m_130940_(ChatFormatting.DARK_GRAY));
/*     */         }
/*     */       }
/* 147 */       else if (this.requestSent) {
/* 148 */         tooltip.add(Component.m_237115_("gui.immersivepetroleum.seismicsurvey.awaitingresponse").m_130940_(ChatFormatting.GRAY));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 153 */     matrix.m_85849_();
/*     */     
/* 155 */     if (!tooltip.isEmpty())
/* 156 */       m_96597_(matrix, tooltip, mouseX, mouseY); 
/*     */   }
/*     */   
/*     */   private void renderCursorBox(PoseStack matrix, int mouseX, int mouseY, int color) {
/* 160 */     MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */     
/* 162 */     matrix.m_85836_();
/*     */     
/* 164 */     matrix.m_85841_(this.gridScale, this.gridScale, 1.0F);
/* 165 */     matrix.m_85837_(((mouseX - this.surveyLeft) / this.gridScale), ((mouseY - this.surveyTop) / this.gridScale), 0.0D);
/*     */     
/* 167 */     VertexConsumer builder = buffer.m_6299_(IPRenderTypes.TRANSLUCENT_POSITION_COLOR);
/* 168 */     Matrix4f mat = matrix.m_85850_().m_85861_();
/*     */     
/* 170 */     float s = this.hoverSquareScale;
/* 171 */     builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(color).m_5752_();
/* 172 */     builder.m_85982_(mat, 0.0F, s, 0.0F).m_193479_(color).m_5752_();
/* 173 */     builder.m_85982_(mat, 1.0F, s, 0.0F).m_193479_(color).m_5752_();
/* 174 */     builder.m_85982_(mat, 1.0F, 0.0F, 0.0F).m_193479_(color).m_5752_();
/*     */     
/* 176 */     builder.m_85982_(mat, 0.0F, 1.0F - s, 0.0F).m_193479_(color).m_5752_();
/* 177 */     builder.m_85982_(mat, 0.0F, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 178 */     builder.m_85982_(mat, 1.0F, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 179 */     builder.m_85982_(mat, 1.0F, 1.0F - s, 0.0F).m_193479_(color).m_5752_();
/*     */     
/* 181 */     builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(color).m_5752_();
/* 182 */     builder.m_85982_(mat, 0.0F, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 183 */     builder.m_85982_(mat, s, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 184 */     builder.m_85982_(mat, s, 0.0F, 0.0F).m_193479_(color).m_5752_();
/*     */     
/* 186 */     builder.m_85982_(mat, 1.0F - s, 0.0F, 0.0F).m_193479_(color).m_5752_();
/* 187 */     builder.m_85982_(mat, 1.0F - s, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 188 */     builder.m_85982_(mat, 1.0F, 1.0F, 0.0F).m_193479_(color).m_5752_();
/* 189 */     builder.m_85982_(mat, 1.0F, 0.0F, 0.0F).m_193479_(color).m_5752_();
/*     */     
/* 191 */     matrix.m_85849_();
/*     */     
/* 193 */     buffer.m_109911_();
/*     */   }
/*     */   
/*     */   private void renderScanTexture(PoseStack matrix, DynamicTextureWrapper wrapper) {
/* 197 */     MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/* 198 */     matrix.m_85836_();
/*     */     
/* 200 */     matrix.m_85841_(this.gridScale, this.gridScale, 1.0F);
/* 201 */     VertexConsumer builder = buffer.m_6299_(wrapper.renderType);
/* 202 */     Matrix4f mat = matrix.m_85850_().m_85861_();
/*     */     
/* 204 */     int a = wrapper.width;
/* 205 */     int b = wrapper.height;
/*     */     
/* 207 */     builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(-1).m_7421_(1.0F, 1.0F).m_85969_(15728880).m_5752_();
/* 208 */     builder.m_85982_(mat, 0.0F, b, 0.0F).m_193479_(-1).m_7421_(1.0F, 0.0F).m_85969_(15728880).m_5752_();
/* 209 */     builder.m_85982_(mat, a, b, 0.0F).m_193479_(-1).m_7421_(0.0F, 0.0F).m_85969_(15728880).m_5752_();
/* 210 */     builder.m_85982_(mat, a, 0.0F, 0.0F).m_193479_(-1).m_7421_(0.0F, 1.0F).m_85969_(15728880).m_5752_();
/*     */     
/* 212 */     builder = buffer.m_6299_(RenderType.m_110497_(OVERLAY_TEXTURE));
/* 213 */     builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_193479_(-1).m_7421_(0.0F, 0.0F).m_85969_(15728880).m_5752_();
/* 214 */     builder.m_85982_(mat, 0.0F, b, 0.0F).m_193479_(-1).m_7421_(0.0F, 1.0F).m_85969_(15728880).m_5752_();
/* 215 */     builder.m_85982_(mat, a, b, 0.0F).m_193479_(-1).m_7421_(1.0F, 1.0F).m_85969_(15728880).m_5752_();
/* 216 */     builder.m_85982_(mat, a, 0.0F, 0.0F).m_193479_(-1).m_7421_(1.0F, 0.0F).m_85969_(15728880).m_5752_();
/*     */     
/* 218 */     matrix.m_85849_();
/*     */     
/* 220 */     buffer.m_109911_();
/*     */   }
/*     */   
/*     */   private void background(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 224 */     MCUtil.bindTexture(GUI_TEXTURE);
/* 225 */     m_93228_(matrix, this.guiLeft, this.guiTop, 0, 0, 154, 154);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7933_(int pKeyCode, int pScanCode, int pModifiers) {
/* 230 */     InputConstants.Key key = InputConstants.m_84827_(pKeyCode, pScanCode);
/* 231 */     if (this.f_96541_.f_91066_.f_92092_.isActiveAndMatches(key)) {
/* 232 */       m_7379_();
/* 233 */       return true;
/*     */     } 
/* 235 */     return super.m_7933_(pKeyCode, pScanCode, pModifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7043_() {
/* 240 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\SeismicSurveyScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */