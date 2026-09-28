package net.echoingechodev.hydroponics.datagen.compat;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import net.echoingechodev.hydroponics.fluids.ModFluids;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class CreateMixerRecipeProvider extends MixingRecipeGen {

    GeneratedRecipe
    NUTRIENT_FLUID_RAW = create("nutrient_fluid_from_mixing_raw", b ->
            b.require(Fluids.WATER, 250)
                    .require(ModItems.SALTPETER)
                    .require(ModItems.COMPOST)
                    .require(Items.BONE_MEAL)
            .output(ModFluids.NUTRIENT_WATER.get(), 250)
            ),
    NUTRIENT_FLUID_MIX = create("nutrient_fluid_from_mixing", b ->
            b.require(Fluids.WATER, 250)
            .require(ModItems.NUTRIENT_MIX)
            .output(ModFluids.NUTRIENT_WATER.get(), 250)
            )
            ;

    public CreateMixerRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }
}
