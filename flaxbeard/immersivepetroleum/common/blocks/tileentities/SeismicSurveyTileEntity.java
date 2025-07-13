/*     */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.items.BulletItem;
/*     */ import blusunrize.immersiveengineering.common.util.IESounds;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.common.ExternalModContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPContent;
/*     */ import flaxbeard.immersivepetroleum.common.IPTileTypes;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.ticking.IPCommonTickableTile;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.IslandInfo;
/*     */ import flaxbeard.immersivepetroleum.common.util.survey.SurveyScan;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class SeismicSurveyTileEntity
/*     */   extends IPTileEntityBase
/*     */   implements IPCommonTickableTile
/*     */ {
/*     */   public static final int DELAY = 10;
/*  38 */   public int timer = 0;
/*     */   @Nonnull
/*  40 */   public ItemStack stack = ItemStack.f_41583_;
/*     */   public boolean isSlave;
/*     */   
/*     */   public SeismicSurveyTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
/*  44 */     super((BlockEntityType)IPTileTypes.SEISMIC_SURVEY.get(), pWorldPosition, pBlockState);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeCustom(CompoundTag tag) {
/*  49 */     tag.m_128379_("slave", this.isSlave);
/*  50 */     tag.m_128405_("timer", this.timer);
/*  51 */     tag.m_128365_("stack", (Tag)this.stack.serializeNBT());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readCustom(CompoundTag tag) {
/*  56 */     this.isSlave = tag.m_128471_("slave");
/*  57 */     this.timer = tag.m_128451_("timer");
/*  58 */     this.stack = ItemStack.m_41712_(tag.m_128469_("stack"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickClient() {}
/*     */ 
/*     */   
/*     */   public void tickServer() {
/*  67 */     if (this.timer > 0) {
/*  68 */       this.timer--;
/*  69 */       m_6596_();
/*     */     } 
/*     */   }
/*     */   
/*     */   public SeismicSurveyTileEntity master() {
/*  74 */     if (this.isSlave)
/*  75 */       for (int i = 1; i < 3; i++) {
/*  76 */         BlockEntity te = this.f_58857_.m_7702_(m_58899_().m_7918_(0, -i, 0));
/*  77 */         if (te instanceof SeismicSurveyTileEntity) { SeismicSurveyTileEntity seis = (SeismicSurveyTileEntity)te; if (!seis.isSlave) {
/*  78 */             return seis;
/*     */           } }
/*     */       
/*     */       }  
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public boolean interact(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand) {
/*  86 */     if (this.timer > 0) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     ItemStack held = player.m_21120_(InteractionHand.MAIN_HAND);
/*     */     
/*  92 */     if (player.m_6144_() && !this.stack.m_41619_()) {
/*  93 */       if (!world.f_46443_) {
/*  94 */         Utils.dropItemNoDelay(world, player.m_20183_().m_7494_(), this.stack);
/*  95 */         this.stack = ItemStack.f_41583_;
/*  96 */         m_6596_();
/*     */       } 
/*     */       
/*  99 */       return true;
/* 100 */     }  if (held.m_41619_()) {
/* 101 */       boolean fire = false;
/*     */       
/* 103 */       if (!this.stack.m_41619_()) {
/* 104 */         if (ExternalModContent.isIEItem_Buckshot(this.stack)) {
/* 105 */           fire = true;
/*     */           
/* 107 */           if (!world.f_46443_) {
/* 108 */             this.timer = 10;
/* 109 */             this.stack = new ItemStack((ItemLike)ExternalModContent.getIEItem_EmptyShell());
/* 110 */             m_6596_();
/*     */           } 
/*     */         } else {
/*     */           
/* 114 */           if (!world.f_46443_) {
/* 115 */             Utils.dropItemNoDelay(world, player.m_20183_().m_7494_(), this.stack);
/* 116 */             this.stack = ItemStack.f_41583_;
/* 117 */             m_6596_();
/*     */             
/* 119 */             double bX = pos.m_123341_() + 0.5D;
/* 120 */             double bY = pos.m_123342_() + 0.5D;
/* 121 */             double bZ = pos.m_123343_() + 0.5D;
/*     */             
/* 123 */             world.m_6263_(null, bX, bY, bZ, SoundEvents.f_12019_, SoundSource.BLOCKS, 0.5F, 0.25F);
/* 124 */             world.m_6263_(null, bX, bY, bZ, SoundEvents.f_12167_, SoundSource.BLOCKS, 0.25F, 0.1F);
/*     */           } 
/*     */           
/* 127 */           return true;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 132 */       if (fire) {
/* 133 */         if (world.f_46443_) {
/* 134 */           if (!player.m_7500_()) {
/* 135 */             double dst = Math.sqrt(player.m_20275_(pos.m_123341_() + 0.5D, pos.m_123342_() + 0.5D, pos.m_123343_() + 0.5D));
/* 136 */             if (dst < 4.0D) {
/* 137 */               double scale = 1.0D - Mth.m_14008_(dst / 3.0D, 0.0D, 1.0D);
/*     */               
/* 139 */               player.f_20917_ = 40;
/* 140 */               player.f_20916_ = 40 + (int)(30.0D * scale);
/* 141 */               player.f_20918_ = (Math.random() < 0.5D) ? 180.0F : 0.0F;
/*     */             } 
/*     */           } 
/*     */           
/* 145 */           double bX = pos.m_123341_() + 0.5D;
/* 146 */           double bY = pos.m_123342_() + 0.25D;
/* 147 */           double bZ = pos.m_123343_() + 0.5D;
/*     */           
/* 149 */           double hSpeed = 0.05D; float i;
/* 150 */           for (i = 0.0F; i < 360.0F; i += 11.25F) {
/* 151 */             double xa = Math.sin(Math.toRadians(i));
/* 152 */             double za = Math.cos(Math.toRadians(i));
/*     */             
/* 154 */             xa *= 0.75D;
/* 155 */             za *= 0.75D;
/*     */             
/* 157 */             world.m_7106_((Math.random() < 0.5D) ? (ParticleOptions)ParticleTypes.f_123762_ : (ParticleOptions)ParticleTypes.f_123755_, bX + xa, bY, bZ + za, hSpeed * xa, 0.0D, hSpeed * za);
/*     */           } 
/*     */         } else {
/* 160 */           SoundEvent sound = ((BulletItem)ExternalModContent.getIEItem_Buckshot()).getType().getSound();
/* 161 */           if (sound == null) {
/* 162 */             sound = (SoundEvent)IESounds.revolverFire.get();
/*     */           }
/*     */           
/* 165 */           double bX = pos.m_123341_() + 0.5D;
/* 166 */           double bY = pos.m_123342_() + 0.5D;
/* 167 */           double bZ = pos.m_123343_() + 0.5D;
/*     */           
/* 169 */           double dst = Math.sqrt(player.m_20275_(bX, bY, bZ));
/* 170 */           float volume = (float)(1.0D - Mth.m_14008_(dst / 3.0D, 0.0D, 0.85D));
/*     */           
/* 172 */           world.m_6263_(null, bX, bY, bZ, sound, SoundSource.BLOCKS, volume, 0.5F);
/*     */         } 
/*     */         
/* 175 */         if (!world.f_46443_) {
/* 176 */           SurveyScan surveyScan; ReservoirIsland currentIsland = ReservoirHandler.getIslandNoCache(world, pos);
/*     */           
/* 178 */           ItemStack stack = new ItemStack((ItemLike)IPContent.Items.SURVEYRESULT.get());
/*     */ 
/*     */           
/* 181 */           if (currentIsland != null) {
/*     */ 
/*     */             
/* 184 */             IslandInfo islandInfo = new IslandInfo(world, pos, currentIsland);
/*     */             
/* 186 */             if (islandInfo.getFluid().equals(IPContent.Fluids.CRUDEOIL.get())) {
/* 187 */               Utils.unlockIPAdvancement(player, "main/root");
/*     */             
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 193 */             surveyScan = new SurveyScan(world, pos);
/*     */           } 
/*     */           
/* 196 */           surveyScan.writeToStack(stack);
/*     */           
/* 198 */           Utils.dropItemNoDelay(world, player.m_20183_().m_7494_(), stack);
/*     */         } 
/*     */         
/* 201 */         return true;
/*     */       } 
/*     */       
/* 204 */       return false;
/* 205 */     }  if (ExternalModContent.isIEItem_Buckshot(held) && 
/* 206 */       this.stack.m_41619_()) {
/* 207 */       if (!world.f_46443_) {
/* 208 */         ItemStack copy = held.m_41777_();
/* 209 */         copy.m_41764_(1);
/* 210 */         this.stack = copy;
/*     */         
/* 212 */         if (!player.m_7500_()) {
/* 213 */           held.m_41774_(1);
/* 214 */           if (held.m_41619_()) {
/* 215 */             player.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
/*     */           }
/*     */         } 
/*     */         
/* 219 */         m_6596_();
/*     */       } 
/*     */       
/* 222 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void m_6596_() {
/* 231 */     super.m_6596_();
/*     */     
/* 233 */     BlockState state = this.f_58857_.m_8055_(this.f_58858_);
/* 234 */     this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
/* 235 */     this.f_58857_.m_46672_(this.f_58858_, state.m_60734_());
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\SeismicSurveyTileEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */