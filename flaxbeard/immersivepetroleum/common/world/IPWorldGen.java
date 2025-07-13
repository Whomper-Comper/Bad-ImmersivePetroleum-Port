/*    */ package flaxbeard.immersivepetroleum.common.world;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.data.BuiltinRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraftforge.eventbus.api.IEventBus;
/*    */ import net.minecraftforge.registries.DeferredRegister;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ public class IPWorldGen
/*    */ {
/* 22 */   public static Map<String, Holder<PlacedFeature>> features = new HashMap<>();
/*    */   
/* 24 */   private static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, "immersivepetroleum");
/*    */   
/* 26 */   private static final RegistryObject<FeatureReservoir> RESERVOIR_FEATURE = FEATURE_REGISTER.register("reservoir", FeatureReservoir::new);
/*    */   
/*    */   public static void init(IEventBus eBus) {
/* 29 */     FEATURE_REGISTER.register(eBus);
/*    */   }
/*    */   
/*    */   public static void registerReservoirGen() {
/* 33 */     Holder<PlacedFeature> reservoirFeature = register(RESERVOIR_FEATURE.getId(), RESERVOIR_FEATURE, new NoneFeatureConfiguration());
/* 34 */     features.put(RESERVOIR_FEATURE.getId().m_135815_(), reservoirFeature);
/*    */   }
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
/*    */   private static <Cfg extends FeatureConfiguration, F extends Feature<Cfg>> Holder<PlacedFeature> register(ResourceLocation rl, RegistryObject<F> feature, Cfg cfg) {
/* 48 */     Holder<ConfiguredFeature<?, ?>> configured = BuiltinRegistries.m_206388_(BuiltinRegistries.f_123861_, rl, new ConfiguredFeature((Feature)feature.get(), (FeatureConfiguration)cfg));
/* 49 */     return BuiltinRegistries.m_206388_(BuiltinRegistries.f_194653_, rl, new PlacedFeature(configured, List.of()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\world\IPWorldGen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */