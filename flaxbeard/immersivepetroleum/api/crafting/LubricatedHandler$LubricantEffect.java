/*     */ package flaxbeard.immersivepetroleum.api.crafting;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
/*     */ import blusunrize.immersiveengineering.common.config.IEServerConfig;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.phys.HitResult;
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
/*     */ public class LubricantEffect
/*     */   extends ChemthrowerHandler.ChemthrowerEffect
/*     */ {
/*     */   public void applyToEntity(LivingEntity target, Player shooter, ItemStack thrower, Fluid fluid) {
/* 175 */     if (target instanceof net.minecraft.world.entity.animal.IronGolem && 
/* 176 */       LubricantHandler.isValidLube(fluid)) {
/* 177 */       int ticks = Math.max(1, ((Integer)IEServerConfig.TOOLS.chemthrower_consumption.get()).intValue() / LubricantHandler.getLubeAmount(fluid)) * 4 / 3;
/*     */       
/* 179 */       MobEffectInstance activeSpeed = target.m_21124_(MobEffects.f_19596_);
/* 180 */       int ticksSpeed = ticks;
/* 181 */       if (activeSpeed != null && activeSpeed.m_19564_() <= 1) {
/* 182 */         ticksSpeed = Math.min(activeSpeed.m_19557_() + ticks, 1200);
/*     */       }
/*     */       
/* 185 */       MobEffectInstance activeStrength = target.m_21124_(MobEffects.f_19600_);
/* 186 */       int ticksStrength = ticks;
/* 187 */       if (activeStrength != null && activeStrength.m_19564_() <= 1) {
/* 188 */         ticksStrength = Math.min(activeStrength.m_19557_() + ticks, 1200);
/*     */       }
/*     */       
/* 191 */       target.m_7292_(new MobEffectInstance(MobEffects.f_19596_, ticksSpeed, 1));
/* 192 */       target.m_7292_(new MobEffectInstance(MobEffects.f_19600_, ticksStrength, 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyToBlock(Level world, HitResult mop, Player shooter, ItemStack thrower, Fluid fluid) {
/* 200 */     if (LubricantHandler.isValidLube(fluid)) {
/* 201 */       int amount = Math.max(1, ((Integer)IEServerConfig.TOOLS.chemthrower_consumption.get()).intValue() / LubricantHandler.getLubeAmount(fluid)) * 2 / 3;
/* 202 */       LubricatedHandler.lubricateTile(world.m_7702_(new BlockPos(mop.m_82450_())), fluid, amount, true, 1200);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\LubricatedHandler$LubricantEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */