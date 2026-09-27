package net.echoingechodev.hydroponics.jade;

import net.echoingechodev.hydroponics.blocks.HydroponicTowerBlock;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicPlanterEntity;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicTowerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import snownee.jade.addon.universal.FluidStorageProvider;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.ProgressStyle;
import snownee.jade.api.view.ProgressView;
import snownee.jade.impl.ui.SimpleProgressStyle;
import snownee.jade.util.JadeForgeUtils;

import static net.echoingechodev.hydroponics.Hydroponics.LOGGER;
import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public enum HydroponicsComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MODID, "hydroponic_tower_entity");



    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag tag = accessor.getServerData();
        //LOGGER.info("DEBUG JADE: HELP!!!");
        if (!tag.contains("TankFluid")) {
            return;
        }
        FluidStack fluid = FluidStack.parseOptional(accessor.getLevel().registryAccess(), tag.getCompound("TankFluid"));
        int capacity = tag.getInt("TankCapacity");

        //LOGGER.info("DEBUG JADE: Fluid: " + fluid + " Capacity: " + capacity);

        IElementHelper helper = IElementHelper.get();
        var fluidElement = helper.fluid(JadeForgeUtils.fromFluidStack(fluid));
        ProgressStyle progressStyle = new SimpleProgressStyle().overlay(fluidElement);
        IElement progressbar = helper.progress(
                capacity == 0 ? 0f : (float) fluid.getAmount() / capacity,
                fluid.isEmpty() ? Component.literal("Empty") : Component.literal(fluid.getAmount() + " mB "),
                progressStyle,
                BoxStyle.getNestedBox(),
                true
        );
        tooltip.add(Component.translatable(fluid.getDescriptionId()));
        tooltip.add(progressbar);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof  HydroponicTowerEntity tower) {
            FluidStack fluid = tower.getTank().getFluid();
            if (fluid.isEmpty()) {
                //compoundTag.put("TankFluid", FluidStack.EMPTY);
                compoundTag.putInt("TankCapacity", tower.getTank().getCapacity());
            } else {
                compoundTag.put("TankFluid", fluid.save(blockAccessor.getLevel().registryAccess()));
                compoundTag.putInt("TankCapacity", tower.getTank().getCapacity());
            }

        }
        if (blockAccessor.getBlockEntity() instanceof  HydroponicPlanterEntity tower) {
            FluidStack fluid = tower.getTank().getFluid();
            if (fluid.isEmpty()) {
                //compoundTag.put("TankFluid", FluidStack.EMPTY);
                compoundTag.putInt("TankCapacity", tower.getTank().getCapacity());
            } else {
                compoundTag.put("TankFluid", fluid.save(blockAccessor.getLevel().registryAccess()));
                compoundTag.putInt("TankCapacity", tower.getTank().getCapacity());
            }

        }
    }
}
