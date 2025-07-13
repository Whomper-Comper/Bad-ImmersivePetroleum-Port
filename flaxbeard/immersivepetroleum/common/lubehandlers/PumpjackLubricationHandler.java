/*     */ package flaxbeard.immersivepetroleum.common.lubehandlers;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Quaternion;
/*     */ import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModel;
/*     */ import flaxbeard.immersivepetroleum.client.model.IPModels;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
/*     */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
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
/*     */ public class PumpjackLubricationHandler
/*     */   implements LubricatedHandler.ILubricationHandler<PumpjackTileEntity>
/*     */ {
/*  33 */   private static final Vec3i size = new Vec3i(4, 6, 3);
/*     */ 
/*     */   
/*     */   public Vec3i getStructureDimensions() {
/*  37 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMachineEnabled(Level world, PumpjackTileEntity mbte) {
/*  42 */     return mbte.wasActive;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity isPlacedCorrectly(Level world, AutoLubricatorTileEntity lubricator, Direction facing) {
/*  47 */     BlockPos target = lubricator.m_58899_().m_121945_(facing);
/*  48 */     BlockEntity te = world.m_7702_(target);
/*     */     
/*  50 */     if (te instanceof PumpjackTileEntity) { PumpjackTileEntity master = (PumpjackTileEntity)te;
/*  51 */       master = (PumpjackTileEntity)master.master();
/*     */       
/*  53 */       if (master != null) {
/*  54 */         Direction f = master.getIsMirrored() ? facing : facing.m_122424_();
/*  55 */         if (master.getFacing().m_122427_() == f) {
/*  56 */           return (BlockEntity)master;
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateClient(ClientLevel world, Fluid lubricant, int ticks, PumpjackTileEntity mbte) {
/*  66 */     mbte.activeTicks += 0.25F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateServer(ServerLevel world, Fluid lubricant, int ticks, PumpjackTileEntity mbte) {
/*  71 */     if (ticks % 4 == 0) {
/*  72 */       mbte.tickServer();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnLubricantParticles(ClientLevel world, AutoLubricatorTileEntity lubricator, Direction facing, PumpjackTileEntity mbte) {
/*  78 */     Direction f = mbte.getIsMirrored() ? facing : facing.m_122424_();
/*  79 */     float location = world.f_46441_.m_188501_();
/*     */     
/*  81 */     int j = ((f.m_122434_() == Direction.Axis.Z) ? 1 : 0) ^ ((facing.m_122421_() == Direction.AxisDirection.POSITIVE) ? 1 : 0) ^ (!mbte.getIsMirrored() ? 1 : 0);
/*  82 */     float xO = 2.5F;
/*  83 */     float zO = -0.15F;
/*  84 */     float yO = 2.25F;
/*     */     
/*  86 */     if (location > 0.5F) {
/*  87 */       xO = 1.7F;
/*  88 */       yO = 2.9F;
/*  89 */       zO = -1.5F;
/*     */     } 
/*     */ 
/*     */     
/*  93 */     if (facing.m_122421_() == Direction.AxisDirection.NEGATIVE)
/*  94 */       xO = -xO + 1.0F; 
/*  95 */     if (j == 0) {
/*  96 */       zO = -zO + 1.0F;
/*     */     }
/*  98 */     float x = lubricator.m_58899_().m_123341_() + ((f.m_122434_() == Direction.Axis.X) ? xO : zO);
/*  99 */     float y = lubricator.m_58899_().m_123342_() + yO;
/* 100 */     float z = lubricator.m_58899_().m_123343_() + ((f.m_122434_() == Direction.Axis.X) ? zO : xO);
/*     */     
/* 102 */     for (int i = 0; i < 3; i++) {
/* 103 */       float r1 = (world.f_46441_.m_188501_() - 0.5F) * 2.0F;
/* 104 */       float r2 = (world.f_46441_.m_188501_() - 0.5F) * 2.0F;
/* 105 */       float r3 = world.f_46441_.m_188501_();
/*     */       
/* 107 */       world.m_7106_((ParticleOptions)ParticleTypes.f_123780_, x, y, z, (r1 * 0.04F), (r3 * 0.0125F), (r2 * 0.025F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Tuple<BlockPos, Direction> getGhostBlockPosition(Level world, PumpjackTileEntity mbte) {
/* 113 */     if (!mbte.isDummy()) {
/* 114 */       Direction mbFacing = mbte.getFacing().m_122424_();
/*     */ 
/*     */ 
/*     */       
/* 118 */       BlockPos pos = mbte.m_58899_().m_121945_(Direction.UP).m_5484_(mbFacing, 4).m_5484_(mbte.getIsMirrored() ? mbFacing.m_122427_() : mbFacing.m_122428_(), 2);
/*     */       
/* 120 */       Direction f = (mbte.getIsMirrored() ? mbte.getFacing().m_122424_() : mbte.getFacing()).m_122428_();
/* 121 */       return new Tuple(pos, f);
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */   
/* 126 */   private static final ResourceLocation TEXTURE = ResourceUtils.ip("textures/models/lube_pipe.png");
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   private static Supplier<IPModel> pipes_normal;
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   private static Supplier<IPModel> pipes_mirrored;
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void renderPipes(AutoLubricatorTileEntity lubricator, PumpjackTileEntity mbte, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
/*     */     IPModel model;
/* 137 */     matrix.m_85837_(0.0D, -1.0D, 0.0D);
/* 138 */     BlockPos blockPos = mbte.m_58899_().m_121996_((Vec3i)lubricator.m_58899_());
/* 139 */     matrix.m_85837_(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_());
/*     */     
/* 141 */     Direction rotation = mbte.getFacing();
/* 142 */     switch (rotation) {
/*     */       case NORTH:
/* 144 */         matrix.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 145 */         matrix.m_85837_(-6.0D, 1.0D, -1.0D);
/*     */         break;
/*     */       case SOUTH:
/* 148 */         matrix.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/* 149 */         matrix.m_85837_(-5.0D, 1.0D, -2.0D);
/*     */         break;
/*     */       case EAST:
/* 152 */         matrix.m_85837_(-5.0D, 1.0D, -1.0D);
/*     */         break;
/*     */       case WEST:
/* 155 */         matrix.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/* 156 */         matrix.m_85837_(-6.0D, 1.0D, -2.0D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (mbte.getIsMirrored()) {
/* 164 */       if (pipes_mirrored == null) {
/* 165 */         pipes_mirrored = IPModels.getSupplier("pumpjack_lubepipes_mirrored");
/*     */       }
/* 167 */       model = pipes_mirrored.get();
/*     */     } else {
/* 169 */       if (pipes_normal == null) {
/* 170 */         pipes_normal = IPModels.getSupplier("pumpjack_lubepipes_normal");
/*     */       }
/* 172 */       model = pipes_normal.get();
/*     */     } 
/*     */     
/* 175 */     if (model != null)
/* 176 */       model.m_7695_(matrix, buffer.m_6299_(model.m_103119_(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\lubehandlers\PumpjackLubricationHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */