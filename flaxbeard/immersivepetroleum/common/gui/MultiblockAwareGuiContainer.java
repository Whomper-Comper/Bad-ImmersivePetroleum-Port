/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
/*    */ import blusunrize.immersiveengineering.common.gui.IEBaseContainerOld;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultiblockAwareGuiContainer<T extends MultiblockPartBlockEntity<T>>
/*    */   extends IEBaseContainerOld<T>
/*    */ {
/* 23 */   static final Vec3i ONE = new Vec3i(1, 1, 1);
/*    */   protected BlockPos templateSize;
/*    */   
/*    */   public MultiblockAwareGuiContainer(MenuType<?> type, T tile, int id, IETemplateMultiblock template) {
/* 27 */     super(type, (BlockEntity)tile, id);
/*    */     
/* 29 */     this.templateSize = (new BlockPos(template.getSize(getTile().getLevelNonnull()))).m_121996_(ONE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Container getInv() {
/* 35 */     return this.inv;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public T getTile() {
/* 41 */     return (T)this.tile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxDistance() {
/* 48 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean m_6875_(@Nonnull Player player) {
/* 53 */     if (getInv() != null) {
/* 54 */       BlockPos min = getTile().getBlockPosForPos(BlockPos.f_121853_);
/* 55 */       BlockPos max = getTile().getBlockPosForPos(this.templateSize);
/*    */       
/* 57 */       AABB box = (new AABB(min, max)).m_82400_(getMaxDistance());
/*    */       
/* 59 */       return box.m_82381_(player.m_20191_());
/*    */     } 
/*    */     
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   protected final void addPlayerInventorySlots(Inventory playerInventory, int x, int y) {
/* 66 */     for (int i = 0; i < 3; i++) {
/* 67 */       for (int j = 0; j < 9; j++) {
/* 68 */         m_38897_(new Slot((Container)playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected final void addPlayerHotbarSlots(Inventory playerInventory, int x, int y) {
/* 74 */     for (int i = 0; i < 9; i++)
/* 75 */       m_38897_(new Slot((Container)playerInventory, i, x + i * 18, y)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\MultiblockAwareGuiContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */