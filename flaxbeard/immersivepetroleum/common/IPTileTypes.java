/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.blocks.MultiblockBEType;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.FlarestackTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.GasGeneratorTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.SeismicSurveyTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellPipeTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ public class IPTileTypes {
/* 21 */   public static final MultiblockBEType<PumpjackTileEntity> PUMP = IPRegisters.registerMultiblockTE("pumpjack", PumpjackTileEntity::new, (Supplier)IPContent.Multiblock.PUMPJACK);
/* 22 */   public static final MultiblockBEType<DistillationTowerTileEntity> TOWER = IPRegisters.registerMultiblockTE("distillationtower", DistillationTowerTileEntity::new, (Supplier)IPContent.Multiblock.DISTILLATIONTOWER);
/* 23 */   public static final MultiblockBEType<CokerUnitTileEntity> COKER = IPRegisters.registerMultiblockTE("cokerunit", CokerUnitTileEntity::new, (Supplier)IPContent.Multiblock.COKERUNIT);
/* 24 */   public static final MultiblockBEType<HydrotreaterTileEntity> TREATER = IPRegisters.registerMultiblockTE("hydrotreater", HydrotreaterTileEntity::new, (Supplier)IPContent.Multiblock.HYDROTREATER);
/* 25 */   public static final MultiblockBEType<DerrickTileEntity> DERRICK = IPRegisters.registerMultiblockTE("derrick", DerrickTileEntity::new, (Supplier)IPContent.Multiblock.DERRICK);
/* 26 */   public static final MultiblockBEType<OilTankTileEntity> OILTANK = IPRegisters.registerMultiblockTE("oiltank", OilTankTileEntity::new, (Supplier)IPContent.Multiblock.OILTANK);
/*    */ 
/*    */   
/* 29 */   public static final RegistryObject<BlockEntityType<GasGeneratorTileEntity>> GENERATOR = IPRegisters.registerTE("gasgenerator", GasGeneratorTileEntity::new, (Supplier)IPContent.Blocks.GAS_GENERATOR);
/* 30 */   public static final RegistryObject<BlockEntityType<AutoLubricatorTileEntity>> AUTOLUBE = IPRegisters.registerTE("autolubricator", AutoLubricatorTileEntity::new, (Supplier)IPContent.Blocks.AUTO_LUBRICATOR);
/* 31 */   public static final RegistryObject<BlockEntityType<FlarestackTileEntity>> FLARE = IPRegisters.registerTE("flarestack", FlarestackTileEntity::new, (Supplier)IPContent.Blocks.FLARESTACK);
/* 32 */   public static final RegistryObject<BlockEntityType<WellTileEntity>> WELL = IPRegisters.registerTE("well", WellTileEntity::new, (Supplier)IPContent.Blocks.WELL);
/* 33 */   public static final RegistryObject<BlockEntityType<WellPipeTileEntity>> WELL_PIPE = IPRegisters.registerTE("well_pipe", WellPipeTileEntity::new, (Supplier)IPContent.Blocks.WELL_PIPE);
/*    */   
/* 35 */   public static final RegistryObject<BlockEntityType<SeismicSurveyTileEntity>> SEISMIC_SURVEY = IPRegisters.registerTE("seismic_survey", SeismicSurveyTileEntity::new, (Supplier)IPContent.Blocks.SEISMIC_SURVEY);
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPTileTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */