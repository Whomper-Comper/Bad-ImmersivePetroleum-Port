/*     */ package flaxbeard.immersivepetroleum.common.util.projector;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Info
/*     */ {
/*     */   public final StructurePlaceSettings settings;
/*     */   public final MultiblockHandler.IMultiblock multiblock;
/*     */   public final BlockPos tPos;
/*     */   public final Level templateWorld;
/*     */   public final StructureTemplate.StructureBlockInfo tBlockInfo;
/*     */   
/*     */   public Info(MultiblockProjection projection, StructureTemplate.StructureBlockInfo templateBlockInfo) {
/* 226 */     this.multiblock = projection.multiblock;
/* 227 */     this.templateWorld = projection.templateWorld;
/* 228 */     this.settings = projection.settings;
/* 229 */     this.tBlockInfo = templateBlockInfo;
/* 230 */     this.tPos = StructureTemplate.m_74563_(this.settings, templateBlockInfo.f_74675_).m_121996_((Vec3i)projection.offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getModifiedState(Level realWorld, BlockPos realPos) {
/* 235 */     return this.templateWorld.m_8055_(this.tBlockInfo.f_74675_)
/* 236 */       .m_60715_(this.settings.m_74401_())
/* 237 */       .rotate((LevelAccessor)realWorld, realPos, this.settings.m_74404_());
/*     */   }
/*     */   
/*     */   public BlockState getRawState() {
/* 241 */     return this.templateWorld.m_8055_(this.tBlockInfo.f_74675_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\projector\MultiblockProjection$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */