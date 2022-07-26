package com.jradams.gaia.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.jradams.gaia.component.MovementComponent;
import com.jradams.gaia.component.PositionComponent;

import static com.jradams.gaia.screen.MainScreen.ENV_HEIGHT;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> movementCm;
    private final ComponentMapper<PositionComponent> positionCm;

    public MovementSystem() {
        super(Family.all(PositionComponent.class, MovementComponent.class).get());

        this.movementCm = ComponentMapper.getFor(MovementComponent.class);
        this.positionCm = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public void processEntity(Entity e, float dt) {
        MovementComponent movement = movementCm.get(e);
        PositionComponent position = positionCm.get(e);

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            position.setX(position.getX() + movement.getSpeed());

            if (position.getX() > 1920) {
                position.setX(0);
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            position.setX(position.getX() - movement.getSpeed());

            if (position.getX() < 0) {
                position.setX(1888);
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && position.getY() < (ENV_HEIGHT * 32)) {
            position.setY(position.getY() + movement.getSpeed());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && position.getY() > 0) {
            position.setY(position.getY() - movement.getSpeed());
        }
    }
}
