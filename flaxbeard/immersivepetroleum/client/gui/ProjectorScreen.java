/*     */ package flaxbeard.immersivepetroleum.client.gui;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import blusunrize.immersiveengineering.api.utils.TemplateWorldCreator;
/*     */ import blusunrize.immersiveengineering.client.ClientUtils;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.client.gui.elements.GuiReactiveList;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.projector.Settings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.components.AbstractButton;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Widget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraftforge.client.model.data.ModelData;
/*     */ import net.minecraftforge.common.util.Lazy;
/*     */ 
/*     */ public class ProjectorScreen
/*     */   extends Screen
/*     */ {
/*  52 */   static final ResourceLocation GUI_TEXTURE = ResourceUtils.ip("textures/gui/projector.png");
/*     */   
/*  54 */   static final Component GUI_CONFIRM = translation("gui.immersivepetroleum.projector.button.confirm");
/*  55 */   static final Component GUI_CANCEL = translation("gui.immersivepetroleum.projector.button.cancel");
/*  56 */   static final Component GUI_MIRROR = translation("gui.immersivepetroleum.projector.button.mirror");
/*  57 */   static final Component GUI_ROTATE_CW = translation("gui.immersivepetroleum.projector.button.rcw");
/*  58 */   static final Component GUI_ROTATE_CCW = translation("gui.immersivepetroleum.projector.button.rccw");
/*  59 */   static final Component GUI_UP = translation("gui.immersivepetroleum.projector.button.up");
/*  60 */   static final Component GUI_DOWN = translation("gui.immersivepetroleum.projector.button.down");
/*  61 */   static final Component GUI_SEARCH = translation("gui.immersivepetroleum.projector.search");
/*     */   
/*  63 */   private final int xSize = 256;
/*  64 */   private final int ySize = 166;
/*     */   
/*     */   private int guiLeft;
/*     */   
/*     */   private int guiTop;
/*     */   
/*     */   private Lazy<List<MultiblockHandler.IMultiblock>> multiblocks;
/*     */   
/*     */   private Level templateWorld;
/*     */   private MultiblockHandler.IMultiblock multiblock;
/*     */   private GuiReactiveList list;
/*     */   private String[] listEntries;
/*     */   private SearchField searchField;
/*     */   Settings settings;
/*     */   InteractionHand hand;
/*  79 */   float rotation = 0.0F, move = 0.0F;
/*     */   public ProjectorScreen(InteractionHand hand, ItemStack projector) {
/*  81 */     super((Component)Component.m_237113_("projector"));
/*  82 */     this.settings = new Settings(projector);
/*  83 */     this.hand = hand;
/*  84 */     this.multiblocks = Lazy.of(MultiblockHandler::getMultiblocks);
/*     */     
/*  86 */     if (this.settings.getMultiblock() != null) {
/*  87 */       this.move = 20.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7856_() {
/*  93 */     this.f_96543_ = Minecraft.m_91087_().m_91268_().m_85445_();
/*  94 */     this.f_96544_ = Minecraft.m_91087_().m_91268_().m_85446_();
/*     */     
/*  96 */     Objects.requireNonNull(this); this.guiLeft = (this.f_96543_ - 256) / 2;
/*  97 */     Objects.requireNonNull(this); this.guiTop = (this.f_96544_ - 166) / 2;
/*     */     
/*  99 */     this.searchField = (SearchField)m_142416_((GuiEventListener)new SearchField(this.f_96547_, this.guiLeft + 25, this.guiTop + 13));
/*     */     
/* 101 */     m_142416_((GuiEventListener)new ConfirmButton(this.guiLeft + 115, this.guiTop + 10, but -> {
/*     */             this.settings.setMode(Settings.Mode.PROJECTION);
/*     */             
/*     */             ItemStack held = MCUtil.getPlayer().m_21120_(this.hand);
/*     */             
/*     */             this.settings.applyTo(held);
/*     */             this.settings.sendPacketToServer(this.hand);
/*     */             MCUtil.getScreen().m_7379_();
/*     */             MCUtil.getPlayer().m_5661_(this.settings.getMode().getTranslated(), true);
/*     */           }));
/* 111 */     m_142416_((GuiEventListener)new CancelButton(this.guiLeft + 115, this.guiTop + 34, but -> MCUtil.getScreen().m_7379_()));
/*     */ 
/*     */     
/* 114 */     m_142416_((GuiEventListener)new MirrorButton(this.guiLeft + 115, this.guiTop + 58, this.settings, but -> this.settings.flip()));
/*     */ 
/*     */     
/* 117 */     m_142416_((GuiEventListener)new RotateLeftButton(this.guiLeft + 115, this.guiTop + 106, but -> this.settings.rotateCCW()));
/*     */ 
/*     */     
/* 120 */     m_142416_((GuiEventListener)new RotateRightButton(this.guiLeft + 115, this.guiTop + 130, but -> this.settings.rotateCW()));
/*     */ 
/*     */ 
/*     */     
/* 124 */     updatelist();
/*     */   }
/*     */   
/*     */   private void listaction(Button button) {
/* 128 */     GuiReactiveList l = (GuiReactiveList)button;
/* 129 */     if (l.selectedOption >= 0 && l.selectedOption < this.listEntries.length) {
/* 130 */       String str = this.listEntries[l.selectedOption];
/* 131 */       MultiblockHandler.IMultiblock mb = ((List<MultiblockHandler.IMultiblock>)this.multiblocks.get()).get(Integer.parseInt(str));
/* 132 */       this.settings.setMultiblock(mb);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatelist() {
/* 137 */     boolean exists = this.f_169369_.contains(this.list);
/*     */     
/* 139 */     List<String> list = new ArrayList<>();
/* 140 */     for (int i = 0; i < ((List)this.multiblocks.get()).size(); i++) {
/* 141 */       MultiblockHandler.IMultiblock mb = ((List<MultiblockHandler.IMultiblock>)this.multiblocks.get()).get(i);
/* 142 */       if (!mb.getUniqueName().toString().equals("immersiveengineering:feedthrough")) {
/* 143 */         list.add(Integer.toString(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 148 */     list.sort((a, b) -> {
/*     */           String nameA = getMBName(a);
/*     */           
/*     */           String nameB = getMBName(b);
/*     */           
/*     */           return nameA.compareToIgnoreCase(nameB);
/*     */         });
/*     */     
/* 156 */     list.removeIf(str -> {
/*     */           String name = getMBName(str);
/*     */           
/*     */           return !name.toLowerCase().contains(this.searchField.m_94155_().toLowerCase());
/*     */         });
/* 161 */     this.listEntries = list.<String>toArray(new String[0]);
/* 162 */     GuiReactiveList guilist = new GuiReactiveList(this, this.guiLeft + 15, this.guiTop + 29, 89, 127, this::listaction, this.listEntries);
/* 163 */     guilist.setPadding(1, 1, 1, 1);
/* 164 */     guilist.setTextColor(0);
/* 165 */     guilist.setTextHoverColor(8355839);
/* 166 */     guilist.setTranslationFunc(this::getMBName);
/*     */     
/* 168 */     if (!exists) {
/* 169 */       this.list = (GuiReactiveList)m_142416_((GuiEventListener)guilist);
/*     */       
/*     */       return;
/*     */     } 
/* 173 */     m_169411_((GuiEventListener)this.list);
/* 174 */     this.list = guilist;
/* 175 */     m_142416_((GuiEventListener)this.list);
/*     */   }
/*     */   
/*     */   private String getMBName(String str) {
/* 179 */     return getMBName(Integer.parseInt(str));
/*     */   }
/*     */   
/*     */   private String getMBName(int index) {
/* 183 */     MultiblockHandler.IMultiblock mb = ((List<MultiblockHandler.IMultiblock>)this.multiblocks.get()).get(index);
/* 184 */     return mb.getDisplayName().getString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6305_(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 190 */     if (this.settings.getMultiblock() != null) {
/* 191 */       MultiblockHandler.IMultiblock mb = this.settings.getMultiblock();
/* 192 */       int x = this.guiLeft + 28;
/* 193 */       int y = this.guiTop - (int)(15.0F * this.move / 20.0F);
/*     */       
/* 195 */       if (this.move < 20.0F) {
/* 196 */         this.move = (float)(this.move + 1.5D * partialTicks);
/*     */       }
/*     */       
/* 199 */       ClientUtils.bindTexture(GUI_TEXTURE);
/* 200 */       m_93228_(matrix, x, y, 0, 166, 200, 13);
/*     */       
/* 202 */       x += 100;
/* 203 */       y += 3;
/*     */       
/* 205 */       Component text = mb.getDisplayName();
/* 206 */       FormattedCharSequence re = text.m_7532_();
/* 207 */       this.f_96547_.m_92877_(matrix, re, (x - this.f_96547_.m_92724_(re) / 2), y, 4144959);
/*     */     } 
/* 209 */     background(matrix, mouseX, mouseY, partialTicks);
/* 210 */     super.m_6305_(matrix, mouseX, mouseY, partialTicks);
/* 211 */     this.searchField.m_6305_(matrix, mouseX, mouseY, partialTicks);
/*     */     
/* 213 */     for (Widget rawWidget : this.f_169369_) {
/* 214 */       if (rawWidget instanceof AbstractWidget) { AbstractWidget widget = (AbstractWidget)rawWidget; if (widget.m_198029_()) {
/* 215 */           widget.m_7428_(matrix, mouseX, mouseY);
/*     */           break;
/*     */         }  }
/*     */     
/*     */     } 
/* 220 */     renderDirectionDisplay(matrix, mouseX, mouseY);
/*     */     
/* 222 */     if (this.settings.getMultiblock() != null) {
/* 223 */       MultiblockHandler.IMultiblock mb = this.settings.getMultiblock();
/*     */       
/* 225 */       MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */       
/*     */       try {
/* 228 */         this.rotation += 1.5F * partialTicks;
/*     */         
/* 230 */         Vec3i size = mb.getSize(null);
/* 231 */         matrix.m_85836_();
/*     */         
/* 233 */         matrix.m_85837_((this.guiLeft + 190), (this.guiTop + 80), 64.0D);
/* 234 */         matrix.m_85841_(mb.getManualScale(), -mb.getManualScale(), 1.0F);
/* 235 */         matrix.m_85845_(new Quaternion(25.0F, 0.0F, 0.0F, true));
/* 236 */         matrix.m_85845_(new Quaternion(0.0F, (int)(45.0F - this.rotation), 0.0F, true));
/* 237 */         matrix.m_85837_((size.m_123341_() / -2.0F), (size.m_123342_() / -2.0F), (size.m_123343_() / -2.0F));
/*     */         
/* 239 */         ClientMultiblocks.MultiblockManualData mbClientData = ClientMultiblocks.get(mb);
/* 240 */         boolean tempDisable = true;
/* 241 */         if (tempDisable && mbClientData.canRenderFormedStructure()) {
/* 242 */           matrix.m_85836_();
/*     */           
/* 244 */           mbClientData.renderFormedStructure(matrix, IPRenderTypes.disableLighting((MultiBufferSource)buffer));
/*     */           
/* 246 */           matrix.m_85849_();
/*     */         } else {
/* 248 */           if (this.templateWorld == null || !this.multiblock.getUniqueName().equals(mb.getUniqueName())) {
/* 249 */             this.templateWorld = ((TemplateWorldCreator)TemplateWorldCreator.CREATOR.getValue()).makeWorld(mb.getStructure(null), pos -> true);
/* 250 */             this.multiblock = mb;
/*     */           } 
/*     */           
/* 253 */           BlockRenderDispatcher blockRender = Minecraft.m_91087_().m_91289_();
/* 254 */           List<StructureTemplate.StructureBlockInfo> infos = mb.getStructure(null);
/* 255 */           for (StructureTemplate.StructureBlockInfo info : infos) {
/* 256 */             if (info.f_74676_.m_60767_() != Material.f_76296_) {
/* 257 */               matrix.m_85836_();
/*     */               
/* 259 */               matrix.m_85837_(info.f_74675_.m_123341_(), info.f_74675_.m_123342_(), info.f_74675_.m_123343_());
/* 260 */               ModelData modelData = ModelData.EMPTY;
/* 261 */               BlockEntity te = this.templateWorld.m_7702_(info.f_74675_);
/* 262 */               if (te != null) {
/* 263 */                 modelData = te.getModelData();
/*     */               }
/* 265 */               blockRender.renderSingleBlock(info.f_74676_, matrix, IPRenderTypes.disableLighting((MultiBufferSource)buffer), 15728880, OverlayTexture.f_118083_, modelData, null);
/*     */               
/* 267 */               matrix.m_85849_();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 272 */         matrix.m_85849_();
/* 273 */       } catch (Exception e) {
/* 274 */         e.printStackTrace();
/*     */       } 
/* 276 */       buffer.m_109911_();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderDirectionDisplay(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
/* 281 */     int x = this.guiLeft + 115;
/* 282 */     int y = this.guiTop + 82;
/*     */ 
/*     */     
/* 285 */     Direction dir = Direction.m_122407_(this.settings.getRotation().ordinal());
/* 286 */     MutableComponent mutableComponent = Component.m_237113_(dir.toString().toUpperCase().substring(0, 1));
/* 287 */     m_93215_(matrix, this.f_96547_, (Component)mutableComponent, x + 5, y + 1, -1);
/*     */     
/* 289 */     if (mouseX > x && mouseX < x + 10 && mouseY > y && mouseY < y + 10) {
/* 290 */       MutableComponent mutableComponent1 = Component.m_237115_("desc.immersivepetroleum.info.projector.rotated." + dir);
/* 291 */       m_96602_(matrix, (Component)mutableComponent1, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void background(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 296 */     ClientUtils.bindTexture(GUI_TEXTURE);
/* 297 */     Objects.requireNonNull(this); Objects.requireNonNull(this); m_93228_(matrix, this.guiLeft, this.guiTop, 0, 0, 256, 166);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
/* 302 */     return (super.m_7933_(keyCode, scanCode, modifiers) || this.searchField.m_7933_(keyCode, scanCode, modifiers));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_5534_(char codePoint, int modifiers) {
/* 307 */     return (super.m_5534_(codePoint, modifiers) || this.searchField.m_5534_(codePoint, modifiers));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7043_() {
/* 312 */     return false;
/*     */   }
/*     */   
/*     */   class ConfirmButton
/*     */     extends ControlButton
/*     */   {
/*     */     public ConfirmButton(int x, int y, Consumer<ProjectorScreen.PButton> action) {
/* 319 */       super(x, y, 10, 10, 0, 179, action, ProjectorScreen.GUI_CONFIRM);
/*     */     }
/*     */   }
/*     */   
/*     */   class CancelButton extends ControlButton {
/*     */     public CancelButton(int x, int y, Consumer<ProjectorScreen.PButton> action) {
/* 325 */       super(x, y, 10, 10, 10, 179, action, ProjectorScreen.GUI_CANCEL);
/*     */     } }
/*     */   
/*     */   class MirrorButton extends ControlButton {
/*     */     Settings settings;
/*     */     
/*     */     public MirrorButton(int x, int y, Settings settings, Consumer<ProjectorScreen.PButton> action) {
/* 332 */       super(x, y, 10, 10, 20, 179, action, ProjectorScreen.GUI_MIRROR);
/* 333 */       this.settings = settings;
/*     */     }
/*     */ 
/*     */     
/*     */     public void m_6303_(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 338 */       ClientUtils.bindTexture(ProjectorScreen.GUI_TEXTURE);
/* 339 */       if (this.f_93622_) {
/* 340 */         m_93172_(matrix, this.f_93620_, this.f_93621_ + 1, this.f_93620_ + this.iconSize, this.f_93621_ + this.iconSize - 1, -1350598657);
/*     */       }
/*     */       
/* 343 */       if (this.settings.isMirrored()) {
/* 344 */         m_93228_(matrix, this.f_93620_, this.f_93621_, this.xOverlay, this.yOverlay + this.iconSize, this.iconSize, this.iconSize);
/*     */       } else {
/* 346 */         m_93228_(matrix, this.f_93620_, this.f_93621_, this.xOverlay, this.yOverlay, this.iconSize, this.iconSize);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class RotateLeftButton extends ControlButton {
/*     */     public RotateLeftButton(int x, int y, Consumer<ProjectorScreen.PButton> action) {
/* 353 */       super(x, y, 10, 10, 30, 179, action, ProjectorScreen.GUI_ROTATE_CCW);
/*     */     }
/*     */   }
/*     */   
/*     */   class RotateRightButton extends ControlButton {
/*     */     public RotateRightButton(int x, int y, Consumer<ProjectorScreen.PButton> action) {
/* 359 */       super(x, y, 10, 10, 40, 179, action, ProjectorScreen.GUI_ROTATE_CW);
/*     */     } }
/*     */   
/*     */   class ControlButton extends PButton {
/*     */     Component hoverText;
/*     */     
/*     */     public ControlButton(int x, int y, int width, int height, int overlayX, int overlayY, Consumer<ProjectorScreen.PButton> action, Component hoverText) {
/* 366 */       super(x, y, width, height, overlayX, overlayY, action);
/* 367 */       this.hoverText = hoverText;
/*     */     }
/*     */ 
/*     */     
/*     */     public void m_7428_(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
/* 372 */       if (this.hoverText != null)
/* 373 */         ProjectorScreen.this.m_96602_(matrixStack, this.hoverText, mouseX, mouseY); 
/*     */     }
/*     */   }
/*     */   
/*     */   class SearchField
/*     */     extends EditBox {
/*     */     public SearchField(Font font, int x, int y) {
/* 380 */       super(font, x, y, 60, 14, ProjectorScreen.GUI_SEARCH);
/* 381 */       m_94199_(50);
/* 382 */       m_94182_(false);
/* 383 */       m_94194_(true);
/* 384 */       m_94202_(16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
/* 389 */       String s = m_94155_();
/* 390 */       if (super.m_7933_(keyCode, scanCode, modifiers)) {
/* 391 */         if (!Objects.equals(s, m_94155_())) {
/* 392 */           ProjectorScreen.this.updatelist();
/*     */         }
/*     */         
/* 395 */         return true;
/*     */       } 
/* 397 */       return ((m_93696_() && m_94213_() && keyCode != 256) || super.m_7933_(keyCode, scanCode, modifiers));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean m_5534_(char codePoint, int modifiers) {
/* 403 */       if (!m_93696_()) {
/* 404 */         m_5755_(true);
/* 405 */         m_94178_(true);
/*     */       } 
/*     */       
/* 408 */       String s = m_94155_();
/* 409 */       if (super.m_5534_(codePoint, modifiers)) {
/* 410 */         if (!Objects.equals(s, m_94155_())) {
/* 411 */           ProjectorScreen.this.updatelist();
/*     */         }
/*     */         
/* 414 */         return true;
/*     */       } 
/* 416 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Component translation(String key) {
/* 424 */     return (Component)Component.m_237115_(key);
/*     */   }
/*     */   
/*     */   static class PButton
/*     */     extends AbstractButton {
/*     */     protected boolean selected;
/*     */     protected final int xOverlay;
/*     */     protected final int yOverlay;
/* 432 */     protected int iconSize = 10;
/* 433 */     protected int bgStartX = 0; protected int bgStartY = 166; protected Consumer<PButton> action;
/*     */     
/*     */     public PButton(int x, int y, int width, int height, int overlayX, int overlayY, Consumer<PButton> action) {
/* 436 */       super(x, y, width, height, (Component)Component.m_237119_());
/* 437 */       this.action = action;
/* 438 */       this.xOverlay = overlayX;
/* 439 */       this.yOverlay = overlayY;
/*     */     }
/*     */ 
/*     */     
/*     */     public void m_6303_(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
/* 444 */       ClientUtils.bindTexture(ProjectorScreen.GUI_TEXTURE);
/* 445 */       if (this.f_93622_) {
/* 446 */         m_93172_(matrix, this.f_93620_, this.f_93621_ + 1, this.f_93620_ + this.iconSize, this.f_93621_ + this.iconSize - 1, -1350598657);
/*     */       }
/* 448 */       m_93228_(matrix, this.f_93620_, this.f_93621_, this.xOverlay, this.yOverlay, this.iconSize, this.iconSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public void m_5691_() {
/* 453 */       this.action.accept(this);
/*     */     }
/*     */     
/*     */     public boolean isSelected() {
/* 457 */       return this.selected;
/*     */     }
/*     */     
/*     */     public void setSelected(boolean isSelected) {
/* 461 */       this.selected = isSelected;
/*     */     }
/*     */     
/*     */     public void m_142291_(@Nonnull NarrationElementOutput output) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\ProjectorScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */