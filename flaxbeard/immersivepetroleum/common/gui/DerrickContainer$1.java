/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Slot
/*    */ {
/*    */   null(Container arg0, int arg1, int arg2, int arg3) {
/* 17 */     super(arg0, arg1, arg2, arg3);
/*    */   }
/*    */   public boolean m_5857_(@Nonnull ItemStack stack) {
/* 20 */     return ExternalModContent.isIEItem_Pipe(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\DerrickContainer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */