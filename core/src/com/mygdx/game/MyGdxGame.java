package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private PlayerCharacter player;
	private ArrayList<Level> levels;
	private int currentLevel;
	private Texture backgroundTexture; // Declare backgroundTexture field

	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new PlayerCharacter();
		levels = new ArrayList<>();

		// Load your levels here (customize this part)
		levels.add(new Level("background1.png", new Vector2[]{new Vector2(1, 50), new Vector2(230, 70), new Vector2(110, 145),new Vector2(500, 50)}));
		levels.add(new Level("background2.png", new Vector2[]{new Vector2(1, 1), new Vector2(1, 1)}));

		currentLevel = 0; // Start with the first level
		loadLevel(currentLevel);
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		// Handle user input and update the character
		player.update(deltaTime, levels.get(currentLevel).getPlatforms());

		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		// Get the screen dimensions
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		// Scale and render the background image to fit the screen
		batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);

		// Render platforms
		for (Platform platform : levels.get(currentLevel).getPlatforms()) {
			platform.render(batch);
		}

		// Render spikes
		for (Platform spike : levels.get(currentLevel).getSpikes()) {
			spike.render(batch);
		}

		// Render the character
		player.render(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		player.dispose();
		backgroundTexture.dispose(); // Dispose of the backgroundTexture
	}

	public void loadLevel(int levelIndex) {
		if (levelIndex >= 0 && levelIndex < levels.size()) {
			Level level = levels.get(levelIndex);
			backgroundTexture = new Texture(level.getBackgroundPath());
		}
	}

	// Helper methods to check key presses

	public static boolean isKeyPressedA() {
		return Gdx.input.isKeyPressed(Input.Keys.A);
	}

	public static boolean isKeyPressedD() {
		return Gdx.input.isKeyPressed(Input.Keys.D);
	}

	public static boolean isKeyPressedSpace() {
		return Gdx.input.isKeyPressed(Input.Keys.SPACE);
	}
}

