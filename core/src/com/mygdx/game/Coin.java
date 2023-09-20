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
    private TextureRegion currentFrame;
    private final Vector2 initialPosition;

    public Coin(float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        Texture texture = new Texture(texturePath);
        this.initialPosition = new Vector2(x, y);
        TextureRegion[][] tmp = TextureRegion.split(texture, 16, 16);
        coinFrames = new TextureRegion[tmp[0].length];

        System.arraycopy(tmp[0], 0, coinFrames, 0, tmp[0].length);
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
    public void resetPosition() {
        position.set(initialPosition);
    }

}
