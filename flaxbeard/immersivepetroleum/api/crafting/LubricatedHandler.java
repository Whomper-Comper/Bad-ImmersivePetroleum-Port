/*     */ package flaxbeard.immersivepetroleum.api.crafting;
/*     */ 
/*     */ import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
/*     */ import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.config.IEServerConfig;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LubricatedHandler
/*     */ {
/*  61 */   static final Map<Class<? extends BlockEntity>, ILubricationHandler<? extends BlockEntity>> lubricationHandlers = new HashMap<>();
/*     */   
/*     */   public static <T extends BlockEntity> void registerLubricatedTile(Class<T> tileClass, Supplier<ILubricationHandler<T>> handler) {
/*  64 */     ILubricationHandler<T> instance = handler.get();
/*  65 */     lubricationHandlers.put(tileClass, instance);
/*     */   }
/*     */   
/*     */   public static <T extends BlockEntity> ILubricationHandler<T> getHandlerForTile(T te) {
/*  69 */     if (te != null) {
/*  70 */       Class<? extends BlockEntity> teClass = (Class)te.getClass();
/*  71 */       if (lubricationHandlers.containsKey(teClass)) {
/*     */         
/*  73 */         ILubricationHandler<T> tmp = (ILubricationHandler<T>)lubricationHandlers.get(teClass);
/*  74 */         return tmp;
/*     */       } 
/*     */     } 
/*  77 */     return null;
/*     */   } public static interface ILubricationHandler<E extends BlockEntity> {
/*     */     Tuple<BlockPos, Direction> getGhostBlockPosition(Level param1Level, E param1E); Vec3i getStructureDimensions(); boolean isMachineEnabled(Level param1Level, E param1E); BlockEntity isPlacedCorrectly(Level param1Level, AutoLubricatorTileEntity param1AutoLubricatorTileEntity, Direction param1Direction); void lubricateClient(ClientLevel param1ClientLevel, Fluid param1Fluid, int param1Int, E param1E); void lubricateServer(ServerLevel param1ServerLevel, Fluid param1Fluid, int param1Int, E param1E);
/*     */     void spawnLubricantParticles(ClientLevel param1ClientLevel, AutoLubricatorTileEntity param1AutoLubricatorTileEntity, Direction param1Direction, E param1E);
/*     */     @OnlyIn(Dist.CLIENT)
/*     */     void renderPipes(AutoLubricatorTileEntity param1AutoLubricatorTileEntity, E param1E, PoseStack param1PoseStack, MultiBufferSource param1MultiBufferSource, int param1Int1, int param1Int2); }
/*  83 */   public static class LubricatedTileInfo { public BlockPos pos; public Fluid lubricant = Fluids.f_76191_; public ResourceKey<Level> world;
/*     */     public int ticks;
/*     */     
/*     */     public LubricatedTileInfo(ResourceKey<Level> registryKey, BlockPos pos, Fluid lubricant, int ticks) {
/*  87 */       this.world = registryKey;
/*  88 */       this.pos = pos;
/*  89 */       this.ticks = ticks;
/*     */       
/*  91 */       if (lubricant != null && lubricant != Fluids.f_76191_) {
/*  92 */         this.lubricant = lubricant;
/*     */       }
/*     */     }
/*     */     
/*     */     public LubricatedTileInfo(CompoundTag tag) {
/*  97 */       int ticks = tag.m_128451_("ticks");
/*  98 */       int x = tag.m_128451_("x");
/*  99 */       int y = tag.m_128451_("y");
/* 100 */       int z = tag.m_128451_("z");
/* 101 */       String name = tag.m_128461_("world");
/* 102 */       String lubricantName = tag.m_128461_("lubricant");
/*     */       
/* 104 */       this.world = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(name));
/* 105 */       this.pos = new BlockPos(x, y, z);
/* 106 */       this.ticks = ticks;
/*     */       
/* 108 */       this.lubricant = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(lubricantName));
/* 109 */       if (this.lubricant == null) {
/* 110 */         this.lubricant = Fluids.f_76191_;
/*     */       }
/*     */     }
/*     */     
/*     */     public CompoundTag writeToNBT() {
/* 115 */       CompoundTag tag = new CompoundTag();
/*     */       
/* 117 */       tag.m_128405_("ticks", this.ticks);
/* 118 */       tag.m_128405_("x", this.pos.m_123341_());
/* 119 */       tag.m_128405_("y", this.pos.m_123342_());
/* 120 */       tag.m_128405_("z", this.pos.m_123343_());
/* 121 */       tag.m_128359_("world", this.world.m_135782_().toString());
/* 122 */       tag.m_128359_("lubricant", ForgeRegistries.FLUIDS.getKey(this.lubricant).toString());
/*     */       
/* 124 */       return tag;
/*     */     } }
/*     */ 
/*     */   
/* 128 */   public static List<LubricatedTileInfo> lubricatedTiles = new ArrayList<>();
/*     */   
/*     */   public static boolean lubricateTile(BlockEntity tile, Fluid lubricant, int ticks) {
/* 131 */     return lubricateTile(tile, lubricant, ticks, false, -1);
/*     */   }
/*     */   public static boolean lubricateTile(BlockEntity tile, Fluid lubricant, int ticks, boolean additive, int cap) {
/*     */     MultiblockPartBlockEntity multiblockPartBlockEntity;
/* 135 */     if (tile instanceof MultiblockPartBlockEntity) { MultiblockPartBlockEntity<?> mpte = (MultiblockPartBlockEntity)tile; if (mpte.offsetToMaster != BlockPos.f_121853_) {
/* 136 */         multiblockPartBlockEntity = mpte.master();
/*     */       } }
/*     */     
/* 139 */     if (getHandlerForTile(multiblockPartBlockEntity) != null) {
/* 140 */       BlockPos pos = multiblockPartBlockEntity.m_58899_();
/*     */       
/* 142 */       ResourceKey<Level> key = multiblockPartBlockEntity.m_58904_().m_46472_();
/* 143 */       for (LubricatedTileInfo info : lubricatedTiles) {
/* 144 */         if (info.pos.equals(pos) && info.world == key) {
/* 145 */           if (info.ticks >= ticks) {
/* 146 */             if (additive) {
/* 147 */               if (cap == -1) {
/* 148 */                 info.ticks += ticks;
/*     */               } else {
/* 150 */                 info.ticks = Math.min(cap, info.ticks + ticks);
/*     */               } 
/* 152 */               return true;
/*     */             } 
/* 154 */             return false;
/*     */           } 
/*     */ 
/*     */           
/* 158 */           info.ticks = ticks;
/* 159 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       LubricatedTileInfo lti = new LubricatedTileInfo(multiblockPartBlockEntity.m_58904_().m_46472_(), multiblockPartBlockEntity.m_58899_(), lubricant, ticks);
/* 164 */       lubricatedTiles.add(lti);
/*     */       
/* 166 */       return true;
/*     */     } 
/*     */     
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public static class LubricantEffect
/*     */     extends ChemthrowerHandler.ChemthrowerEffect {
/*     */     public void applyToEntity(LivingEntity target, Player shooter, ItemStack thrower, Fluid fluid) {
/* 175 */       if (target instanceof net.minecraft.world.entity.animal.IronGolem && 
/* 176 */         LubricantHandler.isValidLube(fluid)) {
/* 177 */         int ticks = Math.max(1, ((Integer)IEServerConfig.TOOLS.chemthrower_consumption.get()).intValue() / LubricantHandler.getLubeAmount(fluid)) * 4 / 3;
/*     */         
/* 179 */         MobEffectInstance activeSpeed = target.m_21124_(MobEffects.f_19596_);
/* 180 */         int ticksSpeed = ticks;
/* 181 */         if (activeSpeed != null && activeSpeed.m_19564_() <= 1) {
/* 182 */           ticksSpeed = Math.min(activeSpeed.m_19557_() + ticks, 1200);
/*     */         }
/*     */         
/* 185 */         MobEffectInstance activeStrength = target.m_21124_(MobEffects.f_19600_);
/* 186 */         int ticksStrength = ticks;
/* 187 */         if (activeStrength != null && activeStrength.m_19564_() <= 1) {
/* 188 */           ticksStrength = Math.min(activeStrength.m_19557_() + ticks, 1200);
/*     */         }
/*     */         
/* 191 */         target.m_7292_(new MobEffectInstance(MobEffects.f_19596_, ticksSpeed, 1));
/* 192 */         target.m_7292_(new MobEffectInstance(MobEffects.f_19600_, ticksStrength, 1));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void applyToBlock(Level world, HitResult mop, Player shooter, ItemStack thrower, Fluid fluid) {
/* 200 */       if (LubricantHandler.isValidLube(fluid)) {
/* 201 */         int amount = Math.max(1, ((Integer)IEServerConfig.TOOLS.chemthrower_consumption.get()).intValue() / LubricantHandler.getLubeAmount(fluid)) * 2 / 3;
/* 202 */         LubricatedHandler.lubricateTile(world.m_7702_(new BlockPos(mop.m_82450_())), fluid, amount, true, 1200);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\api\crafting\LubricatedHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */