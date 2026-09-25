package net.echoingechodev.hydroponics.datagen;

import net.echoingechodev.hydroponics.Hydroponics;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Hydroponics.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
