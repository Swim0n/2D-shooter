package game.ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import game.core.Player;
import game.gameView.*;
import game.utils.ApplicationAssets;
import game.utils.KeyMappings;
import game.utils.Utils;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.UUID;

/**
 * Created by Simon on 2016-04-26.
 */
public class HumanPlayerController extends PlayerController implements ActionListener {
    private InputManager inputManager;
    private KeyMappings keys;
    String[] mapNames;


    public HumanPlayerController(PlayerView view, Player player, GUIView niftyView, ApplicationAssets appAssets, KeyMappings keys){
        super(view,player,niftyView);
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
        for(int i = 0; i < mapNames.length; i++){
            inputManager.addListener(this, mapNames[i]);
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        playerData.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));
        setWalkDirection(Utils.vecMathToJMEVector3f(playerData.getWalkingDirection()));
        playerView.rotateGun(playerData.getGunRotation()*tpf);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //resetting the game to its original state
        if (name.equals("resetGame") && !isPressed){
            this.resetPlayer();
        }
        //movement of player
        if (name.equals(mapNames[0])) {
            playerData.left = isPressed;
        } else if (name.equals(mapNames[1])) {
            playerData.right = isPressed;
        } else if (name.equals(mapNames[2])) {
            playerData.up = isPressed;
        } else if (name.equals(mapNames[3])) {
            playerData.down = isPressed;
        }
        if (name.equals(mapNames[4]) && isPressed){
            shootBullet();
        }
        if(name.equals(mapNames[5])){
            playerData.gunLeft = isPressed;
        }else if(name.equals(mapNames[6])){
            playerData.gunRight = isPressed;
        }
        if(name.equals(mapNames[7]) && !isPressed && !playerData.dashing){
            playerData.dashing = true;
            dashTimer(playerData.getDashMillis());
        }
    }

    public void dashTimer(int millis){
        Timer timer = new Timer(millis, new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerData.dashing = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

