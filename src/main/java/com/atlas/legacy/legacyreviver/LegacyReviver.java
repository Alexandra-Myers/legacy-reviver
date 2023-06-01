package com.atlas.legacy.legacyreviver;

import com.atlas.legacy.legacyreviver.util.ArrayListExtensions;
import com.atlas.legacy.legacyreviver.world.gen.feature.NetherWartVegetationFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.NetherForestVegetationFeatureConfig;

public class LegacyReviver implements ModInitializer {
    public static final Feature<NetherForestVegetationFeatureConfig> NETHER_WART_VEGETATION = register("nether_wart", new NetherWartVegetationFeature(NetherForestVegetationFeatureConfig.VEGETATION_CODEC));
    public static final SoundEvent MUSIC_DISC_DOG = SoundEvents.register(new Identifier("legacy-reviver", "music_disc.dog"));
    @Override
    public void onInitialize() {
        ArrayListExtensions<RegistryKey<Biome>> netherBiomesExcludingSoulSand = new ArrayListExtensions<>();
        netherBiomesExcludingSoulSand.addAll(BiomeKeys.BASALT_DELTAS, BiomeKeys.CRIMSON_FOREST, BiomeKeys.NETHER_WASTES);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(netherBiomesExcludingSoulSand),
                SpawnGroup.MONSTER,
                EntityType.WITHER_SKELETON,
                8,
                4,
                4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                SpawnGroup.MONSTER,
                EntityType.WITHER_SKELETON,
                10,
                6,
                6);
        netherBiomesExcludingSoulSand.add(BiomeKeys.WARPED_FOREST);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(netherBiomesExcludingSoulSand),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("legacy-reviver", "nether_wart_vegetation")));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("legacy-reviver", "nether_wart_vegetation_soul")));

    }
    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, name, feature);
    }
}
