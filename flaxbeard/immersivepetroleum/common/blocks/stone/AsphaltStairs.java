/*    */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockStairs;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ 
/*    */ public class AsphaltStairs
/*    */   extends IPBlockStairs<AsphaltBlock>
/*    */ {
/*    */   public AsphaltStairs(AsphaltBlock base) {
/* 15 */     super(base);
/*    */   }
/*    */ 
/*    */   
/*    */   public float m_49961_() {
/* 20 */     return AsphaltBlock.speedFactor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_5871_(@Nonnull ItemStack stack, BlockGetter worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 25 */     AsphaltBlock.tooltip(stack, worldIn, tooltip, flagIn);
/* 26 */     super.m_5871_(stack, worldIn, tooltip, flagIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\AsphaltStairs.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */