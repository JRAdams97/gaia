package com.jradams.gaia.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jradams.gaia.component.MaterialComponent;
import com.jradams.gaia.component.PositionComponent;
import com.jradams.gaia.component.TextureComponent;

public class MaterialRenderSystem extends IteratingSystem {

    private final TextureAtlas atlas;
    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    private final ComponentMapper<MaterialComponent> materialCm;
    private final ComponentMapper<PositionComponent> positionCm;
    private final ComponentMapper<TextureComponent> textureCm;

    public MaterialRenderSystem(TextureAtlas atlas, OrthographicCamera cam) {
        super(Family.all(MaterialComponent.class, PositionComponent.class, TextureComponent.class).get());

        this.atlas = atlas;
        this.cam = cam;
        this.batch = new SpriteBatch();
        this.materialCm = ComponentMapper.getFor(MaterialComponent.class);
        this.positionCm = ComponentMapper.getFor(PositionComponent.class);
        this.textureCm = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void processEntity(Entity e, float dt) {
        PositionComponent position = positionCm.get(e);
        TextureComponent texture = textureCm.get(e);
        texture.getRegion().setRegion(atlas.findRegion(materialCm.get(e).getType().getValue()));

        cam.update();

        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        batch.draw(texture.getRegion(), position.getX(), position.getY());
        batch.end();
    }
}
