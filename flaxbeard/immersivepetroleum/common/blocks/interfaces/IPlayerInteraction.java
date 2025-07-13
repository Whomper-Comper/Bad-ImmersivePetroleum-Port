package flaxbeard.immersivepetroleum.common.blocks.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IPlayerInteraction {
  InteractionResult interact(Direction paramDirection, Player paramPlayer, InteractionHand paramInteractionHand, ItemStack paramItemStack, float paramFloat1, float paramFloat2, float paramFloat3);
}


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\interfaces\IPlayerInteraction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */