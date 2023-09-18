package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.LegacyReviver;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Redirect(
        method = "<clinit>",
        at = @At(value = "NEW", target = "(ILnet/minecraft/sound/SoundEvent;Lnet/minecraft/item/Item$Settings;I)Lnet/minecraft/item/MusicDiscItem;"),
        slice = @Slice(
            from = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;MUSIC_DISC_13:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC),
            to = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;MUSIC_DISC_CAT:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC)
        ),
        require = 1
    )
    private static MusicDiscItem replaceMusicDiscCatItem(int comparatorOutput, SoundEvent sound, Item.Settings settings, int lengthInSeconds) {
        return new ExtendedDiscItem(comparatorOutput, sound, LegacyReviver.MUSIC_DISC_DOG, settings, lengthInSeconds, 145);
    }
}
