package com.ikeengine.util;

import org.joml.Vector2f;

/**
 *
 * @author Jonathan Elue
 */
public class Transform {
    protected final Vector2f translation, rotation, scale;
    public Transform() {
        translation = new Vector2f();
        rotation = new Vector2f();
        scale = new Vector2f();
    }
    public Vector2f translate(float x, float y) {
        return translation.set(translation.x + x, translation.y + y);
    }
    public Vector2f rotate(float x, float y) {
        return rotation.set(rotation.x + x, rotation.y + y);
    }
    
    public Vector2f scale(float x, float y) {
        return scale.set(scale.x + x, scale.y + y);
    }
    
    public Vector2f translate(Vector2f v) {
        return translation.set(translation.x + v.x, translation.y + v.y);
    }
    public Vector2f rotate(Vector2f v) {
        return rotation.set(rotation.x + v.x, rotation.y + v.y);
    }
    
    public Vector2f scale(Vector2f v) {
        return scale.set(scale.x + v.x, scale.y + v.y);
    }
    
    public Vector2f setTranslation(float x, float y) {
        return translation.set(x, y);
    }
    
    public Vector2f setRotation(float x, float y) {
        return rotation.set(x, y);
    }
    
    public Vector2f setScale(float x, float y) {
        return scale.set(x, y);
    }
    
    public void copy(Transform t) {
        setTranslation(t.translation.x, t.translation.y);
        setRotation(t.rotation.x, t.rotation.y);
        setScale(t.scale.x, t.scale.y);
    }
}
