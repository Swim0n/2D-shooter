package game.ctrl;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import game.gameView.PlayerView;

/**
 * Created by Simon on 2016-04-26.
 */
public class Player2Controller extends PlayerController implements ActionListener {

    public Player2Controller(PlayerView view, float radius, float height, float mass) {
        super(view,radius,height,mass);
        setupKeys();
    }

    private void setupKeys() {
        inputManager.addMapping("left2", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right2", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("up2", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("down2", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("shoot2", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(this, "left2");
        inputManager.addListener(this, "right2");
        inputManager.addListener(this, "up2");
        inputManager.addListener(this, "down2");
        inputManager.addListener(this, "shoot2");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if (name.equals("left2")) {
            left = isPressed;
        } else if (name.equals("right2")) {
            right = isPressed;
        } else if (name.equals("up2")) {
            up = isPressed;
        } else if (name.equals("down2")) {
            down = isPressed;
        }
        if (name.equals("shoot2") && isPressed){
            this.view.shootBullet(this);
        }
    }
}
