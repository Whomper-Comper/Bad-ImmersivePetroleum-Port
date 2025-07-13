/*     */ package flaxbeard.immersivepetroleum.common;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
/*     */ import flaxbeard.immersivepetroleum.common.entity.MotorboatEntity;
/*     */ import flaxbeard.immersivepetroleum.common.fluids.NapalmFluid;
/*     */ import flaxbeard.immersivepetroleum.common.util.IPEffects;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.Skeleton;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinLevelEvent;
/*     */ import net.minecraftforge.event.entity.EntityMountEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.level.LevelEvent;
/*     */ import net.minecraftforge.event.server.ServerStoppedEvent;
/*     */ import net.minecraftforge.eventbus.api.EventPriority;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.LogicalSide;
/*     */ 
/*     */ 
/*     */ public class CommonEventHandler
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void onSave(LevelEvent.Save event) {
/*  61 */     if (!event.getLevel().m_5776_()) {
/*  62 */       IPSaveData.markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUnload(LevelEvent.Unload event) {
/*  68 */     if (!event.getLevel().m_5776_()) {
/*  69 */       IPSaveData.markDirty();
/*  70 */       ReservoirRegionDataStorage.get().markAllDirty();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onServerStopped(ServerStoppedEvent event) {
/*  76 */     ImmersivePetroleum.log.debug("[ReservoirIslands]: Clearing Cache...");
/*  77 */     ReservoirHandler.clearCache();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleBoatImmunity(LivingAttackEvent event) {
/*  82 */     if (event.getSource() == DamageSource.f_19308_ || event.getSource() == DamageSource.f_19307_ || event.getSource() == DamageSource.f_19305_) {
/*  83 */       LivingEntity entity = event.getEntity();
/*  84 */       Entity entity1 = entity.m_20202_(); if (entity1 instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity1;
/*  85 */         if (boat.isFireproof) {
/*  86 */           event.setCanceled(true);
/*     */           
/*     */           return;
/*     */         }  }
/*     */       
/*  91 */       if (entity.m_20094_() > 0 && entity.m_21124_((MobEffect)IPEffects.ANTI_DISMOUNT_FIRE.get()) != null) {
/*  92 */         entity.m_20095_();
/*  93 */         entity.m_21195_((MobEffect)IPEffects.ANTI_DISMOUNT_FIRE.get());
/*  94 */         event.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleBoatImmunity(TickEvent.PlayerTickEvent event) {
/* 101 */     Player entity = event.player;
/* 102 */     if (entity.m_6060_()) { Entity entity1 = entity.m_20202_(); if (entity1 instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity1;
/* 103 */         if (boat.isFireproof) {
/* 104 */           entity.m_20095_();
/* 105 */           boat.m_20115_(0, false);
/*     */         }  }
/*     */        }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleDismountingBoat(EntityMountEvent event) {
/* 115 */     if (event.getEntityMounting() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     Entity entity = event.getEntityMounting(); if (entity instanceof LivingEntity) { LivingEntity living = (LivingEntity)entity; entity = event.getEntityBeingMounted(); if (entity instanceof MotorboatEntity) { MotorboatEntity boat = (MotorboatEntity)entity;
/* 120 */         if (event.isDismounting() && 
/* 121 */           boat.isFireproof) {
/* 122 */           FluidState fluidstate = event.getLevel().m_8055_(new BlockPos(boat.m_20182_().m_82520_(0.5D, 0.0D, 0.5D))).m_60819_();
/* 123 */           if (fluidstate != Fluids.f_76191_.m_76145_() && fluidstate.m_205070_(FluidTags.f_13132_)) {
/* 124 */             living.m_7292_(new MobEffectInstance((MobEffect)IPEffects.ANTI_DISMOUNT_FIRE.get(), 1, 0, false, false));
/*     */           }
/*     */         }  }
/*     */        }
/*     */   
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleLubricatingMachinesServer(TickEvent.LevelTickEvent event) {
/* 133 */     if (event.phase == TickEvent.Phase.END) {
/* 134 */       handleLubricatingMachines(event.level);
/*     */     }
/*     */   }
/*     */   
/* 138 */   static final Random random = new Random();
/*     */   
/*     */   public static void handleLubricatingMachines(Level world) {
/* 141 */     Set<LubricatedHandler.LubricatedTileInfo> toRemove = new HashSet<>();
/* 142 */     for (LubricatedHandler.LubricatedTileInfo info : LubricatedHandler.lubricatedTiles) {
/* 143 */       if (info.world == world.m_46472_() && world.isAreaLoaded(info.pos, 0)) {
/* 144 */         BlockEntity te = world.m_7702_(info.pos);
/* 145 */         LubricatedHandler.ILubricationHandler lubeHandler = LubricatedHandler.getHandlerForTile(te);
/* 146 */         if (lubeHandler != null) {
/* 147 */           if (lubeHandler.isMachineEnabled(world, te)) {
/* 148 */             if (world.f_46443_) {
/* 149 */               lubeHandler.lubricateClient((ClientLevel)world, info.lubricant, info.ticks, te);
/*     */             } else {
/* 151 */               lubeHandler.lubricateServer((ServerLevel)world, info.lubricant, info.ticks, te);
/*     */             } 
/*     */           }
/*     */           
/* 155 */           if (world.f_46443_ && 
/* 156 */             te instanceof MultiblockPartBlockEntity) { MultiblockPartBlockEntity<?> part = (MultiblockPartBlockEntity)te;
/*     */             
/* 158 */             Vec3i size = lubeHandler.getStructureDimensions();
/* 159 */             int numBlocks = (int)((size.m_123341_() * size.m_123342_() * size.m_123343_()) * 0.25F);
/* 160 */             for (int i = 0; i < numBlocks; i++) {
/* 161 */               BlockPos pos = part.getBlockPosForPos(new BlockPos((size.m_123341_() * random.nextFloat()), (size.m_123342_() * random.nextFloat()), (size.m_123343_() * random.nextFloat())));
/*     */               
/* 163 */               if (world.m_8055_(pos).m_60734_() != Blocks.f_50016_) { BlockEntity blockEntity = world.m_7702_(pos); if (blockEntity instanceof MultiblockPartBlockEntity) { MultiblockPartBlockEntity part2 = (MultiblockPartBlockEntity)blockEntity; if (part2.master() == part.master()) {
/* 164 */                     for (Direction facing : Direction.Plane.HORIZONTAL) {
/* 165 */                       if (world.f_46441_.m_188503_(30) == 0) {
/* 166 */                         Vec3i direction = facing.m_122436_();
/*     */                         
/* 168 */                         float x = pos.m_123341_() + 0.5F + direction.m_123341_() * 0.65F;
/* 169 */                         float y = (pos.m_123342_() + 1);
/* 170 */                         float z = pos.m_123343_() + 0.5F + direction.m_123343_() * 0.65F;
/*     */                         
/* 172 */                         world.m_7106_((ParticleOptions)ParticleTypes.f_123780_, x, y, z, 0.0D, 0.0D, 0.0D);
/*     */                       } 
/*     */                     } 
/*     */                   } }
/*     */                  }
/*     */             
/*     */             }  }
/*     */           
/* 180 */           if (info.ticks-- <= 0) {
/* 181 */             toRemove.add(info);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 186 */     for (LubricatedHandler.LubricatedTileInfo info : toRemove) {
/* 187 */       LubricatedHandler.lubricatedTiles.remove(info);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityJoiningWorld(EntityJoinLevelEvent event) {
/* 193 */     Entity entity = event.getEntity(); if (entity instanceof Player) { Player player = (Player)entity;
/* 194 */       if (event.getEntity() instanceof net.minecraftforge.common.util.FakePlayer) {
/*     */         return;
/*     */       }
/*     */       
/* 198 */       if (((Boolean)IPServerConfig.MISCELLANEOUS.autounlock_recipes.get()).booleanValue()) {
/* 199 */         List<Recipe<?>> l = new ArrayList<>();
/* 200 */         Collection<Recipe<?>> recipes = event.getLevel().m_7465_().m_44051_();
/* 201 */         recipes.forEach(recipe -> {
/*     */               ResourceLocation name = recipe.m_6423_();
/*     */               
/*     */               if (name.m_135827_().equals("immersivepetroleum")) {
/*     */                 l.add(recipe);
/*     */               }
/*     */             });
/* 208 */         player.m_7281_(l);
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void livingDeath(LivingDeathEvent event) {
/* 215 */     LivingEntity livingEntity = event.getEntity(); if (livingEntity instanceof Skeleton) { Skeleton skelly = (Skeleton)livingEntity; if (!skelly.f_19853_.f_46443_) {
/* 216 */         DamageSource src = event.getSource();
/* 217 */         Entity entity = src.m_7639_(); if (entity instanceof Player) { Player player = (Player)entity; if (!player.f_19853_.f_46443_) {
/* 218 */             Entity entity1 = player.m_20202_(); if (entity1 instanceof MotorboatEntity) { MotorboatEntity motorboat = (MotorboatEntity)entity1; if (!motorboat.f_19853_.f_46443_ && 
/* 219 */                 src.m_19360_() && motorboat.isSpinningFastEnough() && motorboat.hasRudders)
/* 220 */                 Utils.unlockIPAdvancement(player, "main/rudders");  }
/*     */           
/*     */           }  }
/*     */       
/*     */       }  }
/*     */   
/*     */   }
/* 227 */   public static final Map<ResourceLocation, List<BlockPos>> napalmPositions = new HashMap<>();
/* 228 */   public static final Map<ResourceLocation, List<BlockPos>> toRemove = new HashMap<>();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void handleNapalm(TickEvent.LevelTickEvent event) {
/* 232 */     if (event.side == LogicalSide.CLIENT) {
/*     */       return;
/*     */     }
/* 235 */     ResourceLocation d = event.level.m_46472_().m_135782_();
/*     */     
/* 237 */     switch (event.phase) {
/*     */       case START:
/* 239 */         if (napalmPositions.get(d) != null) {
/* 240 */           List<BlockPos> trList = toRemove.computeIfAbsent(d, f -> new ArrayList());
/*     */           
/* 242 */           (new ArrayList(napalmPositions.get(d))).forEach(pos -> {
/*     */                 BlockState state = event.level.m_8055_(pos); Block patt9339$temp = state.m_60734_();
/*     */                 if (patt9339$temp instanceof LiquidBlock) {
/*     */                   LiquidBlock fluidBlock = (LiquidBlock)patt9339$temp;
/*     */                   if (fluidBlock == IPContent.Fluids.NAPALM.block().get())
/*     */                     NapalmFluid.processFire(IPContent.Fluids.NAPALM, event.level, pos); 
/*     */                 } 
/*     */                 trList.add(pos);
/*     */               });
/*     */         } 
/*     */         break;
/*     */       case END:
/* 254 */         if (toRemove.get(d) != null && napalmPositions.get(d) != null) {
/* 255 */           List<BlockPos> list = new ArrayList<>(toRemove.get(d));
/* 256 */           ((List)napalmPositions.get(d)).removeAll(list);
/* 257 */           ((List)toRemove.get(d)).clear();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\CommonEventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */