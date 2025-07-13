/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.effect.MobEffectCategory;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IPEffects
/*    */ {
/* 19 */   public static final RegistryObject<IPEffect> ANTI_DISMOUNT_FIRE = IPRegisters.registerMobEffect("anti_fire", AntiFireEffect::new);
/*    */   
/*    */   public static void forceClassLoad() {}
/*    */   
/*    */   private static class AntiFireEffect
/*    */     extends IPEffect {
/*    */     public AntiFireEffect() {
/* 26 */       super(MobEffectCategory.BENEFICIAL, 8355711);
/*    */     }
/*    */ 
/*    */     
/*    */     public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
/* 31 */       consumer.accept(new IClientMobEffectExtensions()
/*    */           {
/*    */             public boolean isVisibleInGui(MobEffectInstance instance)
/*    */             {
/* 35 */               return false;
/*    */             }
/*    */ 
/*    */             
/*    */             public boolean isVisibleInInventory(MobEffectInstance instance) {
/* 40 */               return false;
/*    */             }
/*    */           });
/*    */     }
/*    */ 
/*    */     
/*    */     public void m_6742_(LivingEntity living, int amplifier) {
/* 47 */       living.m_20095_(); }
/*    */   } class null implements IClientMobEffectExtensions { public boolean isVisibleInGui(MobEffectInstance instance) {
/*    */       return false;
/*    */     } public boolean isVisibleInInventory(MobEffectInstance instance) {
/*    */       return false;
/*    */     } } public static class IPEffect extends MobEffect { protected IPEffect(MobEffectCategory type, int color) {
/* 53 */       super(type, color);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\IPEffects.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */