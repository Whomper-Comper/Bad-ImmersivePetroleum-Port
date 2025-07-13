/*     */ package flaxbeard.immersivepetroleum.api.event;
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraftforge.eventbus.api.Cancelable;
/*     */ 
/*     */ @Cancelable
/*     */ public class ProjectorEvent extends Event {
/*     */   protected MultiblockHandler.IMultiblock multiblock;
/*     */   protected Level realWorld;
/*     */   protected Level templateWorld;
/*     */   protected Rotation rotation;
/*     */   protected BlockPos worldPos;
/*     */   protected BlockPos templatePos;
/*     */   protected BlockState state;
/*     */   
/*     */   @Cancelable
/*     */   public static class PlaceBlock extends ProjectorEvent {
/*     */     public PlaceBlock(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/*  23 */       super(multiblock, templateWorld, templatePos, world, worldPos, state, rotation);
/*     */     }
/*     */     
/*     */     public void setBlockState(BlockState state) {
/*  27 */       this.state = state;
/*     */     }
/*     */     
/*     */     public void setState(Block block) {
/*  31 */       this.state = block.m_49966_();
/*     */     }
/*     */   }
/*     */   
/*     */   @Cancelable
/*     */   public static class PlaceBlockPost extends ProjectorEvent {
/*     */     public PlaceBlockPost(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/*  38 */       super(multiblock, templateWorld, templatePos, world, worldPos, state, rotation);
/*     */     }
/*     */   }
/*     */   
/*     */   @Cancelable
/*     */   public static class RenderBlock extends ProjectorEvent {
/*     */     public RenderBlock(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/*  45 */       super(multiblock, templateWorld, templatePos, world, worldPos, state, rotation);
/*     */     }
/*     */     
/*     */     public void setState(BlockState state) {
/*  49 */       this.state = state;
/*     */     }
/*     */     
/*     */     public void setState(Block block) {
/*  53 */       this.state = block.m_49966_();
/*     */     }
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
/*     */   public ProjectorEvent(MultiblockHandler.IMultiblock multiblock, Level templateWorld, BlockPos templatePos, Level world, BlockPos worldPos, BlockState state, Rotation rotation) {
/*  67 */     this.multiblock = multiblock;
/*  68 */     this.realWorld = world;
/*  69 */     this.templateWorld = templateWorld;
/*  70 */     this.worldPos = worldPos;
/*  71 */     this.templatePos = templatePos;
/*  72 */     this.state = state;
/*  73 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public MultiblockHandler.IMultiblock getMultiblock() {
/*  77 */     return this.multiblock;
/*     */   }
/*     */   
/*     */   public Level getWorld() {
/*  81 */     return this.realWorld;
/*     */   }
/*     */   
/*     */   public Level getTemplateWorld() {
/*  85 */     return this.templateWorld;
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/*  89 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public BlockPos getWorldPos() {
/*  93 */     return this.worldPos;
/*     */   }
/*     */   
/*     */   public BlockPos getTemplatePos() {
/*  97 */     return this.templatePos;
/*     */   }
/*     */   
/*     */   public BlockState getState() {
/* 101 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getTemplateState() {
/* 106 */     return this.templateWorld.m_8055_(this.templatePos);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\event\ProjectorEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */