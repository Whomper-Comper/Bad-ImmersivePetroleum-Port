/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModel;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModels;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.network.IPPacketHandler;
/*     */ import flaxbeard.immersivepetroleum.common.network.MessageDebugSync;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.CreativeModeTab;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.client.event.InputEvent;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class DebugItem
/*     */   extends IPItemBase
/*     */ {
/*     */   public enum Modes {
/*  44 */     DISABLED("Disabled"),
/*  45 */     INFO_SPEEDBOAT("Info: Speedboat"),
/*     */     
/*  47 */     SEEDBASED_RESERVOIR("Seed-Based Reservoir: Heatmap"),
/*  48 */     SEEDBASED_RESERVOIR_AREA_TEST("Seed-Based Reservoir: Island Testing"),
/*     */     
/*  50 */     REFRESH_ALL_IPMODELS("Refresh all IPModels"),
/*  51 */     UPDATE_SHAPES("Does nothing without Debugging Enviroment"),
/*  52 */     GENERAL_TEST("This one could be dangerous to trigger!");
/*     */     
/*     */     public final String display;
/*     */     
/*     */     Modes(String display) {
/*  57 */       this.display = display;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component m_7626_(@Nonnull ItemStack stack) {
/*  68 */     return (Component)Component.m_237113_("IP Debugging Tool").m_130940_(ChatFormatting.LIGHT_PURPLE);
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void m_7373_(@Nonnull ItemStack stack, Level worldIn, List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/*  74 */     tooltip.add(Component.m_237113_("[Shift + Scroll-UP/DOWN] Change mode.").m_130940_(ChatFormatting.GRAY));
/*  75 */     Modes mode = getMode(stack);
/*  76 */     if (mode == Modes.DISABLED) {
/*  77 */       tooltip.add(Component.m_237113_("  Disabled.").m_130940_(ChatFormatting.DARK_GRAY));
/*     */     } else {
/*  79 */       tooltip.add(Component.m_237113_("  " + mode.display).m_130940_(ChatFormatting.DARK_GRAY));
/*     */     } 
/*     */     
/*  82 */     tooltip.add(Component.m_237113_("You're not supposed to have this.").m_130940_(ChatFormatting.DARK_RED));
/*  83 */     super.m_7373_(stack, worldIn, tooltip, flagIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m_6787_(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
/*  93 */     if (!worldIn.f_46443_) {
/*  94 */       BlockPos playerPos; ChunkPos cPos; ReservoirIsland island; int chunkX, chunkZ, x, z; double noise; Modes mode = getMode(playerIn.m_21120_(handIn));
/*     */       
/*  96 */       switch (mode) {
/*     */         case GENERAL_TEST:
/*  98 */           if (worldIn.f_46443_);
/*     */ 
/*     */           
/* 101 */           return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
/*     */         
/*     */         case REFRESH_ALL_IPMODELS:
/*     */           try {
/* 105 */             IPModels.getModels().forEach(IPModel::init);
/*     */             
/* 107 */             playerIn.m_5661_((Component)Component.m_237113_("Models refreshed."), true);
/* 108 */           } catch (Exception e) {
/* 109 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 112 */           return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
/*     */         
/*     */         case SEEDBASED_RESERVOIR:
/* 115 */           playerPos = playerIn.m_20183_();
/*     */           
/* 117 */           cPos = new ChunkPos(playerPos);
/* 118 */           chunkX = cPos.m_45604_();
/* 119 */           chunkZ = cPos.m_45605_();
/*     */ 
/*     */           
/* 122 */           x = playerPos.m_123341_() - cPos.m_45604_();
/* 123 */           z = playerPos.m_123343_() - cPos.m_45605_();
/*     */           
/* 125 */           noise = ReservoirHandler.getValueOf(worldIn, chunkX + x, chunkZ + z);
/*     */           
/* 127 */           playerIn.m_5661_((Component)Component.m_237113_("" + chunkX + " " + chunkX + ": " + chunkZ), true);
/*     */           
/* 129 */           return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
/*     */         
/*     */         case SEEDBASED_RESERVOIR_AREA_TEST:
/* 132 */           playerPos = playerIn.m_20183_();
/*     */ 
/*     */           
/* 135 */           if ((island = ReservoirHandler.getIsland(worldIn, playerPos)) != null) {
/* 136 */             int i = playerPos.m_123341_();
/* 137 */             int j = playerPos.m_123343_();
/*     */             
/* 139 */             float pressure = island.getPressure(worldIn, i, j);
/*     */             
/* 141 */             if (playerIn.m_6144_()) {
/* 142 */               island.setAmount(island.getCapacity());
/* 143 */               island.setDirty();
/* 144 */               playerIn.m_5661_((Component)Component.m_237113_("Island Refilled."), true);
/* 145 */               return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
/*     */             } 
/*     */             
/* 148 */             String out = String.format(Locale.ENGLISH, "Noise: %.3f, Amount: %d/%d, Pressure: %.3f, Flow: %d, Type: %s", new Object[] {
/*     */                   
/* 150 */                   Double.valueOf(ReservoirHandler.getValueOf(worldIn, i, j)), 
/* 151 */                   Long.valueOf(island.getAmount()), 
/* 152 */                   Long.valueOf(island.getCapacity()), 
/* 153 */                   Float.valueOf(pressure), 
/* 154 */                   Integer.valueOf(ReservoirIsland.getFlow(pressure)), (new FluidStack(island
/* 155 */                     .getFluid(), 1)).getDisplayName().getString()
/*     */                 });
/* 157 */             playerIn.m_5661_((Component)Component.m_237113_(out), true);
/*     */           } 
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
/* 173 */           return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 178 */       return new InteractionResultHolder(InteractionResult.PASS, playerIn.m_21120_(handIn));
/*     */     } 
/*     */     
/* 181 */     return super.m_7203_(worldIn, playerIn, handIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6225_(UseOnContext context) {
/*     */     Level world;
/* 188 */     Player player = context.m_43723_();
/* 189 */     if (player == null) {
/* 190 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 193 */     ItemStack held = player.m_21120_(context.m_43724_());
/* 194 */     Modes mode = getMode(held);
/*     */     
/* 196 */     BlockEntity te = context.m_43725_().m_7702_(context.m_8083_());
/* 197 */     switch (mode) {
/*     */       case GENERAL_TEST:
/* 199 */         world = context.m_43725_();
/* 200 */         if (!world.f_46443_) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 208 */           BlockPos pos = context.m_8083_();
/*     */           
/* 210 */           ResourceLocation dimensionRL = world.m_46472_().m_135782_();
/* 211 */           ResourceLocation biomeRL = RegistryUtils.getRegistryNameOf((Biome)world.m_204166_(pos).m_203334_());
/*     */           
/* 213 */           player.m_5661_((Component)Component.m_237113_(dimensionRL.toString()), false);
/*     */           
/* 215 */           for (ReservoirType res : ReservoirType.map.values()) {
/* 216 */             ReservoirType.BWList dims = res.getDimensions();
/* 217 */             ReservoirType.BWList biom = res.getBiomes();
/*     */             
/* 219 */             boolean validDimension = dims.valid(dimensionRL);
/* 220 */             boolean validBiome = biom.valid(biomeRL);
/*     */ 
/*     */ 
/*     */             
/* 224 */             MutableComponent component = Component.m_237113_(res.name).m_7220_((Component)Component.m_237113_(" Dimension").m_130940_(validDimension ? ChatFormatting.GREEN : ChatFormatting.RED)).m_7220_((Component)Component.m_237113_(" Biome").m_130940_(validBiome ? ChatFormatting.GREEN : ChatFormatting.RED));
/*     */             
/* 226 */             if (validDimension && validBiome) {
/* 227 */               component = component.m_130946_(" (can spawn here)");
/*     */             }
/*     */             
/* 230 */             player.m_5661_((Component)component, false);
/*     */           } 
/*     */         } 
/*     */         
/* 234 */         return InteractionResult.SUCCESS;
/*     */       
/*     */       case UPDATE_SHAPES:
/* 237 */         return InteractionResult.PASS;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public void onSpeedboatClick(MotorboatEntity speedboatEntity, Player player, ItemStack debugStack) {
/* 247 */     if (speedboatEntity.f_19853_.f_46443_ || getMode(debugStack) != Modes.INFO_SPEEDBOAT) {
/*     */       return;
/*     */     }
/*     */     
/* 251 */     MutableComponent textOut = Component.m_237113_("-- Speedboat --\n");
/*     */     
/* 253 */     FluidStack fluid = speedboatEntity.getContainedFluid();
/* 254 */     if (fluid == FluidStack.EMPTY) {
/* 255 */       textOut.m_130946_("Tank: Empty");
/*     */     } else {
/* 257 */       textOut.m_130946_("Tank: " + fluid.getAmount() + "/" + speedboatEntity.getMaxFuel() + "mB of ").m_7220_(fluid.getDisplayName());
/*     */     } 
/*     */     
/* 260 */     MutableComponent upgradesText = Component.m_237113_("\n");
/* 261 */     NonNullList<ItemStack> upgrades = speedboatEntity.getUpgrades();
/* 262 */     int i = 0;
/* 263 */     for (ItemStack upgrade : upgrades) {
/* 264 */       if (upgrade == null || upgrade == ItemStack.f_41583_) {
/* 265 */         upgradesText.m_130946_("Upgrade " + ++i + ": Empty\n"); continue;
/*     */       } 
/* 267 */       upgradesText.m_130946_("Upgrade " + i++ + ": ").m_7220_(upgrade.m_41786_()).m_130946_("\n");
/*     */     } 
/*     */     
/* 270 */     textOut.m_7220_((Component)upgradesText);
/*     */     
/* 272 */     player.m_213846_((Component)textOut);
/*     */   }
/*     */   
/*     */   public static void setModeServer(ItemStack stack, Modes mode) {
/* 276 */     CompoundTag nbt = getSettings(stack);
/* 277 */     nbt.m_128405_("mode", mode.ordinal());
/*     */   }
/*     */   
/*     */   public static void setModeClient(ItemStack stack, Modes mode) {
/* 281 */     CompoundTag nbt = getSettings(stack);
/* 282 */     nbt.m_128405_("mode", mode.ordinal());
/* 283 */     IPPacketHandler.sendToServer(new MessageDebugSync(nbt));
/*     */   }
/*     */   
/*     */   public static Modes getMode(ItemStack stack) {
/* 287 */     CompoundTag nbt = getSettings(stack);
/* 288 */     if (nbt.m_128441_("mode")) {
/* 289 */       int mode = nbt.m_128451_("mode");
/*     */       
/* 291 */       if (mode < 0 || mode >= (Modes.values()).length) {
/* 292 */         mode = 0;
/*     */       }
/* 294 */       return Modes.values()[mode];
/*     */     } 
/* 296 */     return Modes.DISABLED;
/*     */   }
/*     */   
/*     */   public static CompoundTag getSettings(ItemStack stack) {
/* 300 */     return stack.m_41698_("settings");
/*     */   }
/*     */   
/*     */   public static class ClientInputHandler
/*     */   {
/*     */     public static void onSneakScrolling(InputEvent.MouseScrollingEvent event, Player player, double scrollDelta, boolean isSneaking) {
/* 306 */       ItemStack mainItem = player.m_21205_();
/* 307 */       ItemStack secondItem = player.m_21206_();
/* 308 */       boolean main = (!mainItem.m_41619_() && mainItem.m_41720_() == IPContent.DEBUGITEM.get());
/* 309 */       boolean off = (!secondItem.m_41619_() && secondItem.m_41720_() == IPContent.DEBUGITEM.get());
/*     */       
/* 311 */       if (main || off) {
/* 312 */         ItemStack target = main ? mainItem : secondItem;
/*     */         
/* 314 */         DebugItem.Modes mode = DebugItem.getMode(target);
/* 315 */         int id = mode.ordinal() + (int)scrollDelta;
/* 316 */         if (id < 0) {
/* 317 */           id = (DebugItem.Modes.values()).length - 1;
/*     */         }
/* 319 */         if (id >= (DebugItem.Modes.values()).length) {
/* 320 */           id = 0;
/*     */         }
/* 322 */         mode = DebugItem.Modes.values()[id];
/*     */         
/* 324 */         DebugItem.setModeClient(target, mode);
/* 325 */         player.m_5661_((Component)Component.m_237113_(mode.display), true);
/* 326 */         event.setCanceled(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\DebugItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */