package net.echoingechodev.hydroponics.blocks.blockentities;

import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;
import static net.echoingechodev.hydroponics.blocks.ModBlocks.*;

public class ModBlockEntitieTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydroponicTowerEntity>> HYDROPONIC_TOWER_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(
            "hydroponic_tower_be",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            HydroponicTowerEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            HYDROPONIC_TOWER_BLOCK.get()

                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydroponicPlanterEntity>> HYDROPONIC_PLANTER_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(
            "hydroponic_planter_be",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            HydroponicPlanterEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            HYDROPONIC_PLANTER_BLOCK.get()

                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );
}
