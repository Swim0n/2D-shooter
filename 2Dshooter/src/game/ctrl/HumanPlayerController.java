package game.ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import game.gameView.*;
import game.utils.ApplicationAssets;

/**
 * Created by Simon on 2016-04-26.
 */
public class HumanPlayerController extends PlayerController implements ActionListener {
    private InputManager inputManager;
    private boolean left,right,up,down,gunLeft,gunRight;

    public HumanPlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, ApplicationAssets appAssets){
        super(view,radius,height,mass,niftyView,appAssets.getWorld());
        this.inputManager = appAssets.getInputManager();
        setupKeys();
    }

    private void setupKeys() {
        inputManager.addMapping("resetGame",new KeyTrigger(KeyInput.KEY_F8));
        inputManager.addListener(this, "resetGame");
        if(playerView.getPlayerNode().equals(playerView.getGameView().getPlayer1Node())) {
            inputManager.addMapping("p1left", new KeyTrigger(KeyInput.KEY_LEFT));
            inputManager.addMapping("p1right", new KeyTrigger(KeyInput.KEY_RIGHT));
            inputManager.addMapping("p1up", new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("p1down", new KeyTrigger(KeyInput.KEY_DOWN));
            inputManager.addMapping("p1shoot", new KeyTrigger(KeyInput.KEY_NUMPAD5), new KeyTrigger(KeyInput.KEY_RETURN));
            inputManager.addMapping("p1GunLeft", new KeyTrigger(KeyInput.KEY_O),new KeyTrigger(KeyInput.KEY_NUMPAD4));
            inputManager.addMapping("p1GunRight", new KeyTrigger(KeyInput.KEY_P),new KeyTrigger(KeyInput.KEY_NUMPAD6));

            inputManager.addListener(this, "p1left");
            inputManager.addListener(this, "p1right");
            inputManager.addListener(this, "p1up");
            inputManager.addListener(this, "p1down");
            inputManager.addListener(this, "p1shoot");
            inputManager.addListener(this, "p1GunLeft");
            inputManager.addListener(this, "p1GunRight");
        }
        if(playerView.getPlayerNode().equals(playerView.getGameView().getPlayer2Node())) {
            inputManager.addMapping("p2left", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addMapping("p2right", new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("p2up", new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("p2down", new KeyTrigger(KeyInput.KEY_S));
            inputManager.addMapping("p2shoot", new KeyTrigger(KeyInput.KEY_SPACE),new KeyTrigger(KeyInput.KEY_H));
            inputManager.addMapping("p2GunLeft", new KeyTrigger(KeyInput.KEY_G));
            inputManager.addMapping("p2GunRight", new KeyTrigger(KeyInput.KEY_J));
            inputManager.addListener(this, "p2left");
            inputManager.addListener(this, "p2right");
            inputManager.addListener(this, "p2up");
            inputManager.addListener(this, "p2down");
            inputManager.addListener(this, "p2shoot");
            inputManager.addListener(this, "p2GunLeft");
            inputManager.addListener(this, "p2GunRight");
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (!left && !right && !up && !down){
            setWalkDirection(new Vector3f(0f,0f,0f));
        }
        if (left){
            setWalkDirection(lastDirection.set(speed*-1f,0f,0f));
        }
        if (right){
            setWalkDirection(lastDirection.set(speed*1f,0f,0f));
        }
        if (up){
            setWalkDirection(lastDirection.set(0f,0f,speed*1f));
        }
        if (down){
            setWalkDirection(lastDirection.set(0f,0f,speed*-1f));
        }
        if (left && up){
            setWalkDirection(lastDirection.set(speed*-0.707f,0f,speed*0.707f));
        }
        if (left && down){
            setWalkDirection(lastDirection.set(speed*-0.707f,0f,speed*-0.707f));
        }
        if (right && up){
            setWalkDirection(lastDirection.set(speed*0.707f,0f,speed*0.707f));
        }
        if (right && down){
            setWalkDirection(lastDirection.set(speed*0.707f,0f,speed*-0.707f));
        }
        if (gunLeft){
            playerView.rotateGun(tpf*-140f);
        }
        if(gunRight){
            playerView.rotateGun(tpf*140f);
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //resetting the game to its original state
        if (name.equals("resetGame") && !isPressed){
            this.resetPlayer();
        }
        //movement of player
        if(playerView.getPlayerNode().equals(playerView.getGameView().getPlayer1Node())){
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
            if(name.equals("p1GunLeft")){
                gunLeft = isPressed;
            }else if(name.equals("p1GunRight")){
                gunRight = isPressed;
            }
        }
        else if(playerView.getPlayerNode().equals(playerView.getGameView().getPlayer2Node())){
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
            if(name.equals("p2GunLeft")){
                gunLeft = isPressed;
            }else if(name.equals("p2GunRight")){
                gunRight = isPressed;
            }
        }
    }
}

