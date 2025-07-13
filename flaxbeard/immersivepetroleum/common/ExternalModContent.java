/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExternalModContent
/*    */ {
/*    */   private static RegistryObject<Block> IE_REDSTONE_ENGINEERING_BLOCK;
/*    */   private static RegistryObject<Item> IE_ITEM_PIPE;
/*    */   private static RegistryObject<Item> IE_ITEM_BUCKSHOT;
/*    */   private static RegistryObject<Item> IE_ITEM_EMPTY_SHELL;
/*    */   private static RegistryObject<Fluid> IE_FLUID_CONCRETE;
/*    */   
/*    */   public static void init() {
/* 25 */     IE_REDSTONE_ENGINEERING_BLOCK = RegistryObject.create(ResourceUtils.ie("rs_engineering"), ForgeRegistries.BLOCKS);
/*    */     
/* 27 */     IE_ITEM_PIPE = RegistryObject.create(ResourceUtils.ie("fluid_pipe"), ForgeRegistries.ITEMS);
/* 28 */     IE_ITEM_BUCKSHOT = RegistryObject.create(ResourceUtils.ie("buckshot"), ForgeRegistries.ITEMS);
/* 29 */     IE_ITEM_EMPTY_SHELL = RegistryObject.create(ResourceUtils.ie("empty_shell"), ForgeRegistries.ITEMS);
/* 30 */     IE_FLUID_CONCRETE = RegistryObject.create(ResourceUtils.ie("concrete"), ForgeRegistries.FLUIDS);
/*    */   }
/*    */   
/*    */   public static Fluid getIEFluid_Concrete() {
/* 34 */     return (Fluid)IE_FLUID_CONCRETE.get();
/*    */   }
/*    */   
/*    */   public static FluidStack getIEFluid_Concrete(int amount) {
/* 38 */     return new FluidStack((Fluid)IE_FLUID_CONCRETE.get(), amount);
/*    */   }
/*    */   
/*    */   public static Block getIEBlock_RedstoneEngineering() {
/* 42 */     return (Block)IE_REDSTONE_ENGINEERING_BLOCK.get();
/*    */   }
/*    */   
/*    */   public static Item getIEItem_Buckshot() {
/* 46 */     return (Item)IE_ITEM_BUCKSHOT.get();
/*    */   }
/*    */   
/*    */   public static Item getIEItem_EmptyShell() {
/* 50 */     return (Item)IE_ITEM_EMPTY_SHELL.get();
/*    */   }
/*    */   
/*    */   public static Item getIEItem_Pipe() {
/* 54 */     return (Item)IE_ITEM_PIPE.get();
/*    */   }
/*    */   
/*    */   public static boolean isIEBlock_RedstoneEngineering(Block block) {
/* 58 */     return block.equals(getIEBlock_RedstoneEngineering());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_Buckshot(ItemStack stack) {
/* 62 */     return isIEItem_Buckshot(stack.m_41720_());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_Buckshot(Item item) {
/* 66 */     return item.equals(getIEItem_Buckshot());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_EmptyShell(ItemStack stack) {
/* 70 */     return isIEItem_EmptyShell(stack.m_41720_());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_EmptyShell(Item item) {
/* 74 */     return item.equals(getIEItem_EmptyShell());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_Pipe(ItemStack stack) {
/* 78 */     return isIEItem_Pipe(stack.m_41720_());
/*    */   }
/*    */   
/*    */   public static boolean isIEItem_Pipe(Item item) {
/* 82 */     return item.equals(getIEItem_Pipe());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\ExternalModContent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */