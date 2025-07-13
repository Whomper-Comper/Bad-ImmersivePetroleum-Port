/*    */ package flaxbeard.immersivepetroleum.api.crafting;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlarestackHandler
/*    */ {
/* 19 */   static final Set<TagKey<Fluid>> burnables = new HashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register(@Nonnull TagKey<Fluid> fluidTag) {
/* 27 */     burnables.add(fluidTag);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isBurnable(@Nonnull Fluid fluid) {
/* 37 */     return burnables.stream().anyMatch(tag -> match(tag, fluid));
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean match(TagKey<Fluid> tag, Fluid fluid) {
/* 42 */     return fluid.m_205067_(tag);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isBurnable(@Nonnull FluidStack fluidstack) {
/* 52 */     return (!fluidstack.isEmpty() && isBurnable(fluidstack.getFluid()));
/*    */   }
/*    */   
/*    */   public static Set<TagKey<Fluid>> getSet() {
/* 56 */     return Collections.unmodifiableSet(burnables);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\FlarestackHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */