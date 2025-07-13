/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class CokerUnitMultiblock extends IPTemplateMultiblock {
/* 11 */   public static final CokerUnitMultiblock INSTANCE = new CokerUnitMultiblock();
/*    */   
/*    */   public CokerUnitMultiblock() {
/* 14 */     super(ResourceUtils.ip("multiblocks/cokerunit"), new BlockPos(4, 0, 2), new BlockPos(4, 1, 4), new BlockPos(9, 23, 5), (Supplier<? extends Block>)IPContent.Multiblock.COKERUNIT);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getManualScale() {
/* 21 */     return 4.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeClient(Consumer<ClientMultiblocks.MultiblockManualData> consumer) {
/* 26 */     consumer.accept(new IPClientMultiblockProperties(this, 4.5D, 0.5D, 2.5D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\CokerUnitMultiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */