/*     */ package flaxbeard.immersivepetroleum.client.particle;
/*     */ 
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.LogicalSide;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*     */ public class Wind
/*     */ {
/*  96 */   private static Vector3f vec = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */   private static long lastGT;
/*     */   private static float lastDirection;
/*     */   private static float thisDirection;
/*     */   
/*     */   public static Vector3f getDirection() {
/* 102 */     return vec;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void clientTick(TickEvent.ClientTickEvent event) {
/* 107 */     if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.START) {
/* 108 */       ClientLevel world = MCUtil.getLevel();
/* 109 */       if (world == null) {
/*     */         return;
/*     */       }
/* 112 */       long gameTime = world.m_46467_();
/* 113 */       if (gameTime / 20L != lastGT) {
/* 114 */         lastGT = gameTime / 20L;
/*     */         
/* 116 */         double fGameTime = gameTime / 20.0D;
/* 117 */         Random lastRand = new Random(Mth.m_14107_(fGameTime));
/* 118 */         Random thisRand = new Random(Mth.m_14165_(fGameTime));
/*     */         
/* 120 */         lastDirection = lastRand.nextFloat() * 360.0F;
/* 121 */         thisDirection = thisRand.nextFloat() * 360.0F;
/*     */       } 
/*     */       
/* 124 */       double interpDirection = Mth.m_14179_((float)(gameTime % 20L) / 20.0F, lastDirection, thisDirection);
/*     */       
/* 126 */       float xSpeed = (float)Math.sin(interpDirection) * 0.1F;
/* 127 */       float zSpeed = (float)Math.cos(interpDirection) * 0.1F;
/*     */       
/* 129 */       vec = new Vector3f(xSpeed, 0.0F, zSpeed);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\particle\FlareFire$Wind.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */