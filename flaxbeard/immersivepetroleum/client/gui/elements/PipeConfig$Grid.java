/*     */ package flaxbeard.immersivepetroleum.client.gui.elements;
/*     */ 
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Grid
/*     */ {
/*     */   private boolean changed;
/*     */   private int width;
/*     */   private int height;
/*     */   private byte[] array;
/*     */   
/*     */   public Grid(int width, int height) {
/* 243 */     this.width = width;
/* 244 */     this.height = height;
/* 245 */     this.array = new byte[width * height];
/*     */   }
/*     */ 
/*     */   
/*     */   Grid() {}
/*     */   
/*     */   public int getWidth() {
/* 252 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 256 */     return this.height;
/*     */   }
/*     */   
/*     */   public int size() {
/* 260 */     return this.array.length;
/*     */   }
/*     */   
/*     */   public void set(int x, int y, int value) {
/* 264 */     set(index(x, y), value);
/*     */   }
/*     */   
/*     */   public void set(int index, int value) {
/* 268 */     if (index >= 0 && index < this.array.length && this.array[index] != value) {
/* 269 */       this.array[index] = (byte)(value & 0xFF);
/* 270 */       this.changed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int x, int y) {
/* 276 */     return get(index(x, y));
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int index) {
/* 281 */     if (index >= 0 && index < this.array.length) {
/* 282 */       return this.array[index] & 0xFF;
/*     */     }
/* 284 */     return 0;
/*     */   }
/*     */   
/*     */   public void clearGrid() {
/* 288 */     for (int i = 0; i < size(); i++) {
/* 289 */       this.array[i] = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawLine(int xa, int ya, int xb, int yb, int value) {
/* 294 */     xa = Mth.m_14045_(xa, 0, this.width - 1);
/* 295 */     xb = Mth.m_14045_(xb, 0, this.width - 1);
/* 296 */     ya = Mth.m_14045_(ya, 0, this.height - 1);
/* 297 */     yb = Mth.m_14045_(yb, 0, this.height - 1);
/*     */     
/* 299 */     int dx = xb - xa;
/* 300 */     int dy = yb - ya;
/*     */     
/* 302 */     int length = Math.max(Math.abs(dx), Math.abs(dy));
/* 303 */     float tx = dx / length;
/* 304 */     float ty = dy / length;
/*     */     
/* 306 */     float x = xa, y = ya;
/* 307 */     int lx = -1, ly = -1;
/* 308 */     for (int i = 1; i <= length; i++) {
/* 309 */       int cx = Math.round(x);
/* 310 */       int cy = Math.round(y);
/*     */       
/* 312 */       if (lx != cx || ly != cy) {
/* 313 */         set(cx, cy, value);
/* 314 */         lx = cx;
/* 315 */         ly = cy;
/*     */       } 
/*     */       
/* 318 */       x += tx;
/* 319 */       y += ty;
/*     */     } 
/*     */   }
/*     */   
/*     */   int index(int x, int y) {
/* 324 */     return this.width * y + x;
/*     */   }
/*     */   
/*     */   public CompoundTag toCompound() {
/* 328 */     CompoundTag nbt = new CompoundTag();
/* 329 */     nbt.m_128405_("width", this.width);
/* 330 */     nbt.m_128405_("height", this.height);
/* 331 */     nbt.m_128382_("grid", this.array);
/* 332 */     return nbt;
/*     */   }
/*     */   
/*     */   public static Grid fromCompound(CompoundTag nbt) {
/* 336 */     Grid grid = new Grid();
/* 337 */     grid.width = nbt.m_128451_("width");
/* 338 */     grid.height = nbt.m_128451_("height");
/* 339 */     grid.array = nbt.m_128463_("grid");
/*     */     
/* 341 */     if (grid.array.length != grid.width * grid.height) {
/* 342 */       throw new IllegalStateException("Grid width and height don't match array.");
/*     */     }
/*     */     
/* 345 */     return grid;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\gui\elements\PipeConfig$Grid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */