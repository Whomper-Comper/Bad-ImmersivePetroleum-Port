/*     */ package flaxbeard.immersivepetroleum;
/*     */ 
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.IPRecipeTypes;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.common.CommonEventHandler;
/*     */ import flaxbeard.immersivepetroleum.common.CommonProxy;
/*     */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*     */ import flaxbeard.immersivepetroleum.common.IPToolShaders;
/*     */ import flaxbeard.immersivepetroleum.common.ReservoirRegionDataStorage;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPClientConfig;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.RecipeReloadListener;
/*     */ import flaxbeard.immersivepetroleum.common.network.IPPacketHandler;
/*     */ import flaxbeard.immersivepetroleum.common.util.commands.IslandCommand;
/*     */ import flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked.IPPeripheralProvider;
/*     */ import flaxbeard.immersivepetroleum.common.util.loot.IPLootFunctions;
/*     */ import flaxbeard.immersivepetroleum.common.world.IPWorldGen;
/*     */ import java.lang.invoke.SerializedLambda;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.world.item.CreativeModeTab;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.AddReloadListenerEvent;
/*     */ import net.minecraftforge.event.RegisterCommandsEvent;
/*     */ import net.minecraftforge.event.level.LevelEvent;
/*     */ import net.minecraftforge.event.server.ServerStartingEvent;
/*     */ import net.minecraftforge.eventbus.api.IEventBus;
/*     */ import net.minecraftforge.fml.DistExecutor;
/*     */ import net.minecraftforge.fml.ModList;
/*     */ import net.minecraftforge.fml.ModLoadingContext;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.config.IConfigSpec;
/*     */ import net.minecraftforge.fml.config.ModConfig;
/*     */ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
/*     */ import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
/*     */ import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
/*     */ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*     */ import net.minecraftforge.fml.loading.FMLLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @Mod("immersivepetroleum")
/*     */ public class ImmersivePetroleum {
/*     */   public static final String MODID = "immersivepetroleum";
/*  58 */   public static final Logger log = LogManager.getLogger("immersivepetroleum");
/*     */   
/*  60 */   public static final CreativeModeTab creativeTab = new CreativeModeTab("immersivepetroleum")
/*     */     {
/*     */       @Nonnull
/*     */       public ItemStack m_6976_() {
/*  64 */         return new ItemStack((ItemLike)IPContent.Fluids.CRUDEOIL.bucket().get());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Supplier<T> bootstrapErrorToXCPInDev(Supplier<T> in) {
/*  72 */     if (FMLLoader.isProduction())
/*  73 */       return in; 
/*  74 */     return () -> {
/*     */         try {
/*     */           return in.get();
/*  77 */         } catch (BootstrapMethodError e) {
/*     */           throw new RuntimeException(e);
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*  83 */   public static final CommonProxy proxy = (CommonProxy)DistExecutor.safeRunForDist(bootstrapErrorToXCPInDev(() -> flaxbeard.immersivepetroleum.client.ClientProxy::new), bootstrapErrorToXCPInDev(() -> CommonProxy::new));
/*     */   
/*     */   public ImmersivePetroleum() {
/*  86 */     ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, (IConfigSpec)IPServerConfig.ALL);
/*  87 */     ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)IPClientConfig.ALL);
/*     */     
/*  89 */     FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
/*  90 */     FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
/*     */     
/*  92 */     MinecraftForge.EVENT_BUS.addListener(this::worldLoad);
/*  93 */     MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
/*  94 */     MinecraftForge.EVENT_BUS.addListener(this::registerCommand);
/*  95 */     MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
/*     */     
/*  97 */     IEventBus eBus = FMLJavaModLoadingContext.get().getModEventBus();
/*  98 */     IPRegisters.addRegistersToEventBus(eBus);
/*     */     
/* 100 */     IPContent.modConstruction();
/* 101 */     IPLootFunctions.modConstruction();
/* 102 */     IPRecipeTypes.modConstruction();
/*     */     
/* 104 */     MinecraftForge.EVENT_BUS.register(new IPWorldGen());
/* 105 */     IPWorldGen.init(eBus);
/*     */   }
/*     */   
/*     */   public void setup(FMLCommonSetupEvent event) {
/* 109 */     proxy.setup();
/*     */ 
/*     */ 
/*     */     
/* 113 */     proxy.preInit();
/*     */     
/* 115 */     IPContent.preInit();
/* 116 */     IPPacketHandler.preInit();
/* 117 */     IPToolShaders.preInit();
/*     */     
/* 119 */     proxy.preInitEnd();
/*     */ 
/*     */ 
/*     */     
/* 123 */     IPContent.init((ParallelDispatchEvent)event);
/*     */     
/* 125 */     MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
/*     */     
/* 127 */     proxy.init();
/*     */     
/* 129 */     if (ModList.get().isLoaded("computercraft")) {
/* 130 */       IPPeripheralProvider.init();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 135 */     proxy.postInit();
/*     */     
/* 137 */     ReservoirHandler.recalculateChances();
/* 138 */     ExternalModContent.init();
/*     */     
/* 140 */     proxy.registerContainersAndScreens();
/*     */   }
/*     */   
/*     */   public void loadComplete(FMLLoadCompleteEvent event) {
/* 144 */     proxy.completed((ParallelDispatchEvent)event);
/*     */   }
/*     */   
/*     */   public void registerCommand(RegisterCommandsEvent event) {
/* 148 */     LiteralArgumentBuilder<CommandSourceStack> ip = Commands.m_82127_("ip");
/*     */     
/* 150 */     ip.then((ArgumentBuilder)IslandCommand.create());
/*     */     
/* 152 */     event.getDispatcher().register(ip);
/*     */   }
/*     */   
/*     */   public void addReloadListeners(AddReloadListenerEvent event) {
/* 156 */     event.addListener((PreparableReloadListener)new RecipeReloadListener(event.getServerResources()));
/*     */   }
/*     */   
/*     */   public void worldLoad(LevelEvent.Load event) {
/* 160 */     if (!event.getLevel().m_5776_()) { LevelAccessor levelAccessor = event.getLevel(); if (levelAccessor instanceof ServerLevel) { ServerLevel world = (ServerLevel)levelAccessor; if (world.m_46472_() == Level.f_46428_) {
/* 161 */           ReservoirRegionDataStorage.init(world.m_8895_());
/* 162 */           world.m_8895_().m_164861_(flaxbeard.immersivepetroleum.common.IPSaveData::new, flaxbeard.immersivepetroleum.common.IPSaveData::new, "ImmersivePetroleum-SaveData");
/*     */         }  }
/*     */        }
/*     */   
/*     */   } public void serverStarting(ServerStartingEvent event) {
/* 167 */     ReservoirHandler.recalculateChances();
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\ImmersivePetroleum.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */