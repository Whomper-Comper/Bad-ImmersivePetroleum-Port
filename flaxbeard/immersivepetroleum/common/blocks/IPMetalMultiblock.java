/*    */ package flaxbeard.immersivepetroleum.common.blocks;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.MultiblockBEType;
/*    */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*    */ import blusunrize.immersiveengineering.common.blocks.metal.MetalMultiblockBlock;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.ICanSkipGUI;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.interfaces.IHasGUIInteraction;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.SoundType;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraftforge.network.NetworkHooks;
/*    */ 
/*    */ public class IPMetalMultiblock<T extends MultiblockPartBlockEntity<T> & IPCommonTickableTile>
/*    */   extends MetalMultiblockBlock<T> {
/*    */   private final MultiblockBEType<T> multiblockBEType;
/*    */   
/*    */   public IPMetalMultiblock(MultiblockBEType<T> te) {
/* 34 */     super(te, BlockBehaviour.Properties.m_60939_(Material.f_76279_)
/* 35 */         .m_60918_(SoundType.f_56743_)
/* 36 */         .m_60913_(3.0F, 15.0F)
/* 37 */         .m_60999_()
/* 38 */         .m_60971_((state, blockReader, pos) -> false)
/* 39 */         .m_60955_()
/* 40 */         .m_60988_());
/*    */     
/* 42 */     this.multiblockBEType = te;
/*    */   }
/*    */ 
/*    */   
/*    */   public <E extends BlockEntity> BlockEntityTicker<E> m_142354_(@Nonnull Level world, @Nonnull BlockState state, @Nonnull BlockEntityType<E> type) {
/* 47 */     return (BlockEntityTicker)IPBlockBase.createCommonTicker(world.f_46443_, type, this.multiblockBEType.master());
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
/* 52 */     if (hand == InteractionHand.MAIN_HAND) {
/* 53 */       BlockEntity te = world.m_7702_(pos);
/*    */       
/* 55 */       if (te instanceof MenuProvider) { MenuProvider menuProvider = (MenuProvider)te;
/* 56 */         if (te instanceof ICanSkipGUI) { ICanSkipGUI skippable = (ICanSkipGUI)te; if (!player.m_21120_(hand).m_41619_() && skippable.skipGui(hit.m_82434_())) {
/* 57 */             return InteractionResult.FAIL;
/*    */           } }
/*    */         
/* 60 */         if (!player.m_6144_()) {
/* 61 */           if (player instanceof ServerPlayer) { ServerPlayer serverPlayer = (ServerPlayer)player;
/* 62 */             if (menuProvider instanceof IHasGUIInteraction) { IHasGUIInteraction<?> interaction = (IHasGUIInteraction)menuProvider;
/* 63 */               interaction = (IHasGUIInteraction)interaction.getGuiMaster();
/* 64 */               if (interaction != null && interaction.canUseGui(player)) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */                 
/* 70 */                 AbstractContainerMenu tempMenu = interaction.m_7208_(0, player.m_150109_(), player);
/* 71 */                 if (tempMenu instanceof blusunrize.immersiveengineering.common.gui.IEBaseContainerOld) {
/* 72 */                   NetworkHooks.openScreen(serverPlayer, (MenuProvider)interaction, ((BlockEntity)interaction).m_58899_());
/*    */                 } else {
/* 74 */                   NetworkHooks.openScreen(serverPlayer, (MenuProvider)interaction);
/*    */                 } 
/*    */               }  }
/*    */              }
/*    */           
/* 79 */           return InteractionResult.SUCCESS;
/*    */         }  }
/*    */     
/*    */     } 
/* 83 */     return super.m_6227_(state, world, pos, player, hand, hit);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\IPMetalMultiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */