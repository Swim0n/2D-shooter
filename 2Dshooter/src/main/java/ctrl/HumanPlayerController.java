package ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import core.Player;
import view.PlayerView;
import view.WorldView;
import jME3.utils.KeyMappings;
import jME3.utils.Utils;

import java.util.UUID;

/**
 * Created by Simon on 2016-04-26.
 */
public class HumanPlayerController extends PlayerController implements ActionListener {
    private InputManager inputManager;
    private KeyMappings keys;
    String[] mapNames;
    private long lastOverload = 0;


    public HumanPlayerController(PlayerView view, Player player, WorldView worldView, KeyMappings keys){
        super(view,player, worldView);
        this.niftyView = worldView.getNiftyView();
        this.inputManager = worldView.getInputManager();
        this.keys = keys;
        this.mapNames = new String[8];
        initKeys();
    }

    private void initKeys() {
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

    public void setKeyMappings(KeyMappings keys){
        this.keys = keys;
        inputManager.clearMappings();
        initKeys();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        Vector3f newDirection = Utils.vecMathToJMEVector3f(player.getWalkingDirection());
        player.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));

        if(!newDirection.equals(Vector3f.ZERO)){
            playerView.getBodyNode().lookAt(playerView.getPlayerNode().getLocalTranslation().add(newDirection), Vector3f.UNIT_Y);
        }

        setWalkDirection(newDirection);

    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if (name.equals(mapNames[0])) {
            player.left = isPressed;
        } else if (name.equals(mapNames[1])) {
            player.right = isPressed;
        } else if (name.equals(mapNames[2])) {
            player.up = isPressed;
        } else if (name.equals(mapNames[3])) {
            player.down = isPressed;
        }
        if (name.equals(mapNames[4]) && isPressed){
            if (player.getShotMeterPercent() >= player.getShotThreshold() && !player.isOverloaded()) {
                shootBullet();
            } else {
                if(System.currentTimeMillis() - lastOverload > player.getOverloadDuration()){
                    player.overload();
                    playerView.playOverloadSound();
                    lastOverload = System.currentTimeMillis();
                }
            }
        }
        if(name.equals(mapNames[5])){
            player.gunLeft = isPressed;
        }else if(name.equals(mapNames[6])){
            player.gunRight = isPressed;
        }
        if(name.equals(mapNames[7]) && !isPressed && !player.dashing){
            if (player.getDashMeterPercent() >= player.getDashThreshold()){
                dash();
            }
        }
    }


}

