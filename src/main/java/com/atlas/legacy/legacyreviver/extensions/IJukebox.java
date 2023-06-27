package com.atlas.legacy.legacyreviver.extensions;

public interface IJukebox {
    long getTickCount();

    long getRecordStartTick();

    void setIsPlaying(boolean isPlaying);

    void setSong1Finished(boolean song1Finished);

    boolean isSong1Finished();

    void setSong2Finished(boolean song2Finished);

    boolean isSong2Finished();
}
