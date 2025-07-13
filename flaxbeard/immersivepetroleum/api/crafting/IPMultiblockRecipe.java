/*    */ package flaxbeard.immersivepetroleum.api.crafting;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
/*    */ import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraftforge.common.util.Lazy;
/*    */ 
/*    */ public abstract class IPMultiblockRecipe
/*    */   extends MultiblockRecipe
/*    */ {
/*    */   Lazy<Integer> totalProcessTime;
/*    */   Lazy<Integer> totalProcessEnergy;
/*    */   
/*    */   protected <T extends net.minecraft.world.item.crafting.Recipe<?>> IPMultiblockRecipe(ItemStack outputDummy, IERecipeTypes.TypeWithClass<T> type, ResourceLocation id) {
/* 17 */     super(Lazy.of(() -> outputDummy), type, id);
/*    */   }
/*    */   
/*    */   protected void timeAndEnergy(int time, int energy) {
/* 21 */     this.totalProcessEnergy = Lazy.of(() -> Integer.valueOf(energy));
/* 22 */     this.totalProcessTime = Lazy.of(() -> Integer.valueOf(time));
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyTimeAndEnergy(DoubleSupplier timeModifier, DoubleSupplier energyModifier) {
/* 27 */     Lazy<Integer> oldTime = this.totalProcessTime;
/* 28 */     Lazy<Integer> oldEnergy = this.totalProcessEnergy;
/* 29 */     this.totalProcessTime = Lazy.of(() -> Integer.valueOf((int)Math.max(1.0D, ((Integer)oldTime.get()).intValue() * timeModifier.getAsDouble())));
/* 30 */     this.totalProcessEnergy = Lazy.of(() -> Integer.valueOf((int)Math.max(1.0D, ((Integer)oldEnergy.get()).intValue() * energyModifier.getAsDouble())));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalProcessTime() {
/* 35 */     return ((Integer)this.totalProcessTime.get()).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalProcessEnergy() {
/* 40 */     return ((Integer)this.totalProcessEnergy.get()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\IPMultiblockRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */