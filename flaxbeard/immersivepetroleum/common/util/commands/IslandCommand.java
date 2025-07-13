/*     */ package flaxbeard.immersivepetroleum.common.util.commands;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.LongArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.AxisAlignedIslandBB;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirHandler;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirIsland;
/*     */ import flaxbeard.immersivepetroleum.api.reservoir.ReservoirType;
/*     */ import flaxbeard.immersivepetroleum.common.ReservoirRegionDataStorage;
/*     */ import flaxbeard.immersivepetroleum.common.util.Utils;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IslandCommand
/*     */ {
/*     */   public static LiteralArgumentBuilder<CommandSourceStack> create() {
/*  48 */     LiteralArgumentBuilder<CommandSourceStack> main = (LiteralArgumentBuilder<CommandSourceStack>)Commands.m_82127_("reservoir").requires(source -> source.m_6761_(4));
/*     */     
/*  50 */     main.then(Commands.m_82127_("locate").executes(IslandCommand::locate));
/*  51 */     main.then((ArgumentBuilder)setters());
/*  52 */     main.then((ArgumentBuilder)positional(Commands.m_82127_("get"), IslandCommand::get));
/*     */     
/*  54 */     return main;
/*     */   }
/*     */   
/*     */   private static int get(CommandContext<CommandSourceStack> context, @Nonnull ReservoirIsland island) {
/*  58 */     CommandUtils.sendTranslated((CommandSourceStack)context.getSource(), "chat.immersivepetroleum.command.reservoir.get", new Object[] {
/*     */           
/*  60 */           Long.valueOf(island.getAmount()), 
/*  61 */           Utils.fDecimal(island.getAmount() / island.getCapacity() * 100.0D), (new FluidStack(island
/*  62 */             .getFluid(), 1)).getDisplayName()
/*     */         });
/*  64 */     return 1;
/*     */   }
/*     */   
/*     */   private static int locate(CommandContext<CommandSourceStack> command) {
/*  68 */     CommandSourceStack source = (CommandSourceStack)command.getSource();
/*  69 */     BlockPos srcPos = source.m_81373_().m_20183_();
/*  70 */     double dx = srcPos.m_123341_() + 0.5D;
/*  71 */     double dz = srcPos.m_123343_() + 0.5D;
/*  72 */     int range = 128;
/*  73 */     int rangeSqr = range * range;
/*     */     
/*  75 */     Set<ReservoirIsland> nearby = new HashSet<>();
/*     */     
/*  77 */     ReservoirRegionDataStorage storage = ReservoirRegionDataStorage.get();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     ReservoirRegionDataStorage.RegionData[] regions = { storage.getRegionData(new ReservoirRegionDataStorage.RegionPos(srcPos, 1, -1)), storage.getRegionData(new ReservoirRegionDataStorage.RegionPos(srcPos, 1, 1)), storage.getRegionData(new ReservoirRegionDataStorage.RegionPos(srcPos, -1, -1)), storage.getRegionData(new ReservoirRegionDataStorage.RegionPos(srcPos, -1, 1)) };
/*     */ 
/*     */     
/*  86 */     ResourceKey<Level> dimKey = source.m_81372_().m_46472_();
/*  87 */     for (int i = 0; i < regions.length; i++) {
/*  88 */       ReservoirRegionDataStorage.RegionData rd = regions[i];
/*  89 */       if (rd != null) {
/*  90 */         Multimap<ResourceKey<Level>, ReservoirIsland> islands = rd.getReservoirIslandList();
/*  91 */         synchronized (islands) {
/*  92 */           islands.get(dimKey).forEach(island -> {
/*     */                 if (island.getBoundingBox().getCenter().m_203198_(dx, 0.0D, dz) <= rangeSqr) {
/*     */                   nearby.add(island);
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     if (nearby.isEmpty()) {
/* 102 */       CommandUtils.sendTranslated(source, "chat.immersivepetroleum.command.reservoir.notfound", new Object[0]);
/* 103 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     ReservoirIsland closestIsland = null;
/* 108 */     double smallestDistance = rangeSqr;
/* 109 */     ColumnPos p = null;
/* 110 */     for (ReservoirIsland island : nearby) {
/* 111 */       AxisAlignedIslandBB axisAlignedIslandBB = island.getBoundingBox();
/* 112 */       for (int j = axisAlignedIslandBB.minZ() + 1; j < axisAlignedIslandBB.maxZ(); j++) {
/* 113 */         for (int x = axisAlignedIslandBB.minX() + 1; x < axisAlignedIslandBB.maxX(); x++) {
/* 114 */           if (island.contains(x, j)) {
/* 115 */             double xa = x + 0.5D - dx;
/* 116 */             double za = j + 0.5D - dz;
/* 117 */             double dst = xa * xa + za * za;
/* 118 */             if (dst < smallestDistance) {
/* 119 */               p = new ColumnPos(x, j);
/* 120 */               smallestDistance = dst;
/* 121 */               closestIsland = island;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     if (closestIsland == null) {
/* 129 */       CommandUtils.sendStringError(source, "List should not be empty. Please report this bug. (Immersive Petroleum)");
/* 130 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     double hPressure = 0.0D;
/* 135 */     AxisAlignedIslandBB IAABB = closestIsland.getBoundingBox();
/* 136 */     for (int z = IAABB.minZ() + 1; z < IAABB.maxZ(); z++) {
/* 137 */       for (int x = IAABB.minX() + 1; x < IAABB.maxX(); x++) {
/*     */         double cPressure;
/* 139 */         if (closestIsland.contains(x, z) && (cPressure = ReservoirHandler.getValueOf((Level)source.m_81372_(), x, z)) > hPressure) {
/* 140 */           hPressure = cPressure;
/* 141 */           p = new ColumnPos(x, z);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + p.f_140723_() + " ~ " + p.f_140724_());
/* 147 */     HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237115_("chat.coordinates.tooltip"));
/*     */     
/* 149 */     source.m_81354_((Component)Component.m_237110_("chat.immersivepetroleum.command.reservoir.locate", new Object[] {
/* 150 */             (closestIsland.getType()).name, 
/* 151 */             ComponentUtils.m_130748_((Component)Component.m_237113_("" + p.f_140723_() + " " + p.f_140723_())).m_130938_(s -> s.m_131140_(ChatFormatting.GREEN).m_131155_(Boolean.valueOf(true)).m_131142_(clickEvent).m_131144_(hoverEvent))
/*     */           }), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return 1;
/*     */   }
/*     */   
/*     */   private static LiteralArgumentBuilder<CommandSourceStack> setters() {
/* 162 */     LiteralArgumentBuilder<CommandSourceStack> set = (LiteralArgumentBuilder<CommandSourceStack>)Commands.m_82127_("set").requires(source -> source.m_6761_(4));
/*     */     
/* 164 */     set.then(Commands.m_82127_("amount").then((ArgumentBuilder)positional(Commands.m_82129_("amount", (ArgumentType)LongArgumentType.longArg(0L, 4294967295L)), IslandCommand::setReservoirAmount)));
/* 165 */     set.then(Commands.m_82127_("capacity").then((ArgumentBuilder)positional(Commands.m_82129_("capacity", (ArgumentType)LongArgumentType.longArg(0L, 4294967295L)), IslandCommand::setReservoirCapacity)));
/* 166 */     set.then(Commands.m_82127_("type").then((ArgumentBuilder)positional(Commands.m_82129_("name", (ArgumentType)StringArgumentType.string()).suggests(IslandCommand::typeSuggestor), IslandCommand::setReservoirType)));
/*     */     
/* 168 */     return set;
/*     */   }
/*     */   
/*     */   private static CompletableFuture<Suggestions> typeSuggestor(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
/* 172 */     return SharedSuggestionProvider.m_82981_(ReservoirType.map.values().stream().map(type -> type.name), builder);
/*     */   }
/*     */   
/*     */   private static int setReservoirAmount(CommandContext<CommandSourceStack> context, @Nonnull ReservoirIsland island) {
/* 176 */     long amount = ((Long)context.getArgument("amount", Long.class)).longValue();
/* 177 */     island.setAmount(amount);
/* 178 */     island.setDirty();
/*     */     
/* 180 */     CommandUtils.sendTranslated((CommandSourceStack)context.getSource(), "chat.immersivepetroleum.command.reservoir.set.amount.success", new Object[] { Long.valueOf(island.getAmount()) });
/* 181 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setReservoirCapacity(CommandContext<CommandSourceStack> context, @Nonnull ReservoirIsland island) {
/* 185 */     long capacity = ((Long)context.getArgument("capacity", Long.class)).longValue();
/* 186 */     island.setAmountAndCapacity(capacity, capacity);
/* 187 */     island.setDirty();
/*     */     
/* 189 */     CommandUtils.sendTranslated((CommandSourceStack)context.getSource(), "chat.immersivepetroleum.command.reservoir.set.capacity.success", new Object[] { Long.valueOf(island.getCapacity()) });
/* 190 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setReservoirType(CommandContext<CommandSourceStack> context, @Nonnull ReservoirIsland island) {
/* 194 */     String name = (String)context.getArgument("name", String.class);
/* 195 */     ReservoirType reservoir = null;
/* 196 */     for (ReservoirType res : ReservoirType.map.values()) {
/* 197 */       if (res.name.equalsIgnoreCase(name)) {
/* 198 */         reservoir = res;
/*     */       }
/*     */     } 
/* 201 */     if (reservoir == null) {
/* 202 */       CommandUtils.sendTranslatedError((CommandSourceStack)context.getSource(), "chat.immersivepetroleum.command.reservoir.set.type.fail", new Object[] { name });
/* 203 */       return 1;
/*     */     } 
/*     */     
/* 206 */     island.setReservoirType(reservoir);
/* 207 */     island.setDirty();
/*     */     
/* 209 */     CommandUtils.sendTranslated((CommandSourceStack)context.getSource(), "chat.immersivepetroleum.command.reservoir.set.type.success", new Object[] { reservoir.name });
/* 210 */     return 1;
/*     */   }
/*     */   
/*     */   static <T extends ArgumentBuilder<CommandSourceStack, T>> T positional(T builder, BiFunction<CommandContext<CommandSourceStack>, ReservoirIsland, Integer> function) {
/* 214 */     builder.executes(command -> {
/*     */           ColumnPos pos = Utils.toColumnPos(new BlockPos(((CommandSourceStack)command.getSource()).m_81371_()));
/*     */           
/*     */           ReservoirIsland island = ReservoirHandler.getIsland((Level)((CommandSourceStack)command.getSource()).m_81372_(), pos);
/*     */           
/*     */           if (island == null) {
/*     */             CommandUtils.sendTranslated((CommandSourceStack)command.getSource(), "chat.immersivepetroleum.command.reservoir.notfound", new Object[0]);
/*     */             return 1;
/*     */           } 
/*     */           return ((Integer)function.apply(command, island)).intValue();
/* 224 */         }).then(Commands.m_82129_("location", (ArgumentType)ColumnPosArgument.m_118989_()).executes(command -> {
/*     */             ColumnPos pos = ColumnPosArgument.m_118992_(command, "location");
/*     */             
/*     */             ReservoirIsland island = ReservoirHandler.getIsland((Level)((CommandSourceStack)command.getSource()).m_81372_(), pos);
/*     */             
/*     */             if (island == null) {
/*     */               CommandUtils.sendTranslated((CommandSourceStack)command.getSource(), "chat.immersivepetroleum.command.reservoir.notfound", new Object[0]);
/*     */               return 1;
/*     */             } 
/*     */             return ((Integer)function.apply(command, island)).intValue();
/*     */           }));
/* 235 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\Luca\Downloads\ImmersivePetroleum-1.19.2-4.2.0-29.jar!\flaxbeard\immersivepetroleum\commo\\util\commands\IslandCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */