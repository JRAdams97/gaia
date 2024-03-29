package com.jradams.gaia.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jradams.gaia.Gaia;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {

    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Gaia(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration conf = new Lwjgl3ApplicationConfiguration();
        conf.setTitle("gaia");
        conf.useVsync(true);

        // Limits FPS to the refresh rate of the currently active monitor.
        conf.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);

        // If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        // useful for testing performance, but can also be very stressful to some hardware.
        // You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        conf.setWindowedMode(1920, 1080);
        conf.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return conf;
    }
}