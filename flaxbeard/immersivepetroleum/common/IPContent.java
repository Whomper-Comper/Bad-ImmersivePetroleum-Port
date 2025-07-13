/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.IETags;
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.CrusherBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.ExcavatorBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.register.IEPotions;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.IPTags;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.FlarestackHandler;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.client.particle.FluidSpill;
/*     */ import flaxbeard.immersivepetroleum.client.particle.IPParticleTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.CokerUnitBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.DerrickBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.DistillationTowerBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.FlarestackBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.GasGeneratorBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.HydrotreaterBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.OilTankBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.PumpjackBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.SeismicSurveyBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltSlab;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltStairs;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.ParaffinWaxBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.PetcokeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellPipeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.wooden.AutoLubricatorBlock;
/*     */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*     */ import flaxbeard.immersivepetroleum.common.entity.IPEntityTypes;
/*     */ import flaxbeard.immersivepetroleum.common.fluids.IPFluid;
/*     */ import flaxbeard.immersivepetroleum.common.items.IPItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.items.IPUpgradeItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.MolotovItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.MotorboatItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.OilCanItem;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.CokerUnitMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.DerrickMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.DistillationTowerMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.HydroTreaterMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.OilTankMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.multiblocks.PumpjackMultiblock;
/*     */ import flaxbeard.immersivepetroleum.common.util.IPEffects;
/*     */ import flaxbeard.immersivepetroleum.common.util.sounds.IPSounds;
/*     */ import flaxbeard.immersivepetroleum.common.world.IPWorldGen;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.particle.ParticleEngine;
/*     */ import net.minecraft.client.particle.ParticleProvider;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SlabBlock;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
/*     */ import net.minecraftforge.registries.RegistryObject;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*     */ public class IPContent
/*     */ {
/*  86 */   public static final Logger log = LogManager.getLogger("immersivepetroleum/Content");
/*     */   
/*     */   public static class Multiblock {
/*  89 */     public static final RegistryObject<DistillationTowerBlock> DISTILLATIONTOWER = IPRegisters.registerMultiblockBlock("distillation_tower", DistillationTowerBlock::new);
/*     */ 
/*     */     
/*  92 */     public static final RegistryObject<PumpjackBlock> PUMPJACK = IPRegisters.registerMultiblockBlock("pumpjack", PumpjackBlock::new);
/*     */ 
/*     */     
/*  95 */     public static final RegistryObject<CokerUnitBlock> COKERUNIT = IPRegisters.registerMultiblockBlock("coker_unit", CokerUnitBlock::new);
/*     */ 
/*     */     
/*  98 */     public static final RegistryObject<HydrotreaterBlock> HYDROTREATER = IPRegisters.registerMultiblockBlock("hydrotreater", HydrotreaterBlock::new);
/*     */ 
/*     */     
/* 101 */     public static final RegistryObject<DerrickBlock> DERRICK = IPRegisters.registerMultiblockBlock("derrick", DerrickBlock::new);
/*     */ 
/*     */     
/* 104 */     public static final RegistryObject<OilTankBlock> OILTANK = IPRegisters.registerMultiblockBlock("oiltank", OilTankBlock::new);
/*     */ 
/*     */     
/*     */     private static void forceClassLoad() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Fluids
/*     */   {
/* 113 */     public static final IPFluid.IPFluidEntry CRUDEOIL = IPFluid.makeFluid("crudeoil", 1000, 2250, 0.007D, flaxbeard.immersivepetroleum.common.fluids.CrudeOilFluid::new, flaxbeard.immersivepetroleum.common.fluids.CrudeOilFluid.CrudeOilBlock::new);
/* 114 */     public static final IPFluid.IPFluidEntry DIESEL_SULFUR = IPFluid.makeFluid("diesel_sulfur", 789, 1750, flaxbeard.immersivepetroleum.common.fluids.DieselFluid::new);
/* 115 */     public static final IPFluid.IPFluidEntry DIESEL = IPFluid.makeFluid("diesel", 789, 1750, flaxbeard.immersivepetroleum.common.fluids.DieselFluid::new);
/* 116 */     public static final IPFluid.IPFluidEntry LUBRICANT = IPFluid.makeFluid("lubricant", 925, 1000);
/* 117 */     public static final IPFluid.IPFluidEntry GASOLINE = IPFluid.makeFluid("gasoline", 789, 1200);
/*     */     
/* 119 */     public static final IPFluid.IPFluidEntry NAPHTHA = IPFluid.makeFluid("naphtha", 750, 750);
/* 120 */     public static final IPFluid.IPFluidEntry NAPHTHA_CRACKED = IPFluid.makeFluid("naphtha_cracked", 750, 750);
/* 121 */     public static final IPFluid.IPFluidEntry BENZENE = IPFluid.makeFluid("benzene", 876, 700);
/* 122 */     public static final IPFluid.IPFluidEntry PROPYLENE = IPFluid.makeFluid("propylene", 2, 1);
/* 123 */     public static final IPFluid.IPFluidEntry ETHYLENE = IPFluid.makeFluid("ethylene", 1, 1);
/* 124 */     public static final IPFluid.IPFluidEntry LUBRICANT_CRACKED = IPFluid.makeFluid("lubricant_cracked", 925, 1000);
/* 125 */     public static final IPFluid.IPFluidEntry KEROSENE = IPFluid.makeFluid("kerosene", 810, 900);
/* 126 */     public static final IPFluid.IPFluidEntry GASOLINE_ADDITIVES = IPFluid.makeFluid("gasoline_additives", 800, 900);
/*     */     
/* 128 */     public static final IPFluid.IPFluidEntry NAPALM = IPFluid.makeFluid("napalm", 1000, 4000, 0.0105D, flaxbeard.immersivepetroleum.common.fluids.NapalmFluid.NapalmFluidBlock::new);
/*     */     
/*     */     private static void forceClassLoad() {}
/*     */   }
/*     */   
/*     */   public static class Blocks
/*     */   {
/* 135 */     public static final RegistryObject<SeismicSurveyBlock> SEISMIC_SURVEY = IPRegisters.registerIPBlock("seismic_survey", SeismicSurveyBlock::new);
/*     */     
/* 137 */     public static final RegistryObject<GasGeneratorBlock> GAS_GENERATOR = IPRegisters.registerIPBlock("gas_generator", GasGeneratorBlock::new);
/* 138 */     public static final RegistryObject<AutoLubricatorBlock> AUTO_LUBRICATOR = IPRegisters.registerIPBlock("auto_lubricator", AutoLubricatorBlock::new);
/* 139 */     public static final RegistryObject<FlarestackBlock> FLARESTACK = IPRegisters.registerIPBlock("flarestack", FlarestackBlock::new);
/*     */     
/* 141 */     public static final RegistryObject<AsphaltBlock> ASPHALT = IPRegisters.registerIPBlock("asphalt", AsphaltBlock::new);
/* 142 */     public static final RegistryObject<SlabBlock> ASPHALT_SLAB = IPRegisters.registerBlock("asphalt_slab", () -> new AsphaltSlab((AsphaltBlock)ASPHALT.get()));
/* 143 */     public static final RegistryObject<StairBlock> ASPHALT_STAIR = IPRegisters.registerBlock("asphalt_stair", () -> new AsphaltStairs((AsphaltBlock)ASPHALT.get()));
/* 144 */     public static final RegistryObject<PetcokeBlock> PETCOKE = IPRegisters.registerIPBlock("petcoke_block", PetcokeBlock::new);
/* 145 */     public static final RegistryObject<WellBlock> WELL = IPRegisters.registerBlock("well", WellBlock::new);
/* 146 */     public static final RegistryObject<WellPipeBlock> WELL_PIPE = IPRegisters.registerBlock("well_pipe", WellPipeBlock::new);
/* 147 */     public static final RegistryObject<ParaffinWaxBlock> PARAFFIN_WAX = IPRegisters.registerIPBlock("paraffin_wax_block", ParaffinWaxBlock::new);
/*     */     
/*     */     private static void forceClassLoad() {
/* 150 */       registerItemBlock((RegistryObject)ASPHALT_SLAB);
/* 151 */       registerItemBlock((RegistryObject)ASPHALT_STAIR);
/*     */     }
/*     */     
/*     */     private static void registerItemBlock(RegistryObject<? extends Block> block) {
/* 155 */       IPRegisters.registerItem(block.getId().m_135815_(), () -> new IPBlockItemBase((Block)block.get(), (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Items {
/* 160 */     public static final RegistryObject<Item> PROJECTOR = IPRegisters.registerItem("projector", flaxbeard.immersivepetroleum.common.items.ProjectorItem::new);
/* 161 */     public static final RegistryObject<MotorboatItem> SPEEDBOAT = IPRegisters.registerItem("speedboat", MotorboatItem::new);
/* 162 */     public static final RegistryObject<OilCanItem> OIL_CAN = IPRegisters.registerItem("oil_can", OilCanItem::new);
/* 163 */     public static final RegistryObject<Item> BITUMEN = IPRegisters.registerItem("bitumen", IPItemBase::new);
/* 164 */     public static final RegistryObject<Item> PETCOKE = IPRegisters.registerItem("petcoke", () -> new IPItemBase()
/*     */         {
/*     */           public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 167 */             return 3200;
/*     */           }
/*     */         });
/* 170 */     public static final RegistryObject<Item> PETCOKEDUST = IPRegisters.registerItem("petcoke_dust", IPItemBase::new);
/* 171 */     public static final RegistryObject<Item> SURVEYRESULT = IPRegisters.registerItem("survey_result", flaxbeard.immersivepetroleum.common.items.SurveyResultItem::new);
/*     */     
/* 173 */     public static final RegistryObject<Item> PARAFFIN_WAX = IPRegisters.registerItem("paraffin_wax", () -> new IPItemBase()
/*     */         {
/*     */           public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 176 */             return 800;
/*     */           }
/*     */         });
/*     */     
/* 180 */     public static final RegistryObject<Item> GASOLINE_BOTTLE = IPRegisters.registerItem("gasoline_bottle", flaxbeard.immersivepetroleum.common.items.GasolineBottleItem::new);
/* 181 */     public static final RegistryObject<Item> MOLOTOV = IPRegisters.registerItem("molotov", () -> new MolotovItem(false)); private static void forceClassLoad() {}
/* 182 */     public static final RegistryObject<Item> MOLOTOV_LIT = IPRegisters.registerItem("molotov_lit", () -> new MolotovItem(true)); } class null extends IPItemBase {
/*     */     public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/*     */       return 3200;
/*     */     }
/*     */   } class null extends IPItemBase { public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/*     */       return 800;
/*     */     } }
/* 189 */   public static class BoatUpgrades { public static final RegistryObject<IPUpgradeItem> REINFORCED_HULL = createBoatUpgrade("reinforced_hull");
/* 190 */     public static final RegistryObject<IPUpgradeItem> ICE_BREAKER = createBoatUpgrade("icebreaker");
/* 191 */     public static final RegistryObject<IPUpgradeItem> TANK = createBoatUpgrade("tank");
/* 192 */     public static final RegistryObject<IPUpgradeItem> RUDDERS = createBoatUpgrade("rudders");
/* 193 */     public static final RegistryObject<IPUpgradeItem> PADDLES = createBoatUpgrade("paddles");
/*     */ 
/*     */     
/*     */     private static void forceClassLoad() {}
/*     */     
/*     */     private static <T extends Item> RegistryObject<IPUpgradeItem> createBoatUpgrade(String name) {
/* 199 */       return IPRegisters.registerItem("upgrade_" + name, () -> new IPUpgradeItem("MOTORBOAT"));
/*     */     } }
/*     */ 
/*     */   
/* 203 */   public static final RegistryObject<Item> DEBUGITEM = IPRegisters.registerItem("debug", flaxbeard.immersivepetroleum.common.items.DebugItem::new);
/*     */ 
/*     */   
/*     */   public static void modConstruction() {
/* 207 */     Fluids.forceClassLoad();
/* 208 */     Blocks.forceClassLoad();
/* 209 */     Items.forceClassLoad();
/* 210 */     BoatUpgrades.forceClassLoad();
/* 211 */     Multiblock.forceClassLoad();
/* 212 */     IPMenuTypes.forceClassLoad();
/* 213 */     Serializers.forceClassLoad();
/* 214 */     IPEffects.forceClassLoad();
/* 215 */     IPEntityTypes.forceClassLoad();
/* 216 */     IPParticleTypes.forceClassLoad();
/* 217 */     IPSounds.forceClassLoad();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void preInit() {}
/*     */   
/*     */   public static void init(ParallelDispatchEvent event) {
/* 224 */     event.enqueueWork(IPWorldGen::registerReservoirGen);
/*     */     
/* 226 */     Fluids.CRUDEOIL.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 100, 1);
/* 227 */     Fluids.DIESEL.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 40, 1);
/* 228 */     Fluids.DIESEL_SULFUR.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 40, 1);
/* 229 */     Fluids.GASOLINE.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 120, 2);
/* 230 */     Fluids.KEROSENE.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 120, 2);
/* 231 */     Fluids.NAPHTHA.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 120, 2);
/* 232 */     Fluids.NAPALM.setEffect((MobEffect)IEPotions.FLAMMABLE.get(), 140, 2);
/*     */     
/* 234 */     Fluids.LUBRICANT.setEffect((MobEffect)IEPotions.SLIPPERY.get(), 100, 1);
/*     */     
/* 236 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.lubricant, (ChemthrowerHandler.ChemthrowerEffect)new LubricatedHandler.LubricantEffect());
/* 237 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.lubricant, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.SLIPPERY.get(), 60, 1));
/* 238 */     ChemthrowerHandler.registerEffect(IETags.fluidPlantoil, (ChemthrowerHandler.ChemthrowerEffect)new LubricatedHandler.LubricantEffect());
/*     */     
/* 240 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.crudeOil, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.FLAMMABLE.get(), 60, 1));
/* 241 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.gasoline, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.FLAMMABLE.get(), 60, 1));
/* 242 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.naphtha, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.FLAMMABLE.get(), 60, 1));
/* 243 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.benzene, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.FLAMMABLE.get(), 60, 1));
/* 244 */     ChemthrowerHandler.registerEffect(IPTags.Fluids.napalm, (ChemthrowerHandler.ChemthrowerEffect)new ChemthrowerHandler.ChemthrowerEffect_Potion(null, 0.0F, (MobEffect)IEPotions.FLAMMABLE.get(), 60, 2));
/*     */     
/* 246 */     ChemthrowerHandler.registerFlammable(IPTags.Fluids.crudeOil);
/* 247 */     ChemthrowerHandler.registerFlammable(IPTags.Fluids.gasoline);
/* 248 */     ChemthrowerHandler.registerFlammable(IPTags.Fluids.naphtha);
/* 249 */     ChemthrowerHandler.registerFlammable(IPTags.Fluids.benzene);
/* 250 */     ChemthrowerHandler.registerFlammable(IPTags.Fluids.napalm);
/*     */     
/* 252 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)DistillationTowerMultiblock.INSTANCE);
/* 253 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)PumpjackMultiblock.INSTANCE);
/* 254 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)CokerUnitMultiblock.INSTANCE);
/* 255 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)HydroTreaterMultiblock.INSTANCE);
/* 256 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)DerrickMultiblock.INSTANCE);
/* 257 */     MultiblockHandler.registerMultiblock((MultiblockHandler.IMultiblock)OilTankMultiblock.INSTANCE);
/*     */     
/* 259 */     LubricantHandler.register(IPTags.Fluids.lubricant, 3);
/* 260 */     LubricantHandler.register(IETags.fluidPlantoil, 12);
/*     */     
/* 262 */     FlarestackHandler.register(IPTags.Utility.burnableInFlarestack);
/*     */     
/* 264 */     LubricatedHandler.registerLubricatedTile(PumpjackTileEntity.class, flaxbeard.immersivepetroleum.common.lubehandlers.PumpjackLubricationHandler::new);
/* 265 */     LubricatedHandler.registerLubricatedTile(ExcavatorBlockEntity.class, flaxbeard.immersivepetroleum.common.lubehandlers.ExcavatorLubricationHandler::new);
/* 266 */     LubricatedHandler.registerLubricatedTile(CrusherBlockEntity.class, flaxbeard.immersivepetroleum.common.lubehandlers.CrusherLubricationHandler::new);
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   @SubscribeEvent
/*     */   public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
/* 272 */     event.register((ParticleType)IPParticleTypes.FLARE_FIRE.get(), flaxbeard.immersivepetroleum.client.particle.FlareFire.Factory::new);
/* 273 */     event.register((ParticleType)IPParticleTypes.FLUID_SPILL.get(), (ParticleProvider)new FluidSpill.Factory());
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPContent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */