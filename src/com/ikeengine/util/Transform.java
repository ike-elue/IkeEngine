package com.ikeengine.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 *
 * @author Jonathan Elue
 */
public class Transform {
    public final Vector3f translation, rotation, scale;
    public Transform() {
        translation = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f();
    }
    public Vector3f translate(float x, float y, float z) {
        return translation.set(translation.x + x, translation.y + y, translation.z + z);
    }
    public Vector3f rotate(float x, float y, float z) {
        return rotation.set(rotation.x + x, rotation.y + y, rotation.z + z);
    }
    
    public Vector3f scale(float x, float y, float z) {
        return scale.set(scale.x + x, scale.y + y, scale.z + z);
    }
    
    public Vector3f translate(Vector2f v) {
        return translation.set(translation.x + v.x, translation.y + v.y, translation.z);
    }
    public Vector3f rotate(Vector2f v) {
        return rotation.set(rotation.x + v.x, rotation.y + v.y, rotation.z);
    }
    
    public Vector3f scale(Vector2f v) {
        return scale.set(scale.x + v.x, scale.y + v.y, scale.z);
    }
    
    public Vector3f setTranslation(float x, float y) {
        return translation.set(x, y, translation.z);
    }
    
    public Vector3f setRotation(float x, float y) {
        return rotation.set(x, y, rotation.z);
    }
    
    public Vector3f setScale(float x, float y) {
        return scale.set(x, y, scale.z);
    }
    
    public void copy(Transform t) {
        setTranslation(t.translation.x, t.translation.y);
        setRotation(t.rotation.x, t.rotation.y);
        setScale(t.scale.x, t.scale.y);
    }
}
