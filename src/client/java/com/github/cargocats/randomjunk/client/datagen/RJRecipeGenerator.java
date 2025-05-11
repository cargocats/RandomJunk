package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.registry.RJItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RJRecipeGenerator extends FabricRecipeProvider {
    public RJRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShapeless(RecipeCategory.FOOD, RJItems.PROTEIN_DRINK)
                        .input(RJItems.CREATINE)
                        .input(Items.MILK_BUCKET)
                        .criterion(hasItem(RJItems.CREATINE), conditionsFromItem(RJItems.CREATINE))
                        .offerTo(recipeExporter);
                createShapeless(RecipeCategory.BREWING, RJItems.CREATINE)
                        .input(Items.GHAST_TEAR)
                        .input(Items.REDSTONE)
                        .input(Items.BLAZE_POWDER)
                        .input(Items.GLOWSTONE_DUST)
                        .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                        .criterion(hasItem(Items.GHAST_TEAR), conditionsFromItem(Items.GHAST_TEAR))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Random Junk Items";
    }
}
