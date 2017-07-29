package com.ikeengine.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 *
 * @author Jonathan Elue
 */
public class Transform {
    private final Vector3f translation;
    private float rotation;
    private final Vector2f scale;
    
    /**
     * Constructs empty Transform object
     */
    public Transform() {
        translation = new Vector3f();
        rotation = 0;
        scale = new Vector2f();
    }
    /**
     * Translates the transform by (x,y,z)
     * @param x 
     * @param y
     * @param z
     * @return transformed vector
     */
    public Vector3f translate(float x, float y, float z) {
        return translation.set(translation.x + x, translation.y + y, translation.z + z);
    }
    
    /**
     * Rotates the transform about the z axis based on amount
     * @param amount
     * @return 
     */
    public float rotate(float amount) {
        rotation += amount;
        return rotation;
    }
    
    /**
     * Scales the transform vector by (x,y)
     * @param x 
     * @param y
     * @return transformed vector
     */
    public Vector2f scale(float x, float y) {
        return scale.set(scale.x + x, scale.y + y);
    }
    
    /**
     * Translates the transform by 2D Vector
     * @param v
     * @return transformed vector
     */
    public Vector3f translate(Vector2f v) {
        return translation.set(translation.x + v.x, translation.y + v.y, translation.z);
    }

    /**
     * Scales the transform by 2D Vector
     * @param v
     * @return transformed vector
     */
    public Vector2f scale(Vector2f v) {
        return scale.set(scale.x + v.x, scale.y + v.y);
    }
    
    /**
     * Sets the translation's 'x' and 'y'
     * @param x
     * @param y
     * @return 
     */
    public Vector3f setTranslation(float x, float y) {
        return translation.set(x, y, translation.z);
    }
    
    /**
     * Sets the rotation
     * @param amount
     * @return 
     */
    public float setRotation(float amount) {
        rotation = amount;
        return rotation;
    }
    
    /**
     * Sets the scale's 'x' and 'y'
     * @param x
     * @param y
     * @return 
     */
    public Vector2f setScale(float x, float y) {
        return scale.set(x, y);
    }
    
    /**
     * Copys all contents of another transform into this one
     * @param t 
     */
    public void copy(Transform t) {
        setTranslation(t.translation.x, t.translation.y);
        setRotation(t.rotation);
        setScale(t.scale.x, t.scale.y);
    }
    
    public Vector3f getTranslation() {
        return translation;
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public Vector2f getScale() {
        return scale;
    }
}
