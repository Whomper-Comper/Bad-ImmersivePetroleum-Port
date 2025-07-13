/*     */ package flaxbeard.immersivepetroleum.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import flaxbeard.immersivepetroleum.client.IPShaders;
/*     */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderStateShard;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class IPRenderTypes
/*     */   extends RenderStateShard
/*     */ {
/*  20 */   static final ResourceLocation activeTexture = ResourceUtils.ip("textures/multiblock/distillation_tower_active.png");
/*  21 */   static final ResourceLocation oilTankTexture = ResourceUtils.ip("textures/multiblock/oiltank.png");
/*     */ 
/*     */   
/*     */   public static final RenderType DISTILLATION_TOWER_ACTIVE;
/*     */ 
/*     */   
/*     */   public static final RenderType OIL_TANK;
/*     */ 
/*     */   
/*     */   public static final RenderType TRANSLUCENT_LINE;
/*     */ 
/*     */   
/*     */   public static final RenderType TRANSLUCENT_POSITION_COLOR;
/*     */   
/*     */   public static final RenderType ISLAND_DEBUGGING_POSITION_COLOR;
/*     */   
/*     */   public static final RenderType PROJECTION;
/*     */   
/*  39 */   static final RenderStateShard.TextureStateShard TEXTURE_ACTIVE_TOWER = new RenderStateShard.TextureStateShard(activeTexture, false, false);
/*  40 */   static final RenderStateShard.TextureStateShard TEXTURE_OIL_TANK = new RenderStateShard.TextureStateShard(oilTankTexture, false, false);
/*  41 */   static final RenderStateShard.LightmapStateShard LIGHTMAP_ENABLED = new RenderStateShard.LightmapStateShard(true);
/*  42 */   static final RenderStateShard.OverlayStateShard OVERLAY_ENABLED = new RenderStateShard.OverlayStateShard(true);
/*  43 */   static final RenderStateShard.OverlayStateShard OVERLAY_DISABLED = new RenderStateShard.OverlayStateShard(false);
/*  44 */   static final RenderStateShard.DepthTestStateShard DEPTH_ALWAYS = new RenderStateShard.DepthTestStateShard("always", 519);
/*  45 */   static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> { RenderSystem.m_69478_(); RenderSystem.m_69453_(); }RenderSystem::m_69461_);
/*     */ 
/*     */ 
/*     */   
/*  49 */   static final RenderStateShard.TransparencyStateShard NO_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("no_transparency", RenderSystem::m_69461_, () -> {
/*     */       
/*     */       });
/*  52 */   static final RenderStateShard.ShaderStateShard PROJECTION_SHADER = new RenderStateShard.ShaderStateShard(IPShaders::getProjectionStaticShader);
/*  53 */   static final RenderStateShard.ShaderStateShard LINE_SHADER = new RenderStateShard.ShaderStateShard(IPShaders::getTranslucentLineShader);
/*  54 */   static final RenderStateShard.ShaderStateShard TRANSLUCENT_SHADER = new RenderStateShard.ShaderStateShard(IPShaders::getTranslucentShader);
/*  55 */   static final RenderStateShard.ShaderStateShard TRANSLUCENT_POSTION_COLOR_SHADER = new RenderStateShard.ShaderStateShard(IPShaders::getTranslucentPostionColorShader);
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
/*     */   static {
/*  77 */     PROJECTION = (RenderType)RenderType.m_173215_(
/*  78 */         typeName("rendertype_projection"), DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 2097152, true, true, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         RenderType.CompositeState.m_110628_()
/*  85 */         .m_173292_(PROJECTION_SHADER)
/*  86 */         .m_173290_((RenderStateShard.EmptyTextureStateShard)f_110145_)
/*  87 */         .m_110685_(TRANSLUCENT_TRANSPARENCY)
/*  88 */         .m_110675_(f_110125_)
/*  89 */         .m_110663_(DEPTH_ALWAYS)
/*  90 */         .m_110691_(false));
/*     */ 
/*     */ 
/*     */     
/*  94 */     TRANSLUCENT_LINE = (RenderType)RenderType.m_173215_(
/*  95 */         typeName("rendertype_line"), DefaultVertexFormat.f_85815_, VertexFormat.Mode.LINES, 256, false, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 101 */         RenderType.CompositeState.m_110628_()
/* 102 */         .m_173292_(LINE_SHADER)
/* 103 */         .m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.of(3.5D)))
/* 104 */         .m_110685_(TRANSLUCENT_TRANSPARENCY)
/* 105 */         .m_110663_(DEPTH_ALWAYS)
/* 106 */         .m_110661_(f_110110_)
/* 107 */         .m_110691_(false));
/*     */ 
/*     */     
/* 110 */     RenderType.CompositeState.m_110628_()
/* 111 */       .m_173292_(f_173095_)
/* 112 */       .m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.of(3.5D)))
/* 113 */       .m_110669_(f_110119_)
/* 114 */       .m_110685_(TRANSLUCENT_TRANSPARENCY)
/* 115 */       .m_110675_(f_110129_)
/* 116 */       .m_110687_(f_110114_)
/* 117 */       .m_110661_(f_110110_)
/* 118 */       .m_110691_(false);
/*     */     
/* 120 */     DISTILLATION_TOWER_ACTIVE = (RenderType)RenderType.m_173215_(
/* 121 */         typeName("distillation_tower_active"), DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 256, true, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 127 */         RenderType.CompositeState.m_110628_()
/* 128 */         .m_173292_(TRANSLUCENT_SHADER)
/* 129 */         .m_173290_((RenderStateShard.EmptyTextureStateShard)TEXTURE_ACTIVE_TOWER)
/* 130 */         .m_110671_(LIGHTMAP_ENABLED)
/* 131 */         .m_110677_(OVERLAY_DISABLED)
/* 132 */         .m_110691_(false));
/*     */ 
/*     */     
/* 135 */     OIL_TANK = (RenderType)RenderType.m_173215_(
/* 136 */         typeName("oil_tank"), DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 256, true, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         RenderType.CompositeState.m_110628_()
/* 143 */         .m_173292_(TRANSLUCENT_SHADER)
/* 144 */         .m_173290_((RenderStateShard.EmptyTextureStateShard)TEXTURE_OIL_TANK)
/* 145 */         .m_110685_(TRANSLUCENT_TRANSPARENCY)
/* 146 */         .m_110671_(LIGHTMAP_ENABLED)
/* 147 */         .m_110677_(OVERLAY_DISABLED)
/* 148 */         .m_110691_(false));
/*     */ 
/*     */     
/* 151 */     TRANSLUCENT_POSITION_COLOR = (RenderType)RenderType.m_173215_(
/* 152 */         typeName("rendertype_translucent"), DefaultVertexFormat.f_85815_, VertexFormat.Mode.QUADS, 131072, false, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 158 */         RenderType.CompositeState.m_110628_()
/* 159 */         .m_173292_(TRANSLUCENT_POSTION_COLOR_SHADER)
/* 160 */         .m_110685_(TRANSLUCENT_TRANSPARENCY)
/* 161 */         .m_110691_(false));
/*     */ 
/*     */     
/* 164 */     ISLAND_DEBUGGING_POSITION_COLOR = (RenderType)RenderType.m_173215_(
/* 165 */         typeName("translucent_pos_color2"), DefaultVertexFormat.f_85815_, VertexFormat.Mode.QUADS, 256, false, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         RenderType.CompositeState.m_110628_()
/* 172 */         .m_110661_(f_110110_)
/* 173 */         .m_110691_(false));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String typeName(String str) {
/* 178 */     return "immersivepetroleum:" + str;
/*     */   }
/*     */   
/*     */   private IPRenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
/* 182 */     super(pName, pSetupState, pClearState);
/* 183 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RenderType getEntitySolid(ResourceLocation locationIn) {
/* 194 */     RenderType.CompositeState renderState = RenderType.CompositeState.m_110628_().m_173292_(f_173112_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(locationIn, false, false)).m_110685_(NO_TRANSPARENCY).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
/* 195 */     return (RenderType)RenderType.m_173215_("entity_solid", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 256, true, false, renderState);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MultiBufferSource disableLighting(MultiBufferSource in) {
/* 200 */     return type -> {
/*     */         Objects.requireNonNull(type);
/*     */         Objects.requireNonNull(type);
/*     */         RenderType rt = new RenderType("immersivepetroleum:" + type + "_no_lighting", type.m_110508_(), type.m_173186_(), type.m_110507_(), type.m_110405_(), false, type::m_110185_, type::m_110188_) {
/*     */           
/*     */           };
/*     */         return in.m_6299_(rt);
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\render\IPRenderTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */