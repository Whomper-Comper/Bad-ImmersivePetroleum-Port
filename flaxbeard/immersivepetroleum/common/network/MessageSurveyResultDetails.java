/*     */ package flaxbeard.immersivepetroleum.common.network;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.client.gui.SeismicSurveyScreen;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.fml.DistExecutor;
/*     */ import net.minecraftforge.network.NetworkEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageSurveyResultDetails
/*     */ {
/*     */   public static void sendRequestToServer(SurveyScan scan) {
/*  34 */     IPPacketHandler.sendToServer(new ClientToServer(scan));
/*     */   }
/*     */   
/*     */   private static void sendReply(Player player, UUID scanId, BitSet replyBitSet) {
/*  38 */     IPPacketHandler.sendToPlayer(player, new ServerToClient(scanId, replyBitSet));
/*     */   }
/*     */   
/*     */   public static class ClientToServer implements INetMessage {
/*     */     private int x;
/*     */     
/*     */     public ClientToServer(SurveyScan scan) {
/*  45 */       this.x = scan.getX();
/*  46 */       this.z = scan.getZ();
/*  47 */       this.scanId = scan.getUuid();
/*     */     }
/*     */     private int z; private UUID scanId;
/*     */     public ClientToServer(FriendlyByteBuf buf) {
/*  51 */       this.x = buf.readInt();
/*  52 */       this.z = buf.readInt();
/*  53 */       this.scanId = buf.m_130259_();
/*     */     }
/*     */ 
/*     */     
/*     */     public void toBytes(FriendlyByteBuf buf) {
/*  58 */       buf.writeInt(this.x);
/*  59 */       buf.writeInt(this.z);
/*  60 */       buf.m_130077_(this.scanId);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void process(Supplier<NetworkEvent.Context> context) {
/*  66 */       ((NetworkEvent.Context)context.get()).enqueueWork(() -> {
/*     */             ServerPlayer sPlayer = Objects.<ServerPlayer>requireNonNull(((NetworkEvent.Context)context.get()).getSender());
/*     */             ServerLevel sLevel = sPlayer.m_9236_();
/*     */             if (sLevel.isAreaLoaded(new BlockPos(this.x, 0, this.z), 32)) {
/*     */               BitSet set = compileBitSet((Level)sLevel);
/*     */               MessageSurveyResultDetails.sendReply((Player)sPlayer, this.scanId, set);
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     private BitSet compileBitSet(Level level) {
/*  78 */       List<ReservoirIsland> islandCache = new ArrayList<>();
/*  79 */       BitSet set = new BitSet(4225);
/*  80 */       int r = 32;
/*  81 */       for (int j = -32, a = 0; j <= 32; j++, a++) {
/*  82 */         for (int i = -32, b = 0; i <= 32; i++, b++) {
/*  83 */           int x = this.x - i;
/*  84 */           int z = this.z - j;
/*     */           
/*  86 */           double current = ReservoirHandler.getValueOf(level, x, z);
/*  87 */           if (current != -1.0D) {
/*     */ 
/*     */             
/*  90 */             Optional<ReservoirIsland> optional = islandCache.stream().filter(res -> res.contains(x, z)).findFirst();
/*     */             
/*  92 */             ReservoirIsland nearbyIsland = optional.isPresent() ? optional.get() : null;
/*  93 */             if (nearbyIsland == null) {
/*  94 */               nearbyIsland = ReservoirHandler.getIslandNoCache(level, new ColumnPos(x, z));
/*     */               
/*  96 */               if (nearbyIsland != null) {
/*  97 */                 islandCache.add(nearbyIsland);
/*     */               }
/*     */             } 
/*     */             
/* 101 */             if (nearbyIsland != null) {
/* 102 */               set.set(a * 65 + b);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 107 */       return set;
/*     */     } }
/*     */   
/*     */   public static class ServerToClient implements INetMessage {
/*     */     private BitSet replyBitSet;
/*     */     private UUID scanId;
/*     */     
/*     */     public ServerToClient(UUID scanId, BitSet replyBitSet) {
/* 115 */       this.scanId = scanId;
/* 116 */       this.replyBitSet = replyBitSet;
/*     */     }
/*     */     
/*     */     public ServerToClient(FriendlyByteBuf buf) {
/* 120 */       this.scanId = buf.m_130259_();
/* 121 */       this.replyBitSet = buf.m_178384_();
/*     */     }
/*     */ 
/*     */     
/*     */     public void toBytes(FriendlyByteBuf buf) {
/* 126 */       buf.m_130077_(this.scanId);
/* 127 */       buf.m_178350_(this.replyBitSet);
/*     */     }
/*     */ 
/*     */     
/*     */     public void process(Supplier<NetworkEvent.Context> context) {
/* 132 */       DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageSurveyResultDetails.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */