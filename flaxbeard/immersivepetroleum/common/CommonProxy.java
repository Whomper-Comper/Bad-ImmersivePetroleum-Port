/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
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
/*    */ 
/*    */ public class CommonProxy
/*    */ {
/*    */   public void setup() {}
/*    */   
/*    */   public void registerContainersAndScreens() {}
/*    */   
/*    */   public void preInit() {}
/*    */   
/*    */   public void preInitEnd() {}
/*    */   
/*    */   public void init() {}
/*    */   
/*    */   public void postInit() {}
/*    */   
/*    */   public void completed(ParallelDispatchEvent event) {}
/*    */   
/*    */   public void renderTile(BlockEntity te, VertexConsumer iVertexBuilder, PoseStack transform, MultiBufferSource buffer) {}
/*    */   
/*    */   public void handleEntitySound(SoundEvent soundEvent, Entity entity, boolean active, float volume, float pitch) {}
/*    */   
/*    */   public void handleTileSound(SoundEvent soundEvent, BlockEntity te, boolean active, float volume, float pitch) {}
/*    */   
/*    */   public void drawUpperHalfSlab(PoseStack transform, ItemStack stack) {}
/*    */   
/*    */   public Level getClientWorld() {
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public Player getClientPlayer() {
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\CommonProxy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */