/*    */ package flaxbeard.immersivepetroleum.client;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.fluids.IPFluid;
/*    */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
/*    */ 
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class BlockRenderLayers
/*    */ {
/*    */   @SubscribeEvent
/*    */   public static void clientSetup(FMLClientSetupEvent event) {
/* 18 */     for (IPFluid.IPFluidEntry f : IPFluid.FLUIDS) {
/* 19 */       setRenderLayer(f, RenderType.m_110466_());
/*    */     }
/*    */   }
/*    */   
/*    */   private static void setRenderLayer(IPFluid.IPFluidEntry entry, RenderType types) {
/* 24 */     ItemBlockRenderTypes.setRenderLayer((Fluid)entry.source().get(), types);
/* 25 */     ItemBlockRenderTypes.setRenderLayer((Fluid)entry.flowing().get(), types);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\BlockRenderLayers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */