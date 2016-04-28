package game.ctrl;


import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import game.gameView.*;


/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerController extends BetterCharacterControl implements ActionListener {

    protected final PlayerView view;
    protected InputManager inputManager;
    protected boolean left,right,up,down;
    protected float speed = 40;
    protected Vector3f lastDirection = new Vector3f(0f,0f,20f); //last direction this player moved, start value is a placeholder until real movement

    public PlayerController(PlayerView view, float radius, float height, float mass){
        super(radius, height, mass);
        this.view = view;
        this.inputManager = view.getInputManager();
        setupKeys();
    }

    private void setupKeys() {
        inputManager.addMapping("p1left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("p1right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("p1up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("p1down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("p1shoot", new KeyTrigger(KeyInput.KEY_NUMPAD0));
        inputManager.addListener(this, "p1left");
        inputManager.addListener(this, "p1right");
        inputManager.addListener(this, "p1up");
        inputManager.addListener(this, "p1down");
        inputManager.addListener(this, "p1shoot");
        inputManager.addMapping("p2left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("p2right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("p2up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("p2down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("p2shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "p2left");
        inputManager.addListener(this, "p2right");
        inputManager.addListener(this, "p2up");
        inputManager.addListener(this, "p2down");
        inputManager.addListener(this, "p2shoot");
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (!left && !right && !up && !down){
            setWalkDirection(new Vector3f(0f,0f,0f));
        }
        if (left){
            setWalkDirection(lastDirection=new Vector3f(speed*-1f,0f,0f));

        }
        if (right){
            setWalkDirection(lastDirection=new Vector3f(speed*1f,0f,0f));
        }
        if (up){
            setWalkDirection(lastDirection=new Vector3f(0f,0f,speed*1f));
        }
        if (down){
            setWalkDirection(lastDirection=new Vector3f(0f,0f,speed*-1f));
        }
        if (left && up){
            setWalkDirection(lastDirection=new Vector3f(speed*-0.707f,0f,speed*0.707f));
        }
        if (left && down){
            setWalkDirection(lastDirection=new Vector3f(speed*-0.707f,0f,speed*-0.707f));
        }
        if (right && up){
            setWalkDirection(lastDirection=new Vector3f(speed*0.707f,0f,speed*0.707f));
        }
        if (right && down){
            setWalkDirection(lastDirection=new Vector3f(speed*0.707f,0f,speed*-0.707f));
        }
        warp(new Vector3f(location.getX(),-4f, location.getZ()));
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if(view instanceof Player1View){
            if (name.equals("p1left")) {
                left = isPressed;
            } else if (name.equals("p1right")) {
                right = isPressed;
            } else if (name.equals("p1up")) {
                up = isPressed;
            } else if (name.equals("p1down")) {
                down = isPressed;
            }
            if (name.equals("p1shoot") && isPressed){
                shootBullet();
            }
        }
        else if(view instanceof Player2View){
            if (name.equals("p2left")) {
                left = isPressed;
            } else if (name.equals("p2right")) {
                right = isPressed;
            } else if (name.equals("p2up")) {
                up = isPressed;
            } else if (name.equals("p2down")) {
                down = isPressed;
            }
            if (name.equals("p2shoot") && isPressed){
                shootBullet();
            }
        }
    }



    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        BulletView bullet = new BulletView(view.getGameView(), this, view.getPlayer());
    }

    //last direction the player moved, used by bullets
    public Vector3f getLastDirection(){return this.lastDirection;}
}

