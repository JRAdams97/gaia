package com.jradams.gaia.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jradams.gaia.component.PositionComponent;
import com.jradams.gaia.component.TextureComponent;

public class RenderSystem extends IteratingSystem {

    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    private final ComponentMapper<PositionComponent> positionCm;
    private final ComponentMapper<TextureComponent> textureCm;

    public RenderSystem(OrthographicCamera cam) {
        super(Family.all(PositionComponent.class, TextureComponent.class).get());

        this.cam = cam;
        this.batch = new SpriteBatch();
        this.positionCm = ComponentMapper.getFor(PositionComponent.class);
        this.textureCm = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void processEntity(Entity e, float dt) {
        PositionComponent position = positionCm.get(e);
        TextureComponent texture = textureCm.get(e);

        cam.update();

        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        batch.draw(texture.getRegion(), position.getX(), position.getY());
        batch.end();
    }

}
