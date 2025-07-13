/*     */ package flaxbeard.immersivepetroleum.common.fluids;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
/*     */ import net.minecraftforge.common.ForgeMod;
/*     */ import net.minecraftforge.fluids.FluidType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CustomFluidType
/*     */   extends FluidType
/*     */ {
/*     */   final ResourceLocation stillTexture;
/*     */   final ResourceLocation flowTexture;
/*     */   
/*     */   public CustomFluidType(String name, FluidType.Properties properties) {
/* 275 */     super(properties);
/* 276 */     this.stillTexture = ResourceUtils.ip("block/fluid/" + name + "_still");
/* 277 */     this.flowTexture = ResourceUtils.ip("block/fluid/" + name + "_flow");
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
/* 282 */     consumer.accept(new IClientFluidTypeExtensions()
/*     */         {
/*     */           public ResourceLocation getStillTexture() {
/* 285 */             return IPFluid.CustomFluidType.this.stillTexture;
/*     */           }
/*     */ 
/*     */           
/*     */           public ResourceLocation getFlowingTexture() {
/* 290 */             return IPFluid.CustomFluidType.this.flowTexture;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
/* 297 */     if (!(state.m_76152_() instanceof IPFluid)) {
/* 298 */       return false;
/*     */     }
/*     */     
/* 301 */     IPFluid ipFluid = (IPFluid)state.m_76152_();
/* 302 */     if (!ipFluid.hasCustomSlowdown()) {
/* 303 */       return false;
/*     */     }
/*     */     
/* 306 */     double drag = ipFluid.getEntitySlowdown();
/* 307 */     boolean isFalling = ((entity.m_20184_()).f_82480_ <= 0.0D);
/* 308 */     double y = entity.m_20186_();
/* 309 */     double walkSpeed = entity.m_20142_() ? (drag * 1.125D) : drag;
/* 310 */     double swimSpeed = 0.019999999552965164D;
/*     */     
/* 312 */     swimSpeed *= entity.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22135_();
/* 313 */     entity.m_19920_((float)swimSpeed, movementVector);
/* 314 */     entity.m_6478_(MoverType.SELF, entity.m_20184_());
/* 315 */     Vec3 deltaMovment = entity.m_20184_();
/* 316 */     if (entity.f_19862_ && entity.m_6147_()) {
/* 317 */       deltaMovment = new Vec3(deltaMovment.f_82479_, 0.2D, deltaMovment.f_82481_);
/*     */     }
/*     */     
/* 320 */     entity.m_20256_(deltaMovment.m_82542_(walkSpeed, drag, walkSpeed));
/* 321 */     Vec3 vec32 = entity.m_20994_(gravity, isFalling, entity.m_20184_());
/* 322 */     entity.m_20256_(vec32);
/* 323 */     if (entity.f_19862_ && entity.m_20229_(vec32.f_82479_, vec32.f_82480_ + 0.6D - entity.m_20186_() + y, vec32.f_82481_)) {
/* 324 */       entity.m_20334_(vec32.f_82479_, 0.3D, vec32.f_82481_);
/*     */     }
/*     */     
/* 327 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\IPFluid$CustomFluidType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */