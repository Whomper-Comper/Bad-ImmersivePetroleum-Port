/*     */ package flaxbeard.immersivepetroleum.client.render.dyn;
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.RemovalNotification;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class DynamicTextureWrapper {
/*     */   public static final Cache<UUID, DynamicTextureWrapper> DYN_TEXTURE_CACHE;
/*     */   public final int width;
/*     */   public final int height;
/*     */   @Nonnull
/*     */   public final UUID uuid;
/*     */   public final DynamicTexture texture;
/*     */   public final RenderType renderType;
/*     */   private final ResourceLocation rl;
/*     */   
/*     */   static {
/*  31 */     DYN_TEXTURE_CACHE = CacheBuilder.newBuilder().removalListener(s -> { DynamicTextureWrapper wrapper = (DynamicTextureWrapper)s.getValue(); wrapper.dispose(); ImmersivePetroleum.log.debug("Disposed survey result texture {}", wrapper.rl); }).expireAfterAccess(5L, TimeUnit.MINUTES).maximumSize(50L).build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static DynamicTextureWrapper getOrCreate(int width, int height, @Nonnull SurveyScan scan) {
/*  36 */     if (scan == null || scan.getUuid() == null) {
/*  37 */       return null;
/*     */     }
/*     */     
/*  40 */     DynamicTextureWrapper tex = (DynamicTextureWrapper)DYN_TEXTURE_CACHE.getIfPresent(scan.getUuid());
/*  41 */     if (tex == null || tex.texture.m_117991_() == null) {
/*  42 */       tex = new DynamicTextureWrapper(width, height, scan.getUuid());
/*  43 */       DYN_TEXTURE_CACHE.invalidate(scan.getUuid());
/*  44 */       DYN_TEXTURE_CACHE.put(scan.getUuid(), tex);
/*     */       
/*  46 */       tex.write(scan.getData());
/*     */       
/*  48 */       ImmersivePetroleum.log.debug("Created survey result texture {}", tex.rl);
/*     */     } 
/*  50 */     return tex;
/*     */   }
/*     */   
/*     */   public static void clearCache() {
/*  54 */     DYN_TEXTURE_CACHE.invalidateAll();
/*  55 */     DYN_TEXTURE_CACHE.cleanUp();
/*     */   }
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
/*     */   private DynamicTextureWrapper(int width, int height, @Nonnull UUID uuid) {
/*  72 */     Objects.requireNonNull(uuid, "Non-null UUID expected.");
/*     */     
/*  74 */     this.width = width;
/*  75 */     this.height = height;
/*  76 */     this.uuid = uuid;
/*     */     
/*  78 */     this.texture = new DynamicTexture(width, height, true);
/*  79 */     this.rl = ResourceUtils.ip("dyntexture/" + uuid);
/*     */     
/*  81 */     MCUtil.getTextureManager().m_118495_(this.rl, (AbstractTexture)this.texture);
/*  82 */     this.renderType = RenderType.m_110497_(this.rl);
/*     */   }
/*     */   
/*     */   public void write(byte[] mapData) {
/*  86 */     if (mapData == null || mapData.length != this.width * this.height) {
/*     */       return;
/*     */     }
/*  89 */     if (this.texture.m_117991_() != null) {
/*  90 */       NativeImage image = this.texture.m_117991_();
/*  91 */       for (int y = 0; y < this.width; y++) {
/*  92 */         for (int x = 0; x < this.width; x++) {
/*  93 */           int b = mapData[y * this.height + x] & 0xFF;
/*     */           
/*  95 */           int rgba = b << 16 | b << 8 | b;
/*  96 */           image.m_84988_(x, y, 0xFF000000 | rgba);
/*     */         } 
/*     */       } 
/*  99 */       this.texture.m_117985_();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDisposed() {
/* 104 */     return (this.texture.m_117991_() == null);
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 108 */     this.texture.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\dyn\DynamicTextureWrapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */