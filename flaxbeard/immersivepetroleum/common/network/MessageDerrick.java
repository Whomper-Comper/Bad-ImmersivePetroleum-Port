/*    */ package flaxbeard.immersivepetroleum.common.network;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.client.gui.elements.PipeConfig;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.fml.LogicalSide;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ public class MessageDerrick
/*    */   implements INetMessage {
/*    */   public static void sendToServer(BlockPos derrickPos, PipeConfig.Grid grid) {
/* 20 */     IPPacketHandler.sendToServer(new MessageDerrick(derrickPos, grid));
/*    */   }
/*    */   
/*    */   BlockPos derrickPos;
/*    */   CompoundTag nbt;
/*    */   
/*    */   private MessageDerrick(BlockPos derrick, PipeConfig.Grid grid) {
/* 27 */     this.derrickPos = derrick;
/* 28 */     this.nbt = grid.toCompound();
/*    */   }
/*    */   
/*    */   public MessageDerrick(FriendlyByteBuf buf) {
/* 32 */     this.nbt = buf.m_130260_();
/* 33 */     this.derrickPos = buf.m_130135_();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(FriendlyByteBuf buf) {
/* 38 */     buf.m_130079_(this.nbt);
/* 39 */     buf.m_130064_(this.derrickPos);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(Supplier<NetworkEvent.Context> context) {
/* 45 */     ((NetworkEvent.Context)context.get()).enqueueWork(() -> {
/*    */           NetworkEvent.Context con = context.get();
/*    */           if (con.getDirection().getReceptionSide() == LogicalSide.SERVER) {
/*    */             ServerLevel world = ((ServerPlayer)Objects.<ServerPlayer>requireNonNull(con.getSender())).m_9236_();
/*    */             if (world.isAreaLoaded(this.derrickPos, 2)) {
/*    */               BlockEntity te = world.m_7702_(this.derrickPos);
/*    */               if (te instanceof DerrickTileEntity) {
/*    */                 DerrickTileEntity derrick = (DerrickTileEntity)te;
/*    */                 derrick.gridStorage = PipeConfig.Grid.fromCompound(this.nbt);
/*    */                 derrick.updateMasterBlock(null, true);
/*    */                 WellTileEntity well = derrick.getWell();
/*    */                 derrick.transferGridDataToWell(well);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageDerrick.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */