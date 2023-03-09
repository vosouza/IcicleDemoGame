package com.mygdx.gamescreens.presentation.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gamescreens.presentation.data.Constants;

public class Icicle {

    public static final String TAG = Icicle.class.getName();

    Vector2 position;
    Vector2 velocity;

    public Icicle(Vector2 position) {
        this.position = position;
        this.velocity = new Vector2();
    }

    public void update(float delta) {
        velocity.mulAdd(Constants.ICICLES_ACCELERATION, delta);
        position.mulAdd(velocity, delta);
    }
    public void render(ShapeRenderer renderer) {

        // TODO: Set the ShapeRenderer's color
        renderer.setColor(Constants.ICICLE_COLOR);

        // TODO: Set the ShapeType
        renderer.set(ShapeRenderer.ShapeType.Filled);

        // TODO: Draw the icicle using the size constants
        renderer.triangle(
                position.x, position.y,
                position.x - Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT,
                position.x + Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT
        );
    }
}
