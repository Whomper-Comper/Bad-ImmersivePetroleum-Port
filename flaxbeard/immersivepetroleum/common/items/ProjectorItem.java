/*     */ package flaxbeard.immersivepetroleum.common.items;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
/*     */ import blusunrize.immersiveengineering.api.shader.CapabilityShader;
/*     */ import blusunrize.immersiveengineering.api.tool.IUpgradeableTool;
/*     */ import blusunrize.immersiveengineering.api.utils.CapabilityUtils;
/*     */ import blusunrize.immersiveengineering.api.utils.ItemUtils;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Vector3f;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.event.ProjectorEvent;
/*     */ import flaxbeard.immersivepetroleum.client.IPShaders;
/*     */ import flaxbeard.immersivepetroleum.client.gui.ProjectorScreen;
/*     */ import flaxbeard.immersivepetroleum.client.render.IPRenderTypes;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPKeyBinds;
/*     */ import flaxbeard.immersivepetroleum.common.util.IPItemStackHandler;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import flaxbeard.immersivepetroleum.common.util.projector.MultiblockProjection;
/*     */ import flaxbeard.immersivepetroleum.common.util.projector.Settings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.block.ModelBlockRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.CreativeModeTab;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.client.event.InputEvent;
/*     */ import net.minecraftforge.client.event.RenderLevelStageEvent;
/*     */ import net.minecraftforge.client.model.data.ModelData;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*     */ import net.minecraftforge.common.util.LazyOptional;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.eventbus.api.Event;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.LogicalSide;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ 
/*     */ 
/*     */ public class ProjectorItem
/*     */   extends IPItemBase
/*     */   implements IUpgradeableTool
/*     */ {
/*     */   public ProjectorItem() {
/* 101 */     super((new Item.Properties()).m_41487_(1).m_41491_(ImmersivePetroleum.creativeTab));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component m_7626_(@Nonnull ItemStack stack) {
/* 107 */     String selfKey = m_5671_(stack);
/* 108 */     if (stack.m_41782_()) {
/* 109 */       Settings settings = getSettings(stack);
/* 110 */       if (settings.getMultiblock() != null) {
/* 111 */         Component name = settings.getMultiblock().getDisplayName();
/*     */         
/* 113 */         return (Component)Component.m_237110_(selfKey + ".specific", new Object[] { name }).m_130940_(ChatFormatting.GOLD);
/*     */       } 
/*     */     } 
/* 116 */     return (Component)Component.m_237115_(selfKey).m_130940_(ChatFormatting.GOLD);
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void m_7373_(@Nonnull ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
/* 121 */     Settings settings = getSettings(stack);
/* 122 */     if (settings.getMultiblock() != null) {
/* 123 */       Vec3i size = settings.getMultiblock().getSize(worldIn);
/*     */       
/* 125 */       tooltip.add(Component.m_237115_("desc.immersivepetroleum.info.projector.build0"));
/* 126 */       tooltip.add(Component.m_237110_("desc.immersivepetroleum.info.projector.build1", new Object[] { settings.getMultiblock().getDisplayName() }));
/*     */       
/* 128 */       if (isPressing(340) || isPressing(344)) {
/* 129 */         MutableComponent mutableComponent4, mutableComponent1 = Component.m_237115_("desc.immersivepetroleum.info.projector.holdshift.text").m_130940_(ChatFormatting.DARK_AQUA);
/* 130 */         tooltip.add(mutableComponent1);
/*     */         
/* 132 */         MutableComponent mutableComponent2 = Component.m_237110_("desc.immersivepetroleum.info.projector.size", new Object[] { Integer.valueOf(size.m_123341_()), Integer.valueOf(size.m_123342_()), Integer.valueOf(size.m_123343_()) }).m_130940_(ChatFormatting.DARK_GRAY);
/* 133 */         tooltip.add(indent((Component)mutableComponent2));
/*     */         
/* 135 */         Direction dir = Direction.m_122407_(settings.getRotation().ordinal());
/* 136 */         MutableComponent mutableComponent3 = Component.m_237115_("desc.immersivepetroleum.info.projector.rotated." + dir).m_130940_(ChatFormatting.DARK_GRAY);
/*     */ 
/*     */         
/* 139 */         if (settings.isMirrored()) {
/* 140 */           mutableComponent4 = Component.m_237115_("desc.immersivepetroleum.info.projector.flipped.true").m_130940_(ChatFormatting.DARK_GRAY);
/*     */         } else {
/* 142 */           mutableComponent4 = Component.m_237115_("desc.immersivepetroleum.info.projector.flipped.false").m_130940_(ChatFormatting.DARK_GRAY);
/*     */         } 
/*     */         
/* 145 */         if (settings.getPos() != null) {
/* 146 */           int x = settings.getPos().m_123341_();
/* 147 */           int y = settings.getPos().m_123342_();
/* 148 */           int z = settings.getPos().m_123343_();
/*     */           
/* 150 */           MutableComponent mutableComponent = Component.m_237110_("desc.immersivepetroleum.info.projector.center", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) }).m_130940_(ChatFormatting.DARK_GRAY);
/* 151 */           tooltip.add(indent((Component)mutableComponent));
/*     */         } 
/*     */         
/* 154 */         tooltip.add(indent((Component)mutableComponent3));
/* 155 */         tooltip.add(indent((Component)mutableComponent4));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 161 */         MutableComponent mutableComponent = Component.m_237113_("[").m_7220_((Component)Component.m_237115_("desc.immersivepetroleum.info.projector.holdshift")).m_130946_("] ").m_7220_((Component)Component.m_237115_("desc.immersivepetroleum.info.projector.holdshift.text")).m_130940_(ChatFormatting.DARK_AQUA);
/* 162 */         tooltip.add(mutableComponent);
/*     */       } 
/*     */       
/* 165 */       if (isPressing(341) || isPressing(345)) {
/* 166 */         MutableComponent mutableComponent1 = Component.m_237115_("desc.immersivepetroleum.info.projector.holdctrl.text").m_130940_(ChatFormatting.DARK_PURPLE);
/* 167 */         MutableComponent mutableComponent2 = Component.m_237115_("desc.immersivepetroleum.info.projector.control1").m_130940_(ChatFormatting.DARK_GRAY);
/* 168 */         MutableComponent mutableComponent3 = Component.m_237110_("desc.immersivepetroleum.info.projector.control2", new Object[] { IPKeyBinds.keybind_preview_flip.m_90863_() }).m_130940_(ChatFormatting.DARK_GRAY);
/* 169 */         MutableComponent mutableComponent4 = Component.m_237115_("desc.immersivepetroleum.info.projector.control3").m_130940_(ChatFormatting.DARK_GRAY);
/*     */         
/* 171 */         tooltip.add(mutableComponent1);
/* 172 */         tooltip.add(indent((Component)mutableComponent2));
/* 173 */         tooltip.add(indent((Component)mutableComponent3));
/* 174 */         tooltip.add(indent((Component)mutableComponent4));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 180 */         MutableComponent mutableComponent = Component.m_237113_("[").m_7220_((Component)Component.m_237115_("desc.immersivepetroleum.info.projector.holdctrl")).m_130946_("] ").m_7220_((Component)Component.m_237115_("desc.immersivepetroleum.info.projector.holdctrl.text")).m_130940_(ChatFormatting.DARK_PURPLE);
/* 181 */         tooltip.add(mutableComponent);
/*     */       } 
/*     */     } else {
/* 184 */       tooltip.add(Component.m_237115_("desc.immersivepetroleum.info.projector.noMultiblock"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private Component indent(Component text) {
/* 189 */     return (Component)Component.m_237113_("  ").m_7220_(text);
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   private boolean isPressing(int key) {
/* 195 */     long window = Minecraft.m_91087_().m_91268_().m_85439_();
/* 196 */     return (GLFW.glfwGetKey(window, key) == 1);
/*     */   }
/*     */ 
/*     */   
/* 200 */   static final Map<Class<? extends MultiblockHandler.IMultiblock>, String> nameCache = new HashMap<>();
/*     */   
/*     */   public static String getActualMBName(MultiblockHandler.IMultiblock multiblock) {
/* 203 */     if (!nameCache.containsKey(multiblock.getClass())) {
/* 204 */       String name = multiblock.getClass().getSimpleName();
/* 205 */       name = name.substring(0, name.indexOf("Multiblock"));
/*     */       
/* 207 */       switch (name) { case "LightningRod": 
/*     */         case "ImprovedBlastfurnace": 
/*     */         default:
/* 210 */           break; }  name = name;
/*     */ 
/*     */       
/* 213 */       nameCache.put(multiblock.getClass(), name);
/*     */     } 
/*     */     
/* 216 */     return nameCache.get(multiblock.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6787_(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
/* 221 */     if (m_220152_(group)) {
/* 222 */       items.add(new ItemStack((ItemLike)this, 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, @Nonnull InteractionHand hand) {
/* 229 */     ItemStack held = player.m_21120_(hand);
/*     */     
/* 231 */     if (world.f_46443_) {
/* 232 */       boolean changeMode = false;
/* 233 */       Settings settings = getSettings(held);
/* 234 */       switch (settings.getMode()) {
/*     */         case MODEL:
/* 236 */           if (player.m_6144_()) {
/* 237 */             if (settings.getPos() != null) {
/* 238 */               settings.setPos(null);
/* 239 */               settings.sendPacketToServer(hand); break;
/*     */             } 
/* 241 */             changeMode = true;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case ENTITYBLOCK_ANIMATED:
/* 246 */           if (!player.m_6144_()) {
/* 247 */             openGUI(hand, held); break;
/*     */           } 
/* 249 */           changeMode = true;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (changeMode) {
/* 257 */         int modeId = settings.getMode().ordinal() + 1;
/* 258 */         settings.setMode(Settings.Mode.values()[(modeId >= (Settings.Mode.values()).length) ? 0 : modeId]);
/* 259 */         settings.applyTo(held);
/* 260 */         settings.sendPacketToServer(hand);
/* 261 */         player.m_5661_(settings.getMode().getTranslated(), true);
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     return InteractionResultHolder.m_19090_(held);
/*     */   }
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   private static void openGUI(InteractionHand hand, ItemStack held) {
/* 270 */     MCUtil.setScreen((Screen)new ProjectorScreen(hand, held));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionResult m_6225_(UseOnContext context) {
/* 276 */     Level world = context.m_43725_();
/* 277 */     Player playerIn = context.m_43723_();
/* 278 */     InteractionHand hand = context.m_43724_();
/* 279 */     BlockPos pos = context.m_8083_();
/* 280 */     Direction facing = context.m_43719_();
/*     */     
/* 282 */     ItemStack stack = playerIn.m_21120_(hand);
/* 283 */     Settings settings = getSettings(stack);
/* 284 */     if (playerIn.m_6144_() && settings.getPos() != null) {
/* 285 */       if (world.f_46443_) {
/* 286 */         settings.setPos(null);
/* 287 */         settings.applyTo(stack);
/* 288 */         settings.sendPacketToServer(hand);
/*     */       } 
/*     */       
/* 291 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 294 */     if (settings.getMode() == Settings.Mode.PROJECTION && settings.getPos() == null && settings.getMultiblock() != null) {
/* 295 */       BlockState state = world.m_8055_(pos);
/*     */       
/* 297 */       BlockPos.MutableBlockPos hit = pos.m_122032_();
/* 298 */       if (!state.m_60767_().m_76336_() && facing == Direction.UP) {
/* 299 */         hit.m_122154_((Vec3i)hit, 0, 1, 0);
/*     */       }
/*     */       
/* 302 */       Vec3i size = settings.getMultiblock().getSize(world);
/* 303 */       alignHit(hit, playerIn, size, settings.getRotation(), settings.isMirrored());
/*     */       
/* 305 */       if (playerIn.m_6144_() && playerIn.m_7500_()) {
/* 306 */         if (!world.f_46443_) {
/* 307 */           if (settings.getMultiblock().getUniqueName().m_135815_().contains("excavator_demo") || settings.getMultiblock().getUniqueName().m_135815_().contains("bucket_wheel")) {
/* 308 */             hit.m_122154_((Vec3i)hit, 0, -2, 0);
/*     */           }
/*     */ 
/*     */           
/* 312 */           BiPredicate<Integer, MultiblockProjection.Info> pred = (layer, info) -> {
/*     */               BlockPos realPos = info.tPos.m_121955_((Vec3i)hit);
/*     */               
/*     */               BlockState tstate0 = info.getModifiedState(world, realPos);
/*     */               
/*     */               ProjectorEvent.PlaceBlock event = new ProjectorEvent.PlaceBlock(info.multiblock, info.templateWorld, info.tBlockInfo.f_74675_, world, realPos, tstate0, settings.getRotation());
/*     */               
/*     */               if (!MinecraftForge.EVENT_BUS.post((Event)event)) {
/*     */                 BlockState tstate1 = event.getState();
/*     */                 
/*     */                 if (world.m_46597_(realPos, tstate1)) {
/*     */                   ProjectorEvent.PlaceBlockPost postEvent = new ProjectorEvent.PlaceBlockPost(info.multiblock, info.templateWorld, event.getTemplatePos(), world, realPos, tstate1, settings.getRotation());
/*     */                   MinecraftForge.EVENT_BUS.post((Event)postEvent);
/*     */                 } 
/*     */               } 
/*     */               return false;
/*     */             };
/* 329 */           MultiblockProjection projection = new MultiblockProjection(world, settings.getMultiblock());
/* 330 */           projection.setFlip(settings.isMirrored());
/* 331 */           projection.setRotation(settings.getRotation());
/* 332 */           projection.processAll(pred);
/*     */         } 
/*     */         
/* 335 */         return InteractionResult.SUCCESS;
/*     */       } 
/*     */       
/* 338 */       if (world.f_46443_) {
/* 339 */         settings.setPos((BlockPos)hit);
/* 340 */         settings.applyTo(stack);
/* 341 */         settings.sendPacketToServer(hand);
/*     */       } 
/*     */       
/* 344 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */ 
/*     */     
/* 348 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Settings getSettings(@Nullable ItemStack stack) {
/* 354 */     return new Settings(stack);
/*     */   }
/*     */   
/*     */   private static void alignHit(BlockPos.MutableBlockPos hit, Player playerIn, Vec3i size, Rotation rotation, boolean mirror) {
/* 358 */     int x = ((rotation.ordinal() % 2 == 0) ? size.m_123341_() : size.m_123343_()) / 2;
/* 359 */     int z = ((rotation.ordinal() % 2 == 0) ? size.m_123343_() : size.m_123341_()) / 2;
/* 360 */     Direction facing = playerIn.m_6350_();
/*     */     
/* 362 */     boolean xEven = (size.m_123341_() % 2 == 0);
/* 363 */     boolean zEven = (size.m_123343_() % 2 == 0);
/*     */     
/* 365 */     switch (facing) { case MODEL:
/* 366 */         hit.m_122154_((Vec3i)hit, 0, 0, -z + (zEven ? 1 : 0)); break;
/* 367 */       case ENTITYBLOCK_ANIMATED: hit.m_122154_((Vec3i)hit, 0, 0, z); break;
/* 368 */       case null: hit.m_122154_((Vec3i)hit, x, 0, 0); break;
/* 369 */       case null: hit.m_122154_((Vec3i)hit, -x + (xEven ? 1 : 0), 0, 0);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*     */   public static class ClientRenderHandler
/*     */   {
/*     */     @SubscribeEvent
/*     */     public static void renderLevelStage(RenderLevelStageEvent event) {
/* 383 */       if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
/* 384 */         renderProjection(event);
/*     */       }
/*     */     }
/*     */     
/*     */     private static void renderProjection(RenderLevelStageEvent event) {
/* 389 */       Minecraft mc = Minecraft.m_91087_();
/*     */       
/* 391 */       if (mc.f_91074_ != null) {
/* 392 */         PoseStack matrix = event.getPoseStack();
/* 393 */         matrix.m_85836_();
/*     */ 
/*     */         
/* 396 */         Vec3 renderView = MCUtil.getGameRenderer().m_109153_().m_90583_();
/* 397 */         matrix.m_85837_(-renderView.f_82479_, -renderView.f_82480_, -renderView.f_82481_);
/*     */         
/* 399 */         ItemStack secondItem = mc.f_91074_.m_21206_();
/* 400 */         boolean off = (secondItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(secondItem, "settings", 10));
/*     */         
/* 402 */         for (int i = 0; i <= 10; i++) {
/* 403 */           ItemStack stack = (i == 10) ? secondItem : mc.f_91074_.m_150109_().m_8020_(i);
/* 404 */           if (stack.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(stack, "settings", 10)) {
/* 405 */             Settings settings = ProjectorItem.getSettings(stack);
/* 406 */             matrix.m_85836_();
/*     */             
/* 408 */             boolean renderMoving = (i == (mc.f_91074_.m_150109_()).f_35977_ || (i == 10 && off));
/* 409 */             renderSchematic(matrix, settings, (Player)mc.f_91074_, mc.f_91074_.f_19853_, event.getPartialTick(), renderMoving);
/*     */             
/* 411 */             matrix.m_85849_();
/*     */           } 
/*     */         } 
/*     */         
/* 415 */         matrix.m_85849_();
/*     */       } 
/*     */     }
/*     */     
/* 419 */     static final BlockPos.MutableBlockPos FULL_MAX = new BlockPos.MutableBlockPos(2147483647, 2147483647, 2147483647);
/*     */     public static void renderSchematic(PoseStack matrix, Settings settings, Player player, Level world, float partialTicks, boolean renderMoving) {
/* 421 */       if (settings.getMultiblock() == null) {
/*     */         return;
/*     */       }
/* 424 */       Vec3i size = settings.getMultiblock().getSize(world);
/* 425 */       BlockPos.MutableBlockPos hit = new BlockPos.MutableBlockPos(FULL_MAX.m_123341_(), FULL_MAX.m_123342_(), FULL_MAX.m_123343_());
/* 426 */       MutableBoolean isPlaced = new MutableBoolean(false);
/* 427 */       if (settings.getPos() != null) {
/* 428 */         hit.m_122190_((Vec3i)settings.getPos());
/* 429 */         isPlaced.setTrue();
/*     */       }
/* 431 */       else if (renderMoving && MCUtil.getHitResult() != null && MCUtil.getHitResult().m_6662_() == HitResult.Type.BLOCK) {
/* 432 */         BlockHitResult blockRTResult = (BlockHitResult)MCUtil.getHitResult();
/*     */         
/* 434 */         BlockPos pos = blockRTResult.m_82425_();
/*     */         
/* 436 */         BlockState state = world.m_8055_(pos);
/* 437 */         if (state.m_60767_().m_76336_() || blockRTResult.m_82434_() != Direction.UP) {
/* 438 */           hit.m_122190_((Vec3i)pos);
/*     */         } else {
/* 440 */           hit.m_122154_((Vec3i)pos, 0, 1, 0);
/*     */         } 
/*     */         
/* 443 */         ProjectorItem.alignHit(hit, player, size, settings.getRotation(), settings.isMirrored());
/*     */       } 
/*     */       
/* 446 */       if (!hit.equals(FULL_MAX)) {
/* 447 */         ResourceLocation name = settings.getMultiblock().getUniqueName();
/* 448 */         if (name.m_135815_().contains("excavator_demo") || name.m_135815_().contains("bucket_wheel")) {
/* 449 */           hit.m_122154_((Vec3i)hit, 0, -2, 0);
/*     */         }
/*     */         
/* 452 */         MultiblockProjection projection = new MultiblockProjection(world, settings.getMultiblock());
/* 453 */         projection.setRotation(settings.getRotation());
/* 454 */         projection.setFlip(settings.isMirrored());
/*     */         
/* 456 */         List<Pair<ProjectorItem.RenderLayer, MultiblockProjection.Info>> toRender = new ArrayList<>();
/* 457 */         MutableInt currentLayer = new MutableInt();
/* 458 */         MutableInt badBlocks = new MutableInt();
/* 459 */         MutableInt goodBlocks = new MutableInt();
/* 460 */         BiPredicate<Integer, MultiblockProjection.Info> bipred = (layer, info) -> {
/*     */             if (badBlocks.getValue().intValue() == 0 && layer.intValue() > currentLayer.getValue().intValue()) {
/*     */               currentLayer.setValue(layer);
/*     */             } else if (!Objects.equals(layer, currentLayer.getValue())) {
/*     */               return true;
/*     */             } 
/*     */             
/*     */             if (isPlaced.booleanValue() && Objects.equals(layer, currentLayer.getValue())) {
/*     */               BlockPos realPos = info.tPos.m_121955_((Vec3i)hit);
/*     */               
/*     */               BlockState toCompare = world.m_8055_(realPos);
/*     */               
/*     */               BlockState tState = info.getModifiedState(world, realPos);
/*     */               
/*     */               boolean skip = false;
/*     */               
/*     */               if (tState == toCompare) {
/*     */                 toRender.add(Pair.of(ProjectorItem.RenderLayer.PERFECT, info));
/*     */                 
/*     */                 goodBlocks.increment();
/*     */                 
/*     */                 skip = true;
/*     */               } else if (!toCompare.m_60795_()) {
/*     */                 toRender.add(Pair.of(ProjectorItem.RenderLayer.BAD, info));
/*     */                 
/*     */                 skip = true;
/*     */               } else {
/*     */                 badBlocks.increment();
/*     */               } 
/*     */               
/*     */               if (skip) {
/*     */                 return false;
/*     */               }
/*     */             } 
/*     */             
/*     */             toRender.add(Pair.of(ProjectorItem.RenderLayer.ALL, info));
/*     */             
/*     */             return false;
/*     */           };
/* 499 */         projection.processAll(bipred);
/*     */         
/* 501 */         boolean perfect = (goodBlocks.getValue().intValue() == projection.getBlockCount());
/*     */         
/* 503 */         BlockPos.MutableBlockPos min = new BlockPos.MutableBlockPos(2147483647, 2147483647, 2147483647);
/* 504 */         BlockPos.MutableBlockPos max = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/* 505 */         float flicker = world.f_46441_.m_188501_() / 2.0F + 0.25F;
/*     */         
/* 507 */         matrix.m_85837_(hit.m_123341_(), hit.m_123342_(), hit.m_123343_());
/*     */         
/* 509 */         toRender.sort((a, b) -> {
/*     */               int ao = ((ProjectorItem.RenderLayer)a.getLeft()).ordinal();
/*     */ 
/*     */               
/*     */               int bo = ((ProjectorItem.RenderLayer)b.getLeft()).ordinal();
/*     */ 
/*     */               
/*     */               return (ao > bo) ? 1 : ((ao < bo) ? -1 : 0);
/*     */             });
/*     */ 
/*     */         
/* 520 */         MultiBufferSource.BufferSource mainBuffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
/*     */         
/* 522 */         ItemStack heldStack = player.m_21205_();
/* 523 */         for (Pair<ProjectorItem.RenderLayer, MultiblockProjection.Info> pair : toRender) {
/* 524 */           boolean held; int x; float alpha; int y, z; MultiblockProjection.Info rInfo = (MultiblockProjection.Info)pair.getRight();
/*     */           
/* 526 */           switch ((ProjectorItem.RenderLayer)pair.getLeft()) {
/*     */             case MODEL:
/* 528 */               held = (heldStack.m_41720_() == rInfo.getRawState().m_60734_().m_5456_());
/* 529 */               alpha = held ? 1.0F : 0.5F;
/*     */               
/* 531 */               matrix.m_85836_();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 536 */               renderPhantom(matrix, world, rInfo, settings.isMirrored(), flicker, alpha, partialTicks);
/*     */               
/* 538 */               if (held) {
/* 539 */                 renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 11513775, flicker);
/*     */               }
/*     */               
/* 542 */               matrix.m_85849_();
/*     */             
/*     */             case ENTITYBLOCK_ANIMATED:
/* 545 */               matrix.m_85836_();
/*     */               
/* 547 */               matrix.m_85837_(rInfo.tPos.m_123341_(), rInfo.tPos.m_123342_(), rInfo.tPos.m_123343_());
/*     */               
/* 549 */               renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 16711680, flicker);
/*     */               
/* 551 */               matrix.m_85849_();
/*     */             
/*     */             case null:
/* 554 */               x = rInfo.tPos.m_123341_();
/* 555 */               y = rInfo.tPos.m_123342_();
/* 556 */               z = rInfo.tPos.m_123343_();
/*     */               
/* 558 */               min.m_122178_(Math.min(x, min.m_123341_()), Math.min(y, min.m_123342_()), Math.min(z, min.m_123343_()));
/* 559 */               max.m_122178_(Math.max(x, max.m_123341_()), Math.max(y, max.m_123342_()), Math.max(z, max.m_123343_()));
/*     */           } 
/*     */ 
/*     */         
/*     */         } 
/* 564 */         if (perfect) {
/*     */           
/* 566 */           matrix.m_85836_();
/*     */           
/* 568 */           renderOutlineBox((MultiBufferSource)mainBuffer, matrix, (Vec3i)min, (Vec3i)max, 48896, flicker);
/*     */           
/* 570 */           matrix.m_85849_();
/*     */ 
/*     */           
/* 573 */           if (!player.m_21206_().m_41619_() && player.m_21206_().m_41720_() == IPContent.DEBUGITEM.get()) {
/* 574 */             matrix.m_85836_();
/*     */ 
/*     */             
/* 577 */             matrix.m_85837_(min.m_123341_(), min.m_123342_(), min.m_123343_());
/* 578 */             renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 16711680, flicker);
/*     */             
/* 580 */             matrix.m_85849_();
/*     */             
/* 582 */             matrix.m_85836_();
/*     */ 
/*     */             
/* 585 */             matrix.m_85837_(max.m_123341_(), max.m_123342_(), max.m_123343_());
/* 586 */             renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 65280, flicker);
/*     */             
/* 588 */             matrix.m_85849_();
/*     */             
/* 590 */             matrix.m_85836_();
/*     */ 
/*     */             
/* 593 */             BlockPos center = min.m_7949_().m_121955_((Vec3i)max);
/* 594 */             matrix.m_85837_((center.m_123341_() / 2), (center.m_123342_() / 2), (center.m_123343_() / 2));
/* 595 */             renderCenteredOutlineBox((MultiBufferSource)mainBuffer, matrix, 255, flicker);
/*     */             
/* 597 */             matrix.m_85849_();
/*     */           } 
/*     */         } 
/*     */         
/* 601 */         mainBuffer.m_109911_();
/*     */       } 
/*     */     }
/*     */     
/* 605 */     private static final Tesselator PHANTOM_TESSELATOR = new Tesselator();
/*     */     private static void renderPhantom(PoseStack matrix, Level realWorld, MultiblockProjection.Info rInfo, boolean mirror, float flicker, float alpha, float partialTicks) {
/* 607 */       BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
/* 608 */       ModelBlockRenderer blockRenderer = dispatcher.m_110937_();
/* 609 */       BlockColors blockColors = Minecraft.m_91087_().m_91298_();
/*     */ 
/*     */       
/* 612 */       matrix.m_85837_(rInfo.tPos.m_123341_(), rInfo.tPos.m_123342_(), rInfo.tPos.m_123343_());
/*     */       
/* 614 */       MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_(PHANTOM_TESSELATOR.m_85915_());
/*     */       
/* 616 */       BlockState state = rInfo.getModifiedState(realWorld, rInfo.tPos);
/*     */       
/* 618 */       ProjectorEvent.RenderBlock renderEvent = new ProjectorEvent.RenderBlock(rInfo.multiblock, rInfo.templateWorld, rInfo.tBlockInfo.f_74675_, realWorld, rInfo.tPos, state, rInfo.settings.m_74404_());
/* 619 */       if (!MinecraftForge.EVENT_BUS.post((Event)renderEvent)) {
/* 620 */         BakedModel ibakedmodel; ItemStack stack; int i; float red, green, blue; VertexConsumer vc; state = renderEvent.getState();
/* 621 */         state.m_60701_((LevelAccessor)realWorld, rInfo.tPos, 3);
/*     */         
/* 623 */         ModelData modelData = ModelData.EMPTY;
/* 624 */         BlockEntity te = rInfo.templateWorld.m_7702_(rInfo.tBlockInfo.f_74675_);
/* 625 */         if (te != null) {
/* 626 */           te.f_58856_ = state;
/* 627 */           modelData = te.getModelData();
/*     */         } 
/*     */         
/* 630 */         RenderShape blockrendertype = state.m_60799_();
/* 631 */         switch (blockrendertype) {
/*     */           case MODEL:
/* 633 */             ibakedmodel = dispatcher.m_110910_(state);
/* 634 */             i = blockColors.m_92577_(state, null, null, 0);
/* 635 */             red = (i >> 16 & 0xFF) / 255.0F;
/* 636 */             green = (i >> 8 & 0xFF) / 255.0F;
/* 637 */             blue = (i & 0xFF) / 255.0F;
/*     */             
/* 639 */             modelData = ibakedmodel.getModelData((BlockAndTintGetter)rInfo.templateWorld, rInfo.tBlockInfo.f_74675_, state, modelData);
/*     */             
/* 641 */             IPShaders.projNoise(flicker * alpha, (MCUtil.getPlayer()).f_19797_ + partialTicks);
/*     */             
/* 643 */             vc = buffer.m_6299_(IPRenderTypes.PROJECTION);
/*     */             
/* 645 */             blockRenderer.renderModel(matrix.m_85850_(), vc, state, ibakedmodel, red, green, blue, 15728880, OverlayTexture.f_118083_, modelData, null);
/*     */             break;
/*     */           case ENTITYBLOCK_ANIMATED:
/* 648 */             stack = new ItemStack((ItemLike)state.m_60734_());
/*     */             
/* 650 */             MCUtil.getItemRenderer().m_174269_(stack, ItemTransforms.TransformType.NONE, 15728880, OverlayTexture.f_118083_, matrix, (MultiBufferSource)buffer, 0);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 656 */       buffer.m_109911_();
/*     */     }
/*     */     
/*     */     private static void renderOutlineBox(MultiBufferSource buffer, PoseStack matrix, Vec3i min, Vec3i max, int rgb, float flicker) {
/* 660 */       renderBox(buffer, matrix, Vec3.m_82528_(min), Vec3.m_82528_(max).m_82520_(1.0D, 1.0D, 1.0D), rgb, flicker);
/*     */     }
/*     */     
/*     */     private static void renderBox(MultiBufferSource buffer, PoseStack matrix, Vec3 min, Vec3 max, int rgb, float flicker) {
/* 664 */       VertexConsumer builder = buffer.m_6299_(IPRenderTypes.TRANSLUCENT_LINE);
/*     */       
/* 666 */       float alpha = 0.25F + 0.5F * flicker;
/*     */       
/* 668 */       int rgba = rgb | (int)(alpha * 255.0F) << 24;
/*     */       
/* 670 */       line(builder, matrix, min, max, 2, 6, rgba);
/* 671 */       line(builder, matrix, min, max, 6, 7, rgba);
/* 672 */       line(builder, matrix, min, max, 7, 3, rgba);
/* 673 */       line(builder, matrix, min, max, 3, 2, rgba);
/*     */       
/* 675 */       line(builder, matrix, min, max, 2, 0, rgba);
/* 676 */       line(builder, matrix, min, max, 6, 4, rgba);
/* 677 */       line(builder, matrix, min, max, 3, 1, rgba);
/* 678 */       line(builder, matrix, min, max, 7, 5, rgba);
/*     */       
/* 680 */       line(builder, matrix, min, max, 0, 4, rgba);
/* 681 */       line(builder, matrix, min, max, 4, 5, rgba);
/* 682 */       line(builder, matrix, min, max, 5, 1, rgba);
/* 683 */       line(builder, matrix, min, max, 1, 0, rgba);
/*     */     }
/*     */     
/*     */     private static void renderCenteredOutlineBox(MultiBufferSource buffer, PoseStack matrix, int rgb, float flicker) {
/* 687 */       renderBox(buffer, matrix, Vec3.f_82478_, new Vec3(1.0D, 1.0D, 1.0D), rgb, flicker);
/*     */     }
/*     */     
/*     */     private static Vector3f combine(Vec3 start, Vec3 end, int mixBits) {
/* 691 */       float eps = 0.01F;
/* 692 */       return new Vector3f(
/* 693 */           (float)(((mixBits & 0x4) != 0) ? (end.f_82479_ + 0.009999999776482582D) : (start.f_82479_ - 0.009999999776482582D)), 
/* 694 */           (float)(((mixBits & 0x2) != 0) ? (end.f_82480_ + 0.009999999776482582D) : (start.f_82480_ - 0.009999999776482582D)), 
/* 695 */           (float)(((mixBits & 0x1) != 0) ? (end.f_82481_ + 0.009999999776482582D) : (start.f_82481_ - 0.009999999776482582D)));
/*     */     }
/*     */ 
/*     */     
/*     */     private static void line(VertexConsumer out, PoseStack mat, Vec3 min, Vec3 max, int startBits, int endBits, int rgba) {
/* 700 */       Vector3f start = combine(min, max, startBits);
/* 701 */       Vector3f end = combine(min, max, endBits);
/* 702 */       Vector3f delta = end.m_122281_();
/* 703 */       delta.m_122267_(start);
/* 704 */       out.m_85982_(mat.m_85850_().m_85861_(), start.m_122239_(), start.m_122260_(), start.m_122269_())
/* 705 */         .m_193479_(rgba)
/* 706 */         .m_85977_(mat.m_85850_().m_85864_(), delta.m_122239_(), delta.m_122260_(), delta.m_122269_())
/* 707 */         .m_5752_();
/* 708 */       out.m_85982_(mat.m_85850_().m_85861_(), end.m_122239_(), end.m_122260_(), end.m_122269_())
/* 709 */         .m_193479_(rgba)
/* 710 */         .m_85977_(mat.m_85850_().m_85864_(), delta.m_122239_(), delta.m_122260_(), delta.m_122269_())
/* 711 */         .m_5752_();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   @EventBusSubscriber(modid = "immersivepetroleum", value = {Dist.CLIENT})
/*     */   public static class ClientInputHandler
/*     */   {
/*     */     static boolean shiftHeld = false;
/*     */ 
/*     */     
/*     */     @SubscribeEvent
/*     */     public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 725 */       if (event.side == LogicalSide.CLIENT && event.player != null && event.player == Minecraft.m_91087_().m_91288_() && 
/* 726 */         event.phase == TickEvent.Phase.END && 
/* 727 */         !IPKeyBinds.keybind_preview_flip.m_90862_() && IPKeyBinds.keybind_preview_flip.m_90859_()) {
/* 728 */         doAFlip();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static void onSneakScrolling(InputEvent.MouseScrollingEvent event, Player player, double scrollDelta, boolean isSneaking) {
/* 735 */       ItemStack mainItem = player.m_21205_();
/* 736 */       ItemStack secondItem = player.m_21206_();
/*     */       
/* 738 */       boolean main = (mainItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(mainItem, "settings", 10));
/* 739 */       boolean off = (secondItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(secondItem, "settings", 10));
/*     */       
/* 741 */       if (main || off) {
/* 742 */         ItemStack target = main ? mainItem : secondItem;
/*     */         
/* 744 */         Settings settings = ProjectorItem.getSettings(target);
/*     */         
/* 746 */         if (scrollDelta > 0.0D) {
/* 747 */           settings.rotateCCW();
/*     */         } else {
/* 749 */           settings.rotateCW();
/*     */         } 
/*     */         
/* 752 */         settings.applyTo(target);
/* 753 */         settings.sendPacketToServer(main ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
/*     */         
/* 755 */         Direction facing = Direction.m_122407_(settings.getRotation().ordinal());
/* 756 */         player.m_5661_((Component)Component.m_237115_("desc.immersivepetroleum.info.projector.rotated." + facing), true);
/*     */         
/* 758 */         event.setCanceled(true);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void doAFlip() {
/* 763 */       LocalPlayer localPlayer = MCUtil.getPlayer();
/* 764 */       ItemStack mainItem = localPlayer.m_21205_();
/* 765 */       ItemStack secondItem = localPlayer.m_21206_();
/*     */       
/* 767 */       boolean main = (mainItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(mainItem, "settings", 10));
/* 768 */       boolean off = (secondItem.m_150930_((Item)IPContent.Items.PROJECTOR.get()) && Utils.hasKey(mainItem, "settings", 10));
/* 769 */       ItemStack target = main ? mainItem : secondItem;
/*     */       
/* 771 */       if (main || off) {
/* 772 */         MutableComponent mutableComponent; Settings settings = ProjectorItem.getSettings(target);
/*     */         
/* 774 */         settings.flip();
/* 775 */         settings.applyTo(target);
/* 776 */         settings.sendPacketToServer(main ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
/*     */ 
/*     */         
/* 779 */         if (settings.isMirrored()) {
/* 780 */           mutableComponent = Component.m_237115_("desc.immersivepetroleum.info.projector.flipped.true");
/*     */         } else {
/* 782 */           mutableComponent = Component.m_237115_("desc.immersivepetroleum.info.projector.flipped.false");
/*     */         } 
/* 784 */         localPlayer.m_5661_((Component)mutableComponent, true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public enum RenderLayer {
/* 790 */     ALL, BAD, PERFECT;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpgrades(ItemStack stack) {
/* 795 */     return stack.m_41782_() ? stack.m_41784_().m_128469_("upgrades") : new CompoundTag();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearUpgrades(ItemStack stack) {
/* 800 */     ItemUtils.removeTag(stack, "upgrades");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeFromWorkbench(ItemStack stack) {
/* 805 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canModify(ItemStack stack) {
/* 810 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculateUpgrades(ItemStack stack, Level w, Player player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromWorkbench(Player player, ItemStack stack) {}
/*     */ 
/*     */   
/*     */   public void finishUpgradeRecalculation(ItemStack stack) {}
/*     */ 
/*     */   
/* 825 */   private static final Slot[] NONE = new Slot[0];
/*     */   
/*     */   public Slot[] getWorkbenchSlots(AbstractContainerMenu container, ItemStack stack, Level level, Supplier<Player> getPlayer, IItemHandler toolInventory) {
/* 828 */     return NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag nbt) {
/* 833 */     if (!stack.m_41619_()) {
/* 834 */       final ResourceLocation key = ForgeRegistries.ITEMS.getKey(this);
/* 835 */       return (ICapabilityProvider)new IPItemStackHandler(0) {
/* 836 */           private final LazyOptional<CapabilityShader.ShaderWrapper_Item> shaders = CapabilityUtils.constantOptional(new CapabilityShader.ShaderWrapper_Item(key, stack));
/*     */ 
/*     */           
/*     */           public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
/* 840 */             if (capability == CapabilityShader.SHADER_CAPABILITY) {
/* 841 */               return this.shaders.cast();
/*     */             }
/* 843 */             return super.getCapability(capability, facing);
/*     */           }
/*     */         };
/*     */     } 
/* 847 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\items\ProjectorItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */