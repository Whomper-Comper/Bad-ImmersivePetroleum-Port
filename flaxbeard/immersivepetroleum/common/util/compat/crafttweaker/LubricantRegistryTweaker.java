/*    */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*    */ 
/*    */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*    */ import com.blamejared.crafttweaker.api.tag.type.KnownTag;
/*    */ import com.blamejared.crafttweaker.api.util.Many;
/*    */ import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import org.openzen.zencode.java.ZenCodeType.Method;
/*    */ import org.openzen.zencode.java.ZenCodeType.Name;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ZenRegister
/*    */ @Name("mods.immersivepetroleum.Lubricant")
/*    */ public class LubricantRegistryTweaker
/*    */ {
/*    */   @Method
/*    */   public static void register(Many<KnownTag<Fluid>> tag) {
/* 19 */     if (tag == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 24 */     if (tag.getAmount() < 1) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 29 */     LubricantHandler.register(((KnownTag)tag.getData()).getTagKey(), tag.getAmount());
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\LubricantRegistryTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */