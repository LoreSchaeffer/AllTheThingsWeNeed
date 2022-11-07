package it.multicoredev.attwn.registries.blocks;

import it.multicoredev.attwn.AllTheThingsWeNeed;
import it.multicoredev.attwn.datagen.LootTablesGenerator;
import it.multicoredev.attwn.datagen.ModLanguageProvider;
import it.multicoredev.attwn.datagen.tags.ModBlockTags;
import it.multicoredev.attwn.datagen.tags.ModItemTags;
import it.multicoredev.attwn.datagen.utils.BaseRegistry;
import it.multicoredev.attwn.init.ModSetup;
import it.multicoredev.attwn.registries.Registry;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Consumer;

/**
 * BSD 3-Clause License
 * <p>
 * Copyright (c) 2022, Lorenzo Magni
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p>
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p>
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p>
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class BaseBlock extends BaseRegistry {
    protected final String name;
    private final RegistryObject<Block> block;
    private final RegistryObject<Item> blockItem;
    private final Map<String, String> lang = new HashMap<>();
    private final List<RecipeBuilder> recipes = new ArrayList<>();

    public BaseBlock(String name, BlockBehaviour.Properties properties) {
        this.name = name;

        this.block = Registry.BLOCKS.register(name, () -> new Block(properties));
        this.blockItem = fromBlock(this.block);
    }

    public String getName() {
        return name;
    }

    public RegistryObject<Block> getBlockRegistry() {
        return block;
    }

    public RegistryObject<Item> getBlockItemRegistry() {
        return blockItem;
    }

    public Block getBlock() {
        return block.get();
    }

    public Item getBlockItem() {
        return blockItem.get();
    }

    public BaseBlock setLang(String localization, String lang) {
        this.lang.put(localization, lang);
        return this;
    }

    public BaseBlock addRecipe(RecipeBuilder builder) {
        this.recipes.add(builder);
        return this;
    }

    public BaseBlock addRecipes(Collection<? extends RecipeBuilder> builders) {
        this.recipes.addAll(builders);
        return this;
    }

    protected void addAllRecipes() {
    }

    @Override
    public void registerBlockstates(BlockStateProvider provider) {
        provider.simpleBlock(getBlock());
    }

    @Override
    public void registerModels(ItemModelProvider provider) {
        provider.withExistingParent(name, provider.modLoc("block/" + name));
    }

    @Override
    public final void addAllRecipes(Consumer<FinishedRecipe> consumer) {
        addAllRecipes();

        Map<String, Integer> shaped = new HashMap<>();
        Map<String, Integer> shapeless = new HashMap<>();
        Map<String, Integer> smelting = new HashMap<>();
        Map<String, Integer> stonecutter = new HashMap<>();

        recipes.forEach(recipe -> {
            String path;

            if (recipe instanceof ShapedRecipeBuilder) {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (shaped.containsKey(name)) {
                    int i = shaped.get(name);
                    shaped.put(name, i + 1);
                    path = name + "_shaped_" + i;
                } else {
                    shaped.put(name, 1);
                    path = name + "_shaped";
                }
            } else if (recipe instanceof ShapelessRecipeBuilder) {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (shapeless.containsKey(name)) {
                    int i = shapeless.get(name);
                    shapeless.put(name, i + 1);
                    path = name + "_shapeless_" + i;
                } else {
                    shapeless.put(name, 1);
                    path = name + "_shapeless";
                }
            } else if (recipe instanceof SimpleCookingRecipeBuilder) {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (smelting.containsKey(name)) {
                    int i = smelting.get(name);
                    smelting.put(name, i + 1);
                    path = name + "_smelting_" + i;
                } else {
                    smelting.put(name, 1);
                    path = name + "_smelting";
                }
            } else {
                String name = ForgeRegistries.ITEMS.getKey(recipe.getResult()).getPath();

                if (stonecutter.containsKey(name)) {
                    int i = stonecutter.get(name);
                    stonecutter.put(name, i + 1);
                    path = name + "_stonecutter_" + i;
                } else {
                    stonecutter.put(name, 1);
                    path = name + "_stonecutter";
                }
            }

            recipe.save(consumer, new ResourceLocation(AllTheThingsWeNeed.MODID, path));
        });
    }

    @Override
    public void registerToLanguage(ModLanguageProvider provider) {
        if (!lang.containsKey(provider.getLocale())) return;
        provider.add(getBlock(), lang.get(provider.getLocale()));
    }

    @Override
    public void registerBlockTags(ModBlockTags provider) {

    }

    @Override
    public void registerItemTags(ModItemTags provider) {
    }

    @Override
    public void registerLootTables(LootTablesGenerator generator) {
    }

    protected <T extends Block> RegistryObject<Item> fromBlock(RegistryObject<T> block) {
        return Registry.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(ModSetup.ATTWN_TAB)));
    }
}
