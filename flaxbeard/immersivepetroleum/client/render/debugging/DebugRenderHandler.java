/*     */ package flaxbeard.immersivepetroleum.client.render.debugging;
/*     */ 
/*     */ import blusunrize.immersiveengineering.client.utils.GuiHelper;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Matrix3f;
/*     */ import com.mojang.math.Matrix4f;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.AxisAlignedIslandBB;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.ReservoirRegionDataStorage;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.FlarestackTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.GasGeneratorTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.OilTankTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellPipeTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.WellTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.items.DebugItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.RedStoneWireBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.client.event.RenderGuiOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderLevelStageEvent;
/*     */ import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugRenderHandler
/*     */ {
/*     */   private boolean isHoldingDebugItem(Player player) {
/*  85 */     ItemStack main = player.m_21120_(InteractionHand.MAIN_HAND);
/*  86 */     ItemStack off = player.m_21120_(InteractionHand.OFF_HAND);
/*     */     
/*  88 */     return ((main != ItemStack.f_41583_ && main.m_41720_() == IPContent.DEBUGITEM.get()) || (off != ItemStack.f_41583_ && off.m_41720_() == IPContent.DEBUGITEM.get()));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderDebuggingOverlay(RenderGuiOverlayEvent.Post event) {
/*  93 */     Minecraft mc = Minecraft.m_91087_();
/*     */     
/*  95 */     if (mc.f_91074_ != null && event.getOverlay().id() == VanillaGuiOverlay.DEBUG_TEXT.id()) {
/*  96 */       LocalPlayer localPlayer = mc.f_91074_;
/*     */       
/*  98 */       if (isHoldingDebugItem((Player)localPlayer)) {
/*  99 */         HitResult rt = mc.f_91077_;
/* 100 */         if (rt != null) {
/* 101 */           BlockHitResult blockHitResult; EntityHitResult result; Level world; BlockState blockState; Entity entity; List<Component> debugOut; switch (rt.m_6662_()) {
/*     */             case BLOCK:
/* 103 */               blockHitResult = (BlockHitResult)rt;
/* 104 */               world = ((Player)localPlayer).f_19853_;
/*     */               
/* 106 */               blockState = world.m_8055_(blockHitResult.m_82425_());
/*     */               
/* 108 */               debugOut = new ArrayList<>();
/*     */               
/* 110 */               if (blockState.m_60734_() instanceof net.minecraft.world.level.block.EntityBlock) {
/* 111 */                 BlockEntity te = world.m_7702_(blockHitResult.m_82425_());
/*     */                 
/* 113 */                 if (te instanceof GasGeneratorTileEntity) { GasGeneratorTileEntity gas = (GasGeneratorTileEntity)te;
/* 114 */                   debugOut.add(toTranslation(te.m_58900_().m_60734_().m_7705_(), new Object[0]).m_130940_(ChatFormatting.GOLD)); }
/*     */                 
/* 116 */                 else if (te instanceof flaxbeard.immersivepetroleum.common.blocks.tileentities.IPTileEntityBase)
/* 117 */                 { debugOut.add(toTranslation(te.m_58900_().m_60734_().m_7705_(), new Object[0]).m_130940_(ChatFormatting.GOLD));
/*     */                   
/* 119 */                   if (te instanceof AutoLubricatorTileEntity) { AutoLubricatorTileEntity autolube = (AutoLubricatorTileEntity)te;
/* 120 */                     FluidTank tank = autolube.tank;
/* 121 */                     FluidStack fs = tank.getFluid();
/*     */                     
/* 123 */                     debugOut.add(toText("isSlave").m_130940_(autolube.isSlave ? ChatFormatting.GREEN : ChatFormatting.RED));
/* 124 */                     if (!autolube.isSlave) {
/* 125 */                       debugOut.add(toText("Facing: " + autolube.facing.m_122433_()));
/* 126 */                       debugOut.add(toText("Tank: " + fs.getAmount() + "/" + tank.getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */                     }
/*     */                      }
/* 129 */                   else if (te instanceof FlarestackTileEntity) { FlarestackTileEntity flare = (FlarestackTileEntity)te; }
/* 130 */                   else if (te instanceof WellTileEntity) { WellTileEntity well = (WellTileEntity)te; }
/* 131 */                   else if (te instanceof WellPipeTileEntity) { WellPipeTileEntity wellPipeTileEntity = (WellPipeTileEntity)te; }
/*     */                   
/*     */                    }
/* 134 */                 else if (te instanceof MultiblockPartBlockEntity) { MultiblockPartBlockEntity<?> generic = (MultiblockPartBlockEntity)te;
/*     */                   
/* 136 */                   BlockPos tPos = generic.posInMultiblock;
/* 137 */                   if (!generic.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 138 */                     generic = generic.master();
/*     */                   }
/* 140 */                   Block block = generic.m_58900_().m_60734_();
/*     */                   
/* 142 */                   debugOut.add(toText("Template XYZ: " + tPos.m_123341_() + ", " + tPos.m_123342_() + ", " + tPos.m_123343_()));
/*     */                   
/* 144 */                   MutableComponent name = toTranslation(block.m_7705_(), new Object[0]).m_130940_(ChatFormatting.GOLD);
/*     */                   
/*     */                   try {
/* 147 */                     name.m_7220_((Component)toText(generic.isRSDisabled() ? " (Redstoned)" : "").m_130940_(ChatFormatting.RED));
/* 148 */                   } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */ 
/*     */ 
/*     */                   
/* 152 */                   if (generic instanceof PoweredMultiblockBlockEntity) { PoweredMultiblockBlockEntity<?, ?> poweredGeneric = (PoweredMultiblockBlockEntity)generic;
/* 153 */                     name.m_7220_((Component)toText(poweredGeneric.shouldRenderAsActive() ? " (Active)" : "").m_130940_(ChatFormatting.GREEN));
/* 154 */                     debugOut.add(toText("" + poweredGeneric.energyStorage.getEnergyStored() + "/" + poweredGeneric.energyStorage.getEnergyStored() + "RF")); }
/*     */ 
/*     */                   
/* 157 */                   synchronized (LubricatedHandler.lubricatedTiles) {
/* 158 */                     for (LubricatedHandler.LubricatedTileInfo info : LubricatedHandler.lubricatedTiles) {
/* 159 */                       if (info.pos.equals(generic.m_58899_())) {
/* 160 */                         name.m_7220_((Component)toText(" (Lubricated " + info.ticks + ")").m_130940_(ChatFormatting.YELLOW));
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                   
/* 165 */                   debugOut.add(name);
/*     */ 
/*     */                   
/* 168 */                   if (te instanceof DistillationTowerTileEntity) { DistillationTowerTileEntity tower = (DistillationTowerTileEntity)te;
/* 169 */                     distillationtower(debugOut, tower); }
/*     */                   
/* 171 */                   else if (te instanceof CokerUnitTileEntity) { CokerUnitTileEntity coker = (CokerUnitTileEntity)te;
/* 172 */                     cokerunit(debugOut, coker); }
/*     */                   
/* 174 */                   else if (te instanceof HydrotreaterTileEntity) { HydrotreaterTileEntity treater = (HydrotreaterTileEntity)te;
/* 175 */                     hydrotreater(debugOut, treater); }
/*     */                   
/* 177 */                   else if (te instanceof OilTankTileEntity) { OilTankTileEntity oiltank = (OilTankTileEntity)te;
/* 178 */                     oiltank(debugOut, oiltank); }
/*     */                   
/* 180 */                   else if (te instanceof DerrickTileEntity) { DerrickTileEntity derrick = (DerrickTileEntity)te;
/* 181 */                     derrick(debugOut, derrick); }
/*     */                   
/*     */                    }
/*     */               
/* 185 */               } else if (blockState.m_60734_() instanceof RedStoneWireBlock) {
/* 186 */                 debugOut.add(toText("Redstone Wire").m_130940_(ChatFormatting.GOLD));
/* 187 */                 debugOut.add(toText("Power: " + blockState.m_61143_((Property)RedStoneWireBlock.f_55500_)));
/*     */               } 
/*     */ 
/*     */               
/* 191 */               if (!debugOut.isEmpty()) {
/* 192 */                 BlockPos hit = blockHitResult.m_82425_();
/* 193 */                 debugOut.add(0, toText("World XYZ: " + hit.m_123341_() + ", " + hit.m_123342_() + ", " + hit.m_123343_()));
/*     */                 
/* 195 */                 renderOverlay(event.getPoseStack(), debugOut);
/*     */               } 
/*     */               return;
/*     */             case ENTITY:
/* 199 */               result = (EntityHitResult)rt;
/*     */               
/* 201 */               entity = result.m_82443_(); if (entity instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity;
/*     */                 
/* 203 */                 List<Component> list = new ArrayList<>();
/*     */                 
/* 205 */                 list.add(toText("").m_7220_(boat.m_5446_()).m_130940_(ChatFormatting.GOLD));
/*     */                 
/* 207 */                 FluidStack fluid = boat.getContainedFluid();
/* 208 */                 if (fluid == FluidStack.EMPTY) {
/* 209 */                   list.add(toText("Tank: Empty"));
/*     */                 } else {
/* 211 */                   list.add(toText("Tank: " + fluid.getAmount() + "/" + boat.getMaxFuel() + "mB of ").m_7220_(fluid.getDisplayName()));
/*     */                 } 
/*     */                 
/* 214 */                 NonNullList<ItemStack> upgrades = boat.getUpgrades();
/* 215 */                 int i = 0;
/* 216 */                 for (ItemStack upgrade : upgrades) {
/* 217 */                   if (upgrade == null || upgrade == ItemStack.f_41583_) {
/* 218 */                     list.add(toText("Upgrade " + ++i + ": Empty")); continue;
/*     */                   } 
/* 220 */                   list.add(toText("Upgrade " + ++i + ": ").m_7220_(upgrade.m_41786_()));
/*     */                 } 
/*     */ 
/*     */                 
/* 224 */                 renderOverlay(event.getPoseStack(), list); }
/*     */               
/*     */               return;
/*     */           } 
/* 228 */           boolean debug = false;
/* 229 */           if (debug) {
/* 230 */             ReservoirRegionDataStorage storage = ReservoirRegionDataStorage.get();
/* 231 */             BlockPos playerPos = MCUtil.getPlayer().m_20183_();
/*     */             
/* 233 */             ReservoirRegionDataStorage.RegionPos rLocal = new ReservoirRegionDataStorage.RegionPos(playerPos);
/*     */             
/* 235 */             ReservoirRegionDataStorage.RegionPos r0 = new ReservoirRegionDataStorage.RegionPos(playerPos, 1, -1);
/* 236 */             ReservoirRegionDataStorage.RegionPos r1 = new ReservoirRegionDataStorage.RegionPos(playerPos, 1, 1);
/* 237 */             ReservoirRegionDataStorage.RegionPos r2 = new ReservoirRegionDataStorage.RegionPos(playerPos, -1, -1);
/* 238 */             ReservoirRegionDataStorage.RegionPos r3 = new ReservoirRegionDataStorage.RegionPos(playerPos, -1, 1);
/*     */             
/* 240 */             boolean bLocal = (storage.getRegionData(rLocal) != null);
/*     */             
/* 242 */             boolean b0 = (storage.getRegionData(r0) != null);
/* 243 */             boolean b1 = (storage.getRegionData(r1) != null);
/* 244 */             boolean b2 = (storage.getRegionData(r2) != null);
/* 245 */             boolean b3 = (storage.getRegionData(r3) != null);
/*     */             
/* 247 */             List<Component> list = (List)List.of(
/* 248 */                 Component.m_237113_(String.format("PlayerXYZ: %d %d %d", new Object[] { Integer.valueOf(playerPos.m_123341_()), Integer.valueOf(playerPos.m_123342_()), Integer.valueOf(playerPos.m_123343_())
/* 249 */                     })), Component.m_237113_(String.format("LocalXZ: %d %d", new Object[] { Integer.valueOf(rLocal.x()), Integer.valueOf(rLocal.z()) })).m_130940_(bLocal ? ChatFormatting.GREEN : ChatFormatting.RED), 
/* 250 */                 Component.m_237113_(String.format("XZ: %d %d", new Object[] { Integer.valueOf(r0.x()), Integer.valueOf(r0.z()) })).m_130940_(b0 ? ChatFormatting.GREEN : ChatFormatting.RED), 
/* 251 */                 Component.m_237113_(String.format("XZ: %d %d", new Object[] { Integer.valueOf(r1.x()), Integer.valueOf(r1.z()) })).m_130940_(b1 ? ChatFormatting.GREEN : ChatFormatting.RED), 
/* 252 */                 Component.m_237113_(String.format("XZ: %d %d", new Object[] { Integer.valueOf(r2.x()), Integer.valueOf(r2.z()) })).m_130940_(b2 ? ChatFormatting.GREEN : ChatFormatting.RED), 
/* 253 */                 Component.m_237113_(String.format("XZ: %d %d", new Object[] { Integer.valueOf(r3.x()), Integer.valueOf(r3.z()) })).m_130940_(b3 ? ChatFormatting.GREEN : ChatFormatting.RED));
/*     */             
/* 255 */             renderOverlay(event.getPoseStack(), list);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderLevelStage(RenderLevelStageEvent event) {
/* 266 */     if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
/* 267 */       reservoirDebuggingRender(event);
/*     */     }
/*     */   }
/*     */   
/*     */   private void reservoirDebuggingRender(RenderLevelStageEvent event) {
/* 272 */     if (ReservoirHandler.getGenerator() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 276 */     LocalPlayer localPlayer = MCUtil.getPlayer();
/*     */     
/* 278 */     ItemStack main = localPlayer.m_21120_(InteractionHand.MAIN_HAND);
/* 279 */     ItemStack off = localPlayer.m_21120_(InteractionHand.OFF_HAND);
/*     */     
/* 281 */     if ((main != ItemStack.f_41583_ && main.m_41720_() == IPContent.DEBUGITEM.get()) || (off != ItemStack.f_41583_ && off.m_41720_() == IPContent.DEBUGITEM.get())) {
/* 282 */       DebugItem.Modes mode = null;
/* 283 */       if (main != ItemStack.f_41583_) {
/* 284 */         mode = DebugItem.getMode(main);
/*     */       }
/* 286 */       if (off != ItemStack.f_41583_) {
/* 287 */         mode = DebugItem.getMode(off);
/*     */       }
/*     */       
/* 290 */       if (mode == DebugItem.Modes.SEEDBASED_RESERVOIR || mode == DebugItem.Modes.SEEDBASED_RESERVOIR_AREA_TEST) {
/* 291 */         PoseStack matrix = event.getPoseStack();
/* 292 */         Level world = localPlayer.m_20193_();
/* 293 */         BlockPos playerPos = localPlayer.m_20183_();
/*     */         
/* 295 */         matrix.m_85836_();
/*     */ 
/*     */         
/* 298 */         Vec3 renderView = MCUtil.getGameRenderer().m_109153_().m_90583_();
/* 299 */         matrix.m_85837_(-renderView.f_82479_, -renderView.f_82480_, -renderView.f_82481_);
/*     */         
/* 301 */         matrix.m_85836_();
/*     */         
/* 303 */         MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */         
/* 305 */         int radius = 12;
/* 306 */         for (int i = -radius; i <= radius; i++) {
/* 307 */           for (int j = -radius; j <= radius; j++) {
/* 308 */             ChunkPos cPos = new ChunkPos(playerPos.m_7918_(16 * i, 0, 16 * j));
/* 309 */             int chunkX = cPos.m_45604_();
/* 310 */             int chunkZ = cPos.m_45605_();
/*     */             
/* 312 */             for (int cX = 0; cX < 16; cX++) {
/* 313 */               for (int cZ = 0; cZ < 16; cZ++) {
/* 314 */                 int x = chunkX + cX;
/* 315 */                 int z = chunkZ + cZ;
/*     */                 
/* 317 */                 matrix.m_85836_();
/*     */                 
/* 319 */                 double n = ReservoirHandler.getValueOf(world, x, z);
/* 320 */                 if (n > -1.0D) {
/* 321 */                   int c = (int)Math.round(9.0D * n);
/*     */                   
/* 323 */                   switch (c) { case 1: 
/*     */                     case 2: 
/*     */                     case 3: 
/*     */                     case 4: 
/*     */                     case 5: 
/*     */                     case 6: 
/*     */                     case 7: 
/*     */                     default:
/* 331 */                       if (c > 7); break; }  DyeColor color = DyeColor.BLACK;
/*     */ 
/*     */                   
/* 334 */                   int r = (color.m_41071_() & 0xFF0000) >> 16;
/* 335 */                   int g = (color.m_41071_() & 0xFF00) >> 8;
/* 336 */                   int b = color.m_41071_() & 0xFF;
/*     */                   
/* 338 */                   int height = world.m_5452_(Heightmap.Types.WORLD_SURFACE, new BlockPos(x, 0, z)).m_123342_();
/* 339 */                   for (; height > 0 && 
/* 340 */                     !world.m_8055_(new BlockPos(x, height - 1, z)).m_60804_((BlockGetter)world, new BlockPos(x, height - 1, z)); height--);
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 345 */                   matrix.m_85837_(x, Math.max(63, height) + 0.0625D, z);
/*     */                   
/* 347 */                   Matrix4f mat = matrix.m_85850_().m_85861_();
/*     */                   
/* 349 */                   VertexConsumer builder = buffer.m_6299_(IPRenderTypes.TRANSLUCENT_POSITION_COLOR);
/* 350 */                   builder.m_85982_(mat, 0.0F, 0.0F, 0.0F).m_6122_(r, g, b, 127).m_5752_();
/* 351 */                   builder.m_85982_(mat, 0.0F, 0.0F, 1.0F).m_6122_(r, g, b, 127).m_5752_();
/* 352 */                   builder.m_85982_(mat, 1.0F, 0.0F, 1.0F).m_6122_(r, g, b, 127).m_5752_();
/* 353 */                   builder.m_85982_(mat, 1.0F, 0.0F, 0.0F).m_6122_(r, g, b, 127).m_5752_();
/*     */                 } 
/*     */                 
/* 356 */                 matrix.m_85849_();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 361 */         buffer.m_109911_();
/*     */         
/* 363 */         matrix.m_85849_();
/*     */         
/* 365 */         matrix.m_85836_();
/*     */         
/* 367 */         ReservoirRegionDataStorage storage = ReservoirRegionDataStorage.get();
/* 368 */         ResourceKey<Level> dimKey = localPlayer.m_20193_().m_46472_();
/* 369 */         Set<ReservoirIsland> islands = new HashSet<>();
/*     */         
/* 371 */         ReservoirRegionDataStorage.RegionPos pLocal = new ReservoirRegionDataStorage.RegionPos(playerPos);
/* 372 */         ReservoirRegionDataStorage.RegionPos p0 = new ReservoirRegionDataStorage.RegionPos(playerPos, 1, -1);
/* 373 */         ReservoirRegionDataStorage.RegionPos p1 = new ReservoirRegionDataStorage.RegionPos(playerPos, 1, 1);
/* 374 */         ReservoirRegionDataStorage.RegionPos p2 = new ReservoirRegionDataStorage.RegionPos(playerPos, -1, -1);
/* 375 */         ReservoirRegionDataStorage.RegionPos p3 = new ReservoirRegionDataStorage.RegionPos(playerPos, -1, 1);
/*     */         
/* 377 */         ReservoirRegionDataStorage.RegionData rLocal = storage.getRegionData(pLocal);
/* 378 */         ReservoirRegionDataStorage.RegionData r0 = storage.getRegionData(p0);
/* 379 */         ReservoirRegionDataStorage.RegionData r1 = storage.getRegionData(p1);
/* 380 */         ReservoirRegionDataStorage.RegionData r2 = storage.getRegionData(p2);
/* 381 */         ReservoirRegionDataStorage.RegionData r3 = storage.getRegionData(p3);
/*     */         
/* 383 */         ReservoirRegionDataStorage.RegionData[] array = { rLocal, r0, r1, r2, r3 };
/* 384 */         for (ReservoirRegionDataStorage.RegionData rd : array) {
/* 385 */           if (rd != null) {
/* 386 */             Multimap<ResourceKey<Level>, ReservoirIsland> m = rd.getReservoirIslandList();
/* 387 */             synchronized (m) {
/* 388 */               islands.addAll(m.get(dimKey));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 394 */         MultiBufferSource.BufferSource bufferSource1 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */         
/* 396 */         if (islands != null && !islands.isEmpty()) {
/* 397 */           float y = 128.0625F;
/* 398 */           int j = 128;
/* 399 */           j = j * j + j * j;
/* 400 */           for (ReservoirIsland island : islands) {
/* 401 */             BlockPos center = island.getBoundingBox().getCenter();
/*     */             
/* 403 */             if (center.m_123331_((Vec3i)playerPos) <= j) {
/* 404 */               AxisAlignedIslandBB bounds = island.getBoundingBox();
/* 405 */               matrix.m_85836_();
/*     */               
/* 407 */               float minX = bounds.minX() + 0.5F;
/* 408 */               float minZ = bounds.minZ() + 0.5F;
/* 409 */               float maxX = bounds.maxX() + 0.5F;
/* 410 */               float maxZ = bounds.maxZ() + 0.5F;
/*     */               
/* 412 */               VertexConsumer builder = bufferSource1.m_6299_(IPRenderTypes.TRANSLUCENT_LINE);
/*     */               
/* 414 */               Matrix4f mat = matrix.m_85850_().m_85861_();
/* 415 */               Matrix3f nor = matrix.m_85850_().m_85864_();
/*     */               
/* 417 */               builder.m_85982_(mat, minX, y, minZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 418 */               builder.m_85982_(mat, maxX, y, minZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 419 */               builder.m_85982_(mat, minX, y, maxZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 420 */               builder.m_85982_(mat, maxX, y, maxZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 421 */               builder.m_85982_(mat, minX, y, minZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 422 */               builder.m_85982_(mat, minX, y, maxZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 423 */               builder.m_85982_(mat, maxX, y, minZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/* 424 */               builder.m_85982_(mat, maxX, y, maxZ).m_6122_(255, 0, 255, 127).m_85977_(nor, 0.0F, 1.0F, 0.0F).m_5752_();
/*     */               
/* 426 */               matrix.m_85849_();
/*     */               
/* 428 */               if (island.getPolygon() != null && !island.getPolygon().isEmpty()) {
/* 429 */                 List<ColumnPos> poly = island.getPolygon();
/*     */                 
/* 431 */                 matrix.m_85836_();
/*     */                 
/* 433 */                 VertexConsumer vertexConsumer = bufferSource1.m_6299_(IPRenderTypes.TRANSLUCENT_LINE);
/* 434 */                 Matrix4f matrix4f = matrix.m_85850_().m_85861_();
/* 435 */                 Matrix3f matrix3f = matrix.m_85850_().m_85864_();
/*     */ 
/*     */                 
/* 438 */                 int k = poly.size() - 1;
/* 439 */                 for (int m = 0; m < poly.size(); m++) {
/* 440 */                   ColumnPos a = poly.get(k);
/* 441 */                   ColumnPos b = poly.get(m);
/* 442 */                   float f = m / poly.size();
/*     */                   
/* 444 */                   vertexConsumer.m_85982_(matrix4f, a.f_140723_() + 0.5F, y, a.f_140724_() + 0.5F).m_85950_(f, 0.0F, 1.0F - f, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/* 445 */                   vertexConsumer.m_85982_(matrix4f, b.f_140723_() + 0.5F, y, b.f_140724_() + 0.5F).m_85950_(f, 0.0F, 1.0F - f, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/*     */                   
/* 447 */                   k = m;
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 453 */                 vertexConsumer.m_85982_(matrix4f, center.m_123341_() + 0.5F, 128.0F, center.m_123343_() + 0.5F).m_85950_(0.0F, 1.0F, 0.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/* 454 */                 vertexConsumer.m_85982_(matrix4f, center.m_123341_() + 0.5F, 129.0F, center.m_123343_() + 0.5F).m_85950_(0.0F, 1.0F, 0.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/*     */ 
/*     */                 
/* 457 */                 vertexConsumer.m_85982_(matrix4f, center.m_123341_(), 128.5F, center.m_123343_() + 0.5F).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/* 458 */                 vertexConsumer.m_85982_(matrix4f, (center.m_123341_() + 1), 128.5F, center.m_123343_() + 0.5F).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/*     */ 
/*     */                 
/* 461 */                 vertexConsumer.m_85982_(matrix4f, center.m_123341_() + 0.5F, 128.5F, center.m_123343_()).m_85950_(0.0F, 0.0F, 1.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/* 462 */                 vertexConsumer.m_85982_(matrix4f, center.m_123341_() + 0.5F, 128.5F, (center.m_123343_() + 1)).m_85950_(0.0F, 0.0F, 1.0F, 0.5F).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
/*     */ 
/*     */                 
/* 465 */                 matrix.m_85849_();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 471 */         bufferSource1.m_109911_();
/*     */ 
/*     */         
/* 474 */         matrix.m_85849_();
/*     */         
/* 476 */         matrix.m_85849_();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void renderOverlay(PoseStack matrix, List<Component> debugOut) {
/* 483 */     Minecraft mc = Minecraft.m_91087_();
/*     */     
/* 485 */     matrix.m_85836_();
/*     */     
/* 487 */     MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/* 488 */     for (int i = 0; i < debugOut.size(); i++) {
/* 489 */       int w = mc.f_91062_.m_92895_(((Component)debugOut.get(i)).getString());
/* 490 */       Objects.requireNonNull(mc.f_91062_); int yOff = i * (9 + 2);
/*     */       
/* 492 */       matrix.m_85836_();
/*     */       
/* 494 */       matrix.m_85837_(0.0D, 0.0D, 1.0D);
/* 495 */       GuiHelper.drawColouredRect(1, 1 + yOff, w + 1, 10, -1358954496, (MultiBufferSource)buffer, matrix);
/* 496 */       buffer.m_109911_();
/*     */       
/* 498 */       mc.f_91062_.m_92889_(matrix, debugOut.get(i), 2.0F, (2 + yOff), -1);
/*     */       
/* 500 */       matrix.m_85849_();
/*     */     } 
/*     */     
/* 503 */     matrix.m_85849_();
/*     */   }
/*     */   
/*     */   private static void distillationtower(List<Component> text, DistillationTowerTileEntity tower) {
/* 507 */     if (!tower.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 508 */       tower = (DistillationTowerTileEntity)tower.master();
/*     */     }
/*     */     
/* 511 */     for (int i = 0; i < tower.tanks.length; i++) {
/* 512 */       text.add(toText("Tank " + i + 1).m_130940_(ChatFormatting.UNDERLINE));
/*     */       
/* 514 */       MultiFluidTank tank = tower.tanks[i];
/* 515 */       if (tank.fluids.size() > 0) {
/* 516 */         for (int j = 0; j < tank.fluids.size(); j++) {
/* 517 */           FluidStack fstack = tank.fluids.get(j);
/* 518 */           text.add(toText("  " + fstack.getDisplayName().getString() + " (" + fstack.getAmount() + "mB)"));
/*     */         } 
/*     */       } else {
/* 521 */         text.add(toText("  Empty"));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void cokerunit(List<Component> text, CokerUnitTileEntity coker) {
/* 527 */     if (!coker.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 528 */       coker = (CokerUnitTileEntity)coker.master();
/*     */     }
/*     */ 
/*     */     
/* 532 */     FluidTank tank = coker.bufferTanks[0];
/* 533 */     FluidStack fs = tank.getFluid();
/* 534 */     text.add(toText("In Buffer: " + fs.getAmount() + "/" + tank.getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */ 
/*     */ 
/*     */     
/* 538 */     tank = coker.bufferTanks[1];
/* 539 */     fs = tank.getFluid();
/* 540 */     text.add(toText("Out Buffer: " + fs.getAmount() + "/" + tank.getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */ 
/*     */     
/* 543 */     for (int i = 0; i < coker.chambers.length; i++) {
/* 544 */       CokerUnitTileEntity.CokingChamber chamber = coker.chambers[i];
/* 545 */       FluidTank fluidTank = chamber.getTank();
/* 546 */       FluidStack fluidStack = fluidTank.getFluid();
/*     */       
/* 548 */       float completed = (chamber.getTotalAmount() > 0) ? (100.0F * chamber.getOutputAmount() / chamber.getTotalAmount()) : 0.0F;
/*     */       
/* 550 */       text.add(toText("Chamber " + i).m_130944_(new ChatFormatting[] { ChatFormatting.UNDERLINE, ChatFormatting.AQUA }));
/* 551 */       text.add(toText("State: " + chamber.getState().toString()));
/* 552 */       text.add(toText("  Tank: " + fluidStack.getAmount() + "/" + fluidTank.getCapacity() + "mB " + (fluidStack.isEmpty() ? "" : ("(" + fluidStack.getDisplayName().getString() + ")"))));
/* 553 */       text.add(toText("  Content: " + chamber.getTotalAmount() + " / " + chamber.getCapacity()).m_130946_(" (" + chamber.getInputItem().m_41786_().getString() + ")"));
/* 554 */       text.add(toText("  Out: " + chamber.getOutputItem().m_41786_().getString()));
/* 555 */       text.add(toText("  " + Mth.m_14143_(completed) + "% Completed. (Raw: " + completed + ")"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void hydrotreater(List<Component> text, HydrotreaterTileEntity treater) {
/* 560 */     if (!treater.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 561 */       treater = (HydrotreaterTileEntity)treater.master();
/*     */     }
/*     */     
/* 564 */     IFluidTank[] tanks = treater.getInternalTanks();
/* 565 */     if (tanks != null && tanks.length > 0) {
/* 566 */       for (int i = 0; i < tanks.length; i++) {
/* 567 */         FluidStack fs = tanks[i].getFluid();
/* 568 */         text.add(toText("Tank " + i + ": " + fs.getAmount() + "/" + tanks[i].getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void oiltank(List<Component> text, OilTankTileEntity tank) {
/* 574 */     BlockPos mbpos = tank.posInMultiblock;
/* 575 */     OilTankTileEntity.Port port = null;
/* 576 */     for (OilTankTileEntity.Port p : OilTankTileEntity.Port.values()) {
/* 577 */       if (p.matches(mbpos)) {
/* 578 */         port = p;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 583 */     if (!tank.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 584 */       tank = (OilTankTileEntity)tank.master();
/*     */     }
/*     */     
/* 587 */     if (port != null) {
/* 588 */       OilTankTileEntity.PortState portState = (OilTankTileEntity.PortState)tank.portConfig.get(port);
/* 589 */       boolean isInput = (portState == OilTankTileEntity.PortState.INPUT);
/* 590 */       text.add(toText("Port: ")
/* 591 */           .m_7220_((Component)toText((port != null) ? port.m_7912_() : "None"))
/* 592 */           .m_7220_((Component)toText(" " + portState.m_7912_())
/* 593 */             .m_130940_(isInput ? ChatFormatting.AQUA : ChatFormatting.GOLD)));
/*     */     } 
/*     */     
/* 596 */     FluidStack fs = tank.tank.getFluid();
/* 597 */     text.add(toText("Fluid: " + fs.getAmount() + "/" + tank.tank.getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */   }
/*     */   
/*     */   private static void derrick(List<Component> text, DerrickTileEntity derrick) {
/* 601 */     if (!derrick.offsetToMaster.equals(BlockPos.f_121853_)) {
/* 602 */       derrick = (DerrickTileEntity)derrick.master();
/*     */     }
/*     */     
/* 605 */     IFluidTank[] tanks = derrick.getInternalTanks();
/* 606 */     if (tanks != null && tanks.length > 0) {
/* 607 */       for (int i = 0; i < tanks.length; i++) {
/* 608 */         FluidStack fs = tanks[i].getFluid();
/* 609 */         text.add(toText("Tank " + i + ": " + fs.getAmount() + "/" + tanks[i].getCapacity() + "mB " + (fs.isEmpty() ? "" : ("(" + fs.getDisplayName().getString() + ")"))));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static MutableComponent toText(String string) {
/* 615 */     return Component.m_237113_(string);
/*     */   }
/*     */   
/*     */   static MutableComponent toTranslation(String translationKey, Object... args) {
/* 619 */     return Component.m_237110_(translationKey, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\debugging\DebugRenderHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */