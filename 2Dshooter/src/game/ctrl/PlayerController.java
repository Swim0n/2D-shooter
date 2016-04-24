package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import game.gameView.GameView;
import game.gameView.PlayerView;

/**
 * Created by David on 2016-04-19.
 */
public class PlayerController extends BetterCharacterControl implements ActionListener {

    private final PlayerView view;
    private InputManager inputManager;
    private boolean left,right,up,down;
    private float speed = 80;

    public PlayerController(PlayerView view, float radius, float height, float mass){
        super(radius, height, mass);
        this.view = view;
        this.inputManager = view.getInputManager();
        setupKeys();
    }

    private void setupKeys() {
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

    @Override
    public void update(float tpf) {
        super.update(tpf);
        float speedtpf = speed*tpf;
        if (!left && !right && !up && !down){
            setWalkDirection(new Vector3f(0f,0f,0f));
        }
        if (left){
            setWalkDirection(new Vector3f(speedtpf*-500f,0f,0f));
        }
        if (right){
            setWalkDirection(new Vector3f(speedtpf*500f,0f,0f));
        }
        if (up){
            setWalkDirection(new Vector3f(0f,0f,speedtpf*500f));
        }
        if (down){
            setWalkDirection(new Vector3f(0f,0f,speedtpf*-500f));
        }
        if (left && up){
            setWalkDirection(new Vector3f(speedtpf*-353.6f,0f,speedtpf*353.6f));
        }
        if (left && down){
            setWalkDirection(new Vector3f(speedtpf*-353.6f,0f,speedtpf*-353.6f));
        }
        if (right && up){
            setWalkDirection(new Vector3f(speedtpf*353.6f,0f,speedtpf*353.6f));
        }
        if (right && down){
            setWalkDirection(new Vector3f(speedtpf*353.6f,0f,speedtpf*-353.6f));
        }
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
