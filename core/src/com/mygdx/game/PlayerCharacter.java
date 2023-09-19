package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class PlayerCharacter {
    private final TextureRegion[] animationFrames;
    private final Texture idleTexture;
    private final Vector2 position;
    private final Vector2 velocity;
    private final float speed;
    private final float jumpSpeed;
    private boolean isJumping;
    private float stateTime;
    private int currentFrame;
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private boolean flipIdle;

    public PlayerCharacter() {
        Texture spriteSheet = new Texture("mushroomwalk.png");
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight());

        animationFrames = new TextureRegion[4];
        animationFrames[0] = tmp[0][0];
        animationFrames[1] = tmp[0][1];
        animationFrames[2] = tmp[0][2];
        animationFrames[3] = tmp[0][3];

        idleTexture = new Texture("idle.png");

        position = new Vector2(1, 60);
        speed = 200;
        jumpSpeed = 200;
        velocity = new Vector2();
        isJumping = false;
        isMovingRight = false;
        isMovingLeft = false;
        flipIdle = false;
        stateTime = 0;
        currentFrame = 0;
    }

    public void update(float deltaTime, ArrayList<Platform> platforms) {
        // Reset movement flags
        isMovingRight = false;
        isMovingLeft = false;

        // Handle input and update character position here

        // Check if moving right (D key)
        if (MyGdxGame.isKeyPressedD()) {
            isMovingRight = true;
            position.x += speed * deltaTime;
        }



        // Check if moving left (A key)
        if (MyGdxGame.isKeyPressedA()) {
            isMovingLeft = true;
            position.x -= speed * deltaTime;
        }

        // Jump logic (Space key)
        if (MyGdxGame.isKeyPressedSpace() && !isJumping) {
            isJumping = true;
            velocity.y = jumpSpeed;
        }

        if (position.y <= 0) {
            // Teleport the character back to the desired position
            position.set(1, 60);
            isJumping = false;
            velocity.y = 0; // Reset jump velocity when on the ground
        }

        // Apply gravity to the jump velocity
        velocity.y -= 250 * deltaTime;

        // Update position based on velocity
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;

        // Check for collisions with platforms
        for (Platform platform : platforms) {
            if (isCollidingWithPlatform(platform)) {
                // Resolve collision
                if (velocity.y < 0) {
                    // Collided from above, stop falling and stay on top
                    position.y = platform.getBounds().y + platform.getBounds().height;
                    isJumping = false;
                    velocity.y = 0;
                } else if (velocity.y > 0) {
                    // Collided from below, stop jumping
                    position.y = platform.getBounds().y - getHeight();
                    velocity.y = 0;
                }
            }
        }

        // Check if the character has landed
        if (position.y <= 0) {
            position.y = 0;
            isJumping = false;
            velocity.y = 0; // Reset jump velocity when on the ground
        }

        // Update animation
        stateTime += deltaTime;
        currentFrame = (int) (stateTime / 0.2f) % animationFrames.length;

        // Determine if idle should be flipped
        if (isMovingLeft && !isMovingRight) {
            flipIdle = true;
        } else if (isMovingRight && !isMovingLeft) {
            flipIdle = false;
        }
    }

    private boolean isCollidingWithPlatform(Platform platform) {
        return getBounds().overlaps(platform.getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, getWidth(), getHeight());
    }

    public float getWidth() {
        return animationFrames[currentFrame].getRegionWidth();
    }

    public float getHeight() {
        return animationFrames[currentFrame].getRegionHeight();
    }

    public void render(SpriteBatch batch) {
        if (isMovingRight) {
            // Render the character normally when moving right
            batch.draw(animationFrames[currentFrame], position.x, position.y);
        } else if (isMovingLeft) {
            // Flip and render the character when moving left
            batch.draw(animationFrames[currentFrame], position.x + getWidth(), position.y,
                    -getWidth(), getHeight());
        } else {
            // Render the idle texture with or without flipping
            if (flipIdle) {
                batch.draw(idleTexture, position.x + idleTexture.getWidth(), position.y, -idleTexture.getWidth(), idleTexture.getHeight());
            } else {
                batch.draw(idleTexture, position.x, position.y);
            }
        }
    }

    public void dispose() {
        for (TextureRegion frame : animationFrames) {
            frame.getTexture().dispose();
        }
        idleTexture.dispose();
    }
}
