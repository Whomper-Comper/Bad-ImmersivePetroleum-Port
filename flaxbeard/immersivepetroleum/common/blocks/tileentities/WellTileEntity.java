/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.client.ClientProxy;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.stone.WellPipeBlock;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class WellTileEntity
/*     */   extends IPTileEntityBase
/*     */   implements IPCommonTickableTile
/*     */ {
/*     */   static final int PIPE_WORTH = 6;
/*     */   static final int DEFAULT_PIPELENGTH = 384;
/*     */   @Nonnull
/*  38 */   public List<ColumnPos> tappedIslands = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*  42 */   public final List<Integer> phyiscalPipesList = new ArrayList<>();
/*     */ 
/*     */   
/*  45 */   public int pipes = 0;
/*     */   
/*  47 */   public int wellPipeLength = 0;
/*     */   
/*  49 */   public int additionalPipes = 0;
/*     */   
/*     */   public boolean drillingCompleted;
/*     */   
/*     */   public boolean pastPhysicalPart;
/*     */   
/*     */   private boolean selfDestruct;
/*     */   private int selfDestructTimer;
/*  57 */   private Fluid spillFType = Fluids.f_76191_;
/*  58 */   private int spillHeight = -1;
/*     */   
/*     */   boolean spill = false;
/*  61 */   int clientFlow = 0;
/*     */   public WellTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
/*  63 */     super((BlockEntityType)IPTileTypes.WELL.get(), pWorldPosition, pBlockState);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readCustom(CompoundTag nbt) {
/*  68 */     this.spill = nbt.m_128471_("spill");
/*  69 */     this.clientFlow = nbt.m_128451_("flow");
/*  70 */     this.drillingCompleted = nbt.m_128471_("drillingcompleted");
/*  71 */     this.pastPhysicalPart = nbt.m_128471_("pastphyiscalpart");
/*     */     
/*  73 */     this.pipes = nbt.m_128451_("pipes");
/*  74 */     this.wellPipeLength = nbt.m_128451_("wellpipelength");
/*  75 */     this.additionalPipes = nbt.m_128451_("additionalpipes");
/*     */     
/*  77 */     this.selfDestruct = nbt.m_128471_("selfdestruct");
/*  78 */     this.selfDestructTimer = nbt.m_128451_("selfdestructtimer");
/*     */     
/*     */     try {
/*  81 */       this.spillFType = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.m_128461_("spillftype")));
/*  82 */     } catch (ResourceLocationException rle) {
/*  83 */       this.spillFType = Fluids.f_76191_;
/*     */     } 
/*  85 */     this.spillHeight = nbt.m_128451_("spillheight");
/*     */     
/*  87 */     if (nbt.m_128425_("tappedislands", 9)) {
/*  88 */       ListTag list = nbt.m_128437_("tappedislands", 10);
/*  89 */       List<ColumnPos> tmp = new ArrayList<>(list.size());
/*  90 */       list.forEach(n -> {
/*     */             CompoundTag pos = (CompoundTag)n;
/*     */             int x = pos.m_128451_("x");
/*     */             int z = pos.m_128451_("z");
/*     */             tmp.add(new ColumnPos(x, z));
/*     */           });
/*  96 */       this.tappedIslands = tmp;
/*     */     } 
/*     */     
/*  99 */     if (nbt.m_128425_("pipeLoc", 9)) {
/* 100 */       ListTag list = nbt.m_128437_("pipeLoc", 3);
/* 101 */       List<Integer> ints = new ArrayList<>(list.size());
/* 102 */       list.forEach(n -> ints.add(Integer.valueOf(((IntTag)n).m_7047_())));
/* 103 */       this.phyiscalPipesList.clear();
/* 104 */       this.phyiscalPipesList.addAll(ints);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeCustom(CompoundTag nbt) {
/* 110 */     nbt.m_128379_("spill", this.spill);
/* 111 */     nbt.m_128405_("flow", getFlow());
/*     */     
/* 113 */     nbt.m_128379_("drillingcompleted", this.drillingCompleted);
/* 114 */     nbt.m_128379_("pastphyiscalpart", this.pastPhysicalPart);
/*     */     
/* 116 */     nbt.m_128405_("pipes", this.pipes);
/* 117 */     nbt.m_128405_("wellpipelength", this.wellPipeLength);
/* 118 */     nbt.m_128405_("additionalpipes", this.additionalPipes);
/*     */     
/* 120 */     nbt.m_128379_("selfdestruct", this.selfDestruct);
/* 121 */     nbt.m_128405_("selfdestructtimer", this.selfDestructTimer);
/*     */     
/* 123 */     nbt.m_128359_("spillftype", RegistryUtils.getRegistryNameOf(this.spillFType).toString());
/* 124 */     nbt.m_128405_("spillheight", this.spillHeight);
/*     */     
/* 126 */     if (!this.tappedIslands.isEmpty()) {
/* 127 */       ListTag list = new ListTag();
/* 128 */       this.tappedIslands.forEach(c -> {
/*     */             CompoundTag pos = new CompoundTag();
/*     */             pos.m_128405_("x", c.f_140723_());
/*     */             pos.m_128405_("z", c.f_140724_());
/*     */             list.add(pos);
/*     */           });
/* 134 */       nbt.m_128365_("tappedislands", (Tag)list);
/*     */     } 
/*     */     
/* 137 */     if (!this.phyiscalPipesList.isEmpty()) {
/* 138 */       ListTag list = new ListTag();
/* 139 */       this.phyiscalPipesList.forEach(i -> list.add(IntTag.m_128679_(i.intValue())));
/* 140 */       nbt.m_128365_("pipeLoc", (Tag)list);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getFlow() {
/* 145 */     ReservoirIsland island = ReservoirHandler.getIsland(getWorldNonnull(), m_58899_());
/* 146 */     if (island == null) {
/* 147 */       return 0;
/*     */     }
/* 149 */     return island.getFlowFromPressure(getWorldNonnull(), m_58899_());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickClient() {
/* 154 */     if (this.spill && this.spillFType != Fluids.f_76191_) {
/* 155 */       BlockPos pPos = (this.spillHeight > -1) ? new BlockPos(this.f_58858_.m_123341_(), this.spillHeight, this.f_58858_.m_123343_()) : this.f_58858_.m_7494_();
/* 156 */       ClientProxy.spawnSpillParticles(this.f_58857_, pPos, this.spillFType, 10, -0.25F, this.clientFlow);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickServer() {
/* 162 */     if (this.drillingCompleted) {
/* 163 */       if (this.tappedIslands.size() > 0) {
/* 164 */         for (ColumnPos cPos : this.tappedIslands) {
/* 165 */           ReservoirIsland island = ReservoirHandler.getIsland(getWorldNonnull(), cPos);
/* 166 */           if (island != null && island.belowHydrostaticEquilibrium(getWorldNonnull())) {
/* 167 */             island.equalizeHydrostaticPressure(getWorldNonnull());
/*     */           }
/*     */         } 
/*     */         
/* 171 */         if (getWorldNonnull().m_46467_() % 5L == 0L) {
/* 172 */           boolean spill = false;
/*     */           
/* 174 */           int height = -1;
/* 175 */           Fluid fType = Fluids.f_76191_;
/*     */           
/* 177 */           BlockEntity teHigh = getWorldNonnull().m_7702_(m_58899_().m_7494_());
/* 178 */           if (teHigh instanceof WellPipeTileEntity) { WellPipeTileEntity well = (WellPipeTileEntity)teHigh;
/* 179 */             Pair<Boolean, BlockPos> result = well.hasValidConnection();
/*     */ 
/*     */             
/* 182 */             if (!((Boolean)result.getLeft()).booleanValue() || getWorldNonnull().m_7702_((BlockPos)result.getRight()) instanceof PumpjackTileEntity) {
/* 183 */               for (ColumnPos cPos : this.tappedIslands) {
/* 184 */                 ReservoirIsland island = ReservoirHandler.getIsland(getWorldNonnull(), cPos);
/*     */ 
/*     */                 
/* 187 */                 if (island != null && island.getPressure(getWorldNonnull(), cPos.f_140723_(), cPos.f_140724_()) > 0.0D) {
/* 188 */                   fType = island.getFluid();
/* 189 */                   height = ((BlockPos)result.getRight()).m_123342_();
/* 190 */                   spill = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } }
/*     */           else
/* 197 */           { ColumnPos cPos = this.tappedIslands.get(0);
/* 198 */             ReservoirIsland island = ReservoirHandler.getIsland(getWorldNonnull(), cPos);
/*     */             
/* 200 */             if (island != null && island.getPressure(getWorldNonnull(), cPos.f_140723_(), cPos.f_140724_()) > 0.0D) {
/* 201 */               spill = true;
/* 202 */               fType = island.getFluid();
/* 203 */               height = this.f_58858_.m_123342_() + 1;
/*     */             }  }
/*     */ 
/*     */           
/* 207 */           if (spill != this.spill || (spill && getWorldNonnull().m_46467_() % 10L == 0L)) {
/* 208 */             this.spill = spill;
/*     */             
/* 210 */             if (this.spill) {
/* 211 */               this.spillHeight = height;
/* 212 */               this.spillFType = fType;
/*     */             } else {
/* 214 */               this.spillHeight = -1;
/* 215 */               this.spillFType = Fluids.f_76191_;
/*     */             } 
/*     */             
/* 218 */             m_6596_();
/*     */           } 
/*     */         } 
/*     */         
/* 222 */         if (this.spill) {
/* 223 */           for (ColumnPos cPos : this.tappedIslands) {
/* 224 */             ReservoirIsland island = ReservoirHandler.getIsland(getWorldNonnull(), cPos);
/*     */             
/* 226 */             if (island != null)
/*     */             {
/* 228 */               island.extractWithPressure(getWorldNonnull(), cPos.f_140723_(), cPos.f_140724_());
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/* 234 */     } else if (this.selfDestruct && advanceTimer()) {
/*     */       
/* 236 */       if (!this.phyiscalPipesList.isEmpty()) {
/* 237 */         for (Integer integer : this.phyiscalPipesList) {
/* 238 */           BlockPos pos = m_58899_();
/* 239 */           pos = new BlockPos(pos.m_123341_(), integer.intValue(), pos.m_123343_());
/*     */           
/* 241 */           BlockState state = getWorldNonnull().m_8055_(pos);
/*     */           
/* 243 */           if (state.m_60734_() instanceof WellPipeBlock) {
/* 244 */             getWorldNonnull().m_46597_(pos, (BlockState)state.m_61124_((Property)WellPipeBlock.BROKEN, Boolean.valueOf(true)));
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 249 */       getWorldNonnull().m_46597_(m_58899_(), Blocks.f_50752_.m_49966_());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void usePipe() {
/* 255 */     if (this.drillingCompleted) {
/*     */       return;
/*     */     }
/*     */     
/* 259 */     this.pipes--;
/* 260 */     this.wellPipeLength++;
/*     */     
/* 262 */     if (this.wellPipeLength >= getMaxPipeLength()) {
/* 263 */       this.drillingCompleted = true;
/*     */     }
/*     */     
/* 266 */     m_6596_();
/*     */   }
/*     */   
/*     */   public int getMaxPipeLength() {
/* 270 */     return 384 + this.additionalPipes;
/*     */   }
/*     */   
/*     */   public void startSelfDestructSequence() {
/* 274 */     if (this.drillingCompleted) {
/*     */       return;
/*     */     }
/*     */     
/* 278 */     this.selfDestruct = true;
/* 279 */     this.selfDestructTimer = 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public void abortSelfDestructSequence() {
/* 284 */     if (this.selfDestruct) {
/* 285 */       this.selfDestruct = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean advanceTimer() {
/* 290 */     return (this.selfDestruct && this.selfDestructTimer-- <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6596_() {
/* 295 */     super.m_6596_();
/*     */     
/* 297 */     BlockState state = this.f_58857_.m_8055_(this.f_58858_);
/* 298 */     this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
/* 299 */     this.f_58857_.m_46672_(this.f_58858_, state.m_60734_());
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\WellTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */