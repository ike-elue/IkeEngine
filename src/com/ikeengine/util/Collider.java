package com.ikeengine.util;

/**
 *
 * @author Jonathan Elue
 */
public class Collider {

    public final Transform transform;
    public final String name;

    public Collider(String name, Transform transform) {
        this.transform = transform;
        this.name = name;
    }

    public void update(Transform transform) {
        this.transform.copy(transform);
    }
}
