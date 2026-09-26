package net.echoingechodev.hydroponics.items;

import net.echoingechodev.hydroponics.fluids.ModFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.echoingechodev.hydroponics.Hydroponics.MODID;
import static net.echoingechodev.hydroponics.fluids.ModFluids.NUTRIENT_WATER;
import static net.echoingechodev.hydroponics.fluids.ModFluids.NUTRIENT_WATER_FLOWING;
import static net.minecraft.world.item.Items.BUCKET;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> COMPOST = ITEMS.registerSimpleItem("compost", new Item.Properties());
    public static final DeferredItem<Item> NUTRIENT_WATER_BUCKET = ITEMS.register("nutrient_water_bucket",
            () -> new BucketItem(NUTRIENT_WATER.get(), new Item.Properties().craftRemainder(BUCKET).stacksTo(1)));


}
