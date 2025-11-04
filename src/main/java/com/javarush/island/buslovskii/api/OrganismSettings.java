package com.javarush.island.buslovskii.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrganismSettings {
    String name();
    String icon();
    double maxWeight();
    int maxInCell();
    int maxSpeed();
    double maxFood();
    int flockSize();
}
