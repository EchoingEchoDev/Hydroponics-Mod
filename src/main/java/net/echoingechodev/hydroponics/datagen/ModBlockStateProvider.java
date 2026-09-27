package net.echoingechodev.hydroponics.datagen;

import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Hydroponics.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.SALTPETER_ORE.get(), cubeAll(ModBlocks.SALTPETER_ORE.get()));
        simpleBlockWithItem(ModBlocks.NETHER_SALTPETER_ORE.get(), cubeAll(ModBlocks.NETHER_SALTPETER_ORE.get()));
    }
}
