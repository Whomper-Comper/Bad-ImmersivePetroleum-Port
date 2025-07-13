/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.FlarestackBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.GasGeneratorBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.SeismicSurveyBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltSlab;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.AsphaltStairs;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.ParaffinWaxBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.PetcokeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellPipeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.wooden.AutoLubricatorBlock;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SlabBlock;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraftforge.registries.RegistryObject;
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
/*     */ public class Blocks
/*     */ {
/* 135 */   public static final RegistryObject<SeismicSurveyBlock> SEISMIC_SURVEY = IPRegisters.registerIPBlock("seismic_survey", SeismicSurveyBlock::new);
/*     */   
/* 137 */   public static final RegistryObject<GasGeneratorBlock> GAS_GENERATOR = IPRegisters.registerIPBlock("gas_generator", GasGeneratorBlock::new);
/* 138 */   public static final RegistryObject<AutoLubricatorBlock> AUTO_LUBRICATOR = IPRegisters.registerIPBlock("auto_lubricator", AutoLubricatorBlock::new);
/* 139 */   public static final RegistryObject<FlarestackBlock> FLARESTACK = IPRegisters.registerIPBlock("flarestack", FlarestackBlock::new);
/*     */   
/* 141 */   public static final RegistryObject<AsphaltBlock> ASPHALT = IPRegisters.registerIPBlock("asphalt", AsphaltBlock::new);
/* 142 */   public static final RegistryObject<SlabBlock> ASPHALT_SLAB = IPRegisters.registerBlock("asphalt_slab", () -> new AsphaltSlab((AsphaltBlock)ASPHALT.get()));
/* 143 */   public static final RegistryObject<StairBlock> ASPHALT_STAIR = IPRegisters.registerBlock("asphalt_stair", () -> new AsphaltStairs((AsphaltBlock)ASPHALT.get()));
/* 144 */   public static final RegistryObject<PetcokeBlock> PETCOKE = IPRegisters.registerIPBlock("petcoke_block", PetcokeBlock::new);
/* 145 */   public static final RegistryObject<WellBlock> WELL = IPRegisters.registerBlock("well", WellBlock::new);
/* 146 */   public static final RegistryObject<WellPipeBlock> WELL_PIPE = IPRegisters.registerBlock("well_pipe", WellPipeBlock::new);
/* 147 */   public static final RegistryObject<ParaffinWaxBlock> PARAFFIN_WAX = IPRegisters.registerIPBlock("paraffin_wax_block", ParaffinWaxBlock::new);
/*     */   
/*     */   private static void forceClassLoad() {
/* 150 */     registerItemBlock((RegistryObject)ASPHALT_SLAB);
/* 151 */     registerItemBlock((RegistryObject)ASPHALT_STAIR);
/*     */   }
/*     */   
/*     */   private static void registerItemBlock(RegistryObject<? extends Block> block) {
/* 155 */     IPRegisters.registerItem(block.getId().m_135815_(), () -> new IPBlockItemBase((Block)block.get(), (new Item.Properties()).m_41491_(ImmersivePetroleum.creativeTab)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPContent$Blocks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */