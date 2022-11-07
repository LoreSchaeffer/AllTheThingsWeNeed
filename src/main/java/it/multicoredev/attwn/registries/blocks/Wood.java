package it.multicoredev.attwn.registries.blocks;

import it.multicoredev.attwn.datagen.LootTablesGenerator;
import it.multicoredev.attwn.datagen.ModLanguageProvider;
import it.multicoredev.attwn.datagen.tags.ModBlockTags;
import it.multicoredev.attwn.datagen.tags.ModItemTags;
import it.multicoredev.attwn.datagen.utils.BaseRegistry;
import it.multicoredev.attwn.registries.Registry;
import it.multicoredev.attwn.utils.ModRecipeBuilder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.multicoredev.attwn.registries.Registry.BLOCKS;
import static it.multicoredev.attwn.registries.Registry.fromBlock;

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
public class Wood extends BaseRegistry {
    protected final String name;
    private final Map<String, String[]> lang = new HashMap<>();
    private final List<RecipeBuilder> recipes = new ArrayList<>();
    private final WoodType WOOD_TYPE;

    private final RegistryObject<Block> PLANKS;
    private final RegistryObject<RotatedPillarBlock> LOG;
    private final RegistryObject<RotatedPillarBlock> STRIPPED_LOG;
    private final RegistryObject<RotatedPillarBlock> WOOD;
    private final RegistryObject<RotatedPillarBlock> STRIPPED_WOOD;
    private final RegistryObject<StandingSignBlock> SIGN;
    private final RegistryObject<WallSignBlock> WALL_SIGN;
    private final RegistryObject<PressurePlateBlock> PRESSURE_PLATE;
    private final RegistryObject<TrapDoorBlock> TRAPDOOR;
    private final RegistryObject<StairBlock> STAIRS;
    private final RegistryObject<WoodButtonBlock> BUTTON;
    private final RegistryObject<SlabBlock> SLAB;
    private final RegistryObject<FenceGateBlock> FENCE_GATE;
    private final RegistryObject<FenceBlock> FENCE;
    private final RegistryObject<DoorBlock> DOOR;
    private final RegistryObject<Item> PLANKS_ITEM;
    private final RegistryObject<Item> LOG_ITEM;
    private final RegistryObject<Item> STRIPPED_LOG_ITEM;
    private final RegistryObject<Item> WOOD_ITEM;
    private final RegistryObject<Item> STRIPPED_WOOD_ITEM;
    private final RegistryObject<Item> SIGN_ITEM;
    private final RegistryObject<Item> PRESSURE_PLATE_ITEM;
    private final RegistryObject<Item> TRAPDOOR_ITEM;
    private final RegistryObject<Item> STAIRS_ITEM;
    private final RegistryObject<Item> BUTTON_ITEM;
    private final RegistryObject<Item> SLAB_ITEM;
    private final RegistryObject<Item> FENCE_GATE_ITEM;
    private final RegistryObject<Item> FENCE_ITEM;
    private final RegistryObject<Item> DOOR_ITEM;

    public Wood(String name, MaterialColor planksColor, MaterialColor logColor) {
        this.name = name;
        WOOD_TYPE = WoodType.register(WoodType.create(name)); // TODO Make sure to register its rendering by enqueuing Atlases.addWoodType(...) during client setup...

        //TODO Add boat and boat with chest

        PLANKS = BLOCKS.register(name + "_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, planksColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        LOG = BLOCKS.register(name + "_log", () -> rotatingBlock(planksColor, logColor));
        STRIPPED_LOG = BLOCKS.register("stripped_" + name + "_log", () -> rotatingBlock(planksColor, planksColor));
        WOOD = BLOCKS.register(name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, logColor).strength(2.0F).sound(SoundType.WOOD)));
        STRIPPED_WOOD = BLOCKS.register("stripped_" + name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, planksColor).strength(2.0F).sound(SoundType.WOOD)));
        SIGN = BLOCKS.register(name + "_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, logColor).noCollission().strength(1.0F).sound(SoundType.WOOD), WOOD_TYPE));
        WALL_SIGN = BLOCKS.register(name + "_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD, logColor).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> SIGN.get()), WOOD_TYPE));
        PRESSURE_PLATE = BLOCKS.register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, planksColor).noCollission().strength(0.5F).sound(SoundType.WOOD)));
        TRAPDOOR = BLOCKS.register(name + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, planksColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(Registry::neverValidSpawn)));
        STAIRS = BLOCKS.register(name + "_stairs", () -> new StairBlock(() -> PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PLANKS.get())));
        BUTTON = BLOCKS.register(name + "_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
        SLAB = BLOCKS.register(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(PLANKS.get())));
        FENCE_GATE = BLOCKS.register(name + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(PLANKS.get())));
        FENCE = BLOCKS.register(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(PLANKS.get())));
        DOOR = BLOCKS.register(name + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, planksColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));

        PLANKS_ITEM = fromBlock(PLANKS);
        LOG_ITEM = fromBlock(LOG);
        STRIPPED_LOG_ITEM = fromBlock(STRIPPED_LOG);
        WOOD_ITEM = fromBlock(WOOD);
        STRIPPED_WOOD_ITEM = fromBlock(STRIPPED_WOOD);
        SIGN_ITEM = fromBlock(SIGN);
        PRESSURE_PLATE_ITEM = fromBlock(PRESSURE_PLATE);
        TRAPDOOR_ITEM = fromBlock(TRAPDOOR);
        STAIRS_ITEM = fromBlock(STAIRS);
        BUTTON_ITEM = fromBlock(BUTTON);
        SLAB_ITEM = fromBlock(SLAB);
        FENCE_GATE_ITEM = fromBlock(FENCE_GATE);
        FENCE_ITEM = fromBlock(FENCE);
        DOOR_ITEM = fromBlock(DOOR);
    }

    public RegistryObject<Block> getPlanksRegistry() {
        return PLANKS;
    }

    public RegistryObject<RotatedPillarBlock> getLogRegistry() {
        return LOG;
    }

    public RegistryObject<RotatedPillarBlock> getStrippedLogRegistry() {
        return STRIPPED_LOG;
    }

    public RegistryObject<RotatedPillarBlock> getWoodRegistry() {
        return WOOD;
    }

    public RegistryObject<RotatedPillarBlock> getStrippedWoodRegistry() {
        return STRIPPED_WOOD;
    }

    public RegistryObject<StandingSignBlock> getSignRegistry() {
        return SIGN;
    }

    public RegistryObject<WallSignBlock> getWallSignRegistry() {
        return WALL_SIGN;
    }

    public RegistryObject<PressurePlateBlock> getPressurePlateRegistry() {
        return PRESSURE_PLATE;
    }

    public RegistryObject<TrapDoorBlock> getTrapdoorRegistry() {
        return TRAPDOOR;
    }

    public RegistryObject<StairBlock> getStairsRegistry() {
        return STAIRS;
    }

    public RegistryObject<WoodButtonBlock> getButtonRegistry() {
        return BUTTON;
    }

    public RegistryObject<SlabBlock> getSlabRegistry() {
        return SLAB;
    }

    public RegistryObject<FenceGateBlock> getFenceGateRegistry() {
        return FENCE_GATE;
    }

    public RegistryObject<FenceBlock> getFenceRegistry() {
        return FENCE;
    }

    public RegistryObject<DoorBlock> getDoorRegistry() {
        return DOOR;
    }

    public Block getPlanks() {
        return PLANKS.get();
    }

    public RotatedPillarBlock getLog() {
        return LOG.get();
    }

    public RotatedPillarBlock getStrippedLog() {
        return STRIPPED_LOG.get();
    }

    public RotatedPillarBlock getWood() {
        return WOOD.get();
    }

    public RotatedPillarBlock getStrippedWood() {
        return STRIPPED_WOOD.get();
    }

    public StandingSignBlock getSign() {
        return SIGN.get();
    }

    public WallSignBlock getWallSign() {
        return WALL_SIGN.get();
    }

    public PressurePlateBlock getPressurePlate() {
        return PRESSURE_PLATE.get();
    }

    public TrapDoorBlock getTrapdoor() {
        return TRAPDOOR.get();
    }

    public StairBlock getStairs() {
        return STAIRS.get();
    }

    public WoodButtonBlock getButton() {
        return BUTTON.get();
    }

    public SlabBlock getSlab() {
        return SLAB.get();
    }

    public FenceGateBlock getFenceGate() {
        return FENCE_GATE.get();
    }

    public FenceBlock getFence() {
        return FENCE.get();
    }

    public DoorBlock getDoor() {
        return DOOR.get();
    }

    public RegistryObject<Item> getPlanksItemRegistry() {
        return PLANKS_ITEM;
    }

    public RegistryObject<Item> getLogItemRegistry() {
        return LOG_ITEM;
    }

    public RegistryObject<Item> getStrippedLogItemRegistry() {
        return STRIPPED_LOG_ITEM;
    }

    public RegistryObject<Item> getWoodItemRegistry() {
        return WOOD_ITEM;
    }

    public RegistryObject<Item> getStrippedWoodItemRegistry() {
        return STRIPPED_WOOD_ITEM;
    }

    public RegistryObject<Item> getSignItemRegistry() {
        return SIGN_ITEM;
    }

    public RegistryObject<Item> getPressurePlateItemRegistry() {
        return PRESSURE_PLATE_ITEM;
    }

    public RegistryObject<Item> getTrapdoorItemRegistry() {
        return TRAPDOOR_ITEM;
    }

    public RegistryObject<Item> getStairsItemRegistry() {
        return STAIRS_ITEM;
    }

    public RegistryObject<Item> getButtonItemRegistry() {
        return BUTTON_ITEM;
    }

    public RegistryObject<Item> getSlabItemRegistry() {
        return SLAB_ITEM;
    }

    public RegistryObject<Item> getFenceGateItemRegistry() {
        return FENCE_GATE_ITEM;
    }

    public RegistryObject<Item> getFenceItemRegistry() {
        return FENCE_ITEM;
    }

    public RegistryObject<Item> getDoorItemRegistry() {
        return DOOR_ITEM;
    }

    public Item getPlanksItem() {
        return PLANKS_ITEM.get();
    }

    public Item getLogItem() {
        return LOG_ITEM.get();
    }

    public Item getStrippedLogItem() {
        return STRIPPED_LOG_ITEM.get();
    }

    public Item getWoodItem() {
        return WOOD_ITEM.get();
    }

    public Item getStrippedWoodItem() {
        return STRIPPED_WOOD_ITEM.get();
    }

    public Item getSignItem() {
        return SIGN_ITEM.get();
    }

    public Item getPressurePlateItem() {
        return PRESSURE_PLATE_ITEM.get();
    }

    public Item getTrapdoorItem() {
        return TRAPDOOR_ITEM.get();
    }

    public Item getStairsItem() {
        return STAIRS_ITEM.get();
    }

    public Item getButtonItem() {
        return BUTTON_ITEM.get();
    }

    public Item getSlabItem() {
        return SLAB_ITEM.get();
    }

    public Item getFenceGateItem() {
        return FENCE_GATE_ITEM.get();
    }

    public Item getFenceItem() {
        return FENCE_ITEM.get();
    }

    public Item getDoorItem() {
        return DOOR_ITEM.get();
    }

    public Wood addTranslation(String locale, String name) {
        lang.put(locale, new String[]{
                name + " Planks",
                name + " Log",
                "Stripped " + name + " Log",
                name + " Wood",
                "Stripped " + name + " Wood",
                name + " Sign",
                name + " Pressure Plate",
                name + " Trapdoor",
                name + " Stairs",
                name + " Button",
                name + " Slab",
                name + " Fence Gate",
                name + " Fence",
                name + " Door"
        });

        return this;
    }

    @Override
    protected void addAllRecipes() {
        addRecipe(ModRecipeBuilder.oneRecipe(getLog(), getPlanks(), 4));
        addRecipe(ModRecipeBuilder.oneRecipe(getStrippedLog(), getPlanks(), 4));
        addRecipe(ModRecipeBuilder.oneRecipe(getWood(), getPlanks(), 4));
        addRecipe(ModRecipeBuilder.oneRecipe(getStrippedWood(), getPlanks(), 4));

        addRecipe(ShapedRecipeBuilder.shaped(getWood(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', getLog())
                .group(ForgeRegistries.ITEMS.getKey(getLog().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getLog().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getLog().asItem()))
        );

        addRecipe(ShapedRecipeBuilder.shaped(getStrippedWood(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', getLog())
                .group(ForgeRegistries.ITEMS.getKey(getStrippedLog().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getStrippedLog().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getStrippedLog().asItem()))
        );

        addRecipe(ShapedRecipeBuilder.shaped(getSign(), 3)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .define('#', getPlanks())
                .define('S', Items.STICK)
                .group(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getPlanks().asItem()))
        );

        addRecipe(ModRecipeBuilder.pressurePlate(getPressurePlate(), getPlanks()));

        addRecipe(ShapedRecipeBuilder.shaped(getTrapdoor(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', getPlanks())
                .group(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getPlanks().asItem()))
        );

        addRecipe(ModRecipeBuilder.stairs(getStairs(), getPlanks()));
        addRecipe(ModRecipeBuilder.button(getButton(), getPlanks()));
        addRecipe(ModRecipeBuilder.slab(getSlab(), getPlanks()));

        addRecipe(ShapedRecipeBuilder.shaped(getFenceGate(), 1)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', getPlanks())
                .define('S', Items.STICK)
                .group(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getPlanks().asItem()))
        );

        addRecipe(ShapedRecipeBuilder.shaped(getFence(), 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', getPlanks())
                .define('S', Items.STICK)
                .group(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getPlanks().asItem()))
        );

        addRecipe(ShapedRecipeBuilder.shaped(getDoor(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', getPlanks())
                .group(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath())
                .unlockedBy(ForgeRegistries.ITEMS.getKey(getPlanks().asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(getPlanks().asItem()))
        );
    }

    @Override
    public void registerBlockstates(BlockStateProvider provider) { //TODO Fix blockstates
        provider.simpleBlock(getPlanks());
        provider.simpleBlock(getLog());
        provider.simpleBlock(getStrippedLog());
        provider.simpleBlock(getWood());
        provider.simpleBlock(getStrippedWood());
        provider.signBlock(getSign(), getWallSign(), provider.modLoc("block/" + name));
        provider.pressurePlateBlock(getPressurePlate(), provider.modLoc("block/" + name));
        provider.trapdoorBlock(getTrapdoor(), provider.modLoc("block/" + name), true);
        provider.stairsBlock(getStairs(), provider.modLoc("block/" + name));
        provider.buttonBlock(getButton(), provider.modLoc("block/" + name));
        provider.slabBlock(getSlab(), provider.modLoc("block/" + name), provider.modLoc("block/" + name));
        provider.fenceGateBlock(getFenceGate(), provider.modLoc("block/" + name));
        provider.fenceBlock(getFence(), provider.modLoc("block/" + name));
        provider.doorBlock(getDoor(), provider.modLoc("block/" + name + "_bottom"), provider.modLoc("block/" + name + "_top"));
    }

    @Override
    public void registerModels(ItemModelProvider provider) { //TODO Fix models
        provider.withExistingParent(name, provider.modLoc("block/" + name));
        provider.orientableVertical(name + "_log", provider.modLoc("block/" + name + "_log_side"), provider.modLoc("block/" + name + "_log_top"));
        provider.withExistingParent("stripped_" + name + "_log", provider.modLoc("block/" + "stripped_" + name + "_log"));
        provider.withExistingParent(name + "_wood", provider.modLoc("block/" + name + "_wood"));
        provider.withExistingParent("stripped_" + name + "_wood", provider.modLoc("block/" + "stripped_" + name + "_wood"));
        provider.sign(name + "_sign", provider.modLoc("block/" + name + "_sign"));
        provider.pressurePlate(name + "_pressure_plate", provider.modLoc("block/" + name + "_pressure_plate"));
        provider.trapdoorOrientableBottom(name + "_trapdoor", provider.modLoc("block/" + name + "_trapdoor"));
        provider.stairs(name + "_stairs", provider.modLoc("block/" + name + "_stairs"), provider.modLoc("block/" + name + "_stairs"), provider.modLoc("block/" + name + "_stairs"));
        provider.button(name + "_button", provider.modLoc("block/" + name + "_button"));
        provider.slab(name + "_slab", provider.modLoc("block/" + name), provider.modLoc("block/" + name), provider.modLoc("block/" + name));
        provider.fenceGate(name + "_fence_gate", provider.modLoc("block/" + name + "_fence_gate"));
        provider.fencePost(name + "_fence", provider.modLoc("block/" + name + "_fence"));
        //provider.withExistingParent(name + "_door", provider.modLoc("block/" + name + "_door"));
    }

    @Override
    public void registerToLanguage(ModLanguageProvider provider) {
        if (!lang.containsKey(provider.getLocale())) return;
        String[] names = lang.get(provider.getLocale());

        provider.add(getPlanks(), names[0]);
        provider.add(getLog(), names[1]);
        provider.add(getStrippedLog(), names[2]);
        provider.add(getWood(), names[3]);
        provider.add(getStrippedWood(), names[4]);
        provider.add(getSign(), names[5]);
        provider.add(getPressurePlate(), names[6]);
        provider.add(getTrapdoor(), names[7]);
        provider.add(getStairs(), names[8]);
        provider.add(getButton(), names[9]);
        provider.add(getSlab(), names[10]);
        provider.add(getFenceGate(), names[11]);
        provider.add(getFence(), names[12]);
        provider.add(getDoor(), names[13]);
    }

    @Override
    public void registerBlockTags(ModBlockTags provider) {
        provider.addTag(BlockTags.MINEABLE_WITH_AXE).add(
                getPlanks(),
                getLog(),
                getStrippedLog(),
                getWood(),
                getStrippedWood(),
                getSign(),
                getPressurePlate(),
                getTrapdoor(),
                getStairs(),
                getButton(),
                getSlab(),
                getFenceGate(),
                getFence(),
                getDoor()
        );

        provider.addTag(BlockTags.PLANKS).add(getPlanks());
        provider.addTag(BlockTags.WOODEN_BUTTONS).add(getButton());
        provider.addTag(BlockTags.WOODEN_DOORS).add(getDoor());
        provider.addTag(BlockTags.WOODEN_STAIRS).add(getStairs());
        provider.addTag(BlockTags.WOODEN_SLABS).add(getSlab());
        provider.addTag(BlockTags.WOODEN_FENCES).add(getFence());
        //TODO Create tag NAME_LOGS containing log, wood, stripped log, stripped wood
        provider.addTag(BlockTags.LOGS_THAT_BURN).add(getLog());
        provider.addTag(BlockTags.OVERWORLD_NATURAL_LOGS).add(getLog());
        provider.addTag(BlockTags.WOODEN_PRESSURE_PLATES).add(getPressurePlate());
        provider.addTag(BlockTags.WOODEN_TRAPDOORS).add(getTrapdoor());
        provider.addTag(BlockTags.STANDING_SIGNS).add(getSign());
        provider.addTag(BlockTags.WALL_SIGNS).add(getWallSign());
        provider.addTag(BlockTags.FENCE_GATES).add(getFenceGate());
    }

    @Override
    public void registerItemTags(ModItemTags provider) {
    }

    @Override
    public void registerLootTables(LootTablesGenerator generator) {
    }

    private static RotatedPillarBlock rotatingBlock(MaterialColor topColor, MaterialColor sideColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .strength(2.0F)
                .sound(SoundType.WOOD));
    }
}
