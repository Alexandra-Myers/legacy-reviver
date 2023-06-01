package com.atlas.legacy.legacyreviver.extensions;

public interface IJukebox {
    long getTickCount();

    void incrementTickCount();

    long getRecordStartTick();

    void setIsPlaying(boolean isPlaying);

    void setSong1Finished(boolean song1Finished);

    boolean isSong1Finished();
}
