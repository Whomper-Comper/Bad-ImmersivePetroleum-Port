/*    */ package flaxbeard.immersivepetroleum.client;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*    */ import flaxbeard.immersivepetroleum.common.entity.IPEntityTypes;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*    */ import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
/*    */ import net.minecraft.client.renderer.entity.EntityRendererProvider;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.client.event.EntityRenderersEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
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
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class ClientModBusEventHandlers
/*    */ {
/*    */   @SubscribeEvent
/*    */   public static void registerRenders(EntityRenderersEvent.RegisterRenderers ev) {
/* 33 */     registerBERender(ev, IPTileTypes.TOWER.master(), flaxbeard.immersivepetroleum.client.render.MultiblockDistillationTowerRenderer::new);
/* 34 */     registerBERender(ev, IPTileTypes.PUMP.master(), flaxbeard.immersivepetroleum.client.render.MultiblockPumpjackRenderer::new);
/* 35 */     registerBERender(ev, IPTileTypes.OILTANK.master(), flaxbeard.immersivepetroleum.client.render.OilTankRenderer::new);
/* 36 */     registerBERender(ev, IPTileTypes.DERRICK.master(), flaxbeard.immersivepetroleum.client.render.DerrickRenderer::new);
/*    */     
/* 38 */     registerBERender(ev, (BlockEntityType<BlockEntity>)IPTileTypes.AUTOLUBE.get(), flaxbeard.immersivepetroleum.client.render.AutoLubricatorRenderer::new);
/* 39 */     registerBERender(ev, (BlockEntityType<BlockEntity>)IPTileTypes.SEISMIC_SURVEY.get(), flaxbeard.immersivepetroleum.client.render.SeismicSurveyBarrelRenderer::new);
/*    */     
/* 41 */     registerEntityRenderingHandler(ev, (Supplier<EntityType<Entity>>)IPEntityTypes.MOTORBOAT, flaxbeard.immersivepetroleum.client.render.MotorboatRenderer::new);
/* 42 */     registerEntityRenderingHandler(ev, (Supplier<EntityType<Entity>>)IPEntityTypes.MOLOTOV, net.minecraft.client.renderer.entity.ThrownItemRenderer::new);
/*    */   }
/*    */   
/*    */   private static <T extends BlockEntity> void registerBERender(EntityRenderersEvent.RegisterRenderers ev, BlockEntityType<T> type, Supplier<BlockEntityRenderer<T>> factory) {
/* 46 */     ev.registerBlockEntityRenderer(type, ctx -> (BlockEntityRenderer)factory.get());
/*    */   }
/*    */   
/*    */   private static <T extends Entity, T2 extends T> void registerEntityRenderingHandler(EntityRenderersEvent.RegisterRenderers ev, Supplier<EntityType<T2>> type, EntityRendererProvider<T> renderer) {
/* 50 */     ev.registerEntityRenderer(type.get(), renderer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\ClientModBusEventHandlers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */