/*    */ package flaxbeard.immersivepetroleum.common.items;
/*    */ 
/*    */ import blusunrize.immersiveengineering.api.tool.IUpgrade;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.api.distmarker.OnlyIn;
/*    */ import net.minecraftforge.registries.ForgeRegistries;
/*    */ 
/*    */ public class IPUpgradeItem
/*    */   extends IPItemBase
/*    */   implements IUpgrade {
/*    */   private Set<String> set;
/*    */   
/*    */   public IPUpgradeItem(String type) {
/* 25 */     super((new Item.Properties()).m_41487_(1).m_41491_(ImmersivePetroleum.creativeTab));
/* 26 */     this.set = (Set<String>)ImmutableSet.of(type);
/*    */   }
/*    */ 
/*    */   
/*    */   @OnlyIn(Dist.CLIENT)
/*    */   public void m_7373_(@Nonnull ItemStack stack, Level worldIn, List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 32 */     tooltip.add(Component.m_237115_("desc.immersivepetroleum.flavour." + ForgeRegistries.ITEMS.getKey(this).m_135815_()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getUpgradeTypes(ItemStack upgrade) {
/* 37 */     return this.set;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canApplyUpgrades(ItemStack target, ItemStack upgrade) {
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public void applyUpgrades(ItemStack target, ItemStack upgrade, CompoundTag modifications) {}
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\IPUpgradeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */