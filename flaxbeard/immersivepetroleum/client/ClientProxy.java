/*     */ package flaxbeard.immersivepetroleum.client;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.ManualHelper;
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.MetalScaffoldingType;
/*     */ import blusunrize.immersiveengineering.common.register.IEBlocks;
/*     */ import blusunrize.lib.manual.ManualElementItem;
/*     */ import blusunrize.lib.manual.ManualElementTable;
/*     */ import blusunrize.lib.manual.ManualEntry;
/*     */ import blusunrize.lib.manual.ManualInstance;
/*     */ import blusunrize.lib.manual.SpecialManualElement;
/*     */ import com.electronwill.nightconfig.core.Config;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.FlarestackHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*     */ import flaxbeard.immersivepetroleum.client.particle.FluidParticleData;
/*     */ import flaxbeard.immersivepetroleum.client.render.SeismicResultRenderer;
/*     */ import flaxbeard.immersivepetroleum.client.render.debugging.DebugRenderHandler;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.CommonProxy;
/*     */ import flaxbeard.immersivepetroleum.common.IPMenuTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.RecipeReloadListener;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.MenuScreens;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.client.model.data.ModelData;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientProxy
/*     */   extends CommonProxy
/*     */ {
/*     */   public void setup() {}
/*     */   
/*     */   public void registerContainersAndScreens() {
/*  82 */     MenuScreens.m_96206_(IPMenuTypes.DISTILLATION_TOWER.getType(), flaxbeard.immersivepetroleum.client.gui.DistillationTowerScreen::new);
/*  83 */     MenuScreens.m_96206_(IPMenuTypes.COKER.getType(), flaxbeard.immersivepetroleum.client.gui.CokerUnitScreen::new);
/*  84 */     MenuScreens.m_96206_(IPMenuTypes.DERRICK.getType(), flaxbeard.immersivepetroleum.client.gui.DerrickScreen::new);
/*  85 */     MenuScreens.m_96206_(IPMenuTypes.HYDROTREATER.getType(), flaxbeard.immersivepetroleum.client.gui.HydrotreaterScreen::new);
/*     */   }
/*     */ 
/*     */   
/*     */   public void completed(ParallelDispatchEvent event) {
/*  90 */     event.enqueueWork(() -> ManualHelper.addConfigGetter(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     setupManualPages();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void preInitEnd() {}
/*     */ 
/*     */   
/*     */   public void init() {
/* 137 */     MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
/* 138 */     MinecraftForge.EVENT_BUS.register(new RecipeReloadListener(null));
/*     */     
/* 140 */     MinecraftForge.EVENT_BUS.register(new DebugRenderHandler());
/* 141 */     MinecraftForge.EVENT_BUS.register(new SeismicResultRenderer());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTile(BlockEntity te, VertexConsumer iVertexBuilder, PoseStack transform, MultiBufferSource buffer) {
/* 146 */     BlockEntityRenderer<BlockEntity> tesr = Minecraft.m_91087_().m_167982_().m_112265_(te);
/*     */ 
/*     */     
/* 149 */     if (tesr == null) {
/*     */       return;
/*     */     }
/* 152 */     if (te instanceof PumpjackTileEntity) { PumpjackTileEntity pumpjack = (PumpjackTileEntity)te;
/* 153 */       transform.m_85836_();
/* 154 */       transform.m_85845_(new Quaternion(0.0F, -90.0F, 0.0F, true));
/* 155 */       transform.m_85837_(1.0D, 1.0D, -2.0D);
/*     */       
/* 157 */       float pt = 0.0F;
/* 158 */       if (MCUtil.getPlayer() != null) {
/* 159 */         pumpjack.activeTicks = (MCUtil.getPlayer()).f_19797_;
/* 160 */         pt = Minecraft.m_91087_().m_91296_();
/*     */       } 
/*     */       
/* 163 */       tesr.m_6922_((BlockEntity)pumpjack, pt, transform, buffer, 15728880, OverlayTexture.f_118083_);
/* 164 */       transform.m_85849_(); }
/*     */     else
/* 166 */     { transform.m_85836_();
/* 167 */       transform.m_85845_(new Quaternion(0.0F, -90.0F, 0.0F, true));
/* 168 */       transform.m_85837_(0.0D, 1.0D, -4.0D);
/*     */       
/* 170 */       tesr.m_6922_(te, 0.0F, transform, buffer, 15728880, OverlayTexture.f_118083_);
/* 171 */       transform.m_85849_(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawUpperHalfSlab(PoseStack transform, ItemStack stack) {
/* 179 */     BlockRenderDispatcher blockRenderer = Minecraft.m_91087_().m_91289_();
/* 180 */     BlockState state = ((IEBlocks.BlockEntry)IEBlocks.MetalDecoration.STEEL_SCAFFOLDING.get(MetalScaffoldingType.STANDARD)).defaultBlockState();
/* 181 */     BakedModel model = blockRenderer.m_110907_().m_110893_(state);
/*     */     
/* 183 */     MultiBufferSource.BufferSource buffers = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */     
/* 185 */     transform.m_85836_();
/* 186 */     transform.m_85837_(0.0D, 0.5D, 1.0D);
/* 187 */     blockRenderer.m_110937_().renderModel(transform.m_85850_(), buffers.m_6299_(RenderType.m_110451_()), state, model, 1.0F, 1.0F, 1.0F, -1, -1, ModelData.EMPTY, RenderType.m_110463_());
/* 188 */     transform.m_85849_();
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public static void spawnSpillParticles(Level world, BlockPos pos, Fluid fluid, int particles, float yOffset, float flow) {
/* 193 */     if (fluid == null || fluid == Fluids.f_76191_) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     for (int i = 0; i < particles; i++) {
/* 198 */       float xa = (world.f_46441_.m_188501_() - 0.5F) / 2.0F;
/* 199 */       float ya = 0.25F + (0.5F + world.f_46441_.m_188501_() * 0.25F) * flow / 800.0F;
/* 200 */       float za = (world.f_46441_.m_188501_() - 0.5F) / 2.0F;
/*     */       
/* 202 */       float rx = (world.f_46441_.m_188501_() - 0.5F) * 0.5F;
/* 203 */       float rz = (world.f_46441_.m_188501_() - 0.5F) * 0.5F;
/*     */       
/* 205 */       double x = pos.m_123341_() + 0.5D + rx;
/* 206 */       double y = (pos.m_123342_() + yOffset);
/* 207 */       double z = pos.m_123343_() + 0.5D + rz;
/*     */       
/* 209 */       world.m_7106_((ParticleOptions)new FluidParticleData(fluid), x, y, z, xa, ya, za);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Level getClientWorld() {
/* 215 */     return (Level)MCUtil.getLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getClientPlayer() {
/* 220 */     return (Player)MCUtil.getPlayer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEntitySound(SoundEvent soundEvent, Entity entity, boolean active, float volume, float pitch) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleTileSound(SoundEvent soundEvent, BlockEntity te, boolean active, float volume, float pitch) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupManualPages() {
/* 234 */     handleReservoirManual(ResourceUtils.ip("reservoir"), 0);
/* 235 */     flarestack(ResourceUtils.ip("flarestack"), 12);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void flarestack(ResourceLocation location, int priority) {
/* 240 */     ManualInstance man = ManualHelper.getManual();
/*     */     
/* 242 */     ManualEntry.ManualEntryBuilder builder = new ManualEntry.ManualEntryBuilder(man);
/* 243 */     builder.readFromFile(location);
/* 244 */     builder.appendText(() -> {
/*     */           List<Component[]> list = (List)new ArrayList<>();
/*     */           
/*     */           for (TagKey<Fluid> tag : (Iterable<TagKey<Fluid>>)FlarestackHandler.getSet()) {
/*     */             for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
/*     */               if (fluid.m_205067_(tag)) {
/*     */                 Component[] entry = { (Component)Component.m_237119_(), (new FluidStack(fluid, 1)).getDisplayName() };
/*     */                 
/*     */                 list.add(entry);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           StringBuilder additionalText = new StringBuilder();
/*     */           List<ManualEntry.SpecialElementData> newElements = new ArrayList<>();
/*     */           int nextLine = 0;
/*     */           for (int page = 0; nextLine < list.size(); page++) {
/*     */             int linesOnPage = (page == 0) ? 12 : 14;
/*     */             int endIndex = Math.min(nextLine + linesOnPage, list.size());
/*     */             List<Component[]> onPage = (List)list.subList(nextLine, endIndex);
/*     */             nextLine = endIndex;
/*     */             String key = "flarestack_table" + page;
/*     */             additionalText.append("<&").append(key).append(">");
/*     */             newElements.add(new ManualEntry.SpecialElementData(key, 0, (SpecialManualElement)new ManualElementTable(man, (Component[][])onPage.toArray(()), false)));
/*     */           } 
/*     */           return Pair.of(additionalText.toString(), newElements);
/*     */         });
/* 270 */     man.addEntry(man.getRoot().getOrCreateSubnode((Comparable)ResourceUtils.ip("petroleum")), builder.create(), priority);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleReservoirManual(ResourceLocation location, int priority) {
/* 276 */     ManualInstance man = ManualHelper.getManual();
/*     */     
/* 278 */     ManualEntry.ManualEntryBuilder builder = new ManualEntry.ManualEntryBuilder(man);
/* 279 */     builder.setContent(ClientProxy::createContent);
/* 280 */     builder.setLocation(location);
/* 281 */     man.addEntry(man.getRoot().addNewSubnode((Comparable)ResourceUtils.ip("petroleum"), 100), builder.create(), priority);
/*     */   }
/*     */   
/*     */   protected static ManualEntry.EntryData createContent() {
/* 285 */     ArrayList<ManualEntry.SpecialElementData> itemList = new ArrayList<>();
/*     */     
/* 287 */     StringBuilder contentBuilder = new StringBuilder();
/* 288 */     for (int i = 0; i < 5; i++) {
/* 289 */       String tString = "ie.manual.entry.reservoirs.oil" + i;
/* 290 */       if (I18n.m_118938_(tString, new Object[0]).equals(tString)) {
/*     */         break;
/*     */       }
/* 293 */       contentBuilder.append(I18n.m_118938_(tString, new Object[0]));
/*     */     } 
/*     */     
/* 296 */     createReservoirPages(contentBuilder, itemList);
/*     */     
/* 298 */     String translatedTitle = I18n.m_118938_("ie.manual.entry.reservoirs.title", new Object[0]);
/* 299 */     String tanslatedSubtext = I18n.m_118938_("ie.manual.entry.reservoirs.subtitle", new Object[0]);
/* 300 */     String formattedContent = contentBuilder.toString().replaceAll("\r\n|\r|\n", "\n");
/* 301 */     return new ManualEntry.EntryData(translatedTitle, tanslatedSubtext, formattedContent, itemList);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void createReservoirPages(StringBuilder contentBuilder, ArrayList<ManualEntry.SpecialElementData> itemList) {
/* 306 */     ReservoirType[] reservoirs = (ReservoirType[])ReservoirType.map.values().toArray((Object[])new ReservoirType[0]);
/*     */     
/* 308 */     for (int i = 0; i < reservoirs.length; i++) {
/* 309 */       ReservoirType reservoir = reservoirs[i];
/*     */       
/* 311 */       ImmersivePetroleum.log.debug("Creating entry for " + reservoir);
/*     */       
/* 313 */       String name = "desc.immersivepetroleum.info.reservoir." + reservoir.name;
/* 314 */       String localizedName = I18n.m_118938_(name, new Object[0]);
/* 315 */       if (localizedName.equalsIgnoreCase(name)) {
/* 316 */         localizedName = reservoir.name;
/*     */       }
/* 318 */       char c = localizedName.toLowerCase().charAt(0);
/* 319 */       boolean isVowel = (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
/* 320 */       String aOrAn = I18n.m_118938_(isVowel ? "ie.manual.entry.reservoirs.vowel" : "ie.manual.entry.reservoirs.consonant", new Object[0]);
/*     */       
/* 322 */       String dimBWList = "", bioBWList = "";
/*     */       
/* 324 */       if (reservoir.getDimensions().hasEntries()) {
/* 325 */         StringBuilder strBuilder = new StringBuilder();
/*     */         
/* 327 */         reservoir.getDimensions().forEach(rl -> strBuilder.append((strBuilder.length() > 0) ? ", " : "").append("<dim;").append(rl).append(">"));
/*     */ 
/*     */ 
/*     */         
/* 331 */         if (reservoir.getDimensions().isBlacklist()) {
/* 332 */           dimBWList = I18n.m_118938_("ie.manual.entry.reservoirs.dim.invalid", new Object[] { localizedName, strBuilder.toString(), aOrAn });
/*     */         } else {
/* 334 */           dimBWList = I18n.m_118938_("ie.manual.entry.reservoirs.dim.valid", new Object[] { localizedName, strBuilder.toString(), aOrAn });
/*     */         } 
/*     */       } else {
/* 337 */         dimBWList = I18n.m_118938_("ie.manual.entry.reservoirs.dim.any", new Object[] { localizedName, aOrAn });
/*     */       } 
/*     */       
/* 340 */       if (reservoir.getBiomes().hasEntries()) {
/* 341 */         StringBuilder strBuilder = new StringBuilder();
/*     */         
/* 343 */         reservoir.getBiomes().forEach(rl -> {
/*     */               Biome bio = (Biome)ForgeRegistries.BIOMES.getValue(rl);
/*     */               
/*     */               strBuilder.append((strBuilder.length() > 0) ? ", " : "").append((bio != null) ? bio.toString() : rl);
/*     */             });
/* 348 */         if (reservoir.getBiomes().isBlacklist()) {
/* 349 */           bioBWList = I18n.m_118938_("ie.manual.entry.reservoirs.bio.invalid", new Object[] { strBuilder.toString() });
/*     */         } else {
/* 351 */           bioBWList = I18n.m_118938_("ie.manual.entry.reservoirs.bio.valid", new Object[] { strBuilder.toString() });
/*     */         } 
/*     */       } else {
/* 354 */         bioBWList = I18n.m_118938_("ie.manual.entry.reservoirs.bio.any", new Object[0]);
/*     */       } 
/*     */       
/* 357 */       String fluidName = "";
/* 358 */       Fluid fluid = reservoir.getFluid();
/* 359 */       if (fluid != null) {
/* 360 */         fluidName = (new FluidStack(fluid, 1)).getDisplayName().getString();
/*     */       }
/*     */       
/* 363 */       String repRate = "";
/* 364 */       if (reservoir.residual > 0)
/* 365 */         if (reservoir.equilibrium > 0) {
/* 366 */           repRate = I18n.m_118938_("ie.manual.entry.reservoirs.replenish", new Object[] { Integer.valueOf(reservoir.residual), fluidName, Utils.fDecimal(reservoir.equilibrium / 1000) });
/*     */         } else {
/* 368 */           repRate = I18n.m_118938_("ie.manual.entry.reservoirs.replenish_depleted", new Object[] { Integer.valueOf(reservoir.residual), fluidName });
/*     */         }  
/* 370 */       contentBuilder.append("<&").append(reservoir.m_6423_().toString()).append(">");
/* 371 */       contentBuilder.append(I18n.m_118938_("ie.manual.entry.reservoirs.content", new Object[] { dimBWList, fluidName, Utils.fDecimal(reservoir.minSize / 1000), Utils.fDecimal(reservoir.maxSize / 1000), repRate, bioBWList }));
/*     */       
/* 373 */       if (i < reservoirs.length - 1) {
/* 374 */         contentBuilder.append("<np>");
/*     */       }
/* 376 */       itemList.add(new ManualEntry.SpecialElementData(reservoir.m_6423_().toString(), 0, (SpecialManualElement)new ManualElementItem(ManualHelper.getManual(), new ItemStack[] { new ItemStack((ItemLike)fluid.m_6859_()) })));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\ClientProxy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */