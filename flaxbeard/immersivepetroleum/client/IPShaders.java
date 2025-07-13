/*    */ package flaxbeard.immersivepetroleum.client;
/*    */ 
/*    */ import com.mojang.blaze3d.shaders.AbstractUniform;
/*    */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.ShaderInstance;
/*    */ import net.minecraft.server.packs.resources.ResourceProvider;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.client.event.RegisterShadersEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(value = {Dist.CLIENT}, modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD)
/*    */ public class IPShaders
/*    */ {
/*    */   private static ShaderInstance shader_line;
/*    */   private static ShaderInstance shader_projection;
/*    */   private static ShaderInstance shader_translucent_full;
/*    */   private static ShaderInstance shader_translucent_postion_color;
/*    */   private static AbstractUniform projection_alpha;
/*    */   private static AbstractUniform projection_time;
/*    */   
/*    */   public static void projNoise(float alpha, float time) {
/* 29 */     projection_alpha.m_5985_(alpha);
/* 30 */     projection_time.m_5985_(time);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void registerShaders(RegisterShadersEvent event) throws IOException {
/* 35 */     event.registerShader(new ShaderInstance((ResourceProvider)event.getResourceManager(), ResourceUtils.ip("rendertype_line"), DefaultVertexFormat.f_85815_), s -> {
/*    */           ImmersivePetroleum.log.debug("rendertype_line shader loaded.");
/*    */           
/*    */           shader_line = s;
/*    */         });
/* 40 */     event.registerShader(new ShaderInstance((ResourceProvider)event.getResourceManager(), ResourceUtils.ip("rendertype_projection"), DefaultVertexFormat.f_85818_), s -> {
/*    */           ImmersivePetroleum.log.debug("rendertype_projection shader loaded.");
/*    */           
/*    */           shader_projection = s;
/*    */           
/*    */           projection_alpha = shader_projection.m_173356_("Alpha");
/*    */           projection_time = shader_projection.m_173356_("Time");
/*    */         });
/* 48 */     event.registerShader(new ShaderInstance((ResourceProvider)event.getResourceManager(), ResourceUtils.ip("rendertype_translucent_postion_color"), DefaultVertexFormat.f_85815_), s -> {
/*    */           ImmersivePetroleum.log.debug("rendertype_translucent_postion_color shader loaded.");
/*    */           
/*    */           shader_translucent_postion_color = s;
/*    */         });
/* 53 */     event.registerShader(new ShaderInstance((ResourceProvider)event.getResourceManager(), ResourceUtils.ip("rendertype_translucent"), DefaultVertexFormat.f_85811_), s -> {
/*    */           ImmersivePetroleum.log.debug("rendertype_translucent shader loaded.");
/*    */           shader_translucent_full = s;
/*    */         });
/*    */   }
/*    */   
/*    */   public static ShaderInstance getTranslucentLineShader() {
/* 60 */     return shader_line;
/*    */   }
/*    */   
/*    */   public static ShaderInstance getProjectionStaticShader() {
/* 64 */     return shader_projection;
/*    */   }
/*    */   
/*    */   public static ShaderInstance getTranslucentShader() {
/* 68 */     return shader_translucent_full;
/*    */   }
/*    */   
/*    */   public static ShaderInstance getTranslucentPostionColorShader() {
/* 72 */     return shader_translucent_postion_color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\client\IPShaders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */