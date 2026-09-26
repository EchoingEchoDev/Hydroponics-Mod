package net.echoingechodev.hydroponics.fluids;

import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;
import static net.echoingechodev.hydroponics.blocks.ModBlocks.NUTRIENT_WATER_BLOCK;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, MODID);

    //public static final DeferredHolder<Fluid, NutrisentWaterFluid> NUTRIENT_WATER = FLUIDS.register("nutrient_water", NutrientWaterFluid::new);
    public static final Supplier<BaseFlowingFluid.Source> NUTRIENT_WATER = FLUIDS.register("nutrient_water", () -> new BaseFlowingFluid.Source(createProperties()));
    public static final Supplier<BaseFlowingFluid.Flowing> NUTRIENT_WATER_FLOWING = FLUIDS.register("nutrient_water_flowing", () -> new BaseFlowingFluid.Flowing(createProperties()));

    public static final Supplier<FluidType> NUTRIENT_WATER_TYPE = FLUID_TYPES.register("nutrient_water_fluid_type",
            () -> new BaseModFluidType(FluidType.Properties.create().canDrown(true), 0xA1EF2142, new Vector3f(239/255f, 33/255f, 66/255f)));


    public static net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties createProperties() {
        return new net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties((Supplier<? extends FluidType>) NUTRIENT_WATER_TYPE, NUTRIENT_WATER, NUTRIENT_WATER_FLOWING)
                .bucket(() -> ModItems.NUTRIENT_WATER_BUCKET.get())
                .block(NUTRIENT_WATER_BLOCK);
    }
}

