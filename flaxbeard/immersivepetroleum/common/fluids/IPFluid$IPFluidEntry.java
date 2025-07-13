/*     */ package flaxbeard.immersivepetroleum.common.fluids;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import flaxbeard.immersivepetroleum.common.IPRegisters;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.item.BucketItem;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.fluids.FluidType;
/*     */ import net.minecraftforge.registries.RegistryObject;
/*     */ import org.apache.commons.lang3.mutable.Mutable;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IPFluidEntry
/*     */   extends Record
/*     */ {
/*     */   private final RegistryObject<IPFluid> source;
/*     */   private final RegistryObject<IPFluid> flowing;
/*     */   private final RegistryObject<IPFluid.IPFluidBlock> block;
/*     */   private final RegistryObject<BucketItem> bucket;
/*     */   private final RegistryObject<FluidType> type;
/*     */   private final List<Property<?>> properties;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #220	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #220	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #220	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/fluids/IPFluid$IPFluidEntry;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public IPFluidEntry(RegistryObject<IPFluid> source, RegistryObject<IPFluid> flowing, RegistryObject<IPFluid.IPFluidBlock> block, RegistryObject<BucketItem> bucket, RegistryObject<FluidType> type, List<Property<?>> properties) {
/* 220 */     this.source = source; this.flowing = flowing; this.block = block; this.bucket = bucket; this.type = type; this.properties = properties; } public RegistryObject<IPFluid> source() { return this.source; } public RegistryObject<IPFluid> flowing() { return this.flowing; } public RegistryObject<IPFluid.IPFluidBlock> block() { return this.block; } public RegistryObject<BucketItem> bucket() { return this.bucket; } public RegistryObject<FluidType> type() { return this.type; } public List<Property<?>> properties() { return this.properties; }
/*     */   
/*     */   public Fluid get() {
/* 223 */     return (Fluid)source().get();
/*     */   }
/*     */   
/*     */   public void setEffect(MobEffect effect, int duration, int level) {
/* 227 */     ((IPFluid.IPFluidBlock)block().get()).setEffect(effect, duration, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes) {
/* 235 */     return make(name, 0, makeSource, makeFlowing, makeBlock, buildAttributes, (List<Property<?>>)ImmutableList.of());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes, List<Property<?>> properties) {
/* 243 */     return make(name, 0, makeSource, makeFlowing, makeBlock, buildAttributes, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <S extends IPFluid, F extends IPFluid, B extends IPFluid.IPFluidBlock> IPFluidEntry make(String name, int burnTime, Function<IPFluidEntry, S> makeSource, Function<IPFluidEntry, F> makeFlowing, BiFunction<IPFluidEntry, BlockBehaviour.Properties, B> makeBlock, @Nullable Consumer<FluidType.Properties> buildAttributes, List<Property<?>> properties) {
/* 251 */     FluidType.Properties builder = FluidType.Properties.create();
/* 252 */     if (buildAttributes != null) {
/* 253 */       buildAttributes.accept(builder);
/*     */     }
/*     */     
/* 256 */     RegistryObject<FluidType> type = IPRegisters.FLUID_TYPE.register(name, () -> new IPFluid.CustomFluidType(name, builder));
/*     */     
/* 258 */     MutableObject mutableObject = new MutableObject();
/*     */     
/* 260 */     RegistryObject<IPFluid> source = IPRegisters.registerFluid(name, () -> IPFluid.makeFluid(makeSource, (IPFluidEntry)thisMutable.getValue()));
/* 261 */     RegistryObject<IPFluid> flow = IPRegisters.registerFluid(name + "_flowing", () -> IPFluid.makeFluid(makeFlowing, (IPFluidEntry)thisMutable.getValue()));
/* 262 */     RegistryObject<IPFluid.IPFluidBlock> block = IPRegisters.registerBlock(name + "_fluid_block", () -> (IPFluid.IPFluidBlock)makeBlock.apply((IPFluidEntry)thisMutable.getValue(), BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_49990_)));
/* 263 */     RegistryObject<BucketItem> bucket = IPRegisters.registerItem(name + "_bucket", () -> new IPFluid.IPBucketItem((Supplier<? extends Fluid>)source, burnTime));
/*     */     
/* 265 */     IPFluidEntry entry = new IPFluidEntry(source, flow, block, bucket, type, properties);
/* 266 */     mutableObject.setValue(entry);
/* 267 */     IPFluid.FLUIDS.add(entry);
/* 268 */     return entry;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\fluids\IPFluid$IPFluidEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */