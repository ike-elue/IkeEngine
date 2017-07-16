package com.ikeengine.input;

import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Jonathan Elue
 */
public enum Key {

    GLFW_UP(GLFW.GLFW_KEY_UP),
    GLFW_DOWN(GLFW.GLFW_KEY_DOWN),
    GLFW_RIGHT(GLFW.GLFW_KEY_RIGHT),
    GLFW_LEFT(GLFW.GLFW_KEY_LEFT),
    GLFW_DELETE(GLFW.GLFW_KEY_DELETE),
    GLFW_INSERT(GLFW.GLFW_KEY_INSERT),
    GLFW_PAGE_UP(GLFW.GLFW_KEY_PAGE_UP),
    GLFW_PAGE_DOWN(GLFW.GLFW_KEY_PAGE_DOWN),
    GLFW_TAB(GLFW.GLFW_KEY_TAB),
    GLFW_LALT(GLFW.GLFW_KEY_LEFT_ALT),
    GLFW_LBRACKET(GLFW.GLFW_KEY_LEFT_BRACKET),
    GLFW_LCONTROL(GLFW.GLFW_KEY_LEFT_CONTROL),
    GLFW_LSHIFT(GLFW.GLFW_KEY_LEFT_SHIFT),
    GLFW_RALT(GLFW.GLFW_KEY_RIGHT_ALT),
    GLFW_RBRACKET(GLFW.GLFW_KEY_RIGHT_BRACKET),
    GLFW_RCONTROL(GLFW.GLFW_KEY_RIGHT_CONTROL),
    GLFW_RSHIFT(GLFW.GLFW_KEY_RIGHT_SHIFT),
    GLFW_SPACE(GLFW.GLFW_KEY_SPACE),
    GLFW_APOSTROPHE(GLFW.GLFW_KEY_APOSTROPHE),
    GLFW_COMMA(GLFW.GLFW_KEY_COMMA),
    GLFW_MINUS(GLFW.GLFW_KEY_MINUS),
    GLFW_PERIOD(GLFW.GLFW_KEY_PERIOD),
    GLFW_SLASH(GLFW.GLFW_KEY_SLASH),
    GLFW_0(GLFW.GLFW_KEY_0),
    GLFW_1(GLFW.GLFW_KEY_1),
    GLFW_2(GLFW.GLFW_KEY_2),
    GLFW_3(GLFW.GLFW_KEY_3),
    GLFW_4(GLFW.GLFW_KEY_4),
    GLFW_5(GLFW.GLFW_KEY_5),
    GLFW_6(GLFW.GLFW_KEY_6),
    GLFW_7(GLFW.GLFW_KEY_7),
    GLFW_8(GLFW.GLFW_KEY_8),
    GLFW_9(GLFW.GLFW_KEY_9),
    GLFW_SEMI_COLON(GLFW.GLFW_KEY_SEMICOLON),
    GLFW_EQUAL(GLFW.GLFW_KEY_EQUAL),
    GLFW_A(GLFW.GLFW_KEY_A),
    GLFW_B(GLFW.GLFW_KEY_B),
    GLFW_C(GLFW.GLFW_KEY_C),
    GLFW_D(GLFW.GLFW_KEY_D),
    GLFW_E(GLFW.GLFW_KEY_E),
    GLFW_F(GLFW.GLFW_KEY_F),
    GLFW_G(GLFW.GLFW_KEY_G),
    GLFW_H(GLFW.GLFW_KEY_H),
    GLFW_I(GLFW.GLFW_KEY_I),
    GLFW_J(GLFW.GLFW_KEY_J),
    GLFW_K(GLFW.GLFW_KEY_K),
    GLFW_L(GLFW.GLFW_KEY_L),
    GLFW_M(GLFW.GLFW_KEY_M),
    GLFW_N(GLFW.GLFW_KEY_N),
    GLFW_O(GLFW.GLFW_KEY_O),
    GLFW_P(GLFW.GLFW_KEY_P),
    GLFW_Q(GLFW.GLFW_KEY_Q),
    GLFW_R(GLFW.GLFW_KEY_R),
    GLFW_S(GLFW.GLFW_KEY_S),
    GLFW_T(GLFW.GLFW_KEY_T),
    GLFW_U(GLFW.GLFW_KEY_U),
    GLFW_V(GLFW.GLFW_KEY_V),
    GLFW_W(GLFW.GLFW_KEY_W),
    GLFW_X(GLFW.GLFW_KEY_X),
    GLFW_Y(GLFW.GLFW_KEY_Y),
    GLFW_Z(GLFW.GLFW_KEY_Z),
    GLFW_BACKSLASH(GLFW.GLFW_KEY_BACKSLASH),
    GLFW_GRAVE_ACCENT(GLFW.GLFW_KEY_GRAVE_ACCENT),
    GLFW_BACKSPACE(GLFW.GLFW_KEY_BACKSPACE),
    GLFW_ESC(GLFW.GLFW_KEY_ESCAPE);

    private final Integer keycode;
    public boolean down, pressed, released;
    
    private Key(int keycode) {
        this.keycode = keycode;
        down = false;
        pressed = false;
        released = false;
    }

    public Integer value() {
        return keycode;
    }
    
    public void determineValues(boolean isReleased) {
        pressed = !isReleased && !down; 
        released =  isReleased && down; 
        down = !isReleased;
    }
    
    public boolean canBeSent() {
        return down || pressed || released;
    }
    
    public void update() {
        pressed = false;
        released = false;
    }
}