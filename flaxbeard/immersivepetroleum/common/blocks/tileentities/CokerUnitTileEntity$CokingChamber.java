/*      */ package flaxbeard.immersivepetroleum.common.blocks.tileentities;
/*      */ 
/*      */ import blusunrize.immersiveengineering.common.util.Utils;
/*      */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*      */ import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ResourceLocationException;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*      */ import net.minecraftforge.fluids.capability.templates.FluidTank;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CokingChamber
/*      */ {
/*      */   @Nullable
/*  929 */   CokerUnitRecipe recipe = null;
/*      */   
/*  931 */   CokerUnitTileEntity.CokingState state = CokerUnitTileEntity.CokingState.STANDBY;
/*      */   
/*      */   FluidTank tank;
/*      */   
/*      */   int capacity;
/*      */   
/*  937 */   int inputAmount = 0;
/*      */   
/*  939 */   int outputAmount = 0;
/*      */   
/*  941 */   int timer = 0;
/*      */   
/*      */   public CokingChamber(int itemCapacity, int fluidCapacity) {
/*  944 */     this.capacity = itemCapacity;
/*  945 */     this.tank = new FluidTank(fluidCapacity);
/*      */   }
/*      */   
/*      */   public CokingChamber readFromNBT(CompoundTag nbt) {
/*  949 */     this.tank.readFromNBT(nbt.m_128469_("tank"));
/*  950 */     this.timer = nbt.m_128451_("timer");
/*  951 */     this.inputAmount = nbt.m_128451_("input");
/*  952 */     this.outputAmount = nbt.m_128451_("output");
/*  953 */     this.state = CokerUnitTileEntity.CokingState.values()[nbt.m_128451_("state")];
/*      */     
/*  955 */     if (nbt.m_128425_("recipe", 8)) {
/*      */       try {
/*  957 */         this.recipe = (CokerUnitRecipe)CokerUnitRecipe.recipes.get(new ResourceLocation(nbt.m_128461_("recipe")));
/*  958 */       } catch (ResourceLocationException e) {
/*  959 */         ImmersivePetroleum.log.error("Tried to load a coking recipe with an invalid name", (Throwable)e);
/*      */       } 
/*      */     } else {
/*  962 */       this.recipe = null;
/*      */     } 
/*      */     
/*  965 */     return this;
/*      */   }
/*      */   
/*      */   public CompoundTag writeToNBT(CompoundTag nbt) {
/*  969 */     nbt.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag()));
/*  970 */     nbt.m_128405_("timer", this.timer);
/*  971 */     nbt.m_128405_("input", this.inputAmount);
/*  972 */     nbt.m_128405_("output", this.outputAmount);
/*  973 */     nbt.m_128405_("state", this.state.id());
/*      */     
/*  975 */     if (this.recipe != null) {
/*  976 */       nbt.m_128359_("recipe", this.recipe.m_6423_().toString());
/*      */     }
/*      */     
/*  979 */     return nbt;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setRecipe(@Nullable CokerUnitRecipe recipe) {
/*  984 */     if (this.state == CokerUnitTileEntity.CokingState.STANDBY) {
/*  985 */       this.recipe = recipe;
/*  986 */       return true;
/*      */     } 
/*      */     
/*  989 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int addStack(@Nonnull ItemStack stack, boolean simulate) {
/*  994 */     if (this.recipe != null && !stack.m_41619_() && this.recipe.inputItem.test(stack)) {
/*  995 */       int capacity = getCapacity() * this.recipe.inputItem.getCount();
/*  996 */       int current = getTotalAmount() * this.recipe.inputItem.getCount();
/*      */       
/*  998 */       if (simulate) {
/*  999 */         return Math.min(capacity - current, stack.m_41613_());
/*      */       }
/*      */       
/* 1002 */       int filled = capacity - current;
/* 1003 */       if (stack.m_41613_() < filled) {
/* 1004 */         filled = stack.m_41613_();
/*      */       }
/* 1006 */       this.inputAmount++;
/*      */       
/* 1008 */       return filled;
/*      */     } 
/*      */     
/* 1011 */     return 0;
/*      */   }
/*      */   
/*      */   public CokerUnitTileEntity.CokingState getState() {
/* 1015 */     return this.state;
/*      */   }
/*      */   
/*      */   public int getCapacity() {
/* 1019 */     return this.capacity;
/*      */   }
/*      */   
/*      */   public int getInputAmount() {
/* 1023 */     return this.inputAmount;
/*      */   }
/*      */   
/*      */   public int getOutputAmount() {
/* 1027 */     return this.outputAmount;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTotalAmount() {
/* 1032 */     return this.inputAmount + this.outputAmount;
/*      */   }
/*      */   
/*      */   public int getTimer() {
/* 1036 */     return this.timer;
/*      */   }
/*      */   
/*      */   private boolean setStage(CokerUnitTileEntity.CokingState state) {
/* 1040 */     if (this.state != state) {
/* 1041 */       this.state = state;
/* 1042 */       return true;
/*      */     } 
/* 1044 */     return false;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public CokerUnitRecipe getRecipe() {
/* 1049 */     return this.recipe;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getInputItem() {
/* 1054 */     if (this.recipe == null) {
/* 1055 */       return ItemStack.f_41583_;
/*      */     }
/* 1057 */     return this.recipe.inputItem.getMatchingStacks()[0];
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getOutputItem() {
/* 1062 */     if (this.recipe == null) {
/* 1063 */       return ItemStack.f_41583_;
/*      */     }
/*      */     
/* 1066 */     return ((ItemStack)this.recipe.outputItem.get()).m_41777_();
/*      */   }
/*      */   
/*      */   public FluidTank getTank() {
/* 1070 */     return this.tank;
/*      */   }
/*      */   
/*      */   public boolean tick(CokerUnitTileEntity cokerunit, int chamberId) {
/*      */     boolean update;
/* 1075 */     if (this.recipe == null) {
/* 1076 */       return setStage(CokerUnitTileEntity.CokingState.STANDBY);
/*      */     }
/*      */     
/* 1079 */     switch (CokerUnitTileEntity.null.$SwitchMap$flaxbeard$immersivepetroleum$common$blocks$tileentities$CokerUnitTileEntity$CokingState[this.state.ordinal()]) {
/*      */       case 1:
/* 1081 */         if (this.recipe != null) {
/* 1082 */           return setStage(CokerUnitTileEntity.CokingState.PROCESSING);
/*      */         }
/*      */         break;
/*      */       case 2:
/* 1086 */         if (this.inputAmount > 0 && !getInputItem().m_41619_() && this.tank.getCapacity() - this.tank.getFluidAmount() >= this.recipe.outputFluid.getAmount() && 
/* 1087 */           cokerunit.energyStorage.getEnergyStored() >= this.recipe.getTotalProcessEnergy() / this.recipe.getTotalProcessTime()) {
/* 1088 */           cokerunit.energyStorage.extractEnergy(this.recipe.getTotalProcessEnergy() / this.recipe.getTotalProcessTime(), false);
/*      */           
/* 1090 */           this.timer++;
/* 1091 */           if (this.timer >= this.recipe.getTotalProcessTime() * this.recipe.inputItem.getCount()) {
/* 1092 */             this.timer = 0;
/*      */             
/* 1094 */             this.tank.fill(Utils.copyFluidStackWithAmount(this.recipe.outputFluid, this.recipe.outputFluid.getAmount(), false), IFluidHandler.FluidAction.EXECUTE);
/* 1095 */             this.inputAmount--;
/* 1096 */             this.outputAmount++;
/*      */             
/* 1098 */             if (this.inputAmount <= 0) {
/* 1099 */               setStage(CokerUnitTileEntity.CokingState.DRAIN_RESIDUE);
/*      */             }
/*      */           } 
/*      */           
/* 1103 */           return true;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 3:
/* 1108 */         if (this.tank.getFluidAmount() > 0) {
/* 1109 */           FluidTank buffer = cokerunit.bufferTanks[1];
/* 1110 */           FluidStack drained = this.tank.drain(25, IFluidHandler.FluidAction.SIMULATE);
/*      */           
/* 1112 */           int accepted = buffer.fill(drained, IFluidHandler.FluidAction.SIMULATE);
/* 1113 */           if (accepted > 0) {
/* 1114 */             int amount = Math.min(drained.getAmount(), accepted);
/*      */             
/* 1116 */             this.tank.drain(amount, IFluidHandler.FluidAction.EXECUTE);
/* 1117 */             buffer.fill(Utils.copyFluidStackWithAmount(drained, amount, false), IFluidHandler.FluidAction.EXECUTE);
/*      */             
/* 1119 */             return true;
/*      */           }  break;
/*      */         } 
/* 1122 */         return setStage(CokerUnitTileEntity.CokingState.FLOODING);
/*      */ 
/*      */       
/*      */       case 4:
/* 1126 */         this.timer++;
/* 1127 */         if (this.timer >= 2) {
/* 1128 */           this.timer = 0;
/*      */           
/* 1130 */           int max = getTotalAmount() * this.recipe.inputFluid.getAmount();
/* 1131 */           if (this.tank.getFluidAmount() < max) {
/* 1132 */             FluidStack accepted = cokerunit.bufferTanks[0].drain(this.recipe.inputFluid.getAmount(), IFluidHandler.FluidAction.SIMULATE);
/* 1133 */             if (accepted.getAmount() >= this.recipe.inputFluid.getAmount()) {
/* 1134 */               cokerunit.bufferTanks[0].drain(this.recipe.inputFluid.getAmount(), IFluidHandler.FluidAction.EXECUTE);
/* 1135 */               this.tank.fill(accepted, IFluidHandler.FluidAction.EXECUTE);
/*      */             }  break;
/* 1137 */           }  if (this.tank.getFluidAmount() >= max) {
/* 1138 */             return setStage(CokerUnitTileEntity.CokingState.DUMPING);
/*      */           }
/*      */         } 
/*      */         break;
/*      */       case 5:
/* 1143 */         update = false;
/*      */         
/* 1145 */         this.timer++;
/* 1146 */         if (this.timer >= 5) {
/* 1147 */           this.timer = 0;
/*      */           
/* 1149 */           if (this.outputAmount > 0) {
/* 1150 */             Level world = cokerunit.getLevelNonnull();
/* 1151 */             int amount = Math.min(this.outputAmount, 1);
/* 1152 */             ItemStack copy = ((ItemStack)this.recipe.outputItem.get()).m_41777_();
/* 1153 */             copy.m_41764_(amount);
/*      */ 
/*      */             
/* 1156 */             BlockPos itemOutPos = cokerunit.getBlockPosForPos((chamberId == 0) ? CokerUnitTileEntity.Chamber_A_OUT : CokerUnitTileEntity.Chamber_B_OUT);
/* 1157 */             Vec3 center = new Vec3(itemOutPos.m_123341_() + 0.5D, itemOutPos.m_123342_() - 0.5D, itemOutPos.m_123343_() + 0.5D);
/* 1158 */             ItemEntity ent = new ItemEntity(cokerunit.getLevelNonnull(), center.f_82479_, center.f_82480_, center.f_82481_, copy);
/* 1159 */             ent.m_20334_(0.0D, 0.0D, 0.0D);
/* 1160 */             world.m_7967_((Entity)ent);
/* 1161 */             this.outputAmount -= amount;
/*      */             
/* 1163 */             update = true;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1168 */         if (this.tank.getFluidAmount() > 0) {
/* 1169 */           this.tank.drain(25, IFluidHandler.FluidAction.EXECUTE);
/*      */           
/* 1171 */           update = true;
/*      */         } 
/*      */         
/* 1174 */         if (this.outputAmount <= 0 && this.tank.isEmpty()) {
/* 1175 */           this.recipe = null;
/* 1176 */           setStage(CokerUnitTileEntity.CokingState.STANDBY);
/*      */           
/* 1178 */           update = true;
/*      */         } 
/*      */         
/* 1181 */         if (update) {
/* 1182 */           return true;
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/* 1187 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\common\blocks\tileentities\CokerUnitTileEntity$CokingChamber.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */