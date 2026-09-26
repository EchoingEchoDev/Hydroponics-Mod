package net.echoingechodev.hydroponics.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class HydroponicPlanterEntity extends BlockEntity {

    private static final int fill_capacity = 4000;
    //private static final int base_comsume_amount = 10;
    private static final int water_consume_amount = 20;
    private static final int nutrient_fluid_consume_amount = 2;
    private static final int ticks_per_action = 3;
    private int tick_count = 0;

    private FluidTank tank = new FluidTank(fill_capacity) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            // TODO: Change to only accept Water and Nutrient Water
            return true;
        }
    };

    public HydroponicPlanterEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(ModBlockEntitieTypes.HYDROPONIC_PLANTER_ENTITY_TYPE.get(), pos, blockState);
    }

    public HydroponicPlanterEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntitieTypes.HYDROPONIC_PLANTER_ENTITY_TYPE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HydroponicPlanterEntity blockEntity) {
        if (!level.isClientSide() && !blockEntity.getTank().isEmpty()) {
            if (blockEntity.tick_count % ticks_per_action == 0) {

                var tank = blockEntity.getTank();
                if (tank.getFluid().is(Fluids.WATER)) {         // WATER
                    if (tank.getFluidAmount() / water_consume_amount > 0) {
                        var crop = getCropAt(level, pos.above());
                        if (crop != null) {
                            crop.randomTick((ServerLevel) level, pos.above(), RandomSource.create());
                            tank.drain(water_consume_amount, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                } else if (tank.getFluid().is(Fluids.LAVA)) {   // NUTRIENT WATER TODO: Replace with Nutrient Water
                    if (tank.getFluidAmount() / nutrient_fluid_consume_amount > 0) {
                        var crop = getCropAt(level, pos.above());
                        if (crop != null) {
                            crop.randomTick((ServerLevel) level, pos.above(), RandomSource.create());
                            tank.drain(nutrient_fluid_consume_amount, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                }
                blockEntity.tick_count = 0;
            } else {
                blockEntity.tick_count++;
            }
        }
    }

    public static BlockState getCropAt(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() instanceof CropBlock) {
            return level.getBlockState(pos);
        } else {
            return null;
        }
    }

    public FluidTank getTank() {
        return this.tank;
    }

    public boolean fluidFitsInTank(FluidStack fluidStack) {
        if (this.tank.isEmpty()) {
            return true;
        }

        if (this.tank.isFluidValid(fluidStack)) {
            if (this.tank.getSpace() >= fluidStack.getAmount()) {
                return true;
            }
        }
        return false;
    }

    public int getCapacity() {
        return fill_capacity;
    }

    /* SAVING & SNYC */

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(registries, tag.getCompound(("Tank")));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Tank", tank.writeToNBT(registries, new CompoundTag()));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.put("Tank", tank.writeToNBT(registries, new CompoundTag()));
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        tank.readFromNBT(lookupProvider, tag.getCompound(("Tank")));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }
}
