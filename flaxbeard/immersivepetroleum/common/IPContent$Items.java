/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.items.IPItemBase;
/*     */ import flaxbeard.immersivepetroleum.common.items.MolotovItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.MotorboatItem;
/*     */ import flaxbeard.immersivepetroleum.common.items.OilCanItem;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
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
/*     */ public class Items
/*     */ {
/* 160 */   public static final RegistryObject<Item> PROJECTOR = IPRegisters.registerItem("projector", flaxbeard.immersivepetroleum.common.items.ProjectorItem::new);
/* 161 */   public static final RegistryObject<MotorboatItem> SPEEDBOAT = IPRegisters.registerItem("speedboat", MotorboatItem::new);
/* 162 */   public static final RegistryObject<OilCanItem> OIL_CAN = IPRegisters.registerItem("oil_can", OilCanItem::new);
/* 163 */   public static final RegistryObject<Item> BITUMEN = IPRegisters.registerItem("bitumen", IPItemBase::new);
/* 164 */   public static final RegistryObject<Item> PETCOKE = IPRegisters.registerItem("petcoke", () -> new IPItemBase()
/*     */       {
/*     */         public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 167 */           return 3200;
/*     */         }
/*     */       });
/* 170 */   public static final RegistryObject<Item> PETCOKEDUST = IPRegisters.registerItem("petcoke_dust", IPItemBase::new);
/* 171 */   public static final RegistryObject<Item> SURVEYRESULT = IPRegisters.registerItem("survey_result", flaxbeard.immersivepetroleum.common.items.SurveyResultItem::new);
/*     */   
/* 173 */   public static final RegistryObject<Item> PARAFFIN_WAX = IPRegisters.registerItem("paraffin_wax", () -> new IPItemBase()
/*     */       {
/*     */         public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
/* 176 */           return 800;
/*     */         }
/*     */       });
/*     */   
/* 180 */   public static final RegistryObject<Item> GASOLINE_BOTTLE = IPRegisters.registerItem("gasoline_bottle", flaxbeard.immersivepetroleum.common.items.GasolineBottleItem::new);
/* 181 */   public static final RegistryObject<Item> MOLOTOV = IPRegisters.registerItem("molotov", () -> new MolotovItem(false));
/* 182 */   public static final RegistryObject<Item> MOLOTOV_LIT = IPRegisters.registerItem("molotov_lit", () -> new MolotovItem(true));
/*     */   
/*     */   private static void forceClassLoad() {}
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPContent$Items.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */