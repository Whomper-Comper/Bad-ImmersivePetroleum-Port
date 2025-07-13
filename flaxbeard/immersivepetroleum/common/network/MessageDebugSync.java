/*    */ package flaxbeard.immersivepetroleum.common.network;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPContent;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fml.LogicalSide;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ public class MessageDebugSync
/*    */   implements INetMessage {
/*    */   CompoundTag nbt;
/*    */   
/*    */   public MessageDebugSync(CompoundTag nbt) {
/* 17 */     this.nbt = nbt;
/*    */   }
/*    */   
/*    */   public MessageDebugSync(FriendlyByteBuf buf) {
/* 21 */     this.nbt = buf.m_130260_();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(FriendlyByteBuf buf) {
/* 26 */     buf.m_130079_(this.nbt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Supplier<NetworkEvent.Context> context) {
/* 31 */     ((NetworkEvent.Context)context.get()).enqueueWork(() -> {
/*    */           NetworkEvent.Context con = context.get();
/*    */           
/*    */           if (con.getDirection().getReceptionSide() == LogicalSide.SERVER && con.getSender() != null) {
/*    */             ServerPlayer serverPlayer = con.getSender();
/*    */             ItemStack mainItem = serverPlayer.m_21205_();
/*    */             ItemStack secondItem = serverPlayer.m_21206_();
/* 38 */             boolean main = (!mainItem.m_41619_() && mainItem.m_41720_() == IPContent.DEBUGITEM.get());
/* 39 */             boolean off = (!secondItem.m_41619_() && secondItem.m_41720_() == IPContent.DEBUGITEM.get());
/*    */             if (main || off) {
/*    */               ItemStack target = main ? mainItem : secondItem;
/*    */               CompoundTag targetNBT = target.m_41698_("settings");
/*    */               targetNBT.m_128391_(this.nbt);
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageDebugSync.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */