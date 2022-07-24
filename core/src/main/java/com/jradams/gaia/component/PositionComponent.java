package com.jradams.gaia.component;

import com.badlogic.ashley.core.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PositionComponent implements Component {

    private final float x;
    private final float y;
}
