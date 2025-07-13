/*    */ package flaxbeard.immersivepetroleum.common.util.commands;
/*    */ 
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class CommandUtils {
/*    */   static void sendHelp(CommandSourceStack source, String subIdent) {
/*  9 */     sendTranslated(source, "chat.immersivepetroleum.command.reservoir" + subIdent + ".help", new Object[0]);
/*    */   }
/*    */   
/*    */   static void sendString(CommandSourceStack source, String str) {
/* 13 */     source.m_81354_((Component)Component.m_237113_(str), true);
/*    */   }
/*    */   
/*    */   static void sendStringError(CommandSourceStack source, String str) {
/* 17 */     source.m_81354_((Component)Component.m_237113_(str).m_130940_(ChatFormatting.RED), true);
/*    */   }
/*    */   
/*    */   static void sendTranslated(CommandSourceStack source, String translationKey, Object... args) {
/* 21 */     source.m_81354_((Component)Component.m_237110_(translationKey, args), true);
/*    */   }
/*    */   
/*    */   static void sendTranslatedError(CommandSourceStack source, String translationKey, Object... args) {
/* 25 */     source.m_81354_((Component)Component.m_237110_(translationKey, args).m_130940_(ChatFormatting.RED), true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\commands\CommandUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */