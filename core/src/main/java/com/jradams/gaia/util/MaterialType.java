package com.jradams.gaia.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialType {

    UNKNOWN(""),
    AMETHYST("amethyst"),
    BEDROCK("bedrock"),
    CLAY("clay"),
    COAL("coal"),
    COPPER("copper"),
    DIRT("dirt"),
    GOLD("gold"),
    GRASS("grass"),
    RUBY("ruby"),
    SAPPHIRE("sapphire"),
    STONE("stone");

    private final String value;
}
