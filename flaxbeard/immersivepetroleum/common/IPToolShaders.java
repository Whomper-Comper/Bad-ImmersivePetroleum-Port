/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
/*    */ import blusunrize.immersiveengineering.api.shader.ShaderCase;
/*    */ import blusunrize.immersiveengineering.api.shader.ShaderLayer;
/*    */ import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
/*    */ import flaxbeard.immersivepetroleum.common.shaderscases.ShaderCaseProjector;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Rarity;
/*    */ import net.minecraft.world.item.crafting.Ingredient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IPToolShaders
/*    */ {
/*    */   public static void preInit() {
/* 21 */     String warnings = "Do not touch the operational end of the device.\nDo not look directly at the operational end of the device.";
/*    */ 
/*    */     
/* 24 */     addProjectorShader("blue", Rarity.COMMON, -16744449, -16777216, -1, false, true, (primary, secondary, background, layers) -> {
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_portal"), -1));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_1_0"), primary.intValue()));
/* 27 */         }).setInfo("Aperture", "Portal", "Do not touch the operational end of the device.\nDo not look directly at the operational end of the device.");
/*    */     
/* 29 */     addProjectorShader("orange", Rarity.UNCOMMON, -33024, -16777216, -1, false, true, (primary, secondary, background, layers) -> {
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_portal"), -1));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_1_0"), primary.intValue()));
/* 32 */         }).setInfo("Aperture", "Portal", "Do not touch the operational end of the device.\nDo not look directly at the operational end of the device.");
/*    */     
/* 34 */     addProjectorShader("cube0", Rarity.COMMON, -12914177, -16777216, -1, false, true, (primary, secondary, background, layers) -> {
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_cube"), -1));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_1_1"), primary.intValue()));
/* 37 */         }).setInfo("Aperture", "Portal", "Designed to be used on the 1500 Megawatt Aperture Science Heavy Duty Super-Colliding Super Button");
/*    */     
/* 39 */     addProjectorShader("cube1", Rarity.EPIC, -39250, -16777216, -1, false, true, (primary, secondary, background, layers) -> {
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_cube"), -1));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_1_1"), primary.intValue()));
/* 42 */         }).setInfo("Aperture", "Portal", "Can not speak");
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderRegistry.ShaderRegistryEntry addProjectorShader(String name, Rarity rarity, int colorPrimary, int colorSecondary, int colorBackground, boolean loot, boolean bags) {
/* 47 */     return addProjectorShader(name, rarity, colorPrimary, colorSecondary, colorBackground, loot, bags, (primary, secondary, background, layers) -> {
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_0_0"), 0xFF000000 | primary.intValue()));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_0_1"), 0xFF000000 | secondary.intValue()));
/*    */           layers.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_0_2"), 0xFF000000 | background.intValue()));
/*    */         });
/*    */   }
/*    */   
/*    */   public static ShaderRegistry.ShaderRegistryEntry addProjectorShader(String name, Rarity rarity, int colorPrimary, int colorSecondary, int colorBackground, boolean loot, boolean bags, LayerAdder<Integer, Integer, Integer, List<ShaderLayer>> extraLayers) {
/* 55 */     ResourceLocation rlName = ResourceUtils.ip(name);
/*    */     
/* 57 */     ShaderRegistry.registerShader_Item(rlName, rarity, colorBackground, colorPrimary, colorSecondary);
/*    */     
/* 59 */     List<ShaderLayer> list = new ArrayList<>();
/* 60 */     extraLayers.accept(Integer.valueOf(colorPrimary), Integer.valueOf(colorSecondary), Integer.valueOf(colorBackground), list);
/* 61 */     list.add(new ShaderLayer(ResourceUtils.ip("projectors/shaders/projector_uncolored"), -1));
/*    */     
/* 63 */     ShaderCaseProjector shader = new ShaderCaseProjector(list);
/* 64 */     return registerCase(rlName, shader, rarity, colorPrimary, colorSecondary, colorBackground, loot, bags);
/*    */   }
/*    */   
/*    */   private static <S extends ShaderCase> ShaderRegistry.ShaderRegistryEntry registerCase(ResourceLocation name, S shader, Rarity rarity, int colorPrimary, int colorSecondary, int colorBackground, boolean loot, boolean bags) {
/* 68 */     ShaderRegistry.registerShaderCase(name, (ShaderCase)shader, rarity);
/*    */     
/* 70 */     for (ShaderRegistry.IShaderRegistryMethod<?> method : (Iterable<ShaderRegistry.IShaderRegistryMethod<?>>)ShaderRegistry.shaderRegistrationMethods) {
/* 71 */       method.apply(name, "0", rarity, colorBackground, colorPrimary, colorSecondary, -1, null, -1);
/*    */     }
/*    */     
/* 74 */     return ((ShaderRegistry.ShaderRegistryEntry)ShaderRegistry.shaderRegistry.get(name))
/* 75 */       .setCrateLoot(loot)
/* 76 */       .setBagLoot(bags)
/* 77 */       .setReplicationCost(() -> new IngredientWithSize(Ingredient.m_204132_(ShaderRegistry.defaultReplicationCost), 10 - ((Integer)ShaderRegistry.rarityWeightMap.get(rarity)).intValue()));
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   private static interface LayerAdder<P, S, B, L> {
/*    */     void accept(P param1P, S param1S, B param1B, L param1L);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPToolShaders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */