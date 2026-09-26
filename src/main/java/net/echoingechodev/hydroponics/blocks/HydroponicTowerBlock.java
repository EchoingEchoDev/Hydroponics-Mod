package net.echoingechodev.hydroponics.blocks;

import com.mojang.serialization.MapCodec;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicTowerEntity;
import net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import static net.echoingechodev.hydroponics.blocks.ModBlocks.HYDROPONIC_PLANTER_BLOCK;
import static net.echoingechodev.hydroponics.blocks.ModBlocks.HYDROPONIC_TOWER_BLOCK;
import static net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes.BLOCK_ENTITY_TYPES;


public class HydroponicTowerBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<HydroponicTowerBlock> CODEC = simpleCodec(HydroponicTowerBlock::new);
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public HydroponicTowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /* BLOCK ENTITY FUNCTIONS */

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HydroponicTowerEntity(null, blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntitieTypes.HYDROPONIC_TOWER_ENTITY_TYPE.get(), HydroponicTowerEntity::tick);
    }

    public static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if ((level.getBlockEntity(pos) instanceof HydroponicTowerEntity towerEntity)) {
            if (isFilledFluidContainer(stack)) {
                FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(null);
                if (towerEntity.getTank().isEmpty() || towerEntity.fluidFitsInTank(fluidStack)) {
                    var result = FluidUtil.tryEmptyContainer(stack, towerEntity.getTank(), fluidStack.getAmount(), player, true);
                    if (result.isSuccess()) {
                        player.setItemInHand(hand, result.getResult());
                        return ItemInteractionResult.SUCCESS;
                    } else {
                        return ItemInteractionResult.FAIL;
                    }
                }
            } else if (isEmptyFluidContainer(stack)) {
                var result = FluidUtil.tryFillContainer(stack, towerEntity.getTank(), 1000, player, true);
                if (result.isSuccess()) {
                    player.setItemInHand(hand, result.getResult());
                    return ItemInteractionResult.SUCCESS;
                } else {
                    return ItemInteractionResult.FAIL;
                }
            }
            return ItemInteractionResult.FAIL;
        }
        return ItemInteractionResult.FAIL;
    }

    private static boolean isFilledFluidContainer(ItemStack itemStack) {
        return !FluidUtil.getFluidContained(itemStack).isEmpty();
    }

    private static boolean isEmptyFluidContainer(ItemStack itemStack) {
        return FluidUtil.getFluidContained(itemStack).isEmpty();
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof HydroponicTowerEntity entity) {
            if (level.getBlockState(neighbor).is(HYDROPONIC_TOWER_BLOCK) || level.getBlockState(neighbor).is(HYDROPONIC_PLANTER_BLOCK)) {
                entity.updateConnectedSide((Level) level, pos);
                entity.updateConnectedBelow((Level) level, pos);
            }
        }
    }


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
