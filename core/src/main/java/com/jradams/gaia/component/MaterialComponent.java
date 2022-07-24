package com.jradams.gaia.component;

import com.badlogic.ashley.core.Component;
import com.jradams.gaia.util.MaterialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MaterialComponent implements Component {

    private final int hp;
    private final MaterialType type;
}
