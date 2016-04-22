package game.ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import game.gameView.GameView;

/**
 * Created by David on 2016-04-19.
 */
public class PlayerController extends AbstractControl implements ActionListener {
    private boolean left,right,up,down;
    //movement speed for players
    private float speed = 100;

    private final GameView view;

    private InputManager inputManager;

    public PlayerController(GameView view){
        this.view = view;
        this.inputManager = view.getInputManager();
        setupKeys();
    }

    private void setupKeys() {
        //creating inputmappings and adding listeners
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addListener(this, "left");
        inputManager.addListener(this, "right");
        inputManager.addListener(this, "up");
        inputManager.addListener(this, "down");
    }



    protected void controlUpdate(float tpf) {
        if(left){
            spatial.move(tpf*-speed,0,0);
        }else if(right){
            spatial.move(tpf*speed,0,0);
        }else if(up){
            spatial.move(0,tpf*speed,0);
        }else if(down){
            spatial.move(0,tpf*-speed,0);
        }
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if(name.equals("left")){
            left = isPressed;
        }else if(name.equals("right")){
            right = isPressed;
        }else if(name.equals("up")){
            up = isPressed;
        }else if(name.equals("down")){
            down = isPressed;
        }
    }
}
