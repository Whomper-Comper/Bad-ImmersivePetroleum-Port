/*    */ package flaxbeard.immersivepetroleum.api.crafting;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LubricantHandler
/*    */ {
/* 16 */   static final Set<Pair<TagKey<Fluid>, Integer>> lubricants = new HashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register(@Nonnull TagKey<Fluid> fluid, int amount) {
/* 25 */     if (lubricants.stream().noneMatch(pair -> (pair.getLeft() == fluid))) {
/* 26 */       lubricants.add(Pair.of(fluid, Integer.valueOf(amount)));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getLubeAmount(@Nonnull FluidStack toCheck) {
/* 38 */     return getLubeAmount(toCheck.getFluid());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getLubeAmount(@Nonnull Fluid toCheck) {
/* 50 */     for (Map.Entry<TagKey<Fluid>, Integer> entry : lubricants) {
/* 51 */       if (toCheck.m_205067_(entry.getKey())) {
/* 52 */         return ((Integer)entry.getValue()).intValue();
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isValidLube(@Nonnull FluidStack toCheck) {
/* 67 */     return isValidLube(toCheck.getFluid());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isValidLube(@Nonnull Fluid toCheck) {
/* 78 */     return lubricants.stream().anyMatch(pair -> toCheck.m_205067_((TagKey)pair.getKey()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\LubricantHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */