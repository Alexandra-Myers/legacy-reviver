package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.LegacyReviver;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import com.mojang.serialization.Lifecycle;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Shadow
    @Final
    @Mutable
    public static Item MUSIC_DISC_CAT = registrySet(1123, "music_disc_cat", new ExtendedDiscItem(2,
            SoundEvents.MUSIC_DISC_CAT,
            LegacyReviver.MUSIC_DISC_DOG,
            new Item.Settings().maxCount(1).rarity(Rarity.RARE),
            185,
            145));
    private static Item registrySet(int rawId, String id, Item attribute) {
        return ((MutableRegistry<Item>)Registries.ITEM).set(rawId, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(id)), attribute, Lifecycle.stable()).value();
    }
}
