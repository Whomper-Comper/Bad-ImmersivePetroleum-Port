/*     */ package flaxbeard.immersivepetroleum.common.util.compat.crafttweaker;
/*     */ 
/*     */ import com.blamejared.crafttweaker.api.annotation.ZenRegister;
/*     */ import com.blamejared.crafttweaker.api.fluid.IFluidStack;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.openzen.zencode.java.ZenCodeType.Constructor;
/*     */ import org.openzen.zencode.java.ZenCodeType.Method;
/*     */ import org.openzen.zencode.java.ZenCodeType.Name;
/*     */ import org.openzen.zencode.java.ZenCodeType.OptionalInt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ZenRegister
/*     */ @Name("mods.immersivepetroleum.ReservoirRegistry")
/*     */ public class ReservoirTweaker
/*     */ {
/*     */   @Method
/*     */   public static boolean remove(String name) {
/*  27 */     List<ResourceLocation> test = ReservoirType.map.keySet().stream().filter(loc -> loc.m_135815_().contains(name)).toList();
/*     */     
/*  29 */     if (test.size() <= 1)
/*     */     {
/*  31 */       if (test.size() == 1) {
/*  32 */         ResourceLocation id = test.get(0);
/*  33 */         if (ReservoirType.map.containsKey(id)) {
/*  34 */           ReservoirType.map.remove(id);
/*  35 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   @Method
/*     */   public static void removeAll() {
/*  48 */     ReservoirType.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @ZenRegister
/*     */   @Name("mods.immersivepetroleum.ReservoirBuilder")
/*     */   public static class ReservoirBuilder
/*     */   {
/*     */     private boolean isValid = true;
/*     */     
/*     */     private final IFluidStack iFluidStack;
/*     */     private final int minSize;
/*     */     private final int maxSize;
/*     */     private final int traceAmount;
/*     */     private final int equilibrium;
/*     */     private final int weight;
/*     */     private boolean isDimBlacklist = false;
/*  65 */     private final List<ResourceLocation> dimensions = new ArrayList<>();
/*     */     
/*     */     private boolean isBioBlacklist = false;
/*  68 */     private final List<ResourceLocation> biomes = new ArrayList<>();
/*     */     
/*     */     @Constructor
/*     */     public ReservoirBuilder(IFluidStack fluid, int minSize, int maxSize, int traceAmount, int weight, @OptionalInt int equilibrium) {
/*  72 */       if (fluid == null)
/*     */       {
/*  74 */         this.isValid = false;
/*     */       }
/*  76 */       if (minSize <= 0)
/*     */       {
/*  78 */         this.isValid = false;
/*     */       }
/*  80 */       if (maxSize < minSize)
/*     */       {
/*  82 */         this.isValid = false;
/*     */       }
/*  84 */       if (weight <= 1)
/*     */       {
/*  86 */         this.isValid = false;
/*     */       }
/*  88 */       if (equilibrium <= 0)
/*     */       {
/*  90 */         this.isValid = false;
/*     */       }
/*     */       
/*  93 */       this.iFluidStack = fluid;
/*  94 */       this.minSize = minSize;
/*  95 */       this.maxSize = maxSize;
/*  96 */       this.traceAmount = traceAmount;
/*  97 */       this.weight = weight;
/*  98 */       this.equilibrium = equilibrium;
/*     */     }
/*     */     
/*     */     @Method
/*     */     public ReservoirBuilder setDimensions(boolean blacklist, String[] names) {
/* 103 */       if (!this.dimensions.isEmpty()) {
/* 104 */         throw new IllegalArgumentException("Dimensions B/W-List already set!");
/*     */       }
/*     */       
/* 107 */       this.isDimBlacklist = blacklist;
/* 108 */       for (String name : names) {
/*     */         try {
/* 110 */           ResourceLocation rl = new ResourceLocation(name);
/* 111 */           this.dimensions.add(rl);
/* 112 */         } catch (ResourceLocationException e) {
/* 113 */           throw new IllegalArgumentException(e);
/*     */         } 
/*     */       } 
/* 116 */       return this;
/*     */     }
/*     */     
/*     */     @Method
/*     */     public ReservoirBuilder setBiomes(boolean blacklist, String[] names) {
/* 121 */       if (!this.dimensions.isEmpty()) {
/* 122 */         throw new IllegalArgumentException("Biomes B/W-List already set!");
/*     */       }
/*     */       
/* 125 */       this.isBioBlacklist = blacklist;
/* 126 */       for (String name : names) {
/*     */         try {
/* 128 */           ResourceLocation rl = new ResourceLocation(name);
/* 129 */           this.biomes.add(rl);
/* 130 */         } catch (ResourceLocationException e) {
/* 131 */           throw new IllegalArgumentException(e);
/*     */         } 
/*     */       } 
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     @Method
/*     */     public void build(String name) {
/* 139 */       if (name.isEmpty())
/*     */       {
/* 141 */         this.isValid = false;
/*     */       }
/*     */       
/* 144 */       if (this.isValid) {
/* 145 */         ResourceLocation id = ResourceUtils.ct(name);
/*     */         
/* 147 */         if (!ReservoirType.map.containsKey(id)) {
/* 148 */           ReservoirType reservoir = new ReservoirType(name, id, this.iFluidStack.getFluid(), this.minSize, this.maxSize, this.traceAmount, this.equilibrium, this.weight);
/*     */           
/* 150 */           reservoir.setDimensions(this.isDimBlacklist, this.dimensions);
/* 151 */           reservoir.setDimensions(this.isBioBlacklist, this.biomes);
/*     */           
/* 153 */           ReservoirHandler.addReservoir(id, reservoir);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\compat\crafttweaker\ReservoirTweaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */