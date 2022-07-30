package com.jradams.gaia.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jradams.gaia.component.BodyComponent;
import com.jradams.gaia.component.MaterialComponent;
import com.jradams.gaia.component.MovementComponent;
import com.jradams.gaia.component.PlayerComponent;
import com.jradams.gaia.component.PositionComponent;
import com.jradams.gaia.component.TextureComponent;
import com.jradams.gaia.system.CollisionSystem;
import com.jradams.gaia.system.MaterialRenderSystem;
import com.jradams.gaia.system.MovementSystem;
import com.jradams.gaia.system.PhysicsDebugRenderSystem;
import com.jradams.gaia.system.RenderSystem;
import com.jradams.gaia.util.MaterialType;
import com.jradams.gaia.util.MathUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends ScreenAdapter {

    public static final int ENV_HEIGHT = 24;
    public static final int ENV_WIDTH = 60;

    private final OrthographicCamera cam;
    private final SecureRandom random = new SecureRandom();
    private final World world = new World(new Vector2(0f, 0f), true);

    private boolean isInitialized = false;
    private PooledEngine engine;

    public MainScreen() {
        super();

        this.cam = new OrthographicCamera(1920, 1080);
        this.cam.position.set(1920 / 2f, 1080 / 2f, 0);
    }

    private void init() {
        Gdx.app.log("GameScreen", "Initializing");

        engine = new PooledEngine();
        engine.addSystem(new MaterialRenderSystem(new TextureAtlas("materials.atlas"), cam));
        engine.addSystem(new RenderSystem(cam));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PhysicsDebugRenderSystem(world, cam));
        engine.addSystem(new CollisionSystem(engine, world));

        for (Entity e : buildEnvironment()) {
            engine.addEntity(e);
        }

        engine.addEntity(buildShop());
        engine.addEntity(buildPlayer());

        isInitialized = true;
    }

    private Entity buildPlayer() {
        Entity e = engine.createEntity();
        e.add(new PlayerComponent());

        TextureComponent textureComp = new TextureComponent(new TextureRegion(new Texture(
                Gdx.files.internal("player.png")), 0, 0, 32, 32));
        e.add(textureComp);

        PositionComponent posComp = new PositionComponent((float) 1920 / 2 - 32, (ENV_HEIGHT * 32));
        e.add(posComp);

        MovementComponent movementComp = new MovementComponent(32);
        e.add(movementComp);

        BodyComponent bodyComp = new BodyComponent(createBox2dBody(32, 32, false));
        bodyComp.getBody().setUserData(e);
        e.add(bodyComp);

        return e;
    }

    private Entity buildShop() {
        Entity e = engine.createEntity();

        TextureComponent textureComp = new TextureComponent(new TextureRegion(new Texture(
                Gdx.files.internal("shop.png")), 0, 0, 128, 96));
        e.add(textureComp);

        PositionComponent posComp = new PositionComponent((float) 2880 / 2 - 128, (ENV_HEIGHT * 32));
        e.add(posComp);

        return e;
    }

    private List<Entity> buildEnvironment() {
        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < ENV_HEIGHT; i++) {
            for (int j = 0; j < ENV_WIDTH; j++) {
                Entity e = engine.createEntity();

                if (i == ENV_HEIGHT - 1) {
                    e.add(MaterialComponent.builder()
                            .hp(1)
                            .type(MaterialType.GRASS)
                            .build());
                } else {
                    e.add(MaterialComponent.builder()
                            .hp(1)
                            .type(determineMaterial(random.nextInt(100)))
                            .build());
                }

                e.add(new TextureComponent(new TextureRegion()));

                float xPos = (float) j * 32;
                float yPos = (float) i * 32;

                PositionComponent posComp = new PositionComponent(xPos, yPos);
                e.add(posComp);

                BodyComponent bodyComp = new BodyComponent(createBox2dBody(xPos + 16, yPos + 16, true));
                bodyComp.getBody().setUserData(e);
                e.add(bodyComp);

                entities.add(e);
            }
        }

        return entities;
    }

    @Override
    public void render(float dt) {
        if (isInitialized) {
            Gdx.gl.glClearColor(0, 0, 255, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            engine.update(dt);
        } else {
            init();
        }
    }

    private MaterialType determineMaterial(final float range) {
        if (MathUtils.isBetween(range, 0.01f, 0.2f)) {
            return MaterialType.AMETHYST;
        } else if (MathUtils.isBetween(range, 0.21f, 0.4f)) {
            return MaterialType.SAPPHIRE;
        } else if (MathUtils.isBetween(range, 0.41f, 0.6f)) {
            return MaterialType.RUBY;
        } else if (MathUtils.isBetween(range, 0.61f, 0.9f)) {
            return MaterialType.GOLD;
        } else if (MathUtils.isBetween(range, 0.91f, 1.35f)) {
            return MaterialType.COPPER;
        } else if (MathUtils.isBetween(range, 1.36f, 2f)) {
            return MaterialType.COAL;
        } else if (MathUtils.isBetween(range, 2.01f, 3f)) {
            return MaterialType.CLAY;
        } else if (MathUtils.isBetween(range, 3.01f, 5f)) {
            return MaterialType.STONE;
        } else {
            return MaterialType.DIRT;
        }
    }

    private Body createBox2dBody(float x, float y, boolean isStatic) {
        BodyDef bodyDef = new BodyDef();

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(4, 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);

        bodyShape.dispose();

        return body;
    }
}
