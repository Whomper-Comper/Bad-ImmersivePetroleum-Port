/*    */ package flaxbeard.immersivepetroleum.client.model;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.client.render.DerrickRenderer;
/*    */ import flaxbeard.immersivepetroleum.client.render.SeismicSurveyBarrelRenderer;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.client.event.ModelEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class IPModels
/*    */ {
/*    */   @SubscribeEvent
/*    */   public static void init(FMLConstructModEvent event) {
/* 25 */     add("pumpjackarm", new ModelPumpjack());
/*    */     
/* 27 */     add("crusher_lubepipes", new ModelLubricantPipes.Crusher());
/*    */     
/* 29 */     add("excavator_lubepipes_normal", new ModelLubricantPipes.Excavator(false));
/* 30 */     add("excavator_lubepipes_mirrored", new ModelLubricantPipes.Excavator(true));
/*    */     
/* 32 */     add("pumpjack_lubepipes_normal", new ModelLubricantPipes.Pumpjack(false));
/* 33 */     add("pumpjack_lubepipes_mirrored", new ModelLubricantPipes.Pumpjack(true));
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void registerDynamicOBJModels(ModelEvent.RegisterAdditional event) {
/* 38 */     event.register(SeismicSurveyBarrelRenderer.BARREL);
/* 39 */     event.register(DerrickRenderer.DRILL);
/* 40 */     event.register(DerrickRenderer.PIPE_SEGMENT);
/* 41 */     event.register(DerrickRenderer.PIPE_TOP);
/*    */   }
/*    */   
/* 44 */   private static final Map<String, IPModel> MODELS = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void add(String id, IPModel model) {
/* 51 */     if (MODELS.containsKey(id)) {
/* 52 */       ImmersivePetroleum.log.error("Duplicate ID, \"{}\" already used by {}. Skipping.", id, ((IPModel)MODELS.get(id)).getClass());
/*    */     } else {
/* 54 */       model.init();
/* 55 */       MODELS.put(id, model);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Supplier<IPModel> getSupplier(String id) {
/* 64 */     return () -> (IPModel)MODELS.get(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Collection<IPModel> getModels() {
/* 71 */     return Collections.unmodifiableCollection(MODELS.values());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\model\IPModels.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */