package com.mygdx.gamescreens.presentation.screens.iciclegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamescreens.presentation.MainGame;
import com.mygdx.gamescreens.presentation.data.Constants;
import com.mygdx.gamescreens.presentation.model.Icicles;
import com.mygdx.gamescreens.presentation.model.Player;

public class IcicleGameScreen extends ScreenAdapter {


    private Constants.Difficulty difficulty;
    private MainGame game;
    // TODO: Add an ExtendViewport
    ExtendViewport iciclesViewport;

    // TODO: Add a ShapeRenderer
    ShapeRenderer renderer;

    // TODO: Add an Icicle
    Icicles icicles;
    Player player;

    ScreenViewport hudViewport;
    // TODO: Add SpriteBatch
    SpriteBatch batch;
    // TODO: Add BitmapFont
    BitmapFont font;

    int topScore;

    public IcicleGameScreen(MainGame game, Constants.Difficulty difficulty) {
        // TODO: Save the IciclesGame
        this.game = game;
        this.difficulty = difficulty;
    }

    @Override
    public void show() {
        // TODO: Initialize the viewport using the world size constant
        iciclesViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // TODO: Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        // TODO: Initialize the SpriteBatch
        batch = new SpriteBatch();

        // TODO: Initialize the BitmapFont
        font = new BitmapFont();

        // TODO: Give the font a linear TextureFilter
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        topScore = 0;

        // TODO: Initialize the ShapeRenderer
        renderer = new ShapeRenderer();

        // TODO: Set autoShapeType(true) on the ShapeRenderer
        renderer.setAutoShapeType(true);

        // TODO: Create a new Icicle in the middle of the world
        icicles = new Icicles(iciclesViewport, difficulty);
        player = new Player(iciclesViewport);

    }

    @Override
    public void resize(int width, int height) {
        // TODO: Ensure that the viewport updates correctly
        iciclesViewport.update(width, height, true);
        // TODO: Update HUD viewport
        hudViewport.update(width, height, true);

        // TODO: Set font scale to min(width, height) / reference screen size
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
        player.init();
        icicles.init();
    }

    @Override
    public void render(float delta) {
        icicles.update(delta);
        player.update(delta);
        if (player.hitByIcicle(icicles)) {
            icicles.init();
        }


        iciclesViewport.apply(true);
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        icicles.render(renderer);
        player.render(renderer);
        renderer.end();

        // TODO: Set the top score to max(topScore, iciclesDodges)
        topScore = Math.max(topScore, icicles.iciclesDodged);

        // TODO: Apply the HUD viewport
        hudViewport.apply();

        // TODO: Set the SpriteBatch's projection matrix
        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        // TODO: Begin the SpriteBatch
        batch.begin();

        // TODO: Draw the number of player deaths in the top left

        font.draw(batch, "Deaths: " + player.deaths + "\nDifficulty: " + difficulty.label,
                Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN);

        // TODO: Draw the score and top score in the top right
        font.draw(batch, "Score: " + icicles.iciclesDodged + "\nTop Score: " + topScore,
                hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        // TODO: End the SpriteBatch
        batch.end();
    }

    @Override
    public void hide() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
