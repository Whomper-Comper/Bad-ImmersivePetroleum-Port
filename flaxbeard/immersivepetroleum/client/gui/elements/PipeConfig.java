/*     */ package flaxbeard.immersivepetroleum.client.gui.elements;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Matrix4f;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPClientConfig;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ 
/*     */ public class PipeConfig extends Button {
/*     */   static final Button.OnPress NO_ACTION = b -> {
/*     */     
/*     */     };
/*     */   public static final int EMPTY = 0;
/*     */   public static final int PIPE_NORMAL = 1;
/*     */   public static final int PIPE_PERFORATED = 2;
/*     */   public static final int PIPE_PERFORATED_FIXED = 3;
/*     */   private final int dynTextureWidth;
/*     */   private final int dynTextureHeight;
/*     */   private final DynamicTexture gridTexture;
/*     */   private final RenderType gridTextureRenderType;
/*     */   private final ResourceLocation dynTextureRL;
/*     */   private final int pipeNormalColor;
/*     */   private final int pipePerforatedColor;
/*     */   private final int pipePerforatedFixedColor;
/*     */   protected Grid grid;
/*     */   protected ColumnPos tilePos;
/*     */   protected int gridWidthScaled;
/*     */   protected int gridHeightScaled;
/*     */   protected int gridScale;
/*     */   int[] pipeCountArray;
/*     */   
/*  51 */   public PipeConfig(DerrickTileEntity tile, int x, int y, int width, int height, int gridWidth, int gridHeight, int gridScale) { super(x, y, width, height, (Component)Component.m_237119_(), NO_ACTION);
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
/* 204 */     this.pipeCountArray = new int[4]; this.tilePos = Utils.toColumnPos(tile.m_58899_()); this.grid = new Grid(gridWidth, gridHeight); copyGridFrom(tile.gridStorage); this.gridWidthScaled = gridWidth * gridScale; this.gridHeightScaled = gridHeight * gridScale; this.gridScale = Mth.m_14045_(gridScale, 1, 2147483647); this.dynTextureWidth = gridWidth; this.dynTextureHeight = gridHeight; this.gridTexture = new DynamicTexture(this.dynTextureWidth, this.dynTextureHeight, true); this.dynTextureRL = ResourceUtils.ip("pipegrid/" + hashCode()); MCUtil.getTextureManager().m_118495_(this.dynTextureRL, (AbstractTexture)this.gridTexture); this.gridTextureRenderType = RenderType.m_110497_(this.dynTextureRL); this.pipeNormalColor = Integer.valueOf((String)IPClientConfig.GRID_COLORS.pipe_normal_color.get(), 16).intValue(); this.pipePerforatedColor = Integer.valueOf((String)IPClientConfig.GRID_COLORS.pipe_perforated_color.get(), 16).intValue(); this.pipePerforatedFixedColor = Integer.valueOf((String)IPClientConfig.GRID_COLORS.pipe_perforated_fixed_color.get(), 16).intValue(); updateTexture(); } public void reset(DerrickTileEntity tile) { copyGridFrom(tile.gridStorage); updateTexture(); } private void copyGridFrom(Grid grid) { if (grid != null && grid.width == this.grid.width && grid.height == this.grid.height) { System.arraycopy(grid.array, 0, this.grid.array, 0, this.grid.array.length); this.grid.changed = true; }  } public Grid getGrid() { return this.grid; }
/*     */   public int getGridScale() { return this.gridScale; }
/* 206 */   private int[] getPipeCount() { if (this.grid.changed) {
/* 207 */       this.pipeCountArray = new int[4];
/* 208 */       for (int i = 0; i < this.grid.size(); i++) {
/* 209 */         int type = this.grid.get(i);
/* 210 */         this.pipeCountArray[type] = this.pipeCountArray[type] + 1;
/*     */       } 
/* 212 */       this.grid.changed = false;
/*     */     } 
/* 214 */     return this.pipeCountArray; }
/*     */   public void dispose() { this.gridTexture.close(); MCUtil.getTextureManager().m_118513_(this.dynTextureRL); }
/*     */   public void updateTexture() { NativeImage image = this.gridTexture.m_117991_(); int texCenterX = this.grid.width / this.gridScale; int texCenterY = this.grid.height / this.gridScale; int x2 = this.grid.width / 2; int y2 = this.grid.height / 2; int x4 = this.grid.width / 4; int y4 = this.grid.height / 4; ClientLevel world = MCUtil.getLevel(); int a = 0; for (int gy = 0; gy < this.grid.getHeight(); gy++) { for (int gx = 0; gx < this.grid.getWidth(); gx++, a++) { int tmp; switch (this.grid.get(gx, gy)) { case 0: tmp = 0; if (gx < texCenterX - 2 || gx > texCenterX + 2 || gy < texCenterY - 2 || gy > texCenterY + 2) { int px = gx - x2; int py = gy - y2; ColumnPos c = new ColumnPos(this.tilePos.f_140723_() + px, this.tilePos.f_140724_() + py); int y = world.m_5452_(Heightmap.Types.MOTION_BLOCKING, new BlockPos(c.f_140723_(), 0, c.f_140724_())).m_123342_() - 1; BlockPos p = new BlockPos(c.f_140723_(), y, c.f_140724_()); float f = (a % 2 == 0) ? 0.55F : 0.6F; if (gx % x4 == 0 || gy % y4 == 0)
/*     */                 f -= 0.15F;  tmp = (world.m_8055_(p).m_60780_((BlockGetter)world, p)).f_76396_; int r = (int)((tmp >> 16 & 0xFF) * f); int g = (int)((tmp >> 8 & 0xFF) * f); int b = (int)((tmp >> 0 & 0xFF) * f); tmp = r << 16 | g << 8 | b; } case 1: case 2: 
/*     */           case 3: 
/* 219 */           default: break; }  int color = 0; image.m_84988_(gx, gy, toABGR(color)); }  }  this.gridTexture.m_117985_(); } protected boolean m_7972_(int button) { return (button == 0); }
/*     */   public void m_6305_(PoseStack matrix, int mx, int my, float partialTicks) { MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_()); VertexConsumer builder = buffer.m_6299_(this.gridTextureRenderType); matrix.m_85836_(); matrix.m_85837_(this.f_93620_, this.f_93621_, 0.0D); Matrix4f mat = matrix.m_85850_().m_85861_(); int x = this.grid.width * this.gridScale; int y = this.grid.height * this.gridScale; builder.m_85982_(mat, 0.0F, y, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(0.0F, 1.0F).m_85969_(15728880).m_5752_(); builder.m_85982_(mat, x, y, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(1.0F, 1.0F).m_85969_(15728880).m_5752_(); builder.m_85982_(mat, x, 0.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(1.0F, 0.0F).m_85969_(15728880).m_5752_(); builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(0.0F, 0.0F).m_85969_(15728880).m_5752_(); matrix.m_85849_(); buffer.m_109911_(); }
/*     */   public boolean m_6375_(double mouseX, double mouseY, int button) { if (m_7972_(button)) { boolean flag = m_93680_(mouseX, mouseY); if (flag) { m_7435_(Minecraft.m_91087_().m_91106_()); onGridClick((int)(mouseX - this.f_93620_) / this.gridScale, (int)(mouseY - this.f_93621_) / this.gridScale, button); return true; }  }  return false; }
/*     */   protected void onGridClick(int x, int y, int button) { int type = this.grid.get(x, y); if ((type == 1 || type == 2) && type != 3) { int[] array = getPipeCount(); if (type == 1 && array[2] < 2) { this.grid.set(x, y, 2); } else { this.grid.set(x, y, 1); }  } else { this.grid.clearGrid(); this.grid.drawLine(this.grid.getWidth() / 2, this.grid.getHeight() / 2, x, y, 1); this.grid.set(this.grid.getWidth() / 2, this.grid.getHeight() / 2, 3); this.grid.set(Mth.m_14045_(x, 0, this.grid.getWidth() - 1), Mth.m_14045_(y, 0, this.grid.getHeight() - 1), 3); }
/* 223 */      updateTexture(); } public PipeConfig copyDataFrom(PipeConfig other) { if (other != null) {
/* 224 */       this.gridScale = other.gridScale;
/* 225 */       this.grid = other.grid;
/* 226 */       updateTexture();
/*     */     } 
/* 228 */     return this; }
/*     */ 
/*     */   
/*     */   private static int toABGR(int rgb) {
/* 232 */     int r = rgb >> 16 & 0xFF;
/* 233 */     int g = rgb >> 8 & 0xFF;
/* 234 */     int b = rgb & 0xFF;
/* 235 */     return 0xFF000000 | b << 16 | g << 8 | r;
/*     */   }
/*     */   public static class Grid { private boolean changed;
/*     */     private int width;
/*     */     private int height;
/*     */     private byte[] array;
/*     */     
/*     */     public Grid(int width, int height) {
/* 243 */       this.width = width;
/* 244 */       this.height = height;
/* 245 */       this.array = new byte[width * height];
/*     */     }
/*     */ 
/*     */     
/*     */     Grid() {}
/*     */     
/*     */     public int getWidth() {
/* 252 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 256 */       return this.height;
/*     */     }
/*     */     
/*     */     public int size() {
/* 260 */       return this.array.length;
/*     */     }
/*     */     
/*     */     public void set(int x, int y, int value) {
/* 264 */       set(index(x, y), value);
/*     */     }
/*     */     
/*     */     public void set(int index, int value) {
/* 268 */       if (index >= 0 && index < this.array.length && this.array[index] != value) {
/* 269 */         this.array[index] = (byte)(value & 0xFF);
/* 270 */         this.changed = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int get(int x, int y) {
/* 276 */       return get(index(x, y));
/*     */     }
/*     */ 
/*     */     
/*     */     public int get(int index) {
/* 281 */       if (index >= 0 && index < this.array.length) {
/* 282 */         return this.array[index] & 0xFF;
/*     */       }
/* 284 */       return 0;
/*     */     }
/*     */     
/*     */     public void clearGrid() {
/* 288 */       for (int i = 0; i < size(); i++) {
/* 289 */         this.array[i] = 0;
/*     */       }
/*     */     }
/*     */     
/*     */     public void drawLine(int xa, int ya, int xb, int yb, int value) {
/* 294 */       xa = Mth.m_14045_(xa, 0, this.width - 1);
/* 295 */       xb = Mth.m_14045_(xb, 0, this.width - 1);
/* 296 */       ya = Mth.m_14045_(ya, 0, this.height - 1);
/* 297 */       yb = Mth.m_14045_(yb, 0, this.height - 1);
/*     */       
/* 299 */       int dx = xb - xa;
/* 300 */       int dy = yb - ya;
/*     */       
/* 302 */       int length = Math.max(Math.abs(dx), Math.abs(dy));
/* 303 */       float tx = dx / length;
/* 304 */       float ty = dy / length;
/*     */       
/* 306 */       float x = xa, y = ya;
/* 307 */       int lx = -1, ly = -1;
/* 308 */       for (int i = 1; i <= length; i++) {
/* 309 */         int cx = Math.round(x);
/* 310 */         int cy = Math.round(y);
/*     */         
/* 312 */         if (lx != cx || ly != cy) {
/* 313 */           set(cx, cy, value);
/* 314 */           lx = cx;
/* 315 */           ly = cy;
/*     */         } 
/*     */         
/* 318 */         x += tx;
/* 319 */         y += ty;
/*     */       } 
/*     */     }
/*     */     
/*     */     int index(int x, int y) {
/* 324 */       return this.width * y + x;
/*     */     }
/*     */     
/*     */     public CompoundTag toCompound() {
/* 328 */       CompoundTag nbt = new CompoundTag();
/* 329 */       nbt.m_128405_("width", this.width);
/* 330 */       nbt.m_128405_("height", this.height);
/* 331 */       nbt.m_128382_("grid", this.array);
/* 332 */       return nbt;
/*     */     }
/*     */     
/*     */     public static Grid fromCompound(CompoundTag nbt) {
/* 336 */       Grid grid = new Grid();
/* 337 */       grid.width = nbt.m_128451_("width");
/* 338 */       grid.height = nbt.m_128451_("height");
/* 339 */       grid.array = nbt.m_128463_("grid");
/*     */       
/* 341 */       if (grid.array.length != grid.width * grid.height) {
/* 342 */         throw new IllegalStateException("Grid width and height don't match array.");
/*     */       }
/*     */       
/* 345 */       return grid;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\elements\PipeConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */