package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import game.gameView.GameView;

/**
 * Created by David on 2016-04-19.
 */
public class PlayerController extends BetterCharacterControl implements ActionListener {
    private boolean left,right,up,down;
    //movement speed for players
    //private float speed = 100;

    private final GameView view;

    private InputManager inputManager;

    public PlayerController(GameView view, float radius, float height, float mass){
        super(radius, height, mass);
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



    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }


    public void setWalkDirection(Vector3f vec, boolean b){
        if(b){
            this.walkDirection.set(vec);
        } else {
            this.walkDirection.set(new Vector3f(0f,0f,0f));
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if(name.equals("left")){
            setWalkDirection(new Vector3f(-25f,0f,0f), isPressed);
        }else if(name.equals("right")){
            setWalkDirection(new Vector3f(25f,0f,0f), isPressed);
        }else if(name.equals("up")){
            setWalkDirection(new Vector3f(0f,0f,25f), isPressed);
        }else if(name.equals("down")){
            setWalkDirection(new Vector3f(0f,0f,-25f), isPressed);
        }
    }
}
