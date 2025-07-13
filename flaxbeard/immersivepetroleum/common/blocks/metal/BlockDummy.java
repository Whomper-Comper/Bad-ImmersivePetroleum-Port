/*    */ package flaxbeard.immersivepetroleum.common.blocks.metal;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.CreativeModeTab;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ import net.minecraft.world.level.material.MaterialColor;
/*    */ import net.minecraft.world.level.material.PushReaction;
/*    */ 
/*    */ public class BlockDummy
/*    */   extends IPBlockBase {
/* 19 */   private static final Material Material = new Material(MaterialColor.f_76404_, false, false, true, true, false, false, PushReaction.BLOCK);
/*    */   
/*    */   public BlockDummy() {
/* 22 */     super(BlockBehaviour.Properties.m_60939_(Material).m_60955_());
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<BlockItem> blockItemSupplier() {
/* 27 */     return () -> new BlockItem((Block)this, new Item.Properties());
/*    */   }
/*    */   
/*    */   public void m_49811_(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {}
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\metal\BlockDummy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */