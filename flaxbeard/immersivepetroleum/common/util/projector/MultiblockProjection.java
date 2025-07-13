/*     */ package flaxbeard.immersivepetroleum.common.util.projector;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import blusunrize.immersiveengineering.api.utils.TemplateWorldCreator;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiblockProjection
/*     */ {
/*     */   final MultiblockHandler.IMultiblock multiblock;
/*     */   final Level realWorld;
/*     */   final Level templateWorld;
/*  36 */   final StructurePlaceSettings settings = new StructurePlaceSettings();
/*  37 */   final Int2ObjectMap<List<StructureTemplate.StructureBlockInfo>> layers = (Int2ObjectMap<List<StructureTemplate.StructureBlockInfo>>)new Int2ObjectArrayMap();
/*  38 */   final BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos(); final int blockcount;
/*     */   boolean isDirty = true;
/*     */   
/*     */   public MultiblockProjection(@Nonnull Level world, @Nonnull MultiblockHandler.IMultiblock multiblock) {
/*  42 */     Objects.requireNonNull(world, "World cannot be null!");
/*  43 */     Objects.requireNonNull(multiblock, "Multiblock cannot be null!");
/*     */     
/*  45 */     this.multiblock = multiblock;
/*  46 */     this.realWorld = world;
/*     */     
/*  48 */     List<StructureTemplate.StructureBlockInfo> blocks = multiblock.getStructure(world);
/*  49 */     this.templateWorld = ((TemplateWorldCreator)TemplateWorldCreator.CREATOR.getValue()).makeWorld(blocks, pos -> true);
/*     */     
/*  51 */     this.blockcount = blocks.size();
/*  52 */     for (StructureTemplate.StructureBlockInfo info : blocks) {
/*  53 */       List<StructureTemplate.StructureBlockInfo> list = (List<StructureTemplate.StructureBlockInfo>)this.layers.get(info.f_74675_.m_123342_());
/*  54 */       if (list == null) {
/*  55 */         list = new ArrayList<>();
/*  56 */         this.layers.put(info.f_74675_.m_123342_(), list);
/*     */       } 
/*     */       
/*  59 */       list.add(info);
/*     */     } 
/*     */   }
/*     */   
/*     */   public MultiblockProjection setRotation(Rotation rotation) {
/*  64 */     if (this.settings.m_74404_() != rotation) {
/*  65 */       this.settings.m_74379_(rotation);
/*  66 */       this.isDirty = true;
/*     */     } 
/*     */     
/*  69 */     return this;
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
/*     */   public MultiblockProjection setFlip(boolean mirror) {
/*  82 */     Mirror m = mirror ? Mirror.FRONT_BACK : Mirror.NONE;
/*  83 */     if (this.settings.m_74401_() != m) {
/*  84 */       this.settings.m_74377_(m);
/*  85 */       this.isDirty = true;
/*     */     } 
/*     */     
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  92 */     this.settings.m_74379_(Rotation.NONE);
/*  93 */     this.settings.m_74377_(Mirror.NONE);
/*  94 */     this.offset.m_122178_(0, 0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockCount() {
/*  99 */     return this.blockcount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLayerCount() {
/* 104 */     return this.layers.size();
/*     */   }
/*     */   
/*     */   public int getLayerSize(int layer) {
/* 108 */     if (layer < 0 || layer >= this.layers.size()) {
/* 109 */       return 0;
/*     */     }
/*     */     
/* 112 */     return ((List)this.layers.get(layer)).size();
/*     */   }
/*     */   
/*     */   public Level getTemplateWorld() {
/* 116 */     return this.templateWorld;
/*     */   }
/*     */   
/*     */   public MultiblockHandler.IMultiblock getMultiblock() {
/* 120 */     return this.multiblock;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 125 */     if (this == obj)
/* 126 */       return true; 
/* 127 */     if (obj instanceof MultiblockProjection) { MultiblockProjection other = (MultiblockProjection)obj;
/* 128 */       return (this.multiblock.getUniqueName().equals(other.multiblock.getUniqueName()) && this.settings
/* 129 */         .m_74401_() == other.settings.m_74401_() && this.settings
/* 130 */         .m_74404_() == other.settings.m_74404_()); }
/*     */ 
/*     */     
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean process(int layer, Predicate<Info> predicate) {
/* 144 */     updateData();
/*     */     
/* 146 */     List<StructureTemplate.StructureBlockInfo> blocks = (List<StructureTemplate.StructureBlockInfo>)this.layers.get(layer);
/* 147 */     for (StructureTemplate.StructureBlockInfo info : blocks) {
/* 148 */       if (predicate.test(new Info(this, info))) {
/* 149 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processAll(BiPredicate<Integer, Info> predicate) {
/* 163 */     updateData();
/*     */     
/* 165 */     for (int layer = 0; layer < getLayerCount(); layer++) {
/* 166 */       List<StructureTemplate.StructureBlockInfo> blocks = (List<StructureTemplate.StructureBlockInfo>)this.layers.get(layer);
/* 167 */       for (StructureTemplate.StructureBlockInfo info : blocks) {
/* 168 */         if (predicate.test(Integer.valueOf(layer), new Info(this, info))) {
/* 169 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   private void updateData() {
/* 177 */     if (!this.isDirty)
/*     */       return; 
/* 179 */     this.isDirty = false;
/*     */     
/* 181 */     boolean mirrored = (this.settings.m_74401_() == Mirror.FRONT_BACK);
/* 182 */     Rotation rotation = this.settings.m_74404_();
/* 183 */     Vec3i size = this.multiblock.getSize(this.realWorld);
/*     */ 
/*     */     
/* 186 */     if (!mirrored) {
/* 187 */       switch (rotation) { case CLOCKWISE_90:
/* 188 */           this.offset.m_122178_(1 - size.m_123343_(), 0, 0); break;
/* 189 */         case CLOCKWISE_180: this.offset.m_122178_(1 - size.m_123341_(), 0, 1 - size.m_123343_()); break;
/* 190 */         case COUNTERCLOCKWISE_90: this.offset.m_122178_(0, 0, 1 - size.m_123341_()); break;
/* 191 */         default: this.offset.m_122178_(0, 0, 0); break; }
/*     */     
/*     */     } else {
/* 194 */       switch (rotation) { case NONE:
/* 195 */           this.offset.m_122178_(1 - size.m_123341_(), 0, 0); break;
/* 196 */         case CLOCKWISE_90: this.offset.m_122178_(1 - size.m_123343_(), 0, 1 - size.m_123341_()); break;
/* 197 */         case CLOCKWISE_180: this.offset.m_122178_(0, 0, 1 - size.m_123343_()); break;
/* 198 */         default: this.offset.m_122178_(0, 0, 0);
/*     */           break; }
/*     */ 
/*     */     
/*     */     } 
/* 203 */     int x = ((rotation.ordinal() % 2 == 0) ? size.m_123341_() : size.m_123343_()) / 2;
/* 204 */     int z = ((rotation.ordinal() % 2 == 0) ? size.m_123343_() : size.m_123341_()) / 2;
/* 205 */     this.offset.m_122154_((Vec3i)this.offset, x, 0, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Info
/*     */   {
/*     */     public final StructurePlaceSettings settings;
/*     */ 
/*     */     
/*     */     public final MultiblockHandler.IMultiblock multiblock;
/*     */ 
/*     */     
/*     */     public final BlockPos tPos;
/*     */     
/*     */     public final Level templateWorld;
/*     */     
/*     */     public final StructureTemplate.StructureBlockInfo tBlockInfo;
/*     */ 
/*     */     
/*     */     public Info(MultiblockProjection projection, StructureTemplate.StructureBlockInfo templateBlockInfo) {
/* 226 */       this.multiblock = projection.multiblock;
/* 227 */       this.templateWorld = projection.templateWorld;
/* 228 */       this.settings = projection.settings;
/* 229 */       this.tBlockInfo = templateBlockInfo;
/* 230 */       this.tPos = StructureTemplate.m_74563_(this.settings, templateBlockInfo.f_74675_).m_121996_((Vec3i)projection.offset);
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockState getModifiedState(Level realWorld, BlockPos realPos) {
/* 235 */       return this.templateWorld.m_8055_(this.tBlockInfo.f_74675_)
/* 236 */         .m_60715_(this.settings.m_74401_())
/* 237 */         .rotate((LevelAccessor)realWorld, realPos, this.settings.m_74404_());
/*     */     }
/*     */     
/*     */     public BlockState getRawState() {
/* 241 */       return this.templateWorld.m_8055_(this.tBlockInfo.f_74675_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\projector\MultiblockProjection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */