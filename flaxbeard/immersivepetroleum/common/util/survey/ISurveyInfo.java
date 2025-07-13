/*    */ package flaxbeard.immersivepetroleum.common.util.survey;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISurveyInfo
/*    */ {
/*    */   int getX();
/*    */   
/*    */   int getZ();
/*    */   
/*    */   CompoundTag writeToStack(ItemStack paramItemStack);
/*    */   
/*    */   CompoundTag writeToTag(CompoundTag paramCompoundTag);
/*    */   
/*    */   @Nullable
/*    */   static ISurveyInfo from(ItemStack stack) {
/* 21 */     if (stack.m_41782_()) {
/* 22 */       if (stack.m_41783_().m_128425_("islandscan", 10)) {
/* 23 */         return new IslandInfo(stack.m_41737_("islandscan"));
/*    */       }
/*    */       
/* 26 */       if (stack.m_41783_().m_128425_("surveyscan", 10)) {
/* 27 */         return new SurveyScan(stack.m_41737_("surveyscan"));
/*    */       }
/*    */     } 
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\survey\ISurveyInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */