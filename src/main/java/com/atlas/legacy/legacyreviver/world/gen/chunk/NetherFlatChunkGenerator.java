/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package com.atlas.legacy.legacyreviver.world.gen.chunk;

import com.atlas.legacy.legacyreviver.extensions.IFlatChunkGenConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NetherFlatChunkGenerator
extends FlatChunkGenerator {

    public NetherFlatChunkGenerator(FlatChunkGeneratorConfig config, RegistryEntryLookup<Biome> biomeRegistryEntryLookup) {
        super(((IFlatChunkGenConfig)config).setBiome(biomeRegistryEntryLookup.getOrThrow(BiomeKeys.CRIMSON_FOREST)));
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Heightmap heightmap = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        for (int i = 0; i < 4; ++i) {
            BlockState blockState = i == 0 ? Blocks.BEDROCK.getDefaultState() : i == 3 ? Blocks.CRIMSON_NYLIUM.getDefaultState() : Blocks.NETHERRACK.getDefaultState();
            int j = chunk.getBottomY() + i;
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    chunk.setBlockState(mutable.set(k, j, l), blockState, false);
                    heightmap.trackUpdate(k, j, l, blockState);
                    heightmap2.trackUpdate(k, j, l, blockState);
                }
            }
        }
        BlockState blockState = Blocks.BEDROCK.getDefaultState();
        int j = chunk.getBottomY() + 128;
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                chunk.setBlockState(mutable.set(k, j, l), blockState, false);
                heightmap.trackUpdate(k, j, l, blockState);
                heightmap2.trackUpdate(k, j, l, blockState);
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }
}

