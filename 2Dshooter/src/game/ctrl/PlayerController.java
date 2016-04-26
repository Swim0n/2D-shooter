package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;

import com.jme3.math.Vector3f;
import game.gameView.PlayerView;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerController extends BetterCharacterControl {

    protected final PlayerView view;
    protected InputManager inputManager;
    protected boolean left,right,up,down;
    protected float speed = 80;

    public PlayerController(PlayerView view, float radius, float height, float mass){
        super(radius, height, mass);
        this.view = view;
        this.inputManager = view.getInputManager();
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
}

