package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.LegacyReviver;
import com.atlas.legacy.legacyreviver.extensions.IItem;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import com.mojang.logging.LogUtils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Shadow
    private static Item register(String id, Item item) {
        return null;
    }
    @Shadow
    @Final
    @Mutable
    public static Item MUSIC_DISC_CAT = registrySet(1093, "music_disc_cat", new ExtendedDiscItem(2,
            SoundEvents.MUSIC_DISC_CAT,
            LegacyReviver.MUSIC_DISC_DOG,
            new Item.Settings().maxCount(1).rarity(Rarity.RARE),
            185,
            145));
    private static Item registrySet(int RawId, String id, Item attribute) {
        return Registry.register(Registries.ITEM, RawId,id, attribute);
    }
}
