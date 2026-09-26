package net.echoingechodev.hydroponics.blocks;

import com.mojang.serialization.MapCodec;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicPlanterEntity;
import net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

public class HydroponicPlanterBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<HydroponicPlanterBlock> CODEC = simpleCodec(HydroponicPlanterBlock::new);
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public HydroponicPlanterBlock(Properties properties) {
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


    /*@Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

    }*/

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.TRUE;
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    /* BLOCK ENTITY FUNCTIONS */

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HydroponicPlanterEntity(null, blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntitieTypes.HYDROPONIC_PLANTER_ENTITY_TYPE.get(), HydroponicPlanterEntity::tick, true);
    }

    public static <E extends BlockEntity, T extends BlockEntity> @Nullable BlockEntityTicker<T> createTickerHelper(
            BlockEntityType<T> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker, boolean forcethisone
    ) {
        return checkedType == type ? (BlockEntityTicker<T>) ticker : null;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
