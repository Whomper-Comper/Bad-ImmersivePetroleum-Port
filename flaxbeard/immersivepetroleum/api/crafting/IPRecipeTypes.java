/*    */ package flaxbeard.immersivepetroleum.api.crafting;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*    */ import net.minecraftforge.registries.DeferredRegister;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ 
/*    */ public class IPRecipeTypes
/*    */ {
/* 14 */   private static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registry.f_122914_, "immersivepetroleum");
/*    */   
/* 16 */   public static final IERecipeTypes.TypeWithClass<CokerUnitRecipe> COKER = makeType("cokerunit", CokerUnitRecipe.class);
/* 17 */   public static final IERecipeTypes.TypeWithClass<DistillationTowerRecipe> DISTILLATION = makeType("distillationtower", DistillationTowerRecipe.class);
/* 18 */   public static final IERecipeTypes.TypeWithClass<HighPressureRefineryRecipe> HYDROTREATER = makeType("hydrotreater", HighPressureRefineryRecipe.class);
/*    */   
/* 20 */   public static final IERecipeTypes.TypeWithClass<ReservoirType> RESERVOIR = makeType("reservoir", ReservoirType.class);
/*    */   
/*    */   public static void modConstruction() {
/* 23 */     REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
/*    */   }
/*    */   
/*    */   private static <T extends net.minecraft.world.item.crafting.Recipe<?>> IERecipeTypes.TypeWithClass<T> makeType(final String name, Class<T> type) {
/* 27 */     RegistryObject<RecipeType<T>> regObj = REGISTER.register(name, () -> new RecipeType<T>() {
/* 28 */           final String res = "immersivepetroleum:" + name;
/*    */ 
/*    */           
/*    */           public String toString() {
/* 32 */             return this.res;
/*    */           }
/*    */         });
/* 35 */     return new IERecipeTypes.TypeWithClass(regObj, type);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\IPRecipeTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */