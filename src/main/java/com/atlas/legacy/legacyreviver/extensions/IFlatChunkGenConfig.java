package com.atlas.legacy.legacyreviver.extensions;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;

public interface IFlatChunkGenConfig {
    FlatChunkGeneratorConfig setBiome(RegistryEntry<Biome> biome);
}
