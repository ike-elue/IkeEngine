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
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Collider)
            return name.equalsIgnoreCase(((Collider) o).name);
        return false;
    }
}
