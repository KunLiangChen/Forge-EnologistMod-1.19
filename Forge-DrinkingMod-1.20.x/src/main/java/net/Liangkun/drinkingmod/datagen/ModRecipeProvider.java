package net.Liangkun.drinkingmod.datagen;

import net.Liangkun.drinkingmod.DrinkingMod;
import net.Liangkun.drinkingmod.block.ModBlocks;
import net.Liangkun.drinkingmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements ICondition {
    private  static final List<ItemLike> JELLYFISH_SMELTABLES = List.of(ModItems.DRYJELLYHEAD.get(),
            ModBlocks.JELLYFISHIHEAD.get());
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        oreBlasting(
                pWriter,
                JELLYFISH_SMELTABLES,
                RecipeCategory.MISC,
                ModItems.DRYJELLYHEAD.get(),
                0.50f,
                100,
                "jellyfish"
        );
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.CUP.get())
                .pattern("S*S")
                .pattern("S S")
                .pattern(" ! ")
                .define('S', ItemTags.PLANKS)
                .define('*', Items.IRON_INGOT)
                .define('!',ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT))
                .save(pWriter);
    }

    @Override
    public ResourceLocation getID() {
        return null;
    }

    @Override
    public boolean test(IContext iContext) {
        return false;
    }
    protected static void oreSmelting(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTIme,
            @NotNull String pGroup
    ) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTime,
            @NotNull String pGroup
    ) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static void oreCooking(
            @NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
            List<ItemLike> pIngredients,
            @NotNull RecipeCategory pCategory,
            @NotNull ItemLike pResult,
            float pExperience,
            int pCookingTime,
            @NotNull String pGroup,
            String pRecipeName
    ) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder
                    .generic(
                            Ingredient.of(itemlike),
                            pCategory,
                            pResult,
                            pExperience,
                            pCookingTime,
                            pCookingSerializer
                    )
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(
                            pFinishedRecipeConsumer,
                            DrinkingMod.MOD_ID + ":" +
                                    getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)
                    );
        }
    }
}
