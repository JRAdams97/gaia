package com.jradams.gaia.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jradams.gaia.component.HudNumberComponent;
import com.jradams.gaia.component.PositionComponent;

import java.text.MessageFormat;

public class HudRenderSystem extends IteratingSystem {

    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    private final ComponentMapper<HudNumberComponent> hudNumberCm;
    private final ComponentMapper<PositionComponent> positionCm;

    public HudRenderSystem(OrthographicCamera cam) {
        super(Family.all(HudNumberComponent.class, PositionComponent.class).get());

        this.batch = new SpriteBatch();
        this.cam = cam;

        this.hudNumberCm = ComponentMapper.getFor(HudNumberComponent.class);
        this.positionCm = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public void processEntity(Entity e, float dt) {
        HudNumberComponent hudField = hudNumberCm.get(e);
        PositionComponent position = positionCm.get(e);

        cam.update();

        batch.begin();
        hudField.getFont().draw(batch, MessageFormat.format("{0}: {1}", hudField.getLabel(), hudField.getValue()),
                position.getX(), position.getY());
        batch.end();
    }
}
