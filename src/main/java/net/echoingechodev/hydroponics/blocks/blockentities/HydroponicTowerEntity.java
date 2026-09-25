package net.echoingechodev.hydroponics.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;


public class HydroponicTowerEntity extends BlockEntity {

    private static final int fill_capacity = 4000;

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

    public HydroponicTowerEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(ModBlockEntitieTypes.HYDROPONIC_TOWER_ENTITY_TYPE.get(), pos, blockState);
    }

    public HydroponicTowerEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntitieTypes.HYDROPONIC_TOWER_ENTITY_TYPE.get(), blockPos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, HydroponicTowerEntity blockEntity) {
        if (!level.isClientSide() && !blockEntity.tank.isEmpty()) {
            // Process:
            // 1. Check if any Hydroponic Planters are to the side
            // 2. If there are evenly push fluid to them
            // 3. Check if there are Hydroponic Tower Blocks below
            // 4. If there are then push leftover fluid down
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


