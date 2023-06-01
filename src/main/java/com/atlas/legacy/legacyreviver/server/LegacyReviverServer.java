package com.atlas.legacy.legacyreviver.server;

import com.atlas.legacy.legacyreviver.util.ArrayListExtensions;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

@Environment(EnvType.SERVER)
public class LegacyReviverServer implements DedicatedServerModInitializer {
    /**
     * Runs the mod initializer on the server environment.
     */
    @Override
    public void onInitializeServer() {
    }
}
