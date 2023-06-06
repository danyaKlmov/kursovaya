package com.klimov_d.dungeon_hero;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.klimov_d.dungeon_hero.DungeonHeroGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	static final int SCREEN_HEIGHT = 900;
	static final int SCREEN_WIDTH = 1200;
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Dungeon hero");
		config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
		new Lwjgl3Application(new DungeonHeroGame(SCREEN_HEIGHT, SCREEN_WIDTH), config);
	}
}
