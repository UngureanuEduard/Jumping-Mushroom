package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {

    private final Vector2 position;
    private final TextureRegion[] coinFrames;
    private float stateTime;
    private final Texture texture;
    private TextureRegion currentFrame;

    public Coin(float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        this.texture = new Texture(texturePath);

        TextureRegion[][] tmp = TextureRegion.split(texture, 16, 16);
        coinFrames = new TextureRegion[tmp[0].length];

        for (int i = 0; i < tmp[0].length; i++) {
            coinFrames[i] = tmp[0][i];
        }
    }
    public void update(float deltaTime) {
        stateTime += deltaTime;

        // Calculate the current frame index based on time and the number of frames
        int frameIndex = (int) (stateTime / 0.1) % coinFrames.length;
        currentFrame = coinFrames[frameIndex];
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, 16, 16);
    }
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, position.x, position.y);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }


}
