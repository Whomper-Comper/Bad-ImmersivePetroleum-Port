/*    */ package flaxbeard.immersivepetroleum.api.crafting;
/*    */ import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*    */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*    */ import flaxbeard.immersivepetroleum.common.crafting.Serializers;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.common.util.Lazy;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class DistillationTowerRecipe extends IPMultiblockRecipe {
/* 22 */   public static Map<ResourceLocation, DistillationTowerRecipe> recipes = new HashMap<>(); protected final FluidTagInput input;
/*    */   protected final FluidStack[] fluidOutput;
/*    */   
/*    */   public static DistillationTowerRecipe findRecipe(FluidStack input) {
/* 26 */     if (!recipes.isEmpty()) {
/* 27 */       for (DistillationTowerRecipe r : recipes.values()) {
/* 28 */         if (r.input != null && r.input.testIgnoringAmount(input)) {
/* 29 */           return r;
/*    */         }
/*    */       } 
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */   protected final ItemStack[] itemOutput; protected final double[] chances;
/*    */   public static DistillationTowerRecipe loadFromNBT(CompoundTag nbt) {
/* 37 */     FluidStack input = FluidStack.loadFluidStackFromNBT(nbt.m_128469_("input"));
/* 38 */     return findRecipe(input);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DistillationTowerRecipe(ResourceLocation id, FluidStack[] fluidOutput, ItemStack[] itemOutput, FluidTagInput input, int energy, int time, double[] chances) {
/* 47 */     super(ItemStack.f_41583_, (IERecipeTypes.TypeWithClass)IPRecipeTypes.DISTILLATION, id);
/* 48 */     this.fluidOutput = fluidOutput;
/* 49 */     this.itemOutput = itemOutput;
/* 50 */     this.chances = chances;
/*    */     
/* 52 */     this.input = input;
/* 53 */     this.fluidInputList = Collections.singletonList(input);
/* 54 */     this.fluidOutputList = Arrays.asList(this.fluidOutput);
/* 55 */     this.outputList = Lazy.of(() -> NonNullList.m_122783_(ItemStack.f_41583_, (Object[])itemOutput));
/*    */     
/* 57 */     timeAndEnergy(time, energy);
/* 58 */     Objects.requireNonNull(IPServerConfig.REFINING.distillationTower_timeModifier); Objects.requireNonNull(IPServerConfig.REFINING.distillationTower_energyModifier); modifyTimeAndEnergy(IPServerConfig.REFINING.distillationTower_timeModifier::get, IPServerConfig.REFINING.distillationTower_energyModifier::get);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IERecipeSerializer<DistillationTowerRecipe> getIESerializer() {
/* 63 */     return (IERecipeSerializer<DistillationTowerRecipe>)Serializers.DISTILLATION_SERIALIZER.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMultipleProcessTicks() {
/* 68 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public NonNullList<ItemStack> getActualItemOutputs(BlockEntity tile) {
/* 73 */     if (this.itemOutput.length == 0 && this.chances.length == 0) {
/* 74 */       return NonNullList.m_122779_();
/*    */     }
/* 76 */     Level level = tile.m_58904_();
/* 77 */     NonNullList<ItemStack> output = NonNullList.m_122779_();
/* 78 */     for (int i = 0; i < this.itemOutput.length; i++) {
/* 79 */       if (level.f_46441_.m_188501_() <= this.chances[i]) {
/* 80 */         output.add(this.itemOutput[i]);
/*    */       }
/*    */     } 
/*    */     
/* 84 */     return output;
/*    */   }
/*    */   
/*    */   public FluidTagInput getInputFluid() {
/* 88 */     return this.input;
/*    */   }
/*    */   
/*    */   public double[] chances() {
/* 92 */     return this.chances;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\DistillationTowerRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */