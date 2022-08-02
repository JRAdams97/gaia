package com.jradams.gaia.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jradams.gaia.component.BodyComponent;
import com.jradams.gaia.component.HudNumberComponent;
import com.jradams.gaia.component.MaterialComponent;
import com.jradams.gaia.component.PositionComponent;

public class CollisionSystem extends IntervalIteratingSystem implements ContactListener {

    private final Engine engine;
    private final World world;
    private final Array<Body> disposibleBodies = new Array<>();

    private final ComponentMapper<BodyComponent> bodyCm;
    private final ComponentMapper<PositionComponent> positionCm;

    public CollisionSystem(Engine engine, World world) {
        super(Family.all(BodyComponent.class, PositionComponent.class).get(), 1 / 60f);

        this.engine = engine;
        this.world = world;
        world.setContactListener(this);

        this.bodyCm = ComponentMapper.getFor(BodyComponent.class);
        this.positionCm = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    protected void processEntity(Entity e) {
        BodyComponent body = bodyCm.get(e);
        PositionComponent position = positionCm.get(e);

        body.getBody().setTransform(position.getX() + 16, position.getY() + 16, 0);
    }

    @Override
    protected void updateInterval() {
        world.step(1 / 60f, 6, 2);

        for (Body b : disposibleBodies) {
            world.destroyBody(b);
        }

        disposibleBodies.clear();

        super.updateInterval();
    }

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Entity e) {
            handleCollision(e, b);
        } else if (b instanceof Entity e) {
            handleCollision(e, a);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // No processing required
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // No processing required
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // No processing required
    }

    private void handleCollision(Entity e, Object o) {
        if (o instanceof Entity oe) {
            ImmutableArray<Entity> hudEntities = engine.getEntitiesFor(Family.one(HudNumberComponent.class).get());

            if (oe.getComponent(MaterialComponent.class) != null) {
                for (Entity hudEntity : hudEntities) {
                    if ("Score".contentEquals(hudEntity.getComponent(HudNumberComponent.class).getLabel())) {
                        int currentScore = hudEntity.getComponent(HudNumberComponent.class).getValue();

                        hudEntity.getComponent(HudNumberComponent.class)
                                .setValue(currentScore + oe.getComponent(MaterialComponent.class).getScore());
                    }
                }

                disposibleBodies.add(oe.getComponent(BodyComponent.class).getBody());
                engine.removeEntity(oe);
            } else if (e.getComponent(MaterialComponent.class) != null) {
                for (Entity hudEntity : hudEntities) {
                    if ("Score".contentEquals(hudEntity.getComponent(HudNumberComponent.class).getLabel())) {
                        int currentScore = hudEntity.getComponent(HudNumberComponent.class).getValue();

                        hudEntity.getComponent(HudNumberComponent.class)
                                .setValue(currentScore + e.getComponent(MaterialComponent.class).getScore());
                    }
                }

                disposibleBodies.add(e.getComponent(BodyComponent.class).getBody());
                engine.removeEntity(e);
            }
        }
    }
}
