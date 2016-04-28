package game.ctrl;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import game.gameView.PlayerView;

/**
 * Created by David on 2016-04-19.
 */
public class Player1Controller extends PlayerController implements ActionListener {

    public Player1Controller(PlayerView view, float radius, float height, float mass) {
        super(view,radius,height,mass);
        setupKeys();
    }

    private void setupKeys() {
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("shoot", new KeyTrigger(KeyInput.KEY_NUMPAD0));
        inputManager.addListener(this, "left");
        inputManager.addListener(this, "right");
        inputManager.addListener(this, "up");
        inputManager.addListener(this, "down");
        inputManager.addListener(this, "shoot");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if (name.equals("left")) {
            left = isPressed;
        } else if (name.equals("right")) {
            right = isPressed;
        } else if (name.equals("up")) {
            up = isPressed;
        } else if (name.equals("down")) {
            down = isPressed;
        }
        if (name.equals("shoot") && isPressed){
            shootBullet();
        }
    }



}
