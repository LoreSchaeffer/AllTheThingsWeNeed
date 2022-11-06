package it.multicoredev.attwn.registries.blocks;

import it.multicoredev.attwn.registries.Registry;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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
public class Wood {
    private String doubleSlabTexture;
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

    private static RotatedPillarBlock rotatingBlock(MaterialColor topColor, MaterialColor sideColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .strength(2.0F)
                .sound(SoundType.WOOD));
    }


}
