/*    */ package flaxbeard.immersivepetroleum.common.blocks.stone;
/*    */ 
/*    */ import flaxbeard.immersivepetroleum.common.blocks.IPBlockBase;
/*    */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.SoundType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.material.Material;
/*    */ 
/*    */ public class AsphaltBlock
/*    */   extends IPBlockBase
/*    */ {
/*    */   protected static final float SPEED_FACTOR = 1.2F;
/*    */   
/*    */   public AsphaltBlock() {
/* 24 */     super(BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60956_(1.2F).m_60913_(2.0F, 10.0F).m_60918_(SoundType.f_56742_).m_60999_());
/*    */   }
/*    */ 
/*    */   
/*    */   public float m_49961_() {
/* 29 */     return speedFactor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void m_5871_(@Nonnull ItemStack stack, BlockGetter worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 34 */     tooltip(stack, worldIn, tooltip, flagIn);
/* 35 */     super.m_5871_(stack, worldIn, tooltip, flagIn);
/*    */   }
/*    */   
/*    */   static void tooltip(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
/* 39 */     if (((Boolean)IPServerConfig.MISCELLANEOUS.asphalt_speed.get()).booleanValue()) {
/* 40 */       MutableComponent out = Component.m_237110_("desc.immersivepetroleum.flavour.asphalt", new Object[] { String.format(Locale.ENGLISH, "%.1f%%", new Object[] { Float.valueOf(20.000008F) }) }).m_130940_(ChatFormatting.GRAY);
/*    */       
/* 42 */       tooltip.add(out);
/*    */     } 
/*    */   }
/*    */   
/*    */   static float speedFactor() {
/* 47 */     if (!((Boolean)IPServerConfig.MISCELLANEOUS.asphalt_speed.get()).booleanValue()) {
/* 48 */       return 1.0F;
/*    */     }
/*    */     
/* 51 */     return 1.2F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\stone\AsphaltBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */