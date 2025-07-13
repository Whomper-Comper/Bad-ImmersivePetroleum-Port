/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.InputConstants;
/*    */ import net.minecraft.client.KeyMapping;
/*    */ import net.minecraftforge.api.distmarker.Dist;
/*    */ import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
/*    */ import net.minecraftforge.client.settings.IKeyConflictContext;
/*    */ import net.minecraftforge.client.settings.KeyConflictContext;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "immersivepetroleum", bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
/*    */ public class IPKeyBinds
/*    */ {
/* 18 */   public static final KeyMapping keybind_preview_flip = new KeyMapping("key.immersivepetroleum.projector.flip", InputConstants.Type.KEYSYM, 77, "key.categories.immersivepetroleum");
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void registerKeybind(RegisterKeyMappingsEvent event) {
/* 22 */     keybind_preview_flip.setKeyConflictContext((IKeyConflictContext)KeyConflictContext.IN_GAME);
/*    */     
/* 24 */     event.register(keybind_preview_flip);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPKeyBinds.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */