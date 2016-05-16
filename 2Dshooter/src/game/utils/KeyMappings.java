package game.utils;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

/**
 * Created by Simon on 2016-05-16.
 */
public class KeyMappings {
    private KeyTrigger left;
    private KeyTrigger right;
    private KeyTrigger up;
    private KeyTrigger down;
    private KeyTrigger shoot;
    private KeyTrigger gunLeft;
    private KeyTrigger gunRight;
    private KeyTrigger dash;

    public KeyMappings(int left, int right, int up, int down, int shoot, int gunLeft, int gunRight, int dash){
        this.left = new KeyTrigger(left);
        this.right = new KeyTrigger(right);
        this.up = new KeyTrigger(up);
        this.down = new KeyTrigger(down);
        this.shoot = new KeyTrigger(shoot);
        this.gunLeft = new KeyTrigger(gunLeft);
        this.gunRight = new KeyTrigger(gunRight);
        this.dash = new KeyTrigger(dash);
    }

    public KeyTrigger getLeft() {
        return left;
    }
    public KeyTrigger getRight() {
        return right;
    }
    public KeyTrigger getUp() {
        return up;
    }
    public KeyTrigger getDown() {
        return down;
    }
    public KeyTrigger getShoot() {
        return shoot;
    }
    public KeyTrigger getGunLeft() {
        return gunLeft;
    }
    public KeyTrigger getGunRight() {
        return gunRight;
    }
    public KeyTrigger getDash() {
        return dash;
    }
}
