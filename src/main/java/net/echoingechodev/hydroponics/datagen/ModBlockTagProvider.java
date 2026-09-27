package net.echoingechodev.hydroponics.datagen;

import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
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

        tag(Tags.Blocks.ORES)
                .add(ModBlocks.SALTPETER_ORE.get())
                .add(ModBlocks.NETHER_SALTPETER_ORE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SALTPETER_ORE.get())
                .add(ModBlocks.NETHER_SALTPETER_ORE.get())
                .add(ModBlocks.HYDROPONIC_TOWER_BLOCK.get())
                .add(ModBlocks.HYDROPONIC_PLANTER_BLOCK.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SALTPETER_ORE.get())
                .add(ModBlocks.NETHER_SALTPETER_ORE.get());

        tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
                .add(ModBlocks.HYDROPONIC_TOWER_BLOCK.get())
                .add(ModBlocks.HYDROPONIC_PLANTER_BLOCK.get());

        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(ModBlocks.SALTPETER_ORE.get());

        tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK)
                .add(ModBlocks.NETHER_SALTPETER_ORE.get());
    }
}
