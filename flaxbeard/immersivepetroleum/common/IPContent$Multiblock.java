/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.CokerUnitBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.DerrickBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.DistillationTowerBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.HydrotreaterBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.OilTankBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.metal.PumpjackBlock;
/*     */ import java.util.function.Supplier;
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
/*     */ public class Multiblock
/*     */ {
/*  89 */   public static final RegistryObject<DistillationTowerBlock> DISTILLATIONTOWER = IPRegisters.registerMultiblockBlock("distillation_tower", DistillationTowerBlock::new);
/*     */ 
/*     */   
/*  92 */   public static final RegistryObject<PumpjackBlock> PUMPJACK = IPRegisters.registerMultiblockBlock("pumpjack", PumpjackBlock::new);
/*     */ 
/*     */   
/*  95 */   public static final RegistryObject<CokerUnitBlock> COKERUNIT = IPRegisters.registerMultiblockBlock("coker_unit", CokerUnitBlock::new);
/*     */ 
/*     */   
/*  98 */   public static final RegistryObject<HydrotreaterBlock> HYDROTREATER = IPRegisters.registerMultiblockBlock("hydrotreater", HydrotreaterBlock::new);
/*     */ 
/*     */   
/* 101 */   public static final RegistryObject<DerrickBlock> DERRICK = IPRegisters.registerMultiblockBlock("derrick", DerrickBlock::new);
/*     */ 
/*     */   
/* 104 */   public static final RegistryObject<OilTankBlock> OILTANK = IPRegisters.registerMultiblockBlock("oiltank", OilTankBlock::new);
/*     */   
/*     */   private static void forceClassLoad() {}
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPContent$Multiblock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */