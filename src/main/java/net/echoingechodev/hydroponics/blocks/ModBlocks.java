package net.echoingechodev.hydroponics.blocks;

import net.echoingechodev.hydroponics.fluids.ModFluids;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;
import static net.echoingechodev.hydroponics.fluids.ModFluids.NUTRIENT_WATER;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<HydroponicTowerBlock> HYDROPONIC_TOWER_BLOCK = registerBlockWithItem("hydroponic_tower_block",
            () -> new HydroponicTowerBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final DeferredBlock<HydroponicPlanterBlock> HYDROPONIC_PLANTER_BLOCK = registerBlockWithItem("hydroponic_planter_block",
            () -> new HydroponicPlanterBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final Supplier<LiquidBlock> NUTRIENT_WATER_BLOCK = BLOCKS.register("nutrient_water_block", () ->
            new LiquidBlock(NUTRIENT_WATER.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .replaceable().liquid().noCollission().pushReaction(PushReaction.DESTROY).strength(100.0F).noLootTable()));

    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> result = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(result.get(), new Item.Properties()));
        return result;
    }
}
