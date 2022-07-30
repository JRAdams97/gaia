package com.jradams.gaia.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jradams.gaia.component.BodyComponent;

public class PhysicsDebugRenderSystem extends IteratingSystem {

    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final OrthographicCamera cam;

    private boolean isEnabled = false;

    public PhysicsDebugRenderSystem(World world, OrthographicCamera cam) {
        super(Family.all(BodyComponent.class).get());

        this.debugRenderer = new Box2DDebugRenderer();
        this.world = world;
        this.cam = cam;
    }

    @Override
    public void update(float dt) {
        boolean dKeyJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.D);

        if (dKeyJustPressed) {
            isEnabled = !isEnabled;
        }

        if (cam != null && isEnabled) {
            debugRenderer.render(world, cam.combined);
        }
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        // No processing required
    }
}
