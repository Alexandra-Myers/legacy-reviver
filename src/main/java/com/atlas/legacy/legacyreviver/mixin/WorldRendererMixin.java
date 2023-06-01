package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.extensions.IJukebox;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow public abstract void playSong(@Nullable SoundEvent song, BlockPos songPosition);

    @Shadow private @Nullable ClientWorld world;
    private boolean secondDisc = false;

    @Inject(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;playSong(Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/util/math/BlockPos;)V", ordinal = 0), cancellable = true)
    public void injectDog(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        SoundEvent sound;
        if(world.getBlockEntity(pos) instanceof IJukebox jukebox && jukebox.isSong1Finished() && Item.byRawId(data) instanceof ExtendedDiscItem discItem) {
            secondDisc = true;
            sound = discItem.followingSound;
        } else {
            secondDisc = false;
            sound = ((MusicDiscItem) Item.byRawId(data)).getSound();
        }
        this.playSong(sound, pos);
        ci.cancel();
    }
    @Redirect(method = "playSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/MusicDiscItem;getDescription()Lnet/minecraft/text/MutableText;"))
    public MutableText getDesc(MusicDiscItem instance) {
        if(instance instanceof ExtendedDiscItem discItem && secondDisc)
            return discItem.getSoundBDescription();
        return instance.getDescription();
    }
}
