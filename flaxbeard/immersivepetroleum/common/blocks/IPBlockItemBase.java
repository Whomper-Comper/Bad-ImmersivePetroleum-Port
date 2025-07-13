/*    */ package flaxbeard.immersivepetroleum.common.blocks;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class IPBlockItemBase
/*    */   extends BlockItem
/*    */ {
/*    */   public IPBlockItemBase(Block blockIn, Item.Properties builder) {
/* 23 */     super(blockIn, builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_7373_(ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 28 */     if (stack.m_41782_()) {
/*    */       
/* 30 */       if (stack.m_41783_().m_128441_("tank")) {
/* 31 */         CompoundTag tank = stack.m_41783_().m_128469_("tank");
/*    */         
/* 33 */         FluidStack fluidstack = FluidStack.loadFluidStackFromNBT(tank);
/* 34 */         if (fluidstack.getAmount() > 0) {
/* 35 */           tooltip.add(((MutableComponent)fluidstack.getDisplayName()).m_130946_(" " + fluidstack.getAmount() + "mB").m_130940_(ChatFormatting.GRAY));
/*    */         } else {
/* 37 */           tooltip.add(Component.m_237115_("gui.immersiveengineering.empty").m_130940_(ChatFormatting.GRAY));
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 42 */       if (stack.m_41783_().m_128441_("energy")) {
/* 43 */         int flux = stack.m_41783_().m_128451_("energy");
/* 44 */         tooltip.add(Component.m_237113_("" + flux + "RF").m_130940_(ChatFormatting.GRAY));
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     super.m_7373_(stack, worldIn, tooltip, flagIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean m_7429_(BlockPlaceContext pContext, BlockState pState) {
/* 53 */     return super.m_7429_(pContext, pState);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\IPBlockItemBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */