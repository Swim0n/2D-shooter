package ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import core.Player;
import gameView.GameView;
import gameView.PlayerView;
import utils.KeyMappings;
import utils.Utils;

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


    public HumanPlayerController(PlayerView view, Player player, GameView gameView, KeyMappings keys){
        super(view,player, gameView);
        this.niftyView = gameView.getNiftyView();
        this.inputManager = gameView.getInputManager();
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

        Vector3f newDirection = Utils.vecMathToJMEVector3f(playerData.getWalkingDirection());
        playerData.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));

        if(!newDirection.equals(Vector3f.ZERO)){
            playerView.getBodyNode().lookAt(playerView.getPlayerNode().getLocalTranslation().add(newDirection), Vector3f.UNIT_Y);
        }

        setWalkDirection(newDirection);
        playerView.rotateGun(playerData.getGunRotation()*tpf);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //resetting the java to its original state
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
            playerView.playDashSound();
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

