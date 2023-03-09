package com.mygdx.gamescreens.presentation.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gamescreens.presentation.data.Constants;

public class Player {

    public static final String TAG = Player.class.getName();

    // TODO: Add a position (add constants to Constants.java first)
    Vector2 position;

    // TODO: Add a viewport
    Viewport viewport;
    public int deaths;

    // TODO: Add constructor that accepts and sets the viewport, then calls init()
    public Player(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    // TODO: Add init() function that moves the character to the bottom center of the screen
    public void init() {
        position = new Vector2(viewport.getWorldWidth() / 2, Constants.PLAYER_HEAD_HEIGHT);
        deaths = 0;
    }

    // TODO: Create a render function that accepts a ShapeRenderer and does the actual drawing
    public void render(ShapeRenderer renderer) {


        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_HEAD_SEGMENTS);

        Vector2 torsoTop = new Vector2(position.x, position.y - Constants.PLAYER_HEAD_RADIUS);
        Vector2 torsoBottom = new Vector2(torsoTop.x, torsoTop.y - 2 * Constants.PLAYER_HEAD_RADIUS);

        renderer.rectLine(torsoTop, torsoBottom, Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x + Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x - Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x + Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x - Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
        }

        // TODO: Compute accelerometer input = raw input / (gravity * sensitivity)
        float accelerometerInput = -Gdx.input.getAccelerometerY() / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);

        // TODO: Use the accelerometer input to move the player
        position.x += -delta * accelerometerInput * Constants.PLAYER_MOVEMENT_SPEED;

        ensureInBounds();
    }

    private void ensureInBounds() {
        if (position.x - Constants.PLAYER_HEAD_RADIUS < 0) {
            position.x = Constants.PLAYER_HEAD_RADIUS;
        }
        if (position.x + Constants.PLAYER_HEAD_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS;
        }
    }

    public boolean hitByIcicle(Icicles icicles) {
        boolean isHit = false;

        for (Icicle icicle : icicles.icicleList) {
            if (icicle.position.dst(position) < Constants.PLAYER_HEAD_RADIUS) {
                deaths++;
                isHit = true;
            }
        }

        return isHit;
    }
}