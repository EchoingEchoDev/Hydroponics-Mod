package net.echoingechodev.hydroponics.datagen;

import com.simibubi.create.AllTags;
import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Hydroponics.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.BUCKETS)
                .add(ModItems.NUTRIENT_WATER_BUCKET.get());

        tag(Tags.Items.DUSTS)
                .add(ModItems.SALTPETER.get());

        tag(Tags.Items.FERTILIZERS)
                .add(ModItems.COMPOST.get())
                .add(ModItems.SALTPETER.get())
                .add(ModItems.NUTRIENT_MIX.get());
    }
}
