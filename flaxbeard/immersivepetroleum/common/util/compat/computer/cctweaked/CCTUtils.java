/*    */ package flaxbeard.immersivepetroleum.common.util.compat.computer.cctweaked;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class CCTUtils
/*    */ {
/*    */   public static Map<String, Object> itemToMap(ItemStack stack) {
/* 13 */     ResourceLocation rl = RegistryUtils.getRegistryNameOf(stack.m_41720_());
/* 14 */     String regName = (rl == null) ? null : rl.toString();
/*    */     
/* 16 */     Map<String, Object> outputMap = new HashMap<>();
/* 17 */     outputMap.put("name", regName);
/* 18 */     outputMap.put("count", Integer.valueOf(stack.m_41613_()));
/* 19 */     return outputMap;
/*    */   }
/*    */   
/*    */   public static Map<String, Object> fluidToMap(FluidStack stack) {
/* 23 */     ResourceLocation rl = RegistryUtils.getRegistryNameOf(stack.getFluid());
/* 24 */     String regName = (rl == null) ? null : rl.toString();
/*    */     
/* 26 */     Map<String, Object> outputMap = new HashMap<>();
/* 27 */     outputMap.put("name", regName);
/* 28 */     outputMap.put("amount", Integer.valueOf(stack.getAmount()));
/* 29 */     return outputMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\computer\cctweaked\CCTUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */