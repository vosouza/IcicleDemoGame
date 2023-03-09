package com.mygdx.gamescreens.presentation.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gamescreens.presentation.data.Constants;

public class Icicles {
    public static final String TAG = Icicles.class.getName();
    private final Constants.Difficulty difficulty;

    // TODO: Use a DelayedRemovalArray to hold our icicles
    DelayedRemovalArray<Icicle> icicleList;
    Viewport viewport;

    public int iciclesDodged;

    public Icicles(Viewport viewport, Constants.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.viewport = viewport;
        init();
    }

    public void init() {
        // TODO: Initialize the DelayedRemovalArray
        icicleList = new DelayedRemovalArray<Icicle>(false, 100);
        iciclesDodged = 0;

    }

    public void update(float delta) {
        if (MathUtils.random() < delta * difficulty.spawnRate) {
            Vector2 newIciclePosition = new Vector2(
                    MathUtils.random() * viewport.getWorldWidth(),
                    viewport.getWorldHeight()
            );
            Icicle newIcicle = new Icicle(newIciclePosition);
            icicleList.add(newIcicle);
        }

        for (Icicle icicle : icicleList) {
            icicle.update(delta);
        }

        // TODO: begin a removal session
        icicleList.begin();

        // TODO: Remove any icicle completely off the bottom of the screen
        for (int i = 0; i < icicleList.size; i++) {
            if (icicleList.get(i).position.y < -Constants.ICICLES_HEIGHT) {
                iciclesDodged += 1;
                icicleList.removeIndex(i);
            }
        }
        // TODO: End removal session
        icicleList.end();
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.ICICLE_COLOR);
        for (Icicle icicle : icicleList) {
            icicle.render(renderer);
        }
    }
}
