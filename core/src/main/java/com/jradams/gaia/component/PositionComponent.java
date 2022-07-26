package com.jradams.gaia.component;

import com.badlogic.ashley.core.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PositionComponent implements Component {

    private float x;
    private float y;
}
