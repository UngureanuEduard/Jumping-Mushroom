package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private PlayerCharacter player;
	private ArrayList<Level> levels;
	private int currentLevel;
	private Texture backgroundTexture;

	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new PlayerCharacter();
		levels = new ArrayList<>();

		// Load levels from the JSON file
		Json json = new Json();
		LevelData[] levelDataArray = json.fromJson(LevelData[].class, Gdx.files.internal("levels.json"));

		for (LevelData levelData : levelDataArray) {
			levels.add(new Level(
					levelData.getBackgroundPath(),
					levelData.getPlatformPositions(),
					levelData.getNextLevelCoordinate(),
					levelData.getMusicPath()
			));
		}

		currentLevel = 0; // Start with the first level
		loadLevel(currentLevel);

		// Play song for the first level
		if (currentLevel == 0) {
			Level currentLevelObject = levels.get(currentLevel);
			currentLevelObject.getLevelMusic().play();
		}
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		// Handle user input and update the character
		player.update(deltaTime, levels.get(currentLevel).getPlatforms());

		// Check if the character's x-coordinate has reached the nextLevelCoordinate of the current level
		if (player.getPosition().x >= levels.get(currentLevel).getNextLevelCoordinate().x) {
			// Increment the currentLevel and load the next level
			currentLevel++;
			if (currentLevel < levels.size()) {
				Level currentLevelObject = levels.get(currentLevel);
				loadLevel(currentLevel);
				currentLevelObject.getLevelMusic().play();
				// Reset the character's position or perform other level transition logic if needed
				player.setPosition(1, 60);
			} else {
				// Handle the case where there are no more levels
				Gdx.app.exit();
				return;
			}
		}

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

