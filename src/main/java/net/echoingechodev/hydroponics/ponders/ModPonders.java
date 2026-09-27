package net.echoingechodev.hydroponics.ponders;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.registration.SharedTextRegistrationHelper;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public class ModPonders implements PonderPlugin {

    public static final ResourceLocation SETUP_HYDROPONIC_TOWER = ResourceLocation.fromNamespaceAndPath(MODID, "setup_hydroponic_tower");

    @Override
    public String getModId() {
        return MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        //PonderPlugin.super.registerScenes(helper);
        register(helper);
    }

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<Item> HELPER = helper.withKeyFunction(BuiltInRegistries.ITEM::getKey);

        HELPER.forComponents(
                ModBlocks.HYDROPONIC_TOWER_BLOCK.asItem(),
                ModBlocks.HYDROPONIC_PLANTER_BLOCK.asItem(),
                Blocks.WHEAT.asItem()
        ).addStoryBoard(SETUP_HYDROPONIC_TOWER, HydroponicTowerScenes::settingUpHydroponicTowers);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderPlugin.super.registerTags(helper);
    }

    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {
        PonderPlugin.super.registerSharedText(helper);
    }
}
