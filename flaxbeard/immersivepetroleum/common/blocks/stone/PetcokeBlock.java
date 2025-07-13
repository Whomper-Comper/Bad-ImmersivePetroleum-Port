/*    */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.SoundType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ 
/*    */ public class PetcokeBlock extends IPBlockBase {
/*    */   public PetcokeBlock() {
/* 22 */     super(BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60918_(SoundType.f_56742_).m_60913_(2.0F, 10.0F).m_60999_());
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<BlockItem> blockItemSupplier() {
/* 27 */     return () -> new IPBlockItemBase((Block)this, (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab))
/*    */       {
/*    */         public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 30 */           return 32000;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
/* 37 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\PetcokeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */