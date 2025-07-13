/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class OilTankMultiblock extends IPTemplateMultiblock {
/* 11 */   public static final OilTankMultiblock INSTANCE = new OilTankMultiblock();
/*    */   
/*    */   public OilTankMultiblock() {
/* 14 */     super(ResourceUtils.ip("multiblocks/oiltank"), new BlockPos(2, 0, 3), new BlockPos(2, 1, 5), new BlockPos(5, 4, 6), (Supplier<? extends Block>)IPContent.Multiblock.OILTANK);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getManualScale() {
/* 20 */     return 12.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeClient(Consumer<ClientMultiblocks.MultiblockManualData> consumer) {
/* 25 */     consumer.accept(new IPClientMultiblockProperties(this, 2.5D, 0.5D, 3.5D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\OilTankMultiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */