package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {
    private final ArrayList<Platform> platforms;
    private final String backgroundPath;

    private final ArrayList<Platform> spikes;

    public Level(String backgroundPath, Vector2[] platformPositions) {
        this.platforms = new ArrayList<>();
        this.spikes = new ArrayList<>();
        this.backgroundPath = backgroundPath;

        for (Vector2 position : platformPositions) {
            platforms.add(new Platform(position.x, position.y, "platform.png"));
        }

        for (int x = 0; x < 700; x += 60) {
            spikes.add(new Platform(x, 0, "spike.png"));
        }
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Platform> getSpikes() {
        return spikes;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

}
