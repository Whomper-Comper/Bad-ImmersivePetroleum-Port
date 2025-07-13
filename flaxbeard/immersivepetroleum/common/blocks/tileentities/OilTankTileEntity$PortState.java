/*    */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.StringRepresentable;
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
/*    */ public enum PortState
/*    */   implements StringRepresentable
/*    */ {
/* 60 */   INPUT, OUTPUT;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String m_7912_() {
/* 65 */     return toString().toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   public Component getText() {
/* 69 */     return (Component)Component.m_237115_("desc.immersivepetroleum.info.oiltank." + m_7912_());
/*    */   }
/*    */   
/*    */   public PortState next() {
/* 73 */     return (this == INPUT) ? OUTPUT : INPUT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\OilTankTileEntity$PortState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */