/*    */ package flaxbeard.immersivepetroleum.common.crafting;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
/*    */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*    */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Serializers
/*    */ {
/* 16 */   public static final RegistryObject<IERecipeSerializer<DistillationTowerRecipe>> DISTILLATION_SERIALIZER = IPRegisters.registerSerializer("distillation", flaxbeard.immersivepetroleum.common.crafting.serializers.DistillationTowerRecipeSerializer::new);
/*    */ 
/*    */ 
/*    */   
/* 20 */   public static final RegistryObject<IERecipeSerializer<CokerUnitRecipe>> COKER_SERIALIZER = IPRegisters.registerSerializer("coker", flaxbeard.immersivepetroleum.common.crafting.serializers.CokerUnitRecipeSerializer::new);
/*    */ 
/*    */ 
/*    */   
/* 24 */   public static final RegistryObject<IERecipeSerializer<HighPressureRefineryRecipe>> HYDROTREATER_SERIALIZER = IPRegisters.registerSerializer("hydrotreater", flaxbeard.immersivepetroleum.common.crafting.serializers.HighPressureRefineryRecipeSerializer::new);
/*    */ 
/*    */ 
/*    */   
/* 28 */   public static final RegistryObject<IERecipeSerializer<ReservoirType>> RESERVOIR_SERIALIZER = IPRegisters.registerSerializer("reservoirs", flaxbeard.immersivepetroleum.common.crafting.serializers.ReservoirSerializer::new);
/*    */   
/*    */   public static void forceClassLoad() {}
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\crafting\Serializers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */