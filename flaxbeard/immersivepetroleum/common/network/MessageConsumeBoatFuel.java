/*    */ package flaxbeard.immersivepetroleum.common.network;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fml.LogicalSide;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ public class MessageConsumeBoatFuel
/*    */   implements INetMessage {
/*    */   public int amount;
/*    */   
/*    */   public MessageConsumeBoatFuel(int amount) {
/* 16 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   public MessageConsumeBoatFuel(FriendlyByteBuf buf) {
/* 20 */     this.amount = buf.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toBytes(FriendlyByteBuf buf) {
/* 25 */     buf.writeInt(this.amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Supplier<NetworkEvent.Context> context) {
/* 30 */     ((NetworkEvent.Context)context.get()).enqueueWork(() -> {
/*    */           NetworkEvent.Context con = context.get();
/*    */           if (con.getDirection().getReceptionSide() == LogicalSide.SERVER && con.getSender() != null) {
/*    */             Entity entity = con.getSender().m_20202_();
/*    */             if (entity instanceof MotorboatEntity) {
/*    */               MotorboatEntity boat = (MotorboatEntity)entity;
/*    */               FluidStack fluid = boat.getContainedFluid();
/*    */               if (fluid != null && fluid != FluidStack.EMPTY)
/*    */                 fluid.setAmount(Math.max(0, fluid.getAmount() - this.amount)); 
/*    */               boat.setContainedFluid(fluid);
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\MessageConsumeBoatFuel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */