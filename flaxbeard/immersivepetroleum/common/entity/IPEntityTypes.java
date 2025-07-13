/*    */ package flaxbeard.immersivepetroleum.common.entity;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ 
/*    */ public class IPEntityTypes {
/*    */   static {
/*  9 */     MOTORBOAT = IPRegisters.registerEntityType("speedboat", s -> EntityType.Builder.m_20704_(MotorboatEntity::new, MobCategory.MISC).m_20699_(1.375F, 0.5625F).m_20702_(10).m_20712_(s.toString()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final RegistryObject<EntityType<MotorboatEntity>> MOTORBOAT;
/*    */ 
/*    */   
/* 18 */   public static final RegistryObject<EntityType<MolotovItemEntity>> MOLOTOV = createType();
/*    */ 
/*    */   
/*    */   private static RegistryObject<EntityType<MolotovItemEntity>> createType() {
/* 22 */     return IPRegisters.registerEntityType("molotov", s -> EntityType.Builder.m_20704_(MolotovItemEntity::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10).m_20712_(s.toString()));
/*    */   }
/*    */   
/*    */   public static void forceClassLoad() {}
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\entity\IPEntityTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */