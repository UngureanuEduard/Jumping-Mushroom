package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Vector2 startPosition;
    private final Vector2 endPosition;
    private final float speed;
    private final Vector2 position;
    private final Texture texture;
    private boolean movingToEnd = true;

    public Enemy(float startX, float startY, float endX, float endY, String texturePath) {
        this.startPosition = new Vector2(startX, startY);
        this.endPosition = new Vector2(endX, endY);
        this.speed = 100f;
        this.position = startPosition;
        this.texture = new Texture(texturePath);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float deltaTime) {
        float moveAmount = speed * deltaTime;

        if (movingToEnd) {
            position.x += moveAmount;
            if (position.x >= endPosition.x) {
                position.x = endPosition.x;
                movingToEnd = false;
            }
        } else {
            position.x -= moveAmount;
            if (position.x <= startPosition.x) {
                position.x = startPosition.x;
                movingToEnd = true;
            }
        }
    }

    public void render(SpriteBatch batch, float deltaTime) {
        // Move the enemy based on velocity
        position.x += 100f * deltaTime;

        // Draw the enemy
        batch.draw(texture, position.x, position.y);
    }
}
