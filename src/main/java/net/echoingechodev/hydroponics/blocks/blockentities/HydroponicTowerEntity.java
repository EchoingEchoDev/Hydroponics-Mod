package net.echoingechodev.hydroponics.blocks.blockentities;

import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.HTMLDocument;
import java.util.Set;
import java.util.function.Predicate;

import static net.echoingechodev.hydroponics.blocks.ModBlocks.HYDROPONIC_TOWER_BLOCK;
import static net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes.HYDROPONIC_TOWER_ENTITY_TYPE;


public class HydroponicTowerEntity extends BlockEntity {

    private static final int fill_capacity = 4000;
    private static final int max_transfer = 1000;

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
            if (stack.is(ModTags.Fluids.HYDROPONIC_GROWTH_FLUID)) {
                return true;
            }
            return false;
        }
    };
    private int connected_sides = 0;
    private boolean connected_below = false;
    private static final int ticks_per_action = 4;
    private int tick_count = 0;

    public HydroponicTowerEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(HYDROPONIC_TOWER_ENTITY_TYPE.get(), pos, blockState);
    }

    public HydroponicTowerEntity(BlockPos blockPos, BlockState blockState) {
        super(HYDROPONIC_TOWER_ENTITY_TYPE.get(), blockPos, blockState);
    }

    public IFluidHandler getFluidHandler(@Nullable Direction side) {
        if (side != Direction.UP) {
            return null;
        } else {
            return this.tank;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HydroponicTowerEntity blockEntity) {
        if (!level.isClientSide() && !blockEntity.tank.isEmpty()) {
            if (blockEntity.tick_count % ticks_per_action == 0) {
                int toTransferAvailable = Math.max(max_transfer, blockEntity.tank.getFluidAmount());
                int toTransferBelow = 0;
                int toTransferSides = 0;
                blockEntity.updateConnectedSide(level, pos);
                blockEntity.updateConnectedBelow(level, pos);

                // Calc how much to transfer to where
                if (blockEntity.connected_sides == 0) {
                    toTransferBelow = toTransferAvailable;
                } else {
                    if (blockEntity.connected_below) {
                        toTransferBelow = toTransferAvailable / 2;
                        toTransferAvailable = toTransferAvailable / 2;
                    }
                    toTransferSides = toTransferAvailable / blockEntity.connected_sides;
                }

                // Do the actual transferring
                tryTransferFluid(level, pos, blockEntity, pos.north(), toTransferSides);
                tryTransferFluid(level, pos, blockEntity, pos.east(), toTransferSides);
                tryTransferFluid(level, pos, blockEntity, pos.west(), toTransferSides);
                tryTransferFluid(level, pos, blockEntity, pos.south(), toTransferSides);
                if (blockEntity.connected_below) {
                    tryTransferFluid(level, pos, blockEntity, pos.below(), toTransferBelow);
                }

                blockEntity.tick_count = 0;
            } else {
                blockEntity.tick_count++;
            }

        }
    }

    private static void tryTransferFluid(Level level, BlockPos origin, HydroponicTowerEntity originEntity, BlockPos target, int amount) {
        if (level.getBlockState(target).is(ModTags.Blocks.HYDROPONIC_TOWER_BOTTOM_ATTACHMENTS)) {
            if (level.getBlockEntity(target) instanceof HydroponicPlanterEntity targetEntity) {
                FluidUtil.tryFluidTransfer(targetEntity.getTank(), originEntity.getTank(), amount, true);
            }
        } else if (level.getBlockState(target).is(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)) {
            if (level.getBlockEntity(target) instanceof HydroponicPlanterEntity targetEntity) {
                FluidUtil.tryFluidTransfer(targetEntity.getTank(), originEntity.getTank(), amount, true);
            }
        }
    }

    private static int countTransferTargetOnSides(Level level, BlockPos pos) {
        int result = 0;
        if (level.getBlockState(pos.north()).is(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)) {
            result++;
        }
        if (level.getBlockState(pos.east()).is(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)) {
            result++;
        }
        if (level.getBlockState(pos.west()).is(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)) {
            result++;
        }
        if (level.getBlockState(pos.south()).is(ModTags.Blocks.HYDROPONIC_TOWER_SIDE_ATTACHMENTS)) {
            result++;
        }
        return result;
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

    public void updateConnectedSide(Level level, BlockPos pos) {
        connected_sides = countTransferTargetOnSides(level, pos);
    }

    public void updateConnectedBelow(Level level, BlockPos pos) {
        connected_below = level.getBlockState(pos.below()).is(ModTags.Blocks.HYDROPONIC_TOWER_BOTTOM_ATTACHMENTS);
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


