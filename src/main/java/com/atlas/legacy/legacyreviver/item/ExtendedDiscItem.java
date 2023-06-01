package com.atlas.legacy.legacyreviver.item;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ExtendedDiscItem extends MusicDiscItem {
    public final SoundEvent followingSound;
    public final int soundBLengthTicks;
    public ExtendedDiscItem(int comparatorOutput, SoundEvent sound, SoundEvent followingSound, Settings settings, int lengthInSeconds, int soundBLengthSeconds) {
        super(comparatorOutput, sound, settings, lengthInSeconds);
        this.followingSound = followingSound;
        soundBLengthTicks = soundBLengthSeconds * 20;
    }
    public MutableText getSoundBDescription() {
        return Text.translatable(this.getTranslationKey() + ".desc.b");
    }

}
