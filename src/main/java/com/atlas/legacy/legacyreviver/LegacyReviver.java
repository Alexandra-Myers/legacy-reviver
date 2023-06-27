package com.atlas.legacy.legacyreviver;

import com.atlas.legacy.legacyreviver.util.ArrayListExtensions;
import com.atlas.legacy.legacyreviver.world.gen.feature.NetherWartVegetationFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourceManager;
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
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (EntityType.BLAZE.getLootTableId().equals(id) && source.isBuiltin()) {
                LootPool.Builder builder = LootPool.builder()
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .with(ItemEntry.builder(Items.GLOWSTONE_DUST)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 2.0F), false))
                                .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F))));
                tableBuilder.pool(builder);
            }
        });
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
