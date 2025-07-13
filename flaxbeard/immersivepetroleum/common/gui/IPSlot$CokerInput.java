/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CokerInput
/*    */   extends IPSlot
/*    */ {
/*    */   public CokerInput(AbstractContainerMenu container, Container inv, int id, int x, int y) {
/* 50 */     super(inv, id, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_5857_(ItemStack stack) {
/* 55 */     return (!stack.m_41619_() && CokerUnitRecipe.hasRecipeWithInput(stack, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\IPSlot$CokerInput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */