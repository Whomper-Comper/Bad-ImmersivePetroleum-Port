/*    */ package flaxbeard.immersivepetroleum.common.util;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.PlayerAdvancements;
/*    */ import net.minecraft.server.ServerAdvancementManager;
/*    */ import net.minecraft.server.level.ColumnPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraftforge.common.capabilities.ForgeCapabilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Utils
/*    */ {
/* 26 */   static final DecimalFormat FORMATTER = new DecimalFormat("#,###.##");
/*    */   
/*    */   public static String fDecimal(byte number) {
/* 29 */     return FORMATTER.format(number);
/*    */   }
/*    */   
/*    */   public static String fDecimal(short number) {
/* 33 */     return FORMATTER.format(number);
/*    */   }
/*    */   
/*    */   public static String fDecimal(int number) {
/* 37 */     return FORMATTER.format(number);
/*    */   }
/*    */   
/*    */   public static String fDecimal(long number) {
/* 41 */     return FORMATTER.format(number);
/*    */   }
/*    */   
/*    */   public static String fDecimal(float number) {
/* 45 */     return FORMATTER.format(number);
/*    */   }
/*    */   
/*    */   public static String fDecimal(double number) {
/* 49 */     return FORMATTER.format(number);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void unlockIPAdvancement(Player player, String name) {
/* 54 */     if (player instanceof ServerPlayer) { ServerPlayer serverPlayer = (ServerPlayer)player;
/* 55 */       PlayerAdvancements advancements = serverPlayer.m_8960_();
/* 56 */       ServerAdvancementManager manager = ((ServerLevel)serverPlayer.m_20193_()).m_7654_().m_129889_();
/* 57 */       Advancement advancement = manager.m_136041_(ResourceUtils.ip(name));
/* 58 */       if (advancement != null) {
/* 59 */         advancements.m_135988_(advancement, "code_trigger");
/*    */       } }
/*    */   
/*    */   }
/*    */   
/*    */   public static boolean isFluidRelatedItemStack(ItemStack stack) {
/* 65 */     if (stack.m_41619_())
/* 66 */       return false; 
/* 67 */     return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
/*    */   }
/*    */   
/*    */   public static void dropItem(Level level, BlockPos pos, ItemStack stack) {
/* 71 */     dropItem(level, pos, stack, ItemEntity::m_32060_);
/*    */   }
/*    */   
/*    */   public static void dropItemNoDelay(Level level, BlockPos pos, ItemStack stack) {
/* 75 */     dropItem(level, pos, stack, ItemEntity::m_32061_);
/*    */   }
/*    */   
/*    */   public static void dropItem(Level level, BlockPos pos, ItemStack stack, Consumer<ItemEntity> func) {
/* 79 */     if (!level.f_46443_ && !stack.m_41619_() && !level.restoringBlockSnapshots) {
/* 80 */       double f = EntityType.f_20461_.m_20679_() / 2.0D;
/* 81 */       double x = pos.m_123341_() + 0.5D;
/* 82 */       double y = pos.m_123342_() + 0.5D - f;
/* 83 */       double z = pos.m_123343_() + 0.5D;
/*    */       
/* 85 */       ItemEntity entity = new ItemEntity(level, x, y, z, stack);
/* 86 */       func.accept(entity);
/* 87 */       level.m_7967_((Entity)entity);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ColumnPos toColumnPos(BlockPos pos) {
/* 92 */     return new ColumnPos(pos.m_123341_(), pos.m_123343_());
/*    */   }
/*    */   
/*    */   public static boolean hasKey(ItemStack stack, String key, int tagId) {
/* 96 */     return (stack.m_41782_() && stack.m_41783_().m_128425_(key, tagId));
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\Utils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */