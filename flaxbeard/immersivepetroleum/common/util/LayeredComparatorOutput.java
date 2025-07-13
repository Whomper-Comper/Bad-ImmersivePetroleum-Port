/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import java.util.function.IntConsumer;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayeredComparatorOutput
/*    */ {
/*    */   private final double maxValue;
/*    */   private final int numLayers;
/*    */   private final double layerSize;
/*    */   private final Runnable updateMaster;
/*    */   private final IntConsumer updateLayer;
/* 21 */   private double lastValue = -1.0D;
/*    */   private int currentMasterOutput;
/*    */   private final int[] currentLayerOutputs;
/*    */   
/*    */   public LayeredComparatorOutput(double maxValue, int numLayers, Runnable updateMaster, IntConsumer updateLayer) {
/* 26 */     this.maxValue = maxValue;
/* 27 */     this.numLayers = numLayers;
/* 28 */     this.updateMaster = updateMaster;
/* 29 */     this.updateLayer = updateLayer;
/* 30 */     this.currentMasterOutput = 0;
/* 31 */     this.currentLayerOutputs = new int[numLayers];
/* 32 */     this.layerSize = maxValue / numLayers;
/*    */   }
/*    */   
/*    */   public void update(double newValue) {
/* 36 */     if (newValue == this.lastValue)
/*    */       return; 
/* 38 */     this.lastValue = newValue;
/* 39 */     int newMasterOutput = (int)(15.0D * newValue / this.maxValue);
/* 40 */     if (this.currentMasterOutput != newMasterOutput) {
/* 41 */       this.currentMasterOutput = newMasterOutput;
/* 42 */       this.updateMaster.run();
/*    */     } 
/* 44 */     for (int layer = 0; layer < this.numLayers; layer++) {
/* 45 */       double layerValue = newValue - layer * this.layerSize;
/* 46 */       int newLayerOutput = (int)Mth.m_14008_(15.0D * layerValue / this.layerSize, 0.0D, 15.0D);
/* 47 */       if (newLayerOutput != this.currentLayerOutputs[layer]) {
/* 48 */         this.currentLayerOutputs[layer] = newLayerOutput;
/* 49 */         this.updateLayer.accept(layer);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getCurrentMasterOutput() {
/* 55 */     return this.currentMasterOutput;
/*    */   }
/*    */   
/*    */   public int getLayerOutput(int layer) {
/* 59 */     return this.currentLayerOutputs[layer];
/*    */   }
/*    */   
/*    */   public int getLayers() {
/* 63 */     return this.numLayers;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\LayeredComparatorOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */