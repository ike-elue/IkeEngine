package com.ikeengine.render;

import com.ikeengine.util.Transform;

/**
 *
 * @author Jonathan Elue
 */
public class RenderPacket {
    private final Transform transform;
    private final View view;
    public RenderPacket(View view) {
        transform = new Transform();
        this.view = view;
    }
    
    /**
     * Returns transform object
     * @return 
     */
    public Transform getTransform() {
        return transform;
    }
    
    /**
     * Returns view of render packet
     * @return 
     */
    public View getView() {
        return view;
    }
}
