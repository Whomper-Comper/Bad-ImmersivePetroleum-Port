/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.effect.MobEffectCategory;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AntiFireEffect
/*    */   extends IPEffects.IPEffect
/*    */ {
/*    */   public AntiFireEffect() {
/* 26 */     super(MobEffectCategory.BENEFICIAL, 8355711);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
/* 31 */     consumer.accept(new IClientMobEffectExtensions()
/*    */         {
/*    */           public boolean isVisibleInGui(MobEffectInstance instance)
/*    */           {
/* 35 */             return false;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean isVisibleInInventory(MobEffectInstance instance) {
/* 40 */             return false;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_6742_(LivingEntity living, int amplifier) {
/* 47 */     living.m_20095_();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\IPEffects$AntiFireEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */