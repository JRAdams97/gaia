package com.jradams.gaia.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HudNumberComponent implements Component {

    private BitmapFont font;
    private CharSequence label;
    private int value;
}
