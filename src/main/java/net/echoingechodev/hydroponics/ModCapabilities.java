package net.echoingechodev.hydroponics;

import net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

@EventBusSubscriber(modid = MODID)
public class ModCapabilities {

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntitieTypes.HYDROPONIC_TOWER_ENTITY_TYPE.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler(side)
        );
    }
}
