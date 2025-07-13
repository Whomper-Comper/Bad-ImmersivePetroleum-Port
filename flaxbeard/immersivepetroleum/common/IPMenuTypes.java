/*    */ package flaxbeard.immersivepetroleum.common;
/*    */ 
/*    */ import blusunrize.immersiveengineering.common.gui.IEContainerMenu;
/*    */ import blusunrize.immersiveengineering.common.register.IEMenuTypes;
/*    */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DerrickTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
/*    */ import flaxbeard.immersivepetroleum.common.gui.CokerUnitContainer;
/*    */ import flaxbeard.immersivepetroleum.common.gui.DerrickContainer;
/*    */ import flaxbeard.immersivepetroleum.common.gui.DistillationTowerContainer;
/*    */ import flaxbeard.immersivepetroleum.common.gui.HydrotreaterContainer;
/*    */ import flaxbeard.immersivepetroleum.common.gui.IPMenuProvider;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraftforge.registries.RegistryObject;
/*    */ import org.apache.commons.lang3.mutable.Mutable;
/*    */ import org.apache.commons.lang3.mutable.MutableObject;
/*    */ 
/*    */ public class IPMenuTypes
/*    */ {
/* 27 */   public static final IPMenuProvider.BEContainerIP<DistillationTowerTileEntity, DistillationTowerContainer> DISTILLATION_TOWER = register("distillation_tower", DistillationTowerContainer::new);
/*    */   
/* 29 */   public static final IPMenuProvider.BEContainerIP<DerrickTileEntity, DerrickContainer> DERRICK = register("derrick", DerrickContainer::new);
/*    */   
/* 31 */   public static final IPMenuProvider.BEContainerIP<CokerUnitTileEntity, CokerUnitContainer> COKER = register("coker", CokerUnitContainer::new);
/*    */   
/* 33 */   public static final IPMenuProvider.BEContainerIP<HydrotreaterTileEntity, HydrotreaterContainer> HYDROTREATER = register("hydrotreater", HydrotreaterContainer::new);
/*    */ 
/*    */   
/*    */   public static void forceClassLoad() {}
/*    */   
/*    */   public static <T extends BlockEntity, C extends IEContainerMenu> IPMenuProvider.BEContainerIP<T, C> register(String name, IEMenuTypes.BEContainerConstructor<T, C> container) {
/* 39 */     RegistryObject<MenuType<C>> typeRef = IPRegisters.registerMenu(name, () -> {
/*    */           MutableObject mutableObject = new MutableObject();
/*    */ 
/*    */           
/*    */           MenuType<C> type = new MenuType((MenuType.MenuSupplier)(()));
/*    */           
/*    */           mutableObject.setValue(type);
/*    */           
/*    */           return type;
/*    */         });
/*    */     
/* 50 */     return new IPMenuProvider.BEContainerIP(typeRef, container);
/*    */   }
/*    */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\IPMenuTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */