/*    */ package flaxbeard.immersivepetroleum.api;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IPTags
/*    */ {
/* 21 */   private static final Map<TagKey<Block>, TagKey<Item>> toItemTag = new HashMap<>();
/*    */   
/*    */   public static class Blocks {
/* 24 */     public static final TagKey<Block> asphalt = IPTags.createBlockTag(ResourceUtils.forge("asphalt"));
/* 25 */     public static final TagKey<Block> petcoke = IPTags.createBlockTag(ResourceUtils.forge("storage_blocks/petcoke"));
/* 26 */     public static final TagKey<Block> waxBlock = IPTags.createBlockTag(ResourceUtils.forge("storage_blocks/wax"));
/* 27 */     public static final TagKey<Block> paraffinWaxBlock = IPTags.createBlockTag(ResourceUtils.forge("storage_blocks/wax/paraffin"));
/*    */   }
/*    */   
/*    */   public static class Items {
/* 31 */     public static final TagKey<Item> bitumen = IPTags.createItemWrapper(ResourceUtils.forge("bitumen"));
/* 32 */     public static final TagKey<Item> petcoke = IPTags.createItemWrapper(ResourceUtils.forge("coal_petcoke"));
/* 33 */     public static final TagKey<Item> petcokeDust = IPTags.createItemWrapper(ResourceUtils.forge("dusts/coal_petcoke"));
/* 34 */     public static final TagKey<Item> petcokeStorage = IPTags.createItemWrapper(ResourceUtils.forge("storage_blocks/coal_petcoke"));
/* 35 */     public static final TagKey<Item> paraffinWax = IPTags.createItemWrapper(ResourceUtils.forge("wax/paraffin"));
/* 36 */     public static final TagKey<Item> wax = IPTags.createItemWrapper(ResourceUtils.forge("wax"));
/* 37 */     public static final TagKey<Item> waxBlock = IPTags.createItemWrapper(ResourceUtils.forge("storage_blocks/wax"));
/* 38 */     public static final TagKey<Item> paraffinWaxBlock = IPTags.createItemWrapper(ResourceUtils.forge("storage_blocks/wax/paraffin"));
/* 39 */     public static final TagKey<Item> wool = IPTags.createItemWrapper(ResourceUtils.mc("wool"));
/*    */   }
/*    */   
/*    */   public static class Fluids {
/* 43 */     public static final TagKey<Fluid> crudeOil = IPTags.createFluidWrapper(ResourceUtils.forge("crude_oil"));
/* 44 */     public static final TagKey<Fluid> diesel = IPTags.createFluidWrapper(ResourceUtils.forge("diesel"));
/* 45 */     public static final TagKey<Fluid> diesel_sulfur = IPTags.createFluidWrapper(ResourceUtils.forge("diesel_sulfur"));
/* 46 */     public static final TagKey<Fluid> gasoline = IPTags.createFluidWrapper(ResourceUtils.forge("gasoline"));
/* 47 */     public static final TagKey<Fluid> lubricant = IPTags.createFluidWrapper(ResourceUtils.forge("lubricant"));
/* 48 */     public static final TagKey<Fluid> napalm = IPTags.createFluidWrapper(ResourceUtils.forge("napalm"));
/* 49 */     public static final TagKey<Fluid> naphtha = IPTags.createFluidWrapper(ResourceUtils.forge("naphtha"));
/* 50 */     public static final TagKey<Fluid> naphtha_cracked = IPTags.createFluidWrapper(ResourceUtils.forge("naphtha_cracked"));
/* 51 */     public static final TagKey<Fluid> benzene = IPTags.createFluidWrapper(ResourceUtils.forge("benzene"));
/* 52 */     public static final TagKey<Fluid> propylene = IPTags.createFluidWrapper(ResourceUtils.forge("propylene"));
/* 53 */     public static final TagKey<Fluid> ethylene = IPTags.createFluidWrapper(ResourceUtils.forge("ethylene"));
/* 54 */     public static final TagKey<Fluid> lubricant_cracked = IPTags.createFluidWrapper(ResourceUtils.forge("lubricant_cracked"));
/* 55 */     public static final TagKey<Fluid> kerosene = IPTags.createFluidWrapper(ResourceUtils.forge("kerosene"));
/* 56 */     public static final TagKey<Fluid> gasoline_additives = IPTags.createFluidWrapper(ResourceUtils.forge("gasoline_additives"));
/*    */     
/* 58 */     public static final TagKey<Fluid> water = IPTags.createFluidWrapper(ResourceUtils.mc("water"));
/* 59 */     public static final TagKey<Fluid> concrete = IPTags.createFluidWrapper(ResourceUtils.forge("concrete"));
/*    */   }
/*    */   
/*    */   public static class Utility {
/* 63 */     public static final TagKey<Fluid> burnableInFlarestack = IPTags.createFluidWrapper(ResourceUtils.ip("burnable_in_flarestack"));
/* 64 */     public static final TagKey<Item> toolboxTools = IPTags.createItemWrapper(ResourceUtils.ie("toolbox/tools"));
/*    */   }
/*    */   
/*    */   public static TagKey<Item> getItemTag(TagKey<Block> blockTag) {
/* 68 */     Preconditions.checkArgument(toItemTag.containsKey(blockTag));
/* 69 */     return toItemTag.get(blockTag);
/*    */   }
/*    */   
/*    */   private static TagKey<Block> createBlockTag(ResourceLocation name) {
/* 73 */     TagKey<Block> blockTag = createBlockWrapper(name);
/* 74 */     toItemTag.put(blockTag, createItemWrapper(name));
/* 75 */     return blockTag;
/*    */   }
/*    */   
/*    */   public static void forAllBlocktags(BiConsumer<TagKey<Block>, TagKey<Item>> out) {
/* 79 */     for (Map.Entry<TagKey<Block>, TagKey<Item>> entry : toItemTag.entrySet())
/* 80 */       out.accept(entry.getKey(), entry.getValue()); 
/*    */   }
/*    */   
/*    */   private static TagKey<Block> createBlockWrapper(ResourceLocation name) {
/* 84 */     return BlockTags.create(name);
/*    */   }
/*    */   
/*    */   private static TagKey<Item> createItemWrapper(ResourceLocation name) {
/* 88 */     return ItemTags.create(name);
/*    */   }
/*    */   
/*    */   private static TagKey<Fluid> createFluidWrapper(ResourceLocation name) {
/* 92 */     return FluidTags.create(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\IPTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */