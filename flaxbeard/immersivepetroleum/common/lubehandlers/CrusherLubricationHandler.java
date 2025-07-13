/*     */ package flaxbeard.immersivepetroleum.common.lubehandlers;
/*     */ 
/*     */ import blusunrize.immersiveengineering.common.blocks.metal.CrusherBlockEntity;
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
/*     */ public class CrusherLubricationHandler
/*     */   implements LubricatedHandler.ILubricationHandler<CrusherBlockEntity>
/*     */ {
/*  33 */   private static final Vec3i size = new Vec3i(3, 3, 5);
/*     */ 
/*     */   
/*     */   public Vec3i getStructureDimensions() {
/*  37 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMachineEnabled(Level world, CrusherBlockEntity mbte) {
/*  42 */     return mbte.shouldRenderAsActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity isPlacedCorrectly(Level world, AutoLubricatorTileEntity lubricator, Direction facing) {
/*  47 */     BlockPos target = lubricator.m_58899_().m_121945_(facing);
/*  48 */     BlockEntity te = world.m_7702_(target);
/*     */     
/*  50 */     if (te instanceof CrusherBlockEntity) { CrusherBlockEntity master = (CrusherBlockEntity)te;
/*  51 */       master = (CrusherBlockEntity)master.master();
/*     */       
/*  53 */       if (master != null && master.getFacing().m_122424_() == facing) {
/*  54 */         return (BlockEntity)master;
/*     */       } }
/*     */ 
/*     */     
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateClient(ClientLevel world, Fluid lubricant, int ticks, CrusherBlockEntity mbte) {
/*  63 */     if (mbte.shouldRenderAsActive()) {
/*  64 */       mbte.animation_barrelRotation += 4.5F;
/*  65 */       mbte.animation_barrelRotation %= 360.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void lubricateServer(ServerLevel world, Fluid lubricant, int ticks, CrusherBlockEntity mbte) {
/*  71 */     if (ticks % 4 == 0) {
/*  72 */       mbte.tickServer();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnLubricantParticles(ClientLevel world, AutoLubricatorTileEntity lubricator, Direction facing, CrusherBlockEntity mbte) {
/*  78 */     Direction f = mbte.getIsMirrored() ? facing : facing.m_122424_();
/*     */     
/*  80 */     float location = world.f_46441_.m_188501_();
/*     */     
/*  82 */     int j = ((f.m_122434_() == Direction.Axis.Z) ? 1 : 0) ^ ((facing.m_122421_() == Direction.AxisDirection.NEGATIVE) ? 1 : 0) ^ (!mbte.getIsMirrored() ? 1 : 0);
/*  83 */     float xO = 2.5F;
/*  84 */     float zO = -0.1F;
/*  85 */     float yO = 1.3F;
/*     */     
/*  87 */     if (location > 0.5F) {
/*  88 */       xO = 1.0F;
/*  89 */       yO = 3.0F;
/*  90 */       zO = 1.65F;
/*     */     } 
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
/*     */   public Tuple<BlockPos, Direction> getGhostBlockPosition(Level world, CrusherBlockEntity mbte) {
/* 113 */     if (!mbte.isDummy()) {
/* 114 */       BlockPos pos = mbte.m_58899_().m_5484_(mbte.getFacing(), 2);
/* 115 */       Direction f = mbte.getFacing().m_122424_();
/* 116 */       return new Tuple(pos, f);
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */   
/* 121 */   private static final ResourceLocation TEXTURE = ResourceUtils.ip("textures/models/lube_pipe.png");
/*     */   
/*     */   private static Supplier<IPModel> pipes;
/*     */   
/*     */   @OnlyIn(Dist.CLIENT)
/*     */   public void renderPipes(AutoLubricatorTileEntity lubricator, CrusherBlockEntity mbte, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
/* 127 */     matrix.m_85837_(0.0D, -1.0D, 0.0D);
/* 128 */     BlockPos blockPos = mbte.m_58899_().m_121996_((Vec3i)lubricator.m_58899_());
/* 129 */     matrix.m_85837_(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_());
/*     */     
/* 131 */     Direction rotation = mbte.getFacing();
/* 132 */     switch (rotation) {
/*     */       case NORTH:
/* 134 */         matrix.m_85845_(new Quaternion(0.0F, 90.0F, 0.0F, true));
/* 135 */         matrix.m_85837_(-1.0D, 0.0D, 0.0D);
/*     */         break;
/*     */       case SOUTH:
/* 138 */         matrix.m_85845_(new Quaternion(0.0F, 270.0F, 0.0F, true));
/* 139 */         matrix.m_85837_(0.0D, 0.0D, -1.0D);
/*     */         break;
/*     */       case EAST:
/* 142 */         matrix.m_85837_(0.0D, 0.0D, 0.0D);
/*     */         break;
/*     */       case WEST:
/* 145 */         matrix.m_85845_(new Quaternion(0.0F, 180.0F, 0.0F, true));
/* 146 */         matrix.m_85837_(-1.0D, 0.0D, -1.0D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (pipes == null) {
/* 153 */       pipes = IPModels.getSupplier("crusher_lubepipes");
/*     */     }
/*     */     IPModel model;
/* 156 */     if ((model = pipes.get()) != null)
/* 157 */       model.m_7695_(matrix, buffer.m_6299_(model.m_103119_(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\lubehandlers\CrusherLubricationHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */