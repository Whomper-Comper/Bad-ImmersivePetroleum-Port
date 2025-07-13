/*    */ package flaxbeard.immersivepetroleum.common.blocks.interfaces;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ 
/*    */ public interface IHasGUIInteraction<TE extends BlockEntity & IHasGUIInteraction<TE>>
/*    */   extends MenuProvider
/*    */ {
/*    */   @Nullable
/*    */   TE getGuiMaster();
/*    */   
/*    */   IPMenuProvider.BEContainerIP<? super TE, ?> getContainerType();
/*    */   
/*    */   boolean canUseGui(Player paramPlayer);
/*    */   
/*    */   default boolean isValid() {
/* 26 */     return (getGuiMaster() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   default AbstractContainerMenu m_7208_(int id, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
/* 32 */     TE master = getGuiMaster();
/* 33 */     Preconditions.checkNotNull(master);
/* 34 */     IPMenuProvider.BEContainerIP<? super TE, ?> type = getContainerType();
/* 35 */     return (AbstractContainerMenu)type.create(id, playerInventory, (BlockEntity)master);
/*    */   }
/*    */   
/* 38 */   public static final Component NO_DISPLAY_NAME = (Component)Component.m_237113_("");
/*    */   
/*    */   default Component m_5446_() {
/* 41 */     return NO_DISPLAY_NAME;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\interfaces\IHasGUIInteraction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */