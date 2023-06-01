package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.extensions.IFlatChunkGenConfig;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlatChunkGeneratorConfig.class)
public class FlatChunkGeneratorConfigMixin implements IFlatChunkGenConfig {
    @Shadow
    @Final
    @Mutable
    private RegistryEntry<Biome> biome;

    @Override
    public FlatChunkGeneratorConfig setBiome(RegistryEntry<Biome> biome) {
        this.biome = biome;
        return (FlatChunkGeneratorConfig) (Object)this;
    }
}
