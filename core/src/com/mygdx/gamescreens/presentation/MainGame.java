package com.mygdx.gamescreens.presentation;

import com.badlogic.gdx.Game;
import com.mygdx.gamescreens.presentation.data.Constants;
import com.mygdx.gamescreens.presentation.screens.dificulty.DifficultyScreen;
import com.mygdx.gamescreens.presentation.screens.iciclegame.IcicleGameScreen;

public class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new DifficultyScreen(this));
	}
	public void showIciclesScreen(Constants.Difficulty difficulty) {
		// TODO: Show the Icicles screen with the appropriate difficulty
		setScreen(new IcicleGameScreen(this, difficulty));
	}
}
