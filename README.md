# Jumping Mushroom Game

## Introduction
Jumping Mushroom is a 2D platformer game created using the LibGDX game development framework. In this game, players control a mushroom character and navigate through various levels filled with platforms, enemies, coins, and spikes. The goal is to collect as many coins as possible while avoiding spikes to progress through the levels.

![1](https://github.com/UngureanuEduard/Jumping-Mushroom/assets/130817880/86976f0f-3681-487a-b41d-e1beadfbc5d5)
![2](https://github.com/UngureanuEduard/Jumping-Mushroom/assets/130817880/8761f4c5-5aa3-4a91-be7f-8fb92c612fd3)
![3](https://github.com/UngureanuEduard/Jumping-Mushroom/assets/130817880/607c5dc3-a139-458b-8b00-8ca35fc932f9)

## Getting Started
To play the Jumping Mushroom game, follow these steps:

1. **Clone the Repository:** Clone or download the game repository from the source where it is hosted.

2. **Set Up Development Environment:** Ensure you have Java Development Kit (JDK) and Gradle installed on your system.

3. **Build and Run:** Navigate to the project's root directory in your terminal and run the following command to build and run the game:

   ```
   ./gradlew desktop:run
   ```

4. **Game Controls:** Use the following controls to play the game:
   - **A Key:** Move the character left.
   - **D Key:** Move the character right.
   - **Spacebar:** Make the character jump.

5. **Objective:** Collect coins, avoid spikes, and reach the end of each level to progress through the game.

## Game Features
Jumping Mushroom offers the following features:

- Multiple Levels: The game includes multiple levels with varying challenges and backgrounds. Each level offers a unique experience for players.

- Collectible Coins: Players can collect coins scattered throughout the levels to increase their score.

- Health System: The player has a health system represented by heart icons. Losing all hearts results in a game over.

- Menu System: The game includes a menu system with options to start the game, access settings, and exit the game.

## Gameplay
- Start Menu: When the game launches, you will be presented with a start menu containing buttons for playing the game and accessing options.

- Level Progression: Progress through the game by collecting coins and reaching the next level's coordinate. Each level becomes progressively challenging.

- Game Over: If you lose all your lives (hearts), the game will return to the start menu, and your score will be reset.

## Customization
You can customize the game by modifying various assets and parameters:

- **Levels:** Create your own levels by editing the "levels.json" file and specifying platform positions, coin positions, enemy positions, and more.

- **Textures:** Replace the texture images for the character, platforms, enemies, coins, and backgrounds to change the game's appearance.

- **Music:** Customize the game's music by replacing or adding audio files in different levels.

## Dependencies
Jumping Mushroom is built using the LibGDX framework, which handles graphics, input, and audio. Additionally, the game uses JSON files to define level data.
