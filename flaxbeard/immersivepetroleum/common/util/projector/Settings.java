/*     */ package flaxbeard.immersivepetroleum.common.util.projector;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import flaxbeard.immersivepetroleum.common.network.MessageProjectorSync;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Settings
/*     */ {
/*     */   public static final String KEY_SELF = "settings";
/*     */   public static final String KEY_BLOCKS = "blocks";
/*     */   public static final String KEY_MODE = "mode";
/*     */   public static final String KEY_MULTIBLOCK = "multiblock";
/*     */   public static final String KEY_MIRROR = "mirror";
/*     */   public static final String KEY_PLACED = "placed";
/*     */   public static final String KEY_ROTATION = "rotation";
/*     */   public static final String KEY_POSITION = "pos";
/*     */   private Mode mode;
/*     */   private Rotation rotation;
/*  33 */   private BlockPos pos = null;
/*  34 */   private MultiblockHandler.IMultiblock multiblock = null;
/*     */   private boolean mirror;
/*     */   private boolean isPlaced;
/*     */   
/*     */   public Settings() {
/*  39 */     this(new CompoundTag());
/*     */   }
/*     */   
/*     */   public Settings(@Nullable ItemStack stack) {
/*  43 */     this((() -> {
/*     */           CompoundTag nbt = null;
/*     */           
/*     */           if (stack != null && (nbt = stack.m_41737_("settings")) == null) {
/*     */             nbt = new CompoundTag();
/*     */           }
/*     */           
/*     */           return nbt;
/*  51 */         }).get());
/*     */   }
/*     */   
/*     */   public Settings(CompoundTag settingsNbt) {
/*  55 */     if (settingsNbt == null || settingsNbt.m_128456_()) {
/*  56 */       this.mode = Mode.MULTIBLOCK_SELECTION;
/*  57 */       this.rotation = Rotation.NONE;
/*  58 */       this.mirror = false;
/*  59 */       this.isPlaced = false;
/*     */     } else {
/*  61 */       this.mode = Mode.values()[Mth.m_14045_(settingsNbt.m_128451_("mode"), 0, (Mode.values()).length - 1)];
/*  62 */       this.rotation = Rotation.values()[settingsNbt.m_128441_("rotation") ? settingsNbt.m_128451_("rotation") : 0];
/*  63 */       this.mirror = settingsNbt.m_128471_("mirror");
/*  64 */       this.isPlaced = settingsNbt.m_128471_("placed");
/*     */       
/*  66 */       if (settingsNbt.m_128425_("multiblock", 8)) {
/*  67 */         String str = settingsNbt.m_128461_("multiblock");
/*  68 */         this.multiblock = MultiblockHandler.getByUniqueName(new ResourceLocation(str));
/*     */       } 
/*     */       
/*  71 */       if (settingsNbt.m_128425_("pos", 10)) {
/*  72 */         CompoundTag pos = settingsNbt.m_128469_("pos");
/*  73 */         int x = pos.m_128451_("x");
/*  74 */         int y = pos.m_128451_("y");
/*  75 */         int z = pos.m_128451_("z");
/*  76 */         this.pos = new BlockPos(x, y, z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateCW() {
/*  83 */     this.rotation = this.rotation.m_55952_(Rotation.CLOCKWISE_90);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateCCW() {
/*  88 */     this.rotation = this.rotation.m_55952_(Rotation.COUNTERCLOCKWISE_90);
/*     */   }
/*     */   
/*     */   public void flip() {
/*  92 */     this.mirror = !this.mirror;
/*     */   }
/*     */   
/*     */   public void switchMode() {
/*  96 */     int id = this.mode.ordinal() + 1;
/*  97 */     this.mode = Mode.values()[id % (Mode.values()).length];
/*     */   }
/*     */   
/*     */   public void sendPacketToServer(InteractionHand hand) {
/* 101 */     MessageProjectorSync.sendToServer(this, hand);
/*     */   }
/*     */   
/*     */   public void sendPacketToClient(Player player, InteractionHand hand) {
/* 105 */     MessageProjectorSync.sendToClient(player, this, hand);
/*     */   }
/*     */   
/*     */   public void setRotation(Rotation rotation) {
/* 109 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public void setMode(Mode mode) {
/* 113 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void setMultiblock(@Nullable MultiblockHandler.IMultiblock multiblock) {
/* 117 */     this.multiblock = multiblock;
/*     */   }
/*     */   
/*     */   public void setMirror(boolean mirror) {
/* 121 */     this.mirror = mirror;
/*     */   }
/*     */   
/*     */   public void setPlaced(boolean isPlaced) {
/* 125 */     this.isPlaced = isPlaced;
/*     */   }
/*     */   
/*     */   public void setPos(@Nullable BlockPos pos) {
/* 129 */     this.pos = pos;
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/* 133 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public boolean isMirrored() {
/* 137 */     return this.mirror;
/*     */   }
/*     */   
/*     */   public boolean isPlaced() {
/* 141 */     return this.isPlaced;
/*     */   }
/*     */   
/*     */   public Mode getMode() {
/* 145 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getPos() {
/* 153 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MultiblockHandler.IMultiblock getMultiblock() {
/* 159 */     return this.multiblock;
/*     */   }
/*     */   
/*     */   public CompoundTag toNbt() {
/* 163 */     CompoundTag nbt = new CompoundTag();
/* 164 */     nbt.m_128405_("mode", this.mode.ordinal());
/* 165 */     nbt.m_128405_("rotation", this.rotation.ordinal());
/* 166 */     nbt.m_128379_("mirror", this.mirror);
/* 167 */     nbt.m_128379_("placed", this.isPlaced);
/*     */     
/* 169 */     if (this.multiblock != null) {
/* 170 */       nbt.m_128359_("multiblock", this.multiblock.getUniqueName().toString());
/*     */     }
/*     */     
/* 173 */     if (this.pos != null) {
/* 174 */       CompoundTag pos = new CompoundTag();
/* 175 */       pos.m_128405_("x", this.pos.m_123341_());
/* 176 */       pos.m_128405_("y", this.pos.m_123342_());
/* 177 */       pos.m_128405_("z", this.pos.m_123343_());
/* 178 */       nbt.m_128365_("pos", (Tag)pos);
/*     */     } 
/*     */     
/* 181 */     return nbt;
/*     */   }
/*     */   
/*     */   public ItemStack applyTo(ItemStack stack) {
/* 185 */     stack.m_41698_("settings");
/* 186 */     stack.m_41783_().m_128365_("settings", (Tag)toNbt());
/* 187 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 192 */     return "\"Settings\":[" + toNbt().toString() + "]";
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 196 */     MULTIBLOCK_SELECTION, PROJECTION;
/*     */     final String translation;
/*     */     
/*     */     Mode() {
/* 200 */       this.translation = "desc.immersivepetroleum.info.projector.mode_" + ordinal();
/*     */     }
/*     */     
/*     */     public Component getTranslated() {
/* 204 */       return (Component)Component.m_237115_(this.translation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\projector\Settings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */