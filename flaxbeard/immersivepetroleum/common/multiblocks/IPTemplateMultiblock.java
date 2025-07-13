/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import blusunrize.immersiveengineering.common.register.IEBlocks;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ 
/*    */ public abstract class IPTemplateMultiblock
/*    */   extends IETemplateMultiblock
/*    */ {
/*    */   private final Supplier<? extends Block> baseState;
/*    */   
/*    */   public IPTemplateMultiblock(ResourceLocation loc, BlockPos masterFromOrigin, BlockPos triggerFromOrigin, BlockPos size, Supplier<? extends Block> baseState) {
/* 17 */     super(loc, masterFromOrigin, triggerFromOrigin, size, new IEBlocks.BlockEntry(baseState.get()));
/* 18 */     this.baseState = baseState;
/*    */   }
/*    */   
/*    */   public Block getBaseBlock() {
/* 22 */     return this.baseState.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\IPTemplateMultiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */