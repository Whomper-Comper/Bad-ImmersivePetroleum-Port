/*     */ package flaxbeard.immersivepetroleum.common.network;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.client.gui.SeismicSurveyScreen;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import java.util.BitSet;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.fml.DistExecutor;
/*     */ import net.minecraftforge.network.NetworkEvent;
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
/*     */ public class ServerToClient
/*     */   implements INetMessage
/*     */ {
/*     */   private BitSet replyBitSet;
/*     */   private UUID scanId;
/*     */   
/*     */   public ServerToClient(UUID scanId, BitSet replyBitSet) {
/* 115 */     this.scanId = scanId;
/* 116 */     this.replyBitSet = replyBitSet;
/*     */   }
/*     */   
/*     */   public ServerToClient(FriendlyByteBuf buf) {
/* 120 */     this.scanId = buf.m_130259_();
/* 121 */     this.replyBitSet = buf.m_178384_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void toBytes(FriendlyByteBuf buf) {
/* 126 */     buf.m_130077_(this.scanId);
/* 127 */     buf.m_178350_(this.replyBitSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(Supplier<NetworkEvent.Context> context) {
/* 132 */     DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ());
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageSurveyResultDetails$ServerToClient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */