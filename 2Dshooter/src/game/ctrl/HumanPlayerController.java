package game.ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import game.gameView.*;
import game.utils.ApplicationAssets;
import game.utils.KeyMappings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

/**
 * Created by Simon on 2016-04-26.
 */
public class HumanPlayerController extends PlayerController implements ActionListener {
    private InputManager inputManager;
    private boolean left,right,up,down,gunLeft,gunRight,dashing,dashLeft;
    private KeyMappings keys;
    String[] mapNames;


    public HumanPlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, ApplicationAssets appAssets, KeyMappings keys){
        super(view,radius,height,mass,niftyView,appAssets.getWorld());
        this.inputManager = appAssets.getInputManager();
        this.keys = keys;
        this.mapNames = new String[8];
        setupKeys();
    }

    private void setupKeys() {
        inputManager.addMapping("resetGame",new KeyTrigger(KeyInput.KEY_F8));
        inputManager.addListener(this, "resetGame");
        //generating random strings as names for the mappings, allowing for multiple instances of this controller
        for(int i = 0; i < mapNames.length; i++){
            mapNames[i] = UUID.randomUUID().toString();
        }
        inputManager.addMapping(mapNames[0], keys.getLeft());
        inputManager.addMapping(mapNames[1], keys.getRight());
        inputManager.addMapping(mapNames[2], keys.getUp());
        inputManager.addMapping(mapNames[3], keys.getDown());
        inputManager.addMapping(mapNames[4], keys.getShoot());
        inputManager.addMapping(mapNames[5], keys.getGunLeft());
        inputManager.addMapping(mapNames[6], keys.getGunRight());
        inputManager.addMapping(mapNames[7], keys.getDash());
        inputManager.addListener(this, mapNames[0]);
        inputManager.addListener(this, mapNames[1]);
        inputManager.addListener(this, mapNames[2]);
        inputManager.addListener(this, mapNames[3]);
        inputManager.addListener(this, mapNames[4]);
        inputManager.addListener(this, mapNames[5]);
        inputManager.addListener(this, mapNames[6]);
        inputManager.addListener(this, mapNames[7]);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (dashing){
            setWalkDirection(getDashDirection(dashLeft).mult(playerData.getDashSpeed()));
            return;
        }
        if (!left && !right && !up && !down && !dashing ){
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

        if (name.equals(mapNames[0])) {
            left = isPressed;
        } else if (name.equals(mapNames[1])) {
            right = isPressed;
        } else if (name.equals(mapNames[2])) {
            up = isPressed;
        } else if (name.equals(mapNames[3])) {
            down = isPressed;
        }
        if (name.equals(mapNames[4]) && isPressed){
            shootBullet();
        }
        if(name.equals(mapNames[5])){
            gunLeft = isPressed;
        }else if(name.equals(mapNames[6])){
            gunRight = isPressed;
        }
        if(name.equals(mapNames[7]) && !isPressed && !dashing){
            dashing = true;
            dashLeft = true;
            dashTimer(playerData.getDashMillis());
        }
    }

    public void dashTimer(int millis){
        Timer timer = new Timer(millis, new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashing = false;
                dashLeft = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

