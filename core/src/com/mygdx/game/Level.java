package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {
    private final ArrayList<Platform> platforms;

    private final ArrayList<Enemy> enemies;

    private final ArrayList<Coin> coins;
    private final String backgroundPath;

    private final ArrayList<Platform> spikes;

    private final Vector2 nextLevelCoordinate;

    private final Music levelMusic;


    public Level(String backgroundPath, Vector2[] platformPositions, Vector2 nextLevelCoordinate,String musicPath,Vector2[] coinPositions, Vector2[] enemyStartPositions, Vector2[] enemyEndPositions) {
        this.platforms = new ArrayList<>();
        this.spikes = new ArrayList<>();
        this.backgroundPath = backgroundPath;
        this.nextLevelCoordinate = nextLevelCoordinate;
        this.coins=new ArrayList<>();
        this.enemies=new ArrayList<>();


        // Load the music for this level
        levelMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));

        for (Vector2 position : platformPositions) {
            platforms.add(new Platform(position.x, position.y, "Background/platform.png"));
        }

        for (Vector2 position : enemyStartPositions) {
            int index = Arrays.asList(enemyStartPositions).indexOf(position);
            Vector2 endPosition = enemyEndPositions[index];
            enemies.add(new Enemy(position.x, position.y, endPosition.x, endPosition.y, "Character/DuckWalk.png"));
        }


        for (Vector2 position : coinPositions) {
            coins.add(new Coin(position.x, position.y, "Background/coin.png"));
        }

        for (int x = 0; x < 700; x += 60) {
            spikes.add(new Platform(x, 0, "Background/spike.png"));
        }
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public ArrayList<Platform> getSpikes() {
        return spikes;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public Vector2 getNextLevelCoordinate() {
        return nextLevelCoordinate;
    }


    public Music getLevelMusic() {
        return levelMusic;
    }



}
