package com.atlas.legacy.legacyreviver.item;

import com.google.common.collect.Maps;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Map;

public class ExtendedDiscItem extends MusicDiscItem {
    public final SoundEvent followingSound;
    public final int soundBLengthTicks;
    private static final Map<SoundEvent, ExtendedDiscItem> EXTENDED_DISCS = Maps.newHashMap();
    public ExtendedDiscItem(int comparatorOutput, SoundEvent sound, SoundEvent followingSound, Settings settings, int lengthInSeconds, int soundBLengthSeconds) {
        super(comparatorOutput, sound, settings, lengthInSeconds);
        this.followingSound = followingSound;
        soundBLengthTicks = soundBLengthSeconds * 20;
        EXTENDED_DISCS.put(sound, this);
    }
    public MutableText getSoundBDescription() {
        return Text.translatable(this.getTranslationKey() + ".desc.b");
    }
    public static MusicDiscItem bySound(SoundEvent sound, boolean discB) {
        if (discB) {
            return EXTENDED_DISCS.get(sound);
        }
        return bySound(sound);
    }

    @Override
    public MutableText getDescription() {
        return super.getDescription().append(getSoundBDescription());
    }
}
