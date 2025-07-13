/*     */ package flaxbeard.immersivepetroleum.common.util.survey;
/*     */ 
/*     */ import flaxbeard.immersivepetroleum.ImmersivePetroleum;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.common.util.RegistryUtils;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.registries.ForgeRegistries;
/*     */ 
/*     */ public class IslandInfo
/*     */   implements ISurveyInfo {
/*     */   public static final String TAG_KEY = "islandscan";
/*     */   private int x;
/*     */   private int z;
/*     */   private byte status;
/*     */   private long amount;
/*  24 */   private FluidStack fluidStack = FluidStack.EMPTY;
/*     */   private int expected;
/*     */   
/*     */   public IslandInfo(CompoundTag tag) {
/*  28 */     this.x = tag.m_128451_("x");
/*  29 */     this.z = tag.m_128451_("z");
/*  30 */     this.status = tag.m_128445_("status");
/*  31 */     this.amount = tag.m_128454_("amount");
/*  32 */     this.expected = tag.m_128451_("expected");
/*     */     
/*  34 */     if (tag.m_128441_("fluid")) {
/*     */       try {
/*  36 */         ResourceLocation fluidRL = new ResourceLocation(tag.m_128461_("fluid"));
/*     */         
/*  38 */         Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(fluidRL);
/*  39 */         if (fluid != null) {
/*  40 */           this.fluidStack = new FluidStack(fluid, 1);
/*     */         }
/*  42 */       } catch (ResourceLocationException e) {
/*     */         
/*  44 */         ImmersivePetroleum.log.debug("IslandInfo invalid ResourceLocation. Ignoring.");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public IslandInfo(Level world, BlockPos pos, ReservoirIsland island) {
/*  50 */     this.x = pos.m_123341_();
/*  51 */     this.z = pos.m_123343_();
/*     */     
/*  53 */     this.status = (byte)(int)((float)island.getAmount() / (float)island.getCapacity() * 100.0F);
/*  54 */     this.amount = island.getAmount();
/*  55 */     this.fluidStack = new FluidStack(island.getFluid(), 1);
/*  56 */     this.expected = ReservoirIsland.getFlow(island.getPressure(world, pos.m_123341_(), pos.m_123343_()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  61 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  66 */     return this.z;
/*     */   }
/*     */   
/*     */   public byte getStatus() {
/*  70 */     return this.status;
/*     */   }
/*     */   
/*     */   public int getExpected() {
/*  74 */     return this.expected;
/*     */   }
/*     */   
/*     */   public long getAmount() {
/*  78 */     return this.amount;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FluidStack getFluidStack() {
/*  83 */     return this.fluidStack;
/*     */   }
/*     */   
/*     */   public Fluid getFluid() {
/*  87 */     return this.fluidStack.getFluid();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag writeToStack(ItemStack stack) {
/*  92 */     return writeToTag(stack.m_41698_("islandscan"));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag writeToTag(CompoundTag tag) {
/*  97 */     tag.m_128405_("x", this.x);
/*  98 */     tag.m_128405_("z", this.z);
/*  99 */     tag.m_128344_("status", this.status);
/* 100 */     tag.m_128356_("amount", this.amount);
/* 101 */     tag.m_128405_("expected", this.expected);
/*     */     
/* 103 */     if (!this.fluidStack.isEmpty()) {
/* 104 */       tag.m_128359_("fluid", RegistryUtils.getRegistryNameOf(this.fluidStack.getFluid()).toString());
/*     */     }
/*     */     
/* 107 */     return tag;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\survey\IslandInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */