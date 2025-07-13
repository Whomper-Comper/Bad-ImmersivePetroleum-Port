/*    */ package flaxbeard.immersivepetroleum.common.gui;
/*    */ public final class BEContainerIP<T extends BlockEntity, C extends IEContainerMenu> extends Record { private final RegistryObject<MenuType<C>> type; private final IEMenuTypes.BEContainerConstructor<T, C> factory;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP<TT;TC;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP<TT;TC;>;
/*    */   }
/*    */   
/* 12 */   public BEContainerIP(RegistryObject<MenuType<C>> type, IEMenuTypes.BEContainerConstructor<T, C> factory) { this.type = type; this.factory = factory; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 12 */     //   0	8	0	this	Lflaxbeard/immersivepetroleum/common/gui/IPMenuProvider$BEContainerIP<TT;TC;>; } public RegistryObject<MenuType<C>> type() { return this.type; } public IEMenuTypes.BEContainerConstructor<T, C> factory() { return this.factory; }
/*    */    public C create(int windowId, Inventory playerInv, T tile) {
/* 14 */     return (C)this.factory.construct(getType(), windowId, playerInv, (BlockEntity)tile);
/*    */   }
/*    */   
/*    */   public MenuType<C> getType() {
/* 18 */     return (MenuType<C>)this.type.get();
/*    */   } }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\gui\IPMenuProvider$BEContainerIP.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */