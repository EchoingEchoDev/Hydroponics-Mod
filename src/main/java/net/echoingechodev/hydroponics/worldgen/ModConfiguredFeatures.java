package net.echoingechodev.hydroponics.worldgen;

import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_SALTPETER_ORE_KEY = registerKey("overworld_saltpeter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> NETHER_SALTPETER_ORE_KEY = registerKey("nether_saltpeter_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {

        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new TagMatchTest(Tags.Blocks.NETHERRACKS);

        //List<OreConfiguration.TargetBlockState> overworldSaltpeterOre = List.of(
        // OreConfiguration.target(stoneReplaceables, ModBlocks.SALTPETER_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_SALTPETER_ORE_KEY, Feature.ORE, new OreConfiguration(stoneReplaceables,
                ModBlocks.SALTPETER_ORE.get().defaultBlockState(), 8));
        register(context, NETHER_SALTPETER_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.NETHER_SALTPETER_ORE.get().defaultBlockState(), 16));
    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
