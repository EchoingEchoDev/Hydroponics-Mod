package net.echoingechodev.hydroponics.datagen;

import dev.yurisuika.compost.data.loot.CompostLootTableProvider;
import dev.yurisuika.compost.data.loot.packs.ComposterLoot;
import net.echoingechodev.hydroponics.blocks.ModBlocks;
import net.echoingechodev.hydroponics.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Hydroponic Tower
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.HYDROPONIC_TOWER_BLOCK.get())
                .pattern("IGI")
                .pattern("I I")
                .pattern("IGI")
                .define('I', Items.COPPER_INGOT)
                //.define('H', Items.HOPPER)
                .define('G', Items.COPPER_GRATE)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(recipeOutput);

        // Hydroponic Planter
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.HYDROPONIC_PLANTER_BLOCK.get())
                .pattern("IGI")
                .pattern("III")
                .define('I', Items.COPPER_INGOT)
                .define('G', Items.COPPER_GRATE)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(recipeOutput);

        // Nutrient Water - no modded items
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NUTRIENT_WATER_BUCKET.get())
                .requires(Items.WATER_BUCKET)
                .requires(Items.BUCKET)
                .requires(Items.BONE_BLOCK)
                .requires(Items.LEATHER, 2)
                .requires(Items.DRIED_KELP_BLOCK)
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .save(recipeOutput, "hydroponics:nutrient_water_bucket_from_vanilla");

        // Nutrient Water - base recipe
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NUTRIENT_WATER_BUCKET.get())
                .requires(Items.WATER_BUCKET)
                .requires(Items.BUCKET)
                .requires(Items.BONE_MEAL, 2)
                .requires(ModItems.COMPOST.get())
                .requires(ModItems.SALTPETER.get())
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .save(recipeOutput, "hydroponics:nutrient_water_bucket_from_base");

        // Nutrient Water - create recipe

        // Nutrient Water - mekanism recipe
    }
}
