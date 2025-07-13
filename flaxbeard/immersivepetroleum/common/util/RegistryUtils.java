/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ 
/*    */ public class RegistryUtils
/*    */ {
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(Item item) {
/* 23 */     return ForgeRegistries.ITEMS.getKey(item);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(Block block) {
/* 28 */     return ForgeRegistries.BLOCKS.getKey(block);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(Fluid fluid) {
/* 33 */     return ForgeRegistries.FLUIDS.getKey(fluid);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(Biome biome) {
/* 38 */     return ForgeRegistries.BIOMES.getKey(biome);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(SoundEvent soundEvent) {
/* 43 */     return ForgeRegistries.SOUND_EVENTS.getKey(soundEvent);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(MobEffect mobEffect) {
/* 48 */     return ForgeRegistries.MOB_EFFECTS.getKey(mobEffect);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(EntityType<?> entityType) {
/* 53 */     return ForgeRegistries.ENTITY_TYPES.getKey(entityType);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(BlockEntityType<?> blockEntityType) {
/* 58 */     return ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(blockEntityType);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(ParticleType<?> particleType) {
/* 63 */     return ForgeRegistries.PARTICLE_TYPES.getKey(particleType);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(RecipeType<?> recipeType) {
/* 68 */     return ForgeRegistries.RECIPE_TYPES.getKey(recipeType);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(RecipeSerializer<?> recipeSerializer) {
/* 73 */     return ForgeRegistries.RECIPE_SERIALIZERS.getKey(recipeSerializer);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static ResourceLocation getRegistryNameOf(MenuType<?> menuType) {
/* 78 */     return ForgeRegistries.MENU_TYPES.getKey(menuType);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\RegistryUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */