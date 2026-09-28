package net.echoingechodev.hydroponics.datagen;

import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static net.echoingechodev.hydroponics.blocks.ModBlocks.SALTPETER_ORE;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Hydroponics.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.COMPOST.get());
        basicItem(ModItems.SALTPETER.get());
        basicItem(ModItems.NUTRIENT_WATER_BUCKET.get());
        basicItem(ModItems.NUTRIENT_MIX.get());
    }
}
