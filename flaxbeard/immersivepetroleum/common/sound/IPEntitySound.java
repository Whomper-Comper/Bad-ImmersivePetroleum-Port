/*     */ package flaxbeard.immersivepetroleum.common.sound;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.items.EarmuffsItem;
/*     */ import blusunrize.immersiveengineering.common.register.IEItems;
/*     */ import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
/*     */ import flaxbeard.immersivepetroleum.client.utils.MCUtil;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.sounds.Sound;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.client.resources.sounds.TickableSoundInstance;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.client.sounds.WeighedSoundEvents;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ @OnlyIn(Dist.CLIENT)
/*     */ public class IPEntitySound
/*     */   implements TickableSoundInstance {
/*     */   protected Sound sound;
/*     */   private WeighedSoundEvents soundEvent;
/*     */   private SoundSource category;
/*     */   public SoundInstance.Attenuation attenuation;
/*     */   public final ResourceLocation resource;
/*     */   public float volume;
/*     */   public float pitch;
/*     */   public Entity entity;
/*     */   public boolean canRepeat;
/*     */   public int repeatDelay;
/*  41 */   public float volumeAjustment = 1.0F; public boolean donePlaying;
/*     */   
/*     */   public IPEntitySound(SoundEvent event, float volume, float pitch, boolean repeat, int repeatDelay, Entity e, SoundInstance.Attenuation attenuation, SoundSource category) {
/*  44 */     this(RegistryUtils.getRegistryNameOf(event), volume, pitch, repeat, repeatDelay, e, attenuation, category);
/*     */   }
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
/*     */   @Nonnull
/*     */   public SoundInstance.Attenuation m_7438_() {
/*  61 */     return this.attenuation;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResourceLocation m_7904_() {
/*  67 */     return this.resource;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WeighedSoundEvents m_6775_(SoundManager handler) {
/*  73 */     this.soundEvent = handler.m_120384_(this.resource);
/*  74 */     if (this.soundEvent == null) {
/*  75 */       this.sound = SoundManager.f_120344_;
/*     */     } else {
/*  77 */       this.sound = this.soundEvent.m_213718_(RandomSource.m_216327_());
/*  78 */     }  return this.soundEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Sound m_5891_() {
/*  84 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SoundSource m_8070_() {
/*  90 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public float m_7769_() {
/*  95 */     return this.volume * this.volumeAjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   public float m_7783_() {
/* 100 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public double m_7772_() {
/* 105 */     return (float)this.entity.m_20185_();
/*     */   }
/*     */ 
/*     */   
/*     */   public double m_7780_() {
/* 110 */     return (float)this.entity.m_20186_();
/*     */   }
/*     */ 
/*     */   
/*     */   public double m_7778_() {
/* 115 */     return (float)this.entity.m_20189_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean m_7775_() {
/* 120 */     return this.canRepeat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int m_7766_() {
/* 125 */     return this.repeatDelay;
/*     */   }
/*     */   
/*     */   public void evaluateVolume() {
/* 129 */     this.volumeAjustment = 1.0F;
/* 130 */     if (MCUtil.getPlayer() != null && !MCUtil.getPlayer().m_6844_(EquipmentSlot.HEAD).m_41619_()) {
/* 131 */       ItemStack stack = MCUtil.getPlayer().m_6844_(EquipmentSlot.HEAD);
/* 132 */       if (ItemNBTHelper.hasKey(stack, "IE:Earmuffs"))
/* 133 */         stack = ItemNBTHelper.getItemStack(stack, "IE:Earmuffs"); 
/* 134 */       if (stack != null && IEItems.Misc.EARMUFFS.m_5456_().equals(stack.m_41720_())) {
/* 135 */         this.volumeAjustment = EarmuffsItem.getVolumeMod(stack);
/*     */       }
/*     */     } 
/* 138 */     if (this.volumeAjustment > 0.1F) {
/* 139 */       for (int dx = (int)Math.floor(this.entity.m_20185_() - 8.0D) >> 4; dx <= (int)Math.floor(this.entity.m_20185_() + 8.0D) >> 4; dx++) {
/* 140 */         for (int dz = (int)Math.floor(this.entity.m_20189_() - 8.0D) >> 4; dz <= (int)Math.floor(this.entity.m_20189_() + 8.0D) >> 4; dz++) {
/* 141 */           for (BlockEntity tile : (MCUtil.getPlayer()).f_19853_.m_6325_(dx, dz).m_62954_().values()) {
/* 142 */             if (tile != null && tile.getClass().getName().contains("SoundMuffler")) {
/* 143 */               BlockPos tPos = tile.m_58899_();
/* 144 */               double d = this.entity.m_20182_().m_82554_(new Vec3(tPos.m_123341_() + 0.5D, tPos.m_123342_() + 0.5D, tPos.m_123343_() + 0.5D));
/* 145 */               if (d <= 64.0D && d > 0.0D) {
/* 146 */                 this.volumeAjustment = 0.1F;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 153 */     if (!this.entity.m_6084_()) {
/* 154 */       this.donePlaying = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void m_7788_() {
/* 159 */     if (MCUtil.getPlayer() != null && (MCUtil.getPlayer()).f_19853_.m_46468_() % 40L == 0L)
/* 160 */       evaluateVolume(); 
/*     */   }
/*     */   
/* 163 */   public IPEntitySound(ResourceLocation sound, float volume, float pitch, boolean repeat, int repeatDelay, Entity e, SoundInstance.Attenuation attenuation, SoundSource category) { this.donePlaying = false; this.attenuation = attenuation; this.resource = sound; this.volume = volume; this.pitch = pitch;
/*     */     this.entity = e;
/*     */     this.canRepeat = repeat;
/*     */     this.repeatDelay = repeatDelay;
/* 167 */     this.category = category; } public boolean m_7801_() { return this.donePlaying; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean m_7796_() {
/* 172 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\sound\IPEntitySound.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */