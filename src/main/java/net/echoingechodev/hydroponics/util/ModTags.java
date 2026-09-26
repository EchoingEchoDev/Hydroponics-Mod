package net.echoingechodev.hydroponics.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> HYDROPONIC_TOWER_SIDE_ATTACHMENTS = createTag("hydroponic_tower_side_attachments");
        public static final TagKey<Block> HYDROPONIC_TOWER_BOTTOM_ATTACHMENTS = createTag("hydroponic_tower_bottom_attachments");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }

    public static class Fluids {

        public static final TagKey<Fluid> HYDROPONIC_GROWTH_FLUID = createTag("hydroponic_growth_fluid");

        private static TagKey<Fluid> createTag(String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }
}
