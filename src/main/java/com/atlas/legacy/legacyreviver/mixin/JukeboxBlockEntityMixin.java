package com.atlas.legacy.legacyreviver.mixin;

import com.atlas.legacy.legacyreviver.extensions.IJukebox;
import com.atlas.legacy.legacyreviver.item.ExtendedDiscItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin extends BlockEntity implements IJukebox {
    public JukeboxBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow
    public boolean isPlayingRecord() {
        return false;
    }

    @Shadow private long recordStartTick;
    @Shadow private long tickCount;
    @Shadow private boolean isPlaying;
    @Unique
    JukeboxBlockEntity blockEntity = JukeboxBlockEntity.class.cast(this);

    @Shadow protected abstract void stopPlaying();

    private boolean isSong1Finished = false;
    private boolean isSong2Finished = false;
    @Inject(method = "removeStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;stopPlaying()V"), cancellable = true)
    private void injectReset(int slot, int amount, CallbackInfoReturnable<ItemStack> cir){
        ((IJukebox) blockEntity).setSong1Finished(false);
        ((IJukebox) blockEntity).setSong2Finished(false);
        markDirty();
    }
    @Inject(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;stopPlaying()V"), cancellable = true)
    private void redirectCheck(World world, BlockPos pos, BlockState state, CallbackInfo ci){
        Item musicDisc = blockEntity.getStack().getItem();
        if(musicDisc instanceof ExtendedDiscItem && !isSong1Finished()) {
            ((IJukebox) blockEntity).setSong1Finished(true);
            markDirty();
        }
        if(musicDisc instanceof ExtendedDiscItem && ((IJukebox)blockEntity).isSong1Finished() && !((IJukebox)blockEntity).isSong2Finished()) {
            blockEntity.startPlaying();
            tickCount++;
            ci.cancel();
        }
    }
    @Inject(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "HEAD"))
    private void injectDog1(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if(isPlayingRecord() && blockEntity.getStack().getItem() instanceof ExtendedDiscItem extendedDiscItem && isSong1Finished() && isSongBFinished(extendedDiscItem)) {
            stopPlaying();
            ((IJukebox)blockEntity).setSong2Finished(true);
        }
    }
    @Inject(method = "readNbt", at = @At(value = "TAIL"))
    public void readCustomNBT(NbtCompound nbt, CallbackInfo ci) {
        isSong1Finished = nbt.getBoolean("song1Finished");
        isSong2Finished = nbt.getBoolean("song2Finished");
    }
    @Inject(method = "writeNbt", at = @At(value = "TAIL"))
    public void writeCustomNBT(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("song1Finished", isSong1Finished);
        nbt.putBoolean("song2Finished", isSong2Finished);
    }
    private boolean isSongBFinished(ExtendedDiscItem musicDisc) {
        return this.getTickCount() >= this.getRecordStartTick() + (long)musicDisc.soundBLengthTicks + 20L;
    }
    @Override
    public long getTickCount() {
        return tickCount;
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

    @Override
    public void setSong2Finished(boolean song2Finished) {
        isSong2Finished = song2Finished;
    }

    @Override
    public boolean isSong2Finished() {
        return isSong2Finished;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
