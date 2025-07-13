/*     */ package flaxbeard.immersivepetroleum.client;
/*     */ 
/*     */ import blusunrize.immersiveengineering.client.ClientUtils;
/*     */ import blusunrize.immersiveengineering.client.ItemOverlayUtils;
/*     */ import blusunrize.immersiveengineering.client.utils.GuiHelper;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.CommonEventHandler;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.wooden.AutoLubricatorBlock;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.items.DebugItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
/*     */ import net.minecraftforge.client.event.RenderGuiOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderLevelStageEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
/*     */ import net.minecraftforge.client.model.data.ModelData;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class ClientEventHandler
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void renderLevelStage(RenderLevelStageEvent event) {
/*  68 */     if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
/*  69 */       renderAutoLubricatorGhost(event);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderAutoLubricatorGhost(RenderLevelStageEvent event) {
/*  74 */     PoseStack matrix = event.getPoseStack();
/*  75 */     Minecraft mc = Minecraft.m_91087_();
/*     */     
/*  77 */     matrix.m_85836_();
/*     */     
/*  79 */     if (mc.f_91074_ != null) {
/*  80 */       ItemStack mainItem = mc.f_91074_.m_21205_();
/*  81 */       ItemStack secondItem = mc.f_91074_.m_21206_();
/*     */       
/*  83 */       boolean main = (!mainItem.m_41619_() && mainItem.m_41720_() == ((AutoLubricatorBlock)IPContent.Blocks.AUTO_LUBRICATOR.get()).m_5456_());
/*  84 */       boolean off = (!secondItem.m_41619_() && secondItem.m_41720_() == ((AutoLubricatorBlock)IPContent.Blocks.AUTO_LUBRICATOR.get()).m_5456_());
/*     */       
/*  86 */       if (main || off) {
/*  87 */         BlockRenderDispatcher blockDispatcher = Minecraft.m_91087_().m_91289_();
/*  88 */         MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */ 
/*     */         
/*  91 */         Vec3 renderView = MCUtil.getGameRenderer().m_109153_().m_90583_();
/*  92 */         matrix.m_85837_(-renderView.f_82479_, -renderView.f_82480_, -renderView.f_82481_);
/*     */         
/*  94 */         BlockPos base = mc.f_91074_.m_20183_();
/*  95 */         for (int x = -16; x <= 16; x++) {
/*  96 */           for (int z = -16; z <= 16; z++) {
/*  97 */             for (int y = -16; y <= 16; y++) {
/*  98 */               BlockPos pos = base.m_7918_(x, y, z);
/*  99 */               BlockEntity te = mc.f_91074_.f_19853_.m_7702_(pos);
/*     */               
/* 101 */               if (te != null) {
/* 102 */                 LubricatedHandler.ILubricationHandler<BlockEntity> handler = LubricatedHandler.getHandlerForTile(te);
/* 103 */                 if (handler != null) {
/* 104 */                   Tuple<BlockPos, Direction> target = handler.getGhostBlockPosition(mc.f_91074_.f_19853_, te);
/* 105 */                   if (target != null) {
/* 106 */                     BlockPos targetPos = (BlockPos)target.m_14418_();
/* 107 */                     Direction targetFacing = (Direction)target.m_14419_();
/* 108 */                     BlockState targetState = mc.f_91074_.f_19853_.m_8055_(targetPos);
/* 109 */                     BlockState targetStateUp = mc.f_91074_.f_19853_.m_8055_(targetPos.m_7494_());
/* 110 */                     if (targetState.m_60767_().m_76336_() && targetStateUp.m_60767_().m_76336_()) {
/* 111 */                       VertexConsumer vBuilder = buffer.m_6299_(RenderType.m_110466_());
/* 112 */                       matrix.m_85836_();
/*     */                       
/* 114 */                       matrix.m_85837_(targetPos.m_123341_(), (targetPos.m_123342_() - 1), targetPos.m_123343_());
/*     */                       
/* 116 */                       BlockState state = (BlockState)((AutoLubricatorBlock)IPContent.Blocks.AUTO_LUBRICATOR.get()).m_49966_().m_61124_((Property)AutoLubricatorBlock.FACING, (Comparable)targetFacing);
/* 117 */                       BakedModel model = blockDispatcher.m_110910_(state);
/* 118 */                       blockDispatcher.m_110937_().renderModel(matrix.m_85850_(), vBuilder, null, model, 1.0F, 1.0F, 1.0F, 15728880, OverlayTexture.f_118083_, ModelData.EMPTY, null);
/*     */ 
/*     */                       
/* 121 */                       matrix.m_85849_();
/*     */                       
/* 123 */                       buffer.m_109911_();
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     matrix.m_85849_();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void reservoirDebuggingOverlayText(RenderGuiOverlayEvent.Post event) {
/* 139 */     if (ReservoirHandler.getGenerator() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     LocalPlayer localPlayer = MCUtil.getPlayer();
/*     */     
/* 145 */     ItemStack main = localPlayer.m_21120_(InteractionHand.MAIN_HAND);
/* 146 */     ItemStack off = localPlayer.m_21120_(InteractionHand.OFF_HAND);
/*     */     
/* 148 */     if ((main != ItemStack.f_41583_ && main.m_41720_() == IPContent.DEBUGITEM.get()) || (off != ItemStack.f_41583_ && off.m_41720_() == IPContent.DEBUGITEM.get())) {
/* 149 */       if (DebugItem.getMode(main) != DebugItem.Modes.SEEDBASED_RESERVOIR && DebugItem.getMode(off) != DebugItem.Modes.SEEDBASED_RESERVOIR) {
/*     */         return;
/*     */       }
/*     */       
/* 153 */       List<Component> debugOut = new ArrayList<>();
/*     */       
/* 155 */       if (!debugOut.isEmpty()) {
/* 156 */         PoseStack matrix = event.getPoseStack();
/* 157 */         matrix.m_85836_();
/* 158 */         MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/* 159 */         for (int i = 0; i < debugOut.size(); i++) {
/* 160 */           int w = ClientUtils.font().m_92895_(((Component)debugOut.get(i)).getString());
/* 161 */           Objects.requireNonNull(ClientUtils.font()); int yOff = i * (9 + 2);
/*     */           
/* 163 */           matrix.m_85836_();
/* 164 */           matrix.m_85837_(0.0D, 0.0D, 1.0D);
/* 165 */           GuiHelper.drawColouredRect(1, 1 + yOff, w + 1, 10, -1358954496, (MultiBufferSource)buffer, matrix);
/* 166 */           buffer.m_109911_();
/*     */           
/* 168 */           ClientUtils.font().m_92889_(matrix, debugOut.get(i), 2.0F, (2 + yOff), -1);
/* 169 */           matrix.m_85849_();
/*     */         } 
/* 171 */         matrix.m_85849_();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderInfoOverlays(RenderGuiOverlayEvent.Post event) {
/* 178 */     if (MCUtil.getPlayer() != null && event.getOverlay().id() == VanillaGuiOverlay.HOTBAR.id()) {
/* 179 */       LocalPlayer localPlayer = MCUtil.getPlayer();
/*     */       
/* 181 */       if (MCUtil.getHitResult() != null) {
/* 182 */         HitResult result = MCUtil.getHitResult();
/*     */         
/* 184 */         if (result.m_6662_() == HitResult.Type.ENTITY && 
/* 185 */           result instanceof EntityHitResult) { EntityHitResult eHit = (EntityHitResult)result;
/* 186 */           Entity entity = eHit.m_82443_(); if (entity instanceof MotorboatEntity) { MotorboatEntity motorboat = (MotorboatEntity)entity;
/* 187 */             String[] text = motorboat.getOverlayText((Player)localPlayer, result);
/*     */             
/* 189 */             if (text != null && text.length > 0) {
/* 190 */               Font font = ClientUtils.font();
/* 191 */               int col = 16777215;
/* 192 */               for (int i = 0; i < text.length; i++) {
/* 193 */                 if (text[i] != null) {
/* 194 */                   int fx = event.getWindow().m_85445_() / 2 + 8;
/* 195 */                   Objects.requireNonNull(font); int fy = event.getWindow().m_85446_() / 2 + 8 + i * 9;
/* 196 */                   font.m_92750_(event.getPoseStack(), text[i], fx, fy, col);
/*     */                 } 
/*     */               } 
/*     */             }  }
/*     */            }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderOverlayPost(RenderGuiOverlayEvent.Post event) {
/* 209 */     if (MCUtil.getPlayer() != null && event.getOverlay().id() == VanillaGuiOverlay.HOTBAR.id()) {
/* 210 */       LocalPlayer localPlayer = MCUtil.getPlayer();
/* 211 */       PoseStack matrix = event.getPoseStack();
/*     */       
/* 213 */       Entity entity = localPlayer.m_20202_(); if (entity instanceof MotorboatEntity) { MotorboatEntity motorboat = (MotorboatEntity)entity;
/* 214 */         int offset = 0;
/* 215 */         boolean holdingDebugItem = false;
/* 216 */         for (InteractionHand hand : InteractionHand.values()) {
/* 217 */           if (!localPlayer.m_21120_(hand).m_41619_()) {
/* 218 */             ItemStack equipped = localPlayer.m_21120_(hand);
/* 219 */             if (equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.DrillItem || equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.ChemthrowerItem || equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.BuzzsawItem) {
/* 220 */               offset -= 85;
/* 221 */             } else if (equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.RevolverItem || equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.SpeedloaderItem) {
/* 222 */               offset -= 65;
/* 223 */             } else if (equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.RailgunItem) {
/* 224 */               offset -= 50;
/* 225 */             } else if (equipped.m_41720_() instanceof blusunrize.immersiveengineering.common.items.IEShieldItem) {
/* 226 */               offset -= 40;
/*     */             } 
/*     */             
/* 229 */             if (equipped.m_41720_() instanceof DebugItem) {
/* 230 */               holdingDebugItem = true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 235 */         matrix.m_85836_();
/*     */         
/* 237 */         int scaledWidth = MCUtil.getWindow().m_85445_();
/* 238 */         int scaledHeight = MCUtil.getWindow().m_85446_();
/*     */         
/* 240 */         MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/* 241 */         VertexConsumer builder = ItemOverlayUtils.getHudElementsBuilder(buffer);
/*     */         
/* 243 */         int rightOffset = 0;
/* 244 */         if (((Boolean)MCUtil.getOptions().m_231825_().m_231551_()).booleanValue())
/* 245 */           rightOffset += 100; 
/* 246 */         float dx = (scaledWidth - rightOffset - 16);
/* 247 */         float dy = (scaledHeight + offset);
/* 248 */         matrix.m_85836_();
/*     */         
/* 250 */         matrix.m_85837_(dx, dy, 0.0D);
/* 251 */         GuiHelper.drawTexturedRect(builder, matrix, -24, -68, 31, 62, 256.0F, 179, 210, 9, 71);
/*     */         
/* 253 */         matrix.m_85837_(-23.0D, -37.0D, 0.0D);
/* 254 */         float capacity = motorboat.getMaxFuel();
/* 255 */         if (capacity > 0.0F) {
/* 256 */           FluidStack fuel = motorboat.getContainedFluid();
/* 257 */           int amount = fuel.getAmount();
/* 258 */           float angle = 83.0F - (166 * amount) / capacity;
/* 259 */           matrix.m_85836_();
/* 260 */           matrix.m_85845_(new Quaternion(0.0F, 0.0F, angle, true));
/* 261 */           GuiHelper.drawTexturedRect(builder, matrix, 6, -2, 24, 4, 256.0F, 91, 123, 80, 87);
/* 262 */           matrix.m_85849_();
/* 263 */           matrix.m_85837_(23.0D, 37.0D, 0.0D);
/*     */           
/* 265 */           GuiHelper.drawTexturedRect(builder, matrix, -41, -73, 53, 72, 256.0F, 8, 61, 4, 76);
/*     */         } 
/*     */         
/* 268 */         matrix.m_85849_();
/*     */         
/* 270 */         buffer.m_109911_();
/*     */         
/* 272 */         if (holdingDebugItem && MCUtil.getFont() != null) {
/* 273 */           matrix.m_85836_();
/*     */           
/* 275 */           Font font = MCUtil.getFont();
/*     */           
/* 277 */           int j = motorboat.getMaxFuel();
/* 278 */           FluidStack fs = motorboat.getContainedFluid();
/* 279 */           int amount = (fs == FluidStack.EMPTY || fs.getFluid() == null) ? 0 : fs.getAmount();
/*     */           
/* 281 */           Vec3 vec = motorboat.m_20184_();
/* 282 */           float speed = (float)Math.sqrt(vec.f_82479_ * vec.f_82479_ + vec.f_82481_ * vec.f_82481_);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 288 */           String[] array = { String.format(Locale.US, "Fuel: %05d/%d mB (%s)", new Object[] { Integer.valueOf(amount), Integer.valueOf(j), fs.getDisplayName().getString() }), String.format(Locale.US, "Speed: %.3f", new Object[] { Float.valueOf(speed) }), String.format(Locale.US, "PropXRot: %07.3fÂ° (%.3frad)", new Object[] { Float.valueOf(motorboat.propellerXRot), Float.valueOf(motorboat.propellerXRot * 0.017453292F) }), String.format(Locale.US, "PropSpeed: %06.3fÂ°", new Object[] { Float.valueOf(motorboat.propellerXRotSpeed) }) };
/*     */           
/* 290 */           int w = 3, h = 3;
/* 291 */           for (int i = 0; i < array.length; i++) {
/* 292 */             font.m_92750_(matrix, array[i], w, (h + 9 * i), -1);
/*     */           }
/*     */           
/* 295 */           matrix.m_85849_();
/*     */         } 
/*     */         
/* 298 */         matrix.m_85849_(); }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleBoatImmunity(RenderBlockScreenEffectEvent event) {
/* 305 */     Player entity = event.getPlayer();
/* 306 */     if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE && entity.m_6060_()) { Entity entity1 = entity.m_20202_(); if (entity1 instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity1;
/* 307 */         if (boat.isFireproof)
/* 308 */           event.setCanceled(true);  }
/*     */        }
/*     */   
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleFireRender(RenderPlayerEvent.Pre event) {
/* 315 */     Player entity = event.getEntity();
/* 316 */     if (entity.m_6060_()) { Entity entity1 = entity.m_20202_(); if (entity1 instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity1;
/* 317 */         if (boat.isFireproof)
/* 318 */           entity.m_20095_();  }
/*     */        }
/*     */   
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleLubricatingMachinesClient(TickEvent.ClientTickEvent event) {
/* 325 */     if (event.phase == TickEvent.Phase.END && MCUtil.getLevel() != null)
/* 326 */       CommonEventHandler.handleLubricatingMachines((Level)MCUtil.getLevel()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\ClientEventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */