/*     */ package flaxbeard.immersivepetroleum.common.lubehandlers;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.BucketWheelBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.ExcavatorBlockEntity;
/*     */ import blusunrize.immersiveengineering.common.config.IEServerConfig;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModel;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModels;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.api.distmarker.Dist;
/*     */ import net.minecraftforge.api.distmarker.OnlyIn;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExcavatorLubricationHandler
/*     */   implements LubricatedHandler.ILubricationHandler<ExcavatorBlockEntity>
/*     */ {
/*  35 */   private static final Vec3i size = new Vec3i(3, 6, 3);
/*     */ 
/*     */   
/*     */   public Vec3i getStructureDimensions() {
/*  39 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMachineEnabled(Level world, ExcavatorBlockEntity mbte) {
/*  44 */     BlockPos wheelPos = mbte.getWheelCenterPos();
/*  45 */     BlockEntity center = world.m_7702_(wheelPos);
/*     */     
/*  47 */     if (center instanceof BucketWheelBlockEntity) { BucketWheelBlockEntity wheel = (BucketWheelBlockEntity)center;
/*  48 */       if (!wheel.offsetToMaster.equals(BlockPos.f_121853_))
/*     */       {
/*  50 */         wheel = (BucketWheelBlockEntity)wheel.master();
/*     */       }
/*     */       
/*  53 */       return wheel.active; }
/*     */     
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity isPlacedCorrectly(Level world, AutoLubricatorTileEntity lubricator, Direction facing) {
/*  60 */     BlockPos target = lubricator.m_58899_().m_121945_(facing);
/*  61 */     BlockEntity te = world.m_7702_(target);
/*     */     
/*  63 */     if (te instanceof ExcavatorBlockEntity) { ExcavatorBlockEntity master = (ExcavatorBlockEntity)te;
/*  64 */       master = (ExcavatorBlockEntity)master.master();
/*     */       
/*  66 */       if (master != null) {
/*  67 */         Direction dir = master.getIsMirrored() ? master.getFacing().m_122427_() : master.getFacing().m_122428_();
/*  68 */         if (dir == facing) {
/*  69 */           return (BlockEntity)master;
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateClient(ClientLevel world, Fluid lubricant, int ticks, ExcavatorBlockEntity mbte) {
/*  79 */     BlockPos wheelPos = mbte.getWheelCenterPos();
/*  80 */     BlockEntity center = world.m_7702_(wheelPos);
/*     */     
/*  82 */     if (center instanceof BucketWheelBlockEntity) { BucketWheelBlockEntity wheel = (BucketWheelBlockEntity)center;
/*  83 */       if (!wheel.offsetToMaster.equals(BlockPos.f_121853_))
/*     */       {
/*  85 */         wheel = (BucketWheelBlockEntity)wheel.master();
/*     */       }
/*     */       
/*  88 */       wheel.rotation = (float)(wheel.rotation + ((Double)IEServerConfig.MACHINES.excavator_speed.get()).doubleValue() / 4.0D); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateServer(ServerLevel world, Fluid lubricant, int ticks, ExcavatorBlockEntity mbte) {
/*  94 */     BlockPos wheelPos = mbte.getWheelCenterPos();
/*  95 */     BlockEntity center = world.m_7702_(wheelPos);
/*     */     
/*  97 */     if (center instanceof BucketWheelBlockEntity) { BucketWheelBlockEntity wheel = (BucketWheelBlockEntity)center;
/*  98 */       if (!wheel.offsetToMaster.equals(BlockPos.f_121853_))
/*     */       {
/* 100 */         wheel = (BucketWheelBlockEntity)wheel.master();
/*     */       }
/*     */       
/* 103 */       if (ticks % 4 == 0) {
/* 104 */         wheel.tickServer();
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnLubricantParticles(ClientLevel world, AutoLubricatorTileEntity lubricator, Direction facing, ExcavatorBlockEntity mbte) {
/* 111 */     Direction f = mbte.getIsMirrored() ? facing : facing.m_122424_();
/*     */     
/* 113 */     float location = world.f_46441_.m_188501_();
/*     */     
/* 115 */     int j = ((f.m_122434_() == Direction.Axis.Z) ? 1 : 0) ^ ((facing.m_122421_() == Direction.AxisDirection.POSITIVE) ? 1 : 0) ^ (!mbte.getIsMirrored() ? 1 : 0);
/* 116 */     float xO = 1.2F;
/* 117 */     float zO = -0.5F;
/* 118 */     float yO = 0.5F;
/*     */     
/* 120 */     if (location > 0.5F) {
/* 121 */       xO = 0.9F;
/* 122 */       yO = 0.8F;
/* 123 */       zO = 1.75F;
/*     */     } 
/*     */     
/* 126 */     if (facing.m_122421_() == Direction.AxisDirection.NEGATIVE)
/* 127 */       xO = -xO + 1.0F; 
/* 128 */     if (j == 0) {
/* 129 */       zO = -zO + 1.0F;
/*     */     }
/* 131 */     float x = lubricator.m_58899_().m_123341_() + ((f.m_122434_() == Direction.Axis.X) ? xO : zO);
/* 132 */     float y = lubricator.m_58899_().m_123342_() + yO;
/* 133 */     float z = lubricator.m_58899_().m_123343_() + ((f.m_122434_() == Direction.Axis.X) ? zO : xO);
/*     */     
/* 135 */     for (int i = 0; i < 3; i++) {
/* 136 */       float r1 = (world.f_46441_.m_188501_() - 0.5F) * 2.0F;
/* 137 */       float r2 = (world.f_46441_.m_188501_() - 0.5F) * 2.0F;
/* 138 */       float r3 = world.f_46441_.m_188501_();
/*     */       
/* 140 */       world.m_7106_((ParticleOptions)ParticleTypes.f_123780_, x, y, z, (r1 * 0.04F), (r3 * 0.0125F), (r2 * 0.025F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Tuple<BlockPos, Direction> getGhostBlockPosition(Level world, ExcavatorBlockEntity mbte) {
/* 146 */     if (!mbte.isDummy()) {
/*     */ 
/*     */       
/* 149 */       BlockPos pos = mbte.m_58899_().m_5484_(mbte.getFacing(), 4).m_5484_(mbte.getIsMirrored() ? mbte.getFacing().m_122428_() : mbte.getFacing().m_122427_(), 2);
/* 150 */       Direction f = mbte.getIsMirrored() ? mbte.getFacing().m_122427_() : mbte.getFacing().m_122428_();
/* 151 */       return new Tuple(pos, f);
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */   
/* 156 */   private static final ResourceLocation TEXTURE = ResourceUtils.ip("textures/models/lube_pipe.png");
/*     */   
/*     */   private static Supplier<IPModel> pipes_normal;
/*     */   private static Supplier<IPModel> pipes_mirrored;
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void renderPipes(AutoLubricatorTileEntity lubricator, ExcavatorBlockEntity mbte, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
/* 163 */     matrix.m_85837_(0.0D, -1.0D, 0.0D);
/* 164 */     BlockPos blockPos = mbte.m_58899_().m_121996_((Vec3i)lubricator.m_58899_());
/* 165 */     matrix.m_85837_(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_());
/*     */     
/* 167 */     Direction rotation = mbte.getFacing();
/* 168 */     switch (rotation) {
/*     */       case NORTH:
/* 170 */         matrix.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 171 */         matrix.m_85837_(-1.0D, 0.0D, -1.0D);
/*     */         break;
/*     */       case SOUTH:
/* 174 */         matrix.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/* 175 */         matrix.m_85837_(0.0D, 0.0D, -2.0D);
/*     */         break;
/*     */       case EAST:
/* 178 */         matrix.m_85837_(0.0D, 0.0D, -1.0D);
/*     */         break;
/*     */       case WEST:
/* 181 */         matrix.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/* 182 */         matrix.m_85837_(-1.0D, 0.0D, -2.0D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 188 */     IPModel model = null;
/* 189 */     if (mbte.getIsMirrored()) {
/* 190 */       if (pipes_mirrored == null) {
/* 191 */         pipes_mirrored = IPModels.getSupplier("excavator_lubepipes_mirrored");
/*     */       }
/* 193 */       model = pipes_mirrored.get();
/*     */     } else {
/* 195 */       if (pipes_normal == null) {
/* 196 */         pipes_normal = IPModels.getSupplier("excavator_lubepipes_normal");
/*     */       }
/* 198 */       model = pipes_normal.get();
/*     */     } 
/*     */     
/* 201 */     if (model != null)
/* 202 */       model.m_7695_(matrix, buffer.m_6299_(model.m_103119_(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\lubehandlers\ExcavatorLubricationHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */