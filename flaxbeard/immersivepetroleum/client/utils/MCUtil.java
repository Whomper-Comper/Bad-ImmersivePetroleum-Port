/*     */ package flaxbeard.immersivepetroleum.client.utils;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.Window;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.particle.ParticleEngine;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.GameRenderer;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
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
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class MCUtil
/*     */ {
/*     */   public static void bindTexture(ResourceLocation texture) {
/*  37 */     RenderSystem.m_157456_(0, texture);
/*     */   }
/*     */   
/*     */   public static void setScreen(Screen screen) {
/*  41 */     Minecraft.m_91087_().m_91152_(screen);
/*     */   }
/*     */   
/*     */   public static Screen getScreen() {
/*  45 */     Minecraft mc = Minecraft.m_91087_();
/*  46 */     return mc.f_91080_;
/*     */   }
/*     */   
/*     */   public static ParticleEngine getParticleEngine() {
/*  50 */     Minecraft mc = Minecraft.m_91087_();
/*  51 */     return mc.f_91061_;
/*     */   }
/*     */   
/*     */   public static TextureManager getTextureManager() {
/*  55 */     Minecraft mc = Minecraft.m_91087_();
/*  56 */     return mc.f_90987_;
/*     */   }
/*     */   
/*     */   public static BlockRenderDispatcher getBlockRenderer() {
/*  60 */     Minecraft mc = Minecraft.m_91087_();
/*  61 */     return mc.m_91289_();
/*     */   }
/*     */   
/*     */   public static BakedModel getModel(ResourceLocation modelLocation) {
/*  65 */     Minecraft mc = Minecraft.m_91087_();
/*  66 */     return mc.m_91289_().m_110907_().m_110881_().getModel(modelLocation);
/*     */   }
/*     */   
/*     */   public static GameRenderer getGameRenderer() {
/*  70 */     Minecraft mc = Minecraft.m_91087_();
/*  71 */     return mc.f_91063_;
/*     */   }
/*     */   
/*     */   public static ClientLevel getLevel() {
/*  75 */     Minecraft mc = Minecraft.m_91087_();
/*  76 */     return mc.f_91073_;
/*     */   }
/*     */   
/*     */   public static Font getFont() {
/*  80 */     Minecraft mc = Minecraft.m_91087_();
/*  81 */     return mc.f_91062_;
/*     */   }
/*     */   
/*     */   public static LocalPlayer getPlayer() {
/*  85 */     Minecraft mc = Minecraft.m_91087_();
/*  86 */     return mc.f_91074_;
/*     */   }
/*     */   
/*     */   public static HitResult getHitResult() {
/*  90 */     Minecraft mc = Minecraft.m_91087_();
/*  91 */     return mc.f_91077_;
/*     */   }
/*     */   
/*     */   public static Options getOptions() {
/*  95 */     Minecraft mc = Minecraft.m_91087_();
/*  96 */     return mc.f_91066_;
/*     */   }
/*     */   
/*     */   public static Window getWindow() {
/* 100 */     Minecraft mc = Minecraft.m_91087_();
/* 101 */     return mc.m_91268_();
/*     */   }
/*     */   
/*     */   public static ItemRenderer getItemRenderer() {
/* 105 */     Minecraft mc = Minecraft.m_91087_();
/* 106 */     return mc.m_91291_();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\clien\\utils\MCUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */