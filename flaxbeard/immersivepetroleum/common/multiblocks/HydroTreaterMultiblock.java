/*    */ package flaxbeard.immersivepetroleum.common.multiblocks;
/*    */ import blusunrize.immersiveengineering.api.multiblocks.ClientMultiblocks;
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class HydroTreaterMultiblock extends IPTemplateMultiblock {
/* 11 */   public static final HydroTreaterMultiblock INSTANCE = new HydroTreaterMultiblock();
/*    */   
/*    */   public HydroTreaterMultiblock() {
/* 14 */     super(ResourceUtils.ip("multiblocks/hydrotreater"), new BlockPos(1, 0, 2), new BlockPos(1, 1, 3), new BlockPos(3, 3, 4), (Supplier<? extends Block>)IPContent.Multiblock.HYDROTREATER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getManualScale() {
/* 21 */     return 12.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeClient(Consumer<ClientMultiblocks.MultiblockManualData> consumer) {
/* 26 */     consumer.accept(new IPClientMultiblockProperties(this, 1.5D, 0.5D, 2.5D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\multiblocks\HydroTreaterMultiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */