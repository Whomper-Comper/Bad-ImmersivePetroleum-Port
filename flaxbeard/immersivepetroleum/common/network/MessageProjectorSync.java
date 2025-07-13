/*    */ package flaxbeard.immersivepetroleum.common.network;
/*    */ import flaxbeard.immersivepetroleum.common.util.projector.Settings;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fml.LogicalSide;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ public class MessageProjectorSync implements INetMessage {
/*    */   boolean forServer;
/*    */   
/*    */   public static void sendToServer(Settings settings, InteractionHand hand) {
/* 18 */     IPPacketHandler.sendToServer(new MessageProjectorSync(settings, hand, true));
/*    */   }
/*    */   CompoundTag nbt; InteractionHand hand;
/*    */   public static void sendToClient(Player player, Settings settings, InteractionHand hand) {
/* 22 */     IPPacketHandler.sendToPlayer(player, new MessageProjectorSync(settings, hand, false));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MessageProjectorSync(Settings settings, InteractionHand hand, boolean toServer) {
/* 30 */     this(settings.toNbt(), hand, toServer);
/*    */   }
/*    */   
/*    */   public MessageProjectorSync(CompoundTag nbt, InteractionHand hand, boolean toServer) {
/* 34 */     this.nbt = nbt;
/* 35 */     this.forServer = toServer;
/* 36 */     this.hand = hand;
/*    */   }
/*    */   
/*    */   public MessageProjectorSync(FriendlyByteBuf buf) {
/* 40 */     this.nbt = buf.m_130260_();
/* 41 */     this.forServer = buf.readBoolean();
/* 42 */     this.hand = InteractionHand.values()[buf.readByte()];
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(FriendlyByteBuf buf) {
/* 47 */     buf.m_130079_(this.nbt);
/* 48 */     buf.writeBoolean(this.forServer);
/* 49 */     buf.writeByte(this.hand.ordinal());
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Supplier<NetworkEvent.Context> context) {
/* 54 */     ((NetworkEvent.Context)context.get()).enqueueWork(() -> {
/*    */           NetworkEvent.Context con = context.get();
/*    */           if (con.getDirection().getReceptionSide() == getSide() && con.getSender() != null) {
/*    */             ServerPlayer serverPlayer = con.getSender();
/*    */             ItemStack held = serverPlayer.m_21120_(this.hand);
/*    */             if (held.m_150930_((Item)IPContent.Items.PROJECTOR.get())) {
/*    */               Settings settings = new Settings(this.nbt);
/*    */               settings.applyTo(held);
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   LogicalSide getSide() {
/* 70 */     return this.forServer ? LogicalSide.SERVER : LogicalSide.CLIENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageProjectorSync.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */