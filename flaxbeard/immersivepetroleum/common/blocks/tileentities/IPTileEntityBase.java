/*    */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class IPTileEntityBase
/*    */   extends BlockEntity {
/*    */   public IPTileEntityBase(BlockEntityType<?> blockEntityType, BlockPos pWorldPosition, BlockState pBlockState) {
/* 18 */     super(blockEntityType, pWorldPosition, pBlockState);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Level getWorldNonnull() {
/* 23 */     return Objects.<Level>requireNonNull(m_58904_());
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 28 */     return ClientboundBlockEntityDataPacket.m_195642_(this, b -> m_5995_());
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleUpdateTag(CompoundTag tag) {
/* 33 */     m_142466_(tag);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompoundTag m_5995_() {
/* 39 */     CompoundTag nbt = new CompoundTag();
/* 40 */     m_183515_(nbt);
/* 41 */     return nbt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
/* 46 */     if (pkt.m_131708_() != null) {
/* 47 */       m_142466_(pkt.m_131708_());
/*    */     }
/*    */   }
/*    */   
/*    */   public void m_183515_(@Nonnull CompoundTag compound) {
/* 52 */     writeCustom(compound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_142466_(@Nonnull CompoundTag compound) {
/* 57 */     super.m_142466_(compound);
/* 58 */     readCustom(compound);
/*    */   }
/*    */   
/*    */   protected abstract void writeCustom(CompoundTag paramCompoundTag);
/*    */   
/*    */   protected abstract void readCustom(CompoundTag paramCompoundTag);
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\IPTileEntityBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */