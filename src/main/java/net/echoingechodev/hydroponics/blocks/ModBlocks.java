package net.echoingechodev.hydroponics.blocks;

import net.echoingechodev.hydroponics.Hydroponics;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicTowerEntity;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<HydroponicTowerBlock> HYDROPONIC_TOWER_BLOCK = registerBlockWithItem("hydroponic_tower_block",
            () -> new HydroponicTowerBlock(BlockBehaviour.Properties.of().noOcclusion()));


    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> result = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(result.get(), new Item.Properties()));
        return result;
    }
}
