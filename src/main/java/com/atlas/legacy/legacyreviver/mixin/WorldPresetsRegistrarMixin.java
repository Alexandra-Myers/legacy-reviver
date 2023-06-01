package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.world.gen.chunk.NetherFlatChunkGenerator;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

import static net.minecraft.world.gen.WorldPresets.FLAT;

@Mixin(WorldPresets.Registrar.class)
public abstract class WorldPresetsRegistrarMixin {

    @Shadow @Final private DimensionOptions netherDimensionOptions;

    @Shadow @Final private RegistryEntryLookup<Biome> biomeLookup;

    @Shadow @Final private RegistryEntryLookup<StructureSet> structureSetLookup;

    @Shadow @Final private RegistryEntryLookup<PlacedFeature> featureLookup;

    @Shadow @Final private Registerable<WorldPreset> presetRegisterable;

    @Shadow @Final private DimensionOptions endDimensionOptions;

    private DimensionOptions createNetherOptions(ChunkGenerator chunkGenerator) {
        return new DimensionOptions(this.netherDimensionOptions.dimensionTypeEntry(), chunkGenerator);
    }
    private WorldPreset createPreset(DimensionOptions dimensionOptions, DimensionOptions netherDimensionOptions) {
        return new WorldPreset(Map.of(DimensionOptions.OVERWORLD, dimensionOptions, DimensionOptions.NETHER, netherDimensionOptions, DimensionOptions.END, this.endDimensionOptions));
    }

    private void register(RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions, DimensionOptions netherDimensionOptions) {
        this.presetRegisterable.register(key, this.createPreset(dimensionOptions, netherDimensionOptions));
    }
    @Redirect(method = "bootstrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/WorldPresets$Registrar;register(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/world/dimension/DimensionOptions;)V"))
    public void reRegister(WorldPresets.Registrar instance, RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions) {
        if(key == FLAT) {
            register(key, dimensionOptions, this.createNetherOptions(new NetherFlatChunkGenerator(FlatChunkGeneratorConfig.getDefaultConfig(this.biomeLookup, this.structureSetLookup, this.featureLookup), biomeLookup)));
        } else {
            register(key, dimensionOptions, netherDimensionOptions);
        }
    }
}
