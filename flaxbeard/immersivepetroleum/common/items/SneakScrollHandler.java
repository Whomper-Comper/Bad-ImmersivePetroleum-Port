/*    */ package flaxbeard.immersivepetroleum.common.items;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import net.minecraftforge.client.event.InputEvent;
/*    */ import net.minecraftforge.event.TickEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.LogicalSide;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ @OnlyIn(Dist.CLIENT)
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*    */ public class SneakScrollHandler
/*    */ {
/*    */   private static boolean sneaking = false;
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 23 */     if (event.side == LogicalSide.CLIENT && event.player != null && event.player == Minecraft.m_91087_().m_91288_() && 
/* 24 */       event.phase == TickEvent.Phase.END) {
/* 25 */       sneaking = event.player.m_6144_();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void handleScroll(InputEvent.MouseScrollingEvent event) {
/* 32 */     double delta = event.getScrollDelta();
/*    */     
/* 34 */     if (sneaking && delta != 0.0D) {
/* 35 */       LocalPlayer localPlayer = MCUtil.getPlayer();
/*    */       
/* 37 */       DebugItem.ClientInputHandler.onSneakScrolling(event, (Player)localPlayer, delta, sneaking);
/* 38 */       ProjectorItem.ClientInputHandler.onSneakScrolling(event, (Player)localPlayer, delta, sneaking);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\SneakScrollHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */