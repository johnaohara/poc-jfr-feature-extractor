package io.quarkus;

import org.reflections.Reflections;

import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class SomeClass {
    private Reflections reflections;

    public SomeClass() {
        reflections = new Reflections("io.quarkus");
    }

    public Set<Class<?>> getAllObjects(){
        return reflections.get(SubTypes.of(Object.class).asClass());
    }
}
