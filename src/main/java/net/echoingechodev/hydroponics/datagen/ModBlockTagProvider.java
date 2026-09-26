package net.echoingechodev.hydroponics.datagen;

import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Hydroponics.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)
                .add(ModBlocks.HYDROPONIC_PLANTER_BLOCK.get())
                .add(ModBlocks.HYDROPONIC_TOWER_BLOCK.get());

        tag(ModTags.Blocks.HYDROPONIC_TOWER_BOTTOM_ATTACHMENTS)
                .add(ModBlocks.HYDROPONIC_TOWER_BLOCK.get());
    }
}
