package jME3.utils;


import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;


/**
 * To be used in remapping controllers which is an unfinished feature at the moment
 */
public class ReadKeys implements RawInputListener {

    private int pressedKey;
    private boolean done;

    @Override
    public void beginInput() {

    }

    @Override
    public void endInput() {

    }

    @Override
    public void onJoyAxisEvent(JoyAxisEvent joyAxisEvent) {

    }

    @Override
    public void onJoyButtonEvent(JoyButtonEvent joyButtonEvent) {

    }

    @Override
    public void onMouseMotionEvent(MouseMotionEvent mouseMotionEvent) {

    }

    @Override
    public void onMouseButtonEvent(MouseButtonEvent mouseButtonEvent) {

    }

    @Override
    public void onKeyEvent(KeyInputEvent keyInputEvent) {
        if(done){
            return;
        }
        pressedKey = keyInputEvent.getKeyCode();
        done = true;
    }

    @Override
    public void onTouchEvent(TouchEvent touchEvent) {

    }

    public int getPressedKey() {
        return pressedKey;
    }
}
