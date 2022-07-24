package com.jradams.gaia.screen;

import com.badlogic.gdx.Screen;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScreenDispatcher {

    private final List<Screen> screens;

    public ScreenDispatcher() {
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        getScreens().add(screen);
    }
}
