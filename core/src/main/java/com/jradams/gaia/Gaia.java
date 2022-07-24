package com.jradams.gaia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.jradams.gaia.screen.MainScreen;
import com.jradams.gaia.screen.ScreenDispatcher;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
@Getter
@Setter
public class Gaia extends Game {

    private ScreenDispatcher dispatcher;

    @Override
    public void create() {
        dispatcher = new ScreenDispatcher();
        Screen gameScreen = new MainScreen();
        dispatcher.addScreen(gameScreen);
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        float r = 0 / 255f;
        float g = 24f / 255f;
        float b = 72f / 255f;

        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}