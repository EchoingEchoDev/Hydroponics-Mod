package net.echoingechodev.hydroponics;

import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicPlanterEntity;
import net.echoingechodev.hydroponics.blocks.blockentities.HydroponicTowerEntity;
import net.echoingechodev.hydroponics.datagen.DataGenerators;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.echoingechodev.hydroponics.blocks.ModBlocks.*;
import static net.echoingechodev.hydroponics.blocks.blockentities.ModBlockEntitieTypes.BLOCK_ENTITY_TYPES;
import static net.echoingechodev.hydroponics.fluids.ModFluids.FLUIDS;
import static net.echoingechodev.hydroponics.fluids.ModFluids.FLUID_TYPES;
import static net.echoingechodev.hydroponics.items.ModItems.*;
import static net.minecraft.world.item.Items.WHEAT_SEEDS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Hydroponics.MODID)
public class Hydroponics {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "hydroponics";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static boolean isPonderLoaded = false;
    public static boolean isCreateLoaded = false;
    public static boolean isMekanismLoaded = false;

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with the id "hydroponics:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("hydroponics_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.hydroponics")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> COMPOST.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                //output.accept(EXAMPLE_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(COMPOST.get());
                output.accept(SALTPETER.get());
                output.accept(SALTPETER_ORE.get());
                output.accept(NETHER_SALTPETER_ORE.get());
                output.accept(NUTRIENT_WATER_BUCKET.get());
                output.accept(HYDROPONIC_TOWER_BLOCK.get());
                output.accept(HYDROPONIC_PLANTER_BLOCK.get());
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Hydroponics(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        FLUIDS.register(modEventBus);
        FLUID_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Hydroponics) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        isPonderLoaded = ModList.get().isLoaded("ponder");
        isCreateLoaded = ModList.get().isLoaded("create");
        isMekanismLoaded = ModList.get().isLoaded("mekanism");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.SPEC.isLoaded()) {
            HydroponicPlanterEntity.updateFromConfig();
            HydroponicTowerEntity.updateFromConfig();
        }
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
