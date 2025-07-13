/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ import net.minecraftforge.common.util.LazyOptional;
/*    */ import net.minecraftforge.items.IItemHandler;
/*    */ import net.minecraftforge.items.ItemStackHandler;
/*    */ 
/*    */ public class IPItemStackHandler extends ItemStackHandler implements ICapabilityProvider {
/*    */   private static final Runnable EMPTY_RUN = () -> {
/*    */     
/*    */     };
/*    */   @Nonnull
/* 21 */   private Runnable onChange = EMPTY_RUN;
/*    */   LazyOptional<IItemHandler> handler;
/*    */   
/* 24 */   public IPItemStackHandler(int invSize) { super(invSize);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.handler = LazyOptional.of(() -> this); }
/*    */   public void setTile(BlockEntity tile) {
/*    */     Objects.requireNonNull(tile);
/*    */     this.onChange = (tile != null) ? tile::m_6596_ : EMPTY_RUN;
/*    */   } @Nonnull
/* 45 */   public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) { if (capability == ForgeCapabilities.ITEM_HANDLER) {
/* 46 */       return this.handler.cast();
/*    */     }
/*    */     
/* 49 */     return LazyOptional.empty(); }
/*    */   public void setInventoryForUpdate(Container inv) { Objects.requireNonNull(inv);
/*    */     this.onChange = (inv != null) ? inv::m_6596_ : EMPTY_RUN; }
/*    */   protected void onContentsChanged(int slot) { super.onContentsChanged(slot);
/* 53 */     this.onChange.run(); } public NonNullList<ItemStack> getContainedItems() { return this.stacks; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\IPItemStackHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */