package flaxbeard.immersivepetroleum.api.crafting;

import com.mojang.blaze3d.vertex.PoseStack;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ILubricationHandler<E extends BlockEntity> {
  Tuple<BlockPos, Direction> getGhostBlockPosition(Level paramLevel, E paramE);
  
  Vec3i getStructureDimensions();
  
  boolean isMachineEnabled(Level paramLevel, E paramE);
  
  BlockEntity isPlacedCorrectly(Level paramLevel, AutoLubricatorTileEntity paramAutoLubricatorTileEntity, Direction paramDirection);
  
  void lubricateClient(ClientLevel paramClientLevel, Fluid paramFluid, int paramInt, E paramE);
  
  void lubricateServer(ServerLevel paramServerLevel, Fluid paramFluid, int paramInt, E paramE);
  
  void spawnLubricantParticles(ClientLevel paramClientLevel, AutoLubricatorTileEntity paramAutoLubricatorTileEntity, Direction paramDirection, E paramE);
  
  @OnlyIn(Dist.CLIENT)
  void renderPipes(AutoLubricatorTileEntity paramAutoLubricatorTileEntity, E paramE, PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\LubricatedHandler$ILubricationHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */