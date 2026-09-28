package net.echoingechodev.hydroponics.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> OVERWORLD_SALTPETER_ORE_PLACED_KEY = registerKey("overworld_saltpeter_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_SALTPETER_ORE_PLACED_KEY = registerKey("nether_saltpeter_ore_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OVERWORLD_SALTPETER_ORE_PLACED_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.OVERWORLD_SALTPETER_ORE_KEY),
                commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(64))));
        register(context, NETHER_SALTPETER_ORE_PLACED_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.NETHER_SALTPETER_ORE_KEY),
                commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(128))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    public static void register(BootstrapContext<PlacedFeature> context,
                          ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
