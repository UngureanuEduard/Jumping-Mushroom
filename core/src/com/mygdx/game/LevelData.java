package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class LevelData {
    private String backgroundPath;
    private Vector2[] platformPositions;
    private Vector2 nextLevelCoordinate;
    private String musicPath;

    // Getter methods for the fields
    public String getBackgroundPath() {
        return backgroundPath;
    }

    public Vector2[] getPlatformPositions() {
        return platformPositions;
    }

    public Vector2 getNextLevelCoordinate() {
        return nextLevelCoordinate;
    }

    public String getMusicPath() {
        return musicPath;
    }
}