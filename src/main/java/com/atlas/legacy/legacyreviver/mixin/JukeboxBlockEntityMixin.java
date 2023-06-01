package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.extensions.IJukebox;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin implements IJukebox {
    @Shadow public abstract void setRecord(ItemStack stack);

    @Shadow
    private static boolean isPlayingRecord(BlockState state, JukeboxBlockEntity blockEntity) {
        return false;
    }

    @Shadow private long recordStartTick;
    @Shadow private long tickCount;
    @Shadow private boolean isPlaying;

    @Shadow
    private static boolean isSongFinished(JukeboxBlockEntity blockEntity, MusicDiscItem musicDisc) {
        return false;
    }

    private boolean isSong1Finished = false;
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;isSongFinished(Lnet/minecraft/block/entity/JukeboxBlockEntity;Lnet/minecraft/item/MusicDiscItem;)Z"))
    private static boolean redirectCheck(JukeboxBlockEntity blockEntity, MusicDiscItem musicDisc){
        if(musicDisc instanceof ExtendedDiscItem && ((IJukebox)blockEntity).isSong1Finished()) {
            return false;
        }
        return isSongFinished(blockEntity, musicDisc);
    }
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V", ordinal = 0), cancellable = true)
    private static void injectDog(World world, BlockPos pos, BlockState state, JukeboxBlockEntity blockEntity, CallbackInfo ci) {
        ((IJukebox)blockEntity).setSong1Finished(true);
        if(blockEntity.getRecord().getItem() instanceof ExtendedDiscItem) {
            world.emitGameEvent(GameEvent.JUKEBOX_STOP_PLAY, pos, GameEvent.Emitter.of(state));
            ((IJukebox)blockEntity).setIsPlaying(false);
            blockEntity.startPlaying();
            world.syncWorldEvent(null, WorldEvents.MUSIC_DISC_PLAYED, pos, Item.getRawId(blockEntity.getRecord().getItem()));
            ((IJukebox) blockEntity).incrementTickCount();
            ci.cancel();
        }
    }
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private static void injectDog1(World world, BlockPos pos, BlockState state, JukeboxBlockEntity blockEntity, CallbackInfo ci) {
        if(isPlayingRecord(state, blockEntity) && blockEntity.getRecord().getItem() instanceof ExtendedDiscItem extendedDiscItem && isSongBFinished(blockEntity, extendedDiscItem)) {
            world.emitGameEvent(GameEvent.JUKEBOX_STOP_PLAY, pos, GameEvent.Emitter.of(state));
            ((IJukebox)blockEntity).setIsPlaying(false);
            ((IJukebox)blockEntity).setSong1Finished(false);
        }
    }
    private static boolean isSongBFinished(JukeboxBlockEntity blockEntity, ExtendedDiscItem musicDisc) {
        return ((IJukebox)blockEntity).getTickCount() >= ((IJukebox)blockEntity).getRecordStartTick() + (long)musicDisc.soundBLengthTicks;
    }
    @Override
    public long getTickCount() {
        return tickCount;
    }
    @Override
    public void incrementTickCount() {
        tickCount++;
    }
    @Override
    public long getRecordStartTick() {
        return recordStartTick;
    }
    @Override
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public void setSong1Finished(boolean song1Finished) {
        isSong1Finished = song1Finished;
    }

    @Override
    public boolean isSong1Finished() {
        return isSong1Finished;
    }
}
