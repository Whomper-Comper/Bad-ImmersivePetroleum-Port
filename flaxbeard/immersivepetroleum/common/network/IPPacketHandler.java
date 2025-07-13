/*    */ package flaxbeard.immersivepetroleum.common.network;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ import net.minecraftforge.network.NetworkRegistry;
/*    */ import net.minecraftforge.network.PacketDistributor;
/*    */ import net.minecraftforge.network.simple.SimpleChannel;
/*    */ 
/*    */ public class IPPacketHandler
/*    */ {
/*    */   public static final String NET_VERSION = "1";
/* 20 */   public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(ResourceUtils.ip("main"))
/* 21 */     .networkProtocolVersion(() -> "1")
/* 22 */     .serverAcceptedVersions("1"::equals)
/* 23 */     .clientAcceptedVersions("1"::equals)
/* 24 */     .simpleChannel();
/*    */   
/*    */   public static void preInit() {
/* 27 */     registerMessage(MessageDebugSync.class, MessageDebugSync::new);
/* 28 */     registerMessage(MessageConsumeBoatFuel.class, MessageConsumeBoatFuel::new);
/* 29 */     registerMessage(MessageProjectorSync.class, MessageProjectorSync::new);
/* 30 */     registerMessage(MessageDerrick.class, MessageDerrick::new);
/*    */     
/* 32 */     registerMessage(MessageSurveyResultDetails.ClientToServer.class, ClientToServer::new);
/* 33 */     registerMessage(MessageSurveyResultDetails.ServerToClient.class, ServerToClient::new);
/*    */   }
/*    */   
/* 36 */   private static int id = 0;
/*    */   public static <T extends INetMessage> void registerMessage(Class<T> type, Function<FriendlyByteBuf, T> decoder) {
/* 38 */     INSTANCE.registerMessage(id++, type, INetMessage::toBytes, decoder, (t, ctx) -> {
/*    */           t.process(ctx);
/*    */           ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <MSG> void sendToPlayer(Player player, @Nonnull MSG message) {
/* 51 */     if (message != null && player instanceof ServerPlayer) { ServerPlayer serverPlayer = (ServerPlayer)player;
/* 52 */       INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public static <MSG> void sendToServer(MSG message) {
/* 58 */     if (message == null) {
/*    */       return;
/*    */     }
/* 61 */     INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <MSG> void sendToDimension(ResourceKey<Level> dim, MSG message) {
/* 72 */     if (message == null) {
/*    */       return;
/*    */     }
/* 75 */     INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dim), message);
/*    */   }
/*    */   
/*    */   public static <MSG> void sendAll(MSG message) {
/* 79 */     if (message == null) {
/*    */       return;
/*    */     }
/* 82 */     INSTANCE.send(PacketDistributor.ALL.noArg(), message);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\IPPacketHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */