/*    */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.SoundType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ import net.minecraft.world.level.material.MaterialColor;
/*    */ 
/*    */ public class ParaffinWaxBlock
/*    */   extends IPBlockBase {
/*    */   public ParaffinWaxBlock() {
/* 29 */     super(BlockBehaviour.Properties.m_60944_(Material.f_76316_, MaterialColor.f_76416_).m_60913_(0.5F, 0.4F).m_60918_(SoundType.f_56751_).m_60956_(0.95F).m_60911_(1.05F));
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_5871_(@Nonnull ItemStack stack, BlockGetter worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 34 */     tooltip(stack, worldIn, tooltip, flagIn);
/* 35 */     super.m_5871_(stack, worldIn, tooltip, flagIn);
/*    */   }
/*    */   
/*    */   static void tooltip(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 39 */     tooltip.add(Component.m_237115_("desc.immersivepetroleum.flavour.paraffin_wax").m_130940_(ChatFormatting.GRAY));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
/* 44 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<BlockItem> blockItemSupplier() {
/* 49 */     return () -> new IPBlockItemBase((Block)this, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab))
/*    */       {
/*    */         public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 52 */           return 8000;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\ParaffinWaxBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */