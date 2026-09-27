package net.echoingechodev.hydroponics.jade;


import net.echoingechodev.hydroponics.blocks.HydroponicPlanterBlock;
import net.echoingechodev.hydroponics.blocks.HydroponicTowerBlock;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicPlanterEntity;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicTowerEntity;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import snownee.jade.api.*;

@WailaPlugin
public class HydroponicsJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {

        registration.registerBlockDataProvider(HydroponicsComponentProvider.INSTANCE, HydroponicTowerEntity.class);
        registration.registerBlockDataProvider(HydroponicsComponentProvider.INSTANCE, HydroponicPlanterEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {

        registration.registerBlockComponent(HydroponicsComponentProvider.INSTANCE, HydroponicTowerBlock.class);
        registration.registerBlockComponent(HydroponicsComponentProvider.INSTANCE, HydroponicPlanterBlock.class);
    }
}
