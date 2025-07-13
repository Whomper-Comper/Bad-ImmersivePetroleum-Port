package flaxbeard.immersivepetroleum.common.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface INetMessage {
  void toBytes(FriendlyByteBuf paramFriendlyByteBuf);
  
  void process(Supplier<NetworkEvent.Context> paramSupplier);
}


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\network\INetMessage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */