package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private PlayerCharacter player;
	private ArrayList<Level> levels;
	private int currentLevel;
	private Texture backgroundTexture;
	private Stage stage;

	private boolean inMenu = true; // Indicates if the game is in the menu


	@Override
	public void create() {
		batch = new SpriteBatch();
		player = new PlayerCharacter();
		levels = new ArrayList<>();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage); // Set the stage as the input processor

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

		// Play song for the first level / menu
		if (currentLevel == 0) {
			Level currentLevelObject = levels.get(currentLevel);
			currentLevelObject.getLevelMusic().play();
		}

		// Load button images for the menu
		Texture replayTexture = new Texture("UI/PlayButton.png");
		Texture optionTexture = new Texture("UI/OptionsButton.png");
		Texture exitTexture = new Texture("UI/ExitButton.png");
		Texture boxTexture = new Texture("UI/Box.png");
		Texture bannerTexture = new Texture("UI/Banner.png");

		// Create button drawables
		Drawable replayDrawable = new SpriteDrawable(new Sprite(replayTexture));
		Drawable OptionDrawable = new SpriteDrawable(new Sprite(optionTexture));
		Drawable exitDrawable = new SpriteDrawable(new Sprite(exitTexture));
		Drawable boxDrawable = new SpriteDrawable(new Sprite(boxTexture));
		Drawable bannerDrawable = new SpriteDrawable(new Sprite(bannerTexture));

		// Create Replay button
		ImageButton replayButton = new ImageButton(replayDrawable);
		ImageButton optionButton = new ImageButton(OptionDrawable);
		ImageButton exitButton = new ImageButton(exitDrawable);
		Image boxImage = new Image(boxDrawable);
		Image bannerImage = new Image(bannerDrawable);

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		float centerX = (screenWidth - 250) / 2;
		float centerY = (screenHeight - 80) / 2;

		replayButton.setPosition(centerX, centerY + 50);
		optionButton.setPosition(centerX, centerY - 33);
		exitButton.setPosition(centerX, centerY - 117);
		boxImage.setPosition(centerX-75, centerY-150);
		bannerImage.setPosition(centerX-5, centerY+158);


		// Add buttons to the stage
		stage.addActor(boxImage);
		stage.addActor(replayButton);
		stage.addActor(optionButton);
		stage.addActor(exitButton);
		stage.addActor(bannerImage);


		// Add click listeners to buttons
		replayButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				// Start the game with the first level
				inMenu = false;
				loadLevel(0);
			}
		});

		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
				// Exit the game
				Gdx.app.exit();
			}
		});

		// Set up the background texture
		backgroundTexture = new Texture("UI/background.jpg");
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (inMenu) {
			// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.begin();
			batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();

			// Draw the menu buttons
			stage.act();
			stage.draw();
		} else {
			// Handle user input and update the character
			player.update(deltaTime, levels.get(currentLevel).getPlatforms());

			// Check if the character's x-coordinate has reached the nextLevelCoordinate of the current level
			if (player.getPosition().x >= levels.get(currentLevel).getNextLevelCoordinate().x) {
				// Increment the currentLevel and load the next level
				currentLevel++;
				if (currentLevel < levels.size()) {
					levels.get(currentLevel - 1).getLevelMusic().stop();
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
	}

	@Override
	public void dispose() {
		batch.dispose();
		player.dispose();
		backgroundTexture.dispose();
		stage.dispose();
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
