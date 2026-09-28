package net.echoingechodev.hydroponics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue HYDROPONIC_TOWER_FILLCAPACITY = BUILDER
            .comment("How much fluid a Hydroponic Tower Block can store in itself. (Measured in millibuckets)")
            .defineInRange("hydroponic_tower_fillcapacity", 4000, 1000, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_TOWER_TRANSFERRATE = BUILDER
            .comment("How much fluid a Hydroponic Tower Block can transfer in one operation. (Measured in millibuckets)")
            .defineInRange("hydroponic_tower_transferrate", 1000, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_TOWER_TICKRATE = BUILDER
            .comment("How many ticks it takes for a Hydroponic Tower Block to perform one operation.")
            .defineInRange("hydroponic_tower_tickrate", 4, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_PLANTER_FILLCAPACITY = BUILDER
            .comment("How much fluid a Hydroponic Planter Block can store in itself. (Measured in millibuckets)")
            .defineInRange("hydroponic_planter_fillcapacity", 4000, 1000, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_PLANTER_TICKRATE = BUILDER
            .comment("How many ticks it takes for a Hydroponic Planter Block to perform one operation.")
            .defineInRange("hydroponic_planter_tickrate", 3, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_PLANTER_WATER_CONSUME = BUILDER
            .comment("How much water a Hydroponic Planter Block consumes every time it boosts a crops growth. (Measured in millibuckets)")
            .defineInRange("hydroponic_planter_water_consume", 20, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_PLANTER_NUTRIENT_FLUID_CONSUME = BUILDER
            .comment("How much Nutrient Fluid a Hydroponic Planter Block consumes every time it boosts a crops growth. (Measured in millibuckets)")
            .defineInRange("hydroponic_planter_nutrient_fluid_consume", 2, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue HYDROPONIC_PLANTER_OTHER_FLUID_CONSUME = BUILDER
            .comment("How much fluid, with the tag 'hydroponic_growth_fluid', a Hydroponic Planter Block consumes every time it boosts a crops growth. (Measured in millibuckets)")
            .defineInRange("hydroponic_planter_other_fluid_consume", 2, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
