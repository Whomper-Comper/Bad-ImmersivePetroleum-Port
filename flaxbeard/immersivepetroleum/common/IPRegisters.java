/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.IEProperties;
/*     */ import blusunrize.immersiveengineering.common.blocks.MultiblockBEType;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.eventbus.api.IEventBus;
/*     */ import net.minecraftforge.fluids.FluidType;
/*     */ import net.minecraftforge.registries.DeferredRegister;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ import net.minecraftforge.registries.RegistryObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPRegisters
/*     */ {
/*  38 */   private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, "immersivepetroleum");
/*  39 */   private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, "immersivepetroleum");
/*  40 */   private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, "immersivepetroleum");
/*  41 */   private static final DeferredRegister<BlockEntityType<?>> TE_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "immersivepetroleum");
/*  42 */   private static final DeferredRegister<EntityType<?>> ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "immersivepetroleum");
/*  43 */   private static final DeferredRegister<MenuType<?>> MENU_REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "immersivepetroleum");
/*  44 */   private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "immersivepetroleum");
/*  45 */   private static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "immersivepetroleum");
/*  46 */   private static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "immersivepetroleum");
/*  47 */   private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "immersivepetroleum");
/*  48 */   private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "immersivepetroleum");
/*  49 */   public static final DeferredRegister<FluidType> FLUID_TYPE = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, "immersivepetroleum");
/*     */   
/*     */   public static void addRegistersToEventBus(IEventBus eventBus) {
/*  52 */     FLUID_REGISTER.register(eventBus);
/*  53 */     BLOCK_REGISTER.register(eventBus);
/*  54 */     ITEM_REGISTER.register(eventBus);
/*  55 */     TE_REGISTER.register(eventBus);
/*  56 */     ENTITY_REGISTER.register(eventBus);
/*  57 */     MENU_REGISTER.register(eventBus);
/*  58 */     RECIPE_SERIALIZERS.register(eventBus);
/*  59 */     MOB_EFFECT.register(eventBus);
/*  60 */     SOUND_EVENT.register(eventBus);
/*  61 */     PARTICLE_TYPE.register(eventBus);
/*  62 */     ENTITY_TYPE.register(eventBus);
/*  63 */     FLUID_TYPE.register(eventBus);
/*     */   }
/*     */   
/*     */   public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockConstructor) {
/*  67 */     return registerBlock(name, blockConstructor, null);
/*     */   }
/*     */   
/*     */   public static <T extends Block> RegistryObject<T> registerMultiblockBlock(String name, Supplier<T> blockConstructor) {
/*  71 */     return registerBlock(name, blockConstructor, block -> new BlockItem(block, new Item.Properties()));
/*     */   }
/*     */   
/*     */   public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockConstructor, @Nullable Function<T, ? extends BlockItem> blockItem) {
/*  75 */     RegistryObject<T> block = BLOCK_REGISTER.register(name, blockConstructor);
/*  76 */     if (blockItem != null) {
/*  77 */       registerItem(name, () -> (BlockItem)blockItem.apply((Block)block.get()));
/*     */     }
/*  79 */     return block;
/*     */   }
/*     */   
/*     */   public static <T extends IPBlockBase> RegistryObject<T> registerIPBlock(String name, Supplier<T> blockConstructor) {
/*  83 */     RegistryObject<T> block = BLOCK_REGISTER.register(name, blockConstructor);
/*     */     
/*  85 */     registerItem(name, () -> (BlockItem)((IPBlockBase)block.get()).blockItemSupplier().get());
/*     */     
/*  87 */     return block;
/*     */   }
/*     */   
/*     */   public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> itemConstructor) {
/*  91 */     return ITEM_REGISTER.register(name, itemConstructor);
/*     */   }
/*     */   
/*     */   public static <T extends Fluid> RegistryObject<T> registerFluid(String name, Supplier<T> fluidConstructor) {
/*  95 */     return FLUID_REGISTER.register(name, fluidConstructor);
/*     */   }
/*     */   
/*     */   public static <T extends net.minecraft.world.level.block.entity.BlockEntity> RegistryObject<BlockEntityType<T>> registerTE(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> valid) {
/*  99 */     return TE_REGISTER.register(name, () -> new BlockEntityType(factory, (Set)ImmutableSet.of(valid.get()), null));
/*     */   }
/*     */   
/*     */   public static <T extends net.minecraft.world.level.block.entity.BlockEntity & blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IGeneralMultiblock> MultiblockBEType<T> registerMultiblockTE(String name, MultiblockBEType.BEWithTypeConstructor<T> factory, Supplier<? extends Block> valid) {
/* 103 */     return new MultiblockBEType(name, TE_REGISTER, factory, valid, state -> (state.m_61138_((Property)IEProperties.MULTIBLOCKSLAVE) && !((Boolean)state.m_61143_((Property)IEProperties.MULTIBLOCKSLAVE)).booleanValue()));
/*     */   }
/*     */   
/*     */   public static <T extends EntityType<?>> RegistryObject<T> registerEntity(String name, Supplier<T> entityConstructor) {
/* 107 */     return ENTITY_REGISTER.register(name, entityConstructor);
/*     */   }
/*     */   
/*     */   public static <T extends RecipeSerializer<?>> RegistryObject<T> registerSerializer(String name, Supplier<T> serializer) {
/* 111 */     return RECIPE_SERIALIZERS.register(name, serializer);
/*     */   }
/*     */   
/*     */   public static <T extends net.minecraft.world.inventory.AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenu(String name, Supplier<MenuType<T>> factory) {
/* 115 */     return MENU_REGISTER.register(name, factory);
/*     */   }
/*     */   
/*     */   public static <T extends flaxbeard.immersivepetroleum.common.util.IPEffects.IPEffect> RegistryObject<T> registerMobEffect(String name, Supplier<T> constructor) {
/* 119 */     return MOB_EFFECT.register(name, constructor);
/*     */   }
/*     */   
/*     */   public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
/* 123 */     return SOUND_EVENT.register(name, () -> new SoundEvent(ResourceUtils.ip(name)));
/*     */   }
/*     */   
/*     */   public static <PType extends ParticleType<?>> RegistryObject<PType> registerParticleType(String name, Supplier<PType> particleType) {
/* 127 */     return PARTICLE_TYPE.register(name, particleType);
/*     */   }
/*     */   
/*     */   public static <EType extends EntityType<?>> RegistryObject<EType> registerEntityType(String name, Function<ResourceLocation, EType> entityType) {
/* 131 */     return ENTITY_TYPE.register(name, () -> (EntityType)entityType.apply(ResourceUtils.ip(name)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPRegisters.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */