/*     */ package flaxbeard.immersivepetroleum.common.fluids;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.item.BucketItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.StateHolder;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FlowingFluid;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Material;
/*     */ import net.minecraft.world.level.material.MaterialColor;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
/*     */ import net.minecraftforge.common.ForgeMod;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*     */ import net.minecraftforge.fluids.FluidType;
/*     */ import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
/*     */ import net.minecraftforge.registries.RegistryObject;
/*     */ import org.apache.commons.lang3.mutable.Mutable;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ public class IPFluid
/*     */   extends FlowingFluid
/*     */ {
/*  64 */   public static final List<IPFluidEntry> FLUIDS = new ArrayList<>(); private static IPFluidEntry staticEntry; protected final IPFluidEntry entry;
/*     */   
/*     */   public static IPFluidEntry makeFluid(String name, int density, int viscosity) {
/*  67 */     return makeFluid(name, density, viscosity, 0.014D);
/*     */   }
/*     */   
/*     */   public static <B extends IPFluidBlock> IPFluidEntry makeFluid(String name, int density, int viscosity, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> blockFactory) {
/*  71 */     return makeFluid(name, density, viscosity, 0.014D, blockFactory);
/*     */   }
/*     */   
/*     */   public static <S extends IPFluid> IPFluidEntry makeFluid(String name, int density, int viscosity, Function<IPFluidEntry, S> sourceFactory) {
/*  75 */     return makeFluid(name, density, viscosity, 0.014D, sourceFactory);
/*     */   }
/*     */   
/*     */   public static <S extends IPFluid, B extends IPFluidBlock> IPFluidEntry makeFluid(String name, int density, int viscosity, Function<IPFluidEntry, S> sourceFactory, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> blockFactory) {
/*  79 */     return makeFluid(name, density, viscosity, 0.014D, sourceFactory, blockFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IPFluidEntry makeFluid(String name, int density, int viscosity, double motionScale) {
/*  84 */     IPFluidEntry entry = IPFluidEntry.make(name, IPFluid::new, Flowing::new, IPFluidBlock::new, builder(density, viscosity, motionScale));
/*  85 */     return entry;
/*     */   }
/*     */   
/*     */   public static <B extends IPFluidBlock> IPFluidEntry makeFluid(String name, int density, int viscosity, double motionScale, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> blockFactory) {
/*  89 */     IPFluidEntry entry = IPFluidEntry.make(name, IPFluid::new, Flowing::new, blockFactory, builder(density, viscosity, motionScale));
/*  90 */     return entry;
/*     */   }
/*     */   
/*     */   public static <S extends IPFluid> IPFluidEntry makeFluid(String name, int density, int viscosity, double motionScale, Function<IPFluidEntry, S> sourceFactory) {
/*  94 */     IPFluidEntry entry = IPFluidEntry.make(name, sourceFactory, Flowing::new, IPFluidBlock::new, builder(density, viscosity, motionScale));
/*  95 */     return entry;
/*     */   }
/*     */   
/*     */   public static <S extends IPFluid, B extends IPFluidBlock> IPFluidEntry makeFluid(String name, int density, int viscosity, double motionScale, Function<IPFluidEntry, S> sourceFactory, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> blockFactory) {
/*  99 */     IPFluidEntry entry = IPFluidEntry.make(name, sourceFactory, Flowing::new, blockFactory, builder(density, viscosity, motionScale));
/* 100 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Material createMaterial(MaterialColor color) {
/* 105 */     return new Material(color, true, false, false, false, false, true, PushReaction.DESTROY);
/*     */   }
/*     */ 
/*     */   
/*     */   private static IPFluid makeFluid(Function<IPFluidEntry, ? extends IPFluid> make, IPFluidEntry entry) {
/* 110 */     staticEntry = entry;
/* 111 */     IPFluid ret = make.apply(entry);
/* 112 */     staticEntry = null;
/* 113 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IPFluid(IPFluidEntry entry) {
/* 118 */     this.entry = entry;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> builder) {
/* 123 */     super.m_7180_(builder);
/* 124 */     for (Property<?> p : ((this.entry == null) ? staticEntry : this.entry).properties()) {
/* 125 */       builder.m_61104_(new Property[] { p });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void m_7456_(@Nonnull LevelAccessor arg0, @Nonnull BlockPos arg1, @Nonnull BlockState arg2) {}
/*     */ 
/*     */   
/*     */   protected boolean m_6760_() {
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasCustomSlowdown() {
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public double getEntitySlowdown() {
/* 143 */     return 0.8D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Fluid m_5615_() {
/* 149 */     return (Fluid)this.entry.flowing.get();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Fluid m_5613_() {
/* 155 */     return (Fluid)this.entry.source.get();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Item m_6859_() {
/* 161 */     return (Item)this.entry.bucket.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidType getFluidType() {
/* 166 */     return (FluidType)this.entry.type.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int m_6713_(@Nonnull LevelReader arg0) {
/* 171 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int m_6719_(@Nonnull LevelReader arg0) {
/* 176 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean m_5486_(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
/* 181 */     return (direction == Direction.DOWN && !m_6212_(fluid));
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_6718_(@Nonnull LevelReader p_205569_1_) {
/* 186 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float m_6752_() {
/* 191 */     return 100.0F;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected BlockState m_5804_(@Nonnull FluidState state) {
/* 196 */     return (BlockState)((IPFluidBlock)this.entry.block.get()).m_49966_().m_61124_((Property)LiquidBlock.f_54688_, Integer.valueOf(m_76092_(state)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7444_(FluidState state) {
/* 201 */     return state.m_192917_(m_5613_());
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7430_(@Nonnull FluidState state) {
/* 206 */     return m_7444_(state) ? 8 : ((Integer)state.m_61143_((Property)f_75948_)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_6212_(Fluid fluidIn) {
/* 211 */     return (fluidIn == m_5613_() || fluidIn == m_5615_());
/*     */   }
/*     */   
/*     */   private static Consumer<FluidType.Properties> builder(int density, int viscosity, double motionScale) {
/* 215 */     return builder -> builder.viscosity(viscosity).density(density).motionScale(motionScale);
/*     */   }
/*     */   public static final class IPFluidEntry extends Record { private final RegistryObject<IPFluid> source; private final RegistryObject<IPFluid> flowing; private final RegistryObject<IPFluid.IPFluidBlock> block; private final RegistryObject<BucketItem> bucket; private final RegistryObject<FluidType> type;
/*     */     private final List<Property<?>> properties;
/*     */     
/* 220 */     public IPFluidEntry(RegistryObject<IPFluid> source, RegistryObject<IPFluid> flowing, RegistryObject<IPFluid.IPFluidBlock> block, RegistryObject<BucketItem> bucket, RegistryObject<FluidType> type, List<Property<?>> properties) { this.source = source; this.flowing = flowing; this.block = block; this.bucket = bucket; this.type = type; this.properties = properties; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #220	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 220 */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry; } public RegistryObject<IPFluid> source() { return this.source; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #220	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #220	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;
/* 220 */       //   0	8	1	o	Ljava/lang/Object; } public RegistryObject<IPFluid> flowing() { return this.flowing; } public RegistryObject<IPFluid.IPFluidBlock> block() { return this.block; } public RegistryObject<BucketItem> bucket() { return this.bucket; } public RegistryObject<FluidType> type() { return this.type; } public List<Property<?>> properties() { return this.properties; }
/*     */     
/*     */     public Fluid get() {
/* 223 */       return (Fluid)source().get();
/*     */     }
/*     */     
/*     */     public void setEffect(MobEffect effect, int duration, int level) {
/* 227 */       ((IPFluid.IPFluidBlock)block().get()).setEffect(effect, duration, level);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes) {
/* 235 */       return make(name, 0, makeSource, makeFlowing, makeBlock, buildAttributes, (List<Property<?>>)ImmutableList.of());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes, List<Property<?>> properties) {
/* 243 */       return make(name, 0, makeSource, makeFlowing, makeBlock, buildAttributes, properties);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, int burnTime, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes, List<Property<?>> properties) {
/* 251 */       FluidType.Properties builder = FluidType.Properties.create();
/* 252 */       if (buildAttributes != null) {
/* 253 */         buildAttributes.accept(builder);
/*     */       }
/*     */       
/* 256 */       RegistryObject<FluidType> type = IPRegisters.FLUID_TYPE.register(name, () -> new IPFluid.CustomFluidType(name, builder));
/*     */       
/* 258 */       MutableObject mutableObject = new MutableObject();
/*     */       
/* 260 */       RegistryObject<IPFluid> source = IPRegisters.registerFluid(name, () -> IPFluid.makeFluid(makeSource, (IPFluidEntry)thisMutable.getValue()));
/* 261 */       RegistryObject<IPFluid> flow = IPRegisters.registerFluid(name + "_flowing", () -> IPFluid.makeFluid(makeFlowing, (IPFluidEntry)thisMutable.getValue()));
/* 262 */       RegistryObject<IPFluid.IPFluidBlock> block = IPRegisters.registerBlock(name + "_fluid_block", () -> (IPFluid.IPFluidBlock)makeBlock.apply((IPFluidEntry)thisMutable.getValue(), BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_49990_)));
/* 263 */       RegistryObject<BucketItem> bucket = IPRegisters.registerItem(name + "_bucket", () -> new IPFluid.IPBucketItem((Supplier<? extends Fluid>)source, burnTime));
/*     */       
/* 265 */       IPFluidEntry entry = new IPFluidEntry(source, flow, block, bucket, type, properties);
/* 266 */       mutableObject.setValue(entry);
/* 267 */       IPFluid.FLUIDS.add(entry);
/* 268 */       return entry;
/*     */     } }
/*     */   
/*     */   private static class CustomFluidType extends FluidType { final ResourceLocation stillTexture;
/*     */     final ResourceLocation flowTexture;
/*     */     
/*     */     public CustomFluidType(String name, FluidType.Properties properties) {
/* 275 */       super(properties);
/* 276 */       this.stillTexture = ResourceUtils.ip("block/fluid/" + name + "_still");
/* 277 */       this.flowTexture = ResourceUtils.ip("block/fluid/" + name + "_flow");
/*     */     }
/*     */ 
/*     */     
/*     */     public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
/* 282 */       consumer.accept(new IClientFluidTypeExtensions()
/*     */           {
/*     */             public ResourceLocation getStillTexture() {
/* 285 */               return IPFluid.CustomFluidType.this.stillTexture;
/*     */             }
/*     */ 
/*     */             
/*     */             public ResourceLocation getFlowingTexture() {
/* 290 */               return IPFluid.CustomFluidType.this.flowTexture;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
/* 297 */       if (!(state.m_76152_() instanceof IPFluid)) {
/* 298 */         return false;
/*     */       }
/*     */       
/* 301 */       IPFluid ipFluid = (IPFluid)state.m_76152_();
/* 302 */       if (!ipFluid.hasCustomSlowdown()) {
/* 303 */         return false;
/*     */       }
/*     */       
/* 306 */       double drag = ipFluid.getEntitySlowdown();
/* 307 */       boolean isFalling = ((entity.m_20184_()).f_82480_ <= 0.0D);
/* 308 */       double y = entity.m_20186_();
/* 309 */       double walkSpeed = entity.m_20142_() ? (drag * 1.125D) : drag;
/* 310 */       double swimSpeed = 0.019999999552965164D;
/*     */       
/* 312 */       swimSpeed *= entity.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22135_();
/* 313 */       entity.m_19920_((float)swimSpeed, movementVector);
/* 314 */       entity.m_6478_(MoverType.SELF, entity.m_20184_());
/* 315 */       Vec3 deltaMovment = entity.m_20184_();
/* 316 */       if (entity.f_19862_ && entity.m_6147_()) {
/* 317 */         deltaMovment = new Vec3(deltaMovment.f_82479_, 0.2D, deltaMovment.f_82481_);
/*     */       }
/*     */       
/* 320 */       entity.m_20256_(deltaMovment.m_82542_(walkSpeed, drag, walkSpeed));
/* 321 */       Vec3 vec32 = entity.m_20994_(gravity, isFalling, entity.m_20184_());
/* 322 */       entity.m_20256_(vec32);
/* 323 */       if (entity.f_19862_ && entity.m_20229_(vec32.f_82479_, vec32.f_82480_ + 0.6D - entity.m_20186_() + y, vec32.f_82481_)) {
/* 324 */         entity.m_20334_(vec32.f_82479_, 0.3D, vec32.f_82481_);
/*     */       }
/*     */       
/* 327 */       return true;
/*     */     } }
/*     */   class null implements IClientFluidTypeExtensions { public ResourceLocation getStillTexture() {
/*     */       return IPFluid.CustomFluidType.this.stillTexture;
/*     */     }
/*     */     public ResourceLocation getFlowingTexture() {
/*     */       return IPFluid.CustomFluidType.this.flowTexture;
/*     */     } }
/*     */   public static class IPFluidBlock extends LiquidBlock { private static IPFluid.IPFluidEntry staticEntry; protected final IPFluid.IPFluidEntry entry; @Nullable
/*     */     private MobEffect effect; private int duration;
/*     */     private int level;
/*     */     
/*     */     public IPFluidBlock(IPFluid.IPFluidEntry entry, BlockBehaviour.Properties props) {
/* 340 */       super((Supplier)entry.source(), (BlockBehaviour.Properties)Util.m_137469_(props, p -> staticEntry = entry));
/* 341 */       this.entry = entry;
/* 342 */       staticEntry = null;
/*     */     }
/*     */     
/*     */     public void setEffect(@Nullable MobEffect effect, int duration, int level) {
/* 346 */       this.effect = effect;
/* 347 */       this.duration = duration;
/* 348 */       this.level = level;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
/* 353 */       super.m_7926_(builder);
/* 354 */       for (Property<?> p : ((this.entry == null) ? staticEntry : this.entry).properties()) {
/* 355 */         builder.m_61104_(new Property[] { p });
/*     */       } 
/*     */     }
/*     */     
/*     */     public FluidState m_5888_(BlockState pState) {
/* 360 */       FluidState state = super.m_5888_(pState);
/* 361 */       for (Property<?> prop : (Iterable<Property<?>>)state.m_61147_()) {
/* 362 */         if (pState.m_61138_(prop))
/* 363 */           state = copyValue(prop, state, (StateHolder<?, ?>)pState); 
/*     */       } 
/* 365 */       return state;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void m_7892_(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
/* 371 */       super.m_7892_(pState, pLevel, pPos, pEntity);
/* 372 */       if (this.effect != null && pEntity instanceof LivingEntity) { LivingEntity living = (LivingEntity)pEntity;
/* 373 */         living.m_7292_(new MobEffectInstance(this.effect, this.duration, this.level)); }
/*     */     
/*     */     }
/*     */     public static <S extends StateHolder<?, S>, P extends Comparable<P>> S copyValue(Property<P> prop, S oldState, StateHolder<?, ?> from) {
/* 377 */       return (S)oldState.m_61124_(prop, from.m_61143_(prop));
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class IPBucketItem extends BucketItem {
/* 382 */     private static final Item.Properties PROPS = (new Item.Properties()).m_41487_(1).m_41495_(Items.f_42446_).m_41491_(ImmersivePetroleum.creativeTab);
/*     */     private int burnTime;
/*     */     
/*     */     public IPBucketItem(Supplier<? extends Fluid> fluid, int burnTime) {
/* 386 */       super(fluid, PROPS);
/* 387 */       this.burnTime = burnTime;
/*     */     }
/*     */     
/*     */     public IPBucketItem(Supplier<? extends Fluid> fluid, Function<Item.Properties, Item.Properties> props) {
/* 391 */       super(fluid, props.apply(PROPS));
/*     */     }
/*     */ 
/*     */     
/*     */     public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
/* 396 */       return (ICapabilityProvider)new FluidBucketWrapper(stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
/* 401 */       return this.burnTime;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Flowing extends IPFluid {
/*     */     public Flowing(IPFluid.IPFluidEntry entry) {
/* 407 */       super(entry);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void m_7180_(@Nonnull StateDefinition.Builder<Fluid, FluidState> builder) {
/* 412 */       super.m_7180_(builder);
/* 413 */       builder.m_61104_(new Property[] { (Property)f_75948_ });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\IPFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */